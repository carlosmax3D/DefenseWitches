package com.ansca.corona;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

class CameraView extends SurfaceView implements SurfaceHolder.Callback {
    private Camera fCamera = null;
    private int fCameraOrientationInDegrees = 0;
    /* access modifiers changed from: private */
    public int fDeviceOrientationInDegrees = 0;
    private SurfaceHolder fHolder = null;
    private boolean fIsCameraOrientationKnown = false;
    private boolean fIsUsingFrontFacingCamera = false;
    private OrientationEventListener fOrientationListener;
    private Camera.PictureCallback fTakePictureListener = null;
    private int fUsingCameraId = 0;

    CameraView(Context context) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        holder.setType(3);
        this.fOrientationListener = new OrientationEventListener(context) {
            public void onOrientationChanged(int i) {
                if (i != -1) {
                    int i2 = (360 - i) % 360;
                    if (i2 >= 45 && i2 < 135) {
                        int unused = CameraView.this.fDeviceOrientationInDegrees = 90;
                    } else if (i2 >= 135 && i2 < 225) {
                        int unused2 = CameraView.this.fDeviceOrientationInDegrees = 180;
                    } else if (i2 < 225 || i2 >= 315) {
                        int unused3 = CameraView.this.fDeviceOrientationInDegrees = 0;
                    } else {
                        int unused4 = CameraView.this.fDeviceOrientationInDegrees = 270;
                    }
                }
            }
        };
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> list, int i, int i2) {
        double d = ((double) i) / ((double) i2);
        if (list == null) {
            return null;
        }
        Camera.Size size = null;
        double d2 = Double.MAX_VALUE;
        int i3 = i2;
        for (Camera.Size next : list) {
            if (Math.abs((((double) next.width) / ((double) next.height)) - d) <= 0.05d && ((double) Math.abs(next.height - i3)) < d2) {
                size = next;
                d2 = (double) Math.abs(next.height - i3);
            }
        }
        if (size != null) {
            return size;
        }
        double d3 = Double.MAX_VALUE;
        for (Camera.Size next2 : list) {
            if (((double) Math.abs(next2.height - i3)) < d3) {
                size = next2;
                d3 = (double) Math.abs(next2.height - i3);
            }
        }
        return size;
    }

    private void updateCameraInformation() {
        boolean z = true;
        this.fIsUsingFrontFacingCamera = false;
        this.fIsCameraOrientationKnown = false;
        this.fCameraOrientationInDegrees = 0;
        try {
            Class<?> cls = Class.forName("android.hardware.Camera$CameraInfo");
            Object newInstance = cls.newInstance();
            Camera.class.getMethod("getCameraInfo", new Class[]{Integer.TYPE, cls}).invoke((Object) null, new Object[]{Integer.valueOf(this.fUsingCameraId), newInstance});
            Field field = cls.getField("facing");
            Field field2 = cls.getField("orientation");
            if (field.getInt(newInstance) != 1) {
                z = false;
            }
            this.fIsUsingFrontFacingCamera = z;
            this.fCameraOrientationInDegrees = field2.getInt(newInstance);
            this.fIsCameraOrientationKnown = true;
        } catch (Exception e) {
        }
    }

    private void updateCameraOrientation() {
        int i;
        if (this.fCamera != null) {
            Display defaultDisplay = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay();
            switch (defaultDisplay.getRotation()) {
                case 1:
                    i = 90;
                    break;
                case 2:
                    i = 180;
                    break;
                case 3:
                    i = 270;
                    break;
                default:
                    i = 0;
                    break;
            }
            boolean z = defaultDisplay.getWidth() < defaultDisplay.getHeight() && (i == 0 || i == 180);
            int i2 = this.fIsCameraOrientationKnown ? this.fCameraOrientationInDegrees : z ? 90 : 0;
            this.fCamera.setDisplayOrientation(this.fIsUsingFrontFacingCamera ? (360 - ((i2 + i) % 360)) % 360 : ((i2 + 360) - i) % 360);
            Camera.Parameters parameters = this.fCamera.getParameters();
            int i3 = i2 - this.fDeviceOrientationInDegrees;
            if (i3 < 0) {
                i3 += 360;
            }
            if (this.fIsUsingFrontFacingCamera && ((z && i3 % 180 == 0) || (!z && i3 % 180 == 90))) {
                i3 = (i3 + 180) % 360;
            }
            parameters.setRotation(i3);
            this.fCamera.setParameters(parameters);
        }
    }

    /* access modifiers changed from: protected */
    public void disableCamera() {
        if (!isCameraDisabled()) {
            try {
                this.fCamera.stopPreview();
                this.fCamera.setPreviewDisplay((SurfaceHolder) null);
            } catch (Exception e) {
            }
            this.fCamera.release();
            this.fCamera = null;
            if (this.fOrientationListener.canDetectOrientation()) {
                this.fOrientationListener.disable();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void enableCamera() {
        if (!isCameraEnabled() && this.fHolder != null && this.fHolder.getSurface() != null) {
            Method method = null;
            Class<Camera> cls = Camera.class;
            try {
                method = cls.getMethod("open", new Class[]{Integer.TYPE});
            } catch (Exception e) {
            }
            if (method != null) {
                try {
                    this.fCamera = (Camera) method.invoke((Object) null, new Object[]{Integer.valueOf(this.fUsingCameraId)});
                } catch (Exception e2) {
                    if (this.fCamera != null) {
                        this.fCamera.release();
                        this.fCamera = null;
                    }
                    Log.v("Corona", "Failed to enable camera.");
                    return;
                }
            } else {
                this.fCamera = Camera.open();
            }
            updateCameraInformation();
            updateCameraOrientation();
            this.fCamera.setPreviewDisplay(this.fHolder);
            this.fCamera.startPreview();
            if (this.fOrientationListener.canDetectOrientation()) {
                this.fOrientationListener.enable();
            }
        }
    }

    public int getSelectedCameraIndex() {
        return this.fUsingCameraId;
    }

    public boolean isCameraDisabled() {
        return !isCameraEnabled();
    }

    public boolean isCameraEnabled() {
        return this.fCamera != null;
    }

    public void selectCameraByIndex(int i) {
        if (i != this.fUsingCameraId) {
            this.fUsingCameraId = i;
            if (isCameraEnabled()) {
                disableCamera();
                enableCamera();
            }
        }
    }

    public void setTakePictureListener(Camera.PictureCallback pictureCallback) {
        this.fTakePictureListener = pictureCallback;
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (isCameraEnabled()) {
            this.fCamera.stopPreview();
            updateCameraOrientation();
            this.fCamera.startPreview();
        }
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.fHolder = surfaceHolder;
        enableCamera();
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        disableCamera();
    }

    public void takePicture() {
        if (!isCameraDisabled()) {
            this.fCamera.stopPreview();
            updateCameraOrientation();
            this.fCamera.startPreview();
            this.fCamera.takePicture((Camera.ShutterCallback) null, (Camera.PictureCallback) null, this.fTakePictureListener);
        }
    }
}
