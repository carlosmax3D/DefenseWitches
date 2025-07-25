package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;
import com.facebook.internal.NativeProtocol;

public enum ShareDialogFeature implements DialogFeature {
    SHARE_DIALOG(NativeProtocol.PROTOCOL_VERSION_20130618),
    PHOTOS(NativeProtocol.PROTOCOL_VERSION_20140204),
    VIDEO(NativeProtocol.PROTOCOL_VERSION_20141028);
    
    private int minVersion;

    private ShareDialogFeature(int i) {
        this.minVersion = i;
    }

    public String getAction() {
        return NativeProtocol.ACTION_FEED_DIALOG;
    }

    public int getMinVersion() {
        return this.minVersion;
    }
}
