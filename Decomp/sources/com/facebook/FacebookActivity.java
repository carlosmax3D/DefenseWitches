package com.facebook;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.NativeProtocol;
import com.facebook.login.LoginFragment;

public class FacebookActivity extends FragmentActivity {
    private static String FRAGMENT_TAG = "SingleFragment";
    public static String PASS_THROUGH_CANCEL_ACTION = "PassThrough";
    private Fragment singleFragment;

    private void handlePassThroughError() {
        Intent intent = getIntent();
        setResult(0, NativeProtocol.createProtocolResultIntent(intent, (Bundle) null, NativeProtocol.getExceptionFromErrorData(NativeProtocol.getMethodArgumentsFromIntent(intent))));
        finish();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.singleFragment != null) {
            this.singleFragment.onConfigurationChanged(configuration);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.com_facebook_activity_layout);
        Intent intent = getIntent();
        if (PASS_THROUGH_CANCEL_ACTION.equals(intent.getAction())) {
            handlePassThroughError();
            return;
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment findFragmentByTag = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (findFragmentByTag == null) {
            if (FacebookDialogFragment.TAG.equals(intent.getAction())) {
                FacebookDialogFragment facebookDialogFragment = new FacebookDialogFragment();
                facebookDialogFragment.setRetainInstance(true);
                facebookDialogFragment.show(supportFragmentManager, FRAGMENT_TAG);
                findFragmentByTag = facebookDialogFragment;
            } else {
                findFragmentByTag = new LoginFragment();
                findFragmentByTag.setRetainInstance(true);
                supportFragmentManager.beginTransaction().add(R.id.com_facebook_fragment_container, findFragmentByTag, FRAGMENT_TAG).commit();
            }
        }
        this.singleFragment = findFragmentByTag;
    }
}
