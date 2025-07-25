package com.ansca.corona.maps;

import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.naef.jnlua.LuaState;

public class MapMarker {
    String fImageFile;
    double fLatitude;
    int fListener;
    double fLongitude;
    int fMarkerId;
    String fSubtitle;
    String fTitle;

    public MapMarker(double d, double d2) {
        this.fMarkerId = -1;
        this.fListener = -1;
        this.fLatitude = d;
        this.fLongitude = d2;
        this.fSubtitle = null;
        this.fTitle = null;
        this.fImageFile = null;
    }

    public MapMarker(int i, double d, double d2, String str, String str2, int i2, String str3) {
        this.fMarkerId = i;
        this.fListener = i2;
        this.fLatitude = d;
        this.fLongitude = d2;
        this.fTitle = str;
        this.fSubtitle = str2;
        this.fImageFile = str3;
    }

    public void deleteRef(CoronaRuntime coronaRuntime) {
        LuaState luaState;
        if (coronaRuntime != null && this.fListener != -1 && (luaState = coronaRuntime.getLuaState()) != null) {
            CoronaLua.deleteRef(luaState, this.fListener);
        }
    }

    public String getImageFile() {
        return this.fImageFile;
    }

    public double getLatitude() {
        return this.fLatitude;
    }

    public int getListener() {
        return this.fListener;
    }

    public double getLongitude() {
        return this.fLongitude;
    }

    public int getMarkerId() {
        return this.fMarkerId;
    }

    public String getSubtitle() {
        return this.fSubtitle;
    }

    public String getTitle() {
        return this.fTitle;
    }

    public void setImageFile(String str) {
        this.fImageFile = str;
    }

    public void setLatitude(double d) {
        this.fLatitude = d;
    }

    public void setListener(int i) {
        this.fListener = i;
    }

    public void setLongitude(double d) {
        this.fLongitude = d;
    }

    public void setMarkerId(int i) {
        this.fMarkerId = i;
    }

    public void setSubtitle(String str) {
        this.fSubtitle = str;
    }

    public void setTitle(String str) {
        this.fTitle = str;
    }
}
