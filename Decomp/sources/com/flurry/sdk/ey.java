package com.flurry.sdk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ey implements ex<String> {
    public void a(OutputStream outputStream, String str) throws IOException {
        if (outputStream != null && str != null) {
            byte[] bytes = str.getBytes("utf-8");
            outputStream.write(bytes, 0, bytes.length);
        }
    }

    /* renamed from: b */
    public String a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        fe.a(inputStream, (OutputStream) byteArrayOutputStream);
        return byteArrayOutputStream.toString();
    }
}
