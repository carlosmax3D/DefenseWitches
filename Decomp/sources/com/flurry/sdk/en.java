package com.flurry.sdk;

import android.os.Build;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

public class en extends fg {
    private static final String a = en.class.getSimpleName();
    private static SSLContext b;
    private static HostnameVerifier c;
    private String d;
    private a e;
    private int f = 10000;
    private int i = 15000;
    private boolean j = true;
    private final ds<String, String> k = new ds<>();
    private c l;
    /* access modifiers changed from: private */
    public HttpURLConnection m;
    /* access modifiers changed from: private */
    public HttpClient n;
    private boolean o;
    private boolean p;
    private Exception q;
    private int r = -1;
    private final ds<String, String> s = new ds<>();
    private final Object t = new Object();

    public enum a {
        kUnknown,
        kGet,
        kPost,
        kPut,
        kDelete,
        kHead;

        public HttpRequestBase a(String str) {
            switch (this) {
                case kPost:
                    return new HttpPost(str);
                case kPut:
                    return new HttpPut(str);
                case kDelete:
                    return new HttpDelete(str);
                case kHead:
                    return new HttpHead(str);
                case kGet:
                    return new HttpGet(str);
                default:
                    return null;
            }
        }

        public String toString() {
            switch (this) {
                case kPost:
                    return "POST";
                case kPut:
                    return "PUT";
                case kDelete:
                    return "DELETE";
                case kHead:
                    return "HEAD";
                case kGet:
                    return "GET";
                default:
                    return null;
            }
        }
    }

    public static class b implements c {
        public void a(en enVar) {
        }

        public void a(en enVar, InputStream inputStream) throws Exception {
        }

        public void a(en enVar, OutputStream outputStream) throws Exception {
        }
    }

    public interface c {
        void a(en enVar);

        void a(en enVar, InputStream inputStream) throws Exception;

        void a(en enVar, OutputStream outputStream) throws Exception;
    }

    private void a(InputStream inputStream) throws Exception {
        if (this.l != null && !b() && inputStream != null) {
            this.l.a(this, inputStream);
        }
    }

    /* access modifiers changed from: private */
    public void a(OutputStream outputStream) throws Exception {
        if (this.l != null && !b() && outputStream != null) {
            this.l.a(this, outputStream);
        }
    }

    private static synchronized SSLContext m() {
        SSLContext sSLContext;
        synchronized (en.class) {
            if (b != null) {
                sSLContext = b;
            } else {
                try {
                    TrustManager[] trustManagerArr = {new ej((KeyStore) null)};
                    b = SSLContext.getInstance("TLS");
                    b.init((KeyManager[]) null, trustManagerArr, new SecureRandom());
                } catch (Exception e2) {
                    eo.a(3, a, "Exception creating SSL context", e2);
                }
                sSLContext = b;
            }
        }
        return sSLContext;
    }

