package com.ansca.corona;

public class CoronaStatusBarSettings {
    public static final CoronaStatusBarSettings CORONA_STATUSBAR_MODE_DARK = new CoronaStatusBarSettings(3);
    public static final CoronaStatusBarSettings CORONA_STATUSBAR_MODE_DEFAULT = new CoronaStatusBarSettings(1);
    public static final CoronaStatusBarSettings CORONA_STATUSBAR_MODE_HIDDEN = new CoronaStatusBarSettings(0);
    public static final CoronaStatusBarSettings CORONA_STATUSBAR_MODE_TRANSLUCENT = new CoronaStatusBarSettings(2);
    private int fValue;

    private CoronaStatusBarSettings(int i) {
        this.fValue = i;
    }

    public static CoronaStatusBarSettings fromCoronaIntId(int i) {
        switch (i) {
            case 0:
                return CORONA_STATUSBAR_MODE_HIDDEN;
            case 1:
                return CORONA_STATUSBAR_MODE_DEFAULT;
            case 2:
                return CORONA_STATUSBAR_MODE_TRANSLUCENT;
            case 3:
                return CORONA_STATUSBAR_MODE_DARK;
            default:
                return null;
        }
    }

    private int toIntegerValue() {
        return this.fValue;
    }

    public int toCoronaIntId() {
        return this.fValue;
    }
}
