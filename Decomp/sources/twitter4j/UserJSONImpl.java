package twitter4j;

import com.facebook.share.internal.ShareConstants;
import java.io.Serializable;
import java.util.Date;
import jp.stargarage.g2metrics.BuildConfig;
import twitter4j.conf.Configuration;

final class UserJSONImpl extends TwitterResponseImpl implements User, Serializable {
    private static final long serialVersionUID = -5448266606847617015L;
    private Date createdAt;
    private String description;
    private URLEntity[] descriptionURLEntities;
    private int favouritesCount;
    private int followersCount;
    private int friendsCount;
    private long id;
    private boolean isContributorsEnabled;
    private boolean isDefaultProfile;
    private boolean isDefaultProfileImage;
    private boolean isFollowRequestSent;
    private boolean isGeoEnabled;
    private boolean isProtected;
    private boolean isVerified;
    private String lang;
    private int listedCount;
    private String location;
    private String name;
    private String profileBackgroundColor;
    private String profileBackgroundImageUrl;
    private String profileBackgroundImageUrlHttps;
    private boolean profileBackgroundTiled;
    private String profileBannerImageUrl;
    private String profileImageUrl;
    private String profileImageUrlHttps;
    private String profileLinkColor;
    private String profileSidebarBorderColor;
    private String profileSidebarFillColor;
    private String profileTextColor;
    private boolean profileUseBackgroundImage;
    private String screenName;
    private boolean showAllInlineMedia;
    private Status status;
    private int statusesCount;
    private String timeZone;
    private boolean translator;
    private String url;
    private URLEntity urlEntity;
    private int utcOffset;

    UserJSONImpl() {
    }

    UserJSONImpl(HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        super(httpResponse);
        if (configuration.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
        }
        JSONObject asJSONObject = httpResponse.asJSONObject();
        init(asJSONObject);
        if (configuration.isJSONStoreEnabled()) {
            TwitterObjectFactory.registerJSONObject(this, asJSONObject);
        }
    }

    UserJSONImpl(JSONObject jSONObject) throws TwitterException {
        init(jSONObject);
    }

