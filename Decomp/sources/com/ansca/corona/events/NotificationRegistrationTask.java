package com.ansca.corona.events;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;
import com.ansca.corona.notifications.NotificationType;
import com.naef.jnlua.LuaState;
import com.tapjoy.TJAdUnitConstants;

public class NotificationRegistrationTask implements CoronaRuntimeTask {
    private String fRegistrationId;

    public NotificationRegistrationTask(String str) {
        if (str == null) {
            throw new NullPointerException();
        }
        this.fRegistrationId = str;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        LuaState luaState = null;
        if (coronaRuntime != null) {
            luaState = coronaRuntime.getLuaState();
        }
        if (luaState != null) {
            luaState.newTable();
            int top = luaState.getTop();
            luaState.pushString(NotificationReceivedTask.NAME);
            luaState.setField(top, "name");
            luaState.pushString(NotificationType.REMOTE_REGISTRATION.toInvariantString());
            luaState.setField(top, "type");
            luaState.pushString(this.fRegistrationId);
            luaState.setField(top, TJAdUnitConstants.String.EVENT_TOKEN);
            JavaToNativeShim.dispatchEventInLua(coronaRuntime);
            luaState.pop(1);
        }
    }
}
