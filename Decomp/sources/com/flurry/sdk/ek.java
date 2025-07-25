package com.flurry.sdk;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.HttpParams;

public final class ek {
    private static SchemeRegistry a;

    public static HttpClient a(HttpParams httpParams) {
        return new DefaultHttpClient(new SingleClientConnManager(httpParams, a()), httpParams);
    }

    private static synchronized SchemeRegistry a() {
        SchemeRegistry schemeRegistry;
        synchronized (ek.class) {
            if (a != null) {
                schemeRegistry = a;
            } else {
                a = new SchemeRegistry();
                a.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                if (eo.d()) {
                    a.register(new Scheme("https", new ei(), 443));
                } else {
                    a.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
                }
                schemeRegistry = a;
            }
        }
        return schemeRegistry;
    }
}
