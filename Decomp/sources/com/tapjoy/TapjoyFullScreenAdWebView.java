package com.tapjoy;

import android.os.Bundle;

public class TapjoyFullScreenAdWebView extends TJAdUnitView {
    private static final String TAG = "Full Screen Ad";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        TapjoyLog.i(TAG, "TapjoyFullScreenAdWebView onCreate");
        super.onCreate(bundle);
        TapjoyConnectCore.viewDidOpen(1);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            TapjoyConnectCore.viewWillClose(1);
            TapjoyConnectCore.viewDidClose(1);
        }
    }
}
