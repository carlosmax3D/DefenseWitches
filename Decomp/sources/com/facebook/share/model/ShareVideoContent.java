package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.ShareVideo;

public final class ShareVideoContent extends ShareContent<ShareVideoContent, Builder> implements ShareModel {
    public static final Parcelable.Creator<ShareVideoContent> CREATOR = new Parcelable.Creator<ShareVideoContent>() {
        public ShareVideoContent createFromParcel(Parcel parcel) {
            return new ShareVideoContent(parcel);
        }

        public ShareVideoContent[] newArray(int i) {
            return new ShareVideoContent[i];
        }
    };
    private final String contentDescription;
    private final String contentTitle;
    private final SharePhoto previewPhoto;
    private final ShareVideo video;

    public static final class Builder extends ShareContent.Builder<ShareVideoContent, Builder> {
        /* access modifiers changed from: private */
        public String contentDescription;
        /* access modifiers changed from: private */
        public String contentTitle;
        /* access modifiers changed from: private */
        public SharePhoto previewPhoto;
        /* access modifiers changed from: private */
        public ShareVideo video;

        public ShareVideoContent build() {
            return new ShareVideoContent(this);
        }

        public Builder readFrom(Parcel parcel) {
            return readFrom((ShareVideoContent) parcel.readParcelable(ShareVideoContent.class.getClassLoader()));
        }

        public Builder readFrom(ShareVideoContent shareVideoContent) {
            return shareVideoContent == null ? this : ((Builder) super.readFrom(shareVideoContent)).setContentDescription(shareVideoContent.getContentDescription()).setContentTitle(shareVideoContent.getContentTitle()).setPreviewPhoto(shareVideoContent.getPreviewPhoto()).setVideo(shareVideoContent.getVideo());
        }

        public Builder setContentDescription(@Nullable String str) {
            this.contentDescription = str;
            return this;
        }

        public Builder setContentTitle(@Nullable String str) {
            this.contentTitle = str;
            return this;
        }

        public Builder setPreviewPhoto(@Nullable SharePhoto sharePhoto) {
            this.previewPhoto = sharePhoto == null ? null : new SharePhoto.Builder().readFrom(sharePhoto).build();
            return this;
        }

        public Builder setVideo(@Nullable ShareVideo shareVideo) {
            if (shareVideo != null) {
                this.video = new ShareVideo.Builder().readFrom(shareVideo).build();
            }
            return this;
        }
    }

    ShareVideoContent(Parcel parcel) {
        super(parcel);
        this.contentDescription = parcel.readString();
        this.contentTitle = parcel.readString();
        SharePhoto.Builder readFrom = new SharePhoto.Builder().readFrom(parcel);
        if (readFrom.getImageUrl() == null && readFrom.getBitmap() == null) {
            this.previewPhoto = null;
        } else {
            this.previewPhoto = readFrom.build();
        }
        this.video = new ShareVideo.Builder().readFrom(parcel).build();
    }

    private ShareVideoContent(Builder builder) {
        super((ShareContent.Builder) builder);
        this.contentDescription = builder.contentDescription;
        this.contentTitle = builder.contentTitle;
        this.previewPhoto = builder.previewPhoto;
        this.video = builder.video;
    }

    public int describeContents() {
        return 0;
    }

    @Nullable
    public String getContentDescription() {
        return this.contentDescription;
    }

    @Nullable
    public String getContentTitle() {
        return this.contentTitle;
    }

    @Nullable
    public SharePhoto getPreviewPhoto() {
        return this.previewPhoto;
    }

    @Nullable
    public ShareVideo getVideo() {
        return this.video;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.contentDescription);
        parcel.writeString(this.contentTitle);
        parcel.writeParcelable(this.previewPhoto, 0);
        parcel.writeParcelable(this.video, 0);
    }
}
