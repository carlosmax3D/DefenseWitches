package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class LineItem implements SafeParcelable {
    public static final Parcelable.Creator<LineItem> CREATOR = new f();
    String Gj;
    String Gk;
    String Gw;
    String Gx;
    int Gy;
    String description;
    private final int kg;

    public final class Builder {
        private Builder() {
        }

        public LineItem build() {
            return LineItem.this;
        }

        public Builder setCurrencyCode(String str) {
            LineItem.this.Gk = str;
            return this;
        }

        public Builder setDescription(String str) {
            LineItem.this.description = str;
            return this;
        }

        public Builder setQuantity(String str) {
            LineItem.this.Gw = str;
            return this;
        }

        public Builder setRole(int i) {
            LineItem.this.Gy = i;
            return this;
        }

        public Builder setTotalPrice(String str) {
            LineItem.this.Gj = str;
            return this;
        }

        public Builder setUnitPrice(String str) {
            LineItem.this.Gx = str;
            return this;
        }
    }

    public interface Role {
        public static final int REGULAR = 0;
        public static final int SHIPPING = 2;
        public static final int TAX = 1;
    }

    LineItem() {
        this.kg = 1;
        this.Gy = 0;
    }

    LineItem(int i, String str, String str2, String str3, String str4, int i2, String str5) {
        this.kg = i;
        this.description = str;
        this.Gw = str2;
        this.Gx = str3;
        this.Gj = str4;
        this.Gy = i2;
        this.Gk = str5;
    }

    public static Builder newBuilder() {
        LineItem lineItem = new LineItem();
        lineItem.getClass();
        return new Builder();
    }

    public int describeContents() {
        return 0;
    }

    public String getCurrencyCode() {
        return this.Gk;
    }

    public String getDescription() {
        return this.description;
    }

    public String getQuantity() {
        return this.Gw;
    }

    public int getRole() {
        return this.Gy;
    }

    public String getTotalPrice() {
        return this.Gj;
    }

    public String getUnitPrice() {
        return this.Gx;
    }

    public int getVersionCode() {
        return this.kg;
    }

    public void writeToParcel(Parcel parcel, int i) {
        f.a(this, parcel, i);
    }
}
