package CoronaProvider.ads.admob;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.widget.AbsoluteLayout;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaLuaEvent;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeListener;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;
import com.naef.jnlua.NamedJavaFunction;
import jp.co.voyagegroup.android.fluct.jar.util.FluctConstants;
import jp.stargarage.g2metrics.BuildConfig;

public class LuaLoader implements JavaFunction, CoronaRuntimeListener {
    private static String ADSSHOWN = "adsShown";
    private static final String PROVIDER_NAME = "AdMobProvider";
    protected static CoronaRuntimeTaskDispatcher fDispatcher;
    private static int fListener;
    private AbsoluteLayout fAbsoluteLayout;
    private double fAdHeightRatio;
    /* access modifiers changed from: private */
    public AdMobAd fAdMobAd;
    private String fApplicationId;

    private class HeightWrapper implements NamedJavaFunction {
        private HeightWrapper() {
        }

        public String getName() {
            return FluctConstants.XML_NODE_HEIGHT;
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.getHeight(luaState);
        }
    }

    private class HideWrapper implements NamedJavaFunction {
        private HideWrapper() {
        }

        public String getName() {
            return "hide";
        }

        public int invoke(LuaState luaState) {
            LuaLoader.this.fAdMobAd.hide();
            return 0;
        }
    }

    private class InitWrapper implements NamedJavaFunction {
        private InitWrapper() {
        }

        public String getName() {
            return "init";
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.init(luaState);
        }
    }

    private class IsLoadedWrapper implements NamedJavaFunction {
        private IsLoadedWrapper() {
        }

        public String getName() {
            return "isLoaded";
        }

        public int invoke(LuaState luaState) {
            if (luaState.type(-1) == LuaType.STRING) {
                luaState.pushBoolean(LuaLoader.this.fAdMobAd.isLoaded(luaState.toString(-1)));
                return 1;
            }
            luaState.pushBoolean(false);
            return 1;
        }
    }

    private class LoadWrapper implements NamedJavaFunction {
        private LoadWrapper() {
        }

        public String getName() {
            return "load";
        }

        public int invoke(LuaState luaState) {
            boolean z = false;
            String luaState2 = luaState.toString(1);
            int i = 1 + 1;
            if (luaState.isTable(i)) {
                luaState.getField(i, "appId");
                if (luaState.type(-1) == LuaType.STRING) {
                    LuaLoader.this.fAdMobAd.setAppId(luaState.toString(-1));
                }
                luaState.pop(1);
                luaState.getField(i, "testMode");
                if (luaState.isBoolean(-1)) {
                    z = luaState.toBoolean(-1);
                }
                luaState.pop(1);
            }
            LuaLoader.this.fAdMobAd.load(luaState2, z);
            return 0;
        }
    }

    private class ShowWrapper implements NamedJavaFunction {
        private ShowWrapper() {
        }

        public String getName() {
            return "show";
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.show(luaState);
        }
    }

