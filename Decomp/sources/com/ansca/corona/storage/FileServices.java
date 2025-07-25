package com.ansca.corona.storage;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.webkit.MimeTypeMap;
import com.ansca.corona.ApplicationContextProvider;
import com.ansca.corona.storage.AssetFileLocationInfo;
import com.ansca.corona.storage.ZipResourceFile;
import com.google.android.gms.drive.DriveFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import jp.stargarage.g2metrics.BuildConfig;

public class FileServices extends ApplicationContextProvider {
    private static ZipResourceFile sApkZipEntryReader = null;
    private static boolean sHasAccessedExpansionFileDirectory = false;
    private static File sMainExpansionFile = null;
    private static ZipResourceFile sMainExpansionZipReader = null;
    private static File sPatchExpansionFile = null;
    private static ZipResourceFile sPatchExpansionZipReader = null;

    public FileServices(Context context) {
        super(context);
        if (sApkZipEntryReader == null) {
            synchronized (FileServices.class) {
                if (sApkZipEntryReader == null) {
                    try {
                        sApkZipEntryReader = new ZipResourceFile(context.getApplicationInfo().sourceDir);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    private String createRawResourceNameForAsset(File file) {
        if (file == null) {
            return null;
        }
        return createRawResourceNameForAsset(file.getPath());
    }

    private String createRawResourceNameForAsset(String str) {
        if (str == null) {
            return null;
        }
        String trim = str.trim();
        if (trim.length() <= 0) {
            return null;
        }
        String lowerCase = trim.toLowerCase();
        int lastIndexOf = lowerCase.lastIndexOf(46);
        if (lastIndexOf <= 0) {
            return null;
        }
        return "corona_asset_" + lowerCase.substring(0, lastIndexOf).replaceAll("[^[a-z][0-9]]", "_");
    }

    private File getExpansionFileOfType(String str) {
        try {
            File expansionFileDirectory = getExpansionFileDirectory();
            if (expansionFileDirectory == null) {
                return expansionFileDirectory;
            }
            Context applicationContext = getApplicationContext();
            int i = applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0).versionCode;
            return new File(expansionFileDirectory, str + "." + Integer.toString(i) + "." + applicationContext.getPackageName() + ".obb");
        } catch (Exception e) {
            return null;
        }
    }

    private InputStream openZipFileEntry(File file, String str) {
        if (file == null || str == null || str.length() <= 0) {
            return null;
        }
        if (!sHasAccessedExpansionFileDirectory) {
            loadExpansionFiles();
        }
        if (file.equals(getPatchExpansionFile())) {
            if (sPatchExpansionZipReader == null) {
                return null;
            }
            try {
                return sPatchExpansionZipReader.getInputStream(str);
            } catch (Exception e) {
                return null;
            }
        } else if (!file.equals(getMainExpansionFile())) {
            return ZipFileEntryInputStream.tryOpen(file, str);
        } else {
            if (sMainExpansionZipReader == null) {
                return null;
            }
            try {
                return sMainExpansionZipReader.getInputStream(str);
            } catch (Exception e2) {
                return null;
            }
        }
    }

    private InputStream openZipFileEntry(String str, String str2) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        try {
            return openZipFileEntry(new File(str), str2);
        } catch (Exception e) {
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x0064 A[SYNTHETIC, Splitter:B:36:0x0064] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0069 A[SYNTHETIC, Splitter:B:39:0x0069] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0075 A[SYNTHETIC, Splitter:B:45:0x0075] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x007a A[SYNTHETIC, Splitter:B:48:0x007a] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x007f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean copyFile(java.io.File r12, java.io.File r13) {
        /*
            r11 = this;
            r9 = 0
            r6 = 0
            r7 = 0
            r5 = 0
            if (r12 == 0) goto L_0x0008
            if (r13 != 0) goto L_0x0009
        L_0x0008:
            return r9
        L_0x0009:
            java.lang.String r10 = r12.getPath()
            boolean r10 = r11.isAssetFile(r10)
            if (r10 != 0) goto L_0x0019
            boolean r10 = r12.exists()
            if (r10 == 0) goto L_0x0008
        L_0x0019:
            java.io.InputStream r6 = r11.openFile((java.io.File) r12)     // Catch:{ Exception -> 0x005e }
            if (r6 == 0) goto L_0x004d
            java.io.File r9 = r13.getParentFile()     // Catch:{ Exception -> 0x005e }
            r9.mkdirs()     // Catch:{ Exception -> 0x005e }
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x005e }
            r8.<init>(r13)     // Catch:{ Exception -> 0x005e }
            if (r8 == 0) goto L_0x0095
            int r2 = r6.available()     // Catch:{ Exception -> 0x0092, all -> 0x008f }
            if (r2 <= 0) goto L_0x004b
            r0 = 1024(0x400, float:1.435E-42)
            r9 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r9]     // Catch:{ Exception -> 0x0092, all -> 0x008f }
        L_0x0039:
            if (r2 <= 0) goto L_0x004b
            r3 = 1024(0x400, float:1.435E-42)
            if (r3 <= r2) goto L_0x0040
            r3 = r2
        L_0x0040:
            r9 = 0
            int r3 = r6.read(r1, r9, r3)     // Catch:{ Exception -> 0x0092, all -> 0x008f }
            r9 = 0
            r8.write(r1, r9, r3)     // Catch:{ Exception -> 0x0092, all -> 0x008f }
            int r2 = r2 - r3
            goto L_0x0039
        L_0x004b:
            r5 = 1
            r7 = r8
        L_0x004d:
            if (r6 == 0) goto L_0x0052
            r6.close()     // Catch:{ Exception -> 0x0083 }
        L_0x0052:
            if (r7 == 0) goto L_0x0057
            r7.close()     // Catch:{ Exception -> 0x0085 }
        L_0x0057:
            if (r5 != 0) goto L_0x005c
            r13.delete()
        L_0x005c:
            r9 = r5
            goto L_0x0008
        L_0x005e:
            r4 = move-exception
        L_0x005f:
            r4.printStackTrace()     // Catch:{ all -> 0x0072 }
            if (r6 == 0) goto L_0x0067
            r6.close()     // Catch:{ Exception -> 0x0087 }
        L_0x0067:
            if (r7 == 0) goto L_0x006c
            r7.close()     // Catch:{ Exception -> 0x0089 }
        L_0x006c:
            if (r5 != 0) goto L_0x005c
            r13.delete()
            goto L_0x005c
        L_0x0072:
            r9 = move-exception
        L_0x0073:
            if (r6 == 0) goto L_0x0078
            r6.close()     // Catch:{ Exception -> 0x008b }
        L_0x0078:
            if (r7 == 0) goto L_0x007d
            r7.close()     // Catch:{ Exception -> 0x008d }
        L_0x007d:
            if (r5 != 0) goto L_0x0082
            r13.delete()
        L_0x0082:
            throw r9
        L_0x0083:
            r9 = move-exception
            goto L_0x0052
        L_0x0085:
            r9 = move-exception
            goto L_0x0057
        L_0x0087:
            r9 = move-exception
            goto L_0x0067
        L_0x0089:
            r9 = move-exception
            goto L_0x006c
        L_0x008b:
            r10 = move-exception
            goto L_0x0078
        L_0x008d:
            r10 = move-exception
            goto L_0x007d
        L_0x008f:
            r9 = move-exception
            r7 = r8
            goto L_0x0073
        L_0x0092:
            r4 = move-exception
            r7 = r8
            goto L_0x005f
        L_0x0095:
            r7 = r8
            goto L_0x004d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.storage.FileServices.copyFile(java.io.File, java.io.File):boolean");
    }

    public boolean copyFile(String str, String str2) {
        boolean z = false;
        if (str == null || str.length() <= 0 || str2 == null || str2.length() <= 0) {
            return false;
        }
        try {
            z = copyFile(new File(str), new File(str2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }

    public boolean doesAssetFileExist(String str) {
        return getAssetFileLocation(str) != null;
    }

    public File extractAssetFile(File file) {
        return extractAssetFile(file, false);
    }

    public File extractAssetFile(File file, boolean z) {
        if (file == null) {
            return null;
        }
        if (!isAssetFile(file.getPath())) {
            return file;
        }
        Context applicationContext = getApplicationContext();
        if (applicationContext == null) {
            return null;
        }
        File file2 = new File(applicationContext.getFileStreamPath("coronaResources"), file.getPath());
        if ((z || !file2.exists()) && !copyFile(file, file2)) {
            return null;
        }
        return file2;
    }

    public File extractAssetFile(String str) {
        return extractAssetFile(str, false);
    }

    public File extractAssetFile(String str, boolean z) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        try {
            return extractAssetFile(new File(str), z);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public AssetFileLocationInfo getAssetFileLocation(String str) {
        ZipResourceFile.ZipEntryRO zipEntryRO = null;
        String str2 = null;
        if (!isAssetFile(str)) {
            return null;
        }
        if (!sHasAccessedExpansionFileDirectory) {
            loadExpansionFiles();
        }
        if (sPatchExpansionZipReader != null) {
            str2 = str;
            zipEntryRO = sPatchExpansionZipReader.getEntry(str2);
        }
        if (zipEntryRO == null && sMainExpansionZipReader != null) {
            str2 = str;
            zipEntryRO = sMainExpansionZipReader.getEntry(str2);
        }
        if (zipEntryRO == null && sApkZipEntryReader != null) {
            str2 = "assets/" + str;
            zipEntryRO = sApkZipEntryReader.getEntry(str2);
        }
        if (zipEntryRO == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("res/raw/");
            sb.append(createRawResourceNameForAsset(str));
            String extensionFrom = getExtensionFrom(str);
            if (extensionFrom != null && extensionFrom.length() > 0) {
                sb.append(".");
                sb.append(extensionFrom.toLowerCase());
            }
            str2 = sb.toString();
            zipEntryRO = sApkZipEntryReader.getEntry(str2);
        }
        if (zipEntryRO == null) {
            return null;
        }
        try {
            AssetFileLocationInfo.Settings settings = new AssetFileLocationInfo.Settings();
            settings.setPackageFile(zipEntryRO.mFile);
            settings.setAssetFilePath(str);
            settings.setZipEntryName(str2);
            settings.setByteOffsetInPackage(zipEntryRO.mOffset);
            settings.setByteCountInPackage(zipEntryRO.mCompressedLength);
            settings.setIsCompressed(!zipEntryRO.isUncompressed());
            return new AssetFileLocationInfo(settings);
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] getBytesFromFile(File file) {
        if (file == null) {
            return null;
        }
        return getBytesFromFile(file.getPath());
    }

    public byte[] getBytesFromFile(String str) {
        int available;
        int i;
        InputStream inputStream = null;
        byte[] bArr = null;
        if (str == null || str.length() <= 0) {
            return null;
        }
        try {
            InputStream openFile = openFile(str);
            if (openFile != null && (available = openFile.available()) > 0) {
                bArr = new byte[available];
                for (int i2 = 0; i2 < available; i2 += openFile.read(bArr, i2, i)) {
                    i = available - i2;
                    if (i > 1024) {
                        i = 1024;
                    }
                }
            }
            if (openFile != null) {
                try {
                    openFile.close();
                } catch (Exception e) {
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e3) {
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e4) {
                }
            }
            throw th;
        }
        return bArr;
    }

    public File getExpansionFileDirectory() {
        try {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            return externalStorageDirectory != null ? new File(externalStorageDirectory, "Android/obb/" + getApplicationContext().getPackageName()) : externalStorageDirectory;
        } catch (Exception e) {
            return null;
        }
    }

    public String getExtensionFrom(File file) {
        if (file == null) {
            return null;
        }
        return getExtensionFrom(file.getPath());
    }

    public String getExtensionFrom(String str) {
        if (str == null) {
            return null;
        }
        String trim = str.trim();
        if (trim.length() <= 0) {
            return null;
        }
        int lastIndexOf = trim.lastIndexOf(46);
        return (lastIndexOf < 0 || lastIndexOf + 1 >= trim.length()) ? BuildConfig.FLAVOR : trim.substring(lastIndexOf + 1);
    }

    public File getMainExpansionFile() {
        File file = sMainExpansionFile;
        if (file != null) {
            return file;
        }
        File expansionFileOfType = getExpansionFileOfType("main");
        sMainExpansionFile = expansionFileOfType;
        return expansionFileOfType;
    }

    public String getMimeTypeFrom(Uri uri) {
        MimeTypeMap singleton;
        if (uri == null || (singleton = MimeTypeMap.getSingleton()) == null) {
            return null;
        }
        return singleton.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(uri.toString()));
    }

    public File getPatchExpansionFile() {
        File file = sPatchExpansionFile;
        if (file != null) {
            return file;
        }
        File expansionFileOfType = getExpansionFileOfType("patch");
        sPatchExpansionFile = expansionFileOfType;
        return expansionFileOfType;
    }

    public boolean isAssetFile(String str) {
        return str != null && str.length() > 0 && !str.startsWith(File.separator);
    }

    public void loadExpansionFiles() {
        File expansionFileDirectory;
        if (sPatchExpansionZipReader != null) {
            sPatchExpansionZipReader.close();
            sPatchExpansionZipReader = null;
        }
        if (sMainExpansionZipReader != null) {
            sMainExpansionZipReader.close();
            sMainExpansionZipReader = null;
        }
        sHasAccessedExpansionFileDirectory = false;
        if (Environment.getExternalStorageState().equals("mounted") && (expansionFileDirectory = getExpansionFileDirectory()) != null && expansionFileDirectory.exists()) {
            try {
                sPatchExpansionZipReader = new ZipResourceFile(getPatchExpansionFile());
            } catch (Exception e) {
            }
            try {
                sMainExpansionZipReader = new ZipResourceFile(getMainExpansionFile());
            } catch (Exception e2) {
            }
        }
        sHasAccessedExpansionFileDirectory = true;
    }

    public boolean moveFile(File file, File file2) {
        boolean z = false;
        if (file == null || file2 == null || !file.exists()) {
            return false;
        }
        try {
            z = file.renameTo(file2);
            if (!z) {
                z = copyFile(file, file2);
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }

    public AssetFileDescriptor openAssetFileDescriptorFor(File file) {
        if (file == null) {
            return null;
        }
        return openAssetFileDescriptorFor(file.getPath());
    }

    public AssetFileDescriptor openAssetFileDescriptorFor(String str) {
        AssetFileDescriptor assetFileDescriptor;
        if (str == null || str.length() <= 0) {
            return null;
        }
        if (isAssetFile(str)) {
            if (!sHasAccessedExpansionFileDirectory) {
                loadExpansionFiles();
            }
            if (sPatchExpansionZipReader != null) {
                try {
                    assetFileDescriptor = sPatchExpansionZipReader.getAssetFileDescriptor(str);
                } catch (Exception e) {
                    assetFileDescriptor = null;
                }
            } else {
                assetFileDescriptor = null;
            }
            if (assetFileDescriptor != null) {
                return assetFileDescriptor;
            }
            if (sMainExpansionZipReader != null) {
                try {
                    assetFileDescriptor = sMainExpansionZipReader.getAssetFileDescriptor(str);
                } catch (Exception e2) {
                }
            }
            if (assetFileDescriptor != null) {
                return assetFileDescriptor;
            }
            try {
                assetFileDescriptor = getApplicationContext().getAssets().openFd(str);
            } catch (Exception e3) {
            }
            if (assetFileDescriptor != null) {
                return assetFileDescriptor;
            }
            try {
                String createRawResourceNameForAsset = createRawResourceNameForAsset(str);
                ResourceServices resourceServices = new ResourceServices(getApplicationContext());
                int rawResourceId = resourceServices.getRawResourceId(createRawResourceNameForAsset);
                return rawResourceId != 0 ? resourceServices.getResources().openRawResourceFd(rawResourceId) : assetFileDescriptor;
            } catch (Exception e4) {
                return assetFileDescriptor;
            }
        } else {
            try {
                return new AssetFileDescriptor(ParcelFileDescriptor.open(new File(str), DriveFile.MODE_READ_ONLY), 0, -1);
            } catch (Exception e5) {
                return null;
            }
        }
    }

    public InputStream openFile(File file) {
        if (file == null) {
            return null;
        }
        return openFile(file.getPath());
    }

    public InputStream openFile(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        if (isAssetFile(str)) {
            InputStream openZipFileEntry = openZipFileEntry(getPatchExpansionFile(), str);
            if (openZipFileEntry != null) {
                return openZipFileEntry;
            }
            InputStream openZipFileEntry2 = openZipFileEntry(getMainExpansionFile(), str);
            if (openZipFileEntry2 != null) {
                return openZipFileEntry2;
            }
            try {
                openZipFileEntry2 = getApplicationContext().getAssets().open(str, 3);
            } catch (Exception e) {
            }
            if (openZipFileEntry2 != null) {
                return openZipFileEntry2;
            }
            try {
                String createRawResourceNameForAsset = createRawResourceNameForAsset(str);
                ResourceServices resourceServices = new ResourceServices(getApplicationContext());
                int rawResourceId = resourceServices.getRawResourceId(createRawResourceNameForAsset);
                return rawResourceId != 0 ? resourceServices.getResources().openRawResource(rawResourceId) : openZipFileEntry2;
            } catch (Exception e2) {
                return openZipFileEntry2;
            }
        } else {
            try {
                return new FileInputStream(str);
            } catch (Exception e3) {
                return null;
            }
        }
    }

    public void setMainExpansionFileName(String str) {
        File file = null;
        if (!(str == null || str.length() <= 0 || (file = getExpansionFileDirectory()) == null)) {
            file = new File(file, str);
        }
        sMainExpansionFile = file;
        sHasAccessedExpansionFileDirectory = false;
    }

    public void setPatchExpansionFileName(String str) {
        File file = null;
        if (!(str == null || str.length() <= 0 || (file = getExpansionFileDirectory()) == null)) {
            file = new File(file, str);
        }
        sPatchExpansionFile = file;
        sHasAccessedExpansionFileDirectory = false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0031 A[SYNTHETIC, Splitter:B:18:0x0031] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0046 A[SYNTHETIC, Splitter:B:31:0x0046] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean writeToFile(java.io.InputStream r10, java.io.File r11) {
        /*
            r9 = this;
            r7 = 0
            r5 = 0
            r4 = 0
            if (r10 == 0) goto L_0x0007
            if (r11 != 0) goto L_0x0008
        L_0x0007:
            return r7
        L_0x0008:
            java.io.File r7 = r11.getParentFile()
            r7.mkdirs()
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0051 }
            r6.<init>(r11)     // Catch:{ Exception -> 0x0051 }
            if (r6 == 0) goto L_0x0039
            r0 = 1024(0x400, float:1.435E-42)
            r7 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r7]     // Catch:{ Exception -> 0x002a, all -> 0x004e }
        L_0x001c:
            r2 = 1024(0x400, float:1.435E-42)
            r7 = 0
            int r2 = r10.read(r1, r7, r2)     // Catch:{ Exception -> 0x002a, all -> 0x004e }
            if (r2 <= 0) goto L_0x0036
            r7 = 0
            r6.write(r1, r7, r2)     // Catch:{ Exception -> 0x002a, all -> 0x004e }
            goto L_0x001c
        L_0x002a:
            r3 = move-exception
            r5 = r6
        L_0x002c:
            r3.printStackTrace()     // Catch:{ all -> 0x0043 }
            if (r5 == 0) goto L_0x0034
            r5.close()     // Catch:{ Exception -> 0x004a }
        L_0x0034:
            r7 = r4
            goto L_0x0007
        L_0x0036:
            if (r2 >= 0) goto L_0x001c
            r4 = 1
        L_0x0039:
            if (r6 == 0) goto L_0x0053
            r6.close()     // Catch:{ Exception -> 0x0040 }
            r5 = r6
            goto L_0x0034
        L_0x0040:
            r7 = move-exception
            r5 = r6
            goto L_0x0034
        L_0x0043:
            r7 = move-exception
        L_0x0044:
            if (r5 == 0) goto L_0x0049
            r5.close()     // Catch:{ Exception -> 0x004c }
        L_0x0049:
            throw r7
        L_0x004a:
            r7 = move-exception
            goto L_0x0034
        L_0x004c:
            r8 = move-exception
            goto L_0x0049
        L_0x004e:
            r7 = move-exception
            r5 = r6
            goto L_0x0044
        L_0x0051:
            r3 = move-exception
            goto L_0x002c
        L_0x0053:
            r5 = r6
            goto L_0x0034
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.storage.FileServices.writeToFile(java.io.InputStream, java.io.File):boolean");
    }
}
