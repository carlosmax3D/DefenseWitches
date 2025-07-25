package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import java.util.ArrayList;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.FileUtil;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class IntersAdUtil {
    private ArrayList<IntersAdInfo> mAdInfoList;
    private ArrayList<IntersAdLayoutInfo> mAdLayoutInfoList;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class IntersAdInfo {
        public int index = 0;
        public String app_id = BuildConfig.FLAVOR;
        public int frequency = 1;
        public int max = 0;
        public String custom_button_name = BuildConfig.FLAVOR;
        public String titlebar_text = BuildConfig.FLAVOR;

        IntersAdInfo() {
        }
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class IntersAdLayoutInfo {
        public boolean start_time = true;
        public String app_id = BuildConfig.FLAVOR;
        public IntersAdLayout ad_layout = null;

        IntersAdLayoutInfo() {
        }
    }

    public IntersAdUtil() {
        this.mAdLayoutInfoList = null;
        this.mAdInfoList = null;
        this.mAdLayoutInfoList = new ArrayList<>();
        this.mAdInfoList = new ArrayList<>();
    }

    private void addIntersAdLayout(Context context, String str) {
        if (getIntersAdLayoutInfo(str) == null) {
            IntersAdLayoutInfo intersAdLayoutInfo = new IntersAdLayoutInfo();
            intersAdLayoutInfo.app_id = str;
            intersAdLayoutInfo.start_time = true;
            intersAdLayoutInfo.ad_layout = new IntersAdLayout(context);
            intersAdLayoutInfo.ad_layout.setAdfurikunAppKey(str);
            this.mAdLayoutInfoList.add(intersAdLayoutInfo);
        }
    }

    protected void addIntersAdSetting(Context context, String str, String str2, int i, int i2, String str3) {
        IntersAdInfo intersAdInfo = new IntersAdInfo();
        intersAdInfo.index = this.mAdInfoList.size();
        intersAdInfo.app_id = str;
        intersAdInfo.frequency = i;
        intersAdInfo.max = i2;
        if (str3 == null) {
            str3 = BuildConfig.FLAVOR;
        }
        intersAdInfo.custom_button_name = str3;
        if (str2 == null) {
            str2 = BuildConfig.FLAVOR;
        }
        intersAdInfo.titlebar_text = str2;
        this.mAdInfoList.add(intersAdInfo);
        String str4 = intersAdInfo.index + "_" + str;
        FileUtil.IntersAdPref intersAdPref = FileUtil.getIntersAdPref(context, str4);
        intersAdPref.max_ct = 0;
        FileUtil.setIntersAdPref(context, str4, intersAdPref);
        if (str.length() > 0) {
            addIntersAdLayout(context, str);
        }
    }

    protected IntersAdInfo getIntersAdInfo(int i) {
        if (i < 0 || i >= this.mAdInfoList.size()) {
            return null;
        }
        return this.mAdInfoList.get(i);
    }

    protected IntersAdLayoutInfo getIntersAdLayoutInfo(String str) {
        int size = this.mAdLayoutInfoList.size();
        for (int i = 0; i < size; i++) {
            IntersAdLayoutInfo intersAdLayoutInfo = this.mAdLayoutInfoList.get(i);
            if (intersAdLayoutInfo.app_id.equals(str)) {
                return intersAdLayoutInfo;
            }
        }
        return null;
    }

    protected boolean isLoadFinished(int i) {
        IntersAdLayoutInfo intersAdLayoutInfo;
        IntersAdInfo intersAdInfo = getIntersAdInfo(i);
        if (intersAdInfo == null || (intersAdLayoutInfo = getIntersAdLayoutInfo(intersAdInfo.app_id)) == null || intersAdLayoutInfo.ad_layout == null) {
            return false;
        }
        return intersAdLayoutInfo.ad_layout.isLoadFinished();
    }

    protected void removeIntersAdAll() {
        int size = this.mAdLayoutInfoList.size();
        for (int i = 0; i < size; i++) {
            IntersAdLayoutInfo intersAdLayoutInfo = this.mAdLayoutInfoList.get(i);
            if (intersAdLayoutInfo.ad_layout != null) {
                intersAdLayoutInfo.ad_layout.destroy();
                intersAdLayoutInfo.ad_layout = null;
            }
        }
        this.mAdLayoutInfoList.removeAll(this.mAdLayoutInfoList);
        this.mAdInfoList.removeAll(this.mAdInfoList);
    }
}
