package com.facebook.share.internal;

import android.os.Parcel;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;
import com.facebook.share.widget.LikeView;

public class LikeContent implements ShareModel {
    private final String objectId;
    private final LikeView.ObjectType objectType;

    public static class Builder implements ShareModelBuilder<LikeContent, Builder> {
        /* access modifiers changed from: private */
        public String objectId;
        /* access modifiers changed from: private */
        public LikeView.ObjectType objectType = LikeView.ObjectType.UNKNOWN;

        public LikeContent build() {
            return new LikeContent(this);
        }

        public Builder readFrom(Parcel parcel) {
            return readFrom((LikeContent) parcel.readParcelable(LikeContent.class.getClassLoader()));
        }

        public Builder readFrom(LikeContent likeContent) {
            return likeContent == null ? this : setObjectId(likeContent.getObjectId()).setObjectType(likeContent.getObjectType());
        }

        public Builder setObjectId(String str) {
            this.objectId = str;
            return this;
        }

        public Builder setObjectType(LikeView.ObjectType objectType2) {
            if (objectType2 == null) {
                objectType2 = LikeView.ObjectType.UNKNOWN;
            }
            this.objectType = objectType2;
            return this;
        }
    }

    LikeContent(Parcel parcel) {
        this.objectId = parcel.readString();
        this.objectType = LikeView.ObjectType.fromInt(parcel.readInt());
    }

    private LikeContent(Builder builder) {
        this.objectId = builder.objectId;
        this.objectType = builder.objectType;
    }

    public int describeContents() {
        return 0;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public LikeView.ObjectType getObjectType() {
        return this.objectType;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.objectId);
        parcel.writeInt(this.objectType.getValue());
    }
}
