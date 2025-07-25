package com.facebook;

public enum AccessTokenSource {
    NONE(false),
    FACEBOOK_APPLICATION_WEB(true),
    FACEBOOK_APPLICATION_NATIVE(true),
    FACEBOOK_APPLICATION_SERVICE(true),
    WEB_VIEW(false),
    TEST_USER(true),
    CLIENT_TOKEN(true);
    
    private final boolean canExtendToken;

    private AccessTokenSource(boolean z) {
        this.canExtendToken = z;
    }

    /* access modifiers changed from: package-private */
    public boolean canExtendToken() {
        return this.canExtendToken;
    }
}
