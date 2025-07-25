package com.google.android.gms.cast;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.dg;
import com.google.android.gms.internal.dl;
import com.google.android.gms.internal.dm;
import com.google.android.gms.internal.dn;
import java.io.IOException;
import org.json.JSONObject;

public class RemoteMediaPlayer implements Cast.MessageReceivedCallback {
    public static final int RESUME_STATE_PAUSE = 2;
    public static final int RESUME_STATE_PLAY = 1;
    public static final int RESUME_STATE_UNCHANGED = 0;
    public static final int STATUS_CANCELED = 2;
    public static final int STATUS_FAILED = 1;
    public static final int STATUS_REPLACED = 4;
    public static final int STATUS_SUCCEEDED = 0;
    public static final int STATUS_TIMED_OUT = 3;
    /* access modifiers changed from: private */
    public final Object fx = new Object();
    /* access modifiers changed from: private */
    public final dl ld = new dl() {
        /* access modifiers changed from: protected */
        public void onMetadataUpdated() {
            RemoteMediaPlayer.this.onMetadataUpdated();
        }

        /* access modifiers changed from: protected */
        public void onStatusUpdated() {
            RemoteMediaPlayer.this.onStatusUpdated();
        }
    };
    /* access modifiers changed from: private */
    public final a le = new a();
    private OnMetadataUpdatedListener lf;
    private OnStatusUpdatedListener lg;

    public interface MediaChannelResult extends Result {
    }

    public interface OnMetadataUpdatedListener {
        void onMetadataUpdated();
    }

    public interface OnStatusUpdatedListener {
        void onStatusUpdated();
    }

    private class a implements dm {
        private GoogleApiClient lr;
        private long ls = 0;

        /* renamed from: com.google.android.gms.cast.RemoteMediaPlayer$a$a  reason: collision with other inner class name */
        private final class C0000a implements ResultCallback<Status> {
            private final long lt;

            C0000a(long j) {
                this.lt = j;
            }

            /* renamed from: j */
            public void onResult(Status status) {
                if (!status.isSuccess()) {
                    RemoteMediaPlayer.this.ld.a(this.lt, status.getStatusCode());
                }
            }
        }

        public a() {
        }

        public void a(String str, String str2, long j, String str3) throws IOException {
            if (this.lr == null) {
                throw new IOException("No GoogleApiClient available");
            }
            Cast.CastApi.sendMessage(this.lr, str, str2).setResultCallback(new C0000a(j));
        }

        public long aR() {
            long j = this.ls + 1;
            this.ls = j;
            return j;
        }

        public void b(GoogleApiClient googleApiClient) {
            this.lr = googleApiClient;
        }
    }

    private static abstract class b extends Cast.a<MediaChannelResult> {
        dn lv = new dn() {
            public void a(long j, int i, JSONObject jSONObject) {
                b.this.a(new c(new Status(i), jSONObject));
            }

            public void g(long j) {
                b.this.a(b.this.e(new Status(4)));
            }
        };

        b() {
        }

        /* renamed from: k */
        public MediaChannelResult e(final Status status) {
            return new MediaChannelResult() {
                public Status getStatus() {
                    return status;
                }
            };
        }
    }

    private static final class c implements MediaChannelResult {
        private final Status jY;
        private final JSONObject kM;

        c(Status status, JSONObject jSONObject) {
            this.jY = status;
            this.kM = jSONObject;
        }

        public Status getStatus() {
            return this.jY;
        }
    }

    public RemoteMediaPlayer() {
        this.ld.a(this.le);
    }

    /* access modifiers changed from: private */
    public void onMetadataUpdated() {
        if (this.lf != null) {
            this.lf.onMetadataUpdated();
        }
    }

    /* access modifiers changed from: private */
    public void onStatusUpdated() {
        if (this.lg != null) {
            this.lg.onStatusUpdated();
        }
    }

    public long getApproximateStreamPosition() {
        long approximateStreamPosition;
        synchronized (this.fx) {
            approximateStreamPosition = this.ld.getApproximateStreamPosition();
        }
        return approximateStreamPosition;
    }

