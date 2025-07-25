package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.JavaToNativeShim;

public class ImagePickerTask extends MediaPickerTask {
    public ImagePickerTask() {
    }

    public ImagePickerTask(String str) {
        super(str);
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.imagePickerEvent(coronaRuntime, this.fSelectedMediaFileName);
    }
}
