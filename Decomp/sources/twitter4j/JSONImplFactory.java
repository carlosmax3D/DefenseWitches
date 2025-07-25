package twitter4j;

import java.util.Map;
import twitter4j.api.HelpResources;
import twitter4j.conf.Configuration;

class JSONImplFactory implements ObjectFactory {
    private static final long serialVersionUID = -1853541456182663343L;
    private final Configuration conf;

    public JSONImplFactory(Configuration configuration) {
        this.conf = configuration;
    }

    static GeoLocation[][] coordinatesAsGeoLocationArray(JSONArray jSONArray) throws TwitterException {
        try {
            GeoLocation[][] geoLocationArr = new GeoLocation[jSONArray.length()][];
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONArray jSONArray2 = jSONArray.getJSONArray(i);
                geoLocationArr[i] = new GeoLocation[jSONArray2.length()];
                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                    JSONArray jSONArray3 = jSONArray2.getJSONArray(i2);
                    geoLocationArr[i][i2] = new GeoLocation(jSONArray3.getDouble(1), jSONArray3.getDouble(0));
                }
            }
            return geoLocationArr;
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    static GeoLocation createGeoLocation(JSONObject jSONObject) throws TwitterException {
        try {
            if (jSONObject.isNull("coordinates")) {
                return null;
            }
            String string = jSONObject.getJSONObject("coordinates").getString("coordinates");
            String[] split = string.substring(1, string.length() - 1).split(",");
            return new GeoLocation(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    public static HashtagEntity createHashtagEntity(int i, int i2, String str) {
        return new HashtagEntityJSONImpl(i, i2, str);
    }

    public static RateLimitStatus createRateLimitStatusFromResponseHeader(HttpResponse httpResponse) {
        return RateLimitStatusJSONImpl.createFromResponseHeader(httpResponse);
    }

    public static URLEntity createUrlEntity(int i, int i2, String str, String str2, String str3) {
        return new URLEntityJSONImpl(i, i2, str, str2, str3);
    }

    public static UserMentionEntity createUserMentionEntity(int i, int i2, String str, String str2, long j) {
        return new UserMentionEntityJSONImpl(i, i2, str, str2, j);
    }

    public UserList createAUserList(HttpResponse httpResponse) throws TwitterException {
        return new UserListJSONImpl(httpResponse, this.conf);
    }

    public UserList createAUserList(JSONObject jSONObject) throws TwitterException {
        return new UserListJSONImpl(jSONObject);
    }

    public AccountSettings createAccountSettings(HttpResponse httpResponse) throws TwitterException {
        return new AccountSettingsJSONImpl(httpResponse, this.conf);
    }

    public AccountTotals createAccountTotals(HttpResponse httpResponse) throws TwitterException {
        return new AccountTotalsJSONImpl(httpResponse, this.conf);
    }

    public ResponseList<Category> createCategoryList(HttpResponse httpResponse) throws TwitterException {
        return CategoryJSONImpl.createCategoriesList(httpResponse, this.conf);
    }

    public DirectMessage createDirectMessage(HttpResponse httpResponse) throws TwitterException {
        return new DirectMessageJSONImpl(httpResponse, this.conf);
    }

    public ResponseList<DirectMessage> createDirectMessageList(HttpResponse httpResponse) throws TwitterException {
        return DirectMessageJSONImpl.createDirectMessageList(httpResponse, this.conf);
    }

    public <T> ResponseList<T> createEmptyResponseList() {
        return new ResponseListImpl(0, (HttpResponse) null);
    }

    public ResponseList<Friendship> createFriendshipList(HttpResponse httpResponse) throws TwitterException {
        return FriendshipJSONImpl.createFriendshipList(httpResponse, this.conf);
    }

    public IDs createIDs(HttpResponse httpResponse) throws TwitterException {
        return new IDsJSONImpl(httpResponse, this.conf);
    }

    public ResponseList<HelpResources.Language> createLanguageList(HttpResponse httpResponse) throws TwitterException {
        return LanguageJSONImpl.createLanguageList(httpResponse, this.conf);
    }

    public ResponseList<Location> createLocationList(HttpResponse httpResponse) throws TwitterException {
        return LocationJSONImpl.createLocationList(httpResponse, this.conf);
    }

    public OEmbed createOEmbed(HttpResponse httpResponse) throws TwitterException {
        return new OEmbedJSONImpl(httpResponse, this.conf);
    }

    public PagableResponseList<User> createPagableUserList(HttpResponse httpResponse) throws TwitterException {
        return UserJSONImpl.createPagableUserList(httpResponse, this.conf);
    }

    public PagableResponseList<UserList> createPagableUserListList(HttpResponse httpResponse) throws TwitterException {
        return UserListJSONImpl.createPagableUserListList(httpResponse, this.conf);
    }

    public Place createPlace(HttpResponse httpResponse) throws TwitterException {
        return new PlaceJSONImpl(httpResponse, this.conf);
    }

    public ResponseList<Place> createPlaceList(HttpResponse httpResponse) throws TwitterException {
        try {
            return PlaceJSONImpl.createPlaceList(httpResponse, this.conf);
        } catch (TwitterException e) {
            if (e.getStatusCode() == 404) {
                return new ResponseListImpl(0, (HttpResponse) null);
            }
            throw e;
        }
    }

    public QueryResult createQueryResult(HttpResponse httpResponse, Query query) throws TwitterException {
        try {
            return new QueryResultJSONImpl(httpResponse, this.conf);
        } catch (TwitterException e) {
            if (404 == e.getStatusCode()) {
                return new QueryResultJSONImpl(query);
            }
            throw e;
        }
    }

    public Map<String, RateLimitStatus> createRateLimitStatuses(HttpResponse httpResponse) throws TwitterException {
        return RateLimitStatusJSONImpl.createRateLimitStatuses(httpResponse, this.conf);
    }

    public Relationship createRelationship(HttpResponse httpResponse) throws TwitterException {
        return new RelationshipJSONImpl(httpResponse, this.conf);
    }

    public SavedSearch createSavedSearch(HttpResponse httpResponse) throws TwitterException {
        return new SavedSearchJSONImpl(httpResponse, this.conf);
    }

    public ResponseList<SavedSearch> createSavedSearchList(HttpResponse httpResponse) throws TwitterException {
        return SavedSearchJSONImpl.createSavedSearchList(httpResponse, this.conf);
    }

    public Status createStatus(HttpResponse httpResponse) throws TwitterException {
        return new StatusJSONImpl(httpResponse, this.conf);
    }

    public Status createStatus(JSONObject jSONObject) throws TwitterException {
        return new StatusJSONImpl(jSONObject);
    }

    public ResponseList<Status> createStatusList(HttpResponse httpResponse) throws TwitterException {
        return StatusJSONImpl.createStatusList(httpResponse, this.conf);
    }

    public Trends createTrends(HttpResponse httpResponse) throws TwitterException {
        return new TrendsJSONImpl(httpResponse, this.conf);
    }

    public TwitterAPIConfiguration createTwitterAPIConfiguration(HttpResponse httpResponse) throws TwitterException {
        return new TwitterAPIConfigurationJSONImpl(httpResponse, this.conf);
    }

    public User createUser(HttpResponse httpResponse) throws TwitterException {
        return new UserJSONImpl(httpResponse, this.conf);
    }

    public User createUser(JSONObject jSONObject) throws TwitterException {
        return new UserJSONImpl(jSONObject);
    }

    public ResponseList<User> createUserList(HttpResponse httpResponse) throws TwitterException {
        return UserJSONImpl.createUserList(httpResponse, this.conf);
    }

    public ResponseList<User> createUserListFromJSONArray(HttpResponse httpResponse) throws TwitterException {
        return UserJSONImpl.createUserList(httpResponse.asJSONArray(), httpResponse, this.conf);
    }

    public ResponseList<User> createUserListFromJSONArray_Users(HttpResponse httpResponse) throws TwitterException {
        try {
            return UserJSONImpl.createUserList(httpResponse.asJSONObject().getJSONArray("users"), httpResponse, this.conf);
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    public ResponseList<UserList> createUserListList(HttpResponse httpResponse) throws TwitterException {
        return UserListJSONImpl.createUserListList(httpResponse, this.conf);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof JSONImplFactory)) {
            return false;
        }
        JSONImplFactory jSONImplFactory = (JSONImplFactory) obj;
        if (this.conf != null) {
            if (this.conf.equals(jSONImplFactory.conf)) {
                return true;
            }
        } else if (jSONImplFactory.conf == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.conf != null) {
            return this.conf.hashCode();
        }
        return 0;
    }

    public String toString() {
        return "JSONImplFactory{conf=" + this.conf + '}';
    }
}
