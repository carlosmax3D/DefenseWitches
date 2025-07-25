package CoronaProvider.g2metrics.stargarage;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeListener;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.NamedJavaFunction;

import java.io.IOException;
import java.util.HashMap;
import jp.stargarage.g2metrics.G2MPlayerTracking;
import jp.stargarage.g2metrics.G2Metrics;

public class LuaLoader implements JavaFunction, CoronaRuntimeListener {
    static final String GET_G2M_CODE = "getG2mCode";
    static final String SET_APPLICATION = "setApplication";
    static final String SET_CATCH_UNCAUGHT_EXCEPTIONS = "setCatchUncaughtExceptions";
    static final String SET_GCM_SENDER_ID = "setGcmSenderId";
    private static final String TAG = "G2metricsCorona";
    static final String TRACK_BUY_ITEM = "trackBuyItem";
    static final String TRACK_CHARGE = "trackCharge";
    static final String TRACK_COMPLETE_TUTORIAL = "trackCompleteTutorial";
    static final String TRACK_CUSTOM_EVENT = "trackCustomEvent";
    static final String TRACK_END_MISSION = "trackEndMission";
    static final String TRACK_FAIL_MISSION = "trackFailMission";
    static final String TRACK_GET_GAME_CURRENCY_AMOUNT = "trackGetGameCurrencyAmount";
    static final String TRACK_LEVEL_UP = "trackLevelUp";
    static final String TRACK_START_MISSION = "trackStartMission";
    static final String TRACK_USER_REGIST = "trackUserRegist";
    static final String TRACK_USE_GAME_CURRENCY_AMOUNT = "trackUseGameCurrencyAmount";
    static final String TRACK_USE_ITEM = "trackUseItem";
    private static final String VERSION = "1.0.0";
    private HashMap<Integer, G2MPlayerTracking.AccountType> accountTypes = new HashMap<>();
    private HashMap<Integer, G2MPlayerTracking.Gender> genders = new HashMap<>();

    private class GetG2mCodeWrapper implements NamedJavaFunction {
        private GetG2mCodeWrapper() {
        }

        public String getName() {
            return LuaLoader.GET_G2M_CODE;
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.getG2mCode(luaState);
        }
    }

    private class SetApplicationWrapper implements NamedJavaFunction {
        private SetApplicationWrapper() {
        }

        public String getName() {
            return LuaLoader.SET_APPLICATION;
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.setApplication(luaState);
        }
    }

    private class SetCatchUncaughtExceptionsWrapper implements NamedJavaFunction {
        private SetCatchUncaughtExceptionsWrapper() {
        }

        public String getName() {
            return LuaLoader.SET_CATCH_UNCAUGHT_EXCEPTIONS;
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.setCatchUncaughtExceptions(luaState);
        }
    }

    private class SetGcmSenderIdWrapper implements NamedJavaFunction {
        private SetGcmSenderIdWrapper() {
        }

        public String getName() {
            return LuaLoader.SET_GCM_SENDER_ID;
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.setGcmSenderId(luaState);
        }
    }

    private class TrackBuyItemWrapper implements NamedJavaFunction {
        private TrackBuyItemWrapper() {
        }

        public String getName() {
            return LuaLoader.TRACK_BUY_ITEM;
        }

