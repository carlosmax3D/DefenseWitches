package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.r;
import com.google.android.gms.maps.model.internal.g;

public final class TileOverlayOptions implements SafeParcelable {
    public static final TileOverlayOptionsCreator CREATOR = new TileOverlayOptionsCreator();
    private float Cw;
    private boolean Cx;
    /* access modifiers changed from: private */
    public g Da;
    private TileProvider Db;
    private boolean Dc;
    private final int kg;

    public TileOverlayOptions() {
        this.Cx = true;
        this.Dc = true;
        this.kg = 1;
    }

    TileOverlayOptions(int i, IBinder iBinder, boolean z, float f, boolean z2) {
        this.Cx = true;
        this.Dc = true;
        this.kg = i;
        this.Da = g.a.aq(iBinder);
        this.Db = this.Da == null ? null : new TileProvider() {
            private final g Dd = TileOverlayOptions.this.Da;

            public Tile getTile(int i, int i2, int i3) {
                try {
                    return this.Dd.getTile(i, i2, i3);
                } catch (RemoteException e) {
                    return null;
                }
            }
        };
        this.Cx = z;
        this.Cw = f;
        this.Dc = z2;
    }

    public int describeContents() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public IBinder eI() {
        return this.Da.asBinder();
    }

    public TileOverlayOptions fadeIn(boolean z) {
        this.Dc = z;
        return this;
    }

    public boolean getFadeIn() {
        return this.Dc;
    }

    public TileProvider getTileProvider() {
        return this.Db;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public float getZIndex() {
        return this.Cw;
    }

    public boolean isVisible() {
        return this.Cx;
    }

    public TileOverlayOptions tileProvider(final TileProvider tileProvider) {
        this.Db = tileProvider;
        this.Da = this.Db == null ? null : new g.a() {
            public Tile getTile(int i, int i2, int i3) {
                return tileProvider.getTile(i, i2, i3);
            }
        };
        return this;
    }

    public TileOverlayOptions visible(boolean z) {
        this.Cx = z;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (r.eD()) {
            j.a(this, parcel, i);
        } else {
            TileOverlayOptionsCreator.a(this, parcel, i);
        }
    }

    public TileOverlayOptions zIndex(float f) {
        this.Cw = f;
        return this;
    }
}
