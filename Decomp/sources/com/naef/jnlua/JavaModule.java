package com.naef.jnlua;

import com.naef.jnlua.JavaReflector;
import com.tapjoy.TJAdUnitConstants;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

public class JavaModule {
    /* access modifiers changed from: private */
    public static final NamedJavaFunction[] EMPTY_MODULE = new NamedJavaFunction[0];
    private static final JavaModule INSTANCE = new JavaModule();
    private static final Map<String, Class<?>> PRIMITIVE_TYPES = new HashMap();
    private final NamedJavaFunction[] functions = {new Require(), new New(), new InstanceOf(), new Cast(), new Proxy(), new Pairs(), new IPairs(), new ToTable(), new Elements(), new Fields(), new Methods(), new Properties()};

    private static class Cast implements NamedJavaFunction {
        private Cast() {
        }

        public String getName() {
            return "cast";
        }

        public int invoke(LuaState luaState) {
            final Class access$1200 = luaState.isJavaObject(2, Class.class) ? (Class) luaState.checkJavaObject(2, Class.class) : JavaModule.loadType(luaState, luaState.checkString(2));
            final Object checkJavaObject = luaState.checkJavaObject(1, access$1200);
            luaState.pushJavaObject(new TypedJavaObject() {
                public Object getObject() {
                    return checkJavaObject;
                }

                public Class<?> getType() {
                    return access$1200;
                }

                public boolean isStrong() {
                    return false;
                }
            });
            return 1;
        }
    }

    private static class Elements implements NamedJavaFunction {

        private static class ElementIterator implements JavaFunction {
            private Iterator<?> iterator;

            public ElementIterator(Iterator<?> it) {
                this.iterator = it;
            }

            public int invoke(LuaState luaState) {
                if (this.iterator.hasNext()) {
                    luaState.pushJavaObject(this.iterator.next());
                    return 1;
                }
                luaState.pushNil();
                return 1;
            }
        }

        private Elements() {
        }

        public String getName() {
            return "elements";
        }

        public int invoke(LuaState luaState) {
            Iterable iterable = (Iterable) luaState.checkJavaObject(1, Iterable.class);
            luaState.pushJavaObject(new ElementIterator(iterable.iterator()));
            luaState.pushJavaObject(iterable);
            luaState.pushNil();
            return 3;
        }
    }

    private static class Fields implements NamedJavaFunction {
        private Fields() {
        }

        public String getName() {
            return "fields";
        }

        public int invoke(LuaState luaState) {
            luaState.checkArg(1, luaState.isJavaObjectRaw(1), String.format("expected Java object, got %s", new Object[]{luaState.typeName(1)}));
            return luaState.getMetamethod(luaState.toJavaObjectRaw(1), JavaReflector.Metamethod.JAVAFIELDS).invoke(luaState);
        }
    }

    private static class IPairs implements NamedJavaFunction {
        private final JavaFunction arrayNext;
        private final JavaFunction listNext;

        private static class ArrayNext implements JavaFunction {
            private ArrayNext() {
            }

            public int invoke(LuaState luaState) {
                Object checkJavaObject = luaState.checkJavaObject(1, Object.class);
                int length = Array.getLength(checkJavaObject);
                int checkInteger = luaState.checkInteger(2) + 1;
                if (checkInteger < 1 || checkInteger > length) {
                    luaState.pushNil();
                    return 1;
                }
                luaState.pushInteger(checkInteger);
                luaState.pushJavaObject(Array.get(checkJavaObject, checkInteger - 1));
                return 2;
            }
        }

        private static class ListNext implements JavaFunction {
            private ListNext() {
            }

            public int invoke(LuaState luaState) {
                List list = (List) luaState.checkJavaObject(1, List.class);
                int size = list.size();
                int checkInteger = luaState.checkInteger(2) + 1;
                if (checkInteger < 1 || checkInteger > size) {
                    luaState.pushNil();
                    return 1;
                }
                luaState.pushInteger(checkInteger);
                luaState.pushJavaObject(list.get(checkInteger - 1));
                return 2;
            }
        }

        private IPairs() {
            this.listNext = new ListNext();
            this.arrayNext = new ArrayNext();
        }

        public String getName() {
            return "ipairs";
        }

