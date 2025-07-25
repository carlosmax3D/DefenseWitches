package com.ansca.corona;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.TransportMediator;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.ansca.corona.maps.MapMarker;
import com.ansca.corona.maps.MapType;
import com.ansca.corona.maps.MapView;
import com.ansca.corona.purchasing.StoreName;
import com.ansca.corona.storage.FileContentProvider;
import com.ansca.corona.storage.FileServices;
import com.naef.jnlua.LuaState;
import com.tapjoy.TJAdUnitConstants;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import jp.stargarage.g2metrics.BuildConfig;

public class ViewManager {
    /* access modifiers changed from: private */
    public AbsoluteLayout myAbsolutePopupLayout = null;
    /* access modifiers changed from: private */
    public AbsoluteLayout myAbsoluteViewLayout = null;
    /* access modifiers changed from: private */
    public ViewGroup myContentView = null;
    /* access modifiers changed from: private */
    public Context myContext;
    /* access modifiers changed from: private */
    public CoronaRuntime myCoronaRuntime;
    /* access modifiers changed from: private */
    public ArrayList<View> myDisplayObjects = new ArrayList<>();
    private Handler myHandler = new Handler(Looper.getMainLooper());
    private FrameLayout myOverlayView = null;

    private class StringObjectHashMap extends HashMap<String, Object> {
        private StringObjectHashMap() {
        }
    }

    public ViewManager(Context context, CoronaRuntime coronaRuntime) {
        this.myContext = context;
        this.myCoronaRuntime = coronaRuntime;
    }

    private void postOnUiThread(Runnable runnable) {
        if (runnable != null) {
            if (this.myHandler == null || this.myHandler.getLooper() == null || this.myHandler.getLooper().getThread() == null || this.myHandler.getLooper().getThread().isAlive()) {
                this.myHandler.post(runnable);
            }
        }
    }

    /* access modifiers changed from: private */
    public void setHardwareAccelerationEnabled(View view, boolean z) {
        int i = 2;
        if (view != null && Build.VERSION.SDK_INT >= 11) {
            try {
                Method method = View.class.getMethod("setLayerType", new Class[]{Integer.TYPE, Paint.class});
                if (!z) {
                    i = 1;
                }
                method.invoke(view, new Object[]{Integer.valueOf(i), null});
            } catch (Exception e) {
            }
        }
    }

    public int addMapMarker(final int i, final MapMarker mapMarker) {
        MapView mapView = (MapView) getDisplayObjectById(MapView.class, i);
        if (mapView == null) {
            return 0;
        }
        int newMarkerId = mapView.getNewMarkerId();
        mapMarker.setMarkerId(newMarkerId);
        postOnUiThread(new Runnable() {
            public void run() {
                MapView mapView = (MapView) ViewManager.this.getDisplayObjectById(MapView.class, i);
                if (mapView != null) {
                    mapView.addMarker(mapMarker);
                }
            }
        });
        return newMarkerId;
    }

