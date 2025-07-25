package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaStatus;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class dl extends df {
    private static final long me = TimeUnit.SECONDS.toMillis(3);
    private static final long mf = TimeUnit.HOURS.toMillis(24);
    private static final long mg = TimeUnit.SECONDS.toMillis(5);
    private static final long mh = TimeUnit.SECONDS.toMillis(1);
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private long mi;
    private MediaStatus mj;
    /* access modifiers changed from: private */
    public final Cdo mk = new Cdo(mf);
    /* access modifiers changed from: private */
    public final Cdo ml = new Cdo(mg);
    /* access modifiers changed from: private */
    public final Cdo mm = new Cdo(me);
    /* access modifiers changed from: private */
    public final Cdo mn = new Cdo(me);
    /* access modifiers changed from: private */
    public final Cdo mo = new Cdo(me);
    private final Runnable mp = new a();
    /* access modifiers changed from: private */
    public boolean mq;

    private class a implements Runnable {
        private a() {
        }

        public void run() {
            boolean z = false;
            boolean unused = dl.this.mq = false;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            dl.this.mk.d(elapsedRealtime, 3);
            dl.this.ml.d(elapsedRealtime, 3);
            dl.this.mm.d(elapsedRealtime, 3);
            dl.this.mn.d(elapsedRealtime, 3);
            dl.this.mo.d(elapsedRealtime, 3);
            synchronized (Cdo.mw) {
                if (dl.this.mk.bf() || dl.this.ml.bf() || dl.this.mm.bf() || dl.this.mn.bf() || dl.this.mo.bf()) {
                    z = true;
                }
            }
            dl.this.o(z);
        }
    }

    public dl() {
        super("urn:x-cast:com.google.cast.media", "MediaControlChannel");
        bd();
    }

    private void a(long j, JSONObject jSONObject) throws JSONException {
        int i;
        boolean z = true;
        boolean i2 = this.mk.i(j);
        boolean z2 = this.ml.bf() && !this.ml.i(j);
        if ((!this.mm.bf() || this.mm.i(j)) && (!this.mn.bf() || this.mn.i(j))) {
            z = false;
        }
        int i3 = z2 ? 2 : 0;
        if (z) {
            i3 |= 1;
        }
        if (i2 || this.mj == null) {
            this.mj = new MediaStatus(jSONObject);
            this.mi = SystemClock.elapsedRealtime();
            i = 7;
        } else {
            i = this.mj.a(jSONObject, i3);
        }
        if ((i & 1) != 0) {
            this.mi = SystemClock.elapsedRealtime();
            onStatusUpdated();
        }
        if ((i & 2) != 0) {
            this.mi = SystemClock.elapsedRealtime();
            onStatusUpdated();
        }
        if ((i & 4) != 0) {
            onMetadataUpdated();
        }
        this.mk.c(j, 0);
        this.ml.c(j, 0);
        this.mm.c(j, 0);
        this.mn.c(j, 0);
        this.mo.c(j, 0);
    }

    private void bd() {
        o(false);
        this.mi = 0;
        this.mj = null;
        this.mk.clear();
        this.ml.clear();
        this.mm.clear();
    }

    /* access modifiers changed from: private */
    public void o(boolean z) {
        if (this.mq != z) {
            this.mq = z;
            if (z) {
                this.mHandler.postDelayed(this.mp, mh);
            } else {
                this.mHandler.removeCallbacks(this.mp);
            }
        }
    }

    public final void B(String str) {
        this.lx.b("message received: %s", str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("type");
            long optLong = jSONObject.optLong("requestId", -1);
            if (string.equals("MEDIA_STATUS")) {
                JSONArray jSONArray = jSONObject.getJSONArray("status");
                if (jSONArray.length() > 0) {
                    a(optLong, jSONArray.getJSONObject(0));
                    return;
                }
                this.mj = null;
                onStatusUpdated();
                onMetadataUpdated();
                this.mo.c(optLong, 0);
            } else if (string.equals("INVALID_PLAYER_STATE")) {
                this.lx.d("received unexpected error: Invalid Player State.", new Object[0]);
                JSONObject optJSONObject = jSONObject.optJSONObject("customData");
                this.mk.b(optLong, 1, optJSONObject);
                this.ml.b(optLong, 1, optJSONObject);
                this.mm.b(optLong, 1, optJSONObject);
                this.mn.b(optLong, 1, optJSONObject);
                this.mo.b(optLong, 1, optJSONObject);
            } else if (string.equals("LOAD_FAILED")) {
                this.mk.b(optLong, 1, jSONObject.optJSONObject("customData"));
            } else if (string.equals("LOAD_CANCELLED")) {
                this.mk.b(optLong, 2, jSONObject.optJSONObject("customData"));
            } else if (string.equals("INVALID_REQUEST")) {
                this.lx.d("received unexpected error: Invalid Request.", new Object[0]);
                JSONObject optJSONObject2 = jSONObject.optJSONObject("customData");
                this.mk.b(optLong, 1, optJSONObject2);
                this.ml.b(optLong, 1, optJSONObject2);
                this.mm.b(optLong, 1, optJSONObject2);
                this.mn.b(optLong, 1, optJSONObject2);
                this.mo.b(optLong, 1, optJSONObject2);
            }
        } catch (JSONException e) {
            this.lx.d("Message is malformed (%s); ignoring: %s", e.getMessage(), str);
        }
    }

    public long a(dn dnVar) throws IOException {
        JSONObject jSONObject = new JSONObject();
        long aS = aS();
        this.mo.a(aS, dnVar);
        o(true);
        try {
            jSONObject.put("requestId", aS);
            jSONObject.put("type", "GET_STATUS");
            if (this.mj != null) {
                jSONObject.put("mediaSessionId", this.mj.aQ());
            }
        } catch (JSONException e) {
        }
        a(jSONObject.toString(), aS, (String) null);
        return aS;
    }

    public long a(dn dnVar, double d, JSONObject jSONObject) throws IOException, IllegalStateException, IllegalArgumentException {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Volume cannot be " + d);
        }
        JSONObject jSONObject2 = new JSONObject();
        long aS = aS();
        this.mm.a(aS, dnVar);
        o(true);
        try {
            jSONObject2.put("requestId", aS);
            jSONObject2.put("type", "SET_VOLUME");
            jSONObject2.put("mediaSessionId", aQ());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("level", d);
            jSONObject2.put("volume", jSONObject3);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        a(jSONObject2.toString(), aS, (String) null);
        return aS;
    }

    public long a(dn dnVar, long j, int i, JSONObject jSONObject) throws IOException, IllegalStateException {
        JSONObject jSONObject2 = new JSONObject();
        long aS = aS();
        this.ml.a(aS, dnVar);
        o(true);
        try {
            jSONObject2.put("requestId", aS);
            jSONObject2.put("type", "SEEK");
            jSONObject2.put("mediaSessionId", aQ());
            jSONObject2.put("currentTime", dh.h(j));
            if (i == 1) {
                jSONObject2.put("resumeState", "PLAYBACK_START");
            } else if (i == 2) {
                jSONObject2.put("resumeState", "PLAYBACK_PAUSE");
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        a(jSONObject2.toString(), aS, (String) null);
        return aS;
    }

    public long a(dn dnVar, MediaInfo mediaInfo, boolean z, long j, JSONObject jSONObject) throws IOException {
        JSONObject jSONObject2 = new JSONObject();
        long aS = aS();
        this.mk.a(aS, dnVar);
        o(true);
        try {
            jSONObject2.put("requestId", aS);
            jSONObject2.put("type", "LOAD");
            jSONObject2.put("media", mediaInfo.aP());
            jSONObject2.put("autoplay", z);
            jSONObject2.put("currentTime", dh.h(j));
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        a(jSONObject2.toString(), aS, (String) null);
        return aS;
    }

    public long a(dn dnVar, boolean z, JSONObject jSONObject) throws IOException, IllegalStateException {
        JSONObject jSONObject2 = new JSONObject();
        long aS = aS();
        this.mn.a(aS, dnVar);
        o(true);
        try {
            jSONObject2.put("requestId", aS);
            jSONObject2.put("type", "SET_VOLUME");
            jSONObject2.put("mediaSessionId", aQ());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("muted", z);
            jSONObject2.put("volume", jSONObject3);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        a(jSONObject2.toString(), aS, (String) null);
        return aS;
    }

    public void a(long j, int i) {
        this.mk.c(j, i);
        this.ml.c(j, i);
        this.mm.c(j, i);
        this.mn.c(j, i);
        this.mo.c(j, i);
    }

    public long aQ() throws IllegalStateException {
        if (this.mj != null) {
            return this.mj.aQ();
        }
        throw new IllegalStateException("No current media session");
    }

    public void aT() {
        bd();
    }

    public void c(JSONObject jSONObject) throws IOException {
        JSONObject jSONObject2 = new JSONObject();
        long aS = aS();
        try {
            jSONObject2.put("requestId", aS);
            jSONObject2.put("type", "PAUSE");
            jSONObject2.put("mediaSessionId", aQ());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        a(jSONObject2.toString(), aS, (String) null);
    }

    public void d(JSONObject jSONObject) throws IOException {
        JSONObject jSONObject2 = new JSONObject();
        long aS = aS();
        try {
            jSONObject2.put("requestId", aS);
            jSONObject2.put("type", "STOP");
            jSONObject2.put("mediaSessionId", aQ());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        a(jSONObject2.toString(), aS, (String) null);
    }

    public void e(JSONObject jSONObject) throws IOException, IllegalStateException {
        JSONObject jSONObject2 = new JSONObject();
        long aS = aS();
        try {
            jSONObject2.put("requestId", aS);
            jSONObject2.put("type", "PLAY");
            jSONObject2.put("mediaSessionId", aQ());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException e) {
        }
        a(jSONObject2.toString(), aS, (String) null);
    }

    public long getApproximateStreamPosition() {
        MediaInfo mediaInfo = getMediaInfo();
        if (mediaInfo == null || this.mi == 0) {
            return 0;
        }
        double playbackRate = this.mj.getPlaybackRate();
        long streamPosition = this.mj.getStreamPosition();
        int playerState = this.mj.getPlayerState();
        if (playbackRate == 0.0d || playerState != 2) {
            return streamPosition;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.mi;
        long j = elapsedRealtime < 0 ? 0 : elapsedRealtime;
        if (j == 0) {
            return streamPosition;
        }
        long streamDuration = mediaInfo.getStreamDuration();
        long j2 = streamPosition + ((long) (((double) j) * playbackRate));
        if (j2 <= streamDuration) {
            streamDuration = j2 < 0 ? 0 : j2;
        }
        return streamDuration;
    }

    public MediaInfo getMediaInfo() {
        if (this.mj == null) {
            return null;
        }
        return this.mj.getMediaInfo();
    }

    public MediaStatus getMediaStatus() {
        return this.mj;
    }

    public long getStreamDuration() {
        MediaInfo mediaInfo = getMediaInfo();
        if (mediaInfo != null) {
            return mediaInfo.getStreamDuration();
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onMetadataUpdated() {
    }

    /* access modifiers changed from: protected */
    public void onStatusUpdated() {
    }
}
