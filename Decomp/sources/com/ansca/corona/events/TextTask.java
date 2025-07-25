package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class TextTask implements CoronaRuntimeTask {
    private int myEditTextId;
    private boolean myHasFocus;
    private boolean myIsDone;

    public TextTask(int i, boolean z, boolean z2) {
        this.myEditTextId = i;
        this.myHasFocus = z;
        this.myIsDone = z2;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.textEvent(coronaRuntime, this.myEditTextId, this.myHasFocus, this.myIsDone);
    }
}
