package com.google.android.gms.ads;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.internal.br;
import com.google.android.gms.internal.bs;
import com.google.android.gms.internal.ct;

public final class AdActivity extends Activity {
    public static final String CLASS_NAME = "com.google.android.gms.ads.AdActivity";
    public static final String SIMPLE_CLASS_NAME = "AdActivity";
    private bs dV;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.dV = br.a(this);
        if (this.dV == null) {
            ct.v("Could not create ad overlay.");
            finish();
            return;
        }
        try {
            this.dV.onCreate(bundle);
        } catch (RemoteException e) {
            ct.b("Could not forward onCreate to ad overlay:", e);
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        try {
            if (this.dV != null) {
                this.dV.onDestroy();
            }
        } catch (RemoteException e) {
            ct.b("Could not forward onDestroy to ad overlay:", e);
        }
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        try {
            if (this.dV != null) {
                this.dV.onPause();
            }
        } catch (RemoteException e) {
            ct.b("Could not forward onPause to ad overlay:", e);
            finish();
        }
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        super.onRestart();
        try {
            if (this.dV != null) {
                this.dV.onRestart();
            }
        } catch (RemoteException e) {
            ct.b("Could not forward onRestart to ad overlay:", e);
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        try {
            if (this.dV != null) {
                this.dV.onResume();
            }
        } catch (RemoteException e) {
            ct.b("Could not forward onResume to ad overlay:", e);
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        try {
            if (this.dV != null) {
                this.dV.onSaveInstanceState(bundle);
            }
        } catch (RemoteException e) {
            ct.b("Could not forward onSaveInstanceState to ad overlay:", e);
            finish();
        }
        super.onSaveInstanceState(bundle);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        try {
            if (this.dV != null) {
                this.dV.onStart();
            }
        } catch (RemoteException e) {
            ct.b("Could not forward onStart to ad overlay:", e);
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        try {
            if (this.dV != null) {
                this.dV.onStop();
            }
        } catch (RemoteException e) {
            ct.b("Could not forward onStop to ad overlay:", e);
            finish();
        }
        super.onStop();
    }
}
