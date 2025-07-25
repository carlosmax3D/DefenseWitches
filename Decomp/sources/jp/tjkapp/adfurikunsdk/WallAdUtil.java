package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import java.util.ArrayList;
import jp.stargarage.g2metrics.BuildConfig;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class WallAdUtil {
    private ArrayList<WallAdInfo> mAdInfoList;
    private ArrayList<WallAdLayoutInfo> mAdLayoutInfoList;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class WallAdInfo {
        public int index = 0;
        public String app_id = BuildConfig.FLAVOR;
        public int theme = 0;

        WallAdInfo() {
        }
    }

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class WallAdLayoutInfo {
        public boolean start_time = true;
        public String app_id = BuildConfig.FLAVOR;
        public WallAdLayout ad_layout = null;

        WallAdLayoutInfo() {
        }
    }

    public WallAdUtil() {
        this.mAdLayoutInfoList = null;
        this.mAdInfoList = null;
        this.mAdLayoutInfoList = new ArrayList<>();
        this.mAdInfoList = new ArrayList<>();
    }

    private void addWallAdLayout(Context context, String str) {
        if (getWallAdLayoutInfo(str) == null) {
            WallAdLayoutInfo wallAdLayoutInfo = new WallAdLayoutInfo();
            wallAdLayoutInfo.app_id = str;
            wallAdLayoutInfo.start_time = true;
            wallAdLayoutInfo.ad_layout = new WallAdLayout(context);
            wallAdLayoutInfo.ad_layout.setAdfurikunAppKey(str);
            this.mAdLayoutInfoList.add(wallAdLayoutInfo);
        }
    }

    protected WallAdInfo getWallAdInfo() {
        if (this.mAdInfoList == null || this.mAdInfoList.size() <= 0) {
            return null;
        }
        return this.mAdInfoList.get(0);
    }

    protected WallAdLayoutInfo getWallAdLayoutInfo(String str) {
        int size = this.mAdLayoutInfoList.size();
        for (int i = 0; i < size; i++) {
            WallAdLayoutInfo wallAdLayoutInfo = this.mAdLayoutInfoList.get(i);
            if (wallAdLayoutInfo.app_id.equals(str)) {
                return wallAdLayoutInfo;
            }
        }
        return null;
    }

    protected void initializeWallAdSetting(Context context, String str) {
        removeWallAdAll();
        WallAdInfo wallAdInfo = new WallAdInfo();
        wallAdInfo.index = this.mAdInfoList.size();
        wallAdInfo.app_id = str;
        this.mAdInfoList.add(wallAdInfo);
        if (str == null || str.length() <= 0) {
            return;
        }
        addWallAdLayout(context, str);
    }

    protected boolean isLoadFinished() {
        WallAdLayoutInfo wallAdLayoutInfo;
        WallAdInfo wallAdInfo = getWallAdInfo();
        if (wallAdInfo == null || (wallAdLayoutInfo = getWallAdLayoutInfo(wallAdInfo.app_id)) == null || wallAdLayoutInfo.ad_layout == null) {
            return false;
        }
        return wallAdLayoutInfo.ad_layout.isLoadFinished();
    }

    protected void removeWallAdAll() {
        int size = this.mAdLayoutInfoList.size();
        for (int i = 0; i < size; i++) {
            WallAdLayoutInfo wallAdLayoutInfo = this.mAdLayoutInfoList.get(i);
            if (wallAdLayoutInfo.ad_layout != null) {
                wallAdLayoutInfo.ad_layout.destroy();
                wallAdLayoutInfo.ad_layout = null;
            }
        }
        this.mAdLayoutInfoList.removeAll(this.mAdLayoutInfoList);
        this.mAdInfoList.removeAll(this.mAdInfoList);
    }

    protected void setWallAdTheme(int i) {
        WallAdInfo wallAdInfo = getWallAdInfo();
        if (wallAdInfo != null) {
            wallAdInfo.theme = i;
        }
    }
}
