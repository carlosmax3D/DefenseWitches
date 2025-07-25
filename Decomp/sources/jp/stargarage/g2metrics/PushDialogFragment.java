package jp.stargarage.g2metrics;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;

public class PushDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
    protected IPushDialogCallback listener;

    /* access modifiers changed from: protected */
    public Dialog generatePushDialog() {
        PackageManager packageManager = getActivity().getPackageManager();
        String string = getArguments().getString("title");
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getActivity().getPackageName(), 0);
            if (string == null || string.length() == 0) {
                string = packageManager.getApplicationLabel(applicationInfo).toString();
            }
            AlertDialog create = new AlertDialog.Builder(getActivity()).setIcon(applicationInfo.icon).setTitle(string).setMessage(getArguments().getString("message")).setPositiveButton("開く", this).setNegativeButton("閉じる", this).create();
            create.setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (i != 4 || PushDialogFragment.this.listener == null) {
                        return false;
                    }
                    PushDialogFragment.this.listener.onClickNegative(dialogInterface);
                    return false;
                }
            });
            create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            create.setCanceledOnTouchOutside(false);
            return create;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof PushDialogActivity) {
            this.listener = (IPushDialogCallback) activity;
        }
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case -2:
                if (this.listener != null) {
                    this.listener.onClickNegative(dialogInterface);
                    return;
                }
                return;
            case -1:
                if (this.listener != null) {
                    this.listener.onClickPositive(dialogInterface);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        Dialog generatePushDialog = generatePushDialog();
        return generatePushDialog == null ? super.onCreateDialog(bundle) : generatePushDialog;
    }
}
