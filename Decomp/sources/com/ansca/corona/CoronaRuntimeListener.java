package com.ansca.corona;

public interface CoronaRuntimeListener {
    void onExiting(CoronaRuntime coronaRuntime);

    void onLoaded(CoronaRuntime coronaRuntime);

    void onResumed(CoronaRuntime coronaRuntime);

    void onStarted(CoronaRuntime coronaRuntime);

    void onSuspended(CoronaRuntime coronaRuntime);
}
