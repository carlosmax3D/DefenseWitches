package com.facebook.share.internal;

import android.graphics.Bitmap;
import android.net.Uri;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.ShareOpenGraphValueContainer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import java.util.List;
import java.util.Locale;

public class ShareContentValidation {
    private static Validator DefaultValidator;
    private static Validator WebShareValidator;

    private static class Validator {
        private boolean isOpenGraphContent;

        private Validator() {
            this.isOpenGraphContent = false;
        }

        public boolean isOpenGraphContent() {
            return this.isOpenGraphContent;
        }

        public void validate(ShareLinkContent shareLinkContent) {
            ShareContentValidation.validateLinkContent(shareLinkContent, this);
        }

        public void validate(ShareOpenGraphAction shareOpenGraphAction) {
            ShareContentValidation.validateOpenGraphAction(shareOpenGraphAction, this);
        }

        public void validate(ShareOpenGraphContent shareOpenGraphContent) {
            this.isOpenGraphContent = true;
            ShareContentValidation.validateOpenGraphContent(shareOpenGraphContent, this);
        }

        public void validate(ShareOpenGraphObject shareOpenGraphObject) {
            ShareContentValidation.validateOpenGraphObject(shareOpenGraphObject, this);
        }

        public void validate(ShareOpenGraphValueContainer shareOpenGraphValueContainer, boolean z) {
            ShareContentValidation.validateOpenGraphValueContainer(shareOpenGraphValueContainer, this, z);
        }

        public void validate(SharePhoto sharePhoto) {
            ShareContentValidation.validatePhotoForNativeDialog(sharePhoto, this);
        }

        public void validate(SharePhotoContent sharePhotoContent) {
            ShareContentValidation.validatePhotoContent(sharePhotoContent, this);
        }

        public void validate(ShareVideo shareVideo) {
            ShareContentValidation.validateVideo(shareVideo, this);
        }

        public void validate(ShareVideoContent shareVideoContent) {
            ShareContentValidation.validateVideoContent(shareVideoContent, this);
        }
    }

    private static class WebShareValidator extends Validator {
        private WebShareValidator() {
            super();
        }

        public void validate(SharePhoto sharePhoto) {
            ShareContentValidation.validatePhotoForWebDialog(sharePhoto, this);
        }

        public void validate(SharePhotoContent sharePhotoContent) {
            throw new FacebookException("Cannot share SharePhotoContent via web sharing dialogs");
        }

        public void validate(ShareVideoContent shareVideoContent) {
            throw new FacebookException("Cannot share ShareVideoContent via web sharing dialogs");
        }
    }

    private static Validator getDefaultValidator() {
        if (DefaultValidator == null) {
            DefaultValidator = new Validator();
        }
        return DefaultValidator;
    }

    private static Validator getWebShareValidator() {
        if (WebShareValidator == null) {
            WebShareValidator = new WebShareValidator();
        }
        return WebShareValidator;
    }

    private static void validate(ShareContent shareContent, Validator validator) throws FacebookException {
        if (shareContent == null) {
            throw new FacebookException("Must provide non-null content to share");
        } else if (shareContent instanceof ShareLinkContent) {
            validator.validate((ShareLinkContent) shareContent);
        } else if (shareContent instanceof SharePhotoContent) {
            validator.validate((SharePhotoContent) shareContent);
        } else if (shareContent instanceof ShareVideoContent) {
            validator.validate((ShareVideoContent) shareContent);
        } else if (shareContent instanceof ShareOpenGraphContent) {
            validator.validate((ShareOpenGraphContent) shareContent);
        }
    }

    public static void validateForApiShare(ShareContent shareContent) {
        validate(shareContent, getDefaultValidator());
    }

    public static void validateForMessage(ShareContent shareContent) {
        validate(shareContent, getDefaultValidator());
    }

    public static void validateForNativeShare(ShareContent shareContent) {
        validate(shareContent, getDefaultValidator());
    }

    public static void validateForWebShare(ShareContent shareContent) {
        validate(shareContent, getWebShareValidator());
    }

    /* access modifiers changed from: private */
    public static void validateLinkContent(ShareLinkContent shareLinkContent, Validator validator) {
        Uri imageUrl = shareLinkContent.getImageUrl();
        if (imageUrl != null && !Utility.isWebUri(imageUrl)) {
            throw new FacebookException("Image Url must be an http:// or https:// url");
        }
    }

    /* access modifiers changed from: private */
    public static void validateOpenGraphAction(ShareOpenGraphAction shareOpenGraphAction, Validator validator) {
        if (shareOpenGraphAction == null) {
            throw new FacebookException("Must specify a non-null ShareOpenGraphAction");
        } else if (Utility.isNullOrEmpty(shareOpenGraphAction.getActionType())) {
            throw new FacebookException("ShareOpenGraphAction must have a non-empty actionType");
        } else {
            validator.validate(shareOpenGraphAction, false);
        }
    }

