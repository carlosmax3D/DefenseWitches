package com.tapjoy.mraid.controller;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.tapjoy.TapjoyConstants;
import com.tapjoy.TapjoyLog;
import com.tapjoy.mraid.util.NetworkBroadcastReceiver;
import com.tapjoy.mraid.view.MraidView;

public class Network extends Abstract {
    private static final String TAG = "MRAID Network";
    private NetworkBroadcastReceiver mBroadCastReceiver;
    private ConnectivityManager mConnectivityManager;
    private IntentFilter mFilter;
    private int mNetworkListenerCount;

    /* renamed from: com.tapjoy.mraid.controller.Network$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$net$NetworkInfo$State = new int[NetworkInfo.State.values().length];

        static {
            try {
                $SwitchMap$android$net$NetworkInfo$State[NetworkInfo.State.UNKNOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$State[NetworkInfo.State.DISCONNECTED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public Network(MraidView mraidView, Context context) {
        super(mraidView, context);
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
    }

    public String getNetwork() {
        NetworkInfo networkInfo = null;
        try {
            networkInfo = this.mConnectivityManager.getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = "unknown";
        if (networkInfo != null) {
            switch (AnonymousClass1.$SwitchMap$android$net$NetworkInfo$State[networkInfo.getState().ordinal()]) {
                case 1:
                    str = "unknown";
                    break;
                case 2:
                    str = TapjoyConstants.TJC_OFFLINE;
                    break;
                default:
                    int type = networkInfo.getType();
                    if (type != 0) {
                        if (type == 1) {
                            str = TapjoyConstants.TJC_CONNECTION_TYPE_WIFI;
                            break;
                        }
                    } else {
                        str = "cell";
                        break;
                    }
                    break;
            }
        } else {
            str = TapjoyConstants.TJC_OFFLINE;
        }
        TapjoyLog.d(TAG, "getNetwork: " + str);
        return str;
    }

    public void onConnectionChanged() {
        String str = "window.mraidview.fireChangeEvent({ network: '" + getNetwork() + "'});";
        TapjoyLog.d(TAG, str);
        this.mMraidView.injectMraidJavaScript(str);
    }

    public void startNetworkListener() {
        if (this.mNetworkListenerCount == 0) {
            this.mBroadCastReceiver = new NetworkBroadcastReceiver(this);
            this.mFilter = new IntentFilter();
            this.mFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        }
        this.mNetworkListenerCount++;
        this.mContext.registerReceiver(this.mBroadCastReceiver, this.mFilter);
    }

    public void stopAllListeners() {
        this.mNetworkListenerCount = 0;
        try {
            this.mContext.unregisterReceiver(this.mBroadCastReceiver);
        } catch (Exception e) {
        }
    }

    public void stopNetworkListener() {
        this.mNetworkListenerCount--;
        if (this.mNetworkListenerCount == 0) {
            this.mContext.unregisterReceiver(this.mBroadCastReceiver);
            this.mBroadCastReceiver = null;
            this.mFilter = null;
        }
    }
}
