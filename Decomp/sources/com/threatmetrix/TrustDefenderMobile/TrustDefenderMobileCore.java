package com.threatmetrix.TrustDefenderMobile;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import android.util.TimingLogger;
import android.view.WindowManager;
import com.facebook.appevents.AppEventsConstants;
import com.tapjoy.TapjoyConstants;
import com.threatmetrix.TrustDefenderMobile.InfoGatherer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import jp.stargarage.g2metrics.BuildConfig;

@TargetApi(9)
class TrustDefenderMobileCore {
    private static final int MAX_ATTR_LEN = 255;
    private static final String TAG = TrustDefenderMobileCore.class.getSimpleName();
    static final int THM_OPTION_ALL = 3326;
    static final int THM_OPTION_ALL_ASYNC = 3327;
    @Deprecated
    static final int THM_OPTION_ALL_SYNC = 3326;
    static final int THM_OPTION_ALTERNATE_ID = 512;
    static final int THM_OPTION_ASYNC = 1;
    static final int THM_OPTION_BROWSER_PLUGINS = 32;
    static final int THM_OPTION_BROWSER_STRING = 2;
    static final int THM_OPTION_DNS = 1024;
    static final int THM_OPTION_GATHER_URLS = 256;
    @Deprecated
    static final int THM_OPTION_LEAN_ASYNC = 3135;
    @Deprecated
    static final int THM_OPTION_LEAN_SYNC = 3134;
    static final int THM_OPTION_MIME_TYPES = 4;
    @Deprecated
    static final int THM_OPTION_MOST_ASYNC = 3199;
    @Deprecated
    static final int THM_OPTION_MOST_SYNC = 3198;
    static final int THM_OPTION_SCREEN_DIM = 16;
    static final int THM_OPTION_SELF_HASH = 2048;
    @Deprecated
    static final int THM_OPTION_SYNC = 0;
    static final int THM_OPTION_TCP_FP = 64;
    static final int THM_OPTION_TCP_TARPIT = 128;
    static final int THM_OPTION_TIME_ZONE = 8;
    static final int THM_OPTION_VALID_DYNAMIC = 768;
    static final int THM_OPTION_VALID_DYNAMIC_INVERSE = 38;
    static final int THM_OPTION_WEBVIEW = 38;
    public static String version = "2.5-16";
    ArrayList<String> customAttributes = null;
    String m_HTML5Cookie = null;
    private String m_api_key = null;
    private String m_browserStringFromJS = null;
    private BrowserInfo m_browser_info = new BrowserInfo();
    private volatile AtomicBoolean m_cancel = null;
    TDConfiguration m_config = null;
    private Context m_context = null;
    String m_cookie = null;
    String m_deviceFingerprint = null;
    String m_deviceState = null;
    int m_dstDiff = 0;
    String m_flashCookie = null;
    private String m_fontCount = null;
    private String m_fontHash = null;
    String m_fp_server = null;
    int m_gmtOffset = 0;
    private String m_imei = null;
    Location m_location = null;
    int m_options = 0;
    String m_org_id = null;
    String m_referrerURL = null;
    int m_screenHeight = 0;
    int m_screenWidth = 0;
    private String m_self_hash = null;
    String m_session_id = null;
    private volatile InternalStatusCode m_status = InternalStatusCode.THM_OK;
    private TimingLogger m_timings = null;
    String m_url = null;

    TrustDefenderMobileCore() {
    }

    private void setBrowserStringFromJS(String str) {
        this.m_browserStringFromJS = str;
    }

    private void setTimingLogger(TimingLogger timingLogger) {
        this.m_timings = timingLogger;
    }

