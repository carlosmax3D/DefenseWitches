package CoronaProvider.gameNetwork.google;

import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.OnAchievementsLoadedListener;
import com.naef.jnlua.LuaState;

public class LoadAchievementsListener extends Listener implements OnAchievementsLoadedListener {
    public LoadAchievementsListener(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i) {
        super(coronaRuntimeTaskDispatcher, i);
    }

    public void onAchievementsLoaded(int i, AchievementBuffer achievementBuffer) {
        if (this.fListener >= 0) {
            final AchievementBuffer achievementBuffer2 = achievementBuffer;
            this.fDispatcher.send(new CoronaRuntimeTask() {
                public void executeUsing(CoronaRuntime coronaRuntime) {
                    LuaState luaState = coronaRuntime.getLuaState();
                    CoronaLua.newEvent(luaState, "loadAchievements");
                    luaState.pushString("loadAchievements");
                    luaState.setField(-2, "type");
                    luaState.newTable(achievementBuffer2.getCount(), 0);
                    for (int i = 0; i < achievementBuffer2.getCount(); i++) {
                        Listener.pushAchievementToLua(luaState, achievementBuffer2.get(i));
                        luaState.rawSet(-2, i + 1);
                    }
                    luaState.setField(-2, "data");
                    try {
                        CoronaLua.dispatchEvent(luaState, LoadAchievementsListener.this.fListener, 0);
                        CoronaLua.deleteRef(luaState, LoadAchievementsListener.this.fListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
