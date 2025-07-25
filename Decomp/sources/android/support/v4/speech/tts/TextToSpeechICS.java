package android.support.v4.speech.tts;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

class TextToSpeechICS {
    private static final String TAG = "android.support.v4.speech.tts";

    TextToSpeechICS() {
    }

    static TextToSpeech construct(Context context, TextToSpeech.OnInitListener onInitListener, String str) {
        if (Build.VERSION.SDK_INT >= 14) {
            return new TextToSpeech(context, onInitListener, str);
        }
        if (str == null) {
            return new TextToSpeech(context, onInitListener);
        }
        Log.w(TAG, "Can't specify tts engine on this device");
        return new TextToSpeech(context, onInitListener);
    }
}
