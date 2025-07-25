package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public final class bp extends FrameLayout implements View.OnClickListener {
    private final ImageButton gZ;
    private final Activity gs;

    public bp(Activity activity, int i) {
        super(activity);
        this.gs = activity;
        setOnClickListener(this);
        this.gZ = new ImageButton(activity);
        this.gZ.setImageResource(17301527);
        this.gZ.setBackgroundColor(0);
        this.gZ.setOnClickListener(this);
        this.gZ.setPadding(0, 0, 0, 0);
        int a = cs.a((Context) activity, i);
        addView(this.gZ, new FrameLayout.LayoutParams(a, a, 17));
    }

    public void g(boolean z) {
        this.gZ.setVisibility(z ? 4 : 0);
    }

    public void onClick(View view) {
        this.gs.finish();
    }
}
