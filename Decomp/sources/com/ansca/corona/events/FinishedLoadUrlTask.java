package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class FinishedLoadUrlTask implements CoronaRuntimeTask {
    private int fId;
    private String fUrl;

    public FinishedLoadUrlTask(int i, String str) {
        this.fId = i;
        this.fUrl = str;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.webViewFinishedLoadUrl(coronaRuntime, this.fId, this.fUrl);
    }
}
