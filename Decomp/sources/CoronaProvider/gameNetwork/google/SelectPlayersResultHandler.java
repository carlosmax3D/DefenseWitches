package CoronaProvider.gameNetwork.google;

import android.content.Intent;
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.facebook.internal.AnalyticsEvents;
import com.naef.jnlua.LuaState;
import java.util.ArrayList;
import java.util.Iterator;

public class SelectPlayersResultHandler extends Listener implements CoronaActivity.OnActivityResultHandler {
    private GameHelper fGameHelper;

    public SelectPlayersResultHandler(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i, GameHelper gameHelper) {
        super(coronaRuntimeTaskDispatcher, i);
        this.fGameHelper = gameHelper;
    }

    private void pushSelectedPlayersToLua(ArrayList<String> arrayList, int i, int i2, String str) {
        final ArrayList<String> arrayList2 = arrayList;
        final int i3 = i;
        final int i4 = i2;
        final String str2 = str;
        this.fDispatcher.send(new CoronaRuntimeTask() {
            public void executeUsing(CoronaRuntime coronaRuntime) {
                LuaState luaState = coronaRuntime.getLuaState();
                CoronaLua.newEvent(luaState, "selectPlayers");
                luaState.pushString("selectPlayers");
                luaState.setField(-2, "type");
                luaState.newTable();
                int i = 1;
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    luaState.pushString((String) it.next());
                    luaState.rawSet(-2, i);
                    i++;
                }
                luaState.pushNumber((double) i3);
                luaState.setField(-2, "minAutoMatchPlayers");
                luaState.pushNumber((double) i4);
                luaState.setField(-2, "maxAutoMatchPlayers");
                luaState.pushString(str2);
                luaState.setField(-2, "phase");
                luaState.setField(-2, "data");
                try {
                    CoronaLua.dispatchEvent(luaState, SelectPlayersResultHandler.this.fListener, 0);
                    CoronaLua.deleteRef(luaState, SelectPlayersResultHandler.this.fListener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onHandleActivityResult(CoronaActivity coronaActivity, int i, int i2, Intent intent) {
        String str;
        coronaActivity.unregisterActivityResultHandler(this);
        CoronaRuntimeTaskDispatcher runtimeTaskDispatcher = coronaActivity.getRuntimeTaskDispatcher();
        ArrayList<String> arrayList = null;
        int i3 = 0;
        int i4 = 0;
        if (-1 == i2) {
            arrayList = intent.getStringArrayListExtra("players");
            i3 = intent.getIntExtra("min_automatch_players", 0);
            i4 = intent.getIntExtra("max_automatch_players", 0);
            str = "selected";
        } else if (10001 == i2) {
            str = "logout";
            if (!(this.fGameHelper == null || this.fGameHelper.getGamesClient() == null)) {
                this.fGameHelper.signOut();
            }
        } else {
            arrayList = new ArrayList<>();
            str = AnalyticsEvents.PARAMETER_SHARE_OUTCOME_CANCELLED;
        }
        pushSelectedPlayersToLua(arrayList, i3, i4, str);
    }
}
