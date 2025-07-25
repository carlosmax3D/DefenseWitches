package com.ansca.corona.listeners;

public interface CoronaStoreApiListener {
    void storeFinishTransaction(String str);

    void storeInit(String str);

    void storePurchase(String str);

    void storeRestore();
}
