package com.google.android.gms.internal;

import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.internal.ae;

public final class z extends ae.a {
    private final AppEventListener eI;

    public z(AppEventListener appEventListener) {
        this.eI = appEventListener;
    }

    public void onAppEvent(String str, String str2) {
        this.eI.onAppEvent(str, str2);
    }
}
