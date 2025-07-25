package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class FullWalletRequest implements SafeParcelable {
    public static final Parcelable.Creator<FullWalletRequest> CREATOR = new e();
    String Gn;
    String Go;
    Cart Gu;
    private final int kg;

    public final class Builder {
        private Builder() {
        }

        public FullWalletRequest build() {
            return FullWalletRequest.this;
        }

        public Builder setCart(Cart cart) {
            FullWalletRequest.this.Gu = cart;
            return this;
        }

        public Builder setGoogleTransactionId(String str) {
            FullWalletRequest.this.Gn = str;
            return this;
        }

        public Builder setMerchantTransactionId(String str) {
            FullWalletRequest.this.Go = str;
            return this;
        }
    }

    FullWalletRequest() {
        this.kg = 1;
    }

    FullWalletRequest(int i, String str, String str2, Cart cart) {
        this.kg = i;
        this.Gn = str;
        this.Go = str2;
        this.Gu = cart;
    }

    public static Builder newBuilder() {
        FullWalletRequest fullWalletRequest = new FullWalletRequest();
        fullWalletRequest.getClass();
        return new Builder();
    }

    public int describeContents() {
        return 0;
    }

    public Cart getCart() {
        return this.Gu;
    }

    public String getGoogleTransactionId() {
        return this.Gn;
    }

    public String getMerchantTransactionId() {
        return this.Go;
    }

    public int getVersionCode() {
        return this.kg;
    }

    public void writeToParcel(Parcel parcel, int i) {
        e.a(this, parcel, i);
    }
}
