package twitter4j.auth;

import java.io.Serializable;
import javax.crypto.spec.SecretKeySpec;
import jp.newgate.game.android.dw.Constants;
import twitter4j.HttpResponse;
import twitter4j.TwitterException;

abstract class OAuthToken implements Serializable {
    private static final long serialVersionUID = -7841506492508140600L;
    private String[] responseStr;
    private transient SecretKeySpec secretKeySpec;
    private final String token;
    private final String tokenSecret;

    OAuthToken(String str) {
        this.responseStr = null;
        this.responseStr = str.split("&");
        this.tokenSecret = getParameter(Constants.TWITTER_KEY_SECRET_NAME);
        this.token = getParameter(Constants.TWITTER_KEY_TOKEN_NAME);
    }

    public OAuthToken(String str, String str2) {
        this.responseStr = null;
        if (str == null) {
            throw new IllegalArgumentException("Token can't be null");
        } else if (str2 == null) {
            throw new IllegalArgumentException("TokenSecret can't be null");
        } else {
            this.token = str;
            this.tokenSecret = str2;
        }
    }

    OAuthToken(HttpResponse httpResponse) throws TwitterException {
        this(httpResponse.asString());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OAuthToken)) {
            return false;
        }
        OAuthToken oAuthToken = (OAuthToken) obj;
        if (!this.token.equals(oAuthToken.token)) {
            return false;
        }
        return this.tokenSecret.equals(oAuthToken.tokenSecret);
    }

    public String getParameter(String str) {
        for (String str2 : this.responseStr) {
            if (str2.startsWith(str + '=')) {
                return str2.split("=")[1].trim();
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public SecretKeySpec getSecretKeySpec() {
        return this.secretKeySpec;
    }

    public String getToken() {
        return this.token;
    }

    public String getTokenSecret() {
        return this.tokenSecret;
    }

    public int hashCode() {
        return (this.token.hashCode() * 31) + this.tokenSecret.hashCode();
    }

    /* access modifiers changed from: package-private */
    public void setSecretKeySpec(SecretKeySpec secretKeySpec2) {
        this.secretKeySpec = secretKeySpec2;
    }

    public String toString() {
        return "OAuthToken{token='" + this.token + '\'' + ", tokenSecret='" + this.tokenSecret + '\'' + ", secretKeySpec=" + this.secretKeySpec + '}';
    }
}
