package twitter4j;

import java.io.Serializable;

public interface Relationship extends TwitterResponse, Serializable {
    boolean canSourceDm();

    long getSourceUserId();

    String getSourceUserScreenName();

    long getTargetUserId();

    String getTargetUserScreenName();

    boolean isSourceBlockingTarget();

    boolean isSourceFollowedByTarget();

    boolean isSourceFollowingTarget();

    boolean isSourceMutingTarget();

    boolean isSourceNotificationsEnabled();

    boolean isSourceWantRetweets();

    boolean isTargetFollowedBySource();

    boolean isTargetFollowingSource();
}
