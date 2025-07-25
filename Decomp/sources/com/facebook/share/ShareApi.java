package com.facebook.share;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.internal.CollectionMapper;
import com.facebook.internal.Mutable;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareConstants;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ShareApi {
    private final ShareContent shareContent;

    public ShareApi(ShareContent shareContent2) {
        this.shareContent = shareContent2;
    }

    /* access modifiers changed from: private */
    public static void handleImagesOnAction(Bundle bundle) {
        String string = bundle.getString("image");
        if (string != null) {
            try {
                JSONArray jSONArray = new JSONArray(string);
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject optJSONObject = jSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        putImageInBundleWithArrayFormat(bundle, i, optJSONObject);
                    } else {
                        bundle.putString(String.format(Locale.ROOT, "image[%d][url]", new Object[]{Integer.valueOf(i)}), jSONArray.getString(i));
                    }
                }
                bundle.remove("image");
            } catch (JSONException e) {
                try {
                    putImageInBundleWithArrayFormat(bundle, 0, new JSONObject(string));
                    bundle.remove("image");
                } catch (JSONException e2) {
                }
            }
        }
    }

    private static void putImageInBundleWithArrayFormat(Bundle bundle, int i, JSONObject jSONObject) throws JSONException {
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            bundle.putString(String.format(Locale.ROOT, "image[%d][%s]", new Object[]{Integer.valueOf(i), next}), jSONObject.get(next).toString());
        }
    }

    public static void share(ShareContent shareContent2, FacebookCallback<Sharer.Result> facebookCallback) {
        new ShareApi(shareContent2).share(facebookCallback);
    }

    private void shareLinkContent(ShareLinkContent shareLinkContent, final FacebookCallback<Sharer.Result> facebookCallback) {
        AnonymousClass4 r5 = new GraphRequest.Callback() {
            public void onCompleted(GraphResponse graphResponse) {
                JSONObject jSONObject = graphResponse.getJSONObject();
                ShareInternalUtility.invokeCallbackWithResults(facebookCallback, jSONObject == null ? null : jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_ID), graphResponse);
            }
        };
        Bundle bundle = new Bundle();
        bundle.putString(ShareConstants.WEB_DIALOG_PARAM_LINK, Utility.getUriString(shareLinkContent.getContentUrl()));
        bundle.putString(ShareConstants.WEB_DIALOG_PARAM_PICTURE, Utility.getUriString(shareLinkContent.getImageUrl()));
        bundle.putString("name", shareLinkContent.getContentTitle());
        bundle.putString("description", shareLinkContent.getContentDescription());
        bundle.putString("ref", shareLinkContent.getRef());
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/feed", bundle, HttpMethod.POST, r5).executeAsync();
    }

    private void shareOpenGraphContent(ShareOpenGraphContent shareOpenGraphContent, final FacebookCallback<Sharer.Result> facebookCallback) {
        final AnonymousClass1 r4 = new GraphRequest.Callback() {
            public void onCompleted(GraphResponse graphResponse) {
                JSONObject jSONObject = graphResponse.getJSONObject();
                ShareInternalUtility.invokeCallbackWithResults(facebookCallback, jSONObject == null ? null : jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_ID), graphResponse);
            }
        };
        final ShareOpenGraphAction action = shareOpenGraphContent.getAction();
        final Bundle bundle = action.getBundle();
        final FacebookCallback<Sharer.Result> facebookCallback2 = facebookCallback;
        stageOpenGraphAction(bundle, new CollectionMapper.OnMapperCompleteListener() {
            public void onComplete() {
                try {
                    ShareApi.handleImagesOnAction(bundle);
                    new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/" + URLEncoder.encode(action.getActionType(), "UTF-8"), bundle, HttpMethod.POST, r4).executeAsync();
                } catch (UnsupportedEncodingException e) {
                    ShareInternalUtility.invokeCallbackWithException(facebookCallback2, e);
                }
            }

            public void onError(FacebookException facebookException) {
                ShareInternalUtility.invokeCallbackWithException(facebookCallback2, facebookException);
            }
        });
    }

    private void sharePhotoContent(SharePhotoContent sharePhotoContent, FacebookCallback<Sharer.Result> facebookCallback) {
        final Mutable mutable = new Mutable(0);
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList();
        final FacebookCallback<Sharer.Result> facebookCallback2 = facebookCallback;
        AnonymousClass3 r0 = new GraphRequest.Callback() {
            public void onCompleted(GraphResponse graphResponse) {
                JSONObject jSONObject = graphResponse.getJSONObject();
                if (jSONObject != null) {
                    arrayList2.add(jSONObject);
                }
                if (graphResponse.getError() != null) {
                    arrayList3.add(graphResponse);
                }
                mutable.value = Integer.valueOf(((Integer) mutable.value).intValue() - 1);
                if (((Integer) mutable.value).intValue() != 0) {
                    return;
                }
                if (!arrayList3.isEmpty()) {
                    ShareInternalUtility.invokeCallbackWithResults(facebookCallback2, (String) null, (GraphResponse) arrayList3.get(0));
                } else if (!arrayList2.isEmpty()) {
                    ShareInternalUtility.invokeCallbackWithResults(facebookCallback2, ((JSONObject) arrayList2.get(0)).optString(ShareConstants.WEB_DIALOG_PARAM_ID), graphResponse);
                }
            }
        };
        try {
            for (SharePhoto next : sharePhotoContent.getPhotos()) {
                Bitmap bitmap = next.getBitmap();
                Uri imageUrl = next.getImageUrl();
                if (bitmap != null) {
                    arrayList.add(ShareInternalUtility.newUploadPhotoRequest(currentAccessToken, bitmap, (GraphRequest.Callback) r0));
                } else if (imageUrl != null) {
                    arrayList.add(ShareInternalUtility.newUploadPhotoRequest(currentAccessToken, imageUrl, (GraphRequest.Callback) r0));
                }
            }
            mutable.value = Integer.valueOf(((Integer) mutable.value).intValue() + arrayList.size());
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((GraphRequest) it.next()).executeAsync();
            }
        } catch (FileNotFoundException e) {
            ShareInternalUtility.invokeCallbackWithException(facebookCallback, e);
        }
    }

    private void shareVideoContent(ShareVideoContent shareVideoContent, final FacebookCallback<Sharer.Result> facebookCallback) {
        try {
            GraphRequest newUploadVideoRequest = ShareInternalUtility.newUploadVideoRequest(AccessToken.getCurrentAccessToken(), shareVideoContent.getVideo().getLocalUrl(), (GraphRequest.Callback) new GraphRequest.Callback() {
                public void onCompleted(GraphResponse graphResponse) {
                    JSONObject jSONObject;
                    String str = null;
                    if (!(graphResponse == null || (jSONObject = graphResponse.getJSONObject()) == null)) {
                        str = jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_ID);
                    }
                    ShareInternalUtility.invokeCallbackWithResults(facebookCallback, str, graphResponse);
                }
            });
            Bundle parameters = newUploadVideoRequest.getParameters();
            parameters.putString("title", shareVideoContent.getContentTitle());
            parameters.putString("description", shareVideoContent.getContentDescription());
            parameters.putString("ref", shareVideoContent.getRef());
            newUploadVideoRequest.setParameters(parameters);
            newUploadVideoRequest.executeAsync();
        } catch (FileNotFoundException e) {
            ShareInternalUtility.invokeCallbackWithException(facebookCallback, e);
        }
    }

    /* access modifiers changed from: private */
    public static void stageArrayList(final ArrayList arrayList, final CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
        final JSONArray jSONArray = new JSONArray();
        stageCollectionValues(new CollectionMapper.Collection<Integer>() {
            public Object get(Integer num) {
                return arrayList.get(num.intValue());
            }

            public Iterator<Integer> keyIterator() {
                final int size = arrayList.size();
                final Mutable mutable = new Mutable(0);
                return new Iterator<Integer>() {
                    public boolean hasNext() {
                        return ((Integer) mutable.value).intValue() < size;
                    }

                    public Integer next() {
                        Integer num = (Integer) mutable.value;
                        Mutable mutable = mutable;
                        mutable.value = Integer.valueOf(((Integer) mutable.value).intValue() + 1);
                        return num;
                    }

                    public void remove() {
                    }
                };
            }

            public void set(Integer num, Object obj, CollectionMapper.OnErrorListener onErrorListener) {
                try {
                    jSONArray.put(num.intValue(), obj);
                } catch (JSONException e) {
                    String localizedMessage = e.getLocalizedMessage();
                    if (localizedMessage == null) {
                        localizedMessage = "Error staging object.";
                    }
                    onErrorListener.onError(new FacebookException(localizedMessage));
                }
            }
        }, new CollectionMapper.OnMapperCompleteListener() {
            public void onComplete() {
                onMapValueCompleteListener.onComplete(jSONArray);
            }

            public void onError(FacebookException facebookException) {
                onMapValueCompleteListener.onError(facebookException);
            }
        });
    }

    private static <T> void stageCollectionValues(CollectionMapper.Collection<T> collection, CollectionMapper.OnMapperCompleteListener onMapperCompleteListener) {
        CollectionMapper.iterate(collection, new CollectionMapper.ValueMapper() {
            public void mapValue(Object obj, CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
                if (obj instanceof ArrayList) {
                    ShareApi.stageArrayList((ArrayList) obj, onMapValueCompleteListener);
                } else if (obj instanceof ShareOpenGraphObject) {
                    ShareApi.stageOpenGraphObject((ShareOpenGraphObject) obj, onMapValueCompleteListener);
                } else if (obj instanceof SharePhoto) {
                    ShareApi.stagePhoto((SharePhoto) obj, onMapValueCompleteListener);
                } else {
                    onMapValueCompleteListener.onComplete(obj);
                }
            }
        }, onMapperCompleteListener);
    }

    private static void stageOpenGraphAction(final Bundle bundle, CollectionMapper.OnMapperCompleteListener onMapperCompleteListener) {
        stageCollectionValues(new CollectionMapper.Collection<String>() {
            public Object get(String str) {
                return bundle.get(str);
            }

            public Iterator<String> keyIterator() {
                return bundle.keySet().iterator();
            }

            public void set(String str, Object obj, CollectionMapper.OnErrorListener onErrorListener) {
                if (!Utility.putJSONValueInBundle(bundle, str, obj)) {
                    onErrorListener.onError(new FacebookException("Unexpected value: " + obj.toString()));
                }
            }
        }, onMapperCompleteListener);
    }

    /* access modifiers changed from: private */
    public static void stageOpenGraphObject(final ShareOpenGraphObject shareOpenGraphObject, final CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
        String string = shareOpenGraphObject.getString("type");
        if (string == null) {
            string = shareOpenGraphObject.getString("og:type");
        }
        if (string == null) {
            onMapValueCompleteListener.onError(new FacebookException("Open Graph objects must contain a type value."));
            return;
        }
        final JSONObject jSONObject = new JSONObject();
        AnonymousClass10 r0 = new CollectionMapper.Collection<String>() {
            public Object get(String str) {
                return shareOpenGraphObject.get(str);
            }

            public Iterator<String> keyIterator() {
                return shareOpenGraphObject.keySet().iterator();
            }

            public void set(String str, Object obj, CollectionMapper.OnErrorListener onErrorListener) {
                try {
                    jSONObject.put(str, obj);
                } catch (JSONException e) {
                    String localizedMessage = e.getLocalizedMessage();
                    if (localizedMessage == null) {
                        localizedMessage = "Error staging object.";
                    }
                    onErrorListener.onError(new FacebookException(localizedMessage));
                }
            }
        };
        final AnonymousClass11 r3 = new GraphRequest.Callback() {
            public void onCompleted(GraphResponse graphResponse) {
                FacebookRequestError error = graphResponse.getError();
                if (error != null) {
                    String errorMessage = error.getErrorMessage();
                    if (errorMessage == null) {
                        errorMessage = "Error staging Open Graph object.";
                    }
                    onMapValueCompleteListener.onError(new FacebookGraphResponseException(graphResponse, errorMessage));
                    return;
                }
                JSONObject jSONObject = graphResponse.getJSONObject();
                if (jSONObject == null) {
                    onMapValueCompleteListener.onError(new FacebookGraphResponseException(graphResponse, "Error staging Open Graph object."));
                    return;
                }
                String optString = jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_ID);
                if (optString == null) {
                    onMapValueCompleteListener.onError(new FacebookGraphResponseException(graphResponse, "Error staging Open Graph object."));
                } else {
                    onMapValueCompleteListener.onComplete(optString);
                }
            }
        };
        final String str = string;
        stageCollectionValues(r0, new CollectionMapper.OnMapperCompleteListener() {
            public void onComplete() {
                String jSONObject = jSONObject.toString();
                Bundle bundle = new Bundle();
                bundle.putString("object", jSONObject);
                try {
                    new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/objects/" + URLEncoder.encode(str, "UTF-8"), bundle, HttpMethod.POST, r3).executeAsync();
                } catch (UnsupportedEncodingException e) {
                    String localizedMessage = e.getLocalizedMessage();
                    if (localizedMessage == null) {
                        localizedMessage = "Error staging Open Graph object.";
                    }
                    onMapValueCompleteListener.onError(new FacebookException(localizedMessage));
                }
            }

            public void onError(FacebookException facebookException) {
                onMapValueCompleteListener.onError(facebookException);
            }
        });
    }

    /* access modifiers changed from: private */
    public static void stagePhoto(final SharePhoto sharePhoto, final CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
        Bitmap bitmap = sharePhoto.getBitmap();
        Uri imageUrl = sharePhoto.getImageUrl();
        if (bitmap == null && imageUrl == null) {
            onMapValueCompleteListener.onError(new FacebookException("Photos must have an imageURL or bitmap."));
            return;
        }
        AnonymousClass13 r4 = new GraphRequest.Callback() {
            public void onCompleted(GraphResponse graphResponse) {
                FacebookRequestError error = graphResponse.getError();
                if (error != null) {
                    String errorMessage = error.getErrorMessage();
                    if (errorMessage == null) {
                        errorMessage = "Error staging photo.";
                    }
                    onMapValueCompleteListener.onError(new FacebookGraphResponseException(graphResponse, errorMessage));
                    return;
                }
                JSONObject jSONObject = graphResponse.getJSONObject();
                if (jSONObject == null) {
                    onMapValueCompleteListener.onError(new FacebookException("Error staging photo."));
                    return;
                }
                String optString = jSONObject.optString("uri");
                if (optString == null) {
                    onMapValueCompleteListener.onError(new FacebookException("Error staging photo."));
                    return;
                }
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("url", optString);
                    jSONObject2.put(NativeProtocol.IMAGE_USER_GENERATED_KEY, sharePhoto.getUserGenerated());
                    onMapValueCompleteListener.onComplete(jSONObject2);
                } catch (JSONException e) {
                    String localizedMessage = e.getLocalizedMessage();
                    if (localizedMessage == null) {
                        localizedMessage = "Error staging photo.";
                    }
                    onMapValueCompleteListener.onError(new FacebookException(localizedMessage));
                }
            }
        };
        if (bitmap != null) {
            ShareInternalUtility.newUploadStagingResourceWithImageRequest(AccessToken.getCurrentAccessToken(), bitmap, (GraphRequest.Callback) r4).executeAsync();
            return;
        }
        try {
            ShareInternalUtility.newUploadStagingResourceWithImageRequest(AccessToken.getCurrentAccessToken(), imageUrl, (GraphRequest.Callback) r4).executeAsync();
        } catch (FileNotFoundException e) {
            String localizedMessage = e.getLocalizedMessage();
            if (localizedMessage == null) {
                localizedMessage = "Error staging photo.";
            }
            onMapValueCompleteListener.onError(new FacebookException(localizedMessage));
        }
    }

    public boolean canShare() {
        AccessToken currentAccessToken;
        Set<String> permissions;
        if (getShareContent() == null || (currentAccessToken = AccessToken.getCurrentAccessToken()) == null || (permissions = currentAccessToken.getPermissions()) == null) {
            return false;
        }
        return permissions.contains("publish_actions");
    }

    public ShareContent getShareContent() {
        return this.shareContent;
    }

    public void share(FacebookCallback<Sharer.Result> facebookCallback) {
        if (!canShare()) {
            ShareInternalUtility.invokeCallbackWithError(facebookCallback, "Insufficient permissions for sharing content via Api.");
            return;
        }
        ShareContent shareContent2 = getShareContent();
        try {
            ShareContentValidation.validateForApiShare(shareContent2);
            if (shareContent2 instanceof ShareLinkContent) {
                shareLinkContent((ShareLinkContent) shareContent2, facebookCallback);
            } else if (shareContent2 instanceof SharePhotoContent) {
                sharePhotoContent((SharePhotoContent) shareContent2, facebookCallback);
            } else if (shareContent2 instanceof ShareVideoContent) {
                shareVideoContent((ShareVideoContent) shareContent2, facebookCallback);
            } else if (shareContent2 instanceof ShareOpenGraphContent) {
                shareOpenGraphContent((ShareOpenGraphContent) shareContent2, facebookCallback);
            }
        } catch (FacebookException e) {
            ShareInternalUtility.invokeCallbackWithException(facebookCallback, e);
        }
    }
}
