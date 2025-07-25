package CoronaProvider.gameNetwork.google;

import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.OnLeaderboardScoresLoadedListener;
import com.naef.jnlua.LuaState;

public class LoadTopScoresListener extends Listener implements OnLeaderboardScoresLoadedListener {
    /* access modifiers changed from: private */
    public String fCategory;

    public LoadTopScoresListener(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i, String str) {
        super(coronaRuntimeTaskDispatcher, i);
        this.fCategory = str;
    }

    public void onLeaderboardScoresLoaded(int i, Leaderboard leaderboard, LeaderboardScoreBuffer leaderboardScoreBuffer) {
        if (this.fListener >= 0) {
            final LeaderboardScoreBuffer leaderboardScoreBuffer2 = leaderboardScoreBuffer;
            this.fDispatcher.send(new CoronaRuntimeTask() {
                public void executeUsing(CoronaRuntime coronaRuntime) {
                    LuaState luaState = coronaRuntime.getLuaState();
                    CoronaLua.newEvent(luaState, "loadScores");
                    luaState.pushString("loadScores");
                    luaState.setField(-2, "type");
                    luaState.newTable(leaderboardScoreBuffer2.getCount(), 0);
                    for (int i = 0; i < leaderboardScoreBuffer2.getCount(); i++) {
                        Listener.pushLeaderboardScoreToLua(luaState, leaderboardScoreBuffer2.get(i), LoadTopScoresListener.this.fCategory);
                        luaState.rawSet(-2, i + 1);
                    }
                    luaState.setField(-2, "data");
                    try {
                        CoronaLua.dispatchEvent(luaState, LoadTopScoresListener.this.fListener, 0);
                        CoronaLua.deleteRef(luaState, LoadTopScoresListener.this.fListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
