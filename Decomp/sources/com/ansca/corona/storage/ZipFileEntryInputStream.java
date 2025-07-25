package com.ansca.corona.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ZipFileEntryInputStream extends InputStream {
    private InputStream fInputStream;
    private ZipEntry fZipEntry;
    private ZipFile fZipFile;

    public ZipFileEntryInputStream(File file, String str) throws NullPointerException, IllegalArgumentException, IllegalStateException, IOException, FileNotFoundException, ZipException {
        if (file == null) {
            throw new NullPointerException("file");
        } else if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        } else if (str == null) {
            throw new NullPointerException("entryName");
        } else if (str.length() <= 0) {
            throw new IllegalArgumentException("'entryName' cannot be empty.");
        } else {
            this.fZipFile = new ZipFile(file);
            this.fZipEntry = null;
            this.fInputStream = null;
            try {
                open(this.fZipFile, str);
            } finally {
                if (this.fInputStream == null && this.fZipFile != null) {
                    try {
                        this.fZipFile.close();
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    public ZipFileEntryInputStream(String str, String str2) throws NullPointerException, IllegalArgumentException, IllegalStateException, IOException, FileNotFoundException, ZipException {
        this(new File(str), str2);
    }

    public ZipFileEntryInputStream(ZipFile zipFile, String str) throws NullPointerException, IllegalArgumentException, IllegalStateException, IOException, FileNotFoundException, ZipException {
        this.fZipFile = null;
        this.fZipEntry = null;
        this.fInputStream = null;
        open(zipFile, str);
    }

    private void open(ZipFile zipFile, String str) throws NullPointerException, IllegalArgumentException, IllegalStateException, IOException, FileNotFoundException, ZipException {
        if (zipFile == null) {
            throw new NullPointerException("zipFile");
        } else if (str == null) {
            throw new NullPointerException("entryName");
        } else if (str.length() <= 0) {
            throw new IllegalArgumentException("'entryName' cannot be empty.");
        } else {
            this.fZipEntry = zipFile.getEntry(str);
            if (this.fZipEntry == null) {
                throw new ZipException("Failed to find zip file entry: " + str);
            }
            this.fInputStream = zipFile.getInputStream(this.fZipEntry);
        }
    }

    public static ZipFileEntryInputStream tryOpen(File file, String str) {
        if (file == null || !file.exists() || str == null || str.length() <= 0) {
            return null;
        }
        try {
            return new ZipFileEntryInputStream(file, str);
        } catch (Exception e) {
            return null;
        }
    }

    public static ZipFileEntryInputStream tryOpen(String str, String str2) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        try {
            return tryOpen(new File(str), str2);
        } catch (Exception e) {
            return null;
        }
    }

    public static ZipFileEntryInputStream tryOpen(ZipFile zipFile, String str) {
        if (zipFile == null || str == null || str.length() <= 0) {
            return null;
        }
        try {
            return new ZipFileEntryInputStream(zipFile, str);
        } catch (Exception e) {
            return null;
        }
    }

    public int available() throws IOException {
        return (int) this.fZipEntry.getSize();
    }

    public void close() throws IOException {
        this.fInputStream.close();
        if (this.fZipFile != null) {
            this.fZipFile.close();
        }
    }

    public void mark(int i) {
        this.fInputStream.mark(i);
    }

    public boolean markSupported() {
        return this.fInputStream.markSupported();
    }

    public int read() throws IOException {
        return this.fInputStream.read();
    }

    public int read(byte[] bArr) throws IOException, IndexOutOfBoundsException {
        return this.fInputStream.read(bArr);
    }

    public int read(byte[] bArr, int i, int i2) throws IOException, IndexOutOfBoundsException {
        return this.fInputStream.read(bArr, i, i2);
    }

    public void reset() throws IOException {
        this.fInputStream.reset();
    }

    public long skip(long j) throws IOException {
        return this.fInputStream.skip(j);
    }
}
