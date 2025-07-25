package com.google.android.gms.internal;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public final class bo extends FrameLayout implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {
    private final MediaController gQ;
    private final a gR = new a(this);
    private final VideoView gS;
    private long gT;
    private String gU;
    private final cw gv;

    private static final class a {
        private final Runnable ep;
        /* access modifiers changed from: private */
        public volatile boolean gV = false;

        public a(final bo boVar) {
            this.ep = new Runnable() {
                private final WeakReference<bo> gW = new WeakReference<>(boVar);

                public void run() {
                    bo boVar = (bo) this.gW.get();
                    if (!a.this.gV && boVar != null) {
                        boVar.ag();
                        a.this.ah();
                    }
                }
            };
        }

        public void ah() {
            cs.iI.postDelayed(this.ep, 250);
        }

        public void cancel() {
            this.gV = true;
            cs.iI.removeCallbacks(this.ep);
        }
    }

    public bo(Context context, cw cwVar) {
        super(context);
        this.gv = cwVar;
        this.gS = new VideoView(context);
        addView(this.gS, new FrameLayout.LayoutParams(-1, -1, 17));
        this.gQ = new MediaController(context);
        this.gR.ah();
        this.gS.setOnCompletionListener(this);
        this.gS.setOnPreparedListener(this);
        this.gS.setOnErrorListener(this);
    }

    private static void a(cw cwVar, String str) {
        a(cwVar, str, (Map<String, String>) new HashMap(1));
    }

    public static void a(cw cwVar, String str, String str2) {
        boolean z = str2 == null;
        HashMap hashMap = new HashMap(z ? 2 : 3);
        hashMap.put("what", str);
        if (!z) {
            hashMap.put("extra", str2);
        }
        a(cwVar, "error", (Map<String, String>) hashMap);
    }

    private static void a(cw cwVar, String str, String str2, String str3) {
        HashMap hashMap = new HashMap(2);
        hashMap.put(str2, str3);
        a(cwVar, str, (Map<String, String>) hashMap);
    }

    private static void a(cw cwVar, String str, Map<String, String> map) {
        map.put("event", str);
        cwVar.a("onVideoEvent", (Map<String, ?>) map);
    }

    public void af() {
        if (!TextUtils.isEmpty(this.gU)) {
            this.gS.setVideoPath(this.gU);
        } else {
            a(this.gv, "no_src", (String) null);
        }
    }

    public void ag() {
        long currentPosition = (long) this.gS.getCurrentPosition();
        if (this.gT != currentPosition) {
            a(this.gv, "timeupdate", "time", String.valueOf(((float) currentPosition) / 1000.0f));
            this.gT = currentPosition;
        }
    }

    public void b(MotionEvent motionEvent) {
        this.gS.dispatchTouchEvent(motionEvent);
    }

    public void destroy() {
        this.gR.cancel();
        this.gS.stopPlayback();
    }

    public void i(boolean z) {
        if (z) {
            this.gS.setMediaController(this.gQ);
            return;
        }
        this.gQ.hide();
        this.gS.setMediaController((MediaController) null);
    }

    public void n(String str) {
        this.gU = str;
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        a(this.gv, "ended");
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        a(this.gv, String.valueOf(i), String.valueOf(i2));
        return true;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        a(this.gv, "canplaythrough", "duration", String.valueOf(((float) this.gS.getDuration()) / 1000.0f));
    }

    public void pause() {
        this.gS.pause();
    }

    public void play() {
        this.gS.start();
    }

    public void seekTo(int i) {
        this.gS.seekTo(i);
    }
}
