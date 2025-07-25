package twitter4j;

import java.io.IOException;
import java.io.Serializable;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import jp.stargarage.g2metrics.BuildConfig;
import twitter4j.auth.Authorization;
import twitter4j.conf.ConfigurationContext;

class HttpClientImpl extends HttpClientBase implements HttpResponseCode, Serializable {
    private static final Map<HttpClientConfiguration, HttpClient> instanceMap = new HashMap(1);
    private static final Logger logger = Logger.getLogger(HttpClientImpl.class);
    private static final long serialVersionUID = -403500272719330534L;

    static {
        try {
            if (Integer.parseInt((String) Class.forName("android.os.Build$VERSION").getField("SDK").get((Object) null)) < 8) {
                System.setProperty("http.keepAlive", "false");
            }
        } catch (Exception e) {
        }
    }

    public HttpClientImpl() {
        super(ConfigurationContext.getInstance().getHttpClientConfiguration());
    }

    public HttpClientImpl(HttpClientConfiguration httpClientConfiguration) {
        super(httpClientConfiguration);
    }

    public static HttpClient getInstance(HttpClientConfiguration httpClientConfiguration) {
        HttpClient httpClient = instanceMap.get(httpClientConfiguration);
        if (httpClient != null) {
            return httpClient;
        }
        HttpClientImpl httpClientImpl = new HttpClientImpl(httpClientConfiguration);
        instanceMap.put(httpClientConfiguration, httpClientImpl);
        return httpClientImpl;
    }

