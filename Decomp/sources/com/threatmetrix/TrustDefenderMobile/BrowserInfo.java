package com.threatmetrix.TrustDefenderMobile;

import com.facebook.appevents.AppEventsConstants;

class BrowserInfo {
    String m_browserPluginCount = AppEventsConstants.EVENT_PARAM_VALUE_NO;
    String m_browserPlugins = null;
    String m_browserPluginsFromJS = null;
    String m_browserPluginsFromJSHash = null;
    String m_browserStringFromJS = null;
    int m_mimeTypeCount = 0;
    String m_mimeTypes = null;
    String m_mimeTypesHash = null;

    BrowserInfo() {
    }

    private String getBrowserPluginsFromJS() {
        return this.m_browserPluginsFromJS;
    }

    private String getMimeTypes() {
        return this.m_mimeTypes;
    }

    public final String getBrowserPluginCount() {
        return this.m_browserPluginCount;
    }

    public final String getBrowserPlugins() {
        return this.m_browserPlugins;
    }

    public final String getBrowserPluginsFromJSHash() {
        return this.m_browserPluginsFromJSHash;
    }

    public String getBrowserStringFromJS() {
        return this.m_browserStringFromJS;
    }

    public final int getMimeTypeCount() {
        return this.m_mimeTypeCount;
    }

    public final String getMimeTypesHash() {
        return this.m_mimeTypesHash;
    }
}
