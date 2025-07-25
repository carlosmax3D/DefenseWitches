package com.naef.jnlua;

public interface TypedJavaObject {
    Object getObject();

    Class<?> getType();

    boolean isStrong();
}
