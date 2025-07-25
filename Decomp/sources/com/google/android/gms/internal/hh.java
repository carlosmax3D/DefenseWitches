package com.google.android.gms.internal;

import com.google.android.gms.internal.hd;
import java.util.concurrent.BlockingQueue;

public class hh {
    private static int Bj = 10000;
    private static int Bk = 1000;
    private final int Be;
    private final String Bl;
    private final BlockingQueue<hd.a> Bm;

    public void a(hd.a.C0034a aVar) {
        aVar.as(this.Bl);
        aVar.bd(this.Be);
        this.Bm.offer(aVar.ek());
    }
}
