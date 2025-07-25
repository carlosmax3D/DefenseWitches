package CoronaProvider.gameNetwork.google;

import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.naef.jnlua.LuaState;

public class InvitationReceivedListener extends Listener implements OnInvitationReceivedListener {
    public InvitationReceivedListener(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i) {
        super(coronaRuntimeTaskDispatcher, i);
    }

    public void onInvitationReceived(Invitation invitation) {
        if (this.fListener >= 0) {
            final Invitation invitation2 = invitation;
            this.fDispatcher.send(new CoronaRuntimeTask() {
                public void executeUsing(CoronaRuntime coronaRuntime) {
                    LuaState luaState = coronaRuntime.getLuaState();
                    CoronaLua.newEvent(luaState, "invitationReceived");
                    luaState.pushString("invitationReceived");
                    luaState.setField(-2, "type");
                    Listener.pushInvitationToLua(luaState, invitation2);
                    luaState.setField(-2, "data");
                    try {
                        CoronaLua.dispatchEvent(luaState, InvitationReceivedListener.this.fListener, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void onInvitationRemoved(String str) {
    }
}
