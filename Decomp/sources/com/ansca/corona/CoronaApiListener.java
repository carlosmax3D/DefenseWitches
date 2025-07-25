package com.ansca.corona;

public interface CoronaApiListener {
    void addKeepScreenOnFlag();

    void onScreenLockStateChanged(boolean z);

    void removeKeepScreenOnFlag();
}
