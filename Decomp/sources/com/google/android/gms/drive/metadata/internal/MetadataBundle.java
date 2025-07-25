package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.internal.ee;
import com.google.android.gms.internal.eg;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class MetadataBundle implements SafeParcelable {
    public static final Parcelable.Creator<MetadataBundle> CREATOR = new d();
    final int kg;
    final Bundle rF;

    MetadataBundle(int i, Bundle bundle) {
        this.kg = i;
        this.rF = (Bundle) eg.f(bundle);
        this.rF.setClassLoader(getClass().getClassLoader());
        ArrayList<String> arrayList = new ArrayList<>();
        for (String str : this.rF.keySet()) {
            if (c.ac(str) == null) {
                arrayList.add(str);
                Log.w("MetadataBundle", "Ignored unknown metadata field in bundle: " + str);
            }
        }
        for (String remove : arrayList) {
            this.rF.remove(remove);
        }
    }

    private MetadataBundle(Bundle bundle) {
        this(1, bundle);
    }

    public static <T> MetadataBundle a(MetadataField<T> metadataField, T t) {
        MetadataBundle cX = cX();
        cX.b(metadataField, t);
        return cX;
    }

    public static MetadataBundle a(MetadataBundle metadataBundle) {
        return new MetadataBundle(new Bundle(metadataBundle.rF));
    }

    public static MetadataBundle cX() {
        return new MetadataBundle(new Bundle());
    }

    public <T> T a(MetadataField<T> metadataField) {
        return metadataField.d(this.rF);
    }

    public <T> void b(MetadataField<T> metadataField, T t) {
        if (c.ac(metadataField.getName()) == null) {
            throw new IllegalArgumentException("Unregistered field: " + metadataField.getName());
        }
        metadataField.a(t, this.rF);
    }

    public Set<MetadataField<?>> cY() {
        HashSet hashSet = new HashSet();
        for (String ac : this.rF.keySet()) {
            hashSet.add(c.ac(ac));
        }
        return hashSet;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MetadataBundle)) {
            return false;
        }
        MetadataBundle metadataBundle = (MetadataBundle) obj;
        Set<String> keySet = this.rF.keySet();
        if (!keySet.equals(metadataBundle.rF.keySet())) {
            return false;
        }
        for (String str : keySet) {
            if (!ee.equal(this.rF.get(str), metadataBundle.rF.get(str))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 1;
        Iterator it = this.rF.keySet().iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            i = this.rF.get((String) it.next()).hashCode() + (i2 * 31);
        }
    }

    public String toString() {
        return "MetadataBundle [values=" + this.rF + "]";
    }

    public void writeToParcel(Parcel parcel, int i) {
        d.a(this, parcel, i);
    }
}
