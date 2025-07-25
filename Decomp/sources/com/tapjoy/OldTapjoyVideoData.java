package com.tapjoy;

import java.io.Serializable;

public class OldTapjoyVideoData implements Serializable {
    private static final long serialVersionUID = 2169714662587422874L;
    private String clickURL;
    private String currencyAmount;
    private String currencyName;
    private String dataLocation;
    private String iconURL;
    private String offerID;
    private String videoAdName;
    private String videoURL;
    private String webviewURL;

    public void addButton(String str, String str2) {
    }

    public String getClickURL() {
        return this.clickURL;
    }

    public String getCurrencyAmount() {
        return this.currencyAmount;
    }

    public String getCurrencyName() {
        return this.currencyName;
    }

    public String getIconURL() {
        return this.iconURL;
    }

    public String getLocalFilePath() {
        return this.dataLocation;
    }

    public String getOfferId() {
        return this.offerID;
    }

    public String getVideoAdName() {
        return this.videoAdName;
    }

    public String getVideoURL() {
        return this.videoURL;
    }

    public String getWebviewURL() {
        return this.webviewURL;
    }

    public void setClickURL(String str) {
        this.clickURL = str;
    }

    public void setCurrencyAmount(String str) {
        this.currencyAmount = str;
    }

    public void setCurrencyName(String str) {
        this.currencyName = str;
    }

    public void setIconURL(String str) {
        this.iconURL = str;
    }

    public void setLocalFilePath(String str) {
        this.dataLocation = str;
    }

    public void setOfferID(String str) {
        this.offerID = str;
    }

    public void setVideoAdName(String str) {
        this.videoAdName = str;
    }

    public void setVideoURL(String str) {
        this.videoURL = str;
    }

    public void setWebviewURL(String str) {
        this.webviewURL = str;
    }
}
