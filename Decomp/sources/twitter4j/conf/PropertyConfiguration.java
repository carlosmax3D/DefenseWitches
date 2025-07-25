package twitter4j.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import jp.stargarage.g2metrics.BuildConfig;
import twitter4j.HttpClientConfiguration;

public final class PropertyConfiguration extends ConfigurationBase implements Serializable {
    private static final String APPLICATION_ONLY_AUTH_ENABLED = "enableApplicationOnlyAuth";
    private static final String ASYNC_DAEMON_ENABLED = "async.daemonEnabled";
    private static final String ASYNC_DISPATCHER_IMPL = "async.dispatcherImpl";
    private static final String ASYNC_NUM_THREADS = "async.numThreads";
    private static final String CONTRIBUTING_TO = "contributingTo";
    private static final String DEBUG = "debug";
    private static final String HTTP_CONNECTION_TIMEOUT = "http.connectionTimeout";
    private static final String HTTP_GZIP = "http.gzip";
    private static final String HTTP_PRETTY_DEBUG = "http.prettyDebug";
    private static final String HTTP_PROXY_HOST = "http.proxyHost";
    private static final String HTTP_PROXY_HOST_FALLBACK = "http.proxyHost";
    private static final String HTTP_PROXY_PASSWORD = "http.proxyPassword";
    private static final String HTTP_PROXY_PORT = "http.proxyPort";
    private static final String HTTP_PROXY_PORT_FALLBACK = "http.proxyPort";
    private static final String HTTP_PROXY_USER = "http.proxyUser";
    private static final String HTTP_READ_TIMEOUT = "http.readTimeout";
    private static final String HTTP_RETRY_COUNT = "http.retryCount";
    private static final String HTTP_RETRY_INTERVAL_SECS = "http.retryIntervalSecs";
    private static final String HTTP_STREAMING_READ_TIMEOUT = "http.streamingReadTimeout";
    private static final String INCLUDE_ENTITIES = "includeEntities";
    private static final String INCLUDE_MY_RETWEET = "includeMyRetweet";
    private static final String JSON_STORE_ENABLED = "jsonStoreEnabled";
    private static final String LOGGER_FACTORY = "loggerFactory";
    private static final String MBEAN_ENABLED = "mbeanEnabled";
    private static final String MEDIA_PROVIDER = "media.provider";
    private static final String MEDIA_PROVIDER_API_KEY = "media.providerAPIKey";
    private static final String MEDIA_PROVIDER_PARAMETERS = "media.providerParameters";
    private static final String OAUTH2_ACCESS_TOKEN = "oauth2.accessToken";
    private static final String OAUTH2_INVALIDATE_TOKEN_URL = "oauth2.invalidateTokenURL";
    private static final String OAUTH2_SCOPE = "oauth2.scope";
    private static final String OAUTH2_TOKEN_TYPE = "oauth2.tokenType";
    private static final String OAUTH2_TOKEN_URL = "oauth2.tokenURL";
    private static final String OAUTH_ACCESS_TOKEN = "oauth.accessToken";
    private static final String OAUTH_ACCESS_TOKEN_SECRET = "oauth.accessTokenSecret";
    private static final String OAUTH_ACCESS_TOKEN_URL = "oauth.accessTokenURL";
    private static final String OAUTH_AUTHENTICATION_URL = "oauth.authenticationURL";
    private static final String OAUTH_AUTHORIZATION_URL = "oauth.authorizationURL";
    private static final String OAUTH_CONSUMER_KEY = "oauth.consumerKey";
    private static final String OAUTH_CONSUMER_SECRET = "oauth.consumerSecret";
    private static final String OAUTH_REQUEST_TOKEN_URL = "oauth.requestTokenURL";
    private static final String PASSWORD = "password";
    private static final String REST_BASE_URL = "restBaseURL";
    private static final String SITE_STREAM_BASE_URL = "siteStreamBaseURL";
    private static final String STREAM_BASE_URL = "streamBaseURL";
    private static final String STREAM_STALL_WARNINGS_ENABLED = "stream.enableStallWarnings";
    private static final String STREAM_USER_REPLIES_ALL = "stream.user.repliesAll";
    private static final String STREAM_USER_WITH_FOLLOWINGS = "stream.user.withFollowings";
    private static final String USER = "user";
    private static final String USER_STREAM_BASE_URL = "userStreamBaseURL";
    private static final long serialVersionUID = -7262615247923693252L;
    private String OAuth2Scope;

