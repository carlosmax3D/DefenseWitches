package jp.stargarage.g2metrics;

import android.R;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.fragment.app.FragmentActivity;

import com.ansca.corona.events.NotificationReceivedTask;
import com.google.android.gms.drive.DriveFile;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class PushDialogActivity extends FragmentActivity implements IPushDialogCallback {
    private void openNotification() {
        try {
            Bundle extras = getIntent().getExtras();
            EventValueReadNotification eventValueReadNotification = new EventValueReadNotification();
            eventValueReadNotification.f3199ab = Integer.parseInt(extras.getString("ab"));
            eventValueReadNotification.history_id = extras.getString("history_id");
            eventValueReadNotification.segment_id = extras.getString("segment_id");
            G2Metrics.getInstance().addNotificationLog("ps02", eventValueReadNotification);
            Intent flags = getPackageManager().getLaunchIntentForPackage(getPackageName()).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            flags.putExtra("g2m.PUSH_PAYLOAD", extras.getString("g2m.PUSH_PAYLOAD"));
            startActivity(flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setKeepScreenOnTimeout(long j) {
        new Handler().postDelayed(new Runnable() { // from class: jp.stargarage.g2metrics.PushDialogActivity.3
            @Override // java.lang.Runnable
            public void run() {
                PushDialogActivity.this.getWindow().clearFlags(128);
            }
        }, j);
    }

    private void showDialog() {
        manageKeyguard();
        final PushDialogFragment pushDialogFragment = new PushDialogFragment();
        pushDialogFragment.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putString("title", getIntent().getExtras().getString("title"));
        bundle.putString("message", getIntent().getExtras().getString("message"));
        pushDialogFragment.setArguments(bundle);
        if (isFinishing()) {
            return;
        }
        if (!((KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE)).inKeyguardRestrictedInputMode()) {
            runOnUiThread(new Runnable() { // from class: jp.stargarage.g2metrics.PushDialogActivity.2
                @Override // java.lang.Runnable
                public void run() {
                    Toast toastMakeText = Toast.makeText(PushDialogActivity.this.getApplicationContext(), PushDialogActivity.this.getIntent().getExtras().getString("message"), Toast.LENGTH_SHORT);
                    toastMakeText.setGravity(49, 0, 0);
                    toastMakeText.show();
                }
            });
            finish();
        } else {
            getWindow().addFlags(128);
            runOnUiThread(new Runnable() { // from class: jp.stargarage.g2metrics.PushDialogActivity.1
                @Override // java.lang.Runnable
                public void run() {
                    pushDialogFragment.show(PushDialogActivity.this.getSupportFragmentManager(), getClass().getName());
                }
            });
            setKeepScreenOnTimeout(10000L);
        }
    }

    protected void manageKeyguard() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.inKeyguardRestrictedInputMode()) {
            if (Build.VERSION.SDK_INT < 16) {
                getWindow().addFlags(4718592);
            } else if (keyguardManager.isKeyguardSecure()) {
                getWindow().addFlags(AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END);
            } else if (keyguardManager.isKeyguardLocked()) {
                getWindow().addFlags(4194304);
            }
        }
    }

    @Override // jp.stargarage.g2metrics.IPushDialogCallback
    public void onClickNegative(DialogInterface dialogInterface) {
        dialogInterface.dismiss();
        finish();
    }

    @Override // jp.stargarage.g2metrics.IPushDialogCallback
    public void onClickPositive(DialogInterface dialogInterface) {
        dialogInterface.dismiss();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancel("G2Metrics" + getPackageName(), 1);
        }
        openNotification();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setTheme(R.style.Theme_Translucent);
        if (getIntent().getExtras().getBoolean("showG2MPushDialog")) {
            showDialog();
        } else {
            openNotification();
            finish();
        }
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        getWindow().clearFlags(4718592);
        super.onDestroy();
    }
}
