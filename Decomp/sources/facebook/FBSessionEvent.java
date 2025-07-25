package facebook;

import com.ansca.corona.CoronaRuntime;
import com.naef.jnlua.LuaState;
import com.tapjoy.TJAdUnitConstants;
import facebook.FBBaseEvent;

public class FBSessionEvent extends FBBaseEvent {
    private long mExpirationTime;
    private Phase mPhase;
    private String mToken;

    public enum Phase {
        login,
        loginFailed,
        loginCancelled,
        logout
    }

    public FBSessionEvent(Phase phase) {
        super(FBBaseEvent.FBType.session);
        this.mPhase = phase;
        this.mToken = null;
        this.mExpirationTime = 0;
    }

    public FBSessionEvent(Phase phase, String str) {
        super(FBBaseEvent.FBType.session, str, true);
        this.mPhase = phase;
        this.mToken = null;
        this.mExpirationTime = 0;
    }

    public FBSessionEvent(String str, long j) {
        super(FBBaseEvent.FBType.session);
        this.mPhase = Phase.login;
        this.mToken = str;
        this.mExpirationTime = j;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        super.executeUsing(coronaRuntime);
        LuaState luaState = coronaRuntime.getLuaState();
        luaState.pushString(this.mPhase.name());
        luaState.setField(-2, "phase");
        if (this.mToken != null) {
            luaState.pushString(this.mToken);
            luaState.setField(-2, TJAdUnitConstants.String.EVENT_TOKEN);
            luaState.pushNumber(new Long(this.mExpirationTime).doubleValue());
            luaState.setField(-2, "expiration");
        }
    }
}
