package com.ansca.corona;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.drive.DriveFile;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public final class CoronaEnvironment {
    private static final String CORONA_PREFERENCES_LAST_INSTALL_TIME_KEY = "lastInstallTime";
    private static final String CORONA_PREFERENCES_NAME = "Corona";
    private static Context sApplicationContext = null;
    private static CoronaActivity sCoronaActivity = null;
    private static CoronaLuaErrorHandler sCoronaLuaErrorHandler = new CoronaLuaErrorHandler();
    private static JavaFunction sLuaErrorHandlerFunction = null;
    /* access modifiers changed from: private */
    public static ArrayList<CoronaRuntimeListener> sRuntimeListeners = new ArrayList<>();

    private static class RuntimeEventHandler implements CoronaRuntimeListener {
        private RuntimeEventHandler() {
        }

        private ArrayList<CoronaRuntimeListener> cloneListenerCollection() {
            ArrayList<CoronaRuntimeListener> arrayList;
            synchronized (CoronaEnvironment.sRuntimeListeners) {
                arrayList = (ArrayList) CoronaEnvironment.sRuntimeListeners.clone();
            }
            return arrayList;
        }

        public void onExiting(CoronaRuntime coronaRuntime) {
            Iterator<CoronaRuntimeListener> it = cloneListenerCollection().iterator();
            while (it.hasNext()) {
                CoronaRuntimeListener next = it.next();
                if (next != null) {
                    next.onExiting(coronaRuntime);
                }
            }
        }

        public void onLoaded(CoronaRuntime coronaRuntime) {
            Iterator<CoronaRuntimeListener> it = cloneListenerCollection().iterator();
            while (it.hasNext()) {
                CoronaRuntimeListener next = it.next();
                if (next != null) {
                    next.onLoaded(coronaRuntime);
                }
            }
        }

        public void onResumed(CoronaRuntime coronaRuntime) {
            Iterator<CoronaRuntimeListener> it = cloneListenerCollection().iterator();
            while (it.hasNext()) {
                CoronaRuntimeListener next = it.next();
                if (next != null) {
                    next.onResumed(coronaRuntime);
                }
            }
        }

        public void onStarted(CoronaRuntime coronaRuntime) {
            Iterator<CoronaRuntimeListener> it = cloneListenerCollection().iterator();
            while (it.hasNext()) {
                CoronaRuntimeListener next = it.next();
                if (next != null) {
                    next.onStarted(coronaRuntime);
                }
            }
        }

        public void onSuspended(CoronaRuntime coronaRuntime) {
            Iterator<CoronaRuntimeListener> it = cloneListenerCollection().iterator();
            while (it.hasNext()) {
                CoronaRuntimeListener next = it.next();
                if (next != null) {
                    next.onSuspended(coronaRuntime);
                }
            }
        }
    }

    static {
        setLuaErrorHandler(sCoronaLuaErrorHandler);
        CoronaRuntime.addListener(new RuntimeEventHandler());
    }

    private CoronaEnvironment() {
    }

    public static void addRuntimeListener(CoronaRuntimeListener coronaRuntimeListener) {
        synchronized (sRuntimeListeners) {
            if (coronaRuntimeListener != null) {
                if (sRuntimeListeners.indexOf(coronaRuntimeListener) < 0) {
                    sRuntimeListeners.add(coronaRuntimeListener);
                }
            }
        }
    }

    private static void deleteDirectoryTree(File file) {
        if (file.isDirectory()) {
            for (File deleteDirectoryTree : file.listFiles()) {
                deleteDirectoryTree(deleteDirectoryTree);
            }
        }
        file.delete();
    }

    static void deleteTempDirectory(Context context) {
        try {
            deleteDirectoryTree(getInternalTemporaryDirectory(context));
        } catch (Exception e) {
        }
    }

    public static Context getApplicationContext() {
        return sApplicationContext;
    }

    public static File getCachesDirectory(Context context) {
        setApplicationContext(context);
        File file = new File(context.getCacheDir(), "Caches");
        file.mkdirs();
        return file;
    }

    public static CoronaActivity getCoronaActivity() {
        return sCoronaActivity;
    }

    public static File getDocumentsDirectory(Context context) {
        setApplicationContext(context);
        return context.getDir("data", 0);
    }

    static File getInternalCachesDirectory(Context context) {
        setApplicationContext(context);
        File file = new File(context.getCacheDir(), ".system");
        file.mkdirs();
        return file;
    }

    static File getInternalResourceCachesDirectory(Context context) {
        setApplicationContext(context);
        File file = new File(getInternalCachesDirectory(context), "resources");
        file.mkdirs();
        return file;
    }

    static File getInternalTemporaryDirectory(Context context) {
        setApplicationContext(context);
        File file = new File(getInternalCachesDirectory(context), "temp");
        file.mkdirs();
        return file;
    }

    static File getInternalWebCachesDirectory(Context context) {
        setApplicationContext(context);
        File file = new File(getInternalCachesDirectory(context), AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_WEB);
        file.mkdirs();
        return file;
    }

    public static JavaFunction getLuaErrorHandler() {
        return sLuaErrorHandlerFunction;
    }

    public static File getTemporaryDirectory(Context context) {
        setApplicationContext(context);
        File file = new File(context.getCacheDir(), "tmp");
        file.mkdirs();
        return file;
    }

    static int invokeLuaErrorHandler(long j) {
        JavaFunction javaFunction;
        if (j == 0 || (javaFunction = sLuaErrorHandlerFunction) == null) {
            return 1;
        }
        return javaFunction.invoke(new LuaState(j));
    }

    static boolean isNewInstall(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(CORONA_PREFERENCES_NAME, 0);
            return sharedPreferences == null || new File(context.getPackageCodePath()).lastModified() != sharedPreferences.getLong(CORONA_PREFERENCES_LAST_INSTALL_TIME_KEY, 0);
        } catch (Exception e) {
            return true;
        }
    }

    static void onNewInstall(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(CORONA_PREFERENCES_NAME, 0);
            if (sharedPreferences != null) {
                long lastModified = new File(context.getPackageCodePath()).lastModified();
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putLong(CORONA_PREFERENCES_LAST_INSTALL_TIME_KEY, lastModified);
                edit.commit();
                deleteDirectoryTree(context.getFileStreamPath("coronaResources"));
            }
        } catch (Exception e) {
        }
    }

    public static void removeRuntimeListener(CoronaRuntimeListener coronaRuntimeListener) {
        synchronized (sRuntimeListeners) {
            if (coronaRuntimeListener != null) {
                sRuntimeListeners.remove(coronaRuntimeListener);
            }
        }
    }

    static void setApplicationContext(Context context) {
        if (context == null) {
            throw new NullPointerException();
        }
        Context applicationContext = context.getApplicationContext();
        if (applicationContext == null) {
            throw new NullPointerException();
        }
        sApplicationContext = applicationContext;
    }

    static void setController(Controller controller) {
        sCoronaLuaErrorHandler.setController(controller);
    }

    static void setCoronaActivity(CoronaActivity coronaActivity) {
        if (coronaActivity != null) {
            sApplicationContext = coronaActivity.getApplicationContext();
        }
        sCoronaActivity = coronaActivity;
    }

    public static void setLuaErrorHandler(JavaFunction javaFunction) {
        if (javaFunction != sLuaErrorHandlerFunction) {
            sLuaErrorHandlerFunction = javaFunction;
            if (javaFunction != null) {
                JavaToNativeShim.useJavaLuaErrorHandler();
            } else {
                JavaToNativeShim.useDefaultLuaErrorHandler();
            }
        }
    }

    public static void showCoronaActivity(Context context) {
        if (context != null) {
            sApplicationContext = context.getApplicationContext();
            Intent intent = new Intent(context, CoronaActivity.class);
            intent.addFlags(DriveFile.MODE_READ_ONLY);
            intent.addFlags(131072);
            context.startActivity(intent);
        }
    }
}
