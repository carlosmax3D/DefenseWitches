package com.google.android.gms.dynamic;

import android.os.IBinder;
import com.google.android.gms.dynamic.b;
import java.lang.reflect.Field;

public final class c<T> extends b.a {
    private final T sE;

    private c(T t) {
        this.sE = t;
    }

    public static <T> T b(b bVar) {
        if (bVar instanceof c) {
            return ((c) bVar).sE;
        }
        IBinder asBinder = bVar.asBinder();
        Field[] declaredFields = asBinder.getClass().getDeclaredFields();
        if (declaredFields.length == 1) {
            Field field = declaredFields[0];
            if (!field.isAccessible()) {
                field.setAccessible(true);
                try {
                    return field.get(asBinder);
                } catch (NullPointerException e) {
                    throw new IllegalArgumentException("Binder object is null.", e);
                } catch (IllegalArgumentException e2) {
                    throw new IllegalArgumentException("remoteBinder is the wrong class.", e2);
                } catch (IllegalAccessException e3) {
                    throw new IllegalArgumentException("Could not access the field in remoteBinder.", e3);
                }
            } else {
                throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly one declared *private* field for the wrapped object. Preferably, this is an instance of the ObjectWrapper<T> class.");
            }
        } else {
            throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly *one* declared private field for the wrapped object.  Preferably, this is an instance of the ObjectWrapper<T> class.");
        }
    }

    public static <T> b h(T t) {
        return new c(t);
    }
}
