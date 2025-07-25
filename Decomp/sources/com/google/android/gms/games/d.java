package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;

public final class d extends b implements Player {
    private final a tG;

    private static final class a {
        public final String tH;
        public final String tI;
        public final String tJ;
        public final String tK;
        public final String tL;
        public final String tM;
        public final String tN;

        public a(String str) {
            if (TextUtils.isEmpty(str)) {
                this.tH = "external_player_id";
                this.tI = "profile_name";
                this.tJ = "profile_icon_image_uri";
                this.tK = "profile_hi_res_image_uri";
                this.tL = "last_updated";
                this.tM = "is_in_circles";
                this.tN = "played_with_timestamp";
                return;
            }
            this.tH = str + "external_player_id";
            this.tI = str + "profile_name";
            this.tJ = str + "profile_icon_image_uri";
            this.tK = str + "profile_hi_res_image_uri";
            this.tL = str + "last_updated";
            this.tM = str + "is_in_circles";
            this.tN = str + "played_with_timestamp";
        }
    }

    public d(DataHolder dataHolder, int i) {
        this(dataHolder, i, (String) null);
    }

    public d(DataHolder dataHolder, int i, String str) {
        super(dataHolder, i);
        this.tG = new a(str);
    }

    public int db() {
        return getInteger(this.tG.tM);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return PlayerEntity.a(this, obj);
    }

    public Player freeze() {
        return new PlayerEntity(this);
    }

    public String getDisplayName() {
        return getString(this.tG.tI);
    }

    public void getDisplayName(CharArrayBuffer charArrayBuffer) {
        a(this.tG.tI, charArrayBuffer);
    }

    public Uri getHiResImageUri() {
        return L(this.tG.tK);
    }

    public Uri getIconImageUri() {
        return L(this.tG.tJ);
    }

    public long getLastPlayedWithTimestamp() {
        if (!hasColumn(this.tG.tN)) {
            return -1;
        }
        return getLong(this.tG.tN);
    }

    public String getPlayerId() {
        return getString(this.tG.tH);
    }

    public long getRetrievedTimestamp() {
        return getLong(this.tG.tL);
    }

    public boolean hasHiResImage() {
        return getHiResImageUri() != null;
    }

    public boolean hasIconImage() {
        return getIconImageUri() != null;
    }

    public int hashCode() {
        return PlayerEntity.a(this);
    }

    public String toString() {
        return PlayerEntity.b((Player) this);
    }

    public void writeToParcel(Parcel parcel, int i) {
        ((PlayerEntity) freeze()).writeToParcel(parcel, i);
    }
}
