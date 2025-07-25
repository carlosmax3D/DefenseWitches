package com.threatmetrix.TrustDefenderMobile;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import jp.stargarage.g2metrics.BuildConfig;

class JavaScriptInterface implements ValueCallback<String> {
    private final String TAG = JavaScriptInterface.class.getName();
    CountDownLatch latch = null;
    public String returnedValue;
    public final ArrayList<String> returnedValues = new ArrayList<>();

    JavaScriptInterface(CountDownLatch countDownLatch) {
        setLatch(countDownLatch);
    }

    private void callback(String str, String str2) {
        try {
            CountDownLatch countDownLatch = this.latch;
            String str3 = str;
            if (str == null) {
                str3 = "null";
            }
            long j = 0;
            if (countDownLatch != null) {
                j = countDownLatch.getCount();
            }
            String str4 = this.TAG;
            new StringBuilder("in ").append(str2).append("(").append(str3).append(") count = ").append(j);
            this.returnedValue = str;
            if (str == null) {
                this.returnedValues.add(BuildConfig.FLAVOR);
            } else {
                this.returnedValues.add(str);
            }
            if (countDownLatch != null) {
                String str5 = this.TAG;
                new StringBuilder("countdown latch: ").append(countDownLatch.hashCode()).append(" with count: ").append(countDownLatch.getCount());
                countDownLatch.countDown();
                if (str2 == null) {
                    str2 = "null";
                }
                if (countDownLatch == null) {
                    String str6 = this.TAG;
                    new StringBuilder("in ").append(str2).append("() with null latch");
                    return;
                }
                String str7 = this.TAG;
                new StringBuilder("in ").append(str2).append("() count = ").append(countDownLatch.getCount()).append(" and ").append(countDownLatch == this.latch ? "latch constant" : "latch changed");
                return;
            }
            Log.e(this.TAG, "in " + str2 + "() latch == null");
        } catch (Exception e) {
            String str8 = this.TAG;
        }
    }

    private void onReceiveValue(String str) {
        if (str != null) {
            if (str.length() == 2 && str.equals("\"\"")) {
                str = BuildConfig.FLAVOR;
            } else if (str.length() > 1) {
                str = str.substring(1, str.length() - 1);
            }
        }
        callback(str, "onReceiveValue");
    }

    @JavascriptInterface
    public final void getString(String str) {
        callback(str, "getString");
    }

    public /* bridge */ /* synthetic */ void onReceiveValue(Object obj) {
        String str = (String) obj;
        if (str != null) {
            if (str.length() == 2 && str.equals("\"\"")) {
                str = BuildConfig.FLAVOR;
            } else if (str.length() > 1) {
                str = str.substring(1, str.length() - 1);
            }
        }
        callback(str, "onReceiveValue");
    }

    public final void setLatch(CountDownLatch countDownLatch) {
        if (this.latch != null) {
            String str = this.TAG;
            new StringBuilder("existing latch: ").append(this.latch.hashCode()).append(" with count: ").append(this.latch.getCount());
            String str2 = this.TAG;
        }
        this.latch = countDownLatch;
        if (this.latch != null) {
            String str3 = this.TAG;
            new StringBuilder("new latch: ").append(countDownLatch.hashCode()).append(" with count: ").append(countDownLatch.getCount());
        }
    }
}
