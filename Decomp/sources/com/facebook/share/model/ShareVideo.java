package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public final class ShareVideo implements ShareModel {
    public static final Parcelable.Creator<ShareVideo> CREATOR = new Parcelable.Creator<ShareVideo>() {
        public ShareVideo createFromParcel(Parcel parcel) {
            return new ShareVideo(parcel);
        }

        public ShareVideo[] newArray(int i) {
            return new ShareVideo[i];
        }
    };
    private final Uri localUrl;

    public static final class Builder implements ShareModelBuilder<ShareVideo, Builder> {
        /* access modifiers changed from: private */
        public Uri localUrl;

        public ShareVideo build() {
            return new ShareVideo(this);
        }

        public Builder readFrom(Parcel parcel) {
            return readFrom((ShareVideo) parcel.readParcelable(ShareVideo.class.getClassLoader()));
        }

        public Builder readFrom(ShareVideo shareVideo) {
            return shareVideo == null ? this : setLocalUrl(shareVideo.getLocalUrl());
        }

        public Builder setLocalUrl(@Nullable Uri uri) {
            this.localUrl = uri;
            return this;
        }
    }

    ShareVideo(Parcel parcel) {
        this.localUrl = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
    }

    private ShareVideo(Builder builder) {
        this.localUrl = builder.localUrl;
    }

    public int describeContents() {
        return 0;
    }

    @Nullable
    public Uri getLocalUrl() {
        return this.localUrl;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.localUrl, 0);
    }
}