        public int invoke(LuaState luaState) {
            Object checkJavaObject;
            if (luaState.isJavaObject(1, List.class)) {
                checkJavaObject = luaState.toJavaObject(1, List.class);
                luaState.pushJavaFunction(this.listNext);
            } else {
                checkJavaObject = luaState.checkJavaObject(1, Object.class);
                luaState.checkArg(1, checkJavaObject.getClass().isArray(), String.format("expected list or array, got %s", new Object[]{luaState.typeName(1)}));
                luaState.pushJavaFunction(this.arrayNext);
            }
            luaState.pushJavaObject(checkJavaObject);
            luaState.pushInteger(0);
            return 3;
        }
    }

    private static class InstanceOf implements NamedJavaFunction {
        private InstanceOf() {
        }

        public String getName() {
            return "instanceof";
        }

        public int invoke(LuaState luaState) {
            luaState.pushBoolean((luaState.isJavaObject(2, Class.class) ? (Class) luaState.checkJavaObject(2, Class.class) : JavaModule.loadType(luaState, luaState.checkString(2))).isInstance(luaState.checkJavaObject(1, Object.class)));
            return 1;
        }
    }

    private static class Methods implements NamedJavaFunction {
        private Methods() {
        }

        public String getName() {
            return "methods";
        }

        public int invoke(LuaState luaState) {
            luaState.checkArg(1, luaState.isJavaObjectRaw(1), String.format("expected Java object, got %s", new Object[]{luaState.typeName(1)}));
            return luaState.getMetamethod(luaState.toJavaObjectRaw(1), JavaReflector.Metamethod.JAVAMETHODS).invoke(luaState);
        }
    }

    private static class New implements NamedJavaFunction {
        private New() {
        }

        public String getName() {
            return "new";
        }

        public int invoke(LuaState luaState) {
            Object newInstance;
            Class access$1200 = luaState.isJavaObject(1, Class.class) ? (Class) luaState.checkJavaObject(1, Class.class) : JavaModule.loadType(luaState, luaState.checkString(1));
            int top = luaState.getTop() - 1;
            switch (top) {
                case 0:
                    try {
                        newInstance = access$1200.newInstance();
                        break;
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e2) {
                        throw new RuntimeException(e2);
                    }
                case 1:
                    newInstance = Array.newInstance(access$1200, luaState.checkInteger(2));
                    break;
                default:
                    int[] iArr = new int[top];
                    for (int i = 0; i < top; i++) {
                        iArr[i] = luaState.checkInteger(i + 2);
                    }
                    newInstance = Array.newInstance(access$1200, iArr);
                    break;
            }
            luaState.pushJavaObject(newInstance);
            return 1;
        }
    }

    private static class Pairs implements NamedJavaFunction {
        private final JavaFunction navigableMapNext;

        private static class MapNext implements JavaFunction {
            private Iterator<Map.Entry<Object, Object>> iterator;

            public MapNext(Iterator<Map.Entry<Object, Object>> it) {
                this.iterator = it;
            }

            public int invoke(LuaState luaState) {
                if (this.iterator.hasNext()) {
                    Map.Entry next = this.iterator.next();
                    luaState.pushJavaObject(next.getKey());
                    luaState.pushJavaObject(next.getValue());
                    return 2;
                }
                luaState.pushNil();
                return 1;
            }
        }

        private static class NavigableMapNext implements JavaFunction {
            private NavigableMapNext() {
            }

            public int invoke(LuaState luaState) {
                NavigableMap navigableMap = (NavigableMap) luaState.checkJavaObject(1, NavigableMap.class);
                Object checkJavaObject = luaState.checkJavaObject(2, Object.class);
                Map.Entry higherEntry = checkJavaObject != null ? navigableMap.higherEntry(checkJavaObject) : navigableMap.firstEntry();
                if (higherEntry != null) {
                    luaState.pushJavaObject(higherEntry.getKey());
                    luaState.pushJavaObject(higherEntry.getValue());
                    return 2;
                }
                luaState.pushNil();
                return 1;
            }
        }

        private Pairs() {
            this.navigableMapNext = new NavigableMapNext();
        }

        public String getName() {
            return "pairs";
        }

        public int invoke(LuaState luaState) {
            Map map = (Map) luaState.checkJavaObject(1, Map.class);
            luaState.checkArg(1, map != null, String.format("expected map, got %s", new Object[]{luaState.typeName(1)}));
            if (map instanceof NavigableMap) {
                luaState.pushJavaFunction(this.navigableMapNext);
            } else {
                luaState.pushJavaFunction(new MapNext(map.entrySet().iterator()));
            }
            luaState.pushJavaObject(map);
            luaState.pushNil();
            return 3;
        }
    }

    private static class Properties implements NamedJavaFunction {
        private Properties() {
        }

        public String getName() {
            return "properties";
        }

