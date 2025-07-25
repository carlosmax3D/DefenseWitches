package com.google.android.gms.internal;

import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.tapjoy.TJAdUnitConstants;
import java.util.Map;

public final class ap implements an {
    private static int a(DisplayMetrics displayMetrics, Map<String, String> map, String str, int i) {
        String str2 = map.get(str);
        if (str2 == null) {
            return i;
        }
        try {
            return cs.a(displayMetrics, Integer.parseInt(str2));
        } catch (NumberFormatException e) {
            ct.v("Could not parse " + str + " in a video GMSG: " + str2);
            return i;
        }
    }

    public void a(cw cwVar, Map<String, String> map) {
        String str = map.get("action");
        if (str == null) {
            ct.v("Action missing from video GMSG.");
            return;
        }
        bk aB = cwVar.aB();
        if (aB == null) {
            ct.v("Could not get ad overlay for a video GMSG.");
            return;
        }
        boolean equalsIgnoreCase = "new".equalsIgnoreCase(str);
        boolean equalsIgnoreCase2 = "position".equalsIgnoreCase(str);
        if (equalsIgnoreCase || equalsIgnoreCase2) {
            DisplayMetrics displayMetrics = cwVar.getContext().getResources().getDisplayMetrics();
            int a = a(displayMetrics, map, "x", 0);
            int a2 = a(displayMetrics, map, "y", 0);
            int a3 = a(displayMetrics, map, "w", -1);
            int a4 = a(displayMetrics, map, "h", -1);
            if (!equalsIgnoreCase || aB.W() != null) {
                aB.b(a, a2, a3, a4);
            } else {
                aB.c(a, a2, a3, a4);
            }
        } else {
            bo W = aB.W();
            if (W == null) {
                bo.a(cwVar, "no_video_view", (String) null);
            } else if ("click".equalsIgnoreCase(str)) {
                DisplayMetrics displayMetrics2 = cwVar.getContext().getResources().getDisplayMetrics();
                int a5 = a(displayMetrics2, map, "x", 0);
                int a6 = a(displayMetrics2, map, "y", 0);
                long uptimeMillis = SystemClock.uptimeMillis();
                MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, (float) a5, (float) a6, 0);
                W.b(obtain);
                obtain.recycle();
            } else if ("controls".equalsIgnoreCase(str)) {
                String str2 = map.get(TJAdUnitConstants.String.ENABLED);
                if (str2 == null) {
                    ct.v("Enabled parameter missing from controls video GMSG.");
                } else {
                    W.i(Boolean.parseBoolean(str2));
                }
            } else if ("currentTime".equalsIgnoreCase(str)) {
                String str3 = map.get("time");
                if (str3 == null) {
                    ct.v("Time parameter missing from currentTime video GMSG.");
                    return;
                }
                try {
                    W.seekTo((int) (Float.parseFloat(str3) * 1000.0f));
                } catch (NumberFormatException e) {
                    ct.v("Could not parse time parameter from currentTime video GMSG: " + str3);
                }
            } else if ("hide".equalsIgnoreCase(str)) {
                W.setVisibility(4);
            } else if ("load".equalsIgnoreCase(str)) {
                W.af();
            } else if ("pause".equalsIgnoreCase(str)) {
                W.pause();
            } else if ("play".equalsIgnoreCase(str)) {
                W.play();
            } else if ("show".equalsIgnoreCase(str)) {
                W.setVisibility(0);
            } else if ("src".equalsIgnoreCase(str)) {
                W.n(map.get("src"));
            } else {
                ct.v("Unknown video action: " + str);
            }
        }
    }
}
