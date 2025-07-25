package com.ansca.corona.purchasing;

import java.lang.reflect.Field;

public class StoreTransactionState {
    public static final StoreTransactionState CANCELED = new StoreTransactionState(5);
    public static final StoreTransactionState FAILED = new StoreTransactionState(3);
    public static final StoreTransactionState PURCHASED = new StoreTransactionState(2);
    public static final StoreTransactionState PURCHASING = new StoreTransactionState(1);
    public static final StoreTransactionState REFUNDED = new StoreTransactionState(6);
    public static final StoreTransactionState RESTORED = new StoreTransactionState(4);
    public static final StoreTransactionState UNDEFINED = new StoreTransactionState(0);
    private int fStateId;

    private StoreTransactionState(int i) {
        this.fStateId = i;
    }

    public static StoreTransactionState fromValue(int i) {
        try {
            for (Field field : StoreTransactionState.class.getDeclaredFields()) {
                if (field.getType().equals(StoreTransactionState.class)) {
                    StoreTransactionState storeTransactionState = (StoreTransactionState) field.get((Object) null);
                    if (storeTransactionState.toValue() == i) {
                        return storeTransactionState;
                    }
                }
            }
        } catch (Exception e) {
        }
        return UNDEFINED;
    }

    public int toValue() {
        return this.fStateId;
    }
}
