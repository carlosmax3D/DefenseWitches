package com.google.android.gms.plus;

import android.app.PendingIntent;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.widget.ExploreByTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.internal.eg;
import com.google.android.gms.internal.hq;
import com.google.android.gms.internal.ht;

public final class PlusOneButtonWithPopup extends ViewGroup {
    private View DB;
    private int DC;
    private View.OnClickListener DH;
    private String iH;
    private String jG;
    private int mSize;

    public PlusOneButtonWithPopup(Context context) {
        this(context, (AttributeSet) null);
    }

    public PlusOneButtonWithPopup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSize = PlusOneButton.getSize(context, attributeSet);
        this.DC = PlusOneButton.getAnnotation(context, attributeSet);
        this.DB = new PlusOneDummyView(context, this.mSize);
        addView(this.DB);
    }

    private int c(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        switch (mode) {
            case ExploreByTouchHelper.INVALID_ID:
            case 1073741824:
                return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i) - i2, mode);
            default:
                return i;
        }
    }

    private void eL() {
        if (this.DB != null) {
            removeView(this.DB);
        }
        this.DB = ht.a(getContext(), this.mSize, this.DC, this.iH, this.jG);
        if (this.DH != null) {
            setOnClickListener(this.DH);
        }
        addView(this.DB);
    }

    private hq eM() throws RemoteException {
        hq aw = hq.a.aw((IBinder) this.DB.getTag());
        if (aw != null) {
            return aw;
        }
        if (Log.isLoggable("PlusOneButtonWithPopup", 5)) {
            Log.w("PlusOneButtonWithPopup", "Failed to get PlusOneDelegate");
        }
        throw new RemoteException();
    }

    public void cancelClick() {
        if (this.DB != null) {
            try {
                eM().cancelClick();
            } catch (RemoteException e) {
            }
        }
    }

    public PendingIntent getResolution() {
        if (this.DB != null) {
            try {
                return eM().getResolution();
            } catch (RemoteException e) {
            }
        }
        return null;
    }

    public void initialize(String str, String str2) {
        eg.b(str, (Object) "Url must not be null");
        this.iH = str;
        this.jG = str2;
        eL();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.DB.layout(getPaddingLeft(), getPaddingTop(), (i3 - i) - getPaddingRight(), (i4 - i2) - getPaddingBottom());
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        this.DB.measure(c(i, paddingLeft), c(i2, paddingTop));
        setMeasuredDimension(paddingLeft + this.DB.getMeasuredWidth(), paddingTop + this.DB.getMeasuredHeight());
    }

    public void reinitialize() {
        if (this.DB != null) {
            try {
                eM().reinitialize();
            } catch (RemoteException e) {
            }
        }
    }

    public void setAnnotation(int i) {
        this.DC = i;
        eL();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.DH = onClickListener;
        this.DB.setOnClickListener(onClickListener);
    }

    public void setSize(int i) {
        this.mSize = i;
        eL();
    }
}
