package twitter4j;

import twitter4j.auth.OAuthSupport;

public interface TwitterStream extends OAuthSupport, TwitterBase {
    void addConnectionLifeCycleListener(ConnectionLifeCycleListener connectionLifeCycleListener);

    void addListener(StreamListener streamListener);

    void cleanUp();

    void clearListeners();

    void filter(FilterQuery filterQuery);

    void firehose(int i);

    void links(int i);

    void removeListener(StreamListener streamListener);

    void replaceListener(StreamListener streamListener, StreamListener streamListener2);

    void retweet();

    void sample();

    void shutdown();

    StreamController site(boolean z, long[] jArr);

    void user();

    void user(String[] strArr);
}
