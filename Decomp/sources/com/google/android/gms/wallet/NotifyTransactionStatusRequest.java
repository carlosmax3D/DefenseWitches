package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.eg;

public final class NotifyTransactionStatusRequest implements SafeParcelable {
    public static final Parcelable.Creator<NotifyTransactionStatusRequest> CREATOR = new j();
    String GV;
    String Gn;
    final int kg;
    int status;

    public final class Builder {
        private Builder() {
        }

        public NotifyTransactionStatusRequest build() {
            boolean z = true;
            eg.b(!TextUtils.isEmpty(NotifyTransactionStatusRequest.this.Gn), (Object) "googleTransactionId is required");
            if (NotifyTransactionStatusRequest.this.status < 1 || NotifyTransactionStatusRequest.this.status > 8) {
                z = false;
            }
            eg.b(z, (Object) "status is an unrecognized value");
            return NotifyTransactionStatusRequest.this;
        }

        public Builder setDetailedReason(String str) {
            NotifyTransactionStatusRequest.this.GV = str;
            return this;
        }

        public Builder setGoogleTransactionId(String str) {
            NotifyTransactionStatusRequest.this.Gn = str;
            return this;
        }

        public Builder setStatus(int i) {
            NotifyTransactionStatusRequest.this.status = i;
            return this;
        }
    }

    public interface Status {
        public static final int SUCCESS = 1;

        public interface Error {
            public static final int AVS_DECLINE = 7;
            public static final int BAD_CARD = 4;
            public static final int BAD_CVC = 3;
            public static final int DECLINED = 5;
            public static final int FRAUD_DECLINE = 8;
            public static final int OTHER = 6;
            public static final int UNKNOWN = 2;
        }
    }

    NotifyTransactionStatusRequest() {
        this.kg = 1;
    }

    NotifyTransactionStatusRequest(int i, String str, int i2, String str2) {
        this.kg = i;
        this.Gn = str;
        this.status = i2;
        this.GV = str2;
    }

    public static Builder newBuilder() {
        NotifyTransactionStatusRequest notifyTransactionStatusRequest = new NotifyTransactionStatusRequest();
        notifyTransactionStatusRequest.getClass();
        return new Builder();
    }

    public int describeContents() {
        return 0;
    }

    public String getDetailedReason() {
        return this.GV;
    }

    public String getGoogleTransactionId() {
        return this.Gn;
    }

    public int getStatus() {
        return this.status;
    }

    public int getVersionCode() {
        return this.kg;
    }

    public void writeToParcel(Parcel parcel, int i) {
        j.a(this, parcel, i);
    }
}
