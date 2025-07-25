package com.facebook.internal;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.WebDialog;

public class FacebookDialogFragment extends DialogFragment {
    public static final String TAG = "FacebookDialogFragment";
    private Dialog dialog;

    /* access modifiers changed from: private */
    public void onCompleteWebDialog(Bundle bundle, FacebookException facebookException) {
        FragmentActivity activity = getActivity();
        activity.setResult(facebookException == null ? -1 : 0, NativeProtocol.createProtocolResultIntent(activity.getIntent(), bundle, facebookException));
        activity.finish();
    }

    /* access modifiers changed from: private */
    public void onCompleteWebFallbackDialog(Bundle bundle) {
        FragmentActivity activity = getActivity();
        Intent intent = new Intent();
        if (bundle == null) {
            bundle = new Bundle();
        }
        intent.putExtras(bundle);
        activity.setResult(-1, intent);
        activity.finish();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.dialog instanceof WebDialog) {
            ((WebDialog) this.dialog).resize();
        }
    }

    public void onCreate(Bundle bundle) {
        WebDialog facebookWebFallbackDialog;
        super.onCreate(bundle);
        if (this.dialog == null) {
            FragmentActivity activity = getActivity();
            Bundle methodArgumentsFromIntent = NativeProtocol.getMethodArgumentsFromIntent(activity.getIntent());
            if (!methodArgumentsFromIntent.getBoolean(NativeProtocol.WEB_DIALOG_IS_FALLBACK, false)) {
                String string = methodArgumentsFromIntent.getString("action");
                Bundle bundle2 = methodArgumentsFromIntent.getBundle(NativeProtocol.WEB_DIALOG_PARAMS);
                if (Utility.isNullOrEmpty(string)) {
                    Utility.logd(TAG, "Cannot start a WebDialog with an empty/missing 'actionName'");
                    activity.finish();
                    return;
                }
                facebookWebFallbackDialog = new WebDialog.Builder(activity, string, bundle2).setOnCompleteListener(new WebDialog.OnCompleteListener() {
                    public void onComplete(Bundle bundle, FacebookException facebookException) {
                        FacebookDialogFragment.this.onCompleteWebDialog(bundle, facebookException);
                    }
                }).build();
            } else {
                String string2 = methodArgumentsFromIntent.getString("url");
                if (Utility.isNullOrEmpty(string2)) {
                    Utility.logd(TAG, "Cannot start a fallback WebDialog with an empty/missing 'url'");
                    activity.finish();
                    return;
                }
                facebookWebFallbackDialog = new FacebookWebFallbackDialog(activity, string2, String.format("fb%s://bridge/", new Object[]{FacebookSdk.getApplicationId()}));
                facebookWebFallbackDialog.setOnCompleteListener(new WebDialog.OnCompleteListener() {
                    public void onComplete(Bundle bundle, FacebookException facebookException) {
                        FacebookDialogFragment.this.onCompleteWebFallbackDialog(bundle);
                    }
                });
            }
            this.dialog = facebookWebFallbackDialog;
        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        return this.dialog;
    }

    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage((Message) null);
        }
        super.onDestroyView();
    }

    public void setDialog(Dialog dialog2) {
        this.dialog = dialog2;
    }
}
