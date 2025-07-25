package jp.co.voyagegroup.android.fluct.jar.setting;

import java.io.Serializable;

public class FluctAd implements Serializable {
    private static final long serialVersionUID = -3573165148725762405L;
    private String mAdHtml;
    private String mBackColor;

    public String getAdHtml() {
        return this.mAdHtml;
    }

    public String getBackColor() {
        return this.mBackColor;
    }

    public void setAdHtml(String str) {
        this.mAdHtml = str;
    }

    public void setBackColor(String str) {
        this.mBackColor = str;
    }

    public String toString() {
        return "FluctAd [adHtml=" + this.mAdHtml + ", backColor=" + this.mBackColor + "]";
    }
}