    /* access modifiers changed from: package-private */
    public final void gatherInfo() throws InterruptedException {
        String str = TAG;
        if ((this.m_options & 8) != 0) {
            InfoGatherer.TZInfo tZInfo = new InfoGatherer.TZInfo();
            if (InfoGatherer.getTimeZoneInfo(tZInfo)) {
                this.m_dstDiff = tZInfo.dstDiff;
                this.m_gmtOffset = tZInfo.gmtOffset;
            }
            if (this.m_timings != null) {
                this.m_timings.addSplit("get time zone");
            }
        }
        if (this.m_cookie == null || this.m_flashCookie == null || this.m_HTML5Cookie == null) {
            boolean z = (this.m_options & 512) != 0 && TDID.isDodgySerial();
            String str2 = null;
            String str3 = null;
            if (this.m_cookie == null) {
                str2 = TDID.getAndroidID(this.m_context);
                this.m_cookie = TDID.getCookie(str2, z);
            }
            if (this.m_timings != null) {
                this.m_timings.addSplit("cookie");
            }
            if (this.m_HTML5Cookie == null) {
                str3 = TDID.getPref(this.m_context);
                if (this.m_cancel == null || !this.m_cancel.get()) {
                    this.m_HTML5Cookie = TDID.getHTML5Cookie(str3, z);
                } else {
                    throw new InterruptedException();
                }
            }
            if (this.m_timings != null) {
                this.m_timings.addSplit("LSC");
            }
            if (this.m_imei == null) {
                this.m_imei = TDID.getIMEI(this.m_context);
                if (this.m_timings != null) {
                    this.m_timings.addSplit("imei");
                }
            }
            if (this.m_flashCookie == null) {
                if (str2 == null) {
                    str2 = TDID.getAndroidID(this.m_context);
                }
                if (str3 == null) {
                    str3 = TDID.getPref(this.m_context);
                }
                if (this.m_cancel == null || !this.m_cancel.get()) {
                    Context context = this.m_context;
                    this.m_flashCookie = TDID.getFlashCookie$26396263(str2, str3, this.m_imei, z);
                } else {
                    throw new InterruptedException();
                }
            }
            if (this.m_timings != null) {
                this.m_timings.addSplit("Flash");
            }
        }
        if ((this.m_cancel == null || !this.m_cancel.get()) && !Thread.currentThread().isInterrupted()) {
            if ((this.m_options & 16) != 0 && (this.m_screenHeight == 0 || this.m_screenWidth == 0)) {
                DisplayWrapper displayWrapper = new DisplayWrapper(((WindowManager) this.m_context.getSystemService("window")).getDefaultDisplay());
                this.m_screenWidth = displayWrapper.getWidth();
                this.m_screenHeight = displayWrapper.getHeight();
            }
            if (this.m_timings != null) {
                this.m_timings.addSplit("get screen dimensions");
            }
            if ((this.m_cancel == null || !this.m_cancel.get()) && !Thread.currentThread().isInterrupted()) {
                this.m_deviceState = InfoGatherer.getDeviceState();
                if (this.m_timings != null) {
                    this.m_timings.addSplit("get device state");
                }
                if ((this.m_cancel == null || !this.m_cancel.get()) && !Thread.currentThread().isInterrupted()) {
                    if (this.m_deviceFingerprint == null) {
                        this.m_deviceFingerprint = InfoGatherer.getDeviceFingerprint(this.m_context, this.m_context);
                    }
                    if (this.m_timings != null) {
                        this.m_timings.addSplit("get device fingerprint");
                    }
                    if ((this.m_options & 2048) != 0 && this.m_self_hash == null) {
                        this.m_self_hash = ApplicationInfoGatherer.checkThisPackage(this.m_context);
                        if (this.m_timings != null) {
                            this.m_timings.addSplit("get self hash");
                        }
                    }
                    if (this.m_fontCount == null || this.m_fontHash == null) {
                        StringBuilder sb = new StringBuilder();
                        this.m_fontHash = InfoGatherer.getFontHashAndCount(sb);
                        if (this.m_fontHash != null) {
                            this.m_fontCount = sb.toString();
                        }
                        String str4 = TAG;
                        new StringBuilder("Got ").append(this.m_fontCount).append(" fonts gives: ").append(this.m_fontHash);
                    }
                    if (this.m_timings != null) {
                        this.m_timings.addSplit("Get Fontlist");
                        return;
                    }
                    return;
                }
                throw new InterruptedException();
            }
            throw new InterruptedException();
        }
        throw new InterruptedException();
    }

    /* access modifiers changed from: package-private */
    public final String getApiKey() {
        return this.m_api_key;
    }

    /* access modifiers changed from: package-private */
    public final TDConfiguration getConfig() {
        return this.m_config;
    }

    /* access modifiers changed from: package-private */
    public final Map<String, String> getConfigurationHeaders() {
        HashMap hashMap = new HashMap();
        hashMap.put("User-Agent", this.m_browserStringFromJS);
        return hashMap;
    }

    /* access modifiers changed from: package-private */
    public final HttpParameterMap getConfigurationParams() {
        HttpParameterMap httpParameterMap = new HttpParameterMap();
        httpParameterMap.add("org_id", this.m_org_id);
        httpParameterMap.add(TapjoyConstants.TJC_SESSION_ID, this.m_session_id);
        httpParameterMap.add("os", TapjoyConstants.TJC_DEVICE_PLATFORM_TYPE);
        httpParameterMap.add("osVersion", Build.VERSION.RELEASE);
        if (this.m_api_key != null && !this.m_api_key.isEmpty()) {
            httpParameterMap.add("api_key", this.m_api_key);
        }
        return httpParameterMap;
    }

    /* access modifiers changed from: package-private */
    public final String getConfigurationPath() {
        return "https://" + this.m_fp_server + "/fp/mobile/conf";
    }

