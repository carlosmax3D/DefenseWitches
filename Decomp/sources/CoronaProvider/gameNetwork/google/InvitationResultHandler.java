package CoronaProvider.gameNetwork.google;

import android.content.Intent;
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaLuaEvent;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.games.multiplayer.Invitation;
import com.naef.jnlua.LuaState;
import jp.stargarage.g2metrics.BuildConfig;

public class InvitationResultHandler extends Listener implements CoronaActivity.OnActivityResultHandler {
    private GameHelper fGameHelper;

    public InvitationResultHandler(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i, GameHelper gameHelper) {
        super(coronaRuntimeTaskDispatcher, i);
        this.fGameHelper = gameHelper;
    }

    private void pushInvitationsToLua(final String str, final boolean z, final String str2) {
        this.fDispatcher.send(new CoronaRuntimeTask() {
            public void executeUsing(CoronaRuntime coronaRuntime) {
                LuaState luaState = coronaRuntime.getLuaState();
                CoronaLua.newEvent(luaState, "invitations");
                luaState.pushString("invitations");
                luaState.setField(-2, "type");
                luaState.newTable();
                luaState.pushString(str);
                luaState.setField(-2, RoomManager.ROOM_ID);
                luaState.pushString(str2);
                luaState.setField(-2, "phase");
                luaState.pushBoolean(z);
                luaState.setField(-2, CoronaLuaEvent.ISERROR_KEY);
                luaState.setField(-2, "data");
                try {
                    CoronaLua.dispatchEvent(luaState, InvitationResultHandler.this.fListener, 0);
                    CoronaLua.deleteRef(luaState, InvitationResultHandler.this.fListener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onHandleActivityResult(CoronaActivity coronaActivity, int i, int i2, Intent intent) {
        coronaActivity.unregisterActivityResultHandler(this);
        CoronaRuntimeTaskDispatcher runtimeTaskDispatcher = coronaActivity.getRuntimeTaskDispatcher();
        if (-1 == i2) {
            pushInvitationsToLua(((Invitation) intent.getExtras().getParcelable("invitation")).getInvitationId(), false, "selected");
        } else if (10001 == i2) {
            if (!(this.fGameHelper == null || this.fGameHelper.getGamesClient() == null)) {
                this.fGameHelper.signOut();
            }
            pushInvitationsToLua(BuildConfig.FLAVOR, true, "logout");
        } else {
            pushInvitationsToLua(BuildConfig.FLAVOR, true, AnalyticsEvents.PARAMETER_SHARE_OUTCOME_CANCELLED);
        }
    }
}
