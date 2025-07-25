package twitter4j;

import com.facebook.share.internal.ShareConstants;
import java.util.HashMap;
import java.util.Map;
import twitter4j.MediaEntity;

public class MediaEntityJSONImpl extends EntityIndex implements MediaEntity {
    private static final long serialVersionUID = 3609683338035442290L;
    private String displayURL;
    private String expandedURL;
    private long id;
    private String mediaURL;
    private String mediaURLHttps;
    private Map<Integer, MediaEntity.Size> sizes;
    private String type;
    private String url;

    static class Size implements MediaEntity.Size {
        private static final long serialVersionUID = -2515842281909325169L;
        int height;
        int resize;
        int width;

        Size(JSONObject jSONObject) throws JSONException {
            this.width = jSONObject.getInt("w");
            this.height = jSONObject.getInt("h");
            this.resize = "fit".equals(jSONObject.getString("resize")) ? 100 : MediaEntity.Size.CROP;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Size)) {
                return false;
            }
            Size size = (Size) obj;
            if (this.height != size.height) {
                return false;
            }
            if (this.resize != size.resize) {
                return false;
            }
            return this.width == size.width;
        }

        public int getHeight() {
            return this.height;
        }

        public int getResize() {
            return this.resize;
        }

        public int getWidth() {
            return this.width;
        }

        public int hashCode() {
            return (((this.width * 31) + this.height) * 31) + this.resize;
        }

        public String toString() {
            return "Size{width=" + this.width + ", height=" + this.height + ", resize=" + this.resize + '}';
        }
    }

    MediaEntityJSONImpl() {
    }

    MediaEntityJSONImpl(JSONObject jSONObject) throws TwitterException {
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("indices");
            setStart(jSONArray.getInt(0));
            setEnd(jSONArray.getInt(1));
            this.id = ParseUtil.getLong(ShareConstants.WEB_DIALOG_PARAM_ID, jSONObject);
            this.url = jSONObject.getString("url");
            this.expandedURL = jSONObject.getString("expanded_url");
            this.mediaURL = jSONObject.getString("media_url");
            this.mediaURLHttps = jSONObject.getString("media_url_https");
            this.displayURL = jSONObject.getString("display_url");
            JSONObject jSONObject2 = jSONObject.getJSONObject("sizes");
            this.sizes = new HashMap(4);
            addMediaEntitySizeIfNotNull(this.sizes, jSONObject2, MediaEntity.Size.LARGE, "large");
            addMediaEntitySizeIfNotNull(this.sizes, jSONObject2, MediaEntity.Size.MEDIUM, "medium");
            addMediaEntitySizeIfNotNull(this.sizes, jSONObject2, MediaEntity.Size.SMALL, "small");
            addMediaEntitySizeIfNotNull(this.sizes, jSONObject2, MediaEntity.Size.THUMB, "thumb");
            if (!jSONObject.isNull("type")) {
                this.type = jSONObject.getString("type");
            }
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    private void addMediaEntitySizeIfNotNull(Map<Integer, MediaEntity.Size> map, JSONObject jSONObject, Integer num, String str) throws JSONException {
        if (!jSONObject.isNull(str)) {
            map.put(num, new Size(jSONObject.getJSONObject(str)));
        }
    }

    public /* bridge */ /* synthetic */ int compareTo(EntityIndex entityIndex) {
        return super.compareTo(entityIndex);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaEntityJSONImpl)) {
            return false;
        }
        return this.id == ((MediaEntityJSONImpl) obj).id;
    }

    public String getDisplayURL() {
        return this.displayURL;
    }

    public int getEnd() {
        return super.getEnd();
    }

    public String getExpandedURL() {
        return this.expandedURL;
    }

    public long getId() {
        return this.id;
    }

    public String getMediaURL() {
        return this.mediaURL;
    }

    public String getMediaURLHttps() {
        return this.mediaURLHttps;
    }

    public Map<Integer, MediaEntity.Size> getSizes() {
        return this.sizes;
    }

    public int getStart() {
        return super.getStart();
    }

    public String getText() {
        return this.url;
    }

    public String getType() {
        return this.type;
    }

    public String getURL() {
        return this.url;
    }

    public int hashCode() {
        return (int) (this.id ^ (this.id >>> 32));
    }

    public String toString() {
        return "MediaEntityJSONImpl{id=" + this.id + ", url=" + this.url + ", mediaURL=" + this.mediaURL + ", mediaURLHttps=" + this.mediaURLHttps + ", expandedURL=" + this.expandedURL + ", displayURL='" + this.displayURL + '\'' + ", sizes=" + this.sizes + ", type=" + this.type + '}';
    }
}
