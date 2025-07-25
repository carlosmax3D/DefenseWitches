package com.ansca.corona.events;

import android.os.Bundle;
import com.ansca.corona.CoronaData;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;
import com.ansca.corona.notifications.NotificationType;
import com.ansca.corona.notifications.StatusBarNotificationSettings;
import com.naef.jnlua.LuaState;

public class NotificationReceivedTask implements CoronaRuntimeTask {
    public static final String NAME = "notification";
    private String fApplicationStateName;
    private StatusBarNotificationSettings fStatusBarSettings;

    public NotificationReceivedTask(String str, StatusBarNotificationSettings statusBarNotificationSettings) {
        if (str == null || statusBarNotificationSettings == null) {
            throw new NullPointerException();
        }
        this.fApplicationStateName = str;
        this.fStatusBarSettings = statusBarNotificationSettings;
    }

    private String getNotificationTypeString() {
        return this.fStatusBarSettings.isSourceLocal() ? NotificationType.LOCAL.toInvariantString() : NotificationType.REMOTE.toInvariantString();
    }

    private String getSourceDataName() {
        String sourceDataName = this.fStatusBarSettings.getSourceDataName();
        if (sourceDataName != null) {
            sourceDataName = sourceDataName.trim();
        }
        return (sourceDataName == null || sourceDataName.length() <= 0) ? "sourceData" : sourceDataName;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        LuaState luaState = null;
        if (coronaRuntime != null) {
            luaState = coronaRuntime.getLuaState();
        }
        if (luaState != null) {
            luaState.newTable();
            int top = luaState.getTop();
            luaState.pushString(NAME);
            luaState.setField(top, "name");
            luaState.pushString(getNotificationTypeString());
            luaState.setField(top, "type");
            luaState.pushString(this.fApplicationStateName);
            luaState.setField(top, "applicationState");
            luaState.pushString(this.fStatusBarSettings.getContentText());
            luaState.setField(top, "alert");
            if (this.fStatusBarSettings.getSourceData() != null) {
                this.fStatusBarSettings.getSourceData().pushTo(luaState);
                luaState.setField(top, getSourceDataName());
            }
            if (this.fStatusBarSettings.getData() != null) {
                this.fStatusBarSettings.getData().pushTo(luaState);
            } else {
                luaState.newTable();
            }
            luaState.setField(top, "custom");
            JavaToNativeShim.dispatchEventInLua(coronaRuntime);
            luaState.pop(1);
        }
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("name", NAME);
        bundle.putString("type", getNotificationTypeString());
        bundle.putString("applicationState", this.fApplicationStateName);
        bundle.putString("alert", this.fStatusBarSettings.getContentText());
        CoronaData sourceData = this.fStatusBarSettings.getSourceData();
        if (sourceData != null) {
            bundle.putParcelable(getSourceDataName(), new CoronaData.Proxy(sourceData));
        }
        CoronaData.Table data = this.fStatusBarSettings.getData();
        if (data == null) {
            data = new CoronaData.Table();
        }
        bundle.putParcelable("custom", new CoronaData.Proxy(data));
        return bundle;
    }
}
