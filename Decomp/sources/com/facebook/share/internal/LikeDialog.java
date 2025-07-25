package com.facebook.share.internal;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.facebook.FacebookCallback;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import java.util.ArrayList;
import java.util.List;

public class LikeDialog extends FacebookDialogBase<LikeContent, Result> {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.Like.toRequestCode();
    private static final String TAG = "LikeDialog";

    private class NativeHandler extends FacebookDialogBase<LikeContent, Result>.ModeHandler {
        private NativeHandler() {
            super();
        }

        public boolean canShow(LikeContent likeContent) {
            return likeContent != null && LikeDialog.canShowNativeDialog();
        }

        public AppCall createAppCall(final LikeContent likeContent) {
            AppCall createBaseAppCall = LikeDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForNativeDialog(createBaseAppCall, new DialogPresenter.ParameterProvider() {
                public Bundle getLegacyParameters() {
                    Log.e(LikeDialog.TAG, "Attempting to present the Like Dialog with an outdated Facebook app on the device");
                    return new Bundle();
                }

                public Bundle getParameters() {
                    return LikeDialog.createParameters(likeContent);
                }
            }, LikeDialog.getFeature());
            return createBaseAppCall;
        }
    }

    public static final class Result {
    }

    private class WebFallbackHandler extends FacebookDialogBase<LikeContent, Result>.ModeHandler {
        private WebFallbackHandler() {
            super();
        }

        public boolean canShow(LikeContent likeContent) {
            return likeContent != null && LikeDialog.canShowWebFallback();
        }

        public AppCall createAppCall(LikeContent likeContent) {
            AppCall createBaseAppCall = LikeDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForWebFallbackDialog(createBaseAppCall, LikeDialog.createParameters(likeContent), LikeDialog.getFeature());
            return createBaseAppCall;
        }
    }

    LikeDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
    }

    LikeDialog(Fragment fragment) {
        super(fragment, DEFAULT_REQUEST_CODE);
    }

    public static boolean canShowNativeDialog() {
        return Build.VERSION.SDK_INT >= 14 && DialogPresenter.canPresentNativeDialogWithFeature(getFeature());
    }

    public static boolean canShowWebFallback() {
        return Build.VERSION.SDK_INT >= 14 && DialogPresenter.canPresentWebFallbackDialogWithFeature(getFeature());
    }

    /* access modifiers changed from: private */
    public static Bundle createParameters(LikeContent likeContent) {
        Bundle bundle = new Bundle();
        bundle.putString("object_id", likeContent.getObjectId());
        bundle.putString("object_type", likeContent.getObjectType().toString());
        return bundle;
    }

    /* access modifiers changed from: private */
    public static DialogFeature getFeature() {
        return LikeDialogFeature.LIKE_DIALOG;
    }

    /* access modifiers changed from: protected */
    public AppCall createBaseAppCall() {
        return new AppCall(getRequestCode());
    }

    /* access modifiers changed from: protected */
    public List<FacebookDialogBase<LikeContent, Result>.ModeHandler> getOrderedModeHandlers() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NativeHandler());
        arrayList.add(new WebFallbackHandler());
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public void registerCallbackImpl(CallbackManagerImpl callbackManagerImpl, FacebookCallback<Result> facebookCallback) {
        throw new UnsupportedOperationException("registerCallback is not supported for LikeDialog");
    }
}