    public void addMapView(int i, int i2, int i3, int i4, int i5) {
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext != null) {
            applicationContext.enforceCallingOrSelfPermission("android.permission.INTERNET", (String) null);
        }
        final int i6 = i;
        final int i7 = i4;
        final int i8 = i5;
        final int i9 = i2;
        final int i10 = i3;
        postOnUiThread(new Runnable() {
            public void run() {
                if (ViewManager.this.myContext != null && ViewManager.this.myAbsoluteViewLayout != null) {
                    MapView mapView = new MapView(ViewManager.this.myContext, ViewManager.this.myCoronaRuntime, ViewManager.this.myCoronaRuntime.getController());
                    mapView.setId(i6);
                    mapView.setTag(new StringObjectHashMap());
                    ViewManager.this.myAbsoluteViewLayout.addView(mapView, new AbsoluteLayout.LayoutParams(i7, i8, i9, i10));
                    mapView.bringToFront();
                    synchronized (ViewManager.this.myDisplayObjects) {
                        ViewManager.this.myDisplayObjects.add(mapView);
                    }
                }
            }
        });
    }

    public void addTextView(int i, int i2, int i3, int i4, int i5, boolean z) {
        final int i6 = i4;
        final int i7 = i5;
        final int i8 = i2;
        final int i9 = i3;
        final int i10 = i;
        final boolean z2 = z;
        postOnUiThread(new Runnable() {
            public void run() {
                if (ViewManager.this.myAbsoluteViewLayout != null) {
                    CoronaEditText coronaEditText = new CoronaEditText(ViewManager.this.myContext, ViewManager.this.myCoronaRuntime);
                    ViewManager.this.myAbsoluteViewLayout.addView(coronaEditText, new AbsoluteLayout.LayoutParams(i6 + coronaEditText.getBorderPaddingLeft() + coronaEditText.getBorderPaddingRight(), i7 + coronaEditText.getBorderPaddingTop() + coronaEditText.getBorderPaddingBottom(), i8 - coronaEditText.getBorderPaddingLeft(), i9 - coronaEditText.getBorderPaddingTop()));
                    coronaEditText.setId(i10);
                    coronaEditText.setTag(new StringObjectHashMap());
                    coronaEditText.bringToFront();
                    coronaEditText.setTextColor(-16777216);
                    coronaEditText.setSingleLine(z2);
                    coronaEditText.setImeOptions(6);
                    coronaEditText.setGravity((coronaEditText.getGravity() & 7) | (z2 ? 16 : 48));
                    coronaEditText.setNextFocusDownId(coronaEditText.getId());
                    coronaEditText.setNextFocusUpId(coronaEditText.getId());
                    coronaEditText.setNextFocusLeftId(coronaEditText.getId());
                    coronaEditText.setNextFocusRightId(coronaEditText.getId());
                    synchronized (ViewManager.this.myDisplayObjects) {
                        ViewManager.this.myDisplayObjects.add(coronaEditText);
                    }
                }
            }
        });
    }

    public void addVideoView(int i, int i2, int i3, int i4, int i5) {
        final int i6 = i;
        final int i7 = i4;
        final int i8 = i5;
        final int i9 = i2;
        final int i10 = i3;
        postOnUiThread(new Runnable() {
            public void run() {
                AbsoluteLayout access$000;
                ViewGroup.LayoutParams layoutParams;
                if (ViewManager.this.myContext != null && (access$000 = ViewManager.this.myAbsoluteViewLayout) != null) {
                    CoronaVideoView coronaVideoView = new CoronaVideoView(ViewManager.this.myContext, ViewManager.this.myCoronaRuntime);
                    coronaVideoView.setId(i6);
                    coronaVideoView.setTag(new StringObjectHashMap());
                    if (i7 <= 0 || i8 <= 0) {
                        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -1);
                        layoutParams2.addRule(13);
                        layoutParams = layoutParams2;
                    } else {
                        layoutParams = new AbsoluteLayout.LayoutParams(i7, i8, i9, i10);
                    }
                    access$000.addView(coronaVideoView, layoutParams);
                    coronaVideoView.setZOrderOnTop(true);
                    synchronized (ViewManager.this.myDisplayObjects) {
                        ViewManager.this.myDisplayObjects.add(coronaVideoView);
                    }
                }
            }
        });
    }

    public void addWebView(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
        final boolean z3 = z;
        final int i6 = i;
        final boolean z4 = z2;
        final int i7 = i4;
        final int i8 = i5;
        final int i9 = i2;
        final int i10 = i3;
        postOnUiThread(new Runnable() {
            public void run() {
                ViewGroup.LayoutParams layoutParams;
                if (ViewManager.this.myContext != null) {
                    AbsoluteLayout access$700 = z3 ? ViewManager.this.myAbsolutePopupLayout : ViewManager.this.myAbsoluteViewLayout;
                    if (access$700 != null) {
                        CoronaWebView coronaWebView = new CoronaWebView(ViewManager.this.myContext, ViewManager.this.myCoronaRuntime);
                        coronaWebView.setId(i6);
                        coronaWebView.setTag(new StringObjectHashMap());
                        coronaWebView.setBackKeySupported(z3);
                        coronaWebView.setAutoCloseEnabled(z4);
                        if (i7 <= 0 || i8 <= 0) {
                            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -1);
                            layoutParams2.addRule(13);
                            layoutParams = layoutParams2;
                        } else {
                            layoutParams = new AbsoluteLayout.LayoutParams(i7, i8, i9, i10);
                        }
                        access$700.addView(coronaWebView, layoutParams);
                        coronaWebView.bringToFront();
                        synchronized (ViewManager.this.myDisplayObjects) {
                            ViewManager.this.myDisplayObjects.add(coronaWebView);
                        }
                    }
                }
            }
        });
    }

    public void destroy() {
        destroyAllDisplayObjects();
    }

    public void destroyAllDisplayObjects() {
        View view;
        do {
            synchronized (this.myDisplayObjects) {
                view = this.myDisplayObjects.isEmpty() ? null : this.myDisplayObjects.get(this.myDisplayObjects.size() - 1);
            }
            if (view != null) {
                destroyDisplayObject(view.getId());
                continue;
            }
        } while (view != null);
    }

    public void destroyDisplayObject(int i) {
        final View displayObjectById = getDisplayObjectById(i);
        if (displayObjectById != null) {
            synchronized (this.myDisplayObjects) {
                this.myDisplayObjects.remove(displayObjectById);
            }
            postOnUiThread(new Runnable() {
                public void run() {
                    ViewParent parent = displayObjectById.getParent();
                    if (parent != null && (parent instanceof ViewGroup)) {
                        ((ViewGroup) parent).removeView(displayObjectById);
                    }
                    if (displayObjectById instanceof WebView) {
                        ((WebView) displayObjectById).stopLoading();
                        ((WebView) displayObjectById).destroy();
                    } else if (displayObjectById instanceof MapView) {
                        ((MapView) displayObjectById).destroy();
                    }
                    displayObjectById.setId(0);
                }
            });
        }
    }

    public void displayObjectUpdateScreenBounds(int i, int i2, int i3, int i4, int i5) {
        final int i6 = i;
        final int i7 = i4;
        final int i8 = i5;
        final int i9 = i2;
        final int i10 = i3;
        postOnUiThread(new Runnable() {
            public void run() {
                AbsoluteLayout.LayoutParams layoutParams;
                View displayObjectById = ViewManager.this.getDisplayObjectById(i6);
                if (displayObjectById != null) {
                    if (displayObjectById instanceof CoronaEditText) {
                        CoronaEditText coronaEditText = (CoronaEditText) displayObjectById;
                        layoutParams = new AbsoluteLayout.LayoutParams(i7 + coronaEditText.getBorderPaddingLeft() + coronaEditText.getBorderPaddingRight(), i8 + coronaEditText.getBorderPaddingTop() + coronaEditText.getBorderPaddingBottom(), i9 - coronaEditText.getBorderPaddingLeft(), i10 - coronaEditText.getBorderPaddingTop());
                    } else {
                        layoutParams = new AbsoluteLayout.LayoutParams(i7, i8, i9, i10);
                    }
                    displayObjectById.setLayoutParams(layoutParams);
                }
            }
        });
    }

    public AbsoluteLayout getAbsolutePopupLayout() {
        return this.myAbsolutePopupLayout;
    }

    public AbsoluteLayout getAbsoluteViewLayout() {
        return this.myAbsoluteViewLayout;
    }

    public ViewGroup getContentView() {
        return this.myContentView;
    }

    public float getDisplayObjectAlpha(int i) {
        View displayObjectById = getDisplayObjectById(i);
        if (displayObjectById == null) {
            return 1.0f;
        }
        Object tag = displayObjectById.getTag();
        if (!(tag instanceof StringObjectHashMap)) {
            return 1.0f;
        }
        Object obj = ((StringObjectHashMap) tag).get("alpha");
        if (obj instanceof Float) {
            return ((Float) obj).floatValue();
        }
        return 1.0f;
    }

    public boolean getDisplayObjectBackground(int i) {
        boolean z = false;
        View displayObjectById = getDisplayObjectById(i);
        if (displayObjectById == null) {
            return false;
        }
        if (displayObjectById instanceof MapView) {
            z = true;
        } else if (displayObjectById instanceof WebView) {
            z = CoronaWebView.getBackgroundColorFrom((WebView) displayObjectById) != 0;
        } else {
            Drawable background = displayObjectById.getBackground();
            if (background instanceof ColorDrawable) {
                z = ((ColorDrawable) background).getAlpha() > 0;
            } else if (background instanceof ShapeDrawable) {
                Paint paint = ((ShapeDrawable) background).getPaint();
                if (paint != null) {
                    z = paint.getColor() != 0;
                }
            } else if (background != null) {
                z = true;
            }
        }
        return z;
    }

    public View getDisplayObjectById(int i) {
        synchronized (this.myDisplayObjects) {
            Iterator<View> it = this.myDisplayObjects.iterator();
            while (it.hasNext()) {
                View next = it.next();
                if (next != null && next.getId() == i) {
                    return next;
                }
            }
            return null;
        }
    }

    public <T extends View> T getDisplayObjectById(Class<T> cls, int i) {
        synchronized (this.myDisplayObjects) {
            Iterator<View> it = this.myDisplayObjects.iterator();
            while (it.hasNext()) {
                T t = (View) it.next();
                if (cls.isInstance(t) && t.getId() == i) {
                    return t;
                }
            }
            return null;
        }
    }

    public boolean getDisplayObjectVisible(int i) {
        View displayObjectById = getDisplayObjectById(i);
        return displayObjectById != null && displayObjectById.getVisibility() == 0;
    }

    public MapType getMapType(int i) {
        MapView mapView = (MapView) getDisplayObjectById(MapView.class, i);
        return mapView == null ? MapType.STANDARD : mapView.getMapType();
    }

    public FrameLayout getOverlayView() {
        return this.myOverlayView;
    }

    public String getTextViewAlign(int i) {
        CoronaEditText coronaEditText = (CoronaEditText) getDisplayObjectById(CoronaEditText.class, i);
        return coronaEditText == null ? BuildConfig.FLAVOR : coronaEditText.getTextViewAlign();
    }

    public int getTextViewColor(int i) {
        CoronaEditText coronaEditText = (CoronaEditText) getDisplayObjectById(CoronaEditText.class, i);
        if (coronaEditText == null) {
            return 0;
        }
        return coronaEditText.getTextViewColor();
    }

    public String getTextViewInputType(int i) {
        CoronaEditText coronaEditText = (CoronaEditText) getDisplayObjectById(CoronaEditText.class, i);
        return coronaEditText == null ? "error" : coronaEditText.getTextViewInputType();
    }

    public boolean getTextViewPassword(int i) {
        CoronaEditText coronaEditText = (CoronaEditText) getDisplayObjectById(CoronaEditText.class, i);
        if (coronaEditText == null) {
            return false;
        }
        return coronaEditText.getTextViewPassword();
    }

    public String getTextViewPlaceholder(int i) {
        CharSequence hint;
        CoronaEditText coronaEditText = (CoronaEditText) getDisplayObjectById(CoronaEditText.class, i);
        if (coronaEditText == null || (hint = coronaEditText.getHint()) == null) {
            return null;
        }
        return hint.toString();
    }

    public float getTextViewSize(int i) {
        CoronaEditText coronaEditText = (CoronaEditText) getDisplayObjectById(CoronaEditText.class, i);
        if (coronaEditText == null) {
            return 0.0f;
        }
        return coronaEditText.getTextViewSize();
    }

    public String getTextViewText(int i) {
        CoronaEditText coronaEditText = (CoronaEditText) getDisplayObjectById(CoronaEditText.class, i);
        return coronaEditText == null ? BuildConfig.FLAVOR : coronaEditText.getTextString();
    }

    public boolean goBack() {
        boolean z = false;
        if (this.myDisplayObjects != null) {
            KeyEvent keyEvent = new KeyEvent(0, 4);
            synchronized (this.myDisplayObjects) {
                Iterator<View> it = this.myDisplayObjects.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    View next = it.next();
                    if ((next instanceof CoronaWebView) && next.onKeyDown(keyEvent.getKeyCode(), keyEvent)) {
                        z = true;
                        break;
                    }
                }
            }
        }
        return z;
    }

    public boolean hasDisplayObjectWithId(int i) {
        return getDisplayObjectById(i) != null;
    }

    public <T extends View> boolean hasDisplayObjectWithId(Class<T> cls, int i) {
        return getDisplayObjectById(cls, i) != null;
    }

    public boolean isCurrentLocationVisibleInMap(int i) {
        MapView mapView = (MapView) getDisplayObjectById(MapView.class, i);
        if (mapView == null) {
            return false;
        }
        return mapView.isCurrentLocationVisible();
    }

    public boolean isMapScrollEnabled(int i) {
        MapView mapView = (MapView) getDisplayObjectById(MapView.class, i);
        if (mapView == null) {
            return false;
        }
        return mapView.isScrollEnabled();
    }

    public boolean isMapZoomEnabled(int i) {
        MapView mapView = (MapView) getDisplayObjectById(MapView.class, i);
        if (mapView == null) {
            return false;
        }
        return mapView.isZoomEnabled();
    }

    public boolean isTextViewEditable(int i) {
        CoronaEditText coronaEditText = (CoronaEditText) getDisplayObjectById(CoronaEditText.class, i);
        if (coronaEditText == null) {
            return true;
        }
        return coronaEditText.isEnabled();
    }

    public boolean isTextViewSingleLine(int i) {
        CoronaEditText coronaEditText = (CoronaEditText) getDisplayObjectById(CoronaEditText.class, i);
        return coronaEditText == null || (coronaEditText.getInputType() & 131072) == 0;
    }

    public void onScreenLockStateChanged(boolean z) {
        synchronized (this.myDisplayObjects) {
            Iterator<View> it = this.myDisplayObjects.iterator();
            while (it.hasNext()) {
                View next = it.next();
                if (next instanceof CoronaVideoView) {
                    ((CoronaVideoView) next).setScreenLocked(z);
                    ((CoronaVideoView) next).start();
                }
            }
        }
    }

    public int pushMapCurrentLocationToLua(int i, long j) {
        LuaState luaState;
        CoronaRuntime coronaRuntime = this.myCoronaRuntime;
        if (coronaRuntime == null || (luaState = coronaRuntime.getLuaState()) == null || CoronaRuntimeProvider.getLuaStateMemoryAddress(luaState) != j) {
            return 0;
        }
        Location location = null;
        MapView mapView = (MapView) getDisplayObjectById(MapView.class, i);
        if (mapView != null) {
            location = mapView.getCurrentLocation();
        }
        luaState.newTable(0, 0);
        int top = luaState.getTop();
        if (location != null) {
            luaState.pushNumber(location.getLatitude());
            luaState.setField(top, "latitude");
            luaState.pushNumber(location.getLongitude());
            luaState.setField(top, "longitude");
            luaState.pushNumber(location.getAltitude());
            luaState.setField(top, TJAdUnitConstants.String.ALTITUDE);
            luaState.pushNumber((double) location.getAccuracy());
            luaState.setField(top, "accuracy");
            luaState.pushNumber((double) location.getSpeed());
            luaState.setField(top, TJAdUnitConstants.String.SPEED);
            luaState.pushNumber((double) location.getBearing());
            luaState.setField(top, "direction");
            luaState.pushNumber((double) location.getTime());
            luaState.setField(top, "time");
            luaState.pushBoolean(true);
            luaState.setField(top, "isUpdating");
        } else {
            luaState.pushInteger(-1);
            luaState.setField(top, "errorCode");
            luaState.pushString("Current location is unknown.");
            luaState.setField(top, "errorMessage");
        }
        return 1;
    }

    public void removeAllMapViewMarkers(final int i) {
        postOnUiThread(new Runnable() {
            public void run() {
                MapView mapView = (MapView) ViewManager.this.getDisplayObjectById(MapView.class, i);
                if (mapView != null) {
                    mapView.removeAllMarkers();
                }
            }
        });
    }

    public void removeMapMarker(final int i, final int i2) {
        postOnUiThread(new Runnable() {
            public void run() {
                MapView mapView = (MapView) ViewManager.this.getDisplayObjectById(MapView.class, i);
                if (mapView != null) {
                    mapView.removeMarker(i2);
                }
            }
        });
    }

    public void requestWebViewDeleteCookies(int i) {
        postOnUiThread(new Runnable() {
            public void run() {
                CookieManager.getInstance().removeAllCookie();
            }
        });
    }

    public void requestWebViewGoBack(final int i) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaWebView coronaWebView = (CoronaWebView) ViewManager.this.getDisplayObjectById(CoronaWebView.class, i);
                if (coronaWebView != null) {
                    coronaWebView.goBack();
                    coronaWebView.requestFocus(TransportMediator.KEYCODE_MEDIA_RECORD);
                }
            }
        });
    }

    public void requestWebViewGoForward(final int i) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaWebView coronaWebView = (CoronaWebView) ViewManager.this.getDisplayObjectById(CoronaWebView.class, i);
                if (coronaWebView != null) {
                    coronaWebView.goForward();
                    coronaWebView.requestFocus(TransportMediator.KEYCODE_MEDIA_RECORD);
                }
            }
        });
    }

    public void requestWebViewLoadUrl(final int i, final String str) {
        postOnUiThread(new Runnable() {
            public void run() {
                Uri createContentUriForFile;
                CoronaWebView coronaWebView = (CoronaWebView) ViewManager.this.getDisplayObjectById(CoronaWebView.class, i);
                if (coronaWebView != null) {
                    String str = str;
                    Context applicationContext = CoronaEnvironment.getApplicationContext();
                    if (!(applicationContext == null || !new FileServices(applicationContext).doesAssetFileExist(str) || (createContentUriForFile = FileContentProvider.createContentUriForFile(applicationContext, str)) == null)) {
                        str = createContentUriForFile.toString();
                    }
                    coronaWebView.loadUrl(str);
                    coronaWebView.requestFocus(TransportMediator.KEYCODE_MEDIA_RECORD);
                }
            }
        });
    }

    public void requestWebViewReload(final int i) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaWebView coronaWebView = (CoronaWebView) ViewManager.this.getDisplayObjectById(CoronaWebView.class, i);
                if (coronaWebView != null) {
                    coronaWebView.reload();
                    coronaWebView.requestFocus(TransportMediator.KEYCODE_MEDIA_RECORD);
                }
            }
        });
    }

    public void requestWebViewStop(final int i) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaWebView coronaWebView = (CoronaWebView) ViewManager.this.getDisplayObjectById(CoronaWebView.class, i);
                if (coronaWebView != null) {
                    coronaWebView.stopLoading();
                }
            }
        });
    }

    public void resume() {
        synchronized (this.myDisplayObjects) {
            Iterator<View> it = this.myDisplayObjects.iterator();
            while (it.hasNext()) {
                View next = it.next();
                if (next instanceof MapView) {
                    ((MapView) next).setCurrentLocationTrackingEnabled(true);
                }
                if (next instanceof CoronaVideoView) {
                    ((CoronaVideoView) next).setActivityResumed(true);
                    ((CoronaVideoView) next).start();
                }
            }
        }
    }

    public void setDisplayObjectAlpha(final int i, final float f) {
        postOnUiThread(new Runnable() {
            public void run() {
                View displayObjectById = ViewManager.this.getDisplayObjectById(i);
                if (displayObjectById != null) {
                    float f = f;
                    if (f < 0.0f) {
                        f = 0.0f;
                    } else if (f > 1.0f) {
                        f = 1.0f;
                    }
                    Object tag = displayObjectById.getTag();
                    if (tag instanceof StringObjectHashMap) {
                        ((StringObjectHashMap) tag).put("alpha", Float.valueOf(f));
                    }
                    if (f >= 0.9999f || displayObjectById.getVisibility() != 0) {
                        displayObjectById.setAnimation((Animation) null);
                        return;
                    }
                    if ((displayObjectById instanceof WebView) || (displayObjectById instanceof MapView)) {
                        ViewManager.this.setHardwareAccelerationEnabled(displayObjectById, false);
                    }
                    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, f);
                    alphaAnimation.setDuration(0);
                    alphaAnimation.setFillAfter(true);
                    displayObjectById.startAnimation(alphaAnimation);
                }
            }
        });
    }

    public void setDisplayObjectBackground(final int i, final boolean z) {
        postOnUiThread(new Runnable() {
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: android.graphics.drawable.Drawable} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: com.ansca.corona.ViewManager$StringObjectHashMap} */
            /* JADX WARNING: Multi-variable type inference failed */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r9 = this;
                    r6 = 0
                    r1 = -1
                    com.ansca.corona.ViewManager r7 = com.ansca.corona.ViewManager.this
                    int r8 = r2
                    android.view.View r5 = r7.getDisplayObjectById(r8)
                    if (r5 != 0) goto L_0x000d
                L_0x000c:
                    return
                L_0x000d:
                    boolean r7 = r5 instanceof com.ansca.corona.maps.MapView
                    if (r7 != 0) goto L_0x000c
                    boolean r7 = r5 instanceof android.webkit.WebView
                    if (r7 == 0) goto L_0x0027
                    boolean r7 = r3
                    if (r7 == 0) goto L_0x0025
                L_0x0019:
                    r5.setBackgroundColor(r1)
                    com.ansca.corona.ViewManager r7 = com.ansca.corona.ViewManager.this
                    r7.setHardwareAccelerationEnabled(r5, r6)
                L_0x0021:
                    r5.invalidate()
                    goto L_0x000c
                L_0x0025:
                    r1 = r6
                    goto L_0x0019
                L_0x0027:
                    android.graphics.drawable.Drawable r0 = r5.getBackground()
                    boolean r6 = r3
                    if (r6 == 0) goto L_0x0031
                    if (r0 != 0) goto L_0x000c
                L_0x0031:
                    boolean r6 = r3
                    if (r6 != 0) goto L_0x0037
                    if (r0 == 0) goto L_0x000c
                L_0x0037:
                    r2 = 0
                    java.lang.Object r3 = r5.getTag()
                    boolean r6 = r3 instanceof com.ansca.corona.ViewManager.StringObjectHashMap
                    if (r6 == 0) goto L_0x0043
                    r2 = r3
                    com.ansca.corona.ViewManager$StringObjectHashMap r2 = (com.ansca.corona.ViewManager.StringObjectHashMap) r2
                L_0x0043:
                    if (r0 != 0) goto L_0x0054
                    if (r2 == 0) goto L_0x0054
                    java.lang.String r6 = "backgroundDrawable"
                    java.lang.Object r4 = r2.get(r6)
                    boolean r6 = r4 instanceof android.graphics.drawable.Drawable
                    if (r6 == 0) goto L_0x0054
                    r0 = r4
                    android.graphics.drawable.Drawable r0 = (android.graphics.drawable.Drawable) r0
                L_0x0054:
                    boolean r6 = r3
                    if (r6 == 0) goto L_0x006d
                    if (r0 != 0) goto L_0x006d
                    r5.setBackgroundColor(r1)
                    android.graphics.drawable.Drawable r0 = r5.getBackground()
                L_0x0061:
                    boolean r6 = r3
                    if (r6 != 0) goto L_0x0021
                    if (r2 == 0) goto L_0x0021
                    java.lang.String r6 = "backgroundDrawable"
                    r2.put(r6, r0)
                    goto L_0x0021
                L_0x006d:
                    boolean r6 = r3
                    if (r6 == 0) goto L_0x0076
                    r6 = r0
                L_0x0072:
                    r5.setBackgroundDrawable(r6)
                    goto L_0x0061
                L_0x0076:
                    r6 = 0
                    goto L_0x0072
                */
                throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.ViewManager.AnonymousClass19.run():void");
            }
        });
    }

    public void setDisplayObjectVisible(final int i, final boolean z) {
        postOnUiThread(new Runnable() {
            public void run() {
                View displayObjectById = ViewManager.this.getDisplayObjectById(i);
                if (displayObjectById != null) {
                    displayObjectById.setVisibility(z ? 0 : 8);
                    if (z) {
                        ViewManager.this.setDisplayObjectAlpha(i, ViewManager.this.getDisplayObjectAlpha(i));
                    } else {
                        displayObjectById.setAnimation((Animation) null);
                    }
                }
            }
        });
    }

    public void setGLView(View view) {
        this.myContentView = new FrameLayout(this.myContext);
        this.myContentView.setFocusable(true);
        this.myContentView.setFocusableInTouchMode(true);
        this.myContentView.addView(view);
        this.myOverlayView = new FrameLayout(this.myContext);
        this.myAbsoluteViewLayout = new AbsoluteLayout(this.myContext);
        this.myOverlayView.addView(this.myAbsoluteViewLayout);
        this.myContentView.addView(this.myOverlayView);
        this.myAbsolutePopupLayout = new AbsoluteLayout(this.myContext);
        this.myContentView.addView(this.myAbsolutePopupLayout);
    }

    public void setMapCenter(int i, double d, double d2, boolean z) {
        final int i2 = i;
        final double d3 = d;
        final double d4 = d2;
        final boolean z2 = z;
        postOnUiThread(new Runnable() {
            public void run() {
                MapView mapView = (MapView) ViewManager.this.getDisplayObjectById(MapView.class, i2);
                if (mapView != null) {
                    mapView.setCenter(d3, d4, z2);
                }
            }
        });
    }

    public void setMapRegion(int i, double d, double d2, double d3, double d4, boolean z) {
        final int i2 = i;
        final double d5 = d;
        final double d6 = d2;
        final double d7 = d3;
        final double d8 = d4;
        final boolean z2 = z;
        postOnUiThread(new Runnable() {
            public void run() {
                MapView mapView = (MapView) ViewManager.this.getDisplayObjectById(MapView.class, i2);
                if (mapView != null) {
                    mapView.setRegion(d5, d6, d7, d8, z2);
                }
            }
        });
    }

    public void setMapScrollEnabled(final int i, final boolean z) {
        postOnUiThread(new Runnable() {
            public void run() {
                MapView mapView = (MapView) ViewManager.this.getDisplayObjectById(MapView.class, i);
                if (mapView != null) {
                    mapView.setScrollEnabled(z);
                }
            }
        });
    }

    public void setMapType(final int i, final MapType mapType) {
        postOnUiThread(new Runnable() {
            public void run() {
                MapView mapView = (MapView) ViewManager.this.getDisplayObjectById(MapView.class, i);
                if (mapView != null) {
                    mapView.setMapType(mapType);
                }
            }
        });
    }

    public void setMapZoomEnabled(final int i, final boolean z) {
        postOnUiThread(new Runnable() {
            public void run() {
                MapView mapView = (MapView) ViewManager.this.getDisplayObjectById(MapView.class, i);
                if (mapView != null) {
                    mapView.setZoomEnabled(z);
                }
            }
        });
    }

    public void setTextPlaceholder(final int i, final String str) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaEditText coronaEditText = (CoronaEditText) ViewManager.this.getDisplayObjectById(CoronaEditText.class, i);
                if (coronaEditText != null) {
                    coronaEditText.setHint(str);
                }
            }
        });
    }

    public void setTextReturnKey(final int i, final String str) {
        postOnUiThread(new Runnable() {
            public void run() {
                int i = 6;
                if (str.equals("go")) {
                    i = 2;
                } else if (str.equals("next")) {
                    i = 5;
                } else if (str.equals(StoreName.NONE)) {
                    i = 1;
                } else if (str.equals("search")) {
                    i = 3;
                } else if (str.equals("send")) {
                    i = 4;
                }
                CoronaEditText coronaEditText = (CoronaEditText) ViewManager.this.getDisplayObjectById(CoronaEditText.class, i);
                if (coronaEditText != null) {
                    coronaEditText.setImeOptions(i);
                }
            }
        });
    }

    public void setTextSelection(final int i, final int i2, final int i3) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaEditText coronaEditText = (CoronaEditText) ViewManager.this.getDisplayObjectById(CoronaEditText.class, i);
                if (coronaEditText != null) {
                    int length = coronaEditText.getText().length();
                    int i = i2;
                    int i2 = i3;
                    if (i > length) {
                        i = length;
                        i2 = length;
                    }
                    if (i2 > length) {
                        i2 = length;
                    }
                    if (i < 0) {
                        i = 0;
                    }
                    if (i2 < 0) {
                        i2 = 0;
                    }
                    if (i > i2) {
                        i = i2;
                    }
                    coronaEditText.setSelection(i, i2);
                }
            }
        });
    }

    public void setTextViewAlign(final int i, final String str) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaEditText coronaEditText = (CoronaEditText) ViewManager.this.getDisplayObjectById(CoronaEditText.class, i);
                if (coronaEditText != null) {
                    coronaEditText.setTextViewAlign(str);
                }
            }
        });
    }

    public void setTextViewColor(final int i, final int i2) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaEditText coronaEditText = (CoronaEditText) ViewManager.this.getDisplayObjectById(CoronaEditText.class, i);
                if (coronaEditText != null) {
                    coronaEditText.setTextViewColor(i2);
                }
            }
        });
    }

    public void setTextViewEditable(final int i, final boolean z) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaEditText coronaEditText = (CoronaEditText) ViewManager.this.getDisplayObjectById(CoronaEditText.class, i);
                if (coronaEditText != null) {
                    coronaEditText.setReadOnly(!z);
                    coronaEditText.setFocusable(z);
                    coronaEditText.setFocusableInTouchMode(z);
                }
            }
        });
    }

    public void setTextViewFocus(final int i, final boolean z) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaEditText coronaEditText = null;
                if (i != 0) {
                    coronaEditText = (CoronaEditText) ViewManager.this.getDisplayObjectById(CoronaEditText.class, i);
                }
                if (coronaEditText == null || !z) {
                    ViewManager.this.myContentView.requestFocus();
                    ((InputMethodManager) ViewManager.this.myContext.getSystemService("input_method")).hideSoftInputFromWindow(ViewManager.this.myContentView.getApplicationWindowToken(), 0);
                    return;
                }
                coronaEditText.requestFocus();
                ((InputMethodManager) ViewManager.this.myContext.getSystemService("input_method")).showSoftInput(coronaEditText, 2);
            }
        });
    }

    public void setTextViewFont(int i, String str, float f, boolean z) {
        final int i2 = i;
        final String str2 = str;
        final float f2 = f;
        final boolean z2 = z;
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaEditText coronaEditText = (CoronaEditText) ViewManager.this.getDisplayObjectById(CoronaEditText.class, i2);
                if (coronaEditText != null) {
                    coronaEditText.setTextViewFont(str2, f2, z2);
                }
            }
        });
    }

    public void setTextViewInputType(final int i, final String str) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaEditText coronaEditText = (CoronaEditText) ViewManager.this.getDisplayObjectById(CoronaEditText.class, i);
                if (coronaEditText != null) {
                    coronaEditText.setTextViewInputType(str);
                }
            }
        });
    }

    public void setTextViewPassword(final int i, final boolean z) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaEditText coronaEditText = (CoronaEditText) ViewManager.this.getDisplayObjectById(CoronaEditText.class, i);
                if (coronaEditText != null) {
                    coronaEditText.setTextViewPassword(z);
                }
            }
        });
    }

    public void setTextViewSingleLine(final int i, final boolean z) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaEditText coronaEditText = (CoronaEditText) ViewManager.this.getDisplayObjectById(CoronaEditText.class, i);
                if (coronaEditText != null) {
                    coronaEditText.setSingleLine(z);
                }
            }
        });
    }

    public void setTextViewSize(final int i, final float f) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaEditText coronaEditText = (CoronaEditText) ViewManager.this.getDisplayObjectById(CoronaEditText.class, i);
                if (coronaEditText != null) {
                    coronaEditText.setTextViewSize(f);
                }
            }
        });
    }

    public void setTextViewText(final int i, final String str) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaEditText coronaEditText = (CoronaEditText) ViewManager.this.getDisplayObjectById(CoronaEditText.class, i);
                if (coronaEditText != null) {
                    int selectionStart = coronaEditText.getSelectionStart();
                    int selectionEnd = coronaEditText.getSelectionEnd();
                    coronaEditText.setText(str);
                    int length = coronaEditText.getText().length();
                    if (selectionStart > length) {
                        selectionStart = length;
                    }
                    if (selectionEnd > length) {
                        selectionEnd = length;
                    }
                    coronaEditText.setSelection(selectionStart, selectionEnd);
                }
            }
        });
    }

    public void suspend() {
        synchronized (this.myDisplayObjects) {
            Iterator<View> it = this.myDisplayObjects.iterator();
            while (it.hasNext()) {
                View next = it.next();
                if (next instanceof MapView) {
                    ((MapView) next).setCurrentLocationTrackingEnabled(false);
                }
                if (next instanceof CoronaVideoView) {
                    ((CoronaVideoView) next).pause();
                    ((CoronaVideoView) next).setActivityResumed(false);
                }
            }
        }
    }

    public int videoViewGetCurrentTime(int i) {
        CoronaVideoView coronaVideoView = (CoronaVideoView) getDisplayObjectById(CoronaVideoView.class, i);
        if (coronaVideoView != null) {
            return coronaVideoView.getCurrentPosition() / 1000;
        }
        return 0;
    }

    public boolean videoViewGetIsMuted(int i) {
        CoronaVideoView coronaVideoView = (CoronaVideoView) getDisplayObjectById(CoronaVideoView.class, i);
        if (coronaVideoView != null) {
            return coronaVideoView.isMuted();
        }
        return false;
    }

    public boolean videoViewGetIsTouchTogglesPlay(int i) {
        CoronaVideoView coronaVideoView = (CoronaVideoView) getDisplayObjectById(CoronaVideoView.class, i);
        if (coronaVideoView != null) {
            return coronaVideoView.isTouchTogglesPlay();
        }
        return false;
    }

    public int videoViewGetTotalTime(int i) {
        CoronaVideoView coronaVideoView = (CoronaVideoView) getDisplayObjectById(CoronaVideoView.class, i);
        if (coronaVideoView != null) {
            return coronaVideoView.getDuration() / 1000;
        }
        return 0;
    }

    public void videoViewLoad(final int i, final String str) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaVideoView coronaVideoView = (CoronaVideoView) ViewManager.this.getDisplayObjectById(CoronaVideoView.class, i);
                MediaManager mediaManager = ViewManager.this.myCoronaRuntime.getController().getMediaManager();
                if (coronaVideoView != null && mediaManager != null) {
                    coronaVideoView.setVideoURI(MediaManager.createVideoURLFromString(str, ViewManager.this.myContext));
                }
            }
        });
    }

    public void videoViewMute(final int i, final boolean z) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaVideoView coronaVideoView = (CoronaVideoView) ViewManager.this.getDisplayObjectById(CoronaVideoView.class, i);
                if (coronaVideoView != null) {
                    coronaVideoView.mute(z);
                }
            }
        });
    }

    public void videoViewPause(final int i) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaVideoView coronaVideoView = (CoronaVideoView) ViewManager.this.getDisplayObjectById(CoronaVideoView.class, i);
                if (coronaVideoView != null) {
                    coronaVideoView.pause();
                }
            }
        });
    }

    public void videoViewPlay(final int i) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaVideoView coronaVideoView = (CoronaVideoView) ViewManager.this.getDisplayObjectById(CoronaVideoView.class, i);
                if (coronaVideoView != null) {
                    coronaVideoView.start();
                }
            }
        });
    }

    public void videoViewSeek(final int i, final int i2) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaVideoView coronaVideoView = (CoronaVideoView) ViewManager.this.getDisplayObjectById(CoronaVideoView.class, i);
                if (coronaVideoView != null) {
                    coronaVideoView.seekTo(i2 * 1000);
                }
            }
        });
    }

    public void videoViewTouchTogglesPlay(final int i, final boolean z) {
        postOnUiThread(new Runnable() {
            public void run() {
                CoronaVideoView coronaVideoView = (CoronaVideoView) ViewManager.this.getDisplayObjectById(CoronaVideoView.class, i);
                if (coronaVideoView != null) {
                    coronaVideoView.touchTogglesPlay(z);
                }
            }
        });
    }
}
