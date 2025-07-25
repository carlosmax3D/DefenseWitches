package com.google.android.gms.drive.widget;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.android.gms.common.data.DataBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DataBufferAdapter<T> extends BaseAdapter {
    private final Context mContext;
    private final int sm;
    private int sn;
    private final int so;
    private final List<DataBuffer<T>> sp;
    private final LayoutInflater sq;
    private boolean sr;

    public DataBufferAdapter(Context context, int i) {
        this(context, i, 0, new ArrayList());
    }

    public DataBufferAdapter(Context context, int i, int i2) {
        this(context, i, i2, new ArrayList());
    }

    public DataBufferAdapter(Context context, int i, int i2, List<DataBuffer<T>> list) {
        this.sr = true;
        this.mContext = context;
        this.sn = i;
        this.sm = i;
        this.so = i2;
        this.sp = list;
        this.sq = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public DataBufferAdapter(Context context, int i, int i2, DataBuffer<T>... dataBufferArr) {
        this(context, i, i2, Arrays.asList(dataBufferArr));
    }

    public DataBufferAdapter(Context context, int i, List<DataBuffer<T>> list) {
        this(context, i, 0, list);
    }

    public DataBufferAdapter(Context context, int i, DataBuffer<T>... dataBufferArr) {
        this(context, i, 0, Arrays.asList(dataBufferArr));
    }

    private View a(int i, View view, ViewGroup viewGroup, int i2) {
        View inflate = view == null ? this.sq.inflate(i2, viewGroup, false) : view;
        try {
            TextView textView = this.so == 0 ? (TextView) inflate : (TextView) inflate.findViewById(this.so);
            Object item = getItem(i);
            if (item instanceof CharSequence) {
                textView.setText((CharSequence) item);
            } else {
                textView.setText(item.toString());
            }
            return inflate;
        } catch (ClassCastException e) {
            Log.e("DataBufferAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException("DataBufferAdapter requires the resource ID to be a TextView", e);
        }
    }

    public void append(DataBuffer<T> dataBuffer) {
        this.sp.add(dataBuffer);
        if (this.sr) {
            notifyDataSetChanged();
        }
    }

    public void clear() {
        for (DataBuffer<T> close : this.sp) {
            close.close();
        }
        this.sp.clear();
        if (this.sr) {
            notifyDataSetChanged();
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public int getCount() {
        int i = 0;
        Iterator<DataBuffer<T>> it = this.sp.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            i = it.next().getCount() + i2;
        }
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return a(i, view, viewGroup, this.sn);
    }

    public T getItem(int i) throws CursorIndexOutOfBoundsException {
        int i2 = i;
        for (DataBuffer next : this.sp) {
            int count = next.getCount();
            if (count <= i2) {
                i2 -= count;
            } else {
                try {
                    return next.get(i2);
                } catch (CursorIndexOutOfBoundsException e) {
                    throw new CursorIndexOutOfBoundsException(i, getCount());
                }
            }
        }
        throw new CursorIndexOutOfBoundsException(i, getCount());
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        return a(i, view, viewGroup, this.sm);
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.sr = true;
    }

    public void setDropDownViewResource(int i) {
        this.sn = i;
    }

    public void setNotifyOnChange(boolean z) {
        this.sr = z;
    }
}
