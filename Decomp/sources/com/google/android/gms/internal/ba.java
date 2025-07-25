package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.ads.mediation.MediationAdapter;
import com.google.ads.mediation.MediationServerParameters;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.internal.bb;
import java.util.Map;

public final class ba extends bb.a {
    private Map<Class<? extends NetworkExtras>, NetworkExtras> gf;

    private <NETWORK_EXTRAS extends com.google.ads.mediation.NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> bc m(String str) throws RemoteException {
        try {
            Class<?> cls = Class.forName(str, false, ba.class.getClassLoader());
            if (!MediationAdapter.class.isAssignableFrom(cls)) {
                ct.v("Could not instantiate mediation adapter: " + str + ".");
                throw new RemoteException();
            }
            MediationAdapter mediationAdapter = (MediationAdapter) cls.newInstance();
            return new be(mediationAdapter, (com.google.ads.mediation.NetworkExtras) this.gf.get(mediationAdapter.getAdditionalParametersType()));
        } catch (Throwable th) {
            ct.v("Could not instantiate mediation adapter: " + str + ". " + th.getMessage());
            throw new RemoteException();
        }
    }

    public void c(Map<Class<? extends NetworkExtras>, NetworkExtras> map) {
        this.gf = map;
    }

    public bc l(String str) throws RemoteException {
        return m(str);
    }
}
