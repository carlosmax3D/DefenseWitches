package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class DidFailLoadUrlTask implements CoronaRuntimeTask {
    private String myDescription;
    private int myErrorCode;
    private String myFailingUrl;
    private int myId;

    public DidFailLoadUrlTask(int i, String str, String str2, int i2) {
        this.myId = i;
        this.myFailingUrl = str;
        this.myDescription = str2;
        this.myErrorCode = i2;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.webViewDidFailLoadUrl(coronaRuntime, this.myId, this.myFailingUrl, this.myDescription, this.myErrorCode);
    }
}
