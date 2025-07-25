package com.ansca.corona.input;

public class InputDeviceStatusEventInfo {
    private Settings fSettings;

    public static class Settings implements Cloneable {
        private boolean fHasConnectionStateChanged = false;
        private boolean fWasReconfigured = false;

        public Settings clone() {
            try {
                return (Settings) super.clone();
            } catch (Exception e) {
                return null;
            }
        }

        public boolean hasConnectionStateChanged() {
            return this.fHasConnectionStateChanged;
        }

        public void setHasConnectionStateChanged(boolean z) {
            this.fHasConnectionStateChanged = z;
        }

        public void setWasReconfigured(boolean z) {
            this.fWasReconfigured = z;
        }

        public boolean wasReconfigured() {
            return this.fWasReconfigured;
        }
    }

    public InputDeviceStatusEventInfo(Settings settings) {
        if (settings == null) {
            throw new NullPointerException();
        }
        this.fSettings = settings.clone();
    }

    public boolean hasConnectionStateChanged() {
        return this.fSettings.hasConnectionStateChanged();
    }

    public boolean wasReconfigured() {
        return this.fSettings.wasReconfigured();
    }
}