    /* access modifiers changed from: package-private */
    public final String getFPServer() {
        return this.m_fp_server;
    }

    /* access modifiers changed from: package-private */
    public final String getOrgID() {
        return this.m_org_id;
    }

    /* access modifiers changed from: package-private */
    public final HttpParameterMap getRiskBodyParameterMap() throws InterruptedException {
        List<String> checkURLs = InfoGatherer.checkURLs(this.m_context, this.m_config.jb_paths);
        int size = checkURLs.size();
        String ListToSeperatedString = StringUtils.ListToSeperatedString(checkURLs, ";");
        if (this.m_timings != null) {
            this.m_timings.addSplit("Check URLs");
        }
        String str = BuildConfig.FLAVOR;
        if ((this.m_config.options & 256) != 0) {
            str = InfoGatherer.getURLs(this.m_config.ex_paths);
            if (this.m_timings != null) {
                this.m_timings.addSplit("get URLs");
            }
        }
        HttpParameterMap httpParameterMap = new HttpParameterMap();
        httpParameterMap.setPostValueLengthLimit(255);
        httpParameterMap.add("w", this.m_config.w);
        httpParameterMap.add("c", String.valueOf(this.m_gmtOffset));
        httpParameterMap.add("z", String.valueOf(this.m_dstDiff));
        httpParameterMap.add("f", this.m_screenWidth + "x" + this.m_screenHeight);
        httpParameterMap.add("lh", this.m_url, true);
        httpParameterMap.add("dr", this.m_referrerURL, true);
        if (!this.m_browser_info.m_browserPluginCount.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
            httpParameterMap.add("p", this.m_browser_info.m_browserPlugins, true);
            httpParameterMap.add("pl", this.m_browser_info.m_browserPluginCount, true);
            httpParameterMap.add("ph", this.m_browser_info.m_browserPluginsFromJSHash, true);
        }
        httpParameterMap.add("hh", StringUtils.MD5(this.m_org_id + this.m_session_id));
        if (this.m_browser_info.m_mimeTypeCount > 0) {
            httpParameterMap.add("mt", this.m_browser_info.m_mimeTypesHash);
            httpParameterMap.add("mn", String.valueOf(this.m_browser_info.m_mimeTypeCount));
        }
        httpParameterMap.add("mdf", this.m_deviceFingerprint, true);
        httpParameterMap.add("mds", this.m_deviceState, true);
        httpParameterMap.add("imei", this.m_imei, true);
        if (this.m_location != null) {
            httpParameterMap.add("tdlat", String.valueOf(this.m_location.getLatitude()));
            httpParameterMap.add("tdlon", String.valueOf(this.m_location.getLongitude()));
            httpParameterMap.add("tdlacc", String.valueOf(this.m_location.getAccuracy()));
        }
        if (this.customAttributes != null && this.customAttributes.size() > 0) {
            int i = 0;
            Iterator<String> it = this.customAttributes.iterator();
            while (it.hasNext()) {
                int i2 = i + 1;
                httpParameterMap.add("aca" + i, it.next(), true);
                if (i2 >= 5) {
                    break;
                }
                i = i2;
            }
        }
        httpParameterMap.add("ah", this.m_self_hash);
        httpParameterMap.add("la", this.m_config.w + this.m_HTML5Cookie);
        httpParameterMap.add("lq", this.m_browserStringFromJS);
        String num = Integer.toString(new Random().nextInt(10000) + 10000);
        httpParameterMap.add("nu", num.substring(num.length() - 4));
        httpParameterMap.add("fc", this.m_config.w + this.m_flashCookie);
        httpParameterMap.add("ftsn", this.m_fontCount);
        httpParameterMap.add("fts", this.m_fontHash, true);
        httpParameterMap.add("v", Build.VERSION.RELEASE, true);
        httpParameterMap.add("o", Build.BRAND, true);
        httpParameterMap.add("mf", Build.MODEL, true);
        httpParameterMap.add("l", Locale.getDefault().getLanguage(), true);
        httpParameterMap.add("fg", this.m_flashCookie);
        httpParameterMap.add("ls", this.m_HTML5Cookie);
        httpParameterMap.add("gr", this.m_config.jb_paths.size() == 0 ? BuildConfig.FLAVOR : String.valueOf(size));
        httpParameterMap.add("grr", ListToSeperatedString);
        httpParameterMap.add("at", "agent_mobile");
        httpParameterMap.add("av", version);
        httpParameterMap.add("ex3", str);
        HttpParameterMap httpParameterMap2 = new HttpParameterMap();
        httpParameterMap2.add("org_id", this.m_org_id);
        httpParameterMap2.add(TapjoyConstants.TJC_SESSION_ID, this.m_session_id);
        if (this.m_timings != null) {
            this.m_timings.addSplit("params without xor");
        }
        String urlEncodedParamString = httpParameterMap.getUrlEncodedParamString();
        if (this.m_timings != null) {
            this.m_timings.addSplit("url encoding");
        }
        httpParameterMap2.add("ja", StringUtils.xor(urlEncodedParamString, this.m_session_id));
        httpParameterMap2.add("h", AppEventsConstants.EVENT_PARAM_VALUE_NO).add("m", "2");
        if (this.m_timings != null) {
            this.m_timings.addSplit("Params xor'd");
        }
        return httpParameterMap2;
    }

