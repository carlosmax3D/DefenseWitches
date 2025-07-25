package CoronaProvider.gameNetwork.google;

import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.google.android.gms.games.leaderboard.OnScoreSubmittedListener;
import com.google.android.gms.games.leaderboard.SubmitScoreResult;
import com.naef.jnlua.LuaState;

public class SetHighScoreListener extends Listener implements OnScoreSubmittedListener {
    public SetHighScoreListener(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i) {
        super(coronaRuntimeTaskDispatcher, i);
    }

    public void onScoreSubmitted(int i, SubmitScoreResult submitScoreResult) {
        if (this.fListener >= 0) {
            final SubmitScoreResult submitScoreResult2 = submitScoreResult;
            this.fDispatcher.send(new CoronaRuntimeTask() {
                public void executeUsing(CoronaRuntime coronaRuntime) {
                    LuaState luaState = coronaRuntime.getLuaState();
                    CoronaLua.newEvent(luaState, "setHighScore");
                    luaState.pushString("setHighScore");
                    luaState.setField(-2, "type");
                    Listener.pushSubmitScoreResultToLua(luaState, submitScoreResult2);
                    luaState.setField(-2, "data");
                    try {
                        CoronaLua.dispatchEvent(luaState, SetHighScoreListener.this.fListener, 0);
                        CoronaLua.deleteRef(luaState, SetHighScoreListener.this.fListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
