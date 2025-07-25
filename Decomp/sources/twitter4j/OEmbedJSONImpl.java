package twitter4j;

import com.facebook.internal.ServerProtocol;
import java.io.Serializable;
import jp.co.voyagegroup.android.fluct.jar.util.FluctConstants;
import twitter4j.conf.Configuration;

public class OEmbedJSONImpl extends TwitterResponseImpl implements OEmbed, Serializable {
    private static final long serialVersionUID = -2207801480251709819L;
    private String authorName;
    private String authorURL;
    private long cacheAge;
    private String html;
    private String url;
    private String version;
    private int width;

    OEmbedJSONImpl(HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        super(httpResponse);
        JSONObject asJSONObject = httpResponse.asJSONObject();
        init(asJSONObject);
        if (configuration.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, asJSONObject);
        }
    }

    OEmbedJSONImpl(JSONObject jSONObject) throws TwitterException {
        init(jSONObject);
    }

    private void init(JSONObject jSONObject) throws TwitterException {
        try {
            this.html = jSONObject.getString("html");
            this.authorName = jSONObject.getString("author_name");
            this.url = jSONObject.getString("url");
            this.version = jSONObject.getString(ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION);
            this.cacheAge = jSONObject.getLong("cache_age");
            this.authorURL = jSONObject.getString("author_url");
            this.width = jSONObject.getInt(FluctConstants.XML_NODE_WIDTH);
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        OEmbedJSONImpl oEmbedJSONImpl = (OEmbedJSONImpl) obj;
        if (this.cacheAge != oEmbedJSONImpl.cacheAge) {
            return false;
        }
        if (this.width != oEmbedJSONImpl.width) {
            return false;
        }
        if (this.authorName == null ? oEmbedJSONImpl.authorName != null : !this.authorName.equals(oEmbedJSONImpl.authorName)) {
            return false;
        }
        if (this.authorURL == null ? oEmbedJSONImpl.authorURL != null : !this.authorURL.equals(oEmbedJSONImpl.authorURL)) {
            return false;
        }
        if (this.html == null ? oEmbedJSONImpl.html != null : !this.html.equals(oEmbedJSONImpl.html)) {
            return false;
        }
        if (this.url == null ? oEmbedJSONImpl.url != null : !this.url.equals(oEmbedJSONImpl.url)) {
            return false;
        }
        if (this.version != null) {
            if (this.version.equals(oEmbedJSONImpl.version)) {
                return true;
            }
        } else if (oEmbedJSONImpl.version == null) {
            return true;
        }
        return false;
    }

    public /* bridge */ /* synthetic */ int getAccessLevel() {
        return super.getAccessLevel();
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public String getAuthorURL() {
        return this.authorURL;
    }

    public long getCacheAge() {
        return this.cacheAge;
    }

    public String getHtml() {
        return this.html;
    }

    public /* bridge */ /* synthetic */ RateLimitStatus getRateLimitStatus() {
        return super.getRateLimitStatus();
    }

    public String getURL() {
        return this.url;
    }

    public String getVersion() {
        return this.version;
    }

    public int getWidth() {
        return this.width;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (((((((((this.html != null ? this.html.hashCode() : 0) * 31) + (this.authorName != null ? this.authorName.hashCode() : 0)) * 31) + (this.url != null ? this.url.hashCode() : 0)) * 31) + (this.version != null ? this.version.hashCode() : 0)) * 31) + ((int) (this.cacheAge ^ (this.cacheAge >>> 32)))) * 31;
        if (this.authorURL != null) {
            i = this.authorURL.hashCode();
        }
        return ((hashCode + i) * 31) + this.width;
    }

    public String toString() {
        return "OEmbedJSONImpl{html='" + this.html + '\'' + ", authorName='" + this.authorName + '\'' + ", url='" + this.url + '\'' + ", version='" + this.version + '\'' + ", cacheAge=" + this.cacheAge + ", authorURL='" + this.authorURL + '\'' + ", width=" + this.width + '}';
    }
}
