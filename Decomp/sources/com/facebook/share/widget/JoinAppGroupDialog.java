package com.facebook.share.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.FacebookCallback;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareConstants;
import com.facebook.share.internal.ShareInternalUtility;
import java.util.ArrayList;
import java.util.List;

public class JoinAppGroupDialog extends FacebookDialogBase<String, Result> {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.AppGroupJoin.toRequestCode();
    private static final String JOIN_GAME_GROUP_DIALOG = "game_group_join";

    public static final class Result {
        private final Bundle data;

        private Result(Bundle bundle) {
            this.data = bundle;
        }

        public Bundle getData() {
            return this.data;
        }
    }

    private class WebHandler extends FacebookDialogBase<String, Result>.ModeHandler {
        private WebHandler() {
            super();
        }

        public boolean canShow(String str) {
            return true;
        }

        public AppCall createAppCall(String str) {
            AppCall createBaseAppCall = JoinAppGroupDialog.this.createBaseAppCall();
            Bundle bundle = new Bundle();
            bundle.putString(ShareConstants.WEB_DIALOG_PARAM_ID, str);
            DialogPresenter.setupAppCallForWebDialog(createBaseAppCall, JoinAppGroupDialog.JOIN_GAME_GROUP_DIALOG, bundle);
            return createBaseAppCall;
        }
    }

    public JoinAppGroupDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
    }

    public JoinAppGroupDialog(Fragment fragment) {
        super(fragment, DEFAULT_REQUEST_CODE);
    }

    public static boolean canShow() {
        return true;
    }

    public static void show(Activity activity, String str) {
        new JoinAppGroupDialog(activity).show(str);
    }

    public static void show(Fragment fragment, String str) {
        new JoinAppGroupDialog(fragment).show(str);
    }

    /* access modifiers changed from: protected */
    public AppCall createBaseAppCall() {
        return new AppCall(getRequestCode());
    }

    /* access modifiers changed from: protected */
    public List<FacebookDialogBase<String, Result>.ModeHandler> getOrderedModeHandlers() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new WebHandler());
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public void registerCallbackImpl(CallbackManagerImpl callbackManagerImpl, final FacebookCallback<Result> facebookCallback) {
        final AnonymousClass1 r1 = facebookCallback == null ? null : new ResultProcessor(facebookCallback) {
            public void onSuccess(AppCall appCall, Bundle bundle) {
                facebookCallback.onSuccess(new Result(bundle));
            }
        };
        callbackManagerImpl.registerCallback(getRequestCode(), new CallbackManagerImpl.Callback() {
            public boolean onActivityResult(int i, Intent intent) {
                return ShareInternalUtility.handleActivityResult(JoinAppGroupDialog.this.getRequestCode(), i, intent, r1);
            }
        });
    }
}
