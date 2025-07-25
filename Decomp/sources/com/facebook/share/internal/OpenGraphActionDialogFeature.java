package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;
import com.facebook.internal.NativeProtocol;

public enum OpenGraphActionDialogFeature implements DialogFeature {
    OG_ACTION_DIALOG(NativeProtocol.PROTOCOL_VERSION_20130618);
    
    private int minVersion;

    private OpenGraphActionDialogFeature(int i) {
        this.minVersion = i;
    }

    public String getAction() {
        return NativeProtocol.ACTION_OGACTIONPUBLISH_DIALOG;
    }

    public int getMinVersion() {
        return this.minVersion;
    }
}
