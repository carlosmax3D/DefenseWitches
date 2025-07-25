package com.google.android.gms.cast;

import android.text.TextUtils;
import com.google.android.gms.internal.dh;
import com.google.android.gms.internal.ee;
import com.google.android.gms.internal.fe;
import org.json.JSONException;
import org.json.JSONObject;

public final class MediaInfo {
    public static final int STREAM_TYPE_BUFFERED = 1;
    public static final int STREAM_TYPE_INVALID = -1;
    public static final int STREAM_TYPE_LIVE = 2;
    public static final int STREAM_TYPE_NONE = 0;
    private final String kH;
    private int kI;
    private String kJ;
    private MediaMetadata kK;
    private long kL;
    private JSONObject kM;

    public static class Builder {
        private final MediaInfo kN;

        public Builder(String str) throws IllegalArgumentException {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("Content ID cannot be empty");
            }
            this.kN = new MediaInfo(str);
        }

        public MediaInfo build() throws IllegalArgumentException {
            this.kN.aO();
            return this.kN;
        }

        public Builder setContentType(String str) throws IllegalArgumentException {
            this.kN.setContentType(str);
            return this;
        }

        public Builder setCustomData(JSONObject jSONObject) {
            this.kN.a(jSONObject);
            return this;
        }

        public Builder setMetadata(MediaMetadata mediaMetadata) {
            this.kN.a(mediaMetadata);
            return this;
        }

        public Builder setStreamDuration(long j) throws IllegalArgumentException {
            this.kN.f(j);
            return this;
        }

        public Builder setStreamType(int i) throws IllegalArgumentException {
            this.kN.setStreamType(i);
            return this;
        }
    }

    MediaInfo(String str) throws IllegalArgumentException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("content ID cannot be null or empty");
        }
        this.kH = str;
        this.kI = -1;
    }

    MediaInfo(JSONObject jSONObject) throws JSONException {
        this.kH = jSONObject.getString("contentId");
        String string = jSONObject.getString("streamType");
        if ("NONE".equals(string)) {
            this.kI = 0;
        } else if ("BUFFERED".equals(string)) {
            this.kI = 1;
        } else if ("LIVE".equals(string)) {
            this.kI = 2;
        } else {
            this.kI = -1;
        }
        this.kJ = jSONObject.getString("contentType");
        if (jSONObject.has("metadata")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("metadata");
            this.kK = new MediaMetadata(jSONObject2.getInt("metadataType"));
            this.kK.b(jSONObject2);
        }
        this.kL = dh.b(jSONObject.optDouble("duration", 0.0d));
        this.kM = jSONObject.optJSONObject("customData");
    }

    /* access modifiers changed from: package-private */
    public void a(MediaMetadata mediaMetadata) {
        this.kK = mediaMetadata;
    }

    /* access modifiers changed from: package-private */
    public void a(JSONObject jSONObject) {
        this.kM = jSONObject;
    }

    /* access modifiers changed from: package-private */
    public void aO() throws IllegalArgumentException {
        if (TextUtils.isEmpty(this.kH)) {
            throw new IllegalArgumentException("content ID cannot be null or empty");
        } else if (TextUtils.isEmpty(this.kJ)) {
            throw new IllegalArgumentException("content type cannot be null or empty");
        } else if (this.kI == -1) {
            throw new IllegalArgumentException("a valid stream type must be specified");
        }
    }

    public JSONObject aP() {
        String str;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("contentId", this.kH);
            switch (this.kI) {
                case 1:
                    str = "BUFFERED";
                    break;
                case 2:
                    str = "LIVE";
                    break;
                default:
                    str = "NONE";
                    break;
            }
            jSONObject.put("streamType", str);
            if (this.kJ != null) {
                jSONObject.put("contentType", this.kJ);
            }
            if (this.kK != null) {
                jSONObject.put("metadata", this.kK.aP());
            }
            jSONObject.put("duration", dh.h(this.kL));
            if (this.kM != null) {
                jSONObject.put("customData", this.kM);
            }
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaInfo)) {
            return false;
        }
        MediaInfo mediaInfo = (MediaInfo) obj;
        if ((this.kM == null) != (mediaInfo.kM == null)) {
            return false;
        }
        if (this.kM != null && mediaInfo.kM != null && !fe.d(this.kM, mediaInfo.kM)) {
            return false;
        }
        if (!dh.a(this.kH, mediaInfo.kH) || this.kI != mediaInfo.kI || !dh.a(this.kJ, mediaInfo.kJ) || !dh.a(this.kK, mediaInfo.kK) || this.kL != mediaInfo.kL) {
            z = false;
        }
        return z;
    }

    /* access modifiers changed from: package-private */
    public void f(long j) throws IllegalArgumentException {
        if (j < 0) {
            throw new IllegalArgumentException("Stream duration cannot be negative");
        }
        this.kL = j;
    }

    public String getContentId() {
        return this.kH;
    }

    public String getContentType() {
        return this.kJ;
    }

    public JSONObject getCustomData() {
        return this.kM;
    }

    public MediaMetadata getMetadata() {
        return this.kK;
    }

    public long getStreamDuration() {
        return this.kL;
    }

    public int getStreamType() {
        return this.kI;
    }

    public int hashCode() {
        return ee.hashCode(this.kH, Integer.valueOf(this.kI), this.kJ, this.kK, Long.valueOf(this.kL), String.valueOf(this.kM));
    }

    /* access modifiers changed from: package-private */
    public void setContentType(String str) throws IllegalArgumentException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("content type cannot be null or empty");
        }
        this.kJ = str;
    }

    /* access modifiers changed from: package-private */
    public void setStreamType(int i) throws IllegalArgumentException {
        if (i < -1 || i > 2) {
            throw new IllegalArgumentException("invalid stream type");
        }
        this.kI = i;
    }
}
