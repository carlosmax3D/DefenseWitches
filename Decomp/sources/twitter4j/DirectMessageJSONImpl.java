package twitter4j;

import com.facebook.share.internal.ShareConstants;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import twitter4j.conf.Configuration;

final class DirectMessageJSONImpl extends TwitterResponseImpl implements DirectMessage, Serializable {
    private static final long serialVersionUID = 7092906238192790921L;
    private Date createdAt;
    private MediaEntity[] extendedMediaEntities;
    private HashtagEntity[] hashtagEntities;
    private long id;
    private MediaEntity[] mediaEntities;
    private User recipient;
    private long recipientId;
    private String recipientScreenName;
    private User sender;
    private long senderId;
    private String senderScreenName;
    private SymbolEntity[] symbolEntities;
    private String text;
    private URLEntity[] urlEntities;
    private UserMentionEntity[] userMentionEntities;

    DirectMessageJSONImpl(HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        super(httpResponse);
        JSONObject asJSONObject = httpResponse.asJSONObject();
        init(asJSONObject);
        if (configuration.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, asJSONObject);
        }
    }

    DirectMessageJSONImpl(JSONObject jSONObject) throws TwitterException {
        init(jSONObject);
    }

    static ResponseList<DirectMessage> createDirectMessageList(HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        try {
            if (configuration.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
            }
            JSONArray asJSONArray = httpResponse.asJSONArray();
            int length = asJSONArray.length();
            ResponseListImpl responseListImpl = new ResponseListImpl(length, httpResponse);
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = asJSONArray.getJSONObject(i);
                DirectMessageJSONImpl directMessageJSONImpl = new DirectMessageJSONImpl(jSONObject);
                responseListImpl.add(directMessageJSONImpl);
                if (configuration.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(directMessageJSONImpl, jSONObject);
                }
            }
            if (configuration.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(responseListImpl, asJSONArray);
            }
            return responseListImpl;
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    private void init(JSONObject jSONObject) throws TwitterException {
        this.id = ParseUtil.getLong(ShareConstants.WEB_DIALOG_PARAM_ID, jSONObject);
        this.senderId = ParseUtil.getLong("sender_id", jSONObject);
        this.recipientId = ParseUtil.getLong("recipient_id", jSONObject);
        this.createdAt = ParseUtil.getDate("created_at", jSONObject);
        this.senderScreenName = ParseUtil.getUnescapedString("sender_screen_name", jSONObject);
        this.recipientScreenName = ParseUtil.getUnescapedString("recipient_screen_name", jSONObject);
        try {
            this.sender = new UserJSONImpl(jSONObject.getJSONObject("sender"));
            this.recipient = new UserJSONImpl(jSONObject.getJSONObject("recipient"));
            if (!jSONObject.isNull("entities")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("entities");
                if (!jSONObject2.isNull("user_mentions")) {
                    JSONArray jSONArray = jSONObject2.getJSONArray("user_mentions");
                    int length = jSONArray.length();
                    this.userMentionEntities = new UserMentionEntity[length];
                    for (int i = 0; i < length; i++) {
                        this.userMentionEntities[i] = new UserMentionEntityJSONImpl(jSONArray.getJSONObject(i));
                    }
                }
                if (!jSONObject2.isNull("urls")) {
                    JSONArray jSONArray2 = jSONObject2.getJSONArray("urls");
                    int length2 = jSONArray2.length();
                    this.urlEntities = new URLEntity[length2];
                    for (int i2 = 0; i2 < length2; i2++) {
                        this.urlEntities[i2] = new URLEntityJSONImpl(jSONArray2.getJSONObject(i2));
                    }
                }
                if (!jSONObject2.isNull("hashtags")) {
                    JSONArray jSONArray3 = jSONObject2.getJSONArray("hashtags");
                    int length3 = jSONArray3.length();
                    this.hashtagEntities = new HashtagEntity[length3];
                    for (int i3 = 0; i3 < length3; i3++) {
                        this.hashtagEntities[i3] = new HashtagEntityJSONImpl(jSONArray3.getJSONObject(i3));
                    }
                }
                if (!jSONObject2.isNull("symbols")) {
                    JSONArray jSONArray4 = jSONObject2.getJSONArray("symbols");
                    int length4 = jSONArray4.length();
                    this.symbolEntities = new SymbolEntity[length4];
                    for (int i4 = 0; i4 < length4; i4++) {
                        this.symbolEntities[i4] = new HashtagEntityJSONImpl(jSONArray4.getJSONObject(i4));
                    }
                }
                if (!jSONObject2.isNull("media")) {
                    JSONArray jSONArray5 = jSONObject2.getJSONArray("media");
                    int length5 = jSONArray5.length();
                    this.mediaEntities = new MediaEntity[length5];
                    for (int i5 = 0; i5 < length5; i5++) {
                        this.mediaEntities[i5] = new MediaEntityJSONImpl(jSONArray5.getJSONObject(i5));
                    }
                }
            }
            this.userMentionEntities = this.userMentionEntities == null ? new UserMentionEntity[0] : this.userMentionEntities;
            this.urlEntities = this.urlEntities == null ? new URLEntity[0] : this.urlEntities;
            this.hashtagEntities = this.hashtagEntities == null ? new HashtagEntity[0] : this.hashtagEntities;
            this.symbolEntities = this.symbolEntities == null ? new SymbolEntity[0] : this.symbolEntities;
            this.mediaEntities = this.mediaEntities == null ? new MediaEntity[0] : this.mediaEntities;
            this.extendedMediaEntities = this.extendedMediaEntities == null ? new MediaEntity[0] : this.extendedMediaEntities;
            this.text = HTMLEntity.unescapeAndSlideEntityIncdices(jSONObject.getString("text"), this.userMentionEntities, this.urlEntities, this.hashtagEntities, this.mediaEntities);
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this != obj) {
            return (obj instanceof DirectMessage) && ((DirectMessage) obj).getId() == this.id;
        }
        return true;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public MediaEntity[] getExtendedMediaEntities() {
        return this.extendedMediaEntities;
    }

    public HashtagEntity[] getHashtagEntities() {
        return this.hashtagEntities;
    }

    public long getId() {
        return this.id;
    }

    public MediaEntity[] getMediaEntities() {
        return this.mediaEntities;
    }

    public User getRecipient() {
        return this.recipient;
    }

    public long getRecipientId() {
        return this.recipientId;
    }

    public String getRecipientScreenName() {
        return this.recipientScreenName;
    }

    public User getSender() {
        return this.sender;
    }

    public long getSenderId() {
        return this.senderId;
    }

    public String getSenderScreenName() {
        return this.senderScreenName;
    }

    public SymbolEntity[] getSymbolEntities() {
        return this.symbolEntities;
    }

    public String getText() {
        return this.text;
    }

    public URLEntity[] getURLEntities() {
        return this.urlEntities;
    }

    public UserMentionEntity[] getUserMentionEntities() {
        return this.userMentionEntities;
    }

    public int hashCode() {
        return (int) this.id;
    }

    public String toString() {
        List list = null;
        StringBuilder append = new StringBuilder().append("DirectMessageJSONImpl{id=").append(this.id).append(", text='").append(this.text).append('\'').append(", sender_id=").append(this.senderId).append(", recipient_id=").append(this.recipientId).append(", created_at=").append(this.createdAt).append(", userMentionEntities=").append(this.userMentionEntities == null ? null : Arrays.asList(this.userMentionEntities)).append(", urlEntities=").append(this.urlEntities == null ? null : Arrays.asList(this.urlEntities)).append(", hashtagEntities=").append(this.hashtagEntities == null ? null : Arrays.asList(this.hashtagEntities)).append(", sender_screen_name='").append(this.senderScreenName).append('\'').append(", recipient_screen_name='").append(this.recipientScreenName).append('\'').append(", sender=").append(this.sender).append(", recipient=").append(this.recipient).append(", userMentionEntities=").append(this.userMentionEntities == null ? null : Arrays.asList(this.userMentionEntities)).append(", urlEntities=").append(this.urlEntities == null ? null : Arrays.asList(this.urlEntities)).append(", hashtagEntities=");
        if (this.hashtagEntities != null) {
            list = Arrays.asList(this.hashtagEntities);
        }
        return append.append(list).append('}').toString();
    }
}
