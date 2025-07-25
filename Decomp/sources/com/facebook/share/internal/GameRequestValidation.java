package com.facebook.share.internal;

import com.facebook.internal.Validate;
import com.facebook.share.model.GameRequestContent;

public class GameRequestValidation {
    public static void validate(GameRequestContent gameRequestContent) {
        boolean z = false;
        Validate.notNull(gameRequestContent.getMessage(), "message");
        boolean z2 = gameRequestContent.getObjectId() != null;
        if (gameRequestContent.getActionType() == GameRequestContent.ActionType.ASKFOR || gameRequestContent.getActionType() == GameRequestContent.ActionType.SEND) {
            z = true;
        }
        if (z2 ^ z) {
            throw new IllegalArgumentException("Object id should be provided if and only if action type is send or askfor");
        }
        int i = 0;
        if (gameRequestContent.getTo() != null) {
            i = 0 + 1;
        }
        if (gameRequestContent.getSuggestions() != null) {
            i++;
        }
        if (gameRequestContent.getFilters() != null) {
            i++;
        }
        if (i > 1) {
            throw new IllegalArgumentException("Parameters to, filters and suggestions are mutually exclusive");
        }
    }
}
