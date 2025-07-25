package com.tapjoy.mraid.controller;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.URLUtil;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyLog;
import com.tapjoy.mraid.controller.Abstract;
import com.tapjoy.mraid.util.ConfigBroadcastReceiver;
import com.tapjoy.mraid.view.MraidView;
import java.util.List;
import jp.co.voyagegroup.android.fluct.jar.util.FluctConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class Display extends Abstract {
    private static final String TAG = "MRAID Display";
    private boolean bMaxSizeSet = false;
    private Context context;
    private ConfigBroadcastReceiver mBroadCastReceiver;
    private float mDensity;
    private int mMaxHeight = -1;
    private int mMaxWidth = -1;
    private WindowManager mWindowManager;

    public Display(MraidView mraidView, Context context2) {
        super(mraidView, context2);
        this.context = context2;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mWindowManager = (WindowManager) context2.getSystemService("window");
        this.mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);
        this.mDensity = displayMetrics.density;
    }

    private Abstract.Dimensions getDeviceDimensions(Abstract.Dimensions dimensions) {
        dimensions.width = (int) Math.ceil((double) (this.mDensity * ((float) dimensions.width)));
        dimensions.height = (int) Math.ceil((double) (this.mDensity * ((float) dimensions.height)));
        dimensions.x = (int) (((float) dimensions.x) * this.mDensity);
        dimensions.y = (int) (((float) dimensions.y) * this.mDensity);
        if (dimensions.height < 0) {
            dimensions.height = this.mMraidView.getHeight();
        }
        if (dimensions.width < 0) {
            dimensions.width = this.mMraidView.getWidth();
        }
        int[] iArr = new int[2];
        this.mMraidView.getLocationInWindow(iArr);
        if (dimensions.x < 0) {
            dimensions.x = iArr[0];
        }
        if (dimensions.y < 0) {
            dimensions.y = iArr[1] - 0;
        }
        return dimensions;
    }

    @JavascriptInterface
    public void close() {
        TapjoyLog.d(TAG, TJAdUnitConstants.String.CLOSE);
        this.mMraidView.close();
    }

    public String dimensions() {
        return "{ \"top\" :" + ((int) (((float) this.mMraidView.getTop()) / this.mDensity)) + "," + "\"left\" :" + ((int) (((float) this.mMraidView.getLeft()) / this.mDensity)) + "," + "\"bottom\" :" + ((int) (((float) this.mMraidView.getBottom()) / this.mDensity)) + "," + "\"right\" :" + ((int) (((float) this.mMraidView.getRight()) / this.mDensity)) + "}";
    }

    @JavascriptInterface
    public void expand(String str, String str2) {
        TapjoyLog.d(TAG, "expand: properties: " + str2 + " url: " + str);
        try {
            JSONObject jSONObject = new JSONObject(str2);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(FluctConstants.XML_NODE_WIDTH, jSONObject.get(FluctConstants.XML_NODE_WIDTH));
            jSONObject2.put(FluctConstants.XML_NODE_HEIGHT, jSONObject.get(FluctConstants.XML_NODE_HEIGHT));
            jSONObject2.put("x", 0);
            jSONObject2.put("y", 0);
            this.mMraidView.expand(getDeviceDimensions((Abstract.Dimensions) getFromJSON(jSONObject2, Abstract.Dimensions.class)), str, (Abstract.Properties) getFromJSON(new JSONObject(str2), Abstract.Properties.class));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        } catch (InstantiationException e4) {
            e4.printStackTrace();
        } catch (JSONException e5) {
            e5.printStackTrace();
        }
    }

    public String getMaxSize() {
        return this.bMaxSizeSet ? "{ width: " + this.mMaxWidth + ", " + "height: " + this.mMaxHeight + "}" : getScreenSize();
    }

    public int getOrientation() {
        int i = -1;
        switch (this.mWindowManager.getDefaultDisplay().getOrientation()) {
            case 0:
                i = 0;
                break;
            case 1:
                i = 90;
                break;
            case 2:
                i = 180;
                break;
            case 3:
                i = 270;
                break;
        }
        TapjoyLog.d(TAG, "getOrientation: " + i);
        return i;
    }

    public String getScreenSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return "{ width: " + ((int) Math.ceil((double) (((float) displayMetrics.widthPixels) / displayMetrics.density))) + ", " + "height: " + ((int) Math.ceil((double) (((float) displayMetrics.heightPixels) / displayMetrics.density))) + "}";
    }

    public String getSize() {
        return this.mMraidView.getSize();
    }

    @JavascriptInterface
    public void hide() {
        TapjoyLog.d(TAG, "hide");
        this.mMraidView.hide();
    }

    public boolean isVisible() {
        return this.mMraidView.getVisibility() == 0;
    }

    public void logHTML(String str) {
        TapjoyLog.d(TAG, str);
    }

    public void onOrientationChanged(int i) {
        String str = "window.mraidview.fireChangeEvent({ orientation: " + i + "});";
        TapjoyLog.d(TAG, str);
        this.mMraidView.injectMraidJavaScript(str);
    }

    @JavascriptInterface
    public void open(String str, boolean z, boolean z2, boolean z3) {
        TapjoyLog.i(TAG, "open: url: " + str + " back: " + z + " forward: " + z2 + " refresh: " + z3);
        if (!URLUtil.isValidUrl(str)) {
            TapjoyLog.i(TAG, "invalid URL");
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            List<ResolveInfo> queryIntentActivities = this.context.getPackageManager().queryIntentActivities(intent, 0);
            if (queryIntentActivities.size() == 1) {
                this.context.startActivity(intent);
            } else if (queryIntentActivities.size() > 1) {
                ((Activity) this.context).startActivity(Intent.createChooser(intent, TJAdUnitConstants.SHARE_CHOOSE_TITLE));
            } else {
                this.mMraidView.raiseError("Invalid url", "open");
            }
        } else {
            this.mMraidView.open(str, z, z2, z3);
        }
    }

    @JavascriptInterface
    public void openMap(String str, boolean z) {
        TapjoyLog.d(TAG, "openMap: url: " + str);
        this.mMraidView.openMap(str, z);
    }

    @JavascriptInterface
    public void playAudio(String str, boolean z, boolean z2, boolean z3, boolean z4, String str2, String str3) {
        TapjoyLog.d(TAG, "playAudio: url: " + str + " autoPlay: " + z + " controls: " + z2 + " loop: " + z3 + " position: " + z4 + " startStyle: " + str2 + " stopStyle: " + str3);
        if (!URLUtil.isValidUrl(str)) {
            this.mMraidView.raiseError("Invalid url", "playAudio");
        } else {
            this.mMraidView.playAudio(str, z, z2, z3, z4, str2, str3);
        }
    }

    @JavascriptInterface
    public void playVideo(String str, boolean z, boolean z2, boolean z3, boolean z4, int[] iArr, String str2, String str3) {
        TapjoyLog.d(TAG, "playVideo: url: " + str + " audioMuted: " + z + " autoPlay: " + z2 + " controls: " + z3 + " loop: " + z4 + " x: " + iArr[0] + " y: " + iArr[1] + " width: " + iArr[2] + " height: " + iArr[3] + " startStyle: " + str2 + " stopStyle: " + str3);
        Abstract.Dimensions dimensions = null;
        if (iArr[0] != -1) {
            Abstract.Dimensions dimensions2 = new Abstract.Dimensions();
            dimensions2.x = iArr[0];
            dimensions2.y = iArr[1];
            dimensions2.width = iArr[2];
            dimensions2.height = iArr[3];
            dimensions = getDeviceDimensions(dimensions2);
        }
        int i = 0;
        if (str.contains("android.resource")) {
            try {
                i = R.raw.class.getField(str.substring(str.lastIndexOf("/") + 1, str.lastIndexOf("."))).getInt((Object) null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            str = "android.resource://" + this.mContext.getPackageName() + "/" + i;
        }
        this.mMraidView.playVideo(str, false, true, true, false, dimensions, Abstract.FULL_SCREEN, Abstract.EXIT);
    }

    @JavascriptInterface
    public void resize(String str) {
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        String str2 = null;
        boolean z = true;
        try {
            JSONObject jSONObject = new JSONObject(str);
            i = jSONObject.getInt(FluctConstants.XML_NODE_WIDTH);
            i2 = jSONObject.getInt(FluctConstants.XML_NODE_HEIGHT);
            i3 = jSONObject.getInt("offsetX");
            i4 = jSONObject.getInt("offsetY");
            str2 = jSONObject.getString("customClosePosition");
            z = jSONObject.getBoolean("allowOffScreen");
        } catch (Exception e) {
        }
        this.mMraidView.resize((int) (this.mDensity * ((float) i)), (int) (this.mDensity * ((float) i2)), i3, i4, str2, z);
    }

    public void setMaxSize(int i, int i2) {
        TapjoyLog.i(TAG, "setMaxSize: " + i + "x" + i2);
        this.bMaxSizeSet = true;
        this.mMaxWidth = i;
        this.mMaxHeight = i2;
    }

    @JavascriptInterface
    public void setOrientationProperties(boolean z, String str) {
        TapjoyLog.d(TAG, "setOrientationProperties: allowOrientationChange: " + Boolean.toString(z) + " forceOrientation: " + str);
        this.mMraidView.setOrientationProperties(z, str);
    }

    @JavascriptInterface
    public void show() {
        TapjoyLog.d(TAG, "show");
        this.mMraidView.show();
    }

    public void startConfigurationListener() {
        try {
            if (this.mBroadCastReceiver == null) {
                this.mBroadCastReceiver = new ConfigBroadcastReceiver(this);
            }
            this.mContext.registerReceiver(this.mBroadCastReceiver, new IntentFilter("android.intent.action.CONFIGURATION_CHANGED"));
        } catch (Exception e) {
        }
    }

    public void stopAllListeners() {
        stopConfigurationListener();
        this.mBroadCastReceiver = null;
    }

    public void stopConfigurationListener() {
        try {
            this.mContext.unregisterReceiver(this.mBroadCastReceiver);
        } catch (Exception e) {
        }
    }

    @JavascriptInterface
    public void useCustomClose(boolean z) {
        if (z) {
            this.mMraidView.removeCloseImageButton();
        } else if (!z) {
            this.mMraidView.showCloseImageButton();
        }
    }
}
