package twitter4j;

import java.io.IOException;

interface UserStream extends StatusStream {
    void close() throws IOException;

    void next(UserStreamListener userStreamListener) throws TwitterException;
}
