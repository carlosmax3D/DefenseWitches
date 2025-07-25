package twitter4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import twitter4j.conf.ConfigurationContext;

public abstract class HttpResponse {
    private static final Logger logger = Logger.getLogger(HttpResponseImpl.class);
    protected final HttpClientConfiguration CONF;
    protected InputStream is;
    private JSONObject json;
    private JSONArray jsonArray;
    protected String responseAsString;
    protected int statusCode;
    private boolean streamConsumed;

    HttpResponse() {
        this.responseAsString = null;
        this.streamConsumed = false;
        this.json = null;
        this.jsonArray = null;
        this.CONF = ConfigurationContext.getInstance().getHttpClientConfiguration();
    }

    public HttpResponse(HttpClientConfiguration httpClientConfiguration) {
        this.responseAsString = null;
        this.streamConsumed = false;
        this.json = null;
        this.jsonArray = null;
        this.CONF = httpClientConfiguration;
    }

    private void disconnectForcibly() {
        try {
            disconnect();
        } catch (Exception e) {
        }
    }

    public JSONArray asJSONArray() throws TwitterException {
        if (this.jsonArray == null) {
            Reader reader = null;
            try {
                if (this.responseAsString == null) {
                    reader = asReader();
                    this.jsonArray = new JSONArray(new JSONTokener(reader));
                } else {
                    this.jsonArray = new JSONArray(this.responseAsString);
                }
                if (this.CONF.isPrettyDebugEnabled()) {
                    logger.debug(this.jsonArray.toString(1));
                } else {
                    logger.debug(this.responseAsString != null ? this.responseAsString : this.jsonArray.toString());
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                    }
                }
                disconnectForcibly();
            } catch (JSONException e2) {
                if (logger.isDebugEnabled()) {
                    throw new TwitterException(e2.getMessage() + ":" + this.responseAsString, (Throwable) e2);
                }
                throw new TwitterException(e2.getMessage(), (Throwable) e2);
            } catch (Throwable th) {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e3) {
                    }
                }
                disconnectForcibly();
                throw th;
            }
        }
        return this.jsonArray;
    }

    public JSONObject asJSONObject() throws TwitterException {
        if (this.json == null) {
            Reader reader = null;
            try {
                if (this.responseAsString == null) {
                    reader = asReader();
                    this.json = new JSONObject(new JSONTokener(reader));
                } else {
                    this.json = new JSONObject(this.responseAsString);
                }
                if (this.CONF.isPrettyDebugEnabled()) {
                    logger.debug(this.json.toString(1));
                } else {
                    logger.debug(this.responseAsString != null ? this.responseAsString : this.json.toString());
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                    }
                }
                disconnectForcibly();
            } catch (JSONException e2) {
                if (this.responseAsString == null) {
                    throw new TwitterException(e2.getMessage(), (Throwable) e2);
                }
                throw new TwitterException(e2.getMessage() + ":" + this.responseAsString, (Throwable) e2);
            } catch (Throwable th) {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e3) {
                    }
                }
                disconnectForcibly();
                throw th;
            }
        }
        return this.json;
    }

    public Reader asReader() {
        try {
            return new BufferedReader(new InputStreamReader(this.is, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return new InputStreamReader(this.is);
        }
    }

    public InputStream asStream() {
        if (!this.streamConsumed) {
            return this.is;
        }
        throw new IllegalStateException("Stream has already been consumed.");
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x004b A[SYNTHETIC, Splitter:B:28:0x004b] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0050 A[SYNTHETIC, Splitter:B:31:0x0050] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String asString() throws twitter4j.TwitterException {
        /*
            r8 = this;
            java.lang.String r6 = r8.responseAsString
            if (r6 != 0) goto L_0x0077
            r0 = 0
            r5 = 0
            java.io.InputStream r5 = r8.asStream()     // Catch:{ IOException -> 0x0089 }
            if (r5 != 0) goto L_0x001b
            r6 = 0
            if (r5 == 0) goto L_0x0012
            r5.close()     // Catch:{ IOException -> 0x007a }
        L_0x0012:
            if (r0 == 0) goto L_0x0017
            r0.close()     // Catch:{ IOException -> 0x007c }
        L_0x0017:
            r8.disconnectForcibly()
        L_0x001a:
            return r6
        L_0x001b:
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0089 }
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x0089 }
            java.lang.String r7 = "UTF-8"
            r6.<init>(r5, r7)     // Catch:{ IOException -> 0x0089 }
            r1.<init>(r6)     // Catch:{ IOException -> 0x0089 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x003c, all -> 0x0086 }
            r2.<init>()     // Catch:{ IOException -> 0x003c, all -> 0x0086 }
        L_0x002c:
            java.lang.String r4 = r1.readLine()     // Catch:{ IOException -> 0x003c, all -> 0x0086 }
            if (r4 == 0) goto L_0x0057
            java.lang.StringBuilder r6 = r2.append(r4)     // Catch:{ IOException -> 0x003c, all -> 0x0086 }
            java.lang.String r7 = "\n"
            r6.append(r7)     // Catch:{ IOException -> 0x003c, all -> 0x0086 }
            goto L_0x002c
        L_0x003c:
            r3 = move-exception
            r0 = r1
        L_0x003e:
            twitter4j.TwitterException r6 = new twitter4j.TwitterException     // Catch:{ all -> 0x0048 }
            java.lang.String r7 = r3.getMessage()     // Catch:{ all -> 0x0048 }
            r6.<init>((java.lang.String) r7, (java.lang.Throwable) r3)     // Catch:{ all -> 0x0048 }
            throw r6     // Catch:{ all -> 0x0048 }
        L_0x0048:
            r6 = move-exception
        L_0x0049:
            if (r5 == 0) goto L_0x004e
            r5.close()     // Catch:{ IOException -> 0x0082 }
        L_0x004e:
            if (r0 == 0) goto L_0x0053
            r0.close()     // Catch:{ IOException -> 0x0084 }
        L_0x0053:
            r8.disconnectForcibly()
            throw r6
        L_0x0057:
            java.lang.String r6 = r2.toString()     // Catch:{ IOException -> 0x003c, all -> 0x0086 }
            r8.responseAsString = r6     // Catch:{ IOException -> 0x003c, all -> 0x0086 }
            twitter4j.Logger r6 = logger     // Catch:{ IOException -> 0x003c, all -> 0x0086 }
            java.lang.String r7 = r8.responseAsString     // Catch:{ IOException -> 0x003c, all -> 0x0086 }
            r6.debug(r7)     // Catch:{ IOException -> 0x003c, all -> 0x0086 }
            r5.close()     // Catch:{ IOException -> 0x003c, all -> 0x0086 }
            r6 = 1
            r8.streamConsumed = r6     // Catch:{ IOException -> 0x003c, all -> 0x0086 }
            if (r5 == 0) goto L_0x006f
            r5.close()     // Catch:{ IOException -> 0x007e }
        L_0x006f:
            if (r1 == 0) goto L_0x0074
            r1.close()     // Catch:{ IOException -> 0x0080 }
        L_0x0074:
            r8.disconnectForcibly()
        L_0x0077:
            java.lang.String r6 = r8.responseAsString
            goto L_0x001a
        L_0x007a:
            r7 = move-exception
            goto L_0x0012
        L_0x007c:
            r7 = move-exception
            goto L_0x0017
        L_0x007e:
            r6 = move-exception
            goto L_0x006f
        L_0x0080:
            r6 = move-exception
            goto L_0x0074
        L_0x0082:
            r7 = move-exception
            goto L_0x004e
        L_0x0084:
            r7 = move-exception
            goto L_0x0053
        L_0x0086:
            r6 = move-exception
            r0 = r1
            goto L_0x0049
        L_0x0089:
            r3 = move-exception
            goto L_0x003e
        */
        throw new UnsupportedOperationException("Method not decompiled: twitter4j.HttpResponse.asString():java.lang.String");
    }

    public abstract void disconnect() throws IOException;

    public abstract String getResponseHeader(String str);

    public abstract Map<String, List<String>> getResponseHeaderFields();

    public int getStatusCode() {
        return this.statusCode;
    }

    public String toString() {
        return "HttpResponse{statusCode=" + this.statusCode + ", responseAsString='" + this.responseAsString + '\'' + ", is=" + this.is + ", streamConsumed=" + this.streamConsumed + '}';
    }
}
