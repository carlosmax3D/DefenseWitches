package com.google.android.gms.internal;

public final class gc {
    public static String aG(int i) {
        switch (i) {
            case 0:
                return "PUBLIC";
            case 1:
                return "SOCIAL";
            default:
                throw new IllegalArgumentException("Unknown leaderboard collection: " + i);
        }
    }
}
