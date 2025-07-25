package CoronaProvider.gameNetwork.google;

import com.ansca.corona.CoronaRuntimeTaskDispatcher;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.achievement.Achievement;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.SubmitScoreResult;
import com.google.android.gms.games.multiplayer.Invitation;
import com.naef.jnlua.LuaState;
import com.tapjoy.TJAdUnitConstants;
import java.util.Date;

public abstract class Listener {
    public static final String ALIAS = "alias";
    public static final String DATA = "data";
    public static final String PLAYER_ID = "playerID";
    public static final String TYPE = "type";
    protected CoronaRuntimeTaskDispatcher fDispatcher;
    protected int fListener;

    public Listener(CoronaRuntimeTaskDispatcher coronaRuntimeTaskDispatcher, int i) {
        this.fListener = i;
        this.fDispatcher = coronaRuntimeTaskDispatcher;
    }

    protected static void pushAchievementToLua(LuaState luaState, Achievement achievement) {
        boolean z = true;
        luaState.newTable(0, 6);
        luaState.pushString(achievement.getAchievementId());
        luaState.setField(-2, TJAdUnitConstants.String.IDENTIFIER);
        luaState.pushString(achievement.getName());
        luaState.setField(-2, "title");
        luaState.pushString(achievement.getDescription());
        luaState.setField(-2, "description");
        luaState.pushBoolean(achievement.getState() == 0);
        luaState.setField(-2, "isCompleted");
        if (achievement.getState() != 2) {
            z = false;
        }
        luaState.pushBoolean(z);
        luaState.setField(-2, "isHidden");
        luaState.pushNumber((double) achievement.getLastUpdatedTimestamp());
        luaState.setField(-2, "lastReportedDate");
    }

    protected static void pushInvitationToLua(LuaState luaState, Invitation invitation) {
        luaState.newTable(0, 3);
        luaState.pushString(invitation.getInvitationId());
        luaState.setField(-2, RoomManager.ROOM_ID);
        luaState.pushString(invitation.getInviter().getDisplayName());
        luaState.setField(-2, ALIAS);
        luaState.pushString(invitation.getInviter().getPlayer().getPlayerId());
        luaState.setField(-2, PLAYER_ID);
    }

    protected static void pushLeaderboardScoreToLua(LuaState luaState, LeaderboardScore leaderboardScore, String str) {
        Player scoreHolder = leaderboardScore.getScoreHolder();
        luaState.newTable(0, 7);
        luaState.pushString(scoreHolder.getPlayerId());
        luaState.setField(-2, PLAYER_ID);
        luaState.pushString(str);
        luaState.setField(-2, "category");
        luaState.pushNumber((double) leaderboardScore.getRawScore());
        luaState.setField(-2, "value");
        luaState.pushString(new Date(leaderboardScore.getTimestampMillis()).toString());
        luaState.setField(-2, "date");
        luaState.pushString(leaderboardScore.getDisplayScore());
        luaState.setField(-2, "formattedValue");
        luaState.pushNumber((double) leaderboardScore.getRank());
        luaState.setField(-2, "rank");
        luaState.pushNumber((double) leaderboardScore.getTimestampMillis());
        luaState.setField(-2, "unixTime");
    }

    protected static void pushLeaderboardToLua(LuaState luaState, Leaderboard leaderboard) {
        luaState.newTable(0, 2);
        luaState.pushString(leaderboard.getLeaderboardId());
        luaState.setField(-2, "category");
        luaState.pushString(leaderboard.getDisplayName());
        luaState.setField(-2, "title");
    }

    protected static void pushPlayerToLua(LuaState luaState, Player player) {
        luaState.newTable(0, 2);
        luaState.pushString(player.getPlayerId());
        luaState.setField(-2, PLAYER_ID);
        luaState.pushString(player.getDisplayName());
        luaState.setField(-2, ALIAS);
    }

    protected static void pushSubmitScoreResultToLua(LuaState luaState, SubmitScoreResult submitScoreResult) {
        luaState.newTable(4, 0);
        luaState.pushString(submitScoreResult.getLeaderboardId());
        luaState.setField(-2, "category");
        SubmitScoreResult.Result scoreResult = submitScoreResult.getScoreResult(2);
        if (scoreResult != null) {
            luaState.pushNumber((double) scoreResult.rawScore);
            luaState.setField(-2, "value");
            luaState.pushString(scoreResult.formattedScore);
            luaState.setField(-2, "formattedValue");
        }
        luaState.pushString(submitScoreResult.getPlayerId());
        luaState.setField(-2, PLAYER_ID);
    }
}
