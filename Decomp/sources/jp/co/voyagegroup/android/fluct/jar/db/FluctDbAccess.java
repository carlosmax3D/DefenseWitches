package jp.co.voyagegroup.android.fluct.jar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.facebook.share.internal.ShareConstants;
import jp.co.voyagegroup.android.fluct.jar.setting.FluctSetting;
import jp.co.voyagegroup.android.fluct.jar.util.FluctConstants;
import jp.co.voyagegroup.android.fluct.jar.util.FluctUtils;
import jp.co.voyagegroup.android.fluct.jar.util.Log;

public class FluctDbAccess {
    private static final String CLOSE_IMAGE_TABLE_NAME = "tbl_close_image";
    private static final String CONFIG_TABLE_NAME = "tbl_config_info";
    private static final String CONVERSION_TABLE_NAME = "tbl_conversion_status";
    private static final String INTERSTITIAL_TABLE_NAME = "tbl_interstitial_info";
    private static final String LOADING_IMAGE_TABLE_NAME = "tbl_loading_image";
    private static final String TAG = "FluctDbAccess";

    public static synchronized boolean checkInterstitialData(Context context, String str) {
        boolean z;
        synchronized (FluctDbAccess.class) {
            Log.d(TAG, "checkInterstitialData : MediaId is " + str);
            z = false;
            SQLiteDatabase sQLiteDatabase = null;
            try {
                sQLiteDatabase = new FluctDbHelper(context).getReadableDatabase();
                if (queryCount(sQLiteDatabase, INTERSTITIAL_TABLE_NAME, "media_id = ?", new String[]{str}) != 0) {
                    z = true;
                }
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "checkInterstitialData : Exception is " + e.getLocalizedMessage());
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            } catch (Throwable th) {
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                throw th;
            }
        }
        return z;
    }

    public static synchronized void deleteInterstitialData(Context context, String str) {
        synchronized (FluctDbAccess.class) {
            Log.d(TAG, "deleteInterstitialData : MediaId is " + str);
            SQLiteDatabase sQLiteDatabase = null;
            try {
                sQLiteDatabase = new FluctDbHelper(context).getReadableDatabase();
                sQLiteDatabase.delete(INTERSTITIAL_TABLE_NAME, "media_id = ?", new String[]{str});
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "deleteInterstitialData : Exception is " + e.getLocalizedMessage());
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            } catch (Throwable th) {
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                throw th;
            }
        }
    }

    public static synchronized byte[] getCloseButtonImage(Context context) {
        byte[] bArr;
        synchronized (FluctDbAccess.class) {
            Log.d(TAG, "getCloseButtonImage : ");
            bArr = null;
            Cursor cursor = null;
            SQLiteDatabase sQLiteDatabase = null;
            try {
                SQLiteDatabase readableDatabase = new FluctDbHelper(context).getReadableDatabase();
                Cursor query = readableDatabase.query(CLOSE_IMAGE_TABLE_NAME, new String[]{ShareConstants.WEB_DIALOG_PARAM_ID, "close_image"}, (String) null, (String[]) null, (String) null, (String) null, (String) null);
                if (query != null && query.getCount() > 0) {
                    query.moveToFirst();
                    bArr = query.getBlob(query.getColumnIndex("close_image"));
                }
                if (query != null) {
                    query.close();
                }
                if (readableDatabase != null) {
                    readableDatabase.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "getCloseButtonImage : Exception is " + e.getLocalizedMessage());
                if (cursor != null) {
                    cursor.close();
                }
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                throw th;
            }
        }
        return bArr;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: jp.co.voyagegroup.android.fluct.jar.setting.FluctSetting} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized jp.co.voyagegroup.android.fluct.jar.setting.FluctSetting getConfig(android.content.Context r13, java.lang.String r14) {
        /*
            java.lang.Class<jp.co.voyagegroup.android.fluct.jar.db.FluctDbAccess> r12 = jp.co.voyagegroup.android.fluct.jar.db.FluctDbAccess.class
            monitor-enter(r12)
            java.lang.String r2 = "FluctDbAccess"
            java.lang.String r3 = "getConfig : "
            jp.co.voyagegroup.android.fluct.jar.util.Log.d(r2, r3)     // Catch:{ all -> 0x0090 }
            r11 = 0
            r9 = 0
            r1 = 0
            jp.co.voyagegroup.android.fluct.jar.db.FluctDbHelper r2 = new jp.co.voyagegroup.android.fluct.jar.db.FluctDbHelper     // Catch:{ Exception -> 0x006c }
            r2.<init>(r13)     // Catch:{ Exception -> 0x006c }
            android.database.sqlite.SQLiteDatabase r1 = r2.getReadableDatabase()     // Catch:{ Exception -> 0x006c }
            java.lang.String r2 = "tbl_config_info"
            r3 = 2
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ Exception -> 0x006c }
            r4 = 0
            java.lang.String r5 = "mediaId"
            r3[r4] = r5     // Catch:{ Exception -> 0x006c }
            r4 = 1
            java.lang.String r5 = "config"
            r3[r4] = r5     // Catch:{ Exception -> 0x006c }
            java.lang.String r4 = "mediaId=?"
            r5 = 1
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ Exception -> 0x006c }
            r6 = 0
            r5[r6] = r14     // Catch:{ Exception -> 0x006c }
            r6 = 0
            r7 = 0
            r8 = 0
            android.database.Cursor r9 = r1.query(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Exception -> 0x006c }
            boolean r2 = r9.moveToFirst()     // Catch:{ Exception -> 0x006c }
            if (r2 == 0) goto L_0x0060
            java.lang.String r2 = "config"
            int r2 = r9.getColumnIndex(r2)     // Catch:{ Exception -> 0x006c }
            byte[] r2 = r9.getBlob(r2)     // Catch:{ Exception -> 0x006c }
            java.lang.Object r2 = jp.co.voyagegroup.android.fluct.jar.util.FluctUtils.bytesToObject(r2)     // Catch:{ Exception -> 0x006c }
            r0 = r2
            jp.co.voyagegroup.android.fluct.jar.setting.FluctSetting r0 = (jp.co.voyagegroup.android.fluct.jar.setting.FluctSetting) r0     // Catch:{ Exception -> 0x006c }
            r11 = r0
            java.lang.String r2 = "FluctDbAccess"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x006c }
            java.lang.String r4 = "getConfig : setting is "
            r3.<init>(r4)     // Catch:{ Exception -> 0x006c }
            java.lang.StringBuilder r3 = r3.append(r11)     // Catch:{ Exception -> 0x006c }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x006c }
            jp.co.voyagegroup.android.fluct.jar.util.Log.v(r2, r3)     // Catch:{ Exception -> 0x006c }
        L_0x0060:
            if (r9 == 0) goto L_0x0065
            r9.close()     // Catch:{ all -> 0x0090 }
        L_0x0065:
            if (r1 == 0) goto L_0x006a
            r1.close()     // Catch:{ all -> 0x0090 }
        L_0x006a:
            monitor-exit(r12)
            return r11
        L_0x006c:
            r10 = move-exception
            java.lang.String r2 = "FluctDbAccess"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0093 }
            java.lang.String r4 = "getConfig : Exception is "
            r3.<init>(r4)     // Catch:{ all -> 0x0093 }
            java.lang.String r4 = r10.getLocalizedMessage()     // Catch:{ all -> 0x0093 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0093 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0093 }
            jp.co.voyagegroup.android.fluct.jar.util.Log.e(r2, r3)     // Catch:{ all -> 0x0093 }
            if (r9 == 0) goto L_0x008a
            r9.close()     // Catch:{ all -> 0x0090 }
        L_0x008a:
            if (r1 == 0) goto L_0x006a
            r1.close()     // Catch:{ all -> 0x0090 }
            goto L_0x006a
        L_0x0090:
            r2 = move-exception
            monitor-exit(r12)
            throw r2
        L_0x0093:
            r2 = move-exception
            if (r9 == 0) goto L_0x0099
            r9.close()     // Catch:{ all -> 0x0090 }
        L_0x0099:
            if (r1 == 0) goto L_0x009e
            r1.close()     // Catch:{ all -> 0x0090 }
        L_0x009e:
            throw r2     // Catch:{ all -> 0x0090 }
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.voyagegroup.android.fluct.jar.db.FluctDbAccess.getConfig(android.content.Context, java.lang.String):jp.co.voyagegroup.android.fluct.jar.setting.FluctSetting");
    }

    public static synchronized int getConversionFlag(Context context) {
        int i;
        synchronized (FluctDbAccess.class) {
            Log.d(TAG, "getConversionFlag : ");
            i = 0;
            Cursor cursor = null;
            SQLiteDatabase sQLiteDatabase = null;
            try {
                SQLiteDatabase readableDatabase = new FluctDbHelper(context).getReadableDatabase();
                Cursor query = readableDatabase.query(CONVERSION_TABLE_NAME, new String[]{"id,status"}, (String) null, (String[]) null, (String) null, (String) null, (String) null);
                if (query.moveToFirst()) {
                    i = query.getInt(1);
                    Log.v(TAG, "getConversionFlag : conversionFlag is " + i);
                }
                if (query != null) {
                    query.close();
                }
                if (readableDatabase != null) {
                    readableDatabase.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "getConversionFlag : Exception is " + e.getLocalizedMessage());
                if (cursor != null) {
                    cursor.close();
                }
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                throw th;
            }
        }
        return i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b5 A[SYNTHETIC, Splitter:B:28:0x00b5] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00ba  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00c4 A[SYNTHETIC, Splitter:B:37:0x00c4] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00c9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized jp.co.voyagegroup.android.fluct.jar.db.FluctInterstitialTable getInterstitial(android.content.Context r13, java.lang.String r14) {
        /*
            java.lang.Class<jp.co.voyagegroup.android.fluct.jar.db.FluctDbAccess> r12 = jp.co.voyagegroup.android.fluct.jar.db.FluctDbAccess.class
            monitor-enter(r12)
            java.lang.String r1 = "FluctDbAccess"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00be }
            java.lang.String r3 = "getInterstitial : MediaId is "
            r2.<init>(r3)     // Catch:{ all -> 0x00be }
            java.lang.StringBuilder r2 = r2.append(r14)     // Catch:{ all -> 0x00be }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00be }
            jp.co.voyagegroup.android.fluct.jar.util.Log.d(r1, r2)     // Catch:{ all -> 0x00be }
            r10 = 0
            r8 = 0
            r0 = 0
            jp.co.voyagegroup.android.fluct.jar.db.FluctDbHelper r1 = new jp.co.voyagegroup.android.fluct.jar.db.FluctDbHelper     // Catch:{ Exception -> 0x009a }
            r1.<init>(r13)     // Catch:{ Exception -> 0x009a }
            android.database.sqlite.SQLiteDatabase r0 = r1.getReadableDatabase()     // Catch:{ Exception -> 0x009a }
            java.lang.String r1 = "tbl_interstitial_info"
            r2 = 1
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ Exception -> 0x009a }
            r3 = 0
            java.lang.String r4 = "id, media_id, rate, width, height, adhtml, update_time"
            r2[r3] = r4     // Catch:{ Exception -> 0x009a }
            java.lang.String r3 = "media_id = ?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ Exception -> 0x009a }
            r5 = 0
            r4[r5] = r14     // Catch:{ Exception -> 0x009a }
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r8 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x009a }
            if (r8 == 0) goto L_0x008e
            int r1 = r8.getCount()     // Catch:{ Exception -> 0x009a }
            if (r1 <= 0) goto L_0x008e
            r8.moveToFirst()     // Catch:{ Exception -> 0x009a }
            jp.co.voyagegroup.android.fluct.jar.db.FluctInterstitialTable r11 = new jp.co.voyagegroup.android.fluct.jar.db.FluctInterstitialTable     // Catch:{ Exception -> 0x009a }
            r11.<init>()     // Catch:{ Exception -> 0x009a }
            java.lang.String r1 = "rate"
            int r1 = r8.getColumnIndex(r1)     // Catch:{ Exception -> 0x00d0, all -> 0x00cd }
            int r1 = r8.getInt(r1)     // Catch:{ Exception -> 0x00d0, all -> 0x00cd }
            r11.setRate(r1)     // Catch:{ Exception -> 0x00d0, all -> 0x00cd }
            java.lang.String r1 = "width"
            int r1 = r8.getColumnIndex(r1)     // Catch:{ Exception -> 0x00d0, all -> 0x00cd }
            int r1 = r8.getInt(r1)     // Catch:{ Exception -> 0x00d0, all -> 0x00cd }
            r11.setWidth(r1)     // Catch:{ Exception -> 0x00d0, all -> 0x00cd }
            java.lang.String r1 = "height"
            int r1 = r8.getColumnIndex(r1)     // Catch:{ Exception -> 0x00d0, all -> 0x00cd }
            int r1 = r8.getInt(r1)     // Catch:{ Exception -> 0x00d0, all -> 0x00cd }
            r11.setHeight(r1)     // Catch:{ Exception -> 0x00d0, all -> 0x00cd }
            java.lang.String r1 = "adhtml"
            int r1 = r8.getColumnIndex(r1)     // Catch:{ Exception -> 0x00d0, all -> 0x00cd }
            java.lang.String r1 = r8.getString(r1)     // Catch:{ Exception -> 0x00d0, all -> 0x00cd }
            r11.setAdHtml(r1)     // Catch:{ Exception -> 0x00d0, all -> 0x00cd }
            java.lang.String r1 = "update_time"
            int r1 = r8.getColumnIndex(r1)     // Catch:{ Exception -> 0x00d0, all -> 0x00cd }
            int r1 = r8.getInt(r1)     // Catch:{ Exception -> 0x00d0, all -> 0x00cd }
            r11.setUpdateTime(r1)     // Catch:{ Exception -> 0x00d0, all -> 0x00cd }
            r10 = r11
        L_0x008e:
            if (r8 == 0) goto L_0x0093
            r8.close()     // Catch:{ all -> 0x00be }
        L_0x0093:
            if (r0 == 0) goto L_0x0098
            r0.close()     // Catch:{ all -> 0x00be }
        L_0x0098:
            monitor-exit(r12)
            return r10
        L_0x009a:
            r9 = move-exception
        L_0x009b:
            java.lang.String r1 = "FluctDbAccess"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c1 }
            java.lang.String r3 = "getInterstitial : Exception is "
            r2.<init>(r3)     // Catch:{ all -> 0x00c1 }
            java.lang.String r3 = r9.getLocalizedMessage()     // Catch:{ all -> 0x00c1 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x00c1 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00c1 }
            jp.co.voyagegroup.android.fluct.jar.util.Log.e(r1, r2)     // Catch:{ all -> 0x00c1 }
            if (r8 == 0) goto L_0x00b8
            r8.close()     // Catch:{ all -> 0x00be }
        L_0x00b8:
            if (r0 == 0) goto L_0x0098
            r0.close()     // Catch:{ all -> 0x00be }
            goto L_0x0098
        L_0x00be:
            r1 = move-exception
            monitor-exit(r12)
            throw r1
        L_0x00c1:
            r1 = move-exception
        L_0x00c2:
            if (r8 == 0) goto L_0x00c7
            r8.close()     // Catch:{ all -> 0x00be }
        L_0x00c7:
            if (r0 == 0) goto L_0x00cc
            r0.close()     // Catch:{ all -> 0x00be }
        L_0x00cc:
            throw r1     // Catch:{ all -> 0x00be }
        L_0x00cd:
            r1 = move-exception
            r10 = r11
            goto L_0x00c2
        L_0x00d0:
            r9 = move-exception
            r10 = r11
            goto L_0x009b
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.voyagegroup.android.fluct.jar.db.FluctDbAccess.getInterstitial(android.content.Context, java.lang.String):jp.co.voyagegroup.android.fluct.jar.db.FluctInterstitialTable");
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0080 A[SYNTHETIC, Splitter:B:31:0x0080] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x008f A[SYNTHETIC, Splitter:B:40:0x008f] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0094  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.util.ArrayList<byte[]> getLoadingImage(android.content.Context r15) {
        /*
            java.lang.Class<jp.co.voyagegroup.android.fluct.jar.db.FluctDbAccess> r14 = jp.co.voyagegroup.android.fluct.jar.db.FluctDbAccess.class
            monitor-enter(r14)
            java.lang.String r1 = "FluctDbAccess"
            java.lang.String r2 = "getLoadingImage : "
            jp.co.voyagegroup.android.fluct.jar.util.Log.d(r1, r2)     // Catch:{ all -> 0x0089 }
            r12 = 0
            r9 = 0
            r0 = 0
            jp.co.voyagegroup.android.fluct.jar.db.FluctDbHelper r1 = new jp.co.voyagegroup.android.fluct.jar.db.FluctDbHelper     // Catch:{ Exception -> 0x0065 }
            r1.<init>(r15)     // Catch:{ Exception -> 0x0065 }
            android.database.sqlite.SQLiteDatabase r0 = r1.getReadableDatabase()     // Catch:{ Exception -> 0x0065 }
            java.lang.String r1 = "tbl_loading_image"
            r2 = 2
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0065 }
            r3 = 0
            java.lang.String r4 = "id"
            r2[r3] = r4     // Catch:{ Exception -> 0x0065 }
            r3 = 1
            java.lang.String r4 = "loading"
            r2[r3] = r4     // Catch:{ Exception -> 0x0065 }
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r9 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0065 }
            if (r9 == 0) goto L_0x0046
            int r1 = r9.getCount()     // Catch:{ Exception -> 0x0065 }
            if (r1 <= 0) goto L_0x0046
            int r8 = r9.getCount()     // Catch:{ Exception -> 0x0065 }
            r9.moveToFirst()     // Catch:{ Exception -> 0x0065 }
            java.util.ArrayList r13 = new java.util.ArrayList     // Catch:{ Exception -> 0x0065 }
            r13.<init>()     // Catch:{ Exception -> 0x0065 }
            r11 = 0
        L_0x0043:
            if (r11 < r8) goto L_0x0052
            r12 = r13
        L_0x0046:
            if (r9 == 0) goto L_0x004b
            r9.close()     // Catch:{ all -> 0x0089 }
        L_0x004b:
            if (r0 == 0) goto L_0x0050
            r0.close()     // Catch:{ all -> 0x0089 }
        L_0x0050:
            monitor-exit(r14)
            return r12
        L_0x0052:
            java.lang.String r1 = "loading"
            int r1 = r9.getColumnIndex(r1)     // Catch:{ Exception -> 0x009b, all -> 0x0098 }
            byte[] r1 = r9.getBlob(r1)     // Catch:{ Exception -> 0x009b, all -> 0x0098 }
            r13.add(r1)     // Catch:{ Exception -> 0x009b, all -> 0x0098 }
            r9.moveToNext()     // Catch:{ Exception -> 0x009b, all -> 0x0098 }
            int r11 = r11 + 1
            goto L_0x0043
        L_0x0065:
            r10 = move-exception
        L_0x0066:
            java.lang.String r1 = "FluctDbAccess"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x008c }
            java.lang.String r3 = "getLoadingImage : Exception is "
            r2.<init>(r3)     // Catch:{ all -> 0x008c }
            java.lang.String r3 = r10.getLocalizedMessage()     // Catch:{ all -> 0x008c }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x008c }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x008c }
            jp.co.voyagegroup.android.fluct.jar.util.Log.e(r1, r2)     // Catch:{ all -> 0x008c }
            if (r9 == 0) goto L_0x0083
            r9.close()     // Catch:{ all -> 0x0089 }
        L_0x0083:
            if (r0 == 0) goto L_0x0050
            r0.close()     // Catch:{ all -> 0x0089 }
            goto L_0x0050
        L_0x0089:
            r1 = move-exception
            monitor-exit(r14)
            throw r1
        L_0x008c:
            r1 = move-exception
        L_0x008d:
            if (r9 == 0) goto L_0x0092
            r9.close()     // Catch:{ all -> 0x0089 }
        L_0x0092:
            if (r0 == 0) goto L_0x0097
            r0.close()     // Catch:{ all -> 0x0089 }
        L_0x0097:
            throw r1     // Catch:{ all -> 0x0089 }
        L_0x0098:
            r1 = move-exception
            r12 = r13
            goto L_0x008d
        L_0x009b:
            r10 = move-exception
            r12 = r13
            goto L_0x0066
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.voyagegroup.android.fluct.jar.db.FluctDbAccess.getLoadingImage(android.content.Context):java.util.ArrayList");
    }

    private static synchronized int queryCount(SQLiteDatabase sQLiteDatabase, String str, String str2, String[] strArr) {
        int i;
        synchronized (FluctDbAccess.class) {
            Log.d(TAG, "queryCount : table is " + str);
            i = 0;
            Cursor cursor = null;
            try {
                Cursor query = sQLiteDatabase.query(str, new String[]{"count(1)"}, str2, strArr, (String) null, (String) null, (String) null);
                if (query.moveToFirst()) {
                    i = query.getInt(0);
                }
                if (query != null) {
                    query.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "queryCount : Exception is " + e.getLocalizedMessage());
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }
        return i;
    }

    public static synchronized void saveConfig(Context context, FluctSetting fluctSetting) {
        synchronized (FluctDbAccess.class) {
            Log.d(TAG, "saveConfig : setting is " + fluctSetting);
            SQLiteDatabase sQLiteDatabase = null;
            try {
                sQLiteDatabase = new FluctDbHelper(context).getWritableDatabase();
                int queryCount = queryCount(sQLiteDatabase, CONFIG_TABLE_NAME, "mediaId like ?", new String[]{fluctSetting.getMediaId()});
                ContentValues contentValues = new ContentValues();
                contentValues.put("mediaId", fluctSetting.getMediaId());
                contentValues.put("config", FluctUtils.objectToBytes(fluctSetting));
                contentValues.put("update_time", Long.valueOf(System.currentTimeMillis()));
                if (queryCount == 0) {
                    sQLiteDatabase.insert(CONFIG_TABLE_NAME, (String) null, contentValues);
                } else {
                    sQLiteDatabase.update(CONFIG_TABLE_NAME, contentValues, "mediaId=?", new String[]{fluctSetting.getMediaId()});
                }
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "saveConfig : Exception is " + e.getLocalizedMessage());
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            } catch (Throwable th) {
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                throw th;
            }
        }
    }

    public static synchronized void setConversionFlag(Context context, int i) {
        synchronized (FluctDbAccess.class) {
            Log.d(TAG, "setConversionFlag : flag is " + i);
            SQLiteDatabase sQLiteDatabase = null;
            try {
                SQLiteDatabase writableDatabase = new FluctDbHelper(context).getWritableDatabase();
                if (queryCount(writableDatabase, CONVERSION_TABLE_NAME, "1=1", (String[]) null) == 0) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("status", Integer.valueOf(i));
                    contentValues.put("update_time", Long.valueOf(System.currentTimeMillis()));
                    writableDatabase.insert(CONVERSION_TABLE_NAME, (String) null, contentValues);
                }
                if (writableDatabase != null) {
                    writableDatabase.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "setConversionFlag : Exception is " + e.getLocalizedMessage());
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            } catch (Throwable th) {
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                throw th;
            }
        }
    }

    public static synchronized void setInterstitial(Context context, FluctInterstitialTable fluctInterstitialTable) {
        synchronized (FluctDbAccess.class) {
            Log.d(TAG, "setInterstitial : ");
            SQLiteDatabase sQLiteDatabase = null;
            try {
                sQLiteDatabase = new FluctDbHelper(context).getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("media_id", fluctInterstitialTable.getMediaId());
                contentValues.put("rate", Integer.valueOf(fluctInterstitialTable.getRate()));
                contentValues.put(FluctConstants.XML_NODE_WIDTH, Integer.valueOf(fluctInterstitialTable.getWidth()));
                contentValues.put(FluctConstants.XML_NODE_HEIGHT, Integer.valueOf(fluctInterstitialTable.getHeight()));
                contentValues.put("adhtml", fluctInterstitialTable.getAdHtml());
                contentValues.put("update_time", Integer.valueOf(fluctInterstitialTable.getUpdateTime()));
                if (queryCount(sQLiteDatabase, INTERSTITIAL_TABLE_NAME, "media_id = ?", new String[]{fluctInterstitialTable.getMediaId()}) == 0) {
                    sQLiteDatabase.insert(INTERSTITIAL_TABLE_NAME, (String) null, contentValues);
                } else {
                    sQLiteDatabase.update(INTERSTITIAL_TABLE_NAME, contentValues, "media_id = ?", new String[]{fluctInterstitialTable.getMediaId()});
                }
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "setInterstitial : Exception is " + e.getLocalizedMessage());
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            } catch (Throwable th) {
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                throw th;
            }
            Log.v(TAG, "setInterstitial : end");
        }
    }
}
