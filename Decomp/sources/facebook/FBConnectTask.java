package facebook;

import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import facebook.FBSessionEvent;
import jp.stargarage.g2metrics.BuildConfig;

public class FBConnectTask implements CoronaRuntimeTask {
    private static final int REQUEST = 2;
    private static final int SESSION = 0;
    private static final int SESSION_ERROR = 1;
    private String myAccessToken;
    private boolean myDidComplete;
    private boolean myIsDialog;
    private boolean myIsError;
    private int myListener;
    private String myMsg;
    private FBSessionEvent.Phase myPhase;
    private long myTokenExpiration;
    private int myType;

    FBConnectTask(int i, FBSessionEvent.Phase phase, String str, long j) {
        this.myType = 0;
        this.myPhase = phase;
        this.myAccessToken = str;
        this.myTokenExpiration = j / 1000;
        this.myListener = i;
        this.myIsDialog = false;
    }

    FBConnectTask(int i, String str) {
        this.myType = 1;
        this.myAccessToken = BuildConfig.FLAVOR;
        this.myMsg = str;
        this.myTokenExpiration = 0;
        this.myListener = i;
        this.myIsDialog = false;
    }

    FBConnectTask(int i, String str, boolean z) {
        this.myType = 2;
        this.myAccessToken = BuildConfig.FLAVOR;
        this.myTokenExpiration = 0;
        this.myMsg = str;
        this.myIsError = z;
        this.myDidComplete = false;
        this.myListener = i;
        this.myIsDialog = false;
    }

    FBConnectTask(int i, String str, boolean z, boolean z2) {
        this.myType = 2;
        this.myAccessToken = BuildConfig.FLAVOR;
        this.myTokenExpiration = 0;
        this.myMsg = str;
        this.myIsError = z;
        this.myDidComplete = z2;
        this.myListener = i;
        this.myIsDialog = true;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        switch (this.myType) {
            case 0:
                if (this.myAccessToken == null) {
                    new FBSessionEvent(this.myPhase).executeUsing(coronaRuntime);
                    break;
                } else {
                    new FBSessionEvent(this.myAccessToken, this.myTokenExpiration).executeUsing(coronaRuntime);
                    break;
                }
            case 1:
                new FBSessionEvent(FBSessionEvent.Phase.loginFailed, this.myMsg).executeUsing(coronaRuntime);
                break;
            case 2:
                if (!this.myIsDialog) {
                    new FBRequestEvent(this.myMsg, this.myIsError, this.myDidComplete).executeUsing(coronaRuntime);
                    break;
                } else {
                    new FBDialogEvent(this.myMsg, this.myIsError, this.myDidComplete).executeUsing(coronaRuntime);
                    break;
                }
        }
        try {
            CoronaLua.dispatchEvent(coronaRuntime.getLuaState(), this.myListener, 0);
        } catch (Exception e) {
        }
    }
}
