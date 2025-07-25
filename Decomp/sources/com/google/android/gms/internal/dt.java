package com.google.android.gms.internal;

import android.os.Parcel;
import android.view.View;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class dt {
    private final View nu;
    private final a oX;

    public static final class a implements SafeParcelable {
        public static final ef CREATOR = new ef();
        private final String jG;
        private final int kg;
        private final int nt;
        private final String nv;
        private final List<String> oY;

        a(int i, String str, List<String> list, int i2, String str2) {
            this.oY = new ArrayList();
            this.kg = i;
            this.jG = str;
            this.oY.addAll(list);
            this.nt = i2;
            this.nv = str2;
        }

        public a(String str, Collection<String> collection, int i, String str2) {
            this(3, str, new ArrayList(collection), i, str2);
        }

        public String bF() {
            return this.jG != null ? this.jG : "<<default account>>";
        }

        public int bG() {
            return this.nt;
        }

        public List<String> bH() {
            return new ArrayList(this.oY);
        }

        public String bJ() {
            return this.nv;
        }

        public int describeContents() {
            return 0;
        }

        public String getAccountName() {
            return this.jG;
        }

        public int getVersionCode() {
            return this.kg;
        }

        public void writeToParcel(Parcel parcel, int i) {
            ef.a(this, parcel, i);
        }
    }

    public dt(String str, Collection<String> collection, int i, View view, String str2) {
        this.oX = new a(str, collection, i, str2);
        this.nu = view;
    }

    public String bF() {
        return this.oX.bF();
    }

    public int bG() {
        return this.oX.bG();
    }

    public List<String> bH() {
        return this.oX.bH();
    }

    public String[] bI() {
        return (String[]) this.oX.bH().toArray(new String[0]);
    }

    public String bJ() {
        return this.oX.bJ();
    }

    public View bK() {
        return this.nu;
    }

    public String getAccountName() {
        return this.oX.getAccountName();
    }
}
