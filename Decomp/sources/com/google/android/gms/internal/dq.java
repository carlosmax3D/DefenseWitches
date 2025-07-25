package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.v4.view.MotionEventCompat;

public final class dq extends Drawable implements Drawable.Callback {
    private int oB;
    private long oC;
    private int oD;
    private int oE;
    private int oF;
    private int oG;
    private int oH;
    private boolean oI;
    private b oJ;
    private Drawable oK;
    private Drawable oL;
    private boolean oM;
    private boolean oN;
    private boolean oO;
    private int oP;
    private boolean oy;

    private static final class a extends Drawable {
        /* access modifiers changed from: private */
        public static final a oQ = new a();
        private static final C0023a oR = new C0023a();

        /* renamed from: com.google.android.gms.internal.dq$a$a  reason: collision with other inner class name */
        private static final class C0023a extends Drawable.ConstantState {
            private C0023a() {
            }

            public int getChangingConfigurations() {
                return 0;
            }

            public Drawable newDrawable() {
                return a.oQ;
            }
        }

        private a() {
        }

        public void draw(Canvas canvas) {
        }

        public Drawable.ConstantState getConstantState() {
            return oR;
        }

        public int getOpacity() {
            return -2;
        }

        public void setAlpha(int i) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }
    }

    static final class b extends Drawable.ConstantState {
        int oS;
        int oT;

        b(b bVar) {
            if (bVar != null) {
                this.oS = bVar.oS;
                this.oT = bVar.oT;
            }
        }

        public int getChangingConfigurations() {
            return this.oS;
        }

        public Drawable newDrawable() {
            return new dq(this);
        }
    }

    public dq(Drawable drawable, Drawable drawable2) {
        this((b) null);
        drawable = drawable == null ? a.oQ : drawable;
        this.oK = drawable;
        drawable.setCallback(this);
        this.oJ.oT |= drawable.getChangingConfigurations();
        drawable2 = drawable2 == null ? a.oQ : drawable2;
        this.oL = drawable2;
        drawable2.setCallback(this);
        this.oJ.oT |= drawable2.getChangingConfigurations();
    }

    dq(b bVar) {
        this.oB = 0;
        this.oF = MotionEventCompat.ACTION_MASK;
        this.oH = 0;
        this.oy = true;
        this.oJ = new b(bVar);
    }

    public Drawable bC() {
        return this.oL;
    }

    public boolean canConstantState() {
        if (!this.oM) {
            this.oN = (this.oK.getConstantState() == null || this.oL.getConstantState() == null) ? false : true;
            this.oM = true;
        }
        return this.oN;
    }

    public void draw(Canvas canvas) {
        boolean z = true;
        boolean z2 = false;
        switch (this.oB) {
            case 1:
                this.oC = SystemClock.uptimeMillis();
                this.oB = 2;
                break;
            case 2:
                if (this.oC >= 0) {
                    float uptimeMillis = ((float) (SystemClock.uptimeMillis() - this.oC)) / ((float) this.oG);
                    if (uptimeMillis < 1.0f) {
                        z = false;
                    }
                    if (z) {
                        this.oB = 0;
                    }
                    this.oH = (int) ((Math.min(uptimeMillis, 1.0f) * ((float) (this.oE - this.oD))) + ((float) this.oD));
                    break;
                }
                break;
        }
        z2 = z;
        int i = this.oH;
        boolean z3 = this.oy;
        Drawable drawable = this.oK;
        Drawable drawable2 = this.oL;
        if (z2) {
            if (!z3 || i == 0) {
                drawable.draw(canvas);
            }
            if (i == this.oF) {
                drawable2.setAlpha(this.oF);
                drawable2.draw(canvas);
                return;
            }
            return;
        }
        if (z3) {
            drawable.setAlpha(this.oF - i);
        }
        drawable.draw(canvas);
        if (z3) {
            drawable.setAlpha(this.oF);
        }
        if (i > 0) {
            drawable2.setAlpha(i);
            drawable2.draw(canvas);
            drawable2.setAlpha(this.oF);
        }
        invalidateSelf();
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.oJ.oS | this.oJ.oT;
    }

    public Drawable.ConstantState getConstantState() {
        if (!canConstantState()) {
            return null;
        }
        this.oJ.oS = getChangingConfigurations();
        return this.oJ;
    }

    public int getIntrinsicHeight() {
        return Math.max(this.oK.getIntrinsicHeight(), this.oL.getIntrinsicHeight());
    }

    public int getIntrinsicWidth() {
        return Math.max(this.oK.getIntrinsicWidth(), this.oL.getIntrinsicWidth());
    }

    public int getOpacity() {
        if (!this.oO) {
            this.oP = Drawable.resolveOpacity(this.oK.getOpacity(), this.oL.getOpacity());
            this.oO = true;
        }
        return this.oP;
    }

    public void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback;
        if (fg.cD() && (callback = getCallback()) != null) {
            callback.invalidateDrawable(this);
        }
    }

    public Drawable mutate() {
        if (!this.oI && super.mutate() == this) {
            if (!canConstantState()) {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
            this.oK.mutate();
            this.oL.mutate();
            this.oI = true;
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        this.oK.setBounds(rect);
        this.oL.setBounds(rect);
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback;
        if (fg.cD() && (callback = getCallback()) != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    public void setAlpha(int i) {
        if (this.oH == this.oF) {
            this.oH = i;
        }
        this.oF = i;
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.oK.setColorFilter(colorFilter);
        this.oL.setColorFilter(colorFilter);
    }

    public void startTransition(int i) {
        this.oD = 0;
        this.oE = this.oF;
        this.oH = 0;
        this.oG = i;
        this.oB = 1;
        invalidateSelf();
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback;
        if (fg.cD() && (callback = getCallback()) != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }
}
