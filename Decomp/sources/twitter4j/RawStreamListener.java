package twitter4j;

public interface RawStreamListener extends StreamListener {
    void onMessage(String str);
}
