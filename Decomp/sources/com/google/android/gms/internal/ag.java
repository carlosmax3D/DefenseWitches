package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.dynamic.b;
import com.google.android.gms.dynamic.c;

public final class ag {
    private AppEventListener eI;
    private AdSize[] eJ;
    private String eK;
    private final ba eW = new ba();
    private ac eX;
    private ViewGroup eY;
    private AdListener ev;

    public ag(ViewGroup viewGroup) {
        this.eY = viewGroup;
    }

    public ag(ViewGroup viewGroup, AttributeSet attributeSet, boolean z) {
        this.eY = viewGroup;
        Context context = viewGroup.getContext();
        try {
            aa aaVar = new aa(context, attributeSet);
            this.eJ = aaVar.c(z);
            this.eK = aaVar.getAdUnitId();
            if (viewGroup.isInEditMode()) {
                cs.a(viewGroup, new x(context, this.eJ[0]), "Ads by Google");
            }
        } catch (IllegalArgumentException e) {
            cs.a(viewGroup, new x(context, AdSize.BANNER), e.getMessage(), e.getMessage());
        }
    }

    private void T() {
        try {
            b x = this.eX.x();
            if (x != null) {
                this.eY.addView((View) c.b(x));
            }
        } catch (RemoteException e) {
            ct.b("Failed to get an ad frame.", e);
        }
    }

    private void U() throws RemoteException {
        if ((this.eJ == null || this.eK == null) && this.eX == null) {
            throw new IllegalStateException("The ad size and ad unit ID must be set before loadAd is called.");
        }
        Context context = this.eY.getContext();
        this.eX = u.a(context, new x(context, this.eJ), this.eK, this.eW);
        if (this.ev != null) {
            this.eX.a((ab) new t(this.ev));
        }
        if (this.eI != null) {
            this.eX.a((ae) new z(this.eI));
        }
        T();
    }

    public void a(af afVar) {
        try {
            if (this.eX == null) {
                U();
            }
            if (this.eX.a(new v(this.eY.getContext(), afVar))) {
                this.eW.c(afVar.R());
            }
        } catch (RemoteException e) {
            ct.b("Failed to load ad.", e);
        }
    }

    public void a(AdSize... adSizeArr) {
        this.eJ = adSizeArr;
        try {
            if (this.eX != null) {
                this.eX.a(new x(this.eY.getContext(), this.eJ));
            }
        } catch (RemoteException e) {
            ct.b("Failed to set the ad size.", e);
        }
        this.eY.requestLayout();
    }

    public void destroy() {
        try {
            if (this.eX != null) {
                this.eX.destroy();
            }
        } catch (RemoteException e) {
            ct.b("Failed to destroy AdView.", e);
        }
    }

    public AdListener getAdListener() {
        return this.ev;
    }

    public AdSize getAdSize() {
        try {
            if (this.eX != null) {
                return this.eX.y().P();
            }
        } catch (RemoteException e) {
            ct.b("Failed to get the current AdSize.", e);
        }
        if (this.eJ != null) {
            return this.eJ[0];
        }
        return null;
    }

    public AdSize[] getAdSizes() {
        return this.eJ;
    }

    public String getAdUnitId() {
        return this.eK;
    }

    public AppEventListener getAppEventListener() {
        return this.eI;
    }

    public void pause() {
        try {
            if (this.eX != null) {
                this.eX.pause();
            }
        } catch (RemoteException e) {
            ct.b("Failed to call pause.", e);
        }
    }

    public void recordManualImpression() {
        try {
            this.eX.H();
        } catch (RemoteException e) {
            ct.b("Failed to record impression.", e);
        }
    }

    public void resume() {
        try {
            if (this.eX != null) {
                this.eX.resume();
            }
        } catch (RemoteException e) {
            ct.b("Failed to call resume.", e);
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

    public void setAdSizes(AdSize... adSizeArr) {
        if (this.eJ != null) {
            throw new IllegalStateException("The ad size can only be set once on AdView.");
        }
        a(adSizeArr);
    }

    public void setAdUnitId(String str) {
        if (this.eK != null) {
            throw new IllegalStateException("The ad unit ID can only be set once on AdView.");
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
}
