package com.tapjoy;

import android.util.Log;
import java.util.ArrayList;

public class TapjoyLog {
    private static final int MAX_STRING_SIZE = 4096;
    private static final String TAG = "TapjoyLog";
    private static ArrayList<String> logHistory;
    private static boolean saveLog = false;
    private static boolean showLog = false;

    public static void clearLogHistory() {
        if (logHistory != null) {
            logHistory.clear();
        }
    }

    public static void d(String str, String str2) {
        if (showLog) {
            Log.d(str, str2);
        }
        if (saveLog) {
            logHistory.add(str2);
        }
    }

    public static void e(String str, String str2) {
        if (showLog) {
            Log.e(str, str2);
        }
        if (saveLog) {
            logHistory.add(str2);
        }
    }

    public static void enableLogging(boolean z) {
        Log.i(TAG, "enableLogging: " + z);
        showLog = z;
    }

    public static ArrayList<String> getLogHistory() {
        return logHistory;
    }

    public static void i(String str, String str2) {
        if (showLog) {
            if (str2.length() > 4096) {
                for (int i = 0; i <= str2.length() / 4096; i++) {
                    int i2 = i * 4096;
                    int i3 = (i + 1) * 4096;
                    if (i3 > str2.length()) {
                        i3 = str2.length();
                    }
                    Log.i(str, str2.substring(i2, i3));
                }
            } else {
                Log.i(str, str2);
            }
        }
        if (saveLog) {
            logHistory.add(str2);
        }
    }

    public static boolean isLoggingEnabled() {
        return showLog;
    }

    public static void saveLogHistory(boolean z) {
        saveLog = z;
        if (saveLog) {
            logHistory = new ArrayList<>(1024);
        } else {
            logHistory = null;
        }
    }

    public static void v(String str, String str2) {
        if (showLog) {
            Log.v(str, str2);
        }
        if (saveLog) {
            logHistory.add(str2);
        }
    }

    public static void w(String str, String str2) {
        if (showLog) {
            Log.w(str, str2);
        }
        if (saveLog) {
            logHistory.add(str2);
        }
    }
}
