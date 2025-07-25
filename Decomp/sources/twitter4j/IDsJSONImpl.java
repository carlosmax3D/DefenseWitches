package twitter4j;

import java.util.Arrays;
import twitter4j.conf.Configuration;

final class IDsJSONImpl extends TwitterResponseImpl implements IDs {
    private static final long serialVersionUID = 6999637496007165672L;
    private long[] ids;
    private long nextCursor = -1;
    private long previousCursor = -1;

    IDsJSONImpl(String str) throws TwitterException {
        init(str);
    }

    IDsJSONImpl(HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        super(httpResponse);
        String asString = httpResponse.asString();
        init(asString);
        if (configuration.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, asString);
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void init(java.lang.String r9) throws twitter4j.TwitterException {
        /*
            r8 = this;
            java.lang.String r5 = "{"
            boolean r5 = r9.startsWith(r5)     // Catch:{ JSONException -> 0x004b }
            if (r5 == 0) goto L_0x0063
            twitter4j.JSONObject r2 = new twitter4j.JSONObject     // Catch:{ JSONException -> 0x004b }
            r2.<init>((java.lang.String) r9)     // Catch:{ JSONException -> 0x004b }
            java.lang.String r5 = "ids"
            twitter4j.JSONArray r1 = r2.getJSONArray(r5)     // Catch:{ JSONException -> 0x004b }
            int r5 = r1.length()     // Catch:{ JSONException -> 0x004b }
            long[] r5 = new long[r5]     // Catch:{ JSONException -> 0x004b }
            r8.ids = r5     // Catch:{ JSONException -> 0x004b }
            r0 = 0
        L_0x001c:
            int r5 = r1.length()     // Catch:{ JSONException -> 0x004b }
            if (r0 >= r5) goto L_0x0052
            long[] r5 = r8.ids     // Catch:{ NumberFormatException -> 0x0031 }
            java.lang.String r6 = r1.getString(r0)     // Catch:{ NumberFormatException -> 0x0031 }
            long r6 = java.lang.Long.parseLong(r6)     // Catch:{ NumberFormatException -> 0x0031 }
            r5[r0] = r6     // Catch:{ NumberFormatException -> 0x0031 }
            int r0 = r0 + 1
            goto L_0x001c
        L_0x0031:
            r4 = move-exception
            twitter4j.TwitterException r5 = new twitter4j.TwitterException     // Catch:{ JSONException -> 0x004b }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x004b }
            r6.<init>()     // Catch:{ JSONException -> 0x004b }
            java.lang.String r7 = "Twitter API returned malformed response: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ JSONException -> 0x004b }
            java.lang.StringBuilder r6 = r6.append(r2)     // Catch:{ JSONException -> 0x004b }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x004b }
            r5.<init>((java.lang.String) r6, (java.lang.Throwable) r4)     // Catch:{ JSONException -> 0x004b }
            throw r5     // Catch:{ JSONException -> 0x004b }
        L_0x004b:
            r3 = move-exception
            twitter4j.TwitterException r5 = new twitter4j.TwitterException
            r5.<init>((java.lang.Exception) r3)
            throw r5
        L_0x0052:
            java.lang.String r5 = "previous_cursor"
            long r6 = twitter4j.ParseUtil.getLong(r5, r2)     // Catch:{ JSONException -> 0x004b }
            r8.previousCursor = r6     // Catch:{ JSONException -> 0x004b }
            java.lang.String r5 = "next_cursor"
            long r6 = twitter4j.ParseUtil.getLong(r5, r2)     // Catch:{ JSONException -> 0x004b }
            r8.nextCursor = r6     // Catch:{ JSONException -> 0x004b }
        L_0x0062:
            return
        L_0x0063:
            twitter4j.JSONArray r1 = new twitter4j.JSONArray     // Catch:{ JSONException -> 0x004b }
            r1.<init>((java.lang.String) r9)     // Catch:{ JSONException -> 0x004b }
            int r5 = r1.length()     // Catch:{ JSONException -> 0x004b }
            long[] r5 = new long[r5]     // Catch:{ JSONException -> 0x004b }
            r8.ids = r5     // Catch:{ JSONException -> 0x004b }
            r0 = 0
        L_0x0071:
            int r5 = r1.length()     // Catch:{ JSONException -> 0x004b }
            if (r0 >= r5) goto L_0x0062
            long[] r5 = r8.ids     // Catch:{ NumberFormatException -> 0x0086 }
            java.lang.String r6 = r1.getString(r0)     // Catch:{ NumberFormatException -> 0x0086 }
            long r6 = java.lang.Long.parseLong(r6)     // Catch:{ NumberFormatException -> 0x0086 }
            r5[r0] = r6     // Catch:{ NumberFormatException -> 0x0086 }
            int r0 = r0 + 1
            goto L_0x0071
        L_0x0086:
            r4 = move-exception
            twitter4j.TwitterException r5 = new twitter4j.TwitterException     // Catch:{ JSONException -> 0x004b }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x004b }
            r6.<init>()     // Catch:{ JSONException -> 0x004b }
            java.lang.String r7 = "Twitter API returned malformed response: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ JSONException -> 0x004b }
            java.lang.StringBuilder r6 = r6.append(r1)     // Catch:{ JSONException -> 0x004b }
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x004b }
            r5.<init>((java.lang.String) r6, (java.lang.Throwable) r4)     // Catch:{ JSONException -> 0x004b }
            throw r5     // Catch:{ JSONException -> 0x004b }
        */
        throw new UnsupportedOperationException("Method not decompiled: twitter4j.IDsJSONImpl.init(java.lang.String):void");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IDs)) {
            return false;
        }
        return Arrays.equals(this.ids, ((IDs) obj).getIDs());
    }

    public long[] getIDs() {
        return this.ids;
    }

    public long getNextCursor() {
        return this.nextCursor;
    }

    public long getPreviousCursor() {
        return this.previousCursor;
    }

    public boolean hasNext() {
        return 0 != this.nextCursor;
    }

    public boolean hasPrevious() {
        return 0 != this.previousCursor;
    }

    public int hashCode() {
        if (this.ids != null) {
            return Arrays.hashCode(this.ids);
        }
        return 0;
    }

    public String toString() {
        return "IDsJSONImpl{ids=" + Arrays.toString(this.ids) + ", previousCursor=" + this.previousCursor + ", nextCursor=" + this.nextCursor + '}';
    }
}
