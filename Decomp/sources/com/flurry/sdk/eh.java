package com.flurry.sdk;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class eh implements HostnameVerifier {
    public boolean verify(String str, SSLSession sSLSession) {
        return true;
    }
}
