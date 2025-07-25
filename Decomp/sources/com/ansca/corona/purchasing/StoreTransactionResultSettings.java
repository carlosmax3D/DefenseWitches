package com.ansca.corona.purchasing;

import java.util.Date;
import jp.stargarage.g2metrics.BuildConfig;

public class StoreTransactionResultSettings {
    private String fErrorMessage = BuildConfig.FLAVOR;
    private StoreTransactionErrorType fErrorType = StoreTransactionErrorType.UNKNOWN;
    private String fOriginalReceipt = BuildConfig.FLAVOR;
    private String fOriginalTransactionStringId = BuildConfig.FLAVOR;
    private Date fOriginalTransactionTime = null;
    private String fProductName = BuildConfig.FLAVOR;
    private String fReceipt = BuildConfig.FLAVOR;
    private String fSignature = BuildConfig.FLAVOR;
    private StoreTransactionState fState = StoreTransactionState.UNDEFINED;
    private String fTransactionStringId = BuildConfig.FLAVOR;
    private Date fTransactionTime = null;

    public String getErrorMessage() {
        return this.fErrorMessage;
    }

    public StoreTransactionErrorType getErrorType() {
        return this.fErrorType;
    }

    public String getOriginalReceipt() {
        return this.fOriginalReceipt;
    }

    public String getOriginalTransactionStringId() {
        return this.fOriginalTransactionStringId;
    }

    public Date getOriginalTransactionTime() {
        if (this.fOriginalTransactionTime == null) {
            return null;
        }
        return new Date(this.fOriginalTransactionTime.getTime());
    }

    public String getProductName() {
        return this.fProductName;
    }

    public String getReceipt() {
        return this.fReceipt;
    }

    public String getSignature() {
        return this.fSignature;
    }

    public StoreTransactionState getState() {
        return this.fState;
    }

    public String getTransactionStringId() {
        return this.fTransactionStringId;
    }

    public Date getTransactionTime() {
        if (this.fTransactionTime == null) {
            return null;
        }
        return new Date(this.fTransactionTime.getTime());
    }

    public boolean hasOriginalTransactionTime() {
        return this.fOriginalTransactionTime != null;
    }

    public boolean hasTransactionTime() {
        return this.fTransactionTime != null;
    }

    public void setErrorMessage(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fErrorMessage = str;
    }

    public void setErrorType(StoreTransactionErrorType storeTransactionErrorType) {
        if (storeTransactionErrorType == null) {
            storeTransactionErrorType = StoreTransactionErrorType.UNKNOWN;
        }
        this.fErrorType = storeTransactionErrorType;
    }

    public void setOriginalReceipt(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fOriginalReceipt = str;
    }

    public void setOriginalTransactionStringId(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fOriginalTransactionStringId = str;
    }

    public void setOriginalTransactionTime(Date date) {
        this.fOriginalTransactionTime = date;
    }

    public void setProductName(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fProductName = str;
    }

    public void setReceipt(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fReceipt = str;
    }

    public void setSignature(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fSignature = str;
    }

    public void setState(StoreTransactionState storeTransactionState) {
        if (storeTransactionState == null) {
            storeTransactionState = StoreTransactionState.UNDEFINED;
        }
        this.fState = storeTransactionState;
    }

    public void setTransactionStringId(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fTransactionStringId = str;
    }

    public void setTransactionTime(Date date) {
        this.fTransactionTime = date;
    }
}
