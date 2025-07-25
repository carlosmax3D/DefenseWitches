package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.a;
import java.util.ArrayList;

public class b implements Parcelable.Creator<CastDevice> {
    static void a(CastDevice castDevice, Parcel parcel, int i) {
        int o = com.google.android.gms.common.internal.safeparcel.b.o(parcel);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 1, castDevice.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 2, castDevice.getDeviceId(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 3, castDevice.kA, false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 4, castDevice.getFriendlyName(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 5, castDevice.getModelName(), false);
        com.google.android.gms.common.internal.safeparcel.b.a(parcel, 6, castDevice.getDeviceVersion(), false);
        com.google.android.gms.common.internal.safeparcel.b.c(parcel, 7, castDevice.getServicePort());
        com.google.android.gms.common.internal.safeparcel.b.b(parcel, 8, castDevice.getIcons(), false);
        com.google.android.gms.common.internal.safeparcel.b.D(parcel, o);
    }

    /* renamed from: j */
    public CastDevice createFromParcel(Parcel parcel) {
        int i = 0;
        ArrayList<WebImage> arrayList = null;
        int n = a.n(parcel);
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        int i2 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i2 = a.g(parcel, m);
                    break;
                case 2:
                    str5 = a.m(parcel, m);
                    break;
                case 3:
                    str4 = a.m(parcel, m);
                    break;
                case 4:
                    str3 = a.m(parcel, m);
                    break;
                case 5:
                    str2 = a.m(parcel, m);
                    break;
                case 6:
                    str = a.m(parcel, m);
                    break;
                case 7:
                    i = a.g(parcel, m);
                    break;
                case 8:
                    arrayList = a.c(parcel, m, WebImage.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new CastDevice(i2, str5, str4, str3, str2, str, i, arrayList);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: s */
    public CastDevice[] newArray(int i) {
        return new CastDevice[i];
    }
}
