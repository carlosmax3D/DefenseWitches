package CoronaProvider.analytics.flurry;

import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeListener;
import com.flurry.android.FlurryAgent;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.NamedJavaFunction;
import java.util.HashMap;

public class LuaLoader implements JavaFunction, CoronaRuntimeListener {
    private String fApplicationId = null;
    private boolean fSessionStarted = false;

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

    private class LogEventWrapper implements NamedJavaFunction {
        private LogEventWrapper() {
        }

        public String getName() {
            return "logEvent";
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.logEvent(luaState);
        }
    }

    public LuaLoader() {
        initialize();
    }

    static String ToString(LuaState luaState, int i) {
        switch (luaState.type(-2)) {
            case NUMBER:
                return String.valueOf(luaState.toNumber(i));
            default:
                return luaState.toString(i);
        }
    }

    public int init(LuaState luaState) {
        boolean z = false;
        CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
        if (!this.fSessionStarted && this.fApplicationId == null && coronaActivity != null) {
            coronaActivity.enforceCallingOrSelfPermission("android.permission.INTERNET", (String) null);
            String luaState2 = luaState.toString(2);
            if (luaState2 != null) {
                this.fApplicationId = new String(luaState2);
                this.fSessionStarted = true;
                FlurryAgent.onStartSession(coronaActivity, luaState2);
                z = true;
            }
        }
        luaState.pushBoolean(z);
        return 1;
    }

    /* access modifiers changed from: protected */
    public void initialize() {
        this.fApplicationId = null;
        this.fSessionStarted = false;
    }

    public int invoke(LuaState luaState) {
        initialize();
        luaState.register(luaState.toString(1), new NamedJavaFunction[]{new InitWrapper(), new LogEventWrapper()});
        return 1;
    }

    public int logEvent(LuaState luaState) {
        String checkString;
        if (!this.fSessionStarted || (checkString = luaState.checkString(1)) == null) {
            return 0;
        }
        HashMap hashMap = luaState.isTable(2) ? new HashMap(10) : null;
        if (hashMap != null) {
            luaState.pushNil();
            for (int i = 0; i < 10 && luaState.next(2); i++) {
                String ToString = ToString(luaState, -2);
                String ToString2 = ToString(luaState, -1);
                if (!(ToString == null || ToString2 == null)) {
                    hashMap.put(ToString, ToString2);
                }
                luaState.pop(1);
            }
            FlurryAgent.onEvent(checkString, hashMap);
            return 0;
        }
        FlurryAgent.onEvent(checkString);
        return 0;
    }

    public void onExiting(CoronaRuntime coronaRuntime) {
    }

    public void onLoaded(CoronaRuntime coronaRuntime) {
    }

    public void onResumed(CoronaRuntime coronaRuntime) {
        if (this.fApplicationId != null) {
            this.fSessionStarted = true;
            FlurryAgent.onStartSession(CoronaEnvironment.getCoronaActivity(), this.fApplicationId);
        }
    }

    public void onStarted(CoronaRuntime coronaRuntime) {
    }

    public void onSuspended(CoronaRuntime coronaRuntime) {
        if (this.fApplicationId != null) {
            this.fSessionStarted = false;
            FlurryAgent.onEndSession(CoronaEnvironment.getCoronaActivity());
        }
    }
}
