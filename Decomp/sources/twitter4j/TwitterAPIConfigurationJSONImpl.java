package twitter4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import twitter4j.MediaEntity;
import twitter4j.MediaEntityJSONImpl;
import twitter4j.conf.Configuration;

class TwitterAPIConfigurationJSONImpl extends TwitterResponseImpl implements TwitterAPIConfiguration {
    private static final long serialVersionUID = -3588904550808591686L;
    private int charactersReservedPerMedia;
    private int maxMediaPerUpload;
    private String[] nonUsernamePaths;
    private int photoSizeLimit;
    private Map<Integer, MediaEntity.Size> photoSizes;
    private int shortURLLength;
    private int shortURLLengthHttps;

    TwitterAPIConfigurationJSONImpl(HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        super(httpResponse);
        try {
            JSONObject asJSONObject = httpResponse.asJSONObject();
            this.photoSizeLimit = ParseUtil.getInt("photo_size_limit", asJSONObject);
            this.shortURLLength = ParseUtil.getInt("short_url_length", asJSONObject);
            this.shortURLLengthHttps = ParseUtil.getInt("short_url_length_https", asJSONObject);
            this.charactersReservedPerMedia = ParseUtil.getInt("characters_reserved_per_media", asJSONObject);
            JSONObject jSONObject = asJSONObject.getJSONObject("photo_sizes");
            this.photoSizes = new HashMap(4);
            this.photoSizes.put(MediaEntity.Size.LARGE, new MediaEntityJSONImpl.Size(jSONObject.getJSONObject("large")));
            this.photoSizes.put(MediaEntity.Size.MEDIUM, new MediaEntityJSONImpl.Size(jSONObject.isNull("med") ? jSONObject.getJSONObject("medium") : jSONObject.getJSONObject("med")));
            this.photoSizes.put(MediaEntity.Size.SMALL, new MediaEntityJSONImpl.Size(jSONObject.getJSONObject("small")));
            this.photoSizes.put(MediaEntity.Size.THUMB, new MediaEntityJSONImpl.Size(jSONObject.getJSONObject("thumb")));
            if (configuration.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
                TwitterObjectFactory.registerJSONObject(this, httpResponse.asJSONObject());
            }
            JSONArray jSONArray = asJSONObject.getJSONArray("non_username_paths");
            this.nonUsernamePaths = new String[jSONArray.length()];
            for (int i = 0; i < jSONArray.length(); i++) {
                this.nonUsernamePaths[i] = jSONArray.getString(i);
            }
            this.maxMediaPerUpload = ParseUtil.getInt("max_media_per_upload", asJSONObject);
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TwitterAPIConfigurationJSONImpl)) {
            return false;
        }
        TwitterAPIConfigurationJSONImpl twitterAPIConfigurationJSONImpl = (TwitterAPIConfigurationJSONImpl) obj;
        if (this.charactersReservedPerMedia != twitterAPIConfigurationJSONImpl.charactersReservedPerMedia) {
            return false;
        }
        if (this.maxMediaPerUpload != twitterAPIConfigurationJSONImpl.maxMediaPerUpload) {
            return false;
        }
        if (this.photoSizeLimit != twitterAPIConfigurationJSONImpl.photoSizeLimit) {
            return false;
        }
        if (this.shortURLLength != twitterAPIConfigurationJSONImpl.shortURLLength) {
            return false;
        }
        if (this.shortURLLengthHttps != twitterAPIConfigurationJSONImpl.shortURLLengthHttps) {
            return false;
        }
        if (!Arrays.equals(this.nonUsernamePaths, twitterAPIConfigurationJSONImpl.nonUsernamePaths)) {
            return false;
        }
        if (this.photoSizes != null) {
            if (this.photoSizes.equals(twitterAPIConfigurationJSONImpl.photoSizes)) {
                return true;
            }
        } else if (twitterAPIConfigurationJSONImpl.photoSizes == null) {
            return true;
        }
        return false;
    }

    public int getCharactersReservedPerMedia() {
        return this.charactersReservedPerMedia;
    }

    public int getMaxMediaPerUpload() {
        return this.maxMediaPerUpload;
    }

    public String[] getNonUsernamePaths() {
        return this.nonUsernamePaths;
    }

    public int getPhotoSizeLimit() {
        return this.photoSizeLimit;
    }

    public Map<Integer, MediaEntity.Size> getPhotoSizes() {
        return this.photoSizes;
    }

    public int getShortURLLength() {
        return this.shortURLLength;
    }

    public int getShortURLLengthHttps() {
        return this.shortURLLengthHttps;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((this.photoSizeLimit * 31) + this.shortURLLength) * 31) + this.shortURLLengthHttps) * 31) + this.charactersReservedPerMedia) * 31) + (this.photoSizes != null ? this.photoSizes.hashCode() : 0)) * 31;
        if (this.nonUsernamePaths != null) {
            i = Arrays.hashCode(this.nonUsernamePaths);
        }
        return ((hashCode + i) * 31) + this.maxMediaPerUpload;
    }

    public String toString() {
        return "TwitterAPIConfigurationJSONImpl{photoSizeLimit=" + this.photoSizeLimit + ", shortURLLength=" + this.shortURLLength + ", shortURLLengthHttps=" + this.shortURLLengthHttps + ", charactersReservedPerMedia=" + this.charactersReservedPerMedia + ", photoSizes=" + this.photoSizes + ", nonUsernamePaths=" + (this.nonUsernamePaths == null ? null : Arrays.asList(this.nonUsernamePaths)) + ", maxMediaPerUpload=" + this.maxMediaPerUpload + '}';
    }
}
