package com.tapjoy;

public class TJEventRequest {
    public static final int TYPE_CURRENCY = 3;
    public static final int TYPE_IN_APP_PURCHASE = 1;
    public static final int TYPE_NAVIGATION = 4;
    public static final int TYPE_VIRTUAL_GOOD = 2;
    public TJEventRequestCallback callback;
    public String identifier;
    public int quantity;
    public int type;
}
