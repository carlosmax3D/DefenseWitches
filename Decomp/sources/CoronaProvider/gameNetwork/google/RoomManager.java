package CoronaProvider.gameNetwork.google;

import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaLuaEvent;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.naef.jnlua.LuaState;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class RoomManager implements RoomUpdateListener, RoomStatusUpdateListener {
    public static final String ROOM_ID = "roomID";
    static CoronaRuntimeTaskDispatcher fDispatcher;
    static GamesClient fGamesClient;
    static int fRoomListener;
    static RoomManager fRoomManager;
    static HashMap<String, Room> fRooms = new HashMap<>();

    private RoomManager(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i, GamesClient gamesClient) {
        fGamesClient = gamesClient;
        fRoomListener = i;
    }

    public static Room getRoom(String str) {
        return fRooms.get(str);
    }

    public static RoomManager getRoomManager(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i, GamesClient gamesClient) {
        if (fRoomManager == null) {
            fRoomManager = new RoomManager(coronaRuntimeTaskDispatcher, i, gamesClient);
        }
        fDispatcher = coronaRuntimeTaskDispatcher;
        setRoomListener(i);
        return fRoomManager;
    }

    private void pushToLua(String str, Room room, List<String> list, boolean z) {
        final String str2 = str;
        final List<String> list2 = list;
        final Room room2 = room;
        final boolean z2 = z;
        fDispatcher.send(new CoronaRuntimeTask() {
            public void executeUsing(CoronaRuntime coronaRuntime) {
                LuaState luaState = coronaRuntime.getLuaState();
                CoronaLua.newEvent(luaState, str2);
                luaState.pushString(str2);
                luaState.setField(-2, "type");
                luaState.newTable();
                int i = 1;
                if (list2 != null) {
                    ListIterator listIterator = list2.listIterator();
                    while (listIterator.hasNext()) {
                        String str = (String) listIterator.next();
                        if (!(room2 == null || str == room2.getParticipantId(RoomManager.fGamesClient.getCurrentPlayerId()))) {
                            luaState.pushString(str);
                            luaState.rawSet(-2, i);
                            i++;
                        }
                    }
                }
                if (room2 != null) {
                    luaState.pushString(room2.getRoomId());
                    luaState.setField(-2, RoomManager.ROOM_ID);
                }
                luaState.pushBoolean(z2);
                luaState.setField(-2, CoronaLuaEvent.ISERROR_KEY);
                luaState.setField(-2, "data");
                try {
                    CoronaLua.dispatchEvent(luaState, RoomManager.fRoomListener, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setRoomListener(int i) {
        fRoomListener = i;
    }

    public void onConnectedToRoom(Room room) {
    }

    public void onDisconnectedFromRoom(Room room) {
    }

    public void onJoinedRoom(int i, Room room) {
        boolean z = false;
        if (room == null) {
            z = true;
        } else {
            fRooms.put(room.getRoomId(), room);
        }
        pushToLua("joinRoom", room, (List<String>) null, z);
    }

    public void onLeftRoom(int i, String str) {
        final String str2 = str;
        fDispatcher.send(new CoronaRuntimeTask() {
            public void executeUsing(CoronaRuntime coronaRuntime) {
                LuaState luaState = coronaRuntime.getLuaState();
                CoronaLua.newEvent(luaState, "leaveRoom");
                luaState.pushString("leaveRoom");
                luaState.setField(-2, "type");
                luaState.newTable();
                luaState.pushString(str2);
                luaState.setField(-2, RoomManager.ROOM_ID);
                luaState.setField(-2, "data");
                try {
                    CoronaLua.dispatchEvent(luaState, RoomManager.fRoomListener, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onP2PConnected(String str) {
    }

    public void onP2PDisconnected(String str) {
    }

    public void onPeerDeclined(Room room, List<String> list) {
        pushToLua("peerDeclinedInvitation", room, list, false);
    }

    public void onPeerInvitedToRoom(Room room, List<String> list) {
    }

    public void onPeerJoined(Room room, List<String> list) {
        pushToLua("peerAcceptedInvitation", room, list, false);
    }

    public void onPeerLeft(Room room, List<String> list) {
        pushToLua("peerLeftRoom", room, list, false);
    }

    public void onPeersConnected(Room room, List<String> list) {
    }

    public void onPeersDisconnected(Room room, List<String> list) {
        pushToLua("peerDisconnectedFromRoom", room, list, false);
    }

    public void onRoomAutoMatching(Room room) {
    }

    public void onRoomConnected(int i, Room room) {
        pushToLua("connectedRoom", room, room.getParticipantIds(), false);
    }

    public void onRoomConnecting(Room room) {
    }

    public void onRoomCreated(int i, Room room) {
        boolean z = false;
        if (room == null) {
            z = true;
        } else {
            fRooms.put(room.getRoomId(), room);
        }
        pushToLua("createRoom", room, (List<String>) null, z);
    }
}
