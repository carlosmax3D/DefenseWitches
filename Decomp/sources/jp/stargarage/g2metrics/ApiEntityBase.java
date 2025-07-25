package jp.stargarage.g2metrics;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

abstract class ApiEntityBase implements Serializable {
    ApiEntityBase() {
    }

    private static <T> T cast(Object obj) {
        return obj;
    }

    /* access modifiers changed from: package-private */
    public void setDataByJsonStr(String str) {
        Object obj;
        try {
            JSONObject jSONObject = new JSONObject(str);
            for (Field field : getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    if (jSONObject.has(field.getName()) && (obj = jSONObject.get(field.getName())) != null && !Modifier.isStatic(field.getModifiers())) {
                        String name = field.getName();
                        if (obj.getClass().isArray()) {
                            JSONArray jSONArray = jSONObject.getJSONArray(name);
                            Object[] objArr = new Object[jSONArray.length()];
                            for (Integer num = 0; num.intValue() < Array.getLength(jSONArray); num = Integer.valueOf(num.intValue() + 1)) {
                                Object obj2 = Array.get(jSONArray, num.intValue());
                                if (ApiEntityBase.class.isAssignableFrom(obj.getClass().getComponentType())) {
                                    ((ApiEntityBase) obj2).setDataByJsonStr(jSONObject.getString(name));
                                }
                                objArr[num.intValue()] = obj2;
                            }
                            field.set(this, objArr);
                        } else {
                            if (ApiEntityBase.class.isAssignableFrom(field.getClass())) {
                                ((ApiEntityBase) obj).setDataByJsonStr(jSONObject.getString(name));
                            }
                            field.set(this, obj);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            for (Field field : getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object obj = field.get(this);
                if (obj != null && !Modifier.isStatic(field.getModifiers())) {
                    String name = field.getName();
                    if (ApiEntityBase.class.isAssignableFrom(obj.getClass())) {
                        jSONObject.put(name, ((ApiEntityBase) obj).toJson());
                    } else if (obj.getClass().isArray()) {
                        JSONArray jSONArray = new JSONArray();
                        for (Integer num = 0; num.intValue() < Array.getLength(obj); num = Integer.valueOf(num.intValue() + 1)) {
                            Object obj2 = Array.get(obj, num.intValue());
                            if (ApiEntityBase.class.isAssignableFrom(obj.getClass().getComponentType())) {
                                jSONArray.put(num.intValue(), ((ApiEntityBase) obj2).toJson());
                            } else {
                                jSONArray.put(num.intValue(), obj2.toString());
                            }
                        }
                        jSONObject.put(name, jSONArray);
                    } else if (field.getType() == List.class) {
                        JSONArray jSONArray2 = new JSONArray();
                        Integer num2 = 0;
                        for (Object next : (List) cast(obj)) {
                            if (ApiEntityBase.class.isAssignableFrom(next.getClass())) {
                                jSONArray2.put(num2.intValue(), ((ApiEntityBase) next).toJson());
                            } else {
                                jSONArray2.put(num2.intValue(), next.toString());
                            }
                            num2 = Integer.valueOf(num2.intValue() + 1);
                        }
                        jSONObject.put(name, jSONArray2);
                    } else if (field.getType() == HashMap.class) {
                        jSONObject.put(name, new JSONObject((HashMap) obj));
                    } else {
                        jSONObject.put(name, obj);
                    }
                }
            }
            return jSONObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
