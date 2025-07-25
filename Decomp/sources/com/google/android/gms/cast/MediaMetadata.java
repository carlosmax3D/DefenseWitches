package com.google.android.gms.cast;

import android.os.Bundle;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.internal.dp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jp.co.voyagegroup.android.fluct.jar.util.FluctConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class MediaMetadata {
    public static final String KEY_ALBUM_ARTIST = "com.google.android.gms.cast.metadata.ALBUM_ARTIST";
    public static final String KEY_ALBUM_TITLE = "com.google.android.gms.cast.metadata.ALBUM_TITLE";
    public static final String KEY_ARTIST = "com.google.android.gms.cast.metadata.ARTIST";
    public static final String KEY_BROADCAST_DATE = "com.google.android.gms.cast.metadata.BROADCAST_DATE";
    public static final String KEY_COMPOSER = "com.google.android.gms.cast.metadata.COMPOSER";
    public static final String KEY_CREATION_DATE = "com.google.android.gms.cast.metadata.CREATION_DATE";
    public static final String KEY_DISC_NUMBER = "com.google.android.gms.cast.metadata.DISC_NUMBER";
    public static final String KEY_EPISODE_NUMBER = "com.google.android.gms.cast.metadata.EPISODE_NUMBER";
    public static final String KEY_HEIGHT = "com.google.android.gms.cast.metadata.HEIGHT";
    public static final String KEY_LOCATION_LATITUDE = "com.google.android.gms.cast.metadata.LOCATION_LATITUDE";
    public static final String KEY_LOCATION_LONGITUDE = "com.google.android.gms.cast.metadata.LOCATION_LONGITUDE";
    public static final String KEY_LOCATION_NAME = "com.google.android.gms.cast.metadata.LOCATION_NAME";
    public static final String KEY_RELEASE_DATE = "com.google.android.gms.cast.metadata.RELEASE_DATE";
    public static final String KEY_SEASON_NUMBER = "com.google.android.gms.cast.metadata.SEASON_NUMBER";
    public static final String KEY_SERIES_TITLE = "com.google.android.gms.cast.metadata.SERIES_TITLE";
    public static final String KEY_STUDIO = "com.google.android.gms.cast.metadata.STUDIO";
    public static final String KEY_SUBTITLE = "com.google.android.gms.cast.metadata.SUBTITLE";
    public static final String KEY_TITLE = "com.google.android.gms.cast.metadata.TITLE";
    public static final String KEY_TRACK_NUMBER = "com.google.android.gms.cast.metadata.TRACK_NUMBER";
    public static final String KEY_WIDTH = "com.google.android.gms.cast.metadata.WIDTH";
    public static final int MEDIA_TYPE_GENERIC = 0;
    public static final int MEDIA_TYPE_MOVIE = 1;
    public static final int MEDIA_TYPE_MUSIC_TRACK = 3;
    public static final int MEDIA_TYPE_PHOTO = 4;
    public static final int MEDIA_TYPE_TV_SHOW = 2;
    public static final int MEDIA_TYPE_USER = 100;
    private static final String[] kO = {null, "String", "int", "double", "ISO-8601 date String"};
    private static final a kP = new a().a(KEY_CREATION_DATE, "creationDateTime", 4).a(KEY_RELEASE_DATE, "releaseDate", 4).a(KEY_BROADCAST_DATE, "originalAirdate", 4).a(KEY_TITLE, "title", 1).a(KEY_SUBTITLE, "subtitle", 1).a(KEY_ARTIST, "artist", 1).a(KEY_ALBUM_ARTIST, "albumArtist", 1).a(KEY_ALBUM_TITLE, "albumName", 1).a(KEY_COMPOSER, "composer", 1).a(KEY_DISC_NUMBER, "discNumber", 2).a(KEY_TRACK_NUMBER, "trackNumber", 2).a(KEY_SEASON_NUMBER, "season", 2).a(KEY_EPISODE_NUMBER, "episode", 2).a(KEY_SERIES_TITLE, "seriesTitle", 1).a(KEY_STUDIO, "studio", 1).a(KEY_WIDTH, FluctConstants.XML_NODE_WIDTH, 2).a(KEY_HEIGHT, FluctConstants.XML_NODE_HEIGHT, 2).a(KEY_LOCATION_NAME, "location", 1).a(KEY_LOCATION_LATITUDE, "latitude", 3).a(KEY_LOCATION_LONGITUDE, "longitude", 3);
    private final Bundle kQ;
    private int kR;
    private final List<WebImage> ki;

    private static class a {
        private final Map<String, String> kS = new HashMap();
        private final Map<String, String> kT = new HashMap();
        private final Map<String, Integer> kU = new HashMap();

        public int A(String str) {
            Integer num = this.kU.get(str);
            if (num != null) {
                return num.intValue();
            }
            return 0;
        }

        public a a(String str, String str2, int i) {
            this.kS.put(str, str2);
            this.kT.put(str2, str);
            this.kU.put(str, Integer.valueOf(i));
            return this;
        }

        public String y(String str) {
            return this.kS.get(str);
        }

        public String z(String str) {
            return this.kT.get(str);
        }
    }

    public MediaMetadata() {
        this(0);
    }

    public MediaMetadata(int i) {
        this.ki = new ArrayList();
        this.kQ = new Bundle();
        this.kR = i;
    }

    private void a(String str, int i) throws IllegalArgumentException {
        int A = kP.A(str);
        if (A != i && A != 0) {
            throw new IllegalArgumentException("Value for " + str + " must be a " + kO[i]);
        }
    }

    private void a(JSONObject jSONObject, String... strArr) {
        try {
            for (String str : strArr) {
                if (this.kQ.containsKey(str)) {
                    switch (kP.A(str)) {
                        case 1:
                        case 4:
                            jSONObject.put(kP.y(str), this.kQ.getString(str));
                            break;
                        case 2:
                            jSONObject.put(kP.y(str), this.kQ.getInt(str));
                            break;
                        case 3:
                            jSONObject.put(kP.y(str), this.kQ.getDouble(str));
                            break;
                    }
                }
            }
            for (String str2 : this.kQ.keySet()) {
                if (!str2.startsWith("com.google.")) {
                    Object obj = this.kQ.get(str2);
                    if (obj instanceof String) {
                        jSONObject.put(str2, obj);
                    } else if (obj instanceof Integer) {
                        jSONObject.put(str2, obj);
                    } else if (obj instanceof Double) {
                        jSONObject.put(str2, obj);
                    }
                }
            }
        } catch (JSONException e) {
        }
    }

    private boolean a(Bundle bundle, Bundle bundle2) {
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            Object obj2 = bundle2.get(str);
            if ((obj instanceof Bundle) && (obj2 instanceof Bundle) && !a((Bundle) obj, (Bundle) obj2)) {
                return false;
            }
            if (obj == null) {
                if (obj2 != null || !bundle2.containsKey(str)) {
                    return false;
                }
            } else if (!obj.equals(obj2)) {
                return false;
            }
        }
        return true;
    }

    private void b(JSONObject jSONObject, String... strArr) {
        HashSet hashSet = new HashSet(Arrays.asList(strArr));
        try {
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                String z = kP.z(next);
                if (z == null) {
                    Object obj = jSONObject.get(next);
                    if (obj instanceof String) {
                        this.kQ.putString(z, (String) obj);
                    } else if (obj instanceof Integer) {
                        this.kQ.putInt(z, ((Integer) obj).intValue());
                    } else if (obj instanceof Double) {
                        this.kQ.putDouble(z, ((Double) obj).doubleValue());
                    }
                } else if (hashSet.contains(z)) {
                    try {
                        Object obj2 = jSONObject.get(next);
                        if (obj2 != null) {
                            switch (kP.A(z)) {
                                case 1:
                                    if (!(obj2 instanceof String)) {
                                        break;
                                    } else {
                                        this.kQ.putString(z, (String) obj2);
                                        break;
                                    }
                                case 2:
                                    if (!(obj2 instanceof Integer)) {
                                        break;
                                    } else {
                                        this.kQ.putInt(z, ((Integer) obj2).intValue());
                                        break;
                                    }
                                case 3:
                                    if (!(obj2 instanceof Double)) {
                                        break;
                                    } else {
                                        this.kQ.putDouble(z, ((Double) obj2).doubleValue());
                                        break;
                                    }
                                case 4:
                                    if ((obj2 instanceof String) && dp.G((String) obj2) != null) {
                                        this.kQ.putString(z, (String) obj2);
                                        break;
                                    }
                            }
                        }
                    } catch (JSONException e) {
                    }
                }
            }
        } catch (JSONException e2) {
        }
    }

    public JSONObject aP() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("metadataType", this.kR);
        } catch (JSONException e) {
        }
        dp.a(jSONObject, this.ki);
        switch (this.kR) {
            case 0:
                a(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_SUBTITLE, KEY_RELEASE_DATE);
                break;
            case 1:
                a(jSONObject, KEY_TITLE, KEY_STUDIO, KEY_SUBTITLE, KEY_RELEASE_DATE);
                break;
            case 2:
                a(jSONObject, KEY_TITLE, KEY_SERIES_TITLE, KEY_SEASON_NUMBER, KEY_EPISODE_NUMBER, KEY_BROADCAST_DATE);
                break;
            case 3:
                a(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_ALBUM_TITLE, KEY_ALBUM_ARTIST, KEY_COMPOSER, KEY_TRACK_NUMBER, KEY_DISC_NUMBER, KEY_RELEASE_DATE);
                break;
            case 4:
                a(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_LOCATION_NAME, KEY_LOCATION_LATITUDE, KEY_LOCATION_LONGITUDE, KEY_WIDTH, KEY_HEIGHT, KEY_CREATION_DATE);
                break;
        }
        a(jSONObject, new String[0]);
        return jSONObject;
    }

    public void addImage(WebImage webImage) {
        this.ki.add(webImage);
    }

    public void b(JSONObject jSONObject) {
        clear();
        this.kR = 0;
        try {
            this.kR = jSONObject.getInt("metadataType");
        } catch (JSONException e) {
        }
        dp.a(this.ki, jSONObject);
        switch (this.kR) {
            case 0:
                b(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_SUBTITLE, KEY_RELEASE_DATE);
                return;
            case 1:
                b(jSONObject, KEY_TITLE, KEY_STUDIO, KEY_SUBTITLE, KEY_RELEASE_DATE);
                return;
            case 2:
                b(jSONObject, KEY_TITLE, KEY_SERIES_TITLE, KEY_SEASON_NUMBER, KEY_EPISODE_NUMBER, KEY_BROADCAST_DATE);
                return;
            case 3:
                b(jSONObject, KEY_TITLE, KEY_ALBUM_TITLE, KEY_ARTIST, KEY_ALBUM_ARTIST, KEY_COMPOSER, KEY_TRACK_NUMBER, KEY_DISC_NUMBER, KEY_RELEASE_DATE);
                return;
            case 4:
                b(jSONObject, KEY_TITLE, KEY_ARTIST, KEY_LOCATION_NAME, KEY_LOCATION_LATITUDE, KEY_LOCATION_LONGITUDE, KEY_WIDTH, KEY_HEIGHT, KEY_CREATION_DATE);
                break;
        }
        b(jSONObject, new String[0]);
    }

    public void clear() {
        this.kQ.clear();
        this.ki.clear();
    }

    public void clearImages() {
        this.ki.clear();
    }

    public boolean containsKey(String str) {
        return this.kQ.containsKey(str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaMetadata)) {
            return false;
        }
        MediaMetadata mediaMetadata = (MediaMetadata) obj;
        return a(this.kQ, mediaMetadata.kQ) && this.ki.equals(mediaMetadata.ki);
    }

    public Calendar getDate(String str) {
        a(str, 4);
        String string = this.kQ.getString(str);
        if (string != null) {
            return dp.G(string);
        }
        return null;
    }

    public String getDateAsString(String str) {
        a(str, 4);
        return this.kQ.getString(str);
    }

    public double getDouble(String str) {
        a(str, 3);
        return this.kQ.getDouble(str);
    }

    public List<WebImage> getImages() {
        return this.ki;
    }

    public int getInt(String str) {
        a(str, 2);
        return this.kQ.getInt(str);
    }

    public int getMediaType() {
        return this.kR;
    }

    public String getString(String str) {
        a(str, 1);
        return this.kQ.getString(str);
    }

    public boolean hasImages() {
        return this.ki != null && !this.ki.isEmpty();
    }

    public int hashCode() {
        int i = 17;
        Iterator it = this.kQ.keySet().iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return (i2 * 31) + this.ki.hashCode();
            }
            i = this.kQ.get((String) it.next()).hashCode() + (i2 * 31);
        }
    }

    public Set<String> keySet() {
        return this.kQ.keySet();
    }

    public void putDate(String str, Calendar calendar) {
        a(str, 4);
        this.kQ.putString(str, dp.a(calendar));
    }

    public void putDouble(String str, double d) {
        a(str, 3);
        this.kQ.putDouble(str, d);
    }

    public void putInt(String str, int i) {
        a(str, 2);
        this.kQ.putInt(str, i);
    }

    public void putString(String str, String str2) {
        a(str, 1);
        this.kQ.putString(str, str2);
    }
}
