package com.facebook.share.internal;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.GraphUtil;
import com.facebook.internal.NativeAppCallAttachmentStore;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.share.Sharer;
import com.facebook.share.internal.OpenGraphJSONUtility;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.LikeView;
import com.google.android.gms.drive.DriveFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ShareInternalUtility {
    private static final String MY_ACTION_FORMAT = "me/%s";
    private static final String MY_FEED = "me/feed";
    private static final String MY_OBJECTS_FORMAT = "me/objects/%s";
    private static final String MY_PHOTOS = "me/photos";
    private static final String MY_STAGING_RESOURCES = "me/staging_resources";
    private static final String MY_VIDEOS = "me/videos";
    private static final String OBJECT_PARAM = "object";
    private static final String PICTURE_PARAM = "picture";
    private static final String STAGING_PARAM = "file";

    private ShareInternalUtility() {
    }

    private static AppCall getAppCallFromActivityResult(int i, int i2, Intent intent) {
        UUID callIdFromIntent = NativeProtocol.getCallIdFromIntent(intent);
        if (callIdFromIntent == null) {
            return null;
        }
        return AppCall.finishPendingCall(callIdFromIntent, i);
    }

    /* access modifiers changed from: private */
    public static NativeAppCallAttachmentStore.Attachment getAttachment(UUID uuid, SharePhoto sharePhoto) {
        Bitmap bitmap = sharePhoto.getBitmap();
        Uri imageUrl = sharePhoto.getImageUrl();
        if (bitmap != null) {
            return NativeAppCallAttachmentStore.createAttachment(uuid, bitmap);
        }
        if (imageUrl != null) {
            return NativeAppCallAttachmentStore.createAttachment(uuid, imageUrl);
        }
        return null;
    }

    public static Pair<String, String> getFieldNameAndNamespaceFromFullName(String str) {
        String str2;
        String str3 = null;
        int indexOf = str.indexOf(58);
        if (indexOf == -1 || str.length() <= indexOf + 1) {
            str2 = str;
        } else {
            str3 = str.substring(0, indexOf);
            str2 = str.substring(indexOf + 1);
        }
        return new Pair<>(str3, str2);
    }

    @Nullable
    public static LikeView.ObjectType getMostSpecificObjectType(LikeView.ObjectType objectType, LikeView.ObjectType objectType2) {
        if (objectType == objectType2) {
            return objectType;
        }
        if (objectType == LikeView.ObjectType.UNKNOWN) {
            return objectType2;
        }
        if (objectType2 != LikeView.ObjectType.UNKNOWN) {
            return null;
        }
        return objectType;
    }

    public static String getNativeDialogCompletionGesture(Bundle bundle) {
        return bundle.containsKey(NativeProtocol.RESULT_ARGS_DIALOG_COMPLETION_GESTURE_KEY) ? bundle.getString(NativeProtocol.RESULT_ARGS_DIALOG_COMPLETION_GESTURE_KEY) : bundle.getString(NativeProtocol.EXTRA_DIALOG_COMPLETION_GESTURE_KEY);
    }

    public static boolean getNativeDialogDidComplete(Bundle bundle) {
        return bundle.containsKey(NativeProtocol.RESULT_ARGS_DIALOG_COMPLETE_KEY) ? bundle.getBoolean(NativeProtocol.RESULT_ARGS_DIALOG_COMPLETE_KEY) : bundle.getBoolean(NativeProtocol.EXTRA_DIALOG_COMPLETE_KEY, false);
    }

    public static List<String> getPhotoUrls(SharePhotoContent sharePhotoContent, final UUID uuid) {
        List<SharePhoto> photos;
        if (sharePhotoContent == null || (photos = sharePhotoContent.getPhotos()) == null) {
            return null;
        }
        List<K> map = Utility.map(photos, new Utility.Mapper<SharePhoto, NativeAppCallAttachmentStore.Attachment>() {
            public NativeAppCallAttachmentStore.Attachment apply(SharePhoto sharePhoto) {
                return ShareInternalUtility.getAttachment(uuid, sharePhoto);
            }
        });
        List<String> map2 = Utility.map(map, new Utility.Mapper<NativeAppCallAttachmentStore.Attachment, String>() {
            public String apply(NativeAppCallAttachmentStore.Attachment attachment) {
                return attachment.getAttachmentUrl();
            }
        });
        NativeAppCallAttachmentStore.addAttachments(map);
        return map2;
    }

    public static String getShareDialogPostId(Bundle bundle) {
        return bundle.containsKey(ShareConstants.RESULT_POST_ID) ? bundle.getString(ShareConstants.RESULT_POST_ID) : bundle.containsKey(ShareConstants.EXTRA_RESULT_POST_ID) ? bundle.getString(ShareConstants.EXTRA_RESULT_POST_ID) : bundle.getString(ShareConstants.WEB_DIALOG_RESULT_PARAM_POST_ID);
    }

    public static ResultProcessor getShareResultProcessor(final FacebookCallback<Sharer.Result> facebookCallback) {
        return new ResultProcessor(facebookCallback) {
            public void onCancel(AppCall appCall) {
                ShareInternalUtility.invokeOnCancelCallback(facebookCallback);
            }

            public void onError(AppCall appCall, FacebookException facebookException) {
                ShareInternalUtility.invokeOnErrorCallback((FacebookCallback<Sharer.Result>) facebookCallback, facebookException);
            }

            public void onSuccess(AppCall appCall, Bundle bundle) {
                if (bundle != null) {
                    String nativeDialogCompletionGesture = ShareInternalUtility.getNativeDialogCompletionGesture(bundle);
                    if (nativeDialogCompletionGesture == null || "post".equalsIgnoreCase(nativeDialogCompletionGesture)) {
                        ShareInternalUtility.invokeOnSuccessCallback(facebookCallback, ShareInternalUtility.getShareDialogPostId(bundle));
                    } else if ("cancel".equalsIgnoreCase(nativeDialogCompletionGesture)) {
                        ShareInternalUtility.invokeOnCancelCallback(facebookCallback);
                    } else {
                        ShareInternalUtility.invokeOnErrorCallback((FacebookCallback<Sharer.Result>) facebookCallback, new FacebookException(NativeProtocol.ERROR_UNKNOWN_ERROR));
                    }
                }
            }
        };
    }

    public static boolean handleActivityResult(int i, int i2, Intent intent, ResultProcessor resultProcessor) {
        AppCall appCallFromActivityResult = getAppCallFromActivityResult(i, i2, intent);
        if (appCallFromActivityResult == null) {
            return false;
        }
        NativeAppCallAttachmentStore.cleanupAttachmentsForCall(appCallFromActivityResult.getCallId());
        if (resultProcessor == null) {
            return true;
        }
        FacebookException exceptionFromErrorData = NativeProtocol.getExceptionFromErrorData(NativeProtocol.getErrorDataFromResultIntent(intent));
        if (exceptionFromErrorData == null) {
            resultProcessor.onSuccess(appCallFromActivityResult, NativeProtocol.getSuccessResultsFromIntent(intent));
            return true;
        } else if (exceptionFromErrorData instanceof FacebookOperationCanceledException) {
            resultProcessor.onCancel(appCallFromActivityResult);
            return true;
        } else {
            resultProcessor.onError(appCallFromActivityResult, exceptionFromErrorData);
            return true;
        }
    }

    public static void invokeCallbackWithError(FacebookCallback<Sharer.Result> facebookCallback, String str) {
        invokeOnErrorCallback(facebookCallback, str);
    }

    public static void invokeCallbackWithException(FacebookCallback<Sharer.Result> facebookCallback, Exception exc) {
        if (exc instanceof FacebookException) {
            invokeOnErrorCallback(facebookCallback, (FacebookException) exc);
        } else {
            invokeCallbackWithError(facebookCallback, "Error preparing share content: " + exc.getLocalizedMessage());
        }
    }

    public static void invokeCallbackWithResults(FacebookCallback<Sharer.Result> facebookCallback, String str, GraphResponse graphResponse) {
        FacebookRequestError error = graphResponse.getError();
        if (error != null) {
            String errorMessage = error.getErrorMessage();
            if (Utility.isNullOrEmpty(errorMessage)) {
                errorMessage = "Unexpected error sharing.";
            }
            invokeOnErrorCallback(facebookCallback, graphResponse, errorMessage);
            return;
        }
        invokeOnSuccessCallback(facebookCallback, str);
    }

    /* access modifiers changed from: private */
    public static void invokeOnCancelCallback(FacebookCallback<Sharer.Result> facebookCallback) {
        logShareResult(AnalyticsEvents.PARAMETER_SHARE_OUTCOME_CANCELLED, (String) null);
        if (facebookCallback != null) {
            facebookCallback.onCancel();
        }
    }

    /* access modifiers changed from: private */
    public static void invokeOnErrorCallback(FacebookCallback<Sharer.Result> facebookCallback, FacebookException facebookException) {
        logShareResult("error", facebookException.getMessage());
        if (facebookCallback != null) {
            facebookCallback.onError(facebookException);
        }
    }

    private static void invokeOnErrorCallback(FacebookCallback<Sharer.Result> facebookCallback, GraphResponse graphResponse, String str) {
        logShareResult("error", str);
        if (facebookCallback != null) {
            facebookCallback.onError(new FacebookGraphResponseException(graphResponse, str));
        }
    }

    private static void invokeOnErrorCallback(FacebookCallback<Sharer.Result> facebookCallback, String str) {
        logShareResult("error", str);
        if (facebookCallback != null) {
            facebookCallback.onError(new FacebookException(str));
        }
    }

    /* access modifiers changed from: private */
    public static void invokeOnSuccessCallback(FacebookCallback<Sharer.Result> facebookCallback, String str) {
        logShareResult(AnalyticsEvents.PARAMETER_SHARE_OUTCOME_SUCCEEDED, (String) null);
        if (facebookCallback != null) {
            facebookCallback.onSuccess(new Sharer.Result(str));
        }
    }

    private static void logShareResult(String str, String str2) {
        AppEventsLogger newLogger = AppEventsLogger.newLogger(FacebookSdk.getApplicationContext());
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsEvents.PARAMETER_SHARE_OUTCOME, str);
        if (str2 != null) {
            bundle.putString(AnalyticsEvents.PARAMETER_SHARE_ERROR_MESSAGE, str2);
        }
        newLogger.logSdkEvent(AnalyticsEvents.EVENT_SHARE_RESULT, (Double) null, bundle);
    }

    public static GraphRequest newPostOpenGraphActionRequest(AccessToken accessToken, JSONObject jSONObject, GraphRequest.Callback callback) {
        if (jSONObject == null) {
            throw new FacebookException("openGraphAction cannot be null");
        }
        String optString = jSONObject.optString("type");
        if (Utility.isNullOrEmpty(optString)) {
            throw new FacebookException("openGraphAction must have non-null 'type' property");
        }
        return GraphRequest.newPostRequest(accessToken, String.format(MY_ACTION_FORMAT, new Object[]{optString}), jSONObject, callback);
    }

    public static GraphRequest newPostOpenGraphObjectRequest(AccessToken accessToken, String str, String str2, String str3, String str4, String str5, JSONObject jSONObject, GraphRequest.Callback callback) {
        return newPostOpenGraphObjectRequest(accessToken, GraphUtil.createOpenGraphObjectForPost(str, str2, str3, str4, str5, jSONObject, (String) null), callback);
    }

    public static GraphRequest newPostOpenGraphObjectRequest(AccessToken accessToken, JSONObject jSONObject, GraphRequest.Callback callback) {
        if (jSONObject == null) {
            throw new FacebookException("openGraphObject cannot be null");
        } else if (Utility.isNullOrEmpty(jSONObject.optString("type"))) {
            throw new FacebookException("openGraphObject must have non-null 'type' property");
        } else if (Utility.isNullOrEmpty(jSONObject.optString("title"))) {
            throw new FacebookException("openGraphObject must have non-null 'title' property");
        } else {
            String format = String.format(MY_OBJECTS_FORMAT, new Object[]{jSONObject.optString("type")});
            Bundle bundle = new Bundle();
            bundle.putString(OBJECT_PARAM, jSONObject.toString());
            return new GraphRequest(accessToken, format, bundle, HttpMethod.POST, callback);
        }
    }

    public static GraphRequest newStatusUpdateRequest(AccessToken accessToken, String str, GraphRequest.Callback callback) {
        return newStatusUpdateRequest(accessToken, str, (String) null, (List<String>) null, callback);
    }

    private static GraphRequest newStatusUpdateRequest(AccessToken accessToken, String str, String str2, List<String> list, GraphRequest.Callback callback) {
        Bundle bundle = new Bundle();
        bundle.putString("message", str);
        if (str2 != null) {
            bundle.putString("place", str2);
        }
        if (list != null && list.size() > 0) {
            bundle.putString("tags", TextUtils.join(",", list));
        }
        return new GraphRequest(accessToken, MY_FEED, bundle, HttpMethod.POST, callback);
    }

    public static GraphRequest newStatusUpdateRequest(AccessToken accessToken, String str, JSONObject jSONObject, List<JSONObject> list, GraphRequest.Callback callback) {
        ArrayList arrayList = null;
        if (list != null) {
            arrayList = new ArrayList(list.size());
            for (JSONObject optString : list) {
                arrayList.add(optString.optString(ShareConstants.WEB_DIALOG_PARAM_ID));
            }
        }
        return newStatusUpdateRequest(accessToken, str, jSONObject == null ? null : jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_ID), (List<String>) arrayList, callback);
    }

    public static GraphRequest newUpdateOpenGraphObjectRequest(AccessToken accessToken, String str, String str2, String str3, String str4, String str5, JSONObject jSONObject, GraphRequest.Callback callback) {
        return newUpdateOpenGraphObjectRequest(accessToken, GraphUtil.createOpenGraphObjectForPost((String) null, str2, str3, str4, str5, jSONObject, str), callback);
    }

    public static GraphRequest newUpdateOpenGraphObjectRequest(AccessToken accessToken, JSONObject jSONObject, GraphRequest.Callback callback) {
        if (jSONObject == null) {
            throw new FacebookException("openGraphObject cannot be null");
        }
        String optString = jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_ID);
        if (optString == null) {
            throw new FacebookException("openGraphObject must have an id");
        }
        Bundle bundle = new Bundle();
        bundle.putString(OBJECT_PARAM, jSONObject.toString());
        return new GraphRequest(accessToken, optString, bundle, HttpMethod.POST, callback);
    }

    public static GraphRequest newUploadPhotoRequest(AccessToken accessToken, Bitmap bitmap, GraphRequest.Callback callback) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("picture", bitmap);
        return new GraphRequest(accessToken, MY_PHOTOS, bundle, HttpMethod.POST, callback);
    }

    public static GraphRequest newUploadPhotoRequest(AccessToken accessToken, Uri uri, GraphRequest.Callback callback) throws FileNotFoundException {
        if (Utility.isFileUri(uri)) {
            return newUploadPhotoRequest(accessToken, new File(uri.getPath()), callback);
        }
        if (!Utility.isContentUri(uri)) {
            throw new FacebookException("The photo Uri must be either a file:// or content:// Uri");
        }
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("picture", uri);
        return new GraphRequest(accessToken, MY_PHOTOS, bundle, HttpMethod.POST, callback);
    }

    public static GraphRequest newUploadPhotoRequest(AccessToken accessToken, File file, GraphRequest.Callback callback) throws FileNotFoundException {
        ParcelFileDescriptor open = ParcelFileDescriptor.open(file, DriveFile.MODE_READ_ONLY);
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("picture", open);
        return new GraphRequest(accessToken, MY_PHOTOS, bundle, HttpMethod.POST, callback);
    }

    public static GraphRequest newUploadStagingResourceWithImageRequest(AccessToken accessToken, Bitmap bitmap, GraphRequest.Callback callback) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(STAGING_PARAM, bitmap);
        return new GraphRequest(accessToken, MY_STAGING_RESOURCES, bundle, HttpMethod.POST, callback);
    }

    public static GraphRequest newUploadStagingResourceWithImageRequest(AccessToken accessToken, Uri uri, GraphRequest.Callback callback) throws FileNotFoundException {
        if (Utility.isFileUri(uri)) {
            return newUploadStagingResourceWithImageRequest(accessToken, new File(uri.getPath()), callback);
        }
        if (!Utility.isContentUri(uri)) {
            throw new FacebookException("The image Uri must be either a file:// or content:// Uri");
        }
        GraphRequest.ParcelableResourceWithMimeType parcelableResourceWithMimeType = new GraphRequest.ParcelableResourceWithMimeType(uri, "image/png");
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(STAGING_PARAM, parcelableResourceWithMimeType);
        return new GraphRequest(accessToken, MY_STAGING_RESOURCES, bundle, HttpMethod.POST, callback);
    }

    public static GraphRequest newUploadStagingResourceWithImageRequest(AccessToken accessToken, File file, GraphRequest.Callback callback) throws FileNotFoundException {
        GraphRequest.ParcelableResourceWithMimeType parcelableResourceWithMimeType = new GraphRequest.ParcelableResourceWithMimeType(ParcelFileDescriptor.open(file, DriveFile.MODE_READ_ONLY), "image/png");
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(STAGING_PARAM, parcelableResourceWithMimeType);
        return new GraphRequest(accessToken, MY_STAGING_RESOURCES, bundle, HttpMethod.POST, callback);
    }

    public static GraphRequest newUploadVideoRequest(AccessToken accessToken, Uri uri, GraphRequest.Callback callback) throws FileNotFoundException {
        if (Utility.isFileUri(uri)) {
            return newUploadVideoRequest(accessToken, new File(uri.getPath()), callback);
        }
        if (!Utility.isContentUri(uri)) {
            throw new FacebookException("The video Uri must be either a file:// or content:// Uri");
        }
        Cursor query = FacebookSdk.getApplicationContext().getContentResolver().query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
        int columnIndex = query.getColumnIndex("_display_name");
        query.moveToFirst();
        String string = query.getString(columnIndex);
        query.close();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(string, uri);
        return new GraphRequest(accessToken, MY_VIDEOS, bundle, HttpMethod.POST, callback);
    }

    public static GraphRequest newUploadVideoRequest(AccessToken accessToken, File file, GraphRequest.Callback callback) throws FileNotFoundException {
        ParcelFileDescriptor open = ParcelFileDescriptor.open(file, DriveFile.MODE_READ_ONLY);
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(file.getName(), open);
        return new GraphRequest(accessToken, MY_VIDEOS, bundle, HttpMethod.POST, callback);
    }

    public static void registerSharerCallback(final int i, CallbackManager callbackManager, final FacebookCallback<Sharer.Result> facebookCallback) {
        if (!(callbackManager instanceof CallbackManagerImpl)) {
            throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
        }
        ((CallbackManagerImpl) callbackManager).registerCallback(i, new CallbackManagerImpl.Callback() {
            public boolean onActivityResult(int i, Intent intent) {
                return ShareInternalUtility.handleActivityResult(i, i, intent, ShareInternalUtility.getShareResultProcessor(facebookCallback));
            }
        });
    }

    public static void registerStaticShareCallback(final int i) {
        CallbackManagerImpl.registerStaticCallback(i, new CallbackManagerImpl.Callback() {
            public boolean onActivityResult(int i, Intent intent) {
                return ShareInternalUtility.handleActivityResult(i, i, intent, ShareInternalUtility.getShareResultProcessor((FacebookCallback<Sharer.Result>) null));
            }
        });
    }

    public static JSONArray removeNamespacesFromOGJsonArray(JSONArray jSONArray, boolean z) throws JSONException {
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < jSONArray.length(); i++) {
            Object obj = jSONArray.get(i);
            if (obj instanceof JSONArray) {
                obj = removeNamespacesFromOGJsonArray((JSONArray) obj, z);
            } else if (obj instanceof JSONObject) {
                obj = removeNamespacesFromOGJsonObject((JSONObject) obj, z);
            }
            jSONArray2.put(obj);
        }
        return jSONArray2;
    }

    public static JSONObject removeNamespacesFromOGJsonObject(JSONObject jSONObject, boolean z) {
        if (jSONObject == null) {
            return null;
        }
        try {
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            JSONArray names = jSONObject.names();
            for (int i = 0; i < names.length(); i++) {
                String string = names.getString(i);
                Object obj = jSONObject.get(string);
                if (obj instanceof JSONObject) {
                    obj = removeNamespacesFromOGJsonObject((JSONObject) obj, true);
                } else if (obj instanceof JSONArray) {
                    obj = removeNamespacesFromOGJsonArray((JSONArray) obj, true);
                }
                Pair<String, String> fieldNameAndNamespaceFromFullName = getFieldNameAndNamespaceFromFullName(string);
                String str = (String) fieldNameAndNamespaceFromFullName.first;
                String str2 = (String) fieldNameAndNamespaceFromFullName.second;
                if (!z) {
                    jSONObject2.put(str2, obj);
                } else if (str != null && str.equals("fbsdk")) {
                    jSONObject2.put(string, obj);
                } else if (str == null || str.equals("og")) {
                    jSONObject2.put(str2, obj);
                } else {
                    jSONObject3.put(str2, obj);
                }
            }
            if (jSONObject3.length() <= 0) {
                return jSONObject2;
            }
            jSONObject2.put("data", jSONObject3);
            return jSONObject2;
        } catch (JSONException e) {
            throw new FacebookException("Failed to create json object from share content");
        }
    }

    public static JSONObject toJSONObjectForCall(final UUID uuid, ShareOpenGraphAction shareOpenGraphAction) throws JSONException {
        final ArrayList arrayList = new ArrayList();
        JSONObject jSONObject = OpenGraphJSONUtility.toJSONObject(shareOpenGraphAction, (OpenGraphJSONUtility.PhotoJSONProcessor) new OpenGraphJSONUtility.PhotoJSONProcessor() {
            public JSONObject toJSONObject(SharePhoto sharePhoto) {
                NativeAppCallAttachmentStore.Attachment access$300 = ShareInternalUtility.getAttachment(uuid, sharePhoto);
                if (access$300 == null) {
                    return null;
                }
                arrayList.add(access$300);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("url", access$300.getAttachmentUrl());
                    if (!sharePhoto.getUserGenerated()) {
                        return jSONObject;
                    }
                    jSONObject.put(NativeProtocol.IMAGE_USER_GENERATED_KEY, true);
                    return jSONObject;
                } catch (JSONException e) {
                    throw new FacebookException("Unable to attach images", (Throwable) e);
                }
            }
        });
        NativeAppCallAttachmentStore.addAttachments(arrayList);
        return jSONObject;
    }

    public static JSONObject toJSONObjectForWeb(ShareOpenGraphContent shareOpenGraphContent) throws JSONException {
        return OpenGraphJSONUtility.toJSONObject(shareOpenGraphContent.getAction(), (OpenGraphJSONUtility.PhotoJSONProcessor) new OpenGraphJSONUtility.PhotoJSONProcessor() {
            public JSONObject toJSONObject(SharePhoto sharePhoto) {
                Uri imageUrl = sharePhoto.getImageUrl();
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("url", imageUrl.toString());
                    return jSONObject;
                } catch (JSONException e) {
                    throw new FacebookException("Unable to attach images", (Throwable) e);
                }
            }
        });
    }
}
