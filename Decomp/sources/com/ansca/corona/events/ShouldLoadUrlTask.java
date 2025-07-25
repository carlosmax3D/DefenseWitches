package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class ShouldLoadUrlTask implements CoronaRuntimeTask {
    private int myId;
    private int mySourceType;
    private String myUrl;

    public ShouldLoadUrlTask(int i, String str, int i2) {
        this.myId = i;
        this.myUrl = str;
        this.mySourceType = i2;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.webViewShouldLoadUrl(coronaRuntime, this.myId, this.myUrl, this.mySourceType);
    }
}
