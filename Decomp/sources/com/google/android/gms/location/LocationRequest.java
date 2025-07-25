package com.google.android.gms.location;

import android.os.Parcel;
import android.os.SystemClock;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ee;

public final class LocationRequest implements SafeParcelable {
    public static final LocationRequestCreator CREATOR = new LocationRequestCreator();
    public static final int PRIORITY_BALANCED_POWER_ACCURACY = 102;
    public static final int PRIORITY_HIGH_ACCURACY = 100;
    public static final int PRIORITY_LOW_POWER = 104;
    public static final int PRIORITY_NO_POWER = 105;
    private final int kg;
    int mPriority;
    long xB;
    long xC;
    boolean xD;
    int xE;
    float xF;
    long xu;

    public LocationRequest() {
        this.kg = 1;
        this.mPriority = 102;
        this.xB = 3600000;
        this.xC = 600000;
        this.xD = false;
        this.xu = Long.MAX_VALUE;
        this.xE = Integer.MAX_VALUE;
        this.xF = 0.0f;
    }

    LocationRequest(int i, int i2, long j, long j2, boolean z, long j3, int i3, float f) {
        this.kg = i;
        this.mPriority = i2;
        this.xB = j;
        this.xC = j2;
        this.xD = z;
        this.xu = j3;
        this.xE = i3;
        this.xF = f;
    }

    private static void a(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("invalid displacement: " + f);
        }
    }

    private static void aO(int i) {
        switch (i) {
            case 100:
            case 102:
            case PRIORITY_LOW_POWER /*104*/:
            case PRIORITY_NO_POWER /*105*/:
                return;
            default:
                throw new IllegalArgumentException("invalid quality: " + i);
        }
    }

    public static String aP(int i) {
        switch (i) {
            case 100:
                return "PRIORITY_HIGH_ACCURACY";
            case 102:
                return "PRIORITY_BALANCED_POWER_ACCURACY";
            case PRIORITY_LOW_POWER /*104*/:
                return "PRIORITY_LOW_POWER";
            case PRIORITY_NO_POWER /*105*/:
                return "PRIORITY_NO_POWER";
            default:
                return "???";
        }
    }

    public static LocationRequest create() {
        return new LocationRequest();
    }

    private static void m(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("invalid interval: " + j);
        }
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LocationRequest)) {
            return false;
        }
        LocationRequest locationRequest = (LocationRequest) obj;
        return this.mPriority == locationRequest.mPriority && this.xB == locationRequest.xB && this.xC == locationRequest.xC && this.xD == locationRequest.xD && this.xu == locationRequest.xu && this.xE == locationRequest.xE && this.xF == locationRequest.xF;
    }

    public long getExpirationTime() {
        return this.xu;
    }

    public long getFastestInterval() {
        return this.xC;
    }

    public long getInterval() {
        return this.xB;
    }

    public int getNumUpdates() {
        return this.xE;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public float getSmallestDisplacement() {
        return this.xF;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public int hashCode() {
        return ee.hashCode(Integer.valueOf(this.mPriority), Long.valueOf(this.xB), Long.valueOf(this.xC), Boolean.valueOf(this.xD), Long.valueOf(this.xu), Integer.valueOf(this.xE), Float.valueOf(this.xF));
    }

    public LocationRequest setExpirationDuration(long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (j > Long.MAX_VALUE - elapsedRealtime) {
            this.xu = Long.MAX_VALUE;
        } else {
            this.xu = elapsedRealtime + j;
        }
        if (this.xu < 0) {
            this.xu = 0;
        }
        return this;
    }

    public LocationRequest setExpirationTime(long j) {
        this.xu = j;
        if (this.xu < 0) {
            this.xu = 0;
        }
        return this;
    }

    public LocationRequest setFastestInterval(long j) {
        m(j);
        this.xD = true;
        this.xC = j;
        return this;
    }

    public LocationRequest setInterval(long j) {
        m(j);
        this.xB = j;
        if (!this.xD) {
            this.xC = (long) (((double) this.xB) / 6.0d);
        }
        return this;
    }

    public LocationRequest setNumUpdates(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("invalid numUpdates: " + i);
        }
        this.xE = i;
        return this;
    }

    public LocationRequest setPriority(int i) {
        aO(i);
        this.mPriority = i;
        return this;
    }

    public LocationRequest setSmallestDisplacement(float f) {
        a(f);
        this.xF = f;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request[").append(aP(this.mPriority));
        if (this.mPriority != 105) {
            sb.append(" requested=");
            sb.append(this.xB + "ms");
        }
        sb.append(" fastest=");
        sb.append(this.xC + "ms");
        if (this.xu != Long.MAX_VALUE) {
            sb.append(" expireIn=");
            sb.append((this.xu - SystemClock.elapsedRealtime()) + "ms");
        }
        if (this.xE != Integer.MAX_VALUE) {
            sb.append(" num=").append(this.xE);
        }
        sb.append(']');
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        LocationRequestCreator.a(this, parcel, i);
    }
}