        public int invoke(LuaState luaState) {
            luaState.checkArg(1, luaState.isJavaObjectRaw(1), String.format("expected Java object, got %s", new Object[]{luaState.typeName(1)}));
            return luaState.getMetamethod(luaState.toJavaObjectRaw(1), JavaReflector.Metamethod.JAVAPROPERTIES).invoke(luaState);
        }
    }

    private static class Proxy implements NamedJavaFunction {
        private Proxy() {
        }

        public String getName() {
            return "proxy";
        }

        public int invoke(LuaState luaState) {
            luaState.checkType(1, LuaType.TABLE);
            int top = luaState.getTop() - 1;
            luaState.checkArg(2, top > 0, "no interface specified");
            Class[] clsArr = new Class[top];
            for (int i = 0; i < top; i++) {
                if (luaState.isJavaObject(i + 2, Class.class)) {
                    clsArr[i] = (Class) luaState.checkJavaObject(i + 2, Class.class);
                } else {
                    clsArr[i] = JavaModule.loadType(luaState, luaState.checkString(i + 2));
                }
            }
            luaState.pushJavaObjectRaw(luaState.getProxy(1, (Class<?>[]) clsArr));
            return 1;
        }
    }

    private static class Require implements NamedJavaFunction {
        private Require() {
        }

        public String getName() {
            return "require";
        }

        public int invoke(LuaState luaState) {
            String checkString = luaState.checkString(1);
            boolean checkBoolean = luaState.checkBoolean(2, false);
            Class access$1200 = JavaModule.loadType(luaState, checkString);
            luaState.pushJavaObject(access$1200);
            if (checkBoolean) {
                String name = access$1200.getName();
                int lastIndexOf = name.lastIndexOf(46);
                if (lastIndexOf >= 0) {
                    String substring = name.substring(0, lastIndexOf);
                    String substring2 = name.substring(lastIndexOf + 1);
                    luaState.register(substring, JavaModule.EMPTY_MODULE);
                    luaState.pushJavaObject(access$1200);
                    luaState.setField(-2, substring2);
                    luaState.pop(1);
                } else {
                    luaState.pushJavaObject(access$1200);
                    luaState.setGlobal(name);
                }
            }
            luaState.pushBoolean(checkBoolean);
            return 2;
        }
    }

    private static class ToTable implements NamedJavaFunction {

        private static class LuaList implements JavaReflector, TypedJavaObject {
            private static final JavaFunction INDEX = new Index();
            private static final JavaFunction LENGTH = new Length();
            private static final JavaFunction NEW_INDEX = new NewIndex();
            private List<Object> list;

            private static class Index implements JavaFunction {
                private Index() {
                }

                public int invoke(LuaState luaState) {
                    LuaList luaList = (LuaList) luaState.toJavaObjectRaw(1);
                    if (!luaState.isNumber(2)) {
                        throw new LuaRuntimeException(String.format("attempt to read list with %s accessor", new Object[]{luaState.typeName(2)}));
                    }
                    luaState.pushJavaObject(luaList.getList().get(luaState.toInteger(2) - 1));
                    return 1;
                }
            }

            private static class Length implements JavaFunction {
                private Length() {
                }

                public int invoke(LuaState luaState) {
                    luaState.pushInteger(((LuaList) luaState.toJavaObjectRaw(1)).getList().size());
                    return 1;
                }
            }

            private static class NewIndex implements JavaFunction {
                private NewIndex() {
                }

                public int invoke(LuaState luaState) {
                    LuaList luaList = (LuaList) luaState.toJavaObjectRaw(1);
                    if (!luaState.isNumber(2)) {
                        throw new LuaRuntimeException(String.format("attempt to write list with %s accessor", new Object[]{luaState.typeName(2)}));
                    }
                    int integer = luaState.toInteger(2);
                    Object javaObject = luaState.toJavaObject(3, Object.class);
                    if (javaObject == null) {
                        luaList.getList().remove(integer - 1);
                    } else if (integer - 1 != luaList.getList().size()) {
                        luaList.getList().set(integer - 1, javaObject);
                    } else {
                        luaList.getList().add(javaObject);
                    }
                    return 0;
                }
            }

            public LuaList(List<Object> list2) {
                this.list = list2;
            }

            public List<Object> getList() {
                return this.list;
            }

            public JavaFunction getMetamethod(JavaReflector.Metamethod metamethod) {
                switch (metamethod) {
                    case INDEX:
                        return INDEX;
                    case NEWINDEX:
                        return NEW_INDEX;
                    case LEN:
                        return LENGTH;
                    default:
                        return null;
                }
            }

