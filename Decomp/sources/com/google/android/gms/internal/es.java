package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class es {

    public static class a<I, O> implements SafeParcelable {
        public static final et CREATOR = new et();
        private final int kg;
        protected final int qi;
        protected final boolean qj;
        protected final int qk;
        protected final boolean ql;
        protected final String qm;
        protected final int qn;
        protected final Class<? extends es> qo;
        protected final String qp;
        private ev qq;
        /* access modifiers changed from: private */
        public b<I, O> qr;

        a(int i, int i2, boolean z, int i3, boolean z2, String str, int i4, String str2, en enVar) {
            this.kg = i;
            this.qi = i2;
            this.qj = z;
            this.qk = i3;
            this.ql = z2;
            this.qm = str;
            this.qn = i4;
            if (str2 == null) {
                this.qo = null;
                this.qp = null;
            } else {
                this.qo = ey.class;
                this.qp = str2;
            }
            if (enVar == null) {
                this.qr = null;
            } else {
                this.qr = enVar.cf();
            }
        }

        protected a(int i, boolean z, int i2, boolean z2, String str, int i3, Class<? extends es> cls, b<I, O> bVar) {
            this.kg = 1;
            this.qi = i;
            this.qj = z;
            this.qk = i2;
            this.ql = z2;
            this.qm = str;
            this.qn = i3;
            this.qo = cls;
            if (cls == null) {
                this.qp = null;
            } else {
                this.qp = cls.getCanonicalName();
            }
            this.qr = bVar;
        }

        public static a a(String str, int i, b<?, ?> bVar, boolean z) {
            return new a(bVar.ch(), z, bVar.ci(), false, str, i, (Class<? extends es>) null, bVar);
        }

        public static <T extends es> a<T, T> a(String str, int i, Class<T> cls) {
            return new a<>(11, false, 11, false, str, i, cls, (b) null);
        }

        public static <T extends es> a<ArrayList<T>, ArrayList<T>> b(String str, int i, Class<T> cls) {
            return new a<>(11, true, 11, true, str, i, cls, (b) null);
        }

        public static a<Integer, Integer> d(String str, int i) {
            return new a<>(0, false, 0, false, str, i, (Class<? extends es>) null, (b) null);
        }

        public static a<Double, Double> e(String str, int i) {
            return new a<>(4, false, 4, false, str, i, (Class<? extends es>) null, (b) null);
        }

        public static a<Boolean, Boolean> f(String str, int i) {
            return new a<>(6, false, 6, false, str, i, (Class<? extends es>) null, (b) null);
        }

        public static a<String, String> g(String str, int i) {
            return new a<>(7, false, 7, false, str, i, (Class<? extends es>) null, (b) null);
        }

        public static a<ArrayList<String>, ArrayList<String>> h(String str, int i) {
            return new a<>(7, true, 7, true, str, i, (Class<? extends es>) null, (b) null);
        }

        public void a(ev evVar) {
            this.qq = evVar;
        }

        public int ch() {
            return this.qi;
        }

        public int ci() {
            return this.qk;
        }

        public a<I, O> cm() {
            return new a<>(this.kg, this.qi, this.qj, this.qk, this.ql, this.qm, this.qn, this.qp, cu());
        }

        public boolean cn() {
            return this.qj;
        }

        public boolean co() {
            return this.ql;
        }

        public String cp() {
            return this.qm;
        }

        public int cq() {
            return this.qn;
        }

        public Class<? extends es> cr() {
            return this.qo;
        }

        /* access modifiers changed from: package-private */
        public String cs() {
            if (this.qp == null) {
                return null;
            }
            return this.qp;
        }

        public boolean ct() {
            return this.qr != null;
        }

        /* access modifiers changed from: package-private */
        public en cu() {
            if (this.qr == null) {
                return null;
            }
            return en.a(this.qr);
        }

        public HashMap<String, a<?, ?>> cv() {
            eg.f(this.qp);
            eg.f(this.qq);
            return this.qq.Z(this.qp);
        }

        public int describeContents() {
            et etVar = CREATOR;
            return 0;
        }

        public I g(O o) {
            return this.qr.g(o);
        }

        public int getVersionCode() {
            return this.kg;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Field\n");
            sb.append("            versionCode=").append(this.kg).append(10);
            sb.append("                 typeIn=").append(this.qi).append(10);
            sb.append("            typeInArray=").append(this.qj).append(10);
            sb.append("                typeOut=").append(this.qk).append(10);
            sb.append("           typeOutArray=").append(this.ql).append(10);
            sb.append("        outputFieldName=").append(this.qm).append(10);
            sb.append("      safeParcelFieldId=").append(this.qn).append(10);
            sb.append("       concreteTypeName=").append(cs()).append(10);
            if (cr() != null) {
                sb.append("     concreteType.class=").append(cr().getCanonicalName()).append(10);
            }
            sb.append("          converterName=").append(this.qr == null ? "null" : this.qr.getClass().getCanonicalName()).append(10);
            return sb.toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            et etVar = CREATOR;
            et.a(this, parcel, i);
        }
    }

    public interface b<I, O> {
        int ch();

        int ci();

        I g(O o);
    }

    private void a(StringBuilder sb, a aVar, Object obj) {
        if (aVar.ch() == 11) {
            sb.append(((es) aVar.cr().cast(obj)).toString());
        } else if (aVar.ch() == 7) {
            sb.append("\"");
            sb.append(fe.aa((String) obj));
            sb.append("\"");
        } else {
            sb.append(obj);
        }
    }

    private void a(StringBuilder sb, a aVar, ArrayList<Object> arrayList) {
        sb.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(",");
            }
            Object obj = arrayList.get(i);
            if (obj != null) {
                a(sb, aVar, obj);
            }
        }
        sb.append("]");
    }

    /* access modifiers changed from: protected */
    public abstract Object V(String str);

    /* access modifiers changed from: protected */
    public abstract boolean W(String str);

    /* access modifiers changed from: protected */
    public boolean X(String str) {
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    /* access modifiers changed from: protected */
    public boolean Y(String str) {
        throw new UnsupportedOperationException("Concrete type arrays not supported");
    }

    /* access modifiers changed from: protected */
    public <O, I> I a(a<I, O> aVar, Object obj) {
        return aVar.qr != null ? aVar.g(obj) : obj;
    }

    /* access modifiers changed from: protected */
    public boolean a(a aVar) {
        return aVar.ci() == 11 ? aVar.co() ? Y(aVar.cp()) : X(aVar.cp()) : W(aVar.cp());
    }

    /* access modifiers changed from: protected */
    public Object b(a aVar) {
        boolean z = true;
        String cp = aVar.cp();
        if (aVar.cr() == null) {
            return V(aVar.cp());
        }
        if (V(aVar.cp()) != null) {
            z = false;
        }
        eg.a(z, "Concrete field shouldn't be value object: " + aVar.cp());
        HashMap<String, Object> cl = aVar.co() ? cl() : ck();
        if (cl != null) {
            return cl.get(cp);
        }
        try {
            return getClass().getMethod("get" + Character.toUpperCase(cp.charAt(0)) + cp.substring(1), new Class[0]).invoke(this, new Object[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract HashMap<String, a<?, ?>> cj();

    public HashMap<String, Object> ck() {
        return null;
    }

    public HashMap<String, Object> cl() {
        return null;
    }

    public String toString() {
        HashMap<String, a<?, ?>> cj = cj();
        StringBuilder sb = new StringBuilder(100);
        for (String next : cj.keySet()) {
            a aVar = cj.get(next);
            if (a(aVar)) {
                Object a2 = a(aVar, b(aVar));
                if (sb.length() == 0) {
                    sb.append("{");
                } else {
                    sb.append(",");
                }
                sb.append("\"").append(next).append("\":");
                if (a2 != null) {
                    switch (aVar.ci()) {
                        case 8:
                            sb.append("\"").append(fb.b((byte[]) a2)).append("\"");
                            break;
                        case 9:
                            sb.append("\"").append(fb.c((byte[]) a2)).append("\"");
                            break;
                        case 10:
                            ff.a(sb, (HashMap) a2);
                            break;
                        default:
                            if (!aVar.cn()) {
                                a(sb, aVar, a2);
                                break;
                            } else {
                                a(sb, aVar, (ArrayList<Object>) (ArrayList) a2);
                                break;
                            }
                    }
                } else {
                    sb.append("null");
                }
            }
        }
        if (sb.length() > 0) {
            sb.append("}");
        } else {
            sb.append("{}");
        }
        return sb.toString();
    }
}
