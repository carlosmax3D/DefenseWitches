package twitter4j;

import java.io.IOException;

interface StatusStream {
    void close() throws IOException;

    void next(StatusListener statusListener) throws TwitterException;
}
