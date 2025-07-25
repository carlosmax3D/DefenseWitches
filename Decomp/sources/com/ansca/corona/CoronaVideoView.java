package com.ansca.corona;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.VideoView;

public class CoronaVideoView extends VideoView implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private boolean mActivityResumed = true;
    private CoronaRuntime mCoronaRuntime;
    private int mCurrentTime = 0;
    private MediaPlayer mMediaPlayer;
    private float mPrevVolume = 1.0f;
    private boolean mScreenLocked = false;
    private boolean mTouchTogglesPlay = false;
    private float mVolume = 1.0f;

    private class VideoViewEndedTask implements CoronaRuntimeTask {
        private int fId;

        public VideoViewEndedTask(int i) {
            this.fId = i;
        }

        public void executeUsing(CoronaRuntime coronaRuntime) {
            JavaToNativeShim.videoViewEnded(coronaRuntime, this.fId);
        }
    }

    private class VideoViewPreparedTask implements CoronaRuntimeTask {
        private int fId;

        public VideoViewPreparedTask(int i) {
            this.fId = i;
        }

        public void executeUsing(CoronaRuntime coronaRuntime) {
            JavaToNativeShim.videoViewPrepared(coronaRuntime, this.fId);
        }
    }

    public CoronaVideoView(Context context, CoronaRuntime coronaRuntime) {
        super(context);
        setOnPreparedListener(this);
        setOnCompletionListener(this);
        this.mCoronaRuntime = coronaRuntime;
    }

    public float getVolume() {
        return this.mVolume;
    }

    public boolean isMuted() {
        return this.mVolume <= 0.0f;
    }

    public boolean isTouchTogglesPlay() {
        return this.mTouchTogglesPlay;
    }

    public void mute(boolean z) {
        if (z) {
            float f = this.mVolume;
            setVolume(0.0f);
            this.mPrevVolume = f;
            return;
        }
        setVolume(this.mPrevVolume);
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        if (this.mCoronaRuntime != null && this.mCoronaRuntime.isRunning()) {
            this.mCoronaRuntime.getTaskDispatcher().send(new VideoViewEndedTask(getId()));
        }
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        this.mMediaPlayer = mediaPlayer;
        setVolume(this.mVolume);
        if (this.mCoronaRuntime != null && this.mCoronaRuntime.isRunning()) {
            this.mCoronaRuntime.getTaskDispatcher().send(new VideoViewPreparedTask(getId()));
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mTouchTogglesPlay) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 0:
                if (!isPlaying()) {
                    start();
                    return false;
                } else if (!canPause()) {
                    return false;
                } else {
                    pause();
                    return false;
                }
            default:
                return false;
        }
    }

    public void pause() {
        super.pause();
        this.mCurrentTime = getCurrentPosition();
    }

    public void setActivityResumed(boolean z) {
        this.mActivityResumed = z;
    }

    public void setScreenLocked(boolean z) {
        this.mScreenLocked = z;
    }

    public void setVideoURI(String str) {
        Uri createVideoURLFromString = MediaManager.createVideoURLFromString(str, getContext());
        if (createVideoURLFromString != null) {
            super.setVideoURI(createVideoURLFromString);
        }
    }

    public void setVolume(float f) {
        if (this.mMediaPlayer != null) {
            this.mMediaPlayer.setVolume(f, f);
        }
        this.mVolume = f;
        this.mPrevVolume = f;
    }

    public void start() {
        if (!this.mScreenLocked && this.mActivityResumed) {
            seekTo(this.mCurrentTime);
            super.start();
        }
    }

    public void touchTogglesPlay(boolean z) {
        this.mTouchTogglesPlay = z;
    }
}
