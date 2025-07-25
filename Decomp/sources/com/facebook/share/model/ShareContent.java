package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareContent.Builder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ShareContent<P extends ShareContent, E extends Builder> implements ShareModel {
    private final Uri contentUrl;
    private final List<String> peopleIds;
    private final String placeId;
    private final String ref;

    public static abstract class Builder<P extends ShareContent, E extends Builder> implements ShareModelBuilder<P, E> {
        /* access modifiers changed from: private */
        public Uri contentUrl;
        /* access modifiers changed from: private */
        public List<String> peopleIds;
        /* access modifiers changed from: private */
        public String placeId;
        /* access modifiers changed from: private */
        public String ref;

        public E readFrom(P p) {
            return p == null ? this : setContentUrl(p.getContentUrl()).setPeopleIds(p.getPeopleIds()).setPlaceId(p.getPlaceId()).setRef(p.getRef());
        }

        public E setContentUrl(@Nullable Uri uri) {
            this.contentUrl = uri;
            return this;
        }

        public E setPeopleIds(@Nullable List<String> list) {
            this.peopleIds = list == null ? null : Collections.unmodifiableList(list);
            return this;
        }

        public E setPlaceId(@Nullable String str) {
            this.placeId = str;
            return this;
        }

        public E setRef(@Nullable String str) {
            this.ref = str;
            return this;
        }
    }

    ShareContent(Parcel parcel) {
        this.contentUrl = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
        this.peopleIds = readUnmodifiableStringList(parcel);
        this.placeId = parcel.readString();
        this.ref = parcel.readString();
    }

    protected ShareContent(Builder builder) {
        this.contentUrl = builder.contentUrl;
        this.peopleIds = builder.peopleIds;
        this.placeId = builder.placeId;
        this.ref = builder.ref;
    }

    private List<String> readUnmodifiableStringList(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        parcel.readStringList(arrayList);
        if (arrayList.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(arrayList);
    }

    public int describeContents() {
        return 0;
    }

    @Nullable
    public Uri getContentUrl() {
        return this.contentUrl;
    }

    @Nullable
    public List<String> getPeopleIds() {
        return this.peopleIds;
    }

    @Nullable
    public String getPlaceId() {
        return this.placeId;
    }

    @Nullable
    public String getRef() {
        return this.ref;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.contentUrl, 0);
        parcel.writeStringList(this.peopleIds);
        parcel.writeString(this.placeId);
        parcel.writeString(this.ref);
    }
}
