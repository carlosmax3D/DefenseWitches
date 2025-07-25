package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.dh;
import com.google.android.gms.internal.ee;
import jp.co.voyagegroup.android.fluct.jar.util.FluctConstants;
import org.json.JSONException;
import org.json.JSONObject;

public final class WebImage implements SafeParcelable {
    public static final Parcelable.Creator<WebImage> CREATOR = new b();
    private final int kg;
    private final Uri oA;
    private final int v;
    private final int w;

    WebImage(int i, Uri uri, int i2, int i3) {
        this.kg = i;
        this.oA = uri;
        this.w = i2;
        this.v = i3;
    }

    public WebImage(Uri uri) throws IllegalArgumentException {
        this(uri, 0, 0);
    }

    public WebImage(Uri uri, int i, int i2) throws IllegalArgumentException {
        this(1, uri, i, i2);
        if (uri == null) {
            throw new IllegalArgumentException("url cannot be null");
        } else if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("width and height must not be negative");
        }
    }

    public WebImage(JSONObject jSONObject) throws IllegalArgumentException {
        this(f(jSONObject), jSONObject.optInt(FluctConstants.XML_NODE_WIDTH, 0), jSONObject.optInt(FluctConstants.XML_NODE_HEIGHT, 0));
    }

    private static Uri f(JSONObject jSONObject) {
        if (!jSONObject.has("url")) {
            return null;
        }
        try {
            return Uri.parse(jSONObject.getString("url"));
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject aP() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", this.oA.toString());
            jSONObject.put(FluctConstants.XML_NODE_WIDTH, this.w);
            jSONObject.put(FluctConstants.XML_NODE_HEIGHT, this.v);
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof WebImage)) {
            return false;
        }
        WebImage webImage = (WebImage) obj;
        return dh.a(this.oA, webImage.oA) && this.w == webImage.w && this.v == webImage.v;
    }

    public int getHeight() {
        return this.v;
    }

    public Uri getUrl() {
        return this.oA;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public int getWidth() {
        return this.w;
    }

    public int hashCode() {
        return ee.hashCode(this.oA, Integer.valueOf(this.w), Integer.valueOf(this.v));
    }

    public String toString() {
        return String.format("Image %dx%d %s", new Object[]{Integer.valueOf(this.w), Integer.valueOf(this.v), this.oA.toString()});
    }

    public void writeToParcel(Parcel parcel, int i) {
        b.a(this, parcel, i);
    }
}
