package com.threatmetrix.TrustDefenderMobile;

import android.content.Context;
import android.net.SSLCertificateSocketFactory;
import android.net.SSLSessionCache;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.util.Log;
import com.threatmetrix.TrustDefenderMobile.TrustDefenderMobile;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.net.ssl.SSLPeerUnverifiedException;
import jp.stargarage.g2metrics.BuildConfig;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpParams;

class URLConnection {
    private static final String TAG = URLConnection.class.toString();
    private final ArrayList<BasicHeader> m_headers = new ArrayList<>();
    private final AndroidHttpClient m_httpClient;
    private HttpRequestBase m_request;
    private HttpResponse m_response;
    private TrustDefenderMobile.THMStatusCode m_status;

    URLConnection(AndroidHttpClient androidHttpClient) {
        this.m_httpClient = androidHttpClient;
        this.m_status = TrustDefenderMobile.THMStatusCode.THM_NotYet;
        this.m_request = null;
    }

    private void addHeader(String str, String str2) {
        this.m_headers.add(new BasicHeader(str, str2));
    }

    private static String getContentCharSet(HttpEntity httpEntity) {
        NameValuePair parameterByName;
        if (httpEntity.getContentType() == null) {
            return null;
        }
        HeaderElement[] elements = httpEntity.getContentType().getElements();
        if (elements.length <= 0 || (parameterByName = elements[0].getParameterByName("charset")) == null) {
            return null;
        }
        return parameterByName.getValue();
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0038 A[SYNTHETIC, Splitter:B:16:0x0038] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0040 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x009c A[SYNTHETIC, Splitter:B:51:0x009c] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00a7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getResponseBody() {
        /*
            r14 = this;
            org.apache.http.HttpResponse r10 = r14.m_response
            if (r10 != 0) goto L_0x0007
            java.lang.String r10 = ""
        L_0x0006:
            return r10
        L_0x0007:
            r7 = 0
            org.apache.http.HttpResponse r10 = r14.m_response
            org.apache.http.HttpEntity r4 = r10.getEntity()
            r0 = 0
            java.io.InputStream r5 = r4.getContent()     // Catch:{ IOException -> 0x002b }
            if (r5 != 0) goto L_0x0018
            java.lang.String r10 = ""
            goto L_0x0006
        L_0x0018:
            long r10 = r4.getContentLength()     // Catch:{ IOException -> 0x002b }
            r12 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 <= 0) goto L_0x0043
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException     // Catch:{ IOException -> 0x002b }
            java.lang.String r11 = "HTTP entity too large to be buffered in memory"
            r10.<init>(r11)     // Catch:{ IOException -> 0x002b }
            throw r10     // Catch:{ IOException -> 0x002b }
        L_0x002b:
            r3 = move-exception
        L_0x002c:
            java.lang.String r10 = TAG     // Catch:{ all -> 0x0099 }
            java.lang.String r11 = "Failed to fetch"
            android.util.Log.e(r10, r11, r3)     // Catch:{ all -> 0x0099 }
            r3.printStackTrace()     // Catch:{ all -> 0x0099 }
            if (r7 == 0) goto L_0x003e
            r4.consumeContent()     // Catch:{ IOException -> 0x0095 }
            r7.close()     // Catch:{ IOException -> 0x0095 }
        L_0x003e:
            if (r0 != 0) goto L_0x00a7
            java.lang.String r10 = ""
            goto L_0x0006
        L_0x0043:
            r2 = 0
            org.apache.http.Header r10 = r4.getContentType()     // Catch:{ IOException -> 0x002b }
            if (r10 == 0) goto L_0x0064
            org.apache.http.Header r10 = r4.getContentType()     // Catch:{ IOException -> 0x002b }
            org.apache.http.HeaderElement[] r10 = r10.getElements()     // Catch:{ IOException -> 0x002b }
            int r11 = r10.length     // Catch:{ IOException -> 0x002b }
            if (r11 <= 0) goto L_0x0064
            r11 = 0
            r10 = r10[r11]     // Catch:{ IOException -> 0x002b }
            java.lang.String r11 = "charset"
            org.apache.http.NameValuePair r10 = r10.getParameterByName(r11)     // Catch:{ IOException -> 0x002b }
            if (r10 == 0) goto L_0x0064
            java.lang.String r2 = r10.getValue()     // Catch:{ IOException -> 0x002b }
        L_0x0064:
            if (r2 != 0) goto L_0x0068
            java.lang.String r2 = "ISO-8859-1"
        L_0x0068:
            java.io.InputStreamReader r8 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x002b }
            r8.<init>(r5, r2)     // Catch:{ IOException -> 0x002b }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00b4, all -> 0x00ad }
            r1.<init>()     // Catch:{ IOException -> 0x00b4, all -> 0x00ad }
            r10 = 1024(0x400, float:1.435E-42)
            char[] r9 = new char[r10]     // Catch:{ IOException -> 0x0082, all -> 0x00b0 }
        L_0x0076:
            int r6 = r8.read(r9)     // Catch:{ IOException -> 0x0082, all -> 0x00b0 }
            r10 = -1
            if (r6 == r10) goto L_0x0086
            r10 = 0
            r1.append(r9, r10, r6)     // Catch:{ IOException -> 0x0082, all -> 0x00b0 }
            goto L_0x0076
        L_0x0082:
            r3 = move-exception
            r0 = r1
            r7 = r8
            goto L_0x002c
        L_0x0086:
            r4.consumeContent()     // Catch:{ IOException -> 0x008f }
            r8.close()     // Catch:{ IOException -> 0x008f }
            r0 = r1
            r7 = r8
            goto L_0x003e
        L_0x008f:
            r10 = move-exception
            java.lang.String r10 = TAG
            r0 = r1
            r7 = r8
            goto L_0x003e
        L_0x0095:
            r10 = move-exception
            java.lang.String r10 = TAG
            goto L_0x003e
        L_0x0099:
            r10 = move-exception
        L_0x009a:
            if (r7 == 0) goto L_0x00a2
            r4.consumeContent()     // Catch:{ IOException -> 0x00a3 }
            r7.close()     // Catch:{ IOException -> 0x00a3 }
        L_0x00a2:
            throw r10
        L_0x00a3:
            r11 = move-exception
            java.lang.String r11 = TAG
            goto L_0x00a2
        L_0x00a7:
            java.lang.String r10 = r0.toString()
            goto L_0x0006
        L_0x00ad:
            r10 = move-exception
            r7 = r8
            goto L_0x009a
        L_0x00b0:
            r10 = move-exception
            r0 = r1
            r7 = r8
            goto L_0x009a
        L_0x00b4:
            r3 = move-exception
            r7 = r8
            goto L_0x002c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.threatmetrix.TrustDefenderMobile.URLConnection.getResponseBody():java.lang.String");
    }

    private void go(HttpRequestBase httpRequestBase) {
        synchronized (this) {
            this.m_request = httpRequestBase;
        }
        Iterator<BasicHeader> it = this.m_headers.iterator();
        while (it.hasNext()) {
            this.m_request.addHeader(it.next());
        }
        HttpClientParams.setRedirecting(this.m_request.getParams(), true);
        ProxyWrapper proxyWrapper = new ProxyWrapper();
        if (proxyWrapper.getHost() == null || proxyWrapper.getHost().isEmpty()) {
            this.m_httpClient.getParams().setParameter("http.route.default-proxy", (Object) null);
        } else {
            this.m_httpClient.getParams().setParameter("http.route.default-proxy", new HttpHost(proxyWrapper.getHost(), proxyWrapper.getPort()));
        }
        try {
            this.m_response = this.m_httpClient.execute(this.m_request);
            this.m_status = TrustDefenderMobile.THMStatusCode.THM_OK;
        } catch (IOException e) {
            if (e.getCause() instanceof CertificateException) {
                this.m_status = TrustDefenderMobile.THMStatusCode.THM_HostVerification_Error;
            } else if (e instanceof SSLPeerUnverifiedException) {
                this.m_status = TrustDefenderMobile.THMStatusCode.THM_HostVerification_Error;
            } else if (e instanceof UnknownHostException) {
                this.m_status = TrustDefenderMobile.THMStatusCode.THM_HostNotFound_Error;
            } else if (e instanceof SocketTimeoutException) {
                this.m_status = TrustDefenderMobile.THMStatusCode.THM_NetworkTimeout_Error;
            } else if (this.m_status == TrustDefenderMobile.THMStatusCode.THM_NotYet) {
                this.m_status = TrustDefenderMobile.THMStatusCode.THM_Connection_Error;
            } else {
                String str = TAG;
            }
            Log.e(TAG, "Failed to retrieve URI", e);
        } catch (RuntimeException e2) {
            Log.e(TAG, "Caught runtime exception:", e2);
            this.m_status = TrustDefenderMobile.THMStatusCode.THM_Connection_Error;
        }
    }

    private void reset() {
        synchronized (this) {
            this.m_request = null;
        }
        this.m_response = null;
        this.m_headers.clear();
    }

    public static void setSSLSocketFactory(final Context context, HttpClient httpClient, final int i) {
        if (Build.VERSION.SDK_INT >= 14) {
            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", SSLCertificateSocketFactory.getHttpSocketFactory(i, new SSLSessionCache(context)), 443));
            return;
        }
        httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", new LayeredSocketFactory() {
            final SSLSocketFactory delegate = SSLCertificateSocketFactory.getHttpSocketFactory(r7, new SSLSessionCache(r5));

            private static void injectHostname(Socket socket, String str) {
                try {
                    Field declaredField = InetAddress.class.getDeclaredField("hostName");
                    declaredField.setAccessible(true);
                    declaredField.set(socket.getInetAddress(), str);
                } catch (Exception e) {
                }
            }

            public final Socket connectSocket(Socket socket, String str, int i, InetAddress inetAddress, int i2, HttpParams httpParams) throws IOException {
                return this.delegate.connectSocket(socket, str, i, inetAddress, i2, httpParams);
            }

            public final Socket createSocket() throws IOException {
                return this.delegate.createSocket();
            }

            public final Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
                try {
                    Field declaredField = InetAddress.class.getDeclaredField("hostName");
                    declaredField.setAccessible(true);
                    declaredField.set(socket.getInetAddress(), str);
                } catch (Exception e) {
                }
                return this.delegate.createSocket(socket, str, i, z);
            }

            public final boolean isSecure(Socket socket) throws IllegalArgumentException {
                return this.delegate.isSecure(socket);
            }
        }, 443));
    }

    private static void setSSLTimeout(Context context, HttpClient httpClient, int i) {
        httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", SSLCertificateSocketFactory.getHttpSocketFactory(i, new SSLSessionCache(context)), 443));
    }

    private static void workAroundReverseDnsBugInHoneycombAndEarlier(final Context context, HttpClient httpClient, final int i) {
        httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", new LayeredSocketFactory() {
            final SSLSocketFactory delegate = SSLCertificateSocketFactory.getHttpSocketFactory(i, new SSLSessionCache(context));

            private static void injectHostname(Socket socket, String str) {
                try {
                    Field declaredField = InetAddress.class.getDeclaredField("hostName");
                    declaredField.setAccessible(true);
                    declaredField.set(socket.getInetAddress(), str);
                } catch (Exception e) {
                }
            }

            public final Socket connectSocket(Socket socket, String str, int i, InetAddress inetAddress, int i2, HttpParams httpParams) throws IOException {
                return this.delegate.connectSocket(socket, str, i, inetAddress, i2, httpParams);
            }

            public final Socket createSocket() throws IOException {
                return this.delegate.createSocket();
            }

            public final Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
                try {
                    Field declaredField = InetAddress.class.getDeclaredField("hostName");
                    declaredField.setAccessible(true);
                    declaredField.set(socket.getInetAddress(), str);
                } catch (Exception e) {
                }
                return this.delegate.createSocket(socket, str, i, z);
            }

            public final boolean isSecure(Socket socket) throws IllegalArgumentException {
                return this.delegate.isSecure(socket);
            }
        }, 443));
    }

    public final void abort() {
        String str = TAG;
        synchronized (this) {
            if (this.m_request != null) {
                this.m_request.abort();
            }
        }
        this.m_status = TrustDefenderMobile.THMStatusCode.THM_Interrupted_Error;
    }

    /* access modifiers changed from: package-private */
    public final void addHeaders(Map<String, String> map) {
        if (map != null) {
            for (String next : map.keySet()) {
                this.m_headers.add(new BasicHeader(next, map.get(next)));
            }
        }
    }

    public final void consumeContent() {
        HttpEntity entity;
        if (this.m_response != null && (entity = this.m_response.getEntity()) != null) {
            try {
                entity.consumeContent();
            } catch (IOException e) {
                String str = TAG;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final long get(String str) {
        go(new HttpGet(str));
        if (this.m_response == null || this.m_status != TrustDefenderMobile.THMStatusCode.THM_OK) {
            return -1;
        }
        return (long) this.m_response.getStatusLine().getStatusCode();
    }

    public final String getHost() {
        return this.m_request != null ? this.m_request.getURI().getHost() : BuildConfig.FLAVOR;
    }

    public final HttpResponse getResponse() {
        return this.m_response;
    }

    public final TrustDefenderMobile.THMStatusCode getStatus() {
        return this.m_status;
    }

    public final String getURL() {
        return this.m_request != null ? this.m_request.getURI().getScheme() + "://" + this.m_request.getURI().getHost() + this.m_request.getURI().getPath() : BuildConfig.FLAVOR;
    }

    /* access modifiers changed from: package-private */
    public final long post(String str, HttpEntity httpEntity) {
        HttpPost httpPost = new HttpPost(str);
        httpPost.setEntity(httpEntity);
        go(httpPost);
        if (this.m_response == null || this.m_status != TrustDefenderMobile.THMStatusCode.THM_OK) {
            return -1;
        }
        return (long) this.m_response.getStatusLine().getStatusCode();
    }
}
