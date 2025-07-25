package com.tapjoy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import com.tapjoy.TJAdUnitConstants;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import jp.stargarage.g2metrics.BuildConfig;
import org.json.JSONArray;

public class TapjoyVideoView extends Activity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnInfoListener {
    private static final String BUNDLE_DIALOG_SHOWING = "dialog_showing";
    private static final String BUNDLE_SEEK_TIME = "seek_time";
    private static final String COUNTDOWN_IMAGE = "countdown_image.png";
    private static final int DIALOG_CONNECTIVITY_LOST_ID = 1;
    private static final int DIALOG_WARNING_ID = 0;
    private static final String TAG = "VideoView";
    private static boolean streamingVideo = false;
    private static int textSize = 15;
    /* access modifiers changed from: private */
    public static OldTapjoyVideoData videoData;
    private static boolean videoErrorOccurred = false;
    private boolean allowBackKey = true;
    private String cancelMessage = "Currency will not be awarded, are you sure you want to cancel the video?";
    /* access modifiers changed from: private */
    public boolean clickRequestSuccess = false;
    private String connectivityMessage = "A network connection is necessary to view videos. You will be able to complete the offer and receive your reward on the next connect.";
    private ImageView countdownImage;
    private int countdownTextMargin = -5;
    private int countdownTextSize = 50;
    Dialog dialog;
    /* access modifiers changed from: private */
    public boolean dialogShowing = false;
    /* access modifiers changed from: private */
    public boolean didStartPlaying = false;
    /* access modifiers changed from: private */
    public boolean firstQuartileSent = false;
    final Handler mHandler = new Handler();
    final Runnable mUpdateResults = new Runnable() {
        public void run() {
            TapjoyVideoView.this.timerText.setText(BuildConfig.FLAVOR + TapjoyVideoView.this.getRemainingVideoTime());
            float duration = (float) TapjoyVideoView.this.videoView.getDuration();
            if (duration > 0.0f) {
                if (!TapjoyVideoView.this.didStartPlaying) {
                    TapjoyVideoView.this.sendTrackingEvent(TJAdUnitConstants.String.VIDEO_START);
                    boolean unused = TapjoyVideoView.this.didStartPlaying = true;
                }
                float currentPosition = (float) TapjoyVideoView.this.videoView.getCurrentPosition();
                if (currentPosition >= duration / 4.0f && !TapjoyVideoView.this.firstQuartileSent) {
                    TapjoyLog.i(TapjoyVideoView.TAG, "Video 1st quartile: " + currentPosition);
                    TapjoyVideoView.this.sendTrackingEvent(TJAdUnitConstants.String.VIDEO_FIRST_QUARTILE);
                    boolean unused2 = TapjoyVideoView.this.firstQuartileSent = true;
                }
                if (currentPosition >= duration / 2.0f && !TapjoyVideoView.this.midpointSent) {
                    TapjoyLog.i(TapjoyVideoView.TAG, "Video midpoint: " + currentPosition);
                    TapjoyVideoView.this.sendTrackingEvent(TJAdUnitConstants.String.VIDEO_MIDPOINT);
                    boolean unused3 = TapjoyVideoView.this.midpointSent = true;
                }
                if (currentPosition >= (3.0f * duration) / 4.0f && !TapjoyVideoView.this.thirdQuartileSent) {
                    TapjoyLog.i(TapjoyVideoView.TAG, "Video 3rd quartile: " + currentPosition);
                    TapjoyVideoView.this.sendTrackingEvent(TJAdUnitConstants.String.VIDEO_THIRD_QUARTILE);
                    boolean unused4 = TapjoyVideoView.this.thirdQuartileSent = true;
                }
            }
        }
    };
    private TextView messageText = null;
    /* access modifiers changed from: private */
    public boolean midpointSent = false;
    private RelativeLayout relativeLayout;
    /* access modifiers changed from: private */
    public int seekTime = 0;
    private boolean sendClick = false;
    private boolean shouldDismiss = false;
    /* access modifiers changed from: private */
    public boolean thirdQuartileSent = false;
    private int timeRemaining = 0;
    Timer timer = null;
    /* access modifiers changed from: private */
    public TextView timerText = null;
    private HashMap<String, String> trackingUrls = null;
    private TapjoyVideoBroadcastReceiver videoBroadcastReceiver;
    private boolean videoIsComplete = false;
    private String videoMessage = BuildConfig.FLAVOR;
    private String videoURL = null;
    /* access modifiers changed from: private */
    public VideoView videoView = null;
    private String webviewURL = null;

