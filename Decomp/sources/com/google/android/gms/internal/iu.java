package com.google.android.gms.internal;

import android.accounts.Account;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.dw;
import com.google.android.gms.internal.is;
import com.google.android.gms.internal.it;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.NotifyTransactionStatusRequest;
import com.google.android.gms.wallet.WalletConstants;

public class iu extends dw<is> {
    private final int Hi;
    /* access modifiers changed from: private */
    public final Activity gs;
    private final String jG;
    private final int mTheme;

    final class a extends it.a {
        private final int oZ;

        public a(int i) {
            this.oZ = i;
        }

        public void a(int i, FullWallet fullWallet, Bundle bundle) {
            int i2;
            Intent intent;
            PendingIntent pendingIntent = null;
            if (bundle != null) {
                pendingIntent = (PendingIntent) bundle.getParcelable("com.google.android.gms.wallet.EXTRA_PENDING_INTENT");
            }
            ConnectionResult connectionResult = new ConnectionResult(i, pendingIntent);
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(iu.this.gs, this.oZ);
                } catch (IntentSender.SendIntentException e) {
                    Log.w("WalletClientImpl", "Exception starting pending intent", e);
                }
            } else {
                if (connectionResult.isSuccess()) {
                    i2 = -1;
                    intent = new Intent();
                    intent.putExtra(WalletConstants.EXTRA_FULL_WALLET, fullWallet);
                } else {
                    i2 = i == 408 ? 0 : 1;
                    intent = new Intent();
                    intent.putExtra(WalletConstants.EXTRA_ERROR_CODE, i);
                }
                PendingIntent createPendingResult = iu.this.gs.createPendingResult(this.oZ, intent, 1073741824);
                if (createPendingResult == null) {
                    Log.w("WalletClientImpl", "Null pending result returned for onFullWalletLoaded");
                    return;
                }
                try {
                    createPendingResult.send(i2);
                } catch (PendingIntent.CanceledException e2) {
                    Log.w("WalletClientImpl", "Exception setting pending result", e2);
                }
            }
        }

        public void a(int i, MaskedWallet maskedWallet, Bundle bundle) {
            int i2;
            Intent intent;
            PendingIntent pendingIntent = null;
            if (bundle != null) {
                pendingIntent = (PendingIntent) bundle.getParcelable("com.google.android.gms.wallet.EXTRA_PENDING_INTENT");
            }
            ConnectionResult connectionResult = new ConnectionResult(i, pendingIntent);
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(iu.this.gs, this.oZ);
                } catch (IntentSender.SendIntentException e) {
                    Log.w("WalletClientImpl", "Exception starting pending intent", e);
                }
            } else {
                if (connectionResult.isSuccess()) {
                    i2 = -1;
                    intent = new Intent();
                    intent.putExtra(WalletConstants.EXTRA_MASKED_WALLET, maskedWallet);
                } else {
                    i2 = i == 408 ? 0 : 1;
                    intent = new Intent();
                    intent.putExtra(WalletConstants.EXTRA_ERROR_CODE, i);
                }
                PendingIntent createPendingResult = iu.this.gs.createPendingResult(this.oZ, intent, 1073741824);
                if (createPendingResult == null) {
                    Log.w("WalletClientImpl", "Null pending result returned for onMaskedWalletLoaded");
                    return;
                }
                try {
                    createPendingResult.send(i2);
                } catch (PendingIntent.CanceledException e2) {
                    Log.w("WalletClientImpl", "Exception setting pending result", e2);
                }
            }
        }

        public void a(int i, boolean z, Bundle bundle) {
            Intent intent = new Intent();
            intent.putExtra(WalletConstants.EXTRA_IS_USER_PREAUTHORIZED, z);
            PendingIntent createPendingResult = iu.this.gs.createPendingResult(this.oZ, intent, 1073741824);
            if (createPendingResult == null) {
                Log.w("WalletClientImpl", "Null pending result returned for onPreAuthorizationDetermined");
                return;
            }
            try {
                createPendingResult.send(-1);
            } catch (PendingIntent.CanceledException e) {
                Log.w("WalletClientImpl", "Exception setting pending result", e);
            }
        }
    }

    @Deprecated
    public iu(Activity activity, GooglePlayServicesClient.ConnectionCallbacks connectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener onConnectionFailedListener, int i, String str, int i2) {
        this(activity, (GoogleApiClient.ConnectionCallbacks) new dw.c(connectionCallbacks), (GoogleApiClient.OnConnectionFailedListener) new dw.g(onConnectionFailedListener), i, str, i2);
    }

    public iu(Activity activity, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, int i, String str, int i2) {
        super((Context) activity, connectionCallbacks, onConnectionFailedListener, new String[0]);
        this.gs = activity;
        this.Hi = i;
        this.jG = str;
        this.mTheme = i2;
    }

    private Bundle fT() {
        Bundle bundle = new Bundle();
        bundle.putInt("com.google.android.gms.wallet.EXTRA_ENVIRONMENT", this.Hi);
        bundle.putString("androidPackageName", this.gs.getPackageName());
        if (!TextUtils.isEmpty(this.jG)) {
            bundle.putParcelable("com.google.android.gms.wallet.EXTRA_BUYER_ACCOUNT", new Account(this.jG, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE));
        }
        bundle.putInt("com.google.android.gms.wallet.EXTRA_THEME", this.mTheme);
        return bundle;
    }

    /* access modifiers changed from: protected */
    public void a(ec ecVar, dw.e eVar) throws RemoteException {
        ecVar.a(eVar, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE);
    }

    /* access modifiers changed from: protected */
    /* renamed from: aB */
    public is p(IBinder iBinder) {
        return is.a.az(iBinder);
    }

    /* access modifiers changed from: protected */
    public String am() {
        return "com.google.android.gms.wallet.service.BIND";
    }

    /* access modifiers changed from: protected */
    public String an() {
        return "com.google.android.gms.wallet.internal.IOwService";
    }

    public void changeMaskedWallet(String str, String str2, int i) {
        Bundle fT = fT();
        a aVar = new a(i);
        try {
            ((is) bQ()).a(str, str2, fT, aVar);
        } catch (RemoteException e) {
            Log.e("WalletClientImpl", "RemoteException changing masked wallet", e);
            aVar.a(8, (MaskedWallet) null, (Bundle) null);
        }
    }

    public void checkForPreAuthorization(int i) {
        Bundle fT = fT();
        a aVar = new a(i);
        try {
            ((is) bQ()).a(fT, (it) aVar);
        } catch (RemoteException e) {
            Log.e("WalletClientImpl", "RemoteException during checkForPreAuthorization", e);
            aVar.a(8, false, (Bundle) null);
        }
    }

    public void loadFullWallet(FullWalletRequest fullWalletRequest, int i) {
        a aVar = new a(i);
        try {
            ((is) bQ()).a(fullWalletRequest, fT(), (it) aVar);
        } catch (RemoteException e) {
            Log.e("WalletClientImpl", "RemoteException getting full wallet", e);
            aVar.a(8, (FullWallet) null, (Bundle) null);
        }
    }

    public void loadMaskedWallet(MaskedWalletRequest maskedWalletRequest, int i) {
        Bundle fT = fT();
        a aVar = new a(i);
        try {
            ((is) bQ()).a(maskedWalletRequest, fT, (it) aVar);
        } catch (RemoteException e) {
            Log.e("WalletClientImpl", "RemoteException getting masked wallet", e);
            aVar.a(8, (MaskedWallet) null, (Bundle) null);
        }
    }

    public void notifyTransactionStatus(NotifyTransactionStatusRequest notifyTransactionStatusRequest) {
        try {
            ((is) bQ()).a(notifyTransactionStatusRequest, fT());
        } catch (RemoteException e) {
        }
    }
}
