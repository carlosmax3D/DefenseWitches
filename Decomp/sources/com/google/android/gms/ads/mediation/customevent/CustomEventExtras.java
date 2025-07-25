package com.google.android.gms.ads.mediation.customevent;

import com.google.ads.mediation.NetworkExtras;
import java.util.HashMap;

public final class CustomEventExtras implements NetworkExtras {
    private final HashMap<String, Object> ji = new HashMap<>();

    public Object getExtra(String str) {
        return this.ji.get(str);
    }

    public void setExtra(String str, Object obj) {
        this.ji.put(str, obj);
    }
}
