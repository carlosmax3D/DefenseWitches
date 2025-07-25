package com.ansca.corona;

import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;
import com.ansca.corona.graphics.opengl.CoronaGLSurfaceView;
import com.naef.jnlua.LuaState;
import java.util.ArrayList;
import java.util.Iterator;
import jp.stargarage.g2metrics.BuildConfig;

public class CoronaRuntime {
    private static ArrayList<CoronaRuntimeListener> sListeners = new ArrayList<>();
    private static ArrayList<CoronaRuntimeWillLoadMainListener> sWillLoadMainListeners = new ArrayList<>();
    private String fBaseDir = BuildConfig.FLAVOR;
    private Controller fController;
    private CoronaGLSurfaceView fGLView;
    private boolean fIsCoronaKit;
    private long fJavaToNativeBridgeAddress;
    private LuaState fLuaState = null;
    private CoronaRuntimeTaskDispatcher fTaskDispatcher = new CoronaRuntimeTaskDispatcher(this);
    private ViewManager fViewManager;
    private boolean fWasDisposed = false;

    private static class ApiLevel9 {
        private ApiLevel9() {
        }

        public static String getNativeLibraryDirectoryFrom(Context context) {
            if (context != null) {
                return context.getApplicationInfo().nativeLibraryDir;
            }
            throw new NullPointerException();
        }
    }

    CoronaRuntime(Context context, boolean z) {
        this.fIsCoronaKit = z;
        this.fGLView = new CoronaGLSurfaceView(context, this, z);
        JavaToNativeShim.init(this);
        this.fController = new Controller(context, this);
        this.fController.setGLView(this.fGLView);
        this.fController.init();
        this.fViewManager = new ViewManager(context, this);
        this.fViewManager.setGLView(this.fGLView);
    }

    static void addListener(CoronaRuntimeListener coronaRuntimeListener) {
        synchronized (sListeners) {
            if (coronaRuntimeListener != null) {
                if (sListeners.indexOf(coronaRuntimeListener) < 0) {
                    sListeners.add(coronaRuntimeListener);
                }
            }
        }
    }

    static void addWillLoadMainListener(CoronaRuntimeWillLoadMainListener coronaRuntimeWillLoadMainListener) {
        synchronized (sWillLoadMainListeners) {
            if (coronaRuntimeWillLoadMainListener != null) {
                if (sWillLoadMainListeners.indexOf(coronaRuntimeWillLoadMainListener) < 0) {
                    sWillLoadMainListeners.add(coronaRuntimeWillLoadMainListener);
                }
            }
        }
    }

    private static ArrayList<CoronaRuntimeListener> cloneListenerCollection() {
        ArrayList<CoronaRuntimeListener> arrayList;
        synchronized (sListeners) {
            arrayList = (ArrayList) sListeners.clone();
        }
        return arrayList;
    }

    private static ArrayList<CoronaRuntimeWillLoadMainListener> cloneWillLoadMainListenerCollection() {
        ArrayList<CoronaRuntimeWillLoadMainListener> arrayList;
        synchronized (sWillLoadMainListeners) {
            arrayList = (ArrayList) sWillLoadMainListeners.clone();
        }
        return arrayList;
    }

    private void onExiting() {
        if (!this.fWasDisposed) {
            Iterator<CoronaRuntimeListener> it = cloneListenerCollection().iterator();
            while (it.hasNext()) {
                CoronaRuntimeListener next = it.next();
                if (next != null) {
                    next.onExiting(this);
                }
            }
        }
    }

    static void removeListener(CoronaRuntimeListener coronaRuntimeListener) {
        synchronized (sListeners) {
            if (coronaRuntimeListener != null) {
                sListeners.remove(coronaRuntimeListener);
            }
        }
    }

