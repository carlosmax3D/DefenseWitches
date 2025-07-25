package com.flurry.sdk;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.Method;

public class fc {
    public static int a(int i) {
        return Math.round(((float) i) / c().density);
    }

    @SuppressLint({"NewApi"})
    public static Point a() {
        Display defaultDisplay = ((WindowManager) Cdo.a().b().getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealSize(point);
        } else if (Build.VERSION.SDK_INT >= 14) {
            try {
                Method method = Display.class.getMethod("getRawHeight", new Class[0]);
                point.x = ((Integer) Display.class.getMethod("getRawWidth", new Class[0]).invoke(defaultDisplay, new Object[0])).intValue();
                point.y = ((Integer) method.invoke(defaultDisplay, new Object[0])).intValue();
            } catch (Throwable th) {
                defaultDisplay.getSize(point);
            }
        } else if (Build.VERSION.SDK_INT >= 13) {
            defaultDisplay.getSize(point);
        } else {
            point.x = defaultDisplay.getWidth();
            point.y = defaultDisplay.getHeight();
        }
        return point;
    }

    public static int b(int i) {
        return Math.round(c().density * ((float) i));
    }

    public static DisplayMetrics b() {
        Display defaultDisplay = ((WindowManager) Cdo.a().b().getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics;
    }

    @SuppressLint({"NewApi"})
    public static DisplayMetrics c() {
        Display defaultDisplay = ((WindowManager) Cdo.a().b().getSystemService("window")).getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 17) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getRealMetrics(displayMetrics);
            return displayMetrics;
        } else if (Build.VERSION.SDK_INT < 14) {
            return b();
        } else {
            try {
                DisplayMetrics displayMetrics2 = new DisplayMetrics();
                Display.class.getMethod("getRealMetrics", new Class[0]).invoke(defaultDisplay, new Object[]{displayMetrics2});
                return displayMetrics2;
            } catch (Exception e) {
                return b();
            }
        }
    }

    public static Pair<Integer, Integer> c(int i) {
        int g = g();
        int h = h();
        switch (i) {
            case 2:
                return Pair.create(Integer.valueOf(h), Integer.valueOf(g));
            default:
                return Pair.create(Integer.valueOf(g), Integer.valueOf(h));
        }
    }

    public static float d() {
        return c().density;
    }

    public static int e() {
        return a().x;
    }

    public static int f() {
        return a().y;
    }

    public static int g() {
        return a(e());
    }

    public static int h() {
        return a(f());
    }

    public static int i() {
        Point a = a();
        if (a.x == a.y) {
            return 3;
        }
        return a.x < a.y ? 1 : 2;
    }

    public static Pair<Integer, Integer> j() {
        return Pair.create(Integer.valueOf(g()), Integer.valueOf(h()));
    }
}
