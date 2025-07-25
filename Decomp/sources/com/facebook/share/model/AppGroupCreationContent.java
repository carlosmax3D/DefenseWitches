package com.facebook.share.model;

import android.os.Parcel;

public final class AppGroupCreationContent implements ShareModel {
    private final String description;
    private final String name;
    private AppGroupPrivacy privacy;

    public enum AppGroupPrivacy {
        Open,
        Closed
    }

    public static class Builder implements ShareModelBuilder<AppGroupCreationContent, Builder> {
        /* access modifiers changed from: private */
        public String description;
        /* access modifiers changed from: private */
        public String name;
        /* access modifiers changed from: private */
        public AppGroupPrivacy privacy;

        public AppGroupCreationContent build() {
            return new AppGroupCreationContent(this);
        }

        public Builder readFrom(Parcel parcel) {
            return readFrom((AppGroupCreationContent) parcel.readParcelable(AppGroupCreationContent.class.getClassLoader()));
        }

        public Builder readFrom(AppGroupCreationContent appGroupCreationContent) {
            return appGroupCreationContent == null ? this : setName(appGroupCreationContent.getName()).setDescription(appGroupCreationContent.getDescription()).setAppGroupPrivacy(appGroupCreationContent.getAppGroupPrivacy());
        }

        public Builder setAppGroupPrivacy(AppGroupPrivacy appGroupPrivacy) {
            this.privacy = appGroupPrivacy;
            return this;
        }

        public Builder setDescription(String str) {
            this.description = str;
            return this;
        }

        public Builder setName(String str) {
            this.name = str;
            return this;
        }
    }

    AppGroupCreationContent(Parcel parcel) {
        this.name = parcel.readString();
        this.description = parcel.readString();
        this.privacy = AppGroupPrivacy.valueOf(parcel.readString());
    }

    private AppGroupCreationContent(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.privacy = builder.privacy;
    }

    public int describeContents() {
        return 0;
    }

    public AppGroupPrivacy getAppGroupPrivacy() {
        return this.privacy;
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return this.name;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.description);
        parcel.writeString(this.privacy.toString());
    }
}
