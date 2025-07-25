package com.google.android.gms.ads.search;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.internal.af;

public final class SearchAdRequest {
    public static final int BORDER_TYPE_DASHED = 1;
    public static final int BORDER_TYPE_DOTTED = 2;
    public static final int BORDER_TYPE_NONE = 0;
    public static final int BORDER_TYPE_SOLID = 3;
    public static final int CALL_BUTTON_COLOR_DARK = 2;
    public static final int CALL_BUTTON_COLOR_LIGHT = 0;
    public static final int CALL_BUTTON_COLOR_MEDIUM = 1;
    public static final String DEVICE_ID_EMULATOR = af.DEVICE_ID_EMULATOR;
    public static final int ERROR_CODE_INTERNAL_ERROR = 0;
    public static final int ERROR_CODE_INVALID_REQUEST = 1;
    public static final int ERROR_CODE_NETWORK_ERROR = 2;
    public static final int ERROR_CODE_NO_FILL = 3;
    private final af dW;
    private final int jj;
    private final int jk;
    private final int jl;
    private final int jm;
    private final int jn;
    private final int jo;

    /* renamed from: jp  reason: collision with root package name */
    private final int f2jp;
    private final int jq;
    private final String jr;
    private final int js;
    private final String jt;
    private final int ju;
    private final int jv;
    private final String jw;

    public static final class Builder {
        /* access modifiers changed from: private */
        public final af.a dX = new af.a();
        /* access modifiers changed from: private */
        public int jj;
        /* access modifiers changed from: private */
        public int jk;
        /* access modifiers changed from: private */
        public int jl;
        /* access modifiers changed from: private */
        public int jm;
        /* access modifiers changed from: private */
        public int jn;
        /* access modifiers changed from: private */
        public int jo;
        /* access modifiers changed from: private */

        /* renamed from: jp  reason: collision with root package name */
        public int f3jp = 0;
        /* access modifiers changed from: private */
        public int jq;
        /* access modifiers changed from: private */
        public String jr;
        /* access modifiers changed from: private */
        public int js;
        /* access modifiers changed from: private */
        public String jt;
        /* access modifiers changed from: private */
        public int ju;
        /* access modifiers changed from: private */
        public int jv;
        /* access modifiers changed from: private */
        public String jw;

        public Builder addNetworkExtras(NetworkExtras networkExtras) {
            this.dX.a(networkExtras);
            return this;
        }

        public Builder addTestDevice(String str) {
            this.dX.h(str);
            return this;
        }

        public SearchAdRequest build() {
            return new SearchAdRequest(this);
        }

        public Builder setAnchorTextColor(int i) {
            this.jj = i;
            return this;
        }

        public Builder setBackgroundColor(int i) {
            this.jk = i;
            this.jl = Color.argb(0, 0, 0, 0);
            this.jm = Color.argb(0, 0, 0, 0);
            return this;
        }

        public Builder setBackgroundGradient(int i, int i2) {
            this.jk = Color.argb(0, 0, 0, 0);
            this.jl = i2;
            this.jm = i;
            return this;
        }

        public Builder setBorderColor(int i) {
            this.jn = i;
            return this;
        }

        public Builder setBorderThickness(int i) {
            this.jo = i;
            return this;
        }

        public Builder setBorderType(int i) {
            this.f3jp = i;
            return this;
        }

        public Builder setCallButtonColor(int i) {
            this.jq = i;
            return this;
        }

        public Builder setCustomChannels(String str) {
            this.jr = str;
            return this;
        }

        public Builder setDescriptionTextColor(int i) {
            this.js = i;
            return this;
        }

        public Builder setFontFace(String str) {
            this.jt = str;
            return this;
        }

        public Builder setHeaderTextColor(int i) {
            this.ju = i;
            return this;
        }

        public Builder setHeaderTextSize(int i) {
            this.jv = i;
            return this;
        }

        public Builder setLocation(Location location) {
            this.dX.a(location);
            return this;
        }

        public Builder setQuery(String str) {
            this.jw = str;
            return this;
        }

        public Builder tagForChildDirectedTreatment(boolean z) {
            this.dX.e(z);
            return this;
        }
    }

    private SearchAdRequest(Builder builder) {
        this.jj = builder.jj;
        this.jk = builder.jk;
        this.jl = builder.jl;
        this.jm = builder.jm;
        this.jn = builder.jn;
        this.jo = builder.jo;
        this.f2jp = builder.f3jp;
        this.jq = builder.jq;
        this.jr = builder.jr;
        this.js = builder.js;
        this.jt = builder.jt;
        this.ju = builder.ju;
        this.jv = builder.jv;
        this.jw = builder.jw;
        this.dW = new af(builder.dX, this);
    }

    public int getAnchorTextColor() {
        return this.jj;
    }

    public int getBackgroundColor() {
        return this.jk;
    }

    public int getBackgroundGradientBottom() {
        return this.jl;
    }

    public int getBackgroundGradientTop() {
        return this.jm;
    }

    public int getBorderColor() {
        return this.jn;
    }

    public int getBorderThickness() {
        return this.jo;
    }

    public int getBorderType() {
        return this.f2jp;
    }

    public int getCallButtonColor() {
        return this.jq;
    }

    public String getCustomChannels() {
        return this.jr;
    }

    public int getDescriptionTextColor() {
        return this.js;
    }

    public String getFontFace() {
        return this.jt;
    }

    public int getHeaderTextColor() {
        return this.ju;
    }

    public int getHeaderTextSize() {
        return this.jv;
    }

    public Location getLocation() {
        return this.dW.getLocation();
    }

    public <T extends NetworkExtras> T getNetworkExtras(Class<T> cls) {
        return this.dW.getNetworkExtras(cls);
    }

    public String getQuery() {
        return this.jw;
    }

    public boolean isTestDevice(Context context) {
        return this.dW.isTestDevice(context);
    }

    /* access modifiers changed from: package-private */
    public af v() {
        return this.dW;
    }
}
