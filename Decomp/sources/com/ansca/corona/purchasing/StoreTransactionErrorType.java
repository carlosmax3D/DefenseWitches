package com.ansca.corona.purchasing;

import java.lang.reflect.Field;

public class StoreTransactionErrorType {
    public static final StoreTransactionErrorType CLIENT_INVALID = new StoreTransactionErrorType(2);
    public static final StoreTransactionErrorType NONE = new StoreTransactionErrorType(0);
    public static final StoreTransactionErrorType PAYMENT_CANCELED = new StoreTransactionErrorType(3);
    public static final StoreTransactionErrorType PAYMENT_INVALID = new StoreTransactionErrorType(4);
    public static final StoreTransactionErrorType PAYMENT_NOT_ALLOWED = new StoreTransactionErrorType(5);
    public static final StoreTransactionErrorType UNKNOWN = new StoreTransactionErrorType(1);
    private int fNumericId;

    private StoreTransactionErrorType(int i) {
        this.fNumericId = i;
    }

    public static StoreTransactionErrorType fromValue(int i) {
        try {
            for (Field field : StoreTransactionErrorType.class.getDeclaredFields()) {
                if (field.getType().equals(StoreTransactionErrorType.class)) {
                    StoreTransactionErrorType storeTransactionErrorType = (StoreTransactionErrorType) field.get((Object) null);
                    if (storeTransactionErrorType.toValue() == i) {
                        return storeTransactionErrorType;
                    }
                }
            }
        } catch (Exception e) {
        }
        return UNKNOWN;
    }

    public int toValue() {
        return this.fNumericId;
    }
}
