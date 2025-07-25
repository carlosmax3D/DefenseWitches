package com.ansca.corona.graphics.opengl;

import android.content.Context;
import android.opengl.GLDebugHelper;
import android.opengl.GLES10;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class GLSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int DEBUG_CHECK_GL_ERROR = 1;
    public static final int DEBUG_LOG_GL_CALLS = 2;
    private static final boolean LOG_ATTACH_DETACH = false;
    private static final boolean LOG_EGL = false;
    private static final boolean LOG_PAUSE_RESUME = false;
    private static final boolean LOG_RENDERER = false;
    private static final boolean LOG_RENDERER_DRAW_FRAME = false;
    private static final boolean LOG_SURFACE = false;
    private static final boolean LOG_THREADS = false;
    public static final int RENDERMODE_CONTINUOUSLY = 1;
    public static final int RENDERMODE_WHEN_DIRTY = 0;
    private static final String TAG = "GLSurfaceView";
    /* access modifiers changed from: private */
    public static final GLThreadManager sGLThreadManager = new GLThreadManager();
    /* access modifiers changed from: private */
    public int mDebugFlags;
    private boolean mDetached;
    /* access modifiers changed from: private */
    public EGLConfigChooser mEGLConfigChooser;
    /* access modifiers changed from: private */
    public int mEGLContextClientVersion;
    /* access modifiers changed from: private */
    public EGLContextFactory mEGLContextFactory;
    /* access modifiers changed from: private */
    public EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;
    private GLThread mGLThread;
    /* access modifiers changed from: private */
    public GLWrapper mGLWrapper;
    /* access modifiers changed from: private */
    public boolean mNeedsSwap = true;
    private boolean mPreserveEGLContextOnPause;
    /* access modifiers changed from: private */
    public Renderer mRenderer;
    private final WeakReference<GLSurfaceView> mThisWeakRef = new WeakReference<>(this);

    private abstract class BaseConfigChooser implements EGLConfigChooser {
        protected int[] mConfigSpec;

        public BaseConfigChooser(int[] iArr) {
            this.mConfigSpec = filterConfigSpec(iArr);
        }

        private int[] filterConfigSpec(int[] iArr) {
            if (GLSurfaceView.this.mEGLContextClientVersion != 2) {
                return iArr;
            }
            int length = iArr.length;
            int[] iArr2 = new int[(length + 2)];
            System.arraycopy(iArr, 0, iArr2, 0, length - 1);
            iArr2[length - 1] = 12352;
            iArr2[length] = 4;
            iArr2[length + 1] = 12344;
            return iArr2;
        }

        public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay) {
            int[] iArr = new int[1];
            if (!egl10.eglChooseConfig(eGLDisplay, this.mConfigSpec, (EGLConfig[]) null, 0, iArr)) {
                throw new IllegalArgumentException("eglChooseConfig failed");
            }
            int i = iArr[0];
            if (i <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }
            EGLConfig[] eGLConfigArr = new EGLConfig[i];
            if (!egl10.eglChooseConfig(eGLDisplay, this.mConfigSpec, eGLConfigArr, i, iArr)) {
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }
            EGLConfig chooseConfig = chooseConfig(egl10, eGLDisplay, eGLConfigArr);
            if (chooseConfig != null) {
                return chooseConfig;
            }
            throw new IllegalArgumentException("No config chosen");
        }

        /* access modifiers changed from: package-private */
        public abstract EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr);
    }

    private class ComponentSizeChooser extends BaseConfigChooser {
        protected int mAlphaSize;
        protected int mBlueSize;
        protected int mDepthSize;
        protected int mGreenSize;
        protected int mRedSize;
        protected int mStencilSize;
        private int[] mValue = new int[1];

        public ComponentSizeChooser(int i, int i2, int i3, int i4, int i5, int i6) {
            super(new int[]{12324, i, 12323, i2, 12322, i3, 12321, 0, 12325, i5, 12326, i6, 12327, 12344, 12344});
            this.mRedSize = i;
            this.mGreenSize = i2;
            this.mBlueSize = i3;
            this.mAlphaSize = i4;
            this.mDepthSize = i5;
            this.mStencilSize = i6;
        }

        private int findConfigAttrib(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i, int i2) {
            return egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, this.mValue) ? this.mValue[0] : i2;
        }

        public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr) {
            EGLConfig eGLConfig = null;
            int i = 1000;
            int length = eGLConfigArr.length;
            int i2 = 0;
            while (true) {
                int i3 = i2;
                if (i3 >= length) {
                    return eGLConfig;
                }
                EGLConfig eGLConfig2 = eGLConfigArr[i3];
                if (findConfigAttrib(egl10, eGLDisplay, eGLConfig2, 12325, 0) >= this.mDepthSize && findConfigAttrib(egl10, eGLDisplay, eGLConfig2, 12326, 0) >= this.mStencilSize) {
                    int abs = Math.abs(findConfigAttrib(egl10, eGLDisplay, eGLConfig2, 12324, 0) - this.mRedSize) + Math.abs(findConfigAttrib(egl10, eGLDisplay, eGLConfig2, 12323, 0) - this.mGreenSize) + Math.abs(findConfigAttrib(egl10, eGLDisplay, eGLConfig2, 12322, 0) - this.mBlueSize) + Math.abs(findConfigAttrib(egl10, eGLDisplay, eGLConfig2, 12321, 0) - this.mAlphaSize);
                    if (abs < i) {
                        i = abs;
                        eGLConfig = eGLConfig2;
                    }
                }
                i2 = i3 + 1;
            }
        }

        public int getMinAlphaSize() {
            return this.mAlphaSize;
        }

        public void setMinAlphaSize(int i) {
            this.mAlphaSize = i;
        }
    }

    private class DefaultContextFactory implements EGLContextFactory {
        private int EGL_CONTEXT_CLIENT_VERSION;

        private DefaultContextFactory() {
            this.EGL_CONTEXT_CLIENT_VERSION = 12440;
        }

        public EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
            int[] iArr = {this.EGL_CONTEXT_CLIENT_VERSION, GLSurfaceView.this.mEGLContextClientVersion, 12344};
            EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
            if (GLSurfaceView.this.mEGLContextClientVersion == 0) {
                iArr = null;
            }
            return egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        }

        public void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext) {
            if (!egl10.eglDestroyContext(eGLDisplay, eGLContext)) {
                Log.e("DefaultContextFactory", "display:" + eGLDisplay + " context: " + eGLContext);
                EglHelper.throwEglException("eglDestroyContex", egl10.eglGetError());
            }
        }
    }

    private static class DefaultWindowSurfaceFactory implements EGLWindowSurfaceFactory {
        private DefaultWindowSurfaceFactory() {
        }

        public EGLSurface createWindowSurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj) {
            try {
                return egl10.eglCreateWindowSurface(eGLDisplay, eGLConfig, obj, (int[]) null);
            } catch (IllegalArgumentException e) {
                Log.e(GLSurfaceView.TAG, "eglCreateWindowSurface", e);
                return null;
            }
        }

        public void destroySurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface) {
            egl10.eglDestroySurface(eGLDisplay, eGLSurface);
        }
    }

    public interface EGLConfigChooser {
        EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay);
    }

    public interface EGLContextFactory {
        EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig);

        void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext);
    }

    public interface EGLWindowSurfaceFactory {
        EGLSurface createWindowSurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj);

        void destroySurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface);
    }

    private static class EglHelper {
        EGL10 mEgl;
        EGLConfig mEglConfig;
        EGLContext mEglContext;
        EGLDisplay mEglDisplay;
        EGLSurface mEglSurface;
        private WeakReference<GLSurfaceView> mGLSurfaceViewWeakRef;
        private boolean mHasRetriedCreatingSurface = false;

        public EglHelper(WeakReference<GLSurfaceView> weakReference) {
            this.mGLSurfaceViewWeakRef = weakReference;
        }

        private void destroySurfaceImp() {
            if (this.mEglSurface != null && this.mEglSurface != EGL10.EGL_NO_SURFACE) {
                this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                GLSurfaceView gLSurfaceView = (GLSurfaceView) this.mGLSurfaceViewWeakRef.get();
                if (gLSurfaceView != null) {
                    gLSurfaceView.mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
                }
                this.mEglSurface = null;
            }
        }

        public static String formatEglError(String str, int i) {
            return str + " failed: " + getErrorString(i);
        }

        public static String getErrorString(int i) {
            switch (i) {
                case 12288:
                    return "EGL_SUCCESS";
                case 12289:
                    return "EGL_NOT_INITIALIZED";
                case 12290:
                    return "EGL_BAD_ACCESS";
                case 12291:
                    return "EGL_BAD_ALLOC";
                case 12292:
                    return "EGL_BAD_ATTRIBUTE";
                case 12293:
                    return "EGL_BAD_CONFIG";
                case 12294:
                    return "EGL_BAD_CONTEXT";
                case 12295:
                    return "EGL_BAD_CURRENT_SURFACE";
                case 12296:
                    return "EGL_BAD_DISPLAY";
                case 12297:
                    return "EGL_BAD_MATCH";
                case 12298:
                    return "EGL_BAD_NATIVE_PIXMAP";
                case 12299:
                    return "EGL_BAD_NATIVE_WINDOW";
                case 12300:
                    return "EGL_BAD_PARAMETER";
                case 12301:
                    return "EGL_BAD_SURFACE";
                case 12302:
                    return "EGL_CONTEXT_LOST";
                default:
                    return Integer.toHexString(i);
            }
        }

        public static void logEglErrorAsWarning(String str, String str2, int i) {
            Log.w(str, formatEglError(str2, i));
        }

        private boolean recreateSurface() {
            destroySurfaceImp();
            GLSurfaceView gLSurfaceView = (GLSurfaceView) this.mGLSurfaceViewWeakRef.get();
            if (!(gLSurfaceView == null || this.mEglContext == null)) {
                SurfaceHolder holder = gLSurfaceView.getHolder();
                if (!(holder == null || holder.getSurface() == null || !holder.getSurface().isValid())) {
                    gLSurfaceView.mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
                }
                this.mEglContext = null;
            }
            start();
            return createSurface();
        }

        private void throwEglException(String str) {
            throwEglException(str, this.mEgl.eglGetError());
        }

        public static void throwEglException(String str, int i) {
            throw new RuntimeException(formatEglError(str, i));
        }

        /* access modifiers changed from: package-private */
        public GL createGL() {
            GL gl = this.mEglContext.getGL();
            GLSurfaceView gLSurfaceView = (GLSurfaceView) this.mGLSurfaceViewWeakRef.get();
            if (gLSurfaceView == null) {
                return gl;
            }
            if (gLSurfaceView.mGLWrapper != null) {
                gl = gLSurfaceView.mGLWrapper.wrap(gl);
            }
            if ((gLSurfaceView.mDebugFlags & 3) == 0) {
                return gl;
            }
            int i = 0;
            LogWriter logWriter = null;
            if ((gLSurfaceView.mDebugFlags & 1) != 0) {
                i = 0 | 1;
            }
            if ((gLSurfaceView.mDebugFlags & 2) != 0) {
                logWriter = new LogWriter();
            }
            return GLDebugHelper.wrap(gl, i, logWriter);
        }

        public boolean createSurface() {
            String glGetString;
            SurfaceHolder holder;
            if (this.mEgl == null) {
                throw new RuntimeException("egl not initialized");
            } else if (this.mEglDisplay == null) {
                throw new RuntimeException("eglDisplay not initialized");
            } else if (this.mEglConfig == null) {
                throw new RuntimeException("mEglConfig not initialized");
            } else {
                destroySurfaceImp();
                this.mEglSurface = null;
                GLSurfaceView gLSurfaceView = (GLSurfaceView) this.mGLSurfaceViewWeakRef.get();
                if (!(gLSurfaceView == null || (holder = gLSurfaceView.getHolder()) == null || holder.getSurface() == null || !holder.getSurface().isValid())) {
                    this.mEglSurface = gLSurfaceView.mEGLWindowSurfaceFactory.createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig, holder);
                }
                if (this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE) {
                    if (this.mEgl.eglGetError() == 12299) {
                        Log.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                    }
                    return false;
                } else if (!this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
                    logEglErrorAsWarning("EGLHelper", "eglMakeCurrent", this.mEgl.eglGetError());
                    return false;
                } else {
                    if (!this.mHasRetriedCreatingSurface && (glGetString = GLES10.glGetString(7937)) != null && glGetString.equals("PowerVR SGX 540") && (gLSurfaceView.mEGLConfigChooser instanceof ComponentSizeChooser) && ((ComponentSizeChooser) gLSurfaceView.mEGLConfigChooser).getMinAlphaSize() > 0) {
                        int minAlphaSize = ((ComponentSizeChooser) gLSurfaceView.mEGLConfigChooser).getMinAlphaSize();
                        ((ComponentSizeChooser) gLSurfaceView.mEGLConfigChooser).setMinAlphaSize(0);
                        boolean z = false;
                        try {
                            z = recreateSurface();
                        } catch (Exception e) {
                        }
                        if (!z) {
                            this.mHasRetriedCreatingSurface = true;
                            ((ComponentSizeChooser) gLSurfaceView.mEGLConfigChooser).setMinAlphaSize(minAlphaSize);
                            return recreateSurface();
                        }
                    }
                    return true;
                }
            }
        }

        public void destroySurface() {
            destroySurfaceImp();
        }

        public void finish() {
            if (this.mEglContext != null) {
                GLSurfaceView gLSurfaceView = (GLSurfaceView) this.mGLSurfaceViewWeakRef.get();
                if (gLSurfaceView != null) {
                    gLSurfaceView.mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
                }
                this.mEglContext = null;
            }
            if (this.mEglDisplay != null) {
                this.mEgl.eglTerminate(this.mEglDisplay);
                this.mEglDisplay = null;
            }
        }

        public void start() {
            this.mEgl = (EGL10) EGLContext.getEGL();
            this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (this.mEglDisplay == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            }
            if (!this.mEgl.eglInitialize(this.mEglDisplay, new int[2])) {
                throw new RuntimeException("eglInitialize failed");
            }
            GLSurfaceView gLSurfaceView = (GLSurfaceView) this.mGLSurfaceViewWeakRef.get();
            if (gLSurfaceView == null) {
                this.mEglConfig = null;
                this.mEglContext = null;
            } else {
                this.mEglConfig = gLSurfaceView.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
                this.mEglContext = gLSurfaceView.mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
            }
            if (this.mEglContext == null || this.mEglContext == EGL10.EGL_NO_CONTEXT) {
                this.mEglContext = null;
                throwEglException("createContext");
            }
            this.mEglSurface = null;
        }

        public int swap() {
            if (this.mEgl == null || this.mEglDisplay == null || this.mEglSurface == null) {
                return 12289;
            }
            if (!this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface)) {
                return this.mEgl.eglGetError();
            }
            return 12288;
        }
    }

    static class GLThread extends Thread {
        private EglHelper mEglHelper;
        private ArrayList<Runnable> mEventQueue = new ArrayList<>();
        /* access modifiers changed from: private */
        public boolean mExited;
        private WeakReference<GLSurfaceView> mGLSurfaceViewWeakRef;
        private boolean mHasSurface;
        private boolean mHaveEglContext;
        private boolean mHaveEglSurface;
        private int mHeight = 0;
        private boolean mPaused;
        private boolean mRenderComplete;
        private int mRenderMode = 1;
        private boolean mRequestPaused;
        private boolean mRequestRender = true;
        private boolean mShouldExit;
        private boolean mShouldReleaseEglContext;
        private boolean mSizeChanged = true;
        private boolean mSurfaceIsBad;
        private boolean mWaitingForSurface;
        private int mWidth = 0;

        GLThread(WeakReference<GLSurfaceView> weakReference) {
            this.mGLSurfaceViewWeakRef = weakReference;
        }

        /* JADX WARNING: type inference failed for: r18v61, types: [javax.microedition.khronos.opengles.GL] */
        /* JADX WARNING: Code restructure failed: missing block: B:100:0x01ea, code lost:
            monitor-enter(r19);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:103:?, code lost:
            r21.mSurfaceIsBad = true;
            com.ansca.corona.graphics.opengl.GLSurfaceView.access$800().notifyAll();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:104:0x01fa, code lost:
            monitor-exit(r19);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:110:0x0200, code lost:
            r4 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:111:0x0201, code lost:
            if (r5 == false) goto L_0x021c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:112:0x0203, code lost:
            r8 = r21.mEglHelper.createGL();
            com.ansca.corona.graphics.opengl.GLSurfaceView.access$800().checkGLDriver(r8);
            r5 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:113:0x021c, code lost:
            if (r3 == false) goto L_0x0244;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:114:0x021e, code lost:
            r15 = (com.ansca.corona.graphics.opengl.GLSurfaceView) r21.mGLSurfaceViewWeakRef.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:115:0x022a, code lost:
            if (r15 == null) goto L_0x0243;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:116:0x022c, code lost:
            com.ansca.corona.graphics.opengl.GLSurfaceView.access$900(r15).onSurfaceCreated(r8, r21.mEglHelper.mEglConfig);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:117:0x0243, code lost:
            r3 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:118:0x0244, code lost:
            if (r12 == false) goto L_0x0260;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:119:0x0246, code lost:
            r15 = (com.ansca.corona.graphics.opengl.GLSurfaceView) r21.mGLSurfaceViewWeakRef.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:120:0x0252, code lost:
            if (r15 == null) goto L_0x025f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:121:0x0254, code lost:
            com.ansca.corona.graphics.opengl.GLSurfaceView.access$900(r15).onSurfaceChanged(r8, r16, r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:122:0x025f, code lost:
            r12 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:123:0x0260, code lost:
            r15 = (com.ansca.corona.graphics.opengl.GLSurfaceView) r21.mGLSurfaceViewWeakRef.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:124:0x026c, code lost:
            if (r15 == null) goto L_0x002f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:125:0x026e, code lost:
            if (r16 <= 0) goto L_0x002f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:126:0x0270, code lost:
            if (r9 <= 0) goto L_0x002f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:127:0x0272, code lost:
            com.ansca.corona.graphics.opengl.GLSurfaceView.access$900(r15).onDrawFrame(r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:128:0x027f, code lost:
            if (com.ansca.corona.graphics.opengl.GLSurfaceView.access$1000(r15) == false) goto L_0x002f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:129:0x0281, code lost:
            r13 = r21.mEglHelper.swap();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:130:0x028b, code lost:
            switch(r13) {
                case 12288: goto L_0x02b5;
                case 12302: goto L_0x02c1;
                default: goto L_0x028e;
            };
         */
        /* JADX WARNING: Code restructure failed: missing block: B:131:0x028e, code lost:
            com.ansca.corona.graphics.opengl.GLSurfaceView.EglHelper.logEglErrorAsWarning("GLThread", "eglSwapBuffers", r13);
            r19 = com.ansca.corona.graphics.opengl.GLSurfaceView.access$800();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:132:0x029d, code lost:
            monitor-enter(r19);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:135:?, code lost:
            r21.mSurfaceIsBad = true;
            com.ansca.corona.graphics.opengl.GLSurfaceView.access$800().notifyAll();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:136:0x02ad, code lost:
            monitor-exit(r19);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:139:?, code lost:
            android.util.Log.d("Corona", "Error in drawing our frame! Surface is bad!");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:140:0x02b5, code lost:
            if (r17 == false) goto L_0x02b8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:141:0x02b7, code lost:
            r6 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:142:0x02b8, code lost:
            com.ansca.corona.graphics.opengl.GLSurfaceView.access$1002(r15, false);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:143:0x02c1, code lost:
            r10 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:157:0x002f, code lost:
            continue;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:158:0x002f, code lost:
            continue;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:159:0x002f, code lost:
            continue;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:160:0x002f, code lost:
            continue;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x006f, code lost:
            if (r7 == null) goto L_0x01d8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
            r7.run();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0074, code lost:
            r7 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:94:0x01d8, code lost:
            if (r4 == false) goto L_0x0201;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:98:0x01e4, code lost:
            if (r21.mEglHelper.createSurface() != false) goto L_0x0200;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:99:0x01e6, code lost:
            r19 = com.ansca.corona.graphics.opengl.GLSurfaceView.access$800();
         */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void guardedRun() throws java.lang.InterruptedException {
            /*
                r21 = this;
                com.ansca.corona.graphics.opengl.GLSurfaceView$EglHelper r18 = new com.ansca.corona.graphics.opengl.GLSurfaceView$EglHelper
                r0 = r21
                java.lang.ref.WeakReference<com.ansca.corona.graphics.opengl.GLSurfaceView> r0 = r0.mGLSurfaceViewWeakRef
                r19 = r0
                r18.<init>(r19)
                r0 = r18
                r1 = r21
                r1.mEglHelper = r0
                r18 = 0
                r0 = r18
                r1 = r21
                r1.mHaveEglContext = r0
                r18 = 0
                r0 = r18
                r1 = r21
                r1.mHaveEglSurface = r0
                r8 = 0
                r3 = 0
                r4 = 0
                r5 = 0
                r10 = 0
                r12 = 0
                r17 = 0
                r6 = 0
                r2 = 0
                r16 = 0
                r9 = 0
                r7 = 0
            L_0x002f:
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r19 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x018b }
                monitor-enter(r19)     // Catch:{ all -> 0x018b }
            L_0x0034:
                r0 = r21
                boolean r0 = r0.mShouldExit     // Catch:{ all -> 0x0188 }
                r18 = r0
                if (r18 == 0) goto L_0x004d
                monitor-exit(r19)     // Catch:{ all -> 0x0188 }
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r19 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager
                monitor-enter(r19)
                r21.stopEglSurfaceLocked()     // Catch:{ all -> 0x004a }
                r21.stopEglContextLocked()     // Catch:{ all -> 0x004a }
                monitor-exit(r19)     // Catch:{ all -> 0x004a }
                return
            L_0x004a:
                r18 = move-exception
                monitor-exit(r19)     // Catch:{ all -> 0x004a }
                throw r18
            L_0x004d:
                r0 = r21
                java.util.ArrayList<java.lang.Runnable> r0 = r0.mEventQueue     // Catch:{ all -> 0x0188 }
                r18 = r0
                boolean r18 = r18.isEmpty()     // Catch:{ all -> 0x0188 }
                if (r18 != 0) goto L_0x0076
                r0 = r21
                java.util.ArrayList<java.lang.Runnable> r0 = r0.mEventQueue     // Catch:{ all -> 0x0188 }
                r18 = r0
                r20 = 0
                r0 = r18
                r1 = r20
                java.lang.Object r18 = r0.remove(r1)     // Catch:{ all -> 0x0188 }
                r0 = r18
                java.lang.Runnable r0 = (java.lang.Runnable) r0     // Catch:{ all -> 0x0188 }
                r7 = r0
            L_0x006e:
                monitor-exit(r19)     // Catch:{ all -> 0x0188 }
                if (r7 == 0) goto L_0x01d8
                r7.run()     // Catch:{ all -> 0x018b }
                r7 = 0
                goto L_0x002f
            L_0x0076:
                r11 = 0
                r0 = r21
                boolean r0 = r0.mPaused     // Catch:{ all -> 0x0188 }
                r18 = r0
                r0 = r21
                boolean r0 = r0.mRequestPaused     // Catch:{ all -> 0x0188 }
                r20 = r0
                r0 = r18
                r1 = r20
                if (r0 == r1) goto L_0x00a0
                r0 = r21
                boolean r11 = r0.mRequestPaused     // Catch:{ all -> 0x0188 }
                r0 = r21
                boolean r0 = r0.mRequestPaused     // Catch:{ all -> 0x0188 }
                r18 = r0
                r0 = r18
                r1 = r21
                r1.mPaused = r0     // Catch:{ all -> 0x0188 }
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r18 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x0188 }
                r18.notifyAll()     // Catch:{ all -> 0x0188 }
            L_0x00a0:
                r0 = r21
                boolean r0 = r0.mShouldReleaseEglContext     // Catch:{ all -> 0x0188 }
                r18 = r0
                if (r18 == 0) goto L_0x00b7
                r21.stopEglSurfaceLocked()     // Catch:{ all -> 0x0188 }
                r21.stopEglContextLocked()     // Catch:{ all -> 0x0188 }
                r18 = 0
                r0 = r18
                r1 = r21
                r1.mShouldReleaseEglContext = r0     // Catch:{ all -> 0x0188 }
                r2 = 1
            L_0x00b7:
                if (r10 == 0) goto L_0x00c0
                r21.stopEglSurfaceLocked()     // Catch:{ all -> 0x0188 }
                r21.stopEglContextLocked()     // Catch:{ all -> 0x0188 }
                r10 = 0
            L_0x00c0:
                r0 = r21
                boolean r0 = r0.mHasSurface     // Catch:{ all -> 0x0188 }
                r18 = r0
                if (r18 != 0) goto L_0x00f2
                r0 = r21
                boolean r0 = r0.mWaitingForSurface     // Catch:{ all -> 0x0188 }
                r18 = r0
                if (r18 != 0) goto L_0x00f2
                r0 = r21
                boolean r0 = r0.mHaveEglSurface     // Catch:{ all -> 0x0188 }
                r18 = r0
                if (r18 == 0) goto L_0x00db
                r21.stopEglSurfaceLocked()     // Catch:{ all -> 0x0188 }
            L_0x00db:
                r18 = 1
                r0 = r18
                r1 = r21
                r1.mWaitingForSurface = r0     // Catch:{ all -> 0x0188 }
                r18 = 0
                r0 = r18
                r1 = r21
                r1.mSurfaceIsBad = r0     // Catch:{ all -> 0x0188 }
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r18 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x0188 }
                r18.notifyAll()     // Catch:{ all -> 0x0188 }
            L_0x00f2:
                r0 = r21
                boolean r0 = r0.mHasSurface     // Catch:{ all -> 0x0188 }
                r18 = r0
                if (r18 == 0) goto L_0x0111
                r0 = r21
                boolean r0 = r0.mWaitingForSurface     // Catch:{ all -> 0x0188 }
                r18 = r0
                if (r18 == 0) goto L_0x0111
                r18 = 0
                r0 = r18
                r1 = r21
                r1.mWaitingForSurface = r0     // Catch:{ all -> 0x0188 }
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r18 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x0188 }
                r18.notifyAll()     // Catch:{ all -> 0x0188 }
            L_0x0111:
                if (r6 == 0) goto L_0x0125
                r17 = 0
                r6 = 0
                r18 = 1
                r0 = r18
                r1 = r21
                r1.mRenderComplete = r0     // Catch:{ all -> 0x0188 }
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r18 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x0188 }
                r18.notifyAll()     // Catch:{ all -> 0x0188 }
            L_0x0125:
                boolean r18 = r21.readyToDraw()     // Catch:{ all -> 0x0188 }
                if (r18 == 0) goto L_0x01cf
                r0 = r21
                boolean r0 = r0.mHaveEglContext     // Catch:{ all -> 0x0188 }
                r18 = r0
                if (r18 != 0) goto L_0x0136
                if (r2 == 0) goto L_0x0199
                r2 = 0
            L_0x0136:
                r0 = r21
                boolean r0 = r0.mHaveEglContext     // Catch:{ all -> 0x0188 }
                r18 = r0
                if (r18 == 0) goto L_0x0151
                r0 = r21
                boolean r0 = r0.mHaveEglSurface     // Catch:{ all -> 0x0188 }
                r18 = r0
                if (r18 != 0) goto L_0x0151
                r18 = 1
                r0 = r18
                r1 = r21
                r1.mHaveEglSurface = r0     // Catch:{ all -> 0x0188 }
                r4 = 1
                r5 = 1
                r12 = 1
            L_0x0151:
                r0 = r21
                boolean r0 = r0.mHaveEglSurface     // Catch:{ all -> 0x0188 }
                r18 = r0
                if (r18 == 0) goto L_0x01cf
                r0 = r21
                boolean r0 = r0.mSizeChanged     // Catch:{ all -> 0x0188 }
                r18 = r0
                if (r18 == 0) goto L_0x0177
                r12 = 1
                r0 = r21
                int r0 = r0.mWidth     // Catch:{ all -> 0x0188 }
                r16 = r0
                r0 = r21
                int r9 = r0.mHeight     // Catch:{ all -> 0x0188 }
                r17 = 1
                r4 = 1
                r18 = 0
                r0 = r18
                r1 = r21
                r1.mSizeChanged = r0     // Catch:{ all -> 0x0188 }
            L_0x0177:
                r18 = 0
                r0 = r18
                r1 = r21
                r1.mRequestRender = r0     // Catch:{ all -> 0x0188 }
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r18 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x0188 }
                r18.notifyAll()     // Catch:{ all -> 0x0188 }
                goto L_0x006e
            L_0x0188:
                r18 = move-exception
                monitor-exit(r19)     // Catch:{ all -> 0x0188 }
                throw r18     // Catch:{ all -> 0x018b }
            L_0x018b:
                r18 = move-exception
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r19 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager
                monitor-enter(r19)
                r21.stopEglSurfaceLocked()     // Catch:{ all -> 0x02c6 }
                r21.stopEglContextLocked()     // Catch:{ all -> 0x02c6 }
                monitor-exit(r19)     // Catch:{ all -> 0x02c6 }
                throw r18
            L_0x0199:
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r18 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x0188 }
                r0 = r18
                r1 = r21
                boolean r18 = r0.tryAcquireEglContextLocked(r1)     // Catch:{ all -> 0x0188 }
                if (r18 == 0) goto L_0x0136
                r0 = r21
                com.ansca.corona.graphics.opengl.GLSurfaceView$EglHelper r0 = r0.mEglHelper     // Catch:{ RuntimeException -> 0x01c2 }
                r18 = r0
                r18.start()     // Catch:{ RuntimeException -> 0x01c2 }
                r18 = 1
                r0 = r18
                r1 = r21
                r1.mHaveEglContext = r0     // Catch:{ all -> 0x0188 }
                r3 = 1
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r18 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x0188 }
                r18.notifyAll()     // Catch:{ all -> 0x0188 }
                goto L_0x0136
            L_0x01c2:
                r14 = move-exception
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r18 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x0188 }
                r0 = r18
                r1 = r21
                r0.releaseEglContextLocked(r1)     // Catch:{ all -> 0x0188 }
                throw r14     // Catch:{ all -> 0x0188 }
            L_0x01cf:
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r18 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x0188 }
                r18.wait()     // Catch:{ all -> 0x0188 }
                goto L_0x0034
            L_0x01d8:
                if (r4 == 0) goto L_0x0201
                r0 = r21
                com.ansca.corona.graphics.opengl.GLSurfaceView$EglHelper r0 = r0.mEglHelper     // Catch:{ all -> 0x018b }
                r18 = r0
                boolean r18 = r18.createSurface()     // Catch:{ all -> 0x018b }
                if (r18 != 0) goto L_0x0200
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r19 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x018b }
                monitor-enter(r19)     // Catch:{ all -> 0x018b }
                r18 = 1
                r0 = r18
                r1 = r21
                r1.mSurfaceIsBad = r0     // Catch:{ all -> 0x01fd }
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r18 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x01fd }
                r18.notifyAll()     // Catch:{ all -> 0x01fd }
                monitor-exit(r19)     // Catch:{ all -> 0x01fd }
                goto L_0x002f
            L_0x01fd:
                r18 = move-exception
                monitor-exit(r19)     // Catch:{ all -> 0x01fd }
                throw r18     // Catch:{ all -> 0x018b }
            L_0x0200:
                r4 = 0
            L_0x0201:
                if (r5 == 0) goto L_0x021c
                r0 = r21
                com.ansca.corona.graphics.opengl.GLSurfaceView$EglHelper r0 = r0.mEglHelper     // Catch:{ all -> 0x018b }
                r18 = r0
                javax.microedition.khronos.opengles.GL r18 = r18.createGL()     // Catch:{ all -> 0x018b }
                r0 = r18
                javax.microedition.khronos.opengles.GL10 r0 = (javax.microedition.khronos.opengles.GL10) r0     // Catch:{ all -> 0x018b }
                r8 = r0
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r18 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x018b }
                r0 = r18
                r0.checkGLDriver(r8)     // Catch:{ all -> 0x018b }
                r5 = 0
            L_0x021c:
                if (r3 == 0) goto L_0x0244
                r0 = r21
                java.lang.ref.WeakReference<com.ansca.corona.graphics.opengl.GLSurfaceView> r0 = r0.mGLSurfaceViewWeakRef     // Catch:{ all -> 0x018b }
                r18 = r0
                java.lang.Object r15 = r18.get()     // Catch:{ all -> 0x018b }
                com.ansca.corona.graphics.opengl.GLSurfaceView r15 = (com.ansca.corona.graphics.opengl.GLSurfaceView) r15     // Catch:{ all -> 0x018b }
                if (r15 == 0) goto L_0x0243
                com.ansca.corona.graphics.opengl.GLSurfaceView$Renderer r18 = r15.mRenderer     // Catch:{ all -> 0x018b }
                r0 = r21
                com.ansca.corona.graphics.opengl.GLSurfaceView$EglHelper r0 = r0.mEglHelper     // Catch:{ all -> 0x018b }
                r19 = r0
                r0 = r19
                javax.microedition.khronos.egl.EGLConfig r0 = r0.mEglConfig     // Catch:{ all -> 0x018b }
                r19 = r0
                r0 = r18
                r1 = r19
                r0.onSurfaceCreated(r8, r1)     // Catch:{ all -> 0x018b }
            L_0x0243:
                r3 = 0
            L_0x0244:
                if (r12 == 0) goto L_0x0260
                r0 = r21
                java.lang.ref.WeakReference<com.ansca.corona.graphics.opengl.GLSurfaceView> r0 = r0.mGLSurfaceViewWeakRef     // Catch:{ all -> 0x018b }
                r18 = r0
                java.lang.Object r15 = r18.get()     // Catch:{ all -> 0x018b }
                com.ansca.corona.graphics.opengl.GLSurfaceView r15 = (com.ansca.corona.graphics.opengl.GLSurfaceView) r15     // Catch:{ all -> 0x018b }
                if (r15 == 0) goto L_0x025f
                com.ansca.corona.graphics.opengl.GLSurfaceView$Renderer r18 = r15.mRenderer     // Catch:{ all -> 0x018b }
                r0 = r18
                r1 = r16
                r0.onSurfaceChanged(r8, r1, r9)     // Catch:{ all -> 0x018b }
            L_0x025f:
                r12 = 0
            L_0x0260:
                r0 = r21
                java.lang.ref.WeakReference<com.ansca.corona.graphics.opengl.GLSurfaceView> r0 = r0.mGLSurfaceViewWeakRef     // Catch:{ all -> 0x018b }
                r18 = r0
                java.lang.Object r15 = r18.get()     // Catch:{ all -> 0x018b }
                com.ansca.corona.graphics.opengl.GLSurfaceView r15 = (com.ansca.corona.graphics.opengl.GLSurfaceView) r15     // Catch:{ all -> 0x018b }
                if (r15 == 0) goto L_0x002f
                if (r16 <= 0) goto L_0x002f
                if (r9 <= 0) goto L_0x002f
                com.ansca.corona.graphics.opengl.GLSurfaceView$Renderer r18 = r15.mRenderer     // Catch:{ all -> 0x018b }
                r0 = r18
                r0.onDrawFrame(r8)     // Catch:{ all -> 0x018b }
                boolean r18 = r15.mNeedsSwap     // Catch:{ all -> 0x018b }
                if (r18 == 0) goto L_0x002f
                r0 = r21
                com.ansca.corona.graphics.opengl.GLSurfaceView$EglHelper r0 = r0.mEglHelper     // Catch:{ all -> 0x018b }
                r18 = r0
                int r13 = r18.swap()     // Catch:{ all -> 0x018b }
                switch(r13) {
                    case 12288: goto L_0x02b5;
                    case 12302: goto L_0x02c1;
                    default: goto L_0x028e;
                }     // Catch:{ all -> 0x018b }
            L_0x028e:
                java.lang.String r18 = "GLThread"
                java.lang.String r19 = "eglSwapBuffers"
                r0 = r18
                r1 = r19
                com.ansca.corona.graphics.opengl.GLSurfaceView.EglHelper.logEglErrorAsWarning(r0, r1, r13)     // Catch:{ all -> 0x018b }
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r19 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x018b }
                monitor-enter(r19)     // Catch:{ all -> 0x018b }
                r18 = 1
                r0 = r18
                r1 = r21
                r1.mSurfaceIsBad = r0     // Catch:{ all -> 0x02c3 }
                com.ansca.corona.graphics.opengl.GLSurfaceView$GLThreadManager r18 = com.ansca.corona.graphics.opengl.GLSurfaceView.sGLThreadManager     // Catch:{ all -> 0x02c3 }
                r18.notifyAll()     // Catch:{ all -> 0x02c3 }
                monitor-exit(r19)     // Catch:{ all -> 0x02c3 }
                java.lang.String r18 = "Corona"
                java.lang.String r19 = "Error in drawing our frame! Surface is bad!"
                android.util.Log.d(r18, r19)     // Catch:{ all -> 0x018b }
            L_0x02b5:
                if (r17 == 0) goto L_0x02b8
                r6 = 1
            L_0x02b8:
                r18 = 0
                r0 = r18
                boolean unused = r15.mNeedsSwap = r0     // Catch:{ all -> 0x018b }
                goto L_0x002f
            L_0x02c1:
                r10 = 1
                goto L_0x02b5
            L_0x02c3:
                r18 = move-exception
                monitor-exit(r19)     // Catch:{ all -> 0x02c3 }
                throw r18     // Catch:{ all -> 0x018b }
            L_0x02c6:
                r18 = move-exception
                monitor-exit(r19)     // Catch:{ all -> 0x02c6 }
                throw r18
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.graphics.opengl.GLSurfaceView.GLThread.guardedRun():void");
        }

        private boolean readyToDraw() {
            return !this.mPaused && this.mHasSurface && !this.mSurfaceIsBad && this.mWidth > 0 && this.mHeight > 0 && (this.mRequestRender || this.mRenderMode == 1);
        }

        private void stopEglContextLocked() {
            if (this.mHaveEglContext) {
                this.mEglHelper.finish();
                this.mHaveEglContext = false;
                GLSurfaceView.sGLThreadManager.releaseEglContextLocked(this);
            }
        }

        private void stopEglSurfaceLocked() {
            if (this.mHaveEglSurface) {
                this.mHaveEglSurface = false;
                this.mEglHelper.destroySurface();
            }
        }

        public boolean ableToDraw() {
            return this.mHaveEglContext && this.mHaveEglSurface && readyToDraw();
        }

        public int getRenderMode() {
            int i;
            synchronized (GLSurfaceView.sGLThreadManager) {
                i = this.mRenderMode;
            }
            return i;
        }

        public boolean hasGLSurface() {
            return this.mHasSurface;
        }

        public void onPause() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mRequestPaused = true;
                GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited && !this.mPaused) {
                    try {
                        GLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onResume() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mRequestPaused = false;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited && this.mPaused && !this.mRenderComplete) {
                    try {
                        GLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void onWindowResize(int i, int i2) {
            synchronized (GLSurfaceView.sGLThreadManager) {
                if (this.mHasSurface) {
                    this.mWidth = i;
                    this.mHeight = i2;
                    this.mSizeChanged = true;
                    this.mRequestRender = true;
                    this.mRenderComplete = false;
                    GLSurfaceView.sGLThreadManager.notifyAll();
                }
            }
        }

        public void queueEvent(Runnable runnable) {
            if (runnable == null) {
                throw new IllegalArgumentException("r must not be null");
            }
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mEventQueue.add(runnable);
                GLSurfaceView.sGLThreadManager.notifyAll();
            }
        }

        public void requestExitAndWait() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mShouldExit = true;
                GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mExited) {
                    try {
                        GLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void requestReleaseEglContextLocked() {
            this.mShouldReleaseEglContext = true;
            GLSurfaceView.sGLThreadManager.notifyAll();
        }

        public void requestRender() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mRequestRender = true;
                GLSurfaceView.sGLThreadManager.notifyAll();
            }
        }

        public void run() {
            setName("GLThread " + getId());
            try {
                guardedRun();
            } catch (InterruptedException e) {
            } finally {
                GLSurfaceView.sGLThreadManager.threadExiting(this);
            }
        }

        public void setRenderMode(int i) {
            if (i < 0 || i > 1) {
                throw new IllegalArgumentException("renderMode");
            }
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mRenderMode = i;
                GLSurfaceView.sGLThreadManager.notifyAll();
            }
        }

        public void surfaceCreated() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mHasSurface = true;
                GLSurfaceView.sGLThreadManager.notifyAll();
                while (this.mWaitingForSurface && !this.mExited) {
                    try {
                        GLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void surfaceDestroyed() {
            synchronized (GLSurfaceView.sGLThreadManager) {
                this.mHasSurface = false;
                GLSurfaceView.sGLThreadManager.notifyAll();
                while (!this.mWaitingForSurface && !this.mExited) {
                    try {
                        GLSurfaceView.sGLThreadManager.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    private static class GLThreadManager {
        private static String TAG = "GLThreadManager";
        private static final int kGLES_20 = 131072;
        private static final String kMSM7K_RENDERER_PREFIX = "Q3Dimension MSM7500 ";
        private GLThread mEglOwner;
        private boolean mGLESDriverCheckComplete;
        private int mGLESVersion;
        private boolean mGLESVersionCheckComplete;
        private boolean mLimitedGLESContexts;
        private boolean mMultipleGLESContextsAllowed;

        private GLThreadManager() {
        }

        private void checkGLESVersion() {
            if (!this.mGLESVersionCheckComplete) {
                this.mGLESVersion = 0;
                try {
                    this.mGLESVersion = ((Integer) Class.forName("android.os.SystemProperties").getMethod("getInt", new Class[]{String.class, Integer.TYPE}).invoke((Object) null, new Object[]{"ro.opengles.version", 0})).intValue();
                } catch (Exception e) {
                }
                if (this.mGLESVersion >= 131072) {
                    this.mMultipleGLESContextsAllowed = true;
                }
                this.mGLESVersionCheckComplete = true;
            }
        }

        public synchronized void checkGLDriver(GL10 gl10) {
            boolean z = true;
            synchronized (this) {
                if (!this.mGLESDriverCheckComplete) {
                    checkGLESVersion();
                    String glGetString = gl10.glGetString(7937);
                    if (this.mGLESVersion < 131072) {
                        this.mMultipleGLESContextsAllowed = !glGetString.startsWith(kMSM7K_RENDERER_PREFIX);
                        notifyAll();
                    }
                    if (this.mMultipleGLESContextsAllowed) {
                        z = false;
                    }
                    this.mLimitedGLESContexts = z;
                    this.mGLESDriverCheckComplete = true;
                }
            }
        }

        public void releaseEglContextLocked(GLThread gLThread) {
            if (this.mEglOwner == gLThread) {
                this.mEglOwner = null;
            }
            notifyAll();
        }

        public synchronized boolean shouldReleaseEGLContextWhenPausing() {
            return this.mLimitedGLESContexts;
        }

        public synchronized boolean shouldTerminateEGLWhenPausing() {
            checkGLESVersion();
            return !this.mMultipleGLESContextsAllowed;
        }

        public synchronized void threadExiting(GLThread gLThread) {
            boolean unused = gLThread.mExited = true;
            if (this.mEglOwner == gLThread) {
                this.mEglOwner = null;
            }
            notifyAll();
        }

        public boolean tryAcquireEglContextLocked(GLThread gLThread) {
            if (this.mEglOwner == gLThread || this.mEglOwner == null) {
                this.mEglOwner = gLThread;
                notifyAll();
                return true;
            }
            checkGLESVersion();
            if (this.mMultipleGLESContextsAllowed) {
                return true;
            }
            if (this.mEglOwner != null) {
                this.mEglOwner.requestReleaseEglContextLocked();
            }
            return false;
        }
    }

    public interface GLWrapper {
        GL wrap(GL gl);
    }

    static class LogWriter extends Writer {
        private StringBuilder mBuilder = new StringBuilder();

        LogWriter() {
        }

        private void flushBuilder() {
            if (this.mBuilder.length() > 0) {
                Log.v(GLSurfaceView.TAG, this.mBuilder.toString());
                this.mBuilder.delete(0, this.mBuilder.length());
            }
        }

        public void close() {
            flushBuilder();
        }

        public void flush() {
            flushBuilder();
        }

        public void write(char[] cArr, int i, int i2) {
            for (int i3 = 0; i3 < i2; i3++) {
                char c = cArr[i + i3];
                if (c == 10) {
                    flushBuilder();
                } else {
                    this.mBuilder.append(c);
                }
            }
        }
    }

    public interface Renderer {
        void onDrawFrame(GL10 gl10);

        void onSurfaceChanged(GL10 gl10, int i, int i2);

        void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig);
    }

    private class SimpleEGLConfigChooser extends ComponentSizeChooser {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public SimpleEGLConfigChooser(boolean z) {
            super(4, 4, 4, 0, z ? 16 : 0, 0);
            this.mRedSize = 5;
            this.mGreenSize = 6;
            this.mBlueSize = 5;
        }
    }

    public GLSurfaceView(Context context) {
        super(context);
        init();
    }

    public GLSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void checkRenderThreadState() {
        if (this.mGLThread != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }

    private void init() {
        getHolder().addCallback(this);
    }

    public boolean canRender() {
        return this.mGLThread != null;
    }

    public void clearNeedsSwap() {
        this.mNeedsSwap = false;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            if (this.mGLThread != null) {
                this.mGLThread.requestExitAndWait();
            }
        } finally {
            super.finalize();
        }
    }

    public int getDebugFlags() {
        return this.mDebugFlags;
    }

    public boolean getPreserveEGLContextOnPause() {
        return this.mPreserveEGLContextOnPause;
    }

    public int getRenderMode() {
        return this.mGLThread.getRenderMode();
    }

    /* access modifiers changed from: protected */
    public boolean hasGLSurface() {
        GLThread gLThread = this.mGLThread;
        if (gLThread == null) {
            return false;
        }
        return gLThread.hasGLSurface();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mDetached && this.mRenderer != null) {
            int i = 1;
            if (this.mGLThread != null) {
                i = this.mGLThread.getRenderMode();
            }
            this.mGLThread = new GLThread(this.mThisWeakRef);
            if (i != 1) {
                this.mGLThread.setRenderMode(i);
            }
            this.mGLThread.start();
        }
        this.mDetached = false;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (this.mGLThread != null) {
            this.mGLThread.requestExitAndWait();
        }
        this.mDetached = true;
        super.onDetachedFromWindow();
    }

    public void onPause() {
        this.mGLThread.onPause();
    }

    public void onResume() {
        this.mGLThread.onResume();
    }

    public void queueEvent(Runnable runnable) {
        this.mGLThread.queueEvent(runnable);
    }

    public void requestRender() {
        this.mGLThread.requestRender();
    }

    public void setDebugFlags(int i) {
        this.mDebugFlags = i;
    }

    public void setEGLConfigChooser(int i, int i2, int i3, int i4, int i5, int i6) {
        setEGLConfigChooser((EGLConfigChooser) new ComponentSizeChooser(i, i2, i3, i4, i5, i6));
    }

    public void setEGLConfigChooser(EGLConfigChooser eGLConfigChooser) {
        checkRenderThreadState();
        this.mEGLConfigChooser = eGLConfigChooser;
    }

    public void setEGLConfigChooser(boolean z) {
        setEGLConfigChooser((EGLConfigChooser) new SimpleEGLConfigChooser(z));
    }

    public void setEGLContextClientVersion(int i) {
        checkRenderThreadState();
        this.mEGLContextClientVersion = i;
    }

    public void setEGLContextFactory(EGLContextFactory eGLContextFactory) {
        checkRenderThreadState();
        this.mEGLContextFactory = eGLContextFactory;
    }

    public void setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory eGLWindowSurfaceFactory) {
        checkRenderThreadState();
        this.mEGLWindowSurfaceFactory = eGLWindowSurfaceFactory;
    }

    public void setGLWrapper(GLWrapper gLWrapper) {
        this.mGLWrapper = gLWrapper;
    }

    public void setNeedsSwap() {
        this.mNeedsSwap = true;
    }

    public void setPreserveEGLContextOnPause(boolean z) {
        this.mPreserveEGLContextOnPause = z;
    }

    public void setRenderMode(int i) {
        this.mGLThread.setRenderMode(i);
    }

    public void setRenderer(Renderer renderer) {
        checkRenderThreadState();
        if (this.mEGLConfigChooser == null) {
            this.mEGLConfigChooser = new SimpleEGLConfigChooser(true);
        }
        if (this.mEGLContextFactory == null) {
            this.mEGLContextFactory = new DefaultContextFactory();
        }
        if (this.mEGLWindowSurfaceFactory == null) {
            this.mEGLWindowSurfaceFactory = new DefaultWindowSurfaceFactory();
        }
        this.mRenderer = renderer;
        this.mGLThread = new GLThread(this.mThisWeakRef);
        this.mGLThread.start();
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (surfaceHolder != null && surfaceHolder.getSurface() != null && surfaceHolder.getSurface().isValid()) {
            this.mGLThread.surfaceCreated();
            this.mGLThread.onWindowResize(i2, i3);
        }
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.mGLThread.surfaceDestroyed();
    }
}
