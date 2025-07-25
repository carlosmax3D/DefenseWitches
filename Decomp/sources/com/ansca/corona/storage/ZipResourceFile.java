package com.ansca.corona.storage;

import android.content.res.AssetFileDescriptor;
import android.os.ParcelFileDescriptor;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import com.google.android.gms.drive.DriveFile;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import jp.stargarage.g2metrics.BuildConfig;

public class ZipResourceFile {
    static final boolean LOGV = false;
    static final String LOG_TAG = "zipro";
    static final int kCDECRC = 16;
    static final int kCDECommentLen = 32;
    static final int kCDECompLen = 20;
    static final int kCDEExtraLen = 30;
    static final int kCDELen = 46;
    static final int kCDELocalOffset = 42;
    static final int kCDEMethod = 10;
    static final int kCDEModWhen = 12;
    static final int kCDENameLen = 28;
    static final int kCDESignature = 33639248;
    static final int kCDEUncompLen = 24;
    static final int kCompressDeflated = 8;
    static final int kCompressStored = 0;
    static final int kEOCDFileOffset = 16;
    static final int kEOCDLen = 22;
    static final int kEOCDNumEntries = 8;
    static final int kEOCDSignature = 101010256;
    static final int kEOCDSize = 12;
    static final int kLFHExtraLen = 28;
    static final int kLFHLen = 30;
    static final int kLFHNameLen = 26;
    static final int kLFHSignature = 67324752;
    static final int kMaxCommentLen = 65535;
    static final int kMaxEOCDSearch = 65557;
    static final int kZipEntryAdj = 10000;
    private HashMap<String, ZipEntryRO> mHashMap;
    ByteBuffer mLEByteBuffer;
    public HashMap<File, ZipFile> mZipFiles;

    public static final class ZipEntryRO {
        public long mCRC32;
        public long mCompressedLength;
        public final File mFile;
        public final String mFileName;
        public long mLocalHdrOffset;
        public int mMethod;
        public long mOffset = -1;
        public long mUncompressedLength;
        public long mWhenModified;
        public final String mZipFileName;

        public ZipEntryRO(String str, File file, String str2) {
            this.mFileName = str2;
            this.mZipFileName = str;
            this.mFile = file;
        }

