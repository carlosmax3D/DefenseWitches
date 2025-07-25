package com.tapjoy;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;
import twitter4j.HttpResponseCode;

public class TapjoyEvent {
    public static final int EVENT_TYPE_IAP = 1;
    public static final int EVENT_TYPE_SHUTDOWN = 2;
    private static final String TAG = "Event";
    /* access modifiers changed from: private */
    public static TapjoyURLConnection tapjoyURLConnection = null;
    private Context context;

    public class EventThread implements Runnable {
        private Map<String, String> params;

        public EventThread(Map<String, String> map) {
            this.params = map;
        }

        public void run() {
            String str = TapjoyConnectCore.getHostURL() + TapjoyConstants.TJC_EVENT_URL_PATH;
            TapjoyHttpURLResponse responseFromURL = TapjoyEvent.tapjoyURLConnection.getResponseFromURL(str, this.params, 1);
            if (responseFromURL != null) {
                switch (responseFromURL.statusCode) {
                    case 0:
                        TapjoyLog.e(TapjoyEvent.TAG, "Event network error: " + responseFromURL.statusCode);
                        TapjoyConnectCore.saveOfflineLog(str + "?" + this.params);
                        return;
                    case HttpResponseCode.OK /*200*/:
                        TapjoyLog.i(TapjoyEvent.TAG, "Successfully sent Tapjoy event");
                        return;
                    case HttpResponseCode.BAD_REQUEST /*400*/:
                        TapjoyLog.e(TapjoyEvent.TAG, "Error sending event: " + responseFromURL.response);
                        return;
                    default:
                        TapjoyLog.e(TapjoyEvent.TAG, "Unknown error: " + responseFromURL.statusCode);
                        return;
                }
            } else {
                TapjoyLog.e(TapjoyEvent.TAG, "Server/network error");
            }
        }
    }

    public TapjoyEvent(Context context2) {
        this.context = context2;
        tapjoyURLConnection = new TapjoyURLConnection();
    }

    public String createEventParameter(String str) {
        return "ue[" + str + "]";
    }

    public void sendEvent(int i, Map<String, String> map) {
        TapjoyLog.i(TAG, "sendEvent type: " + i);
        Map<String, String> genericURLParams = TapjoyConnectCore.getGenericURLParams();
        TapjoyUtil.safePut(genericURLParams, TapjoyConstants.TJC_EVENT_TYPE_ID, String.valueOf(i), true);
        if (map != null) {
            genericURLParams.putAll(map);
        }
        new Thread(new EventThread(genericURLParams)).start();
    }

    public void sendIAPEvent(String str, float f, int i, String str2) {
        HashMap hashMap = new HashMap();
        TapjoyUtil.safePut(hashMap, createEventParameter("name"), str, true);
        TapjoyUtil.safePut(hashMap, createEventParameter(TapjoyConstants.TJC_EVENT_IAP_PRICE), String.valueOf(f), true);
        TapjoyUtil.safePut(hashMap, createEventParameter("quantity"), String.valueOf(i), true);
        TapjoyUtil.safePut(hashMap, createEventParameter(TapjoyConstants.TJC_EVENT_IAP_CURRENCY_ID), str2, true);
        sendEvent(1, hashMap);
    }

    public void sendShutDownEvent() {
        sendEvent(2, (Map<String, String>) null);
    }
}
