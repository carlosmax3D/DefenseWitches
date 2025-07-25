package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.AppEventListener;

public final class ah {
    private AppEventListener eI;
    private String eK;
    private final ba eW = new ba();
    private ac eX;
    private AdListener ev;
    private final Context mContext;

    public ah(Context context) {
        this.mContext = context;
    }

    private void j(String str) throws RemoteException {
        if (this.eK == null) {
            k(str);
        }
        this.eX = u.a(this.mContext, new x(), this.eK, this.eW);
        if (this.ev != null) {
            this.eX.a((ab) new t(this.ev));
        }
        if (this.eI != null) {
            this.eX.a((ae) new z(this.eI));
        }
    }

    private void k(String str) {
        if (this.eX == null) {
            throw new IllegalStateException("The ad unit ID must be set on InterstitialAd before " + str + " is called.");
        }
    }

    public void a(af afVar) {
        try {
            if (this.eX == null) {
                j("loadAd");
            }
            if (this.eX.a(new v(this.mContext, afVar))) {
                this.eW.c(afVar.R());
            }
        } catch (RemoteException e) {
            ct.b("Failed to load ad.", e);
        }
    }

    public AdListener getAdListener() {
        return this.ev;
    }

    public String getAdUnitId() {
        return this.eK;
    }

    public AppEventListener getAppEventListener() {
        return this.eI;
    }

    public boolean isLoaded() {
        try {
            if (this.eX == null) {
                return false;
            }
            return this.eX.isReady();
        } catch (RemoteException e) {
            ct.b("Failed to check if ad is ready.", e);
            return false;
        }
    }

    public void setAdListener(AdListener adListener) {
        try {
            this.ev = adListener;
            if (this.eX != null) {
                this.eX.a((ab) adListener != null ? new t(adListener) : null);
            }
        } catch (RemoteException e) {
            ct.b("Failed to set the AdListener.", e);
        }
    }

    public void setAdUnitId(String str) {
        if (this.eK != null) {
            throw new IllegalStateException("The ad unit ID can only be set once on InterstitialAd.");
        }
        this.eK = str;
    }

    public void setAppEventListener(AppEventListener appEventListener) {
        try {
            this.eI = appEventListener;
            if (this.eX != null) {
                this.eX.a((ae) appEventListener != null ? new z(appEventListener) : null);
            }
        } catch (RemoteException e) {
            ct.b("Failed to set the AppEventListener.", e);
        }
    }

    public void show() {
        try {
            k("show");
            this.eX.showInterstitial();
        } catch (RemoteException e) {
            ct.b("Failed to show interstitial.", e);
        }
    }
}
