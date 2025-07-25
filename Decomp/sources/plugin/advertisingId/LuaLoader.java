package plugin.advertisingId;

import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.NamedJavaFunction;
import jp.stargarage.g2metrics.BuildConfig;

public class LuaLoader implements JavaFunction {

    private class GetAdvertisingIdWrapper implements NamedJavaFunction {
        private GetAdvertisingIdWrapper() {
        }

        public String getName() {
            return "getAdvertisingId";
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.getAdvertisingId(luaState);
        }
    }

    private class IsTrackingEnabledWrapper implements NamedJavaFunction {
        private IsTrackingEnabledWrapper() {
        }

        public String getName() {
            return "isTrackingEnabled";
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.isTrackingEnabled(luaState);
        }
    }

    public int getAdvertisingId(LuaState luaState) {
        String str = BuildConfig.FLAVOR;
        CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
        if (coronaActivity != null && GooglePlayServicesUtil.isGooglePlayServicesAvailable(coronaActivity) == 0) {
            try {
                str = AdvertisingIdClient.getAdvertisingIdInfo(coronaActivity).getId();
            } catch (Exception e) {
            }
        }
        luaState.pushString(str);
        return 1;
    }

    public int invoke(LuaState luaState) {
        luaState.register(luaState.toString(1), new NamedJavaFunction[]{new GetAdvertisingIdWrapper(), new IsTrackingEnabledWrapper()});
        return 1;
    }

    public int isTrackingEnabled(LuaState luaState) {
        boolean z = false;
        CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
        if (coronaActivity != null && GooglePlayServicesUtil.isGooglePlayServicesAvailable(coronaActivity) == 0) {
            try {
                z = AdvertisingIdClient.getAdvertisingIdInfo(coronaActivity).isLimitAdTrackingEnabled();
            } catch (Exception e) {
            }
        }
        luaState.pushBoolean(!z);
        return 1;
    }
}
