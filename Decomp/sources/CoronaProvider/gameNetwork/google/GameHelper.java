package CoronaProvider.gameNetwork.google;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;
import com.ansca.corona.CoronaEnvironment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.games.GamesClient;
import java.util.Vector;

public class GameHelper implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
    public static final int CLIENT_ALL = 1;
    public static final int CLIENT_GAMES = 1;
    public static final int CLIENT_NONE = 0;
    static int RC_RESOLVE = 9001;
    static final int RC_UNUSED = 9002;
    static final String SIGN_IN_ERROR_MESSAGE = "Could not sign in. Please try again.";
    static final String SIGN_IN_MESSAGE = "Signing in with Google...";
    static final String SIGN_OUT_MESSAGE = "Signing out...";
    Activity mActivity = null;
    boolean mAutoSignIn = true;
    int mClientCurrentlyConnecting = 0;
    int mConnectedClients = 0;
    ConnectionResult mConnectionResult = null;
    Context mContext = null;
    boolean mDebugLog = true;
    String mDebugTag = "BaseGameActivity";
    boolean mExpectingActivityResult = false;
    GamesClient mGamesClient = null;
    GameHelperListener mListener = null;
    ProgressDialog mProgressDialog = null;
    int mRequestedClients = 0;
    String[] mScopes;
    boolean mSignedIn = false;
    boolean mUserInitiatedSignIn = false;

    public interface GameHelperListener {
        void onSignInFailed();

        void onSignInSucceeded();
    }

    public GameHelper(Activity activity) {
        this.mActivity = activity;
        this.mContext = activity;
    }

    private void addToScope(StringBuilder sb, String str) {
        if (sb.length() == 0) {
            sb.append("oauth2:");
        } else {
            sb.append(" ");
        }
        sb.append(str);
    }

    public void beginUserInitiatedSignIn() {
        if (!this.mSignedIn) {
            this.mAutoSignIn = true;
            int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mContext);
            debugLog("isGooglePlayServicesAvailable returned " + isGooglePlayServicesAvailable);
            if (isGooglePlayServicesAvailable != 0) {
                debugLog("Google Play services not available. Show error dialog.");
                if (this.mListener != null) {
                    this.mListener.onSignInFailed();
                    return;
                }
                return;
            }
            this.mUserInitiatedSignIn = true;
            if (this.mConnectionResult != null) {
                debugLog("beginUserInitiatedSignIn: continuing pending sign-in flow.");
                resolveConnectionResult();
                return;
            }
            debugLog("beginUserInitiatedSignIn: starting new sign-in flow.");
            startConnections();
        }
    }

    /* access modifiers changed from: package-private */
    public void connectCurrentClient() {
        switch (this.mClientCurrentlyConnecting) {
            case 1:
                this.mGamesClient.connect();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public void connectNextClient() {
        int i = this.mRequestedClients & (this.mConnectedClients ^ -1);
        if (i == 0) {
            succeedSignIn();
            return;
        }
        try {
            if (this.mGamesClient == null || (i & 1) == 0) {
                throw new AssertionError("Not all clients connected, yet no one is next. R=" + this.mRequestedClients + ", C=" + this.mConnectedClients);
            }
            debugLog("Connecting GamesClient.");
            this.mClientCurrentlyConnecting = 1;
            connectCurrentClient();
        } catch (Exception e) {
            Log.e(this.mDebugTag, "*** EXCEPTION while attempting to connect. Details follow.");
            e.printStackTrace();
            giveUp();
        }
    }

    /* access modifiers changed from: package-private */
    public void debugLog(String str) {
        if (this.mDebugLog) {
            Log.d(this.mDebugTag, str);
        }
    }

    public void enableDebugLog(boolean z, String str) {
        this.mDebugLog = z;
        this.mDebugTag = str;
    }

    public GamesClient getGamesClient() {
        return this.mGamesClient;
    }

    public int getRequestCode() {
        return RC_RESOLVE;
    }

    public String getScopes() {
        StringBuilder sb = new StringBuilder();
        if ((this.mRequestedClients & 1) != 0) {
            addToScope(sb, Scopes.GAMES);
        }
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public void giveUp() {
        debugLog("giveUp: giving up on connection. " + (this.mConnectionResult == null ? "(no connection result)" : "Status code: " + this.mConnectionResult.getErrorCode()));
        this.mAutoSignIn = false;
        if (this.mListener != null) {
            this.mListener.onSignInFailed();
        }
    }

    /* access modifiers changed from: package-private */
    public Dialog makeSignInErrorDialog(String str) {
        return new AlertDialog.Builder(this.mContext).setTitle("Sign-in error").setMessage(str).setNeutralButton("OK", (DialogInterface.OnClickListener) null).create();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == RC_RESOLVE) {
            this.mExpectingActivityResult = false;
            debugLog("onActivityResult, req " + i + " response " + i2);
            if (i2 == -1) {
                debugLog("responseCode == RESULT_OK. So connecting.");
                connectCurrentClient();
                return;
            }
            debugLog("responseCode != RESULT_OK, so not reconnecting.");
            giveUp();
        }
    }

    public void onConnected(Bundle bundle) {
        debugLog("onConnected: connected! client=" + this.mClientCurrentlyConnecting);
        this.mConnectedClients |= this.mClientCurrentlyConnecting;
        connectNextClient();
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.mConnectionResult = connectionResult;
        debugLog("onConnectionFailed: result " + connectionResult.getErrorCode());
        if (!this.mUserInitiatedSignIn) {
            debugLog("onConnectionFailed: since user didn't initiate sign-in, failing now.");
            this.mConnectionResult = connectionResult;
            if (this.mListener != null) {
                this.mListener.onSignInFailed();
                return;
            }
            return;
        }
        debugLog("onConnectionFailed: since user initiated sign-in, trying to resolve problem.");
        resolveConnectionResult();
    }

    public void onDisconnected() {
        debugLog("onDisconnected.");
        this.mConnectionResult = null;
        this.mAutoSignIn = false;
        this.mSignedIn = false;
        this.mConnectedClients = 0;
        if (this.mListener != null) {
            this.mListener.onSignInFailed();
        }
    }

    public void onStart(Activity activity) {
        this.mActivity = activity;
        this.mContext = activity;
    }

    public void reconnectClients(int i) {
        if ((i & 1) != 0 && this.mGamesClient != null && this.mGamesClient.isConnected()) {
            this.mConnectedClients &= -2;
            this.mGamesClient.reconnect();
        }
    }

    /* access modifiers changed from: package-private */
    public void resolveConnectionResult() {
        debugLog("resolveConnectionResult: trying to resolve result: " + this.mConnectionResult);
        if (this.mConnectionResult.hasResolution()) {
            debugLog("result has resolution. Starting it.");
            try {
                this.mExpectingActivityResult = true;
                this.mConnectionResult.startResolutionForResult(this.mActivity, RC_RESOLVE);
            } catch (IntentSender.SendIntentException e) {
                debugLog("SendIntentException.");
                connectCurrentClient();
            }
        } else {
            debugLog("resolveConnectionResult: result has no resolution. Giving up.");
            giveUp();
        }
    }

    public void setRequestCode(int i) {
        RC_RESOLVE = i;
    }

    public void setup(GameHelperListener gameHelperListener) {
        setup(gameHelperListener, 1);
    }

    public void setup(GameHelperListener gameHelperListener, int i) {
        this.mListener = gameHelperListener;
        this.mRequestedClients = i;
        Vector vector = new Vector();
        if ((i & 1) != 0) {
            vector.add(Scopes.GAMES);
        }
        this.mScopes = new String[vector.size()];
        vector.copyInto(this.mScopes);
        final View view = new View(this.mContext);
        if ((i & 1) != 0) {
            CoronaEnvironment.getCoronaActivity().runOnUiThread(new Runnable() {
                public void run() {
                    AbsoluteLayout absoluteLayout = new AbsoluteLayout(CoronaEnvironment.getCoronaActivity());
                    CoronaEnvironment.getCoronaActivity().getOverlayView().addView(absoluteLayout);
                    RelativeLayout relativeLayout = new RelativeLayout(CoronaEnvironment.getCoronaActivity());
                    relativeLayout.addView(view);
                    absoluteLayout.addView(relativeLayout);
                }
            });
            debugLog("onCreate: creating GamesClient");
            this.mGamesClient = new GamesClient.Builder(this.mContext, this, this).setGravityForPopups(49).setScopes(this.mScopes).setViewForPopups(view).create();
        }
    }

    public void signOut() {
        this.mConnectionResult = null;
        this.mAutoSignIn = false;
        this.mSignedIn = false;
        if (this.mGamesClient != null) {
            try {
                this.mGamesClient.signOut();
            } catch (SecurityException e) {
            }
            this.mGamesClient.disconnect();
            this.mGamesClient = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void startConnections() {
        this.mConnectedClients = 0;
        connectNextClient();
    }

    /* access modifiers changed from: package-private */
    public void succeedSignIn() {
        this.mSignedIn = true;
        this.mAutoSignIn = true;
        if (this.mListener != null) {
            this.mListener.onSignInSucceeded();
        }
    }
}
