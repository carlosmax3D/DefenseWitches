package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.eg;
import java.util.Collections;
import java.util.List;

public class ActivityRecognitionResult implements SafeParcelable {
    public static final ActivityRecognitionResultCreator CREATOR = new ActivityRecognitionResultCreator();
    public static final String EXTRA_ACTIVITY_RESULT = "com.google.android.location.internal.EXTRA_ACTIVITY_RESULT";
    private final int kg;
    List<DetectedActivity> xm;
    long xn;
    long xo;

    public ActivityRecognitionResult(int i, List<DetectedActivity> list, long j, long j2) {
        this.kg = 1;
        this.xm = list;
        this.xn = j;
        this.xo = j2;
    }

    public ActivityRecognitionResult(DetectedActivity detectedActivity, long j, long j2) {
        this((List<DetectedActivity>) Collections.singletonList(detectedActivity), j, j2);
    }

    public ActivityRecognitionResult(List<DetectedActivity> list, long j, long j2) {
        eg.b(list != null && list.size() > 0, (Object) "Must have at least 1 detected activity");
        this.kg = 1;
        this.xm = list;
        this.xn = j;
        this.xo = j2;
    }

    public static ActivityRecognitionResult extractResult(Intent intent) {
        if (!hasResult(intent)) {
            return null;
        }
        return (ActivityRecognitionResult) intent.getExtras().get(EXTRA_ACTIVITY_RESULT);
    }

    public static boolean hasResult(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.hasExtra(EXTRA_ACTIVITY_RESULT);
    }

    public int describeContents() {
        return 0;
    }

    public int getActivityConfidence(int i) {
        for (DetectedActivity next : this.xm) {
            if (next.getType() == i) {
                return next.getConfidence();
            }
        }
        return 0;
    }

    public long getElapsedRealtimeMillis() {
        return this.xo;
    }

    public DetectedActivity getMostProbableActivity() {
        return this.xm.get(0);
    }

    public List<DetectedActivity> getProbableActivities() {
        return this.xm;
    }

    public long getTime() {
        return this.xn;
    }

    public int getVersionCode() {
        return this.kg;
    }

    public String toString() {
        return "ActivityRecognitionResult [probableActivities=" + this.xm + ", timeMillis=" + this.xn + ", elapsedRealtimeMillis=" + this.xo + "]";
    }

    public void writeToParcel(Parcel parcel, int i) {
        ActivityRecognitionResultCreator.a(this, parcel, i);
    }
}
