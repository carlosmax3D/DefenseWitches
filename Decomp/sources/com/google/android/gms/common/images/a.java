package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.internal.dq;
import com.google.android.gms.internal.dr;
import com.google.android.gms.internal.ds;
import com.google.android.gms.internal.ee;
import com.google.android.gms.internal.fg;
import java.lang.ref.WeakReference;

public final class a {
    final C0002a op;
    private int oq;
    private int or;
    int os;
    private int ot;
    private WeakReference<ImageManager.OnImageLoadedListener> ou;
    private WeakReference<ImageView> ov;
    private WeakReference<TextView> ow;
    private int ox;
    private boolean oy;
    private boolean oz;

    /* renamed from: com.google.android.gms.common.images.a$a  reason: collision with other inner class name */
    public static final class C0002a {
        public final Uri uri;

        public C0002a(Uri uri2) {
            this.uri = uri2;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C0002a)) {
                return false;
            }
            return this == obj || ((C0002a) obj).hashCode() == hashCode();
        }

        public int hashCode() {
            return ee.hashCode(this.uri);
        }
    }

    public a(int i) {
        this.oq = 0;
        this.or = 0;
        this.ox = -1;
        this.oy = true;
        this.oz = false;
        this.op = new C0002a((Uri) null);
        this.or = i;
    }

    public a(Uri uri) {
        this.oq = 0;
        this.or = 0;
        this.ox = -1;
        this.oy = true;
        this.oz = false;
        this.op = new C0002a(uri);
        this.or = 0;
    }

    private dq a(Drawable drawable, Drawable drawable2) {
        if (drawable == null) {
            drawable = null;
        } else if (drawable instanceof dq) {
            drawable = ((dq) drawable).bC();
        }
        return new dq(drawable, drawable2);
    }

    private void a(Drawable drawable, boolean z, boolean z2, boolean z3) {
        ImageManager.OnImageLoadedListener onImageLoadedListener;
        switch (this.os) {
            case 1:
                if (!z2 && (onImageLoadedListener = (ImageManager.OnImageLoadedListener) this.ou.get()) != null) {
                    onImageLoadedListener.onImageLoaded(this.op.uri, drawable, z3);
                    return;
                }
                return;
            case 2:
                ImageView imageView = (ImageView) this.ov.get();
                if (imageView != null) {
                    a(imageView, drawable, z, z2, z3);
                    return;
                }
                return;
            case 3:
                TextView textView = (TextView) this.ow.get();
                if (textView != null) {
                    a(textView, this.ox, drawable, z, z2);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void a(ImageView imageView, Drawable drawable, boolean z, boolean z2, boolean z3) {
        boolean z4 = !z2 && !z3;
        if (z4 && (imageView instanceof dr)) {
            int bE = ((dr) imageView).bE();
            if (this.or != 0 && bE == this.or) {
                return;
            }
        }
        boolean a = a(z, z2);
        Drawable a2 = a ? a(imageView.getDrawable(), drawable) : drawable;
        imageView.setImageDrawable(a2);
        if (imageView instanceof dr) {
            dr drVar = (dr) imageView;
            drVar.d(z3 ? this.op.uri : null);
            drVar.H(z4 ? this.or : 0);
        }
        if (a) {
            ((dq) a2).startTransition(250);
        }
    }

    private void a(TextView textView, int i, Drawable drawable, boolean z, boolean z2) {
        boolean a = a(z, z2);
        Drawable[] compoundDrawablesRelative = fg.cI() ? textView.getCompoundDrawablesRelative() : textView.getCompoundDrawables();
        Drawable a2 = a ? a(compoundDrawablesRelative[i], drawable) : drawable;
        Drawable drawable2 = i == 0 ? a2 : compoundDrawablesRelative[0];
        Drawable drawable3 = i == 1 ? a2 : compoundDrawablesRelative[1];
        Drawable drawable4 = i == 2 ? a2 : compoundDrawablesRelative[2];
        Drawable drawable5 = i == 3 ? a2 : compoundDrawablesRelative[3];
        if (fg.cI()) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable2, drawable3, drawable4, drawable5);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable2, drawable3, drawable4, drawable5);
        }
        if (a) {
            ((dq) a2).startTransition(250);
        }
    }

    private boolean a(boolean z, boolean z2) {
        return this.oy && !z2 && (!z || this.oz);
    }

    public void F(int i) {
        this.or = i;
    }

    /* access modifiers changed from: package-private */
    public void a(Context context, Bitmap bitmap, boolean z) {
        ds.d(bitmap);
        a(new BitmapDrawable(context.getResources(), bitmap), z, false, true);
    }

    public void a(ImageView imageView) {
        ds.d(imageView);
        this.ou = null;
        this.ov = new WeakReference<>(imageView);
        this.ow = null;
        this.ox = -1;
        this.os = 2;
        this.ot = imageView.hashCode();
    }

    public void a(ImageManager.OnImageLoadedListener onImageLoadedListener) {
        ds.d(onImageLoadedListener);
        this.ou = new WeakReference<>(onImageLoadedListener);
        this.ov = null;
        this.ow = null;
        this.ox = -1;
        this.os = 1;
        this.ot = ee.hashCode(onImageLoadedListener, this.op);
    }

    /* access modifiers changed from: package-private */
    public void b(Context context, boolean z) {
        Drawable drawable = null;
        if (this.or != 0) {
            drawable = context.getResources().getDrawable(this.or);
        }
        a(drawable, z, false, false);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof a)) {
            return false;
        }
        return this == obj || ((a) obj).hashCode() == hashCode();
    }

    public int hashCode() {
        return this.ot;
    }

    /* access modifiers changed from: package-private */
    public void r(Context context) {
        Drawable drawable = null;
        if (this.oq != 0) {
            drawable = context.getResources().getDrawable(this.oq);
        }
        a(drawable, false, true, false);
    }
}
