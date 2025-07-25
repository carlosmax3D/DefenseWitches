package com.facebook.login;

public enum LoginBehavior {
    SSO_WITH_FALLBACK(true, true),
    SSO_ONLY(true, false),
    SUPPRESS_SSO(false, true);
    
    private final boolean allowsKatanaAuth;
    private final boolean allowsWebViewAuth;

    private LoginBehavior(boolean z, boolean z2) {
        this.allowsKatanaAuth = z;
        this.allowsWebViewAuth = z2;
    }

    /* access modifiers changed from: package-private */
    public boolean allowsKatanaAuth() {
        return this.allowsKatanaAuth;
    }

    /* access modifiers changed from: package-private */
    public boolean allowsWebViewAuth() {
        return this.allowsWebViewAuth;
    }
}
