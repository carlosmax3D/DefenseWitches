package com.google.android.gms.maps;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.RemoteException;
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

public class MapFragment extends Fragment {
    private final b BT = new b(this);
    private GoogleMap BU;

    static class a implements LifecycleDelegate {
        private final Fragment BV;
        private final IMapFragmentDelegate BW;

        public a(Fragment fragment, IMapFragmentDelegate iMapFragmentDelegate) {
            this.BW = (IMapFragmentDelegate) eg.f(iMapFragmentDelegate);
            this.BV = (Fragment) eg.f(fragment);
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
            Bundle arguments = this.BV.getArguments();
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
        private final Fragment BV;
        protected d<a> BX;
        private Activity gs;

        b(Fragment fragment) {
            this.BV = fragment;
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
                    this.BX.a(new a(this.BV, q.u(this.gs).f(c.h(this.gs))));
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                } catch (GooglePlayServicesNotAvailableException e2) {
                }
            }
        }
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    public static MapFragment newInstance(GoogleMapOptions googleMapOptions) {
        MapFragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("MapOptions", googleMapOptions);
        mapFragment.setArguments(bundle);
        return mapFragment;
    }

    /* access modifiers changed from: protected */
    public IMapFragmentDelegate ew() {
        this.BT.ex();
        if (this.BT.cZ() == null) {
            return null;
        }
        return ((a) this.BT.cZ()).ew();
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
            bundle.setClassLoader(MapFragment.class.getClassLoader());
        }
        super.onActivityCreated(bundle);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.BT.setActivity(activity);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.BT.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.BT.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public void onDestroy() {
        this.BT.onDestroy();
        super.onDestroy();
    }

    public void onDestroyView() {
        this.BT.onDestroyView();
        super.onDestroyView();
    }

    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(activity, attributeSet, bundle);
        this.BT.setActivity(activity);
        GoogleMapOptions createFromAttributes = GoogleMapOptions.createFromAttributes(activity, attributeSet);
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("MapOptions", createFromAttributes);
        this.BT.onInflate(activity, bundle2, bundle);
    }

    public void onLowMemory() {
        this.BT.onLowMemory();
        super.onLowMemory();
    }

    public void onPause() {
        this.BT.onPause();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.BT.onResume();
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(MapFragment.class.getClassLoader());
        }
        super.onSaveInstanceState(bundle);
        this.BT.onSaveInstanceState(bundle);
    }

    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
    }
}
