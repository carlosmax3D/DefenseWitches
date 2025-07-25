package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import com.google.android.gms.R;

public final class ei extends Button {
    public ei(Context context) {
        this(context, (AttributeSet) null);
    }

    public ei(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 16842824);
    }

    private int b(int i, int i2, int i3) {
        switch (i) {
            case 0:
                return i2;
            case 1:
                return i3;
            default:
                throw new IllegalStateException("Unknown color scheme: " + i);
        }
    }

    private void b(Resources resources, int i, int i2) {
        int b;
        switch (i) {
            case 0:
            case 1:
                b = b(i2, R.drawable.common_signin_btn_text_dark, R.drawable.common_signin_btn_text_light);
                break;
            case 2:
                b = b(i2, R.drawable.common_signin_btn_icon_dark, R.drawable.common_signin_btn_icon_light);
                break;
            default:
                throw new IllegalStateException("Unknown button size: " + i);
        }
        if (b == -1) {
            throw new IllegalStateException("Could not find background resource!");
        }
        setBackgroundDrawable(resources.getDrawable(b));
    }

    private void c(Resources resources) {
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(14.0f);
        float f = resources.getDisplayMetrics().density;
        setMinHeight((int) ((f * 48.0f) + 0.5f));
        setMinWidth((int) ((f * 48.0f) + 0.5f));
    }

    private void c(Resources resources, int i, int i2) {
        setTextColor(resources.getColorStateList(b(i2, R.color.common_signin_btn_text_dark, R.color.common_signin_btn_text_light)));
        switch (i) {
            case 0:
                setText(resources.getString(R.string.common_signin_button_text));
                return;
            case 1:
                setText(resources.getString(R.string.common_signin_button_text_long));
                return;
            case 2:
                setText((CharSequence) null);
                return;
            default:
                throw new IllegalStateException("Unknown button size: " + i);
        }
    }

    public void a(Resources resources, int i, int i2) {
        boolean z = true;
        eg.a(i >= 0 && i < 3, "Unknown button size " + i);
        if (i2 < 0 || i2 >= 2) {
            z = false;
        }
        eg.a(z, "Unknown color scheme " + i2);
        c(resources);
        b(resources, i, i2);
        c(resources, i, i2);
    }
}
