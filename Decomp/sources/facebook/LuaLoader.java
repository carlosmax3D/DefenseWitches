package facebook;

import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeProvider;
import com.facebook.share.internal.ShareConstants;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;
import com.naef.jnlua.NamedJavaFunction;
import java.util.ArrayList;
import java.util.Hashtable;

public class LuaLoader implements JavaFunction {
    /* access modifiers changed from: private */
    public CoronaRuntime mRuntime;

    private class LoginWrapper implements NamedJavaFunction {
        private LoginWrapper() {
        }

        public String getName() {
            return "login";
        }

        public int invoke(LuaState luaState) {
            String luaState2 = luaState.toString(1);
            int i = 1 + 1;
            int i2 = -1;
            if (CoronaLua.isListener(luaState, 2, "fbconnect")) {
                i2 = CoronaLua.newRef(luaState, 2);
            }
            int i3 = i + 1;
            ArrayList arrayList = new ArrayList();
            if (luaState.type(i3) == LuaType.TABLE) {
                int length = luaState.length(i3);
                for (int i4 = 1; i4 <= length; i4++) {
                    luaState.rawGet(i3, i4);
                    arrayList.add(luaState.toString(-1));
                    luaState.pop(1);
                }
            }
            FacebookController.facebookLogin(LuaLoader.this.mRuntime, luaState2, i2, (String[]) arrayList.toArray(new String[1]));
            return 0;
        }
    }

    private class LogoutWrapper implements NamedJavaFunction {
        private LogoutWrapper() {
        }

        public String getName() {
            return "logout";
        }

        public int invoke(LuaState luaState) {
            FacebookController.facebookLogout();
            return 0;
        }
    }

    private class PublishInstallWrapper implements NamedJavaFunction {
        private PublishInstallWrapper() {
        }

        public String getName() {
            return "publishInstall";
        }

        public int invoke(LuaState luaState) {
            FacebookController.publishInstall(luaState.toString(-1));
            return 0;
        }
    }

    private class RequestWrapper implements NamedJavaFunction {
        private RequestWrapper() {
        }

        public String getName() {
            return ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID;
        }

        public int invoke(LuaState luaState) {
            String luaState2 = luaState.toString(1);
            int i = 1 + 1;
            String str = "GET";
            if (luaState.type(i) == LuaType.STRING) {
                str = luaState.toString(i);
            }
            int i2 = i + 1;
            Hashtable<Object, Object> hashtable = luaState.type(i2) == LuaType.TABLE ? CoronaLua.toHashtable(luaState, i2) : new Hashtable<>();
            int i3 = i2 + 1;
            FacebookController.facebookRequest(LuaLoader.this.mRuntime, luaState2, str, hashtable);
            return 0;
        }
    }

    private class ShowDialogWrapper implements NamedJavaFunction {
        private ShowDialogWrapper() {
        }

        public String getName() {
            return "showDialog";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            if (coronaActivity == null) {
                throw new IllegalArgumentException("Activity cannot be null.");
            }
            String luaState2 = luaState.toString(1);
            int i = 1 + 1;
            Hashtable<Object, Object> hashtable = null;
            if (luaState.type(i) == LuaType.TABLE) {
                hashtable = CoronaLua.toHashtable(luaState, i);
            }
            int i2 = i + 1;
            FacebookController.facebookDialog(LuaLoader.this.mRuntime, coronaActivity, luaState2, hashtable);
            return 0;
        }
    }

    public LuaLoader() {
        if (CoronaEnvironment.getCoronaActivity() == null) {
            throw new IllegalArgumentException("Activity cannot be null.");
        }
    }

    public int invoke(LuaState luaState) {
        this.mRuntime = CoronaRuntimeProvider.getRuntimeByLuaState(luaState);
        luaState.register(luaState.toString(1), new NamedJavaFunction[]{new LoginWrapper(), new LogoutWrapper(), new PublishInstallWrapper(), new RequestWrapper(), new ShowDialogWrapper()});
        return 1;
    }
}
