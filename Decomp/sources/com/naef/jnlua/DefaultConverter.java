package com.naef.jnlua;

import com.naef.jnlua.util.AbstractTableList;
import com.naef.jnlua.util.AbstractTableMap;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultConverter implements Converter {
    private static final Map<Class<?>, Integer> BOOLEAN_DISTANCE_MAP = new HashMap();
    private static final Map<Class<?>, Integer> FUNCTION_DISTANCE_MAP = new HashMap();
    private static final DefaultConverter INSTANCE = new DefaultConverter();
    private static final Map<Class<?>, JavaObjectConverter<?>> JAVA_OBJECT_CONVERTERS = new HashMap();
    private static final Map<Class<?>, LuaValueConverter<?>> LUA_VALUE_CONVERTERS = new HashMap();
    private static final Map<Class<?>, Integer> NUMBER_DISTANCE_MAP = new HashMap();
    private static final Map<Class<?>, Integer> STRING_DISTANCE_MAP = new HashMap();

    private interface JavaObjectConverter<T> {
        void convert(LuaState luaState, T t);
    }

    private interface LuaValueConverter<T> {
        T convert(LuaState luaState, int i);
    }

    static {
        BOOLEAN_DISTANCE_MAP.put(Boolean.class, new Integer(1));
        BOOLEAN_DISTANCE_MAP.put(Boolean.TYPE, new Integer(1));
        BOOLEAN_DISTANCE_MAP.put(Object.class, new Integer(2));
        NUMBER_DISTANCE_MAP.put(Byte.class, new Integer(1));
        NUMBER_DISTANCE_MAP.put(Byte.TYPE, new Integer(1));
        NUMBER_DISTANCE_MAP.put(Short.class, new Integer(1));
        NUMBER_DISTANCE_MAP.put(Short.TYPE, new Integer(1));
        NUMBER_DISTANCE_MAP.put(Integer.class, new Integer(1));
        NUMBER_DISTANCE_MAP.put(Integer.TYPE, new Integer(1));
        NUMBER_DISTANCE_MAP.put(Long.class, new Integer(1));
        NUMBER_DISTANCE_MAP.put(Long.TYPE, new Integer(1));
        NUMBER_DISTANCE_MAP.put(Float.class, new Integer(1));
        NUMBER_DISTANCE_MAP.put(Float.TYPE, new Integer(1));
        NUMBER_DISTANCE_MAP.put(Double.class, new Integer(1));
        NUMBER_DISTANCE_MAP.put(Double.TYPE, new Integer(1));
        NUMBER_DISTANCE_MAP.put(BigInteger.class, new Integer(1));
        NUMBER_DISTANCE_MAP.put(BigDecimal.class, new Integer(1));
        NUMBER_DISTANCE_MAP.put(Character.class, new Integer(1));
        NUMBER_DISTANCE_MAP.put(Character.TYPE, new Integer(1));
        NUMBER_DISTANCE_MAP.put(Object.class, new Integer(2));
        NUMBER_DISTANCE_MAP.put(String.class, new Integer(3));
        STRING_DISTANCE_MAP.put(String.class, new Integer(1));
        STRING_DISTANCE_MAP.put(Object.class, new Integer(2));
        STRING_DISTANCE_MAP.put(Byte.class, new Integer(3));
        STRING_DISTANCE_MAP.put(Byte.TYPE, new Integer(3));
        STRING_DISTANCE_MAP.put(Short.class, new Integer(3));
        STRING_DISTANCE_MAP.put(Short.TYPE, new Integer(3));
        STRING_DISTANCE_MAP.put(Integer.class, new Integer(3));
        STRING_DISTANCE_MAP.put(Integer.TYPE, new Integer(3));
        STRING_DISTANCE_MAP.put(Long.class, new Integer(3));
        STRING_DISTANCE_MAP.put(Long.TYPE, new Integer(3));
        STRING_DISTANCE_MAP.put(Float.class, new Integer(3));
        STRING_DISTANCE_MAP.put(Float.TYPE, new Integer(3));
        STRING_DISTANCE_MAP.put(Double.class, new Integer(3));
        STRING_DISTANCE_MAP.put(Double.TYPE, new Integer(3));
        STRING_DISTANCE_MAP.put(BigInteger.class, new Integer(3));
        STRING_DISTANCE_MAP.put(BigDecimal.class, new Integer(3));
        STRING_DISTANCE_MAP.put(Character.class, new Integer(3));
        STRING_DISTANCE_MAP.put(Character.TYPE, new Integer(3));
        FUNCTION_DISTANCE_MAP.put(JavaFunction.class, new Integer(1));
        FUNCTION_DISTANCE_MAP.put(Object.class, new Integer(2));
        AnonymousClass1 r0 = new LuaValueConverter<Boolean>() {
            public Boolean convert(LuaState luaState, int i) {
                return Boolean.valueOf(luaState.toBoolean(i));
            }
        };
        LUA_VALUE_CONVERTERS.put(Boolean.class, r0);
        LUA_VALUE_CONVERTERS.put(Boolean.TYPE, r0);
        AnonymousClass2 r02 = new LuaValueConverter<Byte>() {
            public Byte convert(LuaState luaState, int i) {
                return Byte.valueOf((byte) luaState.toInteger(i));
            }
        };
        LUA_VALUE_CONVERTERS.put(Byte.class, r02);
        LUA_VALUE_CONVERTERS.put(Byte.TYPE, r02);
        AnonymousClass3 r03 = new LuaValueConverter<Short>() {
            public Short convert(LuaState luaState, int i) {
                return Short.valueOf((short) luaState.toInteger(i));
            }
        };
        LUA_VALUE_CONVERTERS.put(Short.class, r03);
        LUA_VALUE_CONVERTERS.put(Short.TYPE, r03);
        AnonymousClass4 r04 = new LuaValueConverter<Integer>() {
            public Integer convert(LuaState luaState, int i) {
                return Integer.valueOf(luaState.toInteger(i));
            }
        };
        LUA_VALUE_CONVERTERS.put(Integer.class, r04);
        LUA_VALUE_CONVERTERS.put(Integer.TYPE, r04);
        AnonymousClass5 r05 = new LuaValueConverter<Long>() {
            public Long convert(LuaState luaState, int i) {
                return Long.valueOf((long) luaState.toNumber(i));
            }
        };
        LUA_VALUE_CONVERTERS.put(Long.class, r05);
        LUA_VALUE_CONVERTERS.put(Long.TYPE, r05);
        AnonymousClass6 r06 = new LuaValueConverter<Float>() {
            public Float convert(LuaState luaState, int i) {
                return Float.valueOf((float) luaState.toNumber(i));
            }
        };
        LUA_VALUE_CONVERTERS.put(Float.class, r06);
        LUA_VALUE_CONVERTERS.put(Float.TYPE, r06);
        AnonymousClass7 r07 = new LuaValueConverter<Double>() {
            public Double convert(LuaState luaState, int i) {
                return Double.valueOf(luaState.toNumber(i));
            }
        };
        LUA_VALUE_CONVERTERS.put(Double.class, r07);
        LUA_VALUE_CONVERTERS.put(Double.TYPE, r07);
        LUA_VALUE_CONVERTERS.put(BigInteger.class, new LuaValueConverter<BigInteger>() {
            public BigInteger convert(LuaState luaState, int i) {
                return BigDecimal.valueOf(luaState.toNumber(i)).setScale(0, 6).toBigInteger();
            }
        });
        LUA_VALUE_CONVERTERS.put(BigDecimal.class, new LuaValueConverter<BigDecimal>() {
            public BigDecimal convert(LuaState luaState, int i) {
                return BigDecimal.valueOf(luaState.toNumber(i));
            }
        });
        AnonymousClass10 r08 = new LuaValueConverter<Character>() {
            public Character convert(LuaState luaState, int i) {
                return Character.valueOf((char) luaState.toInteger(i));
            }
        };
        LUA_VALUE_CONVERTERS.put(Character.class, r08);
        LUA_VALUE_CONVERTERS.put(Character.TYPE, r08);
        LUA_VALUE_CONVERTERS.put(String.class, new LuaValueConverter<String>() {
            public String convert(LuaState luaState, int i) {
                return luaState.toString(i);
            }
        });
        AnonymousClass12 r09 = new JavaObjectConverter<Boolean>() {
            public void convert(LuaState luaState, Boolean bool) {
                luaState.pushBoolean(bool.booleanValue());
            }
        };
        JAVA_OBJECT_CONVERTERS.put(Boolean.class, r09);
        JAVA_OBJECT_CONVERTERS.put(Boolean.TYPE, r09);
        AnonymousClass13 r010 = new JavaObjectConverter<Number>() {
            public void convert(LuaState luaState, Number number) {
                luaState.pushNumber(number.doubleValue());
            }
        };
        JAVA_OBJECT_CONVERTERS.put(Byte.class, r010);
        JAVA_OBJECT_CONVERTERS.put(Byte.TYPE, r010);
        JAVA_OBJECT_CONVERTERS.put(Short.class, r010);
        JAVA_OBJECT_CONVERTERS.put(Short.TYPE, r010);
        JAVA_OBJECT_CONVERTERS.put(Integer.class, r010);
        JAVA_OBJECT_CONVERTERS.put(Integer.TYPE, r010);
        JAVA_OBJECT_CONVERTERS.put(Long.class, r010);
        JAVA_OBJECT_CONVERTERS.put(Long.TYPE, r010);
        JAVA_OBJECT_CONVERTERS.put(Float.class, r010);
        JAVA_OBJECT_CONVERTERS.put(Float.TYPE, r010);
        JAVA_OBJECT_CONVERTERS.put(Double.class, r010);
        JAVA_OBJECT_CONVERTERS.put(Double.TYPE, r010);
        JAVA_OBJECT_CONVERTERS.put(BigInteger.class, r010);
        JAVA_OBJECT_CONVERTERS.put(BigDecimal.class, r010);
        AnonymousClass14 r011 = new JavaObjectConverter<Character>() {
            public void convert(LuaState luaState, Character ch) {
                luaState.pushInteger(ch.charValue());
            }
        };
        JAVA_OBJECT_CONVERTERS.put(Character.class, r011);
        JAVA_OBJECT_CONVERTERS.put(Character.TYPE, r011);
        JAVA_OBJECT_CONVERTERS.put(String.class, new JavaObjectConverter<String>() {
            public void convert(LuaState luaState, String str) {
                luaState.pushString(str);
            }
        });
    }

    private DefaultConverter() {
    }

    public static DefaultConverter getInstance() {
        return INSTANCE;
    }

    public void convertJavaObject(LuaState luaState, Object obj) {
        if (obj == null) {
            luaState.pushNil();
            return;
        }
        JavaObjectConverter javaObjectConverter = JAVA_OBJECT_CONVERTERS.get(obj.getClass());
        if (javaObjectConverter != null) {
            javaObjectConverter.convert(luaState, obj);
        } else if (obj instanceof JavaFunction) {
            luaState.pushJavaFunction((JavaFunction) obj);
        } else if (obj instanceof LuaValueProxy) {
            LuaValueProxy luaValueProxy = (LuaValueProxy) obj;
            if (!luaValueProxy.getLuaState().equals(luaState)) {
                throw new IllegalArgumentException("Lua value proxy is from a different Lua state");
            }
            luaValueProxy.pushValue();
        } else {
            luaState.pushJavaObjectRaw(obj);
        }
    }

    /* JADX INFO: finally extract failed */
    public <T> T convertLuaValue(LuaState luaState, int i, Class<T> cls) {
        LuaType type = luaState.type(i);
        if (type == null) {
            throw new IllegalArgumentException("undefined index: " + i);
        } else if (cls == Void.TYPE) {
            throw new ClassCastException(String.format("cannot convert %s to %s", new Object[]{luaState.typeName(i), cls.getCanonicalName()}));
        } else if (cls == LuaValueProxy.class) {
            return luaState.getProxy(i);
        } else {
            switch (type) {
                case NIL:
                    return null;
                case BOOLEAN:
                    LuaValueConverter luaValueConverter = LUA_VALUE_CONVERTERS.get(cls);
                    if (luaValueConverter != null) {
                        return luaValueConverter.convert(luaState, i);
                    }
                    if (cls == Object.class) {
                        return Boolean.valueOf(luaState.toBoolean(i));
                    }
                    break;
                case NUMBER:
                    LuaValueConverter luaValueConverter2 = LUA_VALUE_CONVERTERS.get(cls);
                    if (luaValueConverter2 != null) {
                        return luaValueConverter2.convert(luaState, i);
                    }
                    if (cls == Object.class) {
                        return Double.valueOf(luaState.toNumber(i));
                    }
                    break;
                case STRING:
                    LuaValueConverter luaValueConverter3 = LUA_VALUE_CONVERTERS.get(cls);
                    if (luaValueConverter3 != null) {
                        return luaValueConverter3.convert(luaState, i);
                    }
                    if (cls == Object.class) {
                        return luaState.toString(i);
                    }
                    break;
                case TABLE:
                    if (cls == Map.class || cls == Object.class) {
                        final LuaValueProxy proxy = luaState.getProxy(i);
                        return new AbstractTableMap<Object>() {
                            /* access modifiers changed from: protected */
                            public Object convertKey(int i) {
                                return getLuaState().toJavaObject(i, Object.class);
                            }

                            public LuaState getLuaState() {
                                return proxy.getLuaState();
                            }

                            public void pushValue() {
                                proxy.pushValue();
                            }
                        };
                    } else if (cls == List.class) {
                        final LuaValueProxy proxy2 = luaState.getProxy(i);
                        return new AbstractTableList() {
                            public LuaState getLuaState() {
                                return proxy2.getLuaState();
                            }

                            public void pushValue() {
                                proxy2.pushValue();
                            }
                        };
                    } else if (cls.isArray()) {
                        int length = luaState.length(i);
                        Class<?> componentType = cls.getComponentType();
                        T newInstance = Array.newInstance(cls.getComponentType(), length);
                        int i2 = 0;
                        while (i2 < length) {
                            luaState.rawGet(i, i2 + 1);
                            try {
                                Array.set(newInstance, i2, convertLuaValue(luaState, -1, componentType));
                                luaState.pop(1);
                                i2++;
                            } catch (Throwable th) {
                                luaState.pop(1);
                                throw th;
                            }
                        }
                        return newInstance;
                    }
                    break;
                case FUNCTION:
                    if (luaState.isJavaFunction(i) && (cls == JavaFunction.class || cls == Object.class)) {
                        return luaState.toJavaFunction(i);
                    }
                case USERDATA:
                    T javaObjectRaw = luaState.toJavaObjectRaw(i);
                    if (javaObjectRaw != null) {
                        if (!(javaObjectRaw instanceof TypedJavaObject)) {
                            return javaObjectRaw;
                        }
                        T t = (TypedJavaObject) javaObjectRaw;
                        return (!t.isStrong() || !cls.isAssignableFrom(t.getClass())) ? ((TypedJavaObject) javaObjectRaw).getObject() : t;
                    }
                    break;
            }
            if (cls == Object.class) {
                return luaState.getProxy(i);
            }
            throw new ClassCastException(String.format("cannot convert %s to %s", new Object[]{luaState.typeName(i), cls.getCanonicalName()}));
        }
    }

    public int getTypeDistance(LuaState luaState, int i, Class<?> cls) {
        Class<?> cls2;
        Integer num;
        LuaType type = luaState.type(i);
        if (type == null) {
            return Integer.MAX_VALUE;
        }
        if (cls == Void.TYPE) {
            return Integer.MAX_VALUE;
        }
        if (cls == LuaValueProxy.class) {
            return 0;
        }
        switch (type) {
            case NIL:
                return 1;
            case BOOLEAN:
                Integer num2 = BOOLEAN_DISTANCE_MAP.get(cls);
                if (num2 != null) {
                    return num2.intValue();
                }
                break;
            case NUMBER:
                Integer num3 = NUMBER_DISTANCE_MAP.get(cls);
                if (num3 != null) {
                    return num3.intValue();
                }
                break;
            case STRING:
                Integer num4 = STRING_DISTANCE_MAP.get(cls);
                if (num4 != null) {
                    return num4.intValue();
                }
                break;
            case TABLE:
                if (cls == Map.class || cls == List.class || cls.isArray()) {
                    return 1;
                }
                if (cls == Object.class) {
                    return 2;
                }
                break;
            case FUNCTION:
                if (luaState.isJavaFunction(i) && (num = FUNCTION_DISTANCE_MAP.get(cls)) != null) {
                    return num.intValue();
                }
            case USERDATA:
                Object javaObjectRaw = luaState.toJavaObjectRaw(i);
                if (javaObjectRaw != null) {
                    if (javaObjectRaw instanceof TypedJavaObject) {
                        TypedJavaObject typedJavaObject = (TypedJavaObject) javaObjectRaw;
                        if (typedJavaObject.isStrong() && cls.isAssignableFrom(typedJavaObject.getClass())) {
                            return 1;
                        }
                        cls2 = typedJavaObject.getType();
                    } else {
                        cls2 = javaObjectRaw.getClass();
                    }
                    if (cls.isAssignableFrom(cls2)) {
                        return 1;
                    }
                }
                break;
        }
        return cls == Object.class ? 2147483646 : Integer.MAX_VALUE;
    }
}
