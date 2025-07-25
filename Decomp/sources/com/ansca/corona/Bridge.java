package com.ansca.corona;

import android.content.Context;
import com.naef.jnlua.LuaState;

public class Bridge extends NativeToJavaBridge {
    public Bridge(Context context) {
        super(context);
    }

    public static void cancelAllNotifications() {
        callNotificationCancelAll((CoronaRuntime) null);
    }

    public static void cancelNotification(int i) {
        callNotificationCancel(i);
    }

    public static int scheduleNotification(LuaState luaState, int i) {
        luaState.pushInteger(notificationSchedule(luaState, i));
        return 1;
    }
}
