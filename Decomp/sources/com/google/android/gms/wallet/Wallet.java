package com.google.android.gms.wallet;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;
import com.google.android.gms.internal.dt;
import com.google.android.gms.internal.eg;
import com.google.android.gms.internal.iu;

public final class Wallet {
    public static final Api API = new Api(jO, new Scope[0]);
    static final Api.b<iu> jO = new Api.b<iu>() {
        public int getPriority() {
            return Integer.MAX_VALUE;
        }

        /* renamed from: h */
        public iu b(Context context, dt dtVar, GoogleApiClient.ApiOptions apiOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            eg.b(context instanceof Activity, (Object) "An Activity must be used for Wallet APIs");
            Activity activity = (Activity) context;
            eg.b(apiOptions == null || (apiOptions instanceof WalletOptions), (Object) "WalletOptions must be used for Wallet APIs");
            WalletOptions walletOptions = apiOptions != null ? (WalletOptions) apiOptions : new WalletOptions();
            return new iu(activity, connectionCallbacks, onConnectionFailedListener, walletOptions.environment, dtVar.getAccountName(), walletOptions.theme);
        }
    };

    public static final class WalletOptions implements GoogleApiClient.ApiOptions {
        public final int environment;
        public final int theme;

        public static final class Builder {
            /* access modifiers changed from: private */
            public int Hi = 0;
            /* access modifiers changed from: private */
            public int mTheme = 0;

            public WalletOptions build() {
                return new WalletOptions(this);
            }

            public Builder setEnvironment(int i) {
                if (i == 0 || i == 2 || i == 1) {
                    this.Hi = i;
                    return this;
                }
                throw new IllegalArgumentException(String.format("Invalid environment value %d", new Object[]{Integer.valueOf(i)}));
            }

            public Builder setTheme(int i) {
                if (i == 0 || i == 1) {
                    this.mTheme = i;
                    return this;
                }
                throw new IllegalArgumentException(String.format("Invalid theme value %d", new Object[]{Integer.valueOf(i)}));
            }
        }

        private WalletOptions() {
            this(new Builder());
        }

        private WalletOptions(Builder builder) {
            this.environment = builder.Hi;
            this.theme = builder.mTheme;
        }
    }

    private static abstract class a extends a.C0001a<Status, iu> {
        public a() {
            super(Wallet.jO);
        }

        /* renamed from: g */
        public Status e(Status status) {
            return status;
        }
    }

    private Wallet() {
    }

    public static void changeMaskedWallet(GoogleApiClient googleApiClient, final String str, final String str2, final int i) {
        googleApiClient.a(new a() {
            /* access modifiers changed from: protected */
            public void a(iu iuVar) {
                iuVar.changeMaskedWallet(str, str2, i);
                a(Status.nA);
            }
        });
    }

    public static void checkForPreAuthorization(GoogleApiClient googleApiClient, final int i) {
        googleApiClient.a(new a() {
            /* access modifiers changed from: protected */
            public void a(iu iuVar) {
                iuVar.checkForPreAuthorization(i);
                a(Status.nA);
            }
        });
    }

    public static void loadFullWallet(GoogleApiClient googleApiClient, final FullWalletRequest fullWalletRequest, final int i) {
        googleApiClient.a(new a() {
            /* access modifiers changed from: protected */
            public void a(iu iuVar) {
                iuVar.loadFullWallet(fullWalletRequest, i);
                a(Status.nA);
            }
        });
    }

    public static void loadMaskedWallet(GoogleApiClient googleApiClient, final MaskedWalletRequest maskedWalletRequest, final int i) {
        googleApiClient.a(new a() {
            /* access modifiers changed from: protected */
            public void a(iu iuVar) {
                iuVar.loadMaskedWallet(maskedWalletRequest, i);
                a(Status.nA);
            }
        });
    }

    public static void notifyTransactionStatus(GoogleApiClient googleApiClient, final NotifyTransactionStatusRequest notifyTransactionStatusRequest) {
        googleApiClient.a(new a() {
            /* access modifiers changed from: protected */
            public void a(iu iuVar) {
                iuVar.notifyTransactionStatus(notifyTransactionStatusRequest);
                a(Status.nA);
            }
        });
    }
}
