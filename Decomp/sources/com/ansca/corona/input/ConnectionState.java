package com.ansca.corona.input;

public class ConnectionState {
    public static final ConnectionState CONNECTED = new ConnectionState(1, "connected");
    public static final ConnectionState CONNECTING = new ConnectionState(2, "connecting");
    public static final ConnectionState DISCONNECTED = new ConnectionState(0, "disconnected");
    public static final ConnectionState DISCONNECTING = new ConnectionState(3, "disconnecting");
    private int fCoronaIntegerId;
    private String fCoronaStringId;

    private ConnectionState(int i, String str) {
        this.fCoronaIntegerId = i;
        this.fCoronaStringId = str;
    }

    public int hashCode() {
        return this.fCoronaIntegerId;
    }

    public boolean isConnected() {
        return this == CONNECTED;
    }

    public int toCoronaIntegerId() {
        return this.fCoronaIntegerId;
    }

    public String toCoronaStringId() {
        return this.fCoronaStringId;
    }

    public String toString() {
        return this.fCoronaStringId;
    }
}
