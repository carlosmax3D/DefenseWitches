package com.google.android.gms.internal;

import android.os.Parcel;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.es;
import com.google.android.gms.plus.model.moments.ItemScope;
import com.google.android.gms.plus.model.moments.Moment;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class id extends es implements SafeParcelable, Moment {
    public static final ie CREATOR = new ie();
    private static final HashMap<String, es.a<?, ?>> Ep = new HashMap<>();
    private String AI;
    private final Set<Integer> Eq;
    private String Fe;
    private ib Fm;
    private ib Fn;
    private final int kg;
    private String uS;

    static {
        Ep.put(ShareConstants.WEB_DIALOG_PARAM_ID, es.a.g(ShareConstants.WEB_DIALOG_PARAM_ID, 2));
        Ep.put("result", es.a.a("result", 4, ib.class));
        Ep.put("startDate", es.a.g("startDate", 5));
        Ep.put("target", es.a.a("target", 6, ib.class));
        Ep.put("type", es.a.g("type", 7));
    }

    public id() {
        this.kg = 1;
        this.Eq = new HashSet();
    }

    id(Set<Integer> set, int i, String str, ib ibVar, String str2, ib ibVar2, String str3) {
        this.Eq = set;
        this.kg = i;
        this.uS = str;
        this.Fm = ibVar;
        this.Fe = str2;
        this.Fn = ibVar2;
        this.AI = str3;
    }

    public id(Set<Integer> set, String str, ib ibVar, String str2, ib ibVar2, String str3) {
        this.Eq = set;
        this.kg = 1;
        this.uS = str;
        this.Fm = ibVar;
        this.Fe = str2;
        this.Fn = ibVar2;
        this.AI = str3;
    }

    /* access modifiers changed from: protected */
    public Object V(String str) {
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean W(String str) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean a(es.a aVar) {
        return this.Eq.contains(Integer.valueOf(aVar.cq()));
    }

    /* access modifiers changed from: protected */
    public Object b(es.a aVar) {
        switch (aVar.cq()) {
            case 2:
                return this.uS;
            case 4:
                return this.Fm;
            case 5:
                return this.Fe;
            case 6:
                return this.Fn;
            case 7:
                return this.AI;
            default:
                throw new IllegalStateException("Unknown safe parcelable id=" + aVar.cq());
        }
    }

    public HashMap<String, es.a<?, ?>> cj() {
        return Ep;
    }

    public int describeContents() {
        ie ieVar = CREATOR;
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof id)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        id idVar = (id) obj;
        for (es.a next : Ep.values()) {
            if (a(next)) {
                if (!idVar.a(next)) {
                    return false;
                }
                if (!b(next).equals(idVar.b(next))) {
                    return false;
                }
            } else if (idVar.a(next)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public Set<Integer> fa() {
        return this.Eq;
    }

    /* access modifiers changed from: package-private */
    public ib fr() {
        return this.Fm;
    }

    /* access modifiers changed from: package-private */
    public ib fs() {
        return this.Fn;
    }

    /* renamed from: ft */
    public id freeze() {
        return this;
    }

    public String getId() {
        return this.uS;
    }

    public ItemScope getResult() {
        return this.Fm;
    }

    public String getStartDate() {
        return this.Fe;
    }

    public ItemScope getTarget() {
        return this.Fn;
    }

    public String getType() {
        return this.AI;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public boolean hasId() {
        return this.Eq.contains(2);
    }

    public boolean hasResult() {
        return this.Eq.contains(4);
    }

    public boolean hasStartDate() {
        return this.Eq.contains(5);
    }

    public boolean hasTarget() {
        return this.Eq.contains(6);
    }

    public boolean hasType() {
        return this.Eq.contains(7);
    }

    public int hashCode() {
        int i = 0;
        Iterator<es.a<?, ?>> it = Ep.values().iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            es.a next = it.next();
            if (a(next)) {
                i = b(next).hashCode() + i2 + next.cq();
            } else {
                i = i2;
            }
        }
    }

    public boolean isDataValid() {
        return true;
    }

    public void writeToParcel(Parcel parcel, int i) {
        ie ieVar = CREATOR;
        ie.a(this, parcel, i);
    }
}
