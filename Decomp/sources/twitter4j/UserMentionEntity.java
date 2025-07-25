package twitter4j;

import java.io.Serializable;

public interface UserMentionEntity extends TweetEntity, Serializable {
    int getEnd();

    long getId();

    String getName();

    String getScreenName();

    int getStart();

    String getText();
}
