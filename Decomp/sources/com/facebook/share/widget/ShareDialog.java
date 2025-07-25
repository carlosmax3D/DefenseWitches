package com.facebook.share.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.FacebookCallback;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.Sharer;
import com.facebook.share.internal.LegacyNativeDialogParameters;
import com.facebook.share.internal.NativeDialogParameters;
import com.facebook.share.internal.OpenGraphActionDialogFeature;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareDialogFeature;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import java.util.ArrayList;
import java.util.List;

public final class ShareDialog extends FacebookDialogBase<ShareContent, Sharer.Result> implements Sharer {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.Share.toRequestCode();
    private static final String FEED_DIALOG = "feed";
    private static final String WEB_OG_SHARE_DIALOG = "share_open_graph";
    private static final String WEB_SHARE_DIALOG = "share";
    private boolean isAutomaticMode = true;
    private boolean shouldFailOnDataError = false;

    private class FeedHandler extends FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler {
        private FeedHandler() {
            super();
        }

        public boolean canShow(ShareContent shareContent) {
            return shareContent instanceof ShareLinkContent;
        }

        public AppCall createAppCall(ShareContent shareContent) {
            ShareDialog.this.logDialogShare(ShareDialog.this.getActivityContext(), shareContent, Mode.FEED);
            ShareLinkContent shareLinkContent = (ShareLinkContent) shareContent;
            AppCall createBaseAppCall = ShareDialog.this.createBaseAppCall();
            ShareContentValidation.validateForWebShare(shareLinkContent);
            DialogPresenter.setupAppCallForWebDialog(createBaseAppCall, ShareDialog.FEED_DIALOG, WebDialogParameters.createForFeed(shareLinkContent));
            return createBaseAppCall;
        }

        public Object getMode() {
            return Mode.FEED;
        }
    }

    public enum Mode {
        AUTOMATIC,
        NATIVE,
        WEB,
        FEED
    }

    private class NativeHandler extends FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler {
        private NativeHandler() {
            super();
        }

        public boolean canShow(ShareContent shareContent) {
            return shareContent != null && ShareDialog.canShowNative(shareContent.getClass());
        }

        public AppCall createAppCall(final ShareContent shareContent) {
            ShareDialog.this.logDialogShare(ShareDialog.this.getActivityContext(), shareContent, Mode.NATIVE);
            ShareContentValidation.validateForNativeShare(shareContent);
            final AppCall createBaseAppCall = ShareDialog.this.createBaseAppCall();
            final boolean shouldFailOnDataError = ShareDialog.this.getShouldFailOnDataError();
            DialogPresenter.setupAppCallForNativeDialog(createBaseAppCall, new DialogPresenter.ParameterProvider() {
                public Bundle getLegacyParameters() {
                    return LegacyNativeDialogParameters.create(createBaseAppCall.getCallId(), shareContent, shouldFailOnDataError);
                }

                public Bundle getParameters() {
                    return NativeDialogParameters.create(createBaseAppCall.getCallId(), shareContent, shouldFailOnDataError);
                }
            }, ShareDialog.getFeature(shareContent.getClass()));
            return createBaseAppCall;
        }

        public Object getMode() {
            return Mode.NATIVE;
        }
    }

    private class WebShareHandler extends FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler {
        private WebShareHandler() {
            super();
        }

        private String getActionName(ShareContent shareContent) {
            if (shareContent instanceof ShareLinkContent) {
                return ShareDialog.WEB_SHARE_DIALOG;
            }
            if (shareContent instanceof ShareOpenGraphContent) {
                return ShareDialog.WEB_OG_SHARE_DIALOG;
            }
            return null;
        }

        public boolean canShow(ShareContent shareContent) {
            return shareContent != null && ShareDialog.canShowWebTypeCheck(shareContent.getClass());
        }

        public AppCall createAppCall(ShareContent shareContent) {
            ShareDialog.this.logDialogShare(ShareDialog.this.getActivityContext(), shareContent, Mode.WEB);
            AppCall createBaseAppCall = ShareDialog.this.createBaseAppCall();
            ShareContentValidation.validateForWebShare(shareContent);
            DialogPresenter.setupAppCallForWebDialog(createBaseAppCall, getActionName(shareContent), shareContent instanceof ShareLinkContent ? WebDialogParameters.create((ShareLinkContent) shareContent) : WebDialogParameters.create((ShareOpenGraphContent) shareContent));
            return createBaseAppCall;
        }

        public Object getMode() {
            return Mode.WEB;
        }
    }

