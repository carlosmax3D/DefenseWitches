package jp.stargarage.g2metrics;

import java.util.HashMap;

public final class G2MPlayerTracking {
    private static final G2Metrics instance = G2Metrics.getInstance();

    public enum AccountType {
        Facebook(1),
        Twitter(2),
        Google(3);
        
        final Integer id;

        private AccountType(Integer num) {
            this.id = num;
        }
    }

    public enum Gender {
        Male(1),
        Female(2);
        
        final Integer id;

        private Gender(Integer num) {
            this.id = num;
        }
    }

    public static void trackBuyItem(String str, int i, int i2) {
        try {
            EventValueItem eventValueItem = new EventValueItem();
            eventValueItem.item_id = str;
            eventValueItem.count = i;
            eventValueItem.price = Integer.valueOf(i2);
            instance.addEventLog("it01", eventValueItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackCharge(String str, float f, String str2, int i) {
        try {
            EventValueCharge eventValueCharge = new EventValueCharge();
            eventValueCharge.item_id = str;
            eventValueCharge.price = f;
            eventValueCharge.currency = str2;
            eventValueCharge.game_currency_amount = i;
            instance.addEventLog("gb01", eventValueCharge);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackCompleteTutorial() {
        try {
            instance.addEventLog("tr01", (ApiEntityBase) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackCustomEvent(String str, HashMap<String, String> hashMap) {
        try {
            EventValueCustomEvent eventValueCustomEvent = new EventValueCustomEvent();
            eventValueCustomEvent.event = str;
            eventValueCustomEvent.event_params = hashMap;
            instance.addEventLog("ce01", eventValueCustomEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackEndMission(String str) {
        try {
            EventValueMission eventValueMission = new EventValueMission();
            eventValueMission.mission_id = str;
            instance.addEventLog("ms02", eventValueMission);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackFailMission(String str, String str2) {
        try {
            EventValueMission eventValueMission = new EventValueMission();
            eventValueMission.mission_id = str;
            eventValueMission.reason = str2;
            instance.addEventLog("ms03", eventValueMission);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackGetGameCurrencyAmount(int i, String str) {
        try {
            EventValueGameCurrencyAmount eventValueGameCurrencyAmount = new EventValueGameCurrencyAmount();
            eventValueGameCurrencyAmount.amount = i;
            eventValueGameCurrencyAmount.reason = str;
            instance.addEventLog("cc01", eventValueGameCurrencyAmount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackLevelUp(int i) {
        try {
            trackLevelUp(String.valueOf(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackLevelUp(String str) {
        try {
            EventValueLevelUp eventValueLevelUp = new EventValueLevelUp();
            eventValueLevelUp.level = str;
            instance.addEventLog("lu01", eventValueLevelUp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackStartMission(String str) {
        try {
            EventValueMission eventValueMission = new EventValueMission();
            eventValueMission.mission_id = str;
            instance.addEventLog("ms01", eventValueMission);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackUseGameCurrencyAmount(int i, String str) {
        try {
            EventValueGameCurrencyAmount eventValueGameCurrencyAmount = new EventValueGameCurrencyAmount();
            eventValueGameCurrencyAmount.amount = i;
            eventValueGameCurrencyAmount.reason = str;
            instance.addEventLog("cc02", eventValueGameCurrencyAmount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackUseItem(String str, int i) {
        try {
            EventValueItem eventValueItem = new EventValueItem();
            eventValueItem.item_id = str;
            eventValueItem.count = i;
            instance.addEventLog("it02", eventValueItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trackUserRegistration(AccountType accountType, String str, Gender gender, int i, String str2) {
        try {
            EventValueUserRegistration eventValueUserRegistration = new EventValueUserRegistration();
            eventValueUserRegistration.account_type_id = accountType.id.intValue();
            eventValueUserRegistration.player_name = str;
            eventValueUserRegistration.player_gender = gender.id.intValue();
            eventValueUserRegistration.player_age = i;
            eventValueUserRegistration.player_server = str2;
            instance.addEventLog("ur01", eventValueUserRegistration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
