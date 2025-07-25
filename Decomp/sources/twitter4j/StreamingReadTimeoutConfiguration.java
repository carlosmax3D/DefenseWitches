package twitter4j;

import twitter4j.conf.Configuration;

/* compiled from: TwitterStreamImpl */
class StreamingReadTimeoutConfiguration implements HttpClientConfiguration {
    final Configuration nestedConf;

    StreamingReadTimeoutConfiguration(Configuration configuration) {
        this.nestedConf = configuration;
    }

    public int getHttpConnectionTimeout() {
        return this.nestedConf.getHttpClientConfiguration().getHttpConnectionTimeout();
    }

    public String getHttpProxyHost() {
        return this.nestedConf.getHttpClientConfiguration().getHttpProxyHost();
    }

    public String getHttpProxyPassword() {
        return this.nestedConf.getHttpClientConfiguration().getHttpProxyPassword();
    }

    public int getHttpProxyPort() {
        return this.nestedConf.getHttpClientConfiguration().getHttpProxyPort();
    }

    public String getHttpProxyUser() {
        return this.nestedConf.getHttpClientConfiguration().getHttpProxyUser();
    }

    public int getHttpReadTimeout() {
        return this.nestedConf.getHttpStreamingReadTimeout();
    }

    public int getHttpRetryCount() {
        return this.nestedConf.getHttpClientConfiguration().getHttpRetryCount();
    }

    public int getHttpRetryIntervalSeconds() {
        return this.nestedConf.getHttpClientConfiguration().getHttpRetryIntervalSeconds();
    }

    public boolean isGZIPEnabled() {
        return this.nestedConf.getHttpClientConfiguration().isGZIPEnabled();
    }

    public boolean isPrettyDebugEnabled() {
        return this.nestedConf.getHttpClientConfiguration().isPrettyDebugEnabled();
    }
}
