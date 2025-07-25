package twitter4j.auth;

public interface AuthorizationConfiguration {
    String getOAuth2AccessToken();

    String getOAuth2TokenType();

    String getOAuthAccessToken();

    String getOAuthAccessTokenSecret();

    String getOAuthConsumerKey();

    String getOAuthConsumerSecret();

    String getPassword();

    String getUser();
}
