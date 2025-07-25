package twitter4j;

import com.facebook.share.internal.ShareConstants;
import java.io.IOException;
import java.io.InputStream;
import jp.stargarage.g2metrics.BuildConfig;
import twitter4j.conf.Configuration;

final class SiteStreamsImpl extends StatusStreamBase {
    protected static final RawStreamListener[] EMPTY = new RawStreamListener[0];
    private static final ThreadLocal<Long> forUser = new ThreadLocal<Long>() {
        /* access modifiers changed from: protected */
        public Long initialValue() {
            return 0L;
        }
    };
    private final StreamController cs;

    SiteStreamsImpl(Dispatcher dispatcher, InputStream inputStream, Configuration configuration, StreamController streamController) throws IOException {
        super(dispatcher, inputStream, configuration);
        this.cs = streamController;
    }

    SiteStreamsImpl(Dispatcher dispatcher, HttpResponse httpResponse, Configuration configuration, StreamController streamController) throws IOException {
        super(dispatcher, httpResponse, configuration);
        this.cs = streamController;
    }

    public void next(StatusListener statusListener) throws TwitterException {
        handleNextElement(new StatusListener[]{statusListener}, EMPTY);
    }

    public void next(StreamListener[] streamListenerArr, RawStreamListener[] rawStreamListenerArr) throws TwitterException {
        handleNextElement(streamListenerArr, rawStreamListenerArr);
    }

    /* access modifiers changed from: protected */
    public void onBlock(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        for (SiteStreamsListener onBlock : streamListenerArr) {
            onBlock.onBlock(forUser.get().longValue(), asUser(jSONObject), asUser(jSONObject2));
        }
    }

    /* access modifiers changed from: protected */
    public void onClose() {
        this.cs.setControlURI((String) null);
    }

    /* access modifiers changed from: protected */
    public void onDelete(JSONObject jSONObject, StreamListener[] streamListenerArr) throws JSONException {
        JSONObject jSONObject2 = jSONObject.getJSONObject("delete");
        if (jSONObject2.has("status")) {
            for (SiteStreamsListener onDeletionNotice : streamListenerArr) {
                onDeletionNotice.onDeletionNotice(forUser.get().longValue(), new StatusDeletionNoticeImpl(jSONObject2.getJSONObject("status")));
            }
            return;
        }
        JSONObject jSONObject3 = jSONObject2.getJSONObject("direct_message");
        for (SiteStreamsListener onDeletionNotice2 : streamListenerArr) {
            onDeletionNotice2.onDeletionNotice(forUser.get().longValue(), (long) ParseUtil.getInt(ShareConstants.WEB_DIALOG_PARAM_ID, jSONObject3), ParseUtil.getLong("user_id", jSONObject3));
        }
    }

