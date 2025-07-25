package jp.tjkapp.adfurikunsdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import jp.stargarage.g2metrics.BuildConfig;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class AdInfo {
    public static final int ADTYPE_BANNER = 0;
    public static final int ADTYPE_INTERSTITIAL = 1;
    public static final int ADTYPE_NATIVE = 3;
    public static final int ADTYPE_OTHER = -1;
    public static final int ADTYPE_WALL = 2;
    public static final int AD_CHECK = 0;
    public static final int BANNER_KIND_BANNER1 = 0;
    public static final int BANNER_KIND_BANNER2 = 1;
    public static final int BANNER_KIND_BANNER3 = 3;
    public static final int BANNER_KIND_BANNER4 = 4;
    public static final int BANNER_KIND_BANNER5 = 5;
    public static final int BANNER_KIND_BANNER6 = 6;
    public static final int BANNER_KIND_BANNER7 = 7;
    public static final int BANNER_KIND_BANNER8 = 8;
    public static final int BANNER_KIND_INTERSTITIAL1 = 9;
    public static final int BANNER_KIND_NATIVE1 = 11;
    public static final int BANNER_KIND_UNKNOWN = -1;
    public static final int BANNER_KIND_WALL1 = 10;
    public static final int NO_AD_CHECK = 1;
    public static final int TAPCHK_OFF_FLG_OFF = 0;
    public static final int TAPCHK_OFF_FLG_ON = 1;
    public static final int WALL_TYPE_API = 2;
    public static final int WALL_TYPE_HTML = 3;
    public static final int WALL_TYPE_NONE = 0;
    public static final int WALL_TYPE_URL = 1;
    public ArrayList<AdInfoDetail> adInfoDetailArray;
    public int banner_kind;
    public String bg_color;
    public long cycle_time;
    private int index;
    private Random random;
    public boolean ta_off;
    private JSONObject weight_total;
    public static final int[] BANNER_KIND_BANNER = {0, 1, 3, 4, 5, 6, 7, 8};
    public static final int[] BANNER_KIND_INTERSTITIAL = {9};
    public static final int[] BANNER_KIND_WALL = {10};
    public static final int[] BANNER_KIND_NATIVE = {11};

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    static class AdInfoDetail {
        public String adnetwork_key;
        public String[] ext_act_url;
        public String html;
        public int noadcheck;
        public String param;
        public String user_ad_id;
        public int wall_type;
        public JSONObject weight;

        public AdInfoDetail(AdInfoDetail adInfoDetail) {
            if (adInfoDetail == null) {
                initialize();
                return;
            }
            this.user_ad_id = AdInfo.newString(adInfoDetail.user_ad_id);
            if (adInfoDetail.weight != null) {
                try {
                    this.weight = new JSONObject(adInfoDetail.weight.toString());
                } catch (JSONException e) {
                    this.weight = null;
                }
            } else {
                this.weight = null;
            }
            this.adnetwork_key = AdInfo.newString(adInfoDetail.adnetwork_key);
            this.html = AdInfo.newString(adInfoDetail.html);
            this.wall_type = adInfoDetail.wall_type;
            this.param = AdInfo.newString(adInfoDetail.param);
            if (adInfoDetail.ext_act_url != null) {
                int length = adInfoDetail.ext_act_url.length;
                this.ext_act_url = new String[length];
                for (int i = 0; i < length; i++) {
                    this.ext_act_url[i] = AdInfo.newString(adInfoDetail.ext_act_url[i]);
                }
            } else {
                this.ext_act_url = null;
            }
            this.noadcheck = adInfoDetail.noadcheck;
        }

        private void initialize() {
            this.user_ad_id = BuildConfig.FLAVOR;
            this.weight = null;
            this.adnetwork_key = BuildConfig.FLAVOR;
            this.html = BuildConfig.FLAVOR;
            this.wall_type = 0;
            this.param = BuildConfig.FLAVOR;
            this.ext_act_url = null;
            this.noadcheck = 0;
        }
    }

    public AdInfo(AdInfo adInfo) {
        if (adInfo == null) {
            initialize();
            return;
        }
        this.cycle_time = adInfo.cycle_time;
        this.banner_kind = adInfo.banner_kind;
        this.bg_color = newString(adInfo.bg_color);
        this.ta_off = adInfo.ta_off;
        this.adInfoDetailArray = new ArrayList<>();
        if (adInfo.adInfoDetailArray != null) {
            this.adInfoDetailArray.addAll(adInfo.adInfoDetailArray);
        }
        try {
            this.weight_total = new JSONObject(adInfo.weight_total.toString());
        } catch (JSONException e) {
            this.weight_total = new JSONObject();
        }
        this.random = new Random();
        this.index = adInfo.index;
    }

    public static int getAdType(int i) {
        int length = BANNER_KIND_BANNER.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (i == BANNER_KIND_BANNER[i2]) {
                return 0;
            }
        }
        int length2 = BANNER_KIND_INTERSTITIAL.length;
        for (int i3 = 0; i3 < length2; i3++) {
            if (i == BANNER_KIND_INTERSTITIAL[i3]) {
                return 1;
            }
        }
        int length3 = BANNER_KIND_WALL.length;
        for (int i4 = 0; i4 < length3; i4++) {
            if (i == BANNER_KIND_WALL[i4]) {
                return 2;
            }
        }
        int length4 = BANNER_KIND_NATIVE.length;
        for (int i5 = 0; i5 < length4; i5++) {
            if (i == BANNER_KIND_NATIVE[i5]) {
                return 3;
            }
        }
        return -1;
    }

    private void initialize() {
        this.cycle_time = 30L;
        this.banner_kind = -1;
        this.bg_color = "ffffff";
        this.ta_off = false;
        this.adInfoDetailArray = new ArrayList<>();
        this.weight_total = new JSONObject();
        this.random = new Random();
        this.index = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String newString(String str) {
        return str == null ? BuildConfig.FLAVOR : new String(str);
    }

    protected int getLocaleAdCt() {
        String language = Locale.getDefault().getLanguage();
        if (!this.weight_total.has(language)) {
            language = Constants.DEFAULT_LOCALE;
        }
        if (!this.weight_total.has(language) || this.adInfoDetailArray == null) {
            return 0;
        }
        int size = this.adInfoDetailArray.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            AdInfoDetail adInfoDetail = this.adInfoDetailArray.get(i2);
            if (adInfoDetail.weight.has(language) && !AdNetworkKey.DEFAULT.equals(adInfoDetail.adnetwork_key)) {
                i++;
            }
        }
        return i;
    }

    protected AdInfoDetail getNextAd(boolean z) throws JSONException {
        if (!z) {
            try {
                ArrayList<AdInfoDetail> arrayList = this.adInfoDetailArray;
                int i = this.index;
                this.index = i + 1;
                AdInfoDetail adInfoDetail = arrayList.get(i);
                if (this.index < this.adInfoDetailArray.size()) {
                    return adInfoDetail;
                }
                this.index = 0;
                return adInfoDetail;
            } catch (Exception e) {
                return null;
            }
        }
        int i2 = 0;
        String language = Locale.getDefault().getLanguage();
        if (!this.weight_total.has(language)) {
            language = Constants.DEFAULT_LOCALE;
        }
        if (this.weight_total.has(language)) {
            try {
                i2 = this.weight_total.getInt(language);
            } catch (JSONException e2) {
            }
        }
        if (i2 > 0 && this.adInfoDetailArray != null) {
            int iNextInt = this.random.nextInt(i2);
            int i3 = 0;
            int size = this.adInfoDetailArray.size();
            for (int i4 = 0; i4 < size; i4++) {
                AdInfoDetail adInfoDetail2 = this.adInfoDetailArray.get(i4);
                if (adInfoDetail2.weight.has(language)) {
                    try {
                        i3 += adInfoDetail2.weight.getInt(language);
                    } catch (JSONException e3) {
                    }
                }
                if (i3 - 1 >= iNextInt) {
                    return adInfoDetail2;
                }
            }
        }
        return null;
    }

    protected boolean hasAd() {
        return this.weight_total.has(Locale.getDefault().getLanguage());
    }

    protected void initCalc() throws JSONException {
        int size = this.adInfoDetailArray.size();
        for (int i = 0; i < size; i++) {
            AdInfoDetail adInfoDetail = this.adInfoDetailArray.get(i);
            Iterator<String> itKeys = adInfoDetail.weight.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                try {
                    int i2 = adInfoDetail.weight.getInt(next);
                    if (this.weight_total.has(next)) {
                        i2 += this.weight_total.getInt(next);
                    }
                    this.weight_total.put(next, i2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
