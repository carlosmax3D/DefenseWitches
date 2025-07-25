package com.tapjoy;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.drive.DriveFile;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import twitter4j.HttpResponseCode;

public class TJEvent {
    private static final String TAG = "TJEvent";
    private boolean autoShowContent;
    /* access modifiers changed from: private */
    public TJEventCallback callback;
    /* access modifiers changed from: private */
    public boolean contentAvailable;
    private boolean contentReady;
    private Context context;
    /* access modifiers changed from: private */
    public TJEventData eventData;
    private Map<String, String> eventParams;
    private boolean preloadEvent;
    /* access modifiers changed from: private */
    public Map<String, String> urlParams;

    public TJEvent(Context context2, String str, TJEventCallback tJEventCallback) {
        this(context2, str, (String) null, tJEventCallback);
    }

    public TJEvent(Context context2, String str, String str2, TJEventCallback tJEventCallback) {
        this.contentAvailable = false;
        this.autoShowContent = false;
        this.preloadEvent = false;
        this.contentReady = false;
        this.context = context2;
        this.callback = tJEventCallback;
        this.eventData = new TJEventData();
        this.eventData.name = str;
        this.eventData.value = str2;
        this.eventData.guid = UUID.randomUUID().toString();
        this.eventParams = new HashMap();
        TapjoyUtil.safePut(this.eventParams, "event_name", this.eventData.name, true);
        TapjoyUtil.safePut(this.eventParams, TJAdUnitConstants.PARAM_EVENT_VALUE, this.eventData.value, true);
        this.urlParams = TapjoyConnectCore.getGenericURLParams();
        this.urlParams.putAll(this.eventParams);
        this.urlParams.putAll(TapjoyConnectCore.getTimeStampAndVerifierParams());
        String str3 = TapjoyConnectCore.getEventURL() + TJAdUnitConstants.EVENTS_PATH;
        this.eventData.url = str3;
        this.eventData.baseURL = str3.substring(0, str3.indexOf(47, str3.indexOf("//") + "//".length() + 1));
        while (TJEventManager.get(this.eventData.guid) != null) {
            this.eventData.guid = UUID.randomUUID().toString();
        }
        TJEventManager.put(this.eventData.guid, this);
    }

    /* access modifiers changed from: private */
    public void callContentReadyCallback(int i) {
        this.contentReady = true;
        TapjoyLog.i(TAG, "Content is ready for event " + this.eventData.name + ", status: " + i);
        this.callback.contentIsReady(this, i);
        if (this.autoShowContent) {
            TapjoyLog.i(TAG, "Presenting content for event " + this.eventData.name + " automatically");
            showContent();
        }
    }

    public void enableAutoPresent(boolean z) {
        this.autoShowContent = z;
    }

    public void enablePreload(boolean z) {
        this.preloadEvent = z;
        TapjoyUtil.safePut(this.urlParams, TJAdUnitConstants.PARAM_EVENT_PRELOAD, String.valueOf(z), false);
    }

    /* access modifiers changed from: protected */
    public void eventSuccess(TapjoyHttpURLResponse tapjoyHttpURLResponse) {
        this.contentAvailable = true;
        this.callback.sendEventCompleted(this, this.contentAvailable);
        TapjoyLog.i(TAG, "Send request delivered successfully for event " + this.eventData.name + ", contentAvailable: " + this.contentAvailable + ", preload: " + this.preloadEvent);
        if (this.preloadEvent) {
            TapjoyLog.i(TAG, "Begin preloading content for event " + this.eventData.name);
            String headerFieldAsString = tapjoyHttpURLResponse.getHeaderFieldAsString(TapjoyConstants.TAPJOY_CACHE_HEADER);
            try {
                if (TapjoyCache.getInstance().getEventPreloadCount() == TapjoyCache.getInstance().getEventPreloadLimit()) {
                    TapjoyLog.i(TAG, "Event preloading limit reached. No content will be cached for event " + this.eventData.name);
                    callContentReadyCallback(1);
                    return;
                }
                TapjoyCache.getInstance().incrementEventCacheCount();
                JSONArray jSONArray = new JSONArray(headerFieldAsString);
                if (jSONArray == null || jSONArray.length() <= 0) {
                    callContentReadyCallback(1);
                } else {
                    TapjoyCache.getInstance().cacheAssetGroup(jSONArray, new TapjoyCacheNotifier() {
                        public void cachingComplete(int i) {
                            TJEvent.this.callContentReadyCallback(i);
                        }
                    });
                }
            } catch (JSONException e) {
                callContentReadyCallback(1);
            }
        } else if (this.autoShowContent) {
            TapjoyLog.i(TAG, "Presenting content for event " + this.eventData.name + " automatically");
            showContent();
        }
    }

    public TJEventCallback getCallback() {
        return this.callback;
    }

    public String getGUID() {
        return this.eventData.guid;
    }

    public String getName() {
        return this.eventData.name;
    }

