package twitter4j;

import java.io.Serializable;
import java.util.Map;
import twitter4j.api.HelpResources;

interface ObjectFactory extends Serializable {
    UserList createAUserList(HttpResponse httpResponse) throws TwitterException;

    UserList createAUserList(JSONObject jSONObject) throws TwitterException;

    AccountSettings createAccountSettings(HttpResponse httpResponse) throws TwitterException;

    AccountTotals createAccountTotals(HttpResponse httpResponse) throws TwitterException;

    ResponseList<Category> createCategoryList(HttpResponse httpResponse) throws TwitterException;

    DirectMessage createDirectMessage(HttpResponse httpResponse) throws TwitterException;

    ResponseList<DirectMessage> createDirectMessageList(HttpResponse httpResponse) throws TwitterException;

    <T> ResponseList<T> createEmptyResponseList();

    ResponseList<Friendship> createFriendshipList(HttpResponse httpResponse) throws TwitterException;

    IDs createIDs(HttpResponse httpResponse) throws TwitterException;

    ResponseList<HelpResources.Language> createLanguageList(HttpResponse httpResponse) throws TwitterException;

    ResponseList<Location> createLocationList(HttpResponse httpResponse) throws TwitterException;

    OEmbed createOEmbed(HttpResponse httpResponse) throws TwitterException;

    PagableResponseList<User> createPagableUserList(HttpResponse httpResponse) throws TwitterException;

    PagableResponseList<UserList> createPagableUserListList(HttpResponse httpResponse) throws TwitterException;

    Place createPlace(HttpResponse httpResponse) throws TwitterException;

    ResponseList<Place> createPlaceList(HttpResponse httpResponse) throws TwitterException;

    QueryResult createQueryResult(HttpResponse httpResponse, Query query) throws TwitterException;

    Map<String, RateLimitStatus> createRateLimitStatuses(HttpResponse httpResponse) throws TwitterException;

    Relationship createRelationship(HttpResponse httpResponse) throws TwitterException;

    SavedSearch createSavedSearch(HttpResponse httpResponse) throws TwitterException;

    ResponseList<SavedSearch> createSavedSearchList(HttpResponse httpResponse) throws TwitterException;

    Status createStatus(HttpResponse httpResponse) throws TwitterException;

    Status createStatus(JSONObject jSONObject) throws TwitterException;

    ResponseList<Status> createStatusList(HttpResponse httpResponse) throws TwitterException;

    Trends createTrends(HttpResponse httpResponse) throws TwitterException;

    TwitterAPIConfiguration createTwitterAPIConfiguration(HttpResponse httpResponse) throws TwitterException;

    User createUser(HttpResponse httpResponse) throws TwitterException;

    User createUser(JSONObject jSONObject) throws TwitterException;

    ResponseList<User> createUserList(HttpResponse httpResponse) throws TwitterException;

    ResponseList<User> createUserListFromJSONArray(HttpResponse httpResponse) throws TwitterException;

    ResponseList<User> createUserListFromJSONArray_Users(HttpResponse httpResponse) throws TwitterException;

    ResponseList<UserList> createUserListList(HttpResponse httpResponse) throws TwitterException;
}
