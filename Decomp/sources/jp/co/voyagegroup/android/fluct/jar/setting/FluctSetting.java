package jp.co.voyagegroup.android.fluct.jar.setting;

import java.io.Serializable;
import java.util.ArrayList;
import jp.co.voyagegroup.android.fluct.jar.db.FluctInterstitialTable;
import jp.stargarage.g2metrics.BuildConfig;

public class FluctSetting implements Serializable {
    private static final long serialVersionUID = -3651259190832877939L;
    private ArrayList<Animation> mAnimations;
    private int mBrowser;
    private String mErrorMessages;
    private FluctAd mFluctAd;
    private FluctConversionEntity mFluctConversion;
    private FluctInterstitialTable mFluctInterstitial;
    private long mLoadTime;
    private String mMediaId;
    private String mMode;
    private long mRefreshTime;
    private String mUserAgent;

    public static class Animation implements Serializable {
        private static final long serialVersionUID = 0;
        private int mDuration;
        private int mType;

        public Animation(int i, int i2) {
            this.mType = i;
            this.mDuration = i2;
        }

        public int getDuration() {
            return this.mDuration;
        }

        public int getType() {
            return this.mType;
        }

        public void setDuration(int i) {
            this.mDuration = i;
        }

        public void setType(int i) {
            this.mType = i;
        }
    }

    public ArrayList<Animation> getAnimations() {
        return this.mAnimations;
    }

    public int getBrowser() {
        return this.mBrowser;
    }

    public String getErrorMessages() {
        return this.mErrorMessages;
    }

    public FluctAd getFluctAd() {
        return this.mFluctAd;
    }

    public FluctConversionEntity getFluctConversion() {
        return this.mFluctConversion;
    }

    public FluctInterstitialTable getFluctInterstitial() {
        return this.mFluctInterstitial;
    }

    public long getLoadTime() {
        return this.mLoadTime;
    }

    public String getMediaId() {
        return this.mMediaId;
    }

    public String getMode() {
        return this.mMode;
    }

    public long getRefreshTime() {
        return this.mRefreshTime;
    }

    public String getUserAgent() {
        return this.mUserAgent;
    }

    public void setAnimations(ArrayList<Animation> arrayList) {
        this.mAnimations = arrayList;
    }

    public void setBrowser(int i) {
        this.mBrowser = i;
    }

    public void setErrorMessages(String str) {
        this.mErrorMessages = str;
    }

    public void setFluctAd(FluctAd fluctAd) {
        this.mFluctAd = fluctAd;
    }

    public void setFluctConversion(FluctConversionEntity fluctConversionEntity) {
        this.mFluctConversion = fluctConversionEntity;
    }

    public void setFluctInterstitial(FluctInterstitialTable fluctInterstitialTable) {
        this.mFluctInterstitial = fluctInterstitialTable;
    }

    public void setLoadTime(long j) {
        this.mLoadTime = j;
    }

    public void setMediaId(String str) {
        this.mMediaId = str;
    }

    public void setMode(String str) {
        this.mMode = str;
    }

    public void setRefreshTime(long j) {
        this.mRefreshTime = j;
    }

    public void setUserAgent(String str) {
        this.mUserAgent = str.replace("\n", BuildConfig.FLAVOR).replace("\r", BuildConfig.FLAVOR);
    }

    public String toString() {
        return "FluctSetting [mMediaId=" + this.mMediaId + ", mFluctAd=" + this.mFluctAd + ", mFluctConversion=" + this.mFluctConversion + ", mMode=" + this.mMode + ", mRefreshTime=" + this.mRefreshTime + ", mBrowser=" + this.mBrowser + ", mErrorMessages=" + this.mErrorMessages + ", mLoadTime=" + this.mLoadTime + "]";
    }
}
