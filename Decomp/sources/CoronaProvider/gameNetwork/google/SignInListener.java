package CoronaProvider.gameNetwork.google;

import CoronaProvider.gameNetwork.google.GameHelper;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaLuaEvent;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.naef.jnlua.LuaState;

public class SignInListener extends Listener implements GameHelper.GameHelperListener {
    int count = 0;

    public SignInListener(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i) {
        super(coronaRuntimeTaskDispatcher, i);
    }

    private void callBackListener(final boolean z) {
        if (this.fListener >= 0 && this.count <= 0) {
            this.count++;
            this.fDispatcher.send(new CoronaRuntimeTask() {
                public void executeUsing(CoronaRuntime coronaRuntime) {
                    try {
                        LuaState luaState = coronaRuntime.getLuaState();
                        CoronaLua.newEvent(luaState, "login");
                        luaState.pushString("login");
                        luaState.setField(-2, "type");
                        if (z) {
                            luaState.pushBoolean(z);
                            luaState.setField(-2, CoronaLuaEvent.ISERROR_KEY);
                        }
                        CoronaLua.dispatchEvent(luaState, SignInListener.this.fListener, 0);
                        CoronaLua.deleteRef(luaState, SignInListener.this.fListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void onSignInFailed() {
        callBackListener(true);
    }

    public void onSignInSucceeded() {
        callBackListener(false);
    }
}
