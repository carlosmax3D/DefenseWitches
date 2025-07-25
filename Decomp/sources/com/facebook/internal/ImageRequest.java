package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import java.util.Locale;

public class ImageRequest {
    private static final String AUTHORITY = "graph.facebook.com";
    private static final String HEIGHT_PARAM = "height";
    private static final String MIGRATION_PARAM = "migration_overrides";
    private static final String MIGRATION_VALUE = "{october_2012:true}";
    private static final String PATH = "%s/picture";
    private static final String SCHEME = "https";
    public static final int UNSPECIFIED_DIMENSION = 0;
    private static final String WIDTH_PARAM = "width";
    private boolean allowCachedRedirects;
    private Callback callback;
    private Object callerTag;
    private Context context;
    private Uri imageUri;

    public static class Builder {
        /* access modifiers changed from: private */
        public boolean allowCachedRedirects;
        /* access modifiers changed from: private */
        public Callback callback;
        /* access modifiers changed from: private */
        public Object callerTag;
        /* access modifiers changed from: private */
        public Context context;
        /* access modifiers changed from: private */
        public Uri imageUrl;

        public Builder(Context context2, Uri uri) {
            Validate.notNull(uri, "imageUri");
            this.context = context2;
            this.imageUrl = uri;
        }

        public ImageRequest build() {
            return new ImageRequest(this);
        }

        public Builder setAllowCachedRedirects(boolean z) {
            this.allowCachedRedirects = z;
            return this;
        }

        public Builder setCallback(Callback callback2) {
            this.callback = callback2;
            return this;
        }

        public Builder setCallerTag(Object obj) {
            this.callerTag = obj;
            return this;
        }
    }

    public interface Callback {
        void onCompleted(ImageResponse imageResponse);
    }

    private ImageRequest(Builder builder) {
        this.context = builder.context;
        this.imageUri = builder.imageUrl;
        this.callback = builder.callback;
        this.allowCachedRedirects = builder.allowCachedRedirects;
        this.callerTag = builder.callerTag == null ? new Object() : builder.callerTag;
    }

    public static Uri getProfilePictureUri(String str, int i, int i2) {
        Validate.notNullOrEmpty(str, "userId");
        int max = Math.max(i, 0);
        int max2 = Math.max(i2, 0);
        if (max == 0 && max2 == 0) {
            throw new IllegalArgumentException("Either width or height must be greater than 0");
        }
        Uri.Builder path = new Uri.Builder().scheme(SCHEME).authority(AUTHORITY).path(String.format(Locale.US, PATH, new Object[]{str}));
        if (max2 != 0) {
            path.appendQueryParameter("height", String.valueOf(max2));
        }
        if (max != 0) {
            path.appendQueryParameter("width", String.valueOf(max));
        }
        path.appendQueryParameter(MIGRATION_PARAM, MIGRATION_VALUE);
        return path.build();
    }

    public Callback getCallback() {
        return this.callback;
    }

    public Object getCallerTag() {
        return this.callerTag;
    }

    public Context getContext() {
        return this.context;
    }

    public Uri getImageUri() {
        return this.imageUri;
    }

    public boolean isCachedRedirectAllowed() {
        return this.allowCachedRedirects;
    }
}
