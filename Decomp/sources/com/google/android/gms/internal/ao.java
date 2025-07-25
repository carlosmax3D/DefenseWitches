package com.google.android.gms.internal;

import com.facebook.appevents.AppEventsConstants;
import java.util.Map;

public final class ao implements an {
    private static boolean a(Map<String, String> map) {
        return AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(map.get("custom_close"));
    }

    private static int b(Map<String, String> map) {
        String str = map.get("o");
        if (str != null) {
            if ("p".equalsIgnoreCase(str)) {
                return co.av();
            }
            if ("l".equalsIgnoreCase(str)) {
                return co.au();
            }
        }
        return -1;
    }

    public void a(cw cwVar, Map<String, String> map) {
        String str = map.get("a");
        if (str == null) {
            ct.v("Action missing from an open GMSG.");
            return;
        }
        cx aC = cwVar.aC();
        if ("expand".equalsIgnoreCase(str)) {
            if (cwVar.aF()) {
                ct.v("Cannot expand WebView that is already expanded.");
            } else {
                aC.a(a(map), b(map));
            }
        } else if ("webapp".equalsIgnoreCase(str)) {
            String str2 = map.get("u");
            if (str2 != null) {
                aC.a(a(map), b(map), str2);
            } else {
                aC.a(a(map), b(map), map.get("html"), map.get("baseurl"));
            }
        } else {
            aC.a(new bj(map.get("i"), map.get("u"), map.get("m"), map.get("p"), map.get("c"), map.get("f"), map.get("e")));
        }
    }
}
