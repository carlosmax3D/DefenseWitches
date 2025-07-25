package twitter4j;

public interface StatusListener extends StreamListener {
    void onDeletionNotice(StatusDeletionNotice statusDeletionNotice);

    void onScrubGeo(long j, long j2);

    void onStallWarning(StallWarning stallWarning);

    void onStatus(Status status);

    void onTrackLimitationNotice(int i);
}
