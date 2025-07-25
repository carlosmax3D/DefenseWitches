package com.ansca.corona.events;

import android.net.Uri;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.JavaToNativeShim;
import java.io.File;

public class VideoPickerTask extends MediaPickerTask {
    private int fDuration;
    private long fSize;

    public VideoPickerTask() {
        this.fDuration = -1;
        this.fSize = -1;
    }

    public VideoPickerTask(String str) {
        super(str);
        this.fDuration = -1;
        this.fSize = -1;
    }

    public VideoPickerTask(String str, int i, long j) {
        super(str);
        this.fDuration = i;
        this.fSize = j;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        File file = new File(this.fSelectedMediaFileName);
        if (file.exists()) {
            JavaToNativeShim.videoPickerEvent(coronaRuntime, Uri.decode(Uri.fromFile(file).toString()), this.fDuration, this.fSize);
        } else {
            JavaToNativeShim.videoPickerEvent(coronaRuntime, this.fSelectedMediaFileName, this.fDuration, this.fSize);
        }
    }
}
