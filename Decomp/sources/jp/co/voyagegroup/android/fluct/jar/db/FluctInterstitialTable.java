package jp.co.voyagegroup.android.fluct.jar.db;

import java.io.Serializable;

public class FluctInterstitialTable implements Serializable {
    private static final long serialVersionUID = 2562785579224548567L;
    private String mAdHtml;
    private int mDisplayRate;
    private int mHeight;
    private String mMediaId;
    private int mUpdateTime;
    private int mWidth;

    public String getAdHtml() {
        return this.mAdHtml;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public String getMediaId() {
        return this.mMediaId;
    }

    public int getRate() {
        return this.mDisplayRate;
    }

    public int getUpdateTime() {
        return this.mUpdateTime;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setAdHtml(String str) {
        this.mAdHtml = str;
    }

    public void setHeight(int i) {
        this.mHeight = i;
    }

    public void setMediaId(String str) {
        this.mMediaId = str;
    }

    public void setRate(int i) {
        this.mDisplayRate = i;
    }

    public void setUpdateTime(int i) {
        this.mUpdateTime = i;
    }

    public void setWidth(int i) {
        this.mWidth = i;
    }
}
