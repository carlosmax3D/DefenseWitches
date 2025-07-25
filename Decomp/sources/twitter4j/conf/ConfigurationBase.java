package twitter4j.conf;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import twitter4j.HttpClientConfiguration;
import twitter4j.Logger;

class ConfigurationBase implements Configuration, Serializable {
    private static final List<ConfigurationBase> instances = new ArrayList();
    private static final long serialVersionUID = 6175546394599249696L;
    private boolean applicationOnlyAuthEnabled = false;
    private int asyncNumThreads = 1;
    private long contributingTo = -1;
    private boolean daemonEnabled = true;
    private boolean debug = false;
    private String dispatcherImpl = "twitter4j.DispatcherImpl";
    private HttpClientConfiguration httpConf = new MyHttpClientConfiguration((String) null, (String) null, (String) null, -1, 20000, 120000, false, true);
    /* access modifiers changed from: private */
    public int httpRetryCount = 0;
    /* access modifiers changed from: private */
    public int httpRetryIntervalSeconds = 5;
    private int httpStreamingReadTimeout = 40000;
    private boolean includeEntitiesEnabled = true;
    private boolean includeMyRetweetEnabled = true;
    private boolean jsonStoreEnabled = false;
    private String loggerFactory = null;
    private boolean mbeanEnabled = false;
    private String mediaProvider = "TWITTER";
    private String mediaProviderAPIKey = null;
    private Properties mediaProviderParameters = null;
    private String oAuth2AccessToken;
    private String oAuth2InvalidateTokenURL = "https://api.twitter.com/oauth2/invalidate_token";
    private String oAuth2Scope;
    private String oAuth2TokenType;
    private String oAuth2TokenURL = "https://api.twitter.com/oauth2/token";
    private String oAuthAccessToken = null;
    private String oAuthAccessTokenSecret = null;
    private String oAuthAccessTokenURL = "https://api.twitter.com/oauth/access_token";
    private String oAuthAuthenticationURL = "https://api.twitter.com/oauth/authenticate";
    private String oAuthAuthorizationURL = "https://api.twitter.com/oauth/authorize";
    private String oAuthConsumerKey = null;
    private String oAuthConsumerSecret = null;
    private String oAuthRequestTokenURL = "https://api.twitter.com/oauth/request_token";
    private String password = null;
    private String restBaseURL = "https://api.twitter.com/1.1/";
    private String siteStreamBaseURL = "https://sitestream.twitter.com/1.1/";
    private boolean stallWarningsEnabled = true;
    private String streamBaseURL = "https://stream.twitter.com/1.1/";
    private boolean trimUserEnabled = false;
    private String uploadBaseURL = "https://upload.twitter.com/1.1/";
    private String user = null;
    private String userStreamBaseURL = "https://userstream.twitter.com/1.1/";
    private boolean userStreamRepliesAllEnabled = false;
    private boolean userStreamWithFollowingsEnabled = true;

    class MyHttpClientConfiguration implements HttpClientConfiguration, Serializable {
        private static final long serialVersionUID = 8226866124868861058L;
        private boolean gzipEnabled = true;
        private int httpConnectionTimeout = 20000;
        private String httpProxyHost = null;
        private String httpProxyPassword = null;
        private int httpProxyPort = -1;
        private String httpProxyUser = null;
        private int httpReadTimeout = 120000;
        private boolean prettyDebug = false;

        MyHttpClientConfiguration(String str, String str2, String str3, int i, int i2, int i3, boolean z, boolean z2) {
            this.httpProxyHost = str;
            this.httpProxyUser = str2;
            this.httpProxyPassword = str3;
            this.httpProxyPort = i;
            this.httpConnectionTimeout = i2;
            this.httpReadTimeout = i3;
            this.prettyDebug = z;
            this.gzipEnabled = z2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            MyHttpClientConfiguration myHttpClientConfiguration = (MyHttpClientConfiguration) obj;
            if (this.gzipEnabled != myHttpClientConfiguration.gzipEnabled) {
                return false;
            }
            if (this.httpConnectionTimeout != myHttpClientConfiguration.httpConnectionTimeout) {
                return false;
            }
            if (this.httpProxyPort != myHttpClientConfiguration.httpProxyPort) {
                return false;
            }
            if (this.httpReadTimeout != myHttpClientConfiguration.httpReadTimeout) {
                return false;
            }
            if (this.prettyDebug != myHttpClientConfiguration.prettyDebug) {
                return false;
            }
            if (this.httpProxyHost == null ? myHttpClientConfiguration.httpProxyHost != null : !this.httpProxyHost.equals(myHttpClientConfiguration.httpProxyHost)) {
                return false;
            }
            if (this.httpProxyPassword == null ? myHttpClientConfiguration.httpProxyPassword != null : !this.httpProxyPassword.equals(myHttpClientConfiguration.httpProxyPassword)) {
                return false;
            }
            if (this.httpProxyUser != null) {
                if (this.httpProxyUser.equals(myHttpClientConfiguration.httpProxyUser)) {
                    return true;
                }
            } else if (myHttpClientConfiguration.httpProxyUser == null) {
                return true;
            }
            return false;
        }

