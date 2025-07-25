package facebook;

import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaLuaEvent;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.naef.jnlua.LuaState;
import jp.stargarage.g2metrics.BuildConfig;

public abstract class FBBaseEvent implements CoronaRuntimeTask {
    private static final String EVENT_NAME = "fbconnect";
    private boolean mIsError;
    private String mResponse;
    private FBType mType;

    protected enum FBType {
        session,
        request,
        dialog
    }

    protected FBBaseEvent(FBType fBType) {
        this.mType = fBType;
        this.mIsError = false;
        this.mResponse = null;
    }

    protected FBBaseEvent(FBType fBType, String str, boolean z) {
        this.mType = fBType;
        this.mResponse = str;
        this.mIsError = z;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        LuaState luaState = coronaRuntime.getLuaState();
        CoronaLua.newEvent(luaState, EVENT_NAME);
        luaState.pushString(this.mType.name());
        luaState.setField(-2, "type");
        luaState.pushBoolean(this.mIsError);
        luaState.setField(-2, CoronaLuaEvent.ISERROR_KEY);
        luaState.pushString(this.mResponse == null ? BuildConfig.FLAVOR : this.mResponse);
        luaState.setField(-2, CoronaLuaEvent.RESPONSE_KEY);
    }
}
