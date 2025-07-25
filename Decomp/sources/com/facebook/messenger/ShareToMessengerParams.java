package com.facebook.messenger;

import android.net.Uri;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ShareToMessengerParams {
    public static final Set<String> VALID_EXTERNAL_URI_SCHEMES;
    public static final Set<String> VALID_MIME_TYPES;
    public static final Set<String> VALID_URI_SCHEMES;
    public final Uri externalUri;
    public final String metaData;
    public final String mimeType;
    public final Uri uri;

    static {
        HashSet hashSet = new HashSet();
        hashSet.add("image/*");
        hashSet.add("image/jpeg");
        hashSet.add("image/png");
        hashSet.add("image/gif");
        hashSet.add("image/webp");
        hashSet.add("video/*");
        hashSet.add("video/mp4");
        hashSet.add("audio/*");
        hashSet.add("audio/mpeg");
        VALID_MIME_TYPES = Collections.unmodifiableSet(hashSet);
        HashSet hashSet2 = new HashSet();
        hashSet2.add("content");
        hashSet2.add("android.resource");
        hashSet2.add("file");
        VALID_URI_SCHEMES = Collections.unmodifiableSet(hashSet2);
        HashSet hashSet3 = new HashSet();
        hashSet3.add("http");
        hashSet3.add("https");
        VALID_EXTERNAL_URI_SCHEMES = Collections.unmodifiableSet(hashSet3);
    }

    ShareToMessengerParams(ShareToMessengerParamsBuilder shareToMessengerParamsBuilder) {
        this.uri = shareToMessengerParamsBuilder.getUri();
        this.mimeType = shareToMessengerParamsBuilder.getMimeType();
        this.metaData = shareToMessengerParamsBuilder.getMetaData();
        this.externalUri = shareToMessengerParamsBuilder.getExternalUri();
        if (this.uri == null) {
            throw new NullPointerException("Must provide non-null uri");
        } else if (this.mimeType == null) {
            throw new NullPointerException("Must provide mimeType");
        } else if (!VALID_URI_SCHEMES.contains(this.uri.getScheme())) {
            throw new IllegalArgumentException("Unsupported URI scheme: " + this.uri.getScheme());
        } else if (!VALID_MIME_TYPES.contains(this.mimeType)) {
            throw new IllegalArgumentException("Unsupported mime-type: " + this.mimeType);
        } else if (this.externalUri != null && !VALID_EXTERNAL_URI_SCHEMES.contains(this.externalUri.getScheme())) {
            throw new IllegalArgumentException("Unsupported external uri scheme: " + this.externalUri.getScheme());
        }
    }

    public static ShareToMessengerParamsBuilder newBuilder(Uri uri2, String str) {
        return new ShareToMessengerParamsBuilder(uri2, str);
    }
}
