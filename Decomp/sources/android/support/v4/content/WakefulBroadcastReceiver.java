package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.util.SparseArray;

public abstract class WakefulBroadcastReceiver extends BroadcastReceiver {
    private static final String EXTRA_WAKE_LOCK_ID = "android.support.content.wakelockid";
    private static final SparseArray<PowerManager.WakeLock> mActiveWakeLocks = new SparseArray<>();
    private static int mNextId = 1;

    public static boolean completeWakefulIntent(Intent intent) {
        int intExtra = intent.getIntExtra(EXTRA_WAKE_LOCK_ID, 0);
        if (intExtra == 0) {
            return false;
        }
        synchronized (mActiveWakeLocks) {
            PowerManager.WakeLock wakeLock = mActiveWakeLocks.get(intExtra);
            if (wakeLock != null) {
                wakeLock.release();
                mActiveWakeLocks.remove(intExtra);
                return true;
            }
            Log.w("WakefulBroadcastReceiver", "No active wake lock id #" + intExtra);
            return true;
        }
    }

    public static ComponentName startWakefulService(Context context, Intent intent) {
        ComponentName startService;
        synchronized (mActiveWakeLocks) {
            int i = mNextId;
            mNextId++;
            if (mNextId <= 0) {
                mNextId = 1;
            }
            intent.putExtra(EXTRA_WAKE_LOCK_ID, i);
            startService = context.startService(intent);
            if (startService == null) {
                startService = null;
            } else {
                PowerManager.WakeLock newWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, "wake:" + startService.flattenToShortString());
                newWakeLock.setReferenceCounted(false);
                newWakeLock.acquire(60000);
                mActiveWakeLocks.put(i, newWakeLock);
            }
        }
        return startService;
    }
}
