package com.threatmetrix.TrustDefenderMobile;

import android.net.http.AndroidHttpClient;
import android.util.Log;
import com.threatmetrix.TrustDefenderMobile.TrustDefenderMobile;
import java.util.Map;

class HttpRunner implements Runnable {
    private static final String TAG = HttpRunner.class.getName();
    final URLConnection m_connection;
    final HttpParameterMap m_params;
    private final TrustDefenderMobile m_sdk;
    private final HttpRunnerType m_type;
    final String m_url;

    enum HttpRunnerType {
        GET,
        GET_CONSUME,
        POST,
        POST_CONSUME
    }

    private HttpRunner(AndroidHttpClient androidHttpClient, HttpRunnerType httpRunnerType, String str) {
        this.m_connection = new URLConnection(androidHttpClient);
        this.m_type = httpRunnerType;
        this.m_url = str;
        this.m_params = new HttpParameterMap();
        this.m_sdk = null;
    }

    public HttpRunner(AndroidHttpClient androidHttpClient, HttpRunnerType httpRunnerType, String str, HttpParameterMap httpParameterMap, Map<String, String> map, TrustDefenderMobile trustDefenderMobile) {
        this.m_connection = new URLConnection(androidHttpClient);
        this.m_connection.addHeaders(map);
        this.m_type = httpRunnerType;
        this.m_url = str;
        this.m_params = httpParameterMap;
        this.m_sdk = trustDefenderMobile;
    }

    public final void abort() {
        this.m_connection.abort();
    }

    /* access modifiers changed from: package-private */
    public final URLConnection getConnection() {
        return this.m_connection;
    }

    public final int getHttpStatusCode() {
        if (this.m_connection.getResponse() != null) {
            return this.m_connection.getResponse().getStatusLine().getStatusCode();
        }
        return 0;
    }

    public TrustDefenderMobile.THMStatusCode getStatusCode() {
        return this.m_connection.getStatus();
    }

    public void run() {
        String str = TAG;
        new StringBuilder("starting retrieval: ").append(this.m_url);
        long j = -1;
        if (this.m_type == HttpRunnerType.GET || this.m_type == HttpRunnerType.GET_CONSUME) {
            j = this.m_connection.get(this.m_url + "?" + this.m_params.getUrlEncodedParamString());
        } else if (this.m_type == HttpRunnerType.POST || this.m_type == HttpRunnerType.POST_CONSUME) {
            j = this.m_connection.post(this.m_url, this.m_params.getEntity());
        }
        if (j < 0) {
            Log.w(TAG, "failed to retrieve from " + this.m_connection.getHost());
            if (this.m_sdk != null) {
                this.m_sdk.setStatus(this.m_connection.getStatus());
                return;
            }
            return;
        }
        String str2 = TAG;
        new StringBuilder("retrieved: ").append(this.m_connection.getURL());
        if (j != 200) {
            Log.w(TAG, "error (" + j + ") status on request to " + this.m_connection.getHost());
        } else if (this.m_type == HttpRunnerType.GET_CONSUME || this.m_type == HttpRunnerType.POST_CONSUME) {
            String str3 = TAG;
            this.m_connection.consumeContent();
        }
    }
}
