package twitter4j;

import com.facebook.share.internal.ShareConstants;
import java.io.IOException;
import java.io.InputStream;
import twitter4j.conf.Configuration;

class StatusStreamImpl extends StatusStreamBase {
    static final RawStreamListener[] EMPTY = new RawStreamListener[0];
    String line;

    StatusStreamImpl(Dispatcher dispatcher, InputStream inputStream, Configuration configuration) throws IOException {
        super(dispatcher, inputStream, configuration);
    }

    StatusStreamImpl(Dispatcher dispatcher, HttpResponse httpResponse, Configuration configuration) throws IOException {
        super(dispatcher, httpResponse, configuration);
    }

    public void next(StatusListener statusListener) throws TwitterException {
        handleNextElement(new StatusListener[]{statusListener}, EMPTY);
    }

    public void next(StreamListener[] streamListenerArr, RawStreamListener[] rawStreamListenerArr) throws TwitterException {
        handleNextElement(streamListenerArr, rawStreamListenerArr);
    }

    /* access modifiers changed from: protected */
    public void onClose() {
    }

    /* access modifiers changed from: protected */
    public void onDelete(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        for (UserStreamListener userStreamListener : streamListenerArr) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("delete");
            if (jSONObject2.has("status")) {
                userStreamListener.onDeletionNotice(new StatusDeletionNoticeImpl(jSONObject2.getJSONObject("status")));
            } else {
                JSONObject jSONObject3 = jSONObject2.getJSONObject("direct_message");
                userStreamListener.onDeletionNotice(ParseUtil.getLong(ShareConstants.WEB_DIALOG_PARAM_ID, jSONObject3), ParseUtil.getLong("user_id", jSONObject3));
            }
        }
    }

    public void onException(Exception exc, StreamListener[] streamListenerArr) {
        for (StreamListener onException : streamListenerArr) {
            onException.onException(exc);
        }
    }

    /* access modifiers changed from: protected */
    public void onLimit(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        for (StatusListener onTrackLimitationNotice : streamListenerArr) {
            onTrackLimitationNotice.onTrackLimitationNotice(ParseUtil.getInt("track", jSONObject.getJSONObject("limit")));
        }
    }

    /* access modifiers changed from: protected */
    public void onMessage(String str, RawStreamListener[] rawStreamListenerArr) throws TwitterException {
        for (RawStreamListener onMessage : rawStreamListenerArr) {
            onMessage.onMessage(str);
        }
    }

    /* access modifiers changed from: protected */
    public void onScrubGeo(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        JSONObject jSONObject2 = jSONObject.getJSONObject("scrub_geo");
        for (StatusListener onScrubGeo : streamListenerArr) {
            onScrubGeo.onScrubGeo(ParseUtil.getLong("user_id", jSONObject2), ParseUtil.getLong("up_to_status_id", jSONObject2));
        }
    }

    /* access modifiers changed from: protected */
    public void onStallWarning(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException, JSONException {
        for (StatusListener onStallWarning : streamListenerArr) {
            onStallWarning.onStallWarning(new StallWarning(jSONObject));
        }
    }

    /* access modifiers changed from: protected */
    public void onStatus(JSONObject jSONObject, StreamListener[] streamListenerArr) throws TwitterException {
        for (StatusListener onStatus : streamListenerArr) {
            onStatus.onStatus(asStatus(jSONObject));
        }
    }

    /* access modifiers changed from: protected */
    public String parseLine(String str) {
        this.line = str;
        return str;
    }
}
