package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class MaskedWalletRequest implements SafeParcelable {
    public static final Parcelable.Creator<MaskedWalletRequest> CREATOR = new i();
    boolean GK;
    boolean GL;
    boolean GM;
    String GN;
    String GO;
    boolean GP;
    boolean GQ;
    CountrySpecification[] GR;
    boolean GS;
    boolean GT;
    String Gk;
    String Go;
    Cart Gu;
    private final int kg;

    public final class Builder {
        private Builder() {
        }

        public MaskedWalletRequest build() {
            return MaskedWalletRequest.this;
        }

        public Builder setAllowDebitCard(boolean z) {
            MaskedWalletRequest.this.GT = z;
            return this;
        }

        public Builder setAllowPrepaidCard(boolean z) {
            MaskedWalletRequest.this.GS = z;
            return this;
        }

        public Builder setAllowedShippingCountrySpecifications(CountrySpecification[] countrySpecificationArr) {
            MaskedWalletRequest.this.GR = countrySpecificationArr;
            return this;
        }

        public Builder setCart(Cart cart) {
            MaskedWalletRequest.this.Gu = cart;
            return this;
        }

        public Builder setCurrencyCode(String str) {
            MaskedWalletRequest.this.Gk = str;
            return this;
        }

        public Builder setEstimatedTotalPrice(String str) {
            MaskedWalletRequest.this.GN = str;
            return this;
        }

        public Builder setIsBillingAgreement(boolean z) {
            MaskedWalletRequest.this.GQ = z;
            return this;
        }

        public Builder setMerchantName(String str) {
            MaskedWalletRequest.this.GO = str;
            return this;
        }

        public Builder setMerchantTransactionId(String str) {
            MaskedWalletRequest.this.Go = str;
            return this;
        }

        public Builder setPhoneNumberRequired(boolean z) {
            MaskedWalletRequest.this.GK = z;
            return this;
        }

        public Builder setShippingAddressRequired(boolean z) {
            MaskedWalletRequest.this.GL = z;
            return this;
        }

        public Builder setShouldRetrieveWalletObjects(boolean z) {
            MaskedWalletRequest.this.GP = z;
            return this;
        }

        public Builder setUseMinimalBillingAddress(boolean z) {
            MaskedWalletRequest.this.GM = z;
            return this;
        }
    }

    MaskedWalletRequest() {
        this.kg = 3;
        this.GS = true;
        this.GT = true;
    }

    MaskedWalletRequest(int i, String str, boolean z, boolean z2, boolean z3, String str2, String str3, String str4, Cart cart, boolean z4, boolean z5, CountrySpecification[] countrySpecificationArr, boolean z6, boolean z7) {
        this.kg = i;
        this.Go = str;
        this.GK = z;
        this.GL = z2;
        this.GM = z3;
        this.GN = str2;
        this.Gk = str3;
        this.GO = str4;
        this.Gu = cart;
        this.GP = z4;
        this.GQ = z5;
        this.GR = countrySpecificationArr;
        this.GS = z6;
        this.GT = z7;
    }

    public static Builder newBuilder() {
        MaskedWalletRequest maskedWalletRequest = new MaskedWalletRequest();
        maskedWalletRequest.getClass();
        return new Builder();
    }

    public boolean allowDebitCard() {
        return this.GT;
    }

    public boolean allowPrepaidCard() {
        return this.GS;
    }

    public int describeContents() {
        return 0;
    }

    public CountrySpecification[] getAllowedShippingCountrySpecifications() {
        return this.GR;
    }

    public Cart getCart() {
        return this.Gu;
    }

    public String getCurrencyCode() {
        return this.Gk;
    }

    public String getEstimatedTotalPrice() {
        return this.GN;
    }

    public String getMerchantName() {
        return this.GO;
    }

    public String getMerchantTransactionId() {
        return this.Go;
    }

    public int getVersionCode() {
        return this.kg;
    }

    public boolean isBillingAgreement() {
        return this.GQ;
    }

    public boolean isPhoneNumberRequired() {
        return this.GK;
    }

    public boolean isShippingAddressRequired() {
        return this.GL;
    }

    public boolean shouldRetrieveWalletObjects() {
        return this.GP;
    }

    public boolean useMinimalBillingAddress() {
        return this.GM;
    }

    public void writeToParcel(Parcel parcel, int i) {
        i.a(this, parcel, i);
    }
}
