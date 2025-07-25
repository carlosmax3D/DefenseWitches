package com.facebook.internal;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class FileLruCache {
    private static final String HEADER_CACHEKEY_KEY = "key";
    private static final String HEADER_CACHE_CONTENT_TAG_KEY = "tag";
    static final String TAG = FileLruCache.class.getSimpleName();
    /* access modifiers changed from: private */
    public static final AtomicLong bufferIndex = new AtomicLong();
    private final File directory;
    private boolean isTrimInProgress;
    private boolean isTrimPending;
    /* access modifiers changed from: private */
    public AtomicLong lastClearCacheTime = new AtomicLong(0);
    private final Limits limits;
    private final Object lock;
    private final String tag;

    private static class BufferFile {
        private static final String FILE_NAME_PREFIX = "buffer";
        private static final FilenameFilter filterExcludeBufferFiles = new FilenameFilter() {
            public boolean accept(File file, String str) {
                return !str.startsWith(BufferFile.FILE_NAME_PREFIX);
            }
        };
        private static final FilenameFilter filterExcludeNonBufferFiles = new FilenameFilter() {
            public boolean accept(File file, String str) {
                return str.startsWith(BufferFile.FILE_NAME_PREFIX);
            }
        };

        private BufferFile() {
        }

        static void deleteAll(File file) {
            File[] listFiles = file.listFiles(excludeNonBufferFiles());
            if (listFiles != null) {
                for (File delete : listFiles) {
                    delete.delete();
                }
            }
        }

        static FilenameFilter excludeBufferFiles() {
            return filterExcludeBufferFiles;
        }

        static FilenameFilter excludeNonBufferFiles() {
            return filterExcludeNonBufferFiles;
        }

        static File newFile(File file) {
            return new File(file, FILE_NAME_PREFIX + Long.valueOf(FileLruCache.bufferIndex.incrementAndGet()).toString());
        }
    }

    private static class CloseCallbackOutputStream extends OutputStream {
        final StreamCloseCallback callback;
        final OutputStream innerStream;

        CloseCallbackOutputStream(OutputStream outputStream, StreamCloseCallback streamCloseCallback) {
            this.innerStream = outputStream;
            this.callback = streamCloseCallback;
        }

        public void close() throws IOException {
            try {
                this.innerStream.close();
            } finally {
                this.callback.onClose();
            }
        }

        public void flush() throws IOException {
            this.innerStream.flush();
        }

        public void write(int i) throws IOException {
            this.innerStream.write(i);
        }

        public void write(byte[] bArr) throws IOException {
            this.innerStream.write(bArr);
        }

        public void write(byte[] bArr, int i, int i2) throws IOException {
            this.innerStream.write(bArr, i, i2);
        }
    }

    private static final class CopyingInputStream extends InputStream {
        final InputStream input;
        final OutputStream output;

        CopyingInputStream(InputStream inputStream, OutputStream outputStream) {
            this.input = inputStream;
            this.output = outputStream;
        }

        public int available() throws IOException {
            return this.input.available();
        }

        public void close() throws IOException {
            try {
                this.input.close();
            } finally {
                this.output.close();
            }
        }

        public void mark(int i) {
            throw new UnsupportedOperationException();
        }

        public boolean markSupported() {
            return false;
        }

        public int read() throws IOException {
            int read = this.input.read();
            if (read >= 0) {
                this.output.write(read);
            }
            return read;
        }

        public int read(byte[] bArr) throws IOException {
            int read = this.input.read(bArr);
            if (read > 0) {
                this.output.write(bArr, 0, read);
            }
            return read;
        }

        public int read(byte[] bArr, int i, int i2) throws IOException {
            int read = this.input.read(bArr, i, i2);
            if (read > 0) {
                this.output.write(bArr, i, read);
            }
            return read;
        }

        public synchronized void reset() {
            throw new UnsupportedOperationException();
        }

        public long skip(long j) throws IOException {
            int read;
            byte[] bArr = new byte[1024];
            long j2 = 0;
            while (j2 < j && (read = read(bArr, 0, (int) Math.min(j - j2, (long) bArr.length))) >= 0) {
                j2 += (long) read;
            }
            return j2;
        }
    }

    public static final class Limits {
        private int byteCount = AccessibilityEventCompat.TYPE_TOUCH_INTERACTION_START;
        private int fileCount = 1024;

        /* access modifiers changed from: package-private */
        public int getByteCount() {
            return this.byteCount;
        }

        /* access modifiers changed from: package-private */
        public int getFileCount() {
            return this.fileCount;
        }

        /* access modifiers changed from: package-private */
        public void setByteCount(int i) {
            if (i < 0) {
                throw new InvalidParameterException("Cache byte-count limit must be >= 0");
            }
            this.byteCount = i;
        }

        /* access modifiers changed from: package-private */
        public void setFileCount(int i) {
            if (i < 0) {
                throw new InvalidParameterException("Cache file count limit must be >= 0");
            }
            this.fileCount = i;
        }
    }

    private static final class ModifiedFile implements Comparable<ModifiedFile> {
        private static final int HASH_MULTIPLIER = 37;
        private static final int HASH_SEED = 29;
        private final File file;
        private final long modified;

        ModifiedFile(File file2) {
            this.file = file2;
            this.modified = file2.lastModified();
        }

        public int compareTo(ModifiedFile modifiedFile) {
            if (getModified() < modifiedFile.getModified()) {
                return -1;
            }
            if (getModified() > modifiedFile.getModified()) {
                return 1;
            }
            return getFile().compareTo(modifiedFile.getFile());
        }

        public boolean equals(Object obj) {
            return (obj instanceof ModifiedFile) && compareTo((ModifiedFile) obj) == 0;
        }

        /* access modifiers changed from: package-private */
        public File getFile() {
            return this.file;
        }

        /* access modifiers changed from: package-private */
        public long getModified() {
            return this.modified;
        }

        public int hashCode() {
            return ((this.file.hashCode() + 1073) * HASH_MULTIPLIER) + ((int) (this.modified % 2147483647L));
        }
    }

    private interface StreamCloseCallback {
        void onClose();
    }

    private static final class StreamHeader {
        private static final int HEADER_VERSION = 0;

        private StreamHeader() {
        }

        static JSONObject readHeader(InputStream inputStream) throws IOException {
            if (inputStream.read() != 0) {
                return null;
            }
            int i = 0;
            for (int i2 = 0; i2 < 3; i2++) {
                int read = inputStream.read();
                if (read == -1) {
                    Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: stream.read returned -1 while reading header size");
                    return null;
                }
                i = (i << 8) + (read & MotionEventCompat.ACTION_MASK);
            }
            byte[] bArr = new byte[i];
            int i3 = 0;
            while (i3 < bArr.length) {
                int read2 = inputStream.read(bArr, i3, bArr.length - i3);
                if (read2 < 1) {
                    Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: stream.read stopped at " + Integer.valueOf(i3) + " when expected " + bArr.length);
                    return null;
                }
                i3 += read2;
            }
            try {
                Object nextValue = new JSONTokener(new String(bArr)).nextValue();
                if (nextValue instanceof JSONObject) {
                    return (JSONObject) nextValue;
                }
                Logger.log(LoggingBehavior.CACHE, FileLruCache.TAG, "readHeader: expected JSONObject, got " + nextValue.getClass().getCanonicalName());
                return null;
            } catch (JSONException e) {
                throw new IOException(e.getMessage());
            }
        }

        static void writeHeader(OutputStream outputStream, JSONObject jSONObject) throws IOException {
            byte[] bytes = jSONObject.toString().getBytes();
            outputStream.write(0);
            outputStream.write((bytes.length >> 16) & MotionEventCompat.ACTION_MASK);
            outputStream.write((bytes.length >> 8) & MotionEventCompat.ACTION_MASK);
            outputStream.write((bytes.length >> 0) & MotionEventCompat.ACTION_MASK);
            outputStream.write(bytes);
        }
    }

    public FileLruCache(String str, Limits limits2) {
        this.tag = str;
        this.limits = limits2;
        this.directory = new File(FacebookSdk.getCacheDir(), str);
        this.lock = new Object();
        if (this.directory.mkdirs() || this.directory.isDirectory()) {
            BufferFile.deleteAll(this.directory);
        }
    }

    private void postTrim() {
        synchronized (this.lock) {
            if (!this.isTrimPending) {
                this.isTrimPending = true;
                FacebookSdk.getExecutor().execute(new Runnable() {
                    public void run() {
                        FileLruCache.this.trim();
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void renameToTargetAndTrim(String str, File file) {
        if (!file.renameTo(new File(this.directory, Utility.md5hash(str)))) {
            file.delete();
        }
        postTrim();
    }

    /* access modifiers changed from: private */
    public void trim() {
        synchronized (this.lock) {
            this.isTrimPending = false;
            this.isTrimInProgress = true;
        }
        try {
            Logger.log(LoggingBehavior.CACHE, TAG, "trim started");
            PriorityQueue priorityQueue = new PriorityQueue();
            long j = 0;
            long j2 = 0;
            File[] listFiles = this.directory.listFiles(BufferFile.excludeBufferFiles());
            if (listFiles != null) {
                for (File file : listFiles) {
                    ModifiedFile modifiedFile = new ModifiedFile(file);
                    priorityQueue.add(modifiedFile);
                    Logger.log(LoggingBehavior.CACHE, TAG, "  trim considering time=" + Long.valueOf(modifiedFile.getModified()) + " name=" + modifiedFile.getFile().getName());
                    j += file.length();
                    j2++;
                }
            }
            while (true) {
                if (j > ((long) this.limits.getByteCount()) || j2 > ((long) this.limits.getFileCount())) {
                    File file2 = ((ModifiedFile) priorityQueue.remove()).getFile();
                    Logger.log(LoggingBehavior.CACHE, TAG, "  trim removing " + file2.getName());
                    j -= file2.length();
                    j2--;
                    file2.delete();
                } else {
                    synchronized (this.lock) {
                        this.isTrimInProgress = false;
                        this.lock.notifyAll();
                    }
                    return;
                }
            }
        } catch (Throwable th) {
            synchronized (this.lock) {
                this.isTrimInProgress = false;
                this.lock.notifyAll();
                throw th;
            }
        }
    }

    public void clearCache() {
        final File[] listFiles = this.directory.listFiles(BufferFile.excludeBufferFiles());
        this.lastClearCacheTime.set(System.currentTimeMillis());
        if (listFiles != null) {
            FacebookSdk.getExecutor().execute(new Runnable() {
                public void run() {
                    for (File delete : listFiles) {
                        delete.delete();
                    }
                }
            });
        }
    }

    public InputStream get(String str) throws IOException {
        return get(str, (String) null);
    }

    public InputStream get(String str, String str2) throws IOException {
        File file = new File(this.directory, Utility.md5hash(str));
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 8192);
            try {
                JSONObject readHeader = StreamHeader.readHeader(bufferedInputStream);
                if (readHeader == null) {
                    FileInputStream fileInputStream2 = fileInputStream;
                    return null;
                }
                String optString = readHeader.optString(HEADER_CACHEKEY_KEY);
                if (optString == null || !optString.equals(str)) {
                    if (0 == 0) {
                        bufferedInputStream.close();
                    }
                    FileInputStream fileInputStream3 = fileInputStream;
                    return null;
                }
                String optString2 = readHeader.optString(HEADER_CACHE_CONTENT_TAG_KEY, (String) null);
                if ((str2 != null || optString2 == null) && (str2 == null || str2.equals(optString2))) {
                    long time = new Date().getTime();
                    Logger.log(LoggingBehavior.CACHE, TAG, "Setting lastModified to " + Long.valueOf(time) + " for " + file.getName());
                    file.setLastModified(time);
                    if (1 == 0) {
                        bufferedInputStream.close();
                    }
                    FileInputStream fileInputStream4 = fileInputStream;
                    return bufferedInputStream;
                }
                if (0 == 0) {
                    bufferedInputStream.close();
                }
                FileInputStream fileInputStream5 = fileInputStream;
                return null;
            } finally {
                if (0 == 0) {
                    bufferedInputStream.close();
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

    public String getLocation() {
        return this.directory.getPath();
    }

    public InputStream interceptAndPut(String str, InputStream inputStream) throws IOException {
        return new CopyingInputStream(inputStream, openPutStream(str));
    }

    public OutputStream openPutStream(String str) throws IOException {
        return openPutStream(str, (String) null);
    }

    public OutputStream openPutStream(String str, String str2) throws IOException {
        final File newFile = BufferFile.newFile(this.directory);
        newFile.delete();
        if (!newFile.createNewFile()) {
            throw new IOException("Could not create file at " + newFile.getAbsolutePath());
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            final long currentTimeMillis = System.currentTimeMillis();
            final String str3 = str;
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new CloseCallbackOutputStream(fileOutputStream, new StreamCloseCallback() {
                public void onClose() {
                    if (currentTimeMillis < FileLruCache.this.lastClearCacheTime.get()) {
                        newFile.delete();
                    } else {
                        FileLruCache.this.renameToTargetAndTrim(str3, newFile);
                    }
                }
            }), 8192);
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(HEADER_CACHEKEY_KEY, str);
                if (!Utility.isNullOrEmpty(str2)) {
                    jSONObject.put(HEADER_CACHE_CONTENT_TAG_KEY, str2);
                }
                StreamHeader.writeHeader(bufferedOutputStream, jSONObject);
                if (1 == 0) {
                    bufferedOutputStream.close();
                }
                return bufferedOutputStream;
            } catch (JSONException e) {
                Logger.log(LoggingBehavior.CACHE, 5, TAG, "Error creating JSON header for cache file: " + e);
                throw new IOException(e.getMessage());
            } catch (Throwable th) {
                if (0 == 0) {
                    bufferedOutputStream.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e2) {
            Logger.log(LoggingBehavior.CACHE, 5, TAG, "Error creating buffer output stream: " + e2);
            throw new IOException(e2.getMessage());
        }
    }

    /* access modifiers changed from: package-private */
    public long sizeInBytesForTest() {
        synchronized (this.lock) {
            while (true) {
                if (!this.isTrimPending && !this.isTrimInProgress) {
                    break;
                }
                try {
                    this.lock.wait();
                } catch (InterruptedException e) {
                }
            }
        }
        File[] listFiles = this.directory.listFiles();
        long j = 0;
        if (listFiles != null) {
            for (File length : listFiles) {
                j += length.length();
            }
        }
        return j;
    }

    public String toString() {
        return "{FileLruCache: tag:" + this.tag + " file:" + this.directory.getName() + "}";
    }
}
