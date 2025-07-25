package com.facebook.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.R;
import com.facebook.login.LoginClient;

public class LoginFragment extends Fragment {
    private static final String EXTRA_REQUEST = "request";
    private static final String NULL_CALLING_PKG_ERROR_MSG = "Cannot call LoginActivity with a null calling package. This can occur if the launchMode of the caller is singleInstance.";
    static final String RESULT_KEY = "com.facebook.LoginFragment:Result";
    private static final String SAVED_LOGIN_CLIENT = "loginClient";
    private static final String TAG = "LoginActivityFragment";
    private String callingPackage;
    private LoginClient loginClient;
    private LoginClient.Request request;

    /* access modifiers changed from: private */
    public void onLoginClientCompleted(LoginClient.Result result) {
        this.request = null;
        int i = result.code == LoginClient.Result.Code.CANCEL ? 0 : -1;
        Bundle bundle = new Bundle();
        bundle.putParcelable(RESULT_KEY, result);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        getActivity().setResult(i, intent);
        getActivity().finish();
    }

    static Bundle populateIntentExtras(LoginClient.Request request2) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("request", request2);
        return bundle;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.loginClient.onActivityResult(i, i2, intent);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.loginClient = (LoginClient) bundle.getParcelable(SAVED_LOGIN_CLIENT);
            this.loginClient.setFragment(this);
        } else {
            this.loginClient = new LoginClient((Fragment) this);
        }
        this.callingPackage = getActivity().getCallingActivity().getPackageName();
        this.request = (LoginClient.Request) getActivity().getIntent().getParcelableExtra("request");
        this.loginClient.setOnCompletedListener(new LoginClient.OnCompletedListener() {
            public void onCompleted(LoginClient.Result result) {
                LoginFragment.this.onLoginClientCompleted(result);
            }
        });
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        final View inflate = layoutInflater.inflate(R.layout.com_facebook_login_fragment, viewGroup, false);
        this.loginClient.setBackgroundProcessingListener(new LoginClient.BackgroundProcessingListener() {
            public void onBackgroundProcessingStarted() {
                inflate.findViewById(R.id.com_facebook_login_activity_progress_bar).setVisibility(0);
            }

            public void onBackgroundProcessingStopped() {
                inflate.findViewById(R.id.com_facebook_login_activity_progress_bar).setVisibility(8);
            }
        });
        return inflate;
    }

    public void onDestroy() {
        this.loginClient.cancelCurrentHandler();
        super.onDestroy();
    }

    public void onPause() {
        super.onPause();
        getActivity().findViewById(R.id.com_facebook_login_activity_progress_bar).setVisibility(8);
    }

    public void onResume() {
        super.onResume();
        if (this.callingPackage == null) {
            Log.e(TAG, NULL_CALLING_PKG_ERROR_MSG);
            getActivity().finish();
            return;
        }
        this.loginClient.startOrContinueAuth(this.request);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable(SAVED_LOGIN_CLIENT, this.loginClient);
    }
}
