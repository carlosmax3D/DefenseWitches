package com.ansca.corona.listeners;

import com.ansca.corona.CoronaStatusBarSettings;

public interface CoronaStatusBarApiListener {
    int getStatusBarHeight();

    CoronaStatusBarSettings getStatusBarMode();

    void setStatusBarMode(CoronaStatusBarSettings coronaStatusBarSettings);
}
