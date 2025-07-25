package CoronaProvider.gameNetwork.google;

import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeReliableMessageSentListener;
import com.naef.jnlua.LuaState;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageManager implements RealTimeMessageReceivedListener {
    static CoronaRuntimeTaskDispatcher fDispatcher;
    static GamesClient fGamesClient;
    static int fMessageListener;
    static MessageManager fMessageManager;

    private MessageManager(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i, GamesClient gamesClient) {
        fGamesClient = gamesClient;
    }

    public static MessageManager getMessageManager(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i, GamesClient gamesClient) {
        if (fMessageManager == null) {
            fMessageManager = new MessageManager(coronaRuntimeTaskDispatcher, i, gamesClient);
        }
        setDispatcher(coronaRuntimeTaskDispatcher);
        if (i > 0) {
            setMessageListener(i);
        }
        return fMessageManager;
    }

    private static void setDispatcher(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher) {
        fDispatcher = coronaRuntimeTaskDispatcher;
    }

    public static void setMessageListener(int i) {
        fMessageListener = i;
    }

    public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) {
        final RealTimeMessage realTimeMessage2 = realTimeMessage;
        fDispatcher.send(new CoronaRuntimeTask() {
            public void executeUsing(CoronaRuntime coronaRuntime) {
                LuaState luaState = coronaRuntime.getLuaState();
                CoronaLua.newEvent(luaState, "messageReceived");
                luaState.pushString("messageReceived");
                luaState.setField(-2, "type");
                luaState.newTable();
                luaState.pushString(new String(realTimeMessage2.getMessageData()));
                luaState.setField(-2, "message");
                luaState.pushString(realTimeMessage2.getSenderParticipantId());
                luaState.setField(-2, "participantId");
                luaState.setField(-2, "data");
                try {
                    CoronaLua.dispatchEvent(luaState, MessageManager.fMessageListener, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendMessage(ArrayList<String> arrayList, String str, String str2, boolean z) {
        if (z) {
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                fGamesClient.sendReliableRealTimeMessage(new RealTimeReliableMessageSentListener() {
                    public void onRealTimeMessageSent(int i, int i2, String str) {
                    }
                }, str.getBytes(), str2, it.next());
            }
            return;
        }
        fGamesClient.sendUnreliableRealTimeMessage(str.getBytes(), str2, (List<String>) arrayList);
    }
}
