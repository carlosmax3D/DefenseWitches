package twitter4j;

import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import java.io.Serializable;
import java.util.Arrays;
import twitter4j.auth.Authorization;
import twitter4j.auth.AuthorizationFactory;
import twitter4j.conf.Configuration;

public class StreamController {
    private static final Logger logger = Logger.getLogger(StreamController.class);
    private final Authorization AUTH;
    private String controlURI = null;
    private final HttpClient http;
    private final Object lock = new Object();

    public final class FriendsIDs implements CursorSupport, Serializable {
        private static final long serialVersionUID = -7393320878760329794L;
        private long[] ids;
        private long nextCursor = -1;
        private long previousCursor = -1;
        private User user;

        FriendsIDs(HttpResponse httpResponse) throws TwitterException {
            init(httpResponse.asJSONObject());
        }

        private void init(JSONObject jSONObject) throws TwitterException {
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("follow");
                JSONArray jSONArray = jSONObject2.getJSONArray(NativeProtocol.AUDIENCE_FRIENDS);
                this.ids = new long[jSONArray.length()];
                for (int i = 0; i < jSONArray.length(); i++) {
                    this.ids[i] = Long.parseLong(jSONArray.getString(i));
                }
                this.user = new User(jSONObject2.getJSONObject("user"));
                this.previousCursor = ParseUtil.getLong("previous_cursor", jSONObject);
                this.nextCursor = ParseUtil.getLong("next_cursor", jSONObject);
            } catch (NumberFormatException e) {
                throw new TwitterException("Twitter API returned malformed response: " + jSONObject, (Throwable) e);
            } catch (JSONException e2) {
                throw new TwitterException((Exception) e2);
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            FriendsIDs friendsIDs = (FriendsIDs) obj;
            if (this.nextCursor != friendsIDs.nextCursor) {
                return false;
            }
            if (this.previousCursor != friendsIDs.previousCursor) {
                return false;
            }
            if (!Arrays.equals(this.ids, friendsIDs.ids)) {
                return false;
            }
            if (this.user != null) {
                if (this.user.equals(friendsIDs.user)) {
                    return true;
                }
            } else if (friendsIDs.user == null) {
                return true;
            }
            return false;
        }

        public long[] getIds() {
            return this.ids;
        }

        public long getNextCursor() {
            return this.nextCursor;
        }

        public long getPreviousCursor() {
            return this.previousCursor;
        }

        public User getUser() {
            return this.user;
        }

        public boolean hasNext() {
            return 0 != this.nextCursor;
        }

        public boolean hasPrevious() {
            return 0 != this.previousCursor;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = (((((this.ids != null ? Arrays.hashCode(this.ids) : 0) * 31) + ((int) (this.previousCursor ^ (this.previousCursor >>> 32)))) * 31) + ((int) (this.nextCursor ^ (this.nextCursor >>> 32)))) * 31;
            if (this.user != null) {
                i = this.user.hashCode();
            }
            return hashCode + i;
        }

        public String toString() {
            return "FriendsIDs{ids=" + Arrays.toString(this.ids) + ", previousCursor=" + this.previousCursor + ", nextCursor=" + this.nextCursor + ", user=" + this.user + '}';
        }
    }

    public final class User implements Serializable {
        private static final long serialVersionUID = -8741743249755418730L;
        private final boolean dm;
        private final long id;
        private final String name;

        User(JSONObject jSONObject) {
            this.id = ParseUtil.getLong(ShareConstants.WEB_DIALOG_PARAM_ID, jSONObject);
            this.name = ParseUtil.getRawString("name", jSONObject);
            this.dm = ParseUtil.getBoolean("dm", jSONObject);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            User user = (User) obj;
            if (this.dm != user.dm) {
                return false;
            }
            if (this.id != user.id) {
                return false;
            }
            if (this.name != null) {
                if (this.name.equals(user.name)) {
                    return true;
                }
            } else if (user.name == null) {
                return true;
            }
            return false;
        }

        public long getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((((int) (this.id ^ (this.id >>> 32))) * 31) + (this.name != null ? this.name.hashCode() : 0)) * 31;
            if (this.dm) {
                i = 1;
            }
            return hashCode + i;
        }

        public boolean isDMAccessible() {
            return this.dm;
        }

        public String toString() {
            return "User{id=" + this.id + ", name='" + this.name + '\'' + ", dm=" + this.dm + '}';
        }
    }

    StreamController(HttpClient httpClient, Authorization authorization) {
        this.http = httpClient;
        this.AUTH = authorization;
    }

    StreamController(Configuration configuration) {
        this.http = HttpClientFactory.getInstance(configuration.getHttpClientConfiguration());
        this.AUTH = AuthorizationFactory.getInstance(configuration);
    }

    public String addUsers(long[] jArr) throws TwitterException {
        ensureControlURISet();
        HttpParameter httpParameter = new HttpParameter("user_id", StringUtil.join(jArr));
        return this.http.post(this.controlURI + "/add_user.json", new HttpParameter[]{httpParameter}, this.AUTH, (HttpResponseListener) null).asString();
    }

    /* access modifiers changed from: package-private */
    public User createUser(JSONObject jSONObject) {
        return new User(jSONObject);
    }

    /* access modifiers changed from: package-private */
    public void ensureControlURISet() throws TwitterException {
        synchronized (this.lock) {
            int i = 0;
            do {
                try {
                    if (this.controlURI == null) {
                        this.lock.wait(1000);
                        i++;
                    }
                } catch (InterruptedException e) {
                }
            } while (i <= 29);
            throw new TwitterException("timed out for control uri to be ready");
        }
    }

    /* access modifiers changed from: package-private */
    public String getControlURI() {
        return this.controlURI;
    }

    public FriendsIDs getFriendsIDs(long j, long j2) throws TwitterException {
        ensureControlURISet();
        return new FriendsIDs(this.http.post(this.controlURI + "/friends/ids.json", new HttpParameter[]{new HttpParameter("user_id", j), new HttpParameter("cursor", j2)}, this.AUTH, (HttpResponseListener) null));
    }

    public ControlStreamInfo getInfo() throws TwitterException {
        ensureControlURISet();
        return new ControlStreamInfo(this, this.http.get(this.controlURI + "/info.json", (HttpParameter[]) null, this.AUTH, (HttpResponseListener) null).asJSONObject());
    }

    public String removeUsers(long[] jArr) throws TwitterException {
        ensureControlURISet();
        HttpParameter httpParameter = new HttpParameter("user_id", StringUtil.join(jArr));
        return this.http.post(this.controlURI + "/remove_user.json", new HttpParameter[]{httpParameter}, this.AUTH, (HttpResponseListener) null).asString();
    }

    /* access modifiers changed from: package-private */
    public void setControlURI(String str) {
        this.controlURI = str.replace("/1.1//1.1/", "/1.1/");
        synchronized (this.lock) {
            this.lock.notifyAll();
        }
    }
}
