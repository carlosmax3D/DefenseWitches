package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;
import com.facebook.internal.NativeProtocol;

public enum MessageDialogFeature implements DialogFeature {
    MESSAGE_DIALOG(NativeProtocol.PROTOCOL_VERSION_20140204),
    PHOTOS(NativeProtocol.PROTOCOL_VERSION_20140324),
    VIDEO(NativeProtocol.PROTOCOL_VERSION_20141218);
    
    private int minVersion;

    private MessageDialogFeature(int i) {
        this.minVersion = i;
    }

    public String getAction() {
        return NativeProtocol.ACTION_MESSAGE_DIALOG;
    }

    public int getMinVersion() {
        return this.minVersion;
    }
}
