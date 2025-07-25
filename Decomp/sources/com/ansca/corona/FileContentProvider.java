package com.ansca.corona;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

@Deprecated
public class FileContentProvider extends ContentProvider {
    public int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }

    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }

    public boolean onCreate() {
        return false;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("Not supported by this provider");
    }
}