    public LuaLoader() {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            throw new IllegalArgumentException("Activity cannot be null.");
        }
        fDispatcher = null;
        this.fApplicationId = BuildConfig.FLAVOR;
        this.fAdHeightRatio = 0.0d;
        fListener = -1;
    }

    protected static void dispatchEvent(boolean z, String str, String str2, String str3) {
        CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher = fDispatcher;
        final int i = fListener;
        if (coronaRuntimeTaskDispatcher != null && i != -1) {
            final boolean z2 = z;
            final String str4 = str;
            final String str5 = str2;
            final String str6 = str3;
            coronaRuntimeTaskDispatcher.send(new CoronaRuntimeTask() {
                public void executeUsing(CoronaRuntime coronaRuntime) {
                    try {
                        LuaState luaState = coronaRuntime.getLuaState();
                        CoronaLua.newEvent(luaState, CoronaLuaEvent.ADSREQUEST_TYPE);
                        luaState.pushString(LuaLoader.PROVIDER_NAME);
                        luaState.setField(-2, CoronaLuaEvent.PROVIDER_KEY);
                        luaState.pushBoolean(z2);
                        luaState.setField(-2, CoronaLuaEvent.ISERROR_KEY);
                        luaState.pushString(str4 == null ? BuildConfig.FLAVOR : str4);
                        luaState.setField(-2, CoronaLuaEvent.RESPONSE_KEY);
                        luaState.pushString(str5 == null ? BuildConfig.FLAVOR : str5);
                        luaState.setField(-2, "type");
                        luaState.pushString(str6 == null ? BuildConfig.FLAVOR : str6);
                        luaState.setField(-2, "phase");
                        CoronaLua.dispatchEvent(luaState, i, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public int getHeight(LuaState luaState) {
        double d = 0.0d;
        double d2 = 0.0d;
        double d3 = 0.0d;
        int top = luaState.getTop();
        luaState.getGlobal("require");
        luaState.pushString("config");
        luaState.call(1, -1);
        luaState.getGlobal("application");
        if (luaState.isTable(-1)) {
            luaState.getField(-1, "content");
            if (luaState.isTable(-1)) {
                luaState.getField(-1, FluctConstants.XML_NODE_HEIGHT);
                d = luaState.toNumber(-1);
                luaState.pop(1);
                luaState.getField(-1, FluctConstants.XML_NODE_WIDTH);
                d2 = luaState.toNumber(-1);
                luaState.pop(1);
            }
        }
        luaState.getGlobal("system");
        luaState.getField(-1, "orientation");
        if (luaState.isString(-1)) {
            String luaState2 = luaState.toString(-1);
            d3 = (luaState2.equals("landscapeLeft") || luaState2.equals("landscapeRight")) ? this.fAdHeightRatio * d2 : this.fAdHeightRatio * d;
        }
        luaState.setTop(top);
        luaState.pushNumber(d3);
        return 1;
    }

    public int init(LuaState luaState) {
        boolean z = false;
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        if (applicationContext != null) {
            applicationContext.enforceCallingOrSelfPermission("android.permission.INTERNET", (String) null);
            applicationContext.enforceCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE", (String) null);
        }
        if (BuildConfig.FLAVOR == this.fApplicationId) {
            if (CoronaLua.isListener(luaState, -1, CoronaLuaEvent.ADSREQUEST_TYPE)) {
                fListener = CoronaLua.newRef(luaState, -1);
            }
            int i = -1 - 1;
            if (luaState.isString(i)) {
                this.fApplicationId = luaState.toString(i);
                z = true;
            }
        } else {
            Log.v("Corona", "WARNING: ads.init() should only be called once. The application id has already been set to :" + this.fApplicationId + ".");
        }
        this.fAdMobAd = new AdMobAd(this.fApplicationId);
        luaState.pushBoolean(z);
        return 1;
    }

    public int invoke(LuaState luaState) {
        luaState.register(luaState.toString(1), new NamedJavaFunction[]{new InitWrapper(), new ShowWrapper(), new HideWrapper(), new HeightWrapper(), new LoadWrapper(), new IsLoadedWrapper()});
        fDispatcher = new CoronaRuntimeTaskDispatcher(luaState);
        return 1;
    }

    public void onExiting(CoronaRuntime coronaRuntime) {
        this.fAdMobAd.destroy();
        CoronaLua.deleteRef(coronaRuntime.getLuaState(), fListener);
        fListener = -1;
        this.fApplicationId = BuildConfig.FLAVOR;
    }

    public void onLoaded(CoronaRuntime coronaRuntime) {
    }

    public void onResumed(CoronaRuntime coronaRuntime) {
        this.fAdMobAd.resume();
    }

    public void onStarted(CoronaRuntime coronaRuntime) {
    }

    public void onSuspended(CoronaRuntime coronaRuntime) {
        this.fAdMobAd.pause();
    }

    public int show(LuaState luaState) {
        String checkString = luaState.checkString(1);
        int i = 0;
        int i2 = 0;
        boolean z = false;
        int i3 = 1 + 1;
        String str = null;
        if (luaState.isTable(i3)) {
            luaState.getField(i3, "x");
            if (luaState.isNumber(-1)) {
                i = (int) Math.round(luaState.toNumber(-1));
            }
            luaState.pop(1);
            luaState.getField(i3, "y");
            if (luaState.isNumber(-1)) {
                i2 = (int) Math.round(luaState.toNumber(-1));
            }
            luaState.pop(1);
            luaState.getField(i3, "testMode");
            if (luaState.isBoolean(-1)) {
                z = luaState.toBoolean(-1);
            }
            luaState.pop(1);
            luaState.getField(i3, "appId");
            if (luaState.type(-1) == LuaType.STRING) {
                str = luaState.toString(-1);
            }
            luaState.pop(1);
        }
        Point convertCoronaPointToAndroidPoint = CoronaEnvironment.getCoronaActivity().convertCoronaPointToAndroidPoint(i, i2);
        if (convertCoronaPointToAndroidPoint != null) {
            i = convertCoronaPointToAndroidPoint.x;
            i2 = convertCoronaPointToAndroidPoint.y;
        }
        showAd(checkString, i, i2, z, str);
        return 0;
    }

    public void showAd(String str, int i, int i2, boolean z, String str2) {
        if (this.fAdMobAd != null) {
            if (str2 != null) {
                this.fAdMobAd.setAppId(str2);
            }
            if (str.equals(AdMobAd.BANNER)) {
                double show = this.fAdMobAd.show(i, i2, z);
                if (show > 0.0d) {
                    this.fAdHeightRatio = show;
                }
            } else if (str.equals("interstitial")) {
                this.fAdMobAd.showInterstitialAd(z);
            } else {
                Log.w("Corona", "ads.show() Could not recognize banner type(" + str + ")");
            }
        }
    }
}
