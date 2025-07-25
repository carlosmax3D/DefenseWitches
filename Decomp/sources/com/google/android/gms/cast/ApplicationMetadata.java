package com.google.android.gms.cast;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.List;

public final class ApplicationMetadata implements SafeParcelable {
    public static final Parcelable.Creator<ApplicationMetadata> CREATOR = new a();
    private final int kg;
    String kh;
    List<WebImage> ki;
    List<String> kj;
    String kk;
    Uri kl;
    String mName;

    private ApplicationMetadata() {
        this.kg = 1;
        this.ki = new ArrayList();
        this.kj = new ArrayList();
    }

    ApplicationMetadata(int i, String str, String str2, List<WebImage> list, List<String> list2, String str3, Uri uri) {
        this.kg = i;
        this.kh = str;
        this.mName = str2;
        this.ki = list;
        this.kj = list2;
        this.kk = str3;
        this.kl = uri;
    }

    public Uri aN() {
        return this.kl;
    }

    public boolean areNamespacesSupported(List<String> list) {
        return this.kj != null && this.kj.containsAll(list);
    }

    public int describeContents() {
        return 0;
    }

    public String getApplicationId() {
        return this.kh;
    }

    public List<WebImage> getImages() {
        return this.ki;
    }

    public String getName() {
        return this.mName;
    }

    public String getSenderAppIdentifier() {
        return this.kk;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public boolean isNamespaceSupported(String str) {
        return this.kj != null && this.kj.contains(str);
    }

    public String toString() {
        return this.mName;
    }

    public void writeToParcel(Parcel parcel, int i) {
        a.a(this, parcel, i);
    }
}
