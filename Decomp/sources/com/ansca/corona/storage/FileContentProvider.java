package com.ansca.corona.storage;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.google.android.gms.drive.DriveFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;

public class FileContentProvider extends ContentProvider {
    public static Uri createContentUriForFile(Context context, File file) {
        if (file != null) {
            return createContentUriForFile(context, file.getPath());
        }
        throw new IllegalArgumentException();
    }

    public static Uri createContentUriForFile(Context context, String str) {
        if (context == null || str == null || str.length() <= 0) {
            throw new IllegalArgumentException();
        }
        boolean z = true;
        String str2 = str;
        String str3 = "/data/data/" + context.getPackageName();
        int indexOf = str.indexOf(str3);
        if (indexOf < 0 || str3.length() + indexOf >= str.length()) {
            int indexOf2 = str.indexOf("file:///android_asset");
            if (indexOf2 >= 0 && "file:///android_asset".length() + indexOf2 < str.length()) {
                str2 = str.substring("file:///android_asset".length() + indexOf2);
            }
        } else {
            str2 = str.substring(str3.length() + indexOf);
            z = false;
        }
        if (!str2.startsWith(File.separator)) {
            str2 = File.separator + str2;
        }
        if (z) {
            str2 = File.separator + "assets" + str2;
        }
        return Uri.parse(Uri.encode("content://" + context.getPackageName() + ".files" + str2, ":/\\."));
    }

    public static void validateManifest(Context context) {
        int i = 0;
        PackageInfo packageInfo = null;
        ProviderInfo providerInfo = null;
        if (context == null) {
            throw new NullPointerException();
        }
        String name = FileContentProvider.class.getName();
        String name2 = com.ansca.corona.FileContentProvider.class.getName();
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 8);
        } catch (Exception e) {
        }
        if (!(packageInfo == null || packageInfo.providers == null)) {
            for (ProviderInfo providerInfo2 : packageInfo.providers) {
                if (name2.equals(providerInfo2.name)) {
                    String str = "Provider \"" + name2 + "\" in the AndroidManifest.xml file has been deprecated. Please change its name to \"" + name + "\".";
                    Log.v("Corona", str);
                    throw new RuntimeException(str);
                }
            }
        }
        if (packageInfo != null && packageInfo.providers != null) {
            ProviderInfo[] providerInfoArr = packageInfo.providers;
            int length = providerInfoArr.length;
            while (true) {
                if (i >= length) {
                    break;
                }
                ProviderInfo providerInfo3 = providerInfoArr[i];
                if (name.equals(providerInfo3.name)) {
                    providerInfo = providerInfo3;
                    break;
                }
                i++;
            }
        }
        if (providerInfo == null) {
            String str2 = "Provider \"" + name + "\" not found in the AndroidManifest.xml file.";
            Log.v("Corona", str2);
            throw new RuntimeException(str2);
        }
        String str3 = context.getPackageName() + ".files";
        if (!str3.equals(providerInfo.authority)) {
            String str4 = "The AndroidManifest.xml provider \"" + name + "\" is misconfigured. Please change its 'authorities' attribute value to \"" + str3 + "\".";
            Log.v("Corona", str4);
            throw new RuntimeException(str4);
        }
    }

    public int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }

    public String getType(Uri uri) {
        return new FileServices(getContext()).getMimeTypeFrom(uri);
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }

    public boolean onCreate() {
        return true;
    }

    public AssetFileDescriptor openAssetFile(Uri uri, String str) throws FileNotFoundException {
        if (uri == null) {
            throw new IllegalArgumentException();
        }
        try {
            Uri parse = Uri.parse(URI.create(uri.toString()).normalize().toString());
            if (parse != null) {
                uri = parse;
            }
        } catch (Exception e) {
        }
        boolean z = true;
        try {
            String path = uri.getPath();
            int indexOf = path.indexOf("files/coronaResources/");
            if (indexOf < 0 || "files/coronaResources/".length() + indexOf >= path.length()) {
                int indexOf2 = path.indexOf("android_asset/");
                if (indexOf2 >= 0 && "android_asset/".length() + indexOf2 < path.length()) {
                    path = path.substring("android_asset/".length() + indexOf2);
                } else if (path.startsWith("/assets/")) {
                    path = path.substring("/assets/".length() + indexOf2);
                } else {
                    z = false;
                }
            } else {
                path = path.substring("files/coronaResources/".length() + indexOf);
            }
            if (!z) {
                return new AssetFileDescriptor(openFile(uri, str), 0, -1);
            }
            if (path.startsWith(File.separator)) {
                path = path.substring(1);
            }
            return new FileServices(getContext()).openAssetFileDescriptorFor(path);
        } catch (Exception e2) {
            throw new FileNotFoundException();
        }
    }

    public ParcelFileDescriptor openFile(Uri uri, String str) throws FileNotFoundException {
        if (uri == null) {
            throw new IllegalArgumentException();
        }
        File file = new File("/data/data/" + getContext().getPackageName() + uri.getPath());
        if (file != null) {
            return ParcelFileDescriptor.open(file, DriveFile.MODE_READ_ONLY);
        }
        throw new FileNotFoundException();
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        if (uri == null) {
            return null;
        }
        try {
            Uri parse = Uri.parse(URI.create(uri.toString()).normalize().toString());
            if (parse != null) {
                uri = parse;
            }
        } catch (Exception e) {
        }
        String[] strArr3 = (strArr == null || strArr.length <= 0) ? new String[]{"_id", "_display_name", "_size", "mime_type"} : strArr;
        Object[] objArr = new Object[strArr3.length];
        for (int i = 0; i < strArr3.length; i++) {
            String str3 = strArr3[i];
            Object obj = null;
            if (str3.equals("_id")) {
                obj = Integer.valueOf(i);
            } else if (str3.equals("_display_name") || str3.equals("title")) {
                obj = new File(uri.getPath()).getName();
            } else if (str3.equals("_size")) {
                AssetFileDescriptor assetFileDescriptor = null;
                try {
                    assetFileDescriptor = openAssetFile(uri, "r");
                } catch (Exception e2) {
                }
                if (assetFileDescriptor != null) {
                    obj = Long.valueOf(assetFileDescriptor.getLength());
                }
            } else if (str3.equals("mime_type")) {
                obj = getType(uri);
            }
            objArr[i] = obj;
        }
        MatrixCursor matrixCursor = new MatrixCursor(strArr3);
        matrixCursor.addRow(objArr);
        return matrixCursor;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }
}
