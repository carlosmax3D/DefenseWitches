package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

public abstract class RoundedBitmapDrawable extends Drawable {
    private static final int DEFAULT_PAINT_FLAGS = 6;
    private boolean mApplyGravity = true;
    Bitmap mBitmap;
    private int mBitmapHeight;
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private float mCornerRadius;
    final Rect mDstRect = new Rect();
    final RectF mDstRectF = new RectF();
    private int mGravity = 119;
    private Paint mPaint = new Paint(6);
    private int mTargetDensity = 160;

    RoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
        if (resources != null) {
            this.mTargetDensity = resources.getDisplayMetrics().densityDpi;
        }
        this.mBitmap = bitmap;
        if (this.mBitmap != null) {
            computeBitmapSize();
            this.mBitmapShader = new BitmapShader(this.mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            return;
        }
        this.mBitmapHeight = -1;
        this.mBitmapWidth = -1;
    }

    private void computeBitmapSize() {
        this.mBitmapWidth = this.mBitmap.getScaledWidth(this.mTargetDensity);
        this.mBitmapHeight = this.mBitmap.getScaledHeight(this.mTargetDensity);
    }

    private static boolean isGreaterThanZero(float f) {
        return Float.compare(f, 0.0f) > 0;
    }

    public void draw(Canvas canvas) {
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            updateDstRect();
            Paint paint = this.mPaint;
            if (paint.getShader() == null) {
                canvas.drawBitmap(bitmap, (Rect) null, this.mDstRect, paint);
            } else {
                canvas.drawRoundRect(this.mDstRectF, this.mCornerRadius, this.mCornerRadius, paint);
            }
        }
    }

    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    public final Bitmap getBitmap() {
        return this.mBitmap;
    }

    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0008, code lost:
        r0 = r4.mBitmap;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getOpacity() {
        /*
            r4 = this;
            r1 = -3
            int r2 = r4.mGravity
            r3 = 119(0x77, float:1.67E-43)
            if (r2 == r3) goto L_0x0008
        L_0x0007:
            return r1
        L_0x0008:
            android.graphics.Bitmap r0 = r4.mBitmap
            if (r0 == 0) goto L_0x0007
            boolean r2 = r0.hasAlpha()
            if (r2 != 0) goto L_0x0007
            android.graphics.Paint r2 = r4.mPaint
            int r2 = r2.getAlpha()
            r3 = 255(0xff, float:3.57E-43)
            if (r2 < r3) goto L_0x0007
            float r2 = r4.mCornerRadius
            boolean r2 = isGreaterThanZero(r2)
            if (r2 != 0) goto L_0x0007
            r1 = -1
            goto L_0x0007
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.graphics.drawable.RoundedBitmapDrawable.getOpacity():int");
    }

    public final Paint getPaint() {
        return this.mPaint;
    }

    /* access modifiers changed from: package-private */
    public void gravityCompatApply(int i, int i2, int i3, Rect rect, Rect rect2) {
        throw new UnsupportedOperationException();
    }

    public boolean hasAntiAlias() {
        return this.mPaint.isAntiAlias();
    }

    public boolean hasMipMap() {
        throw new UnsupportedOperationException();
    }

    public void setAlpha(int i) {
        if (i != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(i);
            invalidateSelf();
        }
    }

    public void setAntiAlias(boolean z) {
        this.mPaint.setAntiAlias(z);
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setCornerRadius(float f) {
        if (isGreaterThanZero(f)) {
            this.mPaint.setShader(this.mBitmapShader);
        } else {
            this.mPaint.setShader((Shader) null);
        }
        this.mCornerRadius = f;
    }

    public void setDither(boolean z) {
        this.mPaint.setDither(z);
        invalidateSelf();
    }

    public void setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
        invalidateSelf();
    }

    public void setGravity(int i) {
        if (this.mGravity != i) {
            this.mGravity = i;
            this.mApplyGravity = true;
            invalidateSelf();
        }
    }

    public void setMipMap(boolean z) {
        throw new UnsupportedOperationException();
    }

    public void setTargetDensity(int i) {
        if (this.mTargetDensity != i) {
            if (i == 0) {
                i = 160;
            }
            this.mTargetDensity = i;
            if (this.mBitmap != null) {
                computeBitmapSize();
            }
            invalidateSelf();
        }
    }

    public void setTargetDensity(Canvas canvas) {
        setTargetDensity(canvas.getDensity());
    }

    public void setTargetDensity(DisplayMetrics displayMetrics) {
        setTargetDensity(displayMetrics.densityDpi);
    }

    /* access modifiers changed from: package-private */
    public void updateDstRect() {
        if (this.mApplyGravity) {
            gravityCompatApply(this.mGravity, this.mBitmapWidth, this.mBitmapHeight, getBounds(), this.mDstRect);
            this.mDstRectF.set(this.mDstRect);
            this.mApplyGravity = false;
        }
    }
}
