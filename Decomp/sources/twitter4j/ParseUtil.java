package twitter4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.LinkedBlockingQueue;
import jp.stargarage.g2metrics.BuildConfig;

final class ParseUtil {
    private static final Map<String, LinkedBlockingQueue<SimpleDateFormat>> formatMapQueue = new HashMap();

    private ParseUtil() {
        throw new AssertionError();
    }

    public static boolean getBoolean(String str, JSONObject jSONObject) {
        String rawString = getRawString(str, jSONObject);
        if (rawString == null || "null".equals(rawString)) {
            return false;
        }
        return Boolean.valueOf(rawString).booleanValue();
    }

    public static Date getDate(String str, String str2) throws TwitterException {
        LinkedBlockingQueue linkedBlockingQueue = formatMapQueue.get(str2);
        if (linkedBlockingQueue == null) {
            linkedBlockingQueue = new LinkedBlockingQueue();
            formatMapQueue.put(str2, linkedBlockingQueue);
        }
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) linkedBlockingQueue.poll();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(str2, Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        try {
            Date parse = simpleDateFormat.parse(str);
            try {
                linkedBlockingQueue.put(simpleDateFormat);
            } catch (InterruptedException e) {
            }
            return parse;
        } catch (ParseException e2) {
            throw new TwitterException("Unexpected date format(" + str + ") returned from twitter.com", (Throwable) e2);
        } catch (Throwable th) {
            try {
                linkedBlockingQueue.put(simpleDateFormat);
            } catch (InterruptedException e3) {
            }
            throw th;
        }
    }

    public static Date getDate(String str, JSONObject jSONObject) throws TwitterException {
        return getDate(str, jSONObject, "EEE MMM d HH:mm:ss z yyyy");
    }

    public static Date getDate(String str, JSONObject jSONObject, String str2) throws TwitterException {
        String unescapedString = getUnescapedString(str, jSONObject);
        if ("null".equals(unescapedString) || unescapedString == null) {
            return null;
        }
        return getDate(unescapedString, str2);
    }

    public static double getDouble(String str, JSONObject jSONObject) {
        String rawString = getRawString(str, jSONObject);
        if (rawString == null || BuildConfig.FLAVOR.equals(rawString) || "null".equals(rawString)) {
            return -1.0d;
        }
        return Double.valueOf(rawString).doubleValue();
    }

    public static int getInt(String str) {
        if (str == null || BuildConfig.FLAVOR.equals(str) || "null".equals(str)) {
            return -1;
        }
        try {
            return Integer.valueOf(str).intValue();
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static int getInt(String str, JSONObject jSONObject) {
        return getInt(getRawString(str, jSONObject));
    }

    public static long getLong(String str) {
        if (str == null || BuildConfig.FLAVOR.equals(str) || "null".equals(str)) {
            return -1;
        }
        return str.endsWith("+") ? Long.valueOf(str.substring(0, str.length() - 1)).longValue() + 1 : Long.valueOf(str).longValue();
    }

    public static long getLong(String str, JSONObject jSONObject) {
        return getLong(getRawString(str, jSONObject));
    }

    public static String getRawString(String str, JSONObject jSONObject) {
        try {
            if (jSONObject.isNull(str)) {
                return null;
            }
            return jSONObject.getString(str);
        } catch (JSONException e) {
            return null;
        }
    }

    static String getURLDecodedString(String str, JSONObject jSONObject) {
        String rawString = getRawString(str, jSONObject);
        if (rawString == null) {
            return rawString;
        }
        try {
            return URLDecoder.decode(rawString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return rawString;
        }
    }

    static String getUnescapedString(String str, JSONObject jSONObject) {
        return HTMLEntity.unescape(getRawString(str, jSONObject));
    }

    public static Date parseTrendsDate(String str) throws TwitterException {
        switch (str.length()) {
            case 10:
                return new Date(Long.parseLong(str) * 1000);
            case 20:
                return getDate(str, "yyyy-MM-dd'T'HH:mm:ss'Z'");
            default:
                return getDate(str, "EEE, d MMM yyyy HH:mm:ss z");
        }
    }

    public static int toAccessLevel(HttpResponse httpResponse) {
        if (httpResponse == null) {
            return -1;
        }
        String responseHeader = httpResponse.getResponseHeader("X-Access-Level");
        if (responseHeader == null) {
            return 0;
        }
        switch (responseHeader.length()) {
            case 4:
                return 1;
            case 10:
                return 2;
            case 25:
                return 3;
            case 26:
                return 3;
            default:
                return 0;
        }
    }
}
