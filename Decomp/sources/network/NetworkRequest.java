package network;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaLuaEvent;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.ansca.corona.notifications.StatusBarNotificationSettings;
import com.ansca.corona.purchasing.StoreName;
import com.facebook.share.internal.ShareConstants;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;
import com.naef.jnlua.NamedJavaFunction;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jp.stargarage.g2metrics.BuildConfig;

public class NetworkRequest implements NamedJavaFunction {
    private static final String EVENT_NAME = "networkRequest";
    /* access modifiers changed from: private */
    public LuaLoader fLoader;
    private CopyOnWriteArrayList<AsyncNetworkRequestRunnable> fOpenRequests = null;

    private class AsyncNetworkRequestRunnable implements Runnable {
        private CopyOnWriteArrayList<AsyncNetworkRequestRunnable> connectionList;
        private NetworkRequestParameters requestParameters;
        public NetworkRequestState requestState;

        public AsyncNetworkRequestRunnable(NetworkRequestParameters networkRequestParameters, CopyOnWriteArrayList<AsyncNetworkRequestRunnable> copyOnWriteArrayList) {
            this.requestParameters = networkRequestParameters;
            this.connectionList = copyOnWriteArrayList;
            this.requestState = new NetworkRequestState(this.requestParameters.requestURL != null ? this.requestParameters.requestURL.toString() : BuildConfig.FLAVOR, this.requestParameters.isDebug);
        }

