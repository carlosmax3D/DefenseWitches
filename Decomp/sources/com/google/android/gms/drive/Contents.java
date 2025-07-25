package com.google.android.gms.drive;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Contents implements SafeParcelable {
    public static final Parcelable.Creator<Contents> CREATOR = new a();
    final int kg;
    private boolean mClosed = false;
    final ParcelFileDescriptor om;
    final int qE;
    final int qF;
    final DriveId qG;
    private boolean qH = false;
    private boolean qI = false;

    Contents(int i, ParcelFileDescriptor parcelFileDescriptor, int i2, int i3, DriveId driveId) {
        this.kg = i;
        this.om = parcelFileDescriptor;
        this.qE = i2;
        this.qF = i3;
        this.qG = driveId;
    }

    public int cJ() {
        return this.qE;
    }

    public void close() {
        this.mClosed = true;
    }

    public int describeContents() {
        return 0;
    }

    public DriveId getDriveId() {
        return this.qG;
    }

    public InputStream getInputStream() {
        if (this.mClosed) {
            throw new IllegalStateException("Contents have been closed, cannot access the input stream.");
        } else if (this.qF != 268435456) {
            throw new IllegalStateException("getInputStream() can only be used with contents opened with MODE_READ_ONLY.");
        } else if (this.qH) {
            throw new IllegalStateException("getInputStream() can only be called once per Contents instance.");
        } else {
            this.qH = true;
            return new FileInputStream(this.om.getFileDescriptor());
        }
    }

    public int getMode() {
        return this.qF;
    }

    public OutputStream getOutputStream() {
        if (this.mClosed) {
            throw new IllegalStateException("Contents have been closed, cannot access the output stream.");
        } else if (this.qF != 536870912) {
            throw new IllegalStateException("getOutputStream() can only be used with contents opened with MODE_WRITE_ONLY.");
        } else if (this.qI) {
            throw new IllegalStateException("getOutputStream() can only be called once per Contents instance.");
        } else {
            this.qI = true;
            return new FileOutputStream(this.om.getFileDescriptor());
        }
    }

    public ParcelFileDescriptor getParcelFileDescriptor() {
        if (!this.mClosed) {
            return this.om;
        }
        throw new IllegalStateException("Contents have been closed, cannot access the output stream.");
    }

    public void writeToParcel(Parcel parcel, int i) {
        a.a(this, parcel, i);
    }
}
