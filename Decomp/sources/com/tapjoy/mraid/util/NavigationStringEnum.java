package com.tapjoy.mraid.util;

import com.ansca.corona.purchasing.StoreName;
import com.tapjoy.TJAdUnitConstants;

public enum NavigationStringEnum {
    NONE(StoreName.NONE),
    CLOSE(TJAdUnitConstants.String.CLOSE),
    BACK("back"),
    FORWARD("forward"),
    REFRESH("refresh");
    
    private String text;

    private NavigationStringEnum(String str) {
        this.text = str;
    }

    public static NavigationStringEnum fromString(String str) {
        if (str != null) {
            for (NavigationStringEnum navigationStringEnum : values()) {
                if (str.equalsIgnoreCase(navigationStringEnum.text)) {
                    return navigationStringEnum;
                }
            }
        }
        return null;
    }

    public String getText() {
        return this.text;
    }
}
