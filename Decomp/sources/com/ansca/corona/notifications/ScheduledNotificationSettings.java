package com.ansca.corona.notifications;

import android.content.Context;
import com.naef.jnlua.LuaState;
import java.util.Date;

public class ScheduledNotificationSettings extends NotificationSettings {
    private Date fEndTime;
    private StatusBarNotificationSettings fStatusBarSettings;

    public ScheduledNotificationSettings() {
        this(new StatusBarNotificationSettings());
    }

    private ScheduledNotificationSettings(StatusBarNotificationSettings statusBarNotificationSettings) {
        statusBarNotificationSettings = statusBarNotificationSettings == null ? new StatusBarNotificationSettings() : statusBarNotificationSettings;
        this.fEndTime = new Date();
        this.fStatusBarSettings = statusBarNotificationSettings;
    }

    public static ScheduledNotificationSettings from(Context context, LuaState luaState, int i) {
        return new ScheduledNotificationSettings(StatusBarNotificationSettings.from(context, luaState, i));
    }

    public ScheduledNotificationSettings clone() {
        ScheduledNotificationSettings scheduledNotificationSettings = (ScheduledNotificationSettings) super.clone();
        scheduledNotificationSettings.fEndTime = getEndTime();
        scheduledNotificationSettings.fStatusBarSettings = this.fStatusBarSettings.clone();
        return scheduledNotificationSettings;
    }

    public void copyFrom(ScheduledNotificationSettings scheduledNotificationSettings) {
        if (scheduledNotificationSettings == null) {
            throw new NullPointerException();
        }
        setId(scheduledNotificationSettings.getId());
        this.fEndTime = scheduledNotificationSettings.getEndTime();
        this.fStatusBarSettings.copyFrom(scheduledNotificationSettings.getStatusBarSettings());
    }

    public Date getEndTime() {
        return (Date) this.fEndTime.clone();
    }

    public int getId() {
        return this.fStatusBarSettings.getId();
    }

    public StatusBarNotificationSettings getStatusBarSettings() {
        return this.fStatusBarSettings;
    }

    public void setEndTime(Date date) {
        if (date == null) {
            throw new NullPointerException();
        }
        this.fEndTime = (Date) date.clone();
    }

    public void setId(int i) {
        this.fStatusBarSettings.setId(i);
    }
}
