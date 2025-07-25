package twitter4j;

import java.io.Serializable;

public interface URLEntity extends TweetEntity, Serializable {
    String getDisplayURL();

    int getEnd();

    String getExpandedURL();

    int getStart();

    String getText();

    String getURL();
}
