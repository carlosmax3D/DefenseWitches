package android.support.v4.media.session;

import android.graphics.Bitmap;
import android.media.Rating;
import android.media.RemoteControlClient;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompatApi14;
import twitter4j.MediaEntity;

public class MediaSessionCompatApi19 {
    private static final String METADATA_KEY_ALBUM_ART = "android.media.metadata.ALBUM_ART";
    private static final String METADATA_KEY_ART = "android.media.metadata.ART";
    private static final String METADATA_KEY_RATING = "android.media.metadata.RATING";
    private static final String METADATA_KEY_USER_RATING = "android.media.metadata.USER_RATING";

    static class OnMetadataUpdateListener<T extends MediaSessionCompatApi14.Callback> implements RemoteControlClient.OnMetadataUpdateListener {
        protected final T mCallback;

        public OnMetadataUpdateListener(T t) {
            this.mCallback = t;
        }

        public void onMetadataUpdate(int i, Object obj) {
            if (i == 268435457 && (obj instanceof Rating)) {
                this.mCallback.onSetRating(obj);
            }
        }
    }

    static void addNewMetadata(Bundle bundle, RemoteControlClient.MetadataEditor metadataEditor) {
        if (bundle.containsKey("android.media.metadata.RATING")) {
            metadataEditor.putObject(MediaEntity.Size.CROP, bundle.getParcelable("android.media.metadata.RATING"));
        }
        if (bundle.containsKey("android.media.metadata.USER_RATING")) {
            metadataEditor.putObject(268435457, bundle.getParcelable("android.media.metadata.USER_RATING"));
        }
        if (bundle.containsKey("android.media.metadata.ART")) {
            metadataEditor.putBitmap(100, (Bitmap) bundle.getParcelable("android.media.metadata.ART"));
        } else if (bundle.containsKey("android.media.metadata.ALBUM_ART")) {
            metadataEditor.putBitmap(100, (Bitmap) bundle.getParcelable("android.media.metadata.ALBUM_ART"));
        }
    }

    public static Object createMetadataUpdateListener(MediaSessionCompatApi14.Callback callback) {
        return new OnMetadataUpdateListener(callback);
    }

    public static void setMetadata(Object obj, Bundle bundle, boolean z) {
        RemoteControlClient.MetadataEditor editMetadata = ((RemoteControlClient) obj).editMetadata(true);
        MediaSessionCompatApi14.buildOldMetadata(bundle, editMetadata);
        addNewMetadata(bundle, editMetadata);
        if (z && Build.VERSION.SDK_INT > 19) {
            editMetadata.addEditableKey(268435457);
        }
        editMetadata.apply();
    }

    public static void setOnMetadataUpdateListener(Object obj, Object obj2) {
        ((RemoteControlClient) obj).setMetadataUpdateListener((RemoteControlClient.OnMetadataUpdateListener) obj2);
    }
}
