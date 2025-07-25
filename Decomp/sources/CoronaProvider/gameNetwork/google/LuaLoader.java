package CoronaProvider.gameNetwork.google;

import android.content.Intent;
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaLuaEvent;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.NamedJavaFunction;
import com.tapjoy.TJAdUnitConstants;
import java.util.ArrayList;
import java.util.HashSet;
import jp.stargarage.g2metrics.BuildConfig;

public class LuaLoader implements JavaFunction {
    private static final String EVENT_NAME = "gameNetwork";
    private CoronaRuntimeTaskDispatcher fDispatcher;
    private int fListener;
    /* access modifiers changed from: private */
    public GameHelper helper;

    private class InitWrapper implements NamedJavaFunction {
        private InitWrapper() {
        }

        public String getName() {
            return "init";
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.init(luaState);
        }
    }

    private class RequestWrapper implements NamedJavaFunction {
        private RequestWrapper() {
        }

        public String getName() {
            return ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID;
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.request(luaState);
        }
    }

    private class ShowWrapper implements NamedJavaFunction {
        private ShowWrapper() {
        }

        public String getName() {
            return "show";
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.show(luaState);
        }
    }

    public LuaLoader() {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            throw new IllegalArgumentException("Activity cannot be null.");
        }
        this.fListener = -1;
    }

    private boolean isConnected() {
        return (this.helper == null || this.helper.getGamesClient() == null || !this.helper.getGamesClient().isConnected()) ? false : true;
    }

    public int init(LuaState luaState) {
        int i = -1;
        int top = luaState.getTop();
        if (CoronaLua.isListener(luaState, -1, BuildConfig.FLAVOR)) {
            i = CoronaLua.newRef(luaState, -1);
        }
        luaState.setTop(top);
        if (i <= 0) {
            return 0;
        }
        final int i2 = i;
        this.fDispatcher.send(new CoronaRuntimeTask() {
            public void executeUsing(CoronaRuntime coronaRuntime) {
                boolean z = true;
                try {
                    int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(CoronaEnvironment.getCoronaActivity());
                    boolean z2 = false;
                    String str = BuildConfig.FLAVOR;
                    if (isGooglePlayServicesAvailable == 1) {
                        z2 = true;
                        str = "Service Missing";
                    } else if (isGooglePlayServicesAvailable == 2) {
                        z2 = true;
                        str = "Service Version Update Required";
                    } else if (isGooglePlayServicesAvailable == 3) {
                        z2 = true;
                        str = "Service Disabled";
                    } else if (isGooglePlayServicesAvailable == 9) {
                        z2 = true;
                        str = "Service Invalid";
                    }
                    LuaState luaState = coronaRuntime.getLuaState();
                    CoronaLua.newEvent(luaState, "init");
                    luaState.pushString("init");
                    luaState.setField(-2, "type");
                    if (z2) {
                        z = false;
                    }
                    luaState.pushBoolean(z);
                    luaState.setField(-2, "data");
                    if (z2) {
                        luaState.pushBoolean(z2);
                        luaState.setField(-2, CoronaLuaEvent.ISERROR_KEY);
                        luaState.pushString(str);
                        luaState.setField(-2, "errorMessage");
                        luaState.pushNumber((double) isGooglePlayServicesAvailable);
                        luaState.setField(-2, "errorCode");
                    }
                    if (i2 > 0) {
                        CoronaLua.dispatchEvent(luaState, i2, 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return 0;
    }

    public int invoke(LuaState luaState) {
        this.fDispatcher = new CoronaRuntimeTaskDispatcher(luaState);
        luaState.register(luaState.toString(1), new NamedJavaFunction[]{new InitWrapper(), new ShowWrapper(), new RequestWrapper()});
        return 1;
    }

    public int login(int i, boolean z) {
        CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
        SignInListener signInListener = new SignInListener(this.fDispatcher, i);
        if (!isConnected() || coronaActivity == null) {
            this.helper = new GameHelper(coronaActivity);
            this.helper.setup(signInListener, 1);
            if (!z) {
                this.helper.getGamesClient().connect();
            } else {
                this.helper.setRequestCode(coronaActivity.registerActivityResultHandler(new CoronaActivity.OnActivityResultHandler() {
                    public void onHandleActivityResult(CoronaActivity coronaActivity, int i, int i2, Intent intent) {
                        LuaLoader.this.helper.onActivityResult(i, i2, intent);
                    }
                }));
                if (!this.helper.getGamesClient().isConnected()) {
                    final GameHelper gameHelper = this.helper;
                    coronaActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            gameHelper.beginUserInitiatedSignIn();
                        }
                    });
                }
            }
        } else {
            signInListener.onSignInSucceeded();
        }
        return 0;
    }

    public int logout() {
        if (!isConnected()) {
            return 0;
        }
        try {
            this.helper.signOut();
        } catch (SecurityException e) {
        }
        this.helper = null;
        return 0;
    }

    public int request(LuaState luaState) {
        int length;
        int length2;
        int length3;
        int i = -1;
        int i2 = -1;
        if (luaState.isTable(-1)) {
            luaState.getField(-1, "listener");
            if (CoronaLua.isListener(luaState, -1, BuildConfig.FLAVOR)) {
                i2 = CoronaLua.newRef(luaState, -1);
            }
            luaState.pop(1);
            i = -1 - 1;
        }
        String luaState2 = luaState.toString(i);
        if (luaState2.equals("unlockAchievement")) {
            String str = BuildConfig.FLAVOR;
            int top = luaState.getTop();
            if (luaState.isTable(-1)) {
                luaState.getField(-1, "achievement");
                if (luaState.isTable(-1)) {
                    luaState.getField(-1, TJAdUnitConstants.String.IDENTIFIER);
                    if (luaState.isString(-1)) {
                        str = luaState.toString(-1);
                    }
                }
            }
            luaState.setTop(top);
            if (!isConnected() || str.equals(BuildConfig.FLAVOR)) {
                return 0;
            }
            this.helper.getGamesClient().unlockAchievementImmediate(new UnlockAchievementListener(this.fDispatcher, i2), str);
            return 0;
        } else if (luaState2.equals("setHighScore")) {
            long j = 0;
            int top2 = luaState.getTop();
            String str2 = BuildConfig.FLAVOR;
            if (luaState.isTable(-1)) {
                luaState.getField(-1, "localPlayerScore");
                if (luaState.isTable(-1)) {
                    luaState.getField(-1, "category");
                    if (luaState.isString(-1)) {
                        str2 = luaState.toString(-1);
                    }
                    luaState.getField(-2, "value");
                    if (luaState.isNumber(-1)) {
                        j = (long) luaState.toNumber(-1);
                    }
                }
            }
            luaState.setTop(top2);
            if (!isConnected() || str2.equals(BuildConfig.FLAVOR)) {
                return 0;
            }
            this.helper.getGamesClient().submitScoreImmediate(new SetHighScoreListener(this.fDispatcher, i2), str2, j);
            return 0;
        } else if (luaState2.equals("isConnected")) {
            int i3 = 0 + 1;
            luaState.pushBoolean(isConnected());
            return i3;
        } else if (luaState2.equals("login")) {
            boolean z = true;
            if (luaState.isTable(-1)) {
                luaState.getField(-1, "userInitiated");
                if (luaState.isBoolean(-1)) {
                    z = luaState.toBoolean(-1);
                }
            }
            login(i2, z);
            return 0;
        } else if (luaState2.equals("logout")) {
            logout();
            return 0;
        } else if (luaState2.equals("loadPlayers")) {
            HashSet hashSet = new HashSet();
            int top3 = luaState.getTop();
            if (luaState.isTable(-1)) {
                luaState.getField(-1, "playerIDs");
                if (luaState.isTable(-1) && (length3 = luaState.length(-1)) > 0) {
                    for (int i4 = 1; i4 <= length3; i4++) {
                        luaState.rawGet(-1, i4);
                        hashSet.add(luaState.toString(-1));
                        luaState.pop(1);
                    }
                }
            }
            luaState.setTop(top3);
            if (!isConnected()) {
                return 0;
            }
            new PlayerLoaderManager(this.fDispatcher, i2, this.helper.getGamesClient(), "loadPlayers").loadPlayers(hashSet, false);
            return 0;
        } else if (luaState2.equals("loadLocalPlayer")) {
            if (!isConnected()) {
                return 0;
            }
            HashSet hashSet2 = new HashSet();
            hashSet2.add(this.helper.getGamesClient().getCurrentPlayerId());
            new PlayerLoaderManager(this.fDispatcher, i2, this.helper.getGamesClient(), "loadPlayers").loadPlayers(hashSet2, true);
            return 0;
        } else if (luaState2.equals("loadScores")) {
            String str3 = BuildConfig.FLAVOR;
            String str4 = "Global";
            String str5 = "AllTime";
            int i5 = 25;
            boolean z2 = false;
            int top4 = luaState.getTop();
            if (luaState.isTable(-1)) {
                luaState.getField(-1, "leaderboard");
                if (luaState.isTable(-1)) {
                    luaState.getField(-1, "category");
                    if (luaState.isString(-1)) {
                        str3 = luaState.toString(-1);
                    }
                    luaState.pop(1);
                    luaState.getField(-1, "playerScope");
                    if (luaState.isString(-1)) {
                        str4 = luaState.toString(-1);
                    }
                    luaState.pop(1);
                    luaState.getField(-1, "timeScope");
                    if (luaState.isString(-1)) {
                        str5 = luaState.toString(-1);
                    }
                    luaState.pop(1);
                    luaState.getField(-1, "playerCentered");
                    if (luaState.isBoolean(-1)) {
                        z2 = luaState.toBoolean(-1);
                    }
                    luaState.pop(1);
                    luaState.getField(-1, "range");
                    if (luaState.isTable(-1)) {
                        luaState.rawGet(-1, 2);
                        if (luaState.isNumber(-1) && (i5 = Double.valueOf(luaState.toNumber(-1)).intValue()) > 25) {
                            i5 = 25;
                        }
                    }
                }
                if (!str3.equals(BuildConfig.FLAVOR)) {
                    int i6 = str5.equals("Week") ? 1 : str5.equals("Today") ? 0 : 2;
                    int i7 = str4.equals("FriendsOnly") ? 1 : 0;
                    if (isConnected()) {
                        if (z2) {
                            this.helper.getGamesClient().loadPlayerCenteredScores(new LoadTopScoresListener(this.fDispatcher, i2, str3), str3, i6, i7, i5, true);
                        } else {
                            this.helper.getGamesClient().loadTopScores(new LoadTopScoresListener(this.fDispatcher, i2, str3), str3, i6, i7, i5, true);
                        }
                    }
                }
            }
            luaState.setTop(top4);
            return 0;
        } else if (luaState2.equals("loadAchievements") || luaState2.equals("loadAchievementDescriptions")) {
            if (!isConnected()) {
                return 0;
            }
            this.helper.getGamesClient().loadAchievements(new LoadAchievementsListener(this.fDispatcher, i2), true);
            return 0;
        } else if (luaState2.equals("loadLeaderboardCategories")) {
            if (!isConnected()) {
                return 0;
            }
            this.helper.getGamesClient().loadLeaderboardMetadata(new LoadLeaderboardCategoriesListener(this.fDispatcher, i2), true);
            return 0;
        } else if (luaState2.equals("createRoom")) {
            if (!isConnected()) {
                return 0;
            }
            ArrayList arrayList = new ArrayList();
            int i8 = 0;
            int i9 = 0;
            int top5 = luaState.getTop();
            if (luaState.isTable(-1)) {
                luaState.getField(-1, "playerIDs");
                if (luaState.isTable(-1) && (length2 = luaState.length(-1)) > 0) {
                    for (int i10 = 1; i10 <= length2; i10++) {
                        luaState.rawGet(-1, i10);
                        arrayList.add(luaState.toString(-1));
                        luaState.pop(1);
                    }
                }
                luaState.pop(1);
                luaState.getField(-1, "maxAutoMatchPlayers");
                i8 = (int) luaState.toNumber(-1);
                luaState.pop(1);
                luaState.getField(-1, "minAutoMatchPlayers");
                i9 = (int) luaState.toNumber(-1);
            }
            luaState.setTop(top5);
            RoomManager roomManager = RoomManager.getRoomManager(this.fDispatcher, i2, this.helper.getGamesClient());
            MessageManager messageManager = MessageManager.getMessageManager(this.fDispatcher, i2, this.helper.getGamesClient());
            RoomConfig.Builder builder = RoomConfig.builder(roomManager);
            if (arrayList.size() > 0) {
                builder.addPlayersToInvite((ArrayList<String>) arrayList);
            }
            if (i9 > 0 || i8 > 0) {
                builder.setAutoMatchCriteria(RoomConfig.createAutoMatchCriteria(i9, i8, 0));
            }
            builder.setMessageReceivedListener(messageManager);
            builder.setRoomStatusUpdateListener(roomManager);
            builder.setSocketCommunicationEnabled(false);
            this.helper.getGamesClient().createRoom(builder.build());
            return 0;
        } else if (luaState2.equals("joinRoom")) {
            if (!isConnected()) {
                return 0;
            }
            String str6 = null;
            int top6 = luaState.getTop();
            if (luaState.isTable(-1)) {
                luaState.getField(-1, RoomManager.ROOM_ID);
                if (luaState.isString(-1)) {
                    str6 = luaState.toString(-1);
                }
            }
            luaState.setTop(top6);
            if (str6 == null) {
                return 0;
            }
            RoomManager roomManager2 = RoomManager.getRoomManager(this.fDispatcher, i2, this.helper.getGamesClient());
            MessageManager messageManager2 = MessageManager.getMessageManager(this.fDispatcher, i2, this.helper.getGamesClient());
            RoomConfig.Builder builder2 = RoomConfig.builder(roomManager2);
            builder2.setInvitationIdToAccept(str6);
            builder2.setSocketCommunicationEnabled(false);
            builder2.setMessageReceivedListener(messageManager2);
            builder2.setRoomStatusUpdateListener(roomManager2);
            this.helper.getGamesClient().joinRoom(builder2.build());
            return 0;
        } else if (luaState2.equals("sendMessage")) {
            if (!isConnected()) {
                return 0;
            }
            ArrayList arrayList2 = new ArrayList();
            String str7 = null;
            String str8 = null;
            boolean z3 = true;
            int top7 = luaState.getTop();
            if (luaState.isTable(-1)) {
                luaState.getField(-1, "playerIDs");
                if (luaState.isTable(-1) && (length = luaState.length(-1)) > 0) {
                    for (int i11 = 1; i11 <= length; i11++) {
                        luaState.rawGet(-1, i11);
                        arrayList2.add(luaState.toString(-1));
                        luaState.pop(1);
                    }
                }
                luaState.pop(1);
                luaState.getField(-1, RoomManager.ROOM_ID);
                if (luaState.isString(-1)) {
                    str8 = luaState.toString(-1);
                }
                luaState.pop(1);
                luaState.getField(-1, "message");
                if (luaState.isString(-1)) {
                    str7 = luaState.toString(-1);
                }
                luaState.pop(1);
                luaState.getField(-1, "reliable");
                if (luaState.isBoolean(-1)) {
                    z3 = luaState.toBoolean(-1);
                }
                luaState.pop(1);
            }
            luaState.setTop(top7);
            if (arrayList2 == null || str7 == null || str8 == null) {
                return 0;
            }
            MessageManager.getMessageManager(this.fDispatcher, i2, this.helper.getGamesClient()).sendMessage(arrayList2, str7, str8, z3);
            return 0;
        } else if (luaState2.equals("leaveRoom")) {
            if (!isConnected()) {
                return 0;
            }
            String str9 = null;
            int top8 = luaState.getTop();
            if (luaState.isTable(-1)) {
                luaState.getField(-1, RoomManager.ROOM_ID);
                if (luaState.isString(-1)) {
                    str9 = luaState.toString(-1);
                }
            }
            luaState.setTop(top8);
            if (str9 == null) {
                return 0;
            }
            this.helper.getGamesClient().leaveRoom(RoomManager.getRoomManager(this.fDispatcher, i2, this.helper.getGamesClient()), str9);
            return 0;
        } else if (luaState2.equals("setMessageReceivedListener")) {
            MessageManager.setMessageListener(i2);
            return 0;
        } else if (luaState2.equals("setRoomListener")) {
            RoomManager.setRoomListener(i2);
            return 0;
        } else if (luaState2.equals("setInvitationReceivedListener")) {
            if (!isConnected()) {
                return 0;
            }
            this.helper.getGamesClient().registerInvitationListener(new InvitationReceivedListener(this.fDispatcher, i2));
            return 0;
        } else if (!luaState2.equals("loadFriends")) {
            return 0;
        } else {
            new LoadInvitablePlayersManager(this.fDispatcher, i2, this.helper.getGamesClient()).load();
            return 0;
        }
    }

    public int show(LuaState luaState) {
        int i = -1;
        int i2 = -1;
        int top = luaState.getTop();
        if (luaState.isTable(-1)) {
            luaState.getField(-1, "listener");
            if (CoronaLua.isListener(luaState, -1, "googlePlayGames")) {
                i = CoronaLua.newRef(luaState, -1);
            }
            i2 = -1 - 1;
        }
        luaState.setTop(top);
        String luaState2 = luaState.toString(i2);
        CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
        if (!isConnected() || coronaActivity == null) {
            return 0;
        }
        final GameHelper gameHelper = this.helper;
        if (luaState2.equals("achievements")) {
            final int registerActivityResultHandler = coronaActivity.registerActivityResultHandler(new CoronaActivity.OnActivityResultHandler() {
                public void onHandleActivityResult(CoronaActivity coronaActivity, int i, int i2, Intent intent) {
                    coronaActivity.unregisterActivityResultHandler(this);
                    if (10001 == i2) {
                        LuaLoader.this.logout();
                    }
                }
            });
            coronaActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaEnvironment.getCoronaActivity() != null) {
                        CoronaEnvironment.getCoronaActivity().startActivityForResult(gameHelper.getGamesClient().getAchievementsIntent(), registerActivityResultHandler);
                    }
                }
            });
            return 0;
        } else if (luaState2.equals("leaderboards")) {
            final int registerActivityResultHandler2 = coronaActivity.registerActivityResultHandler(new CoronaActivity.OnActivityResultHandler() {
                public void onHandleActivityResult(CoronaActivity coronaActivity, int i, int i2, Intent intent) {
                    coronaActivity.unregisterActivityResultHandler(this);
                    if (10001 == i2) {
                        LuaLoader.this.logout();
                    }
                }
            });
            coronaActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaEnvironment.getCoronaActivity() != null) {
                        CoronaEnvironment.getCoronaActivity().startActivityForResult(gameHelper.getGamesClient().getAllLeaderboardsIntent(), registerActivityResultHandler2);
                    }
                }
            });
            return 0;
        } else if (luaState2.equals("selectPlayers")) {
            int i3 = -1;
            int i4 = -1;
            if (luaState.isTable(-1)) {
                luaState.getField(-1, "minPlayers");
                if (luaState.isNumber(-1)) {
                    i3 = (int) luaState.toNumber(-1);
                    luaState.pop(1);
                }
                luaState.getField(-1, "maxPlayers");
                if (luaState.isNumber(-1)) {
                    i4 = (int) luaState.toNumber(-1);
                    luaState.pop(1);
                }
            }
            if (i3 <= -1 || i4 <= -1) {
                return 0;
            }
            final int registerActivityResultHandler3 = coronaActivity.registerActivityResultHandler(new SelectPlayersResultHandler(this.fDispatcher, i, gameHelper));
            final int i5 = i3;
            final int i6 = i4;
            coronaActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaEnvironment.getCoronaActivity() != null) {
                        CoronaEnvironment.getCoronaActivity().startActivityForResult(gameHelper.getGamesClient().getRealTimeSelectOpponentsIntent(i5, i6), registerActivityResultHandler3);
                    }
                }
            });
            return 0;
        } else if (luaState2.equals("waitingRoom")) {
            String str = null;
            int i7 = 0;
            if (luaState.isTable(-1)) {
                luaState.getField(-1, RoomManager.ROOM_ID);
                str = luaState.toString(-1);
                luaState.pop(1);
                luaState.getField(-1, "minPlayers");
                if (luaState.isNumber(-1)) {
                    i7 = (int) luaState.toNumber(-1);
                }
            }
            if (str == null) {
                return 0;
            }
            int registerActivityResultHandler4 = coronaActivity.registerActivityResultHandler(new WaitingRoomResultHandler(this.fDispatcher, i, gameHelper));
            final Room room = RoomManager.getRoom(str);
            final int i8 = i7;
            final GameHelper gameHelper2 = gameHelper;
            final int i9 = registerActivityResultHandler4;
            coronaActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaEnvironment.getCoronaActivity() != null && room != null) {
                        CoronaEnvironment.getCoronaActivity().startActivityForResult(gameHelper2.getGamesClient().getRealTimeWaitingRoomIntent(room, i8), i9);
                    }
                }
            });
            return 0;
        } else if (!luaState2.equals("invitations")) {
            return 0;
        } else {
            final int registerActivityResultHandler5 = coronaActivity.registerActivityResultHandler(new InvitationResultHandler(this.fDispatcher, i, gameHelper));
            coronaActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (CoronaEnvironment.getCoronaActivity() != null) {
                        CoronaEnvironment.getCoronaActivity().startActivityForResult(gameHelper.getGamesClient().getInvitationInboxIntent(), registerActivityResultHandler5);
                    }
                }
            });
            return 0;
        }
    }
}
