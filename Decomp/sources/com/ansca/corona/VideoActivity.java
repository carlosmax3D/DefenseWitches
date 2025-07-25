package com.ansca.corona;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;
import com.ansca.corona.events.VideoEndedTask;
import com.tapjoy.TapjoyConstants;

public class VideoActivity extends Activity {
    private int mCurrentPosition = 0;
    /* access modifiers changed from: private */
    public int myVideoId;
    private VideoView myVideoView;

    public void finish() {
        super.finish();
        if ((getIntent().getFlags() & 65536) != 0) {
            overridePendingTransition(0, 0);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        for (CoronaRuntime next : CoronaRuntimeProvider.getAllCoronaRuntimes()) {
            if (!next.wasDisposed()) {
                next.getTaskDispatcher().send(new VideoEndedTask(this.myVideoId));
            }
            MediaManager mediaManager = next.getController().getMediaManager();
            if (mediaManager != null) {
                mediaManager.resumeAll();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this, Build.VERSION.SDK_INT >= 14 ? 16974120 : Build.VERSION.SDK_INT >= 11 ? 16973931 : 16973835);
        FrameLayout frameLayout = new FrameLayout(contextThemeWrapper);
        setContentView(frameLayout);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1, 17);
        this.myVideoView = new VideoView(contextThemeWrapper);
        frameLayout.addView(this.myVideoView, layoutParams);
        setVolumeControlStream(3);
        this.myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                for (CoronaRuntime next : CoronaRuntimeProvider.getAllCoronaRuntimes()) {
                    if (!next.wasDisposed()) {
                        next.getTaskDispatcher().send(new VideoEndedTask(VideoActivity.this.myVideoId));
                    }
                    MediaManager mediaManager = next.getController().getMediaManager();
                    if (mediaManager != null) {
                        mediaManager.resumeAll();
                    }
                }
                VideoActivity.this.finish();
            }
        });
        if (getIntent().getExtras().getBoolean("media_controls_enabled")) {
            this.myVideoView.setMediaController(new MediaController(contextThemeWrapper));
        }
        this.myVideoView.setVideoURI(Uri.parse(getIntent().getExtras().getString("video_uri")));
        this.myVideoId = getIntent().getExtras().getInt(TapjoyConstants.TJC_VIDEO_ID);
        this.myVideoView.start();
    }

    public void onPause() {
        super.onPause();
        this.myVideoView.pause();
        this.mCurrentPosition = this.myVideoView.getCurrentPosition();
    }

    public void onResume() {
        super.onResume();
        this.myVideoView.seekTo(this.mCurrentPosition);
    }
}
