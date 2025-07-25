package twitter4j.auth;

import java.io.Serializable;
import twitter4j.HttpResponse;
import twitter4j.TwitterException;

public class AccessToken extends OAuthToken implements Serializable {
    private static final long serialVersionUID = 2470022129505774772L;
    private String screenName;
    private long userId;

    AccessToken(String str) {
        super(str);
        this.userId = -1;
        this.screenName = getParameter("screen_name");
        String parameter = getParameter("user_id");
        if (parameter != null) {
            this.userId = Long.parseLong(parameter);
        }
    }

    public AccessToken(String str, String str2) {
        super(str, str2);
        this.userId = -1;
        int indexOf = str.indexOf(AdNetworkKey.DEFAULT_AD);
        if (indexOf != -1) {
            try {
                this.userId = Long.parseLong(str.substring(0, indexOf));
            } catch (NumberFormatException e) {
            }
        }
    }

    public AccessToken(String str, String str2, long j) {
        super(str, str2);
        this.userId = -1;
        this.userId = j;
    }

    AccessToken(HttpResponse httpResponse) throws TwitterException {
        this(httpResponse.asString());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        AccessToken accessToken = (AccessToken) obj;
        if (this.userId != accessToken.userId) {
            return false;
        }
        if (this.screenName != null) {
            if (this.screenName.equals(accessToken.screenName)) {
                return true;
            }
        } else if (accessToken.screenName == null) {
            return true;
        }
        return false;
    }

    public /* bridge */ /* synthetic */ String getParameter(String str) {
        return super.getParameter(str);
    }

    public String getScreenName() {
        return this.screenName;
    }

    public /* bridge */ /* synthetic */ String getToken() {
        return super.getToken();
    }

    public /* bridge */ /* synthetic */ String getTokenSecret() {
        return super.getTokenSecret();
    }

    public long getUserId() {
        return this.userId;
    }

    public int hashCode() {
        return (((super.hashCode() * 31) + (this.screenName != null ? this.screenName.hashCode() : 0)) * 31) + ((int) (this.userId ^ (this.userId >>> 32)));
    }

    public String toString() {
        return "AccessToken{screenName='" + this.screenName + '\'' + ", userId=" + this.userId + '}';
    }
}