    static void removeWillLoadMainListener(CoronaRuntimeWillLoadMainListener coronaRuntimeWillLoadMainListener) {
        synchronized (sWillLoadMainListeners) {
            if (coronaRuntimeWillLoadMainListener != null) {
                sWillLoadMainListeners.remove(coronaRuntimeWillLoadMainListener);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispose() {
        if (!this.fWasDisposed) {
            onExiting();
            CoronaRuntimeProvider.removeRuntime(this);
            if (this.fLuaState != null) {
                this.fLuaState.close();
                this.fLuaState = null;
            }
            this.fWasDisposed = true;
            this.fController.destroy();
            this.fController = null;
            this.fViewManager.destroy();
            this.fViewManager = null;
        }
    }

    /* access modifiers changed from: package-private */
    public Controller getController() {
        return this.fController;
    }

    /* access modifiers changed from: package-private */
    public CoronaGLSurfaceView getGLView() {
        return this.fGLView;
    }

    /* access modifiers changed from: package-private */
    public long getJavaToNativeBridgeAddress() {
        return this.fJavaToNativeBridgeAddress;
    }

    public LuaState getLuaState() {
        return this.fLuaState;
    }

    /* access modifiers changed from: package-private */
    public String getPath() {
        return this.fBaseDir;
    }

    public CoronaRuntimeTaskDispatcher getTaskDispatcher() {
        return this.fTaskDispatcher;
    }

    /* access modifiers changed from: package-private */
    public ViewManager getViewManager() {
        return this.fViewManager;
    }

    /* access modifiers changed from: package-private */
    public void initializePackagePath(LuaState luaState) {
        if (luaState != null) {
            String nativeLibraryDirectoryFrom = Build.VERSION.SDK_INT >= 9 ? ApiLevel9.getNativeLibraryDirectoryFrom(CoronaEnvironment.getApplicationContext()) : CoronaEnvironment.getApplicationContext().getApplicationInfo().dataDir + "/lib";
            if (nativeLibraryDirectoryFrom != null && nativeLibraryDirectoryFrom.length() > 0) {
                this.fLuaState.getGlobal("package");
                this.fLuaState.getField(-1, "cpath");
                String luaState2 = this.fLuaState.toString(-1);
                this.fLuaState.pop(1);
                this.fLuaState.pushString(nativeLibraryDirectoryFrom + "/lib?.so;" + luaState2);
                this.fLuaState.setField(-2, "cpath");
                this.fLuaState.pop(1);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isCoronaKit() {
        return this.fIsCoronaKit;
    }

    public boolean isRunning() {
        if (this.fWasDisposed) {
            return false;
        }
        return this.fController.isRunning();
    }

    /* access modifiers changed from: package-private */
    public void onLoaded(long j) {
        if (!this.fWasDisposed && j != 0) {
            if (this.fLuaState != null) {
                this.fLuaState.close();
                this.fLuaState = null;
            }
            this.fLuaState = new LuaState(j);
            CoronaRuntimeProvider.addRuntime(this);
            initializePackagePath(this.fLuaState);
            Iterator<CoronaRuntimeListener> it = cloneListenerCollection().iterator();
            while (it.hasNext()) {
                CoronaRuntimeListener next = it.next();
                if (next != null) {
                    next.onLoaded(this);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onPause() {
        this.fController.stop();
        this.fGLView.onSuspendCoronaRuntime();
        this.fViewManager.suspend();
    }

    /* access modifiers changed from: package-private */
    public void onResume() {
        this.fController.start();
        this.fGLView.onResumeCoronaRuntime();
        updateViews();
        this.fViewManager.resume();
    }

    /* access modifiers changed from: package-private */
    public void onResumed() {
        if (!this.fWasDisposed) {
            Iterator<CoronaRuntimeListener> it = cloneListenerCollection().iterator();
            while (it.hasNext()) {
                CoronaRuntimeListener next = it.next();
                if (next != null) {
                    next.onResumed(this);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onStarted() {
        if (!this.fWasDisposed) {
            Iterator<CoronaRuntimeListener> it = cloneListenerCollection().iterator();
            while (it.hasNext()) {
                CoronaRuntimeListener next = it.next();
                if (next != null) {
                    next.onStarted(this);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onSuspended() {
        if (!this.fWasDisposed) {
            Iterator<CoronaRuntimeListener> it = cloneListenerCollection().iterator();
            while (it.hasNext()) {
                CoronaRuntimeListener next = it.next();
                if (next != null) {
                    next.onSuspended(this);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onWillLoadMain() {
        if (!this.fWasDisposed) {
            Iterator<CoronaRuntimeWillLoadMainListener> it = cloneWillLoadMainListenerCollection().iterator();
            while (it.hasNext()) {
                CoronaRuntimeWillLoadMainListener next = it.next();
                if (next != null) {
                    next.onWillLoadMain(this);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void reset(Context context) {
        this.fGLView = new CoronaGLSurfaceView(context, this, true);
        this.fController.setGLView(this.fGLView);
        this.fViewManager.setGLView(this.fGLView);
    }

    /* access modifiers changed from: package-private */
    public void setJavaToNativeBridgeAddress(long j) {
        this.fJavaToNativeBridgeAddress = j;
    }

    /* access modifiers changed from: package-private */
    public void setPath(String str) {
        this.fBaseDir = str;
    }

    /* access modifiers changed from: package-private */
    public void updateViews() {
        ViewGroup viewGroup = null;
        ViewManager viewManager = this.fViewManager;
        if (viewManager != null) {
            viewGroup = viewManager.getContentView();
        }
        if (viewGroup != null) {
            if (this.fController.getSystemMonitor() == null || !this.fController.getSystemMonitor().isScreenOff()) {
                if (this.fGLView.getParent() == null) {
                    viewGroup.addView(this.fGLView, 0);
                    this.fGLView.getHolder().setSizeFromLayout();
                }
            } else if (this.fGLView.getParent() != null) {
                viewGroup.removeView(this.fGLView);
            }
            if (this.fController.getSystemMonitor() == null || !this.fController.getSystemMonitor().isScreenLocked()) {
                viewGroup.setVisibility(0);
            } else {
                viewGroup.setVisibility(4);
            }
        }
    }

    public boolean wasDisposed() {
        return this.fWasDisposed;
    }

    public boolean wasNotDisposed() {
        return !wasDisposed();
    }
}
