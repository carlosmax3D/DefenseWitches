package plugin.notifications;

import com.ansca.corona.Bridge;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.NamedJavaFunction;

public class LuaLoader implements JavaFunction {
    private static final String EVENT_NAME = "pluginlibraryevent";

    private class CancelNotificationWrapper implements NamedJavaFunction {
        private CancelNotificationWrapper() {
        }

        public String getName() {
            return "cancelNotification";
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.cancelNotification(luaState);
        }
    }

    private class RegisterForPushNotificationsWrapper implements NamedJavaFunction {
        private RegisterForPushNotificationsWrapper() {
        }

        public String getName() {
            return "registerForPushNotifications";
        }

        public int invoke(LuaState luaState) {
            return 0;
        }
    }

    private class ScheduleNotificationWrapper implements NamedJavaFunction {
        private ScheduleNotificationWrapper() {
        }

        public String getName() {
            return "scheduleNotification";
        }

        public int invoke(LuaState luaState) {
            return LuaLoader.this.scheduleNotification(luaState);
        }
    }

    /* access modifiers changed from: protected */
    public int cancelNotification(LuaState luaState) {
        if (luaState.isNone(1)) {
            Bridge.cancelAllNotifications();
            return 0;
        }
        Bridge.cancelNotification(Double.valueOf(luaState.toNumber(-1)).intValue());
        return 0;
    }

    public int invoke(LuaState luaState) {
        luaState.register(luaState.toString(1), new NamedJavaFunction[]{new ScheduleNotificationWrapper(), new CancelNotificationWrapper(), new RegisterForPushNotificationsWrapper()});
        return 1;
    }

    /* access modifiers changed from: protected */
    public int scheduleNotification(LuaState luaState) {
        return Bridge.scheduleNotification(luaState, 1);
    }
}