    PropertyConfiguration() {
        this("/");
    }

    public PropertyConfiguration(InputStream inputStream) {
        Properties properties = new Properties();
        loadProperties(properties, inputStream);
        setFieldsWithTreePath(properties, "/");
    }

    PropertyConfiguration(String str) {
        Properties properties;
        try {
            properties = (Properties) System.getProperties().clone();
            try {
                Map<String, String> map = System.getenv();
                for (String next : map.keySet()) {
                    properties.setProperty(next, map.get(next));
                }
            } catch (SecurityException e) {
            }
            normalize(properties);
        } catch (SecurityException e2) {
            properties = new Properties();
        }
        loadProperties(properties, "." + File.separatorChar + "twitter4j.properties");
        loadProperties(properties, Configuration.class.getResourceAsStream("/twitter4j.properties"));
        loadProperties(properties, Configuration.class.getResourceAsStream("/WEB-INF/twitter4j.properties"));
        try {
            loadProperties(properties, (InputStream) new FileInputStream("WEB-INF/twitter4j.properties"));
        } catch (FileNotFoundException | SecurityException e3) {
        }
        setFieldsWithTreePath(properties, str);
    }

    public PropertyConfiguration(Properties properties) {
        this(properties, "/");
    }

    public PropertyConfiguration(Properties properties, String str) {
        setFieldsWithTreePath(properties, str);
    }

