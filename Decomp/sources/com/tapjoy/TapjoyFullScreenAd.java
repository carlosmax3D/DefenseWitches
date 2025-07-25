package com.tapjoy;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.drive.DriveFile;
import java.util.Map;
import twitter4j.HttpResponseCode;

public class TapjoyFullScreenAd {
    private static final String TAG = "Full Screen Ad";
    /* access modifiers changed from: private */
    public static String baseURL = null;
    /* access modifiers changed from: private */
    public static TapjoyFullScreenAdNotifier fullScreenAdNotifier;
    /* access modifiers changed from: private */
    public static String htmlResponseData = null;
    private Context context;
    /* access modifiers changed from: private */
    public Map<String, String> legacyFullScreenAdParams;

    public TapjoyFullScreenAd(Context context2) {
        this.context = context2;
    }

    public void getFullScreenAd(TapjoyFullScreenAdNotifier tapjoyFullScreenAdNotifier) {
        TapjoyLog.i(TAG, "getFullScreenAd");
        getFullScreenAd((String) null, tapjoyFullScreenAdNotifier);
    }

    public void getFullScreenAd(String str, TapjoyFullScreenAdNotifier tapjoyFullScreenAdNotifier) {
        fullScreenAdNotifier = tapjoyFullScreenAdNotifier;
        getFullScreenAdLegacy(str);
    }

    public void getFullScreenAdLegacy(String str) {
        TapjoyLog.i(TAG, "Getting Full Screen Ad");
        this.legacyFullScreenAdParams = TapjoyConnectCore.getURLParams();
        TapjoyUtil.safePut(this.legacyFullScreenAdParams, TapjoyConstants.TJC_CURRENCY_ID, str, true);
        new Thread(new Runnable() {
            public void run() {
                TapjoyHttpURLResponse responseFromURL = new TapjoyURLConnection().getResponseFromURL(TapjoyConnectCore.getHostURL() + TapjoyConstants.TJC_FEATURED_APP_URL_PATH, (Map<String, String>) TapjoyFullScreenAd.this.legacyFullScreenAdParams);
                String unused = TapjoyFullScreenAd.baseURL = TapjoyConnectCore.getHostURL();
                if (responseFromURL != null) {
                    switch (responseFromURL.statusCode) {
                        case HttpResponseCode.OK /*200*/:
                            String unused2 = TapjoyFullScreenAd.htmlResponseData = responseFromURL.response;
                            if (TapjoyFullScreenAd.fullScreenAdNotifier != null) {
                                TapjoyFullScreenAd.fullScreenAdNotifier.getFullScreenAdResponse();
                                return;
                            }
                            return;
                        default:
                            if (TapjoyFullScreenAd.fullScreenAdNotifier != null) {
                                TapjoyFullScreenAd.fullScreenAdNotifier.getFullScreenAdResponseFailed(1);
                                return;
                            }
                            return;
                    }
                } else if (TapjoyFullScreenAd.fullScreenAdNotifier != null) {
                    TapjoyFullScreenAd.fullScreenAdNotifier.getFullScreenAdResponseFailed(2);
                }
            }
        }).start();
    }

    public void setDisplayCount(int i) {
    }

    public void showFullScreenAd() {
        if (htmlResponseData != null && htmlResponseData.length() > 0 && !TapjoyConnectCore.isViewOpen()) {
            TapjoyConnectCore.viewWillOpen(1);
            Intent intent = new Intent(this.context, TapjoyFullScreenAdWebView.class);
            intent.setFlags(DriveFile.MODE_READ_ONLY);
            intent.putExtra("html", htmlResponseData);
            intent.putExtra(TJAdUnitConstants.EXTRA_BASE_URL, baseURL);
            intent.putExtra(TJAdUnitConstants.EXTRA_LEGACY_VIEW, true);
            this.context.startActivity(intent);
        }
    }
}
