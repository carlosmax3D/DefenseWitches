package network;

import android.os.Handler;
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeListener;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.NamedJavaFunction;

public class LuaLoader implements JavaFunction, CoronaRuntimeListener {
    private static final String EVENT_NAME = "networkLibraryEvent";
    private NetworkRequest fNetworkRequest = null;
    private CoronaRuntimeTaskDispatcher fRuntimeTaskDispatcher = null;

    public CoronaRuntimeTaskDispatcher getRuntimeTaskDispatcher() {
        return this.fRuntimeTaskDispatcher;
    }

    public int invoke(LuaState luaState) {
        NetworkRequest.debug("LuaLoader invoke - luaState: %s", Integer.toHexString(System.identityHashCode(luaState)));
        this.fNetworkRequest = new NetworkRequest(this);
        luaState.register(luaState.toString(1), new NamedJavaFunction[]{this.fNetworkRequest, new NetworkCancel(this), new NetworkGetConnectionStatus(this)});
        LuaHelper.loadLuaHelper(luaState);
        return 1;
    }

    public void onExiting(CoronaRuntime coronaRuntime) {
        NetworkRequest.debug("onExiting", new Object[0]);
        this.fNetworkRequest.abortOpenConnections(coronaRuntime);
    }

    public void onLoaded(CoronaRuntime coronaRuntime) {
        this.fRuntimeTaskDispatcher = new CoronaRuntimeTaskDispatcher(coronaRuntime);
        LuaState luaState = coronaRuntime.getLuaState();
        NetworkRequest.debug("network plugin onLoaded - JNLUA version is: " + LuaState.VERSION, new Object[0]);
        NetworkRequest.debug("LuaLoader onLoaded - luaState: %s", Integer.toHexString(System.identityHashCode(luaState)));
        System.setProperty("http.keepAlive", "false");
    }

    public void onResumed(CoronaRuntime coronaRuntime) {
        NetworkRequest.debug("onResumed", new Object[0]);
    }

    public void onStarted(CoronaRuntime coronaRuntime) {
        NetworkRequest.debug("onStarted", new Object[0]);
    }

    public void onSuspended(CoronaRuntime coronaRuntime) {
        NetworkRequest.debug("onSuspended", new Object[0]);
    }

    public boolean postOnUiThread(Runnable runnable) {
        CoronaActivity coronaActivity;
        Handler handler;
        if (runnable == null || (coronaActivity = CoronaEnvironment.getCoronaActivity()) == null || (handler = coronaActivity.getHandler()) == null) {
            return false;
        }
        return handler.post(runnable);
    }
}
