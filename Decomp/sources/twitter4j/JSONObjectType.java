package twitter4j;

import com.facebook.internal.NativeProtocol;

public final class JSONObjectType {
    private static final Logger logger = Logger.getLogger(JSONObjectType.class);

    public enum Type {
        SENDER,
        STATUS,
        DIRECT_MESSAGE,
        DELETE,
        LIMIT,
        STALL_WARNING,
        SCRUB_GEO,
        FRIENDS,
        FAVORITE,
        UNFAVORITE,
        FOLLOW,
        UNFOLLOW,
        USER_LIST_MEMBER_ADDED,
        USER_LIST_MEMBER_DELETED,
        USER_LIST_SUBSCRIBED,
        USER_LIST_UNSUBSCRIBED,
        USER_LIST_CREATED,
        USER_LIST_UPDATED,
        USER_LIST_DESTROYED,
        USER_UPDATE,
        BLOCK,
        UNBLOCK,
        DISCONNECTION,
        UNKNOWN
    }

    public static Type determine(JSONObject jSONObject) {
        if (!jSONObject.isNull("sender")) {
            return Type.SENDER;
        }
        if (!jSONObject.isNull("text")) {
            return Type.STATUS;
        }
        if (!jSONObject.isNull("direct_message")) {
            return Type.DIRECT_MESSAGE;
        }
        if (!jSONObject.isNull("delete")) {
            return Type.DELETE;
        }
        if (!jSONObject.isNull("limit")) {
            return Type.LIMIT;
        }
        if (!jSONObject.isNull("warning")) {
            return Type.STALL_WARNING;
        }
        if (!jSONObject.isNull("scrub_geo")) {
            return Type.SCRUB_GEO;
        }
        if (!jSONObject.isNull(NativeProtocol.AUDIENCE_FRIENDS)) {
            return Type.FRIENDS;
        }
        if (!jSONObject.isNull("event")) {
            try {
                String string = jSONObject.getString("event");
                if ("favorite".equals(string)) {
                    return Type.FAVORITE;
                }
                if ("unfavorite".equals(string)) {
                    return Type.UNFAVORITE;
                }
                if ("follow".equals(string)) {
                    return Type.FOLLOW;
                }
                if ("unfollow".equals(string)) {
                    return Type.UNFOLLOW;
                }
                if (string.startsWith("list")) {
                    if ("list_member_added".equals(string)) {
                        return Type.USER_LIST_MEMBER_ADDED;
                    }
                    if ("list_member_removed".equals(string)) {
                        return Type.USER_LIST_MEMBER_DELETED;
                    }
                    if ("list_user_subscribed".equals(string)) {
                        return Type.USER_LIST_SUBSCRIBED;
                    }
                    if ("list_user_unsubscribed".equals(string)) {
                        return Type.USER_LIST_UNSUBSCRIBED;
                    }
                    if ("list_created".equals(string)) {
                        return Type.USER_LIST_CREATED;
                    }
                    if ("list_updated".equals(string)) {
                        return Type.USER_LIST_UPDATED;
                    }
                    if ("list_destroyed".equals(string)) {
                        return Type.USER_LIST_DESTROYED;
                    }
                } else if ("user_update".equals(string)) {
                    return Type.USER_UPDATE;
                } else {
                    if ("block".equals(string)) {
                        return Type.BLOCK;
                    }
                    if ("unblock".equals(string)) {
                        return Type.UNBLOCK;
                    }
                }
            } catch (JSONException e) {
                try {
                    logger.warn("Failed to get event element: ", jSONObject.toString(2));
                } catch (JSONException e2) {
                }
            }
        } else if (!jSONObject.isNull("disconnect")) {
            return Type.DISCONNECTION;
        }
        return Type.UNKNOWN;
    }
}
