package jp.stargarage.g2metrics;

import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.widget.Toast;
import com.ansca.corona.events.NotificationReceivedTask;
import com.google.android.gms.drive.DriveFile;

public class PushDialogActivity extends FragmentActivity implements IPushDialogCallback {
    private void openNotification() {
        try {
            Bundle extras = getIntent().getExtras();
            EventValueReadNotification eventValueReadNotification = new EventValueReadNotification();
            eventValueReadNotification.ab = Integer.parseInt(extras.getString("ab"));
            eventValueReadNotification.history_id = extras.getString("history_id");
            eventValueReadNotification.segment_id = extras.getString("segment_id");
            G2Metrics.getInstance().addNotificationLog("ps02", eventValueReadNotification);
            Intent flags = getPackageManager().getLaunchIntentForPackage(getPackageName()).setFlags(DriveFile.MODE_WRITE_ONLY);
            flags.putExtra("g2m.PUSH_PAYLOAD", extras.getString("g2m.PUSH_PAYLOAD"));
            startActivity(flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setKeepScreenOnTimeout(long j) {
        new Handler().postDelayed(new Runnable() {
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
        if (((KeyguardManager) getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            getWindow().addFlags(128);
            runOnUiThread(new Runnable() {
                public void run() {
                    pushDialogFragment.show(PushDialogActivity.this.getSupportFragmentManager(), getClass().getName());
                }
            });
            setKeepScreenOnTimeout(10000);
            return;
        }
        runOnUiThread(new Runnable() {
            public void run() {
                Toast makeText = Toast.makeText(PushDialogActivity.this.getApplicationContext(), PushDialogActivity.this.getIntent().getExtras().getString("message"), 0);
                makeText.setGravity(49, 0, 0);
                makeText.show();
            }
        });
        finish();
    }

    /* access modifiers changed from: protected */
    public void manageKeyguard() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService("keyguard");
        if (!keyguardManager.inKeyguardRestrictedInputMode()) {
            return;
        }
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().addFlags(4718592);
        } else if (keyguardManager.isKeyguardSecure()) {
            getWindow().addFlags(AccessibilityEventCompat.TYPE_GESTURE_DETECTION_END);
        } else if (keyguardManager.isKeyguardLocked()) {
            getWindow().addFlags(4194304);
        }
    }

    public void onClickNegative(DialogInterface dialogInterface) {
        dialogInterface.dismiss();
        finish();
    }

    public void onClickPositive(DialogInterface dialogInterface) {
        dialogInterface.dismiss();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationReceivedTask.NAME);
        if (notificationManager != null) {
            notificationManager.cancel("G2Metrics" + getPackageName(), 1);
        }
        openNotification();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setTheme(16973839);
        if (getIntent().getExtras().getBoolean("showG2MPushDialog")) {
            showDialog();
            return;
        }
        openNotification();
        finish();
    }

    public void onDestroy() {
        getWindow().clearFlags(4718592);
        super.onDestroy();
    }
}
