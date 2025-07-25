package twitter4j;

import CoronaProvider.ads.admob.AdMobAd;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.share.internal.ShareConstants;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jp.co.voyagegroup.android.fluct.jar.util.FluctConstants;
import jp.stargarage.g2metrics.BuildConfig;
import twitter4j.api.DirectMessagesResources;
import twitter4j.api.FavoritesResources;
import twitter4j.api.FriendsFollowersResources;
import twitter4j.api.HelpResources;
import twitter4j.api.ListsResources;
import twitter4j.api.PlacesGeoResources;
import twitter4j.api.SavedSearchesResources;
import twitter4j.api.SearchResource;
import twitter4j.api.SpamReportingResource;
import twitter4j.api.SuggestedUsersResources;
import twitter4j.api.TimelinesResources;
import twitter4j.api.TrendsResources;
import twitter4j.api.TweetsResources;
import twitter4j.api.UsersResources;
import twitter4j.auth.Authorization;
import twitter4j.conf.Configuration;

class TwitterImpl extends TwitterBaseImpl implements Twitter {
    private static final ConcurrentHashMap<Configuration, HttpParameter[]> implicitParamsMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Configuration, String> implicitParamsStrMap = new ConcurrentHashMap<>();
    private static final long serialVersionUID = 9170943084096085770L;
    private final HttpParameter[] IMPLICIT_PARAMS;
    private final String IMPLICIT_PARAMS_STR;
    private final HttpParameter INCLUDE_MY_RETWEET;

    TwitterImpl(Configuration configuration, Authorization authorization) {
        super(configuration, authorization);
        this.INCLUDE_MY_RETWEET = new HttpParameter("include_my_retweet", configuration.isIncludeMyRetweetEnabled());
        if (implicitParamsMap.containsKey(configuration)) {
            this.IMPLICIT_PARAMS = implicitParamsMap.get(configuration);
            this.IMPLICIT_PARAMS_STR = implicitParamsStrMap.get(configuration);
            return;
        }
        String str = configuration.isIncludeEntitiesEnabled() ? "include_entities=true" : BuildConfig.FLAVOR;
        boolean z = configuration.getContributingTo() != -1;
        if (z) {
            str = (!BuildConfig.FLAVOR.equals(str) ? str + "?" : str) + "contributingto=" + configuration.getContributingTo();
        }
        ArrayList arrayList = new ArrayList(3);
        if (configuration.isIncludeEntitiesEnabled()) {
            arrayList.add(new HttpParameter("include_entities", "true"));
        }
        if (z) {
            arrayList.add(new HttpParameter("contributingto", configuration.getContributingTo()));
        }
        if (configuration.isTrimUserEnabled()) {
            arrayList.add(new HttpParameter("trim_user", AppEventsConstants.EVENT_PARAM_VALUE_YES));
        }
        HttpParameter[] httpParameterArr = (HttpParameter[]) arrayList.toArray(new HttpParameter[arrayList.size()]);
        implicitParamsStrMap.putIfAbsent(configuration, str);
        implicitParamsMap.putIfAbsent(configuration, httpParameterArr);
        this.IMPLICIT_PARAMS = httpParameterArr;
        this.IMPLICIT_PARAMS_STR = str;
    }

    private void addParameterToList(List<HttpParameter> list, String str, String str2) {
        if (str2 != null) {
            list.add(new HttpParameter(str, str2));
        }
    }

    private void checkFileValidity(File file) throws TwitterException {
        if (!file.exists()) {
            throw new TwitterException((Exception) new FileNotFoundException(file + " is not found."));
        } else if (!file.isFile()) {
            throw new TwitterException((Exception) new IOException(file + " is not a file."));
        }
    }

    private HttpResponse get(String str) throws TwitterException {
        ensureAuthorizationEnabled();
        if (this.IMPLICIT_PARAMS_STR.length() > 0) {
            str = str.contains("?") ? str + "&" + this.IMPLICIT_PARAMS_STR : str + "?" + this.IMPLICIT_PARAMS_STR;
        }
        if (!this.conf.isMBeanEnabled()) {
            return this.http.get(str, (HttpParameter[]) null, this.auth, this);
        }
        HttpResponse httpResponse = null;
        long currentTimeMillis = System.currentTimeMillis();
        try {
            httpResponse = this.http.get(str, (HttpParameter[]) null, this.auth, this);
            return httpResponse;
        } finally {
            TwitterAPIMonitor.getInstance().methodCalled(str, System.currentTimeMillis() - currentTimeMillis, isOk(httpResponse));
        }
    }

    private HttpResponse get(String str, HttpParameter[] httpParameterArr) throws TwitterException {
        ensureAuthorizationEnabled();
        if (!this.conf.isMBeanEnabled()) {
            return this.http.get(str, mergeImplicitParams(httpParameterArr), this.auth, this);
        }
        HttpResponse httpResponse = null;
        long currentTimeMillis = System.currentTimeMillis();
        try {
            httpResponse = this.http.get(str, mergeImplicitParams(httpParameterArr), this.auth, this);
            return httpResponse;
        } finally {
            TwitterAPIMonitor.getInstance().methodCalled(str, System.currentTimeMillis() - currentTimeMillis, isOk(httpResponse));
        }
    }

    private boolean isOk(HttpResponse httpResponse) {
        return httpResponse != null && httpResponse.getStatusCode() < 300;
    }

    private HttpParameter[] mergeImplicitParams(HttpParameter[] httpParameterArr) {
        return mergeParameters(httpParameterArr, this.IMPLICIT_PARAMS);
    }

    private HttpParameter[] mergeParameters(HttpParameter[] httpParameterArr, HttpParameter httpParameter) {
        if (httpParameterArr != null && httpParameter != null) {
            HttpParameter[] httpParameterArr2 = new HttpParameter[(httpParameterArr.length + 1)];
            System.arraycopy(httpParameterArr, 0, httpParameterArr2, 0, httpParameterArr.length);
            httpParameterArr2[httpParameterArr2.length - 1] = httpParameter;
            return httpParameterArr2;
        } else if (httpParameterArr == null && httpParameter == null) {
            return new HttpParameter[0];
        } else {
            if (httpParameterArr != null) {
                return httpParameterArr;
            }
            return new HttpParameter[]{httpParameter};
        }
    }