    private static synchronized HostnameVerifier n() {
        HostnameVerifier hostnameVerifier;
        synchronized (en.class) {
            if (c != null) {
                hostnameVerifier = c;
            } else {
                c = new eh();
                hostnameVerifier = c;
            }
        }
        return hostnameVerifier;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: java.io.BufferedInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.io.BufferedInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.io.BufferedInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.io.BufferedInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: java.io.OutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: java.io.BufferedInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.io.BufferedInputStream} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void o() throws java.lang.Exception {
        /*
            r7 = this;
            r2 = 0
            boolean r0 = r7.p
            if (r0 == 0) goto L_0x0006
        L_0x0005:
            return
        L_0x0006:
            java.net.URL r0 = new java.net.URL
            java.lang.String r1 = r7.d
            r0.<init>(r1)
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ all -> 0x0092 }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ all -> 0x0092 }
            r7.m = r0     // Catch:{ all -> 0x0092 }
            java.net.HttpURLConnection r0 = r7.m     // Catch:{ all -> 0x0092 }
            int r1 = r7.f     // Catch:{ all -> 0x0092 }
            r0.setConnectTimeout(r1)     // Catch:{ all -> 0x0092 }
            java.net.HttpURLConnection r0 = r7.m     // Catch:{ all -> 0x0092 }
            int r1 = r7.i     // Catch:{ all -> 0x0092 }
            r0.setReadTimeout(r1)     // Catch:{ all -> 0x0092 }
            java.net.HttpURLConnection r0 = r7.m     // Catch:{ all -> 0x0092 }
            com.flurry.sdk.en$a r1 = r7.e     // Catch:{ all -> 0x0092 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0092 }
            r0.setRequestMethod(r1)     // Catch:{ all -> 0x0092 }
            java.net.HttpURLConnection r0 = r7.m     // Catch:{ all -> 0x0092 }
            boolean r1 = r7.j     // Catch:{ all -> 0x0092 }
            r0.setInstanceFollowRedirects(r1)     // Catch:{ all -> 0x0092 }
            java.net.HttpURLConnection r0 = r7.m     // Catch:{ all -> 0x0092 }
            com.flurry.sdk.en$a r1 = com.flurry.sdk.en.a.kPost     // Catch:{ all -> 0x0092 }
            com.flurry.sdk.en$a r3 = r7.e     // Catch:{ all -> 0x0092 }
            boolean r1 = r1.equals(r3)     // Catch:{ all -> 0x0092 }
            r0.setDoOutput(r1)     // Catch:{ all -> 0x0092 }
            java.net.HttpURLConnection r0 = r7.m     // Catch:{ all -> 0x0092 }
            r1 = 1
            r0.setDoInput(r1)     // Catch:{ all -> 0x0092 }
            boolean r0 = com.flurry.sdk.eo.d()     // Catch:{ all -> 0x0092 }
            if (r0 == 0) goto L_0x006a
            java.net.HttpURLConnection r0 = r7.m     // Catch:{ all -> 0x0092 }
            boolean r0 = r0 instanceof javax.net.ssl.HttpsURLConnection     // Catch:{ all -> 0x0092 }
            if (r0 == 0) goto L_0x006a
            java.net.HttpURLConnection r0 = r7.m     // Catch:{ all -> 0x0092 }
            javax.net.ssl.HttpsURLConnection r0 = (javax.net.ssl.HttpsURLConnection) r0     // Catch:{ all -> 0x0092 }
            javax.net.ssl.HostnameVerifier r1 = n()     // Catch:{ all -> 0x0092 }
            r0.setHostnameVerifier(r1)     // Catch:{ all -> 0x0092 }
            javax.net.ssl.SSLContext r1 = m()     // Catch:{ all -> 0x0092 }
            javax.net.ssl.SSLSocketFactory r1 = r1.getSocketFactory()     // Catch:{ all -> 0x0092 }
            r0.setSSLSocketFactory(r1)     // Catch:{ all -> 0x0092 }
        L_0x006a:
            com.flurry.sdk.ds<java.lang.String, java.lang.String> r0 = r7.k     // Catch:{ all -> 0x0092 }
            java.util.Collection r0 = r0.b()     // Catch:{ all -> 0x0092 }
            java.util.Iterator r3 = r0.iterator()     // Catch:{ all -> 0x0092 }
        L_0x0074:
            boolean r0 = r3.hasNext()     // Catch:{ all -> 0x0092 }
            if (r0 == 0) goto L_0x0097
            java.lang.Object r0 = r3.next()     // Catch:{ all -> 0x0092 }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ all -> 0x0092 }
            java.net.HttpURLConnection r4 = r7.m     // Catch:{ all -> 0x0092 }
            java.lang.Object r1 = r0.getKey()     // Catch:{ all -> 0x0092 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0092 }
            java.lang.Object r0 = r0.getValue()     // Catch:{ all -> 0x0092 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0092 }
            r4.addRequestProperty(r1, r0)     // Catch:{ all -> 0x0092 }
            goto L_0x0074
        L_0x0092:
            r0 = move-exception
            r7.r()
            throw r0
        L_0x0097:
            com.flurry.sdk.en$a r0 = com.flurry.sdk.en.a.kGet     // Catch:{ all -> 0x0092 }
            com.flurry.sdk.en$a r1 = r7.e     // Catch:{ all -> 0x0092 }
            boolean r0 = r0.equals(r1)     // Catch:{ all -> 0x0092 }
            if (r0 != 0) goto L_0x00b4
            com.flurry.sdk.en$a r0 = com.flurry.sdk.en.a.kPost     // Catch:{ all -> 0x0092 }
            com.flurry.sdk.en$a r1 = r7.e     // Catch:{ all -> 0x0092 }
            boolean r0 = r0.equals(r1)     // Catch:{ all -> 0x0092 }
            if (r0 != 0) goto L_0x00b4
            java.net.HttpURLConnection r0 = r7.m     // Catch:{ all -> 0x0092 }
            java.lang.String r1 = "Accept-Encoding"
            java.lang.String r3 = ""
            r0.setRequestProperty(r1, r3)     // Catch:{ all -> 0x0092 }
        L_0x00b4:
            boolean r0 = r7.p     // Catch:{ all -> 0x0092 }
            if (r0 == 0) goto L_0x00bd
            r7.r()
            goto L_0x0005
        L_0x00bd:
            com.flurry.sdk.en$a r0 = com.flurry.sdk.en.a.kPost     // Catch:{ all -> 0x0092 }
            com.flurry.sdk.en$a r1 = r7.e     // Catch:{ all -> 0x0092 }
            boolean r0 = r0.equals(r1)     // Catch:{ all -> 0x0092 }
            if (r0 == 0) goto L_0x00db
            java.net.HttpURLConnection r0 = r7.m     // Catch:{ all -> 0x011d }
            java.io.OutputStream r3 = r0.getOutputStream()     // Catch:{ all -> 0x011d }
            java.io.BufferedOutputStream r1 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x0171 }
            r1.<init>(r3)     // Catch:{ all -> 0x0171 }
            r7.a((java.io.OutputStream) r1)     // Catch:{ all -> 0x0175 }
            com.flurry.sdk.fe.a((java.io.Closeable) r1)     // Catch:{ all -> 0x0092 }
            com.flurry.sdk.fe.a((java.io.Closeable) r3)     // Catch:{ all -> 0x0092 }
        L_0x00db:
            java.net.HttpURLConnection r0 = r7.m     // Catch:{ all -> 0x0092 }
            int r0 = r0.getResponseCode()     // Catch:{ all -> 0x0092 }
            r7.r = r0     // Catch:{ all -> 0x0092 }
            java.net.HttpURLConnection r0 = r7.m     // Catch:{ all -> 0x0092 }
            java.util.Map r0 = r0.getHeaderFields()     // Catch:{ all -> 0x0092 }
            java.util.Set r0 = r0.entrySet()     // Catch:{ all -> 0x0092 }
            java.util.Iterator r3 = r0.iterator()     // Catch:{ all -> 0x0092 }
        L_0x00f1:
            boolean r0 = r3.hasNext()     // Catch:{ all -> 0x0092 }
            if (r0 == 0) goto L_0x0126
            java.lang.Object r0 = r3.next()     // Catch:{ all -> 0x0092 }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ all -> 0x0092 }
            java.lang.Object r1 = r0.getValue()     // Catch:{ all -> 0x0092 }
            java.util.List r1 = (java.util.List) r1     // Catch:{ all -> 0x0092 }
            java.util.Iterator r4 = r1.iterator()     // Catch:{ all -> 0x0092 }
        L_0x0107:
            boolean r1 = r4.hasNext()     // Catch:{ all -> 0x0092 }
            if (r1 == 0) goto L_0x00f1
            java.lang.Object r1 = r4.next()     // Catch:{ all -> 0x0092 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0092 }
            com.flurry.sdk.ds<java.lang.String, java.lang.String> r5 = r7.s     // Catch:{ all -> 0x0092 }
            java.lang.Object r6 = r0.getKey()     // Catch:{ all -> 0x0092 }
            r5.a(r6, r1)     // Catch:{ all -> 0x0092 }
            goto L_0x0107
        L_0x011d:
            r0 = move-exception
            r1 = r2
        L_0x011f:
            com.flurry.sdk.fe.a((java.io.Closeable) r1)     // Catch:{ all -> 0x0092 }
            com.flurry.sdk.fe.a((java.io.Closeable) r2)     // Catch:{ all -> 0x0092 }
            throw r0     // Catch:{ all -> 0x0092 }
        L_0x0126:
            com.flurry.sdk.en$a r0 = com.flurry.sdk.en.a.kGet     // Catch:{ all -> 0x0092 }
            com.flurry.sdk.en$a r1 = r7.e     // Catch:{ all -> 0x0092 }
            boolean r0 = r0.equals(r1)     // Catch:{ all -> 0x0092 }
            if (r0 != 0) goto L_0x013f
            com.flurry.sdk.en$a r0 = com.flurry.sdk.en.a.kPost     // Catch:{ all -> 0x0092 }
            com.flurry.sdk.en$a r1 = r7.e     // Catch:{ all -> 0x0092 }
            boolean r0 = r0.equals(r1)     // Catch:{ all -> 0x0092 }
            if (r0 != 0) goto L_0x013f
            r7.r()
            goto L_0x0005
        L_0x013f:
            boolean r0 = r7.p     // Catch:{ all -> 0x0092 }
            if (r0 == 0) goto L_0x0148
            r7.r()
            goto L_0x0005
        L_0x0148:
            java.net.HttpURLConnection r0 = r7.m     // Catch:{ all -> 0x0161 }
            java.io.InputStream r3 = r0.getInputStream()     // Catch:{ all -> 0x0161 }
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ all -> 0x016a }
            r1.<init>(r3)     // Catch:{ all -> 0x016a }
            r7.a((java.io.InputStream) r1)     // Catch:{ all -> 0x016d }
            com.flurry.sdk.fe.a((java.io.Closeable) r1)     // Catch:{ all -> 0x0092 }
            com.flurry.sdk.fe.a((java.io.Closeable) r3)     // Catch:{ all -> 0x0092 }
            r7.r()
            goto L_0x0005
        L_0x0161:
            r0 = move-exception
            r1 = r2
        L_0x0163:
            com.flurry.sdk.fe.a((java.io.Closeable) r2)     // Catch:{ all -> 0x0092 }
            com.flurry.sdk.fe.a((java.io.Closeable) r1)     // Catch:{ all -> 0x0092 }
            throw r0     // Catch:{ all -> 0x0092 }
        L_0x016a:
            r0 = move-exception
            r1 = r3
            goto L_0x0163
        L_0x016d:
            r0 = move-exception
            r2 = r1
            r1 = r3
            goto L_0x0163
        L_0x0171:
            r0 = move-exception
            r1 = r2
            r2 = r3
            goto L_0x011f
        L_0x0175:
            r0 = move-exception
            r2 = r3
            goto L_0x011f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.en.o():void");
    }

    private void p() throws Exception {
        BufferedInputStream bufferedInputStream;
        InputStream inputStream = null;
        if (!this.p) {
            HttpPost a2 = this.e.a(this.d);
            for (Map.Entry next : this.k.b()) {
                a2.setHeader((String) next.getKey(), (String) next.getValue());
            }
            if (!a.kGet.equals(this.e) && !a.kPost.equals(this.e)) {
                a2.removeHeaders("Accept-Encoding");
            }
            if (a.kPost.equals(this.e)) {
                a2.setEntity(new AbstractHttpEntity() {
                    public InputStream getContent() throws IOException {
                        throw new UnsupportedOperationException();
                    }

                    public long getContentLength() {
                        return -1;
                    }

                    public boolean isRepeatable() {
                        return false;
                    }

                    public boolean isStreaming() {
                        return false;
                    }

                    /* JADX WARNING: Removed duplicated region for block: B:19:0x001f A[Catch:{ all -> 0x0012 }] */
                    /* JADX WARNING: Removed duplicated region for block: B:21:0x0025 A[Catch:{ all -> 0x0012 }] */
                    /* JADX WARNING: Unknown top exception splitter block from list: {B:9:0x0011=Splitter:B:9:0x0011, B:16:0x0019=Splitter:B:16:0x0019} */
                    @android.annotation.TargetApi(9)
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public void writeTo(java.io.OutputStream r5) throws java.io.IOException {
                        /*
                            r4 = this;
                            r2 = 0
                            java.io.BufferedOutputStream r1 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x000f, Exception -> 0x0017, all -> 0x002f }
                            r1.<init>(r5)     // Catch:{ IOException -> 0x000f, Exception -> 0x0017, all -> 0x002f }
                            com.flurry.sdk.en r0 = com.flurry.sdk.en.this     // Catch:{ IOException -> 0x0034, Exception -> 0x0032 }
                            r0.a((java.io.OutputStream) r1)     // Catch:{ IOException -> 0x0034, Exception -> 0x0032 }
                            com.flurry.sdk.fe.a((java.io.Closeable) r1)
                            return
                        L_0x000f:
                            r0 = move-exception
                            r1 = r2
                        L_0x0011:
                            throw r0     // Catch:{ all -> 0x0012 }
                        L_0x0012:
                            r0 = move-exception
                        L_0x0013:
                            com.flurry.sdk.fe.a((java.io.Closeable) r1)
                            throw r0
                        L_0x0017:
                            r0 = move-exception
                            r1 = r2
                        L_0x0019:
                            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0012 }
                            r3 = 9
                            if (r2 < r3) goto L_0x0025
                            java.io.IOException r2 = new java.io.IOException     // Catch:{ all -> 0x0012 }
                            r2.<init>(r0)     // Catch:{ all -> 0x0012 }
                            throw r2     // Catch:{ all -> 0x0012 }
                        L_0x0025:
                            java.io.IOException r2 = new java.io.IOException     // Catch:{ all -> 0x0012 }
                            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0012 }
                            r2.<init>(r0)     // Catch:{ all -> 0x0012 }
                            throw r2     // Catch:{ all -> 0x0012 }
                        L_0x002f:
                            r0 = move-exception
                            r1 = r2
                            goto L_0x0013
                        L_0x0032:
                            r0 = move-exception
                            goto L_0x0019
                        L_0x0034:
                            r0 = move-exception
                            goto L_0x0011
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.en.AnonymousClass1.writeTo(java.io.OutputStream):void");
                    }
                });
            }
            try {
                BasicHttpParams basicHttpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(basicHttpParams, this.f);
                HttpConnectionParams.setSoTimeout(basicHttpParams, this.i);
                basicHttpParams.setParameter("http.protocol.handle-redirects", Boolean.valueOf(this.j));
                this.n = ek.a(basicHttpParams);
                HttpResponse execute = this.n.execute(a2);
                if (this.p) {
                    throw new Exception("Request cancelled");
                }
                if (execute != null) {
                    this.r = execute.getStatusLine().getStatusCode();
                    Header[] allHeaders = execute.getAllHeaders();
                    if (allHeaders != null) {
                        for (Header elements : allHeaders) {
                            for (HeaderElement headerElement : elements.getElements()) {
                                this.s.a(headerElement.getName(), headerElement.getValue());
                            }
                        }
                    }
                    if (!a.kGet.equals(this.e) && !a.kPost.equals(this.e)) {
                        return;
                    }
                    if (this.p) {
                        throw new Exception("Request cancelled");
                    }
                    HttpEntity entity = execute.getEntity();
                    if (entity != null) {
                        try {
                            InputStream content = entity.getContent();
                            try {
                                bufferedInputStream = new BufferedInputStream(content);
                            } catch (Throwable th) {
                                th = th;
                                bufferedInputStream = null;
                                inputStream = content;
                                fe.a((Closeable) bufferedInputStream);
                                fe.a((Closeable) inputStream);
                                throw th;
                            }
                            try {
                                a((InputStream) bufferedInputStream);
                                fe.a((Closeable) bufferedInputStream);
                                fe.a((Closeable) content);
                            } catch (Throwable th2) {
                                th = th2;
                                inputStream = content;
                                fe.a((Closeable) bufferedInputStream);
                                fe.a((Closeable) inputStream);
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            bufferedInputStream = null;
                        }
                    }
                }
                r();
            } finally {
                r();
            }
        }
    }

    private void q() {
        if (this.l != null && !b()) {
            this.l.a(this);
        }
    }

    private void r() {
        if (!this.o) {
            this.o = true;
            if (this.m != null) {
                this.m.disconnect();
            }
            if (this.n != null) {
                this.n.getConnectionManager().shutdown();
            }
        }
    }

    private void s() {
        if (!this.o) {
            this.o = true;
            if (this.m != null || this.n != null) {
                new Thread() {
                    public void run() {
                        if (en.this.m != null) {
                            en.this.m.disconnect();
                        }
                        if (en.this.n != null) {
                            en.this.n.getConnectionManager().shutdown();
                        }
                    }
                }.start();
            }
        }
    }

    public void a() {
        try {
            if (this.d != null) {
                if (!ev.a().c()) {
                    eo.a(3, a, "Network not available, aborting http request: " + this.d);
                    q();
                    return;
                }
                if (this.e == null || a.kUnknown.equals(this.e)) {
                    this.e = a.kGet;
                }
                if (Build.VERSION.SDK_INT >= 9) {
                    o();
                } else {
                    p();
                }
                eo.a(4, a, "HTTP status: " + this.r + " for url: " + this.d);
                q();
            }
        } catch (Exception e2) {
            eo.a(4, a, "HTTP status: " + this.r + " for url: " + this.d);
            eo.a(3, a, "Exception during http request: " + this.d, e2);
            this.q = e2;
        } finally {
            q();
        }
    }

    public void a(a aVar) {
        this.e = aVar;
    }

    public void a(c cVar) {
        this.l = cVar;
    }

    public void a(String str) {
        this.d = str;
    }

    public void a(String str, String str2) {
        this.k.a(str, str2);
    }

    public void a(boolean z) {
        this.j = z;
    }

    public List<String> b(String str) {
        if (str == null) {
            return null;
        }
        return this.s.a(str);
    }

    public boolean b() {
        boolean z;
        synchronized (this.t) {
            z = this.p;
        }
        return z;
    }

    public boolean c() {
        return !f() && d();
    }

    public boolean d() {
        return this.r >= 200 && this.r < 400;
    }

    public int e() {
        return this.r;
    }

    public boolean f() {
        return this.q != null;
    }

    public void g() {
        synchronized (this.t) {
            this.p = true;
        }
        s();
    }

    public void h() {
        g();
    }
}
