package com.ansca.corona.graphics.opengl;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.provider.Settings;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import com.ansca.corona.Controller;
import com.ansca.corona.CoronaActivityInfo;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.JavaToNativeShim;
import com.ansca.corona.MessageBasedTimer;
import com.ansca.corona.TimeSpan;
import com.ansca.corona.WindowOrientation;
import com.ansca.corona.events.OrientationTask;
import com.ansca.corona.events.ResizeTask;
import com.ansca.corona.graphics.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CoronaGLSurfaceView extends GLSurfaceView {
    /* access modifiers changed from: private */
    public Activity fActivity;
    /* access modifiers changed from: private */
    public CoronaActivityInfo fActivityInfo;
    /* access modifiers changed from: private */
    public CoronaRuntime fCoronaRuntime;
    /* access modifiers changed from: private */
    public WindowOrientation fCurrentDeviceOrientation;
    /* access modifiers changed from: private */
    public WindowOrientation fCurrentWindowOrientation;
    private OrientationEventListener fOrientationListener;
    /* access modifiers changed from: private */
    public WindowOrientation fPreviousDeviceOrientation;
    /* access modifiers changed from: private */
    public WindowOrientation fPreviousWindowOrientation;
    private CoronaRenderer fRenderer;
    private MessageBasedTimer fWatchdogTimer;

    private static class CoronaRenderer implements GLSurfaceView.Renderer {
        private static boolean sFirstSurface = true;
        private boolean fCanRender;
        private CoronaRuntime fCoronaRuntime;
        private boolean fIsCoronaKit;
        private WindowOrientation fLastReceivedWindowOrientation;
        private int fLastViewHeight;
        private int fLastViewWidth;
        private CoronaGLSurfaceView fView;

        public CoronaRenderer(CoronaGLSurfaceView coronaGLSurfaceView, CoronaRuntime coronaRuntime, boolean z) {
            if (coronaGLSurfaceView == null) {
                throw new NullPointerException();
            }
            this.fView = coronaGLSurfaceView;
            this.fCanRender = false;
            this.fLastReceivedWindowOrientation = WindowOrientation.UNKNOWN;
            this.fLastViewWidth = -1;
            this.fLastViewHeight = -1;
            this.fIsCoronaKit = z;
            this.fCoronaRuntime = coronaRuntime;
        }

        public boolean canRender() {
            return this.fCanRender;
        }

        public void clearFirstSurface() {
            sFirstSurface = true;
        }

        public void onDrawFrame(GL10 gl10) {
            Controller.updateRuntimeState(this.fCoronaRuntime, this.fCanRender);
        }

        public void onSurfaceChanged(GL10 gl10, int i, int i2) {
            WindowOrientation access$600 = this.fView.fCurrentWindowOrientation;
            WindowOrientation access$700 = this.fView.fPreviousWindowOrientation;
            if (this.fIsCoronaKit || ((!access$600.isPortrait() || i <= i2) && (!access$600.isLandscape() || i >= i2))) {
                if (this.fIsCoronaKit) {
                    access$600 = WindowOrientation.PORTRAIT_UPRIGHT;
                }
                JavaToNativeShim.resize(this.fCoronaRuntime, this.fView.getContext(), i, i2, access$600, this.fIsCoronaKit);
                this.fCanRender = true;
                if (this.fLastReceivedWindowOrientation == WindowOrientation.UNKNOWN) {
                    this.fLastReceivedWindowOrientation = access$600;
                } else if (this.fLastReceivedWindowOrientation != access$600) {
                    this.fLastReceivedWindowOrientation = access$600;
                    if (this.fCoronaRuntime != null) {
                        this.fCoronaRuntime.getTaskDispatcher().send(new OrientationTask(access$600.toCoronaIntegerId(), access$700.toCoronaIntegerId()));
                    }
                }
                if (this.fLastViewWidth >= 0 && this.fLastViewHeight >= 0 && !((this.fLastViewWidth == i && this.fLastViewHeight == i2) || this.fCoronaRuntime == null)) {
                    this.fCoronaRuntime.getTaskDispatcher().send(new ResizeTask());
                }
                this.fLastViewWidth = i;
                this.fLastViewHeight = i2;
                return;
            }
            this.fCanRender = false;
        }

        public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
            if (!sFirstSurface) {
                this.fView.setNeedsSwap();
            }
            sFirstSurface = false;
            JavaToNativeShim.unloadResources(this.fCoronaRuntime);
        }
    }

    public CoronaGLSurfaceView(Context context, CoronaRuntime coronaRuntime, boolean z) {
        super(context);
        if (context == null) {
            throw new NullPointerException();
        }
        this.fCoronaRuntime = coronaRuntime;
        this.fCurrentWindowOrientation = WindowOrientation.fromCurrentWindowUsing(getContext());
        this.fPreviousWindowOrientation = this.fCurrentWindowOrientation;
        this.fWatchdogTimer = new MessageBasedTimer();
        this.fWatchdogTimer.setHandler(new Handler());
        this.fWatchdogTimer.setInterval(TimeSpan.fromSeconds(1));
        this.fWatchdogTimer.setListener(new MessageBasedTimer.Listener() {
            public void onTimerElapsed() {
                SurfaceHolder holder = CoronaGLSurfaceView.this.getHolder();
                if (holder != null && holder.getSurface() != null) {
                    if (!CoronaGLSurfaceView.this.hasGLSurface() || !CoronaGLSurfaceView.this.canRender()) {
                        CoronaGLSurfaceView.this.surfaceChanged(holder, 1, CoronaGLSurfaceView.this.getWidth(), CoronaGLSurfaceView.this.getHeight());
                    }
                }
            }
        });
        this.fCurrentDeviceOrientation = this.fCurrentWindowOrientation;
        this.fPreviousDeviceOrientation = WindowOrientation.UNKNOWN;
        this.fOrientationListener = new OrientationEventListener(getContext()) {
            public void onOrientationChanged(int i) {
                if (CoronaGLSurfaceView.this.fActivityInfo != null && i != -1 && CoronaGLSurfaceView.this.fCoronaRuntime.isRunning() && CoronaGLSurfaceView.this.canRender()) {
                    WindowOrientation fromDegrees = WindowOrientation.fromDegrees(CoronaGLSurfaceView.this.getContext(), (360 - i) % 360);
                    if (fromDegrees != CoronaGLSurfaceView.this.fCurrentDeviceOrientation || CoronaGLSurfaceView.this.fPreviousDeviceOrientation == WindowOrientation.UNKNOWN) {
                        WindowOrientation unused = CoronaGLSurfaceView.this.fPreviousDeviceOrientation = CoronaGLSurfaceView.this.fCurrentDeviceOrientation;
                        WindowOrientation unused2 = CoronaGLSurfaceView.this.fCurrentDeviceOrientation = fromDegrees;
                        if (CoronaGLSurfaceView.this.fActivityInfo.hasFixedOrientation() && Settings.System.getInt(CoronaGLSurfaceView.this.fActivity.getContentResolver(), "accelerometer_rotation", 0) != 0) {
                            CoronaGLSurfaceView.this.sendOrientationChangedEvent();
                        }
                    }
                }
            }
        };
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 0, 0);
        this.fRenderer = new CoronaRenderer(this, coronaRuntime, z);
        setRenderer(this.fRenderer);
        setRenderMode(0);
        getHolder().setFormat(1);
        setDebugFlags(3);
    }

    /* access modifiers changed from: private */
    public void sendOrientationChangedEvent() {
        if (this.fCoronaRuntime != null && this.fCoronaRuntime.isRunning()) {
            this.fCoronaRuntime.getTaskDispatcher().send(new OrientationTask(this.fCurrentDeviceOrientation.toCoronaIntegerId(), this.fPreviousDeviceOrientation.toCoronaIntegerId()));
        }
    }

    public boolean canRender() {
        return this.fRenderer != null && this.fRenderer.canRender() && super.canRender();
    }

    public void clearFirstSurface() {
        this.fRenderer.clearFirstSurface();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.fActivityInfo != null && !this.fOrientationListener.canDetectOrientation()) {
            WindowOrientation windowOrientation = configuration.orientation == 2 ? WindowOrientation.LANDSCAPE_RIGHT : WindowOrientation.PORTRAIT_UPRIGHT;
            if (windowOrientation != this.fCurrentDeviceOrientation || this.fPreviousDeviceOrientation == WindowOrientation.UNKNOWN) {
                this.fPreviousDeviceOrientation = this.fCurrentDeviceOrientation;
                this.fCurrentDeviceOrientation = windowOrientation;
                if (this.fActivityInfo.hasFixedOrientation() && Settings.System.getInt(this.fActivity.getContentResolver(), "accelerometer_rotation", 0) != 0) {
                    sendOrientationChangedEvent();
                }
            }
        }
    }

    public void onResumeCoronaRuntime() {
        this.fWatchdogTimer.start();
        if (this.fOrientationListener.canDetectOrientation()) {
            this.fOrientationListener.enable();
        }
    }

    public void onSuspendCoronaRuntime() {
        this.fWatchdogTimer.stop();
        if (this.fOrientationListener.canDetectOrientation()) {
            this.fOrientationListener.disable();
        }
    }

    public void setActivity(Activity activity) {
        this.fActivity = activity;
        this.fActivityInfo = new CoronaActivityInfo(this.fActivity);
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (surfaceHolder != null && surfaceHolder.getSurface() != null && surfaceHolder.getSurface().isValid()) {
            WindowOrientation fromCurrentWindowUsing = WindowOrientation.fromCurrentWindowUsing(getContext());
            if ((this.fActivity == null ? true : fromCurrentWindowUsing.isSupportedBy(this.fActivity)) && this.fCurrentWindowOrientation != fromCurrentWindowUsing) {
                this.fPreviousWindowOrientation = this.fCurrentWindowOrientation;
                this.fCurrentWindowOrientation = fromCurrentWindowUsing;
            }
            super.surfaceChanged(surfaceHolder, i, i2, i3);
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.fWatchdogTimer.stop();
        super.surfaceDestroyed(surfaceHolder);
    }
}
