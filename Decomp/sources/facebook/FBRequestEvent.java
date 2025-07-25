package facebook;

import com.ansca.corona.CoronaRuntime;
import com.facebook.internal.NativeProtocol;
import com.naef.jnlua.LuaState;
import facebook.FBBaseEvent;

public class FBRequestEvent extends FBBaseEvent {
    private boolean mDidComplete;

    public FBRequestEvent(String str, boolean z, boolean z2) {
        super(FBBaseEvent.FBType.request, str, z);
        this.mDidComplete = z2;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        super.executeUsing(coronaRuntime);
        LuaState luaState = coronaRuntime.getLuaState();
        luaState.pushBoolean(this.mDidComplete);
        luaState.setField(-2, NativeProtocol.RESULT_ARGS_DIALOG_COMPLETE_KEY);
    }
}
