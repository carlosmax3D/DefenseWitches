package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import java.lang.ref.WeakReference;

public class fq {
    protected fl te;
    protected a uI;

    public static final class a {
        public int bottom;
        public int gravity;
        public int left;
        public int right;
        public int top;
        public IBinder uJ;
        public int uK;

        private a(int i, IBinder iBinder) {
            this.uK = -1;
            this.left = 0;
            this.top = 0;
            this.right = 0;
            this.bottom = 0;
            this.gravity = i;
            this.uJ = iBinder;
        }

        /* renamed from: do  reason: not valid java name */
        public Bundle m0do() {
            Bundle bundle = new Bundle();
            bundle.putInt("popupLocationInfo.gravity", this.gravity);
            bundle.putInt("popupLocationInfo.displayId", this.uK);
            bundle.putInt("popupLocationInfo.left", this.left);
            bundle.putInt("popupLocationInfo.top", this.top);
            bundle.putInt("popupLocationInfo.right", this.right);
            bundle.putInt("popupLocationInfo.bottom", this.bottom);
            return bundle;
        }
    }

    private static final class b extends fq implements View.OnAttachStateChangeListener, ViewTreeObserver.OnGlobalLayoutListener {
        private boolean tT = false;
        private WeakReference<View> uL;

        protected b(fl flVar, int i) {
            super(flVar, i);
        }

        private void f(View view) {
            Display display;
            int i = -1;
            if (fg.cI() && (display = view.getDisplay()) != null) {
                i = display.getDisplayId();
            }
            IBinder windowToken = view.getWindowToken();
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            int width = view.getWidth();
            int height = view.getHeight();
            this.uI.uK = i;
            this.uI.uJ = windowToken;
            this.uI.left = iArr[0];
            this.uI.top = iArr[1];
            this.uI.right = iArr[0] + width;
            this.uI.bottom = iArr[1] + height;
            if (this.tT) {
                dl();
                this.tT = false;
            }
        }

        /* access modifiers changed from: protected */
        public void aF(int i) {
            this.uI = new a(i, (IBinder) null);
        }

        public void dl() {
            if (this.uI.uJ != null) {
                fq.super.dl();
            } else {
                this.tT = this.uL != null;
            }
        }

        public void e(View view) {
            this.te.df();
            if (this.uL != null) {
                View view2 = (View) this.uL.get();
                Context context = this.te.getContext();
                if (view2 == null && (context instanceof Activity)) {
                    view2 = ((Activity) context).getWindow().getDecorView();
                }
                if (view2 != null) {
                    view2.removeOnAttachStateChangeListener(this);
                    ViewTreeObserver viewTreeObserver = view2.getViewTreeObserver();
                    if (fg.cH()) {
                        viewTreeObserver.removeOnGlobalLayoutListener(this);
                    } else {
                        viewTreeObserver.removeGlobalOnLayoutListener(this);
                    }
                }
            }
            this.uL = null;
            Context context2 = this.te.getContext();
            if (view == null && (context2 instanceof Activity)) {
                View findViewById = ((Activity) context2).findViewById(16908290);
                if (findViewById == null) {
                    findViewById = ((Activity) context2).getWindow().getDecorView();
                }
                fn.c("PopupManager", "You have not specified a View to use as content view for popups. Falling back to the Activity content view which may not work properly in future versions of the API. Use setViewForPopups() to set your content view.");
                view = findViewById;
            }
            if (view != null) {
                f(view);
                this.uL = new WeakReference<>(view);
                view.addOnAttachStateChangeListener(this);
                view.getViewTreeObserver().addOnGlobalLayoutListener(this);
                return;
            }
            fn.d("PopupManager", "No content view usable to display popups. Popups will not be displayed in response to this client's calls. Use setViewForPopups() to set your content view.");
        }

        public void onGlobalLayout() {
            View view;
            if (this.uL != null && (view = (View) this.uL.get()) != null) {
                f(view);
            }
        }

        public void onViewAttachedToWindow(View view) {
            f(view);
        }

        public void onViewDetachedFromWindow(View view) {
            this.te.df();
            view.removeOnAttachStateChangeListener(this);
        }
    }

    private fq(fl flVar, int i) {
        this.te = flVar;
        aF(i);
    }

    public static fq a(fl flVar, int i) {
        return fg.cE() ? new b(flVar, i) : new fq(flVar, i);
    }

    /* access modifiers changed from: protected */
    public void aF(int i) {
        this.uI = new a(i, new Binder());
    }

    public void dl() {
        this.te.a(this.uI.uJ, this.uI.m0do());
    }

    public Bundle dm() {
        return this.uI.m0do();
    }

    public IBinder dn() {
        return this.uI.uJ;
    }

    public void e(View view) {
    }

    public void setGravity(int i) {
        this.uI.gravity = i;
    }
}
