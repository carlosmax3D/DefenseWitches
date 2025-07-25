package com.google.android.gms.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

public class du implements DialogInterface.OnClickListener {
    private final Activity gs;
    private final Intent mIntent;
    private final int oZ;

    public du(Activity activity, Intent intent, int i) {
        this.gs = activity;
        this.mIntent = intent;
        this.oZ = i;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        try {
            if (this.mIntent != null) {
                this.gs.startActivityForResult(this.mIntent, this.oZ);
            }
            dialogInterface.dismiss();
        } catch (ActivityNotFoundException e) {
            Log.e("SettingsRedirect", "Can't redirect to app settings for Google Play services");
        }
    }
}
