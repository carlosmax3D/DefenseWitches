package twitter4j;

import android.support.v4.util.TimeUtils;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.NativeProtocol;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import twitter4j.JSONObjectType;
import twitter4j.conf.Configuration;

abstract class StatusStreamBase implements StatusStream {
    static final Logger logger = Logger.getLogger(StatusStreamImpl.class);
    final Configuration CONF;
    private BufferedReader br;
    private final Dispatcher dispatcher;
    private ObjectFactory factory;
    private InputStream is;
    private HttpResponse response;
    private boolean streamAlive;

    /* renamed from: twitter4j.StatusStreamBase$2  reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$twitter4j$JSONObjectType$Type = new int[JSONObjectType.Type.values().length];

        static {
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.SENDER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.STATUS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.DIRECT_MESSAGE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.DELETE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.LIMIT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.STALL_WARNING.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.SCRUB_GEO.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.FRIENDS.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.FAVORITE.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.UNFAVORITE.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.FOLLOW.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.UNFOLLOW.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.USER_LIST_MEMBER_ADDED.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.USER_LIST_MEMBER_DELETED.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.USER_LIST_SUBSCRIBED.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.USER_LIST_UNSUBSCRIBED.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.USER_LIST_CREATED.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.USER_LIST_UPDATED.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.USER_LIST_DESTROYED.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.USER_UPDATE.ordinal()] = 20;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.BLOCK.ordinal()] = 21;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.UNBLOCK.ordinal()] = 22;
            } catch (NoSuchFieldError e22) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.DISCONNECTION.ordinal()] = 23;
            } catch (NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$twitter4j$JSONObjectType$Type[JSONObjectType.Type.UNKNOWN.ordinal()] = 24;
            } catch (NoSuchFieldError e24) {
            }
        }
    }

    abstract class StreamEvent implements Runnable {
        String line;

        StreamEvent(String str) {
            this.line = str;
        }
    }

    StatusStreamBase(Dispatcher dispatcher2, InputStream inputStream, Configuration configuration) throws IOException {
        this.streamAlive = true;
        this.is = inputStream;
        this.br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        this.dispatcher = dispatcher2;
        this.CONF = configuration;
        this.factory = new JSONImplFactory(configuration);
    }

    StatusStreamBase(Dispatcher dispatcher2, HttpResponse httpResponse, Configuration configuration) throws IOException {
        this(dispatcher2, httpResponse.asStream(), configuration);
        this.response = httpResponse;
    }

    /* access modifiers changed from: package-private */
    public DirectMessage asDirectMessage(JSONObject jSONObject) throws TwitterException {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("direct_message");
            DirectMessageJSONImpl directMessageJSONImpl = new DirectMessageJSONImpl(jSONObject2);
            if (this.CONF.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(directMessageJSONImpl, jSONObject2);
            }
            return directMessageJSONImpl;
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    /* access modifiers changed from: package-private */
    public long[] asFriendList(JSONObject jSONObject) throws TwitterException {
        try {
            JSONArray jSONArray = jSONObject.getJSONArray(NativeProtocol.AUDIENCE_FRIENDS);
            long[] jArr = new long[jSONArray.length()];
            for (int i = 0; i < jArr.length; i++) {
                jArr[i] = Long.parseLong(jSONArray.getString(i));
            }
            return jArr;
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    /* access modifiers changed from: package-private */
    public Status asStatus(JSONObject jSONObject) throws TwitterException {
        StatusJSONImpl statusJSONImpl = new StatusJSONImpl(jSONObject);
        if (this.CONF.isJSONStoreEnabled()) {
            TwitterObjectFactory.registerJSONObject(statusJSONImpl, jSONObject);
        }
        return statusJSONImpl;
    }

    /* access modifiers changed from: package-private */
    public User asUser(JSONObject jSONObject) throws TwitterException {
        UserJSONImpl userJSONImpl = new UserJSONImpl(jSONObject);
        if (this.CONF.isJSONStoreEnabled()) {
            TwitterObjectFactory.registerJSONObject(userJSONImpl, jSONObject);
        }
        return userJSONImpl;
    }

    /* access modifiers changed from: package-private */
    public UserList asUserList(JSONObject jSONObject) throws TwitterException {
        UserListJSONImpl userListJSONImpl = new UserListJSONImpl(jSONObject);
        if (this.CONF.isJSONStoreEnabled()) {
            TwitterObjectFactory.registerJSONObject(userListJSONImpl, jSONObject);
        }
        return userListJSONImpl;
    }

    public void close() throws IOException {
        this.streamAlive = false;
        this.is.close();
        this.br.close();
        if (this.response != null) {
            this.response.disconnect();
        }
        onClose();
    }

    /* access modifiers changed from: package-private */
    public void handleNextElement(final StreamListener[] streamListenerArr, final RawStreamListener[] rawStreamListenerArr) throws TwitterException {
        if (!this.streamAlive) {
            throw new IllegalStateException("Stream already closed.");
        }
        try {
            String readLine = this.br.readLine();
            if (readLine == null) {
                throw new IOException("the end of the stream has been reached");
            }
            this.dispatcher.invokeLater(new StreamEvent(readLine) {
                public void run() {
                    try {
                        if (rawStreamListenerArr.length > 0) {
                            StatusStreamBase.this.onMessage(this.line, rawStreamListenerArr);
                        }
                        this.line = StatusStreamBase.this.parseLine(this.line);
                        if (this.line != null && this.line.length() > 0 && streamListenerArr.length > 0) {
                            if (StatusStreamBase.this.CONF.isJSONStoreEnabled()) {
                                TwitterObjectFactory.clearThreadLocalMap();
                            }
                            JSONObject jSONObject = new JSONObject(this.line);
                            JSONObjectType.Type determine = JSONObjectType.determine(jSONObject);
                            if (StatusStreamBase.logger.isDebugEnabled()) {
                                StatusStreamBase.logger.debug("Received:", StatusStreamBase.this.CONF.getHttpClientConfiguration().isPrettyDebugEnabled() ? jSONObject.toString(1) : jSONObject.toString());
                            }
                            switch (AnonymousClass2.$SwitchMap$twitter4j$JSONObjectType$Type[determine.ordinal()]) {
                                case 1:
                                    StatusStreamBase.this.onSender(jSONObject, streamListenerArr);
                                    return;
                                case 2:
                                    StatusStreamBase.this.onStatus(jSONObject, streamListenerArr);
                                    return;
                                case 3:
                                    StatusStreamBase.this.onDirectMessage(jSONObject, streamListenerArr);
                                    return;
                                case 4:
                                    StatusStreamBase.this.onDelete(jSONObject, streamListenerArr);
                                    return;
                                case 5:
                                    StatusStreamBase.this.onLimit(jSONObject, streamListenerArr);
                                    return;
                                case 6:
                                    StatusStreamBase.this.onStallWarning(jSONObject, streamListenerArr);
                                    return;
                                case 7:
                                    StatusStreamBase.this.onScrubGeo(jSONObject, streamListenerArr);
                                    return;
                                case 8:
                                    StatusStreamBase.this.onFriends(jSONObject, streamListenerArr);
                                    return;
                                case 9:
                                    StatusStreamBase.this.onFavorite(jSONObject.getJSONObject("source"), jSONObject.getJSONObject("target"), jSONObject.getJSONObject("target_object"), streamListenerArr);
                                    return;
                                case 10:
                                    StatusStreamBase.this.onUnfavorite(jSONObject.getJSONObject("source"), jSONObject.getJSONObject("target"), jSONObject.getJSONObject("target_object"), streamListenerArr);
                                    return;
                                case 11:
                                    StatusStreamBase.this.onFollow(jSONObject.getJSONObject("source"), jSONObject.getJSONObject("target"), streamListenerArr);
                                    return;
                                case 12:
                                    StatusStreamBase.this.onUnfollow(jSONObject.getJSONObject("source"), jSONObject.getJSONObject("target"), streamListenerArr);
                                    return;
                                case 13:
                                    StatusStreamBase.this.onUserListMemberAddition(jSONObject.getJSONObject("target"), jSONObject.getJSONObject("source"), jSONObject.getJSONObject("target_object"), streamListenerArr);
                                    return;
                                case 14:
                                    StatusStreamBase.this.onUserListMemberDeletion(jSONObject.getJSONObject("target"), jSONObject.getJSONObject("source"), jSONObject.getJSONObject("target_object"), streamListenerArr);
                                    return;
                                case 15:
                                    StatusStreamBase.this.onUserListSubscription(jSONObject.getJSONObject("source"), jSONObject.getJSONObject("target"), jSONObject.getJSONObject("target_object"), streamListenerArr);
                                    return;
                                case 16:
                                    StatusStreamBase.this.onUserListUnsubscription(jSONObject.getJSONObject("source"), jSONObject.getJSONObject("target"), jSONObject.getJSONObject("target_object"), streamListenerArr);
                                    return;
                                case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS:
                                    StatusStreamBase.this.onUserListCreation(jSONObject.getJSONObject("source"), jSONObject.getJSONObject("target_object"), streamListenerArr);
                                    return;
                                case 18:
                                    StatusStreamBase.this.onUserListUpdated(jSONObject.getJSONObject("source"), jSONObject.getJSONObject("target_object"), streamListenerArr);
                                    return;
                                case TimeUtils.HUNDRED_DAY_FIELD_LEN:
                                    StatusStreamBase.this.onUserListDestroyed(jSONObject.getJSONObject("source"), jSONObject.getJSONObject("target_object"), streamListenerArr);
                                    return;
                                case 20:
                                    StatusStreamBase.this.onUserUpdate(jSONObject.getJSONObject("source"), jSONObject.getJSONObject("target"), streamListenerArr);
                                    return;
                                case 21:
                                    StatusStreamBase.this.onBlock(jSONObject.getJSONObject("source"), jSONObject.getJSONObject("target"), streamListenerArr);
                                    return;
                                case 22:
                                    StatusStreamBase.this.onUnblock(jSONObject.getJSONObject("source"), jSONObject.getJSONObject("target"), streamListenerArr);
                                    return;
                                case 23:
                                    StatusStreamBase.this.onDisconnectionNotice(this.line, streamListenerArr);
                                    return;
                                default:
                                    StatusStreamBase.logger.warn("Received unknown event:", StatusStreamBase.this.CONF.getHttpClientConfiguration().isPrettyDebugEnabled() ? jSONObject.toString(1) : jSONObject.toString());
                                    return;
                            }
                            StatusStreamBase.this.onException(e, streamListenerArr);
                        }
                    } catch (Exception e) {
                        StatusStreamBase.this.onException(e, streamListenerArr);
                    }
                }
            });
        } catch (IOException e) {
            try {
                this.is.close();
            } catch (IOException e2) {
            }
            boolean z = this.streamAlive;
            this.streamAlive = false;
            onClose();
            if (z) {
                throw new TwitterException("Stream closed.", (Throwable) e);
            }
        }
    }

    public abstract void next(StatusListener statusListener) throws TwitterException;

    public abstract void next(StreamListener[] streamListenerArr, RawStreamListener[] rawStreamListenerArr) throws TwitterException;

    /* access modifiers changed from: package-private */
    public void onBlock(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        logger.warn("Unhandled event: onBlock");
    }

    /* access modifiers changed from: protected */
    public abstract void onClose();

    /* access modifiers changed from: package-private */
    public void onDelete(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        logger.warn("Unhandled event: onDelete");
    }

    /* access modifiers changed from: package-private */
    public void onDirectMessage(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        logger.warn("Unhandled event: onDirectMessage");
    }

    /* access modifiers changed from: package-private */
    public void onDisconnectionNotice(String str, StreamListener[] streamListenerArr) {
        logger.warn("Unhandled event: ", str);
    }

    /* access modifiers changed from: package-private */
    public void onException(Exception exc, StreamListener[] streamListenerArr) {
        logger.warn("Unhandled event: ", exc.getMessage());
    }

    public void onException(Exception exc, StreamListener[] streamListenerArr, RawStreamListener[] rawStreamListenerArr) {
        for (StreamListener onException : streamListenerArr) {
            onException.onException(exc);
        }
        for (RawStreamListener onException2 : rawStreamListenerArr) {
            onException2.onException(exc);
        }
    }

    /* access modifiers changed from: package-private */
    public void onFavorite(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException {
        logger.warn("Unhandled event: onFavorite");
    }

    /* access modifiers changed from: package-private */
    public void onFollow(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        logger.warn("Unhandled event: onFollow");
    }

    /* access modifiers changed from: package-private */
    public void onFriends(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        logger.warn("Unhandled event: onFriends");
    }

    /* access modifiers changed from: package-private */
    public void onLimit(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        logger.warn("Unhandled event: onLimit");
    }

    /* access modifiers changed from: package-private */
    public void onMessage(String str, RawStreamListener[] rawStreamListenerArr) throws TwitterException {
        logger.warn("Unhandled event: onMessage");
    }

    /* access modifiers changed from: package-private */
    public void onScrubGeo(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        logger.warn("Unhandled event: onScrubGeo");
    }

    /* access modifiers changed from: package-private */
    public void onSender(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException {
        logger.warn("Unhandled event: onSender");
    }

    /* access modifiers changed from: package-private */
    public void onStallWarning(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        logger.warn("Unhandled event: onStallWarning");
    }

    /* access modifiers changed from: package-private */
    public void onStatus(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException {
        logger.warn("Unhandled event: onStatus");
    }

    /* access modifiers changed from: package-private */
    public void onUnblock(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        logger.warn("Unhandled event: onUnblock");
    }

    /* access modifiers changed from: package-private */
    public void onUnfavorite(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException {
        logger.warn("Unhandled event: onUnfavorite");
    }

    /* access modifiers changed from: package-private */
    public void onUnfollow(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        logger.warn("Unhandled event: onUnfollow");
    }

    /* access modifiers changed from: package-private */
    public void onUserListCreation(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        logger.warn("Unhandled event: onUserListCreation");
    }

    /* access modifiers changed from: package-private */
    public void onUserListDestroyed(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        logger.warn("Unhandled event: onUserListDestroyed");
    }

    /* access modifiers changed from: package-private */
    public void onUserListMemberAddition(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        logger.warn("Unhandled event: onUserListMemberAddition");
    }

    /* access modifiers changed from: package-private */
    public void onUserListMemberDeletion(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        logger.warn("Unhandled event: onUserListMemberDeletion");
    }

    /* access modifiers changed from: package-private */
    public void onUserListSubscription(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        logger.warn("Unhandled event: onUserListSubscription");
    }

    /* access modifiers changed from: package-private */
    public void onUserListUnsubscription(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        logger.warn("Unhandled event: onUserListUnsubscription");
    }

    /* access modifiers changed from: package-private */
    public void onUserListUpdated(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        logger.warn("Unhandled event: onUserListUpdated");
    }

    /* access modifiers changed from: package-private */
    public void onUserUpdate(JSONObject jSONObject, JSONObject jSONObject2, StreamListener[] streamListenerArr) throws TwitterException {
        logger.warn("Unhandled event: onUserUpdate");
    }

    /* access modifiers changed from: package-private */
    public String parseLine(String str) {
        return str;
    }
}
