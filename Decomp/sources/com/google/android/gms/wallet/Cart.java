package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.List;

public final class Cart implements SafeParcelable {
    public static final Parcelable.Creator<Cart> CREATOR = new b();
    String Gj;
    String Gk;
    ArrayList<LineItem> Gl;
    private final int kg;

    public final class Builder {
        private Builder() {
        }

        public Builder addLineItem(LineItem lineItem) {
            Cart.this.Gl.add(lineItem);
            return this;
        }

        public Cart build() {
            return Cart.this;
        }

        public Builder setCurrencyCode(String str) {
            Cart.this.Gk = str;
            return this;
        }

        public Builder setLineItems(List<LineItem> list) {
            Cart.this.Gl.clear();
            Cart.this.Gl.addAll(list);
            return this;
        }

        public Builder setTotalPrice(String str) {
            Cart.this.Gj = str;
            return this;
        }
    }

    Cart() {
        this.kg = 1;
        this.Gl = new ArrayList<>();
    }

    Cart(int i, String str, String str2, ArrayList<LineItem> arrayList) {
        this.kg = i;
        this.Gj = str;
        this.Gk = str2;
        this.Gl = arrayList;
    }

    public static Builder newBuilder() {
        Cart cart = new Cart();
        cart.getClass();
        return new Builder();
    }

    public int describeContents() {
        return 0;
    }

    public String getCurrencyCode() {
        return this.Gk;
    }

    public ArrayList<LineItem> getLineItems() {
        return this.Gl;
    }

    public String getTotalPrice() {
        return this.Gj;
    }

    public int getVersionCode() {
        return this.kg;
    }

    public void writeToParcel(Parcel parcel, int i) {
        b.a(this, parcel, i);
    }
}
