package com.tapjoy.mraid.util;

import com.ansca.corona.purchasing.StoreName;

public enum TransitionStringEnum {
    DEFAULT(AdNetworkKey.DEFAULT),
    DISSOLVE("dissolve"),
    FADE("fade"),
    ROLL("roll"),
    SLIDE("slide"),
    ZOOM("zoom"),
    NONE(StoreName.NONE);
    
    private String text;

    private TransitionStringEnum(String str) {
        this.text = str;
    }

    public static TransitionStringEnum fromString(String str) {
        if (str != null) {
            for (TransitionStringEnum transitionStringEnum : values()) {
                if (str.equalsIgnoreCase(transitionStringEnum.text)) {
                    return transitionStringEnum;
                }
            }
        }
        return null;
    }

    public String getText() {
        return this.text;
    }
}
