package twitter4j;

import java.io.Serializable;

public interface RateLimitStatus extends Serializable {
    int getLimit();

    int getRemaining();

    int getResetTimeInSeconds();

    int getSecondsUntilReset();
}