        public int getHttpConnectionTimeout() {
            return this.httpConnectionTimeout;
        }

        public String getHttpProxyHost() {
            return this.httpProxyHost;
        }

        public String getHttpProxyPassword() {
            return this.httpProxyPassword;
        }

        public int getHttpProxyPort() {
            return this.httpProxyPort;
        }

        public String getHttpProxyUser() {
            return this.httpProxyUser;
        }

        public int getHttpReadTimeout() {
            return this.httpReadTimeout;
        }

        public int getHttpRetryCount() {
            return ConfigurationBase.this.httpRetryCount;
        }

        public int getHttpRetryIntervalSeconds() {
            return ConfigurationBase.this.httpRetryIntervalSeconds;
        }

        public int hashCode() {
            int i = 1;
            int hashCode = (((((((((((((this.httpProxyHost != null ? this.httpProxyHost.hashCode() : 0) * 31) + (this.httpProxyUser != null ? this.httpProxyUser.hashCode() : 0)) * 31) + (this.httpProxyPassword != null ? this.httpProxyPassword.hashCode() : 0)) * 31) + this.httpProxyPort) * 31) + this.httpConnectionTimeout) * 31) + this.httpReadTimeout) * 31) + (this.prettyDebug ? 1 : 0)) * 31;
            if (!this.gzipEnabled) {
                i = 0;
            }
            return hashCode + i;
        }

        public boolean isGZIPEnabled() {
            return this.gzipEnabled;
        }

        public boolean isPrettyDebugEnabled() {
            return this.prettyDebug;
        }

        public String toString() {
            return "MyHttpClientConfiguration{httpProxyHost='" + this.httpProxyHost + '\'' + ", httpProxyUser='" + this.httpProxyUser + '\'' + ", httpProxyPassword='" + this.httpProxyPassword + '\'' + ", httpProxyPort=" + this.httpProxyPort + ", httpConnectionTimeout=" + this.httpConnectionTimeout + ", httpReadTimeout=" + this.httpReadTimeout + ", prettyDebug=" + this.prettyDebug + ", gzipEnabled=" + this.gzipEnabled + '}';
        }
    }

    protected ConfigurationBase() {
    }

    private static void cacheInstance(ConfigurationBase configurationBase) {
        if (!instances.contains(configurationBase)) {
            instances.add(configurationBase);
        }
    }

    static String fixURL(boolean z, String str) {
        if (str == null) {
            return null;
        }
        int indexOf = str.indexOf("://");
        if (-1 == indexOf) {
            throw new IllegalArgumentException("url should contain '://'");
        }
        String substring = str.substring(indexOf + 3);
        return z ? "https://" + substring : "http://" + substring;
    }

    private static ConfigurationBase getInstance(ConfigurationBase configurationBase) {
        int indexOf = instances.indexOf(configurationBase);
        if (indexOf != -1) {
            return instances.get(indexOf);
        }
        instances.add(configurationBase);
        return configurationBase;
    }

    /* access modifiers changed from: protected */
    public void cacheInstance() {
        cacheInstance(this);
    }

