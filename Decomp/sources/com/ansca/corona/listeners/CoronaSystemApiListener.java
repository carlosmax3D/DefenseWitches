package com.ansca.corona.listeners;

import android.content.Intent;

public interface CoronaSystemApiListener {
    Intent getInitialIntent();

    Intent getIntent();

    boolean requestSystem(String str);
}