            public Object getObject() {
                return this.list;
            }

            public Class<?> getType() {
                return List.class;
            }

            public boolean isStrong() {
                return true;
            }
        }

        private static class LuaMap implements JavaReflector, TypedJavaObject {
            private static final JavaFunction INDEX = new Index();
            private static final JavaFunction NEW_INDEX = new NewIndex();
            private Map<Object, Object> map;

            private static class Index implements JavaFunction {
                private Index() {
                }

                public int invoke(LuaState luaState) {
                    LuaMap luaMap = (LuaMap) luaState.toJavaObjectRaw(1);
                    Object javaObject = luaState.toJavaObject(2, Object.class);
                    if (javaObject == null) {
                        throw new LuaRuntimeException(String.format("attempt to read map with %s accessor", new Object[]{luaState.typeName(2)}));
                    }
                    luaState.pushJavaObject(luaMap.getMap().get(javaObject));
                    return 1;
                }
            }

            private static class NewIndex implements JavaFunction {
                private NewIndex() {
                }

                public int invoke(LuaState luaState) {
                    LuaMap luaMap = (LuaMap) luaState.toJavaObjectRaw(1);
                    Object javaObject = luaState.toJavaObject(2, Object.class);
                    if (javaObject == null) {
                        throw new LuaRuntimeException(String.format("attempt to write map with %s accessor", new Object[]{luaState.typeName(2)}));
                    }
                    Object javaObject2 = luaState.toJavaObject(3, Object.class);
                    if (javaObject2 != null) {
                        luaMap.getMap().put(javaObject, javaObject2);
                    } else {
                        luaMap.getMap().remove(javaObject);
                    }
                    return 0;
                }
            }

            public LuaMap(Map<Object, Object> map2) {
                this.map = map2;
            }

            public Map<Object, Object> getMap() {
                return this.map;
            }

            public JavaFunction getMetamethod(JavaReflector.Metamethod metamethod) {
                switch (metamethod) {
                    case INDEX:
                        return INDEX;
                    case NEWINDEX:
                        return NEW_INDEX;
                    default:
                        return null;
                }
            }

            public Object getObject() {
                return this.map;
            }

            public Class<?> getType() {
                return Map.class;
            }

            public boolean isStrong() {
                return true;
            }
        }

        private ToTable() {
        }

        public static TypedJavaObject toTable(List<?> list) {
            return new LuaList(list);
        }

        public static TypedJavaObject toTable(Map<?, ?> map) {
            return new LuaMap(map);
        }

        public String getName() {
            return "totable";
        }

        public int invoke(LuaState luaState) {
            if (luaState.isJavaObject(1, Map.class)) {
                luaState.pushJavaObject(new LuaMap((Map) luaState.toJavaObject(1, Map.class)));
            } else if (luaState.isJavaObject(1, List.class)) {
                luaState.pushJavaObject(new LuaList((List) luaState.toJavaObject(1, List.class)));
            } else {
                luaState.checkArg(1, false, String.format("expected map or list, got %s", new Object[]{luaState.typeName(1)}));
            }
            return 1;
        }
    }

    static {
        PRIMITIVE_TYPES.put("boolean", Boolean.TYPE);
        PRIMITIVE_TYPES.put("byte", Byte.TYPE);
        PRIMITIVE_TYPES.put("char", Character.TYPE);
        PRIMITIVE_TYPES.put("double", Double.TYPE);
        PRIMITIVE_TYPES.put("float", Float.TYPE);
        PRIMITIVE_TYPES.put("int", Integer.TYPE);
        PRIMITIVE_TYPES.put(TJAdUnitConstants.String.LONG, Long.TYPE);
        PRIMITIVE_TYPES.put("short", Short.TYPE);
        PRIMITIVE_TYPES.put("void", Void.TYPE);
    }

    private JavaModule() {
    }

    public static JavaModule getInstance() {
        return INSTANCE;
    }

    /* access modifiers changed from: private */
    public static Class<?> loadType(LuaState luaState, String str) {
        Class<?> cls = PRIMITIVE_TYPES.get(str);
        if (cls != null) {
            return cls;
        }
        try {
            return luaState.getClassLoader().loadClass(str);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void open(LuaState luaState) {
        synchronized (luaState) {
            luaState.register("java", this.functions);
            luaState.pop(1);
        }
    }

    public TypedJavaObject toTable(List<?> list) {
        return ToTable.toTable(list);
    }

    public TypedJavaObject toTable(Map<?, ?> map) {
        return ToTable.toTable(map);
    }
}
