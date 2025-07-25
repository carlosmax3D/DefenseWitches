package com.google.android.gms.cast;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.ArrayList;

public class a implements Parcelable.Creator<ApplicationMetadata> {
    static void a(ApplicationMetadata applicationMetadata, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, applicationMetadata.getVersionCode());
        b.a(parcel, 2, applicationMetadata.getApplicationId(), false);
        b.a(parcel, 3, applicationMetadata.getName(), false);
        b.b(parcel, 4, applicationMetadata.getImages(), false);
        b.a(parcel, 5, applicationMetadata.kj, false);
        b.a(parcel, 6, applicationMetadata.getSenderAppIdentifier(), false);
        b.a(parcel, 7, (Parcelable) applicationMetadata.aN(), i, false);
        b.D(parcel, o);
    }

    /* renamed from: i */
    public ApplicationMetadata createFromParcel(Parcel parcel) {
        Uri uri = null;
        int n = com.google.android.gms.common.internal.safeparcel.a.n(parcel);
        int i = 0;
        String str = null;
        ArrayList<String> arrayList = null;
        ArrayList<WebImage> arrayList2 = null;
        String str2 = null;
        String str3 = null;
        while (parcel.dataPosition() < n) {
            int m = com.google.android.gms.common.internal.safeparcel.a.m(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.M(m)) {
                case 1:
                    i = com.google.android.gms.common.internal.safeparcel.a.g(parcel, m);
                    break;
                case 2:
                    str3 = com.google.android.gms.common.internal.safeparcel.a.m(parcel, m);
                    break;
                case 3:
                    str2 = com.google.android.gms.common.internal.safeparcel.a.m(parcel, m);
                    break;
                case 4:
                    arrayList2 = com.google.android.gms.common.internal.safeparcel.a.c(parcel, m, WebImage.CREATOR);
                    break;
                case 5:
                    arrayList = com.google.android.gms.common.internal.safeparcel.a.y(parcel, m);
                    break;
                case 6:
                    str = com.google.android.gms.common.internal.safeparcel.a.m(parcel, m);
                    break;
                case 7:
                    uri = (Uri) com.google.android.gms.common.internal.safeparcel.a.a(parcel, m, Uri.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ApplicationMetadata(i, str3, str2, arrayList2, arrayList, str, uri);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: q */
    public ApplicationMetadata[] newArray(int i) {
        return new ApplicationMetadata[i];
    }
}
