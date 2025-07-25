package com.facebook.share.internal;

import com.facebook.internal.DialogFeature;
import com.facebook.internal.NativeProtocol;

public enum OpenGraphMessageDialogFeature implements DialogFeature {
    OG_MESSAGE_DIALOG(NativeProtocol.PROTOCOL_VERSION_20140204);
    
    private int minVersion;

    private OpenGraphMessageDialogFeature(int i) {
        this.minVersion = i;
    }

    public String getAction() {
        return NativeProtocol.ACTION_OGMESSAGEPUBLISH_DIALOG;
    }

    public int getMinVersion() {
        return this.minVersion;
    }
}
