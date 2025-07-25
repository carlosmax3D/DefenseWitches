package twitter4j.api;

import java.io.File;
import twitter4j.IDs;
import twitter4j.OEmbed;
import twitter4j.OEmbedRequest;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.UploadedMedia;

public interface TweetsResources {
    Status destroyStatus(long j) throws TwitterException;

    OEmbed getOEmbed(OEmbedRequest oEmbedRequest) throws TwitterException;

    IDs getRetweeterIds(long j, int i, long j2) throws TwitterException;

    IDs getRetweeterIds(long j, long j2) throws TwitterException;

    ResponseList<Status> getRetweets(long j) throws TwitterException;

    ResponseList<Status> lookup(long[] jArr) throws TwitterException;

    Status retweetStatus(long j) throws TwitterException;

    Status showStatus(long j) throws TwitterException;

    Status updateStatus(String str) throws TwitterException;

    Status updateStatus(StatusUpdate statusUpdate) throws TwitterException;

    UploadedMedia uploadMedia(File file) throws TwitterException;
}
