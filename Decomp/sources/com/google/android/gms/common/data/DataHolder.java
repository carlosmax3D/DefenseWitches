package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.AbstractWindowedCursor;
import android.database.CharArrayBuffer;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ds;
import com.google.android.gms.internal.ee;
import com.google.android.gms.internal.eg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class DataHolder implements SafeParcelable {
    public static final DataHolderCreator CREATOR = new DataHolderCreator();
    private static final Builder nS = new Builder(new String[0], (String) null) {
        public Builder withRow(ContentValues contentValues) {
            throw new UnsupportedOperationException("Cannot add data to empty builder");
        }

        public Builder withRow(HashMap<String, Object> hashMap) {
            throw new UnsupportedOperationException("Cannot add data to empty builder");
        }
    };
    private final int kg;
    private final int mC;
    boolean mClosed;
    private final String[] nK;
    Bundle nL;
    private final CursorWindow[] nM;
    private final Bundle nN;
    int[] nO;
    int nP;
    private Object nQ;
    private boolean nR;

    public static class Builder {
        /* access modifiers changed from: private */
        public final String[] nK;
        /* access modifiers changed from: private */
        public final ArrayList<HashMap<String, Object>> nT;
        private final String nU;
        private final HashMap<Object, Integer> nV;
        private boolean nW;
        private String nX;

        private Builder(String[] strArr, String str) {
            this.nK = (String[]) eg.f(strArr);
            this.nT = new ArrayList<>();
            this.nU = str;
            this.nV = new HashMap<>();
            this.nW = false;
            this.nX = null;
        }

        private void a(HashMap<String, Object> hashMap) {
            Object obj = hashMap.get(this.nU);
            if (obj != null) {
                Integer remove = this.nV.remove(obj);
                if (remove != null) {
                    this.nT.remove(remove.intValue());
                }
                this.nV.put(obj, Integer.valueOf(this.nT.size()));
            }
        }

        private void bx() {
            if (this.nU != null) {
                this.nV.clear();
                int size = this.nT.size();
                for (int i = 0; i < size; i++) {
                    Object obj = this.nT.get(i).get(this.nU);
                    if (obj != null) {
                        this.nV.put(obj, Integer.valueOf(i));
                    }
                }
            }
        }

        public DataHolder build(int i) {
            return new DataHolder(this, i, (Bundle) null);
        }

        public DataHolder build(int i, Bundle bundle) {
            return new DataHolder(this, i, bundle, -1);
        }

        public DataHolder build(int i, Bundle bundle, int i2) {
            return new DataHolder(this, i, bundle, i2);
        }

        public int getCount() {
            return this.nT.size();
        }

        public Builder removeRowsWithValue(String str, Object obj) {
            for (int size = this.nT.size() - 1; size >= 0; size--) {
                if (ee.equal(this.nT.get(size).get(str), obj)) {
                    this.nT.remove(size);
                }
            }
            return this;
        }

        public Builder sort(String str) {
            ds.d(str);
            if (!this.nW || !str.equals(this.nX)) {
                Collections.sort(this.nT, new a(str));
                bx();
                this.nW = true;
                this.nX = str;
            }
            return this;
        }

        public Builder withRow(ContentValues contentValues) {
            ds.d(contentValues);
            HashMap hashMap = new HashMap(contentValues.size());
            for (Map.Entry next : contentValues.valueSet()) {
                hashMap.put(next.getKey(), next.getValue());
            }
            return withRow((HashMap<String, Object>) hashMap);
        }

        public Builder withRow(HashMap<String, Object> hashMap) {
            ds.d(hashMap);
            if (this.nU != null) {
                a(hashMap);
            }
            this.nT.add(hashMap);
            this.nW = false;
            return this;
        }
    }

    private static final class a implements Comparator<HashMap<String, Object>> {
        private final String nY;

        a(String str) {
            this.nY = (String) eg.f(str);
        }

        /* renamed from: a */
        public int compare(HashMap<String, Object> hashMap, HashMap<String, Object> hashMap2) {
            Object f = eg.f(hashMap.get(this.nY));
            Object f2 = eg.f(hashMap2.get(this.nY));
            if (f.equals(f2)) {
                return 0;
            }
            if (f instanceof Boolean) {
                return ((Boolean) f).compareTo((Boolean) f2);
            }
            if (f instanceof Long) {
                return ((Long) f).compareTo((Long) f2);
            }
            if (f instanceof Integer) {
                return ((Integer) f).compareTo((Integer) f2);
            }
            if (f instanceof String) {
                return ((String) f).compareTo((String) f2);
            }
            throw new IllegalArgumentException("Unknown type for lValue " + f);
        }
    }

    DataHolder(int i, String[] strArr, CursorWindow[] cursorWindowArr, int i2, Bundle bundle) {
        this.mClosed = false;
        this.nR = true;
        this.kg = i;
        this.nK = strArr;
        this.nM = cursorWindowArr;
        this.mC = i2;
        this.nN = bundle;
    }

    public DataHolder(AbstractWindowedCursor abstractWindowedCursor, int i, Bundle bundle) {
        this(abstractWindowedCursor.getColumnNames(), a(abstractWindowedCursor), i, bundle);
    }

    private DataHolder(Builder builder, int i, Bundle bundle) {
        this(builder.nK, a(builder, -1), i, bundle);
    }

    private DataHolder(Builder builder, int i, Bundle bundle, int i2) {
        this(builder.nK, a(builder, i2), i, bundle);
    }

    public DataHolder(String[] strArr, CursorWindow[] cursorWindowArr, int i, Bundle bundle) {
        this.mClosed = false;
        this.nR = true;
        this.kg = 1;
        this.nK = (String[]) eg.f(strArr);
        this.nM = (CursorWindow[]) eg.f(cursorWindowArr);
        this.mC = i;
        this.nN = bundle;
        validateContents();
    }

    /* JADX INFO: finally extract failed */
    private static CursorWindow[] a(AbstractWindowedCursor abstractWindowedCursor) {
        int i;
        ArrayList arrayList = new ArrayList();
        try {
            int count = abstractWindowedCursor.getCount();
            CursorWindow window = abstractWindowedCursor.getWindow();
            if (window == null || window.getStartPosition() != 0) {
                i = 0;
            } else {
                window.acquireReference();
                abstractWindowedCursor.setWindow((CursorWindow) null);
                arrayList.add(window);
                i = window.getNumRows();
            }
            while (i < count && abstractWindowedCursor.moveToPosition(i)) {
                CursorWindow window2 = abstractWindowedCursor.getWindow();
                if (window2 != null) {
                    window2.acquireReference();
                    abstractWindowedCursor.setWindow((CursorWindow) null);
                } else {
                    window2 = new CursorWindow(false);
                    window2.setStartPosition(i);
                    abstractWindowedCursor.fillWindow(i, window2);
                }
                if (window2.getNumRows() == 0) {
                    break;
                }
                arrayList.add(window2);
                i = window2.getNumRows() + window2.getStartPosition();
            }
            abstractWindowedCursor.close();
            return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
        } catch (Throwable th) {
            abstractWindowedCursor.close();
            throw th;
        }
    }

    private static CursorWindow[] a(Builder builder, int i) {
        int i2;
        int i3;
        int i4;
        CursorWindow cursorWindow;
        if (builder.nK.length == 0) {
            return new CursorWindow[0];
        }
        ArrayList b = (i < 0 || i >= builder.nT.size()) ? builder.nT : builder.nT.subList(0, i);
        int size = b.size();
        CursorWindow cursorWindow2 = new CursorWindow(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(cursorWindow2);
        cursorWindow2.setNumColumns(builder.nK.length);
        int i5 = 0;
        int i6 = 0;
        while (i5 < size) {
            try {
                if (!cursorWindow2.allocRow()) {
                    Log.d("DataHolder", "Allocating additional cursor window for large data set (row " + i5 + ")");
                    cursorWindow2 = new CursorWindow(false);
                    cursorWindow2.setStartPosition(i5);
                    cursorWindow2.setNumColumns(builder.nK.length);
                    arrayList.add(cursorWindow2);
                    if (!cursorWindow2.allocRow()) {
                        Log.e("DataHolder", "Unable to allocate row to hold data.");
                        arrayList.remove(cursorWindow2);
                        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                    }
                    i2 = 0;
                } else {
                    i2 = i6;
                }
                Map map = (Map) b.get(i5);
                boolean z = true;
                for (int i7 = 0; i7 < builder.nK.length && z; i7++) {
                    String str = builder.nK[i7];
                    Object obj = map.get(str);
                    if (obj == null) {
                        z = cursorWindow2.putNull(i2, i7);
                    } else if (obj instanceof String) {
                        z = cursorWindow2.putString((String) obj, i2, i7);
                    } else if (obj instanceof Long) {
                        z = cursorWindow2.putLong(((Long) obj).longValue(), i2, i7);
                    } else if (obj instanceof Integer) {
                        z = cursorWindow2.putLong((long) ((Integer) obj).intValue(), i2, i7);
                    } else if (obj instanceof Boolean) {
                        z = cursorWindow2.putLong(((Boolean) obj).booleanValue() ? 1 : 0, i2, i7);
                    } else if (obj instanceof byte[]) {
                        z = cursorWindow2.putBlob((byte[]) obj, i2, i7);
                    } else {
                        throw new IllegalArgumentException("Unsupported object for column " + str + ": " + obj);
                    }
                }
                if (!z) {
                    Log.d("DataHolder", "Couldn't populate window data for row " + i5 + " - allocating new window.");
                    cursorWindow2.freeLastRow();
                    CursorWindow cursorWindow3 = new CursorWindow(false);
                    cursorWindow3.setNumColumns(builder.nK.length);
                    arrayList.add(cursorWindow3);
                    i4 = i5 - 1;
                    cursorWindow = cursorWindow3;
                    i3 = 0;
                } else {
                    i3 = i2 + 1;
                    i4 = i5;
                    cursorWindow = cursorWindow2;
                }
                cursorWindow2 = cursorWindow;
                i5 = i4 + 1;
                i6 = i3;
            } catch (RuntimeException e) {
                RuntimeException runtimeException = e;
                int size2 = arrayList.size();
                for (int i8 = 0; i8 < size2; i8++) {
                    ((CursorWindow) arrayList.get(i8)).close();
                }
                throw runtimeException;
            }
        }
        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
    }

    private void b(String str, int i) {
        if (this.nL == null || !this.nL.containsKey(str)) {
            throw new IllegalArgumentException("No such column: " + str);
        } else if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        } else if (i < 0 || i >= this.nP) {
            throw new CursorIndexOutOfBoundsException(i, this.nP);
        }
    }

    public static Builder builder(String[] strArr) {
        return new Builder(strArr, (String) null);
    }

    public static Builder builder(String[] strArr, String str) {
        eg.f(str);
        return new Builder(strArr, str);
    }

    public static DataHolder empty(int i) {
        return empty(i, (Bundle) null);
    }

    public static DataHolder empty(int i, Bundle bundle) {
        return new DataHolder(nS, i, bundle);
    }

    public int C(int i) {
        int i2 = 0;
        eg.p(i >= 0 && i < this.nP);
        while (true) {
            if (i2 >= this.nO.length) {
                break;
            } else if (i < this.nO[i2]) {
                i2--;
                break;
            } else {
                i2++;
            }
        }
        return i2 == this.nO.length ? i2 - 1 : i2;
    }

    /* access modifiers changed from: package-private */
    public String[] bv() {
        return this.nK;
    }

    /* access modifiers changed from: package-private */
    public CursorWindow[] bw() {
        return this.nM;
    }

    public void c(Object obj) {
        this.nQ = obj;
    }

    public void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (CursorWindow close : this.nM) {
                    close.close();
                }
            }
        }
    }

    public void copyToBuffer(String str, int i, int i2, CharArrayBuffer charArrayBuffer) {
        b(str, i);
        this.nM[i2].copyStringToBuffer(i, this.nL.getInt(str), charArrayBuffer);
    }

    public int describeContents() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            if (this.nR && this.nM.length > 0 && !isClosed()) {
                Log.e("DataBuffer", "Internal data leak within a DataBuffer object detected!  Be sure to explicitly call close() on all DataBuffer extending objects when you are done with them. (" + (this.nQ == null ? "internal object: " + toString() : this.nQ.toString()) + ")");
                close();
            }
        } finally {
            super.finalize();
        }
    }

    public boolean getBoolean(String str, int i, int i2) {
        b(str, i);
        return Long.valueOf(this.nM[i2].getLong(i, this.nL.getInt(str))).longValue() == 1;
    }

    public byte[] getByteArray(String str, int i, int i2) {
        b(str, i);
        return this.nM[i2].getBlob(i, this.nL.getInt(str));
    }

    public int getCount() {
        return this.nP;
    }

    public int getInteger(String str, int i, int i2) {
        b(str, i);
        return this.nM[i2].getInt(i, this.nL.getInt(str));
    }

    public long getLong(String str, int i, int i2) {
        b(str, i);
        return this.nM[i2].getLong(i, this.nL.getInt(str));
    }

    public Bundle getMetadata() {
        return this.nN;
    }

    public int getStatusCode() {
        return this.mC;
    }

    public String getString(String str, int i, int i2) {
        b(str, i);
        return this.nM[i2].getString(i, this.nL.getInt(str));
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public boolean hasColumn(String str) {
        return this.nL.containsKey(str);
    }

    public boolean hasNull(String str, int i, int i2) {
        b(str, i);
        return this.nM[i2].isNull(i, this.nL.getInt(str));
    }

    public boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.mClosed;
        }
        return z;
    }

    public Uri parseUri(String str, int i, int i2) {
        String string = getString(str, i, i2);
        if (string == null) {
            return null;
        }
        return Uri.parse(string);
    }

    public void validateContents() {
        this.nL = new Bundle();
        for (int i = 0; i < this.nK.length; i++) {
            this.nL.putInt(this.nK[i], i);
        }
        this.nO = new int[this.nM.length];
        int i2 = 0;
        for (int i3 = 0; i3 < this.nM.length; i3++) {
            this.nO[i3] = i2;
            i2 += this.nM[i3].getNumRows() - (i2 - this.nM[i3].getStartPosition());
        }
        this.nP = i2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        DataHolderCreator.a(this, parcel, i);
    }
}
