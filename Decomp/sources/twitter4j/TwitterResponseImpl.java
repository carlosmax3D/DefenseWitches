package twitter4j;

import java.io.Serializable;

abstract class TwitterResponseImpl implements TwitterResponse, Serializable {
    private static final long serialVersionUID = 7422171124869859808L;
    private final transient int accessLevel;
    private transient RateLimitStatus rateLimitStatus;

    public TwitterResponseImpl() {
        this.rateLimitStatus = null;
        this.accessLevel = 0;
    }

    public TwitterResponseImpl(HttpResponse httpResponse) {
        this.rateLimitStatus = null;
        this.rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(httpResponse);
        this.accessLevel = ParseUtil.toAccessLevel(httpResponse);
    }

    public int getAccessLevel() {
        return this.accessLevel;
    }

    public RateLimitStatus getRateLimitStatus() {
        return this.rateLimitStatus;
    }
}
