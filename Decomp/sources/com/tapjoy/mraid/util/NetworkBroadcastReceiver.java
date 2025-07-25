package com.tapjoy.mraid.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tapjoy.mraid.controller.Network;

public class NetworkBroadcastReceiver extends BroadcastReceiver {
    private Network mMraidNetwork;

    public NetworkBroadcastReceiver(Network network2) {
        this.mMraidNetwork = network2;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            this.mMraidNetwork.onConnectionChanged();
        }
    }
}
