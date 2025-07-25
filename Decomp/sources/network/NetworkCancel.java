package network;

import com.naef.jnlua.LuaState;
import com.naef.jnlua.NamedJavaFunction;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetworkCancel implements NamedJavaFunction {
    private LuaLoader fLoader;

    public NetworkCancel(LuaLoader luaLoader) {
        this.fLoader = luaLoader;
    }

    public String getName() {
        return "cancel";
    }

    public int invoke(LuaState luaState) {
        if (luaState.isNil(-1) || !luaState.isJavaObject(1, AtomicBoolean.class)) {
            NetworkRequest.paramValidationFailure(luaState, "The parameter passed to network.cancel() must be a requestId returned from call to network.request(), but was not", new Object[0]);
            return 0;
        }
        NetworkRequest.debug("Cancelling request", new Object[0]);
        ((AtomicBoolean) luaState.toJavaObject(1, AtomicBoolean.class)).set(true);
        luaState.pushBoolean(true);
        return 0 + 1;
    }
}