    private HttpParameter[] mergeParameters(HttpParameter[] httpParameterArr, HttpParameter[] httpParameterArr2) {
        if (httpParameterArr == null || httpParameterArr2 == null) {
            return (httpParameterArr == null && httpParameterArr2 == null) ? new HttpParameter[0] : httpParameterArr != null ? httpParameterArr : httpParameterArr2;
        }
        HttpParameter[] httpParameterArr3 = new HttpParameter[(httpParameterArr.length + httpParameterArr2.length)];
        System.arraycopy(httpParameterArr, 0, httpParameterArr3, 0, httpParameterArr.length);
        System.arraycopy(httpParameterArr2, 0, httpParameterArr3, httpParameterArr.length, httpParameterArr2.length);
        return httpParameterArr3;
    }

    private HttpResponse post(String str) throws TwitterException {
        ensureAuthorizationEnabled();
        if (!this.conf.isMBeanEnabled()) {
            return this.http.post(str, this.IMPLICIT_PARAMS, this.auth, this);
        }
        HttpResponse httpResponse = null;
        long currentTimeMillis = System.currentTimeMillis();
        try {
            httpResponse = this.http.post(str, this.IMPLICIT_PARAMS, this.auth, this);
            return httpResponse;
        } finally {
            TwitterAPIMonitor.getInstance().methodCalled(str, System.currentTimeMillis() - currentTimeMillis, isOk(httpResponse));
        }
    }

    private HttpResponse post(String str, HttpParameter[] httpParameterArr) throws TwitterException {
        ensureAuthorizationEnabled();
        if (!this.conf.isMBeanEnabled()) {
            return this.http.post(str, mergeImplicitParams(httpParameterArr), this.auth, this);
        }
        HttpResponse httpResponse = null;
        long currentTimeMillis = System.currentTimeMillis();
        try {
            httpResponse = this.http.post(str, mergeImplicitParams(httpParameterArr), this.auth, this);
            return httpResponse;
        } finally {
            TwitterAPIMonitor.getInstance().methodCalled(str, System.currentTimeMillis() - currentTimeMillis, isOk(httpResponse));
        }
    }

