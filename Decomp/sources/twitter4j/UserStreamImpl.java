package twitter4j;

import java.io.IOException;
import java.io.InputStream;
import twitter4j.conf.Configuration;

final class UserStreamImpl extends StatusStreamImpl implements UserStream {
    UserStreamImpl(Dispatcher dispatcher, InputStream inputStream, Configuration configuration) throws IOException {
        super(dispatcher, inputStream, configuration);
    }

    UserStreamImpl(Dispatcher dispatcher, HttpResponse httpResponse, Configuration configuration) throws IOException {
        super(dispatcher, httpResponse, configuration);
    }

    public void next(UserStreamListener userStreamListener) throws TwitterException {
        handleNextElement(new StreamListener[]{userStreamListener}, EMPTY);
    }

    /* access modifiers changed from: protected */
    public void onBlock(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        for (UserStreamListener onBlock : streamListenerArr) {
            onBlock.onBlock(asUser(jSONObject), asUser(jSONObject2));
        }
    }

    /* access modifiers changed from: protected */
    public void onDirectMessage(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        DirectMessage asDirectMessage = asDirectMessage(jSONObject);
        for (UserStreamListener onDirectMessage : streamListenerArr) {
            onDirectMessage.onDirectMessage(asDirectMessage);
        }
    }

    /* access modifiers changed from: protected */
    public void onFavorite(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException {
        for (UserStreamListener onFavorite : streamListenerArr) {
            onFavorite.onFavorite(asUser(jSONObject), asUser(jSONObject2), asStatus(jSONObject3));
        }
    }

    /* access modifiers changed from: protected */
    public void onFollow(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        for (UserStreamListener onFollow : streamListenerArr) {
            onFollow.onFollow(asUser(jSONObject), asUser(jSONObject2));
        }
    }

    /* access modifiers changed from: protected */
    public void onFriends(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        long[] asFriendList = asFriendList(jSONObject);
        for (UserStreamListener onFriendList : streamListenerArr) {
            onFriendList.onFriendList(asFriendList);
        }
    }

    /* access modifiers changed from: protected */
    public void onScrubGeo(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException {
        logger.info("Geo-tagging deletion notice (not implemented yet): " + this.line);
    }

    /* access modifiers changed from: protected */
    public void onSender(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException {
        for (UserStreamListener onDirectMessage : streamListenerArr) {
            onDirectMessage.onDirectMessage(new DirectMessageJSONImpl(jSONObject));
        }
    }

    /* access modifiers changed from: protected */
    public void onUnblock(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        for (UserStreamListener onUnblock : streamListenerArr) {
            onUnblock.onUnblock(asUser(jSONObject), asUser(jSONObject2));
        }
    }

    /* access modifiers changed from: protected */
    public void onUnfavorite(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException {
        for (UserStreamListener onUnfavorite : streamListenerArr) {
            onUnfavorite.onUnfavorite(asUser(jSONObject), asUser(jSONObject2), asStatus(jSONObject3));
        }
    }

    /* access modifiers changed from: protected */
    public void onUnfollow(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        for (UserStreamListener onUnfollow : streamListenerArr) {
            onUnfollow.onUnfollow(asUser(jSONObject), asUser(jSONObject2));
        }
    }

    /* access modifiers changed from: protected */
    public void onUserListCreation(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        for (UserStreamListener onUserListCreation : streamListenerArr) {
            onUserListCreation.onUserListCreation(asUser(jSONObject), asUserList(jSONObject2));
        }
    }

    /* access modifiers changed from: protected */
    public void onUserListDestroyed(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        for (UserStreamListener onUserListDeletion : streamListenerArr) {
            onUserListDeletion.onUserListDeletion(asUser(jSONObject), asUserList(jSONObject2));
        }
    }

    /* access modifiers changed from: protected */
    public void onUserListMemberAddition(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        for (UserStreamListener onUserListMemberAddition : streamListenerArr) {
            onUserListMemberAddition.onUserListMemberAddition(asUser(jSONObject), asUser(jSONObject2), asUserList(jSONObject3));
        }
    }

    /* access modifiers changed from: protected */
    public void onUserListMemberDeletion(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        for (UserStreamListener onUserListMemberDeletion : streamListenerArr) {
            onUserListMemberDeletion.onUserListMemberDeletion(asUser(jSONObject), asUser(jSONObject2), asUserList(jSONObject3));
        }
    }

    /* access modifiers changed from: protected */
    public void onUserListSubscription(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        for (UserStreamListener onUserListSubscription : streamListenerArr) {
            onUserListSubscription.onUserListSubscription(asUser(jSONObject), asUser(jSONObject2), asUserList(jSONObject3));
        }
    }

    /* access modifiers changed from: protected */
    public void onUserListUnsubscription(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        for (UserStreamListener onUserListUnsubscription : streamListenerArr) {
            onUserListUnsubscription.onUserListUnsubscription(asUser(jSONObject), asUser(jSONObject2), asUserList(jSONObject3));
        }
    }

    /* access modifiers changed from: protected */
    public void onUserListUpdated(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        for (UserStreamListener onUserListUpdate : streamListenerArr) {
            onUserListUpdate.onUserListUpdate(asUser(jSONObject), asUserList(jSONObject2));
        }
    }

    /* access modifiers changed from: protected */
    public void onUserUpdate(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        for (UserStreamListener onUserProfileUpdate : streamListenerArr) {
            onUserProfileUpdate.onUserProfileUpdate(asUser(jSONObject));
        }
    }
}
