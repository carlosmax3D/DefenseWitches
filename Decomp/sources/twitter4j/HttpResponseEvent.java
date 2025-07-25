package twitter4j;

public final class HttpResponseEvent {
    private final HttpRequest request;
    private final HttpResponse response;
    private final TwitterException twitterException;

    HttpResponseEvent(HttpRequest httpRequest, HttpResponse httpResponse, TwitterException twitterException2) {
        this.request = httpRequest;
        this.response = httpResponse;
        this.twitterException = twitterException2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HttpResponseEvent httpResponseEvent = (HttpResponseEvent) obj;
        if (this.request == null ? httpResponseEvent.request != null : !this.request.equals(httpResponseEvent.request)) {
            return false;
        }
        if (this.response != null) {
            if (this.response.equals(httpResponseEvent.response)) {
                return true;
            }
        } else if (httpResponseEvent.response == null) {
            return true;
        }
        return false;
    }

    public HttpRequest getRequest() {
        return this.request;
    }

    public HttpResponse getResponse() {
        return this.response;
    }

    public TwitterException getTwitterException() {
        return this.twitterException;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (this.request != null ? this.request.hashCode() : 0) * 31;
        if (this.response != null) {
            i = this.response.hashCode();
        }
        return hashCode + i;
    }

    public boolean isAuthenticated() {
        return this.request.getAuthorization().isEnabled();
    }

    public String toString() {
        return "HttpResponseEvent{request=" + this.request + ", response=" + this.response + '}';
    }
}
