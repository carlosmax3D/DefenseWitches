package com.facebook.share.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.AppCall;
import com.facebook.internal.BundleJSONConverter;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.FileLruCache;
import com.facebook.internal.Logger;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.PlatformServiceClient;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.WorkQueue;
import com.facebook.share.internal.LikeContent;
import com.facebook.share.widget.LikeView;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import jp.stargarage.g2metrics.BuildConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LikeActionController {
    public static final String ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR = "com.facebook.sdk.LikeActionController.DID_ERROR";
    public static final String ACTION_LIKE_ACTION_CONTROLLER_DID_RESET = "com.facebook.sdk.LikeActionController.DID_RESET";
    public static final String ACTION_LIKE_ACTION_CONTROLLER_UPDATED = "com.facebook.sdk.LikeActionController.UPDATED";
    public static final String ACTION_OBJECT_ID_KEY = "com.facebook.sdk.LikeActionController.OBJECT_ID";
    private static final int ERROR_CODE_OBJECT_ALREADY_LIKED = 3501;
    public static final String ERROR_INVALID_OBJECT_ID = "Invalid Object Id";
    public static final String ERROR_PUBLISH_ERROR = "Unable to publish the like/unlike action";
    private static final String JSON_BOOL_IS_OBJECT_LIKED_KEY = "is_object_liked";
    private static final String JSON_BUNDLE_FACEBOOK_DIALOG_ANALYTICS_BUNDLE = "facebook_dialog_analytics_bundle";
    private static final String JSON_INT_OBJECT_TYPE_KEY = "object_type";
    private static final String JSON_INT_VERSION_KEY = "com.facebook.share.internal.LikeActionController.version";
    private static final String JSON_STRING_LIKE_COUNT_WITHOUT_LIKE_KEY = "like_count_string_without_like";
    private static final String JSON_STRING_LIKE_COUNT_WITH_LIKE_KEY = "like_count_string_with_like";
    private static final String JSON_STRING_OBJECT_ID_KEY = "object_id";
    private static final String JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY = "social_sentence_without_like";
    private static final String JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY = "social_sentence_with_like";
    private static final String JSON_STRING_UNLIKE_TOKEN_KEY = "unlike_token";
    private static final String LIKE_ACTION_CONTROLLER_STORE = "com.facebook.LikeActionController.CONTROLLER_STORE_KEY";
    private static final String LIKE_ACTION_CONTROLLER_STORE_OBJECT_SUFFIX_KEY = "OBJECT_SUFFIX";
    private static final String LIKE_ACTION_CONTROLLER_STORE_PENDING_OBJECT_ID_KEY = "PENDING_CONTROLLER_KEY";
    private static final int LIKE_ACTION_CONTROLLER_VERSION = 3;
    private static final String LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY = "like_count_string";
    private static final String LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY = "object_is_liked";
    private static final String LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY = "social_sentence";
    private static final String LIKE_DIALOG_RESPONSE_UNLIKE_TOKEN_KEY = "unlike_token";
    private static final int MAX_CACHE_SIZE = 128;
    private static final int MAX_OBJECT_SUFFIX = 1000;
    /* access modifiers changed from: private */
    public static final String TAG = LikeActionController.class.getSimpleName();
    private static AccessTokenTracker accessTokenTracker;
    /* access modifiers changed from: private */
    public static final ConcurrentHashMap<String, LikeActionController> cache = new ConcurrentHashMap<>();
    /* access modifiers changed from: private */
    public static FileLruCache controllerDiskCache;
    private static WorkQueue diskIOWorkQueue = new WorkQueue(1);
    private static Handler handler;
    private static boolean isInitialized;
    private static WorkQueue mruCacheWorkQueue = new WorkQueue(1);
    private static String objectIdForPendingController;
    /* access modifiers changed from: private */
    public static volatile int objectSuffix;
    private AppEventsLogger appEventsLogger;
    private Bundle facebookDialogAnalyticsBundle;
    /* access modifiers changed from: private */
    public boolean isObjectLiked;
    /* access modifiers changed from: private */
    public boolean isObjectLikedOnServer;
    /* access modifiers changed from: private */
    public boolean isPendingLikeOrUnlike;
    /* access modifiers changed from: private */
    public String likeCountStringWithLike;
    /* access modifiers changed from: private */
    public String likeCountStringWithoutLike;
    /* access modifiers changed from: private */
    public String objectId;
    /* access modifiers changed from: private */
    public boolean objectIsPage;
    /* access modifiers changed from: private */
    public LikeView.ObjectType objectType;
    /* access modifiers changed from: private */
    public String socialSentenceWithLike;
    /* access modifiers changed from: private */
    public String socialSentenceWithoutLike;
    /* access modifiers changed from: private */
    public String unlikeToken;
    /* access modifiers changed from: private */
    public String verifiedObjectId;

    private abstract class AbstractRequestWrapper {
        FacebookRequestError error;
        protected String objectId;
        protected LikeView.ObjectType objectType;
        private GraphRequest request;

        protected AbstractRequestWrapper(String str, LikeView.ObjectType objectType2) {
            this.objectId = str;
            this.objectType = objectType2;
        }

        /* access modifiers changed from: package-private */
        public void addToBatch(GraphRequestBatch graphRequestBatch) {
            graphRequestBatch.add(this.request);
        }

        /* access modifiers changed from: protected */
        public void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error running request for object '%s' with type '%s' : %s", this.objectId, this.objectType, facebookRequestError);
        }

        /* access modifiers changed from: protected */
        public abstract void processSuccess(GraphResponse graphResponse);

        /* access modifiers changed from: protected */
        public void setRequest(GraphRequest graphRequest) {
            this.request = graphRequest;
            graphRequest.setVersion(ServerProtocol.GRAPH_API_VERSION);
            graphRequest.setCallback(new GraphRequest.Callback() {
                public void onCompleted(GraphResponse graphResponse) {
                    AbstractRequestWrapper.this.error = graphResponse.getError();
                    if (AbstractRequestWrapper.this.error != null) {
                        AbstractRequestWrapper.this.processError(AbstractRequestWrapper.this.error);
                    } else {
                        AbstractRequestWrapper.this.processSuccess(graphResponse);
                    }
                }
            });
        }
    }

    private static class CreateLikeActionControllerWorkItem implements Runnable {
        private CreationCallback callback;
        private String objectId;
        private LikeView.ObjectType objectType;

        CreateLikeActionControllerWorkItem(String str, LikeView.ObjectType objectType2, CreationCallback creationCallback) {
            this.objectId = str;
            this.objectType = objectType2;
            this.callback = creationCallback;
        }

        public void run() {
            LikeActionController.createControllerForObjectIdAndType(this.objectId, this.objectType, this.callback);
        }
    }

    public interface CreationCallback {
        void onComplete(LikeActionController likeActionController, FacebookException facebookException);
    }

    private class GetEngagementRequestWrapper extends AbstractRequestWrapper {
        String likeCountStringWithLike = LikeActionController.this.likeCountStringWithLike;
        String likeCountStringWithoutLike = LikeActionController.this.likeCountStringWithoutLike;
        String socialSentenceStringWithLike = LikeActionController.this.socialSentenceWithLike;
        String socialSentenceStringWithoutLike = LikeActionController.this.socialSentenceWithoutLike;

        GetEngagementRequestWrapper(String str, LikeView.ObjectType objectType) {
            super(str, objectType);
            Bundle bundle = new Bundle();
            bundle.putString("fields", "engagement.fields(count_string_with_like,count_string_without_like,social_sentence_with_like,social_sentence_without_like)");
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), str, bundle, HttpMethod.GET));
        }

        /* access modifiers changed from: protected */
        public void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error fetching engagement for object '%s' with type '%s' : %s", this.objectId, this.objectType, facebookRequestError);
            LikeActionController.this.logAppEventForError("get_engagement", facebookRequestError);
        }

        /* access modifiers changed from: protected */
        public void processSuccess(GraphResponse graphResponse) {
            JSONObject tryGetJSONObjectFromResponse = Utility.tryGetJSONObjectFromResponse(graphResponse.getJSONObject(), "engagement");
            if (tryGetJSONObjectFromResponse != null) {
                this.likeCountStringWithLike = tryGetJSONObjectFromResponse.optString("count_string_with_like", this.likeCountStringWithLike);
                this.likeCountStringWithoutLike = tryGetJSONObjectFromResponse.optString("count_string_without_like", this.likeCountStringWithoutLike);
                this.socialSentenceStringWithLike = tryGetJSONObjectFromResponse.optString(LikeActionController.JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY, this.socialSentenceStringWithLike);
                this.socialSentenceStringWithoutLike = tryGetJSONObjectFromResponse.optString(LikeActionController.JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY, this.socialSentenceStringWithoutLike);
            }
        }
    }

    private class GetOGObjectIdRequestWrapper extends AbstractRequestWrapper {
        String verifiedObjectId;

        GetOGObjectIdRequestWrapper(String str, LikeView.ObjectType objectType) {
            super(str, objectType);
            Bundle bundle = new Bundle();
            bundle.putString("fields", "og_object.fields(id)");
            bundle.putString("ids", str);
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), BuildConfig.FLAVOR, bundle, HttpMethod.GET));
        }

        /* access modifiers changed from: protected */
        public void processError(FacebookRequestError facebookRequestError) {
            if (facebookRequestError.getErrorMessage().contains("og_object")) {
                this.error = null;
                return;
            }
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error getting the FB id for object '%s' with type '%s' : %s", this.objectId, this.objectType, facebookRequestError);
        }

        /* access modifiers changed from: protected */
        public void processSuccess(GraphResponse graphResponse) {
            JSONObject optJSONObject;
            JSONObject tryGetJSONObjectFromResponse = Utility.tryGetJSONObjectFromResponse(graphResponse.getJSONObject(), this.objectId);
            if (tryGetJSONObjectFromResponse != null && (optJSONObject = tryGetJSONObjectFromResponse.optJSONObject("og_object")) != null) {
                this.verifiedObjectId = optJSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_ID);
            }
        }
    }

    private class GetOGObjectLikesRequestWrapper extends AbstractRequestWrapper {
        boolean objectIsLiked = LikeActionController.this.isObjectLiked;
        String unlikeToken;

        GetOGObjectLikesRequestWrapper(String str, LikeView.ObjectType objectType) {
            super(str, objectType);
            Bundle bundle = new Bundle();
            bundle.putString("fields", "id,application");
            bundle.putString("object", str);
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "me/og.likes", bundle, HttpMethod.GET));
        }

        /* access modifiers changed from: protected */
        public void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error fetching like status for object '%s' with type '%s' : %s", this.objectId, this.objectType, facebookRequestError);
            LikeActionController.this.logAppEventForError("get_og_object_like", facebookRequestError);
        }

        /* access modifiers changed from: protected */
        public void processSuccess(GraphResponse graphResponse) {
            JSONArray tryGetJSONArrayFromResponse = Utility.tryGetJSONArrayFromResponse(graphResponse.getJSONObject(), "data");
            if (tryGetJSONArrayFromResponse != null) {
                for (int i = 0; i < tryGetJSONArrayFromResponse.length(); i++) {
                    JSONObject optJSONObject = tryGetJSONArrayFromResponse.optJSONObject(i);
                    if (optJSONObject != null) {
                        this.objectIsLiked = true;
                        JSONObject optJSONObject2 = optJSONObject.optJSONObject("application");
                        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
                        if (!(optJSONObject2 == null || currentAccessToken == null || !Utility.areObjectsEqual(currentAccessToken.getApplicationId(), optJSONObject2.optString(ShareConstants.WEB_DIALOG_PARAM_ID)))) {
                            this.unlikeToken = optJSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_ID);
                        }
                    }
                }
            }
        }
    }

    private class GetPageIdRequestWrapper extends AbstractRequestWrapper {
        boolean objectIsPage;
        String verifiedObjectId;

        GetPageIdRequestWrapper(String str, LikeView.ObjectType objectType) {
            super(str, objectType);
            Bundle bundle = new Bundle();
            bundle.putString("fields", ShareConstants.WEB_DIALOG_PARAM_ID);
            bundle.putString("ids", str);
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), BuildConfig.FLAVOR, bundle, HttpMethod.GET));
        }

        /* access modifiers changed from: protected */
        public void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error getting the FB id for object '%s' with type '%s' : %s", this.objectId, this.objectType, facebookRequestError);
        }

        /* access modifiers changed from: protected */
        public void processSuccess(GraphResponse graphResponse) {
            JSONObject tryGetJSONObjectFromResponse = Utility.tryGetJSONObjectFromResponse(graphResponse.getJSONObject(), this.objectId);
            if (tryGetJSONObjectFromResponse != null) {
                this.verifiedObjectId = tryGetJSONObjectFromResponse.optString(ShareConstants.WEB_DIALOG_PARAM_ID);
                this.objectIsPage = !Utility.isNullOrEmpty(this.verifiedObjectId);
            }
        }
    }

    private static class MRUCacheWorkItem implements Runnable {
        private static ArrayList<String> mruCachedItems = new ArrayList<>();
        private String cacheItem;
        private boolean shouldTrim;

        MRUCacheWorkItem(String str, boolean z) {
            this.cacheItem = str;
            this.shouldTrim = z;
        }

        public void run() {
            if (this.cacheItem != null) {
                mruCachedItems.remove(this.cacheItem);
                mruCachedItems.add(0, this.cacheItem);
            }
            if (this.shouldTrim && mruCachedItems.size() >= 128) {
                while (64 < mruCachedItems.size()) {
                    LikeActionController.cache.remove(mruCachedItems.remove(mruCachedItems.size() - 1));
                }
            }
        }
    }

    private class PublishLikeRequestWrapper extends AbstractRequestWrapper {
        String unlikeToken;

        PublishLikeRequestWrapper(String str, LikeView.ObjectType objectType) {
            super(str, objectType);
            Bundle bundle = new Bundle();
            bundle.putString("object", str);
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "me/og.likes", bundle, HttpMethod.POST));
        }

        /* access modifiers changed from: protected */
        public void processError(FacebookRequestError facebookRequestError) {
            if (facebookRequestError.getErrorCode() == LikeActionController.ERROR_CODE_OBJECT_ALREADY_LIKED) {
                this.error = null;
                return;
            }
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error liking object '%s' with type '%s' : %s", this.objectId, this.objectType, facebookRequestError);
            LikeActionController.this.logAppEventForError("publish_like", facebookRequestError);
        }

        /* access modifiers changed from: protected */
        public void processSuccess(GraphResponse graphResponse) {
            this.unlikeToken = Utility.safeGetStringFromResponse(graphResponse.getJSONObject(), ShareConstants.WEB_DIALOG_PARAM_ID);
        }
    }

    private class PublishUnlikeRequestWrapper extends AbstractRequestWrapper {
        private String unlikeToken;

        PublishUnlikeRequestWrapper(String str) {
            super((String) null, (LikeView.ObjectType) null);
            this.unlikeToken = str;
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), str, (Bundle) null, HttpMethod.DELETE));
        }

        /* access modifiers changed from: protected */
        public void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error unliking object with unlike token '%s' : %s", this.unlikeToken, facebookRequestError);
            LikeActionController.this.logAppEventForError("publish_unlike", facebookRequestError);
        }

        /* access modifiers changed from: protected */
        public void processSuccess(GraphResponse graphResponse) {
        }
    }

    private interface RequestCompletionCallback {
        void onComplete();
    }

    private static class SerializeToDiskWorkItem implements Runnable {
        private String cacheKey;
        private String controllerJson;

        SerializeToDiskWorkItem(String str, String str2) {
            this.cacheKey = str;
            this.controllerJson = str2;
        }

        public void run() {
            LikeActionController.serializeToDiskSynchronously(this.cacheKey, this.controllerJson);
        }
    }

    private LikeActionController(String str, LikeView.ObjectType objectType2) {
        this.objectId = str;
        this.objectType = objectType2;
    }

    /* access modifiers changed from: private */
    public static void broadcastAction(LikeActionController likeActionController, String str) {
        broadcastAction(likeActionController, str, (Bundle) null);
    }

    /* access modifiers changed from: private */
    public static void broadcastAction(LikeActionController likeActionController, String str, Bundle bundle) {
        Intent intent = new Intent(str);
        if (likeActionController != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString(ACTION_OBJECT_ID_KEY, likeActionController.getObjectId());
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext()).sendBroadcast(intent);
    }

    private boolean canUseOGPublish() {
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        return (this.objectIsPage || this.verifiedObjectId == null || currentAccessToken == null || currentAccessToken.getPermissions() == null || !currentAccessToken.getPermissions().contains("publish_actions")) ? false : true;
    }

    private void clearState() {
        this.facebookDialogAnalyticsBundle = null;
        storeObjectIdForPendingController((String) null);
    }

    /* access modifiers changed from: private */
    public static void createControllerForObjectIdAndType(String str, LikeView.ObjectType objectType2, CreationCallback creationCallback) {
        LikeActionController controllerFromInMemoryCache = getControllerFromInMemoryCache(str);
        if (controllerFromInMemoryCache != null) {
            verifyControllerAndInvokeCallback(controllerFromInMemoryCache, objectType2, creationCallback);
            return;
        }
        LikeActionController deserializeFromDiskSynchronously = deserializeFromDiskSynchronously(str);
        if (deserializeFromDiskSynchronously == null) {
            deserializeFromDiskSynchronously = new LikeActionController(str, objectType2);
            serializeToDiskAsync(deserializeFromDiskSynchronously);
        }
        putControllerInMemoryCache(str, deserializeFromDiskSynchronously);
        LikeActionController likeActionController = deserializeFromDiskSynchronously;
        handler.post(new Runnable(likeActionController) {
            final /* synthetic */ LikeActionController val$controllerToRefresh;

            {
                this.val$controllerToRefresh = r1;
            }

            public void run() {
                this.val$controllerToRefresh.refreshStatusAsync();
            }
        });
        invokeCallbackWithController(creationCallback, likeActionController, (FacebookException) null);
    }

    private static LikeActionController deserializeFromDiskSynchronously(String str) {
        LikeActionController likeActionController = null;
        try {
            InputStream inputStream = controllerDiskCache.get(getCacheKeyForObjectId(str));
            if (inputStream != null) {
                String readStreamToString = Utility.readStreamToString(inputStream);
                if (!Utility.isNullOrEmpty(readStreamToString)) {
                    likeActionController = deserializeFromJson(readStreamToString);
                }
            }
            if (inputStream != null) {
                Utility.closeQuietly(inputStream);
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to deserialize controller from disk", e);
            likeActionController = null;
            if (0 != 0) {
                Utility.closeQuietly((Closeable) null);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                Utility.closeQuietly((Closeable) null);
            }
            throw th;
        }
        return likeActionController;
    }

    private static LikeActionController deserializeFromJson(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt(JSON_INT_VERSION_KEY, -1) != 3) {
                return null;
            }
            LikeActionController likeActionController = new LikeActionController(jSONObject.getString("object_id"), LikeView.ObjectType.fromInt(jSONObject.optInt("object_type", LikeView.ObjectType.UNKNOWN.getValue())));
            likeActionController.likeCountStringWithLike = jSONObject.optString(JSON_STRING_LIKE_COUNT_WITH_LIKE_KEY, (String) null);
            likeActionController.likeCountStringWithoutLike = jSONObject.optString(JSON_STRING_LIKE_COUNT_WITHOUT_LIKE_KEY, (String) null);
            likeActionController.socialSentenceWithLike = jSONObject.optString(JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY, (String) null);
            likeActionController.socialSentenceWithoutLike = jSONObject.optString(JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY, (String) null);
            likeActionController.isObjectLiked = jSONObject.optBoolean(JSON_BOOL_IS_OBJECT_LIKED_KEY);
            likeActionController.unlikeToken = jSONObject.optString("unlike_token", (String) null);
            JSONObject optJSONObject = jSONObject.optJSONObject(JSON_BUNDLE_FACEBOOK_DIALOG_ANALYTICS_BUNDLE);
            if (optJSONObject == null) {
                return likeActionController;
            }
            likeActionController.facebookDialogAnalyticsBundle = BundleJSONConverter.convertToBundle(optJSONObject);
            return likeActionController;
        } catch (JSONException e) {
            Log.e(TAG, "Unable to deserialize controller from JSON", e);
            return null;
        }
    }

    private void fetchVerifiedObjectId(final RequestCompletionCallback requestCompletionCallback) {
        if (Utility.isNullOrEmpty(this.verifiedObjectId)) {
            final GetOGObjectIdRequestWrapper getOGObjectIdRequestWrapper = new GetOGObjectIdRequestWrapper(this.objectId, this.objectType);
            final GetPageIdRequestWrapper getPageIdRequestWrapper = new GetPageIdRequestWrapper(this.objectId, this.objectType);
            GraphRequestBatch graphRequestBatch = new GraphRequestBatch();
            getOGObjectIdRequestWrapper.addToBatch(graphRequestBatch);
            getPageIdRequestWrapper.addToBatch(graphRequestBatch);
            graphRequestBatch.addCallback(new GraphRequestBatch.Callback() {
                public void onBatchCompleted(GraphRequestBatch graphRequestBatch) {
                    String unused = LikeActionController.this.verifiedObjectId = getOGObjectIdRequestWrapper.verifiedObjectId;
                    if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
                        String unused2 = LikeActionController.this.verifiedObjectId = getPageIdRequestWrapper.verifiedObjectId;
                        boolean unused3 = LikeActionController.this.objectIsPage = getPageIdRequestWrapper.objectIsPage;
                    }
                    if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
                        Logger.log(LoggingBehavior.DEVELOPER_ERRORS, LikeActionController.TAG, "Unable to verify the FB id for '%s'. Verify that it is a valid FB object or page", LikeActionController.this.objectId);
                        LikeActionController.this.logAppEventForError("get_verified_id", getPageIdRequestWrapper.error != null ? getPageIdRequestWrapper.error : getOGObjectIdRequestWrapper.error);
                    }
                    if (requestCompletionCallback != null) {
                        requestCompletionCallback.onComplete();
                    }
                }
            });
            graphRequestBatch.executeAsync();
        } else if (requestCompletionCallback != null) {
            requestCompletionCallback.onComplete();
        }
    }

    /* access modifiers changed from: private */
    public AppEventsLogger getAppEventsLogger() {
        if (this.appEventsLogger == null) {
            this.appEventsLogger = AppEventsLogger.newLogger(FacebookSdk.getApplicationContext());
        }
        return this.appEventsLogger;
    }

    private static String getCacheKeyForObjectId(String str) {
        String str2 = null;
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        if (currentAccessToken != null) {
            str2 = currentAccessToken.getToken();
        }
        if (str2 != null) {
            str2 = Utility.md5hash(str2);
        }
        return String.format(Locale.ROOT, "%s|%s|com.fb.sdk.like|%d", new Object[]{str, Utility.coerceValueIfNullOrEmpty(str2, BuildConfig.FLAVOR), Integer.valueOf(objectSuffix)});
    }

    public static void getControllerForObjectId(String str, LikeView.ObjectType objectType2, CreationCallback creationCallback) {
        if (!isInitialized) {
            performFirstInitialize();
        }
        LikeActionController controllerFromInMemoryCache = getControllerFromInMemoryCache(str);
        if (controllerFromInMemoryCache != null) {
            verifyControllerAndInvokeCallback(controllerFromInMemoryCache, objectType2, creationCallback);
        } else {
            diskIOWorkQueue.addActiveWorkItem(new CreateLikeActionControllerWorkItem(str, objectType2, creationCallback));
        }
    }

    private static LikeActionController getControllerFromInMemoryCache(String str) {
        String cacheKeyForObjectId = getCacheKeyForObjectId(str);
        LikeActionController likeActionController = cache.get(cacheKeyForObjectId);
        if (likeActionController != null) {
            mruCacheWorkQueue.addActiveWorkItem(new MRUCacheWorkItem(cacheKeyForObjectId, false));
        }
        return likeActionController;
    }

    private ResultProcessor getResultProcessor(final Bundle bundle) {
        return new ResultProcessor((FacebookCallback) null) {
            public void onCancel(AppCall appCall) {
                onError(appCall, new FacebookOperationCanceledException());
            }

            public void onError(AppCall appCall, FacebookException facebookException) {
                Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Like Dialog failed with error : %s", facebookException);
                Bundle bundle = bundle == null ? new Bundle() : bundle;
                bundle.putString(AnalyticsEvents.PARAMETER_CALL_ID, appCall.getCallId().toString());
                LikeActionController.this.logAppEventForError("present_dialog", bundle);
                LikeActionController.broadcastAction(LikeActionController.this, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, NativeProtocol.createBundleForException(facebookException));
            }

            public void onSuccess(AppCall appCall, Bundle bundle) {
                if (bundle != null && bundle.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY)) {
                    boolean z = bundle.getBoolean(LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY);
                    String access$700 = LikeActionController.this.likeCountStringWithLike;
                    String access$800 = LikeActionController.this.likeCountStringWithoutLike;
                    if (bundle.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY)) {
                        access$700 = bundle.getString(LikeActionController.LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY);
                        access$800 = access$700;
                    }
                    String access$900 = LikeActionController.this.socialSentenceWithLike;
                    String access$1000 = LikeActionController.this.socialSentenceWithoutLike;
                    if (bundle.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY)) {
                        access$900 = bundle.getString(LikeActionController.LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY);
                        access$1000 = access$900;
                    }
                    String string = bundle.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY) ? bundle.getString("unlike_token") : LikeActionController.this.unlikeToken;
                    Bundle bundle2 = bundle == null ? new Bundle() : bundle;
                    bundle2.putString(AnalyticsEvents.PARAMETER_CALL_ID, appCall.getCallId().toString());
                    LikeActionController.this.getAppEventsLogger().logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DIALOG_DID_SUCCEED, (Double) null, bundle2);
                    LikeActionController.this.updateState(z, access$700, access$800, access$900, access$1000, string);
                }
            }
        };
    }

    public static boolean handleOnActivityResult(final int i, final int i2, final Intent intent) {
        if (Utility.isNullOrEmpty(objectIdForPendingController)) {
            objectIdForPendingController = FacebookSdk.getApplicationContext().getSharedPreferences(LIKE_ACTION_CONTROLLER_STORE, 0).getString(LIKE_ACTION_CONTROLLER_STORE_PENDING_OBJECT_ID_KEY, (String) null);
        }
        if (Utility.isNullOrEmpty(objectIdForPendingController)) {
            return false;
        }
        getControllerForObjectId(objectIdForPendingController, LikeView.ObjectType.UNKNOWN, new CreationCallback() {
            public void onComplete(LikeActionController likeActionController, FacebookException facebookException) {
                if (facebookException == null) {
                    likeActionController.onActivityResult(i, i2, intent);
                } else {
                    Utility.logd(LikeActionController.TAG, (Exception) facebookException);
                }
            }
        });
        return true;
    }

    private static void invokeCallbackWithController(final CreationCallback creationCallback, final LikeActionController likeActionController, final FacebookException facebookException) {
        if (creationCallback != null) {
            handler.post(new Runnable() {
                public void run() {
                    creationCallback.onComplete(likeActionController, facebookException);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void logAppEventForError(String str, Bundle bundle) {
        Bundle bundle2 = new Bundle(bundle);
        bundle2.putString("object_id", this.objectId);
        bundle2.putString("object_type", this.objectType.toString());
        bundle2.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_CURRENT_ACTION, str);
        getAppEventsLogger().logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_ERROR, (Double) null, bundle2);
    }

    /* access modifiers changed from: private */
    public void logAppEventForError(String str, FacebookRequestError facebookRequestError) {
        JSONObject requestResult;
        Bundle bundle = new Bundle();
        if (!(facebookRequestError == null || (requestResult = facebookRequestError.getRequestResult()) == null)) {
            bundle.putString("error", requestResult.toString());
        }
        logAppEventForError(str, bundle);
    }

    /* access modifiers changed from: private */
    public void onActivityResult(int i, int i2, Intent intent) {
        ShareInternalUtility.handleActivityResult(i, i2, intent, getResultProcessor(this.facebookDialogAnalyticsBundle));
        clearState();
    }

    private static synchronized void performFirstInitialize() {
        synchronized (LikeActionController.class) {
            if (!isInitialized) {
                handler = new Handler(Looper.getMainLooper());
                objectSuffix = FacebookSdk.getApplicationContext().getSharedPreferences(LIKE_ACTION_CONTROLLER_STORE, 0).getInt(LIKE_ACTION_CONTROLLER_STORE_OBJECT_SUFFIX_KEY, 1);
                controllerDiskCache = new FileLruCache(TAG, new FileLruCache.Limits());
                registerAccessTokenTracker();
                CallbackManagerImpl.registerStaticCallback(CallbackManagerImpl.RequestCodeOffset.Like.toRequestCode(), new CallbackManagerImpl.Callback() {
                    public boolean onActivityResult(int i, Intent intent) {
                        return LikeActionController.handleOnActivityResult(CallbackManagerImpl.RequestCodeOffset.Like.toRequestCode(), i, intent);
                    }
                });
                isInitialized = true;
            }
        }
    }

    private void presentLikeDialog(Activity activity, Fragment fragment, Bundle bundle) {
        String str = null;
        if (LikeDialog.canShowNativeDialog()) {
            str = AnalyticsEvents.EVENT_LIKE_VIEW_DID_PRESENT_DIALOG;
        } else if (LikeDialog.canShowWebFallback()) {
            str = AnalyticsEvents.EVENT_LIKE_VIEW_DID_PRESENT_FALLBACK;
        } else {
            logAppEventForError("present_dialog", bundle);
            Utility.logd(TAG, "Cannot show the Like Dialog on this device.");
            broadcastAction((LikeActionController) null, ACTION_LIKE_ACTION_CONTROLLER_UPDATED);
        }
        if (str != null) {
            LikeContent build = new LikeContent.Builder().setObjectId(this.objectId).setObjectType(this.objectType).build();
            if (fragment != null) {
                new LikeDialog(fragment).show(build);
            } else {
                new LikeDialog(activity).show(build);
            }
            saveState(bundle);
            getAppEventsLogger().logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_PRESENT_DIALOG, (Double) null, bundle);
        }
    }

    /* access modifiers changed from: private */
    public void publishAgainIfNeeded(Bundle bundle) {
        if (this.isObjectLiked != this.isObjectLikedOnServer && !publishLikeOrUnlikeAsync(this.isObjectLiked, bundle)) {
            publishDidError(!this.isObjectLiked);
        }
    }

    /* access modifiers changed from: private */
    public void publishDidError(boolean z) {
        updateLikeState(z);
        Bundle bundle = new Bundle();
        bundle.putString(NativeProtocol.STATUS_ERROR_DESCRIPTION, ERROR_PUBLISH_ERROR);
        broadcastAction(this, ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, bundle);
    }

    private void publishLikeAsync(final Bundle bundle) {
        this.isPendingLikeOrUnlike = true;
        fetchVerifiedObjectId(new RequestCompletionCallback() {
            public void onComplete() {
                if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(NativeProtocol.STATUS_ERROR_DESCRIPTION, LikeActionController.ERROR_INVALID_OBJECT_ID);
                    LikeActionController.broadcastAction(LikeActionController.this, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, bundle);
                    return;
                }
                GraphRequestBatch graphRequestBatch = new GraphRequestBatch();
                final PublishLikeRequestWrapper publishLikeRequestWrapper = new PublishLikeRequestWrapper(LikeActionController.this.verifiedObjectId, LikeActionController.this.objectType);
                publishLikeRequestWrapper.addToBatch(graphRequestBatch);
                graphRequestBatch.addCallback(new GraphRequestBatch.Callback() {
                    public void onBatchCompleted(GraphRequestBatch graphRequestBatch) {
                        boolean unused = LikeActionController.this.isPendingLikeOrUnlike = false;
                        if (publishLikeRequestWrapper.error != null) {
                            LikeActionController.this.publishDidError(false);
                            return;
                        }
                        String unused2 = LikeActionController.this.unlikeToken = Utility.coerceValueIfNullOrEmpty(publishLikeRequestWrapper.unlikeToken, (String) null);
                        boolean unused3 = LikeActionController.this.isObjectLikedOnServer = true;
                        LikeActionController.this.getAppEventsLogger().logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_LIKE, (Double) null, bundle);
                        LikeActionController.this.publishAgainIfNeeded(bundle);
                    }
                });
                graphRequestBatch.executeAsync();
            }
        });
    }

    private boolean publishLikeOrUnlikeAsync(boolean z, Bundle bundle) {
        if (!canUseOGPublish()) {
            return false;
        }
        if (z) {
            publishLikeAsync(bundle);
            return true;
        } else if (Utility.isNullOrEmpty(this.unlikeToken)) {
            return false;
        } else {
            publishUnlikeAsync(bundle);
            return true;
        }
    }

    private void publishUnlikeAsync(final Bundle bundle) {
        this.isPendingLikeOrUnlike = true;
        GraphRequestBatch graphRequestBatch = new GraphRequestBatch();
        final PublishUnlikeRequestWrapper publishUnlikeRequestWrapper = new PublishUnlikeRequestWrapper(this.unlikeToken);
        publishUnlikeRequestWrapper.addToBatch(graphRequestBatch);
        graphRequestBatch.addCallback(new GraphRequestBatch.Callback() {
            public void onBatchCompleted(GraphRequestBatch graphRequestBatch) {
                boolean unused = LikeActionController.this.isPendingLikeOrUnlike = false;
                if (publishUnlikeRequestWrapper.error != null) {
                    LikeActionController.this.publishDidError(true);
                    return;
                }
                String unused2 = LikeActionController.this.unlikeToken = null;
                boolean unused3 = LikeActionController.this.isObjectLikedOnServer = false;
                LikeActionController.this.getAppEventsLogger().logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_UNLIKE, (Double) null, bundle);
                LikeActionController.this.publishAgainIfNeeded(bundle);
            }
        });
        graphRequestBatch.executeAsync();
    }

    private static void putControllerInMemoryCache(String str, LikeActionController likeActionController) {
        String cacheKeyForObjectId = getCacheKeyForObjectId(str);
        mruCacheWorkQueue.addActiveWorkItem(new MRUCacheWorkItem(cacheKeyForObjectId, true));
        cache.put(cacheKeyForObjectId, likeActionController);
    }

    /* access modifiers changed from: private */
    public void refreshStatusAsync() {
        if (AccessToken.getCurrentAccessToken() == null) {
            refreshStatusViaService();
        } else {
            fetchVerifiedObjectId(new RequestCompletionCallback() {
                public void onComplete() {
                    final GetOGObjectLikesRequestWrapper getOGObjectLikesRequestWrapper = new GetOGObjectLikesRequestWrapper(LikeActionController.this.verifiedObjectId, LikeActionController.this.objectType);
                    final GetEngagementRequestWrapper getEngagementRequestWrapper = new GetEngagementRequestWrapper(LikeActionController.this.verifiedObjectId, LikeActionController.this.objectType);
                    GraphRequestBatch graphRequestBatch = new GraphRequestBatch();
                    getOGObjectLikesRequestWrapper.addToBatch(graphRequestBatch);
                    getEngagementRequestWrapper.addToBatch(graphRequestBatch);
                    graphRequestBatch.addCallback(new GraphRequestBatch.Callback() {
                        public void onBatchCompleted(GraphRequestBatch graphRequestBatch) {
                            if (getOGObjectLikesRequestWrapper.error == null && getEngagementRequestWrapper.error == null) {
                                LikeActionController.this.updateState(getOGObjectLikesRequestWrapper.objectIsLiked, getEngagementRequestWrapper.likeCountStringWithLike, getEngagementRequestWrapper.likeCountStringWithoutLike, getEngagementRequestWrapper.socialSentenceStringWithLike, getEngagementRequestWrapper.socialSentenceStringWithoutLike, getOGObjectLikesRequestWrapper.unlikeToken);
                                return;
                            }
                            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Unable to refresh like state for id: '%s'", LikeActionController.this.objectId);
                        }
                    });
                    graphRequestBatch.executeAsync();
                }
            });
        }
    }

    private void refreshStatusViaService() {
        LikeStatusClient likeStatusClient = new LikeStatusClient(FacebookSdk.getApplicationContext(), FacebookSdk.getApplicationId(), this.objectId);
        if (likeStatusClient.start()) {
            likeStatusClient.setCompletedListener(new PlatformServiceClient.CompletedListener() {
                public void completed(Bundle bundle) {
                    if (bundle != null && bundle.containsKey(ShareConstants.EXTRA_OBJECT_IS_LIKED)) {
                        LikeActionController.this.updateState(bundle.getBoolean(ShareConstants.EXTRA_OBJECT_IS_LIKED), bundle.containsKey(ShareConstants.EXTRA_LIKE_COUNT_STRING_WITH_LIKE) ? bundle.getString(ShareConstants.EXTRA_LIKE_COUNT_STRING_WITH_LIKE) : LikeActionController.this.likeCountStringWithLike, bundle.containsKey(ShareConstants.EXTRA_LIKE_COUNT_STRING_WITHOUT_LIKE) ? bundle.getString(ShareConstants.EXTRA_LIKE_COUNT_STRING_WITHOUT_LIKE) : LikeActionController.this.likeCountStringWithoutLike, bundle.containsKey(ShareConstants.EXTRA_SOCIAL_SENTENCE_WITH_LIKE) ? bundle.getString(ShareConstants.EXTRA_SOCIAL_SENTENCE_WITH_LIKE) : LikeActionController.this.socialSentenceWithLike, bundle.containsKey(ShareConstants.EXTRA_SOCIAL_SENTENCE_WITHOUT_LIKE) ? bundle.getString(ShareConstants.EXTRA_SOCIAL_SENTENCE_WITHOUT_LIKE) : LikeActionController.this.socialSentenceWithoutLike, bundle.containsKey(ShareConstants.EXTRA_UNLIKE_TOKEN) ? bundle.getString(ShareConstants.EXTRA_UNLIKE_TOKEN) : LikeActionController.this.unlikeToken);
                    }
                }
            });
        }
    }

    private static void registerAccessTokenTracker() {
        accessTokenTracker = new AccessTokenTracker() {
            /* access modifiers changed from: protected */
            public void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                Context applicationContext = FacebookSdk.getApplicationContext();
                if (accessToken2 == null) {
                    int unused = LikeActionController.objectSuffix = (LikeActionController.objectSuffix + 1) % 1000;
                    applicationContext.getSharedPreferences(LikeActionController.LIKE_ACTION_CONTROLLER_STORE, 0).edit().putInt(LikeActionController.LIKE_ACTION_CONTROLLER_STORE_OBJECT_SUFFIX_KEY, LikeActionController.objectSuffix).apply();
                    LikeActionController.cache.clear();
                    LikeActionController.controllerDiskCache.clearCache();
                }
                LikeActionController.broadcastAction((LikeActionController) null, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET);
            }
        };
    }

    private void saveState(Bundle bundle) {
        storeObjectIdForPendingController(this.objectId);
        this.facebookDialogAnalyticsBundle = bundle;
        serializeToDiskAsync(this);
    }

    private static void serializeToDiskAsync(LikeActionController likeActionController) {
        String serializeToJson = serializeToJson(likeActionController);
        String cacheKeyForObjectId = getCacheKeyForObjectId(likeActionController.objectId);
        if (!Utility.isNullOrEmpty(serializeToJson) && !Utility.isNullOrEmpty(cacheKeyForObjectId)) {
            diskIOWorkQueue.addActiveWorkItem(new SerializeToDiskWorkItem(cacheKeyForObjectId, serializeToJson));
        }
    }

    /* access modifiers changed from: private */
    public static void serializeToDiskSynchronously(String str, String str2) {
        OutputStream outputStream = null;
        try {
            outputStream = controllerDiskCache.openPutStream(str);
            outputStream.write(str2.getBytes());
            if (outputStream != null) {
                Utility.closeQuietly(outputStream);
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to serialize controller to disk", e);
            if (outputStream != null) {
                Utility.closeQuietly(outputStream);
            }
        } catch (Throwable th) {
            if (outputStream != null) {
                Utility.closeQuietly(outputStream);
            }
            throw th;
        }
    }

    private static String serializeToJson(LikeActionController likeActionController) {
        JSONObject convertToJSON;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(JSON_INT_VERSION_KEY, 3);
            jSONObject.put("object_id", likeActionController.objectId);
            jSONObject.put("object_type", likeActionController.objectType.getValue());
            jSONObject.put(JSON_STRING_LIKE_COUNT_WITH_LIKE_KEY, likeActionController.likeCountStringWithLike);
            jSONObject.put(JSON_STRING_LIKE_COUNT_WITHOUT_LIKE_KEY, likeActionController.likeCountStringWithoutLike);
            jSONObject.put(JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY, likeActionController.socialSentenceWithLike);
            jSONObject.put(JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY, likeActionController.socialSentenceWithoutLike);
            jSONObject.put(JSON_BOOL_IS_OBJECT_LIKED_KEY, likeActionController.isObjectLiked);
            jSONObject.put("unlike_token", likeActionController.unlikeToken);
            if (!(likeActionController.facebookDialogAnalyticsBundle == null || (convertToJSON = BundleJSONConverter.convertToJSON(likeActionController.facebookDialogAnalyticsBundle)) == null)) {
                jSONObject.put(JSON_BUNDLE_FACEBOOK_DIALOG_ANALYTICS_BUNDLE, convertToJSON);
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            Log.e(TAG, "Unable to serialize controller to JSON", e);
            return null;
        }
    }

    private static void storeObjectIdForPendingController(String str) {
        objectIdForPendingController = str;
        FacebookSdk.getApplicationContext().getSharedPreferences(LIKE_ACTION_CONTROLLER_STORE, 0).edit().putString(LIKE_ACTION_CONTROLLER_STORE_PENDING_OBJECT_ID_KEY, objectIdForPendingController).apply();
    }

    private void updateLikeState(boolean z) {
        updateState(z, this.likeCountStringWithLike, this.likeCountStringWithoutLike, this.socialSentenceWithLike, this.socialSentenceWithoutLike, this.unlikeToken);
    }

    /* access modifiers changed from: private */
    public void updateState(boolean z, String str, String str2, String str3, String str4, String str5) {
        String coerceValueIfNullOrEmpty = Utility.coerceValueIfNullOrEmpty(str, (String) null);
        String coerceValueIfNullOrEmpty2 = Utility.coerceValueIfNullOrEmpty(str2, (String) null);
        String coerceValueIfNullOrEmpty3 = Utility.coerceValueIfNullOrEmpty(str3, (String) null);
        String coerceValueIfNullOrEmpty4 = Utility.coerceValueIfNullOrEmpty(str4, (String) null);
        String coerceValueIfNullOrEmpty5 = Utility.coerceValueIfNullOrEmpty(str5, (String) null);
        if (z != this.isObjectLiked || !Utility.areObjectsEqual(coerceValueIfNullOrEmpty, this.likeCountStringWithLike) || !Utility.areObjectsEqual(coerceValueIfNullOrEmpty2, this.likeCountStringWithoutLike) || !Utility.areObjectsEqual(coerceValueIfNullOrEmpty3, this.socialSentenceWithLike) || !Utility.areObjectsEqual(coerceValueIfNullOrEmpty4, this.socialSentenceWithoutLike) || !Utility.areObjectsEqual(coerceValueIfNullOrEmpty5, this.unlikeToken)) {
            this.isObjectLiked = z;
            this.likeCountStringWithLike = coerceValueIfNullOrEmpty;
            this.likeCountStringWithoutLike = coerceValueIfNullOrEmpty2;
            this.socialSentenceWithLike = coerceValueIfNullOrEmpty3;
            this.socialSentenceWithoutLike = coerceValueIfNullOrEmpty4;
            this.unlikeToken = coerceValueIfNullOrEmpty5;
            serializeToDiskAsync(this);
            broadcastAction(this, ACTION_LIKE_ACTION_CONTROLLER_UPDATED);
        }
    }

    private static void verifyControllerAndInvokeCallback(LikeActionController likeActionController, LikeView.ObjectType objectType2, CreationCallback creationCallback) {
        LikeView.ObjectType mostSpecificObjectType = ShareInternalUtility.getMostSpecificObjectType(objectType2, likeActionController.objectType);
        FacebookException facebookException = null;
        if (mostSpecificObjectType == null) {
            facebookException = new FacebookException("Object with id:\"%s\" is already marked as type:\"%s\". Cannot change the type to:\"%s\"", likeActionController.objectId, likeActionController.objectType.toString(), objectType2.toString());
            likeActionController = null;
        } else {
            likeActionController.objectType = mostSpecificObjectType;
        }
        invokeCallbackWithController(creationCallback, likeActionController, facebookException);
    }

    public String getLikeCountString() {
        return this.isObjectLiked ? this.likeCountStringWithLike : this.likeCountStringWithoutLike;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public String getSocialSentence() {
        return this.isObjectLiked ? this.socialSentenceWithLike : this.socialSentenceWithoutLike;
    }

    public boolean isObjectLiked() {
        return this.isObjectLiked;
    }

    public boolean shouldEnableView() {
        if (LikeDialog.canShowNativeDialog() || LikeDialog.canShowWebFallback()) {
            return true;
        }
        if (this.objectIsPage || this.objectType == LikeView.ObjectType.PAGE) {
            return false;
        }
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        return (currentAccessToken == null || currentAccessToken.getPermissions() == null || !currentAccessToken.getPermissions().contains("publish_actions")) ? false : true;
    }

    public void toggleLike(Activity activity, Fragment fragment, Bundle bundle) {
        boolean z = true;
        getAppEventsLogger().logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_TAP, (Double) null, bundle);
        boolean z2 = !this.isObjectLiked;
        if (canUseOGPublish()) {
            updateLikeState(z2);
            if (this.isPendingLikeOrUnlike) {
                getAppEventsLogger().logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_UNDO_QUICKLY, (Double) null, bundle);
            } else if (!publishLikeOrUnlikeAsync(z2, bundle)) {
                if (z2) {
                    z = false;
                }
                updateLikeState(z);
                presentLikeDialog(activity, fragment, bundle);
            }
        } else {
            presentLikeDialog(activity, fragment, bundle);
        }
    }
}
