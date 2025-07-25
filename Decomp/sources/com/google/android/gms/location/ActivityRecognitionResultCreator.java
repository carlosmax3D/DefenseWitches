package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import java.util.ArrayList;

public class ActivityRecognitionResultCreator implements Parcelable.Creator<ActivityRecognitionResult> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void a(ActivityRecognitionResult activityRecognitionResult, Parcel parcel, int i) {
        int o = b.o(parcel);
        b.b(parcel, 1, activityRecognitionResult.xm, false);
        b.c(parcel, 1000, activityRecognitionResult.getVersionCode());
        b.a(parcel, 2, activityRecognitionResult.xn);
        b.a(parcel, 3, activityRecognitionResult.xo);
        b.D(parcel, o);
    }

    public ActivityRecognitionResult createFromParcel(Parcel parcel) {
        long j = 0;
        int n = a.n(parcel);
        int i = 0;
        ArrayList arrayList = null;
        long j2 = 0;
        while (parcel.dataPosition() < n) {
            int m = a.m(parcel);
            switch (a.M(m)) {
                case 1:
                    arrayList = a.c(parcel, m, DetectedActivity.CREATOR);
                    break;
                case 2:
                    j2 = a.h(parcel, m);
                    break;
                case 3:
                    j = a.h(parcel, m);
                    break;
                case 1000:
                    i = a.g(parcel, m);
                    break;
                default:
                    a.b(parcel, m);
                    break;
            }
        }
        if (parcel.dataPosition() == n) {
            return new ActivityRecognitionResult(i, arrayList, j2, j);
        }
        throw new a.C0003a("Overread allowed size end=" + n, parcel);
    }

    public ActivityRecognitionResult[] newArray(int i) {
        return new ActivityRecognitionResult[i];
    }
}
