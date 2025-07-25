package CoronaProvider.gameNetwork.google;

import android.content.Intent;
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaLuaEvent;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.naef.jnlua.LuaState;
import com.tapjoy.TJAdUnitConstants;
import java.util.ArrayList;

public class WaitingRoomResultHandler extends Listener implements CoronaActivity.OnActivityResultHandler {
    private GameHelper fGameHelper;

    public WaitingRoomResultHandler(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i, GameHelper gameHelper) {
        super(coronaRuntimeTaskDispatcher, i);
        this.fGameHelper = gameHelper;
    }

    public void onHandleActivityResult(CoronaActivity coronaActivity, int i, int i2, Intent intent) {
        coronaActivity.unregisterActivityResultHandler(this);
        CoronaRuntimeTaskDispatcher runtimeTaskDispatcher = coronaActivity.getRuntimeTaskDispatcher();
        Room room = (Room) intent.getExtras().get("room");
        if (-1 == i2) {
            pushIntentResult(false, TJAdUnitConstants.String.VIDEO_START, room);
        } else if (i2 == 0 || 10005 == i2) {
            pushIntentResult(true, "cancel", room);
            if (this.fGameHelper != null && this.fGameHelper.getGamesClient() != null) {
                this.fGameHelper.getGamesClient().leaveRoom(RoomManager.getRoomManager(this.fDispatcher, this.fListener, this.fGameHelper.getGamesClient()), room.getRoomId());
            }
        } else if (10001 == i2 && this.fGameHelper != null && this.fGameHelper.getGamesClient() != null) {
            this.fGameHelper.signOut();
        }
    }

    public void pushIntentResult(boolean z, String str, Room room) {
        if (this.fListener >= 0) {
            final GamesClient gamesClient = this.fGameHelper.getGamesClient();
            final boolean z2 = z;
            final String str2 = str;
            final Room room2 = room;
            this.fDispatcher.send(new CoronaRuntimeTask() {
                public void executeUsing(CoronaRuntime coronaRuntime) {
                    LuaState luaState = coronaRuntime.getLuaState();
                    CoronaLua.newEvent(luaState, "waitingRoom");
                    luaState.pushString("waitingRoom");
                    luaState.setField(-2, "type");
                    luaState.newTable();
                    luaState.pushBoolean(z2);
                    luaState.setField(-2, CoronaLuaEvent.ISERROR_KEY);
                    luaState.pushString(str2);
                    luaState.setField(-2, "phase");
                    luaState.pushString(room2.getRoomId());
                    luaState.setField(-2, RoomManager.ROOM_ID);
                    int i = 1;
                    ArrayList<String> participantIds = room2.getParticipantIds();
                    for (int i2 = 0; i2 < participantIds.size(); i2++) {
                        if (participantIds.get(i2) != room2.getParticipantId(gamesClient.getCurrentPlayerId())) {
                            luaState.pushString(participantIds.get(i2));
                            luaState.rawSet(-2, i);
                            i++;
                        }
                    }
                    luaState.setField(-2, "data");
                    try {
                        CoronaLua.dispatchEvent(luaState, WaitingRoomResultHandler.this.fListener, 0);
                        CoronaLua.deleteRef(luaState, WaitingRoomResultHandler.this.fListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
