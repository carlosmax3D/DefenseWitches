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
import java.util.HashSet;
import java.util.Iterator;

public class PlayerLoaderManager {
    protected CoronaRuntimeTaskDispatcher fDispatcher;
    protected String fEventName;
    protected GamesClient fGamesClient;
    protected int fListener;
    protected boolean fLocalPlayer;
    protected HashSet<String> fNameSet;
    protected HashSet<Player> fPlayerSet = new HashSet<>();

    public class LoadPlayerListener implements OnPlayersLoadedListener {
        public LoadPlayerListener() {
        }

        private void callback() {
            if (PlayerLoaderManager.this.fListener >= 0) {
                final HashSet<Player> hashSet = PlayerLoaderManager.this.fPlayerSet;
                PlayerLoaderManager.this.fDispatcher.send(new CoronaRuntimeTask() {
                    public void executeUsing(CoronaRuntime coronaRuntime) {
                        Iterator it = hashSet.iterator();
                        LuaState luaState = coronaRuntime.getLuaState();
                        CoronaLua.newEvent(luaState, PlayerLoaderManager.this.fEventName);
                        luaState.pushString(PlayerLoaderManager.this.fEventName);
                        luaState.setField(-2, "type");
                        luaState.newTable(hashSet.size(), 0);
                        int i = 1;
                        while (it.hasNext()) {
                            Player player = (Player) it.next();
                            if (!PlayerLoaderManager.this.fLocalPlayer && hashSet.size() > 1) {
                                luaState.newTable(0, 2);
                            }
                            luaState.pushString(player.getPlayerId());
                            luaState.setField(-2, Listener.PLAYER_ID);
                            luaState.pushString(player.getDisplayName());
                            luaState.setField(-2, Listener.ALIAS);
                            if (!PlayerLoaderManager.this.fLocalPlayer && hashSet.size() > 1) {
                                luaState.rawSet(-2, i);
                                i++;
                            }
                        }
                        luaState.setField(-2, "data");
                        try {
                            CoronaLua.dispatchEvent(luaState, PlayerLoaderManager.this.fListener, 0);
                            CoronaLua.deleteRef(luaState, PlayerLoaderManager.this.fListener);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        public void onPlayersLoaded(int i, PlayerBuffer playerBuffer) {
            for (int i2 = 0; i2 < playerBuffer.getCount(); i2++) {
                PlayerLoaderManager.this.fPlayerSet.add(playerBuffer.get(i2));
                PlayerLoaderManager.this.fNameSet.remove(playerBuffer.get(i2).getPlayerId());
            }
            if (PlayerLoaderManager.this.fNameSet.size() < 1) {
                callback();
            }
        }
    }

    public PlayerLoaderManager(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i, GamesClient gamesClient, String str) {
        this.fDispatcher = coronaRuntimeTaskDispatcher;
        this.fListener = i;
        this.fGamesClient = gamesClient;
        this.fEventName = str;
    }

    public void loadPlayers(HashSet<String> hashSet, boolean z) {
        this.fNameSet = hashSet;
        HashSet hashSet2 = new HashSet();
        Iterator<String> it = this.fNameSet.iterator();
        while (it.hasNext()) {
            hashSet2.add(new String(it.next()));
        }
        this.fLocalPlayer = z;
        Iterator it2 = hashSet2.iterator();
        while (it2.hasNext()) {
            this.fGamesClient.loadPlayer(new LoadPlayerListener(), (String) it2.next());
        }
    }
}
