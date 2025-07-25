package com.ansca.corona;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import jp.stargarage.g2metrics.BuildConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

public abstract class CoronaData implements Cloneable, Serializable, Parcelable {

    public static class Boolean extends Value {
        public static final Parcelable.Creator<Boolean> CREATOR = new ParcelableCreator();
        public static final Boolean FALSE = new Boolean(false);
        public static final Boolean TRUE = new Boolean(true);
        private boolean fValue;

        private static class ParcelableCreator implements Parcelable.Creator<Boolean> {
            private ParcelableCreator() {
            }

            public Boolean createFromParcel(Parcel parcel) {
                return Boolean.from(parcel.readByte() != 0);
            }

            public Boolean[] newArray(int i) {
                return new Boolean[i];
            }
        }

        public Boolean(boolean z) {
            this.fValue = z;
        }

        public static Boolean from(boolean z) {
            return z ? TRUE : FALSE;
        }

        public boolean equals(boolean z) {
            return this.fValue == z;
        }

        public boolean getValue() {
            return this.fValue;
        }

        public Object getValueAsObject() {
            return Boolean.valueOf(this.fValue);
        }

        public boolean pushTo(LuaState luaState) {
            luaState.pushBoolean(this.fValue);
            return true;
        }

        public void writeTo(XmlSerializer xmlSerializer) throws IOException {
            xmlSerializer.startTag(BuildConfig.FLAVOR, "boolean");
            xmlSerializer.attribute(BuildConfig.FLAVOR, "value", Boolean.toString(this.fValue));
            xmlSerializer.endTag(BuildConfig.FLAVOR, "boolean");
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte((byte) (this.fValue ? 1 : 0));
        }
    }

    public static class Double extends Value {
        public static final Parcelable.Creator<Double> CREATOR = new ParcelableCreator();
        private double fValue;

        private static class ParcelableCreator implements Parcelable.Creator<Double> {
            private ParcelableCreator() {
            }

            public Double createFromParcel(Parcel parcel) {
                return new Double(parcel.readDouble());
            }

            public Double[] newArray(int i) {
                return new Double[i];
            }
        }

        public Double(double d) {
            this.fValue = d;
        }

        public boolean equals(double d) {
            return this.fValue == d;
        }

        public double getValue() {
            return this.fValue;
        }

        public Object getValueAsObject() {
            return Double.valueOf(this.fValue);
        }

        public boolean pushTo(LuaState luaState) {
            luaState.pushNumber(this.fValue);
            return true;
        }

        public void writeTo(XmlSerializer xmlSerializer) throws IOException {
            xmlSerializer.startTag(BuildConfig.FLAVOR, "double");
            xmlSerializer.attribute(BuildConfig.FLAVOR, "value", Double.toString(this.fValue));
            xmlSerializer.endTag(BuildConfig.FLAVOR, "double");
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeDouble(this.fValue);
        }
    }

    public static class List extends CoronaData implements Iterable<CoronaData> {
        public static final Parcelable.Creator<List> CREATOR = new ParcelableCreator();
        private ArrayList<CoronaData> fCollection = new ArrayList<>();

        private static class ParcelableCreator implements Parcelable.Creator<List> {
            private ParcelableCreator() {
            }

            public List createFromParcel(Parcel parcel) {
                List list = new List();
                ClassLoader classLoader = getClass().getClassLoader();
                int readInt = parcel.readInt();
                for (int i = 0; i < readInt; i++) {
                    list.add((CoronaData) parcel.readParcelable(classLoader));
                }
                return list;
            }

            public List[] newArray(int i) {
                return new List[i];
            }
        }

        public static List from(JSONArray jSONArray) throws JSONException {
            if (jSONArray == null) {
                return null;
            }
            List list = new List();
            for (int i = 0; i < jSONArray.length(); i++) {
                list.add(CoronaData.from(jSONArray.get(i)));
            }
            return list;
        }

        public void add(CoronaData coronaData) {
            if (coronaData != null) {
                this.fCollection.add(coronaData);
            }
        }

