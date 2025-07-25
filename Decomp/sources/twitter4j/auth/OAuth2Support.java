package twitter4j.auth;

import twitter4j.TwitterException;

public interface OAuth2Support {
    OAuth2Token getOAuth2Token() throws TwitterException;

    void invalidateOAuth2Token() throws TwitterException;

    void setOAuth2Token(OAuth2Token oAuth2Token);

    void setOAuthConsumer(String str, String str2);
}