    private void setHeaders(HttpRequest httpRequest, HttpURLConnection httpURLConnection) {
        String authorizationHeader;
        if (logger.isDebugEnabled()) {
            logger.debug("Request: ");
            logger.debug(httpRequest.getMethod().name() + " ", httpRequest.getURL());
        }
        if (!(httpRequest.getAuthorization() == null || (authorizationHeader = httpRequest.getAuthorization().getAuthorizationHeader(httpRequest)) == null)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Authorization: ", authorizationHeader.replaceAll(".", "*"));
            }
            httpURLConnection.addRequestProperty("Authorization", authorizationHeader);
        }
        if (httpRequest.getRequestHeaders() != null) {
            for (String next : httpRequest.getRequestHeaders().keySet()) {
                httpURLConnection.addRequestProperty(next, httpRequest.getRequestHeaders().get(next));
                logger.debug(next + ": " + httpRequest.getRequestHeaders().get(next));
            }
        }
    }

    public HttpResponse get(String str) throws TwitterException {
        return request(new HttpRequest(RequestMethod.GET, str, (HttpParameter[]) null, (Authorization) null, (Map<String, String>) null));
    }

    /* access modifiers changed from: package-private */
    public HttpURLConnection getConnection(String str) throws IOException {
        HttpURLConnection httpURLConnection;
        if (isProxyConfigured()) {
            if (this.CONF.getHttpProxyUser() != null && !this.CONF.getHttpProxyUser().equals(BuildConfig.FLAVOR)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Proxy AuthUser: " + this.CONF.getHttpProxyUser());
                    logger.debug("Proxy AuthPassword: " + this.CONF.getHttpProxyPassword().replaceAll(".", "*"));
                }
                Authenticator.setDefault(new Authenticator() {
                    /* access modifiers changed from: protected */
                    public PasswordAuthentication getPasswordAuthentication() {
                        if (getRequestorType().equals(Authenticator.RequestorType.PROXY)) {
                            return new PasswordAuthentication(HttpClientImpl.this.CONF.getHttpProxyUser(), HttpClientImpl.this.CONF.getHttpProxyPassword().toCharArray());
                        }
                        return null;
                    }
                });
            }
            Proxy proxy = new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(this.CONF.getHttpProxyHost(), this.CONF.getHttpProxyPort()));
            if (logger.isDebugEnabled()) {
                logger.debug("Opening proxied connection(" + this.CONF.getHttpProxyHost() + ":" + this.CONF.getHttpProxyPort() + ")");
            }
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection(proxy);
        } else {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        }
        if (this.CONF.getHttpConnectionTimeout() > 0) {
            httpURLConnection.setConnectTimeout(this.CONF.getHttpConnectionTimeout());
        }
        if (this.CONF.getHttpReadTimeout() > 0) {
            httpURLConnection.setReadTimeout(this.CONF.getHttpReadTimeout());
        }
        httpURLConnection.setInstanceFollowRedirects(false);
        return httpURLConnection;
    }

    /* JADX WARNING: type inference failed for: r24v99, types: [java.io.InputStream] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public twitter4j.HttpResponse handleRequest(twitter4j.HttpRequest r30) throws twitter4j.TwitterException {
        /*
            r29 = this;
            r0 = r29
            twitter4j.HttpClientConfiguration r0 = r0.CONF
            r24 = r0
            int r24 = r24.getHttpRetryCount()
            int r21 = r24 + 1
            r16 = 0
            r20 = 0
            r17 = r16
        L_0x0012:
            r0 = r20
            r1 = r21
            if (r0 >= r1) goto L_0x0409
            r18 = -1
            r12 = 0
            java.lang.String r24 = r30.getURL()     // Catch:{ all -> 0x0194 }
            r0 = r29
            r1 = r24
            java.net.HttpURLConnection r7 = r0.getConnection(r1)     // Catch:{ all -> 0x0194 }
            r24 = 1
            r0 = r24
            r7.setDoInput(r0)     // Catch:{ all -> 0x0194 }
            r0 = r29
            r1 = r30
            r0.setHeaders(r1, r7)     // Catch:{ all -> 0x0194 }
            twitter4j.RequestMethod r24 = r30.getMethod()     // Catch:{ all -> 0x0194 }
            java.lang.String r24 = r24.name()     // Catch:{ all -> 0x0194 }
            r0 = r24
            r7.setRequestMethod(r0)     // Catch:{ all -> 0x0194 }
            twitter4j.RequestMethod r24 = r30.getMethod()     // Catch:{ all -> 0x0194 }
            twitter4j.RequestMethod r25 = twitter4j.RequestMethod.POST     // Catch:{ all -> 0x0194 }
            r0 = r24
            r1 = r25
            if (r0 != r1) goto L_0x028b
            twitter4j.HttpParameter[] r24 = r30.getParameters()     // Catch:{ all -> 0x0194 }
            boolean r24 = twitter4j.HttpParameter.containsFile((twitter4j.HttpParameter[]) r24)     // Catch:{ all -> 0x0194 }
            if (r24 == 0) goto L_0x0305
            java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ all -> 0x0194 }
            r24.<init>()     // Catch:{ all -> 0x0194 }
            java.lang.String r25 = "----Twitter4J-upload"
            java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ all -> 0x0194 }
            long r26 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0194 }
            r0 = r24
            r1 = r26
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x0194 }
            java.lang.String r4 = r24.toString()     // Catch:{ all -> 0x0194 }
            java.lang.String r24 = "Content-Type"
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ all -> 0x0194 }
            r25.<init>()     // Catch:{ all -> 0x0194 }
            java.lang.String r26 = "multipart/form-data; boundary="
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ all -> 0x0194 }
            r0 = r25
            java.lang.StringBuilder r25 = r0.append(r4)     // Catch:{ all -> 0x0194 }
            java.lang.String r25 = r25.toString()     // Catch:{ all -> 0x0194 }
            r0 = r24
            r1 = r25
            r7.setRequestProperty(r0, r1)     // Catch:{ all -> 0x0194 }
            java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ all -> 0x0194 }
            r24.<init>()     // Catch:{ all -> 0x0194 }
            java.lang.String r25 = "--"
            java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ all -> 0x0194 }
            r0 = r24
            java.lang.StringBuilder r24 = r0.append(r4)     // Catch:{ all -> 0x0194 }
            java.lang.String r4 = r24.toString()     // Catch:{ all -> 0x0194 }
            r24 = 1
            r0 = r24
            r7.setDoOutput(r0)     // Catch:{ all -> 0x0194 }
            java.io.OutputStream r12 = r7.getOutputStream()     // Catch:{ all -> 0x0194 }
            java.io.DataOutputStream r13 = new java.io.DataOutputStream     // Catch:{ all -> 0x0194 }
            r13.<init>(r12)     // Catch:{ all -> 0x0194 }
            twitter4j.HttpParameter[] r26 = r30.getParameters()     // Catch:{ all -> 0x0194 }
            r0 = r26
            int r0 = r0.length     // Catch:{ all -> 0x0194 }
            r27 = r0
            r24 = 0
            r25 = r24
        L_0x00c3:
            r0 = r25
            r1 = r27
            if (r0 >= r1) goto L_0x0260
            r14 = r26[r25]     // Catch:{ all -> 0x0194 }
            boolean r24 = r14.isFile()     // Catch:{ all -> 0x0194 }
            if (r24 == 0) goto L_0x01dc
            java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ all -> 0x0194 }
            r24.<init>()     // Catch:{ all -> 0x0194 }
            r0 = r24
            java.lang.StringBuilder r24 = r0.append(r4)     // Catch:{ all -> 0x0194 }
            java.lang.String r28 = "\r\n"
            r0 = r24
            r1 = r28
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x0194 }
            java.lang.String r24 = r24.toString()     // Catch:{ all -> 0x0194 }
            r0 = r29
            r1 = r24
            r0.write(r13, r1)     // Catch:{ all -> 0x0194 }
            java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ all -> 0x0194 }
            r24.<init>()     // Catch:{ all -> 0x0194 }
            java.lang.String r28 = "Content-Disposition: form-data; name=\""
            r0 = r24
            r1 = r28
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x0194 }
            java.lang.String r28 = r14.getName()     // Catch:{ all -> 0x0194 }
            r0 = r24
            r1 = r28
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x0194 }
            java.lang.String r28 = "\"; filename=\""
            r0 = r24
            r1 = r28
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x0194 }
            java.io.File r28 = r14.getFile()     // Catch:{ all -> 0x0194 }
            java.lang.String r28 = r28.getName()     // Catch:{ all -> 0x0194 }
            r0 = r24
            r1 = r28
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x0194 }
            java.lang.String r28 = "\"\r\n"
            r0 = r24
            r1 = r28
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x0194 }
            java.lang.String r24 = r24.toString()     // Catch:{ all -> 0x0194 }
            r0 = r29
            r1 = r24
            r0.write(r13, r1)     // Catch:{ all -> 0x0194 }
            java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ all -> 0x0194 }
            r24.<init>()     // Catch:{ all -> 0x0194 }
            java.lang.String r28 = "Content-Type: "
            r0 = r24
            r1 = r28
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x0194 }
            java.lang.String r28 = r14.getContentType()     // Catch:{ all -> 0x0194 }
            r0 = r24
            r1 = r28
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x0194 }
            java.lang.String r28 = "\r\n\r\n"
            r0 = r24
            r1 = r28
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x0194 }
            java.lang.String r24 = r24.toString()     // Catch:{ all -> 0x0194 }
            r0 = r29
            r1 = r24
            r0.write(r13, r1)     // Catch:{ all -> 0x0194 }
            java.io.BufferedInputStream r8 = new java.io.BufferedInputStream     // Catch:{ all -> 0x0194 }
            boolean r24 = r14.hasFileBody()     // Catch:{ all -> 0x0194 }
            if (r24 == 0) goto L_0x01bc
            java.io.InputStream r24 = r14.getFileBody()     // Catch:{ all -> 0x0194 }
        L_0x0177:
            r0 = r24
            r8.<init>(r0)     // Catch:{ all -> 0x0194 }
            r24 = 1024(0x400, float:1.435E-42)
            r0 = r24
            byte[] r5 = new byte[r0]     // Catch:{ all -> 0x0194 }
        L_0x0182:
            int r11 = r8.read(r5)     // Catch:{ all -> 0x0194 }
            r24 = -1
            r0 = r24
            if (r11 == r0) goto L_0x01ca
            r24 = 0
            r0 = r24
            r13.write(r5, r0, r11)     // Catch:{ all -> 0x0194 }
            goto L_0x0182
        L_0x0194:
            r24 = move-exception
            r16 = r17
        L_0x0197:
            r12.close()     // Catch:{ Exception -> 0x0404 }
        L_0x019a:
            throw r24     // Catch:{ IOException -> 0x019b }
        L_0x019b:
            r9 = move-exception
            r0 = r29
            twitter4j.HttpClientConfiguration r0 = r0.CONF
            r24 = r0
            int r24 = r24.getHttpRetryCount()
            r0 = r20
            r1 = r24
            if (r0 != r1) goto L_0x03ad
            twitter4j.TwitterException r24 = new twitter4j.TwitterException
            java.lang.String r25 = r9.getMessage()
            r0 = r24
            r1 = r25
            r2 = r18
            r0.<init>(r1, r9, r2)
            throw r24
        L_0x01bc:
            java.io.FileInputStream r24 = new java.io.FileInputStream     // Catch:{ all -> 0x0194 }
            java.io.File r28 = r14.getFile()     // Catch:{ all -> 0x0194 }
            r0 = r24
            r1 = r28
            r0.<init>(r1)     // Catch:{ all -> 0x0194 }
            goto L_0x0177
        L_0x01ca:
            java.lang.String r24 = "\r\n"
            r0 = r29
            r1 = r24
            r0.write(r13, r1)     // Catch:{ all -> 0x0194 }
            r8.close()     // Catch:{ all -> 0x0194 }
        L_0x01d6:
            int r24 = r25 + 1
            r25 = r24
            goto L_0x00c3
        L_0x01dc:
            java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ all -> 0x0194 }
            r24.<init>()     // Catch:{ all -> 0x0194 }
            r0 = r24
            java.lang.StringBuilder r24 = r0.append(r4)     // Catch:{ all -> 0x0194 }
            java.lang.String r28 = "\r\n"
            r0 = r24
            r1 = r28
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x0194 }
            java.lang.String r24 = r24.toString()     // Catch:{ all -> 0x0194 }
            r0 = r29
            r1 = r24
            r0.write(r13, r1)     // Catch:{ all -> 0x0194 }
            java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ all -> 0x0194 }
            r24.<init>()     // Catch:{ all -> 0x0194 }
            java.lang.String r28 = "Content-Disposition: form-data; name=\""
            r0 = r24
            r1 = r28
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x0194 }
            java.lang.String r28 = r14.getName()     // Catch:{ all -> 0x0194 }
            r0 = r24
            r1 = r28
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x0194 }
            java.lang.String r28 = "\"\r\n"
            r0 = r24
            r1 = r28
            java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x0194 }
            java.lang.String r24 = r24.toString()     // Catch:{ all -> 0x0194 }
            r0 = r29
            r1 = r24
            r0.write(r13, r1)     // Catch:{ all -> 0x0194 }
            java.lang.String r24 = "Content-Type: text/plain; charset=UTF-8\r\n\r\n"
            r0 = r29
            r1 = r24
            r0.write(r13, r1)     // Catch:{ all -> 0x0194 }
            twitter4j.Logger r24 = logger     // Catch:{ all -> 0x0194 }
            java.lang.String r28 = r14.getValue()     // Catch:{ all -> 0x0194 }
            r0 = r24
            r1 = r28
            r0.debug(r1)     // Catch:{ all -> 0x0194 }
            java.lang.String r24 = r14.getValue()     // Catch:{ all -> 0x0194 }
            java.lang.String r28 = "UTF-8"
            r0 = r24
            r1 = r28
            byte[] r24 = r0.getBytes(r1)     // Catch:{ all -> 0x0194 }
            r0 = r24
            r13.write(r0)     // Catch:{ all -> 0x0194 }
            java.lang.String r24 = "\r\n"
            r0 = r29
            r1 = r24
            r0.write(r13, r1)     // Catch:{ all -> 0x0194 }
            goto L_0x01d6
        L_0x0260:
            java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ all -> 0x0194 }
            r24.<init>()     // Catch:{ all -> 0x0194 }
            r0 = r24
            java.lang.StringBuilder r24 = r0.append(r4)     // Catch:{ all -> 0x0194 }
            java.lang.String r25 = "--\r\n"
            java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ all -> 0x0194 }
            java.lang.String r24 = r24.toString()     // Catch:{ all -> 0x0194 }
            r0 = r29
            r1 = r24
            r0.write(r13, r1)     // Catch:{ all -> 0x0194 }
            java.lang.String r24 = "\r\n"
            r0 = r29
            r1 = r24
            r0.write(r13, r1)     // Catch:{ all -> 0x0194 }
        L_0x0285:
            r12.flush()     // Catch:{ all -> 0x0194 }
            r12.close()     // Catch:{ all -> 0x0194 }
        L_0x028b:
            twitter4j.HttpResponseImpl r16 = new twitter4j.HttpResponseImpl     // Catch:{ all -> 0x0194 }
            r0 = r29
            twitter4j.HttpClientConfiguration r0 = r0.CONF     // Catch:{ all -> 0x0194 }
            r24 = r0
            r0 = r16
            r1 = r24
            r0.<init>(r7, r1)     // Catch:{ all -> 0x0194 }
            int r18 = r7.getResponseCode()     // Catch:{ all -> 0x0302 }
            twitter4j.Logger r24 = logger     // Catch:{ all -> 0x0302 }
            boolean r24 = r24.isDebugEnabled()     // Catch:{ all -> 0x0302 }
            if (r24 == 0) goto L_0x0356
            twitter4j.Logger r24 = logger     // Catch:{ all -> 0x0302 }
            java.lang.String r25 = "Response: "
            r24.debug(r25)     // Catch:{ all -> 0x0302 }
            java.util.Map r19 = r7.getHeaderFields()     // Catch:{ all -> 0x0302 }
            java.util.Set r24 = r19.keySet()     // Catch:{ all -> 0x0302 }
            java.util.Iterator r24 = r24.iterator()     // Catch:{ all -> 0x0302 }
        L_0x02b9:
            boolean r25 = r24.hasNext()     // Catch:{ all -> 0x0302 }
            if (r25 == 0) goto L_0x0356
            java.lang.Object r10 = r24.next()     // Catch:{ all -> 0x0302 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ all -> 0x0302 }
            r0 = r19
            java.lang.Object r23 = r0.get(r10)     // Catch:{ all -> 0x0302 }
            java.util.List r23 = (java.util.List) r23     // Catch:{ all -> 0x0302 }
            java.util.Iterator r25 = r23.iterator()     // Catch:{ all -> 0x0302 }
        L_0x02d1:
            boolean r26 = r25.hasNext()     // Catch:{ all -> 0x0302 }
            if (r26 == 0) goto L_0x02b9
            java.lang.Object r22 = r25.next()     // Catch:{ all -> 0x0302 }
            java.lang.String r22 = (java.lang.String) r22     // Catch:{ all -> 0x0302 }
            if (r10 == 0) goto L_0x034b
            twitter4j.Logger r26 = logger     // Catch:{ all -> 0x0302 }
            java.lang.StringBuilder r27 = new java.lang.StringBuilder     // Catch:{ all -> 0x0302 }
            r27.<init>()     // Catch:{ all -> 0x0302 }
            r0 = r27
            java.lang.StringBuilder r27 = r0.append(r10)     // Catch:{ all -> 0x0302 }
            java.lang.String r28 = ": "
            java.lang.StringBuilder r27 = r27.append(r28)     // Catch:{ all -> 0x0302 }
            r0 = r27
            r1 = r22
            java.lang.StringBuilder r27 = r0.append(r1)     // Catch:{ all -> 0x0302 }
            java.lang.String r27 = r27.toString()     // Catch:{ all -> 0x0302 }
            r26.debug(r27)     // Catch:{ all -> 0x0302 }
            goto L_0x02d1
        L_0x0302:
            r24 = move-exception
            goto L_0x0197
        L_0x0305:
            java.lang.String r24 = "Content-Type"
            java.lang.String r25 = "application/x-www-form-urlencoded"
            r0 = r24
            r1 = r25
            r7.setRequestProperty(r0, r1)     // Catch:{ all -> 0x0194 }
            twitter4j.HttpParameter[] r24 = r30.getParameters()     // Catch:{ all -> 0x0194 }
            java.lang.String r15 = twitter4j.HttpParameter.encodeParameters(r24)     // Catch:{ all -> 0x0194 }
            twitter4j.Logger r24 = logger     // Catch:{ all -> 0x0194 }
            java.lang.String r25 = "Post Params: "
            r0 = r24
            r1 = r25
            r0.debug(r1, r15)     // Catch:{ all -> 0x0194 }
            java.lang.String r24 = "UTF-8"
            r0 = r24
            byte[] r6 = r15.getBytes(r0)     // Catch:{ all -> 0x0194 }
            java.lang.String r24 = "Content-Length"
            int r0 = r6.length     // Catch:{ all -> 0x0194 }
            r25 = r0
            java.lang.String r25 = java.lang.Integer.toString(r25)     // Catch:{ all -> 0x0194 }
            r0 = r24
            r1 = r25
            r7.setRequestProperty(r0, r1)     // Catch:{ all -> 0x0194 }
            r24 = 1
            r0 = r24
            r7.setDoOutput(r0)     // Catch:{ all -> 0x0194 }
            java.io.OutputStream r12 = r7.getOutputStream()     // Catch:{ all -> 0x0194 }
            r12.write(r6)     // Catch:{ all -> 0x0194 }
            goto L_0x0285
        L_0x034b:
            twitter4j.Logger r26 = logger     // Catch:{ all -> 0x0302 }
            r0 = r26
            r1 = r22
            r0.debug(r1)     // Catch:{ all -> 0x0302 }
            goto L_0x02d1
        L_0x0356:
            r24 = 200(0xc8, float:2.8E-43)
            r0 = r18
            r1 = r24
            if (r0 < r1) goto L_0x036e
            r24 = 302(0x12e, float:4.23E-43)
            r0 = r18
            r1 = r24
            if (r0 == r1) goto L_0x03a6
            r24 = 300(0x12c, float:4.2E-43)
            r0 = r24
            r1 = r18
            if (r0 > r1) goto L_0x03a6
        L_0x036e:
            r24 = 420(0x1a4, float:5.89E-43)
            r0 = r18
            r1 = r24
            if (r0 == r1) goto L_0x0396
            r24 = 400(0x190, float:5.6E-43)
            r0 = r18
            r1 = r24
            if (r0 == r1) goto L_0x0396
            r24 = 500(0x1f4, float:7.0E-43)
            r0 = r18
            r1 = r24
            if (r0 < r1) goto L_0x0396
            r0 = r29
            twitter4j.HttpClientConfiguration r0 = r0.CONF     // Catch:{ all -> 0x0302 }
            r24 = r0
            int r24 = r24.getHttpRetryCount()     // Catch:{ all -> 0x0302 }
            r0 = r20
            r1 = r24
            if (r0 != r1) goto L_0x03aa
        L_0x0396:
            twitter4j.TwitterException r24 = new twitter4j.TwitterException     // Catch:{ all -> 0x0302 }
            java.lang.String r25 = r16.asString()     // Catch:{ all -> 0x0302 }
            r0 = r24
            r1 = r25
            r2 = r16
            r0.<init>((java.lang.String) r1, (twitter4j.HttpResponse) r2)     // Catch:{ all -> 0x0302 }
            throw r24     // Catch:{ all -> 0x0302 }
        L_0x03a6:
            r12.close()     // Catch:{ Exception -> 0x0400 }
        L_0x03a9:
            return r16
        L_0x03aa:
            r12.close()     // Catch:{ Exception -> 0x0402 }
        L_0x03ad:
            twitter4j.Logger r24 = logger     // Catch:{ InterruptedException -> 0x0407 }
            boolean r24 = r24.isDebugEnabled()     // Catch:{ InterruptedException -> 0x0407 }
            if (r24 == 0) goto L_0x03ba
            if (r16 == 0) goto L_0x03ba
            r16.asString()     // Catch:{ InterruptedException -> 0x0407 }
        L_0x03ba:
            twitter4j.Logger r24 = logger     // Catch:{ InterruptedException -> 0x0407 }
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x0407 }
            r25.<init>()     // Catch:{ InterruptedException -> 0x0407 }
            java.lang.String r26 = "Sleeping "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ InterruptedException -> 0x0407 }
            r0 = r29
            twitter4j.HttpClientConfiguration r0 = r0.CONF     // Catch:{ InterruptedException -> 0x0407 }
            r26 = r0
            int r26 = r26.getHttpRetryIntervalSeconds()     // Catch:{ InterruptedException -> 0x0407 }
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ InterruptedException -> 0x0407 }
            java.lang.String r26 = " seconds until the next retry."
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ InterruptedException -> 0x0407 }
            java.lang.String r25 = r25.toString()     // Catch:{ InterruptedException -> 0x0407 }
            r24.debug(r25)     // Catch:{ InterruptedException -> 0x0407 }
            r0 = r29
            twitter4j.HttpClientConfiguration r0 = r0.CONF     // Catch:{ InterruptedException -> 0x0407 }
            r24 = r0
            int r24 = r24.getHttpRetryIntervalSeconds()     // Catch:{ InterruptedException -> 0x0407 }
            r0 = r24
            int r0 = r0 * 1000
            r24 = r0
            r0 = r24
            long r0 = (long) r0     // Catch:{ InterruptedException -> 0x0407 }
            r24 = r0
            java.lang.Thread.sleep(r24)     // Catch:{ InterruptedException -> 0x0407 }
        L_0x03fa:
            int r20 = r20 + 1
            r17 = r16
            goto L_0x0012
        L_0x0400:
            r24 = move-exception
            goto L_0x03a9
        L_0x0402:
            r24 = move-exception
            goto L_0x03ad
        L_0x0404:
            r25 = move-exception
            goto L_0x019a
        L_0x0407:
            r24 = move-exception
            goto L_0x03fa
        L_0x0409:
            r16 = r17
            goto L_0x03a9
        */
        throw new UnsupportedOperationException("Method not decompiled: twitter4j.HttpClientImpl.handleRequest(twitter4j.HttpRequest):twitter4j.HttpResponse");
    }

    public HttpResponse post(String str, HttpParameter[] httpParameterArr) throws TwitterException {
        return request(new HttpRequest(RequestMethod.POST, str, httpParameterArr, (Authorization) null, (Map<String, String>) null));
    }
}
