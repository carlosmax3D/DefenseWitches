package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.ads.AdSize;
import com.google.android.gms.common.images.WebImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class dp {
    private static final dk lA = new dk("MetadataUtils");
    private static final String[] mx = {"Z", "+hh", "+hhmm", "+hh:mm"};
    private static final String my = ("yyyyMMdd'T'HHmmss" + mx[0]);

    public static Calendar G(String str) {
        if (TextUtils.isEmpty(str)) {
            lA.b("Input string is empty or null", new Object[0]);
            return null;
        }
        String H = H(str);
        if (TextUtils.isEmpty(H)) {
            lA.b("Invalid date format", new Object[0]);
            return null;
        }
        String I = I(str);
        String str2 = "yyyyMMdd";
        if (!TextUtils.isEmpty(I)) {
            H = H + "T" + I;
            str2 = I.length() == "HHmmss".length() ? "yyyyMMdd'T'HHmmss" : my;
        }
        Calendar instance = GregorianCalendar.getInstance();
        try {
            instance.setTime(new SimpleDateFormat(str2).parse(H));
            return instance;
        } catch (ParseException e) {
            lA.b("Error parsing string: %s", e.getMessage());
            return null;
        }
    }

    private static String H(String str) {
        if (TextUtils.isEmpty(str)) {
            lA.b("Input string is empty or null", new Object[0]);
            return null;
        }
        try {
            return str.substring(0, "yyyyMMdd".length());
        } catch (IndexOutOfBoundsException e) {
            lA.c("Error extracting the date: %s", e.getMessage());
            return null;
        }
    }

    private static String I(String str) {
        if (TextUtils.isEmpty(str)) {
            lA.b("string is empty or null", new Object[0]);
            return null;
        }
        int indexOf = str.indexOf(84);
        int i = indexOf + 1;
        if (indexOf != "yyyyMMdd".length()) {
            lA.b("T delimeter is not found", new Object[0]);
            return null;
        }
        try {
            String substring = str.substring(i);
            if (substring.length() == "HHmmss".length()) {
                return substring;
            }
            switch (substring.charAt("HHmmss".length())) {
                case '+':
                case '-':
                    if (J(substring)) {
                        return substring.replaceAll("([\\+\\-]\\d\\d):(\\d\\d)", "$1$2");
                    }
                    return null;
                case AdSize.LARGE_AD_HEIGHT:
                    if (substring.length() == "HHmmss".length() + mx[0].length()) {
                        return substring.substring(0, substring.length() - 1) + "+0000";
                    }
                    return null;
                default:
                    return null;
            }
        } catch (IndexOutOfBoundsException e) {
            lA.b("Error extracting the time substring: %s", e.getMessage());
            return null;
        }
    }

    private static boolean J(String str) {
        int length = str.length();
        int length2 = "HHmmss".length();
        return length == mx[1].length() + length2 || length == mx[2].length() + length2 || length == length2 + mx[3].length();
    }

    public static String a(Calendar calendar) {
        if (calendar == null) {
            lA.b("Calendar object cannot be null", new Object[0]);
            return null;
        }
        String str = my;
        if (calendar.get(11) == 0 && calendar.get(12) == 0 && calendar.get(13) == 0) {
            str = "yyyyMMdd";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        simpleDateFormat.setTimeZone(calendar.getTimeZone());
        String format = simpleDateFormat.format(calendar.getTime());
        return format.endsWith("+0000") ? format.replace("+0000", mx[0]) : format;
    }

    public static void a(List<WebImage> list, JSONObject jSONObject) {
        try {
            list.clear();
            JSONArray jSONArray = jSONObject.getJSONArray("images");
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                try {
                    list.add(new WebImage(jSONArray.getJSONObject(i)));
                } catch (IllegalArgumentException e) {
                }
            }
        } catch (JSONException e2) {
        }
    }

    public static void a(JSONObject jSONObject, List<WebImage> list) {
        if (list != null && !list.isEmpty()) {
            JSONArray jSONArray = new JSONArray();
            for (WebImage aP : list) {
                jSONArray.put(aP.aP());
            }
            try {
                jSONObject.put("images", jSONArray);
            } catch (JSONException e) {
            }
        }
    }
}
