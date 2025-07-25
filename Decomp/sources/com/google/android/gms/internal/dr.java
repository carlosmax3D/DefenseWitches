package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.net.Uri;
import android.widget.ImageView;

public final class dr extends ImageView {
    private Uri oU;
    private int oV;
    private int oW;

    public void H(int i) {
        this.oV = i;
    }

    public int bE() {
        return this.oV;
    }

    public void d(Uri uri) {
        this.oU = uri;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.oW != 0) {
            canvas.drawColor(this.oW);
        }
    }
}
