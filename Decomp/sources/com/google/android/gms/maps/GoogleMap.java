package com.google.android.gms.maps;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.internal.eg;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.ILocationSourceDelegate;
import com.google.android.gms.maps.internal.b;
import com.google.android.gms.maps.internal.d;
import com.google.android.gms.maps.internal.e;
import com.google.android.gms.maps.internal.f;
import com.google.android.gms.maps.internal.g;
import com.google.android.gms.maps.internal.h;
import com.google.android.gms.maps.internal.i;
import com.google.android.gms.maps.internal.j;
import com.google.android.gms.maps.internal.k;
import com.google.android.gms.maps.internal.l;
import com.google.android.gms.maps.internal.m;
import com.google.android.gms.maps.internal.n;
import com.google.android.gms.maps.internal.o;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.internal.c;
import com.google.android.gms.maps.model.internal.d;
import com.google.android.gms.maps.model.internal.f;

public final class GoogleMap {
    public static final int MAP_TYPE_HYBRID = 4;
    public static final int MAP_TYPE_NONE = 0;
    public static final int MAP_TYPE_NORMAL = 1;
    public static final int MAP_TYPE_SATELLITE = 2;
    public static final int MAP_TYPE_TERRAIN = 3;
    private final IGoogleMapDelegate Br;
    private UiSettings Bs;

    public interface CancelableCallback {
        void onCancel();

        void onFinish();
    }

    public interface InfoWindowAdapter {
        View getInfoContents(Marker marker);

        View getInfoWindow(Marker marker);
    }

    public interface OnCameraChangeListener {
        void onCameraChange(CameraPosition cameraPosition);
    }

    public interface OnInfoWindowClickListener {
        void onInfoWindowClick(Marker marker);
    }

    public interface OnMapClickListener {
        void onMapClick(LatLng latLng);
    }

    public interface OnMapLoadedCallback {
        void onMapLoaded();
    }

    public interface OnMapLongClickListener {
        void onMapLongClick(LatLng latLng);
    }

    public interface OnMarkerClickListener {
        boolean onMarkerClick(Marker marker);
    }

    public interface OnMarkerDragListener {
        void onMarkerDrag(Marker marker);

        void onMarkerDragEnd(Marker marker);

        void onMarkerDragStart(Marker marker);
    }

    public interface OnMyLocationButtonClickListener {
        boolean onMyLocationButtonClick();
    }

    @Deprecated
    public interface OnMyLocationChangeListener {
        void onMyLocationChange(Location location);
    }

    public interface SnapshotReadyCallback {
        void onSnapshotReady(Bitmap bitmap);
    }

    private static final class a extends b.a {
        private final CancelableCallback BI;

        a(CancelableCallback cancelableCallback) {
            this.BI = cancelableCallback;
        }

        public void onCancel() {
            this.BI.onCancel();
        }

        public void onFinish() {
            this.BI.onFinish();
        }
    }

    protected GoogleMap(IGoogleMapDelegate iGoogleMapDelegate) {
        this.Br = (IGoogleMapDelegate) eg.f(iGoogleMapDelegate);
    }