    public void dumpConfiguration() {
        Logger logger = Logger.getLogger(ConfigurationBase.class);
        if (this.debug) {
            for (Field field : ConfigurationBase.class.getDeclaredFields()) {
                try {
                    Object obj = field.get(this);
                    String valueOf = String.valueOf(obj);
                    if (obj != null && field.getName().matches("oAuthConsumerSecret|oAuthAccessTokenSecret|password")) {
                        valueOf = String.valueOf(obj).replaceAll(".", "*");
                    }
                    logger.debug(field.getName() + ": " + valueOf);
                } catch (IllegalAccessException e) {
                }
            }
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ConfigurationBase configurationBase = (ConfigurationBase) obj;
        if (this.applicationOnlyAuthEnabled != configurationBase.applicationOnlyAuthEnabled) {
            return false;
        }
        if (this.asyncNumThreads != configurationBase.asyncNumThreads) {
            return false;
        }
        if (this.contributingTo != configurationBase.contributingTo) {
            return false;
        }
        if (this.daemonEnabled != configurationBase.daemonEnabled) {
            return false;
        }
        if (this.debug != configurationBase.debug) {
            return false;
        }
        if (this.httpRetryCount != configurationBase.httpRetryCount) {
            return false;
        }
        if (this.httpRetryIntervalSeconds != configurationBase.httpRetryIntervalSeconds) {
            return false;
        }
        if (this.httpStreamingReadTimeout != configurationBase.httpStreamingReadTimeout) {
            return false;
        }
        if (this.includeEntitiesEnabled != configurationBase.includeEntitiesEnabled) {
            return false;
        }
        if (this.includeMyRetweetEnabled != configurationBase.includeMyRetweetEnabled) {
            return false;
        }
        if (this.jsonStoreEnabled != configurationBase.jsonStoreEnabled) {
            return false;
        }
        if (this.mbeanEnabled != configurationBase.mbeanEnabled) {
            return false;
        }
        if (this.stallWarningsEnabled != configurationBase.stallWarningsEnabled) {
            return false;
        }
        if (this.trimUserEnabled != configurationBase.trimUserEnabled) {
            return false;
        }
        if (this.userStreamRepliesAllEnabled != configurationBase.userStreamRepliesAllEnabled) {
            return false;
        }
        if (this.userStreamWithFollowingsEnabled != configurationBase.userStreamWithFollowingsEnabled) {
            return false;
        }
        if (this.dispatcherImpl == null ? configurationBase.dispatcherImpl != null : !this.dispatcherImpl.equals(configurationBase.dispatcherImpl)) {
            return false;
        }
        if (this.httpConf == null ? configurationBase.httpConf != null : !this.httpConf.equals(configurationBase.httpConf)) {
            return false;
        }
        if (this.loggerFactory == null ? configurationBase.loggerFactory != null : !this.loggerFactory.equals(configurationBase.loggerFactory)) {
            return false;
        }
        if (this.mediaProvider == null ? configurationBase.mediaProvider != null : !this.mediaProvider.equals(configurationBase.mediaProvider)) {
            return false;
        }
        if (this.mediaProviderAPIKey == null ? configurationBase.mediaProviderAPIKey != null : !this.mediaProviderAPIKey.equals(configurationBase.mediaProviderAPIKey)) {
            return false;
        }
        if (this.mediaProviderParameters == null ? configurationBase.mediaProviderParameters != null : !this.mediaProviderParameters.equals(configurationBase.mediaProviderParameters)) {
            return false;
        }
        if (this.oAuth2AccessToken == null ? configurationBase.oAuth2AccessToken != null : !this.oAuth2AccessToken.equals(configurationBase.oAuth2AccessToken)) {
            return false;
        }
        if (this.oAuth2InvalidateTokenURL == null ? configurationBase.oAuth2InvalidateTokenURL != null : !this.oAuth2InvalidateTokenURL.equals(configurationBase.oAuth2InvalidateTokenURL)) {
            return false;
        }
        if (this.oAuth2TokenType == null ? configurationBase.oAuth2TokenType != null : !this.oAuth2TokenType.equals(configurationBase.oAuth2TokenType)) {
            return false;
        }
        if (this.oAuth2TokenURL == null ? configurationBase.oAuth2TokenURL != null : !this.oAuth2TokenURL.equals(configurationBase.oAuth2TokenURL)) {
            return false;
        }
        if (this.oAuth2Scope == null ? configurationBase.oAuth2Scope != null : !this.oAuth2Scope.equals(configurationBase.oAuth2Scope)) {
            return false;
        }
        if (this.oAuthAccessToken == null ? configurationBase.oAuthAccessToken != null : !this.oAuthAccessToken.equals(configurationBase.oAuthAccessToken)) {
            return false;
        }
        if (this.oAuthAccessTokenSecret == null ? configurationBase.oAuthAccessTokenSecret != null : !this.oAuthAccessTokenSecret.equals(configurationBase.oAuthAccessTokenSecret)) {
            return false;
        }
        if (this.oAuthAccessTokenURL == null ? configurationBase.oAuthAccessTokenURL != null : !this.oAuthAccessTokenURL.equals(configurationBase.oAuthAccessTokenURL)) {
            return false;
        }
        if (this.oAuthAuthenticationURL == null ? configurationBase.oAuthAuthenticationURL != null : !this.oAuthAuthenticationURL.equals(configurationBase.oAuthAuthenticationURL)) {
            return false;
        }
        if (this.oAuthAuthorizationURL == null ? configurationBase.oAuthAuthorizationURL != null : !this.oAuthAuthorizationURL.equals(configurationBase.oAuthAuthorizationURL)) {
            return false;
        }
        if (this.oAuthConsumerKey == null ? configurationBase.oAuthConsumerKey != null : !this.oAuthConsumerKey.equals(configurationBase.oAuthConsumerKey)) {
            return false;
        }
        if (this.oAuthConsumerSecret == null ? configurationBase.oAuthConsumerSecret != null : !this.oAuthConsumerSecret.equals(configurationBase.oAuthConsumerSecret)) {
            return false;
        }
        if (this.oAuthRequestTokenURL == null ? configurationBase.oAuthRequestTokenURL != null : !this.oAuthRequestTokenURL.equals(configurationBase.oAuthRequestTokenURL)) {
            return false;
        }
        if (this.password == null ? configurationBase.password != null : !this.password.equals(configurationBase.password)) {
            return false;
        }
        if (this.restBaseURL == null ? configurationBase.restBaseURL != null : !this.restBaseURL.equals(configurationBase.restBaseURL)) {
            return false;
        }
        if (this.uploadBaseURL == null ? configurationBase.uploadBaseURL != null : !this.uploadBaseURL.equals(configurationBase.uploadBaseURL)) {
            return false;
        }
        if (this.siteStreamBaseURL == null ? configurationBase.siteStreamBaseURL != null : !this.siteStreamBaseURL.equals(configurationBase.siteStreamBaseURL)) {
            return false;
        }
        if (this.streamBaseURL == null ? configurationBase.streamBaseURL != null : !this.streamBaseURL.equals(configurationBase.streamBaseURL)) {
            return false;
        }
        if (this.user == null ? configurationBase.user != null : !this.user.equals(configurationBase.user)) {
            return false;
        }
        if (this.userStreamBaseURL != null) {
            if (this.userStreamBaseURL.equals(configurationBase.userStreamBaseURL)) {
                return true;
            }
        } else if (configurationBase.userStreamBaseURL == null) {
            return true;
        }
        return false;
    }

    public final int getAsyncNumThreads() {
        return this.asyncNumThreads;
    }

    public final long getContributingTo() {
        return this.contributingTo;
    }

    public String getDispatcherImpl() {
        return this.dispatcherImpl;
    }

    public HttpClientConfiguration getHttpClientConfiguration() {
        return this.httpConf;
    }

    public int getHttpStreamingReadTimeout() {
        return this.httpStreamingReadTimeout;
    }

    public String getLoggerFactory() {
        return this.loggerFactory;
    }

    public String getMediaProvider() {
        return this.mediaProvider;
    }

    public String getMediaProviderAPIKey() {
        return this.mediaProviderAPIKey;
    }

    public Properties getMediaProviderParameters() {
        return this.mediaProviderParameters;
    }

    public String getOAuth2AccessToken() {
        return this.oAuth2AccessToken;
    }

    public String getOAuth2InvalidateTokenURL() {
        return this.oAuth2InvalidateTokenURL;
    }

    public String getOAuth2Scope() {
        return this.oAuth2Scope;
    }

    public String getOAuth2TokenType() {
        return this.oAuth2TokenType;
    }

    public String getOAuth2TokenURL() {
        return this.oAuth2TokenURL;
    }

    public String getOAuthAccessToken() {
        return this.oAuthAccessToken;
    }

    public String getOAuthAccessTokenSecret() {
        return this.oAuthAccessTokenSecret;
    }

    public String getOAuthAccessTokenURL() {
        return this.oAuthAccessTokenURL;
    }

    public String getOAuthAuthenticationURL() {
        return this.oAuthAuthenticationURL;
    }

    public String getOAuthAuthorizationURL() {
        return this.oAuthAuthorizationURL;
    }

    public final String getOAuthConsumerKey() {
        return this.oAuthConsumerKey;
    }

    public final String getOAuthConsumerSecret() {
        return this.oAuthConsumerSecret;
    }

    public String getOAuthRequestTokenURL() {
        return this.oAuthRequestTokenURL;
    }

    public final String getPassword() {
        return this.password;
    }

    public String getRestBaseURL() {
        return this.restBaseURL;
    }

    public String getSiteStreamBaseURL() {
        return this.siteStreamBaseURL;
    }

    public String getStreamBaseURL() {
        return this.streamBaseURL;
    }

    public String getUploadBaseURL() {
        return this.uploadBaseURL;
    }

    public final String getUser() {
        return this.user;
    }

    public String getUserStreamBaseURL() {
        return this.userStreamBaseURL;
    }

    public int hashCode() {
        int i = 1;
        int hashCode = (((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((this.debug ? 1 : 0) * 31) + (this.user != null ? this.user.hashCode() : 0)) * 31) + (this.password != null ? this.password.hashCode() : 0)) * 31) + (this.httpConf != null ? this.httpConf.hashCode() : 0)) * 31) + this.httpStreamingReadTimeout) * 31) + this.httpRetryCount) * 31) + this.httpRetryIntervalSeconds) * 31) + (this.oAuthConsumerKey != null ? this.oAuthConsumerKey.hashCode() : 0)) * 31) + (this.oAuthConsumerSecret != null ? this.oAuthConsumerSecret.hashCode() : 0)) * 31) + (this.oAuthAccessToken != null ? this.oAuthAccessToken.hashCode() : 0)) * 31) + (this.oAuthAccessTokenSecret != null ? this.oAuthAccessTokenSecret.hashCode() : 0)) * 31) + (this.oAuth2TokenType != null ? this.oAuth2TokenType.hashCode() : 0)) * 31) + (this.oAuth2AccessToken != null ? this.oAuth2AccessToken.hashCode() : 0)) * 31) + (this.oAuth2Scope != null ? this.oAuth2Scope.hashCode() : 0)) * 31) + (this.oAuthRequestTokenURL != null ? this.oAuthRequestTokenURL.hashCode() : 0)) * 31) + (this.oAuthAuthorizationURL != null ? this.oAuthAuthorizationURL.hashCode() : 0)) * 31) + (this.oAuthAccessTokenURL != null ? this.oAuthAccessTokenURL.hashCode() : 0)) * 31) + (this.oAuthAuthenticationURL != null ? this.oAuthAuthenticationURL.hashCode() : 0)) * 31) + (this.oAuth2TokenURL != null ? this.oAuth2TokenURL.hashCode() : 0)) * 31) + (this.oAuth2InvalidateTokenURL != null ? this.oAuth2InvalidateTokenURL.hashCode() : 0)) * 31) + (this.restBaseURL != null ? this.restBaseURL.hashCode() : 0)) * 31) + (this.uploadBaseURL != null ? this.uploadBaseURL.hashCode() : 0)) * 31) + (this.streamBaseURL != null ? this.streamBaseURL.hashCode() : 0)) * 31) + (this.userStreamBaseURL != null ? this.userStreamBaseURL.hashCode() : 0)) * 31) + (this.siteStreamBaseURL != null ? this.siteStreamBaseURL.hashCode() : 0)) * 31) + (this.dispatcherImpl != null ? this.dispatcherImpl.hashCode() : 0)) * 31) + this.asyncNumThreads) * 31) + (this.loggerFactory != null ? this.loggerFactory.hashCode() : 0)) * 31) + ((int) (this.contributingTo ^ (this.contributingTo >>> 32)))) * 31) + (this.includeMyRetweetEnabled ? 1 : 0)) * 31) + (this.includeEntitiesEnabled ? 1 : 0)) * 31) + (this.trimUserEnabled ? 1 : 0)) * 31) + (this.jsonStoreEnabled ? 1 : 0)) * 31) + (this.mbeanEnabled ? 1 : 0)) * 31) + (this.userStreamRepliesAllEnabled ? 1 : 0)) * 31) + (this.userStreamWithFollowingsEnabled ? 1 : 0)) * 31) + (this.stallWarningsEnabled ? 1 : 0)) * 31) + (this.applicationOnlyAuthEnabled ? 1 : 0)) * 31) + (this.mediaProvider != null ? this.mediaProvider.hashCode() : 0)) * 31) + (this.mediaProviderAPIKey != null ? this.mediaProviderAPIKey.hashCode() : 0)) * 31) + (this.mediaProviderParameters != null ? this.mediaProviderParameters.hashCode() : 0)) * 31;
        if (!this.daemonEnabled) {
            i = 0;
        }
        return hashCode + i;
    }

    public boolean isApplicationOnlyAuthEnabled() {
        return this.applicationOnlyAuthEnabled;
    }

    public boolean isDaemonEnabled() {
        return this.daemonEnabled;
    }

    public final boolean isDebugEnabled() {
        return this.debug;
    }

    public boolean isIncludeEntitiesEnabled() {
        return this.includeEntitiesEnabled;
    }

    public boolean isIncludeMyRetweetEnabled() {
        return this.includeMyRetweetEnabled;
    }

    public boolean isJSONStoreEnabled() {
        return this.jsonStoreEnabled;
    }

    public boolean isMBeanEnabled() {
        return this.mbeanEnabled;
    }

    public boolean isStallWarningsEnabled() {
        return this.stallWarningsEnabled;
    }

    public boolean isTrimUserEnabled() {
        return this.trimUserEnabled;
    }

    public boolean isUserStreamRepliesAllEnabled() {
        return this.userStreamRepliesAllEnabled;
    }

    public boolean isUserStreamWithFollowingsEnabled() {
        return this.userStreamWithFollowingsEnabled;
    }

    /* access modifiers changed from: protected */
    public Object readResolve() throws ObjectStreamException {
        return getInstance(this);
    }

    /* access modifiers changed from: protected */
    public final void setApplicationOnlyAuthEnabled(boolean z) {
        this.applicationOnlyAuthEnabled = z;
    }

    /* access modifiers changed from: protected */
    public final void setAsyncNumThreads(int i) {
        this.asyncNumThreads = i;
    }

    /* access modifiers changed from: protected */
    public final void setContributingTo(long j) {
        this.contributingTo = j;
    }

    /* access modifiers changed from: protected */
    public void setDaemonEnabled(boolean z) {
        this.daemonEnabled = z;
    }

    /* access modifiers changed from: protected */
    public final void setDebug(boolean z) {
        this.debug = z;
    }

    /* access modifiers changed from: protected */
    public final void setDispatcherImpl(String str) {
        this.dispatcherImpl = str;
    }

    /* access modifiers changed from: protected */
    public final void setGZIPEnabled(boolean z) {
        this.httpConf = new MyHttpClientConfiguration(this.httpConf.getHttpProxyHost(), this.httpConf.getHttpProxyUser(), this.httpConf.getHttpProxyPassword(), this.httpConf.getHttpProxyPort(), this.httpConf.getHttpConnectionTimeout(), this.httpConf.getHttpReadTimeout(), this.httpConf.isPrettyDebugEnabled(), z);
    }

    /* access modifiers changed from: protected */
    public final void setHttpConnectionTimeout(int i) {
        this.httpConf = new MyHttpClientConfiguration(this.httpConf.getHttpProxyHost(), this.httpConf.getHttpProxyUser(), this.httpConf.getHttpProxyPassword(), this.httpConf.getHttpProxyPort(), i, this.httpConf.getHttpReadTimeout(), this.httpConf.isPrettyDebugEnabled(), this.httpConf.isGZIPEnabled());
    }

    /* access modifiers changed from: protected */
    public final void setHttpProxyHost(String str) {
        this.httpConf = new MyHttpClientConfiguration(str, this.httpConf.getHttpProxyUser(), this.httpConf.getHttpProxyPassword(), this.httpConf.getHttpProxyPort(), this.httpConf.getHttpConnectionTimeout(), this.httpConf.getHttpReadTimeout(), this.httpConf.isPrettyDebugEnabled(), this.httpConf.isGZIPEnabled());
    }

    /* access modifiers changed from: protected */
    public final void setHttpProxyPassword(String str) {
        this.httpConf = new MyHttpClientConfiguration(this.httpConf.getHttpProxyHost(), this.httpConf.getHttpProxyUser(), str, this.httpConf.getHttpProxyPort(), this.httpConf.getHttpConnectionTimeout(), this.httpConf.getHttpReadTimeout(), this.httpConf.isPrettyDebugEnabled(), this.httpConf.isGZIPEnabled());
    }

    /* access modifiers changed from: protected */
    public final void setHttpProxyPort(int i) {
        this.httpConf = new MyHttpClientConfiguration(this.httpConf.getHttpProxyHost(), this.httpConf.getHttpProxyUser(), this.httpConf.getHttpProxyPassword(), i, this.httpConf.getHttpConnectionTimeout(), this.httpConf.getHttpReadTimeout(), this.httpConf.isPrettyDebugEnabled(), this.httpConf.isGZIPEnabled());
    }

    /* access modifiers changed from: protected */
    public final void setHttpProxyUser(String str) {
        this.httpConf = new MyHttpClientConfiguration(this.httpConf.getHttpProxyHost(), str, this.httpConf.getHttpProxyPassword(), this.httpConf.getHttpProxyPort(), this.httpConf.getHttpConnectionTimeout(), this.httpConf.getHttpReadTimeout(), this.httpConf.isPrettyDebugEnabled(), this.httpConf.isGZIPEnabled());
    }

    /* access modifiers changed from: protected */
    public final void setHttpReadTimeout(int i) {
        this.httpConf = new MyHttpClientConfiguration(this.httpConf.getHttpProxyHost(), this.httpConf.getHttpProxyUser(), this.httpConf.getHttpProxyPassword(), this.httpConf.getHttpProxyPort(), this.httpConf.getHttpConnectionTimeout(), i, this.httpConf.isPrettyDebugEnabled(), this.httpConf.isGZIPEnabled());
    }

    /* access modifiers changed from: protected */
    public final void setHttpRetryCount(int i) {
        this.httpRetryCount = i;
    }

    /* access modifiers changed from: protected */
    public final void setHttpRetryIntervalSeconds(int i) {
        this.httpRetryIntervalSeconds = i;
    }

    /* access modifiers changed from: protected */
    public final void setHttpStreamingReadTimeout(int i) {
        this.httpStreamingReadTimeout = i;
    }

    /* access modifiers changed from: protected */
    public void setIncludeEntitiesEnabled(boolean z) {
        this.includeEntitiesEnabled = z;
    }

    public void setIncludeMyRetweetEnabled(boolean z) {
        this.includeMyRetweetEnabled = z;
    }

    /* access modifiers changed from: protected */
    public final void setJSONStoreEnabled(boolean z) {
        this.jsonStoreEnabled = z;
    }

    /* access modifiers changed from: protected */
    public final void setLoggerFactory(String str) {
        this.loggerFactory = str;
    }

    /* access modifiers changed from: protected */
    public final void setMBeanEnabled(boolean z) {
        this.mbeanEnabled = z;
    }

    /* access modifiers changed from: protected */
    public final void setMediaProvider(String str) {
        this.mediaProvider = str;
    }

    /* access modifiers changed from: protected */
    public final void setMediaProviderAPIKey(String str) {
        this.mediaProviderAPIKey = str;
    }

    /* access modifiers changed from: protected */
    public final void setMediaProviderParameters(Properties properties) {
        this.mediaProviderParameters = properties;
    }

    /* access modifiers changed from: protected */
    public final void setOAuth2AccessToken(String str) {
        this.oAuth2AccessToken = str;
    }

    /* access modifiers changed from: protected */
    public final void setOAuth2InvalidateTokenURL(String str) {
        this.oAuth2InvalidateTokenURL = str;
    }

    /* access modifiers changed from: protected */
    public final void setOAuth2Scope(String str) {
        this.oAuth2Scope = str;
    }

    /* access modifiers changed from: protected */
    public final void setOAuth2TokenType(String str) {
        this.oAuth2TokenType = str;
    }

    /* access modifiers changed from: protected */
    public final void setOAuth2TokenURL(String str) {
        this.oAuth2TokenURL = str;
    }

    /* access modifiers changed from: protected */
    public final void setOAuthAccessToken(String str) {
        this.oAuthAccessToken = str;
    }

    /* access modifiers changed from: protected */
    public final void setOAuthAccessTokenSecret(String str) {
        this.oAuthAccessTokenSecret = str;
    }

    /* access modifiers changed from: protected */
    public final void setOAuthAccessTokenURL(String str) {
        this.oAuthAccessTokenURL = str;
    }

    /* access modifiers changed from: protected */
    public final void setOAuthAuthenticationURL(String str) {
        this.oAuthAuthenticationURL = str;
    }

    /* access modifiers changed from: protected */
    public final void setOAuthAuthorizationURL(String str) {
        this.oAuthAuthorizationURL = str;
    }

    /* access modifiers changed from: protected */
    public final void setOAuthConsumerKey(String str) {
        this.oAuthConsumerKey = str;
    }

    /* access modifiers changed from: protected */
    public final void setOAuthConsumerSecret(String str) {
        this.oAuthConsumerSecret = str;
    }

    /* access modifiers changed from: protected */
    public final void setOAuthRequestTokenURL(String str) {
        this.oAuthRequestTokenURL = str;
    }

    /* access modifiers changed from: protected */
    public final void setPassword(String str) {
        this.password = str;
    }

    /* access modifiers changed from: protected */
    public final void setPrettyDebugEnabled(boolean z) {
        this.httpConf = new MyHttpClientConfiguration(this.httpConf.getHttpProxyHost(), this.httpConf.getHttpProxyUser(), this.httpConf.getHttpProxyPassword(), this.httpConf.getHttpProxyPort(), this.httpConf.getHttpConnectionTimeout(), this.httpConf.getHttpReadTimeout(), z, this.httpConf.isGZIPEnabled());
    }

    /* access modifiers changed from: protected */
    public final void setRestBaseURL(String str) {
        this.restBaseURL = str;
    }

    /* access modifiers changed from: protected */
    public final void setSiteStreamBaseURL(String str) {
        this.siteStreamBaseURL = str;
    }

    /* access modifiers changed from: protected */
    public final void setStallWarningsEnabled(boolean z) {
        this.stallWarningsEnabled = z;
    }

    /* access modifiers changed from: protected */
    public final void setStreamBaseURL(String str) {
        this.streamBaseURL = str;
    }

    public void setTrimUserEnabled(boolean z) {
        this.trimUserEnabled = z;
    }

    /* access modifiers changed from: protected */
    public final void setUploadBaseURL(String str) {
        this.uploadBaseURL = str;
    }

    /* access modifiers changed from: protected */
    public final void setUser(String str) {
        this.user = str;
    }

    /* access modifiers changed from: protected */
    public final void setUserStreamBaseURL(String str) {
        this.userStreamBaseURL = str;
    }

    /* access modifiers changed from: protected */
    public final void setUserStreamRepliesAllEnabled(boolean z) {
        this.userStreamRepliesAllEnabled = z;
    }

    /* access modifiers changed from: protected */
    public final void setUserStreamWithFollowingsEnabled(boolean z) {
        this.userStreamWithFollowingsEnabled = z;
    }

    public String toString() {
        return "ConfigurationBase{debug=" + this.debug + ", user='" + this.user + '\'' + ", password='" + this.password + '\'' + ", httpConf=" + this.httpConf + ", httpStreamingReadTimeout=" + this.httpStreamingReadTimeout + ", httpRetryCount=" + this.httpRetryCount + ", httpRetryIntervalSeconds=" + this.httpRetryIntervalSeconds + ", oAuthConsumerKey='" + this.oAuthConsumerKey + '\'' + ", oAuthConsumerSecret='" + this.oAuthConsumerSecret + '\'' + ", oAuthAccessToken='" + this.oAuthAccessToken + '\'' + ", oAuthAccessTokenSecret='" + this.oAuthAccessTokenSecret + '\'' + ", oAuth2TokenType='" + this.oAuth2TokenType + '\'' + ", oAuth2AccessToken='" + this.oAuth2AccessToken + '\'' + ", oAuth2Scope='" + this.oAuth2Scope + '\'' + ", oAuthRequestTokenURL='" + this.oAuthRequestTokenURL + '\'' + ", oAuthAuthorizationURL='" + this.oAuthAuthorizationURL + '\'' + ", oAuthAccessTokenURL='" + this.oAuthAccessTokenURL + '\'' + ", oAuthAuthenticationURL='" + this.oAuthAuthenticationURL + '\'' + ", oAuth2TokenURL='" + this.oAuth2TokenURL + '\'' + ", oAuth2InvalidateTokenURL='" + this.oAuth2InvalidateTokenURL + '\'' + ", restBaseURL='" + this.restBaseURL + '\'' + ", uploadBaseURL='" + this.uploadBaseURL + '\'' + ", streamBaseURL='" + this.streamBaseURL + '\'' + ", userStreamBaseURL='" + this.userStreamBaseURL + '\'' + ", siteStreamBaseURL='" + this.siteStreamBaseURL + '\'' + ", dispatcherImpl='" + this.dispatcherImpl + '\'' + ", asyncNumThreads=" + this.asyncNumThreads + ", loggerFactory='" + this.loggerFactory + '\'' + ", contributingTo=" + this.contributingTo + ", includeMyRetweetEnabled=" + this.includeMyRetweetEnabled + ", includeEntitiesEnabled=" + this.includeEntitiesEnabled + ", trimUserEnabled=" + this.trimUserEnabled + ", jsonStoreEnabled=" + this.jsonStoreEnabled + ", mbeanEnabled=" + this.mbeanEnabled + ", userStreamRepliesAllEnabled=" + this.userStreamRepliesAllEnabled + ", userStreamWithFollowingsEnabled=" + this.userStreamWithFollowingsEnabled + ", stallWarningsEnabled=" + this.stallWarningsEnabled + ", applicationOnlyAuthEnabled=" + this.applicationOnlyAuthEnabled + ", mediaProvider='" + this.mediaProvider + '\'' + ", mediaProviderAPIKey='" + this.mediaProviderAPIKey + '\'' + ", mediaProviderParameters=" + this.mediaProviderParameters + ", daemonEnabled=" + this.daemonEnabled + '}';
    }
}
