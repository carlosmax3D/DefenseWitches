package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import jp.stargarage.g2metrics.BuildConfig;

public abstract class MediaPickerTask implements CoronaRuntimeTask {
    protected String fSelectedMediaFileName;

    public MediaPickerTask() {
        this.fSelectedMediaFileName = BuildConfig.FLAVOR;
    }

    public MediaPickerTask(String str) {
        this.fSelectedMediaFileName = str == null ? BuildConfig.FLAVOR : str;
    }

    public abstract void executeUsing(CoronaRuntime coronaRuntime);
}
