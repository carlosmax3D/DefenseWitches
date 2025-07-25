package com.google.android.gms.internal;

import CoronaProvider.ads.admob.AdMobAd;
import android.os.Parcel;
import android.support.v4.util.TimeUtils;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.es;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jp.co.voyagegroup.android.fluct.jar.util.FluctConstants;

public final class ig extends es implements SafeParcelable, Person {
    public static final ih CREATOR = new ih();
    private static final HashMap<String, es.a<?, ?>> Ep = new HashMap<>();
    private final Set<Integer> Eq;
    private String FA;
    private int FB;
    private List<f> FC;
    private List<g> FD;
    private int FE;
    private int FF;
    private String FG;
    private List<h> FH;
    private boolean FI;
    private String Fp;
    private a Fq;
    private String Fr;
    private String Fs;
    private int Ft;
    private b Fu;
    private String Fv;
    private c Fw;
    private boolean Fx;
    private String Fy;
    private d Fz;
    private int eL;
    private String iH;
    private final int kg;
    private String qa;
    private String uS;

    public static final class a extends es implements SafeParcelable, Person.AgeRange {
        public static final ii CREATOR = new ii();
        private static final HashMap<String, es.a<?, ?>> Ep = new HashMap<>();
        private final Set<Integer> Eq;
        private int FJ;
        private int FK;
        private final int kg;

        static {
            Ep.put("max", es.a.d("max", 2));
            Ep.put("min", es.a.d("min", 3));
        }

        public a() {
            this.kg = 1;
            this.Eq = new HashSet();
        }

        a(Set<Integer> set, int i, int i2, int i3) {
            this.Eq = set;
            this.kg = i;
            this.FJ = i2;
            this.FK = i3;
        }

        /* access modifiers changed from: protected */
        public Object V(String str) {
            return null;
        }

        /* access modifiers changed from: protected */
        public boolean W(String str) {
            return false;
        }

        /* access modifiers changed from: protected */
        public boolean a(es.a aVar) {
            return this.Eq.contains(Integer.valueOf(aVar.cq()));
        }

        /* access modifiers changed from: protected */
        public Object b(es.a aVar) {
            switch (aVar.cq()) {
                case 2:
                    return Integer.valueOf(this.FJ);
                case 3:
                    return Integer.valueOf(this.FK);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + aVar.cq());
            }
        }

        public HashMap<String, es.a<?, ?>> cj() {
            return Ep;
        }

