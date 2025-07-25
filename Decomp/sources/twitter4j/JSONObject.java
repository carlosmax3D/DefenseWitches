package twitter4j;

import com.facebook.appevents.AppEventsConstants;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeSet;
import jp.stargarage.g2metrics.BuildConfig;
import twitter4j.MediaEntity;

public class JSONObject {
    public static final Object NULL = new Null();
    private final Map map;

    private static final class Null {
        private Null() {
        }

        /* access modifiers changed from: protected */
        public final Object clone() {
            return this;
        }

        public boolean equals(Object obj) {
            return obj == null || obj == this;
        }

        public String toString() {
            return "null";
        }
    }

    public JSONObject() {
        this.map = new HashMap();
    }

    public JSONObject(Object obj) {
        this();
        populateMap(obj);
    }

    public JSONObject(Object obj, String[] strArr) {
        this();
        Class<?> cls = obj.getClass();
        for (String str : strArr) {
            try {
                putOpt(str, cls.getField(str).get(obj));
            } catch (Exception e) {
            }
        }
    }

    public JSONObject(String str) throws JSONException {
        this(new JSONTokener(str));
    }

    public JSONObject(String str, Locale locale) throws JSONException {
        this();
        ResourceBundle bundle = ResourceBundle.getBundle(str, locale, Thread.currentThread().getContextClassLoader());
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String nextElement = keys.nextElement();
            if (nextElement instanceof String) {
                String[] split = nextElement.split("\\.");
                int length = split.length - 1;
                JSONObject jSONObject = this;
                for (int i = 0; i < length; i++) {
                    String str2 = split[i];
                    Object opt = jSONObject.opt(str2);
                    JSONObject jSONObject2 = opt instanceof JSONObject ? (JSONObject) opt : null;
                    if (jSONObject2 == null) {
                        jSONObject2 = new JSONObject();
                        jSONObject.put(str2, (Object) jSONObject2);
                    }
                    jSONObject = jSONObject2;
                }
                jSONObject.put(split[length], (Object) bundle.getString(nextElement));
            }
        }
    }

    public JSONObject(Map map2) {
        this.map = new HashMap();
        if (map2 != null) {
            for (Map.Entry entry : map2.entrySet()) {
                Object value = entry.getValue();
                if (value != null) {
                    this.map.put(entry.getKey(), wrap(value));
                }
            }
        }
    }

    public JSONObject(JSONObject jSONObject, String[] strArr) {
        this();
        for (String str : strArr) {
            try {
                putOnce(str, jSONObject.opt(str));
            } catch (Exception e) {
            }
        }
    }

    public JSONObject(JSONTokener jSONTokener) throws JSONException {
        this();
        if (jSONTokener.nextClean() != '{') {
            throw jSONTokener.syntaxError("A JSONObject text must begin with '{' found:" + jSONTokener.nextClean());
        }
        while (true) {
            switch (jSONTokener.nextClean()) {
                case 0:
                    throw jSONTokener.syntaxError("A JSONObject text must end with '}'");
                case '}':
                    return;
                default:
                    jSONTokener.back();
                    String obj = jSONTokener.nextValue().toString();
                    char nextClean = jSONTokener.nextClean();
                    if (nextClean == '=') {
                        if (jSONTokener.next() != '>') {
                            jSONTokener.back();
                        }
                    } else if (nextClean != ':') {
                        throw jSONTokener.syntaxError("Expected a ':' after a key");
                    }
                    putOnce(obj, jSONTokener.nextValue());
                    switch (jSONTokener.nextClean()) {
                        case ',':
                        case ';':
                            if (jSONTokener.nextClean() != '}') {
                                jSONTokener.back();
                            } else {
                                return;
                            }
                        case '}':
                            return;
                        default:
                            throw jSONTokener.syntaxError("Expected a ',' or '}'");
                    }
            }
        }
    }

    public static String numberToString(Number number) throws JSONException {
        if (number == null) {
            throw new JSONException("Null pointer");
        }
        testValidity(number);
        String obj = number.toString();
        if (obj.indexOf(46) <= 0 || obj.indexOf(MediaEntity.Size.CROP) >= 0 || obj.indexOf(69) >= 0) {
            return obj;
        }
        while (obj.endsWith(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
            obj = obj.substring(0, obj.length() - 1);
        }
        return obj.endsWith(".") ? obj.substring(0, obj.length() - 1) : obj;
    }

    private void populateMap(Object obj) {
        Class<?> cls = obj.getClass();
        for (Method method : cls.getClassLoader() != null ? cls.getMethods() : cls.getDeclaredMethods()) {
            try {
                if (Modifier.isPublic(method.getModifiers())) {
                    String name = method.getName();
                    String str = BuildConfig.FLAVOR;
                    if (name.startsWith("get")) {
                        str = (name.equals("getClass") || name.equals("getDeclaringClass")) ? BuildConfig.FLAVOR : name.substring(3);
                    } else if (name.startsWith("is")) {
                        str = name.substring(2);
                    }
                    if (str.length() > 0 && Character.isUpperCase(str.charAt(0)) && method.getParameterTypes().length == 0) {
                        if (str.length() == 1) {
                            str = str.toLowerCase();
                        } else if (!Character.isUpperCase(str.charAt(1))) {
                            str = str.substring(0, 1).toLowerCase() + str.substring(1);
                        }
                        Object invoke = method.invoke(obj, (Object[]) null);
                        if (invoke != null) {
                            this.map.put(str, wrap(invoke));
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public static String quote(String str) {
        if (str == null || str.length() == 0) {
            return "\"\"";
        }
        char c = 0;
        int length = str.length();
        StringBuilder sb = new StringBuilder(length + 4);
        sb.append('\"');
        for (int i = 0; i < length; i++) {
            char c2 = c;
            c = str.charAt(i);
            switch (c) {
                case 8:
                    sb.append("\\b");
                    break;
                case 9:
                    sb.append("\\t");
                    break;
                case 10:
                    sb.append("\\n");
                    break;
                case 12:
                    sb.append("\\f");
                    break;
                case 13:
                    sb.append("\\r");
                    break;
                case '\"':
                case '\\':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '/':
                    if (c2 == '<') {
                        sb.append('\\');
                    }
                    sb.append(c);
                    break;
                default:
                    if (c >= ' ' && ((c < 128 || c >= 160) && (c < 8192 || c >= 8448))) {
                        sb.append(c);
                        break;
                    } else {
                        String str2 = "000" + Integer.toHexString(c);
                        sb.append("\\u").append(str2.substring(str2.length() - 4));
                        break;
                    }
            }
        }
        sb.append('\"');
        return sb.toString();
    }

    public static Object stringToValue(String str) {
        if (str.equals(BuildConfig.FLAVOR)) {
            return str;
        }
        if (str.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (str.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        if (str.equalsIgnoreCase("null")) {
            return NULL;
        }
        char charAt = str.charAt(0);
        if ((charAt < '0' || charAt > '9') && charAt != '.' && charAt != '-' && charAt != '+') {
            return str;
        }
        if (charAt == '0' && str.length() > 2 && (str.charAt(1) == 'x' || str.charAt(1) == 'X')) {
            try {
                return Integer.valueOf(Integer.parseInt(str.substring(2), 16));
            } catch (Exception e) {
            }
        }
        try {
            if (str.indexOf(46) > -1 || str.indexOf(MediaEntity.Size.CROP) > -1 || str.indexOf(69) > -1) {
                return Double.valueOf(str);
            }
            Long l = new Long(str);
            return l.longValue() == ((long) l.intValue()) ? Integer.valueOf(l.intValue()) : l;
        } catch (Exception e2) {
            return str;
        }
    }

    public static void testValidity(Object obj) throws JSONException {
        if (obj == null) {
            return;
        }
        if (obj instanceof Double) {
            if (((Double) obj).isInfinite() || ((Double) obj).isNaN()) {
                throw new JSONException("JSON does not allow non-finite numbers.");
            }
        } else if (!(obj instanceof Float)) {
        } else {
            if (((Float) obj).isInfinite() || ((Float) obj).isNaN()) {
                throw new JSONException("JSON does not allow non-finite numbers.");
            }
        }
    }

    public static String valueToString(Object obj) throws JSONException {
        return (obj == null || obj.equals((Object) null)) ? "null" : obj instanceof Number ? numberToString((Number) obj) : ((obj instanceof Boolean) || (obj instanceof JSONObject) || (obj instanceof JSONArray)) ? obj.toString() : obj instanceof Map ? new JSONObject((Map) obj).toString() : obj instanceof Collection ? new JSONArray((Collection) obj).toString() : obj.getClass().isArray() ? new JSONArray(obj).toString() : quote(obj.toString());
    }

    static String valueToString(Object obj, int i, int i2) throws JSONException {
        return (obj == null || obj.equals((Object) null)) ? "null" : obj instanceof Number ? numberToString((Number) obj) : obj instanceof Boolean ? obj.toString() : obj instanceof JSONObject ? ((JSONObject) obj).toString(i, i2) : obj instanceof JSONArray ? ((JSONArray) obj).toString(i, i2) : obj instanceof Map ? new JSONObject((Map) obj).toString(i, i2) : obj instanceof Collection ? new JSONArray((Collection) obj).toString(i, i2) : obj.getClass().isArray() ? new JSONArray(obj).toString(i, i2) : quote(obj.toString());
    }

    public static Object wrap(Object obj) {
        if (obj == null) {
            try {
                return NULL;
            } catch (Exception e) {
                return null;
            }
        } else if ((obj instanceof JSONObject) || (obj instanceof JSONArray) || NULL.equals(obj) || (obj instanceof Byte) || (obj instanceof Character) || (obj instanceof Short) || (obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Boolean) || (obj instanceof Float) || (obj instanceof Double) || (obj instanceof String)) {
            return obj;
        } else {
            if (obj instanceof Collection) {
                return new JSONArray((Collection) obj);
            }
            if (obj.getClass().isArray()) {
                return new JSONArray(obj);
            }
            if (obj instanceof Map) {
                return new JSONObject((Map) obj);
            }
            Package packageR = obj.getClass().getPackage();
            String name = packageR != null ? packageR.getName() : BuildConfig.FLAVOR;
            return (name.startsWith("java.") || name.startsWith("javax.") || obj.getClass().getClassLoader() == null) ? obj.toString() : new JSONObject(obj);
        }
    }

    public JSONObject append(String str, Object obj) throws JSONException {
        testValidity(obj);
        Object opt = opt(str);
        if (opt == null) {
            put(str, (Object) new JSONArray().put(obj));
        } else if (opt instanceof JSONArray) {
            put(str, (Object) ((JSONArray) opt).put(obj));
        } else {
            throw new JSONException("JSONObject[" + str + "] is not a JSONArray.");
        }
        return this;
    }

    public Object get(String str) throws JSONException {
        if (str == null) {
            throw new JSONException("Null key.");
        }
        Object opt = opt(str);
        if (opt != null) {
            return opt;
        }
        throw new JSONException("JSONObject[" + quote(str) + "] not found.");
    }

    public boolean getBoolean(String str) throws JSONException {
        Object obj = get(str);
        if (obj.equals(Boolean.FALSE) || ((obj instanceof String) && ((String) obj).equalsIgnoreCase("false"))) {
            return false;
        }
        if (obj.equals(Boolean.TRUE) || ((obj instanceof String) && ((String) obj).equalsIgnoreCase("true"))) {
            return true;
        }
        throw new JSONException("JSONObject[" + quote(str) + "] is not a Boolean.");
    }

    public int getInt(String str) throws JSONException {
        Object obj = get(str);
        try {
            return obj instanceof Number ? ((Number) obj).intValue() : Integer.parseInt((String) obj);
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(str) + "] is not an int.");
        }
    }

    public JSONArray getJSONArray(String str) throws JSONException {
        Object obj = get(str);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        throw new JSONException("JSONObject[" + quote(str) + "] is not a JSONArray.");
    }

    public JSONObject getJSONObject(String str) throws JSONException {
        Object obj = get(str);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        throw new JSONException("JSONObject[" + quote(str) + "] is not a JSONObject.");
    }

    public long getLong(String str) throws JSONException {
        Object obj = get(str);
        try {
            return obj instanceof Number ? ((Number) obj).longValue() : Long.parseLong((String) obj);
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(str) + "] is not a long.");
        }
    }

    public String getString(String str) throws JSONException {
        Object obj = get(str);
        if (obj == NULL) {
            return null;
        }
        return obj.toString();
    }

    public boolean has(String str) {
        return this.map.containsKey(str);
    }

    public boolean isNull(String str) {
        return NULL.equals(opt(str));
    }

    public Iterator keys() {
        return this.map.keySet().iterator();
    }

    public int length() {
        return this.map.size();
    }

    public JSONArray names() {
        JSONArray jSONArray = new JSONArray();
        Iterator keys = keys();
        while (keys.hasNext()) {
            jSONArray.put(keys.next());
        }
        if (jSONArray.length() == 0) {
            return null;
        }
        return jSONArray;
    }

    public Object opt(String str) {
        if (str == null) {
            return null;
        }
        return this.map.get(str);
    }

    public JSONObject put(String str, double d) throws JSONException {
        put(str, (Object) new Double(d));
        return this;
    }

    public JSONObject put(String str, int i) throws JSONException {
        put(str, (Object) new Integer(i));
        return this;
    }

    public JSONObject put(String str, long j) throws JSONException {
        put(str, (Object) new Long(j));
        return this;
    }

    public JSONObject put(String str, Object obj) throws JSONException {
        if (str == null) {
            throw new JSONException("Null key.");
        }
        if (obj != null) {
            testValidity(obj);
            this.map.put(str, obj);
        } else {
            remove(str);
        }
        return this;
    }

    public JSONObject put(String str, Collection collection) throws JSONException {
        put(str, (Object) new JSONArray(collection));
        return this;
    }

    public JSONObject put(String str, Map map2) throws JSONException {
        put(str, (Object) new JSONObject(map2));
        return this;
    }

    public JSONObject put(String str, boolean z) throws JSONException {
        put(str, (Object) z ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public JSONObject putOnce(String str, Object obj) throws JSONException {
        if (!(str == null || obj == null)) {
            if (opt(str) != null) {
                throw new JSONException("Duplicate key \"" + str + "\"");
            }
            put(str, obj);
        }
        return this;
    }

    public JSONObject putOpt(String str, Object obj) throws JSONException {
        if (!(str == null || obj == null)) {
            put(str, obj);
        }
        return this;
    }

    public Object remove(String str) {
        return this.map.remove(str);
    }

    public Iterator sortedKeys() {
        return new TreeSet(this.map.keySet()).iterator();
    }

    public String toString() {
        try {
            Iterator keys = keys();
            StringBuilder sb = new StringBuilder("{");
            while (keys.hasNext()) {
                if (sb.length() > 1) {
                    sb.append(',');
                }
                Object next = keys.next();
                sb.append(quote(next.toString()));
                sb.append(':');
                sb.append(valueToString(this.map.get(next)));
            }
            sb.append('}');
            return sb.toString();
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
            return "{}";
        }
        Iterator sortedKeys = sortedKeys();
        int i3 = i2 + i;
        StringBuilder sb = new StringBuilder("{");
        if (length == 1) {
            Object next = sortedKeys.next();
            sb.append(quote(next.toString()));
            sb.append(": ");
            sb.append(valueToString(this.map.get(next), i, i2));
        } else {
            while (sortedKeys.hasNext()) {
                Object next2 = sortedKeys.next();
                if (sb.length() > 1) {
                    sb.append(",\n");
                } else {
                    sb.append(10);
                }
                for (int i4 = 0; i4 < i3; i4++) {
                    sb.append(' ');
                }
                sb.append(quote(next2.toString()));
                sb.append(": ");
                sb.append(valueToString(this.map.get(next2), i, i3));
            }
            if (sb.length() > 1) {
                sb.append(10);
                for (int i5 = 0; i5 < i2; i5++) {
                    sb.append(' ');
                }
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public Writer write(Writer writer) throws JSONException {
        boolean z = false;
        try {
            Iterator keys = keys();
            writer.write(123);
            while (keys.hasNext()) {
                if (z) {
                    writer.write(44);
                }
                Object next = keys.next();
                writer.write(quote(next.toString()));
                writer.write(58);
                Object obj = this.map.get(next);
                if (obj instanceof JSONObject) {
                    ((JSONObject) obj).write(writer);
                } else if (obj instanceof JSONArray) {
                    ((JSONArray) obj).write(writer);
                } else {
                    writer.write(valueToString(obj));
                }
                z = true;
            }
            writer.write(125);
            return writer;
        } catch (IOException e) {
            throw new JSONException((Throwable) e);
        }
    }
}
