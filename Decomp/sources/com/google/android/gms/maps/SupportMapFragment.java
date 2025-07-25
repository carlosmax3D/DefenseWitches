package com.google.android.gms.maps;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.c;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.internal.eg;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.internal.p;
import com.google.android.gms.maps.internal.q;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class SupportMapFragment extends Fragment {
    private GoogleMap BU;
    private final b Cf = new b(this);

    static class a implements LifecycleDelegate {
        private final IMapFragmentDelegate BW;
        private final Fragment Cg;

        public a(Fragment fragment, IMapFragmentDelegate iMapFragmentDelegate) {
            this.BW = (IMapFragmentDelegate) eg.f(iMapFragmentDelegate);
            this.Cg = (Fragment) eg.f(fragment);
        }

        public IMapFragmentDelegate ew() {
            return this.BW;
        }

        public void onCreate(Bundle bundle) {
            if (bundle == null) {
                try {
                    bundle = new Bundle();
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                }
            }
            Bundle arguments = this.Cg.getArguments();
            if (arguments != null && arguments.containsKey("MapOptions")) {
                p.a(bundle, "MapOptions", arguments.getParcelable("MapOptions"));
            }
            this.BW.onCreate(bundle);
        }

        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            try {
                return (View) c.b(this.BW.onCreateView(c.h(layoutInflater), c.h(viewGroup), bundle));
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onDestroy() {
            try {
                this.BW.onDestroy();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onDestroyView() {
            try {
                this.BW.onDestroyView();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            try {
                this.BW.onInflate(c.h(activity), (GoogleMapOptions) bundle.getParcelable("MapOptions"), bundle2);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onLowMemory() {
            try {
                this.BW.onLowMemory();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onPause() {
            try {
                this.BW.onPause();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onResume() {
            try {
                this.BW.onResume();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onSaveInstanceState(Bundle bundle) {
            try {
                this.BW.onSaveInstanceState(bundle);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
    }

    static class b extends com.google.android.gms.dynamic.a<a> {
        protected d<a> BX;
        private final Fragment Cg;
        private Activity gs;

        b(Fragment fragment) {
            this.Cg = fragment;
        }

        /* access modifiers changed from: private */
        public void setActivity(Activity activity) {
            this.gs = activity;
            ex();
        }

        /* access modifiers changed from: protected */
        public void a(d<a> dVar) {
            this.BX = dVar;
            ex();
        }

        public void ex() {
            if (this.gs != null && this.BX != null && cZ() == null) {
                try {
                    MapsInitializer.initialize(this.gs);
                    this.BX.a(new a(this.Cg, q.u(this.gs).f(c.h(this.gs))));
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                } catch (GooglePlayServicesNotAvailableException e2) {
                }
            }
        }
    }

    public static SupportMapFragment newInstance() {
        return new SupportMapFragment();
    }

    public static SupportMapFragment newInstance(GoogleMapOptions googleMapOptions) {
        SupportMapFragment supportMapFragment = new SupportMapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("MapOptions", googleMapOptions);
        supportMapFragment.setArguments(bundle);
        return supportMapFragment;
    }

    /* access modifiers changed from: protected */
    public IMapFragmentDelegate ew() {
        this.Cf.ex();
        if (this.Cf.cZ() == null) {
            return null;
        }
        return ((a) this.Cf.cZ()).ew();
    }

    public final GoogleMap getMap() {
        IMapFragmentDelegate ew = ew();
        if (ew == null) {
            return null;
        }
        try {
            IGoogleMapDelegate map = ew.getMap();
            if (map == null) {
                return null;
            }
            if (this.BU == null || this.BU.en().asBinder() != map.asBinder()) {
                this.BU = new GoogleMap(map);
            }
            return this.BU;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void onActivityCreated(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(SupportMapFragment.class.getClassLoader());
        }
        super.onActivityCreated(bundle);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.Cf.setActivity(activity);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.Cf.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.Cf.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public void onDestroy() {
        this.Cf.onDestroy();
        super.onDestroy();
    }

    public void onDestroyView() {
        this.Cf.onDestroyView();
        super.onDestroyView();
    }

    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(activity, attributeSet, bundle);
        this.Cf.setActivity(activity);
        GoogleMapOptions createFromAttributes = GoogleMapOptions.createFromAttributes(activity, attributeSet);
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("MapOptions", createFromAttributes);
        this.Cf.onInflate(activity, bundle2, bundle);
    }

    public void onLowMemory() {
        this.Cf.onLowMemory();
        super.onLowMemory();
    }

    public void onPause() {
        this.Cf.onPause();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.Cf.onResume();
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(SupportMapFragment.class.getClassLoader());
        }
        super.onSaveInstanceState(bundle);
        this.Cf.onSaveInstanceState(bundle);
    }

    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
    }
}
