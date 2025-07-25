package com.ansca.corona.notifications;

import android.content.Context;
import android.net.Uri;
import com.ansca.corona.CoronaData;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.storage.FileContentProvider;
import com.ansca.corona.storage.ResourceServices;
import com.naef.jnlua.LuaState;
import java.util.Date;
import jp.stargarage.g2metrics.BuildConfig;

public class StatusBarNotificationSettings extends NotificationSettings {
    public static final String DEFAULT_ICON_RESOURCE_NAME = "corona_statusbar_icon_default";
    private int fBadgeNumber = 0;
    private String fContentText = BuildConfig.FLAVOR;
    private String fContentTitle = BuildConfig.FLAVOR;
    private CoronaData.Table fData = new CoronaData.Table();
    private String fIconResourceName = DEFAULT_ICON_RESOURCE_NAME;
    private int fId = 0;
    private boolean fIsRemovable = true;
    private boolean fIsSourceLocal = true;
    private Uri fSoundFileUri = null;
    private CoronaData fSourceData = null;
    private String fSourceDataName = BuildConfig.FLAVOR;
    private String fSourceName = BuildConfig.FLAVOR;
    private String fTickerText = BuildConfig.FLAVOR;
    private Date fTimestamp = new Date();

    public static StatusBarNotificationSettings from(Context context, LuaState luaState, int i) {
        if (context == null) {
            throw new NullPointerException();
        }
        StatusBarNotificationSettings statusBarNotificationSettings = new StatusBarNotificationSettings();
        String str = (String) context.getPackageManager().getApplicationLabel(context.getApplicationInfo());
        statusBarNotificationSettings.setContentTitle(str);
        statusBarNotificationSettings.setTickerText(str);
        if (luaState != null && luaState.isTable(i)) {
            luaState.getField(i, "alert");
            if (luaState.isString(-1)) {
                String luaState2 = luaState.toString(-1);
                statusBarNotificationSettings.setContentText(luaState2);
                statusBarNotificationSettings.setTickerText(luaState2);
            } else if (luaState.isTable(-1)) {
                luaState.getField(-1, "title");
                if (luaState.isString(-1)) {
                    statusBarNotificationSettings.setContentTitle(luaState.toString(-1));
                }
                luaState.pop(1);
                luaState.getField(-1, "body");
                if (luaState.isString(-1)) {
                    String luaState3 = luaState.toString(-1);
                    statusBarNotificationSettings.setContentText(luaState3);
                    statusBarNotificationSettings.setTickerText(luaState3);
                }
                luaState.pop(1);
                luaState.getField(-1, "number");
                if (luaState.isNumber(-1)) {
                    statusBarNotificationSettings.setBadgeNumber(luaState.toInteger(-1));
                }
                luaState.pop(1);
            }
            luaState.pop(1);
            luaState.getField(i, "sound");
            if (luaState.isString(-1)) {
                Uri uri = null;
                try {
                    uri = FileContentProvider.createContentUriForFile(context, luaState.toString(-1).trim());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                statusBarNotificationSettings.setSoundFileUri(uri);
            }
            luaState.pop(1);
            luaState.getField(i, "custom");
            if (luaState.isTable(-1)) {
                CoronaData from = CoronaData.from(luaState, -1);
                if (from instanceof CoronaData.Table) {
                    statusBarNotificationSettings.setData((CoronaData.Table) from);
                }
            }
            luaState.pop(1);
        }
        return statusBarNotificationSettings;
    }

    public StatusBarNotificationSettings clone() {
        CoronaData.Table table = null;
        StatusBarNotificationSettings statusBarNotificationSettings = (StatusBarNotificationSettings) super.clone();
        statusBarNotificationSettings.fTimestamp = getTimestamp();
        statusBarNotificationSettings.fSourceData = this.fSourceData != null ? this.fSourceData.clone() : null;
        if (this.fData != null) {
            table = this.fData.clone();
        }
        statusBarNotificationSettings.fData = table;
        return statusBarNotificationSettings;
    }

    public void copyFrom(StatusBarNotificationSettings statusBarNotificationSettings) {
        CoronaData.Table table = null;
        if (statusBarNotificationSettings == null) {
            throw new NullPointerException();
        }
        setId(statusBarNotificationSettings.getId());
        this.fTimestamp = statusBarNotificationSettings.getTimestamp();
        this.fContentTitle = statusBarNotificationSettings.fContentTitle;
        this.fContentText = statusBarNotificationSettings.fContentText;
        this.fTickerText = statusBarNotificationSettings.fTickerText;
        this.fIconResourceName = statusBarNotificationSettings.fIconResourceName;
        this.fBadgeNumber = statusBarNotificationSettings.fBadgeNumber;
        this.fIsRemovable = statusBarNotificationSettings.fIsRemovable;
        this.fSoundFileUri = statusBarNotificationSettings.fSoundFileUri;
        this.fSourceName = statusBarNotificationSettings.fSourceName;
        this.fIsSourceLocal = statusBarNotificationSettings.fIsSourceLocal;
        this.fSourceDataName = statusBarNotificationSettings.fSourceDataName;
        this.fSourceData = statusBarNotificationSettings.fSourceData != null ? statusBarNotificationSettings.fSourceData.clone() : null;
        if (statusBarNotificationSettings.fData != null) {
            table = statusBarNotificationSettings.fData.clone();
        }
        this.fData = table;
    }

    public int getBadgeNumber() {
        return this.fBadgeNumber;
    }

    public String getContentText() {
        return this.fContentText;
    }

    public String getContentTitle() {
        return this.fContentTitle;
    }

    public CoronaData.Table getData() {
        return this.fData;
    }

    public int getIconResourceId() {
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext == null) {
            return 0;
        }
        return new ResourceServices(applicationContext).getDrawableResourceId(this.fIconResourceName);
    }

