package com.threatmetrix.TrustDefenderMobile;

import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class WrapperHelper {
    private static final String TAG = WrapperHelper.class.getName();

    WrapperHelper() {
    }

    static Class getClass(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            String str2 = TAG;
            return null;
        } catch (Exception e2) {
            Log.e(TAG, str + " getClass failed: ", e2);
            return null;
        }
    }

    static Field getField(Class cls, String str) {
        if (cls == null) {
            return null;
        }
        try {
            return cls.getDeclaredField(str);
        } catch (Exception | NoSuchFieldException e) {
            return null;
        }
    }

    static Method getMethod(Class cls, String str, Class... clsArr) {
        if (cls == null) {
            return null;
        }
        try {
            return cls.getMethod(str, clsArr);
        } catch (Exception | NoSuchMethodException e) {
            return null;
        }
    }

    static Object getValue(Object obj, Field field) {
        if (obj == null || field == null) {
            return null;
        }
        try {
            return field.get(obj);
        } catch (Exception | IllegalAccessException | IllegalArgumentException e) {
            return null;
        }
    }

    static <T> T invoke(Object obj, Method method, Object... objArr) {
        T t = null;
        if (method != null) {
            boolean z = false;
            try {
                t = method.invoke(obj, objArr);
            } catch (ClassCastException e) {
                Log.e(TAG, method.getName() + " invoke failed: ", e);
                z = true;
            } catch (IllegalAccessException e2) {
                Log.e(TAG, method.getName() + " invoke failed: ", e2);
                z = true;
            } catch (IllegalArgumentException e3) {
                Log.e(TAG, method.getName() + " invoke failed: ", e3);
                z = true;
            } catch (InvocationTargetException e4) {
                Log.e(TAG, method.getName() + " invoke failed: ", e4);
                z = true;
            } catch (Exception e5) {
                Log.e(TAG, method.getName() + " invoke failed: ", e5);
                z = true;
            }
            if (!z) {
                return t;
            }
        }
        return null;
    }
}
