package twitter4j;

import com.facebook.share.internal.ShareConstants;
import twitter4j.conf.Configuration;

class FriendshipJSONImpl implements Friendship {
    private static final long serialVersionUID = 6847273186993125826L;
    private boolean followedBy = false;
    private boolean following = false;
    private final long id;
    private final String name;
    private final String screenName;

    FriendshipJSONImpl(JSONObject jSONObject) throws TwitterException {
        try {
            this.id = ParseUtil.getLong(ShareConstants.WEB_DIALOG_PARAM_ID, jSONObject);
            this.name = jSONObject.getString("name");
            this.screenName = jSONObject.getString("screen_name");
            JSONArray jSONArray = jSONObject.getJSONArray("connections");
            for (int i = 0; i < jSONArray.length(); i++) {
                String string = jSONArray.getString(i);
                if ("following".equals(string)) {
                    this.following = true;
                } else if ("followed_by".equals(string)) {
                    this.followedBy = true;
                }
            }
        } catch (JSONException e) {
            throw new TwitterException(e.getMessage() + ":" + jSONObject.toString(), (Throwable) e);
        }
    }

    static ResponseList<Friendship> createFriendshipList(HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        try {
            if (configuration.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
            }
            JSONArray asJSONArray = httpResponse.asJSONArray();
            int length = asJSONArray.length();
            ResponseListImpl responseListImpl = new ResponseListImpl(length, httpResponse);
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = asJSONArray.getJSONObject(i);
                FriendshipJSONImpl friendshipJSONImpl = new FriendshipJSONImpl(jSONObject);
                if (configuration.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(friendshipJSONImpl, jSONObject);
                }
                responseListImpl.add(friendshipJSONImpl);
            }
            if (configuration.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(responseListImpl, asJSONArray);
            }
            return responseListImpl;
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FriendshipJSONImpl friendshipJSONImpl = (FriendshipJSONImpl) obj;
        if (this.followedBy != friendshipJSONImpl.followedBy) {
            return false;
        }
        if (this.following != friendshipJSONImpl.following) {
            return false;
        }
        if (this.id != friendshipJSONImpl.id) {
            return false;
        }
        if (!this.name.equals(friendshipJSONImpl.name)) {
            return false;
        }
        return this.screenName.equals(friendshipJSONImpl.screenName);
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getScreenName() {
        return this.screenName;
    }

    public int hashCode() {
        int i = 1;
        int hashCode = ((((((((int) (this.id ^ (this.id >>> 32))) * 31) + (this.name != null ? this.name.hashCode() : 0)) * 31) + (this.screenName != null ? this.screenName.hashCode() : 0)) * 31) + (this.following ? 1 : 0)) * 31;
        if (!this.followedBy) {
            i = 0;
        }
        return hashCode + i;
    }

    public boolean isFollowedBy() {
        return this.followedBy;
    }

    public boolean isFollowing() {
        return this.following;
    }

    public String toString() {
        return "FriendshipJSONImpl{id=" + this.id + ", name='" + this.name + '\'' + ", screenName='" + this.screenName + '\'' + ", following=" + this.following + ", followedBy=" + this.followedBy + '}';
    }
}
