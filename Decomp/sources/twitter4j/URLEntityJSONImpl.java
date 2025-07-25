package twitter4j;

final class URLEntityJSONImpl extends EntityIndex implements URLEntity {
    private static final long serialVersionUID = 7333552738058031524L;
    private String displayURL;
    private String expandedURL;
    private String url;

    URLEntityJSONImpl() {
    }

    URLEntityJSONImpl(int i, int i2, String str, String str2, String str3) {
        setStart(i);
        setEnd(i2);
        this.url = str;
        this.expandedURL = str2;
        this.displayURL = str3;
    }

    URLEntityJSONImpl(JSONObject jSONObject) throws TwitterException {
        init(jSONObject);
    }

    private void init(JSONObject jSONObject) throws TwitterException {
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("indices");
            setStart(jSONArray.getInt(0));
            setEnd(jSONArray.getInt(1));
            this.url = jSONObject.getString("url");
            if (!jSONObject.isNull("expanded_url")) {
                this.expandedURL = jSONObject.getString("expanded_url");
            } else {
                this.expandedURL = this.url;
            }
            if (!jSONObject.isNull("display_url")) {
                this.displayURL = jSONObject.getString("display_url");
            } else {
                this.displayURL = this.url;
            }
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
        URLEntityJSONImpl uRLEntityJSONImpl = (URLEntityJSONImpl) obj;
        if (this.displayURL == null ? uRLEntityJSONImpl.displayURL != null : !this.displayURL.equals(uRLEntityJSONImpl.displayURL)) {
            return false;
        }
        if (this.expandedURL == null ? uRLEntityJSONImpl.expandedURL != null : !this.expandedURL.equals(uRLEntityJSONImpl.expandedURL)) {
            return false;
        }
        if (this.url != null) {
            if (this.url.equals(uRLEntityJSONImpl.url)) {
                return true;
            }
        } else if (uRLEntityJSONImpl.url == null) {
            return true;
        }
        return false;
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

    public int getStart() {
        return super.getStart();
    }

    public String getText() {
        return this.url;
    }

    public String getURL() {
        return this.url;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (((this.url != null ? this.url.hashCode() : 0) * 31) + (this.expandedURL != null ? this.expandedURL.hashCode() : 0)) * 31;
        if (this.displayURL != null) {
            i = this.displayURL.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "URLEntityJSONImpl{url='" + this.url + '\'' + ", expandedURL='" + this.expandedURL + '\'' + ", displayURL='" + this.displayURL + '\'' + '}';
    }
}
