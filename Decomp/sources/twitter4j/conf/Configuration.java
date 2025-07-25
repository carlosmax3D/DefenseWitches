package twitter4j.conf;

import java.io.Serializable;
import java.util.Properties;
import twitter4j.HttpClientConfiguration;
import twitter4j.auth.AuthorizationConfiguration;

public interface Configuration extends AuthorizationConfiguration, Serializable {
    int getAsyncNumThreads();

    long getContributingTo();

    String getDispatcherImpl();

    HttpClientConfiguration getHttpClientConfiguration();

    int getHttpStreamingReadTimeout();

    String getLoggerFactory();

    String getMediaProvider();

    String getMediaProviderAPIKey();

    Properties getMediaProviderParameters();

    String getOAuth2AccessToken();

    String getOAuth2InvalidateTokenURL();

    String getOAuth2Scope();

    String getOAuth2TokenType();

    String getOAuth2TokenURL();

    String getOAuthAccessToken();

    String getOAuthAccessTokenSecret();

    String getOAuthAccessTokenURL();

    String getOAuthAuthenticationURL();

    String getOAuthAuthorizationURL();

    String getOAuthConsumerKey();

    String getOAuthConsumerSecret();

    String getOAuthRequestTokenURL();

    String getPassword();

    String getRestBaseURL();

    String getSiteStreamBaseURL();

    String getStreamBaseURL();

    String getUploadBaseURL();

    String getUser();

    String getUserStreamBaseURL();

    boolean isApplicationOnlyAuthEnabled();

    boolean isDaemonEnabled();

    boolean isDebugEnabled();

    boolean isIncludeEntitiesEnabled();

    boolean isIncludeMyRetweetEnabled();

    boolean isJSONStoreEnabled();

    boolean isMBeanEnabled();

    boolean isStallWarningsEnabled();

    boolean isTrimUserEnabled();

    boolean isUserStreamRepliesAllEnabled();

    boolean isUserStreamWithFollowingsEnabled();
}
