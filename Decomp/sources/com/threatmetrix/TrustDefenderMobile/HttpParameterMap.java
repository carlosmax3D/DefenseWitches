package com.threatmetrix.TrustDefenderMobile;

import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

class HttpParameterMap extends HashMap<String, String> {
    private static final String TAG = HttpParameterMap.class.getName();
    private int m_postValueLengthLimit = 0;

    HttpParameterMap() {
    }

    private int getPostValueLengthLimit() {
        return this.m_postValueLengthLimit;
    }

    public final HttpParameterMap add(String str, String str2) {
        put(str, str2);
        return this;
    }

    public final HttpParameterMap add(String str, String str2, boolean z) {
        if (str2 == null || str2.isEmpty()) {
            put(str, str2);
        } else {
            put(str, str2.toLowerCase(Locale.US));
        }
        return this;
    }

    public final UrlEncodedFormEntity getEntity() {
        ArrayList arrayList = new ArrayList();
        for (String str : keySet()) {
            String str2 = (String) get(str);
            if (!str2.isEmpty()) {
                if (this.m_postValueLengthLimit != 0 && str2.length() > this.m_postValueLengthLimit) {
                    str2 = str2.substring(0, this.m_postValueLengthLimit);
                }
                arrayList.add(new BasicNameValuePair(str, str2));
            }
        }
        try {
            return new UrlEncodedFormEntity(arrayList, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Failed url encoding", e);
            return null;
        }
    }

    public final String getUrlEncodedParamString() {
        StringBuilder sb = new StringBuilder();
        for (String str : keySet()) {
            String str2 = (String) get(str);
            if (str2 != null && !str2.isEmpty()) {
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(str);
                if (this.m_postValueLengthLimit != 0 && str2.length() > this.m_postValueLengthLimit) {
                    str2 = str2.substring(0, this.m_postValueLengthLimit);
                }
                sb.append("=");
                sb.append(StringUtils.urlEncode(str2));
            }
        }
        return sb.toString();
    }

    public final void setPostValueLengthLimit(int i) {
        this.m_postValueLengthLimit = MotionEventCompat.ACTION_MASK;
    }
}
