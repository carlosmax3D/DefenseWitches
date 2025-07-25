package CoronaProvider.gameNetwork.google;

import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.google.android.gms.games.achievement.OnAchievementUpdatedListener;
import com.naef.jnlua.LuaState;

public class UnlockAchievementListener extends Listener implements OnAchievementUpdatedListener {
    public UnlockAchievementListener(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i) {
        super(coronaRuntimeTaskDispatcher, i);
    }

    public void onAchievementUpdated(int i, String str) {
        if (this.fListener >= 0) {
            final String str2 = str;
            this.fDispatcher.send(new CoronaRuntimeTask() {
                public void executeUsing(CoronaRuntime coronaRuntime) {
                    LuaState luaState = coronaRuntime.getLuaState();
                    CoronaLua.newEvent(luaState, "unlockAchievement");
                    luaState.pushString("unlockAchievement");
                    luaState.setField(-2, "type");
                    luaState.newTable(1, 0);
                    luaState.pushString(str2);
                    luaState.setField(-2, "achievementId");
                    luaState.setField(-2, "data");
                    try {
                        CoronaLua.dispatchEvent(luaState, UnlockAchievementListener.this.fListener, 0);
                        CoronaLua.deleteRef(luaState, UnlockAchievementListener.this.fListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
