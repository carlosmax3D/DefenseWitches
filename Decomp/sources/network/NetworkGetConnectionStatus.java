package network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.ansca.corona.CoronaEnvironment;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.NamedJavaFunction;

public class NetworkGetConnectionStatus implements NamedJavaFunction {
    private LuaLoader fLoader;

    public NetworkGetConnectionStatus(LuaLoader luaLoader) {
        this.fLoader = luaLoader;
    }

    public String getName() {
        return "getConnectionStatus";
    }

    public int invoke(LuaState luaState) {
        boolean z = false;
        boolean z2 = false;
        Context applicationContext = CoronaEnvironment.getApplicationContext();
        applicationContext.enforceCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE", (String) null);
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) applicationContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && (z = activeNetworkInfo.isConnectedOrConnecting())) {
            z2 = activeNetworkInfo.getType() != 1;
        }
        NetworkRequest.debug("Is connected: %b, is mobile: %b", Boolean.valueOf(z), Boolean.valueOf(z2));
        luaState.newTable(0, 2);
        int top = luaState.getTop();
        luaState.pushBoolean(z);
        luaState.setField(top, "isConnected");
        luaState.pushBoolean(z2);
        luaState.setField(top, "isMobile");
        return 1;
    }
}
