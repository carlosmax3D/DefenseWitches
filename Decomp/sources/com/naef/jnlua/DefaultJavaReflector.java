package com.naef.jnlua;

import com.naef.jnlua.JavaReflector;
import com.tapjoy.TJAdUnitConstants;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DefaultJavaReflector implements JavaReflector {
    private static final Object[] EMPTY_ARGUMENTS = new Object[0];
    private static final DefaultJavaReflector INSTANCE = new DefaultJavaReflector();
    /* access modifiers changed from: private */
    public static final Object JAVA_FUNCTION_TYPE = new Object();
    private ReadWriteLock accessorLock = new ReentrantReadWriteLock();
    private Map<Class<?>, Map<String, Accessor>> accessors = new HashMap();
    private JavaFunction equal = new Equal();
    private JavaFunction index = new Index();
    /* access modifiers changed from: private */
    public ReadWriteLock invocableDispatchLock = new ReentrantReadWriteLock();
    /* access modifiers changed from: private */
    public Map<LuaCallSignature, Invocable> invocableDispatches = new HashMap();
    private JavaFunction javaFields = new AccessorPairs(FieldAccessor.class);
    private JavaFunction javaMethods = new AccessorPairs(InvocableAccessor.class);
    private JavaFunction javaProperties = new AccessorPairs(PropertyAccessor.class);
    private JavaFunction length = new Length();
    private JavaFunction lessThan = new LessThan();
    private JavaFunction lessThanOrEqual = new LessThanOrEqual();
    private JavaFunction newIndex = new NewIndex();
    private JavaFunction toString = new ToString();

    private interface Accessor {
        boolean isNotStatic();

        boolean isStatic();

        void read(LuaState luaState, Object obj);

        void write(LuaState luaState, Object obj);
    }

    private class AccessorPairs implements JavaFunction {
        /* access modifiers changed from: private */
        public Class<?> accessorClass;

        private class AccessorNext implements JavaFunction {
            private boolean isStatic;
            private Iterator<Map.Entry<String, Accessor>> iterator;

            public AccessorNext(Iterator<Map.Entry<String, Accessor>> it, boolean z) {
                this.iterator = it;
                this.isStatic = z;
            }

            public int invoke(LuaState luaState) {
                while (this.iterator.hasNext()) {
                    Map.Entry next = this.iterator.next();
                    Accessor accessor = (Accessor) next.getValue();
                    if (accessor.getClass() == AccessorPairs.this.accessorClass) {
                        if (this.isStatic) {
                            if (accessor.isStatic()) {
                            }
                        } else if (!accessor.isNotStatic()) {
                        }
                        luaState.pushString((String) next.getKey());
                        accessor.read(luaState, luaState.toJavaObject(1, Object.class));
                        return 2;
                    }
                }
                return 0;
            }
        }

        public AccessorPairs(Class<?> cls) {
            this.accessorClass = cls;
        }

        public int invoke(LuaState luaState) {
            boolean z = true;
            Object javaObject = luaState.toJavaObject(1, Object.class);
            Class access$700 = DefaultJavaReflector.this.getObjectClass(javaObject);
            Iterator it = DefaultJavaReflector.this.getObjectAccessors(javaObject).entrySet().iterator();
            if (access$700 != javaObject) {
                z = false;
            }
            luaState.pushJavaObject(new AccessorNext(it, z));
            luaState.pushJavaObject(javaObject);
            luaState.pushNil();
            return 3;
        }
    }

    private class Equal implements JavaFunction {
        private Equal() {
        }

        public int invoke(LuaState luaState) {
            Object javaObject = luaState.toJavaObject(1, Object.class);
            Object javaObject2 = luaState.toJavaObject(2, Object.class);
            luaState.pushBoolean(javaObject == javaObject2 || (javaObject != null && javaObject.equals(javaObject2)));
            return 1;
        }
    }

    private class FieldAccessor implements Accessor {
        private Field field;

        public FieldAccessor(Field field2) {
            this.field = field2;
        }

        public boolean isNotStatic() {
            return !Modifier.isStatic(this.field.getModifiers());
        }

        public boolean isStatic() {
            return Modifier.isStatic(this.field.getModifiers());
        }

        public void read(LuaState luaState, Object obj) {
            try {
                if (DefaultJavaReflector.this.getObjectClass(obj) == obj) {
                    obj = null;
                }
                luaState.pushJavaObject(this.field.get(obj));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            }
        }

        public void write(LuaState luaState, Object obj) {
            try {
                if (DefaultJavaReflector.this.getObjectClass(obj) == obj) {
                    obj = null;
                }
                this.field.set(obj, luaState.checkJavaObject(-1, this.field.getType()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    private class Index implements JavaFunction {
        private Index() {
        }

        public int invoke(LuaState luaState) {
            Object javaObject = luaState.toJavaObject(1, Object.class);
            Class access$700 = DefaultJavaReflector.this.getObjectClass(javaObject);
            if (!access$700.isArray()) {
                Map access$800 = DefaultJavaReflector.this.getObjectAccessors(javaObject);
                String luaState2 = luaState.toString(-1);
                if (luaState2 == null) {
                    throw new LuaRuntimeException(String.format("attempt to read class %s with %s accessor", new Object[]{javaObject.getClass().getCanonicalName(), luaState.typeName(-1)}));
                }
                Accessor accessor = (Accessor) access$800.get(luaState2);
                if (accessor == null) {
                    throw new LuaRuntimeException(String.format("attempt to read class %s with accessor '%s' (undefined)", new Object[]{access$700.getCanonicalName(), luaState2}));
                }
                accessor.read(luaState, javaObject);
            } else if (!luaState.isNumber(2)) {
                throw new LuaRuntimeException(String.format("attempt to read array with %s accessor", new Object[]{luaState.typeName(2)}));
            } else {
                int integer = luaState.toInteger(2);
                int length = Array.getLength(javaObject);
                if (integer < 1 || integer > length) {
                    throw new LuaRuntimeException(String.format("attempt to read array of length %d at index %d", new Object[]{Integer.valueOf(length), Integer.valueOf(integer)}));
                }
                luaState.pushJavaObject(Array.get(javaObject, integer - 1));
            }
            return 1;
        }
    }

    private interface Invocable {
        Class<?> getDeclaringClass();

        int getModifiers();

        String getName();

        int getParameterCount();

        Class<?> getParameterType(int i);

        Class<?>[] getParameterTypes();

        Class<?> getReturnType();

        String getWhat();

        Object invoke(Object obj, Object... objArr) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;

        boolean isRawReturn();

        boolean isVarArgs();
    }

    private class InvocableAccessor implements Accessor, JavaFunction {
        private Class<?> clazz;
        private List<Invocable> invocables;

        public InvocableAccessor(Class<?> cls, Collection<Invocable> collection) {
            this.clazz = cls;
            this.invocables = new ArrayList(collection);
        }

        private Invocable dispatchInvocable(LuaState luaState, boolean z) {
            boolean z2;
            HashSet hashSet = new HashSet(this.invocables);
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                if (Modifier.isStatic(((Invocable) it.next()).getModifiers()) != z) {
                    it.remove();
                }
            }
            int top = luaState.getTop() - 1;
            Iterator it2 = hashSet.iterator();
            while (it2.hasNext()) {
                Invocable invocable = (Invocable) it2.next();
                if (invocable.isVarArgs()) {
                    if (top < invocable.getParameterCount() - 1) {
                        it2.remove();
                    }
                } else if (top != invocable.getParameterCount()) {
                    it2.remove();
                }
            }
            Converter converter = luaState.getConverter();
            Iterator it3 = hashSet.iterator();
            while (it3.hasNext()) {
                Invocable invocable2 = (Invocable) it3.next();
                int i = 0;
                while (true) {
                    if (i >= top) {
                        break;
                    }
                    if (converter.getTypeDistance(luaState, i + 2, invocable2.getParameterType(i)) == Integer.MAX_VALUE) {
                        it3.remove();
                        break;
                    }
                    i++;
                }
            }
            boolean z3 = false;
            Iterator it4 = hashSet.iterator();
            boolean z4 = false;
            while (true) {
                z2 = z3;
                if (!it4.hasNext()) {
                    break;
                }
                Invocable invocable3 = (Invocable) it4.next();
                z4 = z4 || !invocable3.isVarArgs();
                z3 = z2 || invocable3.isVarArgs();
            }
            if (z2 && z4) {
                Iterator it5 = hashSet.iterator();
                while (it5.hasNext()) {
                    if (((Invocable) it5.next()).isVarArgs()) {
                        it5.remove();
                    }
                }
            }
            Iterator it6 = hashSet.iterator();
            while (it6.hasNext()) {
                Invocable invocable4 = (Invocable) it6.next();
                Iterator it7 = hashSet.iterator();
                while (true) {
                    if (!it7.hasNext()) {
                        break;
                    }
                    Invocable invocable5 = (Invocable) it7.next();
                    if (invocable5 != invocable4) {
                        int min = Math.min(top, Math.max(invocable4.getParameterCount(), invocable5.getParameterCount()));
                        boolean z5 = false;
                        int i2 = 0;
                        while (true) {
                            if (i2 < min) {
                                int typeDistance = converter.getTypeDistance(luaState, i2 + 2, invocable4.getParameterType(i2));
                                int typeDistance2 = converter.getTypeDistance(luaState, i2 + 2, invocable5.getParameterType(i2));
                                if (typeDistance2 > typeDistance) {
                                    break;
                                }
                                z5 = z5 || typeDistance != typeDistance2;
                                i2++;
                            } else if (z5) {
                                it6.remove();
                                break;
                            }
                        }
                    }
                }
            }
            Iterator it8 = hashSet.iterator();
            while (it8.hasNext()) {
                Invocable invocable6 = (Invocable) it8.next();
                Iterator it9 = hashSet.iterator();
                while (true) {
                    if (!it9.hasNext()) {
                        break;
                    }
                    Invocable invocable7 = (Invocable) it9.next();
                    if (invocable7 != invocable6) {
                        int min2 = Math.min(top, Math.max(invocable6.getParameterCount(), invocable7.getParameterCount()));
                        boolean z6 = false;
                        int i3 = 0;
                        while (true) {
                            if (i3 < min2) {
                                Class<?> parameterType = invocable6.getParameterType(i3);
                                Class<?> parameterType2 = invocable7.getParameterType(i3);
                                if (!parameterType.isAssignableFrom(parameterType2)) {
                                    break;
                                }
                                z6 = z6 || parameterType != parameterType2;
                                i3++;
                            } else if (z6) {
                                it8.remove();
                                break;
                            }
                        }
                    }
                }
            }
            if (hashSet.isEmpty()) {
                throw getSignatureMismatchException(luaState);
            } else if (hashSet.size() <= 1) {
                return (Invocable) hashSet.iterator().next();
            } else {
                throw getSignatureAmbivalenceException(luaState, hashSet);
            }
        }

        private String getJavaSignatureString(Class<?>[] clsArr) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < clsArr.length; i++) {
                if (i > 0) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(clsArr[i].getCanonicalName());
            }
            return stringBuffer.toString();
        }

        private LuaCallSignature getLuaCallSignature(LuaState luaState) {
            int top = luaState.getTop() - 1;
            Object[] objArr = new Object[top];
            for (int i = 0; i < top; i++) {
                LuaType type = luaState.type(i + 2);
                switch (type) {
                    case FUNCTION:
                        objArr[i] = luaState.isJavaFunction(i + 2) ? DefaultJavaReflector.JAVA_FUNCTION_TYPE : LuaType.FUNCTION;
                        break;
                    case USERDATA:
                        if (!luaState.isJavaObjectRaw(i + 2)) {
                            objArr[i] = LuaType.USERDATA;
                            break;
                        } else {
                            Object javaObjectRaw = luaState.toJavaObjectRaw(i + 2);
                            if (!(javaObjectRaw instanceof TypedJavaObject)) {
                                objArr[i] = javaObjectRaw.getClass();
                                break;
                            } else {
                                objArr[i] = ((TypedJavaObject) javaObjectRaw).getType();
                                break;
                            }
                        }
                    default:
                        objArr[i] = type;
                        break;
                }
            }
            return new LuaCallSignature(this.clazz, getName(), objArr);
        }

        private String getLuaSignatureString(LuaState luaState) {
            int top = luaState.getTop() - 1;
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < top; i++) {
                if (i > 0) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(luaState.typeName(i + 2));
            }
            return stringBuffer.toString();
        }

        private LuaRuntimeException getSignatureAmbivalenceException(LuaState luaState, Set<Invocable> set) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(String.format("%s '%s(%s)' on class %s is ambivalent among ", new Object[]{getWhat(), getName(), getLuaSignatureString(luaState), this.clazz.getCanonicalName()}));
            boolean z = true;
            for (Invocable next : set) {
                if (z) {
                    z = false;
                } else {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(String.format("'%s(%s)'", new Object[]{getName(), getJavaSignatureString(next.getParameterTypes())}));
            }
            return new LuaRuntimeException(stringBuffer.toString());
        }

        private LuaRuntimeException getSignatureMismatchException(LuaState luaState) {
            return new LuaRuntimeException(String.format("no %s of class %s matches '%s(%s)'", new Object[]{getWhat(), this.clazz.getCanonicalName(), getName(), getLuaSignatureString(luaState)}));
        }

        public String getName() {
            return this.invocables.get(0).getName();
        }

        public String getWhat() {
            return this.invocables.get(0).getWhat();
        }

        public int invoke(LuaState luaState) {
            Object checkJavaObject = luaState.checkJavaObject(1, Object.class);
            Class access$700 = DefaultJavaReflector.this.getObjectClass(checkJavaObject);
            luaState.checkArg(1, this.clazz.isAssignableFrom(access$700), String.format("class %s is not a subclass of %s", new Object[]{access$700.getCanonicalName(), this.clazz.getCanonicalName()}));
            Object obj = access$700 == checkJavaObject ? null : checkJavaObject;
            LuaCallSignature luaCallSignature = getLuaCallSignature(luaState);
            DefaultJavaReflector.this.invocableDispatchLock.readLock().lock();
            try {
                Invocable invocable = (Invocable) DefaultJavaReflector.this.invocableDispatches.get(luaCallSignature);
                if (invocable == null) {
                    invocable = dispatchInvocable(luaState, obj == null);
                    DefaultJavaReflector.this.invocableDispatchLock.writeLock().lock();
                    try {
                        if (!DefaultJavaReflector.this.invocableDispatches.containsKey(luaCallSignature)) {
                            DefaultJavaReflector.this.invocableDispatches.put(luaCallSignature, invocable);
                        } else {
                            invocable = (Invocable) DefaultJavaReflector.this.invocableDispatches.get(luaCallSignature);
                        }
                    } finally {
                        DefaultJavaReflector.this.invocableDispatchLock.writeLock().unlock();
                    }
                }
                int top = luaState.getTop() - 1;
                int parameterCount = invocable.getParameterCount();
                Object[] objArr = new Object[parameterCount];
                if (invocable.isVarArgs()) {
                    for (int i = 0; i < parameterCount - 1; i++) {
                        objArr[i] = luaState.toJavaObject(i + 2, invocable.getParameterType(i));
                    }
                    objArr[parameterCount - 1] = Array.newInstance(invocable.getParameterType(parameterCount - 1), top - (parameterCount - 1));
                    for (int i2 = parameterCount - 1; i2 < top; i2++) {
                        Array.set(objArr[parameterCount - 1], i2 - (parameterCount - 1), luaState.toJavaObject(i2 + 2, invocable.getParameterType(i2)));
                    }
                } else {
                    for (int i3 = 0; i3 < parameterCount; i3++) {
                        objArr[i3] = luaState.toJavaObject(i3 + 2, invocable.getParameterType(i3));
                    }
                }
                try {
                    Object invoke = invocable.invoke(obj, objArr);
                    if (invocable.getReturnType() == Void.TYPE) {
                        return 0;
                    }
                    if (invocable.isRawReturn()) {
                        luaState.pushJavaObjectRaw(invoke);
                        return 1;
                    }
                    luaState.pushJavaObject(invoke);
                    return 1;
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalArgumentException e2) {
                    throw new RuntimeException(e2);
                } catch (IllegalAccessException e3) {
                    throw new RuntimeException(e3);
                } catch (InvocationTargetException e4) {
                    throw new RuntimeException(e4.getTargetException());
                }
            } finally {
                DefaultJavaReflector.this.invocableDispatchLock.readLock().unlock();
            }
        }

        public boolean isNotStatic() {
            for (Invocable modifiers : this.invocables) {
                if (!Modifier.isStatic(modifiers.getModifiers())) {
                    return true;
                }
            }
            return false;
        }

        public boolean isStatic() {
            for (Invocable modifiers : this.invocables) {
                if (Modifier.isStatic(modifiers.getModifiers())) {
                    return true;
                }
            }
            return false;
        }

        public void read(LuaState luaState, Object obj) {
            if (DefaultJavaReflector.this.getObjectClass(obj) == obj) {
            }
            luaState.pushJavaFunction(this);
        }

        public void write(LuaState luaState, Object obj) {
            throw new LuaRuntimeException(String.format("attempt to write class %s with accessor '%s' (a %s)", new Object[]{DefaultJavaReflector.this.getObjectClass(obj).getCanonicalName(), getName(), getWhat()}));
        }
    }

    private static class InvocableConstructor implements Invocable {
        private Constructor<?> constructor;
        private Class<?>[] parameterTypes;

        public InvocableConstructor(Constructor<?> constructor2) {
            this.constructor = constructor2;
            this.parameterTypes = constructor2.getParameterTypes();
        }

        public Class<?> getDeclaringClass() {
            return this.constructor.getDeclaringClass();
        }

        public int getModifiers() {
            return this.constructor.getModifiers() | 8;
        }

        public String getName() {
            return "new";
        }

        public int getParameterCount() {
            return this.parameterTypes.length;
        }

        public Class<?> getParameterType(int i) {
            return (!this.constructor.isVarArgs() || i < this.parameterTypes.length + -1) ? this.parameterTypes[i] : this.parameterTypes[this.parameterTypes.length - 1].getComponentType();
        }

        public Class<?>[] getParameterTypes() {
            return this.parameterTypes;
        }

        public Class<?> getReturnType() {
            return this.constructor.getDeclaringClass();
        }

        public String getWhat() {
            return "constructor";
        }

        public Object invoke(Object obj, Object... objArr) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            return this.constructor.newInstance(objArr);
        }

        public boolean isRawReturn() {
            return false;
        }

        public boolean isVarArgs() {
            return this.constructor.isVarArgs();
        }

        public String toString() {
            return this.constructor.toString();
        }
    }

    private static class InvocableMethod implements Invocable {
        private Method method;
        private Class<?>[] parameterTypes;

        public InvocableMethod(Method method2) {
            this.method = method2;
            this.parameterTypes = method2.getParameterTypes();
        }

        public Class<?> getDeclaringClass() {
            return this.method.getDeclaringClass();
        }

        public int getModifiers() {
            return this.method.getModifiers();
        }

        public String getName() {
            return this.method.getName();
        }

        public int getParameterCount() {
            return this.parameterTypes.length;
        }

        public Class<?> getParameterType(int i) {
            return (!this.method.isVarArgs() || i < this.parameterTypes.length + -1) ? this.parameterTypes[i] : this.parameterTypes[this.parameterTypes.length - 1].getComponentType();
        }

        public Class<?>[] getParameterTypes() {
            return this.parameterTypes;
        }

        public Class<?> getReturnType() {
            return this.method.getReturnType();
        }

        public String getWhat() {
            return TJAdUnitConstants.String.METHOD;
        }

        public Object invoke(Object obj, Object... objArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            return this.method.invoke(obj, objArr);
        }

        public boolean isRawReturn() {
            return false;
        }

        public boolean isVarArgs() {
            return this.method.isVarArgs();
        }

        public String toString() {
            return this.method.toString();
        }
    }

    private static class InvocableProxy implements Invocable {
        private static final Class<?>[] PARAMETER_TYPES = {LuaValueProxy.class};
        private Class<?> interfaze;

        public InvocableProxy(Class<?> cls) {
            this.interfaze = cls;
        }

        public Class<?> getDeclaringClass() {
            return this.interfaze;
        }

        public int getModifiers() {
            return this.interfaze.getModifiers() | 8;
        }

        public String getName() {
            return "new";
        }

        public int getParameterCount() {
            return 1;
        }

        public Class<?> getParameterType(int i) {
            return PARAMETER_TYPES[0];
        }

        public Class<?>[] getParameterTypes() {
            return PARAMETER_TYPES;
        }

        public Class<?> getReturnType() {
            return this.interfaze;
        }

        public String getWhat() {
            return "proxy";
        }

        public Object invoke(Object obj, Object... objArr) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            LuaValueProxy luaValueProxy = objArr[0];
            luaValueProxy.pushValue();
            Object proxy = luaValueProxy.getLuaState().getProxy(-1, this.interfaze);
            luaValueProxy.getLuaState().pop(1);
            return proxy;
        }

        public boolean isRawReturn() {
            return true;
        }

        public boolean isVarArgs() {
            return false;
        }

        public String toString() {
            return this.interfaze.toString();
        }
    }

    private class Length implements JavaFunction {
        private Length() {
        }

        public int invoke(LuaState luaState) {
            Object javaObject = luaState.toJavaObject(1, Object.class);
            if (javaObject.getClass().isArray()) {
                luaState.pushInteger(Array.getLength(javaObject));
            } else {
                luaState.pushInteger(0);
            }
            return 1;
        }
    }

    private class LessThan implements JavaFunction {
        private LessThan() {
        }

        public int invoke(LuaState luaState) {
            if (!luaState.isJavaObject(1, Comparable.class)) {
                throw new LuaRuntimeException(String.format("class %s does not implement Comparable", new Object[]{luaState.typeName(1)}));
            }
            luaState.pushBoolean(((Comparable) luaState.toJavaObject(1, Comparable.class)).compareTo(luaState.toJavaObject(2, Object.class)) < 0);
            return 1;
        }
    }

    private class LessThanOrEqual implements JavaFunction {
        private LessThanOrEqual() {
        }

        public int invoke(LuaState luaState) {
            if (!luaState.isJavaObject(1, Comparable.class)) {
                throw new LuaRuntimeException(String.format("class %s does not implement Comparable", new Object[]{luaState.typeName(1)}));
            }
            luaState.pushBoolean(((Comparable) luaState.toJavaObject(1, Comparable.class)).compareTo(luaState.toJavaObject(2, Object.class)) <= 0);
            return 1;
        }
    }

    private static class LuaCallSignature {
        private Class<?> clazz;
        private int hashCode;
        private String invocableName;
        private Object[] types;

        public LuaCallSignature(Class<?> cls, String str, Object[] objArr) {
            this.clazz = cls;
            this.invocableName = str;
            this.types = objArr;
            this.hashCode = cls.hashCode();
            this.hashCode = (this.hashCode * 65599) + str.hashCode();
            for (Object hashCode2 : objArr) {
                this.hashCode = (this.hashCode * 65599) + hashCode2.hashCode();
            }
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof LuaCallSignature)) {
                return false;
            }
            LuaCallSignature luaCallSignature = (LuaCallSignature) obj;
            if (this.clazz != luaCallSignature.clazz || !this.invocableName.equals(luaCallSignature.invocableName) || this.types.length != luaCallSignature.types.length) {
                return false;
            }
            for (int i = 0; i < this.types.length; i++) {
                if (this.types[i] != luaCallSignature.types[i]) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            return this.hashCode;
        }

        public String toString() {
            return this.clazz.getCanonicalName() + ": " + this.invocableName + "(" + Arrays.asList(this.types) + ")";
        }
    }

    private class NewIndex implements JavaFunction {
        private NewIndex() {
        }

        public int invoke(LuaState luaState) {
            Object javaObject = luaState.toJavaObject(1, Object.class);
            Class access$700 = DefaultJavaReflector.this.getObjectClass(javaObject);
            if (!access$700.isArray()) {
                Map access$800 = DefaultJavaReflector.this.getObjectAccessors(javaObject);
                String luaState2 = luaState.toString(2);
                if (luaState2 == null) {
                    throw new LuaRuntimeException(String.format("attempt to write class %s with %s accessor", new Object[]{javaObject.getClass().getCanonicalName(), luaState.typeName(2)}));
                }
                Accessor accessor = (Accessor) access$800.get(luaState2);
                if (accessor == null) {
                    throw new LuaRuntimeException(String.format("attempt to write class %s with accessor '%s' (undefined)", new Object[]{access$700.getCanonicalName(), luaState2}));
                }
                accessor.write(luaState, javaObject);
            } else if (!luaState.isNumber(2)) {
                throw new LuaRuntimeException(String.format("attempt to write array with %s accessor", new Object[]{luaState.typeName(2)}));
            } else {
                int integer = luaState.toInteger(2);
                int length = Array.getLength(javaObject);
                if (integer < 1 || integer > length) {
                    throw new LuaRuntimeException(String.format("attempt to write array of length %d at index %d", new Object[]{Integer.valueOf(length), Integer.valueOf(integer)}));
                }
                Class<?> componentType = access$700.getComponentType();
                if (!luaState.isJavaObject(3, componentType)) {
                    throw new LuaRuntimeException(String.format("attempt to write array of %s at index %d with %s value", new Object[]{componentType.getCanonicalName(), luaState.typeName(3)}));
                }
                Array.set(javaObject, integer - 1, luaState.toJavaObject(3, componentType));
            }
            return 0;
        }
    }

    private class PropertyAccessor implements Accessor {
        private PropertyAccessor() {
        }

        public boolean isNotStatic() {
            return true;
        }

        public boolean isStatic() {
            return false;
        }

        public void read(LuaState luaState, Object obj) {
            throw new UnsupportedOperationException();
        }

        public void write(LuaState luaState, Object obj) {
            throw new UnsupportedOperationException();
        }
    }

    private class ToString implements JavaFunction {
        private ToString() {
        }

        public int invoke(LuaState luaState) {
            Object javaObject = luaState.toJavaObject(1, Object.class);
            luaState.pushString(javaObject != null ? javaObject.toString() : "null");
            return 1;
        }
    }

    private DefaultJavaReflector() {
    }

    private Map<String, Accessor> createClassAccessors(Class<?> cls) {
        HashMap hashMap;
        HashMap hashMap2 = new HashMap();
        Field[] fields = cls.getFields();
        for (int i = 0; i < fields.length; i++) {
            hashMap2.put(fields[i].getName(), new FieldAccessor(fields[i]));
        }
        HashMap hashMap3 = new HashMap();
        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            if (!hashMap2.containsKey(method.getName()) && (Modifier.isPublic(method.getDeclaringClass().getModifiers()) || (method = getInterfaceMethod(cls, method.getName(), method.getParameterTypes())) != null)) {
                Method method2 = method;
                Map map = (Map) hashMap3.get(method2.getName());
                if (map == null) {
                    HashMap hashMap4 = new HashMap();
                    hashMap3.put(method2.getName(), hashMap4);
                    hashMap = hashMap4;
                } else {
                    hashMap = map;
                }
                List asList = Arrays.asList(method2.getParameterTypes());
                Invocable invocable = (Invocable) hashMap.get(asList);
                if (invocable == null || !method2.getDeclaringClass().isAssignableFrom(invocable.getDeclaringClass())) {
                    hashMap.put(asList, new InvocableMethod(method2));
                }
            }
        }
        for (Map.Entry entry : hashMap3.entrySet()) {
            hashMap2.put(entry.getKey(), new InvocableAccessor(cls, ((Map) entry.getValue()).values()));
        }
        Constructor[] constructors = cls.getConstructors();
        ArrayList arrayList = new ArrayList(constructors.length);
        for (int i2 = 0; i2 < constructors.length; i2++) {
            if (Modifier.isPublic(constructors[i2].getDeclaringClass().getModifiers())) {
                arrayList.add(new InvocableConstructor(constructors[i2]));
            }
        }
        if (cls.isInterface()) {
            arrayList.add(new InvocableProxy(cls));
        }
        if (!arrayList.isEmpty()) {
            hashMap2.put("new", new InvocableAccessor(cls, arrayList));
        }
        return hashMap2;
    }

    public static DefaultJavaReflector getInstance() {
        return INSTANCE;
    }

    private Method getInterfaceMethod(Class<?> cls, String str, Class<?>[] clsArr) {
        Class<? super Object> superclass;
        do {
            Class[] interfaces = r4.getInterfaces();
            Class<? super Object> cls2 = cls;
            for (int i = 0; i < interfaces.length; i++) {
                if (Modifier.isPublic(interfaces[i].getModifiers())) {
                    try {
                        return interfaces[i].getDeclaredMethod(str, clsArr);
                    } catch (NoSuchMethodException e) {
                        Method interfaceMethod = getInterfaceMethod(interfaces[i], str, clsArr);
                        if (interfaceMethod != null) {
                            return interfaceMethod;
                        }
                    }
                }
            }
            superclass = cls2.getSuperclass();
            cls2 = superclass;
        } while (superclass != null);
        return null;
    }

    /* access modifiers changed from: private */
    public Map<String, Accessor> getObjectAccessors(Object obj) {
        Class<?> objectClass = getObjectClass(obj);
        this.accessorLock.readLock().lock();
        try {
            Map<String, Accessor> map = this.accessors.get(objectClass);
            if (map == null) {
                this.accessorLock.readLock().unlock();
                map = createClassAccessors(objectClass);
                this.accessorLock.writeLock().lock();
                try {
                    if (!this.accessors.containsKey(objectClass)) {
                        this.accessors.put(objectClass, map);
                    } else {
                        map = this.accessors.get(objectClass);
                    }
                } finally {
                    this.accessorLock.writeLock().unlock();
                }
            }
            return map;
        } finally {
            this.accessorLock.readLock().unlock();
        }
    }

    /* access modifiers changed from: private */
    public Class<?> getObjectClass(Object obj) {
        return obj instanceof Class ? (Class) obj : obj.getClass();
    }

    public JavaFunction getMetamethod(JavaReflector.Metamethod metamethod) {
        switch (metamethod) {
            case INDEX:
                return this.index;
            case NEWINDEX:
                return this.newIndex;
            case EQ:
                return this.equal;
            case LEN:
                return this.length;
            case LT:
                return this.lessThan;
            case LE:
                return this.lessThanOrEqual;
            case TOSTRING:
                return this.toString;
            case JAVAFIELDS:
                return this.javaFields;
            case JAVAMETHODS:
                return this.javaMethods;
            case JAVAPROPERTIES:
                return this.javaProperties;
            default:
                return null;
        }
    }
}
