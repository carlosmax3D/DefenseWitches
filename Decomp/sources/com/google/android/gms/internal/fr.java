package com.google.android.gms.internal;

import android.net.LocalSocket;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

final class fr implements RealTimeSocket {
    private ParcelFileDescriptor om;
    private final LocalSocket uM;
    private final String up;

    fr(LocalSocket localSocket, String str) {
        this.uM = localSocket;
        this.up = str;
    }

    public void close() throws IOException {
        this.uM.close();
    }

    public InputStream getInputStream() throws IOException {
        return this.uM.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return this.uM.getOutputStream();
    }

    public ParcelFileDescriptor getParcelFileDescriptor() throws IOException {
        if (this.om == null && !isClosed()) {
            Parcel obtain = Parcel.obtain();
            obtain.writeFileDescriptor(this.uM.getFileDescriptor());
            obtain.setDataPosition(0);
            this.om = obtain.readFileDescriptor();
        }
        return this.om;
    }

    public boolean isClosed() {
        return !this.uM.isConnected() && !this.uM.isBound();
    }
}
