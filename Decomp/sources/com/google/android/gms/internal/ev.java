package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.es;
import java.util.ArrayList;
import java.util.HashMap;

public class ev implements SafeParcelable {
    public static final ew CREATOR = new ew();
    private final int kg;
    private final HashMap<String, HashMap<String, es.a<?, ?>>> qs;
    private final ArrayList<a> qt;
    private final String qu;

    public static class a implements SafeParcelable {
        public static final ex CREATOR = new ex();
        final String className;
        final ArrayList<b> qv;
        final int versionCode;

        a(int i, String str, ArrayList<b> arrayList) {
            this.versionCode = i;
            this.className = str;
            this.qv = arrayList;
        }

        a(String str, HashMap<String, es.a<?, ?>> hashMap) {
            this.versionCode = 1;
            this.className = str;
            this.qv = b(hashMap);
        }

        private static ArrayList<b> b(HashMap<String, es.a<?, ?>> hashMap) {
            if (hashMap == null) {
                return null;
            }
            ArrayList<b> arrayList = new ArrayList<>();
            for (String next : hashMap.keySet()) {
                arrayList.add(new b(next, hashMap.get(next)));
            }
            return arrayList;
        }

        /* access modifiers changed from: package-private */
        public HashMap<String, es.a<?, ?>> cA() {
            HashMap<String, es.a<?, ?>> hashMap = new HashMap<>();
            int size = this.qv.size();
            for (int i = 0; i < size; i++) {
                b bVar = this.qv.get(i);
                hashMap.put(bVar.qw, bVar.qx);
            }
            return hashMap;
        }

        public int describeContents() {
            ex exVar = CREATOR;
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            ex exVar = CREATOR;
            ex.a(this, parcel, i);
        }
    }

    public static class b implements SafeParcelable {
        public static final eu CREATOR = new eu();
        final String qw;
        final es.a<?, ?> qx;
        final int versionCode;

        b(int i, String str, es.a<?, ?> aVar) {
            this.versionCode = i;
            this.qw = str;
            this.qx = aVar;
        }

        b(String str, es.a<?, ?> aVar) {
            this.versionCode = 1;
            this.qw = str;
            this.qx = aVar;
        }

        public int describeContents() {
            eu euVar = CREATOR;
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            eu euVar = CREATOR;
            eu.a(this, parcel, i);
        }
    }

    ev(int i, ArrayList<a> arrayList, String str) {
        this.kg = i;
        this.qt = null;
        this.qs = b(arrayList);
        this.qu = (String) eg.f(str);
        cw();
    }

    public ev(Class<? extends es> cls) {
        this.kg = 1;
        this.qt = null;
        this.qs = new HashMap<>();
        this.qu = cls.getCanonicalName();
    }

    private static HashMap<String, HashMap<String, es.a<?, ?>>> b(ArrayList<a> arrayList) {
        HashMap<String, HashMap<String, es.a<?, ?>>> hashMap = new HashMap<>();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            a aVar = arrayList.get(i);
            hashMap.put(aVar.className, aVar.cA());
        }
        return hashMap;
    }

    public HashMap<String, es.a<?, ?>> Z(String str) {
        return this.qs.get(str);
    }

    public void a(Class<? extends es> cls, HashMap<String, es.a<?, ?>> hashMap) {
        this.qs.put(cls.getCanonicalName(), hashMap);
    }

    public boolean b(Class<? extends es> cls) {
        return this.qs.containsKey(cls.getCanonicalName());
    }

    public void cw() {
        for (String str : this.qs.keySet()) {
            HashMap hashMap = this.qs.get(str);
            for (String str2 : hashMap.keySet()) {
                ((es.a) hashMap.get(str2)).a(this);
            }
        }
    }

    public void cx() {
        for (String next : this.qs.keySet()) {
            HashMap hashMap = this.qs.get(next);
            HashMap hashMap2 = new HashMap();
            for (String str : hashMap.keySet()) {
                hashMap2.put(str, ((es.a) hashMap.get(str)).cm());
            }
            this.qs.put(next, hashMap2);
        }
    }

    /* access modifiers changed from: package-private */
    public ArrayList<a> cy() {
        ArrayList<a> arrayList = new ArrayList<>();
        for (String next : this.qs.keySet()) {
            arrayList.add(new a(next, this.qs.get(next)));
        }
        return arrayList;
    }

    public String cz() {
        return this.qu;
    }

    public int describeContents() {
        ew ewVar = CREATOR;
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String next : this.qs.keySet()) {
            sb.append(next).append(":\n");
            HashMap hashMap = this.qs.get(next);
            for (String str : hashMap.keySet()) {
                sb.append("  ").append(str).append(": ");
                sb.append(hashMap.get(str));
            }
        }
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        ew ewVar = CREATOR;
        ew.a(this, parcel, i);
    }
}