    private class RemainingTime extends TimerTask {
        private RemainingTime() {
        }

        public void run() {
            TapjoyVideoView.this.mHandler.post(TapjoyVideoView.this.mUpdateResults);
        }
    }

    private class TapjoyVideoBroadcastReceiver extends BroadcastReceiver {
        private TapjoyVideoBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra("noConnectivity", false)) {
                TapjoyVideoView.this.videoView.pause();
                boolean unused = TapjoyVideoView.this.dialogShowing = true;
                TapjoyVideoView.this.showDialog(1);
                TapjoyLog.i(TapjoyVideoView.TAG, "No network connectivity during video playback");
            }
        }
    }

    private void cancelTimer() {
        if (this.timer != null) {
            this.timer.cancel();
        }
    }

    /* access modifiers changed from: private */
    public void finishWithResult(boolean z) {
        Intent intent = new Intent();
        intent.putExtra("result", z);
        if (this.videoView != null) {
            intent.putExtra(TJAdUnitConstants.EXTRA_RESULT_STRING1, Float.toString(((float) this.videoView.getCurrentPosition()) / 1000.0f));
            intent.putExtra(TJAdUnitConstants.EXTRA_RESULT_STRING2, Float.toString(((float) this.videoView.getDuration()) / 1000.0f));
        }
        intent.putExtra(TJAdUnitConstants.EXTRA_CALLBACK_ID, getIntent().getStringExtra(TJAdUnitConstants.EXTRA_CALLBACK_ID));
        setResult(-1, intent);
        finish();
    }

    private void fireTrackingEvent(final String str, final String str2) {
        if (str != null && !str.equals(BuildConfig.FLAVOR)) {
            new Thread(new Runnable() {
                public void run() {
                    TapjoyLog.i(TapjoyVideoView.TAG, "Sending video tracking event...");
                    TapjoyHttpURLResponse responseFromURL = new TapjoyURLConnection().getResponseFromURL(str);
                    if (responseFromURL.response != null && responseFromURL.statusCode == 200) {
                        TapjoyLog.i(TapjoyVideoView.TAG, "Video tracking event success: " + str2);
                    }
                }
            }).start();
        }
    }

    /* access modifiers changed from: private */
    public int getRemainingVideoTime() {
        int duration = (this.videoView.getDuration() - this.videoView.getCurrentPosition()) / 1000;
        if (duration < 0) {
            return 0;
        }
        return duration;
    }

    private void initVideoView() {
        this.relativeLayout.removeAllViews();
        this.relativeLayout.setBackgroundColor(-16777216);
        if (this.videoView == null && this.timerText == null) {
            this.videoView = new VideoView(this);
            this.videoView.setOnCompletionListener(this);
            this.videoView.setOnErrorListener(this);
            this.videoView.setOnPreparedListener(this);
            if (streamingVideo) {
                TapjoyLog.i(TAG, "streaming video: " + this.videoURL);
                this.videoView.setVideoURI(Uri.parse(this.videoURL));
            } else {
                TapjoyLog.i(TAG, "cached video: " + this.videoURL);
                this.videoView.setVideoPath(this.videoURL);
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(13);
            this.videoView.setLayoutParams(layoutParams);
            this.timeRemaining = this.videoView.getDuration() / 1000;
            TapjoyLog.i(TAG, "videoView.getDuration(): " + this.videoView.getDuration());
            TapjoyLog.i(TAG, "timeRemaining: " + this.timeRemaining);
            this.timerText = new TextView(this);
            this.timerText.setGravity(17);
            this.timerText.setTextSize((float) textSize);
            this.timerText.setTextColor(Color.parseColor("#535256"));
            this.timerText.setTypeface(Typeface.create(AdNetworkKey.DEFAULT, 1), 1);
            this.countdownImage = new ImageView(this);
            this.countdownImage.setId(1);
            Bitmap loadBitmapFromJar = TapjoyUtil.loadBitmapFromJar(COUNTDOWN_IMAGE, this);
            if (loadBitmapFromJar != null) {
                this.countdownImage.setImageBitmap(loadBitmapFromJar);
                RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
                layoutParams2.addRule(12);
                layoutParams2.addRule(9);
                layoutParams2.setMargins(this.countdownTextMargin, 0, 0, this.countdownTextMargin);
                this.countdownImage.setLayoutParams(layoutParams2);
            }
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(this.countdownTextSize, this.countdownTextSize);
            layoutParams3.addRule(12);
            layoutParams3.addRule(9);
            layoutParams3.setMargins(this.countdownTextMargin, 0, 0, this.countdownTextMargin);
            this.timerText.setLayoutParams(layoutParams3);
            this.messageText = new TextView(this);
            this.messageText.setGravity(17);
            this.messageText.setTextSize((float) textSize);
            this.messageText.setTextColor(-1);
            this.messageText.setTypeface(Typeface.create(AdNetworkKey.DEFAULT, 0), 0);
            this.messageText.setText(this.videoMessage);
            RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-2, this.countdownTextSize);
            layoutParams4.addRule(1, this.countdownImage.getId());
            layoutParams4.addRule(12);
            layoutParams4.setMargins(0, 0, 0, this.countdownTextMargin);
            this.messageText.setLayoutParams(layoutParams4);
        }
        startVideo();
        this.relativeLayout.addView(this.videoView);
        this.relativeLayout.addView(this.countdownImage);
        this.relativeLayout.addView(this.timerText);
        this.relativeLayout.addView(this.messageText);
    }

    /* access modifiers changed from: private */
    public void sendTrackingEvent(String str) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            String str2 = this.trackingUrls.get(str);
            if (str2 != null) {
                JSONArray jSONArray = new JSONArray(str2);
                for (int i = 0; i < jSONArray.length(); i++) {
                    String optString = jSONArray.optString(i);
                    if (optString != null) {
                        fireTrackingEvent(optString.replace("[TIMESTAMP]", Long.toString(currentTimeMillis)), str);
                    }
                }
            }
        } catch (Exception e) {
            TapjoyLog.w(TAG, "Bad video tracking urls array");
        }
    }

    private void showVideoCompletionScreen() {
        if (this.shouldDismiss) {
            finishWithResult(true);
            return;
        }
        Intent intent = new Intent(this, TJAdUnitView.class);
        intent.putExtra(TJAdUnitConstants.EXTRA_VIEW_TYPE, 4);
        intent.putExtra("url", this.webviewURL);
        intent.putExtra(TJAdUnitConstants.EXTRA_LEGACY_VIEW, true);
        startActivityForResult(intent, 0);
    }

    private void startVideo() {
        this.videoView.requestFocus();
        if (this.dialogShowing) {
            this.videoView.seekTo(this.seekTime);
            TapjoyLog.i(TAG, "dialog is showing -- don't start");
        } else {
            TapjoyLog.i(TAG, TJAdUnitConstants.String.VIDEO_START);
            this.videoView.seekTo(0);
            this.videoView.start();
            TapjoyVideo.videoNotifierStart();
        }
        cancelTimer();
        this.timer = new Timer();
        this.timer.schedule(new RemainingTime(), 500, 100);
        this.clickRequestSuccess = false;
        if (this.sendClick) {
            new Thread(new Runnable() {
                public void run() {
                    TapjoyLog.i(TapjoyVideoView.TAG, "SENDING CLICK...");
                    TapjoyHttpURLResponse responseFromURL = new TapjoyURLConnection().getResponseFromURL(TapjoyVideoView.videoData.getClickURL());
                    if (responseFromURL.response != null && responseFromURL.response.contains("OK")) {
                        TapjoyLog.i(TapjoyVideoView.TAG, "CLICK REQUEST SUCCESS!");
                        boolean unused = TapjoyVideoView.this.clickRequestSuccess = true;
                    }
                }
            }).start();
            this.sendClick = false;
        }
    }

    public int convertToDp(int i) {
        return (int) Math.ceil((double) (((float) i) * getResources().getDisplayMetrics().density));
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        TapjoyLog.i(TAG, "onActivityResult requestCode:" + i + ", resultCode: " + i2);
        Bundle bundle = null;
        if (intent != null) {
            bundle = intent.getExtras();
        }
        String string = bundle != null ? bundle.getString("result") : null;
        if (string == null || string.length() == 0 || string.equals(TapjoyConstants.TJC_VIDEO_OFFER_WALL_URL)) {
            finishWithResult(true);
        } else if (string.equals(TapjoyConstants.TJC_VIDEO_TJVIDEO_URL)) {
            initVideoView();
        }
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        TapjoyLog.i(TAG, "onCompletion");
        cancelTimer();
        showVideoCompletionScreen();
        if (!videoErrorOccurred) {
            TapjoyVideo.videoNotifierComplete();
            sendTrackingEvent(TJAdUnitConstants.String.VIDEO_COMPLETE);
            new Thread(new Runnable() {
                public void run() {
                    if (TapjoyVideoView.this.clickRequestSuccess) {
                        TapjoyConnectCore.getInstance().actionComplete(TapjoyVideoView.videoData.getOfferId());
                    }
                }
            }).start();
        }
        videoErrorOccurred = false;
        this.videoIsComplete = true;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        TapjoyLog.i(TAG, "onCreate");
        super.onCreate(bundle);
        TapjoyConnectCore.viewWillOpen(3);
        if (bundle != null) {
            TapjoyLog.i(TAG, "*** Loading saved data from bundle ***");
            this.seekTime = bundle.getInt(BUNDLE_SEEK_TIME);
            this.dialogShowing = bundle.getBoolean(BUNDLE_DIALOG_SHOWING);
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.videoURL = extras.getString(TapjoyConstants.EXTRA_VIDEO_URL);
            videoData = new OldTapjoyVideoData();
            videoData.setVideoURL(this.videoURL);
            if (extras.containsKey(TapjoyConstants.EXTRA_VIDEO_MESSAGE)) {
                this.videoMessage = extras.getString(TapjoyConstants.EXTRA_VIDEO_MESSAGE);
            }
            if (extras.containsKey(TapjoyConstants.EXTRA_VIDEO_DATA)) {
                videoData = (OldTapjoyVideoData) extras.getSerializable(TapjoyConstants.EXTRA_VIDEO_DATA);
            }
            if (extras.containsKey(TapjoyConstants.EXTRA_CACHED_VIDEO_LOCATION)) {
                videoData.setLocalFilePath(extras.getString(TapjoyConstants.EXTRA_CACHED_VIDEO_LOCATION));
            }
            if (extras.containsKey(TapjoyConstants.EXTRA_VIDEO_ALLOW_BACK_BUTTON)) {
                this.allowBackKey = extras.getBoolean(TapjoyConstants.EXTRA_VIDEO_ALLOW_BACK_BUTTON);
            }
            if (extras.containsKey(TapjoyConstants.EXTRA_VIDEO_CANCEL_MESSAGE)) {
                this.cancelMessage = extras.getString(TapjoyConstants.EXTRA_VIDEO_CANCEL_MESSAGE);
            }
            if (extras.containsKey(TapjoyConstants.EXTRA_VIDEO_SHOULD_DISMISS)) {
                this.shouldDismiss = extras.getBoolean(TapjoyConstants.EXTRA_VIDEO_SHOULD_DISMISS);
            }
            if (extras.containsKey(TapjoyConstants.EXTRA_VIDEO_TRACKING_URLS)) {
                this.trackingUrls = (HashMap) extras.getSerializable(TapjoyConstants.EXTRA_VIDEO_TRACKING_URLS);
            }
        }
        TapjoyLog.i(TAG, "dialogShowing: " + this.dialogShowing + ", seekTime: " + this.seekTime);
        if (videoData != null) {
            if (videoData.getClickURL() != null) {
                this.sendClick = true;
            }
            streamingVideo = false;
            if (TapjoyVideo.getInstance() == null) {
                TapjoyLog.i(TAG, "null video");
                finishWithResult(false);
                return;
            }
            this.videoURL = videoData.getLocalFilePath();
            this.webviewURL = videoData.getWebviewURL();
            if (this.videoURL == null || this.videoURL.length() == 0) {
                TapjoyLog.i(TAG, "Playing video at location:: " + videoData.getVideoURL());
                this.videoURL = videoData.getVideoURL();
                streamingVideo = true;
            }
            TapjoyLog.i(TAG, "videoPath: " + this.videoURL);
        } else if (this.videoURL != null) {
            streamingVideo = true;
            this.sendClick = false;
            TapjoyLog.i(TAG, "playing video only: " + this.videoURL);
        }
        requestWindowFeature(1);
        this.relativeLayout = new RelativeLayout(this);
        this.relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        setContentView(this.relativeLayout);
        if (Build.VERSION.SDK_INT > 3) {
            int screenLayoutSize = new TapjoyDisplayMetricsUtil(this).getScreenLayoutSize();
            TapjoyLog.i(TAG, "deviceScreenLayoutSize: " + screenLayoutSize);
            if (screenLayoutSize == 4) {
                textSize = 32;
            }
        }
        this.countdownTextSize = convertToDp(this.countdownTextSize);
        this.countdownTextMargin = convertToDp(this.countdownTextMargin);
        this.videoBroadcastReceiver = new TapjoyVideoBroadcastReceiver();
        registerReceiver(this.videoBroadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        initVideoView();
        TapjoyLog.i(TAG, "onCreate DONE");
        TapjoyConnectCore.viewDidOpen(3);
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int i) {
        TapjoyLog.i(TAG, "dialog onCreateDialog");
        if (!this.dialogShowing) {
            return this.dialog;
        }
        switch (i) {
            case 0:
                this.dialog = new AlertDialog.Builder(this).setTitle("Cancel Video?").setMessage(this.cancelMessage).setNegativeButton("End", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TapjoyVideoView.this.finishWithResult(false);
                    }
                }).setPositiveButton("Resume", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        TapjoyVideoView.this.videoView.seekTo(TapjoyVideoView.this.seekTime);
                        TapjoyVideoView.this.videoView.start();
                        boolean unused = TapjoyVideoView.this.dialogShowing = false;
                        TapjoyLog.i(TapjoyVideoView.TAG, "RESUME VIDEO time: " + TapjoyVideoView.this.seekTime);
                        TapjoyLog.i(TapjoyVideoView.TAG, "currentPosition: " + TapjoyVideoView.this.videoView.getCurrentPosition());
                        TapjoyLog.i(TapjoyVideoView.TAG, "duration: " + TapjoyVideoView.this.videoView.getDuration() + ", elapsed: " + (TapjoyVideoView.this.videoView.getDuration() - TapjoyVideoView.this.videoView.getCurrentPosition()));
                    }
                }).create();
                this.dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialogInterface) {
                        TapjoyLog.i(TapjoyVideoView.TAG, "dialog onCancel");
                        dialogInterface.dismiss();
                        TapjoyVideoView.this.videoView.seekTo(TapjoyVideoView.this.seekTime);
                        TapjoyVideoView.this.videoView.start();
                        boolean unused = TapjoyVideoView.this.dialogShowing = false;
                    }
                });
                this.dialog.show();
                this.dialogShowing = true;
                break;
            case 1:
                this.dialog = new AlertDialog.Builder(this).setTitle("Network Connection Lost").setMessage(this.connectivityMessage).setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        boolean unused = TapjoyVideoView.this.dialogShowing = false;
                        TapjoyVideoView.this.finishWithResult(false);
                    }
                }).create();
                this.dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialogInterface) {
                        TapjoyLog.i(TapjoyVideoView.TAG, "dialog onCancel");
                        dialogInterface.dismiss();
                        boolean unused = TapjoyVideoView.this.dialogShowing = false;
                        TapjoyVideoView.this.finishWithResult(false);
                    }
                });
                this.dialog.show();
                this.dialogShowing = true;
                break;
            default:
                this.dialog = null;
                break;
        }
        return this.dialog;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            if (this.videoBroadcastReceiver != null) {
                unregisterReceiver(this.videoBroadcastReceiver);
                this.videoBroadcastReceiver = null;
            }
            TapjoyConnectCore.viewWillClose(3);
            TapjoyConnectCore.viewDidClose(3);
        }
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        TapjoyLog.i(TAG, "onError, what: " + i + "extra: " + i2);
        videoErrorOccurred = true;
        TapjoyVideo.videoNotifierError(3);
        cancelTimer();
        return i == 1 && i2 == -1004;
    }

    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
        if (!this.didStartPlaying) {
            return false;
        }
        if (i == 701) {
            sendTrackingEvent(TJAdUnitConstants.String.VIDEO_STALL);
            return false;
        } else if (i != 702) {
            return false;
        } else {
            sendTrackingEvent(TJAdUnitConstants.String.VIDEO_RESUME);
            return false;
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            if (this.videoIsComplete || videoErrorOccurred) {
                if (this.videoView.isPlaying()) {
                    this.videoView.stopPlayback();
                    cancelTimer();
                    showVideoCompletionScreen();
                    return true;
                }
            } else if (!videoErrorOccurred) {
                if (!this.allowBackKey) {
                    return true;
                }
                if (this.cancelMessage != null && this.cancelMessage.length() > 0) {
                    this.seekTime = this.videoView.getCurrentPosition();
                    this.videoView.pause();
                    this.dialogShowing = true;
                    showDialog(0);
                    TapjoyLog.i(TAG, "PAUSE VIDEO time: " + this.seekTime);
                    TapjoyLog.i(TAG, "currentPosition: " + this.videoView.getCurrentPosition());
                    TapjoyLog.i(TAG, "duration: " + this.videoView.getDuration() + ", elapsed: " + (this.videoView.getDuration() - this.videoView.getCurrentPosition()));
                    return true;
                }
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (this.videoView.isPlaying()) {
            TapjoyLog.i(TAG, "onPause");
            this.videoView.pause();
            this.seekTime = this.videoView.getCurrentPosition();
            TapjoyLog.i(TAG, "seekTime: " + this.seekTime);
        }
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.setOnInfoListener(this);
        TapjoyLog.i(TAG, "onPrepared");
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        TapjoyLog.i(TAG, "onResume");
        super.onResume();
        setRequestedOrientation(0);
        if (this.seekTime > 0) {
            TapjoyLog.i(TAG, "seekTime: " + this.seekTime);
            this.videoView.seekTo(this.seekTime);
            if (!this.dialogShowing || this.dialog == null || !this.dialog.isShowing()) {
                this.videoView.start();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        TapjoyLog.i(TAG, "*** onSaveInstanceState ***");
        TapjoyLog.i(TAG, "dialogShowing: " + this.dialogShowing + ", seekTime: " + this.seekTime);
        bundle.putBoolean(BUNDLE_DIALOG_SHOWING, this.dialogShowing);
        bundle.putInt(BUNDLE_SEEK_TIME, this.seekTime);
    }

    public void onWindowFocusChanged(boolean z) {
        TapjoyLog.i(TAG, "onWindowFocusChanged");
        super.onWindowFocusChanged(z);
    }
}
