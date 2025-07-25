package com.threatmetrix.TrustDefenderMobile;

import android.net.Proxy;
import java.lang.reflect.Method;

class ProxyWrapper extends WrapperHelper {
    private static final String TAG = ProxyWrapper.class.getName();
    private static final Method m_getDefaultHost = getMethod(Proxy.class, "getDefaultHost", new Class[0]);
    private static final Method m_getDefaultPort = getMethod(Proxy.class, "getDefaultPort", new Class[0]);
    private String m_host = null;
    private int m_port = 0;

    public ProxyWrapper() {
        String property = System.getProperty("http.proxyHost");
        if (property != null && !property.isEmpty()) {
            this.m_host = property;
        }
        String property2 = System.getProperty("http.proxyPort");
        if (property2 != null && !property2.isEmpty()) {
            try {
                this.m_port = Integer.parseInt(property2);
            } catch (NumberFormatException e) {
            }
        }
        if (this.m_host == null || this.m_port == 0) {
            Integer num = (Integer) invoke((Object) null, m_getDefaultPort, new Object[0]);
            if (num != null) {
                this.m_port = num.intValue();
            }
            String str = (String) invoke((Object) null, m_getDefaultHost, new Object[0]);
            if (str != null) {
                this.m_host = str;
            }
        }
    }

    public final String getHost() {
        return this.m_host;
    }

    public final int getPort() {
        return this.m_port;
    }
}
