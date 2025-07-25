package com.google.android.gms.appstate;

import com.google.android.gms.internal.ee;

public final class a implements AppState {
    private final int jI;
    private final String jJ;
    private final byte[] jK;
    private final boolean jL;
    private final String jM;
    private final byte[] jN;

    public a(AppState appState) {
        this.jI = appState.getKey();
        this.jJ = appState.getLocalVersion();
        this.jK = appState.getLocalData();
        this.jL = appState.hasConflict();
        this.jM = appState.getConflictVersion();
        this.jN = appState.getConflictData();
    }

    static int a(AppState appState) {
        return ee.hashCode(Integer.valueOf(appState.getKey()), appState.getLocalVersion(), appState.getLocalData(), Boolean.valueOf(appState.hasConflict()), appState.getConflictVersion(), appState.getConflictData());
    }

    static boolean a(AppState appState, Object obj) {
        if (!(obj instanceof AppState)) {
            return false;
        }
        if (appState == obj) {
            return true;
        }
        AppState appState2 = (AppState) obj;
        return ee.equal(Integer.valueOf(appState2.getKey()), Integer.valueOf(appState.getKey())) && ee.equal(appState2.getLocalVersion(), appState.getLocalVersion()) && ee.equal(appState2.getLocalData(), appState.getLocalData()) && ee.equal(Boolean.valueOf(appState2.hasConflict()), Boolean.valueOf(appState.hasConflict())) && ee.equal(appState2.getConflictVersion(), appState.getConflictVersion()) && ee.equal(appState2.getConflictData(), appState.getConflictData());
    }

    static String b(AppState appState) {
        return ee.e(appState).a("Key", Integer.valueOf(appState.getKey())).a("LocalVersion", appState.getLocalVersion()).a("LocalData", appState.getLocalData()).a("HasConflict", Boolean.valueOf(appState.hasConflict())).a("ConflictVersion", appState.getConflictVersion()).a("ConflictData", appState.getConflictData()).toString();
    }

    /* renamed from: aK */
    public AppState freeze() {
        return this;
    }

    public boolean equals(Object obj) {
        return a(this, obj);
    }

    public byte[] getConflictData() {
        return this.jN;
    }

    public String getConflictVersion() {
        return this.jM;
    }

    public int getKey() {
        return this.jI;
    }

    public byte[] getLocalData() {
        return this.jK;
    }

    public String getLocalVersion() {
        return this.jJ;
    }

    public boolean hasConflict() {
        return this.jL;
    }

    public int hashCode() {
        return a(this);
    }

    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return b(this);
    }
}
