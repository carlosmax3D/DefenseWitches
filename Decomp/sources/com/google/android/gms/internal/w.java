package com.google.android.gms.internal;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.ArrayList;

public class w implements Parcelable.Creator<v> {
    static void a(v vVar, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.c(parcel, 1, vVar.versionCode);
        b.a(parcel, 2, vVar.ex);
        b.a(parcel, 3, vVar.extras, false);
        b.c(parcel, 4, vVar.ey);
        b.a(parcel, 5, vVar.ez, false);
        b.a(parcel, 6, vVar.eA);
        b.c(parcel, 7, vVar.tagForChildDirectedTreatment);
        b.a(parcel, 8, vVar.eB);
        b.a(parcel, 9, vVar.eC, false);
        b.a(parcel, 10, (Parcelable) vVar.eD, i, false);
        b.a(parcel, 11, (Parcelable) vVar.eE, i, false);
        b.D(parcel, o);
    }

    /* renamed from: a */
    public v createFromParcel(Parcel parcel) {
        int n = a.n(parcel);
        int i = 0;
        long j = 0;
        Bundle bundle = null;
        int i2 = 0;
        ArrayList<String> arrayList = null;
        boolean z = false;
        int i3 = 0;
        boolean z2 = false;
        String str = null;
        ai aiVar = null;
        Location location = null;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    i = a.g(parcel, m);
                    break;
                case 2:
                    j = a.h(parcel, m);
                    break;
                case 3:
                    bundle = a.o(parcel, m);
                    break;
                case 4:
                    i2 = a.g(parcel, m);
                    break;
                case 5:
                    arrayList = a.y(parcel, m);
                    break;
                case 6:
                    z = a.c(parcel, m);
                    break;
                case 7:
                    i3 = a.g(parcel, m);
                    break;
                case 8:
                    z2 = a.c(parcel, m);
                    break;
                case 9:
                    str = a.m(parcel, m);
                    break;
                case 10:
                    aiVar = (ai) a.a(parcel, m, ai.CREATOR);
                    break;
                case 11:
                    location = (Location) a.a(parcel, m, Location.CREATOR);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new v(i, j, bundle, i2, arrayList, z, i3, z2, str, aiVar, location);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    /* renamed from: b */
    public v[] newArray(int i) {
        return new v[i];
    }
}
