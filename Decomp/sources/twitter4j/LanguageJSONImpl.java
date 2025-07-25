package twitter4j;

import twitter4j.api.HelpResources;
import twitter4j.conf.Configuration;

public class LanguageJSONImpl implements HelpResources.Language {
    private String code;
    private String name;
    private String status;

    LanguageJSONImpl(JSONObject jSONObject) throws TwitterException {
        init(jSONObject);
    }

    static ResponseList<HelpResources.Language> createLanguageList(HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        return createLanguageList(httpResponse.asJSONArray(), httpResponse, configuration);
    }

    static ResponseList<HelpResources.Language> createLanguageList(JSONArray jSONArray, HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        if (configuration.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
        }
        try {
            int length = jSONArray.length();
            ResponseListImpl responseListImpl = new ResponseListImpl(length, httpResponse);
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                LanguageJSONImpl languageJSONImpl = new LanguageJSONImpl(jSONObject);
                responseListImpl.add(languageJSONImpl);
                if (configuration.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(languageJSONImpl, jSONObject);
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

    private void init(JSONObject jSONObject) throws TwitterException {
        try {
            this.name = jSONObject.getString("name");
            this.code = jSONObject.getString("code");
            this.status = jSONObject.getString("status");
        } catch (JSONException e) {
            throw new TwitterException(e.getMessage() + ":" + jSONObject.toString(), (Throwable) e);
        }
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getStatus() {
        return this.status;
    }
}
