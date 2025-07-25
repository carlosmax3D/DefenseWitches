package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;

public interface Player extends Parcelable, Freezable<Player> {
    int db();

    String getDisplayName();

    void getDisplayName(CharArrayBuffer charArrayBuffer);

    Uri getHiResImageUri();

    Uri getIconImageUri();

    long getLastPlayedWithTimestamp();

    String getPlayerId();

    long getRetrievedTimestamp();

    boolean hasHiResImage();

    boolean hasIconImage();
}
