package com.facebook.share.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public final class SharePhoto implements ShareModel {
    public static final Parcelable.Creator<SharePhoto> CREATOR = new Parcelable.Creator<SharePhoto>() {
        public SharePhoto createFromParcel(Parcel parcel) {
            return new SharePhoto(parcel);
        }

        public SharePhoto[] newArray(int i) {
            return new SharePhoto[i];
        }
    };
    private final Bitmap bitmap;
    private final Uri imageUrl;
    private final boolean userGenerated;

    public static final class Builder implements ShareModelBuilder<SharePhoto, Builder> {
        /* access modifiers changed from: private */
        public Bitmap bitmap;
        /* access modifiers changed from: private */
        public Uri imageUrl;
        /* access modifiers changed from: private */
        public boolean userGenerated;

        public static List<SharePhoto> readListFrom(Parcel parcel) {
            ArrayList arrayList = new ArrayList();
            parcel.readTypedList(arrayList, SharePhoto.CREATOR);
            return arrayList;
        }

        public static void writeListTo(Parcel parcel, List<SharePhoto> list) {
            ArrayList arrayList = new ArrayList();
            for (SharePhoto add : list) {
                arrayList.add(add);
            }
            parcel.writeTypedList(arrayList);
        }

        public SharePhoto build() {
            return new SharePhoto(this);
        }

        /* access modifiers changed from: package-private */
        public Bitmap getBitmap() {
            return this.bitmap;
        }

        /* access modifiers changed from: package-private */
        public Uri getImageUrl() {
            return this.imageUrl;
        }

        public Builder readFrom(Parcel parcel) {
            return readFrom((SharePhoto) parcel.readParcelable(SharePhoto.class.getClassLoader()));
        }

        public Builder readFrom(SharePhoto sharePhoto) {
            return sharePhoto == null ? this : setBitmap(sharePhoto.getBitmap()).setImageUrl(sharePhoto.getImageUrl()).setUserGenerated(sharePhoto.getUserGenerated());
        }

        public Builder setBitmap(@Nullable Bitmap bitmap2) {
            this.bitmap = bitmap2;
            return this;
        }

        public Builder setImageUrl(@Nullable Uri uri) {
            this.imageUrl = uri;
            return this;
        }

        public Builder setUserGenerated(boolean z) {
            this.userGenerated = z;
            return this;
        }
    }

    SharePhoto(Parcel parcel) {
        this.bitmap = (Bitmap) parcel.readParcelable(Bitmap.class.getClassLoader());
        this.imageUrl = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
        this.userGenerated = parcel.readByte() != 0;
    }

    private SharePhoto(Builder builder) {
        this.bitmap = builder.bitmap;
        this.imageUrl = builder.imageUrl;
        this.userGenerated = builder.userGenerated;
    }

    public int describeContents() {
        return 0;
    }

    @Nullable
    public Bitmap getBitmap() {
        return this.bitmap;
    }

    @Nullable
    public Uri getImageUrl() {
        return this.imageUrl;
    }

    public boolean getUserGenerated() {
        return this.userGenerated;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 0;
        parcel.writeParcelable(this.bitmap, 0);
        parcel.writeParcelable(this.imageUrl, 0);
        if (this.userGenerated) {
            i2 = 1;
        }
        parcel.writeByte((byte) i2);
    }
}
