package com.ansca.corona.graphics;

import android.graphics.Paint;
import android.text.Layout;
import java.lang.reflect.Field;

public class HorizontalAlignment {
    public static final HorizontalAlignment CENTER = new HorizontalAlignment("center", 1, Layout.Alignment.ALIGN_CENTER, Paint.Align.CENTER);
    public static final HorizontalAlignment LEFT = new HorizontalAlignment("left", 3, Layout.Alignment.ALIGN_NORMAL, Paint.Align.LEFT);
    public static final HorizontalAlignment RIGHT = new HorizontalAlignment("right", 5, Layout.Alignment.ALIGN_OPPOSITE, Paint.Align.RIGHT);
    private int fAndroidGravityBitField;
    private Paint.Align fAndroidPaintAlignment;
    private Layout.Alignment fAndroidTextLayoutAlignment;
    private String fCoronaStringId;

    private HorizontalAlignment(String str, int i, Layout.Alignment alignment, Paint.Align align) {
        this.fCoronaStringId = str;
        this.fAndroidGravityBitField = i;
        this.fAndroidTextLayoutAlignment = alignment;
        this.fAndroidPaintAlignment = align;
    }

    public static HorizontalAlignment fromCoronaStringId(String str) {
        try {
            for (Field field : HorizontalAlignment.class.getDeclaredFields()) {
                if (field.getType().equals(HorizontalAlignment.class)) {
                    HorizontalAlignment horizontalAlignment = (HorizontalAlignment) field.get((Object) null);
                    if (horizontalAlignment.fCoronaStringId.equals(str)) {
                        return horizontalAlignment;
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public int toAndroidGravityBitField() {
        return this.fAndroidGravityBitField;
    }

    public Paint.Align toAndroidPaintAlignment() {
        return this.fAndroidPaintAlignment;
    }

    public Layout.Alignment toAndroidTextLayoutAlignment() {
        return this.fAndroidTextLayoutAlignment;
    }

    public String toCoronaStringId() {
        return this.fCoronaStringId;
    }

    public String toString() {
        return this.fCoronaStringId;
    }
}
