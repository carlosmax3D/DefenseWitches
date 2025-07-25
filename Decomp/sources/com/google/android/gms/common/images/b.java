package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

public class b implements Parcelable.Creator<WebImage> {
    static void a(WebImage webImage, Parcel parcel, int i) {
        int o = com.google.android.gms.common.internal.safeparcel.b.o(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, webImage.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, (Parcelable) webImage.getUrl(), i, false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 3, webImage.getWidth());
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 4, webImage.getHeight());
        com.google.android.gms.common.internal.safeparcel.b.D(parcel, o);
    }

    /* renamed from: G */
    public WebImage[] newArray(int i) {
        return new WebImage[i];
    }

    /* renamed from: k */
    public WebImage createFromParcel(Parcel parcel) {
        int g;
        int i;
        Uri uri;
        int i2;
        int i3 = 0;
        int n = a.n(parcel);
        Uri uri2 = null;
        int i4 = 0;
        int i5 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    int i6 = i3;
                    i = i4;
                    uri = uri2;
                    i2 = a.g(parcel, m);
                    g = i6;
                    break;
                case 2:
                    i2 = i5;
                    int i7 = i4;
                    uri = (Uri) a.a(parcel, m, Uri.CREATOR);
                    g = i3;
                    i = i7;
                    break;
                case 3:
                    uri = uri2;
                    i2 = i5;
                    int i8 = i3;
                    i = a.g(parcel, m);
                    g = i8;
                    break;
                case 4:
                    g = a.g(parcel, m);
                    i = i4;
                    uri = uri2;
                    i2 = i5;
                    break;
                default:
                    a.b(parcel, m);
                    g = i3;
                    i = i4;
                    uri = uri2;
                    i2 = i5;
                    break;
            }
            i5 = i2;
            uri2 = uri;
            i4 = i;
            i3 = g;
        }
        if (parcel.dataPosition() == n) {
            return new WebImage(i5, uri2, i4, i3);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }
}
