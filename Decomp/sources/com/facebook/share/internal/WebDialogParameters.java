package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.share.model.AppGroupCreationContent;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphContent;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class WebDialogParameters {
    public static Bundle create(AppGroupCreationContent appGroupCreationContent) {
        Bundle bundle = new Bundle();
        Utility.putNonEmptyString(bundle, "name", appGroupCreationContent.getName());
        Utility.putNonEmptyString(bundle, "description", appGroupCreationContent.getDescription());
        Utility.putNonEmptyString(bundle, ShareConstants.WEB_DIALOG_PARAM_PRIVACY, appGroupCreationContent.getAppGroupPrivacy().toString().toLowerCase(Locale.ENGLISH));
        return bundle;
    }

    public static Bundle create(GameRequestContent gameRequestContent) {
        Bundle bundle = new Bundle();
        Utility.putNonEmptyString(bundle, "message", gameRequestContent.getMessage());
        Utility.putNonEmptyString(bundle, ShareConstants.WEB_DIALOG_PARAM_TO, gameRequestContent.getTo());
        Utility.putNonEmptyString(bundle, "title", gameRequestContent.getTitle());
        Utility.putNonEmptyString(bundle, "data", gameRequestContent.getData());
        if (gameRequestContent.getActionType() != null) {
            Utility.putNonEmptyString(bundle, ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE, gameRequestContent.getActionType().toString().toLowerCase(Locale.ENGLISH));
        }
        Utility.putNonEmptyString(bundle, "object_id", gameRequestContent.getObjectId());
        if (gameRequestContent.getFilters() != null) {
            Utility.putNonEmptyString(bundle, ShareConstants.WEB_DIALOG_PARAM_FILTERS, gameRequestContent.getFilters().toString().toLowerCase(Locale.ENGLISH));
        }
        Utility.putCommaSeparatedStringList(bundle, ShareConstants.WEB_DIALOG_PARAM_SUGGESTIONS, gameRequestContent.getSuggestions());
        return bundle;
    }

    public static Bundle create(ShareLinkContent shareLinkContent) {
        Bundle bundle = new Bundle();
        Utility.putUri(bundle, ShareConstants.WEB_DIALOG_PARAM_HREF, shareLinkContent.getContentUrl());
        return bundle;
    }

    public static Bundle create(ShareOpenGraphContent shareOpenGraphContent) {
        Bundle bundle = new Bundle();
        Utility.putNonEmptyString(bundle, ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE, shareOpenGraphContent.getAction().getActionType());
        try {
            JSONObject removeNamespacesFromOGJsonObject = ShareInternalUtility.removeNamespacesFromOGJsonObject(ShareInternalUtility.toJSONObjectForWeb(shareOpenGraphContent), false);
            if (removeNamespacesFromOGJsonObject != null) {
                Utility.putNonEmptyString(bundle, ShareConstants.WEB_DIALOG_PARAM_ACTION_PROPERTIES, removeNamespacesFromOGJsonObject.toString());
            }
            return bundle;
        } catch (JSONException e) {
            throw new FacebookException("Unable to serialize the ShareOpenGraphContent to JSON", (Throwable) e);
        }
    }

    public static Bundle createForFeed(ShareLinkContent shareLinkContent) {
        Bundle bundle = new Bundle();
        Utility.putNonEmptyString(bundle, "name", shareLinkContent.getContentTitle());
        Utility.putNonEmptyString(bundle, "description", shareLinkContent.getContentDescription());
        Utility.putNonEmptyString(bundle, ShareConstants.WEB_DIALOG_PARAM_LINK, Utility.getUriString(shareLinkContent.getContentUrl()));
        Utility.putNonEmptyString(bundle, ShareConstants.WEB_DIALOG_PARAM_PICTURE, Utility.getUriString(shareLinkContent.getImageUrl()));
        return bundle;
    }
}
