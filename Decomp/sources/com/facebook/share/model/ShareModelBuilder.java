package com.facebook.share.model;

import android.os.Parcel;
import com.facebook.share.ShareBuilder;
import com.facebook.share.model.ShareModel;
import com.facebook.share.model.ShareModelBuilder;

public interface ShareModelBuilder<P extends ShareModel, E extends ShareModelBuilder> extends ShareBuilder<P, E> {
    E readFrom(Parcel parcel);

    E readFrom(P p);
}
