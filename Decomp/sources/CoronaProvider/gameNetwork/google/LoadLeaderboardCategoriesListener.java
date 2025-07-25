package CoronaProvider.gameNetwork.google;

import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.OnLeaderboardMetadataLoadedListener;
import com.naef.jnlua.LuaState;

public class LoadLeaderboardCategoriesListener extends Listener implements OnLeaderboardMetadataLoadedListener {
    public LoadLeaderboardCategoriesListener(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i) {
        super(coronaRuntimeTaskDispatcher, i);
    }

    public void onLeaderboardMetadataLoaded(int i, LeaderboardBuffer leaderboardBuffer) {
        if (this.fListener >= 0) {
            final LeaderboardBuffer leaderboardBuffer2 = leaderboardBuffer;
            this.fDispatcher.send(new CoronaRuntimeTask() {
                public void executeUsing(CoronaRuntime coronaRuntime) {
                    try {
                        LuaState luaState = coronaRuntime.getLuaState();
                        CoronaLua.newEvent(luaState, "loadLeaderboardCategories");
                        luaState.pushString("loadLeaderboardCategories");
                        luaState.setField(-2, "type");
                        luaState.newTable(leaderboardBuffer2.getCount() + 1, 0);
                        for (int i = 0; i < leaderboardBuffer2.getCount(); i++) {
                            Listener.pushLeaderboardToLua(luaState, (Leaderboard) leaderboardBuffer2.get(i));
                            luaState.rawSet(-2, i + 1);
                        }
                        luaState.setField(-2, "data");
                        CoronaLua.dispatchEvent(luaState, LoadLeaderboardCategoriesListener.this.fListener, 0);
                        CoronaLua.deleteRef(luaState, LoadLeaderboardCategoriesListener.this.fListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
