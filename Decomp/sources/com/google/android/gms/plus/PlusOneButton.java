package com.google.android.gms.plus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.gms.internal.eg;
import com.google.android.gms.internal.ej;
import com.google.android.gms.internal.ht;
import com.tapjoy.TapjoyConstants;

public final class PlusOneButton extends FrameLayout {
    public static final int ANNOTATION_BUBBLE = 1;
    public static final int ANNOTATION_INLINE = 2;
    public static final int ANNOTATION_NONE = 0;
    public static final int DEFAULT_ACTIVITY_REQUEST_CODE = -1;
    public static final int SIZE_MEDIUM = 1;
    public static final int SIZE_SMALL = 0;
    public static final int SIZE_STANDARD = 3;
    public static final int SIZE_TALL = 2;
    /* access modifiers changed from: private */
    public View DB;
    private int DC;
    /* access modifiers changed from: private */
    public int DD;
    private OnPlusOneClickListener DE;
    private String iH;
    private int mSize;

    protected class DefaultOnPlusOneClickListener implements View.OnClickListener, OnPlusOneClickListener {
        private final OnPlusOneClickListener DF;

        public DefaultOnPlusOneClickListener(OnPlusOneClickListener onPlusOneClickListener) {
            this.DF = onPlusOneClickListener;
        }

        public void onClick(View view) {
            Intent intent = (Intent) PlusOneButton.this.DB.getTag();
            if (this.DF != null) {
                this.DF.onPlusOneClick(intent);
            } else {
                onPlusOneClick(intent);
            }
        }

        public void onPlusOneClick(Intent intent) {
            Context context = PlusOneButton.this.getContext();
            if ((context instanceof Activity) && intent != null) {
                ((Activity) context).startActivityForResult(intent, PlusOneButton.this.DD);
            }
        }
    }

    public interface OnPlusOneClickListener {
        void onPlusOneClick(Intent intent);
    }

    public PlusOneButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public PlusOneButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSize = getSize(context, attributeSet);
        this.DC = getAnnotation(context, attributeSet);
        this.DD = -1;
        p(getContext());
        if (isInEditMode()) {
        }
    }

    protected static int getAnnotation(Context context, AttributeSet attributeSet) {
        String a = ej.a("http://schemas.android.com/apk/lib/com.google.android.gms.plus", "annotation", context, attributeSet, true, false, "PlusOneButton");
        if ("INLINE".equalsIgnoreCase(a)) {
            return 2;
        }
        return !"NONE".equalsIgnoreCase(a) ? 1 : 0;
    }

    protected static int getSize(Context context, AttributeSet attributeSet) {
        String a = ej.a("http://schemas.android.com/apk/lib/com.google.android.gms.plus", TapjoyConstants.TJC_DISPLAY_AD_SIZE, context, attributeSet, true, false, "PlusOneButton");
        if ("SMALL".equalsIgnoreCase(a)) {
            return 0;
        }
        if ("MEDIUM".equalsIgnoreCase(a)) {
            return 1;
        }
        return "TALL".equalsIgnoreCase(a) ? 2 : 3;
    }

    private void p(Context context) {
        if (this.DB != null) {
            removeView(this.DB);
        }
        this.DB = ht.a(context, this.mSize, this.DC, this.iH, this.DD);
        setOnPlusOneClickListener(this.DE);
        addView(this.DB);
    }

    public void initialize(String str, int i) {
        eg.a(getContext() instanceof Activity, "To use this method, the PlusOneButton must be placed in an Activity. Use initialize(PlusClient, String, OnPlusOneClickListener).");
        this.iH = str;
        this.DD = i;
        p(getContext());
    }

    public void initialize(String str, OnPlusOneClickListener onPlusOneClickListener) {
        this.iH = str;
        this.DD = 0;
        p(getContext());
        setOnPlusOneClickListener(onPlusOneClickListener);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.DB.layout(0, 0, i3 - i, i4 - i2);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        View view = this.DB;
        measureChild(view, i, i2);
        setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public void setAnnotation(int i) {
        this.DC = i;
        p(getContext());
    }

    public void setOnPlusOneClickListener(OnPlusOneClickListener onPlusOneClickListener) {
        this.DE = onPlusOneClickListener;
        this.DB.setOnClickListener(new DefaultOnPlusOneClickListener(onPlusOneClickListener));
    }

    public void setSize(int i) {
        this.mSize = i;
        p(getContext());
    }
}
