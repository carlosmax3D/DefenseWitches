package com.threatmetrix.TrustDefenderMobile;

import android.net.http.AndroidHttpClient;
import android.util.Log;
import com.threatmetrix.TrustDefenderMobile.HttpRunner;
import com.threatmetrix.TrustDefenderMobile.TrustDefenderMobile;
import java.io.IOException;
import java.util.Map;

class HttpConfigRunner extends HttpRunner {
    private static final String TAG = HttpConfigRunner.class.getName();
    public TDConfiguration m_config = null;

    public HttpConfigRunner(AndroidHttpClient androidHttpClient, String str, HttpParameterMap httpParameterMap, Map<String, String> map, TrustDefenderMobile trustDefenderMobile) {
        super(androidHttpClient, HttpRunner.HttpRunnerType.GET, str, httpParameterMap, map, trustDefenderMobile);
    }

    public final TrustDefenderMobile.THMStatusCode getStatusCode() {
        return this.m_connection.getStatus() == TrustDefenderMobile.THMStatusCode.THM_OK ? (this.m_config == null || !this.m_config.isUsable()) ? TrustDefenderMobile.THMStatusCode.THM_ConfigurationError : TrustDefenderMobile.THMStatusCode.THM_OK : super.getStatusCode();
    }

    public void run() {
        String str = TAG;
        new StringBuilder("starting retrieval: ").append(this.m_url).append("?").append(this.m_params.getUrlEncodedParamString());
        this.m_config = null;
        super.run();
        if (getHttpStatusCode() == 200) {
            this.m_config = new TDConfiguration();
            try {
                this.m_config.parseConfigFromStream(this.m_connection.getResponse().getEntity().getContent());
            } catch (IOException e) {
                Log.e(TAG, "IO Error", e);
            }
        }
    }
}
