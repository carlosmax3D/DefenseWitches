package com.google.android.gms.internal;

public final class gf {
    public static String aG(int i) {
        switch (i) {
            case 0:
                return "TURN_STATUS_INVITED";
            case 1:
                return "TURN_STATUS_MY_TURN";
            case 2:
                return "TURN_STATUS_THEIR_TURN";
            case 3:
                return "TURN_STATUS_COMPLETE";
            default:
                fn.d("MatchTurnStatus", "Unknown match turn status: " + i);
                return "TURN_STATUS_UNKNOWN";
        }
    }
}
