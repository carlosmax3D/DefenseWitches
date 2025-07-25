package com.ansca.corona;

import android.content.Context;
import android.hardware.Camera;

public class CameraServices {
    private CameraServices() {
    }

    public static int getCameraCount() {
        try {
            if (hasCamera()) {
                return ((Integer) Camera.class.getMethod("getNumberOfCameras", new Class[0]).invoke((Object) null, new Object[0])).intValue();
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean hasCamera() {
        return hasRearCamera() || hasFrontFacingCamera();
    }

    public static boolean hasFrontFacingCamera() {
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext != null) {
            return applicationContext.getPackageManager().hasSystemFeature("android.hardware.camera.front");
        }
        return false;
    }

    public static boolean hasPermission() {
        try {
            Context applicationContext = CoronaEnvironment.getApplicationContext();
            return applicationContext != null && applicationContext.checkCallingOrSelfPermission("android.permission.CAMERA") == 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasRearCamera() {
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext != null) {
            return applicationContext.getPackageManager().hasSystemFeature("android.hardware.camera");
        }
        return false;
    }
}
