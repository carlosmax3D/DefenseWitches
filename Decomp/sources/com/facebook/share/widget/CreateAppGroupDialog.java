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
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.AppGroupCreationContent;
import java.util.ArrayList;
import java.util.List;

public class CreateAppGroupDialog extends FacebookDialogBase<AppGroupCreationContent, Result> {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.AppGroupCreate.toRequestCode();
    private static final String GAME_GROUP_CREATION_DIALOG = "game_group_create";

    public static final class Result {
        private final String id;

        private Result(String str) {
            this.id = str;
        }

        public String getId() {
            return this.id;
        }
    }

    private class WebHandler extends FacebookDialogBase<AppGroupCreationContent, Result>.ModeHandler {
        private WebHandler() {
            super();
        }

        public boolean canShow(AppGroupCreationContent appGroupCreationContent) {
            return true;
        }

        public AppCall createAppCall(AppGroupCreationContent appGroupCreationContent) {
            AppCall createBaseAppCall = CreateAppGroupDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForWebDialog(createBaseAppCall, CreateAppGroupDialog.GAME_GROUP_CREATION_DIALOG, WebDialogParameters.create(appGroupCreationContent));
            return createBaseAppCall;
        }
    }

    public CreateAppGroupDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
    }

    public CreateAppGroupDialog(Fragment fragment) {
        super(fragment, DEFAULT_REQUEST_CODE);
    }

    public static boolean canShow() {
        return true;
    }

    public static void show(Activity activity, AppGroupCreationContent appGroupCreationContent) {
        new CreateAppGroupDialog(activity).show(appGroupCreationContent);
    }

    public static void show(Fragment fragment, AppGroupCreationContent appGroupCreationContent) {
        new CreateAppGroupDialog(fragment).show(appGroupCreationContent);
    }

    /* access modifiers changed from: protected */
    public AppCall createBaseAppCall() {
        return new AppCall(getRequestCode());
    }

    /* access modifiers changed from: protected */
    public List<FacebookDialogBase<AppGroupCreationContent, Result>.ModeHandler> getOrderedModeHandlers() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new WebHandler());
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public void registerCallbackImpl(CallbackManagerImpl callbackManagerImpl, final FacebookCallback<Result> facebookCallback) {
        final AnonymousClass1 r1 = facebookCallback == null ? null : new ResultProcessor(facebookCallback) {
            public void onSuccess(AppCall appCall, Bundle bundle) {
                facebookCallback.onSuccess(new Result(bundle.getString(ShareConstants.WEB_DIALOG_PARAM_ID)));
            }
        };
        callbackManagerImpl.registerCallback(getRequestCode(), new CallbackManagerImpl.Callback() {
            public boolean onActivityResult(int i, Intent intent) {
                return ShareInternalUtility.handleActivityResult(CreateAppGroupDialog.this.getRequestCode(), i, intent, r1);
            }
        });
    }
}
