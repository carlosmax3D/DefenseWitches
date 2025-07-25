package twitter4j;

interface StreamImplementation {
    void close();

    void next(StreamListener[] streamListenerArr, RawStreamListener[] rawStreamListenerArr);

    void onException(Exception exc, StreamListener[] streamListenerArr, RawStreamListener[] rawStreamListenerArr);
}