    public final Circle addCircle(CircleOptions circleOptions) {
        try {
            return new Circle(this.Br.addCircle(circleOptions));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final GroundOverlay addGroundOverlay(GroundOverlayOptions groundOverlayOptions) {
        try {
            c addGroundOverlay = this.Br.addGroundOverlay(groundOverlayOptions);
            if (addGroundOverlay != null) {
                return new GroundOverlay(addGroundOverlay);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Marker addMarker(MarkerOptions markerOptions) {
        try {
            d addMarker = this.Br.addMarker(markerOptions);
            if (addMarker != null) {
                return new Marker(addMarker);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Polygon addPolygon(PolygonOptions polygonOptions) {
        try {
            return new Polygon(this.Br.addPolygon(polygonOptions));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Polyline addPolyline(PolylineOptions polylineOptions) {
        try {
            return new Polyline(this.Br.addPolyline(polylineOptions));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final TileOverlay addTileOverlay(TileOverlayOptions tileOverlayOptions) {
        try {
            f addTileOverlay = this.Br.addTileOverlay(tileOverlayOptions);
            if (addTileOverlay != null) {
                return new TileOverlay(addTileOverlay);
            }
            return null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate cameraUpdate) {
        try {
            this.Br.animateCamera(cameraUpdate.el());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate cameraUpdate, int i, CancelableCallback cancelableCallback) {
        try {
            this.Br.animateCameraWithDurationAndCallback(cameraUpdate.el(), i, cancelableCallback == null ? null : new a(cancelableCallback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate cameraUpdate, CancelableCallback cancelableCallback) {
        try {
            this.Br.animateCameraWithCallback(cameraUpdate.el(), cancelableCallback == null ? null : new a(cancelableCallback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void clear() {
        try {
            this.Br.clear();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    /* access modifiers changed from: package-private */
    public IGoogleMapDelegate en() {
        return this.Br;
    }

    public final CameraPosition getCameraPosition() {
        try {
            return this.Br.getCameraPosition();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final int getMapType() {
        try {
            return this.Br.getMapType();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMaxZoomLevel() {
        try {
            return this.Br.getMaxZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMinZoomLevel() {
        try {
            return this.Br.getMinZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Deprecated
    public final Location getMyLocation() {
        try {
            return this.Br.getMyLocation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Projection getProjection() {
        try {
            return new Projection(this.Br.getProjection());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final UiSettings getUiSettings() {
        try {
            if (this.Bs == null) {
                this.Bs = new UiSettings(this.Br.getUiSettings());
            }
            return this.Bs;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isBuildingsEnabled() {
        try {
            return this.Br.isBuildingsEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isIndoorEnabled() {
        try {
            return this.Br.isIndoorEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isMyLocationEnabled() {
        try {
            return this.Br.isMyLocationEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isTrafficEnabled() {
        try {
            return this.Br.isTrafficEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void moveCamera(CameraUpdate cameraUpdate) {
        try {
            this.Br.moveCamera(cameraUpdate.el());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setBuildingsEnabled(boolean z) {
        try {
            this.Br.setBuildingsEnabled(z);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean setIndoorEnabled(boolean z) {
        try {
            return this.Br.setIndoorEnabled(z);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setInfoWindowAdapter(final InfoWindowAdapter infoWindowAdapter) {
        if (infoWindowAdapter == null) {
            try {
                this.Br.setInfoWindowAdapter((com.google.android.gms.maps.internal.d) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.Br.setInfoWindowAdapter(new d.a() {
                public com.google.android.gms.dynamic.b f(com.google.android.gms.maps.model.internal.d dVar) {
                    return com.google.android.gms.dynamic.c.h(infoWindowAdapter.getInfoWindow(new Marker(dVar)));
                }

                public com.google.android.gms.dynamic.b g(com.google.android.gms.maps.model.internal.d dVar) {
                    return com.google.android.gms.dynamic.c.h(infoWindowAdapter.getInfoContents(new Marker(dVar)));
                }
            });
        }
    }

    public final void setLocationSource(final LocationSource locationSource) {
        if (locationSource == null) {
            try {
                this.Br.setLocationSource((ILocationSourceDelegate) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.Br.setLocationSource(new ILocationSourceDelegate.a() {
                public void activate(final g gVar) {
                    locationSource.activate(new LocationSource.OnLocationChangedListener() {
                        public void onLocationChanged(Location location) {
                            try {
                                gVar.g(com.google.android.gms.dynamic.c.h(location));
                            } catch (RemoteException e) {
                                throw new RuntimeRemoteException(e);
                            }
                        }
                    });
                }

                public void deactivate() {
                    locationSource.deactivate();
                }
            });
        }
    }

    public final void setMapType(int i) {
        try {
            this.Br.setMapType(i);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setMyLocationEnabled(boolean z) {
        try {
            this.Br.setMyLocationEnabled(z);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnCameraChangeListener(final OnCameraChangeListener onCameraChangeListener) {
        if (onCameraChangeListener == null) {
            try {
                this.Br.setOnCameraChangeListener((e) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.Br.setOnCameraChangeListener(new e.a() {
                public void onCameraChange(CameraPosition cameraPosition) {
                    onCameraChangeListener.onCameraChange(cameraPosition);
                }
            });
        }
    }

    public final void setOnInfoWindowClickListener(final OnInfoWindowClickListener onInfoWindowClickListener) {
        if (onInfoWindowClickListener == null) {
            try {
                this.Br.setOnInfoWindowClickListener((com.google.android.gms.maps.internal.f) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.Br.setOnInfoWindowClickListener(new f.a() {
                public void e(com.google.android.gms.maps.model.internal.d dVar) {
                    onInfoWindowClickListener.onInfoWindowClick(new Marker(dVar));
                }
            });
        }
    }

    public final void setOnMapClickListener(final OnMapClickListener onMapClickListener) {
        if (onMapClickListener == null) {
            try {
                this.Br.setOnMapClickListener((h) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.Br.setOnMapClickListener(new h.a() {
                public void onMapClick(LatLng latLng) {
                    onMapClickListener.onMapClick(latLng);
                }
            });
        }
    }

    public void setOnMapLoadedCallback(final OnMapLoadedCallback onMapLoadedCallback) {
        if (onMapLoadedCallback == null) {
            try {
                this.Br.setOnMapLoadedCallback((i) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.Br.setOnMapLoadedCallback(new i.a() {
                public void onMapLoaded() throws RemoteException {
                    onMapLoadedCallback.onMapLoaded();
                }
            });
        }
    }

    public final void setOnMapLongClickListener(final OnMapLongClickListener onMapLongClickListener) {
        if (onMapLongClickListener == null) {
            try {
                this.Br.setOnMapLongClickListener((j) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.Br.setOnMapLongClickListener(new j.a() {
                public void onMapLongClick(LatLng latLng) {
                    onMapLongClickListener.onMapLongClick(latLng);
                }
            });
        }
    }

    public final void setOnMarkerClickListener(final OnMarkerClickListener onMarkerClickListener) {
        if (onMarkerClickListener == null) {
            try {
                this.Br.setOnMarkerClickListener((k) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.Br.setOnMarkerClickListener(new k.a() {
                public boolean a(com.google.android.gms.maps.model.internal.d dVar) {
                    return onMarkerClickListener.onMarkerClick(new Marker(dVar));
                }
            });
        }
    }

    public final void setOnMarkerDragListener(final OnMarkerDragListener onMarkerDragListener) {
        if (onMarkerDragListener == null) {
            try {
                this.Br.setOnMarkerDragListener((l) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.Br.setOnMarkerDragListener(new l.a() {
                public void b(com.google.android.gms.maps.model.internal.d dVar) {
                    onMarkerDragListener.onMarkerDragStart(new Marker(dVar));
                }

                public void c(com.google.android.gms.maps.model.internal.d dVar) {
                    onMarkerDragListener.onMarkerDragEnd(new Marker(dVar));
                }

                public void d(com.google.android.gms.maps.model.internal.d dVar) {
                    onMarkerDragListener.onMarkerDrag(new Marker(dVar));
                }
            });
        }
    }

    public final void setOnMyLocationButtonClickListener(final OnMyLocationButtonClickListener onMyLocationButtonClickListener) {
        if (onMyLocationButtonClickListener == null) {
            try {
                this.Br.setOnMyLocationButtonClickListener((m) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.Br.setOnMyLocationButtonClickListener(new m.a() {
                public boolean onMyLocationButtonClick() throws RemoteException {
                    return onMyLocationButtonClickListener.onMyLocationButtonClick();
                }
            });
        }
    }

    @Deprecated
    public final void setOnMyLocationChangeListener(final OnMyLocationChangeListener onMyLocationChangeListener) {
        if (onMyLocationChangeListener == null) {
            try {
                this.Br.setOnMyLocationChangeListener((n) null);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        } else {
            this.Br.setOnMyLocationChangeListener(new n.a() {
                public void d(com.google.android.gms.dynamic.b bVar) {
                    onMyLocationChangeListener.onMyLocationChange((Location) com.google.android.gms.dynamic.c.b(bVar));
                }
            });
        }
    }

    public final void setPadding(int i, int i2, int i3, int i4) {
        try {
            this.Br.setPadding(i, i2, i3, i4);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setTrafficEnabled(boolean z) {
        try {
            this.Br.setTrafficEnabled(z);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void snapshot(SnapshotReadyCallback snapshotReadyCallback) {
        snapshot(snapshotReadyCallback, (Bitmap) null);
    }

    public final void snapshot(final SnapshotReadyCallback snapshotReadyCallback, Bitmap bitmap) {
        try {
            this.Br.snapshot(new o.a() {
                public void c(com.google.android.gms.dynamic.b bVar) throws RemoteException {
                    snapshotReadyCallback.onSnapshotReady((Bitmap) com.google.android.gms.dynamic.c.b(bVar));
                }

                public void onSnapshotReady(Bitmap bitmap) throws RemoteException {
                    snapshotReadyCallback.onSnapshotReady(bitmap);
                }
            }, (com.google.android.gms.dynamic.c) (bitmap != null ? com.google.android.gms.dynamic.c.h(bitmap) : null));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void stopAnimation() {
        try {
            this.Br.stopAnimation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
