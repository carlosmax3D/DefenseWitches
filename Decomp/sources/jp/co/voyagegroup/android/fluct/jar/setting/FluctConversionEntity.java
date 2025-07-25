package jp.co.voyagegroup.android.fluct.jar.setting;

import java.io.Serializable;
import java.util.ArrayList;

public class FluctConversionEntity implements Serializable {
    private static final long serialVersionUID = 3538379580278642662L;
    private String mBrowserOpenUrl;
    private ArrayList<String> mConvUrl;

    public String getBrowserOpenUrl() {
        return this.mBrowserOpenUrl;
    }

    public ArrayList<String> getConvUrl() {
        return this.mConvUrl;
    }

    public void setBrowserOpenUrl(String str) {
        this.mBrowserOpenUrl = str;
    }

    public void setConvUrl(ArrayList<String> arrayList) {
        this.mConvUrl = arrayList;
    }

    public String toString() {
        return "FluctConversionEntity [convUrl=" + this.mConvUrl + ", browserOpenUrl=" + this.mBrowserOpenUrl + "]";
    }
}
