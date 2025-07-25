package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.Scopes;
import java.util.ArrayList;
import java.util.Arrays;

public class hv {
    private String[] DT;
    private String DU;
    private String DV;
    private String DW;
    private ArrayList<String> DY = new ArrayList<>();
    private String[] DZ;
    private String jG;

    public hv(Context context) {
        this.DV = context.getPackageName();
        this.DU = context.getPackageName();
        this.DY.add(Scopes.PLUS_LOGIN);
    }

    public hv aA(String str) {
        this.jG = str;
        return this;
    }

    public hv d(String... strArr) {
        this.DY.clear();
        this.DY.addAll(Arrays.asList(strArr));
        return this;
    }

    public hv e(String... strArr) {
        this.DZ = strArr;
        return this;
    }

    public hv eY() {
        this.DY.clear();
        return this;
    }

    public hu eZ() {
        if (this.jG == null) {
            this.jG = "<<default account>>";
        }
        return new hu(this.jG, (String[]) this.DY.toArray(new String[this.DY.size()]), this.DZ, this.DT, this.DU, this.DV, this.DW);
    }
}