        public AssetFileDescriptor getAssetFileDescriptor() {
            if (this.mMethod == 0) {
                try {
                    return new AssetFileDescriptor(ParcelFileDescriptor.open(this.mFile, DriveFile.MODE_READ_ONLY), getOffset(), this.mUncompressedLength);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        public long getOffset() {
            return this.mOffset;
        }

        public File getZipFile() {
            return this.mFile;
        }

        public String getZipFileName() {
            return this.mZipFileName;
        }

        public boolean isUncompressed() {
            return this.mMethod == 0;
        }

        public void setOffsetFromFile(RandomAccessFile randomAccessFile, ByteBuffer byteBuffer) throws IOException {
            long j = this.mLocalHdrOffset;
            try {
                randomAccessFile.seek(j);
                randomAccessFile.readFully(byteBuffer.array());
                if (byteBuffer.getInt(0) != ZipResourceFile.kLFHSignature) {
                    Log.w(ZipResourceFile.LOG_TAG, "didn't find signature at start of lfh");
                    throw new IOException();
                }
                this.mOffset = 30 + j + ((long) (byteBuffer.getShort(ZipResourceFile.kLFHNameLen) & 65535)) + ((long) (byteBuffer.getShort(28) & 65535));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public ZipResourceFile(File file) throws IOException {
        this(file.getAbsolutePath());
    }

    public ZipResourceFile(String str) throws IOException {
        this.mHashMap = new HashMap<>();
        this.mZipFiles = new HashMap<>();
        this.mLEByteBuffer = ByteBuffer.allocate(4);
        addPatchFile(str);
    }

    private static int read4LE(RandomAccessFile randomAccessFile) throws EOFException, IOException {
        return swapEndian(randomAccessFile.readInt());
    }

    private static int swapEndian(int i) {
        return ((i & MotionEventCompat.ACTION_MASK) << kCDEUncompLen) + ((65280 & i) << 8) + ((16711680 & i) >>> 8) + ((i >>> kCDEUncompLen) & MotionEventCompat.ACTION_MASK);
    }

    private static int swapEndian(short s) {
        return ((s & 255) << 8) | ((65280 & s) >>> 8);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r24v0, resolved type: short} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r24v1, resolved type: short} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r24v3, resolved type: short} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addPatchFile(java.lang.String r37) throws java.io.IOException {
        /*
            r36 = this;
            java.io.File r19 = new java.io.File
            r0 = r19
            r1 = r37
            r0.<init>(r1)
            java.io.RandomAccessFile r18 = new java.io.RandomAccessFile
            java.lang.String r4 = "r"
            r0 = r18
            r1 = r19
            r0.<init>(r1, r4)
            long r20 = r18.length()
            r4 = 22
            int r4 = (r20 > r4 ? 1 : (r20 == r4 ? 0 : -1))
            if (r4 >= 0) goto L_0x0024
            java.io.IOException r4 = new java.io.IOException
            r4.<init>()
            throw r4
        L_0x0024:
            r26 = 65557(0x10015, double:3.23895E-319)
            int r4 = (r26 > r20 ? 1 : (r26 == r20 ? 0 : -1))
            if (r4 <= 0) goto L_0x002d
            r26 = r20
        L_0x002d:
            r4 = 0
            r0 = r18
            r0.seek(r4)
            int r23 = read4LE(r18)
            r4 = 101010256(0x6054b50, float:2.506985E-35)
            r0 = r23
            if (r0 != r4) goto L_0x004c
            java.lang.String r4 = "zipro"
            java.lang.String r5 = "Found Zip archive, but it looks empty"
            android.util.Log.i(r4, r5)
            java.io.IOException r4 = new java.io.IOException
            r4.<init>()
            throw r4
        L_0x004c:
            r4 = 67324752(0x4034b50, float:1.5433558E-36)
            r0 = r23
            if (r0 == r4) goto L_0x0060
            java.lang.String r4 = "zipro"
            java.lang.String r5 = "Not a Zip archive"
            android.util.Log.v(r4, r5)
            java.io.IOException r4 = new java.io.IOException
            r4.<init>()
            throw r4
        L_0x0060:
            long r28 = r20 - r26
            r0 = r18
            r1 = r28
            r0.seek(r1)
            r0 = r26
            int r4 = (int) r0
            java.nio.ByteBuffer r10 = java.nio.ByteBuffer.allocate(r4)
            byte[] r12 = r10.array()
            r0 = r18
            r0.readFully(r12)
            java.nio.ByteOrder r4 = java.nio.ByteOrder.LITTLE_ENDIAN
            r10.order(r4)
            int r4 = r12.length
            int r16 = r4 + -22
        L_0x0081:
            if (r16 < 0) goto L_0x0094
            byte r4 = r12[r16]
            r5 = 80
            if (r4 != r5) goto L_0x0124
            r0 = r16
            int r4 = r10.getInt(r0)
            r5 = 101010256(0x6054b50, float:2.506985E-35)
            if (r4 != r5) goto L_0x0124
        L_0x0094:
            if (r16 >= 0) goto L_0x00ba
            java.lang.String r4 = "zipro"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r33 = "Zip: EOCD not found, "
            r0 = r33
            java.lang.StringBuilder r5 = r5.append(r0)
            r0 = r37
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r33 = " is not zip"
            r0 = r33
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r4, r5)
        L_0x00ba:
            int r4 = r16 + 8
            short r25 = r10.getShort(r4)
            int r4 = r16 + 12
            int r4 = r10.getInt(r4)
            long r4 = (long) r4
            r34 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r8 = r4 & r34
            int r4 = r16 + 16
            int r4 = r10.getInt(r4)
            long r4 = (long) r4
            r34 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r6 = r4 & r34
            long r4 = r6 + r8
            int r4 = (r4 > r20 ? 1 : (r4 == r20 ? 0 : -1))
            if (r4 <= 0) goto L_0x0128
            java.lang.String r4 = "zipro"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r33 = "bad offsets (dir "
            r0 = r33
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r33 = ", size "
            r0 = r33
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r33 = ", eocd "
            r0 = r33
            java.lang.StringBuilder r5 = r5.append(r0)
            r0 = r16
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r33 = ")"
            r0 = r33
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            android.util.Log.w(r4, r5)
            java.io.IOException r4 = new java.io.IOException
            r4.<init>()
            throw r4
        L_0x0124:
            int r16 = r16 + -1
            goto L_0x0081
        L_0x0128:
            if (r25 != 0) goto L_0x0137
            java.lang.String r4 = "zipro"
            java.lang.String r5 = "empty archive?"
            android.util.Log.w(r4, r5)
            java.io.IOException r4 = new java.io.IOException
            r4.<init>()
            throw r4
        L_0x0137:
            java.nio.channels.FileChannel r4 = r18.getChannel()
            java.nio.channels.FileChannel$MapMode r5 = java.nio.channels.FileChannel.MapMode.READ_ONLY
            java.nio.MappedByteBuffer r15 = r4.map(r5, r6, r8)
            java.nio.ByteOrder r4 = java.nio.ByteOrder.LITTLE_ENDIAN
            r15.order(r4)
            r4 = 65535(0xffff, float:9.1834E-41)
            byte[] r0 = new byte[r4]
            r31 = r0
            r14 = 0
            r4 = 30
            java.nio.ByteBuffer r11 = java.nio.ByteBuffer.allocate(r4)
            java.nio.ByteOrder r4 = java.nio.ByteOrder.LITTLE_ENDIAN
            r11.order(r4)
            r24 = 0
        L_0x015b:
            r0 = r24
            r1 = r25
            if (r0 >= r1) goto L_0x0261
            int r4 = r15.getInt(r14)
            r5 = 33639248(0x2014b50, float:9.499037E-38)
            if (r4 == r5) goto L_0x0192
            java.lang.String r4 = "zipro"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r33 = "Missed a central dir sig (at "
            r0 = r33
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.StringBuilder r5 = r5.append(r14)
            java.lang.String r33 = ")"
            r0 = r33
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            android.util.Log.w(r4, r5)
            java.io.IOException r4 = new java.io.IOException
            r4.<init>()
            throw r4
        L_0x0192:
            int r4 = r14 + 28
            short r4 = r15.getShort(r4)
            r5 = 65535(0xffff, float:9.1834E-41)
            r22 = r4 & r5
            int r4 = r14 + 30
            short r4 = r15.getShort(r4)
            r5 = 65535(0xffff, float:9.1834E-41)
            r17 = r4 & r5
            int r4 = r14 + 32
            short r4 = r15.getShort(r4)
            r5 = 65535(0xffff, float:9.1834E-41)
            r13 = r4 & r5
            int r4 = r14 + 46
            r15.position(r4)
            r4 = 0
            r0 = r31
            r1 = r22
            r15.get(r0, r4, r1)
            r4 = 0
            r15.position(r4)
            java.lang.String r30 = new java.lang.String
            r4 = 0
            r0 = r30
            r1 = r31
            r2 = r22
            r0.<init>(r1, r4, r2)
            com.ansca.corona.storage.ZipResourceFile$ZipEntryRO r32 = new com.ansca.corona.storage.ZipResourceFile$ZipEntryRO
            r0 = r32
            r1 = r37
            r2 = r19
            r3 = r30
            r0.<init>(r1, r2, r3)
            int r4 = r14 + 10
            short r4 = r15.getShort(r4)
            r5 = 65535(0xffff, float:9.1834E-41)
            r4 = r4 & r5
            r0 = r32
            r0.mMethod = r4
            int r4 = r14 + 12
            int r4 = r15.getInt(r4)
            long r4 = (long) r4
            r34 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r4 = r4 & r34
            r0 = r32
            r0.mWhenModified = r4
            int r4 = r14 + 16
            long r4 = r15.getLong(r4)
            r34 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r4 = r4 & r34
            r0 = r32
            r0.mCRC32 = r4
            int r4 = r14 + 20
            long r4 = r15.getLong(r4)
            r34 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r4 = r4 & r34
            r0 = r32
            r0.mCompressedLength = r4
            int r4 = r14 + 24
            long r4 = r15.getLong(r4)
            r34 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r4 = r4 & r34
            r0 = r32
            r0.mUncompressedLength = r4
            int r4 = r14 + 42
            int r4 = r15.getInt(r4)
            long r4 = (long) r4
            r34 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r4 = r4 & r34
            r0 = r32
            r0.mLocalHdrOffset = r4
            r11.clear()
            r0 = r32
            r1 = r18
            r0.setOffsetFromFile(r1, r11)
            r0 = r36
            java.util.HashMap<java.lang.String, com.ansca.corona.storage.ZipResourceFile$ZipEntryRO> r4 = r0.mHashMap
            r0 = r30
            r1 = r32
            r4.put(r0, r1)
            int r4 = r22 + 46
            int r4 = r4 + r17
            int r4 = r4 + r13
            int r14 = r14 + r4
            int r24 = r24 + 1
            goto L_0x015b
        L_0x0261:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.storage.ZipResourceFile.addPatchFile(java.lang.String):void");
    }

    public void close() {
        for (ZipFile next : this.mZipFiles.values()) {
            if (next != null) {
                try {
                    next.close();
                } catch (Exception e) {
                }
            }
        }
        this.mZipFiles.clear();
    }

    public ZipEntryRO[] getAllEntries() {
        Collection<ZipEntryRO> values = this.mHashMap.values();
        return (ZipEntryRO[]) values.toArray(new ZipEntryRO[values.size()]);
    }

    public AssetFileDescriptor getAssetFileDescriptor(String str) {
        ZipEntryRO zipEntryRO = this.mHashMap.get(str);
        if (zipEntryRO != null) {
            return zipEntryRO.getAssetFileDescriptor();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public ZipEntryRO[] getEntriesAt(String str) {
        Vector vector = new Vector();
        Collection<ZipEntryRO> values = this.mHashMap.values();
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        int length = str.length();
        for (ZipEntryRO next : values) {
            if (next.mFileName.startsWith(str) && -1 == next.mFileName.indexOf(47, length)) {
                vector.add(next);
            }
        }
        return (ZipEntryRO[]) vector.toArray(new ZipEntryRO[vector.size()]);
    }

    public ZipEntryRO getEntry(String str) {
        return this.mHashMap.get(str);
    }

    public InputStream getInputStream(String str) throws IOException {
        ZipEntryRO zipEntryRO = this.mHashMap.get(str);
        if (zipEntryRO != null) {
            if (zipEntryRO.isUncompressed()) {
                return zipEntryRO.getAssetFileDescriptor().createInputStream();
            }
            ZipFile zipFile = this.mZipFiles.get(zipEntryRO.getZipFile());
            if (zipFile == null) {
                zipFile = new ZipFile(zipEntryRO.getZipFile(), 1);
                this.mZipFiles.put(zipEntryRO.getZipFile(), zipFile);
            }
            ZipEntry entry = zipFile.getEntry(str);
            if (entry != null) {
                return zipFile.getInputStream(entry);
            }
        }
        return null;
    }
}