    public String getIconResourceName() {
        return this.fIconResourceName;
    }

    public int getId() {
        return this.fId;
    }

    public Uri getSoundFileUri() {
        return this.fSoundFileUri;
    }

    public CoronaData getSourceData() {
        return this.fSourceData;
    }

    public String getSourceDataName() {
        return this.fSourceDataName;
    }

    public String getSourceName() {
        return this.fSourceName;
    }

    public String getTickerText() {
        return this.fTickerText;
    }

    public Date getTimestamp() {
        return (Date) this.fTimestamp.clone();
    }

    public boolean isRemovable() {
        return this.fIsRemovable;
    }

    public boolean isSourceLocal() {
        return this.fIsSourceLocal;
    }

    public void setBadgeNumber(int i) {
        if (i < 0) {
            i = 0;
        }
        this.fBadgeNumber = i;
    }

    public void setContentText(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fContentText = str;
    }

    public void setContentTitle(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fContentTitle = str;
    }

    public void setData(CoronaData.Table table) {
        this.fData = table;
    }

    public void setIconResourceName(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fIconResourceName = str;
    }

    public void setId(int i) {
        this.fId = i;
    }

    public void setRemovable(boolean z) {
        this.fIsRemovable = z;
    }

    public void setSoundFileUri(Uri uri) {
        this.fSoundFileUri = uri;
    }

    public void setSourceData(CoronaData coronaData) {
        this.fSourceData = coronaData;
    }

    public void setSourceDataName(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fSourceDataName = str;
    }

    public void setSourceLocal(boolean z) {
        this.fIsSourceLocal = z;
    }

    public void setSourceName(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fSourceName = str;
    }

    public void setTickerText(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fTickerText = str;
    }

    public void setTimestamp(Date date) {
        if (date == null) {
            throw new NullPointerException();
        }
        this.fTimestamp = (Date) date.clone();
    }
}
