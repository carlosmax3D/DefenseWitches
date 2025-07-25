package android.support.v4.media.session;

import android.media.session.PlaybackState;

class PlaybackStateCompatApi21 {
    PlaybackStateCompatApi21() {
    }

    public static long getActions(Object obj) {
        return ((PlaybackState) obj).getActions();
    }

    public static long getBufferedPosition(Object obj) {
        return ((PlaybackState) obj).getBufferedPosition();
    }

    public static CharSequence getErrorMessage(Object obj) {
        return ((PlaybackState) obj).getErrorMessage();
    }

    public static long getLastPositionUpdateTime(Object obj) {
        return ((PlaybackState) obj).getLastPositionUpdateTime();
    }

    public static float getPlaybackSpeed(Object obj) {
        return ((PlaybackState) obj).getPlaybackSpeed();
    }

    public static long getPosition(Object obj) {
        return ((PlaybackState) obj).getPosition();
    }

    public static int getState(Object obj) {
        return ((PlaybackState) obj).getState();
    }

    public static Object newInstance(int i, long j, long j2, float f, long j3, CharSequence charSequence, long j4) {
        PlaybackState.Builder builder = new PlaybackState.Builder();
        builder.setState(i, j, f, j4);
        builder.setBufferedPosition(j2);
        builder.setActions(j3);
        builder.setErrorMessage(charSequence);
        return builder.build();
    }
}
