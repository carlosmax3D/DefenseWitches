package jp.co.voyagegroup.android.fluct.jar.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import jp.co.voyagegroup.android.fluct.jar.util.Log;
import jp.stargarage.g2metrics.BuildConfig;

public class FluctDbHelper extends SQLiteOpenHelper {
    private static final String CREATE_SQL = "fluctsdk_create.sql";
    private static final String DATABASE_NAME = "FluctSDK.sqlite";
    private static final int DATABASE_VERSION = 2;
    private static final String TAG = "FluctDbHelper";
    private static final String UPDATE_SQL_V1_V2 = "fluctsdk_update_ver1_ver2.sql";
    private AssetManager mAssetManager;

    public FluctDbHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 2);
        Log.d(TAG, "FluctDbHelper : ");
        this.mAssetManager = context.getResources().getAssets();
    }

    private String readFile(InputStream inputStream) throws IOException {
        Log.d(TAG, "readFile : ");
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream, "SJIS"));
            try {
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader2.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(String.valueOf(readLine) + "\n");
                }
                String sb2 = sb.toString();
                if (bufferedReader2 != null) {
                    bufferedReader2.close();
                }
                return sb2;
            } catch (Throwable th) {
                th = th;
                bufferedReader = bufferedReader2;
            }
        } catch (Throwable th2) {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            throw th2;
        }
        return "";
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        Log.d(TAG, "onCreate : ");
        try {
            for (String str : readFile(this.mAssetManager.open(CREATE_SQL)).split(";")) {
                if (!str.trim().equals(BuildConfig.FLAVOR)) {
                    sQLiteDatabase.execSQL(String.valueOf(str) + ";");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate : Exception is " + e.getLocalizedMessage());
        }
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        Log.d(TAG, "onUpgrade : old ver is " + i + " new ver is " + i2);
        try {
            for (String str : readFile(this.mAssetManager.open(UPDATE_SQL_V1_V2)).split(";")) {
                if (!str.trim().equals(BuildConfig.FLAVOR)) {
                    sQLiteDatabase.execSQL(String.valueOf(str) + ";");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onUpgrade : Exception is " + e.getLocalizedMessage());
        }
    }
}