        public int describeContents() {
            ii iiVar = CREATOR;
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof a)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            a aVar = (a) obj;
            for (es.a next : Ep.values()) {
                if (a(next)) {
                    if (!aVar.a(next)) {
                        return false;
                    }
                    if (!b(next).equals(aVar.b(next))) {
                        return false;
                    }
                } else if (aVar.a(next)) {
                    return false;
                }
            }
            return true;
        }

        /* renamed from: fD */
        public a freeze() {
            return this;
        }

        /* access modifiers changed from: package-private */
        public Set<Integer> fa() {
            return this.Eq;
        }

        public int getMax() {
            return this.FJ;
        }

        public int getMin() {
            return this.FK;
        }

        /* access modifiers changed from: package-private */
        public int getVersionCode() {
            return this.kg;
        }

        public boolean hasMax() {
            return this.Eq.contains(2);
        }

        public boolean hasMin() {
            return this.Eq.contains(3);
        }

        public int hashCode() {
            int i = 0;
            Iterator<es.a<?, ?>> it = Ep.values().iterator();
            while (true) {
                int i2 = i;
                if (!it.hasNext()) {
                    return i2;
                }
                es.a next = it.next();
                if (a(next)) {
                    i = b(next).hashCode() + i2 + next.cq();
                } else {
                    i = i2;
                }
            }
        }

        public boolean isDataValid() {
            return true;
        }

        public void writeToParcel(Parcel parcel, int i) {
            ii iiVar = CREATOR;
            ii.a(this, parcel, i);
        }
    }

    public static final class b extends es implements SafeParcelable, Person.Cover {
        public static final ij CREATOR = new ij();
        private static final HashMap<String, es.a<?, ?>> Ep = new HashMap<>();
        private final Set<Integer> Eq;
        private a FL;
        private C0041b FM;
        private int FN;
        private final int kg;

        public static final class a extends es implements SafeParcelable, Person.Cover.CoverInfo {
            public static final ik CREATOR = new ik();
            private static final HashMap<String, es.a<?, ?>> Ep = new HashMap<>();
            private final Set<Integer> Eq;
            private int FO;
            private int FP;
            private final int kg;

            static {
                Ep.put("leftImageOffset", es.a.d("leftImageOffset", 2));
                Ep.put("topImageOffset", es.a.d("topImageOffset", 3));
            }

            public a() {
                this.kg = 1;
                this.Eq = new HashSet();
            }

            a(Set<Integer> set, int i, int i2, int i3) {
                this.Eq = set;
                this.kg = i;
                this.FO = i2;
                this.FP = i3;
            }

            /* access modifiers changed from: protected */
            public Object V(String str) {
                return null;
            }

            /* access modifiers changed from: protected */
            public boolean W(String str) {
                return false;
            }

            /* access modifiers changed from: protected */
            public boolean a(es.a aVar) {
                return this.Eq.contains(Integer.valueOf(aVar.cq()));
            }

            /* access modifiers changed from: protected */
            public Object b(es.a aVar) {
                switch (aVar.cq()) {
                    case 2:
                        return Integer.valueOf(this.FO);
                    case 3:
                        return Integer.valueOf(this.FP);
                    default:
                        throw new IllegalStateException("Unknown safe parcelable id=" + aVar.cq());
                }
            }

            public HashMap<String, es.a<?, ?>> cj() {
                return Ep;
            }

            public int describeContents() {
                ik ikVar = CREATOR;
                return 0;
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof a)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                a aVar = (a) obj;
                for (es.a next : Ep.values()) {
                    if (a(next)) {
                        if (!aVar.a(next)) {
                            return false;
                        }
                        if (!b(next).equals(aVar.b(next))) {
                            return false;
                        }
                    } else if (aVar.a(next)) {
                        return false;
                    }
                }
                return true;
            }

            /* renamed from: fH */
            public a freeze() {
                return this;
            }

            /* access modifiers changed from: package-private */
            public Set<Integer> fa() {
                return this.Eq;
            }

            public int getLeftImageOffset() {
                return this.FO;
            }

            public int getTopImageOffset() {
                return this.FP;
            }

            /* access modifiers changed from: package-private */
            public int getVersionCode() {
                return this.kg;
            }

            public boolean hasLeftImageOffset() {
                return this.Eq.contains(2);
            }

            public boolean hasTopImageOffset() {
                return this.Eq.contains(3);
            }

            public int hashCode() {
                int i = 0;
                Iterator<es.a<?, ?>> it = Ep.values().iterator();
                while (true) {
                    int i2 = i;
                    if (!it.hasNext()) {
                        return i2;
                    }
                    es.a next = it.next();
                    if (a(next)) {
                        i = b(next).hashCode() + i2 + next.cq();
                    } else {
                        i = i2;
                    }
                }
            }

            public boolean isDataValid() {
                return true;
            }

            public void writeToParcel(Parcel parcel, int i) {
                ik ikVar = CREATOR;
                ik.a(this, parcel, i);
            }
        }

        /* renamed from: com.google.android.gms.internal.ig$b$b  reason: collision with other inner class name */
        public static final class C0041b extends es implements SafeParcelable, Person.Cover.CoverPhoto {
            public static final il CREATOR = new il();
            private static final HashMap<String, es.a<?, ?>> Ep = new HashMap<>();
            private final Set<Integer> Eq;
            private String iH;
            private final int kg;
            private int v;
            private int w;

            static {
                Ep.put(FluctConstants.XML_NODE_HEIGHT, es.a.d(FluctConstants.XML_NODE_HEIGHT, 2));
                Ep.put("url", es.a.g("url", 3));
                Ep.put(FluctConstants.XML_NODE_WIDTH, es.a.d(FluctConstants.XML_NODE_WIDTH, 4));
            }

            public C0041b() {
                this.kg = 1;
                this.Eq = new HashSet();
            }

            C0041b(Set<Integer> set, int i, int i2, String str, int i3) {
                this.Eq = set;
                this.kg = i;
                this.v = i2;
                this.iH = str;
                this.w = i3;
            }

            /* access modifiers changed from: protected */
            public Object V(String str) {
                return null;
            }

            /* access modifiers changed from: protected */
            public boolean W(String str) {
                return false;
            }

            /* access modifiers changed from: protected */
            public boolean a(es.a aVar) {
                return this.Eq.contains(Integer.valueOf(aVar.cq()));
            }

            /* access modifiers changed from: protected */
            public Object b(es.a aVar) {
                switch (aVar.cq()) {
                    case 2:
                        return Integer.valueOf(this.v);
                    case 3:
                        return this.iH;
                    case 4:
                        return Integer.valueOf(this.w);
                    default:
                        throw new IllegalStateException("Unknown safe parcelable id=" + aVar.cq());
                }
            }

            public HashMap<String, es.a<?, ?>> cj() {
                return Ep;
            }

            public int describeContents() {
                il ilVar = CREATOR;
                return 0;
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof C0041b)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                C0041b bVar = (C0041b) obj;
                for (es.a next : Ep.values()) {
                    if (a(next)) {
                        if (!bVar.a(next)) {
                            return false;
                        }
                        if (!b(next).equals(bVar.b(next))) {
                            return false;
                        }
                    } else if (bVar.a(next)) {
                        return false;
                    }
                }
                return true;
            }

            /* renamed from: fI */
            public C0041b freeze() {
                return this;
            }

            /* access modifiers changed from: package-private */
            public Set<Integer> fa() {
                return this.Eq;
            }

            public int getHeight() {
                return this.v;
            }

            public String getUrl() {
                return this.iH;
            }

            /* access modifiers changed from: package-private */
            public int getVersionCode() {
                return this.kg;
            }

            public int getWidth() {
                return this.w;
            }

            public boolean hasHeight() {
                return this.Eq.contains(2);
            }

            public boolean hasUrl() {
                return this.Eq.contains(3);
            }

            public boolean hasWidth() {
                return this.Eq.contains(4);
            }

            public int hashCode() {
                int i = 0;
                Iterator<es.a<?, ?>> it = Ep.values().iterator();
                while (true) {
                    int i2 = i;
                    if (!it.hasNext()) {
                        return i2;
                    }
                    es.a next = it.next();
                    if (a(next)) {
                        i = b(next).hashCode() + i2 + next.cq();
                    } else {
                        i = i2;
                    }
                }
            }

            public boolean isDataValid() {
                return true;
            }

            public void writeToParcel(Parcel parcel, int i) {
                il ilVar = CREATOR;
                il.a(this, parcel, i);
            }
        }

        static {
            Ep.put("coverInfo", es.a.a("coverInfo", 2, a.class));
            Ep.put("coverPhoto", es.a.a("coverPhoto", 3, C0041b.class));
            Ep.put("layout", es.a.a("layout", 4, new ep().c(AdMobAd.BANNER, 0), false));
        }

        public b() {
            this.kg = 1;
            this.Eq = new HashSet();
        }

        b(Set<Integer> set, int i, a aVar, C0041b bVar, int i2) {
            this.Eq = set;
            this.kg = i;
            this.FL = aVar;
            this.FM = bVar;
            this.FN = i2;
        }

        /* access modifiers changed from: protected */
        public Object V(String str) {
            return null;
        }

        /* access modifiers changed from: protected */
        public boolean W(String str) {
            return false;
        }

        /* access modifiers changed from: protected */
        public boolean a(es.a aVar) {
            return this.Eq.contains(Integer.valueOf(aVar.cq()));
        }

        /* access modifiers changed from: protected */
        public Object b(es.a aVar) {
            switch (aVar.cq()) {
                case 2:
                    return this.FL;
                case 3:
                    return this.FM;
                case 4:
                    return Integer.valueOf(this.FN);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + aVar.cq());
            }
        }

        public HashMap<String, es.a<?, ?>> cj() {
            return Ep;
        }

        public int describeContents() {
            ij ijVar = CREATOR;
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof b)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            b bVar = (b) obj;
            for (es.a next : Ep.values()) {
                if (a(next)) {
                    if (!bVar.a(next)) {
                        return false;
                    }
                    if (!b(next).equals(bVar.b(next))) {
                        return false;
                    }
                } else if (bVar.a(next)) {
                    return false;
                }
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public a fE() {
            return this.FL;
        }

        /* access modifiers changed from: package-private */
        public C0041b fF() {
            return this.FM;
        }

        /* renamed from: fG */
        public b freeze() {
            return this;
        }

        /* access modifiers changed from: package-private */
        public Set<Integer> fa() {
            return this.Eq;
        }

        public Person.Cover.CoverInfo getCoverInfo() {
            return this.FL;
        }

        public Person.Cover.CoverPhoto getCoverPhoto() {
            return this.FM;
        }

        public int getLayout() {
            return this.FN;
        }

        /* access modifiers changed from: package-private */
        public int getVersionCode() {
            return this.kg;
        }

        public boolean hasCoverInfo() {
            return this.Eq.contains(2);
        }

        public boolean hasCoverPhoto() {
            return this.Eq.contains(3);
        }

        public boolean hasLayout() {
            return this.Eq.contains(4);
        }

        public int hashCode() {
            int i = 0;
            Iterator<es.a<?, ?>> it = Ep.values().iterator();
            while (true) {
                int i2 = i;
                if (!it.hasNext()) {
                    return i2;
                }
                es.a next = it.next();
                if (a(next)) {
                    i = b(next).hashCode() + i2 + next.cq();
                } else {
                    i = i2;
                }
            }
        }

        public boolean isDataValid() {
            return true;
        }

        public void writeToParcel(Parcel parcel, int i) {
            ij ijVar = CREATOR;
            ij.a(this, parcel, i);
        }
    }

    public static final class c extends es implements SafeParcelable, Person.Image {
        public static final im CREATOR = new im();
        private static final HashMap<String, es.a<?, ?>> Ep = new HashMap<>();
        private final Set<Integer> Eq;
        private String iH;
        private final int kg;

        static {
            Ep.put("url", es.a.g("url", 2));
        }

        public c() {
            this.kg = 1;
            this.Eq = new HashSet();
        }

        public c(String str) {
            this.Eq = new HashSet();
            this.kg = 1;
            this.iH = str;
            this.Eq.add(2);
        }

        c(Set<Integer> set, int i, String str) {
            this.Eq = set;
            this.kg = i;
            this.iH = str;
        }

        /* access modifiers changed from: protected */
        public Object V(String str) {
            return null;
        }

        /* access modifiers changed from: protected */
        public boolean W(String str) {
            return false;
        }

        /* access modifiers changed from: protected */
        public boolean a(es.a aVar) {
            return this.Eq.contains(Integer.valueOf(aVar.cq()));
        }

        /* access modifiers changed from: protected */
        public Object b(es.a aVar) {
            switch (aVar.cq()) {
                case 2:
                    return this.iH;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + aVar.cq());
            }
        }

        public HashMap<String, es.a<?, ?>> cj() {
            return Ep;
        }

        public int describeContents() {
            im imVar = CREATOR;
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof c)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            c cVar = (c) obj;
            for (es.a next : Ep.values()) {
                if (a(next)) {
                    if (!cVar.a(next)) {
                        return false;
                    }
                    if (!b(next).equals(cVar.b(next))) {
                        return false;
                    }
                } else if (cVar.a(next)) {
                    return false;
                }
            }
            return true;
        }

        /* renamed from: fJ */
        public c freeze() {
            return this;
        }

        /* access modifiers changed from: package-private */
        public Set<Integer> fa() {
            return this.Eq;
        }

        public String getUrl() {
            return this.iH;
        }

        /* access modifiers changed from: package-private */
        public int getVersionCode() {
            return this.kg;
        }

        public boolean hasUrl() {
            return this.Eq.contains(2);
        }

        public int hashCode() {
            int i = 0;
            Iterator<es.a<?, ?>> it = Ep.values().iterator();
            while (true) {
                int i2 = i;
                if (!it.hasNext()) {
                    return i2;
                }
                es.a next = it.next();
                if (a(next)) {
                    i = b(next).hashCode() + i2 + next.cq();
                } else {
                    i = i2;
                }
            }
        }

        public boolean isDataValid() {
            return true;
        }

        public void writeToParcel(Parcel parcel, int i) {
            im imVar = CREATOR;
            im.a(this, parcel, i);
        }
    }

    public static final class d extends es implements SafeParcelable, Person.Name {
        public static final in CREATOR = new in();
        private static final HashMap<String, es.a<?, ?>> Ep = new HashMap<>();
        private String EP;
        private String ES;
        private final Set<Integer> Eq;
        private String FQ;
        private String FR;
        private String FS;
        private String FT;
        private final int kg;

        static {
            Ep.put("familyName", es.a.g("familyName", 2));
            Ep.put("formatted", es.a.g("formatted", 3));
            Ep.put("givenName", es.a.g("givenName", 4));
            Ep.put("honorificPrefix", es.a.g("honorificPrefix", 5));
            Ep.put("honorificSuffix", es.a.g("honorificSuffix", 6));
            Ep.put("middleName", es.a.g("middleName", 7));
        }

        public d() {
            this.kg = 1;
            this.Eq = new HashSet();
        }

        d(Set<Integer> set, int i, String str, String str2, String str3, String str4, String str5, String str6) {
            this.Eq = set;
            this.kg = i;
            this.EP = str;
            this.FQ = str2;
            this.ES = str3;
            this.FR = str4;
            this.FS = str5;
            this.FT = str6;
        }

        /* access modifiers changed from: protected */
        public Object V(String str) {
            return null;
        }

        /* access modifiers changed from: protected */
        public boolean W(String str) {
            return false;
        }

        /* access modifiers changed from: protected */
        public boolean a(es.a aVar) {
            return this.Eq.contains(Integer.valueOf(aVar.cq()));
        }

        /* access modifiers changed from: protected */
        public Object b(es.a aVar) {
            switch (aVar.cq()) {
                case 2:
                    return this.EP;
                case 3:
                    return this.FQ;
                case 4:
                    return this.ES;
                case 5:
                    return this.FR;
                case 6:
                    return this.FS;
                case 7:
                    return this.FT;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + aVar.cq());
            }
        }

        public HashMap<String, es.a<?, ?>> cj() {
            return Ep;
        }

        public int describeContents() {
            in inVar = CREATOR;
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof d)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            d dVar = (d) obj;
            for (es.a next : Ep.values()) {
                if (a(next)) {
                    if (!dVar.a(next)) {
                        return false;
                    }
                    if (!b(next).equals(dVar.b(next))) {
                        return false;
                    }
                } else if (dVar.a(next)) {
                    return false;
                }
            }
            return true;
        }

        /* renamed from: fK */
        public d freeze() {
            return this;
        }

        /* access modifiers changed from: package-private */
        public Set<Integer> fa() {
            return this.Eq;
        }

        public String getFamilyName() {
            return this.EP;
        }

        public String getFormatted() {
            return this.FQ;
        }

        public String getGivenName() {
            return this.ES;
        }

        public String getHonorificPrefix() {
            return this.FR;
        }

        public String getHonorificSuffix() {
            return this.FS;
        }

        public String getMiddleName() {
            return this.FT;
        }

        /* access modifiers changed from: package-private */
        public int getVersionCode() {
            return this.kg;
        }

        public boolean hasFamilyName() {
            return this.Eq.contains(2);
        }

        public boolean hasFormatted() {
            return this.Eq.contains(3);
        }

        public boolean hasGivenName() {
            return this.Eq.contains(4);
        }

        public boolean hasHonorificPrefix() {
            return this.Eq.contains(5);
        }

        public boolean hasHonorificSuffix() {
            return this.Eq.contains(6);
        }

        public boolean hasMiddleName() {
            return this.Eq.contains(7);
        }

        public int hashCode() {
            int i = 0;
            Iterator<es.a<?, ?>> it = Ep.values().iterator();
            while (true) {
                int i2 = i;
                if (!it.hasNext()) {
                    return i2;
                }
                es.a next = it.next();
                if (a(next)) {
                    i = b(next).hashCode() + i2 + next.cq();
                } else {
                    i = i2;
                }
            }
        }

        public boolean isDataValid() {
            return true;
        }

        public void writeToParcel(Parcel parcel, int i) {
            in inVar = CREATOR;
            in.a(this, parcel, i);
        }
    }

    public static class e {
        public static int aB(String str) {
            if (str.equals("person")) {
                return 0;
            }
            if (str.equals("page")) {
                return 1;
            }
            throw new IllegalArgumentException("Unknown objectType string: " + str);
        }
    }

    public static final class f extends es implements SafeParcelable, Person.Organizations {
        public static final io CREATOR = new io();
        private static final HashMap<String, es.a<?, ?>> Ep = new HashMap<>();
        private String EO;
        private final Set<Integer> Eq;
        private String FU;
        private String FV;
        private boolean FW;
        private String Fe;
        private final int kg;
        private String mName;
        private int os;
        private String qL;
        private String sJ;

        static {
            Ep.put("department", es.a.g("department", 2));
            Ep.put("description", es.a.g("description", 3));
            Ep.put("endDate", es.a.g("endDate", 4));
            Ep.put("location", es.a.g("location", 5));
            Ep.put("name", es.a.g("name", 6));
            Ep.put("primary", es.a.f("primary", 7));
            Ep.put("startDate", es.a.g("startDate", 8));
            Ep.put("title", es.a.g("title", 9));
            Ep.put("type", es.a.a("type", 10, new ep().c("work", 0).c("school", 1), false));
        }

        public f() {
            this.kg = 1;
            this.Eq = new HashSet();
        }

        f(Set<Integer> set, int i, String str, String str2, String str3, String str4, String str5, boolean z, String str6, String str7, int i2) {
            this.Eq = set;
            this.kg = i;
            this.FU = str;
            this.sJ = str2;
            this.EO = str3;
            this.FV = str4;
            this.mName = str5;
            this.FW = z;
            this.Fe = str6;
            this.qL = str7;
            this.os = i2;
        }

        /* access modifiers changed from: protected */
        public Object V(String str) {
            return null;
        }

        /* access modifiers changed from: protected */
        public boolean W(String str) {
            return false;
        }

        /* access modifiers changed from: protected */
        public boolean a(es.a aVar) {
            return this.Eq.contains(Integer.valueOf(aVar.cq()));
        }

        /* access modifiers changed from: protected */
        public Object b(es.a aVar) {
            switch (aVar.cq()) {
                case 2:
                    return this.FU;
                case 3:
                    return this.sJ;
                case 4:
                    return this.EO;
                case 5:
                    return this.FV;
                case 6:
                    return this.mName;
                case 7:
                    return Boolean.valueOf(this.FW);
                case 8:
                    return this.Fe;
                case 9:
                    return this.qL;
                case 10:
                    return Integer.valueOf(this.os);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + aVar.cq());
            }
        }

        public HashMap<String, es.a<?, ?>> cj() {
            return Ep;
        }

        public int describeContents() {
            io ioVar = CREATOR;
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof f)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            f fVar = (f) obj;
            for (es.a next : Ep.values()) {
                if (a(next)) {
                    if (!fVar.a(next)) {
                        return false;
                    }
                    if (!b(next).equals(fVar.b(next))) {
                        return false;
                    }
                } else if (fVar.a(next)) {
                    return false;
                }
            }
            return true;
        }

        /* renamed from: fL */
        public f freeze() {
            return this;
        }

        /* access modifiers changed from: package-private */
        public Set<Integer> fa() {
            return this.Eq;
        }

        public String getDepartment() {
            return this.FU;
        }

        public String getDescription() {
            return this.sJ;
        }

        public String getEndDate() {
            return this.EO;
        }

        public String getLocation() {
            return this.FV;
        }

        public String getName() {
            return this.mName;
        }

        public String getStartDate() {
            return this.Fe;
        }

        public String getTitle() {
            return this.qL;
        }

        public int getType() {
            return this.os;
        }

        /* access modifiers changed from: package-private */
        public int getVersionCode() {
            return this.kg;
        }

        public boolean hasDepartment() {
            return this.Eq.contains(2);
        }

        public boolean hasDescription() {
            return this.Eq.contains(3);
        }

        public boolean hasEndDate() {
            return this.Eq.contains(4);
        }

        public boolean hasLocation() {
            return this.Eq.contains(5);
        }

        public boolean hasName() {
            return this.Eq.contains(6);
        }

        public boolean hasPrimary() {
            return this.Eq.contains(7);
        }

        public boolean hasStartDate() {
            return this.Eq.contains(8);
        }

        public boolean hasTitle() {
            return this.Eq.contains(9);
        }

        public boolean hasType() {
            return this.Eq.contains(10);
        }

        public int hashCode() {
            int i = 0;
            Iterator<es.a<?, ?>> it = Ep.values().iterator();
            while (true) {
                int i2 = i;
                if (!it.hasNext()) {
                    return i2;
                }
                es.a next = it.next();
                if (a(next)) {
                    i = b(next).hashCode() + i2 + next.cq();
                } else {
                    i = i2;
                }
            }
        }

        public boolean isDataValid() {
            return true;
        }

        public boolean isPrimary() {
            return this.FW;
        }

        public void writeToParcel(Parcel parcel, int i) {
            io ioVar = CREATOR;
            io.a(this, parcel, i);
        }
    }

    public static final class g extends es implements SafeParcelable, Person.PlacesLived {
        public static final ip CREATOR = new ip();
        private static final HashMap<String, es.a<?, ?>> Ep = new HashMap<>();
        private final Set<Integer> Eq;
        private boolean FW;
        private final int kg;
        private String mValue;

        static {
            Ep.put("primary", es.a.f("primary", 2));
            Ep.put("value", es.a.g("value", 3));
        }

        public g() {
            this.kg = 1;
            this.Eq = new HashSet();
        }

        g(Set<Integer> set, int i, boolean z, String str) {
            this.Eq = set;
            this.kg = i;
            this.FW = z;
            this.mValue = str;
        }

        /* access modifiers changed from: protected */
        public Object V(String str) {
            return null;
        }

        /* access modifiers changed from: protected */
        public boolean W(String str) {
            return false;
        }

        /* access modifiers changed from: protected */
        public boolean a(es.a aVar) {
            return this.Eq.contains(Integer.valueOf(aVar.cq()));
        }

        /* access modifiers changed from: protected */
        public Object b(es.a aVar) {
            switch (aVar.cq()) {
                case 2:
                    return Boolean.valueOf(this.FW);
                case 3:
                    return this.mValue;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + aVar.cq());
            }
        }

        public HashMap<String, es.a<?, ?>> cj() {
            return Ep;
        }

        public int describeContents() {
            ip ipVar = CREATOR;
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof g)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            g gVar = (g) obj;
            for (es.a next : Ep.values()) {
                if (a(next)) {
                    if (!gVar.a(next)) {
                        return false;
                    }
                    if (!b(next).equals(gVar.b(next))) {
                        return false;
                    }
                } else if (gVar.a(next)) {
                    return false;
                }
            }
            return true;
        }

        /* renamed from: fM */
        public g freeze() {
            return this;
        }

        /* access modifiers changed from: package-private */
        public Set<Integer> fa() {
            return this.Eq;
        }

        public String getValue() {
            return this.mValue;
        }

        /* access modifiers changed from: package-private */
        public int getVersionCode() {
            return this.kg;
        }

        public boolean hasPrimary() {
            return this.Eq.contains(2);
        }

        public boolean hasValue() {
            return this.Eq.contains(3);
        }

        public int hashCode() {
            int i = 0;
            Iterator<es.a<?, ?>> it = Ep.values().iterator();
            while (true) {
                int i2 = i;
                if (!it.hasNext()) {
                    return i2;
                }
                es.a next = it.next();
                if (a(next)) {
                    i = b(next).hashCode() + i2 + next.cq();
                } else {
                    i = i2;
                }
            }
        }

        public boolean isDataValid() {
            return true;
        }

        public boolean isPrimary() {
            return this.FW;
        }

        public void writeToParcel(Parcel parcel, int i) {
            ip ipVar = CREATOR;
            ip.a(this, parcel, i);
        }
    }

    public static final class h extends es implements SafeParcelable, Person.Urls {
        public static final iq CREATOR = new iq();
        private static final HashMap<String, es.a<?, ?>> Ep = new HashMap<>();
        private final Set<Integer> Eq;
        private String FX;
        private final int FY;
        private final int kg;
        private String mValue;
        private int os;

        static {
            Ep.put(PlusShare.KEY_CALL_TO_ACTION_LABEL, es.a.g(PlusShare.KEY_CALL_TO_ACTION_LABEL, 5));
            Ep.put("type", es.a.a("type", 6, new ep().c("home", 0).c("work", 1).c("blog", 2).c(Scopes.PROFILE, 3).c(FacebookRequestErrorClassification.KEY_OTHER, 4).c("otherProfile", 5).c("contributor", 6).c("website", 7), false));
            Ep.put("value", es.a.g("value", 4));
        }

        public h() {
            this.FY = 4;
            this.kg = 2;
            this.Eq = new HashSet();
        }

        h(Set<Integer> set, int i, String str, int i2, String str2, int i3) {
            this.FY = 4;
            this.Eq = set;
            this.kg = i;
            this.FX = str;
            this.os = i2;
            this.mValue = str2;
        }

        /* access modifiers changed from: protected */
        public Object V(String str) {
            return null;
        }

        /* access modifiers changed from: protected */
        public boolean W(String str) {
            return false;
        }

        /* access modifiers changed from: protected */
        public boolean a(es.a aVar) {
            return this.Eq.contains(Integer.valueOf(aVar.cq()));
        }

        /* access modifiers changed from: protected */
        public Object b(es.a aVar) {
            switch (aVar.cq()) {
                case 4:
                    return this.mValue;
                case 5:
                    return this.FX;
                case 6:
                    return Integer.valueOf(this.os);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + aVar.cq());
            }
        }

        public HashMap<String, es.a<?, ?>> cj() {
            return Ep;
        }

        public int describeContents() {
            iq iqVar = CREATOR;
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof h)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            h hVar = (h) obj;
            for (es.a next : Ep.values()) {
                if (a(next)) {
                    if (!hVar.a(next)) {
                        return false;
                    }
                    if (!b(next).equals(hVar.b(next))) {
                        return false;
                    }
                } else if (hVar.a(next)) {
                    return false;
                }
            }
            return true;
        }

        @Deprecated
        public int fN() {
            return 4;
        }

        /* renamed from: fO */
        public h freeze() {
            return this;
        }

        /* access modifiers changed from: package-private */
        public Set<Integer> fa() {
            return this.Eq;
        }

        public String getLabel() {
            return this.FX;
        }

        public int getType() {
            return this.os;
        }

        public String getValue() {
            return this.mValue;
        }

        /* access modifiers changed from: package-private */
        public int getVersionCode() {
            return this.kg;
        }

        public boolean hasLabel() {
            return this.Eq.contains(5);
        }

        public boolean hasType() {
            return this.Eq.contains(6);
        }

        public boolean hasValue() {
            return this.Eq.contains(4);
        }

        public int hashCode() {
            int i = 0;
            Iterator<es.a<?, ?>> it = Ep.values().iterator();
            while (true) {
                int i2 = i;
                if (!it.hasNext()) {
                    return i2;
                }
                es.a next = it.next();
                if (a(next)) {
                    i = b(next).hashCode() + i2 + next.cq();
                } else {
                    i = i2;
                }
            }
        }

        public boolean isDataValid() {
            return true;
        }

        public void writeToParcel(Parcel parcel, int i) {
            iq iqVar = CREATOR;
            iq.a(this, parcel, i);
        }
    }

    static {
        Ep.put("aboutMe", es.a.g("aboutMe", 2));
        Ep.put("ageRange", es.a.a("ageRange", 3, a.class));
        Ep.put("birthday", es.a.g("birthday", 4));
        Ep.put("braggingRights", es.a.g("braggingRights", 5));
        Ep.put("circledByCount", es.a.d("circledByCount", 6));
        Ep.put("cover", es.a.a("cover", 7, b.class));
        Ep.put("currentLocation", es.a.g("currentLocation", 8));
        Ep.put("displayName", es.a.g("displayName", 9));
        Ep.put("gender", es.a.a("gender", 12, new ep().c("male", 0).c("female", 1).c(FacebookRequestErrorClassification.KEY_OTHER, 2), false));
        Ep.put(ShareConstants.WEB_DIALOG_PARAM_ID, es.a.g(ShareConstants.WEB_DIALOG_PARAM_ID, 14));
        Ep.put("image", es.a.a("image", 15, c.class));
        Ep.put("isPlusUser", es.a.f("isPlusUser", 16));
        Ep.put("language", es.a.g("language", 18));
        Ep.put("name", es.a.a("name", 19, d.class));
        Ep.put("nickname", es.a.g("nickname", 20));
        Ep.put("objectType", es.a.a("objectType", 21, new ep().c("person", 0).c("page", 1), false));
        Ep.put("organizations", es.a.b("organizations", 22, f.class));
        Ep.put("placesLived", es.a.b("placesLived", 23, g.class));
        Ep.put("plusOneCount", es.a.d("plusOneCount", 24));
        Ep.put("relationshipStatus", es.a.a("relationshipStatus", 25, new ep().c("single", 0).c("in_a_relationship", 1).c("engaged", 2).c("married", 3).c("its_complicated", 4).c("open_relationship", 5).c("widowed", 6).c("in_domestic_partnership", 7).c("in_civil_union", 8), false));
        Ep.put("tagline", es.a.g("tagline", 26));
        Ep.put("url", es.a.g("url", 27));
        Ep.put("urls", es.a.b("urls", 28, h.class));
        Ep.put("verified", es.a.f("verified", 29));
    }

    public ig() {
        this.kg = 2;
        this.Eq = new HashSet();
    }

    public ig(String str, String str2, c cVar, int i, String str3) {
        this.kg = 2;
        this.Eq = new HashSet();
        this.qa = str;
        this.Eq.add(9);
        this.uS = str2;
        this.Eq.add(14);
        this.Fw = cVar;
        this.Eq.add(15);
        this.FB = i;
        this.Eq.add(21);
        this.iH = str3;
        this.Eq.add(27);
    }

    ig(Set<Integer> set, int i, String str, a aVar, String str2, String str3, int i2, b bVar, String str4, String str5, int i3, String str6, c cVar, boolean z, String str7, d dVar, String str8, int i4, List<f> list, List<g> list2, int i5, int i6, String str9, String str10, List<h> list3, boolean z2) {
        this.Eq = set;
        this.kg = i;
        this.Fp = str;
        this.Fq = aVar;
        this.Fr = str2;
        this.Fs = str3;
        this.Ft = i2;
        this.Fu = bVar;
        this.Fv = str4;
        this.qa = str5;
        this.eL = i3;
        this.uS = str6;
        this.Fw = cVar;
        this.Fx = z;
        this.Fy = str7;
        this.Fz = dVar;
        this.FA = str8;
        this.FB = i4;
        this.FC = list;
        this.FD = list2;
        this.FE = i5;
        this.FF = i6;
        this.FG = str9;
        this.iH = str10;
        this.FH = list3;
        this.FI = z2;
    }

    public static ig g(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        ig au = CREATOR.createFromParcel(obtain);
        obtain.recycle();
        return au;
    }

    /* access modifiers changed from: protected */
    public Object V(String str) {
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean W(String str) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean a(es.a aVar) {
        return this.Eq.contains(Integer.valueOf(aVar.cq()));
    }

    /* access modifiers changed from: protected */
    public Object b(es.a aVar) {
        switch (aVar.cq()) {
            case 2:
                return this.Fp;
            case 3:
                return this.Fq;
            case 4:
                return this.Fr;
            case 5:
                return this.Fs;
            case 6:
                return Integer.valueOf(this.Ft);
            case 7:
                return this.Fu;
            case 8:
                return this.Fv;
            case 9:
                return this.qa;
            case 12:
                return Integer.valueOf(this.eL);
            case 14:
                return this.uS;
            case 15:
                return this.Fw;
            case 16:
                return Boolean.valueOf(this.Fx);
            case 18:
                return this.Fy;
            case TimeUtils.HUNDRED_DAY_FIELD_LEN:
                return this.Fz;
            case 20:
                return this.FA;
            case 21:
                return Integer.valueOf(this.FB);
            case 22:
                return this.FC;
            case 23:
                return this.FD;
            case 24:
                return Integer.valueOf(this.FE);
            case 25:
                return Integer.valueOf(this.FF);
            case 26:
                return this.FG;
            case 27:
                return this.iH;
            case 28:
                return this.FH;
            case 29:
                return Boolean.valueOf(this.FI);
            default:
                throw new IllegalStateException("Unknown safe parcelable id=" + aVar.cq());
        }
    }

    public HashMap<String, es.a<?, ?>> cj() {
        return Ep;
    }

    public int describeContents() {
        ih ihVar = CREATOR;
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ig)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ig igVar = (ig) obj;
        for (es.a next : Ep.values()) {
            if (a(next)) {
                if (!igVar.a(next)) {
                    return false;
                }
                if (!b(next).equals(igVar.b(next))) {
                    return false;
                }
            } else if (igVar.a(next)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public List<g> fA() {
        return this.FD;
    }

    /* access modifiers changed from: package-private */
    public List<h> fB() {
        return this.FH;
    }

    /* renamed from: fC */
    public ig freeze() {
        return this;
    }

    /* access modifiers changed from: package-private */
    public Set<Integer> fa() {
        return this.Eq;
    }

    /* access modifiers changed from: package-private */
    public a fv() {
        return this.Fq;
    }

    /* access modifiers changed from: package-private */
    public b fw() {
        return this.Fu;
    }

    /* access modifiers changed from: package-private */
    public c fx() {
        return this.Fw;
    }

    /* access modifiers changed from: package-private */
    public d fy() {
        return this.Fz;
    }

    /* access modifiers changed from: package-private */
    public List<f> fz() {
        return this.FC;
    }

    public String getAboutMe() {
        return this.Fp;
    }

    public Person.AgeRange getAgeRange() {
        return this.Fq;
    }

    public String getBirthday() {
        return this.Fr;
    }

    public String getBraggingRights() {
        return this.Fs;
    }

    public int getCircledByCount() {
        return this.Ft;
    }

    public Person.Cover getCover() {
        return this.Fu;
    }

    public String getCurrentLocation() {
        return this.Fv;
    }

    public String getDisplayName() {
        return this.qa;
    }

    public int getGender() {
        return this.eL;
    }

    public String getId() {
        return this.uS;
    }

    public Person.Image getImage() {
        return this.Fw;
    }

    public String getLanguage() {
        return this.Fy;
    }

    public Person.Name getName() {
        return this.Fz;
    }

    public String getNickname() {
        return this.FA;
    }

    public int getObjectType() {
        return this.FB;
    }

    public List<Person.Organizations> getOrganizations() {
        return (ArrayList) this.FC;
    }

    public List<Person.PlacesLived> getPlacesLived() {
        return (ArrayList) this.FD;
    }

    public int getPlusOneCount() {
        return this.FE;
    }

    public int getRelationshipStatus() {
        return this.FF;
    }

    public String getTagline() {
        return this.FG;
    }

    public String getUrl() {
        return this.iH;
    }

    public List<Person.Urls> getUrls() {
        return (ArrayList) this.FH;
    }

    /* access modifiers changed from: package-private */
    public int getVersionCode() {
        return this.kg;
    }

    public boolean hasAboutMe() {
        return this.Eq.contains(2);
    }

    public boolean hasAgeRange() {
        return this.Eq.contains(3);
    }

    public boolean hasBirthday() {
        return this.Eq.contains(4);
    }

    public boolean hasBraggingRights() {
        return this.Eq.contains(5);
    }

    public boolean hasCircledByCount() {
        return this.Eq.contains(6);
    }

    public boolean hasCover() {
        return this.Eq.contains(7);
    }

    public boolean hasCurrentLocation() {
        return this.Eq.contains(8);
    }

    public boolean hasDisplayName() {
        return this.Eq.contains(9);
    }

    public boolean hasGender() {
        return this.Eq.contains(12);
    }

    public boolean hasId() {
        return this.Eq.contains(14);
    }

    public boolean hasImage() {
        return this.Eq.contains(15);
    }

    public boolean hasIsPlusUser() {
        return this.Eq.contains(16);
    }

    public boolean hasLanguage() {
        return this.Eq.contains(18);
    }

    public boolean hasName() {
        return this.Eq.contains(19);
    }

    public boolean hasNickname() {
        return this.Eq.contains(20);
    }

    public boolean hasObjectType() {
        return this.Eq.contains(21);
    }

    public boolean hasOrganizations() {
        return this.Eq.contains(22);
    }

    public boolean hasPlacesLived() {
        return this.Eq.contains(23);
    }

    public boolean hasPlusOneCount() {
        return this.Eq.contains(24);
    }

    public boolean hasRelationshipStatus() {
        return this.Eq.contains(25);
    }

    public boolean hasTagline() {
        return this.Eq.contains(26);
    }

    public boolean hasUrl() {
        return this.Eq.contains(27);
    }

    public boolean hasUrls() {
        return this.Eq.contains(28);
    }

    public boolean hasVerified() {
        return this.Eq.contains(29);
    }

    public int hashCode() {
        int i = 0;
        Iterator<es.a<?, ?>> it = Ep.values().iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            es.a next = it.next();
            if (a(next)) {
                i = b(next).hashCode() + i2 + next.cq();
            } else {
                i = i2;
            }
        }
    }

    public boolean isDataValid() {
        return true;
    }

    public boolean isPlusUser() {
        return this.Fx;
    }

    public boolean isVerified() {
        return this.FI;
    }

    public void writeToParcel(Parcel parcel, int i) {
        ih ihVar = CREATOR;
        ih.a(this, parcel, i);
    }
}
