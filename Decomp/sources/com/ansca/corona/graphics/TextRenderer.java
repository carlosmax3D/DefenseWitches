package com.ansca.corona.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.text.StaticLayout;
import android.text.TextPaint;
import java.util.HashMap;
import jp.stargarage.g2metrics.BuildConfig;

public class TextRenderer {
    private static HashMap<Typeface, TextPaint> sTextPaintCollection = new HashMap<>();
    private int fClipHeight;
    private int fClipWidth;
    private Context fContext;
    private FontSettings fFontSettings;
    private HorizontalAlignment fHorizontalAlignment;
    private String fText;
    private int fWrapWidth;

    public TextRenderer(Context context) {
        if (context == null) {
            throw new NullPointerException();
        }
        this.fContext = context;
        this.fFontSettings = new FontSettings();
        this.fHorizontalAlignment = HorizontalAlignment.LEFT;
        this.fWrapWidth = 0;
        this.fClipWidth = 0;
        this.fClipHeight = 0;
        this.fText = BuildConfig.FLAVOR;
    }

    public Bitmap createBitmap() {
        FontServices fontServices;
        Typeface fetchTypefaceFor;
        TextPaint textPaint;
        if (this.fText == null || this.fText.length() <= 0 || this.fFontSettings.getPointSize() <= 0.0f || (fontServices = new FontServices(this.fContext)) == null || (fetchTypefaceFor = fontServices.fetchTypefaceFor((TypefaceSettings) this.fFontSettings)) == null) {
            return null;
        }
        synchronized (sTextPaintCollection) {
            textPaint = sTextPaintCollection.get(fetchTypefaceFor);
        }
        if (textPaint == null) {
            textPaint = new TextPaint();
            textPaint.setARGB(MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK);
            textPaint.setAntiAlias(true);
            textPaint.setTextAlign(Paint.Align.LEFT);
            textPaint.setTypeface(fetchTypefaceFor);
            synchronized (sTextPaintCollection) {
                sTextPaintCollection.put(fetchTypefaceFor, textPaint);
            }
        }
        textPaint.setTextSize(this.fFontSettings.getPointSize());
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int i = this.fWrapWidth;
        if (i <= 0 && (i = (int) (StaticLayout.getDesiredWidth(this.fText, textPaint) + 1.0f)) < 1) {
            i = 1;
        }
        StaticLayout staticLayout = new StaticLayout(this.fText, textPaint, i, this.fHorizontalAlignment.toAndroidTextLayoutAlignment(), 1.0f, 1.0f, true);
        int i2 = this.fClipHeight;
        if (i2 <= 0 && (i2 = (Math.abs(fontMetricsInt.top) + Math.abs(fontMetricsInt.bottom) + 1) * staticLayout.getLineCount()) < 1) {
            i2 = 1;
        }
        if (this.fClipWidth > 0 && i > this.fClipWidth) {
            i = this.fClipWidth;
        }
        if (this.fClipHeight > 0 && i2 > this.fClipHeight) {
            i2 = this.fClipHeight;
        }
        int i3 = i % 4;
        if (i3 > 0 && (i = i + (4 - i3)) > this.fClipWidth) {
            i -= 4;
        }
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ALPHA_8);
            staticLayout.draw(new Canvas(bitmap));
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public int getClipHeight() {
        return this.fClipHeight;
    }

    public int getClipWidth() {
        return this.fClipWidth;
    }

    public Context getContext() {
        return this.fContext;
    }

    public FontSettings getFontSettings() {
        return this.fFontSettings;
    }

    public HorizontalAlignment getHorizontalAlignment() {
        return this.fHorizontalAlignment;
    }

    public String getText() {
        return this.fText;
    }

    public int getWrapWidth() {
        return this.fWrapWidth;
    }

    public void setClipHeight(int i) {
        if (i < 0) {
            i = 0;
        }
        this.fClipHeight = i;
    }

    public void setClipWidth(int i) {
        if (i < 0) {
            i = 0;
        }
        this.fClipWidth = i;
    }

    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        if (horizontalAlignment == null) {
            throw new NullPointerException();
        }
        this.fHorizontalAlignment = horizontalAlignment;
    }

    public void setText(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fText = str;
    }

    public void setWrapWidth(int i) {
        if (i < 0) {
            i = 0;
        }
        this.fWrapWidth = i;
    }
}