        public int invoke(LuaState luaState) {
            try {
                return LuaLoader.this.trackBuyItem(luaState);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    private class TrackChargeWrapper implements NamedJavaFunction {
        private TrackChargeWrapper() {
        }

        public String getName() {
            return LuaLoader.TRACK_CHARGE;
        }

        public int invoke(LuaState luaState) {
            try {
                return LuaLoader.this.trackCharge(luaState);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    private class TrackCompleteTutorialWrapper implements NamedJavaFunction {
        private TrackCompleteTutorialWrapper() {
        }

        public String getName() {
            return LuaLoader.TRACK_COMPLETE_TUTORIAL;
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.trackCompleteTutorial(luaState);
        }
    }

    private class TrackCustomEventWrapper implements NamedJavaFunction {
        private TrackCustomEventWrapper() {
        }

        public String getName() {
            return LuaLoader.TRACK_CUSTOM_EVENT;
        }

        public int invoke(LuaState luaState) {
            try {
                return LuaLoader.this.trackCustomEvent(luaState);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    private class TrackEndMissionWrapper implements NamedJavaFunction {
        private TrackEndMissionWrapper() {
        }

        public String getName() {
            return LuaLoader.TRACK_END_MISSION;
        }

        public int invoke(LuaState luaState) {
            try {
                return LuaLoader.this.trackEndMission(luaState);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    private class TrackFailMissionWrapper implements NamedJavaFunction {
        private TrackFailMissionWrapper() {
        }

        public String getName() {
            return LuaLoader.TRACK_FAIL_MISSION;
        }

        public int invoke(LuaState luaState) {
            try {
                return LuaLoader.this.trackFailMission(luaState);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    private class TrackGetGameCurrencyAmountWrapper implements NamedJavaFunction {
        private TrackGetGameCurrencyAmountWrapper() {
        }

        public String getName() {
            return LuaLoader.TRACK_GET_GAME_CURRENCY_AMOUNT;
        }

        public int invoke(LuaState luaState) {
            try {
                return LuaLoader.this.trackGetGameCurrencyAmount(luaState);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    private class TrackLevelUpWrapper implements NamedJavaFunction {
        private TrackLevelUpWrapper() {
        }

        public String getName() {
            return LuaLoader.TRACK_LEVEL_UP;
        }

        public int invoke(LuaState luaState) {
            try {
                return LuaLoader.this.trackLevelUp(luaState);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    private class TrackStartMissionWrapper implements NamedJavaFunction {
        private TrackStartMissionWrapper() {
        }

        public String getName() {
            return LuaLoader.TRACK_START_MISSION;
        }

        public int invoke(LuaState luaState) {
            try {
                return LuaLoader.this.trackStartMission(luaState);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    private class TrackUseGameCurrencyAmountWrapper implements NamedJavaFunction {
        private TrackUseGameCurrencyAmountWrapper() {
        }

        public String getName() {
            return LuaLoader.TRACK_USE_GAME_CURRENCY_AMOUNT;
        }

        public int invoke(LuaState luaState) {
            try {
                return LuaLoader.this.trackUseGameCurrencyAmount(luaState);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    private class TrackUseItemWrapper implements NamedJavaFunction {
        private TrackUseItemWrapper() {
        }

        public String getName() {
            return LuaLoader.TRACK_USE_ITEM;
        }

        public int invoke(LuaState luaState) {
            try {
                return LuaLoader.this.trackUseItem(luaState);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    private class TrackUserRegistWrapper implements NamedJavaFunction {
        private TrackUserRegistWrapper() {
        }

        public String getName() {
            return LuaLoader.TRACK_USER_REGIST;
        }

        public int invoke(LuaState luaState) {
            try {
                return LuaLoader.this.trackUserRegist(luaState);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    private String ToString(LuaState luaState, int i) {
        switch (luaState.type(-2).ordinal()) {
            case 1:
                return String.valueOf(luaState.toNumber(i));
            default:
                return luaState.toString(i);
        }
    }

    private void post(Runnable runnable) {
        CoronaEnvironment.getCoronaActivity().runOnUiThread(runnable);
    }

    public int getG2mCode(LuaState luaState) {
        try {
            luaState.pushString(G2Metrics.getG2mCode());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }

    public int invoke(LuaState luaState) {
        this.accountTypes.put(1, G2MPlayerTracking.AccountType.Facebook);
        this.accountTypes.put(2, G2MPlayerTracking.AccountType.Twitter);
        this.accountTypes.put(3, G2MPlayerTracking.AccountType.Google);
        this.genders.put(1, G2MPlayerTracking.Gender.Male);
        this.genders.put(2, G2MPlayerTracking.Gender.Female);
        luaState.register(luaState.toString(1), new NamedJavaFunction[]{new SetApplicationWrapper(), new SetGcmSenderIdWrapper(), new SetCatchUncaughtExceptionsWrapper(), new GetG2mCodeWrapper(), new TrackUserRegistWrapper(), new TrackLevelUpWrapper(), new TrackCompleteTutorialWrapper(), new TrackChargeWrapper(), new TrackStartMissionWrapper(), new TrackCompleteTutorialWrapper(), new TrackStartMissionWrapper(), new TrackEndMissionWrapper(), new TrackFailMissionWrapper(), new TrackGetGameCurrencyAmountWrapper(), new TrackUseGameCurrencyAmountWrapper(), new TrackBuyItemWrapper(), new TrackUseItemWrapper(), new TrackCustomEventWrapper()});
        return 1;
    }

    public void onExiting(CoronaRuntime coronaRuntime) {
        post(new Runnable() {
            public void run() {
                G2Metrics.notifyAppEnd();
            }
        });
    }

    public void onLoaded(CoronaRuntime coronaRuntime) {
    }

    public void onResumed(CoronaRuntime coronaRuntime) {
    }

    public void onStarted(CoronaRuntime coronaRuntime) {
    }

    public void onSuspended(CoronaRuntime coronaRuntime) {
    }

    public int setApplication(LuaState luaState) {
        final boolean checkBoolean = luaState.checkBoolean(1);
        post(new Runnable() {
            public void run() {
                try {
                    G2Metrics.setApplication((Application) CoronaEnvironment.getApplicationContext(), checkBoolean);
                } catch (PackageManager.NameNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return 0;
    }

    public int setCatchUncaughtExceptions(LuaState luaState) {
        final boolean z = luaState.toBoolean(1);
        post(new Runnable() {
            public void run() {
                G2Metrics.setCatchUncaughtExceptions(z);
            }
        });
        return 0;
    }

    public int setGcmSenderId(LuaState luaState) {
        final String checkString = luaState.checkString(1);
        if (checkString == null) {
            Log.w(TAG, "WARNING: setGcmSenderId() senderId was null");
        }
        post(new Runnable() {
            public void run() {
                G2Metrics.setGcmSenderId(checkString);
            }
        });
        return 0;
    }

    public int trackBuyItem(LuaState luaState) {
        final String luaState2 = luaState.toString(1);
        final int checkInteger = luaState.checkInteger(2);
        final int checkInteger2 = luaState.checkInteger(3);
        post(new Runnable() {
            public void run() {
                G2MPlayerTracking.trackBuyItem(luaState2, checkInteger, checkInteger2);
            }
        });
        return 0;
    }

    public int trackCharge(LuaState luaState) {
        final String checkString = luaState.checkString(1);
        final double checkNumber = luaState.checkNumber(2);
        final String checkString2 = luaState.checkString(3);
        final int checkInteger = luaState.checkInteger(4);
        if (checkString == null || checkString2 == null || checkNumber <= 0.0d) {
            Log.e(TAG, "item_id currency price is error.");
        } else {
            post(new Runnable() {
                public void run() {
                    G2MPlayerTracking.trackCharge(checkString, (float) checkNumber, checkString2, checkInteger);
                }
            });
        }
        return 0;
    }

    public int trackCompleteTutorial(LuaState luaState) {
        post(new Runnable() {
            public void run() {
                G2MPlayerTracking.trackCompleteTutorial();
            }
        });
        return 0;
    }

    public int trackCustomEvent(LuaState luaState) {
        final String checkString = luaState.checkString(1);
        HashMap hashMap = null;
        if (checkString != null) {
            hashMap = luaState.isTable(2) ? new HashMap(10) : null;
            if (hashMap != null) {
                luaState.pushNil();
                for (int i = 0; i < 10 && luaState.next(2); i++) {
                    String ToString = ToString(luaState, -2);
                    String ToString2 = ToString(luaState, -1);
                    if (!(ToString == null || ToString2 == null)) {
                        hashMap.put(ToString, ToString2);
                    }
                    luaState.pop(1);
                }
            }
        }
        final HashMap hashMap2 = new HashMap(hashMap);
        post(new Runnable() {
            public void run() {
                G2MPlayerTracking.trackCustomEvent(checkString, hashMap2);
            }
        });
        return 0;
    }

    public int trackEndMission(LuaState luaState) {
        final String checkString = luaState.checkString(1);
        post(new Runnable() {
            public void run() {
                G2MPlayerTracking.trackEndMission(checkString);
            }
        });
        return 0;
    }

    public int trackFailMission(LuaState luaState) {
        final String checkString = luaState.checkString(1);
        final String checkString2 = luaState.checkString(2);
        post(new Runnable() {
            public void run() {
                G2MPlayerTracking.trackFailMission(checkString, checkString2);
            }
        });
        return 0;
    }

    public int trackGetGameCurrencyAmount(LuaState luaState) {
        final int checkInteger = luaState.checkInteger(1);
        final String checkString = luaState.checkString(2);
        post(new Runnable() {
            public void run() {
                G2MPlayerTracking.trackGetGameCurrencyAmount(checkInteger, checkString);
            }
        });
        return 0;
    }

    public int trackLevelUp(LuaState luaState) {
        int integer = luaState.toInteger(1);
        final String luaState2 = integer <= 0 ? luaState.toString(1) : String.valueOf(integer);
        post(new Runnable() {
            public void run() {
                G2MPlayerTracking.trackLevelUp(luaState2);
            }
        });
        return 0;
    }

    public int trackStartMission(LuaState luaState) {
        final String checkString = luaState.checkString(1);
        post(new Runnable() {
            public void run() {
                G2MPlayerTracking.trackStartMission(checkString);
            }
        });
        return 0;
    }

    public int trackUseGameCurrencyAmount(LuaState luaState) {
        final int checkInteger = luaState.checkInteger(1);
        final String checkString = luaState.checkString(2);
        post(new Runnable() {
            public void run() {
                G2MPlayerTracking.trackUseGameCurrencyAmount(checkInteger, checkString);
            }
        });
        return 0;
    }

    public int trackUseItem(LuaState luaState) {
        final String checkString = luaState.checkString(1);
        final int checkInteger = luaState.checkInteger(2);
        post(new Runnable() {
            public void run() {
                G2MPlayerTracking.trackUseItem(checkString, checkInteger);
            }
        });
        return 0;
    }

    public int trackUserRegist(LuaState luaState) {
        int checkInteger = luaState.checkInteger(1);
        if (this.accountTypes.containsKey(Integer.valueOf(checkInteger))) {
            final G2MPlayerTracking.AccountType accountType = this.accountTypes.get(Integer.valueOf(checkInteger));
            final String checkString = luaState.checkString(2);
            int checkInteger2 = luaState.checkInteger(3);
            if (this.genders.containsKey(Integer.valueOf(checkInteger2))) {
                final G2MPlayerTracking.Gender gender = this.genders.get(Integer.valueOf(checkInteger2));
                final int checkInteger3 = luaState.checkInteger(4);
                final String checkString2 = luaState.checkString(5);
                post(new Runnable() {
                    public void run() {
                        G2MPlayerTracking.trackUserRegistration(accountType, checkString, gender, checkInteger3, checkString2);
                    }
                });
            } else {
                Log.e(TAG, "gender is error.");
            }
        } else {
            Log.e(TAG, "account type is error.");
        }
        return 0;
    }
}
