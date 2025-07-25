package com.google.android.gms.internal;

import java.util.Map;

public final class ak implements an {
    private final al fm;

    public ak(al alVar) {
        this.fm = alVar;
    }

    public void a(cw cwVar, Map<String, String> map) {
        String str = map.get("name");
        if (str == null) {
            ct.v("App event with no name parameter.");
        } else {
            this.fm.onAppEvent(str, map.get("info"));
        }
    }
}
