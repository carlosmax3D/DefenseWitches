package jp.stargarage.g2metrics;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
abstract class ApiEntityBase implements Serializable {
    ApiEntityBase() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <T> T cast(Object obj) {
        return (T) obj;
    }

    void setDataByJsonStr(String str) throws JSONException, IllegalAccessException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
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
                            for (Integer numValueOf = 0; numValueOf.intValue() < Array.getLength(jSONArray); numValueOf = Integer.valueOf(numValueOf.intValue() + 1)) {
                                Object obj2 = Array.get(jSONArray, numValueOf.intValue());
                                if (ApiEntityBase.class.isAssignableFrom(obj.getClass().getComponentType())) {
                                    ((ApiEntityBase) obj2).setDataByJsonStr(jSONObject.getString(name));
                                }
                                objArr[numValueOf.intValue()] = obj2;
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

    JSONObject toJson() throws IllegalAccessException, JSONException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
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
                        for (Integer numValueOf = 0; numValueOf.intValue() < Array.getLength(obj); numValueOf = Integer.valueOf(numValueOf.intValue() + 1)) {
                            Object obj2 = Array.get(obj, numValueOf.intValue());
                            if (ApiEntityBase.class.isAssignableFrom(obj.getClass().getComponentType())) {
                                jSONArray.put(numValueOf.intValue(), ((ApiEntityBase) obj2).toJson());
                            } else {
                                jSONArray.put(numValueOf.intValue(), obj2.toString());
                            }
                        }
                        jSONObject.put(name, jSONArray);
                    } else if (field.getType() == List.class) {
                        List list = (List) cast(obj);
                        JSONArray jSONArray2 = new JSONArray();
                        Integer numValueOf2 = 0;
                        for (Object obj3 : list) {
                            if (ApiEntityBase.class.isAssignableFrom(obj3.getClass())) {
                                jSONArray2.put(numValueOf2.intValue(), ((ApiEntityBase) obj3).toJson());
                            } else {
                                jSONArray2.put(numValueOf2.intValue(), obj3.toString());
                            }
                            numValueOf2 = Integer.valueOf(numValueOf2.intValue() + 1);
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