    static PagableResponseList<User> createPagableUserList(HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        try {
            if (configuration.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
            }
            JSONObject asJSONObject = httpResponse.asJSONObject();
            JSONArray jSONArray = asJSONObject.getJSONArray("users");
            int length = jSONArray.length();
            PagableResponseListImpl pagableResponseListImpl = new PagableResponseListImpl(length, asJSONObject, httpResponse);
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                UserJSONImpl userJSONImpl = new UserJSONImpl(jSONObject);
                if (configuration.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(userJSONImpl, jSONObject);
                }
                pagableResponseListImpl.add(userJSONImpl);
            }
            if (configuration.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(pagableResponseListImpl, asJSONObject);
            }
            return pagableResponseListImpl;
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    static ResponseList<User> createUserList(HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        return createUserList(httpResponse.asJSONArray(), httpResponse, configuration);
    }

    static ResponseList<User> createUserList(JSONArray jSONArray, HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        try {
            if (configuration.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
            }
            int length = jSONArray.length();
            ResponseListImpl responseListImpl = new ResponseListImpl(length, httpResponse);
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                UserJSONImpl userJSONImpl = new UserJSONImpl(jSONObject);
                responseListImpl.add(userJSONImpl);
                if (configuration.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(userJSONImpl, jSONObject);
                }
            }
            if (configuration.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(responseListImpl, jSONArray);
            }
            return responseListImpl;
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    private static URLEntity[] getURLEntitiesFromJSON(JSONObject jSONObject, String str) throws JSONException, TwitterException {
        if (!jSONObject.isNull("entities")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("entities");
            if (!jSONObject2.isNull(str)) {
                JSONObject jSONObject3 = jSONObject2.getJSONObject(str);
                if (!jSONObject3.isNull("urls")) {
                    JSONArray jSONArray = jSONObject3.getJSONArray("urls");
                    int length = jSONArray.length();
                    URLEntity[] uRLEntityArr = new URLEntity[length];
                    for (int i = 0; i < length; i++) {
                        uRLEntityArr[i] = new URLEntityJSONImpl(jSONArray.getJSONObject(i));
                    }
                    return uRLEntityArr;
                }
            }
        }
        return null;
    }

    private void init(JSONObject jSONObject) throws TwitterException {
        try {
            this.id = ParseUtil.getLong(ShareConstants.WEB_DIALOG_PARAM_ID, jSONObject);
            this.name = ParseUtil.getRawString("name", jSONObject);
            this.screenName = ParseUtil.getRawString("screen_name", jSONObject);
            this.location = ParseUtil.getRawString("location", jSONObject);
            this.descriptionURLEntities = getURLEntitiesFromJSON(jSONObject, "description");
            this.descriptionURLEntities = this.descriptionURLEntities == null ? new URLEntity[0] : this.descriptionURLEntities;
            URLEntity[] uRLEntitiesFromJSON = getURLEntitiesFromJSON(jSONObject, "url");
            if (uRLEntitiesFromJSON != null && uRLEntitiesFromJSON.length > 0) {
                this.urlEntity = uRLEntitiesFromJSON[0];
            }
            this.description = ParseUtil.getRawString("description", jSONObject);
            if (this.description != null) {
                this.description = HTMLEntity.unescapeAndSlideEntityIncdices(this.description, (UserMentionEntity[]) null, this.descriptionURLEntities, (HashtagEntity[]) null, (MediaEntity[]) null);
            }
            this.isContributorsEnabled = ParseUtil.getBoolean("contributors_enabled", jSONObject);
            this.profileImageUrl = ParseUtil.getRawString("profile_image_url", jSONObject);
            this.profileImageUrlHttps = ParseUtil.getRawString("profile_image_url_https", jSONObject);
            this.isDefaultProfileImage = ParseUtil.getBoolean("default_profile_image", jSONObject);
            this.url = ParseUtil.getRawString("url", jSONObject);
            this.isProtected = ParseUtil.getBoolean("protected", jSONObject);
            this.isGeoEnabled = ParseUtil.getBoolean("geo_enabled", jSONObject);
            this.isVerified = ParseUtil.getBoolean("verified", jSONObject);
            this.translator = ParseUtil.getBoolean("is_translator", jSONObject);
            this.followersCount = ParseUtil.getInt("followers_count", jSONObject);
            this.profileBackgroundColor = ParseUtil.getRawString("profile_background_color", jSONObject);
            this.profileTextColor = ParseUtil.getRawString("profile_text_color", jSONObject);
            this.profileLinkColor = ParseUtil.getRawString("profile_link_color", jSONObject);
            this.profileSidebarFillColor = ParseUtil.getRawString("profile_sidebar_fill_color", jSONObject);
            this.profileSidebarBorderColor = ParseUtil.getRawString("profile_sidebar_border_color", jSONObject);
            this.profileUseBackgroundImage = ParseUtil.getBoolean("profile_use_background_image", jSONObject);
            this.isDefaultProfile = ParseUtil.getBoolean("default_profile", jSONObject);
            this.showAllInlineMedia = ParseUtil.getBoolean("show_all_inline_media", jSONObject);
            this.friendsCount = ParseUtil.getInt("friends_count", jSONObject);
            this.createdAt = ParseUtil.getDate("created_at", jSONObject, "EEE MMM dd HH:mm:ss z yyyy");
            this.favouritesCount = ParseUtil.getInt("favourites_count", jSONObject);
            this.utcOffset = ParseUtil.getInt("utc_offset", jSONObject);
            this.timeZone = ParseUtil.getRawString("time_zone", jSONObject);
            this.profileBackgroundImageUrl = ParseUtil.getRawString("profile_background_image_url", jSONObject);
            this.profileBackgroundImageUrlHttps = ParseUtil.getRawString("profile_background_image_url_https", jSONObject);
            this.profileBannerImageUrl = ParseUtil.getRawString("profile_banner_url", jSONObject);
            this.profileBackgroundTiled = ParseUtil.getBoolean("profile_background_tile", jSONObject);
            this.lang = ParseUtil.getRawString("lang", jSONObject);
            this.statusesCount = ParseUtil.getInt("statuses_count", jSONObject);
            this.listedCount = ParseUtil.getInt("listed_count", jSONObject);
            this.isFollowRequestSent = ParseUtil.getBoolean("follow_request_sent", jSONObject);
            if (!jSONObject.isNull("status")) {
                this.status = new StatusJSONImpl(jSONObject.getJSONObject("status"));
            }
        } catch (JSONException e) {
            throw new TwitterException(e.getMessage() + ":" + jSONObject.toString(), (Throwable) e);
        }
    }

    private String toResizedURL(String str, String str2) {
        if (str == null) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf("_");
        int lastIndexOf2 = str.lastIndexOf(".");
        String str3 = str.substring(0, lastIndexOf) + str2;
        return lastIndexOf2 > str.lastIndexOf("/") ? str3 + str.substring(lastIndexOf2) : str3;
    }

    public int compareTo(User user) {
        return (int) (this.id - user.getId());
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this != obj) {
            return (obj instanceof User) && ((User) obj).getId() == this.id;
        }
        return true;
    }

    public String getBiggerProfileImageURL() {
        return toResizedURL(this.profileImageUrl, "_bigger");
    }

    public String getBiggerProfileImageURLHttps() {
        return toResizedURL(this.profileImageUrlHttps, "_bigger");
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public String getDescription() {
        return this.description;
    }

    public URLEntity[] getDescriptionURLEntities() {
        return this.descriptionURLEntities;
    }

    public int getFavouritesCount() {
        return this.favouritesCount;
    }

    public int getFollowersCount() {
        return this.followersCount;
    }

    public int getFriendsCount() {
        return this.friendsCount;
    }

    public long getId() {
        return this.id;
    }

    public String getLang() {
        return this.lang;
    }

    public int getListedCount() {
        return this.listedCount;
    }

    public String getLocation() {
        return this.location;
    }

    public String getMiniProfileImageURL() {
        return toResizedURL(this.profileImageUrl, "_mini");
    }

    public String getMiniProfileImageURLHttps() {
        return toResizedURL(this.profileImageUrlHttps, "_mini");
    }

    public String getName() {
        return this.name;
    }

    public String getOriginalProfileImageURL() {
        return toResizedURL(this.profileImageUrl, BuildConfig.FLAVOR);
    }

    public String getOriginalProfileImageURLHttps() {
        return toResizedURL(this.profileImageUrlHttps, BuildConfig.FLAVOR);
    }

    public String getProfileBackgroundColor() {
        return this.profileBackgroundColor;
    }

    public String getProfileBackgroundImageURL() {
        return this.profileBackgroundImageUrl;
    }

    public String getProfileBackgroundImageUrlHttps() {
        return this.profileBackgroundImageUrlHttps;
    }

    public String getProfileBannerIPadRetinaURL() {
        if (this.profileBannerImageUrl != null) {
            return this.profileBannerImageUrl + "/ipad_retina";
        }
        return null;
    }

    public String getProfileBannerIPadURL() {
        if (this.profileBannerImageUrl != null) {
            return this.profileBannerImageUrl + "/ipad";
        }
        return null;
    }

    public String getProfileBannerMobileRetinaURL() {
        if (this.profileBannerImageUrl != null) {
            return this.profileBannerImageUrl + "/mobile_retina";
        }
        return null;
    }

    public String getProfileBannerMobileURL() {
        if (this.profileBannerImageUrl != null) {
            return this.profileBannerImageUrl + "/mobile";
        }
        return null;
    }

    public String getProfileBannerRetinaURL() {
        if (this.profileBannerImageUrl != null) {
            return this.profileBannerImageUrl + "/web_retina";
        }
        return null;
    }

    public String getProfileBannerURL() {
        if (this.profileBannerImageUrl != null) {
            return this.profileBannerImageUrl + "/web";
        }
        return null;
    }

    public String getProfileImageURL() {
        return this.profileImageUrl;
    }

    public String getProfileImageURLHttps() {
        return this.profileImageUrlHttps;
    }

    public String getProfileLinkColor() {
        return this.profileLinkColor;
    }

    public String getProfileSidebarBorderColor() {
        return this.profileSidebarBorderColor;
    }

    public String getProfileSidebarFillColor() {
        return this.profileSidebarFillColor;
    }

    public String getProfileTextColor() {
        return this.profileTextColor;
    }

    public String getScreenName() {
        return this.screenName;
    }

    public Status getStatus() {
        return this.status;
    }

    public int getStatusesCount() {
        return this.statusesCount;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public String getURL() {
        return this.url;
    }

    public URLEntity getURLEntity() {
        if (this.urlEntity == null) {
            String str = this.url == null ? BuildConfig.FLAVOR : this.url;
            this.urlEntity = new URLEntityJSONImpl(0, str.length(), str, str, str);
        }
        return this.urlEntity;
    }

    public int getUtcOffset() {
        return this.utcOffset;
    }

    public int hashCode() {
        return (int) this.id;
    }

    public boolean isContributorsEnabled() {
        return this.isContributorsEnabled;
    }

    public boolean isDefaultProfile() {
        return this.isDefaultProfile;
    }

    public boolean isDefaultProfileImage() {
        return this.isDefaultProfileImage;
    }

    public boolean isFollowRequestSent() {
        return this.isFollowRequestSent;
    }

    public boolean isGeoEnabled() {
        return this.isGeoEnabled;
    }

    public boolean isProfileBackgroundTiled() {
        return this.profileBackgroundTiled;
    }

    public boolean isProfileUseBackgroundImage() {
        return this.profileUseBackgroundImage;
    }

    public boolean isProtected() {
        return this.isProtected;
    }

    public boolean isShowAllInlineMedia() {
        return this.showAllInlineMedia;
    }

    public boolean isTranslator() {
        return this.translator;
    }

    public boolean isVerified() {
        return this.isVerified;
    }

    public String toString() {
        return "UserJSONImpl{id=" + this.id + ", name='" + this.name + '\'' + ", screenName='" + this.screenName + '\'' + ", location='" + this.location + '\'' + ", description='" + this.description + '\'' + ", isContributorsEnabled=" + this.isContributorsEnabled + ", profileImageUrl='" + this.profileImageUrl + '\'' + ", profileImageUrlHttps='" + this.profileImageUrlHttps + '\'' + ", isDefaultProfileImage=" + this.isDefaultProfileImage + ", url='" + this.url + '\'' + ", isProtected=" + this.isProtected + ", followersCount=" + this.followersCount + ", status=" + this.status + ", profileBackgroundColor='" + this.profileBackgroundColor + '\'' + ", profileTextColor='" + this.profileTextColor + '\'' + ", profileLinkColor='" + this.profileLinkColor + '\'' + ", profileSidebarFillColor='" + this.profileSidebarFillColor + '\'' + ", profileSidebarBorderColor='" + this.profileSidebarBorderColor + '\'' + ", profileUseBackgroundImage=" + this.profileUseBackgroundImage + ", isDefaultProfile=" + this.isDefaultProfile + ", showAllInlineMedia=" + this.showAllInlineMedia + ", friendsCount=" + this.friendsCount + ", createdAt=" + this.createdAt + ", favouritesCount=" + this.favouritesCount + ", utcOffset=" + this.utcOffset + ", timeZone='" + this.timeZone + '\'' + ", profileBackgroundImageUrl='" + this.profileBackgroundImageUrl + '\'' + ", profileBackgroundImageUrlHttps='" + this.profileBackgroundImageUrlHttps + '\'' + ", profileBackgroundTiled=" + this.profileBackgroundTiled + ", lang='" + this.lang + '\'' + ", statusesCount=" + this.statusesCount + ", isGeoEnabled=" + this.isGeoEnabled + ", isVerified=" + this.isVerified + ", translator=" + this.translator + ", listedCount=" + this.listedCount + ", isFollowRequestSent=" + this.isFollowRequestSent + '}';
    }
}