    /* access modifiers changed from: protected */
    public void onDirectMessage(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException {
        for (SiteStreamsListener onDirectMessage : streamListenerArr) {
            onDirectMessage.onDirectMessage(forUser.get().longValue(), asDirectMessage(jSONObject));
        }
    }

    public void onDisconnectionNotice(String str, StreamListener[] streamListenerArr) {
        for (SiteStreamsListener onDisconnectionNotice : streamListenerArr) {
            onDisconnectionNotice.onDisconnectionNotice(str);
        }
    }

    public void onException(Exception exc, StreamListener[] streamListenerArr) {
        for (StreamListener onException : streamListenerArr) {
            onException.onException(exc);
        }
    }

    /* access modifiers changed from: protected */
    public void onFavorite(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException {
        for (SiteStreamsListener onFavorite : streamListenerArr) {
            onFavorite.onFavorite(forUser.get().longValue(), asUser(jSONObject), asUser(jSONObject2), asStatus(jSONObject3));
        }
    }

    /* access modifiers changed from: protected */
    public void onFollow(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        for (SiteStreamsListener onFollow : streamListenerArr) {
            onFollow.onFollow(forUser.get().longValue(), asUser(jSONObject), asUser(jSONObject2));
        }
    }

    /* access modifiers changed from: protected */
    public void onFriends(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        for (SiteStreamsListener onFriendList : streamListenerArr) {
            onFriendList.onFriendList(forUser.get().longValue(), asFriendList(jSONObject));
        }
    }

    /* access modifiers changed from: protected */
    public void onMessage(String str, RawStreamListener[] rawStreamListenerArr) throws TwitterException {
        for (RawStreamListener onMessage : rawStreamListenerArr) {
            onMessage.onMessage(str);
        }
    }

    /* access modifiers changed from: protected */
    public void onStatus(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException {
        for (SiteStreamsListener onStatus : streamListenerArr) {
            onStatus.onStatus(forUser.get().longValue(), asStatus(jSONObject));
        }
    }

    /* access modifiers changed from: protected */
    public void onUnblock(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        for (SiteStreamsListener onUnblock : streamListenerArr) {
            onUnblock.onUnblock(forUser.get().longValue(), asUser(jSONObject), asUser(jSONObject2));
        }
    }

    /* access modifiers changed from: protected */
    public void onUnfavorite(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException {
        for (SiteStreamsListener onUnfavorite : streamListenerArr) {
            onUnfavorite.onUnfavorite(forUser.get().longValue(), asUser(jSONObject), asUser(jSONObject2), asStatus(jSONObject3));
        }
    }

    /* access modifiers changed from: protected */
    public void onUnfollow(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        for (SiteStreamsListener onUnfollow : streamListenerArr) {
            onUnfollow.onUnfollow(forUser.get().longValue(), asUser(jSONObject), asUser(jSONObject2));
        }
    }

    /* access modifiers changed from: protected */
    public void onUserListCreation(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        for (SiteStreamsListener onUserListCreation : streamListenerArr) {
            onUserListCreation.onUserListCreation(forUser.get().longValue(), asUser(jSONObject), asUserList(jSONObject2));
        }
    }

    /* access modifiers changed from: protected */
    public void onUserListDestroyed(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        for (SiteStreamsListener onUserListDeletion : streamListenerArr) {
            onUserListDeletion.onUserListDeletion(forUser.get().longValue(), asUser(jSONObject), asUserList(jSONObject2));
        }
    }

    /* access modifiers changed from: protected */
    public void onUserListMemberAddition(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        for (SiteStreamsListener onUserListMemberAddition : streamListenerArr) {
            onUserListMemberAddition.onUserListMemberAddition(forUser.get().longValue(), asUser(jSONObject), asUser(jSONObject2), asUserList(jSONObject3));
        }
    }

    /* access modifiers changed from: protected */
    public void onUserListMemberDeletion(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        for (SiteStreamsListener onUserListMemberDeletion : streamListenerArr) {
            onUserListMemberDeletion.onUserListMemberDeletion(forUser.get().longValue(), asUser(jSONObject), asUser(jSONObject2), asUserList(jSONObject3));
        }
    }

    /* access modifiers changed from: protected */
    public void onUserListSubscription(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        for (SiteStreamsListener onUserListSubscription : streamListenerArr) {
            onUserListSubscription.onUserListSubscription(forUser.get().longValue(), asUser(jSONObject), asUser(jSONObject2), asUserList(jSONObject3));
        }
    }

    /* access modifiers changed from: protected */
    public void onUserListUnsubscription(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        for (SiteStreamsListener onUserListUnsubscription : streamListenerArr) {
            onUserListUnsubscription.onUserListUnsubscription(forUser.get().longValue(), asUser(jSONObject), asUser(jSONObject2), asUserList(jSONObject3));
        }
    }

    /* access modifiers changed from: protected */
    public void onUserListUpdated(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        for (SiteStreamsListener onUserListUpdate : streamListenerArr) {
            onUserListUpdate.onUserListUpdate(forUser.get().longValue(), asUser(jSONObject), asUserList(jSONObject2));
        }
    }

    /* access modifiers changed from: protected */
    public void onUserUpdate(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        for (SiteStreamsListener onUserProfileUpdate : streamListenerArr) {
            onUserProfileUpdate.onUserProfileUpdate(forUser.get().longValue(), asUser(jSONObject));
        }
    }

    /* access modifiers changed from: protected */
    public String parseLine(String str) {
        if (BuildConfig.FLAVOR.equals(str) || str == null) {
            return str;
        }
        int indexOf = str.indexOf(44, 12);
        if (this.cs.getControlURI() == null && str.charAt(2) == 'c' && str.charAt(3) == 'o' && str.charAt(4) == 'n') {
            try {
                JSONObject jSONObject = new JSONObject(str);
                try {
                    this.cs.setControlURI(this.CONF.getSiteStreamBaseURL() + jSONObject.getJSONObject("control").getString("control_uri"));
                    logger.info("control_uri: " + this.cs.getControlURI());
                    JSONObject jSONObject2 = jSONObject;
                } catch (JSONException e) {
                    JSONObject jSONObject3 = jSONObject;
                    logger.warn("received unexpected event:" + str);
                    return null;
                }
            } catch (JSONException e2) {
                logger.warn("received unexpected event:" + str);
                return null;
            }
            return null;
        } else if (str.charAt(2) == 'd') {
            return str;
        } else {
            if (str.charAt(12) == '\"') {
                forUser.set(Long.valueOf(Long.parseLong(str.substring(13, indexOf - 1))));
            } else {
                forUser.set(Long.valueOf(Long.parseLong(str.substring(12, indexOf))));
            }
            return str.substring(indexOf + 11, str.length() - 1);
        }
    }
}
