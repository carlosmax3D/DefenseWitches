package com.ansca.corona.input;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class RaiseMouseEventTask implements CoronaRuntimeTask {
    private boolean fIsMiddleButtonDown;
    private boolean fIsPrimaryButtonDown;
    private boolean fIsSecondaryButtonDown;
    private float fPointX;
    private float fPointY;
    private long fTimestamp;

    public RaiseMouseEventTask(float f, float f2, long j, boolean z, boolean z2, boolean z3) {
        this.fPointX = f;
        this.fPointY = f2;
        this.fTimestamp = j;
        this.fIsPrimaryButtonDown = z;
        this.fIsSecondaryButtonDown = z2;
        this.fIsMiddleButtonDown = z3;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.mouseEvent(coronaRuntime, (int) this.fPointX, (int) this.fPointY, this.fTimestamp, this.fIsPrimaryButtonDown, this.fIsSecondaryButtonDown, this.fIsMiddleButtonDown);
    }
}
