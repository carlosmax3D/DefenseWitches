package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;

public final class ShareLinkContent extends ShareContent<ShareLinkContent, Builder> {
    public static final Parcelable.Creator<ShareLinkContent> CREATOR = new Parcelable.Creator<ShareLinkContent>() {
        public ShareLinkContent createFromParcel(Parcel parcel) {
            return new ShareLinkContent(parcel);
        }

        public ShareLinkContent[] newArray(int i) {
            return new ShareLinkContent[i];
        }
    };
    private final String contentDescription;
    private final String contentTitle;
    private final Uri imageUrl;

    public static final class Builder extends ShareContent.Builder<ShareLinkContent, Builder> {
        /* access modifiers changed from: private */
        public String contentDescription;
        /* access modifiers changed from: private */
        public String contentTitle;
        /* access modifiers changed from: private */
        public Uri imageUrl;

        public ShareLinkContent build() {
            return new ShareLinkContent(this);
        }

        public Builder readFrom(Parcel parcel) {
            return readFrom((ShareLinkContent) parcel.readParcelable(ShareLinkContent.class.getClassLoader()));
        }

        public Builder readFrom(ShareLinkContent shareLinkContent) {
            return shareLinkContent == null ? this : ((Builder) super.readFrom(shareLinkContent)).setContentDescription(shareLinkContent.getContentDescription()).setImageUrl(shareLinkContent.getImageUrl()).setContentTitle(shareLinkContent.getContentTitle());
        }

        public Builder setContentDescription(@Nullable String str) {
            this.contentDescription = str;
            return this;
        }

        public Builder setContentTitle(@Nullable String str) {
            this.contentTitle = str;
            return this;
        }

        public Builder setImageUrl(@Nullable Uri uri) {
            this.imageUrl = uri;
            return this;
        }
    }

    ShareLinkContent(Parcel parcel) {
        super(parcel);
        this.contentDescription = parcel.readString();
        this.contentTitle = parcel.readString();
        this.imageUrl = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
    }

    private ShareLinkContent(Builder builder) {
        super((ShareContent.Builder) builder);
        this.contentDescription = builder.contentDescription;
        this.contentTitle = builder.contentTitle;
        this.imageUrl = builder.imageUrl;
    }

    public int describeContents() {
        return 0;
    }

    public String getContentDescription() {
        return this.contentDescription;
    }

    @Nullable
    public String getContentTitle() {
        return this.contentTitle;
    }

    @Nullable
    public Uri getImageUrl() {
        return this.imageUrl;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.contentDescription);
        parcel.writeString(this.contentTitle);
        parcel.writeParcelable(this.imageUrl, 0);
    }
}
