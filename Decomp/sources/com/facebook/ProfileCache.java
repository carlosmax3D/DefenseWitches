package com.facebook;

import android.content.SharedPreferences;
import com.facebook.internal.Validate;
import com.google.android.gms.common.Scopes;
import org.json.JSONException;
import org.json.JSONObject;

final class ProfileCache {
    static final String CACHED_PROFILE_KEY = "com.facebook.ProfileManager.CachedProfile";
    static final String SHARED_PREFERENCES_NAME = "com.facebook.AccessTokenManager.SharedPreferences";
    private final SharedPreferences sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, 0);

    ProfileCache() {
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.sharedPreferences.edit().remove(CACHED_PROFILE_KEY).apply();
    }

    /* access modifiers changed from: package-private */
    public Profile load() {
        String string = this.sharedPreferences.getString(CACHED_PROFILE_KEY, (String) null);
        if (string != null) {
            try {
                return new Profile(new JSONObject(string));
            } catch (JSONException e) {
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void save(Profile profile) {
        Validate.notNull(profile, Scopes.PROFILE);
        JSONObject jSONObject = profile.toJSONObject();
        if (jSONObject != null) {
            this.sharedPreferences.edit().putString(CACHED_PROFILE_KEY, jSONObject.toString()).apply();
        }
    }
}
