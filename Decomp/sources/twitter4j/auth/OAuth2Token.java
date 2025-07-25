package twitter4j.auth;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import jp.stargarage.g2metrics.BuildConfig;
import twitter4j.HttpResponse;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.TwitterException;

public class OAuth2Token implements Serializable {
    private static final long serialVersionUID = -8985359441959903216L;
    private String accessToken;
    private String tokenType;

    public OAuth2Token(String str, String str2) {
        this.tokenType = str;
        this.accessToken = str2;
    }

    OAuth2Token(HttpResponse httpResponse) throws TwitterException {
        JSONObject asJSONObject = httpResponse.asJSONObject();
        this.tokenType = getRawString("token_type", asJSONObject);
        try {
            this.accessToken = URLDecoder.decode(getRawString("access_token", asJSONObject), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
    }

    private static String getRawString(String str, JSONObject jSONObject) {
        try {
            if (jSONObject.isNull(str)) {
                return null;
            }
            return jSONObject.getString(str);
        } catch (JSONException e) {
            return null;
        }
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof OAuth2Token)) {
            return false;
        }
        OAuth2Token oAuth2Token = (OAuth2Token) obj;
        if (this.tokenType != null) {
            if (!this.tokenType.equals(oAuth2Token.tokenType)) {
                return false;
            }
        } else if (oAuth2Token.tokenType != null) {
            return false;
        }
        if (this.accessToken != null) {
            return this.accessToken.equals(oAuth2Token.accessToken);
        }
        if (oAuth2Token.accessToken != null) {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public String generateAuthorizationHeader() {
        String str = BuildConfig.FLAVOR;
        try {
            str = URLEncoder.encode(this.accessToken, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return "Bearer " + str;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (this.tokenType != null ? this.tokenType.hashCode() : 0) * 31;
        if (this.accessToken != null) {
            i = this.accessToken.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "OAuth2Token{tokenType='" + this.tokenType + '\'' + ", accessToken='" + this.accessToken + '\'' + '}';
    }
}
