package twitter4j;

public interface HttpClientConfiguration {
    int getHttpConnectionTimeout();

    String getHttpProxyHost();

    String getHttpProxyPassword();

    int getHttpProxyPort();

    String getHttpProxyUser();

    int getHttpReadTimeout();

    int getHttpRetryCount();

    int getHttpRetryIntervalSeconds();

    boolean isGZIPEnabled();

    boolean isPrettyDebugEnabled();
}
