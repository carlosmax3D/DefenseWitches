package com.google.android.gms.cast;

import com.google.android.gms.internal.dh;
import org.json.JSONException;
import org.json.JSONObject;

public final class MediaStatus {
    public static final long COMMAND_PAUSE = 1;
    public static final long COMMAND_SEEK = 2;
    public static final long COMMAND_SET_VOLUME = 4;
    public static final long COMMAND_SKIP_BACKWARD = 32;
    public static final long COMMAND_SKIP_FORWARD = 16;
    public static final long COMMAND_TOGGLE_MUTE = 8;
    public static final int IDLE_REASON_CANCELED = 2;
    public static final int IDLE_REASON_ERROR = 4;
    public static final int IDLE_REASON_FINISHED = 1;
    public static final int IDLE_REASON_INTERRUPTED = 3;
    public static final int IDLE_REASON_NONE = 0;
    public static final int PLAYER_STATE_BUFFERING = 4;
    public static final int PLAYER_STATE_IDLE = 1;
    public static final int PLAYER_STATE_PAUSED = 3;
    public static final int PLAYER_STATE_PLAYING = 2;
    public static final int PLAYER_STATE_UNKNOWN = 0;
    private JSONObject kM;
    private MediaInfo kN;
    private long kV;
    private double kW;
    private int kX;
    private int kY;
    private long kZ;
    private long la;
    private double lb;
    private boolean lc;

    public MediaStatus(JSONObject jSONObject) throws JSONException {
        a(jSONObject, 0);
    }

    public int a(JSONObject jSONObject, int i) throws JSONException {
        int i2;
        int i3 = 2;
        long j = jSONObject.getLong("mediaSessionId");
        if (j != this.kV) {
            this.kV = j;
            i2 = 1;
        } else {
            i2 = 0;
        }
        if (jSONObject.has("playerState")) {
            String string = jSONObject.getString("playerState");
            int i4 = string.equals("IDLE") ? 1 : string.equals("PLAYING") ? 2 : string.equals("PAUSED") ? 3 : string.equals("BUFFERING") ? 4 : 0;
            if (i4 != this.kX) {
                this.kX = i4;
                i2 |= 2;
            }
            if (i4 == 1 && jSONObject.has("idleReason")) {
                String string2 = jSONObject.getString("idleReason");
                if (!string2.equals("CANCELLED")) {
                    i3 = string2.equals("INTERRUPTED") ? 3 : string2.equals("FINISHED") ? 1 : string2.equals("ERROR") ? 4 : 0;
                }
                if (i3 != this.kY) {
                    this.kY = i3;
                    i2 |= 2;
                }
            }
        }
        if (jSONObject.has("playbackRate")) {
            double d = jSONObject.getDouble("playbackRate");
            if (this.kW != d) {
                this.kW = d;
                i2 |= 2;
            }
        }
        if (jSONObject.has("currentTime") && (i & 2) == 0) {
            long b = dh.b(jSONObject.getDouble("currentTime"));
            if (b != this.kZ) {
                this.kZ = b;
                i2 |= 2;
            }
        }
        if (jSONObject.has("supportedMediaCommands")) {
            long j2 = jSONObject.getLong("supportedMediaCommands");
            if (j2 != this.la) {
                this.la = j2;
                i2 |= 2;
            }
        }
        if (jSONObject.has("volume") && (i & 1) == 0) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("volume");
            double d2 = jSONObject2.getDouble("level");
            if (d2 != this.lb) {
                this.lb = d2;
                i2 |= 2;
            }
            boolean z = jSONObject2.getBoolean("muted");
            if (z != this.lc) {
                this.lc = z;
                i2 |= 2;
            }
        }
        if (jSONObject.has("customData")) {
            this.kM = jSONObject.getJSONObject("customData");
            i2 |= 2;
        }
        if (!jSONObject.has("media")) {
            return i2;
        }
        JSONObject jSONObject3 = jSONObject.getJSONObject("media");
        this.kN = new MediaInfo(jSONObject3);
        int i5 = i2 | 2;
        return jSONObject3.has("metadata") ? i5 | 4 : i5;
    }

    public long aQ() {
        return this.kV;
    }

    public JSONObject getCustomData() {
        return this.kM;
    }

    public int getIdleReason() {
        return this.kY;
    }

    public MediaInfo getMediaInfo() {
        return this.kN;
    }

    public double getPlaybackRate() {
        return this.kW;
    }

    public int getPlayerState() {
        return this.kX;
    }

    public long getStreamPosition() {
        return this.kZ;
    }

    public double getStreamVolume() {
        return this.lb;
    }

    public boolean isMediaCommandSupported(long j) {
        return (this.la & j) != 0;
    }

    public boolean isMute() {
        return this.lc;
    }
}
