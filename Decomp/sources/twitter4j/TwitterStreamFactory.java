package twitter4j;

import java.io.Serializable;
import twitter4j.auth.AccessToken;
import twitter4j.auth.Authorization;
import twitter4j.auth.AuthorizationFactory;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;

public final class TwitterStreamFactory implements Serializable {
    private static final TwitterStream SINGLETON = new TwitterStreamImpl(ConfigurationContext.getInstance(), TwitterFactory.DEFAULT_AUTHORIZATION);
    private static final long serialVersionUID = -5181136070759074681L;
    private final Configuration conf;

    public TwitterStreamFactory() {
        this(ConfigurationContext.getInstance());
    }

    public TwitterStreamFactory(String str) {
        this(ConfigurationContext.getInstance(str));
    }

    public TwitterStreamFactory(Configuration configuration) {
        this.conf = configuration;
    }

    private TwitterStream getInstance(Configuration configuration, Authorization authorization) {
        return new TwitterStreamImpl(configuration, authorization);
    }

    public static TwitterStream getSingleton() {
        return SINGLETON;
    }

    public TwitterStream getInstance() {
        return getInstance(AuthorizationFactory.getInstance(this.conf));
    }

    public TwitterStream getInstance(AccessToken accessToken) {
        String oAuthConsumerKey = this.conf.getOAuthConsumerKey();
        String oAuthConsumerSecret = this.conf.getOAuthConsumerSecret();
        if (oAuthConsumerKey == null && oAuthConsumerSecret == null) {
            throw new IllegalStateException("Consumer key and Consumer secret not supplied.");
        }
        OAuthAuthorization oAuthAuthorization = new OAuthAuthorization(this.conf);
        oAuthAuthorization.setOAuthAccessToken(accessToken);
        return getInstance(this.conf, oAuthAuthorization);
    }

    public TwitterStream getInstance(Authorization authorization) {
        return getInstance(this.conf, authorization);
    }
}
