package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class WebHistoryUpdatedTask implements CoronaRuntimeTask {
    private boolean fCanGoBack;
    private boolean fCanGoForward;
    private int fId;

    public WebHistoryUpdatedTask(int i, boolean z, boolean z2) {
        this.fId = i;
        this.fCanGoBack = z;
        this.fCanGoForward = z2;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.webViewHistoryUpdated(coronaRuntime, this.fId, this.fCanGoBack, this.fCanGoForward);
    }
}