    private boolean loadProperties(Properties properties, InputStream inputStream) {
        try {
            properties.load(inputStream);
            normalize(properties);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x002f A[SYNTHETIC, Splitter:B:20:0x002f] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0038 A[SYNTHETIC, Splitter:B:25:0x0038] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean loadProperties(java.util.Properties r6, java.lang.String r7) {
        /*
            r5 = this;
            r1 = 0
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x002c, all -> 0x0035 }
            r0.<init>(r7)     // Catch:{ Exception -> 0x002c, all -> 0x0035 }
            boolean r3 = r0.exists()     // Catch:{ Exception -> 0x002c, all -> 0x0035 }
            if (r3 == 0) goto L_0x0025
            boolean r3 = r0.isFile()     // Catch:{ Exception -> 0x002c, all -> 0x0035 }
            if (r3 == 0) goto L_0x0025
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x002c, all -> 0x0035 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x002c, all -> 0x0035 }
            r6.load(r2)     // Catch:{ Exception -> 0x0045, all -> 0x0042 }
            r5.normalize(r6)     // Catch:{ Exception -> 0x0045, all -> 0x0042 }
            r3 = 1
            if (r2 == 0) goto L_0x0023
            r2.close()     // Catch:{ IOException -> 0x003c }
        L_0x0023:
            r1 = r2
        L_0x0024:
            return r3
        L_0x0025:
            if (r1 == 0) goto L_0x002a
            r1.close()     // Catch:{ IOException -> 0x003e }
        L_0x002a:
            r3 = 0
            goto L_0x0024
        L_0x002c:
            r3 = move-exception
        L_0x002d:
            if (r1 == 0) goto L_0x002a
            r1.close()     // Catch:{ IOException -> 0x0033 }
            goto L_0x002a
        L_0x0033:
            r3 = move-exception
            goto L_0x002a
        L_0x0035:
            r3 = move-exception
        L_0x0036:
            if (r1 == 0) goto L_0x003b
            r1.close()     // Catch:{ IOException -> 0x0040 }
        L_0x003b:
            throw r3
        L_0x003c:
            r4 = move-exception
            goto L_0x0023
        L_0x003e:
            r3 = move-exception
            goto L_0x002a
        L_0x0040:
            r4 = move-exception
            goto L_0x003b
        L_0x0042:
            r3 = move-exception
            r1 = r2
            goto L_0x0036
        L_0x0045:
            r3 = move-exception
            r1 = r2
            goto L_0x002d
        */
        throw new UnsupportedOperationException("Method not decompiled: twitter4j.conf.PropertyConfiguration.loadProperties(java.util.Properties, java.lang.String):boolean");
    }

    private void normalize(Properties properties) {
        Set<String> keySet = properties.keySet();
        ArrayList arrayList = new ArrayList(10);
        for (String str : keySet) {
            if (-1 != str.indexOf("twitter4j.")) {
                arrayList.add(str);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            String property = properties.getProperty(str2);
            int indexOf = str2.indexOf("twitter4j.");
            properties.setProperty(str2.substring(0, indexOf) + str2.substring(indexOf + 10), property);
        }
    }

    private boolean notNull(Properties properties, String str, String str2) {
        return properties.getProperty(new StringBuilder().append(str).append(str2).toString()) != null;
    }

    private void setFieldsWithPrefix(Properties properties, String str) {
        if (notNull(properties, str, DEBUG)) {
            setDebug(getBoolean(properties, str, DEBUG));
        }
        if (notNull(properties, str, USER)) {
            setUser(getString(properties, str, USER));
        }
        if (notNull(properties, str, PASSWORD)) {
            setPassword(getString(properties, str, PASSWORD));
        }
        if (notNull(properties, str, HTTP_PRETTY_DEBUG)) {
            setPrettyDebugEnabled(getBoolean(properties, str, HTTP_PRETTY_DEBUG));
        }
        if (notNull(properties, str, HTTP_GZIP)) {
            setGZIPEnabled(getBoolean(properties, str, HTTP_GZIP));
        }
        if (notNull(properties, str, "http.proxyHost")) {
            setHttpProxyHost(getString(properties, str, "http.proxyHost"));
        } else if (notNull(properties, str, "http.proxyHost")) {
            setHttpProxyHost(getString(properties, str, "http.proxyHost"));
        }
        if (notNull(properties, str, HTTP_PROXY_USER)) {
            setHttpProxyUser(getString(properties, str, HTTP_PROXY_USER));
        }
        if (notNull(properties, str, HTTP_PROXY_PASSWORD)) {
            setHttpProxyPassword(getString(properties, str, HTTP_PROXY_PASSWORD));
        }
        if (notNull(properties, str, "http.proxyPort")) {
            setHttpProxyPort(getIntProperty(properties, str, "http.proxyPort"));
        } else if (notNull(properties, str, "http.proxyPort")) {
            setHttpProxyPort(getIntProperty(properties, str, "http.proxyPort"));
        }
        if (notNull(properties, str, HTTP_CONNECTION_TIMEOUT)) {
            setHttpConnectionTimeout(getIntProperty(properties, str, HTTP_CONNECTION_TIMEOUT));
        }
        if (notNull(properties, str, HTTP_READ_TIMEOUT)) {
            setHttpReadTimeout(getIntProperty(properties, str, HTTP_READ_TIMEOUT));
        }
        if (notNull(properties, str, HTTP_STREAMING_READ_TIMEOUT)) {
            setHttpStreamingReadTimeout(getIntProperty(properties, str, HTTP_STREAMING_READ_TIMEOUT));
        }
        if (notNull(properties, str, HTTP_RETRY_COUNT)) {
            setHttpRetryCount(getIntProperty(properties, str, HTTP_RETRY_COUNT));
        }
        if (notNull(properties, str, HTTP_RETRY_INTERVAL_SECS)) {
            setHttpRetryIntervalSeconds(getIntProperty(properties, str, HTTP_RETRY_INTERVAL_SECS));
        }
        if (notNull(properties, str, OAUTH_CONSUMER_KEY)) {
            setOAuthConsumerKey(getString(properties, str, OAUTH_CONSUMER_KEY));
        }
        if (notNull(properties, str, OAUTH_CONSUMER_SECRET)) {
            setOAuthConsumerSecret(getString(properties, str, OAUTH_CONSUMER_SECRET));
        }
        if (notNull(properties, str, OAUTH_ACCESS_TOKEN)) {
            setOAuthAccessToken(getString(properties, str, OAUTH_ACCESS_TOKEN));
        }
        if (notNull(properties, str, OAUTH_ACCESS_TOKEN_SECRET)) {
            setOAuthAccessTokenSecret(getString(properties, str, OAUTH_ACCESS_TOKEN_SECRET));
        }
        if (notNull(properties, str, OAUTH2_TOKEN_TYPE)) {
            setOAuth2TokenType(getString(properties, str, OAUTH2_TOKEN_TYPE));
        }
        if (notNull(properties, str, OAUTH2_ACCESS_TOKEN)) {
            setOAuth2AccessToken(getString(properties, str, OAUTH2_ACCESS_TOKEN));
        }
        if (notNull(properties, str, OAUTH2_SCOPE)) {
            setOAuth2Scope(getString(properties, str, OAUTH2_SCOPE));
        }
        if (notNull(properties, str, ASYNC_NUM_THREADS)) {
            setAsyncNumThreads(getIntProperty(properties, str, ASYNC_NUM_THREADS));
        }
        if (notNull(properties, str, ASYNC_DAEMON_ENABLED)) {
            setDaemonEnabled(getBoolean(properties, str, ASYNC_DAEMON_ENABLED));
        }
        if (notNull(properties, str, CONTRIBUTING_TO)) {
            setContributingTo(getLongProperty(properties, str, CONTRIBUTING_TO));
        }
        if (notNull(properties, str, ASYNC_DISPATCHER_IMPL)) {
            setDispatcherImpl(getString(properties, str, ASYNC_DISPATCHER_IMPL));
        }
        if (notNull(properties, str, OAUTH_REQUEST_TOKEN_URL)) {
            setOAuthRequestTokenURL(getString(properties, str, OAUTH_REQUEST_TOKEN_URL));
        }
        if (notNull(properties, str, OAUTH_AUTHORIZATION_URL)) {
            setOAuthAuthorizationURL(getString(properties, str, OAUTH_AUTHORIZATION_URL));
        }
        if (notNull(properties, str, OAUTH_ACCESS_TOKEN_URL)) {
            setOAuthAccessTokenURL(getString(properties, str, OAUTH_ACCESS_TOKEN_URL));
        }
        if (notNull(properties, str, OAUTH_AUTHENTICATION_URL)) {
            setOAuthAuthenticationURL(getString(properties, str, OAUTH_AUTHENTICATION_URL));
        }
        if (notNull(properties, str, OAUTH2_TOKEN_URL)) {
            setOAuth2TokenURL(getString(properties, str, OAUTH2_TOKEN_URL));
        }
        if (notNull(properties, str, OAUTH2_INVALIDATE_TOKEN_URL)) {
            setOAuth2InvalidateTokenURL(getString(properties, str, OAUTH2_INVALIDATE_TOKEN_URL));
        }
        if (notNull(properties, str, REST_BASE_URL)) {
            setRestBaseURL(getString(properties, str, REST_BASE_URL));
        }
        if (notNull(properties, str, STREAM_BASE_URL)) {
            setStreamBaseURL(getString(properties, str, STREAM_BASE_URL));
        }
        if (notNull(properties, str, USER_STREAM_BASE_URL)) {
            setUserStreamBaseURL(getString(properties, str, USER_STREAM_BASE_URL));
        }
        if (notNull(properties, str, SITE_STREAM_BASE_URL)) {
            setSiteStreamBaseURL(getString(properties, str, SITE_STREAM_BASE_URL));
        }
        if (notNull(properties, str, INCLUDE_MY_RETWEET)) {
            setIncludeMyRetweetEnabled(getBoolean(properties, str, INCLUDE_MY_RETWEET));
        }
        if (notNull(properties, str, INCLUDE_ENTITIES)) {
            setIncludeEntitiesEnabled(getBoolean(properties, str, INCLUDE_ENTITIES));
        }
        if (notNull(properties, str, LOGGER_FACTORY)) {
            setLoggerFactory(getString(properties, str, LOGGER_FACTORY));
        }
        if (notNull(properties, str, JSON_STORE_ENABLED)) {
            setJSONStoreEnabled(getBoolean(properties, str, JSON_STORE_ENABLED));
        }
        if (notNull(properties, str, MBEAN_ENABLED)) {
            setMBeanEnabled(getBoolean(properties, str, MBEAN_ENABLED));
        }
        if (notNull(properties, str, STREAM_USER_REPLIES_ALL)) {
            setUserStreamRepliesAllEnabled(getBoolean(properties, str, STREAM_USER_REPLIES_ALL));
        }
        if (notNull(properties, str, STREAM_USER_WITH_FOLLOWINGS)) {
            setUserStreamWithFollowingsEnabled(getBoolean(properties, str, STREAM_USER_WITH_FOLLOWINGS));
        }
        if (notNull(properties, str, STREAM_STALL_WARNINGS_ENABLED)) {
            setStallWarningsEnabled(getBoolean(properties, str, STREAM_STALL_WARNINGS_ENABLED));
        }
        if (notNull(properties, str, APPLICATION_ONLY_AUTH_ENABLED)) {
            setApplicationOnlyAuthEnabled(getBoolean(properties, str, APPLICATION_ONLY_AUTH_ENABLED));
        }
        if (notNull(properties, str, MEDIA_PROVIDER)) {
            setMediaProvider(getString(properties, str, MEDIA_PROVIDER));
        }
        if (notNull(properties, str, MEDIA_PROVIDER_API_KEY)) {
            setMediaProviderAPIKey(getString(properties, str, MEDIA_PROVIDER_API_KEY));
        }
        if (notNull(properties, str, MEDIA_PROVIDER_PARAMETERS)) {
            String[] split = getString(properties, str, MEDIA_PROVIDER_PARAMETERS).split("&");
            Properties properties2 = new Properties();
            for (String split2 : split) {
                String[] split3 = split2.split("=");
                properties2.setProperty(split3[0], split3[1]);
            }
            setMediaProviderParameters(properties2);
        }
        cacheInstance();
    }

    private void setFieldsWithTreePath(Properties properties, String str) {
        setFieldsWithPrefix(properties, BuildConfig.FLAVOR);
        String str2 = null;
        for (String str3 : str.split("/")) {
            if (!BuildConfig.FLAVOR.equals(str3)) {
                str2 = str2 == null ? str3 + "." : str2 + str3 + ".";
                setFieldsWithPrefix(properties, str2);
            }
        }
    }

    public /* bridge */ /* synthetic */ void dumpConfiguration() {
        super.dumpConfiguration();
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* access modifiers changed from: package-private */
    public boolean getBoolean(Properties properties, String str, String str2) {
        return Boolean.valueOf(properties.getProperty(str + str2)).booleanValue();
    }

    public /* bridge */ /* synthetic */ String getDispatcherImpl() {
        return super.getDispatcherImpl();
    }

    public /* bridge */ /* synthetic */ HttpClientConfiguration getHttpClientConfiguration() {
        return super.getHttpClientConfiguration();
    }

    public /* bridge */ /* synthetic */ int getHttpStreamingReadTimeout() {
        return super.getHttpStreamingReadTimeout();
    }

    /* access modifiers changed from: package-private */
    public int getIntProperty(Properties properties, String str, String str2) {
        try {
            return Integer.parseInt(properties.getProperty(str + str2));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public /* bridge */ /* synthetic */ String getLoggerFactory() {
        return super.getLoggerFactory();
    }

    /* access modifiers changed from: package-private */
    public long getLongProperty(Properties properties, String str, String str2) {
        try {
            return Long.parseLong(properties.getProperty(str + str2));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public /* bridge */ /* synthetic */ String getMediaProvider() {
        return super.getMediaProvider();
    }

    public /* bridge */ /* synthetic */ String getMediaProviderAPIKey() {
        return super.getMediaProviderAPIKey();
    }

    public /* bridge */ /* synthetic */ Properties getMediaProviderParameters() {
        return super.getMediaProviderParameters();
    }

    public /* bridge */ /* synthetic */ String getOAuth2AccessToken() {
        return super.getOAuth2AccessToken();
    }

    public /* bridge */ /* synthetic */ String getOAuth2InvalidateTokenURL() {
        return super.getOAuth2InvalidateTokenURL();
    }

    public /* bridge */ /* synthetic */ String getOAuth2Scope() {
        return super.getOAuth2Scope();
    }

    public /* bridge */ /* synthetic */ String getOAuth2TokenType() {
        return super.getOAuth2TokenType();
    }

    public /* bridge */ /* synthetic */ String getOAuth2TokenURL() {
        return super.getOAuth2TokenURL();
    }

    public /* bridge */ /* synthetic */ String getOAuthAccessToken() {
        return super.getOAuthAccessToken();
    }

    public /* bridge */ /* synthetic */ String getOAuthAccessTokenSecret() {
        return super.getOAuthAccessTokenSecret();
    }

    public /* bridge */ /* synthetic */ String getOAuthAccessTokenURL() {
        return super.getOAuthAccessTokenURL();
    }

    public /* bridge */ /* synthetic */ String getOAuthAuthenticationURL() {
        return super.getOAuthAuthenticationURL();
    }

    public /* bridge */ /* synthetic */ String getOAuthAuthorizationURL() {
        return super.getOAuthAuthorizationURL();
    }

    public /* bridge */ /* synthetic */ String getOAuthRequestTokenURL() {
        return super.getOAuthRequestTokenURL();
    }

    public /* bridge */ /* synthetic */ String getRestBaseURL() {
        return super.getRestBaseURL();
    }

    public /* bridge */ /* synthetic */ String getSiteStreamBaseURL() {
        return super.getSiteStreamBaseURL();
    }

    public /* bridge */ /* synthetic */ String getStreamBaseURL() {
        return super.getStreamBaseURL();
    }

    /* access modifiers changed from: package-private */
    public String getString(Properties properties, String str, String str2) {
        return properties.getProperty(str + str2);
    }

    public /* bridge */ /* synthetic */ String getUploadBaseURL() {
        return super.getUploadBaseURL();
    }

    public /* bridge */ /* synthetic */ String getUserStreamBaseURL() {
        return super.getUserStreamBaseURL();
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ boolean isApplicationOnlyAuthEnabled() {
        return super.isApplicationOnlyAuthEnabled();
    }

    public /* bridge */ /* synthetic */ boolean isDaemonEnabled() {
        return super.isDaemonEnabled();
    }

    public /* bridge */ /* synthetic */ boolean isIncludeEntitiesEnabled() {
        return super.isIncludeEntitiesEnabled();
    }

    public /* bridge */ /* synthetic */ boolean isIncludeMyRetweetEnabled() {
        return super.isIncludeMyRetweetEnabled();
    }

    public /* bridge */ /* synthetic */ boolean isJSONStoreEnabled() {
        return super.isJSONStoreEnabled();
    }

    public /* bridge */ /* synthetic */ boolean isMBeanEnabled() {
        return super.isMBeanEnabled();
    }

    public /* bridge */ /* synthetic */ boolean isStallWarningsEnabled() {
        return super.isStallWarningsEnabled();
    }

    public /* bridge */ /* synthetic */ boolean isTrimUserEnabled() {
        return super.isTrimUserEnabled();
    }

    public /* bridge */ /* synthetic */ boolean isUserStreamRepliesAllEnabled() {
        return super.isUserStreamRepliesAllEnabled();
    }

    public /* bridge */ /* synthetic */ boolean isUserStreamWithFollowingsEnabled() {
        return super.isUserStreamWithFollowingsEnabled();
    }

    /* access modifiers changed from: protected */
    public Object readResolve() throws ObjectStreamException {
        return super.readResolve();
    }

    public /* bridge */ /* synthetic */ void setIncludeMyRetweetEnabled(boolean z) {
        super.setIncludeMyRetweetEnabled(z);
    }

    public /* bridge */ /* synthetic */ void setTrimUserEnabled(boolean z) {
        super.setTrimUserEnabled(z);
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }
}
