package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.google.android.gms.ads.AdActivity;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class co {
    /* access modifiers changed from: private */
    public static final Object hC = new Object();
    /* access modifiers changed from: private */
    public static boolean iD = true;
    /* access modifiers changed from: private */
    public static String iE;
    private static boolean iF = false;

    private static final class a extends BroadcastReceiver {
        private a() {
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                boolean unused = co.iD = true;
            } else if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                boolean unused2 = co.iD = false;
            }
        }
    }

    public static String a(Readable readable) throws IOException {
        StringBuilder sb = new StringBuilder();
        CharBuffer allocate = CharBuffer.allocate(2048);
        while (true) {
            int read = readable.read(allocate);
            if (read == -1) {
                return sb.toString();
            }
            allocate.flip();
            sb.append(allocate, 0, read);
        }
    }

    private static JSONArray a(Collection<?> collection) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (Object a2 : collection) {
            a(jSONArray, a2);
        }
        return jSONArray;
    }

    private static JSONArray a(Object[] objArr) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (Object a2 : objArr) {
            a(jSONArray, a2);
        }
        return jSONArray;
    }

    private static JSONObject a(Bundle bundle) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            a(jSONObject, str, bundle.get(str));
        }
        return jSONObject;
    }

    public static void a(Context context, String str, WebSettings webSettings) {
        webSettings.setUserAgentString(b(context, str));
    }

    public static void a(Context context, String str, List<String> list) {
        for (String crVar : list) {
            new cr(context, str, crVar).start();
        }
    }

    public static void a(Context context, String str, boolean z, HttpURLConnection httpURLConnection) {
        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setInstanceFollowRedirects(z);
        httpURLConnection.setReadTimeout(60000);
        httpURLConnection.setRequestProperty("User-Agent", b(context, str));
        httpURLConnection.setUseCaches(false);
    }

    public static void a(WebView webView) {
        if (Build.VERSION.SDK_INT >= 11) {
            cp.a(webView);
        }
    }

    private static void a(JSONArray jSONArray, Object obj) throws JSONException {
        if (obj instanceof Bundle) {
            jSONArray.put(a((Bundle) obj));
        } else if (obj instanceof Map) {
            jSONArray.put(m((Map) obj));
        } else if (obj instanceof Collection) {
            jSONArray.put(a((Collection<?>) (Collection) obj));
        } else if (obj instanceof Object[]) {
            jSONArray.put(a((Object[]) obj));
        } else {
            jSONArray.put(obj);
        }
    }

    private static void a(JSONObject jSONObject, String str, Object obj) throws JSONException {
        if (obj instanceof Bundle) {
            jSONObject.put(str, a((Bundle) obj));
        } else if (obj instanceof Map) {
            jSONObject.put(str, m((Map) obj));
        } else if (obj instanceof Collection) {
            if (str == null) {
                str = "null";
            }
            jSONObject.put(str, a((Collection<?>) (Collection) obj));
        } else if (obj instanceof Object[]) {
            jSONObject.put(str, a((Collection<?>) Arrays.asList((Object[]) obj)));
        } else {
            jSONObject.put(str, obj);
        }
    }

    public static boolean a(PackageManager packageManager, String str, String str2) {
        return packageManager.checkPermission(str2, str) == 0;
    }

    public static boolean a(ClassLoader classLoader, Class<?> cls, String str) {
        try {
            return cls.isAssignableFrom(Class.forName(str, false, classLoader));
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean at() {
        return iD;
    }

    public static int au() {
        return Build.VERSION.SDK_INT >= 9 ? 6 : 0;
    }

    public static int av() {
        return Build.VERSION.SDK_INT >= 9 ? 7 : 1;
    }

    private static String b(final Context context, String str) {
        String str2;
        synchronized (hC) {
            if (iE != null) {
                str2 = iE;
            } else {
                if (Build.VERSION.SDK_INT >= 17) {
                    iE = cq.getDefaultUserAgent(context);
                } else if (!cs.ay()) {
                    cs.iI.post(new Runnable() {
                        public void run() {
                            synchronized (co.hC) {
                                String unused = co.iE = co.j(context);
                                co.hC.notifyAll();
                            }
                        }
                    });
                    while (iE == null) {
                        try {
                            hC.wait();
                        } catch (InterruptedException e) {
                            str2 = iE;
                        }
                    }
                } else {
                    iE = j(context);
                }
                iE += " (Mobile; " + str + ")";
                str2 = iE;
            }
        }
        return str2;
    }

    public static void b(WebView webView) {
        if (Build.VERSION.SDK_INT >= 11) {
            cp.b(webView);
        }
    }

    public static boolean h(Context context) {
        boolean z;
        Intent intent = new Intent();
        intent.setClassName(context, AdActivity.CLASS_NAME);
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 65536);
        if (resolveActivity == null || resolveActivity.activityInfo == null) {
            ct.v("Could not find com.google.android.gms.ads.AdActivity, please make sure it is declared in AndroidManifest.xml.");
            return false;
        }
        if ((resolveActivity.activityInfo.configChanges & 16) == 0) {
            ct.v(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"keyboard"}));
            z = false;
        } else {
            z = true;
        }
        if ((resolveActivity.activityInfo.configChanges & 32) == 0) {
            ct.v(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"keyboardHidden"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 128) == 0) {
            ct.v(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"orientation"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 256) == 0) {
            ct.v(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"screenLayout"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 512) == 0) {
            ct.v(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"uiMode"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 1024) == 0) {
            ct.v(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"screenSize"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 2048) != 0) {
            return z;
        }
        ct.v(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"smallestScreenSize"}));
        return false;
    }

    public static void i(Context context) {
        if (!iF) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            context.getApplicationContext().registerReceiver(new a(), intentFilter);
            iF = true;
        }
    }

    /* access modifiers changed from: private */
    public static String j(Context context) {
        return new WebView(context).getSettings().getUserAgentString();
    }

    public static JSONObject m(Map<String, ?> map) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            for (String next : map.keySet()) {
                a(jSONObject, next, (Object) map.get(next));
            }
            return jSONObject;
        } catch (ClassCastException e) {
            throw new JSONException("Could not convert map to JSON: " + e.getMessage());
        }
    }

    public static String o(String str) {
        return Uri.parse(str).buildUpon().query((String) null).build().toString();
    }
}
