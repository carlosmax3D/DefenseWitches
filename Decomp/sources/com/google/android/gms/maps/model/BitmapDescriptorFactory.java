package com.google.android.gms.maps.model;

import android.graphics.Bitmap;
import android.os.RemoteException;
import com.google.android.gms.internal.eg;
import com.google.android.gms.maps.model.internal.a;

public final class BitmapDescriptorFactory {
    private static a Cl = null;
    public static final float HUE_AZURE = 210.0f;
    public static final float HUE_BLUE = 240.0f;
    public static final float HUE_CYAN = 180.0f;
    public static final float HUE_GREEN = 120.0f;
    public static final float HUE_MAGENTA = 300.0f;
    public static final float HUE_ORANGE = 30.0f;
    public static final float HUE_RED = 0.0f;
    public static final float HUE_ROSE = 330.0f;
    public static final float HUE_VIOLET = 270.0f;
    public static final float HUE_YELLOW = 60.0f;

    private BitmapDescriptorFactory() {
    }

    public static void a(a aVar) {
        if (Cl == null) {
            Cl = (a) eg.f(aVar);
        }
    }

    public static BitmapDescriptor defaultMarker() {
        try {
            return new BitmapDescriptor(eE().eJ());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public static BitmapDescriptor defaultMarker(float f) {
        try {
            return new BitmapDescriptor(eE().c(f));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    private static a eE() {
        return (a) eg.b(Cl, (Object) "IBitmapDescriptorFactory is not initialized");
    }

    public static BitmapDescriptor fromAsset(String str) {
        try {
            return new BitmapDescriptor(eE().at(str));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public static BitmapDescriptor fromBitmap(Bitmap bitmap) {
        try {
            return new BitmapDescriptor(eE().a(bitmap));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public static BitmapDescriptor fromFile(String str) {
        try {
            return new BitmapDescriptor(eE().au(str));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public static BitmapDescriptor fromPath(String str) {
        try {
            return new BitmapDescriptor(eE().av(str));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public static BitmapDescriptor fromResource(int i) {
        try {
            return new BitmapDescriptor(eE().bh(i));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
