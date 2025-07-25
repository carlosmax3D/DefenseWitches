package CoronaProvider.gameNetwork.google;

import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.OnPlayersLoadedListener;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.naef.jnlua.LuaState;
import java.util.HashMap;

public class LoadInvitablePlayersManager {
    /* access modifiers changed from: private */
    public CoronaRuntimeTaskDispatcher fDispatcher;
    /* access modifiers changed from: private */
    public GamesClient fGamesClient;
    /* access modifiers changed from: private */
    public int fListener;

    public class LoadPlayerListener implements OnPlayersLoadedListener {
        /* access modifiers changed from: private */
        public HashMap<String, Player> fPlayers = new HashMap<>();

        public LoadPlayerListener() {
        }

        private void callback() {
            if (LoadInvitablePlayersManager.this.fListener >= 0) {
                LoadInvitablePlayersManager.this.fDispatcher.send(new CoronaRuntimeTask() {
                    public void executeUsing(CoronaRuntime coronaRuntime) {
                        LuaState luaState = coronaRuntime.getLuaState();
                        CoronaLua.newEvent(luaState, "loadFriends");
                        luaState.pushString("loadFriends");
                        luaState.setField(-2, "type");
                        luaState.newTable();
                        int i = 1;
                        for (Player pushPlayerToLua : LoadPlayerListener.this.fPlayers.values()) {
                            Listener.pushPlayerToLua(luaState, pushPlayerToLua);
                            luaState.rawSet(-2, i);
                            i++;
                        }
                        luaState.setField(-2, "data");
                        try {
                            CoronaLua.dispatchEvent(luaState, LoadInvitablePlayersManager.this.fListener, 0);
                            CoronaLua.deleteRef(luaState, LoadInvitablePlayersManager.this.fListener);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        public void onPlayersLoaded(int i, PlayerBuffer playerBuffer) {
            if (playerBuffer.getCount() > 1) {
                int i2 = 0;
                while (i2 < playerBuffer.getCount()) {
                    String playerId = playerBuffer.get(i2).getPlayerId();
                    if (this.fPlayers.containsKey(playerId)) {
                        callback();
                        return;
                    } else {
                        this.fPlayers.put(playerId, playerBuffer.get(i2));
                        i2++;
                    }
                }
                LoadInvitablePlayersManager.this.fGamesClient.loadMoreInvitablePlayers(this, 25);
                return;
            }
            callback();
        }
    }

    public LoadInvitablePlayersManager(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i, GamesClient gamesClient) {
        this.fDispatcher = coronaRuntimeTaskDispatcher;
        this.fListener = i;
        this.fGamesClient = gamesClient;
    }

    public void load() {
        if (this.fDispatcher != null && this.fListener > 0 && this.fGamesClient != null) {
            this.fGamesClient.loadMoreInvitablePlayers(new LoadPlayerListener(), 25);
        }
    }
}