    /* access modifiers changed from: package-private */
    public final Map<String, String> getRiskDataHeaders() {
        HashMap hashMap = new HashMap();
        if (this.m_browserStringFromJS != null && !this.m_browserStringFromJS.isEmpty()) {
            String str = TAG;
            new StringBuilder("Setting User-Agent to ").append(this.m_browserStringFromJS);
            hashMap.put("User-Agent", this.m_browserStringFromJS);
        }
        if (this.m_cookie == null) {
            hashMap.put("Cookie", "thx_guid=");
        } else {
            hashMap.put("Cookie", "thx_guid=" + this.m_cookie);
        }
        hashMap.put("Referer", this.m_referrerURL);
        hashMap.put("Content-Type", "application/x-www-form-urlencoded");
        return hashMap;
    }

    /* access modifiers changed from: package-private */
    public final String getSessionID() {
        return this.m_session_id;
    }

    /* access modifiers changed from: package-private */
    public final InternalStatusCode getStatus() {
        String str = TAG;
        new StringBuilder("getStatus returns: ").append(this.m_status.toString());
        return this.m_status;
    }

    /* access modifiers changed from: package-private */
    public final void reset() {
        this.m_cookie = null;
        this.m_gmtOffset = 0;
        this.m_dstDiff = 0;
        this.m_deviceState = null;
        this.m_location = null;
        this.m_config = null;
    }

    /* access modifiers changed from: package-private */
    public final void setApiKey(String str) {
        this.m_api_key = str;
    }

    /* access modifiers changed from: package-private */
    public final void setBrowserInfo(BrowserInfo browserInfo) {
        this.m_browser_info = browserInfo;
    }

    /* access modifiers changed from: package-private */
    public final void setCancelObject(AtomicBoolean atomicBoolean) {
        this.m_cancel = atomicBoolean;
    }

    /* access modifiers changed from: package-private */
    public final void setConfig(TDConfiguration tDConfiguration) {
        this.m_config = tDConfiguration;
    }

    /* access modifiers changed from: package-private */
    public final void setContext(Context context) {
        this.m_context = context;
    }

    /* access modifiers changed from: package-private */
    public final void setCustomAttributes(List<String> list) {
        if (list == null || list.isEmpty()) {
            this.customAttributes.clear();
        } else {
            this.customAttributes = new ArrayList<>(list);
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean setFPServer(String str) {
        if (str == null) {
            str = TapjoyConstants.TJC_THREATMETRIX_SERVER_URL;
        }
        try {
            new URL("https://" + str);
            this.m_fp_server = str;
            return true;
        } catch (MalformedURLException e) {
            Log.e(TAG, "Invalid hostname " + str, e);
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public final void setLocation(Location location) {
        this.m_location = location;
    }

    /* access modifiers changed from: package-private */
    public final boolean setOrgID(String str) {
        if (str == null || str.length() != 8) {
            Log.e(TAG, "Invalid org_id");
            return false;
        }
        this.m_org_id = str;
        return true;
    }

    /* access modifiers changed from: package-private */
    public final boolean setProfileOptions(int i) {
        if ((i & 1) == 0) {
            Log.e(TAG, "Synchronous is deprecated, please switch to ASYNC");
            return false;
        }
        this.m_options = i;
        return true;
    }

    /* access modifiers changed from: package-private */
    public final void setSessionID(String str) {
        this.m_session_id = str;
    }

    /* access modifiers changed from: package-private */
    public final void setStatus(InternalStatusCode internalStatusCode) {
        this.m_status = internalStatusCode;
    }

    /* access modifiers changed from: package-private */
    public final boolean setURLS(String str, String str2) {
        if (str2 == null || str2.isEmpty()) {
            str2 = "TrustDefenderMobileSDK";
        }
        this.m_referrerURL = "http://" + str2;
        if (str == null || str.length() <= 0) {
            this.m_url = "http://" + str2 + "/mobile";
            return true;
        }
        this.m_url = str;
        if (str.compareToIgnoreCase(this.m_referrerURL) != 0) {
            return true;
        }
        this.m_url = str + TapjoyConstants.TJC_CONNECTION_TYPE_MOBILE;
        return true;
    }
}
