package jp.stargarage.g2metrics;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.DialogFragment;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class PushDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
    protected IPushDialogCallback listener;

    protected Dialog generatePushDialog() throws PackageManager.NameNotFoundException {
        PackageManager packageManager = getActivity().getPackageManager();
        String string = getArguments().getString("title");
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getActivity().getPackageName(), 0);
            if (string == null || string.length() == 0) {
                string = packageManager.getApplicationLabel(applicationInfo).toString();
            }
            AlertDialog alertDialogCreate = new AlertDialog.Builder(getActivity()).setIcon(applicationInfo.icon).setTitle(string).setMessage(getArguments().getString("message")).setPositiveButton("開く", this).setNegativeButton("閉じる", this).create();
            alertDialogCreate.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: jp.stargarage.g2metrics.PushDialogFragment.1
                @Override // android.content.DialogInterface.OnKeyListener
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (i != 4 || PushDialogFragment.this.listener == null) {
                        return false;
                    }
                    PushDialogFragment.this.listener.onClickNegative(dialogInterface);
                    return false;
                }
            });
            alertDialogCreate.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            alertDialogCreate.setCanceledOnTouchOutside(false);
            return alertDialogCreate;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.support.v4.app.DialogFragment, android.support.v4.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof PushDialogActivity) {
            this.listener = (IPushDialogCallback) activity;
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case -2:
                if (this.listener != null) {
                    this.listener.onClickNegative(dialogInterface);
                    break;
                }
                break;
            case -1:
                if (this.listener != null) {
                    this.listener.onClickPositive(dialogInterface);
                    break;
                }
                break;
        }
    }

    @Override // android.support.v4.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        Dialog dialogGeneratePushDialog = null;
        try {
            dialogGeneratePushDialog = generatePushDialog();
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        return dialogGeneratePushDialog == null ? super.onCreateDialog(bundle) : dialogGeneratePushDialog;
    }
}
