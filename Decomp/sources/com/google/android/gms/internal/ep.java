package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.es;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class ep implements SafeParcelable, es.b<String, Integer> {
    public static final eq CREATOR = new eq();
    private final int kg;
    private final HashMap<String, Integer> qd;
    private final HashMap<Integer, String> qe;
    private final ArrayList<a> qf;

    public static final class a implements SafeParcelable {
        public static final er CREATOR = new er();
        final String qg;
        final int qh;
        final int versionCode;

        a(int i, String str, int i2) {
            this.versionCode = i;
            this.qg = str;
            this.qh = i2;
        }

        a(String str, int i) {
            this.versionCode = 1;
            this.qg = str;
            this.qh = i;
        }

        public int describeContents() {
            er erVar = CREATOR;
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            er erVar = CREATOR;
            er.a(this, parcel, i);
        }
    }

    public ep() {
        this.kg = 1;
        this.qd = new HashMap<>();
        this.qe = new HashMap<>();
        this.qf = null;
    }

    ep(int i, ArrayList<a> arrayList) {
        this.kg = i;
        this.qd = new HashMap<>();
        this.qe = new HashMap<>();
        this.qf = null;
        a(arrayList);
    }

    private void a(ArrayList<a> arrayList) {
        Iterator<a> it = arrayList.iterator();
        while (it.hasNext()) {
            a next = it.next();
            c(next.qg, next.qh);
        }
    }

    /* renamed from: a */
    public String g(Integer num) {
        String str = this.qe.get(num);
        return (str != null || !this.qd.containsKey("gms_unknown")) ? str : "gms_unknown";
    }

    public ep c(String str, int i) {
        this.qd.put(str, Integer.valueOf(i));
        this.qe.put(Integer.valueOf(i), str);
        return this;
    }

    /* access modifiers changed from: package-private */
    public ArrayList<a> cg() {
        ArrayList<a> arrayList = new ArrayList<>();
        for (String next : this.qd.keySet()) {
            arrayList.add(new a(next, this.qd.get(next).intValue()));
        }
        return arrayList;
    }

    public int ch() {
        return 7;
    }

    public int ci() {
        return 0;
    }

    public int describeContents() {
        eq eqVar = CREATOR;
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public void writeToParcel(Parcel parcel, int i) {
        eq eqVar = CREATOR;
        eq.a(this, parcel, i);
    }
}
