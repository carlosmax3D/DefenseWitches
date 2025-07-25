package com.flurry.sdk;

import android.app.Activity;

public interface du {

    public enum a {
        kCreated,
        kDestroyed,
        kPaused,
        kResumed,
        kStarted,
        kStopped,
        kSaveState
    }

    void a(Activity activity, a aVar);
}
