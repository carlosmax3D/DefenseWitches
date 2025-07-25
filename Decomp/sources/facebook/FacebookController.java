package facebook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.storage.FileServices;
import com.facebook.AppEventsLogger;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.facebook.widget.WebDialog;
import com.naef.jnlua.LuaState;
import facebook.FBSessionEvent;
import java.io.File;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jp.stargarage.g2metrics.BuildConfig;

public class FacebookController {
    /* access modifiers changed from: private */
    public static int mListener;

    private static class FacebookEventHandler implements Session.StatusCallback {
        private CoronaRuntime mCoronaRuntime;
        private String[] mPermissions;

        public FacebookEventHandler(CoronaRuntime coronaRuntime, String[] strArr) {
            this.mPermissions = strArr;
            this.mCoronaRuntime = coronaRuntime;
        }

        public void call(Session session, SessionState sessionState, Exception exc) {
            if (sessionState == SessionState.OPENED || sessionState == SessionState.OPENED_TOKEN_UPDATED) {
                LinkedList linkedList = new LinkedList();
                boolean z = false;
                if (this.mPermissions != null) {
                    for (int i = 0; i < this.mPermissions.length; i++) {
                        if (!Session.isPublishPermission(this.mPermissions[i]) && this.mPermissions[i] != null) {
                            linkedList.add(this.mPermissions[i]);
                            this.mPermissions[i] = null;
                            z = true;
                        }
                    }
                    if (linkedList.isEmpty()) {
                        for (int i2 = 0; i2 < this.mPermissions.length; i2++) {
                            if (Session.isPublishPermission(this.mPermissions[i2]) && this.mPermissions[i2] != null) {
                                linkedList.add(this.mPermissions[i2]);
                                this.mPermissions[i2] = null;
                            }
                        }
                    }
                }
                CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
                if (coronaActivity != null) {
                    if (!linkedList.isEmpty()) {
                        Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(coronaActivity, linkedList);
                        newPermissionsRequest.setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK);
                        newPermissionsRequest.setCallback(this);
                        newPermissionsRequest.setRequestCode(coronaActivity.registerActivityResultHandler(new FacebookLoginActivityResultHandler()));
                        if (z) {
                            session.requestNewReadPermissions(newPermissionsRequest);
                        } else {
                            session.requestNewPublishPermissions(newPermissionsRequest);
                        }
                    } else {
                        this.mCoronaRuntime.getTaskDispatcher().send(new FBConnectTask(FacebookController.mListener, FBSessionEvent.Phase.login, session.getAccessToken(), session.getExpirationDate().getTime()));
                    }
                }
            } else if (sessionState == SessionState.CLOSED) {
                this.mCoronaRuntime.getTaskDispatcher().send(new FBConnectTask(FacebookController.mListener, FBSessionEvent.Phase.logout, (String) null, 0));
            } else if (sessionState == SessionState.CLOSED_LOGIN_FAILED && exc != null && exc.getClass().equals(FacebookOperationCanceledException.class)) {
                this.mCoronaRuntime.getTaskDispatcher().send(new FBConnectTask(FacebookController.mListener, FBSessionEvent.Phase.loginCancelled, (String) null, 0));
            } else if (exc != null) {
                this.mCoronaRuntime.getTaskDispatcher().send(new FBConnectTask(FacebookController.mListener, exc.getLocalizedMessage()));
            }
        }
    }

    private static class FacebookLoginActivityResultHandler implements CoronaActivity.OnActivityResultHandler {
        private FacebookLoginActivityResultHandler() {
        }

        public void onHandleActivityResult(CoronaActivity coronaActivity, int i, int i2, Intent intent) {
            coronaActivity.unregisterActivityResultHandler(this);
            Session activeSession = Session.getActiveSession();
            if (activeSession != null) {
                activeSession.onActivityResult(coronaActivity, i, i2, intent);
            }
        }
    }

    private static class FacebookRequestCallbackListener implements Request.Callback {
        CoronaRuntime fCoronaRuntime;

        FacebookRequestCallbackListener(CoronaRuntime coronaRuntime) {
            this.fCoronaRuntime = coronaRuntime;
        }

        public void onCompleted(Response response) {
            if (this.fCoronaRuntime.isRunning() && response != null) {
                if (response.getError() != null) {
                    this.fCoronaRuntime.getTaskDispatcher().send(new FBConnectTask(FacebookController.mListener, response.getError().getErrorMessage(), true));
                    return;
                }
                String str = BuildConfig.FLAVOR;
                if (!(response.getGraphObject() == null || response.getGraphObject().getInnerJSONObject() == null || response.getGraphObject().getInnerJSONObject().toString() == null)) {
                    str = response.getGraphObject().getInnerJSONObject().toString();
                }
                this.fCoronaRuntime.getTaskDispatcher().send(new FBConnectTask(FacebookController.mListener, str, false));
            }
        }
    }

    private static class FacebookWebDialogOnCompleteListener implements WebDialog.OnCompleteListener {
        CoronaRuntime fCoronaRuntime;

        FacebookWebDialogOnCompleteListener(CoronaRuntime coronaRuntime) {
            this.fCoronaRuntime = coronaRuntime;
        }

        public void onComplete(Bundle bundle, FacebookException facebookException) {
            if (!this.fCoronaRuntime.isRunning()) {
                return;
            }
            if (facebookException == null) {
                Uri.Builder builder = new Uri.Builder();
                builder.authority(GraphResponse.SUCCESS_KEY);
                builder.scheme("fbconnect");
                for (String str : bundle.keySet()) {
                    String string = bundle.getString(str);
                    if (string == null) {
                        string = BuildConfig.FLAVOR;
                    }
                    builder.appendQueryParameter(str, string);
                }
                this.fCoronaRuntime.getTaskDispatcher().send(new FBConnectTask(FacebookController.mListener, builder.build().toString(), false, true));
                return;
            }
            this.fCoronaRuntime.getTaskDispatcher().send(new FBConnectTask(FacebookController.mListener, facebookException.getLocalizedMessage(), true, false));
        }
    }

    protected static Bundle createFacebookBundle(Hashtable hashtable) {
        Set<Map.Entry> entrySet;
        byte[] bytesFromFile;
        Bundle bundle = new Bundle();
        if (!(hashtable == null || (entrySet = hashtable.entrySet()) == null)) {
            FileServices fileServices = new FileServices(CoronaEnvironment.getApplicationContext());
            for (Map.Entry entry : entrySet) {
                String str = (String) entry.getKey();
                Object value = entry.getValue();
                if (value instanceof File) {
                    byte[] bytesFromFile2 = fileServices.getBytesFromFile(((File) value).getPath());
                    if (bytesFromFile2 != null) {
                        bundle.putByteArray(str, bytesFromFile2);
                    }
                } else if (value instanceof byte[]) {
                    bundle.putByteArray(str, (byte[]) value);
                } else if (value instanceof String[]) {
                    bundle.putStringArray(str, (String[]) value);
                } else if (value != null) {
                    boolean z = false;
                    File file = new File(value.toString());
                    if (file.exists() && (bytesFromFile = fileServices.getBytesFromFile(file)) != null) {
                        bundle.putByteArray(str, bytesFromFile);
                        z = true;
                    }
                    if (!z) {
                        bundle.putString(str, value.toString());
                    }
                }
            }
        }
        return bundle;
    }

    public static void facebookDialog(CoronaRuntime coronaRuntime, Context context, String str, Hashtable hashtable) {
        LuaState luaState;
        int i = -1;
        if (!(coronaRuntime == null || (luaState = coronaRuntime.getLuaState()) == null || !CoronaLua.isListener(luaState, -1, BuildConfig.FLAVOR))) {
            i = CoronaLua.newRef(luaState, -1);
        }
        final int i2 = i;
        final String str2 = str;
        final Context context2 = context;
        final Hashtable hashtable2 = hashtable;
        final CoronaRuntime coronaRuntime2 = coronaRuntime;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                Session activeSession = Session.getActiveSession();
                WebDialog webDialog = null;
                if (str2.equals("feed")) {
                    WebDialog.FeedDialogBuilder feedDialogBuilder = new WebDialog.FeedDialogBuilder(context2, activeSession, FacebookController.createFacebookBundle(hashtable2));
                    String str = (String) hashtable2.get("caption");
                    if (str != null) {
                        feedDialogBuilder.setCaption(str);
                    }
                    String str2 = (String) hashtable2.get("description");
                    if (str2 != null) {
                        feedDialogBuilder.setDescription(str2);
                    }
                    String str3 = (String) hashtable2.get("from");
                    if (str3 != null) {
                        feedDialogBuilder.setFrom(str3);
                    }
                    String str4 = (String) hashtable2.get(ShareConstants.WEB_DIALOG_PARAM_LINK);
                    if (str4 != null) {
                        feedDialogBuilder.setLink(str4);
                    }
                    String str5 = (String) hashtable2.get("name");
                    if (str5 != null) {
                        feedDialogBuilder.setName(str5);
                    }
                    String str6 = (String) hashtable2.get(ShareConstants.WEB_DIALOG_PARAM_PICTURE);
                    if (str6 != null) {
                        feedDialogBuilder.setPicture(str6);
                    }
                    String str7 = (String) hashtable2.get("source");
                    if (str7 != null) {
                        feedDialogBuilder.setSource(str7);
                    }
                    String str8 = (String) hashtable2.get(ShareConstants.WEB_DIALOG_PARAM_TO);
                    if (str8 != null) {
                        feedDialogBuilder.setTo(str8);
                    }
                    webDialog = feedDialogBuilder.build();
                } else if (str2.equals("requests") || str2.equals("apprequests")) {
                    WebDialog.RequestsDialogBuilder requestsDialogBuilder = new WebDialog.RequestsDialogBuilder(context2, activeSession, FacebookController.createFacebookBundle(hashtable2));
                    String str9 = (String) hashtable2.get("data");
                    if (str9 != null) {
                        requestsDialogBuilder.setData(str9);
                    }
                    String str10 = (String) hashtable2.get("message");
                    if (str10 != null) {
                        requestsDialogBuilder.setMessage(str10);
                    }
                    String str11 = (String) hashtable2.get("title");
                    if (str11 != null) {
                        requestsDialogBuilder.setTitle(str11);
                    }
                    String str12 = (String) hashtable2.get(ShareConstants.WEB_DIALOG_PARAM_TO);
                    if (str12 != null) {
                        requestsDialogBuilder.setTo(str12);
                    }
                    webDialog = requestsDialogBuilder.build();
                } else if (str2.equals("place") || str2.equals(NativeProtocol.AUDIENCE_FRIENDS)) {
                    Intent intent = new Intent(context2, FacebookFragmentActivity.class);
                    intent.putExtra(FacebookFragmentActivity.FRAGMENT_NAME, str2);
                    intent.putExtra(FacebookFragmentActivity.FRAGMENT_LISTENER, i2);
                    intent.putExtra(FacebookFragmentActivity.FRAGMENT_EXTRAS, FacebookController.createFacebookBundle(hashtable2));
                    context2.startActivity(intent);
                } else {
                    webDialog = new WebDialog.Builder(context2, activeSession, str2, FacebookController.createFacebookBundle(hashtable2)).build();
                }
                if (webDialog != null) {
                    webDialog.setOnCompleteListener(new FacebookWebDialogOnCompleteListener(coronaRuntime2));
                    webDialog.show();
                }
            }
        });
    }

    public static void facebookLogin(CoronaRuntime coronaRuntime, String str, int i, String[] strArr) {
        CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
        if (coronaActivity != null) {
            mListener = i;
            Context applicationContext = CoronaEnvironment.getApplicationContext();
            if (applicationContext != null) {
                applicationContext.enforceCallingOrSelfPermission("android.permission.INTERNET", (String) null);
            }
            if (Session.getActiveSession() == null || Session.getActiveSession().isClosed()) {
                Session build = new Session.Builder(coronaActivity).setApplicationId(str).build();
                Session.setActiveSession(build);
                Session.OpenRequest openRequest = new Session.OpenRequest(coronaActivity);
                openRequest.setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK);
                openRequest.setRequestCode(coronaActivity.registerActivityResultHandler(new FacebookLoginActivityResultHandler()));
                build.addCallback(new FacebookEventHandler(coronaRuntime, strArr));
                build.openForRead(openRequest);
                return;
            }
            Session activeSession = Session.getActiveSession();
            List permissions = activeSession.getPermissions();
            for (int i2 = 0; i2 < strArr.length; i2++) {
                if (permissions.contains(strArr[i2])) {
                    strArr[i2] = null;
                }
            }
            new FacebookEventHandler(coronaRuntime, strArr).call(activeSession, activeSession.getState(), (Exception) null);
        }
    }

    public static void facebookLogout() {
        Session activeSession = Session.getActiveSession();
        if (activeSession != null) {
            activeSession.closeAndClearTokenInformation();
        }
    }

    public static void facebookRequest(CoronaRuntime coronaRuntime, String str, String str2, Hashtable hashtable) {
        CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
        if (coronaActivity != null) {
            Request request = new Request(Session.getActiveSession(), str, createFacebookBundle(hashtable), HttpMethod.valueOf(str2));
            request.setCallback(new FacebookRequestCallbackListener(coronaRuntime));
            final Request request2 = request;
            coronaActivity.runOnUiThread(new Runnable() {
                public void run() {
                    request2.executeAsync();
                }
            });
        }
    }

    public static void publishInstall(String str) {
        AppEventsLogger.activateApp(CoronaEnvironment.getApplicationContext(), str);
    }
}
