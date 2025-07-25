package com.flurry.sdk;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ex<ObjectType> {
    ObjectType a(InputStream inputStream) throws IOException;

    void a(OutputStream outputStream, ObjectType objecttype) throws IOException;
}
