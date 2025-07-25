package com.ansca.corona.input;

public class InputDeviceInterface {
    private InputDeviceContext fDeviceContext;

    InputDeviceInterface(InputDeviceContext inputDeviceContext) {
        if (inputDeviceContext == null) {
            throw new NullPointerException();
        }
        this.fDeviceContext = inputDeviceContext;
    }

    public ConnectionState getConnectionState() {
        return this.fDeviceContext.getConnectionState();
    }

    /* access modifiers changed from: package-private */
    public InputDeviceContext getContext() {
        return this.fDeviceContext;
    }

    public int getCoronaDeviceId() {
        return this.fDeviceContext.getCoronaDeviceId();
    }

    public InputDeviceInfo getDeviceInfo() {
        return this.fDeviceContext.getDeviceInfo();
    }

    public boolean isConnected() {
        return this.fDeviceContext.isConnected();
    }

    public void vibrate() {
        this.fDeviceContext.vibrate();
    }
}
