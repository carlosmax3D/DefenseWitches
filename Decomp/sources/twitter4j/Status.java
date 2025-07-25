package twitter4j;

import java.io.Serializable;
import java.util.Date;

public interface Status extends Comparable<Status>, TwitterResponse, EntitySupport, Serializable {
    long[] getContributors();

    Date getCreatedAt();

    long getCurrentUserRetweetId();

    int getFavoriteCount();

    GeoLocation getGeoLocation();

    long getId();

    String getInReplyToScreenName();

    long getInReplyToStatusId();

    long getInReplyToUserId();

    String getLang();

    Place getPlace();

    int getRetweetCount();

    Status getRetweetedStatus();

    Scopes getScopes();

    String getSource();

    String getText();

    User getUser();

    boolean isFavorited();

    boolean isPossiblySensitive();

    boolean isRetweet();

    boolean isRetweeted();

    boolean isRetweetedByMe();

    boolean isTruncated();
}
