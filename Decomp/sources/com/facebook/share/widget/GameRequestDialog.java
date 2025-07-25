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
import com.facebook.share.internal.GameRequestValidation;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareConstants;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.GameRequestContent;
import java.util.ArrayList;
import java.util.List;

public class GameRequestDialog extends FacebookDialogBase<GameRequestContent, Result> {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.GameRequest.toRequestCode();
    private static final String GAME_REQUEST_DIALOG = "apprequests";

    public static final class Result {
        String requestId;

        private Result(String str) {
            this.requestId = str;
        }

        public String getRequestId() {
            return this.requestId;
        }
    }

    private class WebHandler extends FacebookDialogBase<GameRequestContent, Result>.ModeHandler {
        private WebHandler() {
            super();
        }

        public boolean canShow(GameRequestContent gameRequestContent) {
            return true;
        }

        public AppCall createAppCall(GameRequestContent gameRequestContent) {
            GameRequestValidation.validate(gameRequestContent);
            AppCall createBaseAppCall = GameRequestDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForWebDialog(createBaseAppCall, GameRequestDialog.GAME_REQUEST_DIALOG, WebDialogParameters.create(gameRequestContent));
            return createBaseAppCall;
        }
    }

    public GameRequestDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
    }

    public GameRequestDialog(Fragment fragment) {
        super(fragment, DEFAULT_REQUEST_CODE);
    }

    public static boolean canShow() {
        return true;
    }

    public static void show(Activity activity, GameRequestContent gameRequestContent) {
        new GameRequestDialog(activity).show(gameRequestContent);
    }

    public static void show(Fragment fragment, GameRequestContent gameRequestContent) {
        new GameRequestDialog(fragment).show(gameRequestContent);
    }

    /* access modifiers changed from: protected */
    public AppCall createBaseAppCall() {
        return new AppCall(getRequestCode());
    }

    /* access modifiers changed from: protected */
    public List<FacebookDialogBase<GameRequestContent, Result>.ModeHandler> getOrderedModeHandlers() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new WebHandler());
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public void registerCallbackImpl(CallbackManagerImpl callbackManagerImpl, final FacebookCallback<Result> facebookCallback) {
        final AnonymousClass1 r0 = facebookCallback == null ? null : new ResultProcessor(facebookCallback) {
            public void onSuccess(AppCall appCall, Bundle bundle) {
                if (bundle != null) {
                    facebookCallback.onSuccess(new Result(bundle.getString(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID)));
                } else {
                    onCancel(appCall);
                }
            }
        };
        callbackManagerImpl.registerCallback(getRequestCode(), new CallbackManagerImpl.Callback() {
            public boolean onActivityResult(int i, Intent intent) {
                return ShareInternalUtility.handleActivityResult(GameRequestDialog.this.getRequestCode(), i, intent, r0);
            }
        });
    }
}
