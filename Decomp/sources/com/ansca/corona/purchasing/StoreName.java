package com.ansca.corona.purchasing;

public class StoreName {
    public static final String AMAZON = "amazon";
    public static final String GOOGLE = "google";
    public static final String NONE = "none";
    public static final String NOOK = "nook";
    public static final String SAMSUNG = "samsung";
    public static final String UNKNOWN = "unknown";

    private StoreName() {
    }

    public static String fromPackageName(String str) {
        return (str == null || str.length() <= 0) ? NONE : (str.equals("com.android.vending") || str.equals(StoreServices.GOOGLE_MARKETPLACE_APP_PACKAGE_NAME_2)) ? GOOGLE : str.equals(StoreServices.AMAZON_MARKETPLACE_APP_PACKAGE_NAME) ? AMAZON : str.equals(StoreServices.SAMSUNG_MARKETPLACE_APP_PACKAGE_NAME) ? SAMSUNG : "unknown";
    }

    public static boolean isNotValid(String str) {
        return !isValid(str);
    }

    public static boolean isValid(String str) {
        return str != null && (str.equals(GOOGLE) || str.equals(AMAZON) || str.equals(NOOK) || str.equals(SAMSUNG));
    }
}
