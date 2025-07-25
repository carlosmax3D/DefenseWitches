package com.facebook.appevents;

import android.os.Bundle;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import com.tapjoy.TapjoyConstants;
import java.io.Serializable;
import java.util.Locale;

class FacebookTimeSpentData implements Serializable {
    private static final long APP_ACTIVATE_SUPPRESSION_PERIOD_IN_MILLISECONDS = 300000;
    private static final long FIRST_TIME_LOAD_RESUME_TIME = -1;
    private static final long[] INACTIVE_SECONDS_QUANTA = {APP_ACTIVATE_SUPPRESSION_PERIOD_IN_MILLISECONDS, TapjoyConstants.PAID_APP_TIME, TapjoyConstants.SESSION_ID_INACTIVITY_TIME, 3600000, 21600000, 43200000, 86400000, 172800000, 259200000, 604800000, 1209600000, 1814400000, 2419200000L, 5184000000L, 7776000000L, 10368000000L, 12960000000L, 15552000000L, 31536000000L};
    private static final long INTERRUPTION_THRESHOLD_MILLISECONDS = 1000;
    private static final long NUM_MILLISECONDS_IDLE_TO_BE_NEW_SESSION = 60000;
    private static final String TAG = AppEventsLogger.class.getCanonicalName();
    private static final long serialVersionUID = 1;
    private String firstOpenSourceApplication;
    private int interruptionCount;
    private boolean isAppActive;
    private boolean isWarmLaunch;
    private long lastActivateEventLoggedTime;
    private long lastResumeTime;
    private long lastSuspendTime;
    private long millisecondsSpentInSession;

    private static class SerializationProxyV1 implements Serializable {
        private static final long serialVersionUID = 6;
        private final int interruptionCount;
        private final long lastResumeTime;
        private final long lastSuspendTime;
        private final long millisecondsSpentInSession;

        SerializationProxyV1(long j, long j2, long j3, int i) {
            this.lastResumeTime = j;
            this.lastSuspendTime = j2;
            this.millisecondsSpentInSession = j3;
            this.interruptionCount = i;
        }

        private Object readResolve() {
            return new FacebookTimeSpentData(this.lastResumeTime, this.lastSuspendTime, this.millisecondsSpentInSession, this.interruptionCount);
        }
    }

    private static class SerializationProxyV2 implements Serializable {
        private static final long serialVersionUID = 6;
        private final String firstOpenSourceApplication;
        private final int interruptionCount;
        private final long lastResumeTime;
        private final long lastSuspendTime;
        private final long millisecondsSpentInSession;

        SerializationProxyV2(long j, long j2, long j3, int i, String str) {
            this.lastResumeTime = j;
            this.lastSuspendTime = j2;
            this.millisecondsSpentInSession = j3;
            this.interruptionCount = i;
            this.firstOpenSourceApplication = str;
        }

        private Object readResolve() {
            return new FacebookTimeSpentData(this.lastResumeTime, this.lastSuspendTime, this.millisecondsSpentInSession, this.interruptionCount, this.firstOpenSourceApplication);
        }
    }

    FacebookTimeSpentData() {
        resetSession();
    }

    private FacebookTimeSpentData(long j, long j2, long j3, int i) {
        resetSession();
        this.lastResumeTime = j;
        this.lastSuspendTime = j2;
        this.millisecondsSpentInSession = j3;
        this.interruptionCount = i;
    }

    private FacebookTimeSpentData(long j, long j2, long j3, int i, String str) {
        resetSession();
        this.lastResumeTime = j;
        this.lastSuspendTime = j2;
        this.millisecondsSpentInSession = j3;
        this.interruptionCount = i;
        this.firstOpenSourceApplication = str;
    }

    private static int getQuantaIndex(long j) {
        int i = 0;
        while (i < INACTIVE_SECONDS_QUANTA.length && INACTIVE_SECONDS_QUANTA[i] < j) {
            i++;
        }
        return i;
    }

    private boolean isColdLaunch() {
        boolean z = !this.isWarmLaunch;
        this.isWarmLaunch = true;
        return z;
    }

    private void logAppDeactivatedEvent(AppEventsLogger appEventsLogger, long j) {
        Bundle bundle = new Bundle();
        bundle.putInt(AppEventsConstants.EVENT_NAME_SESSION_INTERRUPTIONS, this.interruptionCount);
        bundle.putString(AppEventsConstants.EVENT_NAME_TIME_BETWEEN_SESSIONS, String.format(Locale.ROOT, "session_quanta_%d", new Object[]{Integer.valueOf(getQuantaIndex(j))}));
        bundle.putString(AppEventsConstants.EVENT_PARAM_SOURCE_APPLICATION, this.firstOpenSourceApplication);
        appEventsLogger.logEvent(AppEventsConstants.EVENT_NAME_DEACTIVATED_APP, (double) (this.millisecondsSpentInSession / INTERRUPTION_THRESHOLD_MILLISECONDS), bundle);
        resetSession();
    }

    private void resetSession() {
        this.isAppActive = false;
        this.lastResumeTime = -1;
        this.lastSuspendTime = -1;
        this.interruptionCount = 0;
        this.millisecondsSpentInSession = 0;
    }

    private boolean wasSuspendedEver() {
        return this.lastSuspendTime != -1;
    }

    private Object writeReplace() {
        return new SerializationProxyV2(this.lastResumeTime, this.lastSuspendTime, this.millisecondsSpentInSession, this.interruptionCount, this.firstOpenSourceApplication);
    }

    /* access modifiers changed from: package-private */
    public void onResume(AppEventsLogger appEventsLogger, long j, String str) {
        long j2 = j;
        if (isColdLaunch() || j2 - this.lastActivateEventLoggedTime > APP_ACTIVATE_SUPPRESSION_PERIOD_IN_MILLISECONDS) {
            Bundle bundle = new Bundle();
            bundle.putString(AppEventsConstants.EVENT_PARAM_SOURCE_APPLICATION, str);
            appEventsLogger.logEvent(AppEventsConstants.EVENT_NAME_ACTIVATED_APP, bundle);
            this.lastActivateEventLoggedTime = j2;
        }
        if (this.isAppActive) {
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Resume for active app");
            return;
        }
        long j3 = wasSuspendedEver() ? j2 - this.lastSuspendTime : 0;
        if (j3 < 0) {
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Clock skew detected");
            j3 = 0;
        }
        if (j3 > 60000) {
            logAppDeactivatedEvent(appEventsLogger, j3);
        } else if (j3 > INTERRUPTION_THRESHOLD_MILLISECONDS) {
            this.interruptionCount++;
        }
        if (this.interruptionCount == 0) {
            this.firstOpenSourceApplication = str;
        }
        this.lastResumeTime = j2;
        this.isAppActive = true;
    }

    /* access modifiers changed from: package-private */
    public void onSuspend(AppEventsLogger appEventsLogger, long j) {
        if (!this.isAppActive) {
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Suspend for inactive app");
            return;
        }
        long j2 = j;
        long j3 = j2 - this.lastResumeTime;
        if (j3 < 0) {
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Clock skew detected");
            j3 = 0;
        }
        this.millisecondsSpentInSession += j3;
        this.lastSuspendTime = j2;
        this.isAppActive = false;
    }
}
