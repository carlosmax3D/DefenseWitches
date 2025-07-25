package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public final class hd extends gr implements SafeParcelable {
    public static final he CREATOR = new he();
    private final Bundle AM;
    private final hf AN;
    private final LatLng AO;
    private final float AP;
    private final LatLngBounds AQ;
    private final String AR;
    private final Uri AS;
    private final boolean AT;
    private final float AU;
    private final int AV;
    private final long AW;
    private final List<gx> AX;
    private final Map<gx, String> AY;
    private final TimeZone AZ;
    private Locale Ba;
    private hh Bb;
    final int kg;
    private final String uS;

    public static final class a implements SafeParcelable {
        public static final ha CREATOR = new ha();
        private final String Bc;
        private final String Bd;
        private final int Be;
        final int kg;
        private final String mTag;

        /* renamed from: com.google.android.gms.internal.hd$a$a  reason: collision with other inner class name */
        public static class C0034a {
            private final String Bc;
            private String Bd;
            private int Be;
            private final String mTag;

            public C0034a(String str, String str2) {
                this.Bc = str;
                this.mTag = str2;
            }

            public C0034a as(String str) {
                this.Bd = str;
                return this;
            }

            public C0034a bd(int i) {
                this.Be = i;
                return this;
            }

            public a ek() {
                return new a(0, this.Bc, this.mTag, this.Bd, this.Be);
            }
        }

        a(int i, String str, String str2, String str3, int i2) {
            this.kg = i;
            this.Bc = str;
            this.mTag = str2;
            this.Bd = str3;
            this.Be = i2;
        }

        public int describeContents() {
            ha haVar = CREATOR;
            return 0;
        }

        public String eh() {
            return this.Bc;
        }

        public String ei() {
            return this.Bd;
        }

        public int ej() {
            return this.Be;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return this.Bc.equals(aVar.Bc) && ee.equal(this.mTag, aVar.mTag);
        }

        public String getTag() {
            return this.mTag;
        }

        public int hashCode() {
            return ee.hashCode(this.Bc, this.mTag, this.Bd, Integer.valueOf(this.Be));
        }

        public String toString() {
            return ee.e(this).a("placeId", this.Bc).a("tag", this.mTag).a("callingAppPackageName", this.Bd).a("callingAppVersionCode", Integer.valueOf(this.Be)).toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            ha haVar = CREATOR;
            ha.a(this, parcel, i);
        }
    }

    hd(int i, String str, List<gx> list, Bundle bundle, hf hfVar, LatLng latLng, float f, LatLngBounds latLngBounds, String str2, Uri uri, boolean z, float f2, int i2, long j) {
        this.kg = i;
        this.uS = str;
        this.AX = Collections.unmodifiableList(list);
        this.AM = bundle;
        this.AN = hfVar;
        this.AO = latLng;
        this.AP = f;
        this.AQ = latLngBounds;
        this.AR = str2;
        this.AS = uri;
        this.AT = z;
        this.AU = f2;
        this.AV = i2;
        this.AW = j;
        HashMap hashMap = new HashMap();
        for (String str3 : bundle.keySet()) {
            hashMap.put(gx.aq(str3), bundle.getString(str3));
        }
        this.AY = Collections.unmodifiableMap(hashMap);
        this.AZ = TimeZone.getTimeZone(this.AR);
        this.Ba = null;
        this.Bb = null;
    }

    private void ar(String str) {
        if (this.Bb != null) {
            this.Bb.a(new a.C0034a(this.uS, str));
        }
    }

    public List<gx> dW() {
        ar("getTypes");
        return this.AX;
    }

    public LatLng dX() {
        ar("getLatLng");
        return this.AO;
    }

    public float dY() {
        ar("getLevelNumber");
        return this.AP;
    }

    public LatLngBounds dZ() {
        ar("getViewport");
        return this.AQ;
    }

    public int describeContents() {
        he heVar = CREATOR;
        return 0;
    }

    public Uri ea() {
        ar("getWebsiteUri");
        return this.AS;
    }

    public boolean eb() {
        ar("isPermanentlyClosed");
        return this.AT;
    }

    public int ec() {
        ar("getPriceLevel");
        return this.AV;
    }

    public long ed() {
        return this.AW;
    }

    public Bundle ee() {
        return this.AM;
    }

    public hf ef() {
        return this.AN;
    }

    public String eg() {
        return this.AR;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof hd)) {
            return false;
        }
        hd hdVar = (hd) obj;
        return this.uS.equals(hdVar.uS) && ee.equal(this.Ba, hdVar.Ba) && this.AW == hdVar.AW;
    }

    public String getId() {
        ar("getId");
        return this.uS;
    }

    public float getRating() {
        ar("getRating");
        return this.AU;
    }

    public int hashCode() {
        return ee.hashCode(this.uS, this.Ba, Long.valueOf(this.AW));
    }

    public String toString() {
        return ee.e(this).a(ShareConstants.WEB_DIALOG_PARAM_ID, this.uS).a("localization", this.AN).a("locale", this.Ba).a("latlng", this.AO).a("levelNumber", Float.valueOf(this.AP)).a("viewport", this.AQ).a("timeZone", this.AR).a("websiteUri", this.AS).a("isPermanentlyClosed", Boolean.valueOf(this.AT)).a("priceLevel", Integer.valueOf(this.AV)).a("timestampSecs", Long.valueOf(this.AW)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        he heVar = CREATOR;
        he.a(this, parcel, i);
    }
}
