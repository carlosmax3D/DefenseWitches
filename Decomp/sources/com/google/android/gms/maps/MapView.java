package com.google.android.gms.maps;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.c;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.internal.eg;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.q;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class MapView extends FrameLayout {
    private GoogleMap BU;
    private final b BY;

    static class a implements LifecycleDelegate {
        private final ViewGroup BZ;
        private final IMapViewDelegate Ca;
        private View Cb;

        public a(ViewGroup viewGroup, IMapViewDelegate iMapViewDelegate) {
            this.Ca = (IMapViewDelegate) eg.f(iMapViewDelegate);
            this.BZ = (ViewGroup) eg.f(viewGroup);
        }

        public IMapViewDelegate ey() {
            return this.Ca;
        }

        public void onCreate(Bundle bundle) {
            try {
                this.Ca.onCreate(bundle);
                this.Cb = (View) c.b(this.Ca.getView());
                this.BZ.removeAllViews();
                this.BZ.addView(this.Cb);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            throw new UnsupportedOperationException("onCreateView not allowed on MapViewDelegate");
        }

        public void onDestroy() {
            try {
                this.Ca.onDestroy();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onDestroyView() {
            throw new UnsupportedOperationException("onDestroyView not allowed on MapViewDelegate");
        }

        public void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            throw new UnsupportedOperationException("onInflate not allowed on MapViewDelegate");
        }

        public void onLowMemory() {
            try {
                this.Ca.onLowMemory();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onPause() {
            try {
                this.Ca.onPause();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onResume() {
            try {
                this.Ca.onResume();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onSaveInstanceState(Bundle bundle) {
            try {
                this.Ca.onSaveInstanceState(bundle);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
    }

    static class b extends com.google.android.gms.dynamic.a<a> {
        protected d<a> BX;
        private final ViewGroup Cc;
        private final GoogleMapOptions Cd;
        private final Context mContext;

        b(ViewGroup viewGroup, Context context, GoogleMapOptions googleMapOptions) {
            this.Cc = viewGroup;
            this.mContext = context;
            this.Cd = googleMapOptions;
        }

        /* access modifiers changed from: protected */
        public void a(d<a> dVar) {
            this.BX = dVar;
            ex();
        }

        public void ex() {
            if (this.BX != null && cZ() == null) {
                try {
                    this.BX.a(new a(this.Cc, q.u(this.mContext).a(c.h(this.mContext), this.Cd)));
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                } catch (GooglePlayServicesNotAvailableException e2) {
                }
            }
        }
    }

    public MapView(Context context) {
        super(context);
        this.BY = new b(this, context, (GoogleMapOptions) null);
    }

    public MapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.BY = new b(this, context, GoogleMapOptions.createFromAttributes(context, attributeSet));
    }

    public MapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.BY = new b(this, context, GoogleMapOptions.createFromAttributes(context, attributeSet));
    }

    public MapView(Context context, GoogleMapOptions googleMapOptions) {
        super(context);
        this.BY = new b(this, context, googleMapOptions);
    }

    public final GoogleMap getMap() {
        if (this.BU != null) {
            return this.BU;
        }
        this.BY.ex();
        if (this.BY.cZ() == null) {
            return null;
        }
        try {
            this.BU = new GoogleMap(((a) this.BY.cZ()).ey().getMap());
            return this.BU;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void onCreate(Bundle bundle) {
        this.BY.onCreate(bundle);
        if (this.BY.cZ() == null) {
            this.BY.a((FrameLayout) this);
        }
    }

    public final void onDestroy() {
        this.BY.onDestroy();
    }

    public final void onLowMemory() {
        this.BY.onLowMemory();
    }

    public final void onPause() {
        this.BY.onPause();
    }

    public final void onResume() {
        this.BY.onResume();
    }

    public final void onSaveInstanceState(Bundle bundle) {
        this.BY.onSaveInstanceState(bundle);
    }
}