        public boolean abort(CoronaRuntime coronaRuntime) {
            this.requestState.isRequestCancelled.set(true);
            if (this.requestParameters.callback == null) {
                return false;
            }
            this.requestParameters.callback.unregister(coronaRuntime);
            return false;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r65v8, resolved type: java.lang.Object[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r39v0, resolved type: java.io.InputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r39v1, resolved type: java.io.InputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r39v2, resolved type: java.io.InputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r49v5, resolved type: java.io.ByteArrayOutputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r38v6, resolved type: java.io.PushbackInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v294, resolved type: java.io.PushbackInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r39v4, resolved type: java.io.PushbackInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r66v16, resolved type: java.lang.Object[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r66v18, resolved type: java.lang.Object[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v360, resolved type: java.lang.String} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r65v77, resolved type: java.lang.Object[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r66v26, resolved type: java.lang.Object[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v390, resolved type: java.lang.String} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r67v23, resolved type: java.lang.Object[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r36v7, resolved type: java.io.BufferedInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r36v8, resolved type: java.io.BufferedInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r36v9, resolved type: java.io.BufferedInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r36v10, resolved type: java.io.BufferedInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r37v3, resolved type: java.io.ByteArrayInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r36v11, resolved type: java.io.InputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r37v4, resolved type: java.io.ByteArrayInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r37v6, resolved type: java.io.ByteArrayInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r36v12, resolved type: java.io.BufferedInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r36v13, resolved type: java.io.BufferedInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r36v14, resolved type: java.io.BufferedInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r65v113, resolved type: java.lang.Object[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r49v16, resolved type: java.io.FileOutputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v557, resolved type: java.io.FileOutputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v558, resolved type: java.io.ByteArrayInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v559, resolved type: java.io.ByteArrayInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v560, resolved type: java.io.ByteArrayInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r37v7, resolved type: java.io.ByteArrayInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r37v8, resolved type: java.io.ByteArrayInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r37v9, resolved type: java.io.ByteArrayInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r37v10, resolved type: java.io.ByteArrayInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v561, resolved type: java.io.ByteArrayInputStream} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v562, resolved type: java.io.ByteArrayInputStream} */
        /* JADX WARNING: type inference failed for: r64v55, types: [java.net.URLConnection] */
        /* JADX WARNING: type inference failed for: r49v1, types: [java.io.OutputStream] */
        /* JADX WARNING: type inference failed for: r38v7 */
        /* JADX WARNING: type inference failed for: r64v201, types: [java.net.URLConnection] */
        /* JADX WARNING: type inference failed for: r37v2 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Removed duplicated region for block: B:120:0x056e A[SYNTHETIC, Splitter:B:120:0x056e] */
        /* JADX WARNING: Removed duplicated region for block: B:123:0x0573 A[Catch:{ Exception -> 0x0364, all -> 0x017e, Exception -> 0x0185 }] */
        /* JADX WARNING: Removed duplicated region for block: B:273:0x0b77 A[SYNTHETIC, Splitter:B:273:0x0b77] */
        /* JADX WARNING: Removed duplicated region for block: B:276:0x0b7c A[Catch:{ Exception -> 0x0364, all -> 0x017e, Exception -> 0x0185 }] */
        /* JADX WARNING: Removed duplicated region for block: B:323:0x0cce A[SYNTHETIC, Splitter:B:323:0x0cce] */
        /* JADX WARNING: Removed duplicated region for block: B:326:0x0cd3 A[Catch:{ Exception -> 0x0364, all -> 0x017e, Exception -> 0x0185 }] */
        /* JADX WARNING: Removed duplicated region for block: B:328:0x0cd8 A[Catch:{ Exception -> 0x0364, all -> 0x017e, Exception -> 0x0185 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r70 = this;
                r0 = r70
                java.util.concurrent.CopyOnWriteArrayList<network.NetworkRequest$AsyncNetworkRequestRunnable> r0 = r0.connectionList
                r64 = r0
                r0 = r64
                r1 = r70
                r0.add(r1)
                r20 = 0
                r22 = 0
                r18 = 0
                r16 = 0
                r60 = 0
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                java.net.URL r0 = r0.requestURL     // Catch:{ all -> 0x017e }
                r64 = r0
                java.net.URLConnection r64 = r64.openConnection()     // Catch:{ all -> 0x017e }
                r0 = r64
                java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ all -> 0x017e }
                r60 = r0
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                int r0 = r0.timeout     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                int r0 = r0 * 1000
                r64 = r0
                r0 = r60
                r1 = r64
                r0.setConnectTimeout(r1)     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                int r0 = r0.timeout     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                int r0 = r0 * 1000
                r64 = r0
                r0 = r60
                r1 = r64
                r0.setReadTimeout(r1)     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                java.lang.String r0 = r0.method     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r60
                r1 = r64
                r0.setRequestMethod(r1)     // Catch:{ all -> 0x017e }
                r64 = 0
                r0 = r60
                r1 = r64
                r0.setInstanceFollowRedirects(r1)     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                java.net.URL r0 = r0.requestURL     // Catch:{ all -> 0x017e }
                r64 = r0
                java.lang.String r64 = r64.getUserInfo()     // Catch:{ all -> 0x017e }
                if (r64 == 0) goto L_0x00d5
                java.lang.String r64 = "Adding basic auth header"
                r65 = 0
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
                java.lang.StringBuilder r64 = new java.lang.StringBuilder     // Catch:{ all -> 0x017e }
                r64.<init>()     // Catch:{ all -> 0x017e }
                java.lang.String r65 = "Basic "
                java.lang.StringBuilder r64 = r64.append(r65)     // Catch:{ all -> 0x017e }
                java.lang.String r65 = new java.lang.String     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r66 = r0
                r0 = r66
                java.net.URL r0 = r0.requestURL     // Catch:{ all -> 0x017e }
                r66 = r0
                java.lang.String r66 = r66.getUserInfo()     // Catch:{ all -> 0x017e }
                byte[] r66 = r66.getBytes()     // Catch:{ all -> 0x017e }
                r67 = 0
                java.lang.String r66 = android.util.Base64.encodeToString(r66, r67)     // Catch:{ all -> 0x017e }
                r65.<init>(r66)     // Catch:{ all -> 0x017e }
                java.lang.StringBuilder r64 = r64.append(r65)     // Catch:{ all -> 0x017e }
                java.lang.String r9 = r64.toString()     // Catch:{ all -> 0x017e }
                java.lang.String r64 = "Authorization"
                r0 = r60
                r1 = r64
                r0.setRequestProperty(r1, r9)     // Catch:{ all -> 0x017e }
            L_0x00d5:
                java.lang.String r64 = "Opening connection to: %s"
                r65 = 1
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                r66 = 0
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r67 = r0
                r0 = r67
                java.net.URL r0 = r0.requestURL     // Catch:{ all -> 0x017e }
                r67 = r0
                r65[r66] = r67     // Catch:{ all -> 0x017e }
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
                java.lang.String r54 = "UTF-8"
                r64 = 0
                java.lang.Boolean r63 = java.lang.Boolean.valueOf(r64)     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                java.util.Map<java.lang.String, java.lang.String> r0 = r0.requestHeaders     // Catch:{ all -> 0x017e }
                r64 = r0
                if (r64 == 0) goto L_0x03b4
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                java.util.Map<java.lang.String, java.lang.String> r0 = r0.requestHeaders     // Catch:{ all -> 0x017e }
                r64 = r0
                java.util.Set r64 = r64.entrySet()     // Catch:{ all -> 0x017e }
                java.util.Iterator r64 = r64.iterator()     // Catch:{ all -> 0x017e }
            L_0x011c:
                boolean r65 = r64.hasNext()     // Catch:{ all -> 0x017e }
                if (r65 == 0) goto L_0x03b4
                java.lang.Object r34 = r64.next()     // Catch:{ all -> 0x017e }
                java.util.Map$Entry r34 = (java.util.Map.Entry) r34     // Catch:{ all -> 0x017e }
                java.lang.Object r41 = r34.getKey()     // Catch:{ all -> 0x017e }
                java.lang.String r41 = (java.lang.String) r41     // Catch:{ all -> 0x017e }
                java.lang.Object r62 = r34.getValue()     // Catch:{ all -> 0x017e }
                java.lang.String r62 = (java.lang.String) r62     // Catch:{ all -> 0x017e }
                java.lang.String r65 = "Content-Type"
                r0 = r65
                r1 = r41
                boolean r65 = r0.equalsIgnoreCase(r1)     // Catch:{ all -> 0x017e }
                if (r65 == 0) goto L_0x0174
                java.lang.String r65 = "Content-Type header value for POST/PUT is: %s"
                r66 = 1
                r0 = r66
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r66 = r0
                r67 = 0
                r66[r67] = r62     // Catch:{ all -> 0x017e }
                network.NetworkRequest.debug(r65, r66)     // Catch:{ all -> 0x017e }
                r65 = 1
                java.lang.Boolean r63 = java.lang.Boolean.valueOf(r65)     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r65 = r0
                r0 = r65
                java.lang.Boolean r0 = r0.bBodyIsText     // Catch:{ all -> 0x017e }
                r65 = r0
                boolean r65 = r65.booleanValue()     // Catch:{ all -> 0x017e }
                if (r65 == 0) goto L_0x0174
                java.lang.String r29 = network.NetworkRequest.getContentTypeEncoding(r62)     // Catch:{ all -> 0x017e }
                if (r29 == 0) goto L_0x0382
                java.nio.charset.Charset.forName(r29)     // Catch:{ Exception -> 0x0364 }
                r54 = r29
            L_0x0174:
                r0 = r60
                r1 = r41
                r2 = r62
                r0.setRequestProperty(r1, r2)     // Catch:{ all -> 0x017e }
                goto L_0x011c
            L_0x017e:
                r64 = move-exception
                if (r60 == 0) goto L_0x0184
                r60.disconnect()     // Catch:{ Exception -> 0x0185 }
            L_0x0184:
                throw r64     // Catch:{ Exception -> 0x0185 }
            L_0x0185:
                r33 = move-exception
                java.lang.String r45 = r33.getMessage()
                if (r45 != 0) goto L_0x0196
                r0 = r33
                boolean r0 = r0 instanceof java.net.SocketTimeoutException
                r64 = r0
                if (r64 == 0) goto L_0x0e1e
                java.lang.String r45 = "The request timed out"
            L_0x0196:
                r8 = 1
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters
                r64 = r0
                if (r64 == 0) goto L_0x01d6
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters
                r64 = r0
                r0 = r64
                java.net.URL r0 = r0.requestURL
                r64 = r0
                if (r64 == 0) goto L_0x0e2e
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters
                r64 = r0
                r0 = r64
                java.net.URL r0 = r0.requestURL
                r64 = r0
                java.lang.String r61 = r64.toString()
                java.lang.String r64 = "https://api.amplitude.com"
                r0 = r61
                r1 = r64
                boolean r64 = r0.startsWith(r1)
                if (r64 != 0) goto L_0x01d5
                java.lang.String r64 = "https://api.intercom.io"
                r0 = r61
                r1 = r64
                boolean r64 = r0.startsWith(r1)
                if (r64 == 0) goto L_0x01d6
            L_0x01d5:
                r8 = 0
            L_0x01d6:
                if (r8 == 0) goto L_0x01ea
                r64 = 0
                r0 = r64
                java.lang.Object[] r0 = new java.lang.Object[r0]
                r64 = r0
                r0 = r45
                r1 = r64
                network.NetworkRequest.error(r0, r1)
                r33.printStackTrace()
            L_0x01ea:
                java.lang.String r64 = "Exception during request: %s"
                r65 = 1
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]
                r65 = r0
                r66 = 0
                r65[r66] = r45
                network.NetworkRequest.debug(r64, r65)
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState
                r64 = r0
                r65 = 1
                java.lang.Boolean r65 = java.lang.Boolean.valueOf(r65)
                r0 = r65
                r1 = r64
                r1.isError = r0
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState
                r64 = r0
                r0 = r45
                r1 = r64
                r1.response = r0
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState
                r64 = r0
                java.lang.String r65 = "errorMessage"
                r0 = r64
                r1 = r65
                r2 = r45
                r0.setDebugValue(r1, r2)
            L_0x022a:
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState
                r64 = r0
                r0 = r64
                java.lang.Boolean r0 = r0.isError
                r64 = r0
                boolean r64 = r64.booleanValue()
                if (r64 != 0) goto L_0x0266
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState
                r64 = r0
                r0 = r64
                java.lang.Object r0 = r0.response
                r64 = r0
                if (r64 != 0) goto L_0x0266
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState
                r64 = r0
                java.lang.String r65 = ""
                r0 = r65
                r1 = r64
                r1.response = r0
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState
                r64 = r0
                java.lang.String r65 = "text"
                r0 = r65
                r1 = r64
                r1.responseType = r0
            L_0x0266:
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState
                r64 = r0
                r0 = r64
                java.lang.Boolean r0 = r0.isError
                r64 = r0
                boolean r64 = r64.booleanValue()
                if (r64 != 0) goto L_0x02f7
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState
                r64 = r0
                r0 = r64
                int r0 = r0.status
                r64 = r0
                r65 = 200(0xc8, float:2.8E-43)
                r0 = r64
                r1 = r65
                if (r0 < r1) goto L_0x02f7
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState
                r64 = r0
                r0 = r64
                int r0 = r0.status
                r64 = r0
                r65 = 300(0x12c, float:4.2E-43)
                r0 = r64
                r1 = r65
                if (r0 >= r1) goto L_0x02f7
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState
                r64 = r0
                r0 = r64
                java.util.concurrent.atomic.AtomicBoolean r0 = r0.isRequestCancelled
                r64 = r0
                boolean r64 = r64.get()
                if (r64 != 0) goto L_0x02f7
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters
                r64 = r0
                r0 = r64
                com.ansca.corona.notifications.StatusBarNotificationSettings r0 = r0.successNotificationSettings
                r64 = r0
                if (r64 == 0) goto L_0x02f7
                com.ansca.corona.notifications.NotificationServices r46 = new com.ansca.corona.notifications.NotificationServices
                android.content.Context r64 = com.ansca.corona.CoronaEnvironment.getApplicationContext()
                r0 = r46
                r1 = r64
                r0.<init>(r1)
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters
                r64 = r0
                r0 = r64
                com.ansca.corona.notifications.StatusBarNotificationSettings r0 = r0.successNotificationSettings
                r57 = r0
                int r64 = r46.reserveId()
                r0 = r57
                r1 = r64
                r0.setId(r1)
                java.util.Date r64 = new java.util.Date
                r64.<init>()
                r0 = r57
                r1 = r64
                r0.setTimestamp(r1)
                r0 = r46
                r1 = r57
                r0.post((com.ansca.corona.notifications.NotificationSettings) r1)
            L_0x02f7:
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState
                r64 = r0
                java.lang.String r65 = "ended"
                r0 = r65
                r1 = r64
                r1.phase = r0
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters
                r64 = r0
                r0 = r64
                network.NetworkRequest$ProgressDirection r0 = r0.progressDirection
                r64 = r0
                network.NetworkRequest$ProgressDirection r65 = network.NetworkRequest.ProgressDirection.NONE
                r0 = r64
                r1 = r65
                if (r0 != r1) goto L_0x0331
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState
                r64 = r0
                r0 = r16
                r2 = r64
                r2.bytesTransferred = r0
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState
                r64 = r0
                r0 = r18
                r2 = r64
                r2.bytesEstimated = r0
            L_0x0331:
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters
                r64 = r0
                r0 = r64
                network.NetworkRequest$LuaCallback r0 = r0.callback
                r64 = r0
                if (r64 == 0) goto L_0x0356
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters
                r64 = r0
                r0 = r64
                network.NetworkRequest$LuaCallback r0 = r0.callback
                r64 = r0
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState
                r65 = r0
                r66 = 1
                r64.call(r65, r66)
            L_0x0356:
                r0 = r70
                java.util.concurrent.CopyOnWriteArrayList<network.NetworkRequest$AsyncNetworkRequestRunnable> r0 = r0.connectionList
                r64 = r0
                r0 = r64
                r1 = r70
                r0.remove(r1)
                return
            L_0x0364:
                r33 = move-exception
                java.lang.Exception r64 = new java.lang.Exception     // Catch:{ all -> 0x017e }
                java.lang.StringBuilder r65 = new java.lang.StringBuilder     // Catch:{ all -> 0x017e }
                r65.<init>()     // Catch:{ all -> 0x017e }
                java.lang.String r66 = "Caller specified an unsupported character encoding in Content-Type charset, was: "
                java.lang.StringBuilder r65 = r65.append(r66)     // Catch:{ all -> 0x017e }
                r0 = r65
                r1 = r29
                java.lang.StringBuilder r65 = r0.append(r1)     // Catch:{ all -> 0x017e }
                java.lang.String r65 = r65.toString()     // Catch:{ all -> 0x017e }
                r64.<init>(r65)     // Catch:{ all -> 0x017e }
                throw r64     // Catch:{ all -> 0x017e }
            L_0x0382:
                java.lang.StringBuilder r65 = new java.lang.StringBuilder     // Catch:{ all -> 0x017e }
                r65.<init>()     // Catch:{ all -> 0x017e }
                r0 = r65
                r1 = r62
                java.lang.StringBuilder r65 = r0.append(r1)     // Catch:{ all -> 0x017e }
                java.lang.String r66 = "; charset="
                java.lang.StringBuilder r65 = r65.append(r66)     // Catch:{ all -> 0x017e }
                r0 = r65
                r1 = r54
                java.lang.StringBuilder r65 = r0.append(r1)     // Catch:{ all -> 0x017e }
                java.lang.String r62 = r65.toString()     // Catch:{ all -> 0x017e }
                java.lang.String r65 = "Adding default charset to Content-Type header: %s"
                r66 = 1
                r0 = r66
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r66 = r0
                r67 = 0
                r66[r67] = r62     // Catch:{ all -> 0x017e }
                network.NetworkRequest.debug(r65, r66)     // Catch:{ all -> 0x017e }
                goto L_0x0174
            L_0x03b4:
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                java.lang.String r0 = r0.method     // Catch:{ all -> 0x017e }
                r64 = r0
                java.lang.String r65 = "POST"
                boolean r64 = r64.equals(r65)     // Catch:{ all -> 0x017e }
                if (r64 != 0) goto L_0x03dc
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                java.lang.String r0 = r0.method     // Catch:{ all -> 0x017e }
                r64 = r0
                java.lang.String r65 = "PUT"
                boolean r64 = r64.equals(r65)     // Catch:{ all -> 0x017e }
                if (r64 == 0) goto L_0x06e4
            L_0x03dc:
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                java.lang.Object r0 = r0.requestBody     // Catch:{ all -> 0x017e }
                r64 = r0
                if (r64 == 0) goto L_0x03fd
                boolean r64 = r63.booleanValue()     // Catch:{ all -> 0x017e }
                if (r64 != 0) goto L_0x03fd
                java.lang.String r64 = "No Content-Type request header was provided for the POST/PUT"
                r65 = 0
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
            L_0x03fd:
                r49 = 0
                r36 = 0
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0e3d }
                r64 = r0
                r0 = r64
                java.lang.Object r0 = r0.requestBody     // Catch:{ all -> 0x0e3d }
                r64 = r0
                r0 = r64
                boolean r0 = r0 instanceof java.lang.String     // Catch:{ all -> 0x0e3d }
                r64 = r0
                if (r64 == 0) goto L_0x0577
                java.lang.String r64 = "Request body is text from Lua string (requestBodyCharset: %s)"
                r65 = 1
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0e3d }
                r65 = r0
                r66 = 0
                r65[r66] = r54     // Catch:{ all -> 0x0e3d }
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x0e3d }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0e3d }
                r64 = r0
                r0 = r64
                java.lang.Object r11 = r0.requestBody     // Catch:{ all -> 0x0e3d }
                java.lang.String r11 = (java.lang.String) r11     // Catch:{ all -> 0x0e3d }
                java.io.ByteArrayInputStream r37 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x0e3d }
                r0 = r54
                byte[] r64 = r11.getBytes(r0)     // Catch:{ all -> 0x0e3d }
                r0 = r37
                r1 = r64
                r0.<init>(r1)     // Catch:{ all -> 0x0e3d }
                int r64 = r37.available()     // Catch:{ all -> 0x0602 }
                r0 = r64
                long r0 = (long) r0     // Catch:{ all -> 0x0602 }
                r20 = r0
            L_0x044a:
                java.lang.String r64 = "Request body size: %d"
                r65 = 1
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0602 }
                r65 = r0
                r66 = 0
                java.lang.Long r67 = java.lang.Long.valueOf(r20)     // Catch:{ all -> 0x0602 }
                r65[r66] = r67     // Catch:{ all -> 0x0602 }
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x0602 }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0602 }
                r64 = r0
                r0 = r64
                network.NetworkRequest$ProgressDirection r0 = r0.progressDirection     // Catch:{ all -> 0x0602 }
                r64 = r0
                network.NetworkRequest$ProgressDirection r65 = network.NetworkRequest.ProgressDirection.UPLOAD     // Catch:{ all -> 0x0602 }
                r0 = r64
                r1 = r65
                if (r0 != r1) goto L_0x04b0
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0602 }
                r64 = r0
                java.lang.String r65 = "began"
                r0 = r65
                r1 = r64
                r1.phase = r0     // Catch:{ all -> 0x0602 }
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0602 }
                r64 = r0
                r0 = r20
                r2 = r64
                r2.bytesEstimated = r0     // Catch:{ all -> 0x0602 }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0602 }
                r64 = r0
                r0 = r64
                network.NetworkRequest$LuaCallback r0 = r0.callback     // Catch:{ all -> 0x0602 }
                r64 = r0
                if (r64 == 0) goto L_0x04b0
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0602 }
                r64 = r0
                r0 = r64
                network.NetworkRequest$LuaCallback r0 = r0.callback     // Catch:{ all -> 0x0602 }
                r64 = r0
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0602 }
                r65 = r0
                r64.call(r65)     // Catch:{ all -> 0x0602 }
            L_0x04b0:
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0602 }
                r64 = r0
                java.lang.String r65 = "progress"
                r0 = r65
                r1 = r64
                r1.phase = r0     // Catch:{ all -> 0x0602 }
                if (r37 == 0) goto L_0x06d3
                r64 = 1
                r0 = r60
                r1 = r64
                r0.setDoOutput(r1)     // Catch:{ all -> 0x0602 }
                r64 = 0
                int r64 = (r20 > r64 ? 1 : (r20 == r64 ? 0 : -1))
                if (r64 <= 0) goto L_0x05f7
                r64 = 2147483647(0x7fffffff, double:1.060997895E-314)
                int r64 = (r20 > r64 ? 1 : (r20 == r64 ? 0 : -1))
                if (r64 >= 0) goto L_0x05f7
                r0 = r20
                int r0 = (int) r0     // Catch:{ all -> 0x0602 }
                r64 = r0
                r0 = r60
                r1 = r64
                r0.setFixedLengthStreamingMode(r1)     // Catch:{ all -> 0x0602 }
            L_0x04e2:
                java.io.BufferedInputStream r36 = new java.io.BufferedInputStream     // Catch:{ all -> 0x0602 }
                r36.<init>(r37)     // Catch:{ all -> 0x0602 }
                java.io.BufferedOutputStream r50 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x0e3d }
                java.io.OutputStream r64 = r60.getOutputStream()     // Catch:{ all -> 0x0e3d }
                r0 = r50
                r1 = r64
                r0.<init>(r1)     // Catch:{ all -> 0x0e3d }
                r56 = 1024(0x400, float:1.435E-42)
                r64 = 1024(0x400, float:1.435E-42)
                r0 = r64
                byte[] r12 = new byte[r0]     // Catch:{ all -> 0x0569 }
                r13 = 0
            L_0x04fd:
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0569 }
                r64 = r0
                r0 = r64
                java.util.concurrent.atomic.AtomicBoolean r0 = r0.isRequestCancelled     // Catch:{ all -> 0x0569 }
                r64 = r0
                boolean r64 = r64.get()     // Catch:{ all -> 0x0569 }
                if (r64 != 0) goto L_0x0607
                r0 = r36
                int r13 = r0.read(r12)     // Catch:{ all -> 0x0569 }
                if (r13 <= 0) goto L_0x0607
                r64 = 0
                r0 = r50
                r1 = r64
                r0.write(r12, r1, r13)     // Catch:{ all -> 0x0569 }
                long r0 = (long) r13     // Catch:{ all -> 0x0569 }
                r64 = r0
                long r22 = r22 + r64
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0569 }
                r64 = r0
                r0 = r64
                network.NetworkRequest$ProgressDirection r0 = r0.progressDirection     // Catch:{ all -> 0x0569 }
                r64 = r0
                network.NetworkRequest$ProgressDirection r65 = network.NetworkRequest.ProgressDirection.UPLOAD     // Catch:{ all -> 0x0569 }
                r0 = r64
                r1 = r65
                if (r0 != r1) goto L_0x04fd
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0569 }
                r64 = r0
                r0 = r22
                r2 = r64
                r2.bytesTransferred = r0     // Catch:{ all -> 0x0569 }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0569 }
                r64 = r0
                r0 = r64
                network.NetworkRequest$LuaCallback r0 = r0.callback     // Catch:{ all -> 0x0569 }
                r64 = r0
                if (r64 == 0) goto L_0x04fd
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0569 }
                r64 = r0
                r0 = r64
                network.NetworkRequest$LuaCallback r0 = r0.callback     // Catch:{ all -> 0x0569 }
                r64 = r0
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0569 }
                r65 = r0
                r64.call(r65)     // Catch:{ all -> 0x0569 }
                goto L_0x04fd
            L_0x0569:
                r64 = move-exception
                r49 = r50
            L_0x056c:
                if (r36 == 0) goto L_0x0571
                r36.close()     // Catch:{ all -> 0x017e }
            L_0x0571:
                if (r49 == 0) goto L_0x0576
                r49.close()     // Catch:{ all -> 0x017e }
            L_0x0576:
                throw r64     // Catch:{ all -> 0x017e }
            L_0x0577:
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0e3d }
                r64 = r0
                r0 = r64
                java.lang.Object r0 = r0.requestBody     // Catch:{ all -> 0x0e3d }
                r64 = r0
                r0 = r64
                boolean r0 = r0 instanceof byte[]     // Catch:{ all -> 0x0e3d }
                r64 = r0
                if (r64 == 0) goto L_0x05bc
                java.lang.String r64 = "Request body is binary from Lua string"
                r65 = 0
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0e3d }
                r65 = r0
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x0e3d }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0e3d }
                r64 = r0
                r0 = r64
                java.lang.Object r0 = r0.requestBody     // Catch:{ all -> 0x0e3d }
                r64 = r0
                byte[] r64 = (byte[]) r64     // Catch:{ all -> 0x0e3d }
                r0 = r64
                byte[] r0 = (byte[]) r0     // Catch:{ all -> 0x0e3d }
                r10 = r0
                java.io.ByteArrayInputStream r37 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x0e3d }
                r0 = r37
                r0.<init>(r10)     // Catch:{ all -> 0x0e3d }
                int r0 = r10.length     // Catch:{ all -> 0x0602 }
                r64 = r0
                r0 = r64
                long r0 = (long) r0
                r20 = r0
                goto L_0x044a
            L_0x05bc:
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0e3d }
                r64 = r0
                r0 = r64
                java.lang.Object r0 = r0.requestBody     // Catch:{ all -> 0x0e3d }
                r64 = r0
                r0 = r64
                boolean r0 = r0 instanceof network.NetworkRequest.CoronaFileSpec     // Catch:{ all -> 0x0e3d }
                r64 = r0
                if (r64 == 0) goto L_0x0e50
                java.lang.String r64 = "Request body is from a file"
                r65 = 0
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0e3d }
                r65 = r0
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x0e3d }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0e3d }
                r64 = r0
                r0 = r64
                java.lang.Object r0 = r0.requestBody     // Catch:{ all -> 0x0e3d }
                r35 = r0
                network.NetworkRequest$CoronaFileSpec r35 = (network.NetworkRequest.CoronaFileSpec) r35     // Catch:{ all -> 0x0e3d }
                java.io.InputStream r36 = r35.getInputStream()     // Catch:{ all -> 0x0e3d }
                long r20 = r35.getFileSize()     // Catch:{ all -> 0x0e3d }
                r37 = r36
                goto L_0x044a
            L_0x05f7:
                r64 = 0
                r0 = r60
                r1 = r64
                r0.setChunkedStreamingMode(r1)     // Catch:{ all -> 0x0602 }
                goto L_0x04e2
            L_0x0602:
                r64 = move-exception
                r36 = r37
                goto L_0x056c
            L_0x0607:
                r49 = r50
            L_0x0609:
                if (r36 == 0) goto L_0x060e
                r36.close()     // Catch:{ all -> 0x017e }
            L_0x060e:
                if (r49 == 0) goto L_0x0613
                r49.close()     // Catch:{ all -> 0x017e }
            L_0x0613:
                java.lang.String r64 = "Waiting for response code (%s)"
                r65 = 1
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                r66 = 0
                java.net.URL r67 = r60.getURL()     // Catch:{ all -> 0x017e }
                java.lang.String r67 = r67.toString()     // Catch:{ all -> 0x017e }
                r65[r66] = r67     // Catch:{ all -> 0x017e }
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
                int r55 = r60.getResponseCode()     // Catch:{ all -> 0x017e }
                java.lang.String r64 = "Got response code %d (%s)"
                r65 = 2
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                r66 = 0
                java.lang.Integer r67 = java.lang.Integer.valueOf(r55)     // Catch:{ all -> 0x017e }
                r65[r66] = r67     // Catch:{ all -> 0x017e }
                r66 = 1
                java.net.URL r67 = r60.getURL()     // Catch:{ all -> 0x017e }
                java.lang.String r67 = r67.toString()     // Catch:{ all -> 0x017e }
                r65[r66] = r67     // Catch:{ all -> 0x017e }
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
                r47 = 0
                r44 = 10
            L_0x0655:
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                java.lang.Boolean r0 = r0.bHandleRedirects     // Catch:{ all -> 0x017e }
                r64 = r0
                boolean r64 = r64.booleanValue()     // Catch:{ all -> 0x017e }
                if (r64 == 0) goto L_0x08e8
                r64 = 301(0x12d, float:4.22E-43)
                r0 = r55
                r1 = r64
                if (r0 == r1) goto L_0x0687
                r64 = 302(0x12e, float:4.23E-43)
                r0 = r55
                r1 = r64
                if (r0 == r1) goto L_0x0687
                r64 = 303(0x12f, float:4.25E-43)
                r0 = r55
                r1 = r64
                if (r0 == r1) goto L_0x0687
                r64 = 307(0x133, float:4.3E-43)
                r0 = r55
                r1 = r64
                if (r0 != r1) goto L_0x08e8
            L_0x0687:
                java.lang.String r64 = "Location"
                r0 = r60
                r1 = r64
                java.lang.String r43 = r0.getHeaderField(r1)     // Catch:{ all -> 0x017e }
                java.net.URL r64 = r60.getURL()     // Catch:{ all -> 0x017e }
                java.lang.String r48 = r64.toString()     // Catch:{ all -> 0x017e }
                java.util.Map r64 = r60.getHeaderFields()     // Catch:{ all -> 0x017e }
                java.lang.String r65 = "Set-Cookie"
                java.lang.Object r28 = r64.get(r65)     // Catch:{ all -> 0x017e }
                java.util.List r28 = (java.util.List) r28     // Catch:{ all -> 0x017e }
                if (r43 == 0) goto L_0x06b3
                java.lang.String r64 = ""
                r0 = r43
                r1 = r64
                boolean r64 = r0.equals(r1)     // Catch:{ all -> 0x017e }
                if (r64 == 0) goto L_0x072b
            L_0x06b3:
                java.lang.RuntimeException r64 = new java.lang.RuntimeException     // Catch:{ all -> 0x017e }
                java.lang.String r65 = "no Location: header in %d redirect response from %s"
                r66 = 2
                r0 = r66
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r66 = r0
                r67 = 0
                java.lang.Integer r68 = java.lang.Integer.valueOf(r55)     // Catch:{ all -> 0x017e }
                r66[r67] = r68     // Catch:{ all -> 0x017e }
                r67 = 1
                r66[r67] = r48     // Catch:{ all -> 0x017e }
                java.lang.String r65 = java.lang.String.format(r65, r66)     // Catch:{ all -> 0x017e }
                r64.<init>(r65)     // Catch:{ all -> 0x017e }
                throw r64     // Catch:{ all -> 0x017e }
            L_0x06d3:
                java.lang.String r64 = "No request body supplied"
                r65 = 0
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0602 }
                r65 = r0
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x0602 }
                r36 = r37
                goto L_0x0609
            L_0x06e4:
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                network.NetworkRequest$ProgressDirection r0 = r0.progressDirection     // Catch:{ all -> 0x017e }
                r64 = r0
                network.NetworkRequest$ProgressDirection r65 = network.NetworkRequest.ProgressDirection.UPLOAD     // Catch:{ all -> 0x017e }
                r0 = r64
                r1 = r65
                if (r0 != r1) goto L_0x0613
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x017e }
                r64 = r0
                java.lang.String r65 = "began"
                r0 = r65
                r1 = r64
                r1.phase = r0     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                network.NetworkRequest$LuaCallback r0 = r0.callback     // Catch:{ all -> 0x017e }
                r64 = r0
                if (r64 == 0) goto L_0x0613
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                network.NetworkRequest$LuaCallback r0 = r0.callback     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x017e }
                r65 = r0
                r64.call(r65)     // Catch:{ all -> 0x017e }
                goto L_0x0613
            L_0x072b:
                int r47 = r47 + 1
                r64 = 10
                r0 = r47
                r1 = r64
                if (r0 != r1) goto L_0x075b
                java.lang.RuntimeException r64 = new java.lang.RuntimeException     // Catch:{ all -> 0x017e }
                java.lang.String r65 = "more than maximum number of redirects attempted (%d) (%s -> %s)"
                r66 = 3
                r0 = r66
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r66 = r0
                r67 = 0
                r68 = 10
                java.lang.Integer r68 = java.lang.Integer.valueOf(r68)     // Catch:{ all -> 0x017e }
                r66[r67] = r68     // Catch:{ all -> 0x017e }
                r67 = 1
                r66[r67] = r48     // Catch:{ all -> 0x017e }
                r67 = 2
                r66[r67] = r43     // Catch:{ all -> 0x017e }
                java.lang.String r65 = java.lang.String.format(r65, r66)     // Catch:{ all -> 0x017e }
                r64.<init>(r65)     // Catch:{ all -> 0x017e }
                throw r64     // Catch:{ all -> 0x017e }
            L_0x075b:
                r64 = 5
                r0 = r48
                r1 = r64
                java.lang.String r64 = r0.substring(r1)     // Catch:{ all -> 0x017e }
                java.lang.String r65 = "https"
                boolean r64 = r64.equalsIgnoreCase(r65)     // Catch:{ all -> 0x017e }
                if (r64 == 0) goto L_0x07ad
                r64 = 5
                r0 = r43
                r1 = r64
                java.lang.String r64 = r0.substring(r1)     // Catch:{ all -> 0x017e }
                java.lang.String r65 = "http:"
                boolean r64 = r64.equalsIgnoreCase(r65)     // Catch:{ all -> 0x017e }
                if (r64 == 0) goto L_0x07ad
                java.io.PrintStream r64 = java.lang.System.out     // Catch:{ all -> 0x017e }
                java.lang.StringBuilder r65 = new java.lang.StringBuilder     // Catch:{ all -> 0x017e }
                r65.<init>()     // Catch:{ all -> 0x017e }
                java.lang.String r66 = "WARNING: "
                java.lang.StringBuilder r65 = r65.append(r66)     // Catch:{ all -> 0x017e }
                java.lang.String r66 = "redirecting from HTTPS to HTTP (%s -> %s)"
                r67 = 2
                r0 = r67
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r67 = r0
                r68 = 0
                r67[r68] = r48     // Catch:{ all -> 0x017e }
                r68 = 1
                r67[r68] = r43     // Catch:{ all -> 0x017e }
                java.lang.String r66 = java.lang.String.format(r66, r67)     // Catch:{ all -> 0x017e }
                java.lang.StringBuilder r65 = r65.append(r66)     // Catch:{ all -> 0x017e }
                java.lang.String r65 = r65.toString()     // Catch:{ all -> 0x017e }
                r64.println(r65)     // Catch:{ all -> 0x017e }
            L_0x07ad:
                java.net.URL r59 = new java.net.URL     // Catch:{ all -> 0x017e }
                r0 = r59
                r1 = r43
                r0.<init>(r1)     // Catch:{ all -> 0x017e }
                java.lang.String r64 = "Handling %d redirect to: %s"
                r65 = 2
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                r66 = 0
                java.lang.Integer r67 = java.lang.Integer.valueOf(r55)     // Catch:{ all -> 0x017e }
                r65[r66] = r67     // Catch:{ all -> 0x017e }
                r66 = 1
                r65[r66] = r43     // Catch:{ all -> 0x017e }
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
                java.net.URLConnection r64 = r59.openConnection()     // Catch:{ all -> 0x017e }
                r0 = r64
                java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ all -> 0x017e }
                r60 = r0
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                int r0 = r0.timeout     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                int r0 = r0 * 1000
                r64 = r0
                r0 = r60
                r1 = r64
                r0.setConnectTimeout(r1)     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                int r0 = r0.timeout     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                int r0 = r0 * 1000
                r64 = r0
                r0 = r60
                r1 = r64
                r0.setReadTimeout(r1)     // Catch:{ all -> 0x017e }
                r64 = 0
                r0 = r60
                r1 = r64
                r0.setInstanceFollowRedirects(r1)     // Catch:{ all -> 0x017e }
                if (r28 == 0) goto L_0x08a8
                java.lang.String r27 = ""
                java.util.Iterator r64 = r28.iterator()     // Catch:{ all -> 0x017e }
            L_0x081c:
                boolean r65 = r64.hasNext()     // Catch:{ all -> 0x017e }
                if (r65 == 0) goto L_0x088c
                java.lang.Object r26 = r64.next()     // Catch:{ all -> 0x017e }
                java.lang.String r26 = (java.lang.String) r26     // Catch:{ all -> 0x017e }
                java.lang.String r65 = "=== set cookie: %s ('%s')"
                r66 = 2
                r0 = r66
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r66 = r0
                r67 = 0
                r66[r67] = r26     // Catch:{ all -> 0x017e }
                r67 = 1
                java.lang.String r68 = ";"
                r0 = r26
                r1 = r68
                java.lang.String[] r68 = r0.split(r1)     // Catch:{ all -> 0x017e }
                r69 = 0
                r68 = r68[r69]     // Catch:{ all -> 0x017e }
                r66[r67] = r68     // Catch:{ all -> 0x017e }
                network.NetworkRequest.debug(r65, r66)     // Catch:{ all -> 0x017e }
                int r65 = r27.length()     // Catch:{ all -> 0x017e }
                if (r65 <= 0) goto L_0x0868
                java.lang.StringBuilder r65 = new java.lang.StringBuilder     // Catch:{ all -> 0x017e }
                r65.<init>()     // Catch:{ all -> 0x017e }
                r0 = r65
                r1 = r27
                java.lang.StringBuilder r65 = r0.append(r1)     // Catch:{ all -> 0x017e }
                java.lang.String r66 = "; "
                java.lang.StringBuilder r65 = r65.append(r66)     // Catch:{ all -> 0x017e }
                java.lang.String r27 = r65.toString()     // Catch:{ all -> 0x017e }
            L_0x0868:
                java.lang.StringBuilder r65 = new java.lang.StringBuilder     // Catch:{ all -> 0x017e }
                r65.<init>()     // Catch:{ all -> 0x017e }
                r0 = r65
                r1 = r27
                java.lang.StringBuilder r65 = r0.append(r1)     // Catch:{ all -> 0x017e }
                java.lang.String r66 = ";"
                r0 = r26
                r1 = r66
                java.lang.String[] r66 = r0.split(r1)     // Catch:{ all -> 0x017e }
                r67 = 0
                r66 = r66[r67]     // Catch:{ all -> 0x017e }
                java.lang.StringBuilder r65 = r65.append(r66)     // Catch:{ all -> 0x017e }
                java.lang.String r27 = r65.toString()     // Catch:{ all -> 0x017e }
                goto L_0x081c
            L_0x088c:
                java.lang.String r64 = "=== set Cookie: %s"
                r65 = 1
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                r66 = 0
                r65[r66] = r27     // Catch:{ all -> 0x017e }
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
                java.lang.String r64 = "Cookie"
                r0 = r60
                r1 = r64
                r2 = r27
                r0.addRequestProperty(r1, r2)     // Catch:{ all -> 0x017e }
            L_0x08a8:
                java.lang.String r64 = "Waiting for response code (%s)"
                r65 = 1
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                r66 = 0
                java.net.URL r67 = r60.getURL()     // Catch:{ all -> 0x017e }
                java.lang.String r67 = r67.toString()     // Catch:{ all -> 0x017e }
                r65[r66] = r67     // Catch:{ all -> 0x017e }
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
                int r55 = r60.getResponseCode()     // Catch:{ all -> 0x017e }
                java.lang.String r64 = "Got response code %d (%s)"
                r65 = 2
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                r66 = 0
                java.lang.Integer r67 = java.lang.Integer.valueOf(r55)     // Catch:{ all -> 0x017e }
                r65[r66] = r67     // Catch:{ all -> 0x017e }
                r66 = 1
                java.net.URL r67 = r60.getURL()     // Catch:{ all -> 0x017e }
                java.lang.String r67 = r67.toString()     // Catch:{ all -> 0x017e }
                r65[r66] = r67     // Catch:{ all -> 0x017e }
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
                goto L_0x0655
            L_0x08e8:
                r64 = 0
                java.lang.Boolean r40 = java.lang.Boolean.valueOf(r64)     // Catch:{ all -> 0x017e }
                r38 = 0
                r64 = 200(0xc8, float:2.8E-43)
                r0 = r55
                r1 = r64
                if (r0 < r1) goto L_0x0b87
                r64 = 300(0x12c, float:4.2E-43)
                r0 = r55
                r1 = r64
                if (r0 >= r1) goto L_0x0b87
                java.io.InputStream r38 = r60.getInputStream()     // Catch:{ all -> 0x017e }
                if (r38 == 0) goto L_0x0e4c
                java.io.PushbackInputStream r39 = new java.io.PushbackInputStream     // Catch:{ all -> 0x017e }
                r0 = r39
                r1 = r38
                r0.<init>(r1)     // Catch:{ all -> 0x017e }
                int r6 = r39.read()     // Catch:{ all -> 0x017e }
                r64 = -1
                r0 = r64
                if (r0 == r6) goto L_0x0b80
                r0 = r39
                java.io.PushbackInputStream r0 = (java.io.PushbackInputStream) r0     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                r0.unread(r6)     // Catch:{ all -> 0x017e }
                r38 = r39
            L_0x0926:
                r39 = r38
            L_0x0928:
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r55
                r1 = r64
                r1.status = r0     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x017e }
                r64 = r0
                java.util.Map r65 = r60.getHeaderFields()     // Catch:{ all -> 0x017e }
                r0 = r65
                r1 = r64
                r1.responseHeaders = r0     // Catch:{ all -> 0x017e }
                if (r39 == 0) goto L_0x094f
                int r64 = r60.getContentLength()     // Catch:{ all -> 0x017e }
                r0 = r64
                long r0 = (long) r0     // Catch:{ all -> 0x017e }
                r18 = r0
            L_0x094f:
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                network.NetworkRequest$ProgressDirection r0 = r0.progressDirection     // Catch:{ all -> 0x017e }
                r64 = r0
                network.NetworkRequest$ProgressDirection r65 = network.NetworkRequest.ProgressDirection.DOWNLOAD     // Catch:{ all -> 0x017e }
                r0 = r64
                r1 = r65
                if (r0 != r1) goto L_0x09a0
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x017e }
                r64 = r0
                java.lang.String r65 = "began"
                r0 = r65
                r1 = r64
                r1.phase = r0     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r18
                r2 = r64
                r2.bytesEstimated = r0     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                network.NetworkRequest$LuaCallback r0 = r0.callback     // Catch:{ all -> 0x017e }
                r64 = r0
                if (r64 == 0) goto L_0x09a0
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                network.NetworkRequest$LuaCallback r0 = r0.callback     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x017e }
                r65 = r0
                r64.call(r65)     // Catch:{ all -> 0x017e }
            L_0x09a0:
                if (r39 == 0) goto L_0x0e48
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x017e }
                r64 = r0
                java.lang.String r65 = "progress"
                r0 = r65
                r1 = r64
                r1.phase = r0     // Catch:{ all -> 0x017e }
                java.lang.String r25 = r60.getContentType()     // Catch:{ all -> 0x017e }
                if (r25 != 0) goto L_0x09b8
                java.lang.String r25 = ""
            L_0x09b8:
                java.lang.String r15 = network.NetworkRequest.getContentTypeEncoding(r25)     // Catch:{ all -> 0x017e }
                if (r15 == 0) goto L_0x09ea
                java.lang.String r64 = "Writing protocol charset debug"
                r65 = 0
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x017e }
                r64 = r0
                java.lang.String r65 = "charset"
                r0 = r64
                r1 = r65
                r0.setDebugValue(r1, r15)     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x017e }
                r64 = r0
                java.lang.String r65 = "charsetSource"
                java.lang.String r66 = "protocol"
                r64.setDebugValue(r65, r66)     // Catch:{ all -> 0x017e }
                java.nio.charset.Charset.forName(r15)     // Catch:{ Exception -> 0x0b97 }
            L_0x09ea:
                if (r15 != 0) goto L_0x0e44
                boolean r64 = network.NetworkRequest.isContentTypeText(r25)     // Catch:{ all -> 0x017e }
                if (r64 == 0) goto L_0x0e40
                r64 = 1024(0x400, float:1.435E-42)
                r0 = r64
                byte[] r0 = new byte[r0]     // Catch:{ all -> 0x017e }
                r52 = r0
                java.io.PushbackInputStream r38 = new java.io.PushbackInputStream     // Catch:{ all -> 0x017e }
                r0 = r52
                int r0 = r0.length     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r38
                r1 = r39
                r2 = r64
                r0.<init>(r1, r2)     // Catch:{ all -> 0x017e }
                r0 = r38
                r1 = r52
                int r53 = r0.read(r1)     // Catch:{ all -> 0x017e }
                if (r53 <= 0) goto L_0x0a74
                java.lang.String r51 = new java.lang.String     // Catch:{ all -> 0x017e }
                r64 = 0
                java.lang.String r65 = "usascii"
                r0 = r51
                r1 = r52
                r2 = r64
                r3 = r53
                r4 = r65
                r0.<init>(r1, r2, r3, r4)     // Catch:{ all -> 0x017e }
                r0 = r25
                r1 = r51
                java.lang.String r24 = network.NetworkRequest.getEncodingFromContent(r0, r1)     // Catch:{ all -> 0x017e }
                if (r24 == 0) goto L_0x0a61
                java.lang.String r64 = "Writing content charset debug"
                r65 = 0
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x017e }
                r64 = r0
                java.lang.String r65 = "charset"
                r0 = r64
                r1 = r65
                r2 = r24
                r0.setDebugValue(r1, r2)     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x017e }
                r64 = r0
                java.lang.String r65 = "charsetSource"
                java.lang.String r66 = "content"
                r64.setDebugValue(r65, r66)     // Catch:{ all -> 0x017e }
                java.nio.charset.Charset.forName(r24)     // Catch:{ Exception -> 0x0bbb }
                r15 = r24
            L_0x0a61:
                r0 = r38
                java.io.PushbackInputStream r0 = (java.io.PushbackInputStream) r0     // Catch:{ all -> 0x017e }
                r64 = r0
                r65 = 0
                r0 = r64
                r1 = r52
                r2 = r65
                r3 = r53
                r0.unread(r1, r2, r3)     // Catch:{ all -> 0x017e }
            L_0x0a74:
                if (r15 != 0) goto L_0x0aa1
                java.lang.String r15 = "UTF-8"
                java.lang.String r64 = "Writing implicit charset debug"
                r65 = 0
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x017e }
                r64 = r0
                java.lang.String r65 = "charset"
                r0 = r64
                r1 = r65
                r0.setDebugValue(r1, r15)     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x017e }
                r64 = r0
                java.lang.String r65 = "charsetSource"
                java.lang.String r66 = "implicit"
                r64.setDebugValue(r65, r66)     // Catch:{ all -> 0x017e }
            L_0x0aa1:
                boolean r64 = r40.booleanValue()     // Catch:{ all -> 0x017e }
                if (r64 == 0) goto L_0x0aab
                if (r15 != 0) goto L_0x0aab
                java.lang.String r15 = "UTF-8"
            L_0x0aab:
                r14 = 1024(0x400, float:1.435E-42)
                if (r15 == 0) goto L_0x0c17
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                network.NetworkRequest$CoronaFileSpec r0 = r0.response     // Catch:{ all -> 0x017e }
                r64 = r0
                if (r64 == 0) goto L_0x0ac3
                boolean r64 = r40.booleanValue()     // Catch:{ all -> 0x017e }
                if (r64 == 0) goto L_0x0c17
            L_0x0ac3:
                java.lang.String r64 = "Response content was text, to be decoded with: %s"
                r65 = 1
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                r66 = 0
                r65[r66] = r15     // Catch:{ all -> 0x017e }
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x017e }
                r64 = r0
                java.lang.String r65 = "text"
                r0 = r65
                r1 = r64
                r1.responseType = r0     // Catch:{ all -> 0x017e }
                r36 = 0
                r49 = 0
                java.io.BufferedReader r37 = new java.io.BufferedReader     // Catch:{ all -> 0x0e35 }
                java.io.InputStreamReader r64 = new java.io.InputStreamReader     // Catch:{ all -> 0x0e35 }
                r0 = r64
                r1 = r38
                r0.<init>(r1, r15)     // Catch:{ all -> 0x0e35 }
                r0 = r37
                r1 = r64
                r0.<init>(r1)     // Catch:{ all -> 0x0e35 }
                java.io.StringWriter r50 = new java.io.StringWriter     // Catch:{ all -> 0x0e38 }
                r50.<init>()     // Catch:{ all -> 0x0e38 }
                r64 = 1024(0x400, float:1.435E-42)
                r0 = r64
                char[] r12 = new char[r0]     // Catch:{ all -> 0x0b70 }
                r13 = 0
            L_0x0b04:
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0b70 }
                r64 = r0
                r0 = r64
                java.util.concurrent.atomic.AtomicBoolean r0 = r0.isRequestCancelled     // Catch:{ all -> 0x0b70 }
                r64 = r0
                boolean r64 = r64.get()     // Catch:{ all -> 0x0b70 }
                if (r64 != 0) goto L_0x0be8
                r0 = r37
                int r13 = r0.read(r12)     // Catch:{ all -> 0x0b70 }
                if (r13 <= 0) goto L_0x0be8
                r64 = 0
                r0 = r50
                r1 = r64
                r0.write(r12, r1, r13)     // Catch:{ all -> 0x0b70 }
                long r0 = (long) r13     // Catch:{ all -> 0x0b70 }
                r64 = r0
                long r16 = r16 + r64
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0b70 }
                r64 = r0
                r0 = r64
                network.NetworkRequest$ProgressDirection r0 = r0.progressDirection     // Catch:{ all -> 0x0b70 }
                r64 = r0
                network.NetworkRequest$ProgressDirection r65 = network.NetworkRequest.ProgressDirection.DOWNLOAD     // Catch:{ all -> 0x0b70 }
                r0 = r64
                r1 = r65
                if (r0 != r1) goto L_0x0b04
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0b70 }
                r64 = r0
                r0 = r16
                r2 = r64
                r2.bytesTransferred = r0     // Catch:{ all -> 0x0b70 }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0b70 }
                r64 = r0
                r0 = r64
                network.NetworkRequest$LuaCallback r0 = r0.callback     // Catch:{ all -> 0x0b70 }
                r64 = r0
                if (r64 == 0) goto L_0x0b04
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0b70 }
                r64 = r0
                r0 = r64
                network.NetworkRequest$LuaCallback r0 = r0.callback     // Catch:{ all -> 0x0b70 }
                r64 = r0
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0b70 }
                r65 = r0
                r64.call(r65)     // Catch:{ all -> 0x0b70 }
                goto L_0x0b04
            L_0x0b70:
                r64 = move-exception
                r49 = r50
                r36 = r37
            L_0x0b75:
                if (r36 == 0) goto L_0x0b7a
                r36.close()     // Catch:{ all -> 0x017e }
            L_0x0b7a:
                if (r49 == 0) goto L_0x0b7f
                r49.close()     // Catch:{ all -> 0x017e }
            L_0x0b7f:
                throw r64     // Catch:{ all -> 0x017e }
            L_0x0b80:
                r39.close()     // Catch:{ all -> 0x017e }
                r38 = 0
                goto L_0x0926
            L_0x0b87:
                java.io.InputStream r38 = r60.getErrorStream()     // Catch:{ all -> 0x017e }
                if (r38 == 0) goto L_0x0e4c
                r64 = 1
                java.lang.Boolean r40 = java.lang.Boolean.valueOf(r64)     // Catch:{ all -> 0x017e }
                r39 = r38
                goto L_0x0928
            L_0x0b97:
                r33 = move-exception
                java.lang.StringBuilder r64 = new java.lang.StringBuilder     // Catch:{ all -> 0x017e }
                r64.<init>()     // Catch:{ all -> 0x017e }
                java.lang.String r65 = "The character encoding found in the Content-Type header was not supported, was: "
                java.lang.StringBuilder r64 = r64.append(r65)     // Catch:{ all -> 0x017e }
                r0 = r64
                java.lang.StringBuilder r64 = r0.append(r15)     // Catch:{ all -> 0x017e }
                java.lang.String r64 = r64.toString()     // Catch:{ all -> 0x017e }
                r65 = 0
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
                r15 = 0
                goto L_0x09ea
            L_0x0bbb:
                r33 = move-exception
                java.lang.String r15 = "UTF-8"
                java.lang.StringBuilder r64 = new java.lang.StringBuilder     // Catch:{ all -> 0x017e }
                r64.<init>()     // Catch:{ all -> 0x017e }
                java.lang.String r65 = "The character encoding found in the content itself was not supported, was: "
                java.lang.StringBuilder r64 = r64.append(r65)     // Catch:{ all -> 0x017e }
                r0 = r64
                r1 = r24
                java.lang.StringBuilder r64 = r0.append(r1)     // Catch:{ all -> 0x017e }
                java.lang.String r65 = ", content will be decoded using UTF-8"
                java.lang.StringBuilder r64 = r64.append(r65)     // Catch:{ all -> 0x017e }
                java.lang.String r64 = r64.toString()     // Catch:{ all -> 0x017e }
                r65 = 0
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
                goto L_0x0a61
            L_0x0be8:
                r0 = r50
                boolean r0 = r0 instanceof java.io.StringWriter     // Catch:{ all -> 0x0b70 }
                r64 = r0
                if (r64 == 0) goto L_0x0c06
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0b70 }
                r65 = r0
                r0 = r50
                java.io.StringWriter r0 = (java.io.StringWriter) r0     // Catch:{ all -> 0x0b70 }
                r64 = r0
                java.lang.String r64 = r64.toString()     // Catch:{ all -> 0x0b70 }
                r0 = r64
                r1 = r65
                r1.response = r0     // Catch:{ all -> 0x0b70 }
            L_0x0c06:
                if (r37 == 0) goto L_0x0c0b
                r37.close()     // Catch:{ all -> 0x017e }
            L_0x0c0b:
                if (r50 == 0) goto L_0x0c10
                r50.close()     // Catch:{ all -> 0x017e }
            L_0x0c10:
                if (r60 == 0) goto L_0x022a
                r60.disconnect()     // Catch:{ Exception -> 0x0185 }
                goto L_0x022a
            L_0x0c17:
                java.lang.String r64 = "Response content was binary"
                r65 = 0
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x017e }
                r64 = r0
                java.lang.String r65 = "binary"
                r0 = r65
                r1 = r64
                r1.responseType = r0     // Catch:{ all -> 0x017e }
                r36 = 0
                r49 = 0
                r58 = 0
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                network.NetworkRequest$CoronaFileSpec r0 = r0.response     // Catch:{ all -> 0x017e }
                r64 = r0
                if (r64 == 0) goto L_0x0c4c
                boolean r64 = r40.booleanValue()     // Catch:{ all -> 0x017e }
                if (r64 == 0) goto L_0x0cdc
            L_0x0c4c:
                java.io.ByteArrayOutputStream r49 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x017e }
                r49.<init>()     // Catch:{ all -> 0x017e }
            L_0x0c51:
                java.io.BufferedInputStream r37 = new java.io.BufferedInputStream     // Catch:{ all -> 0x0e32 }
                r37.<init>(r38)     // Catch:{ all -> 0x0e32 }
                r64 = 1024(0x400, float:1.435E-42)
                r0 = r64
                byte[] r12 = new byte[r0]     // Catch:{ all -> 0x0cc9 }
                r13 = 0
            L_0x0c5d:
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                r0 = r64
                java.util.concurrent.atomic.AtomicBoolean r0 = r0.isRequestCancelled     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                boolean r64 = r64.get()     // Catch:{ all -> 0x0cc9 }
                if (r64 != 0) goto L_0x0d4e
                r0 = r37
                int r13 = r0.read(r12)     // Catch:{ all -> 0x0cc9 }
                if (r13 <= 0) goto L_0x0d4e
                r64 = 0
                r0 = r49
                r1 = r64
                r0.write(r12, r1, r13)     // Catch:{ all -> 0x0cc9 }
                long r0 = (long) r13     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                long r16 = r16 + r64
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                r0 = r64
                network.NetworkRequest$ProgressDirection r0 = r0.progressDirection     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                network.NetworkRequest$ProgressDirection r65 = network.NetworkRequest.ProgressDirection.DOWNLOAD     // Catch:{ all -> 0x0cc9 }
                r0 = r64
                r1 = r65
                if (r0 != r1) goto L_0x0c5d
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                r0 = r16
                r2 = r64
                r2.bytesTransferred = r0     // Catch:{ all -> 0x0cc9 }
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                r0 = r64
                network.NetworkRequest$LuaCallback r0 = r0.callback     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                if (r64 == 0) goto L_0x0c5d
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                r0 = r64
                network.NetworkRequest$LuaCallback r0 = r0.callback     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0cc9 }
                r65 = r0
                r64.call(r65)     // Catch:{ all -> 0x0cc9 }
                goto L_0x0c5d
            L_0x0cc9:
                r64 = move-exception
                r36 = r37
            L_0x0ccc:
                if (r36 == 0) goto L_0x0cd1
                r36.close()     // Catch:{ all -> 0x017e }
            L_0x0cd1:
                if (r49 == 0) goto L_0x0cd6
                r49.close()     // Catch:{ all -> 0x017e }
            L_0x0cd6:
                if (r58 == 0) goto L_0x0cdb
                r58.delete()     // Catch:{ all -> 0x017e }
            L_0x0cdb:
                throw r64     // Catch:{ all -> 0x017e }
            L_0x0cdc:
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x017e }
                r64 = r0
                r0 = r64
                network.NetworkRequest$CoronaFileSpec r0 = r0.response     // Catch:{ all -> 0x017e }
                r35 = r0
                r32 = 0
                r0 = r35
                java.lang.String r0 = r0.fullPath     // Catch:{ all -> 0x017e }
                r64 = r0
                if (r64 == 0) goto L_0x0d20
                r0 = r35
                java.lang.String r0 = r0.fullPath     // Catch:{ all -> 0x017e }
                r64 = r0
                java.lang.String r65 = java.io.File.separator     // Catch:{ all -> 0x017e }
                int r42 = r64.lastIndexOf(r65)     // Catch:{ all -> 0x017e }
                if (r42 <= 0) goto L_0x0d20
                r0 = r35
                java.lang.String r0 = r0.fullPath     // Catch:{ all -> 0x017e }
                r64 = r0
                r65 = 0
                r0 = r64
                r1 = r65
                r2 = r42
                java.lang.String r31 = r0.substring(r1, r2)     // Catch:{ all -> 0x017e }
                if (r31 == 0) goto L_0x0d20
                java.io.File r32 = new java.io.File     // Catch:{ all -> 0x017e }
                r0 = r32
                r1 = r31
                r0.<init>(r1)     // Catch:{ all -> 0x017e }
                r32.mkdirs()     // Catch:{ all -> 0x017e }
            L_0x0d20:
                java.lang.String r64 = "download"
                java.lang.String r65 = ".tmp"
                r0 = r64
                r1 = r65
                r2 = r32
                java.io.File r58 = java.io.File.createTempFile(r0, r1, r2)     // Catch:{ all -> 0x017e }
                java.lang.String r64 = "Temp file path: %s"
                r65 = 1
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x017e }
                r65 = r0
                r66 = 0
                java.lang.String r67 = r58.getPath()     // Catch:{ all -> 0x017e }
                r65[r66] = r67     // Catch:{ all -> 0x017e }
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x017e }
                java.io.FileOutputStream r49 = new java.io.FileOutputStream     // Catch:{ all -> 0x017e }
                r0 = r49
                r1 = r58
                r0.<init>(r1)     // Catch:{ all -> 0x017e }
                goto L_0x0c51
            L_0x0d4e:
                r0 = r49
                boolean r0 = r0 instanceof java.io.ByteArrayOutputStream     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                if (r64 == 0) goto L_0x0d82
                r49.close()     // Catch:{ all -> 0x0cc9 }
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0cc9 }
                r65 = r0
                r0 = r49
                java.io.ByteArrayOutputStream r0 = (java.io.ByteArrayOutputStream) r0     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                byte[] r64 = r64.toByteArray()     // Catch:{ all -> 0x0cc9 }
                r0 = r64
                r1 = r65
                r1.response = r0     // Catch:{ all -> 0x0cc9 }
                r49 = 0
            L_0x0d71:
                if (r37 == 0) goto L_0x0d76
                r37.close()     // Catch:{ all -> 0x017e }
            L_0x0d76:
                if (r49 == 0) goto L_0x0d7b
                r49.close()     // Catch:{ all -> 0x017e }
            L_0x0d7b:
                if (r58 == 0) goto L_0x0c10
                r58.delete()     // Catch:{ all -> 0x017e }
                goto L_0x0c10
            L_0x0d82:
                r0 = r49
                boolean r0 = r0 instanceof java.io.FileOutputStream     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                if (r64 == 0) goto L_0x0d71
                r49.close()     // Catch:{ all -> 0x0cc9 }
                r49 = 0
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                r0 = r64
                java.util.concurrent.atomic.AtomicBoolean r0 = r0.isRequestCancelled     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                boolean r64 = r64.get()     // Catch:{ all -> 0x0cc9 }
                if (r64 != 0) goto L_0x0e18
                r0 = r70
                network.NetworkRequest$NetworkRequestParameters r0 = r0.requestParameters     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                r0 = r64
                network.NetworkRequest$CoronaFileSpec r0 = r0.response     // Catch:{ all -> 0x0cc9 }
                r35 = r0
                if (r58 == 0) goto L_0x0dea
                java.io.File r30 = new java.io.File     // Catch:{ all -> 0x0cc9 }
                r0 = r35
                java.lang.String r0 = r0.fullPath     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                r0 = r30
                r1 = r64
                r0.<init>(r1)     // Catch:{ all -> 0x0cc9 }
                boolean r64 = r30.exists()     // Catch:{ all -> 0x0cc9 }
                if (r64 == 0) goto L_0x0dc7
                r30.delete()     // Catch:{ all -> 0x0cc9 }
            L_0x0dc7:
                r0 = r58
                r1 = r30
                boolean r7 = r0.renameTo(r1)     // Catch:{ all -> 0x0cc9 }
                if (r7 == 0) goto L_0x0ded
                java.lang.String r64 = "Temp file successfully renamed"
                r65 = 0
                r0 = r65
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0cc9 }
                r65 = r0
                network.NetworkRequest.debug(r64, r65)     // Catch:{ all -> 0x0cc9 }
                r0 = r70
                network.NetworkRequest$NetworkRequestState r0 = r0.requestState     // Catch:{ all -> 0x0cc9 }
                r64 = r0
                r0 = r35
                r1 = r64
                r1.response = r0     // Catch:{ all -> 0x0cc9 }
            L_0x0dea:
                r58 = 0
                goto L_0x0d71
            L_0x0ded:
                java.lang.Exception r64 = new java.lang.Exception     // Catch:{ all -> 0x0cc9 }
                java.lang.StringBuilder r65 = new java.lang.StringBuilder     // Catch:{ all -> 0x0cc9 }
                r65.<init>()     // Catch:{ all -> 0x0cc9 }
                java.lang.String r66 = "Failed to renamed temporary download file at path "
                java.lang.StringBuilder r65 = r65.append(r66)     // Catch:{ all -> 0x0cc9 }
                java.lang.String r66 = r58.getPath()     // Catch:{ all -> 0x0cc9 }
                java.lang.StringBuilder r65 = r65.append(r66)     // Catch:{ all -> 0x0cc9 }
                java.lang.String r66 = " to final file at path "
                java.lang.StringBuilder r65 = r65.append(r66)     // Catch:{ all -> 0x0cc9 }
                java.lang.String r66 = r30.getPath()     // Catch:{ all -> 0x0cc9 }
                java.lang.StringBuilder r65 = r65.append(r66)     // Catch:{ all -> 0x0cc9 }
                java.lang.String r65 = r65.toString()     // Catch:{ all -> 0x0cc9 }
                r64.<init>(r65)     // Catch:{ all -> 0x0cc9 }
                throw r64     // Catch:{ all -> 0x0cc9 }
            L_0x0e18:
                if (r58 == 0) goto L_0x0dea
                r58.delete()     // Catch:{ all -> 0x0cc9 }
                goto L_0x0dea
            L_0x0e1e:
                java.lang.Throwable r64 = r33.getCause()
                if (r64 == 0) goto L_0x0196
                java.lang.Throwable r64 = r33.getCause()
                java.lang.String r45 = r64.toString()
                goto L_0x0196
            L_0x0e2e:
                java.lang.String r45 = "Malformed URL"
                goto L_0x01d6
            L_0x0e32:
                r64 = move-exception
                goto L_0x0ccc
            L_0x0e35:
                r64 = move-exception
                goto L_0x0b75
            L_0x0e38:
                r64 = move-exception
                r36 = r37
                goto L_0x0b75
            L_0x0e3d:
                r64 = move-exception
                goto L_0x056c
            L_0x0e40:
                r38 = r39
                goto L_0x0aa1
            L_0x0e44:
                r38 = r39
                goto L_0x0aab
            L_0x0e48:
                r38 = r39
                goto L_0x0c10
            L_0x0e4c:
                r39 = r38
                goto L_0x0928
            L_0x0e50:
                r37 = r36
                goto L_0x044a
            */
            throw new UnsupportedOperationException("Method not decompiled: network.NetworkRequest.AsyncNetworkRequestRunnable.run():void");
        }
    }

    private class CoronaFileSpec {
        public Object baseDirectory = null;
        public String filename = null;
        public String fullPath = null;
        public Boolean isResourceFile = false;

        public CoronaFileSpec(String str, Object obj, String str2, Boolean bool) {
            this.filename = str;
            this.baseDirectory = obj;
            this.fullPath = str2;
            this.isResourceFile = bool;
        }

        public long getFileSize() throws IOException {
            if (!this.isResourceFile.booleanValue()) {
                return new File(this.fullPath).length();
            }
            InputStream inputStream = getInputStream();
            long available = (long) inputStream.available();
            inputStream.close();
            return available;
        }

        public InputStream getInputStream() throws IOException {
            return !this.isResourceFile.booleanValue() ? new FileInputStream(this.fullPath) : CoronaEnvironment.getApplicationContext().getResources().getAssets().open(this.filename);
        }
    }

    private class LuaCallback {
        private String lastNotificationPhase = null;
        private long lastNotificationTime = 0;
        /* access modifiers changed from: private */
        public int luaFunctionReferenceKey = -1;
        private final long minNotificationIntervalMs = 1000;
        private CoronaRuntimeTaskDispatcher taskDispatcher = null;

        public LuaCallback(int i, CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher) {
            this.luaFunctionReferenceKey = i;
            this.taskDispatcher = coronaRuntimeTaskDispatcher;
        }

        public boolean call(NetworkRequestState networkRequestState) {
            return call(networkRequestState, false);
        }

        public boolean call(NetworkRequestState networkRequestState, boolean z) {
            synchronized (this) {
                if (this.luaFunctionReferenceKey == -1) {
                    NetworkRequest.debug("Attempt to post call to callback after it was unregistered, ignoring.", new Object[0]);
                    return false;
                } else if (this.taskDispatcher == null) {
                    NetworkRequest.debug("Attempt to post call to callback without a CoronaRuntimeTaskDispatcher.", new Object[0]);
                    return false;
                } else {
                    final boolean z2 = z;
                    if (networkRequestState.isRequestCancelled.get()) {
                        NetworkRequest.debug("Attempt to post call to callback after cancelling, ignoring.", new Object[0]);
                        return false;
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    if (networkRequestState.phase.equals(this.lastNotificationPhase)) {
                        long j = this.lastNotificationTime;
                        getClass();
                        if (j + 1000 > currentTimeMillis) {
                            NetworkRequest.debug("Attempt to post call to callback for phase \"%s\" within notification interval, ignoring.", networkRequestState.phase);
                            return false;
                        }
                    }
                    this.lastNotificationPhase = networkRequestState.phase;
                    this.lastNotificationTime = currentTimeMillis;
                    final NetworkRequestState networkRequestState2 = new NetworkRequestState(networkRequestState);
                    AnonymousClass1 r4 = new CoronaRuntimeTask() {
                        public void executeUsing(CoronaRuntime coronaRuntime) {
                            int access$100 = this.luaFunctionReferenceKey;
                            if (access$100 == -1) {
                                NetworkRequest.debug("Attempt to post call to callback after it was unregistered, ignoring (Corona thread).", new Object[0]);
                            } else if (networkRequestState2.isRequestCancelled.get()) {
                                NetworkRequest.debug("Attempt to call to callback posted before cancelling, after cancelling, ignoring.", new Object[0]);
                            } else {
                                NetworkRequest.debug("Executing callback - thread: %d", Long.valueOf(Thread.currentThread().getId()));
                                NetworkRequest.debug("Executing callback - runtime luaState: %s", Integer.toHexString(System.identityHashCode(coronaRuntime.getLuaState())));
                                try {
                                    NetworkRequest.debug("Calling Lua callback", new Object[0]);
                                    LuaState luaState = coronaRuntime.getLuaState();
                                    CoronaLua.newEvent(luaState, NetworkRequest.EVENT_NAME);
                                    networkRequestState2.push(luaState);
                                    CoronaLua.dispatchEvent(luaState, access$100, 0);
                                    if (z2) {
                                        NetworkRequest.debug("Unregistering callback after call", new Object[0]);
                                        CoronaLua.deleteRef(luaState, access$100);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                    NetworkRequest.debug("Posting callback CoronaRuntimeTask to Corona thread", new Object[0]);
                    this.taskDispatcher.send(r4);
                    return true;
                }
            }
        }

        public boolean unregister(CoronaRuntime coronaRuntime) {
            synchronized (this) {
                if (this.luaFunctionReferenceKey == -1) {
                    NetworkRequest.debug("Attempt to unregister callback after it was already unregistered, ignoring.", new Object[0]);
                    return false;
                }
                try {
                    NetworkRequest.debug("Unregistering Lua callback using runtime", new Object[0]);
                    CoronaLua.deleteRef(coronaRuntime.getLuaState(), this.luaFunctionReferenceKey);
                    this.luaFunctionReferenceKey = -1;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
    }

    private class NetworkRequestParameters {
        public Boolean bBodyIsText;
        public Boolean bHandleRedirects;
        public LuaCallback callback;
        public Boolean isDebug;
        public String method;
        public ProgressDirection progressDirection;
        public Object requestBody;
        public Map<String, String> requestHeaders;
        public URL requestURL;
        public CoronaFileSpec response;
        public StatusBarNotificationSettings successNotificationSettings;
        public int timeout;

        private NetworkRequestParameters() {
            this.requestURL = null;
            this.method = null;
            this.requestHeaders = null;
            this.requestBody = null;
            this.progressDirection = ProgressDirection.NONE;
            this.bBodyIsText = true;
            this.response = null;
            this.timeout = 30;
            this.isDebug = false;
            this.callback = null;
            this.successNotificationSettings = null;
            this.bHandleRedirects = true;
        }

        public boolean extractRequestParameters(LuaState luaState) {
            Context applicationContext;
            Boolean bool = false;
            if (LuaType.STRING == luaState.type(1)) {
                try {
                    this.requestURL = new URL(luaState.toString(1));
                } catch (MalformedURLException e) {
                }
            } else {
                NetworkRequest.paramValidationFailure(luaState, "First argument to network.request() should be a URL string.", new Object[0]);
                bool = true;
            }
            int i = 1 + 1;
            if (!bool.booleanValue()) {
                if (LuaType.STRING == luaState.type(i)) {
                    this.method = luaState.toString(i);
                    if (!this.method.matches("\\b(GET)|(PUT)|(POST)|(HEAD)|(DELETE)\\b")) {
                        NetworkRequest.paramValidationFailure(luaState, "Method argument was invalid, must be one of GET, PUT, POST, HEAD, or DELETE, but was: %s.", this.method);
                        bool = true;
                    }
                    i++;
                } else {
                    this.method = "GET";
                }
            }
            if (!bool.booleanValue() && CoronaLua.isListener(luaState, i, NetworkRequest.EVENT_NAME)) {
                CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher = new CoronaRuntimeTaskDispatcher(luaState);
                if (coronaRuntimeTaskDispatcher.isRuntimeUnavailable()) {
                    coronaRuntimeTaskDispatcher = NetworkRequest.this.fLoader.getRuntimeTaskDispatcher();
                }
                if (coronaRuntimeTaskDispatcher != null) {
                    this.callback = new LuaCallback(CoronaLua.newRef(luaState, i), coronaRuntimeTaskDispatcher);
                }
                i++;
            }
            int i2 = i;
            if (!bool.booleanValue() && !luaState.isNoneOrNil(i)) {
                if (LuaType.TABLE == luaState.type(i)) {
                    Boolean bool2 = false;
                    luaState.getField(i2, "headers");
                    if (!luaState.isNil(-1)) {
                        if (LuaType.TABLE == luaState.type(-1)) {
                            luaState.pushNil();
                            while (luaState.next(-2)) {
                                String luaState2 = luaState.toString(-2);
                                if (luaState2 != null && !"Content-Length".equalsIgnoreCase(luaState2)) {
                                    String str = null;
                                    switch (luaState.type(-1)) {
                                        case STRING:
                                            str = luaState.toString(-1);
                                            break;
                                        case NUMBER:
                                            double number = luaState.toNumber(-1);
                                            if (Math.floor(number) != number) {
                                                str = Double.toString(number);
                                                break;
                                            } else {
                                                str = Long.toString(Math.round(number));
                                                break;
                                            }
                                        case BOOLEAN:
                                            str = Boolean.toString(luaState.toBoolean(-1));
                                            break;
                                    }
                                    if (str != null) {
                                        if (this.requestHeaders == null) {
                                            this.requestHeaders = new HashMap();
                                        }
                                        if ("Content-Type".equalsIgnoreCase(luaState2)) {
                                            bool2 = true;
                                            String access$300 = NetworkRequest.getContentTypeEncoding(str);
                                            if (access$300 != null) {
                                                try {
                                                    Charset.forName(access$300);
                                                } catch (Exception e2) {
                                                    NetworkRequest.paramValidationFailure(luaState, "'header' value for Content-Type header contained an unsupported character encoding: %s", access$300);
                                                    bool = true;
                                                }
                                            }
                                        }
                                        this.requestHeaders.put(luaState2, str);
                                        NetworkRequest.debug("Header - %s: %s", luaState2, str);
                                    }
                                }
                                luaState.pop(1);
                            }
                        } else {
                            NetworkRequest.paramValidationFailure(luaState, "'headers' value of params table, if provided, should be a table.", new Object[0]);
                            bool = true;
                        }
                    }
                    luaState.pop(1);
                    if (this.requestHeaders == null && this.method.equals("POST") && !bool2.booleanValue()) {
                        this.requestHeaders = new HashMap();
                        this.requestHeaders.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                        bool2 = true;
                    }
                    this.bHandleRedirects = true;
                    luaState.getField(i2, "handleRedirects");
                    if (!luaState.isNil(-1)) {
                        if (LuaType.BOOLEAN == luaState.type(-1)) {
                            this.bHandleRedirects = Boolean.valueOf(luaState.toBoolean(-1));
                            Object[] objArr = new Object[1];
                            objArr[0] = this.bHandleRedirects.booleanValue() ? "true" : "false";
                            NetworkRequest.debug("Redirect option provided, was: %s", objArr);
                        } else {
                            NetworkRequest.paramValidationFailure(luaState, "'handleRedirects' value of params table, if provided, should be a boolean value.", new Object[0]);
                            bool = true;
                        }
                    }
                    luaState.pop(1);
                    this.bBodyIsText = true;
                    luaState.getField(i2, "bodyType");
                    if (!luaState.isNil(-1)) {
                        if (LuaType.STRING == luaState.type(-1)) {
                            String luaState3 = luaState.toString(-1);
                            if (luaState3.matches("\\b(text)|(binary)\\b")) {
                                this.bBodyIsText = Boolean.valueOf(luaState3.matches("\\b(text)\\b"));
                                NetworkRequest.debug("Request body is text: %b", this.bBodyIsText);
                            } else {
                                NetworkRequest.paramValidationFailure(luaState, "'bodyType' value of params table was invalid, must be either \"text\" or \"binary\", but was: \"%s\"", luaState3);
                                bool = true;
                            }
                        } else {
                            NetworkRequest.paramValidationFailure(luaState, "'bodyType' value of params table, if provided, should be a string value.", new Object[0]);
                            bool = true;
                        }
                    }
                    luaState.pop(1);
                    luaState.getField(i2, "body");
                    if (!luaState.isNil(-1)) {
                        switch (luaState.type(-1)) {
                            case STRING:
                                if (!this.bBodyIsText.booleanValue()) {
                                    NetworkRequest.debug("Request body from String (binary)", new Object[0]);
                                    this.requestBody = luaState.toByteArray(-1);
                                    if (!bool2.booleanValue()) {
                                        if (this.requestHeaders == null) {
                                            this.requestHeaders = new HashMap();
                                        }
                                        this.requestHeaders.put("Content-Type", "application/octet-stream");
                                        bool2 = true;
                                        break;
                                    }
                                } else {
                                    NetworkRequest.debug("Request body from String (text)", new Object[0]);
                                    this.requestBody = luaState.toString(-1);
                                    if (!bool2.booleanValue()) {
                                        if (this.requestHeaders == null) {
                                            this.requestHeaders = new HashMap();
                                        }
                                        this.requestHeaders.put("Content-Type", "text/plain; charset=UTF-8");
                                        bool2 = true;
                                        break;
                                    }
                                }
                                break;
                            case TABLE:
                                this.bBodyIsText = false;
                                luaState.getField(-1, "filename");
                                if (LuaType.STRING != luaState.type(-1)) {
                                    NetworkRequest.paramValidationFailure(luaState, "body 'filename' value is required and must be a string value.", new Object[0]);
                                    bool = true;
                                    break;
                                } else {
                                    int i3 = 1;
                                    Object obj = null;
                                    String luaState4 = luaState.toString(-1);
                                    luaState.pop(1);
                                    luaState.getField(-1, "baseDirectory");
                                    if (!luaState.isNoneOrNil(-1)) {
                                        obj = luaState.toJavaObject(-1, Object.class);
                                    }
                                    luaState.pop(1);
                                    luaState.getGlobal("_network_pathForFile");
                                    luaState.pushString(luaState4);
                                    if (obj != null) {
                                        luaState.pushJavaObject(obj);
                                        i3 = 1 + 1;
                                    }
                                    luaState.call(i3, 2);
                                    Boolean valueOf = Boolean.valueOf(luaState.toBoolean(-1));
                                    String luaState5 = luaState.toString(-2);
                                    luaState.pop(2);
                                    NetworkRequest.debug("body filename: %s, baseDirectory: ", luaState4, obj);
                                    NetworkRequest.debug("pathForFile from LUA: %s - isResourceFile: %b", luaState5, valueOf);
                                    this.requestBody = new CoronaFileSpec(luaState4, obj, luaState5, valueOf);
                                    break;
                                }
                            default:
                                NetworkRequest.paramValidationFailure(luaState, "Either body string or table specifying body file is required if 'body' is specified", new Object[0]);
                                bool = true;
                                break;
                        }
                        if (this.requestBody != null && !bool2.booleanValue()) {
                            NetworkRequest.paramValidationFailure(luaState, "Request Content-Type header is required when request 'body' is specified", new Object[0]);
                            bool = true;
                        }
                    }
                    luaState.pop(1);
                    luaState.getField(i2, "progress");
                    if (!luaState.isNil(-1)) {
                        if (LuaType.STRING == luaState.type(-1)) {
                            String luaState6 = luaState.toString(-1);
                            this.progressDirection = ProgressDirection.fromString(luaState6);
                            NetworkRequest.debug("Progress was: %s", ProgressDirection.toString(this.progressDirection));
                            if (this.progressDirection == null) {
                                NetworkRequest.paramValidationFailure(luaState, "'progress' value of params table was invalid, if provided, must be either \"upload\" or \"download\", but was: \"%s\"", luaState6);
                                bool = true;
                            }
                        } else {
                            NetworkRequest.paramValidationFailure(luaState, "'progress' value of params table, if provided, should be a string value.", new Object[0]);
                            bool = true;
                        }
                    }
                    luaState.pop(1);
                    luaState.getField(i2, CoronaLuaEvent.RESPONSE_KEY);
                    if (!luaState.isNil(-1)) {
                        if (LuaType.TABLE == luaState.type(-1)) {
                            luaState.getField(-1, "filename");
                            if (LuaType.STRING == luaState.type(-1)) {
                                int i4 = 1;
                                Object obj2 = null;
                                String luaState7 = luaState.toString(-1);
                                luaState.pop(1);
                                luaState.getField(-1, "baseDirectory");
                                if (!luaState.isNoneOrNil(-1)) {
                                    obj2 = luaState.toJavaObject(-1, Object.class);
                                }
                                luaState.pop(1);
                                luaState.getGlobal("_network_pathForFile");
                                luaState.pushString(luaState7);
                                if (obj2 != null) {
                                    luaState.pushJavaObject(obj2);
                                    i4 = 1 + 1;
                                }
                                luaState.call(i4, 2);
                                Boolean valueOf2 = Boolean.valueOf(luaState.toBoolean(-1));
                                String luaState8 = luaState.toString(-2);
                                luaState.pop(2);
                                NetworkRequest.debug("response filename: %s, baseDirectory: %s", luaState7, obj2);
                                NetworkRequest.debug("pathForFile from LUA: %s - isResourceFile: %b", luaState8, valueOf2);
                                boolean z = false;
                                try {
                                    File externalStorageDirectory = Environment.getExternalStorageDirectory();
                                    if (!(externalStorageDirectory == null || luaState8 == null)) {
                                        z = luaState8.startsWith(externalStorageDirectory.getAbsolutePath());
                                    }
                                } catch (Exception e3) {
                                }
                                if (z && (applicationContext = CoronaEnvironment.getApplicationContext()) != null) {
                                    applicationContext.enforceCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE", (String) null);
                                }
                                this.response = new CoronaFileSpec(luaState7, obj2, luaState8, valueOf2);
                            } else {
                                NetworkRequest.paramValidationFailure(luaState, "response 'filename' value is required and must be a string value.", new Object[0]);
                                bool = true;
                            }
                        } else {
                            NetworkRequest.paramValidationFailure(luaState, "'response' value of params table, if provided, should be a table specifying response location values.", new Object[0]);
                            bool = true;
                        }
                    }
                    luaState.pop(1);
                    luaState.getField(i2, "timeout");
                    if (!luaState.isNil(-1)) {
                        if (LuaType.NUMBER == luaState.type(-1)) {
                            this.timeout = luaState.toInteger(-1);
                            NetworkRequest.debug("Request timeout provided, was: %d", Integer.valueOf(this.timeout));
                        } else {
                            NetworkRequest.paramValidationFailure(luaState, "'timeout' value of params table, if provided, should be a numeric value.", new Object[0]);
                            bool = true;
                        }
                    }
                    luaState.pop(1);
                    luaState.getField(i2, "debug");
                    if (!luaState.isNil(-1) && LuaType.BOOLEAN == luaState.type(-1)) {
                        this.isDebug = Boolean.valueOf(luaState.toBoolean(-1));
                        NetworkRequest.debug("debug setting provided, was: %b", this.isDebug);
                    }
                    luaState.pop(1);
                    luaState.getField(i2, "successNotification");
                    if (luaState.isTable(-1)) {
                        this.successNotificationSettings = StatusBarNotificationSettings.from(CoronaEnvironment.getApplicationContext(), luaState, -1);
                    }
                    luaState.pop(1);
                } else {
                    NetworkRequest.paramValidationFailure(luaState, "Fourth argument to network.request(), if provided, should be a params table.", new Object[0]);
                    bool = true;
                }
            }
            return !bool.booleanValue();
        }
    }

    private class NetworkRequestState {
        public long bytesEstimated = 0;
        public long bytesTransferred = 0;
        public Map<String, String> debugValues = null;
        public Boolean isError = false;
        public AtomicBoolean isRequestCancelled = null;
        public String name = NetworkRequest.EVENT_NAME;
        public String phase = "began";
        public Object response = null;
        public Map<String, List<String>> responseHeaders = null;
        public String responseType = "text";
        public int status = -1;
        public String url = null;

        public NetworkRequestState(String str, Boolean bool) {
            this.url = str;
            this.isRequestCancelled = new AtomicBoolean(false);
            if (bool.booleanValue()) {
                this.debugValues = new HashMap();
                this.debugValues.put("isDebug", "true");
            }
        }

        public NetworkRequestState(NetworkRequestState networkRequestState) {
            this.isError = networkRequestState.isError;
            this.name = networkRequestState.name;
            this.phase = networkRequestState.phase;
            this.status = networkRequestState.status;
            this.url = networkRequestState.url;
            this.responseHeaders = networkRequestState.responseHeaders;
            this.responseType = networkRequestState.responseType;
            this.response = networkRequestState.response;
            this.isRequestCancelled = networkRequestState.isRequestCancelled;
            this.bytesTransferred = networkRequestState.bytesTransferred;
            this.bytesEstimated = networkRequestState.bytesEstimated;
            this.debugValues = networkRequestState.debugValues;
        }

        public int push(LuaState luaState) {
            int top = luaState.getTop();
            luaState.pushBoolean(this.isError.booleanValue());
            luaState.setField(top, CoronaLuaEvent.ISERROR_KEY);
            luaState.pushString(this.name);
            luaState.setField(top, "name");
            luaState.pushString(this.phase);
            luaState.setField(top, "phase");
            luaState.pushJavaObjectRaw(this.isRequestCancelled);
            luaState.setField(top, "requestId");
            luaState.pushNumber((double) this.status);
            luaState.setField(top, "status");
            luaState.pushString(this.url);
            luaState.setField(top, "url");
            if (this.responseHeaders != null) {
                luaState.newTable(0, this.responseHeaders.size());
                int top2 = luaState.getTop();
                for (Map.Entry next : this.responseHeaders.entrySet()) {
                    String str = (String) next.getKey();
                    if (str == null) {
                        str = "HTTP-STATUS-LINE";
                    }
                    NetworkRequest.debug("Processing header: %s", str);
                    luaState.pushString(NetworkRequest.concatHeaderValues((List) next.getValue()));
                    luaState.setField(top2, str);
                }
                luaState.setField(top, "responseHeaders");
            }
            if (this.response != null) {
                luaState.pushString(this.responseType);
                luaState.setField(top, "responseType");
                if (this.response instanceof String) {
                    luaState.pushString((String) this.response);
                } else if (this.response instanceof byte[]) {
                    luaState.pushString((byte[]) this.response);
                } else if (this.response instanceof CoronaFileSpec) {
                    CoronaFileSpec coronaFileSpec = (CoronaFileSpec) this.response;
                    luaState.newTable(0, 3);
                    int top3 = luaState.getTop();
                    luaState.pushString(coronaFileSpec.filename);
                    luaState.setField(top3, "filename");
                    luaState.pushJavaObject(coronaFileSpec.baseDirectory);
                    luaState.setField(top3, "baseDirectory");
                    luaState.pushString(coronaFileSpec.fullPath);
                    luaState.setField(top3, "fullPath");
                }
                luaState.setField(top, CoronaLuaEvent.RESPONSE_KEY);
            }
            luaState.pushNumber((double) this.bytesTransferred);
            luaState.setField(top, "bytesTransferred");
            luaState.pushNumber((double) this.bytesEstimated);
            luaState.setField(top, "bytesEstimated");
            if (this.debugValues != null) {
                luaState.newTable(0, this.debugValues.size());
                int top4 = luaState.getTop();
                for (Map.Entry next2 : this.debugValues.entrySet()) {
                    String str2 = (String) next2.getKey();
                    String str3 = (String) next2.getValue();
                    NetworkRequest.debug("Writing debug value - %s: %s", str2, str3);
                    luaState.pushString(str3);
                    luaState.setField(top4, str2);
                }
                luaState.setField(top, "debug");
            }
            return 1;
        }

        public void setDebugValue(String str, String str2) {
            if (this.debugValues != null) {
                this.debugValues.put(str, str2);
            }
        }
    }

    private enum ProgressDirection {
        UPLOAD,
        DOWNLOAD,
        NONE;

        public static ProgressDirection fromString(String str) {
            if (str == null) {
                return NONE;
            }
            if ("upload".equalsIgnoreCase(str)) {
                return UPLOAD;
            }
            if ("download".equalsIgnoreCase(str)) {
                return DOWNLOAD;
            }
            return null;
        }

        public static String toString(ProgressDirection progressDirection) {
            switch (progressDirection) {
                case UPLOAD:
                    return "upload";
                case DOWNLOAD:
                    return "download";
                default:
                    return StoreName.NONE;
            }
        }
    }

    public NetworkRequest(LuaLoader luaLoader) {
        this.fLoader = luaLoader;
        this.fOpenRequests = new CopyOnWriteArrayList<>();
    }

    /* access modifiers changed from: private */
    public static String concatHeaderValues(List<String> list) {
        StringBuilder sb = new StringBuilder();
        String str = BuildConfig.FLAVOR;
        for (String append : list) {
            sb.append(str).append(append);
            str = ",";
        }
        return sb.toString();
    }

    public static void debug(String str, Object... objArr) {
    }

    public static void error(String str, Object... objArr) {
        if (objArr.length > 0) {
            Log.e("Corona", "ERROR: " + String.format(str, objArr));
        } else {
            Log.e("Corona", "ERROR: " + str);
        }
    }

    /* access modifiers changed from: private */
    public static String getContentTypeEncoding(String str) {
        String str2 = null;
        if (str != null) {
            for (String trim : str.split(";")) {
                String trim2 = trim.trim();
                if (trim2.toLowerCase().startsWith("charset=")) {
                    str2 = trim2.substring("charset=".length());
                    debug("Explicit charset was found in content type, was: %s", str2);
                }
            }
        }
        return str2;
    }

    /* access modifiers changed from: private */
    public static String getEncodingFromContent(String str, String str2) {
        String str3 = null;
        debug("Looking for embedded encoding in content: <BODY>%s</BODY>", str2);
        if (isContentTypeXML(str)) {
            Matcher matcher = Pattern.compile("<?xml\\b[^>]*\\bencoding=['\"]([a-zA-Z0-9_\\-]+)['\"].*[^>]*?>", 2).matcher(str2);
            if (matcher.find()) {
                str3 = matcher.group(1);
                debug("Found charset in XML meta header encoding attribute: %s", str3);
            }
        }
        if (str3 != null || !isContentTypeHTML(str)) {
            return str3;
        }
        Matcher matcher2 = Pattern.compile("<meta\\b[^>]*\\bcharset=['\"]([a-zA-Z0-9_\\-]+)['\"][^>]*>", 2).matcher(str2);
        if (matcher2.find()) {
            str3 = matcher2.group(1);
            debug("Found charset in HTML meta charset header: %s", str3);
        }
        if (str3 != null) {
            return str3;
        }
        Matcher matcher3 = Pattern.compile("<meta\\b[^>]*\\bhttp-equiv=['\"](?:Content-Type)['\"][^>]*>", 2).matcher(str2);
        if (!matcher3.find()) {
            return str3;
        }
        String group = matcher3.group();
        debug("Found httpMetaCtHeader: %s", group);
        Matcher matcher4 = Pattern.compile("\\bcharset=([a-zA-Z0-9_\\-]+)\\b", 2).matcher(group);
        if (!matcher4.find()) {
            return str3;
        }
        String group2 = matcher4.group(1);
        debug("Found charset in meta Content-Type header: %s", group2);
        return group2;
    }

    private static boolean isContentTypeHTML(String str) {
        return str.startsWith("text/html") || str.startsWith("application/xhtml");
    }

    /* access modifiers changed from: private */
    public static boolean isContentTypeText(String str) {
        return isContentTypeXML(str) || isContentTypeHTML(str) || str.startsWith("text/") || str.startsWith("application/json") || str.startsWith("application/javascript") || str.startsWith("application/x-javascript") || str.startsWith("application/ecmascript") || str.startsWith("application/x-www-form-urlencoded");
    }

    private static boolean isContentTypeXML(String str) {
        return str.startsWith("text/xml") || str.startsWith("application/xml") || str.startsWith("application/xhtml") || Pattern.compile("^application/(?:\\w+)[+]xml").matcher(str).find();
    }

    public static void paramValidationFailure(LuaState luaState, String str, Object... objArr) {
        System.out.println("Invalid Parameter: " + String.format(str, objArr));
    }

    public void abortOpenConnections(CoronaRuntime coronaRuntime) {
        Iterator<AsyncNetworkRequestRunnable> it = this.fOpenRequests.iterator();
        while (it.hasNext()) {
            debug("Aborting connection", new Object[0]);
            it.next().abort(coronaRuntime);
        }
        this.fOpenRequests.clear();
    }

    public String getName() {
        return ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID;
    }

    public int invoke(LuaState luaState) {
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext != null) {
            applicationContext.enforceCallingOrSelfPermission("android.permission.INTERNET", (String) null);
        }
        debug("network.request() - thread: %d", Long.valueOf(Thread.currentThread().getId()));
        debug("network.request() - luaState %s", Integer.toHexString(System.identityHashCode(luaState)));
        NetworkRequestParameters networkRequestParameters = new NetworkRequestParameters();
        if (!networkRequestParameters.extractRequestParameters(luaState)) {
            return 0;
        }
        AsyncNetworkRequestRunnable asyncNetworkRequestRunnable = new AsyncNetworkRequestRunnable(networkRequestParameters, this.fOpenRequests);
        AtomicBoolean atomicBoolean = asyncNetworkRequestRunnable.requestState.isRequestCancelled;
        new Thread(asyncNetworkRequestRunnable).start();
        luaState.pushJavaObjectRaw(atomicBoolean);
        return 1;
    }
}
