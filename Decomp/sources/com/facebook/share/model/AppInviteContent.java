package com.facebook.share.model;

import android.os.Parcel;

public final class AppInviteContent implements ShareModel {
    private final String applinkUrl;
    private final String previewImageUrl;

    public static class Builder implements ShareModelBuilder<AppInviteContent, Builder> {
        /* access modifiers changed from: private */
        public String applinkUrl;
        /* access modifiers changed from: private */
        public String previewImageUrl;

        public AppInviteContent build() {
            return new AppInviteContent(this);
        }

        public Builder readFrom(Parcel parcel) {
            return readFrom((AppInviteContent) parcel.readParcelable(AppInviteContent.class.getClassLoader()));
        }

        public Builder readFrom(AppInviteContent appInviteContent) {
            return appInviteContent == null ? this : setApplinkUrl(appInviteContent.getApplinkUrl()).setPreviewImageUrl(appInviteContent.getPreviewImageUrl());
        }

        public Builder setApplinkUrl(String str) {
            this.applinkUrl = str;
            return this;
        }

        public Builder setPreviewImageUrl(String str) {
            this.previewImageUrl = str;
            return this;
        }
    }

    AppInviteContent(Parcel parcel) {
        this.applinkUrl = parcel.readString();
        this.previewImageUrl = parcel.readString();
    }

    private AppInviteContent(Builder builder) {
        this.applinkUrl = builder.applinkUrl;
        this.previewImageUrl = builder.previewImageUrl;
    }

    public int describeContents() {
        return 0;
    }

    public String getApplinkUrl() {
        return this.applinkUrl;
    }

    public String getPreviewImageUrl() {
        return this.previewImageUrl;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.applinkUrl);
        parcel.writeString(this.previewImageUrl);
    }
}
