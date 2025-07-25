package com.tapjoy.mraid.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import com.tapjoy.TapjoyLog;
import com.tapjoy.mraid.controller.Abstract;
import com.tapjoy.mraid.listener.Player;

public class MraidPlayer extends VideoView implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {
    private static String TAG = "MRAID Player";
    private static String transientText = "Loading. Please Wait..";
    private AudioManager aManager = ((AudioManager) getContext().getSystemService("audio"));
    private ImageButton closeImageButton;
    private String contentURL;
    private boolean isReleased;
    private Player listener;
    private int mutedVolume;
    private Abstract.PlayerProperties playProperties;
    private RelativeLayout transientLayout;

    public MraidPlayer(Context context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public void addTransientMessage() {
        if (!this.playProperties.inline) {
            this.transientLayout = new RelativeLayout(getContext());
            this.transientLayout.setLayoutParams(getLayoutParams());
            TextView textView = new TextView(getContext());
            textView.setText(transientText);
            textView.setTextColor(-1);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.addRule(13);
            this.transientLayout.addView(textView, layoutParams);
            ((ViewGroup) getParent()).addView(this.transientLayout);
        }
    }

    /* access modifiers changed from: package-private */
    public void clearTransientMessage() {
        if (this.transientLayout != null) {
            ((ViewGroup) getParent()).removeView(this.transientLayout);
        }
    }

    /* access modifiers changed from: package-private */
    public void displayControl() {
        if (this.playProperties.showControl()) {
            MediaController mediaController = new MediaController(getContext());
            setMediaController(mediaController);
            mediaController.setAnchorView(this);
        }
    }

    public ImageButton getCloseImageButton() {
        return this.closeImageButton;
    }

    /* access modifiers changed from: package-private */
    public void loadContent() {
        this.contentURL = this.contentURL.trim();
        this.contentURL = Utils.convert(this.contentURL);
        if (this.contentURL != null || this.listener == null) {
            setVideoURI(Uri.parse(this.contentURL));
            TapjoyLog.d("player", Uri.parse(this.contentURL).toString());
            displayControl();
            startContent();
            return;
        }
        removeView();
        this.listener.onError();
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        if (this.playProperties.doLoop()) {
            start();
        } else if (this.playProperties.exitOnComplete() || this.playProperties.inline) {
            releasePlayer();
        }
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        TapjoyLog.i(TAG, "Player error : " + i);
        clearTransientMessage();
        removeView();
        if (this.listener == null) {
            return false;
        }
        this.listener.onError();
        return false;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        clearTransientMessage();
        if (this.listener != null) {
            this.listener.onPrepared();
        }
    }

    public void playAudio() {
        loadContent();
    }

    public void playVideo() {
        if (this.playProperties.doMute()) {
            this.mutedVolume = this.aManager.getStreamVolume(3);
            this.aManager.setStreamVolume(3, 0, 4);
        }
        loadContent();
    }

    public void releasePlayer() {
        if (!this.isReleased) {
            this.isReleased = true;
            stopPlayback();
            removeView();
            if (this.playProperties != null && this.playProperties.doMute()) {
                unMute();
            }
            if (this.listener != null) {
                this.listener.onComplete();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void removeView() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
    }

    public void setListener(Player player) {
        this.listener = player;
    }

    public void setPlayData(Abstract.PlayerProperties playerProperties, String str) {
        this.isReleased = false;
        this.playProperties = playerProperties;
        this.contentURL = str;
    }

    /* access modifiers changed from: package-private */
    public void startContent() {
        setOnCompletionListener(this);
        setOnErrorListener(this);
        setOnPreparedListener(this);
        if (!this.playProperties.inline) {
            addTransientMessage();
        }
        if (this.playProperties.isAutoPlay()) {
            start();
        }
    }

    /* access modifiers changed from: package-private */
    public void unMute() {
        this.aManager.setStreamVolume(3, this.mutedVolume, 4);
    }
}