    public ShareDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
        ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
    }

    ShareDialog(Activity activity, int i) {
        super(activity, i);
        ShareInternalUtility.registerStaticShareCallback(i);
    }

    public ShareDialog(Fragment fragment) {
        super(fragment, DEFAULT_REQUEST_CODE);
        ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
    }

    ShareDialog(Fragment fragment, int i) {
        super(fragment, i);
        ShareInternalUtility.registerStaticShareCallback(i);
    }

    public static boolean canShow(Class<? extends ShareContent> cls) {
        return canShowWebTypeCheck(cls) || canShowNative(cls);
    }

    /* access modifiers changed from: private */
    public static boolean canShowNative(Class<? extends ShareContent> cls) {
        DialogFeature feature = getFeature(cls);
        return feature != null && DialogPresenter.canPresentNativeDialogWithFeature(feature);
    }

    /* access modifiers changed from: private */
    public static boolean canShowWebTypeCheck(Class<? extends ShareContent> cls) {
        return ShareLinkContent.class.isAssignableFrom(cls) || ShareOpenGraphContent.class.isAssignableFrom(cls);
    }

    /* access modifiers changed from: private */
    public static DialogFeature getFeature(Class<? extends ShareContent> cls) {
        if (ShareLinkContent.class.isAssignableFrom(cls)) {
            return ShareDialogFeature.SHARE_DIALOG;
        }
        if (SharePhotoContent.class.isAssignableFrom(cls)) {
            return ShareDialogFeature.PHOTOS;
        }
        if (ShareVideoContent.class.isAssignableFrom(cls)) {
            return ShareDialogFeature.VIDEO;
        }
        if (ShareOpenGraphContent.class.isAssignableFrom(cls)) {
            return OpenGraphActionDialogFeature.OG_ACTION_DIALOG;
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void logDialogShare(Context context, ShareContent shareContent, Mode mode) {
        String str;
        if (this.isAutomaticMode) {
            mode = Mode.AUTOMATIC;
        }
        switch (mode) {
            case AUTOMATIC:
                str = AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_AUTOMATIC;
                break;
            case WEB:
                str = AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_WEB;
                break;
            case NATIVE:
                str = "native";
                break;
            default:
                str = "unknown";
                break;
        }
        DialogFeature feature = getFeature(shareContent.getClass());
        String str2 = feature == ShareDialogFeature.SHARE_DIALOG ? "status" : feature == ShareDialogFeature.PHOTOS ? AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_PHOTO : feature == ShareDialogFeature.VIDEO ? AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_VIDEO : feature == OpenGraphActionDialogFeature.OG_ACTION_DIALOG ? AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_OPENGRAPH : "unknown";
        AppEventsLogger newLogger = AppEventsLogger.newLogger(context);
        Bundle bundle = new Bundle();
        bundle.putString("fb_share_dialog_show", str);
        bundle.putString(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_TYPE, str2);
        newLogger.logSdkEvent("fb_share_dialog_show", (Double) null, bundle);
    }

    public static void show(Activity activity, ShareContent shareContent) {
        new ShareDialog(activity).show(shareContent);
    }

    public static void show(Fragment fragment, ShareContent shareContent) {
        new ShareDialog(fragment).show(shareContent);
    }

    public boolean canShow(ShareContent shareContent, Mode mode) {
        Object obj = mode;
        if (mode == Mode.AUTOMATIC) {
            obj = BASE_AUTOMATIC_MODE;
        }
        return canShowImpl(shareContent, obj);
    }

    /* access modifiers changed from: protected */
    public AppCall createBaseAppCall() {
        return new AppCall(getRequestCode());
    }

    /* access modifiers changed from: protected */
    public List<FacebookDialogBase<ShareContent, Sharer.Result>.ModeHandler> getOrderedModeHandlers() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NativeHandler());
        arrayList.add(new FeedHandler());
        arrayList.add(new WebShareHandler());
        return arrayList;
    }

    public boolean getShouldFailOnDataError() {
        return this.shouldFailOnDataError;
    }

    /* access modifiers changed from: protected */
    public void registerCallbackImpl(CallbackManagerImpl callbackManagerImpl, FacebookCallback<Sharer.Result> facebookCallback) {
        ShareInternalUtility.registerSharerCallback(getRequestCode(), callbackManagerImpl, facebookCallback);
    }

    public void setShouldFailOnDataError(boolean z) {
        this.shouldFailOnDataError = z;
    }

    public void show(ShareContent shareContent, Mode mode) {
        this.isAutomaticMode = mode == Mode.AUTOMATIC;
        Object obj = mode;
        if (this.isAutomaticMode) {
            obj = BASE_AUTOMATIC_MODE;
        }
        showImpl(shareContent, obj);
    }
}