    public MediaInfo getMediaInfo() {
        MediaInfo mediaInfo;
        synchronized (this.fx) {
            mediaInfo = this.ld.getMediaInfo();
        }
        return mediaInfo;
    }

    public MediaStatus getMediaStatus() {
        MediaStatus mediaStatus;
        synchronized (this.fx) {
            mediaStatus = this.ld.getMediaStatus();
        }
        return mediaStatus;
    }

    public String getNamespace() {
        return this.ld.getNamespace();
    }

    public long getStreamDuration() {
        long streamDuration;
        synchronized (this.fx) {
            streamDuration = this.ld.getStreamDuration();
        }
        return streamDuration;
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient googleApiClient, MediaInfo mediaInfo) {
        return load(googleApiClient, mediaInfo, true, 0, (JSONObject) null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient googleApiClient, MediaInfo mediaInfo, boolean z) {
        return load(googleApiClient, mediaInfo, z, 0, (JSONObject) null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient googleApiClient, MediaInfo mediaInfo, boolean z, long j) {
        return load(googleApiClient, mediaInfo, z, j, (JSONObject) null);
    }

    public PendingResult<MediaChannelResult> load(GoogleApiClient googleApiClient, MediaInfo mediaInfo, boolean z, long j, JSONObject jSONObject) {
        final GoogleApiClient googleApiClient2 = googleApiClient;
        final MediaInfo mediaInfo2 = mediaInfo;
        final boolean z2 = z;
        final long j2 = j;
        final JSONObject jSONObject2 = jSONObject;
        return googleApiClient.b(new b() {
            /* access modifiers changed from: protected */
            public void a(dg dgVar) {
                synchronized (RemoteMediaPlayer.this.fx) {
                    RemoteMediaPlayer.this.le.b(googleApiClient2);
                    try {
                        RemoteMediaPlayer.this.ld.a(this.lv, mediaInfo2, z2, j2, jSONObject2);
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                    } catch (IOException e) {
                        a(e(new Status(1)));
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                    } catch (Throwable th) {
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                        throw th;
                    }
                }
            }
        });
    }

    public void onMessageReceived(CastDevice castDevice, String str, String str2) {
        this.ld.B(str2);
    }

    public void pause(GoogleApiClient googleApiClient) throws IOException {
        pause(googleApiClient, (JSONObject) null);
    }

    public void pause(GoogleApiClient googleApiClient, JSONObject jSONObject) throws IOException {
        synchronized (this.fx) {
            this.le.b(googleApiClient);
            try {
                this.ld.c(jSONObject);
            } finally {
                this.le.b((GoogleApiClient) null);
            }
        }
    }

    public void play(GoogleApiClient googleApiClient) throws IOException, IllegalStateException {
        play(googleApiClient, (JSONObject) null);
    }

    public void play(GoogleApiClient googleApiClient, JSONObject jSONObject) throws IOException, IllegalStateException {
        synchronized (this.fx) {
            this.le.b(googleApiClient);
            try {
                this.ld.e(jSONObject);
            } finally {
                this.le.b((GoogleApiClient) null);
            }
        }
    }

    public PendingResult<MediaChannelResult> requestStatus(final GoogleApiClient googleApiClient) {
        return googleApiClient.b(new b() {
            /* access modifiers changed from: protected */
            public void a(dg dgVar) {
                synchronized (RemoteMediaPlayer.this.fx) {
                    RemoteMediaPlayer.this.le.b(googleApiClient);
                    try {
                        RemoteMediaPlayer.this.ld.a(this.lv);
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                    } catch (IOException e) {
                        a(e(new Status(1)));
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                    } catch (Throwable th) {
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                        throw th;
                    }
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient googleApiClient, long j) {
        return seek(googleApiClient, j, 0, (JSONObject) null);
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient googleApiClient, long j, int i) {
        return seek(googleApiClient, j, i, (JSONObject) null);
    }

    public PendingResult<MediaChannelResult> seek(GoogleApiClient googleApiClient, long j, int i, JSONObject jSONObject) {
        final GoogleApiClient googleApiClient2 = googleApiClient;
        final long j2 = j;
        final int i2 = i;
        final JSONObject jSONObject2 = jSONObject;
        return googleApiClient.b(new b() {
            /* access modifiers changed from: protected */
            public void a(dg dgVar) {
                synchronized (RemoteMediaPlayer.this.fx) {
                    RemoteMediaPlayer.this.le.b(googleApiClient2);
                    try {
                        RemoteMediaPlayer.this.ld.a(this.lv, j2, i2, jSONObject2);
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                    } catch (IOException e) {
                        a(e(new Status(1)));
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                    } catch (Throwable th) {
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                        throw th;
                    }
                }
            }
        });
    }

    public void setOnMetadataUpdatedListener(OnMetadataUpdatedListener onMetadataUpdatedListener) {
        this.lf = onMetadataUpdatedListener;
    }

    public void setOnStatusUpdatedListener(OnStatusUpdatedListener onStatusUpdatedListener) {
        this.lg = onStatusUpdatedListener;
    }

    public PendingResult<MediaChannelResult> setStreamMute(GoogleApiClient googleApiClient, boolean z) {
        return setStreamMute(googleApiClient, z, (JSONObject) null);
    }

    public PendingResult<MediaChannelResult> setStreamMute(final GoogleApiClient googleApiClient, final boolean z, final JSONObject jSONObject) {
        return googleApiClient.b(new b() {
            /* access modifiers changed from: protected */
            public void a(dg dgVar) {
                synchronized (RemoteMediaPlayer.this.fx) {
                    RemoteMediaPlayer.this.le.b(googleApiClient);
                    try {
                        RemoteMediaPlayer.this.ld.a(this.lv, z, jSONObject);
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                    } catch (IllegalStateException e) {
                        a(e(new Status(1)));
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                    } catch (IOException e2) {
                        a(e(new Status(1)));
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                    } catch (Throwable th) {
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                        throw th;
                    }
                }
            }
        });
    }

    public PendingResult<MediaChannelResult> setStreamVolume(GoogleApiClient googleApiClient, double d) throws IllegalArgumentException {
        return setStreamVolume(googleApiClient, d, (JSONObject) null);
    }

    public PendingResult<MediaChannelResult> setStreamVolume(GoogleApiClient googleApiClient, double d, JSONObject jSONObject) throws IllegalArgumentException {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Volume cannot be " + d);
        }
        final GoogleApiClient googleApiClient2 = googleApiClient;
        final double d2 = d;
        final JSONObject jSONObject2 = jSONObject;
        return googleApiClient.b(new b() {
            /* access modifiers changed from: protected */
            public void a(dg dgVar) {
                synchronized (RemoteMediaPlayer.this.fx) {
                    RemoteMediaPlayer.this.le.b(googleApiClient2);
                    try {
                        RemoteMediaPlayer.this.ld.a(this.lv, d2, jSONObject2);
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                    } catch (IllegalStateException e) {
                        a(e(new Status(1)));
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                    } catch (IllegalArgumentException e2) {
                        a(e(new Status(1)));
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                    } catch (IOException e3) {
                        a(e(new Status(1)));
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                    } catch (Throwable th) {
                        RemoteMediaPlayer.this.le.b((GoogleApiClient) null);
                        throw th;
                    }
                }
                return;
            }
        });
    }

    public void stop(GoogleApiClient googleApiClient) throws IOException {
        stop(googleApiClient, (JSONObject) null);
    }

    public void stop(GoogleApiClient googleApiClient, JSONObject jSONObject) throws IOException {
        synchronized (this.fx) {
            this.le.b(googleApiClient);
            try {
                this.ld.d(jSONObject);
            } finally {
                this.le.b((GoogleApiClient) null);
            }
        }
    }
}
