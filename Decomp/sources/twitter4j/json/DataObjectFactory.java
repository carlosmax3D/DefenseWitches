package twitter4j.json;

import java.util.Map;
import twitter4j.AccountTotals;
import twitter4j.Category;
import twitter4j.DirectMessage;
import twitter4j.IDs;
import twitter4j.Location;
import twitter4j.OEmbed;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Relationship;
import twitter4j.SavedSearch;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.User;
import twitter4j.UserList;

public final class DataObjectFactory {
    private DataObjectFactory() {
        throw new AssertionError("not intended to be instantiated.");
    }

    public static AccountTotals createAccountTotals(String str) throws TwitterException {
        return TwitterObjectFactory.createAccountTotals(str);
    }

    public static Category createCategory(String str) throws TwitterException {
        return TwitterObjectFactory.createCategory(str);
    }

    public static DirectMessage createDirectMessage(String str) throws TwitterException {
        return TwitterObjectFactory.createDirectMessage(str);
    }

    public static IDs createIDs(String str) throws TwitterException {
        return TwitterObjectFactory.createIDs(str);
    }

    public static Location createLocation(String str) throws TwitterException {
        return TwitterObjectFactory.createLocation(str);
    }

    public static OEmbed createOEmbed(String str) throws TwitterException {
        return TwitterObjectFactory.createOEmbed(str);
    }

    public static Object createObject(String str) throws TwitterException {
        return TwitterObjectFactory.createObject(str);
    }

    public static Place createPlace(String str) throws TwitterException {
        return TwitterObjectFactory.createPlace(str);
    }

    public static Map<String, RateLimitStatus> createRateLimitStatus(String str) throws TwitterException {
        return TwitterObjectFactory.createRateLimitStatus(str);
    }

    public static Relationship createRelationship(String str) throws TwitterException {
        return TwitterObjectFactory.createRelationship(str);
    }

    public static SavedSearch createSavedSearch(String str) throws TwitterException {
        return TwitterObjectFactory.createSavedSearch(str);
    }

    public static Status createStatus(String str) throws TwitterException {
        return TwitterObjectFactory.createStatus(str);
    }

    public static Trend createTrend(String str) throws TwitterException {
        return TwitterObjectFactory.createTrend(str);
    }

    public static Trends createTrends(String str) throws TwitterException {
        return TwitterObjectFactory.createTrends(str);
    }

    public static User createUser(String str) throws TwitterException {
        return TwitterObjectFactory.createUser(str);
    }

    public static UserList createUserList(String str) throws TwitterException {
        return TwitterObjectFactory.createUserList(str);
    }

    public static String getRawJSON(Object obj) {
        return TwitterObjectFactory.getRawJSON(obj);
    }
}
