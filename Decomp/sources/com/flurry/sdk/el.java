package com.flurry.sdk;

import com.flurry.sdk.en;
import java.io.InputStream;
import java.io.OutputStream;

public class el<RequestObjectType, ResponseObjectType> extends en {
    private a<RequestObjectType, ResponseObjectType> a;
    /* access modifiers changed from: private */
    public RequestObjectType b;
    /* access modifiers changed from: private */
    public ResponseObjectType c;
    /* access modifiers changed from: private */
    public ex<RequestObjectType> d;
    /* access modifiers changed from: private */
    public ex<ResponseObjectType> e;

    public interface a<RequestObjectType, ResponseObjectType> {
        void a(el<RequestObjectType, ResponseObjectType> elVar, ResponseObjectType responseobjecttype);
    }

    private void m() {
        a((en.c) new en.c() {
            public void a(en enVar) {
                el.this.n();
            }

            public void a(en enVar, InputStream inputStream) throws Exception {
                if (enVar.d() && el.this.e != null) {
                    Object unused = el.this.c = el.this.e.a(inputStream);
                }
            }

            public void a(en enVar, OutputStream outputStream) throws Exception {
                if (el.this.b != null && el.this.d != null) {
                    el.this.d.a(outputStream, el.this.b);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void n() {
        if (this.a != null && !b()) {
            this.a.a(this, this.c);
        }
    }

    public void a() {
        m();
        super.a();
    }

    public void a(a<RequestObjectType, ResponseObjectType> aVar) {
        this.a = aVar;
    }

    public void a(ex<RequestObjectType> exVar) {
        this.d = exVar;
    }

    public void a(RequestObjectType requestobjecttype) {
        this.b = requestobjecttype;
    }

    public void b(ex<ResponseObjectType> exVar) {
        this.e = exVar;
    }
}
