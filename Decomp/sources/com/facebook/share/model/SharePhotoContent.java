package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.SharePhoto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SharePhotoContent extends ShareContent<SharePhotoContent, Builder> {
    public static final Parcelable.Creator<SharePhotoContent> CREATOR = new Parcelable.Creator<SharePhotoContent>() {
        public SharePhotoContent createFromParcel(Parcel parcel) {
            return new SharePhotoContent(parcel);
        }

        public SharePhotoContent[] newArray(int i) {
            return new SharePhotoContent[i];
        }
    };
    private final List<SharePhoto> photos;

    public static class Builder extends ShareContent.Builder<SharePhotoContent, Builder> {
        /* access modifiers changed from: private */
        public final List<SharePhoto> photos = new ArrayList();

        public Builder addPhoto(@Nullable SharePhoto sharePhoto) {
            if (sharePhoto != null) {
                this.photos.add(new SharePhoto.Builder().readFrom(sharePhoto).build());
            }
            return this;
        }

        public Builder addPhotos(@Nullable List<SharePhoto> list) {
            if (list != null) {
                for (SharePhoto addPhoto : list) {
                    addPhoto(addPhoto);
                }
            }
            return this;
        }

        public SharePhotoContent build() {
            return new SharePhotoContent(this);
        }

        public Builder readFrom(Parcel parcel) {
            return readFrom((SharePhotoContent) parcel.readParcelable(SharePhotoContent.class.getClassLoader()));
        }

        public Builder readFrom(SharePhotoContent sharePhotoContent) {
            return sharePhotoContent == null ? this : ((Builder) super.readFrom(sharePhotoContent)).addPhotos(sharePhotoContent.getPhotos());
        }

        public Builder setPhotos(@Nullable List<SharePhoto> list) {
            this.photos.clear();
            addPhotos(list);
            return this;
        }
    }

    SharePhotoContent(Parcel parcel) {
        super(parcel);
        this.photos = Collections.unmodifiableList(SharePhoto.Builder.readListFrom(parcel));
    }

    private SharePhotoContent(Builder builder) {
        super((ShareContent.Builder) builder);
        this.photos = Collections.unmodifiableList(builder.photos);
    }

    public int describeContents() {
        return 0;
    }

    @Nullable
    public List<SharePhoto> getPhotos() {
        return this.photos;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        SharePhoto.Builder.writeListTo(parcel, this.photos);
    }
}
