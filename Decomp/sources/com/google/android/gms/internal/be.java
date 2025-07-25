package com.google.android.gms.internal;

import android.app.Activity;
import android.os.RemoteException;
import com.google.ads.mediation.MediationAdapter;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationServerParameters;
import com.google.ads.mediation.NetworkExtras;
import com.google.ads.mediation.admob.AdMobServerParameters;
import com.google.android.gms.dynamic.b;
import com.google.android.gms.dynamic.c;
import com.google.android.gms.internal.bc;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

public final class be<NETWORK_EXTRAS extends NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> extends bc.a {
    private final MediationAdapter<NETWORK_EXTRAS, SERVER_PARAMETERS> gg;
    private final NETWORK_EXTRAS gh;

    public be(MediationAdapter<NETWORK_EXTRAS, SERVER_PARAMETERS> mediationAdapter, NETWORK_EXTRAS network_extras) {
        this.gg = mediationAdapter;
        this.gh = network_extras;
    }

    private SERVER_PARAMETERS a(String str, int i, String str2) throws RemoteException {
        HashMap hashMap;
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                HashMap hashMap2 = new HashMap(jSONObject.length());
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    hashMap2.put(next, jSONObject.getString(next));
                }
                hashMap = hashMap2;
            } catch (Throwable th) {
                ct.b("Could not get MediationServerParameters.", th);
                throw new RemoteException();
            }
        } else {
            hashMap = new HashMap(0);
        }
        Class<SERVER_PARAMETERS> serverParametersType = this.gg.getServerParametersType();
        SERVER_PARAMETERS server_parameters = null;
        if (serverParametersType != null) {
            SERVER_PARAMETERS server_parameters2 = (MediationServerParameters) serverParametersType.newInstance();
            server_parameters2.load(hashMap);
            server_parameters = server_parameters2;
        }
        if (server_parameters instanceof AdMobServerParameters) {
            AdMobServerParameters adMobServerParameters = (AdMobServerParameters) server_parameters;
            adMobServerParameters.adJson = str2;
            adMobServerParameters.tagForChildDirectedTreatment = i;
        }
        return server_parameters;
    }

    public void a(b bVar, v vVar, String str, bd bdVar) throws RemoteException {
        a(bVar, vVar, str, (String) null, bdVar);
    }

    public void a(b bVar, v vVar, String str, String str2, bd bdVar) throws RemoteException {
        if (!(this.gg instanceof MediationInterstitialAdapter)) {
            ct.v("MediationAdapter is not a MediationInterstitialAdapter: " + this.gg.getClass().getCanonicalName());
            throw new RemoteException();
        }
        ct.r("Requesting interstitial ad from adapter.");
        try {
            ((MediationInterstitialAdapter) this.gg).requestInterstitialAd(new bf(bdVar), (Activity) c.b(bVar), a(str, vVar.tagForChildDirectedTreatment, str2), bg.e(vVar), this.gh);
        } catch (Throwable th) {
            ct.b("Could not request interstitial ad from adapter.", th);
            throw new RemoteException();
        }
    }

    public void a(b bVar, x xVar, v vVar, String str, bd bdVar) throws RemoteException {
        a(bVar, xVar, vVar, str, (String) null, bdVar);
    }

    public void a(b bVar, x xVar, v vVar, String str, String str2, bd bdVar) throws RemoteException {
        if (!(this.gg instanceof MediationBannerAdapter)) {
            ct.v("MediationAdapter is not a MediationBannerAdapter: " + this.gg.getClass().getCanonicalName());
            throw new RemoteException();
        }
        ct.r("Requesting banner ad from adapter.");
        try {
            ((MediationBannerAdapter) this.gg).requestBannerAd(new bf(bdVar), (Activity) c.b(bVar), a(str, vVar.tagForChildDirectedTreatment, str2), bg.b(xVar), bg.e(vVar), this.gh);
        } catch (Throwable th) {
            ct.b("Could not request banner ad from adapter.", th);
            throw new RemoteException();
        }
    }

    public void destroy() throws RemoteException {
        try {
            this.gg.destroy();
        } catch (Throwable th) {
            ct.b("Could not destroy adapter.", th);
            throw new RemoteException();
        }
    }

    public b getView() throws RemoteException {
        if (!(this.gg instanceof MediationBannerAdapter)) {
            ct.v("MediationAdapter is not a MediationBannerAdapter: " + this.gg.getClass().getCanonicalName());
            throw new RemoteException();
        }
        try {
            return c.h(((MediationBannerAdapter) this.gg).getBannerView());
        } catch (Throwable th) {
            ct.b("Could not get banner view from adapter.", th);
            throw new RemoteException();
        }
    }

    public void showInterstitial() throws RemoteException {
        if (!(this.gg instanceof MediationInterstitialAdapter)) {
            ct.v("MediationAdapter is not a MediationInterstitialAdapter: " + this.gg.getClass().getCanonicalName());
            throw new RemoteException();
        }
        ct.r("Showing interstitial from adapter.");
        try {
            ((MediationInterstitialAdapter) this.gg).showInterstitial();
        } catch (Throwable th) {
            ct.b("Could not show interstitial from adapter.", th);
            throw new RemoteException();
        }
    }
}
