package com.google.android.gms.drive.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.a;

public class z extends a {
    private final a.c<Status> jW;

    public z(a.c<Status> cVar) {
        this.jW = cVar;
    }

    public void m(Status status) throws RemoteException {
        this.jW.a(status);
    }

    public void onSuccess() throws RemoteException {
        this.jW.a(Status.nA);
    }
}
