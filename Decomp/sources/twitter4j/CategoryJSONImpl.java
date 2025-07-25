package twitter4j;

import com.tapjoy.TapjoyConstants;
import java.io.Serializable;
import twitter4j.conf.Configuration;

final class CategoryJSONImpl implements Category, Serializable {
    private static final long serialVersionUID = 3811335888122469876L;
    private String name;
    private int size;
    private String slug;

    CategoryJSONImpl(JSONObject jSONObject) throws JSONException {
        init(jSONObject);
    }

    static ResponseList<Category> createCategoriesList(HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        return createCategoriesList(httpResponse.asJSONArray(), httpResponse, configuration);
    }

    static ResponseList<Category> createCategoriesList(JSONArray jSONArray, HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        try {
            if (configuration.isJSONStoreEnabled()) {
                TwitterObjectFactory.clearThreadLocalMap();
            }
            ResponseListImpl responseListImpl = new ResponseListImpl(jSONArray.length(), httpResponse);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                CategoryJSONImpl categoryJSONImpl = new CategoryJSONImpl(jSONObject);
                responseListImpl.add(categoryJSONImpl);
                if (configuration.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(categoryJSONImpl, jSONObject);
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CategoryJSONImpl categoryJSONImpl = (CategoryJSONImpl) obj;
        if (this.size != categoryJSONImpl.size) {
            return false;
        }
        if (this.name == null ? categoryJSONImpl.name != null : !this.name.equals(categoryJSONImpl.name)) {
            return false;
        }
        if (this.slug != null) {
            if (this.slug.equals(categoryJSONImpl.slug)) {
                return true;
            }
        } else if (categoryJSONImpl.slug == null) {
            return true;
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public String getSlug() {
        return this.slug;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (this.name != null ? this.name.hashCode() : 0) * 31;
        if (this.slug != null) {
            i = this.slug.hashCode();
        }
        return ((hashCode + i) * 31) + this.size;
    }

    /* access modifiers changed from: package-private */
    public void init(JSONObject jSONObject) throws JSONException {
        this.name = jSONObject.getString("name");
        this.slug = jSONObject.getString("slug");
        this.size = ParseUtil.getInt(TapjoyConstants.TJC_DISPLAY_AD_SIZE, jSONObject);
    }

    public String toString() {
        return "CategoryJSONImpl{name='" + this.name + '\'' + ", slug='" + this.slug + '\'' + ", size=" + this.size + '}';
    }
}
