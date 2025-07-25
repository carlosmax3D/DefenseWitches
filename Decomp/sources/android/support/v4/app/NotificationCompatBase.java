package android.support.v4.app;

import android.app.PendingIntent;
import android.os.Bundle;
import android.support.v4.app.RemoteInputCompatBase;

class NotificationCompatBase {

    public static abstract class Action {

        public interface Factory {
            Action build(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInputCompatBase.RemoteInput[] remoteInputArr);

            Action[] newArray(int i);
        }

        /* access modifiers changed from: protected */
        public abstract PendingIntent getActionIntent();

        /* access modifiers changed from: protected */
        public abstract Bundle getExtras();

        /* access modifiers changed from: protected */
        public abstract int getIcon();

        /* access modifiers changed from: protected */
        public abstract RemoteInputCompatBase.RemoteInput[] getRemoteInputs();

        /* access modifiers changed from: protected */
        public abstract CharSequence getTitle();
    }

    public static abstract class UnreadConversation {

        public interface Factory {
            UnreadConversation build(String[] strArr, RemoteInputCompatBase.RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] strArr2, long j);
        }

        /* access modifiers changed from: package-private */
        public abstract long getLatestTimestamp();

        /* access modifiers changed from: package-private */
        public abstract String[] getMessages();

        /* access modifiers changed from: package-private */
        public abstract String getParticipant();

        /* access modifiers changed from: package-private */
        public abstract String[] getParticipants();

        /* access modifiers changed from: package-private */
        public abstract PendingIntent getReadPendingIntent();

        /* access modifiers changed from: package-private */
        public abstract RemoteInputCompatBase.RemoteInput getRemoteInput();

        /* access modifiers changed from: package-private */
        public abstract PendingIntent getReplyPendingIntent();
    }

    NotificationCompatBase() {
    }
}