    /* access modifiers changed from: private */
    public static void validateOpenGraphContent(ShareOpenGraphContent shareOpenGraphContent, Validator validator) {
        validator.validate(shareOpenGraphContent.getAction());
        String previewPropertyName = shareOpenGraphContent.getPreviewPropertyName();
        if (Utility.isNullOrEmpty(previewPropertyName)) {
            throw new FacebookException("Must specify a previewPropertyName.");
        } else if (shareOpenGraphContent.getAction().get(previewPropertyName) == null) {
            throw new FacebookException("Property \"" + previewPropertyName + "\" was not found on the action. " + "The name of the preview property must match the name of an " + "action property.");
        }
    }

    private static void validateOpenGraphKey(String str, boolean z) {
        if (z) {
            String[] split = str.split(":");
            if (split.length < 2) {
                throw new FacebookException("Open Graph keys must be namespaced: %s", str);
            }
            for (String isEmpty : split) {
                if (isEmpty.isEmpty()) {
                    throw new FacebookException("Invalid key found in Open Graph dictionary: %s", str);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static void validateOpenGraphObject(ShareOpenGraphObject shareOpenGraphObject, Validator validator) {
        if (shareOpenGraphObject == null) {
            throw new FacebookException("Cannot share a null ShareOpenGraphObject");
        }
        validator.validate(shareOpenGraphObject, true);
    }

    /* access modifiers changed from: private */
    public static void validateOpenGraphValueContainer(ShareOpenGraphValueContainer shareOpenGraphValueContainer, Validator validator, boolean z) {
        for (String next : shareOpenGraphValueContainer.keySet()) {
            validateOpenGraphKey(next, z);
            Object obj = shareOpenGraphValueContainer.get(next);
            if (obj instanceof List) {
                for (Object next2 : (List) obj) {
                    if (next2 == null) {
                        throw new FacebookException("Cannot put null objects in Lists in ShareOpenGraphObjects and ShareOpenGraphActions");
                    }
                    validateOpenGraphValueContainerObject(next2, validator);
                }
                continue;
            } else {
                validateOpenGraphValueContainerObject(obj, validator);
            }
        }
    }

    private static void validateOpenGraphValueContainerObject(Object obj, Validator validator) {
        if (obj instanceof ShareOpenGraphObject) {
            validator.validate((ShareOpenGraphObject) obj);
        } else if (obj instanceof SharePhoto) {
            validator.validate((SharePhoto) obj);
        }
    }

    /* access modifiers changed from: private */
    public static void validatePhotoContent(SharePhotoContent sharePhotoContent, Validator validator) {
        List<SharePhoto> photos = sharePhotoContent.getPhotos();
        if (photos == null || photos.isEmpty()) {
            throw new FacebookException("Must specify at least one Photo in SharePhotoContent.");
        } else if (photos.size() > 6) {
            throw new FacebookException(String.format(Locale.ROOT, "Cannot add more than %d photos.", new Object[]{6}));
        } else {
            for (SharePhoto validate : photos) {
                validator.validate(validate);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void validatePhotoForNativeDialog(SharePhoto sharePhoto, Validator validator) {
        if (sharePhoto == null) {
            throw new FacebookException("Cannot share a null SharePhoto");
        }
        Bitmap bitmap = sharePhoto.getBitmap();
        Uri imageUrl = sharePhoto.getImageUrl();
        if (bitmap != null) {
            return;
        }
        if (imageUrl == null) {
            throw new FacebookException("SharePhoto does not have a Bitmap or ImageUrl specified");
        } else if (Utility.isWebUri(imageUrl) && !validator.isOpenGraphContent()) {
            throw new FacebookException("Cannot set the ImageUrl of a SharePhoto to the Uri of an image on the web when sharing SharePhotoContent");
        }
    }

    /* access modifiers changed from: private */
    public static void validatePhotoForWebDialog(SharePhoto sharePhoto, Validator validator) {
        if (sharePhoto == null) {
            throw new FacebookException("Cannot share a null SharePhoto");
        }
        Uri imageUrl = sharePhoto.getImageUrl();
        if (imageUrl == null || !Utility.isWebUri(imageUrl)) {
            throw new FacebookException("SharePhoto must have a non-null imageUrl set to the Uri of an image on the web");
        }
    }

    /* access modifiers changed from: private */
    public static void validateVideo(ShareVideo shareVideo, Validator validator) {
        if (shareVideo == null) {
            throw new FacebookException("Cannot share a null ShareVideo");
        } else if (shareVideo.getLocalUrl() == null) {
            throw new FacebookException("ShareVideo does not have a LocalUrl specified");
        }
    }

    /* access modifiers changed from: private */
    public static void validateVideoContent(ShareVideoContent shareVideoContent, Validator validator) {
        validator.validate(shareVideoContent.getVideo());
        SharePhoto previewPhoto = shareVideoContent.getPreviewPhoto();
        if (previewPhoto != null) {
            validator.validate(previewPhoto);
        }
    }
}