    private UserList updateUserList(String str, boolean z, String str2, HttpParameter... httpParameterArr) throws TwitterException {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, httpParameterArr);
        if (str != null) {
            arrayList.add(new HttpParameter("name", str));
        }
        arrayList.add(new HttpParameter(FluctConstants.XML_NODE_MODE, z ? "public" : "private"));
        if (str2 != null) {
            arrayList.add(new HttpParameter("description", str2));
        }
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/update.json", (HttpParameter[]) arrayList.toArray(new HttpParameter[arrayList.size()])));
    }

    public User createBlock(long j) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "blocks/create.json?user_id=" + j));
    }

    public User createBlock(String str) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "blocks/create.json?screen_name=" + str));
    }

    public Status createFavorite(long j) throws TwitterException {
        return this.factory.createStatus(post(this.conf.getRestBaseURL() + "favorites/create.json?id=" + j));
    }

    public User createFriendship(long j) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "friendships/create.json?user_id=" + j));
    }

    public User createFriendship(long j, boolean z) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "friendships/create.json?user_id=" + j + "&follow=" + z));
    }

    public User createFriendship(String str) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "friendships/create.json?screen_name=" + str));
    }

    public User createFriendship(String str, boolean z) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "friendships/create.json?screen_name=" + str + "&follow=" + z));
    }

    public User createMute(long j) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "mutes/users/create.json?user_id=" + j));
    }

    public User createMute(String str) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "mutes/users/create.json?screen_name=" + str));
    }

    public SavedSearch createSavedSearch(String str) throws TwitterException {
        return this.factory.createSavedSearch(post(this.conf.getRestBaseURL() + "saved_searches/create.json", new HttpParameter[]{new HttpParameter("query", str)}));
    }

    public UserList createUserList(String str, boolean z, String str2) throws TwitterException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new HttpParameter("name", str));
        arrayList.add(new HttpParameter(FluctConstants.XML_NODE_MODE, z ? "public" : "private"));
        if (str2 != null) {
            arrayList.add(new HttpParameter("description", str2));
        }
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/create.json", (HttpParameter[]) arrayList.toArray(new HttpParameter[arrayList.size()])));
    }

    public UserList createUserListMember(long j, long j2) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create.json", new HttpParameter[]{new HttpParameter("user_id", j2), new HttpParameter("list_id", j)}));
    }

    public UserList createUserListMember(long j, String str, long j2) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create.json", new HttpParameter[]{new HttpParameter("user_id", j2), new HttpParameter("owner_id", j), new HttpParameter("slug", str)}));
    }

    public UserList createUserListMember(String str, String str2, long j) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create.json", new HttpParameter[]{new HttpParameter("user_id", j), new HttpParameter("owner_screen_name", str), new HttpParameter("slug", str2)}));
    }

    public UserList createUserListMembers(long j, String str, long[] jArr) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create_all.json", new HttpParameter[]{new HttpParameter("owner_id", j), new HttpParameter("slug", str), new HttpParameter("user_id", StringUtil.join(jArr))}));
    }

    public UserList createUserListMembers(long j, String str, String[] strArr) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create_all.json", new HttpParameter[]{new HttpParameter("owner_id", j), new HttpParameter("slug", str), new HttpParameter("screen_name", StringUtil.join(strArr))}));
    }

    public UserList createUserListMembers(long j, long[] jArr) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create_all.json", new HttpParameter[]{new HttpParameter("list_id", j), new HttpParameter("user_id", StringUtil.join(jArr))}));
    }

    public UserList createUserListMembers(long j, String[] strArr) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create_all.json", new HttpParameter[]{new HttpParameter("list_id", j), new HttpParameter("screen_name", StringUtil.join(strArr))}));
    }

    public UserList createUserListMembers(String str, String str2, long[] jArr) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create_all.json", new HttpParameter[]{new HttpParameter("owner_screen_name", str), new HttpParameter("slug", str2), new HttpParameter("user_id", StringUtil.join(jArr))}));
    }

    public UserList createUserListMembers(String str, String str2, String[] strArr) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/create_all.json", new HttpParameter[]{new HttpParameter("owner_screen_name", str), new HttpParameter("slug", str2), new HttpParameter("screen_name", StringUtil.join(strArr))}));
    }

    public UserList createUserListSubscription(long j) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/subscribers/create.json", new HttpParameter[]{new HttpParameter("list_id", j)}));
    }

    public UserList createUserListSubscription(long j, String str) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/subscribers/create.json", new HttpParameter[]{new HttpParameter("owner_id", j), new HttpParameter("slug", str)}));
    }

    public UserList createUserListSubscription(String str, String str2) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/subscribers/create.json", new HttpParameter[]{new HttpParameter("owner_screen_name", str), new HttpParameter("slug", str2)}));
    }

    public User destroyBlock(long j) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "blocks/destroy.json?user_id=" + j));
    }

    public User destroyBlock(String str) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "blocks/destroy.json?screen_name=" + str));
    }

    public DirectMessage destroyDirectMessage(long j) throws TwitterException {
        return this.factory.createDirectMessage(post(this.conf.getRestBaseURL() + "direct_messages/destroy.json?id=" + j));
    }

    public Status destroyFavorite(long j) throws TwitterException {
        return this.factory.createStatus(post(this.conf.getRestBaseURL() + "favorites/destroy.json?id=" + j));
    }

    public User destroyFriendship(long j) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "friendships/destroy.json?user_id=" + j));
    }

    public User destroyFriendship(String str) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "friendships/destroy.json?screen_name=" + str));
    }

    public User destroyMute(long j) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "mutes/users/destroy.json?user_id=" + j));
    }

    public User destroyMute(String str) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "mutes/users/destroy.json?screen_name=" + str));
    }

    public SavedSearch destroySavedSearch(int i) throws TwitterException {
        return this.factory.createSavedSearch(post(this.conf.getRestBaseURL() + "saved_searches/destroy/" + i + ".json"));
    }

    public Status destroyStatus(long j) throws TwitterException {
        return this.factory.createStatus(post(this.conf.getRestBaseURL() + "statuses/destroy/" + j + ".json"));
    }

    public UserList destroyUserList(long j) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/destroy.json", new HttpParameter[]{new HttpParameter("list_id", j)}));
    }

    public UserList destroyUserList(long j, String str) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/destroy.json", new HttpParameter[]{new HttpParameter("owner_id", j), new HttpParameter("slug", str)}));
    }

    public UserList destroyUserList(String str, String str2) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/destroy.json", new HttpParameter[]{new HttpParameter("owner_screen_name", str), new HttpParameter("slug", str2)}));
    }

    public UserList destroyUserListMember(long j, long j2) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/destroy.json", new HttpParameter[]{new HttpParameter("list_id", j), new HttpParameter("user_id", j2)}));
    }

    public UserList destroyUserListMember(long j, String str) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/destroy.json", new HttpParameter[]{new HttpParameter("list_id", j), new HttpParameter("screen_name", str)}));
    }

    public UserList destroyUserListMember(long j, String str, long j2) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/destroy.json", new HttpParameter[]{new HttpParameter("owner_id", j), new HttpParameter("slug", str), new HttpParameter("user_id", j2)}));
    }

    public UserList destroyUserListMember(String str, String str2, long j) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/destroy.json", new HttpParameter[]{new HttpParameter("owner_screen_name", str), new HttpParameter("slug", str2), new HttpParameter("user_id", j)}));
    }

    public UserList destroyUserListMembers(long j, long[] jArr) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/destroy_all.json", new HttpParameter[]{new HttpParameter("list_id", j), new HttpParameter("user_id", StringUtil.join(jArr))}));
    }

    public UserList destroyUserListMembers(long j, String[] strArr) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/destroy_all.json", new HttpParameter[]{new HttpParameter("list_id", j), new HttpParameter("screen_name", StringUtil.join(strArr))}));
    }

    public UserList destroyUserListMembers(String str, String str2, String[] strArr) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/members/destroy_all.json", new HttpParameter[]{new HttpParameter("owner_screen_name", str), new HttpParameter("slug", str2), new HttpParameter("screen_name", StringUtil.join(strArr))}));
    }

    public UserList destroyUserListSubscription(long j) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/subscribers/destroy.json", new HttpParameter[]{new HttpParameter("list_id", j)}));
    }

    public UserList destroyUserListSubscription(long j, String str) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/subscribers/destroy.json", new HttpParameter[]{new HttpParameter("owner_id", j), new HttpParameter("slug", str)}));
    }

    public UserList destroyUserListSubscription(String str, String str2) throws TwitterException {
        return this.factory.createAUserList(post(this.conf.getRestBaseURL() + "lists/subscribers/destroy.json", new HttpParameter[]{new HttpParameter("owner_screen_name", str), new HttpParameter("slug", str2)}));
    }

    public DirectMessagesResources directMessages() {
        return this;
    }

    public FavoritesResources favorites() {
        return this;
    }

    public FriendsFollowersResources friendsFollowers() {
        return this;
    }

    public TwitterAPIConfiguration getAPIConfiguration() throws TwitterException {
        return this.factory.createTwitterAPIConfiguration(get(this.conf.getRestBaseURL() + "help/configuration.json"));
    }

    public AccountSettings getAccountSettings() throws TwitterException {
        return this.factory.createAccountSettings(get(this.conf.getRestBaseURL() + "account/settings.json"));
    }

    public ResponseList<Location> getAvailableTrends() throws TwitterException {
        return this.factory.createLocationList(get(this.conf.getRestBaseURL() + "trends/available.json"));
    }

    public IDs getBlocksIDs() throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "blocks/ids.json"));
    }

    public IDs getBlocksIDs(long j) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "blocks/ids.json?cursor=" + j));
    }

    public PagableResponseList<User> getBlocksList() throws TwitterException {
        return getBlocksList(-1);
    }

    public PagableResponseList<User> getBlocksList(long j) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "blocks/list.json?cursor=" + j));
    }

    public ResponseList<Location> getClosestTrends(GeoLocation geoLocation) throws TwitterException {
        return this.factory.createLocationList(get(this.conf.getRestBaseURL() + "trends/closest.json", new HttpParameter[]{new HttpParameter((String) TJAdUnitConstants.String.LAT, geoLocation.getLatitude()), new HttpParameter((String) TJAdUnitConstants.String.LONG, geoLocation.getLongitude())}));
    }

    public ResponseList<User> getContributees(long j) throws TwitterException {
        return this.factory.createUserList(get(this.conf.getRestBaseURL() + "users/contributees.json?user_id=" + j));
    }

    public ResponseList<User> getContributees(String str) throws TwitterException {
        return this.factory.createUserList(get(this.conf.getRestBaseURL() + "users/contributees.json?screen_name=" + str));
    }

    public ResponseList<User> getContributors(long j) throws TwitterException {
        return this.factory.createUserList(get(this.conf.getRestBaseURL() + "users/contributors.json?user_id=" + j));
    }

    public ResponseList<User> getContributors(String str) throws TwitterException {
        return this.factory.createUserList(get(this.conf.getRestBaseURL() + "users/contributors.json?screen_name=" + str));
    }

    public InputStream getDMImageAsStream(String str) throws TwitterException {
        return get(str).asStream();
    }

    public ResponseList<DirectMessage> getDirectMessages() throws TwitterException {
        return this.factory.createDirectMessageList(get(this.conf.getRestBaseURL() + "direct_messages.json"));
    }

    public ResponseList<DirectMessage> getDirectMessages(Paging paging) throws TwitterException {
        return this.factory.createDirectMessageList(get(this.conf.getRestBaseURL() + "direct_messages.json", paging.asPostParameterArray()));
    }

    public ResponseList<Status> getFavorites() throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "favorites/list.json"));
    }

    public ResponseList<Status> getFavorites(long j) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "favorites/list.json?user_id=" + j));
    }

    public ResponseList<Status> getFavorites(long j, Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "favorites/list.json", mergeParameters(new HttpParameter[]{new HttpParameter("user_id", j)}, paging.asPostParameterArray())));
    }

    public ResponseList<Status> getFavorites(String str) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "favorites/list.json?screen_name=" + str));
    }

    public ResponseList<Status> getFavorites(String str, Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "favorites/list.json", mergeParameters(new HttpParameter[]{new HttpParameter("screen_name", str)}, paging.asPostParameterArray())));
    }

    public ResponseList<Status> getFavorites(Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "favorites/list.json", paging.asPostParameterArray()));
    }

    public IDs getFollowersIDs(long j) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "followers/ids.json?cursor=" + j));
    }

    public IDs getFollowersIDs(long j, long j2) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "followers/ids.json?user_id=" + j + "&cursor=" + j2));
    }

    public IDs getFollowersIDs(long j, long j2, int i) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "followers/ids.json?user_id=" + j + "&cursor=" + j2 + "&count=" + i));
    }

    public IDs getFollowersIDs(String str, long j) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "followers/ids.json?screen_name=" + str + "&cursor=" + j));
    }

    public IDs getFollowersIDs(String str, long j, int i) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "followers/ids.json?screen_name=" + str + "&cursor=" + j + "&count=" + i));
    }

    public PagableResponseList<User> getFollowersList(long j, long j2) throws TwitterException {
        return getFollowersList(j, j2, 20);
    }

    public PagableResponseList<User> getFollowersList(long j, long j2, int i) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "followers/list.json?user_id=" + j + "&cursor=" + j2 + "&count=" + i));
    }

    public PagableResponseList<User> getFollowersList(long j, long j2, int i, boolean z, boolean z2) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "followers/list.json?user_id=" + j + "&cursor=" + j2 + "&count=" + i + "&skip_status=" + z + "&include_user_entities=" + z2));
    }

    public PagableResponseList<User> getFollowersList(String str, long j) throws TwitterException {
        return getFollowersList(str, j, 20);
    }

    public PagableResponseList<User> getFollowersList(String str, long j, int i) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "followers/list.json?screen_name=" + str + "&cursor=" + j + "&count=" + i));
    }

    public PagableResponseList<User> getFollowersList(String str, long j, int i, boolean z, boolean z2) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "followers/list.json?screen_name=" + str + "&cursor=" + j + "&count=" + i + "&skip_status=" + z + "&include_user_entities=" + z2));
    }

    public IDs getFriendsIDs(long j) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "friends/ids.json?cursor=" + j));
    }

    public IDs getFriendsIDs(long j, long j2) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "friends/ids.json?user_id=" + j + "&cursor=" + j2));
    }

    public IDs getFriendsIDs(long j, long j2, int i) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "friends/ids.json?user_id=" + j + "&cursor=" + j2 + "&count=" + i));
    }

    public IDs getFriendsIDs(String str, long j) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "friends/ids.json?screen_name=" + str + "&cursor=" + j));
    }

    public IDs getFriendsIDs(String str, long j, int i) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "friends/ids.json?screen_name=" + str + "&cursor=" + j + "&count=" + i));
    }

    public PagableResponseList<User> getFriendsList(long j, long j2) throws TwitterException {
        return getFriendsList(j, j2, 20);
    }

    public PagableResponseList<User> getFriendsList(long j, long j2, int i) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "friends/list.json?user_id=" + j + "&cursor=" + j2 + "&count=" + i));
    }

    public PagableResponseList<User> getFriendsList(long j, long j2, int i, boolean z, boolean z2) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "friends/list.json?user_id=" + j + "&cursor=" + j2 + "&count=" + i + "&skip_status=" + z + "&include_user_entities=" + z2));
    }

    public PagableResponseList<User> getFriendsList(String str, long j) throws TwitterException {
        return getFriendsList(str, j, 20);
    }

    public PagableResponseList<User> getFriendsList(String str, long j, int i) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "friends/list.json?screen_name=" + str + "&cursor=" + j + "&count=" + i));
    }

    public PagableResponseList<User> getFriendsList(String str, long j, int i, boolean z, boolean z2) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "friends/list.json?screen_name=" + str + "&cursor=" + j + "&count=" + i + "&skip_status=" + z + "&include_user_entities=" + z2));
    }

    public Place getGeoDetails(String str) throws TwitterException {
        return this.factory.createPlace(get(this.conf.getRestBaseURL() + "geo/id/" + str + ".json"));
    }

    public ResponseList<Status> getHomeTimeline() throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/home_timeline.json", new HttpParameter[]{this.INCLUDE_MY_RETWEET}));
    }

    public ResponseList<Status> getHomeTimeline(Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/home_timeline.json", mergeParameters(paging.asPostParameterArray(), new HttpParameter[]{this.INCLUDE_MY_RETWEET})));
    }

    public IDs getIncomingFriendships(long j) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "friendships/incoming.json?cursor=" + j));
    }

    public ResponseList<HelpResources.Language> getLanguages() throws TwitterException {
        return this.factory.createLanguageList(get(this.conf.getRestBaseURL() + "help/languages.json"));
    }

    public ResponseList<User> getMemberSuggestions(String str) throws TwitterException {
        try {
            return this.factory.createUserListFromJSONArray(get(this.conf.getRestBaseURL() + "users/suggestions/" + URLEncoder.encode(str, "UTF-8") + "/members.json"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseList<Status> getMentionsTimeline() throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/mentions_timeline.json"));
    }

    public ResponseList<Status> getMentionsTimeline(Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/mentions_timeline.json", paging.asPostParameterArray()));
    }

    public IDs getMutesIDs(long j) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "mutes/users/ids.json?cursor=" + j));
    }

    public PagableResponseList<User> getMutesList(long j) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "mutes/users/list.json?cursor=" + j));
    }

    public IDs getNoRetweetsFriendships() throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "friendships/no_retweets/ids.json"));
    }

    public OEmbed getOEmbed(OEmbedRequest oEmbedRequest) throws TwitterException {
        return this.factory.createOEmbed(get(this.conf.getRestBaseURL() + "statuses/oembed.json", oEmbedRequest.asHttpParameterArray()));
    }

    public IDs getOutgoingFriendships(long j) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "friendships/outgoing.json?cursor=" + j));
    }

    public Trends getPlaceTrends(int i) throws TwitterException {
        return this.factory.createTrends(get(this.conf.getRestBaseURL() + "trends/place.json?id=" + i));
    }

    public String getPrivacyPolicy() throws TwitterException {
        try {
            return get(this.conf.getRestBaseURL() + "help/privacy.json").asJSONObject().getString(ShareConstants.WEB_DIALOG_PARAM_PRIVACY);
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    public Map<String, RateLimitStatus> getRateLimitStatus() throws TwitterException {
        return this.factory.createRateLimitStatuses(get(this.conf.getRestBaseURL() + "application/rate_limit_status.json"));
    }

    public Map<String, RateLimitStatus> getRateLimitStatus(String... strArr) throws TwitterException {
        return this.factory.createRateLimitStatuses(get(this.conf.getRestBaseURL() + "application/rate_limit_status.json?resources=" + StringUtil.join(strArr)));
    }

    public IDs getRetweeterIds(long j, int i, long j2) throws TwitterException {
        return this.factory.createIDs(get(this.conf.getRestBaseURL() + "statuses/retweeters/ids.json?id=" + j + "&cursor=" + j2 + "&count=" + i));
    }

    public IDs getRetweeterIds(long j, long j2) throws TwitterException {
        return getRetweeterIds(j, 100, j2);
    }

    public ResponseList<Status> getRetweets(long j) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/retweets/" + j + ".json?count=100"));
    }

    public ResponseList<Status> getRetweetsOfMe() throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/retweets_of_me.json"));
    }

    public ResponseList<Status> getRetweetsOfMe(Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/retweets_of_me.json", paging.asPostParameterArray()));
    }

    public ResponseList<SavedSearch> getSavedSearches() throws TwitterException {
        return this.factory.createSavedSearchList(get(this.conf.getRestBaseURL() + "saved_searches/list.json"));
    }

    public ResponseList<DirectMessage> getSentDirectMessages() throws TwitterException {
        return this.factory.createDirectMessageList(get(this.conf.getRestBaseURL() + "direct_messages/sent.json"));
    }

    public ResponseList<DirectMessage> getSentDirectMessages(Paging paging) throws TwitterException {
        return this.factory.createDirectMessageList(get(this.conf.getRestBaseURL() + "direct_messages/sent.json", paging.asPostParameterArray()));
    }

    public ResponseList<Place> getSimilarPlaces(GeoLocation geoLocation, String str, String str2, String str3) throws TwitterException {
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(new HttpParameter(TJAdUnitConstants.String.LAT, geoLocation.getLatitude()));
        arrayList.add(new HttpParameter(TJAdUnitConstants.String.LONG, geoLocation.getLongitude()));
        arrayList.add(new HttpParameter("name", str));
        if (str2 != null) {
            arrayList.add(new HttpParameter("contained_within", str2));
        }
        if (str3 != null) {
            arrayList.add(new HttpParameter("attribute:street_address", str3));
        }
        return this.factory.createPlaceList(get(this.conf.getRestBaseURL() + "geo/similar_places.json", (HttpParameter[]) arrayList.toArray(new HttpParameter[arrayList.size()])));
    }

    public ResponseList<Category> getSuggestedUserCategories() throws TwitterException {
        return this.factory.createCategoryList(get(this.conf.getRestBaseURL() + "users/suggestions.json"));
    }

    public String getTermsOfService() throws TwitterException {
        try {
            return get(this.conf.getRestBaseURL() + "help/tos.json").asJSONObject().getString("tos");
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    public PagableResponseList<User> getUserListMembers(long j, long j2) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "lists/members.json?list_id=" + j + "&cursor=" + j2));
    }

    public PagableResponseList<User> getUserListMembers(long j, String str, long j2) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "lists/members.json?owner_id=" + j + "&slug=" + str + "&cursor=" + j2));
    }

    public PagableResponseList<User> getUserListMembers(String str, String str2, long j) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "lists/members.json?owner_screen_name=" + str + "&slug=" + str2 + "&cursor=" + j));
    }

    public PagableResponseList<UserList> getUserListMemberships(long j) throws TwitterException {
        return this.factory.createPagableUserListList(get(this.conf.getRestBaseURL() + "lists/memberships.json?cursor=" + j));
    }

    public PagableResponseList<UserList> getUserListMemberships(long j, long j2) throws TwitterException {
        return getUserListMemberships(j, j2, false);
    }

    public PagableResponseList<UserList> getUserListMemberships(long j, long j2, boolean z) throws TwitterException {
        return this.factory.createPagableUserListList(get(this.conf.getRestBaseURL() + "lists/memberships.json?user_id=" + j + "&cursor=" + j2 + "&filter_to_owned_lists=" + z));
    }

    public PagableResponseList<UserList> getUserListMemberships(String str, long j) throws TwitterException {
        return getUserListMemberships(str, j, false);
    }

    public PagableResponseList<UserList> getUserListMemberships(String str, long j, boolean z) throws TwitterException {
        return this.factory.createPagableUserListList(get(this.conf.getRestBaseURL() + "lists/memberships.json?screen_name=" + str + "&cursor=" + j + "&filter_to_owned_lists=" + z));
    }

    public ResponseList<Status> getUserListStatuses(long j, String str, Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "lists/statuses.json", mergeParameters(paging.asPostParameterArray(Paging.SMCP, "count"), new HttpParameter[]{new HttpParameter("owner_id", j), new HttpParameter("slug", str)})));
    }

    public ResponseList<Status> getUserListStatuses(long j, Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "lists/statuses.json", mergeParameters(paging.asPostParameterArray(Paging.SMCP, "count"), new HttpParameter("list_id", j))));
    }

    public ResponseList<Status> getUserListStatuses(String str, String str2, Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "lists/statuses.json", mergeParameters(paging.asPostParameterArray(Paging.SMCP, "count"), new HttpParameter[]{new HttpParameter("owner_screen_name", str), new HttpParameter("slug", str2)})));
    }

    public PagableResponseList<User> getUserListSubscribers(long j, long j2) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "lists/subscribers.json?list_id=" + j + "&cursor=" + j2));
    }

    public PagableResponseList<User> getUserListSubscribers(long j, String str, long j2) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "lists/subscribers.json?owner_id=" + j + "&slug=" + str + "&cursor=" + j2));
    }

    public PagableResponseList<User> getUserListSubscribers(String str, String str2, long j) throws TwitterException {
        return this.factory.createPagableUserList(get(this.conf.getRestBaseURL() + "lists/subscribers.json?owner_screen_name=" + str + "&slug=" + str2 + "&cursor=" + j));
    }

    public PagableResponseList<UserList> getUserListSubscriptions(String str, long j) throws TwitterException {
        return this.factory.createPagableUserListList(get(this.conf.getRestBaseURL() + "lists/subscriptions.json?screen_name=" + str + "&cursor=" + j));
    }

    public ResponseList<UserList> getUserLists(long j) throws TwitterException {
        return this.factory.createUserListList(get(this.conf.getRestBaseURL() + "lists/list.json?user_id=" + j));
    }

    public ResponseList<UserList> getUserLists(String str) throws TwitterException {
        return this.factory.createUserListList(get(this.conf.getRestBaseURL() + "lists/list.json?screen_name=" + str));
    }

    public PagableResponseList<UserList> getUserListsOwnerships(long j, int i, long j2) throws TwitterException {
        return this.factory.createPagableUserListList(get(this.conf.getRestBaseURL() + "lists/ownerships.json", new HttpParameter[]{new HttpParameter("user_id", j), new HttpParameter("count", i), new HttpParameter("cursor", j2)}));
    }

    public PagableResponseList<UserList> getUserListsOwnerships(String str, int i, long j) throws TwitterException {
        return this.factory.createPagableUserListList(get(this.conf.getRestBaseURL() + "lists/ownerships.json", new HttpParameter[]{new HttpParameter("screen_name", str), new HttpParameter("count", i), new HttpParameter("cursor", j)}));
    }

    public ResponseList<User> getUserSuggestions(String str) throws TwitterException {
        try {
            return this.factory.createUserListFromJSONArray_Users(get(this.conf.getRestBaseURL() + "users/suggestions/" + URLEncoder.encode(str, "UTF-8") + ".json"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseList<Status> getUserTimeline() throws TwitterException {
        return getUserTimeline(new Paging());
    }

    public ResponseList<Status> getUserTimeline(long j) throws TwitterException {
        return getUserTimeline(j, new Paging());
    }

    public ResponseList<Status> getUserTimeline(long j, Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/user_timeline.json", mergeParameters(new HttpParameter[]{new HttpParameter("user_id", j), this.INCLUDE_MY_RETWEET}, paging.asPostParameterArray())));
    }

    public ResponseList<Status> getUserTimeline(String str) throws TwitterException {
        return getUserTimeline(str, new Paging());
    }

    public ResponseList<Status> getUserTimeline(String str, Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/user_timeline.json", mergeParameters(new HttpParameter[]{new HttpParameter("screen_name", str), this.INCLUDE_MY_RETWEET}, paging.asPostParameterArray())));
    }

    public ResponseList<Status> getUserTimeline(Paging paging) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/user_timeline.json", mergeParameters(new HttpParameter[]{this.INCLUDE_MY_RETWEET}, paging.asPostParameterArray())));
    }

    public HelpResources help() {
        return this;
    }

    public ListsResources list() {
        return this;
    }

    public ResponseList<Status> lookup(long[] jArr) throws TwitterException {
        return this.factory.createStatusList(get(this.conf.getRestBaseURL() + "statuses/lookup.json?id=" + StringUtil.join(jArr)));
    }

    public ResponseList<Friendship> lookupFriendships(long[] jArr) throws TwitterException {
        return this.factory.createFriendshipList(get(this.conf.getRestBaseURL() + "friendships/lookup.json?user_id=" + StringUtil.join(jArr)));
    }

    public ResponseList<Friendship> lookupFriendships(String[] strArr) throws TwitterException {
        return this.factory.createFriendshipList(get(this.conf.getRestBaseURL() + "friendships/lookup.json?screen_name=" + StringUtil.join(strArr)));
    }

    public ResponseList<User> lookupUsers(long[] jArr) throws TwitterException {
        return this.factory.createUserList(get(this.conf.getRestBaseURL() + "users/lookup.json", new HttpParameter[]{new HttpParameter("user_id", StringUtil.join(jArr))}));
    }

    public ResponseList<User> lookupUsers(String[] strArr) throws TwitterException {
        return this.factory.createUserList(get(this.conf.getRestBaseURL() + "users/lookup.json", new HttpParameter[]{new HttpParameter("screen_name", StringUtil.join(strArr))}));
    }

    public PlacesGeoResources placesGeo() {
        return this;
    }

    public void removeProfileBanner() throws TwitterException {
        post(this.conf.getRestBaseURL() + "account/remove_profile_banner.json");
    }

    public User reportSpam(long j) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "users/report_spam.json?user_id=" + j));
    }

    public User reportSpam(String str) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "users/report_spam.json?screen_name=" + str));
    }

    public Status retweetStatus(long j) throws TwitterException {
        return this.factory.createStatus(post(this.conf.getRestBaseURL() + "statuses/retweet/" + j + ".json"));
    }

    public ResponseList<Place> reverseGeoCode(GeoQuery geoQuery) throws TwitterException {
        try {
            return this.factory.createPlaceList(get(this.conf.getRestBaseURL() + "geo/reverse_geocode.json", geoQuery.asHttpParameterArray()));
        } catch (TwitterException e) {
            if (e.getStatusCode() == 404) {
                return this.factory.createEmptyResponseList();
            }
            throw e;
        }
    }

    public SavedSearchesResources savedSearches() {
        return this;
    }

    public QueryResult search(Query query) throws TwitterException {
        return query.nextPage() != null ? this.factory.createQueryResult(get(this.conf.getRestBaseURL() + "search/tweets.json" + query.nextPage()), query) : this.factory.createQueryResult(get(this.conf.getRestBaseURL() + "search/tweets.json", query.asHttpParameterArray()), query);
    }

    public SearchResource search() {
        return this;
    }

    public ResponseList<Place> searchPlaces(GeoQuery geoQuery) throws TwitterException {
        return this.factory.createPlaceList(get(this.conf.getRestBaseURL() + "geo/search.json", geoQuery.asHttpParameterArray()));
    }

    public ResponseList<User> searchUsers(String str, int i) throws TwitterException {
        return this.factory.createUserList(get(this.conf.getRestBaseURL() + "users/search.json", new HttpParameter[]{new HttpParameter("q", str), new HttpParameter("per_page", 20), new HttpParameter("page", i)}));
    }

    public DirectMessage sendDirectMessage(long j, String str) throws TwitterException {
        return this.factory.createDirectMessage(post(this.conf.getRestBaseURL() + "direct_messages/new.json", new HttpParameter[]{new HttpParameter("user_id", j), new HttpParameter("text", str)}));
    }

    public DirectMessage sendDirectMessage(String str, String str2) throws TwitterException {
        return this.factory.createDirectMessage(post(this.conf.getRestBaseURL() + "direct_messages/new.json", new HttpParameter[]{new HttpParameter("screen_name", str), new HttpParameter("text", str2)}));
    }

    public DirectMessage showDirectMessage(long j) throws TwitterException {
        return this.factory.createDirectMessage(get(this.conf.getRestBaseURL() + "direct_messages/show.json?id=" + j));
    }

    public Relationship showFriendship(long j, long j2) throws TwitterException {
        return this.factory.createRelationship(get(this.conf.getRestBaseURL() + "friendships/show.json", new HttpParameter[]{new HttpParameter("source_id", j), new HttpParameter("target_id", j2)}));
    }

    public Relationship showFriendship(String str, String str2) throws TwitterException {
        return this.factory.createRelationship(get(this.conf.getRestBaseURL() + "friendships/show.json", HttpParameter.getParameterArray("source_screen_name", str, "target_screen_name", str2)));
    }

    public SavedSearch showSavedSearch(int i) throws TwitterException {
        return this.factory.createSavedSearch(get(this.conf.getRestBaseURL() + "saved_searches/show/" + i + ".json"));
    }

    public Status showStatus(long j) throws TwitterException {
        return this.factory.createStatus(get(this.conf.getRestBaseURL() + "statuses/show/" + j + ".json", new HttpParameter[]{this.INCLUDE_MY_RETWEET}));
    }

    public User showUser(long j) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "users/show.json?user_id=" + j));
    }

    public User showUser(String str) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "users/show.json?screen_name=" + str));
    }

    public UserList showUserList(long j) throws TwitterException {
        return this.factory.createAUserList(get(this.conf.getRestBaseURL() + "lists/show.json?list_id=" + j));
    }

    public UserList showUserList(long j, String str) throws TwitterException {
        return this.factory.createAUserList(get(this.conf.getRestBaseURL() + "lists/show.json?owner_id=" + j + "&slug=" + str));
    }

    public UserList showUserList(String str, String str2) throws TwitterException {
        return this.factory.createAUserList(get(this.conf.getRestBaseURL() + "lists/show.json?owner_screen_name=" + str + "&slug=" + str2));
    }

    public User showUserListMembership(long j, long j2) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "lists/members/show.json?list_id=" + j + "&user_id=" + j2));
    }

    public User showUserListMembership(long j, String str, long j2) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "lists/members/show.json?owner_id=" + j + "&slug=" + str + "&user_id=" + j2));
    }

    public User showUserListMembership(String str, String str2, long j) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "lists/members/show.json?owner_screen_name=" + str + "&slug=" + str2 + "&user_id=" + j));
    }

    public User showUserListSubscription(long j, long j2) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "lists/subscribers/show.json?list_id=" + j + "&user_id=" + j2));
    }

    public User showUserListSubscription(long j, String str, long j2) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "lists/subscribers/show.json?owner_id=" + j + "&slug=" + str + "&user_id=" + j2));
    }

    public User showUserListSubscription(String str, String str2, long j) throws TwitterException {
        return this.factory.createUser(get(this.conf.getRestBaseURL() + "lists/subscribers/show.json?owner_screen_name=" + str + "&slug=" + str2 + "&user_id=" + j));
    }

    public SpamReportingResource spamReporting() {
        return this;
    }

    public SuggestedUsersResources suggestedUsers() {
        return this;
    }

    public TimelinesResources timelines() {
        return this;
    }

    public String toString() {
        return "TwitterImpl{INCLUDE_MY_RETWEET=" + this.INCLUDE_MY_RETWEET + '}';
    }

    public TrendsResources trends() {
        return this;
    }

    public TweetsResources tweets() {
        return this;
    }

    public AccountSettings updateAccountSettings(Integer num, Boolean bool, String str, String str2, String str3, String str4) throws TwitterException {
        ArrayList arrayList = new ArrayList(6);
        if (num != null) {
            arrayList.add(new HttpParameter("trend_location_woeid", num.intValue()));
        }
        if (bool != null) {
            arrayList.add(new HttpParameter("sleep_time_enabled", bool.toString()));
        }
        if (str != null) {
            arrayList.add(new HttpParameter("start_sleep_time", str));
        }
        if (str2 != null) {
            arrayList.add(new HttpParameter("end_sleep_time", str2));
        }
        if (str3 != null) {
            arrayList.add(new HttpParameter("time_zone", str3));
        }
        if (str4 != null) {
            arrayList.add(new HttpParameter("lang", str4));
        }
        return this.factory.createAccountSettings(post(this.conf.getRestBaseURL() + "account/settings.json", (HttpParameter[]) arrayList.toArray(new HttpParameter[arrayList.size()])));
    }

    public Relationship updateFriendship(long j, boolean z, boolean z2) throws TwitterException {
        return this.factory.createRelationship(post(this.conf.getRestBaseURL() + "friendships/update.json", new HttpParameter[]{new HttpParameter("user_id", j), new HttpParameter((String) TapjoyConstants.TJC_NOTIFICATION_DEVICE_PREFIX, z), new HttpParameter("retweets", z2)}));
    }

    public Relationship updateFriendship(String str, boolean z, boolean z2) throws TwitterException {
        return this.factory.createRelationship(post(this.conf.getRestBaseURL() + "friendships/update.json", new HttpParameter[]{new HttpParameter("screen_name", str), new HttpParameter((String) TapjoyConstants.TJC_NOTIFICATION_DEVICE_PREFIX, z), new HttpParameter("retweets", z2)}));
    }

    public User updateProfile(String str, String str2, String str3, String str4) throws TwitterException {
        ArrayList arrayList = new ArrayList(4);
        addParameterToList(arrayList, "name", str);
        addParameterToList(arrayList, "url", str2);
        addParameterToList(arrayList, "location", str3);
        addParameterToList(arrayList, "description", str4);
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "account/update_profile.json", (HttpParameter[]) arrayList.toArray(new HttpParameter[arrayList.size()])));
    }

    public User updateProfileBackgroundImage(File file, boolean z) throws TwitterException {
        checkFileValidity(file);
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "account/update_profile_background_image.json", new HttpParameter[]{new HttpParameter("image", file), new HttpParameter("tile", z)}));
    }

    public User updateProfileBackgroundImage(InputStream inputStream, boolean z) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "account/update_profile_background_image.json", new HttpParameter[]{new HttpParameter("image", "image", inputStream), new HttpParameter("tile", z)}));
    }

    public void updateProfileBanner(File file) throws TwitterException {
        checkFileValidity(file);
        post(this.conf.getRestBaseURL() + "account/update_profile_banner.json", new HttpParameter[]{new HttpParameter((String) AdMobAd.BANNER, file)});
    }

    public void updateProfileBanner(InputStream inputStream) throws TwitterException {
        post(this.conf.getRestBaseURL() + "account/update_profile_banner.json", new HttpParameter[]{new HttpParameter(AdMobAd.BANNER, AdMobAd.BANNER, inputStream)});
    }

    public User updateProfileColors(String str, String str2, String str3, String str4, String str5) throws TwitterException {
        ArrayList arrayList = new ArrayList(6);
        addParameterToList(arrayList, "profile_background_color", str);
        addParameterToList(arrayList, "profile_text_color", str2);
        addParameterToList(arrayList, "profile_link_color", str3);
        addParameterToList(arrayList, "profile_sidebar_fill_color", str4);
        addParameterToList(arrayList, "profile_sidebar_border_color", str5);
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "account/update_profile_colors.json", (HttpParameter[]) arrayList.toArray(new HttpParameter[arrayList.size()])));
    }

    public User updateProfileImage(File file) throws TwitterException {
        checkFileValidity(file);
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "account/update_profile_image.json", new HttpParameter[]{new HttpParameter("image", file)}));
    }

    public User updateProfileImage(InputStream inputStream) throws TwitterException {
        return this.factory.createUser(post(this.conf.getRestBaseURL() + "account/update_profile_image.json", new HttpParameter[]{new HttpParameter("image", "image", inputStream)}));
    }

    public Status updateStatus(String str) throws TwitterException {
        return this.factory.createStatus(post(this.conf.getRestBaseURL() + "statuses/update.json", new HttpParameter[]{new HttpParameter("status", str)}));
    }

    public Status updateStatus(StatusUpdate statusUpdate) throws TwitterException {
        return this.factory.createStatus(post(this.conf.getRestBaseURL() + (statusUpdate.isForUpdateWithMedia() ? "statuses/update_with_media.json" : "statuses/update.json"), statusUpdate.asHttpParameterArray()));
    }

    public UserList updateUserList(long j, String str, String str2, boolean z, String str3) throws TwitterException {
        return updateUserList(str2, z, str3, new HttpParameter("owner_id", j), new HttpParameter("slug", str));
    }

    public UserList updateUserList(long j, String str, boolean z, String str2) throws TwitterException {
        return updateUserList(str, z, str2, new HttpParameter("list_id", j));
    }

    public UserList updateUserList(String str, String str2, String str3, boolean z, String str4) throws TwitterException {
        return updateUserList(str3, z, str4, new HttpParameter("owner_screen_name", str), new HttpParameter("slug", str2));
    }

    public UploadedMedia uploadMedia(File file) throws TwitterException {
        checkFileValidity(file);
        return new UploadedMedia(post(this.conf.getUploadBaseURL() + "media/upload.json", new HttpParameter[]{new HttpParameter("media", file)}).asJSONObject());
    }

    public UsersResources users() {
        return this;
    }

    public User verifyCredentials() throws TwitterException {
        return super.fillInIDAndScreenName();
    }
}
