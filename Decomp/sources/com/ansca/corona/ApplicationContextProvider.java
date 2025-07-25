package com.ansca.corona;

import android.content.Context;

public class ApplicationContextProvider {
    public ApplicationContextProvider(Context context) {
        CoronaEnvironment.setApplicationContext(context);
    }

    public static Context getApplicationContext() {
        return CoronaEnvironment.getApplicationContext();
    }
}
