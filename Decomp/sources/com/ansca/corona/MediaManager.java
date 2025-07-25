package com.ansca.corona;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.webkit.URLUtil;
import com.ansca.corona.events.SoundEndedTask;
import com.ansca.corona.storage.FileContentProvider;
import com.ansca.corona.storage.FileServices;
import com.tapjoy.TapjoyConstants;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class MediaManager {
    private static final int SOUNDPOOL_STREAMS = 4;
    private AudioRecorder myAudioRecorder;
    private Context myContext;
    /* access modifiers changed from: private */
    public CoronaRuntime myCoronaRuntime;
    private Handler myHandler;
    private HashMap<Integer, Integer> myIdToSoundPoolIdMap;
    /* access modifiers changed from: private */
    public HashMap<Integer, MediaPlayer> myMediaPlayers;
    private SoundPool mySoundPool;
    private float myVolume;

    public MediaManager(CoronaRuntime coronaRuntime, Context context) {
        this.myContext = context;
        this.myCoronaRuntime = coronaRuntime;
    }

    public static Uri createVideoURLFromString(String str, Context context) {
        Uri uri;
        String lowerCase = str.toLowerCase();
        if (URLUtil.isNetworkUrl(str) || lowerCase.startsWith("rtsp:") || lowerCase.startsWith("file:") || lowerCase.startsWith("content:")) {
            uri = Uri.parse(str);
        } else if (context == null) {
            return null;
        } else {
            uri = FileContentProvider.createContentUriForFile(context, str);
        }
        return uri;
    }

    static void onUsingAudio() {
        final CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
        if (coronaActivity != null && coronaActivity.getVolumeControlStream() == Integer.MIN_VALUE) {
            coronaActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (coronaActivity != null) {
                        coronaActivity.setVolumeControlStream(3);
                    }
                }
            });
        }
    }

    public AudioRecorder getAudioRecorder(int i) {
        if (this.myAudioRecorder == null && this.myHandler != null) {
            this.myAudioRecorder = new AudioRecorder(this.myCoronaRuntime, this.myHandler);
        }
        this.myAudioRecorder.setId(i);
        return this.myAudioRecorder;
    }

    public float getVolume(int i) {
        return this.myVolume;
    }

    public void init() {
        this.myIdToSoundPoolIdMap = new HashMap<>();
        this.mySoundPool = new SoundPool(4, 3, 0);
        this.myMediaPlayers = new HashMap<>();
        this.myHandler = new Handler(Looper.getMainLooper());
    }

    public void loadEventSound(int i, String str) {
        Context context;
        if (str != null && str.length() > 0 && (context = this.myContext) != null) {
            onUsingAudio();
            FileServices fileServices = new FileServices(context);
            if (fileServices.isAssetFile(str)) {
                AssetFileDescriptor openAssetFileDescriptorFor = fileServices.openAssetFileDescriptorFor(str);
                if (openAssetFileDescriptorFor != null) {
                    this.myIdToSoundPoolIdMap.put(Integer.valueOf(i), Integer.valueOf(this.mySoundPool.load(openAssetFileDescriptorFor, 1)));
                    return;
                }
                return;
            }
            this.myIdToSoundPoolIdMap.put(Integer.valueOf(i), Integer.valueOf(this.mySoundPool.load(str, 1)));
        }
    }

    public void loadSound(final int i, String str) {
        MediaPlayer mediaPlayer = null;
        try {
            onUsingAudio();
            if (str.startsWith("/") || str.startsWith("http:")) {
                if (!URLUtil.isNetworkUrl(str)) {
                    File file = new File(str);
                    if (!file.exists()) {
                        System.err.println("Could not load sound " + str);
                        return;
                    }
                    FileInputStream fileInputStream = new FileInputStream(file);
                    MediaPlayer mediaPlayer2 = new MediaPlayer();
                    try {
                        mediaPlayer2.setDataSource(fileInputStream.getFD());
                        mediaPlayer2.setAudioStreamType(3);
                        mediaPlayer2.prepare();
                        mediaPlayer = mediaPlayer2;
                    } catch (Exception e) {
                        MediaPlayer mediaPlayer3 = mediaPlayer2;
                        return;
                    }
                } else {
                    mediaPlayer = MediaPlayer.create(this.myCoronaRuntime.getController().getContext(), Uri.parse(str));
                }
            }
            if (mediaPlayer == null) {
                System.err.println("Could not load sound " + str);
                return;
            }
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    mediaPlayer.release();
                    if (MediaManager.this.myMediaPlayers != null) {
                        MediaManager.this.myMediaPlayers.remove(new Integer(i));
                    }
                    if (MediaManager.this.myCoronaRuntime == null) {
                        return true;
                    }
                    MediaManager.this.myCoronaRuntime.getTaskDispatcher().send(new SoundEndedTask(i));
                    return true;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.release();
                    if (MediaManager.this.myMediaPlayers != null) {
                        MediaManager.this.myMediaPlayers.remove(new Integer(i));
                    }
                    if (MediaManager.this.myCoronaRuntime != null) {
                        MediaManager.this.myCoronaRuntime.getTaskDispatcher().send(new SoundEndedTask(i));
                    }
                }
            });
            this.myMediaPlayers.put(new Integer(i), mediaPlayer);
        } catch (Exception e2) {
        }
    }

    public void pauseAll() {
        for (Integer intValue : this.myMediaPlayers.keySet()) {
            pauseMedia(intValue.intValue());
        }
        for (Integer intValue2 : this.myIdToSoundPoolIdMap.keySet()) {
            pauseMedia(intValue2.intValue());
        }
    }

    public void pauseMedia(int i) {
        MediaPlayer mediaPlayer = this.myMediaPlayers.get(Integer.valueOf(i));
        if (mediaPlayer != null) {
            try {
                mediaPlayer.pause();
            } catch (IllegalStateException e) {
            }
        } else {
            Integer num = this.myIdToSoundPoolIdMap.get(new Integer(i));
            if (num != null) {
                this.mySoundPool.pause(num.intValue());
            }
        }
    }

    public void playMedia(int i, boolean z) {
        onUsingAudio();
        MediaPlayer mediaPlayer = this.myMediaPlayers.get(new Integer(i));
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(z);
            mediaPlayer.start();
            return;
        }
        Integer num = this.myIdToSoundPoolIdMap.get(new Integer(i));
        if (num != null) {
            AudioManager audioManager = (AudioManager) this.myContext.getSystemService("audio");
            float streamVolume = ((float) audioManager.getStreamVolume(3)) / ((float) audioManager.getStreamMaxVolume(3));
            if (this.mySoundPool.play(num.intValue(), streamVolume, streamVolume, 1, 0, 1.0f) == 0) {
                System.out.println("playSound failed " + num);
            }
        }
    }

    public void playVideo(int i, String str, boolean z) {
        final Context context = this.myContext;
        if (context != null && str != null && str.length() >= 1) {
            pauseAll();
            final int i2 = i;
            final String str2 = str;
            final boolean z2 = z;
            this.myHandler.post(new Runnable() {
                public void run() {
                    Uri createVideoURLFromString = MediaManager.createVideoURLFromString(str2, context);
                    if (createVideoURLFromString != null) {
                        Intent intent = new Intent(context, VideoActivity.class);
                        intent.putExtra("video_uri", createVideoURLFromString.toString());
                        intent.putExtra(TapjoyConstants.TJC_VIDEO_ID, i2);
                        intent.putExtra("media_controls_enabled", z2);
                        intent.setFlags(65536);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    public void release() {
        if (this.mySoundPool != null) {
            this.mySoundPool.release();
            this.mySoundPool = null;
            this.myMediaPlayers = null;
            this.myIdToSoundPoolIdMap = null;
        }
    }

    public void resumeAll() {
        for (Integer intValue : this.myMediaPlayers.keySet()) {
            resumeMedia(intValue.intValue());
        }
        for (Integer intValue2 : this.myIdToSoundPoolIdMap.keySet()) {
            resumeMedia(intValue2.intValue());
        }
    }

    public void resumeMedia(int i) {
        MediaPlayer mediaPlayer = this.myMediaPlayers.get(Integer.valueOf(i));
        if (mediaPlayer != null) {
            try {
                mediaPlayer.start();
            } catch (IllegalStateException e) {
            }
        } else {
            Integer num = this.myIdToSoundPoolIdMap.get(new Integer(i));
            if (num != null) {
                this.mySoundPool.resume(num.intValue());
            }
        }
    }

    public void setVolume(int i, float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        MediaPlayer mediaPlayer = this.myMediaPlayers.get(new Integer(i));
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(f, f);
        }
        this.myVolume = f;
    }

    public void stopMedia(int i) {
        MediaPlayer mediaPlayer = this.myMediaPlayers.get(new Integer(i));
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            return;
        }
        Integer num = this.myIdToSoundPoolIdMap.get(new Integer(i));
        if (num != null) {
            this.mySoundPool.stop(num.intValue());
        }
    }
}
