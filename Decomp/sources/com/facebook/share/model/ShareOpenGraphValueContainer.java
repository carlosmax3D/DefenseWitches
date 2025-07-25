package com.facebook.share.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.share.model.ShareOpenGraphValueContainer;
import com.facebook.share.model.ShareOpenGraphValueContainer.Builder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public abstract class ShareOpenGraphValueContainer<P extends ShareOpenGraphValueContainer, E extends Builder> implements ShareModel {
    private final Bundle bundle;

    public static abstract class Builder<P extends ShareOpenGraphValueContainer, E extends Builder> implements ShareModelBuilder<P, E> {
        /* access modifiers changed from: private */
        public Bundle bundle = new Bundle();

        public E putBoolean(String str, boolean z) {
            this.bundle.putBoolean(str, z);
            return this;
        }

        public E putBooleanArray(String str, @Nullable boolean[] zArr) {
            this.bundle.putBooleanArray(str, zArr);
            return this;
        }

        public E putDouble(String str, double d) {
            this.bundle.putDouble(str, d);
            return this;
        }

        public E putDoubleArray(String str, @Nullable double[] dArr) {
            this.bundle.putDoubleArray(str, dArr);
            return this;
        }

        public E putInt(String str, int i) {
            this.bundle.putInt(str, i);
            return this;
        }

        public E putIntArray(String str, @Nullable int[] iArr) {
            this.bundle.putIntArray(str, iArr);
            return this;
        }

        public E putLong(String str, long j) {
            this.bundle.putLong(str, j);
            return this;
        }

        public E putLongArray(String str, @Nullable long[] jArr) {
            this.bundle.putLongArray(str, jArr);
            return this;
        }

        public E putObject(String str, @Nullable ShareOpenGraphObject shareOpenGraphObject) {
            this.bundle.putParcelable(str, shareOpenGraphObject);
            return this;
        }

        public E putObjectArrayList(String str, @Nullable ArrayList<ShareOpenGraphObject> arrayList) {
            this.bundle.putParcelableArrayList(str, arrayList);
            return this;
        }

        public E putPhoto(String str, @Nullable SharePhoto sharePhoto) {
            this.bundle.putParcelable(str, sharePhoto);
            return this;
        }

        public E putPhotoArrayList(String str, @Nullable ArrayList<SharePhoto> arrayList) {
            this.bundle.putParcelableArrayList(str, arrayList);
            return this;
        }

        public E putString(String str, @Nullable String str2) {
            this.bundle.putString(str, str2);
            return this;
        }

        public E putStringArrayList(String str, @Nullable ArrayList<String> arrayList) {
            this.bundle.putStringArrayList(str, arrayList);
            return this;
        }

        public E readFrom(P p) {
            if (p != null) {
                this.bundle.putAll(p.getBundle());
            }
            return this;
        }
    }

    ShareOpenGraphValueContainer(Parcel parcel) {
        this.bundle = parcel.readBundle(Builder.class.getClassLoader());
    }

    protected ShareOpenGraphValueContainer(Builder<P, E> builder) {
        this.bundle = (Bundle) builder.bundle.clone();
    }

    public int describeContents() {
        return 0;
    }

    @Nullable
    public Object get(String str) {
        return this.bundle.get(str);
    }

    public boolean getBoolean(String str, boolean z) {
        return this.bundle.getBoolean(str, z);
    }

    @Nullable
    public boolean[] getBooleanArray(String str) {
        return this.bundle.getBooleanArray(str);
    }

    public Bundle getBundle() {
        return (Bundle) this.bundle.clone();
    }

    public double getDouble(String str, double d) {
        return this.bundle.getDouble(str, d);
    }

    @Nullable
    public double[] getDoubleArray(String str) {
        return this.bundle.getDoubleArray(str);
    }

    public int getInt(String str, int i) {
        return this.bundle.getInt(str, i);
    }

    @Nullable
    public int[] getIntArray(String str) {
        return this.bundle.getIntArray(str);
    }

    public long getLong(String str, long j) {
        return this.bundle.getLong(str, j);
    }

    @Nullable
    public long[] getLongArray(String str) {
        return this.bundle.getLongArray(str);
    }

    public ShareOpenGraphObject getObject(String str) {
        Object obj = this.bundle.get(str);
        if (obj instanceof ShareOpenGraphObject) {
            return (ShareOpenGraphObject) obj;
        }
        return null;
    }

    @Nullable
    public ArrayList<ShareOpenGraphObject> getObjectArrayList(String str) {
        ArrayList parcelableArrayList = this.bundle.getParcelableArrayList(str);
        if (parcelableArrayList == null) {
            return null;
        }
        ArrayList<ShareOpenGraphObject> arrayList = new ArrayList<>();
        Iterator it = parcelableArrayList.iterator();
        while (it.hasNext()) {
            Parcelable parcelable = (Parcelable) it.next();
            if (parcelable instanceof ShareOpenGraphObject) {
                arrayList.add((ShareOpenGraphObject) parcelable);
            }
        }
        return arrayList;
    }

    @Nullable
    public SharePhoto getPhoto(String str) {
        Parcelable parcelable = this.bundle.getParcelable(str);
        if (parcelable instanceof SharePhoto) {
            return (SharePhoto) parcelable;
        }
        return null;
    }

    @Nullable
    public ArrayList<SharePhoto> getPhotoArrayList(String str) {
        ArrayList parcelableArrayList = this.bundle.getParcelableArrayList(str);
        if (parcelableArrayList == null) {
            return null;
        }
        ArrayList<SharePhoto> arrayList = new ArrayList<>();
        Iterator it = parcelableArrayList.iterator();
        while (it.hasNext()) {
            Parcelable parcelable = (Parcelable) it.next();
            if (parcelable instanceof SharePhoto) {
                arrayList.add((SharePhoto) parcelable);
            }
        }
        return arrayList;
    }

    @Nullable
    public String getString(String str) {
        return this.bundle.getString(str);
    }

    @Nullable
    public ArrayList<String> getStringArrayList(String str) {
        return this.bundle.getStringArrayList(str);
    }

    public Set<String> keySet() {
        return this.bundle.keySet();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.bundle);
    }
}
