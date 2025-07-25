package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.ads.AdRequest;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.google.ads.mediation.MediationServerParameters;
import com.google.ads.mediation.NetworkExtras;

public final class bf<NETWORK_EXTRAS extends NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> implements MediationBannerListener, MediationInterstitialListener {
    /* access modifiers changed from: private */
    public final bd gi;

    public bf(bd bdVar) {
        this.gi = bdVar;
    }

    public void onClick(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        ct.r("Adapter called onClick.");
        if (!cs.ay()) {
            ct.v("onClick must be called on the main UI thread.");
            cs.iI.post(new Runnable() {
                public void run() {
                    try {
                        bf.this.gi.w();
                    } catch (RemoteException e) {
                        ct.b("Could not call onAdClicked.", e);
                    }
                }
            });
            return;
        }
        try {
            this.gi.w();
        } catch (RemoteException e) {
            ct.b("Could not call onAdClicked.", e);
        }
    }

    public void onDismissScreen(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        ct.r("Adapter called onDismissScreen.");
        if (!cs.ay()) {
            ct.v("onDismissScreen must be called on the main UI thread.");
            cs.iI.post(new Runnable() {
                public void run() {
                    try {
                        bf.this.gi.onAdClosed();
                    } catch (RemoteException e) {
                        ct.b("Could not call onAdClosed.", e);
                    }
                }
            });
            return;
        }
        try {
            this.gi.onAdClosed();
        } catch (RemoteException e) {
            ct.b("Could not call onAdClosed.", e);
        }
    }

    public void onDismissScreen(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        ct.r("Adapter called onDismissScreen.");
        if (!cs.ay()) {
            ct.v("onDismissScreen must be called on the main UI thread.");
            cs.iI.post(new Runnable() {
                public void run() {
                    try {
                        bf.this.gi.onAdClosed();
                    } catch (RemoteException e) {
                        ct.b("Could not call onAdClosed.", e);
                    }
                }
            });
            return;
        }
        try {
            this.gi.onAdClosed();
        } catch (RemoteException e) {
            ct.b("Could not call onAdClosed.", e);
        }
    }

    public void onFailedToReceiveAd(MediationBannerAdapter<?, ?> mediationBannerAdapter, final AdRequest.ErrorCode errorCode) {
        ct.r("Adapter called onFailedToReceiveAd with error. " + errorCode);
        if (!cs.ay()) {
            ct.v("onFailedToReceiveAd must be called on the main UI thread.");
            cs.iI.post(new Runnable() {
                public void run() {
                    try {
                        bf.this.gi.onAdFailedToLoad(bg.a(errorCode));
                    } catch (RemoteException e) {
                        ct.b("Could not call onAdFailedToLoad.", e);
                    }
                }
            });
            return;
        }
        try {
            this.gi.onAdFailedToLoad(bg.a(errorCode));
        } catch (RemoteException e) {
            ct.b("Could not call onAdFailedToLoad.", e);
        }
    }

    public void onFailedToReceiveAd(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter, final AdRequest.ErrorCode errorCode) {
        ct.r("Adapter called onFailedToReceiveAd with error " + errorCode + ".");
        if (!cs.ay()) {
            ct.v("onFailedToReceiveAd must be called on the main UI thread.");
            cs.iI.post(new Runnable() {
                public void run() {
                    try {
                        bf.this.gi.onAdFailedToLoad(bg.a(errorCode));
                    } catch (RemoteException e) {
                        ct.b("Could not call onAdFailedToLoad.", e);
                    }
                }
            });
            return;
        }
        try {
            this.gi.onAdFailedToLoad(bg.a(errorCode));
        } catch (RemoteException e) {
            ct.b("Could not call onAdFailedToLoad.", e);
        }
    }

    public void onLeaveApplication(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        ct.r("Adapter called onLeaveApplication.");
        if (!cs.ay()) {
            ct.v("onLeaveApplication must be called on the main UI thread.");
            cs.iI.post(new Runnable() {
                public void run() {
                    try {
                        bf.this.gi.onAdLeftApplication();
                    } catch (RemoteException e) {
                        ct.b("Could not call onAdLeftApplication.", e);
                    }
                }
            });
            return;
        }
        try {
            this.gi.onAdLeftApplication();
        } catch (RemoteException e) {
            ct.b("Could not call onAdLeftApplication.", e);
        }
    }

    public void onLeaveApplication(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        ct.r("Adapter called onLeaveApplication.");
        if (!cs.ay()) {
            ct.v("onLeaveApplication must be called on the main UI thread.");
            cs.iI.post(new Runnable() {
                public void run() {
                    try {
                        bf.this.gi.onAdLeftApplication();
                    } catch (RemoteException e) {
                        ct.b("Could not call onAdLeftApplication.", e);
                    }
                }
            });
            return;
        }
        try {
            this.gi.onAdLeftApplication();
        } catch (RemoteException e) {
            ct.b("Could not call onAdLeftApplication.", e);
        }
    }

    public void onPresentScreen(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        ct.r("Adapter called onPresentScreen.");
        if (!cs.ay()) {
            ct.v("onPresentScreen must be called on the main UI thread.");
            cs.iI.post(new Runnable() {
                public void run() {
                    try {
                        bf.this.gi.onAdOpened();
                    } catch (RemoteException e) {
                        ct.b("Could not call onAdOpened.", e);
                    }
                }
            });
            return;
        }
        try {
            this.gi.onAdOpened();
        } catch (RemoteException e) {
            ct.b("Could not call onAdOpened.", e);
        }
    }

    public void onPresentScreen(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        ct.r("Adapter called onPresentScreen.");
        if (!cs.ay()) {
            ct.v("onPresentScreen must be called on the main UI thread.");
            cs.iI.post(new Runnable() {
                public void run() {
                    try {
                        bf.this.gi.onAdOpened();
                    } catch (RemoteException e) {
                        ct.b("Could not call onAdOpened.", e);
                    }
                }
            });
            return;
        }
        try {
            this.gi.onAdOpened();
        } catch (RemoteException e) {
            ct.b("Could not call onAdOpened.", e);
        }
    }

    public void onReceivedAd(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        ct.r("Adapter called onReceivedAd.");
        if (!cs.ay()) {
            ct.v("onReceivedAd must be called on the main UI thread.");
            cs.iI.post(new Runnable() {
                public void run() {
                    try {
                        bf.this.gi.onAdLoaded();
                    } catch (RemoteException e) {
                        ct.b("Could not call onAdLoaded.", e);
                    }
                }
            });
            return;
        }
        try {
            this.gi.onAdLoaded();
        } catch (RemoteException e) {
            ct.b("Could not call onAdLoaded.", e);
        }
    }

    public void onReceivedAd(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        ct.r("Adapter called onReceivedAd.");
        if (!cs.ay()) {
            ct.v("onReceivedAd must be called on the main UI thread.");
            cs.iI.post(new Runnable() {
                public void run() {
                    try {
                        bf.this.gi.onAdLoaded();
                    } catch (RemoteException e) {
                        ct.b("Could not call onAdLoaded.", e);
                    }
                }
            });
            return;
        }
        try {
            this.gi.onAdLoaded();
        } catch (RemoteException e) {
            ct.b("Could not call onAdLoaded.", e);
        }
    }
}
