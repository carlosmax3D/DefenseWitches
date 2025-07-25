package twitter4j;

import com.facebook.internal.NativeProtocol;
import java.io.Serializable;
import twitter4j.conf.Configuration;

class AccountTotalsJSONImpl extends TwitterResponseImpl implements AccountTotals, Serializable {
    private static final long serialVersionUID = 4199733699237229892L;
    private final int favorites;
    private final int followers;
    private final int friends;
    private final int updates;

    private AccountTotalsJSONImpl(HttpResponse httpResponse, JSONObject jSONObject) {
        super(httpResponse);
        this.updates = ParseUtil.getInt("updates", jSONObject);
        this.followers = ParseUtil.getInt("followers", jSONObject);
        this.favorites = ParseUtil.getInt("favorites", jSONObject);
        this.friends = ParseUtil.getInt(NativeProtocol.AUDIENCE_FRIENDS, jSONObject);
    }

    AccountTotalsJSONImpl(HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        this(httpResponse, httpResponse.asJSONObject());
        if (configuration.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, httpResponse.asJSONObject());
        }
    }

    AccountTotalsJSONImpl(JSONObject jSONObject) throws TwitterException {
        this((HttpResponse) null, jSONObject);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AccountTotalsJSONImpl accountTotalsJSONImpl = (AccountTotalsJSONImpl) obj;
        if (this.favorites != accountTotalsJSONImpl.favorites) {
            return false;
        }
        if (this.followers != accountTotalsJSONImpl.followers) {
            return false;
        }
        if (this.friends != accountTotalsJSONImpl.friends) {
            return false;
        }
        return this.updates == accountTotalsJSONImpl.updates;
    }

    public int getFavorites() {
        return this.favorites;
    }

    public int getFollowers() {
        return this.followers;
    }

    public int getFriends() {
        return this.friends;
    }

    public int getUpdates() {
        return this.updates;
    }

    public int hashCode() {
        return (((((this.updates * 31) + this.followers) * 31) + this.favorites) * 31) + this.friends;
    }

    public String toString() {
        return "AccountTotalsJSONImpl{updates=" + this.updates + ", followers=" + this.followers + ", favorites=" + this.favorites + ", friends=" + this.friends + '}';
    }
}
