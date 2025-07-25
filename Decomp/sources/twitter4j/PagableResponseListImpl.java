package twitter4j;

class PagableResponseListImpl<T> extends ResponseListImpl implements PagableResponseList {
    private static final long serialVersionUID = -8603601553967559275L;
    private final long nextCursor;
    private final long previousCursor;

    PagableResponseListImpl(int i, JSONObject jSONObject, HttpResponse httpResponse) {
        super(i, httpResponse);
        this.previousCursor = ParseUtil.getLong("previous_cursor", jSONObject);
        this.nextCursor = ParseUtil.getLong("next_cursor", jSONObject);
    }

    PagableResponseListImpl(RateLimitStatus rateLimitStatus, int i) {
        super(rateLimitStatus, i);
        this.previousCursor = 0;
        this.nextCursor = 0;
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
}