    public Map<String, String> getParameters() {
        return this.eventParams;
    }

    public String getValue() {
        return this.eventData.value;
    }

    public boolean isContentAvailable() {
        return this.contentAvailable;
    }

    public boolean isContentReady() {
        return this.contentReady;
    }

    public boolean isPreloadEnabled() {
        return this.preloadEvent;
    }

    public void processSendCallback(boolean z) {
        if (z) {
            sendRequest();
            return;
        }
        trackEventForOfflineDelivery();
        this.callback.sendEventCompleted(this, false);
    }

    public void send() {
        if (this.callback == null) {
            TapjoyLog.e(TAG, "TJEventSendCallback is null");
        }
        if (TapjoyConnectCore.getInstance() == null || TJEventOptimizer.getInstance() == null || !TapjoyConnectCore.getInstance().isInitialized()) {
            TapjoyLog.e(TAG, "ERROR -- SDK not initialized -- requestTapjoyConnect must be called first");
            if (this.callback != null) {
                this.callback.sendEventFail(this, new TJError(0, "SDK not initialized -- requestTapjoyConnect must be called first"));
            }
        } else if (this.context == null) {
            if (this.callback != null) {
                this.callback.sendEventFail(this, new TJError(0, "Context is null -- TJEvent requires a valid Context."));
            }
        } else if (this.eventData.name != null && this.eventData.name.length() != 0) {
            TJEventOptimizer.getInstance().checkEvent(this);
        } else if (this.callback != null) {
            this.callback.sendEventFail(this, new TJError(0, "Invalid eventName -- TJEvent requires a valid eventName."));
        }
    }

    public void sendRequest() {
        new Thread() {
            public void run() {
                TapjoyLog.i(TJEvent.TAG, "Sending event: " + TJEvent.this.eventData.name);
                TapjoyHttpURLResponse responseFromURL = new TapjoyURLConnection().getResponseFromURL(TJEvent.this.eventData.url, (Map<String, String>) TJEvent.this.urlParams);
                TJEvent.this.eventData.httpStatusCode = responseFromURL.statusCode;
                TJEvent.this.eventData.httpResponse = responseFromURL.response;
                String headerFieldAsString = responseFromURL.getHeaderFieldAsString(TapjoyConstants.TAPJOY_EVENT_DEBUG_HEADER);
                if (headerFieldAsString != null) {
                    TapjoyLog.v(TJEvent.TAG, "Tapjoy-Server-Debug: " + headerFieldAsString);
                }
                if (responseFromURL != null && TJEvent.this.callback != null) {
                    switch (responseFromURL.statusCode) {
                        case 0:
                            TapjoyLog.i(TJEvent.TAG, "Send request failed for event " + TJEvent.this.eventData.name);
                            TJEvent.this.trackEventForOfflineDelivery();
                            TJEvent.this.callback.sendEventFail(TJEvent.this, new TJError(responseFromURL.statusCode, responseFromURL.response));
                            return;
                        case HttpResponseCode.OK /*200*/:
                            TJEvent.this.eventSuccess(responseFromURL);
                            return;
                        default:
                            TapjoyLog.i(TJEvent.TAG, "Send request delivered successfully for event " + TJEvent.this.eventData.name + ", contentAvailable: " + TJEvent.this.contentAvailable);
                            TJEvent.this.callback.sendEventCompleted(TJEvent.this, TJEvent.this.contentAvailable);
                            return;
                    }
                }
            }
        }.start();
    }

    public void setParameters(Map<String, String> map) {
        this.eventParams = map;
    }

    public void showContent() {
        TapjoyLog.i(TAG, "showContent() called for event " + this.eventData.name);
        if (!this.contentAvailable) {
            TapjoyLog.e(TAG, "Cannot show content for non-200 send event");
        } else if (this.callback == null) {
            TapjoyLog.e(TAG, "TJEventCallback is null");
        } else if (TapjoyConnectCore.isViewOpen()) {
            TapjoyLog.w(TAG, "Only one view can be presented at a time.");
        } else {
            TapjoyConnectCore.viewWillOpen(4);
            Intent intent = new Intent(this.context, TJAdUnitView.class);
            intent.putExtra(TJAdUnitConstants.EXTRA_VIEW_TYPE, 1);
            intent.putExtra(TJAdUnitConstants.EXTRA_TJEVENT, this.eventData);
            intent.setFlags(DriveFile.MODE_READ_ONLY);
            this.context.startActivity(intent);
            this.contentAvailable = false;
            this.contentReady = false;
        }
    }

    public void trackEventForOfflineDelivery() {
        TapjoyLog.i(TAG, "Tracking event " + this.eventData.name + " for offline delivery");
        this.urlParams.remove("timestamp");
        this.urlParams.remove(TapjoyConstants.TJC_VERIFIER);
        TapjoyConnectCore.saveOfflineLog(this.eventData.url + TapjoyUtil.convertURLParams(this.urlParams, false));
    }
}
