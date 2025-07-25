package twitter4j;

import java.io.Serializable;
import java.util.Arrays;
import twitter4j.StreamController;

public final class ControlStreamInfo implements Serializable {
    private static final long serialVersionUID = 5182091913786509723L;
    private final boolean includeFollowingsActivity;
    private final boolean includeUserChanges;
    private final String replies;
    private final StreamController.User[] users;
    private final String with;

    ControlStreamInfo(StreamController streamController, JSONObject jSONObject) throws TwitterException {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("info");
            this.includeFollowingsActivity = ParseUtil.getBoolean("include_followings_activity", jSONObject2);
            this.includeUserChanges = ParseUtil.getBoolean("include_user_changes", jSONObject2);
            this.replies = ParseUtil.getRawString("replies", jSONObject2);
            this.with = ParseUtil.getRawString("with", jSONObject2);
            JSONArray jSONArray = jSONObject2.getJSONArray("users");
            this.users = new StreamController.User[jSONArray.length()];
            for (int i = 0; i < jSONArray.length(); i++) {
                this.users[i] = streamController.createUser(jSONArray.getJSONObject(i));
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
        ControlStreamInfo controlStreamInfo = (ControlStreamInfo) obj;
        if (this.includeFollowingsActivity != controlStreamInfo.includeFollowingsActivity) {
            return false;
        }
        if (this.includeUserChanges != controlStreamInfo.includeUserChanges) {
            return false;
        }
        if (this.replies == null ? controlStreamInfo.replies != null : !this.replies.equals(controlStreamInfo.replies)) {
            return false;
        }
        if (!Arrays.equals(this.users, controlStreamInfo.users)) {
            return false;
        }
        if (this.with != null) {
            if (this.with.equals(controlStreamInfo.with)) {
                return true;
            }
        } else if (controlStreamInfo.with == null) {
            return true;
        }
        return false;
    }

    public StreamController.User[] getUsers() {
        return this.users;
    }

    public int hashCode() {
        int i = 1;
        int i2 = 0;
        int hashCode = (((this.users != null ? Arrays.hashCode(this.users) : 0) * 31) + (this.includeFollowingsActivity ? 1 : 0)) * 31;
        if (!this.includeUserChanges) {
            i = 0;
        }
        int hashCode2 = (((hashCode + i) * 31) + (this.replies != null ? this.replies.hashCode() : 0)) * 31;
        if (this.with != null) {
            i2 = this.with.hashCode();
        }
        return hashCode2 + i2;
    }

    public boolean isIncludeFollowingsActivity() {
        return this.includeFollowingsActivity;
    }

    public boolean isIncludeUserChanges() {
        return this.includeUserChanges;
    }

    public String isReplies() {
        return this.replies;
    }

    public String isWith() {
        return this.with;
    }

    public String toString() {
        return "ControlStreamInfo{users=" + (this.users == null ? null : Arrays.asList(this.users)) + ", includeFollowingsActivity=" + this.includeFollowingsActivity + ", includeUserChanges=" + this.includeUserChanges + ", replies='" + this.replies + '\'' + ", with='" + this.with + '\'' + '}';
    }
}