        public void clear() {
            this.fCollection.clear();
        }

        public List clone() {
            List list = (List) CoronaData.super.clone();
            list.fCollection = new ArrayList<>();
            Iterator<CoronaData> it = this.fCollection.iterator();
            while (it.hasNext()) {
                list.add(it.next());
            }
            return list;
        }

        public Iterator<CoronaData> iterator() {
            return this.fCollection.iterator();
        }

        public boolean pushTo(LuaState luaState) {
            int size = this.fCollection.size();
            if (size <= 0) {
                luaState.newTable();
            } else {
                luaState.newTable(size, 0);
                int top = luaState.getTop();
                for (int i = 0; i < size; i++) {
                    CoronaData coronaData = this.fCollection.get(i);
                    if (coronaData != null && coronaData.pushTo(luaState)) {
                        luaState.rawSet(top, i + 1);
                    }
                }
            }
            return true;
        }

        public boolean remove(CoronaData coronaData) {
            if (coronaData == null) {
                return false;
            }
            return this.fCollection.remove(coronaData);
        }

        public int size() {
            return this.fCollection.size();
        }

        public void writeTo(XmlSerializer xmlSerializer) throws IOException {
            xmlSerializer.startTag(BuildConfig.FLAVOR, "list");
            Iterator<CoronaData> it = this.fCollection.iterator();
            while (it.hasNext()) {
                CoronaData next = it.next();
                if (next != null) {
                    next.writeTo(xmlSerializer);
                }
            }
            xmlSerializer.endTag(BuildConfig.FLAVOR, "list");
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.fCollection.size());
            Iterator<CoronaData> it = this.fCollection.iterator();
            while (it.hasNext()) {
                CoronaData next = it.next();
                if (next != null) {
                    parcel.writeParcelable(next, i);
                }
            }
        }
    }

    public static class Proxy extends CoronaData {
        public static final Parcelable.Creator<Proxy> CREATOR = new ParcelableCreator();
        private CoronaData fData;

        private static class ParcelableCreator implements Parcelable.Creator<Proxy> {
            private ParcelableCreator() {
            }

            public Proxy createFromParcel(Parcel parcel) {
                Proxy proxy = new Proxy();
                if (parcel.readByte() != 0) {
                    proxy.setData((CoronaData) parcel.readParcelable(getClass().getClassLoader()));
                }
                return proxy;
            }

            public Proxy[] newArray(int i) {
                return new Proxy[i];
            }
        }

        public Proxy() {
            this((CoronaData) null);
        }

        public Proxy(CoronaData coronaData) {
            this.fData = coronaData;
        }

        public Proxy clone() {
            return (Proxy) CoronaData.super.clone();
        }

        public CoronaData getData() {
            return this.fData;
        }

        public boolean pushTo(LuaState luaState) {
            if (this.fData == null) {
                return false;
            }
            return this.fData.pushTo(luaState);
        }

        public void setData(CoronaData coronaData) {
            this.fData = coronaData;
        }

        public void writeTo(XmlSerializer xmlSerializer) throws IOException {
            xmlSerializer.startTag(BuildConfig.FLAVOR, "proxy");
            if (this.fData != null) {
                this.fData.writeTo(xmlSerializer);
            }
            xmlSerializer.endTag(BuildConfig.FLAVOR, "proxy");
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte((byte) (this.fData != null ? 1 : 0));
            if (this.fData != null) {
                parcel.writeParcelable(this.fData, i);
            }
        }
    }

    public static class String extends Value {
        public static final Parcelable.Creator<String> CREATOR = new ParcelableCreator();
        public static final String EMPTY = new String(BuildConfig.FLAVOR);
        private String fValue;

        private static class ParcelableCreator implements Parcelable.Creator<String> {
            private ParcelableCreator() {
            }

            public String createFromParcel(Parcel parcel) {
                return new String(parcel.readString());
            }

            public String[] newArray(int i) {
                return new String[i];
            }
        }

        public String(String str) {
            this.fValue = str == null ? BuildConfig.FLAVOR : str;
        }

        public String getValue() {
            return this.fValue;
        }

        public Object getValueAsObject() {
            return this.fValue;
        }

        public boolean pushTo(LuaState luaState) {
            luaState.pushString(getValue());
            return true;
        }

        public void writeTo(XmlSerializer xmlSerializer) throws IOException {
            xmlSerializer.startTag(BuildConfig.FLAVOR, "string");
            xmlSerializer.text(this.fValue);
            xmlSerializer.endTag(BuildConfig.FLAVOR, "string");
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.fValue);
        }
    }

    public static class Table extends CoronaData implements Map<Value, CoronaData> {
        public static final Parcelable.Creator<Table> CREATOR = new ParcelableCreator();
        private HashMap<Value, CoronaData> fHashMap = new HashMap<>();

        private static class ParcelableCreator implements Parcelable.Creator<Table> {
            private ParcelableCreator() {
            }

            public Table createFromParcel(Parcel parcel) {
                Table table = new Table();
                ClassLoader classLoader = getClass().getClassLoader();
                int readInt = parcel.readInt();
                for (int i = 0; i < readInt; i++) {
                    Value value = (Value) parcel.readParcelable(classLoader);
                    CoronaData coronaData = null;
                    if (parcel.readByte() != 0) {
                        coronaData = (CoronaData) parcel.readParcelable(classLoader);
                    }
                    table.put(value, coronaData);
                }
                return table;
            }

            public Table[] newArray(int i) {
                return new Table[i];
            }
        }

        public static Table from(Bundle bundle) {
            if (bundle == null) {
                return null;
            }
            Table table = new Table();
            for (String str : bundle.keySet()) {
                table.put((Value) new String(str), CoronaData.from(bundle.get(str)));
            }
            return table;
        }

        public static Table from(JSONObject jSONObject) throws JSONException {
            if (jSONObject == null) {
                return null;
            }
            Table table = new Table();
            Iterator<String> keys = jSONObject.keys();
            if (keys == null) {
                return table;
            }
            while (keys.hasNext()) {
                String next = keys.next();
                table.put((Value) new String(next), CoronaData.from(jSONObject.get(next)));
            }
            return table;
        }

        public void clear() {
            this.fHashMap.clear();
        }

        public Table clone() {
            Table table = (Table) CoronaData.super.clone();
            table.fHashMap = new HashMap<>();
            for (Map.Entry next : entrySet()) {
                if (next.getKey() != null) {
                    Value value = (Value) ((Value) next.getKey()).clone();
                    CoronaData coronaData = (CoronaData) next.getValue();
                    if (coronaData != null) {
                        coronaData = coronaData.clone();
                    }
                    table.put(value, coronaData);
                }
            }
            return table;
        }

        public boolean containsKey(Object obj) {
            if (obj == null) {
                return false;
            }
            return this.fHashMap.containsKey(obj);
        }

        public boolean containsValue(Object obj) {
            return this.fHashMap.containsValue(obj);
        }

        public Set<Map.Entry<Value, CoronaData>> entrySet() {
            return this.fHashMap.entrySet();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Table)) {
                return false;
            }
            return this.fHashMap.equals(((Table) obj).fHashMap);
        }

        public CoronaData get(Object obj) {
            if (obj == null) {
                return null;
            }
            return this.fHashMap.get(obj);
        }

        public int hashCode() {
            return this.fHashMap.hashCode();
        }

        public boolean isEmpty() {
            return this.fHashMap.isEmpty();
        }

        public Set<Value> keySet() {
            return this.fHashMap.keySet();
        }

        public boolean pushTo(LuaState luaState) {
            int size = this.fHashMap.size();
            if (size <= 0) {
                luaState.newTable();
            } else {
                luaState.newTable(0, size);
                int top = luaState.getTop();
                for (Map.Entry next : entrySet()) {
                    if (!(next.getKey() == null || next.getValue() == null)) {
                        ((Value) next.getKey()).pushTo(luaState);
                        ((CoronaData) next.getValue()).pushTo(luaState);
                        luaState.rawSet(top);
                    }
                }
            }
            return true;
        }

        public CoronaData put(Value value, CoronaData coronaData) {
            if (value == null) {
                return null;
            }
            return this.fHashMap.put(value, coronaData);
        }

        public void putAll(Map<? extends Value, ? extends CoronaData> map) {
            if (map != null) {
                this.fHashMap.putAll(map);
            }
        }

        public CoronaData remove(Object obj) {
            if (obj == null) {
                return null;
            }
            return this.fHashMap.remove(obj);
        }

        public int size() {
            return this.fHashMap.size();
        }

        public Collection<CoronaData> values() {
            return this.fHashMap.values();
        }

        public void writeTo(XmlSerializer xmlSerializer) throws IOException {
            xmlSerializer.startTag(BuildConfig.FLAVOR, "table");
            for (Map.Entry next : entrySet()) {
                if (!(next.getKey() == null || next.getValue() == null)) {
                    xmlSerializer.startTag(BuildConfig.FLAVOR, "entry");
                    xmlSerializer.startTag(BuildConfig.FLAVOR, "key");
                    ((Value) next.getKey()).writeTo(xmlSerializer);
                    xmlSerializer.endTag(BuildConfig.FLAVOR, "key");
                    xmlSerializer.startTag(BuildConfig.FLAVOR, "value");
                    ((CoronaData) next.getValue()).writeTo(xmlSerializer);
                    xmlSerializer.endTag(BuildConfig.FLAVOR, "value");
                    xmlSerializer.endTag(BuildConfig.FLAVOR, "entry");
                }
            }
            xmlSerializer.endTag(BuildConfig.FLAVOR, "table");
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(size());
            for (Map.Entry next : entrySet()) {
                if (!(next == null || next.getKey() == null)) {
                    parcel.writeParcelable((Parcelable) next.getKey(), i);
                    boolean z = next.getValue() != null;
                    parcel.writeByte((byte) (z ? 1 : 0));
                    if (z) {
                        parcel.writeParcelable((Parcelable) next.getValue(), i);
                    }
                }
            }
        }
    }

    public static abstract class Value extends CoronaData {
        public /* bridge */ /* synthetic */ Object clone() throws CloneNotSupportedException {
            return CoronaData.super.clone();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Value)) {
                return false;
            }
            Object valueAsObject = getValueAsObject();
            Object valueAsObject2 = ((Value) obj).getValueAsObject();
            if (valueAsObject == valueAsObject2) {
                return true;
            }
            if (valueAsObject != null) {
                return valueAsObject.equals(valueAsObject2);
            }
            return false;
        }

        public abstract Object getValueAsObject();

        public int hashCode() {
            Object valueAsObject = getValueAsObject();
            if (valueAsObject == null) {
                return 0;
            }
            return valueAsObject.hashCode();
        }
    }

    public static CoronaData from(LuaState luaState, int i) {
        if (luaState == null) {
            return null;
        }
        LuaType type = luaState.type(i);
        if (type == LuaType.BOOLEAN) {
            return Boolean.from(luaState.toBoolean(i));
        }
        if (type == LuaType.NUMBER) {
            return new Double(luaState.toNumber(i));
        }
        if (type == LuaType.STRING) {
            return new String(luaState.toString(i));
        }
        if (type != LuaType.TABLE) {
            return null;
        }
        if (i < 0) {
            i = luaState.getTop() + i + 1;
        }
        Table table = new Table();
        luaState.pushNil();
        while (luaState.next(i)) {
            CoronaData from = from(luaState, -2);
            if (from instanceof Value) {
                table.put((Value) from, from(luaState, -1));
            }
            luaState.pop(1);
        }
        return table;
    }

    public static CoronaData from(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof CoronaData) {
            return (CoronaData) obj;
        }
        CoronaData coronaData = null;
        if (obj instanceof Boolean) {
            coronaData = Boolean.from(((Boolean) obj).booleanValue());
        } else if (obj instanceof Number) {
            coronaData = new Double(((Number) obj).doubleValue());
        } else if ((obj instanceof Character) || (obj instanceof CharSequence)) {
            coronaData = new String(obj.toString());
        } else if (obj instanceof File) {
            coronaData = new String(((File) obj).getAbsolutePath());
        } else if (obj instanceof Uri) {
            coronaData = new String(obj.toString());
        } else if (obj instanceof Bundle) {
            coronaData = Table.from((Bundle) obj);
        } else if (obj instanceof JSONArray) {
            try {
                coronaData = List.from((JSONArray) obj);
            } catch (Exception e) {
                try {
                    coronaData = new String(((JSONArray) obj).toString());
                } catch (Exception e2) {
                }
            }
        } else if (obj instanceof JSONObject) {
            try {
                coronaData = Table.from((JSONObject) obj);
            } catch (Exception e3) {
                try {
                    coronaData = new String(((JSONObject) obj).toString());
                } catch (Exception e4) {
                }
            }
        } else if (obj.getClass().isArray()) {
            List list = new List();
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                list.add(from(Array.get(obj, i)));
            }
            coronaData = list;
        } else if (obj instanceof Map) {
            Table table = new Table();
            for (Map.Entry entry : ((Map) obj).entrySet()) {
                CoronaData from = from(entry.getKey());
                if (from instanceof Value) {
                    table.put((Value) from, from(entry.getValue()));
                }
            }
            coronaData = table;
        } else if (obj instanceof Iterable) {
            List list2 = new List();
            for (Object from2 : (Iterable) obj) {
                list2.add(from(from2));
            }
            coronaData = list2;
        }
        return coronaData;
    }

    /* JADX WARNING: type inference failed for: r0v4, types: [com.ansca.corona.CoronaData] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.ansca.corona.CoronaData from(org.xmlpull.v1.XmlPullParser r13) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
            r0 = 0
            r12 = 3
            r11 = 2
            if (r13 != 0) goto L_0x0006
        L_0x0005:
            return r0
        L_0x0006:
            int r8 = r13.nextTag()
            if (r8 != r11) goto L_0x0005
            java.lang.String r4 = r13.getName()
            if (r4 == 0) goto L_0x0005
            int r9 = r4.length()
            if (r9 <= 0) goto L_0x0005
            r0 = 0
            java.lang.String r9 = "boolean"
            boolean r9 = r9.equals(r4)
            if (r9 == 0) goto L_0x0045
            java.lang.String r9 = ""
            java.lang.String r10 = "value"
            java.lang.String r9 = r13.getAttributeValue(r9, r10)
            boolean r6 = java.lang.Boolean.parseBoolean(r9)
            com.ansca.corona.CoronaData$Boolean r0 = com.ansca.corona.CoronaData.Boolean.from(r6)
        L_0x0031:
            int r9 = r13.getEventType()
            if (r9 != r12) goto L_0x0041
            java.lang.String r9 = r13.getName()
            boolean r9 = r4.equals(r9)
            if (r9 != 0) goto L_0x0005
        L_0x0041:
            r13.nextTag()
            goto L_0x0031
        L_0x0045:
            java.lang.String r9 = "double"
            boolean r9 = r9.equals(r4)
            if (r9 == 0) goto L_0x005f
            java.lang.String r9 = ""
            java.lang.String r10 = "value"
            java.lang.String r9 = r13.getAttributeValue(r9, r10)
            double r6 = java.lang.Double.parseDouble(r9)
            com.ansca.corona.CoronaData$Double r0 = new com.ansca.corona.CoronaData$Double
            r0.<init>(r6)
            goto L_0x0031
        L_0x005f:
            java.lang.String r9 = "string"
            boolean r9 = r9.equals(r4)
            if (r9 == 0) goto L_0x007c
            com.ansca.corona.CoronaData$String r0 = com.ansca.corona.CoronaData.String.EMPTY
            int r8 = r13.next()
            r9 = 4
            if (r8 != r9) goto L_0x0031
            java.lang.String r5 = r13.getText()
            if (r5 == 0) goto L_0x0031
            com.ansca.corona.CoronaData$String r0 = new com.ansca.corona.CoronaData$String
            r0.<init>(r5)
            goto L_0x0031
        L_0x007c:
            java.lang.String r9 = "proxy"
            boolean r9 = r9.equals(r4)
            if (r9 == 0) goto L_0x008e
            com.ansca.corona.CoronaData$Proxy r0 = new com.ansca.corona.CoronaData$Proxy
            com.ansca.corona.CoronaData r9 = from((org.xmlpull.v1.XmlPullParser) r13)
            r0.<init>(r9)
            goto L_0x0031
        L_0x008e:
            java.lang.String r9 = "list"
            boolean r9 = r9.equals(r4)
            if (r9 == 0) goto L_0x00a6
            com.ansca.corona.CoronaData$List r2 = new com.ansca.corona.CoronaData$List
            r2.<init>()
        L_0x009b:
            com.ansca.corona.CoronaData r0 = from((org.xmlpull.v1.XmlPullParser) r13)
            r2.add(r0)
            if (r0 != 0) goto L_0x009b
            r0 = r2
            goto L_0x0031
        L_0x00a6:
            java.lang.String r9 = "table"
            boolean r9 = r9.equals(r4)
            if (r9 == 0) goto L_0x0031
            com.ansca.corona.CoronaData$Table r3 = new com.ansca.corona.CoronaData$Table
            r3.<init>()
        L_0x00b3:
            int r8 = r13.nextTag()
            if (r8 != r11) goto L_0x010c
            java.lang.String r9 = "entry"
            java.lang.String r10 = r13.getName()
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x010c
            r1 = 0
            r6 = 0
        L_0x00c7:
            int r8 = r13.nextTag()
            if (r8 != r11) goto L_0x00f6
            java.lang.String r9 = "key"
            java.lang.String r10 = r13.getName()
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x00e5
            com.ansca.corona.CoronaData r0 = from((org.xmlpull.v1.XmlPullParser) r13)
            boolean r9 = r0 instanceof com.ansca.corona.CoronaData.Value
            if (r9 == 0) goto L_0x00c7
            r1 = r0
            com.ansca.corona.CoronaData$Value r1 = (com.ansca.corona.CoronaData.Value) r1
            goto L_0x00c7
        L_0x00e5:
            java.lang.String r9 = "value"
            java.lang.String r10 = r13.getName()
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x00c7
            com.ansca.corona.CoronaData r6 = from((org.xmlpull.v1.XmlPullParser) r13)
            goto L_0x00c7
        L_0x00f6:
            if (r8 != r12) goto L_0x00c7
            java.lang.String r9 = "entry"
            java.lang.String r10 = r13.getName()
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x00c7
            if (r1 == 0) goto L_0x00b3
            if (r6 == 0) goto L_0x00b3
            r3.put((com.ansca.corona.CoronaData.Value) r1, (com.ansca.corona.CoronaData) r6)
            goto L_0x00b3
        L_0x010c:
            if (r8 != r12) goto L_0x00b3
            java.lang.String r9 = "table"
            java.lang.String r10 = r13.getName()
            boolean r9 = r9.equals(r10)
            if (r9 == 0) goto L_0x00b3
            r0 = r3
            goto L_0x0031
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.CoronaData.from(org.xmlpull.v1.XmlPullParser):com.ansca.corona.CoronaData");
    }

    public CoronaData clone() {
        try {
            return (CoronaData) super.clone();
        } catch (Exception e) {
            return null;
        }
    }

    public int describeContents() {
        return 0;
    }

    public abstract boolean pushTo(LuaState luaState);

    public abstract void writeTo(XmlSerializer xmlSerializer) throws IOException;
}
