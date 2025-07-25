package twitter4j;

import java.util.EventObject;

public final class RateLimitStatusEvent extends EventObject {
    private static final long serialVersionUID = 3749366911109722414L;
    private final boolean isAccountRateLimitStatus;
    private final RateLimitStatus rateLimitStatus;

    RateLimitStatusEvent(Object obj, RateLimitStatus rateLimitStatus2, boolean z) {
        super(obj);
        this.rateLimitStatus = rateLimitStatus2;
        this.isAccountRateLimitStatus = z;
    }

    public RateLimitStatus getRateLimitStatus() {
        return this.rateLimitStatus;
    }

    public boolean isAccountRateLimitStatus() {
        return this.isAccountRateLimitStatus;
    }

    public boolean isIPRateLimitStatus() {
        return !this.isAccountRateLimitStatus;
    }
}
