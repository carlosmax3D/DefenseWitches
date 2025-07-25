package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ee {

    public static final class a {
        private final List<String> pN;
        private final Object pO;

        private a(Object obj) {
            this.pO = eg.f(obj);
            this.pN = new ArrayList();
        }

        public a a(String str, Object obj) {
            this.pN.add(((String) eg.f(str)) + "=" + String.valueOf(obj));
            return this;
        }

        public String toString() {
            StringBuilder append = new StringBuilder(100).append(this.pO.getClass().getSimpleName()).append('{');
            int size = this.pN.size();
            for (int i = 0; i < size; i++) {
                append.append(this.pN.get(i));
                if (i < size - 1) {
                    append.append(", ");
                }
            }
            return append.append('}').toString();
        }
    }

    public static a e(Object obj) {
        return new a(obj);
    }

    public static boolean equal(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static int hashCode(Object... objArr) {
        return Arrays.hashCode(objArr);
    }
}
