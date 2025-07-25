package com.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import com.facebook.appevents.AppEventsLogger;

public abstract class FacebookButtonBase extends Button {
    private String analyticsButtonCreatedEventName;
    /* access modifiers changed from: private */
    public View.OnClickListener externalOnClickListener;
    /* access modifiers changed from: private */
    public View.OnClickListener internalOnClickListener;
    private boolean overrideCompoundPadding;
    private int overrideCompoundPaddingLeft;
    private int overrideCompoundPaddingRight;
    private Fragment parentFragment;
    private int requestCode;

    protected FacebookButtonBase(Context context, AttributeSet attributeSet, int i, int i2, String str, int i3) {
        super(context, attributeSet, 0);
        i2 = i2 == 0 ? getDefaultStyleResource() : i2;
        configureButton(context, attributeSet, i, i2 == 0 ? R.style.com_facebook_button : i2);
        this.analyticsButtonCreatedEventName = str;
        this.requestCode = i3;
    }

    private void logButtonCreated(Context context) {
        AppEventsLogger.newLogger(context).logSdkEvent(this.analyticsButtonCreatedEventName, (Double) null, (Bundle) null);
    }

    private void parseBackgroundAttributes(Context context, AttributeSet attributeSet, int i, int i2) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, new int[]{16842964}, i, i2);
        try {
            if (obtainStyledAttributes.hasValue(0)) {
                int resourceId = obtainStyledAttributes.getResourceId(0, 0);
                if (resourceId != 0) {
                    setBackgroundResource(resourceId);
                } else {
                    setBackgroundColor(obtainStyledAttributes.getColor(0, 0));
                }
            } else {
                setBackgroundColor(obtainStyledAttributes.getColor(0, R.color.com_facebook_blue));
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    private void parseCompoundDrawableAttributes(Context context, AttributeSet attributeSet, int i, int i2) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, new int[]{16843119, 16843117, 16843120, 16843118, 16843121}, i, i2);
        try {
            setCompoundDrawablesWithIntrinsicBounds(obtainStyledAttributes.getResourceId(0, 0), obtainStyledAttributes.getResourceId(1, 0), obtainStyledAttributes.getResourceId(2, 0), obtainStyledAttributes.getResourceId(3, 0));
            setCompoundDrawablePadding(obtainStyledAttributes.getDimensionPixelSize(4, 0));
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    private void parseContentAttributes(Context context, AttributeSet attributeSet, int i, int i2) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, new int[]{16842966, 16842967, 16842968, 16842969}, i, i2);
        try {
            setPadding(obtainStyledAttributes.getDimensionPixelSize(0, 0), obtainStyledAttributes.getDimensionPixelSize(1, 0), obtainStyledAttributes.getDimensionPixelSize(2, 0), obtainStyledAttributes.getDimensionPixelSize(3, 0));
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* JADX INFO: finally extract failed */
    private void parseTextAttributes(Context context, AttributeSet attributeSet, int i, int i2) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, new int[]{16842904}, i, i2);
        try {
            setTextColor(obtainStyledAttributes.getColor(0, -1));
            obtainStyledAttributes.recycle();
            TypedArray obtainStyledAttributes2 = context.getTheme().obtainStyledAttributes(attributeSet, new int[]{16842927}, i, i2);
            try {
                setGravity(obtainStyledAttributes2.getInt(0, 17));
                obtainStyledAttributes2.recycle();
                TypedArray obtainStyledAttributes3 = context.getTheme().obtainStyledAttributes(attributeSet, new int[]{16842901, 16842903, 16843087}, i, i2);
                try {
                    setTextSize(0, (float) obtainStyledAttributes3.getDimensionPixelSize(0, 0));
                    setTypeface(Typeface.defaultFromStyle(obtainStyledAttributes3.getInt(1, 1)));
                    setText(obtainStyledAttributes3.getString(2));
                } finally {
                    obtainStyledAttributes3.recycle();
                }
            } catch (Throwable th) {
                obtainStyledAttributes2.recycle();
                throw th;
            }
        } catch (Throwable th2) {
            obtainStyledAttributes.recycle();
            throw th2;
        }
    }

    private void setupOnClickListener() {
        super.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (FacebookButtonBase.this.internalOnClickListener != null) {
                    FacebookButtonBase.this.internalOnClickListener.onClick(view);
                } else if (FacebookButtonBase.this.externalOnClickListener != null) {
                    FacebookButtonBase.this.externalOnClickListener.onClick(view);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void callExternalOnClickListener(View view) {
        if (this.externalOnClickListener != null) {
            this.externalOnClickListener.onClick(view);
        }
    }

    /* access modifiers changed from: protected */
    public void configureButton(Context context, AttributeSet attributeSet, int i, int i2) {
        parseBackgroundAttributes(context, attributeSet, i, i2);
        parseCompoundDrawableAttributes(context, attributeSet, i, i2);
        parseContentAttributes(context, attributeSet, i, i2);
        parseTextAttributes(context, attributeSet, i, i2);
        setupOnClickListener();
    }

    /* access modifiers changed from: protected */
    public Activity getActivity() {
        Context context = getContext();
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            Context baseContext = ((ContextWrapper) context).getBaseContext();
            if (baseContext instanceof Activity) {
                return (Activity) baseContext;
            }
        }
        throw new FacebookException("Unable to get Activity.");
    }

    public int getCompoundPaddingLeft() {
        return this.overrideCompoundPadding ? this.overrideCompoundPaddingLeft : super.getCompoundPaddingLeft();
    }

    public int getCompoundPaddingRight() {
        return this.overrideCompoundPadding ? this.overrideCompoundPaddingRight : super.getCompoundPaddingRight();
    }

    /* access modifiers changed from: protected */
    public int getDefaultStyleResource() {
        return 0;
    }

    public Fragment getFragment() {
        return this.parentFragment;
    }

    public int getRequestCode() {
        return this.requestCode;
    }

    /* access modifiers changed from: protected */
    public int measureTextWidth(String str) {
        return (int) Math.ceil((double) getPaint().measureText(str));
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        logButtonCreated(getContext());
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if ((getGravity() & 1) != 0) {
            int compoundPaddingLeft = getCompoundPaddingLeft();
            int compoundPaddingRight = getCompoundPaddingRight();
            int min = Math.min((((getWidth() - (compoundPaddingLeft + getCompoundDrawablePadding())) - compoundPaddingRight) - measureTextWidth(getText().toString())) / 2, (compoundPaddingLeft - getPaddingLeft()) / 2);
            this.overrideCompoundPaddingLeft = compoundPaddingLeft - min;
            this.overrideCompoundPaddingRight = compoundPaddingRight + min;
            this.overrideCompoundPadding = true;
        }
        super.onDraw(canvas);
        this.overrideCompoundPadding = false;
    }

    public void setFragment(Fragment fragment) {
        this.parentFragment = fragment;
    }

    /* access modifiers changed from: protected */
    public void setInternalOnClickListener(View.OnClickListener onClickListener) {
        this.internalOnClickListener = onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.externalOnClickListener = onClickListener;
    }

    /* access modifiers changed from: protected */
    public void setRequestCode(int i) {
        if (FacebookSdk.isFacebookRequestCode(i)) {
            throw new IllegalArgumentException("Request code " + i + " cannot be within the range reserved by the Facebook SDK.");
        }
        this.requestCode = i;
    }
}
