package twitter4j;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JSONArray {
    private final ArrayList myArrayList;

    public JSONArray() {
        this.myArrayList = new ArrayList();
    }

    public JSONArray(Object obj) throws JSONException {
        this();
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                put(JSONObject.wrap(Array.get(obj, i)));
            }
            return;
        }
        throw new JSONException("JSONArray initial value should be a string or collection or array.");
    }

    public JSONArray(String str) throws JSONException {
        this(new JSONTokener(str));
    }

    public JSONArray(Collection collection) {
        this.myArrayList = new ArrayList();
        if (collection != null) {
            for (Object wrap : collection) {
                this.myArrayList.add(JSONObject.wrap(wrap));
            }
        }
    }

    public JSONArray(JSONTokener jSONTokener) throws JSONException {
        this();
        if (jSONTokener.nextClean() != '[') {
            throw jSONTokener.syntaxError("A JSONArray text must start with '['");
        } else if (jSONTokener.nextClean() != ']') {
            jSONTokener.back();
            while (true) {
                if (jSONTokener.nextClean() == ',') {
                    jSONTokener.back();
                    this.myArrayList.add(JSONObject.NULL);
                } else {
                    jSONTokener.back();
                    this.myArrayList.add(jSONTokener.nextValue());
                }
                switch (jSONTokener.nextClean()) {
                    case ',':
                    case ';':
                        if (jSONTokener.nextClean() != ']') {
                            jSONTokener.back();
                        } else {
                            return;
                        }
                    case ']':
                        return;
                    default:
                        throw jSONTokener.syntaxError("Expected a ',' or ']'");
                }
            }
        }
    }

    public Object get(int i) throws JSONException {
        Object opt = opt(i);
        if (opt != null) {
            return opt;
        }
        throw new JSONException("JSONArray[" + i + "] not found.");
    }

    public boolean getBoolean(int i) throws JSONException {
        Object obj = get(i);
        if (obj.equals(Boolean.FALSE) || ((obj instanceof String) && ((String) obj).equalsIgnoreCase("false"))) {
            return false;
        }
        if (obj.equals(Boolean.TRUE) || ((obj instanceof String) && ((String) obj).equalsIgnoreCase("true"))) {
            return true;
        }
        throw new JSONException("JSONArray[" + i + "] is not a boolean.");
    }

    public double getDouble(int i) throws JSONException {
        Object obj = get(i);
        try {
            return obj instanceof Number ? ((Number) obj).doubleValue() : Double.parseDouble((String) obj);
        } catch (Exception e) {
            throw new JSONException("JSONArray[" + i + "] is not a number.");
        }
    }

    public int getInt(int i) throws JSONException {
        Object obj = get(i);
        try {
            return obj instanceof Number ? ((Number) obj).intValue() : Integer.parseInt((String) obj);
        } catch (Exception e) {
            throw new JSONException("JSONArray[" + i + "] is not a number.");
        }
    }

    public JSONArray getJSONArray(int i) throws JSONException {
        Object obj = get(i);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        throw new JSONException("JSONArray[" + i + "] is not a JSONArray.");
    }

    public JSONObject getJSONObject(int i) throws JSONException {
        Object obj = get(i);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        throw new JSONException("JSONArray[" + i + "] is not a JSONObject.");
    }

    public long getLong(int i) throws JSONException {
        Object obj = get(i);
        try {
            return obj instanceof Number ? ((Number) obj).longValue() : Long.parseLong((String) obj);
        } catch (Exception e) {
            throw new JSONException("JSONArray[" + i + "] is not a number.");
        }
    }

    public String getString(int i) throws JSONException {
        Object obj = get(i);
        if (obj == JSONObject.NULL) {
            return null;
        }
        return obj.toString();
    }

    public boolean isNull(int i) {
        return JSONObject.NULL.equals(opt(i));
    }

    public String join(String str) throws JSONException {
        int length = length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                sb.append(str);
            }
            sb.append(JSONObject.valueToString(this.myArrayList.get(i)));
        }
        return sb.toString();
    }

    public int length() {
        return this.myArrayList.size();
    }

    public Object opt(int i) {
        if (i < 0 || i >= length()) {
            return null;
        }
        return this.myArrayList.get(i);
    }

    public JSONArray put(int i) {
        put((Object) new Integer(i));
        return this;
    }

    public JSONArray put(int i, double d) throws JSONException {
        put(i, (Object) new Double(d));
        return this;
    }

    public JSONArray put(int i, int i2) throws JSONException {
        put(i, (Object) new Integer(i2));
        return this;
    }

    public JSONArray put(int i, long j) throws JSONException {
        put(i, (Object) new Long(j));
        return this;
    }

    public JSONArray put(int i, Object obj) throws JSONException {
        JSONObject.testValidity(obj);
        if (i < 0) {
            throw new JSONException("JSONArray[" + i + "] not found.");
        }
        if (i < length()) {
            this.myArrayList.set(i, obj);
        } else {
            while (i != length()) {
                put(JSONObject.NULL);
            }
            put(obj);
        }
        return this;
    }

    public JSONArray put(int i, Collection collection) throws JSONException {
        put(i, (Object) new JSONArray(collection));
        return this;
    }

    public JSONArray put(int i, Map map) throws JSONException {
        put(i, (Object) new JSONObject(map));
        return this;
    }

    public JSONArray put(int i, boolean z) throws JSONException {
        put(i, (Object) z ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public JSONArray put(long j) {
        put((Object) new Long(j));
        return this;
    }

    public JSONArray put(Object obj) {
        this.myArrayList.add(obj);
        return this;
    }

    public JSONArray put(Collection collection) {
        put((Object) new JSONArray(collection));
        return this;
    }

    public JSONArray put(Map map) {
        put((Object) new JSONObject(map));
        return this;
    }

    public JSONArray put(boolean z) {
        put((Object) z ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public String toString() {
        try {
            return '[' + join(",") + ']';
        } catch (Exception e) {
            return null;
        }
    }

    public String toString(int i) throws JSONException {
        return toString(i, 0);
    }

    /* access modifiers changed from: package-private */
    public String toString(int i, int i2) throws JSONException {
        int length = length();
        if (length == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        if (length == 1) {
            sb.append(JSONObject.valueToString(this.myArrayList.get(0), i, i2));
        } else {
            int i3 = i2 + i;
            sb.append(10);
            for (int i4 = 0; i4 < length; i4++) {
                if (i4 > 0) {
                    sb.append(",\n");
                }
                for (int i5 = 0; i5 < i3; i5++) {
                    sb.append(' ');
                }
                sb.append(JSONObject.valueToString(this.myArrayList.get(i4), i, i3));
            }
            sb.append(10);
            for (int i6 = 0; i6 < i2; i6++) {
                sb.append(' ');
            }
        }
        sb.append(']');
        return sb.toString();
    }

    public Writer write(Writer writer) throws JSONException {
        boolean z = false;
        try {
            int length = length();
            writer.write(91);
            for (int i = 0; i < length; i++) {
                if (z) {
                    writer.write(44);
                }
                Object obj = this.myArrayList.get(i);
                if (obj instanceof JSONObject) {
                    ((JSONObject) obj).write(writer);
                } else if (obj instanceof JSONArray) {
                    ((JSONArray) obj).write(writer);
                } else {
                    writer.write(JSONObject.valueToString(obj));
                }
                z = true;
            }
            writer.write(93);
            return writer;
        } catch (IOException e) {
            throw new JSONException((Throwable) e);
        }
    }
}
