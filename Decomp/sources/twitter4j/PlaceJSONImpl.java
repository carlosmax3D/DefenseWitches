package twitter4j;

import com.facebook.share.internal.ShareConstants;
import com.tapjoy.TapjoyConstants;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import twitter4j.conf.Configuration;

final class PlaceJSONImpl extends TwitterResponseImpl implements Place, Serializable {
    private static final long serialVersionUID = -6368276880878829754L;
    private GeoLocation[][] boundingBoxCoordinates;
    private String boundingBoxType;
    private Place[] containedWithIn;
    private String country;
    private String countryCode;
    private String fullName;
    private GeoLocation[][] geometryCoordinates;
    private String geometryType;
    private String id;
    private String name;
    private String placeType;
    private String streetAddress;
    private String url;

    PlaceJSONImpl() {
    }

    PlaceJSONImpl(HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        super(httpResponse);
        JSONObject asJSONObject = httpResponse.asJSONObject();
        init(asJSONObject);
        if (configuration.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
            TwitterObjectFactory.registerJSONObject(this, asJSONObject);
        }
    }

    PlaceJSONImpl(JSONObject jSONObject) throws TwitterException {
        init(jSONObject);
    }

    static ResponseList<Place> createPlaceList(HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        JSONObject jSONObject = null;
        try {
            jSONObject = httpResponse.asJSONObject();
            return createPlaceList(jSONObject.getJSONObject("result").getJSONArray("places"), httpResponse, configuration);
        } catch (JSONException e) {
            throw new TwitterException(e.getMessage() + ":" + jSONObject.toString(), (Throwable) e);
        }
    }

    static ResponseList<Place> createPlaceList(JSONArray jSONArray, HttpResponse httpResponse, Configuration configuration) throws TwitterException {
        if (configuration.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
        }
        try {
            int length = jSONArray.length();
            ResponseListImpl responseListImpl = new ResponseListImpl(length, httpResponse);
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                PlaceJSONImpl placeJSONImpl = new PlaceJSONImpl(jSONObject);
                responseListImpl.add(placeJSONImpl);
                if (configuration.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(placeJSONImpl, jSONObject);
                }
            }
            if (configuration.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(responseListImpl, jSONArray);
            }
            return responseListImpl;
        } catch (JSONException e) {
            throw new TwitterException((Exception) e);
        }
    }

    private void init(JSONObject jSONObject) throws TwitterException {
        try {
            this.name = ParseUtil.getUnescapedString("name", jSONObject);
            this.streetAddress = ParseUtil.getUnescapedString("street_address", jSONObject);
            this.countryCode = ParseUtil.getRawString(TapjoyConstants.TJC_DEVICE_COUNTRY_CODE, jSONObject);
            this.id = ParseUtil.getRawString(ShareConstants.WEB_DIALOG_PARAM_ID, jSONObject);
            this.country = ParseUtil.getRawString("country", jSONObject);
            if (!jSONObject.isNull("place_type")) {
                this.placeType = ParseUtil.getRawString("place_type", jSONObject);
            } else {
                this.placeType = ParseUtil.getRawString("type", jSONObject);
            }
            this.url = ParseUtil.getRawString("url", jSONObject);
            this.fullName = ParseUtil.getRawString("full_name", jSONObject);
            if (!jSONObject.isNull("bounding_box")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("bounding_box");
                this.boundingBoxType = ParseUtil.getRawString("type", jSONObject2);
                this.boundingBoxCoordinates = JSONImplFactory.coordinatesAsGeoLocationArray(jSONObject2.getJSONArray("coordinates"));
            } else {
                this.boundingBoxType = null;
                this.boundingBoxCoordinates = null;
            }
            if (!jSONObject.isNull("geometry")) {
                JSONObject jSONObject3 = jSONObject.getJSONObject("geometry");
                this.geometryType = ParseUtil.getRawString("type", jSONObject3);
                JSONArray jSONArray = jSONObject3.getJSONArray("coordinates");
                if (this.geometryType.equals("Point")) {
                    this.geometryCoordinates = (GeoLocation[][]) Array.newInstance(GeoLocation.class, new int[]{1, 1});
                    this.geometryCoordinates[0][0] = new GeoLocation(jSONArray.getDouble(1), jSONArray.getDouble(0));
                } else if (this.geometryType.equals("Polygon")) {
                    this.geometryCoordinates = JSONImplFactory.coordinatesAsGeoLocationArray(jSONArray);
                } else {
                    this.geometryType = null;
                    this.geometryCoordinates = null;
                }
            } else {
                this.geometryType = null;
                this.geometryCoordinates = null;
            }
            if (!jSONObject.isNull("contained_within")) {
                JSONArray jSONArray2 = jSONObject.getJSONArray("contained_within");
                this.containedWithIn = new Place[jSONArray2.length()];
                for (int i = 0; i < jSONArray2.length(); i++) {
                    this.containedWithIn[i] = new PlaceJSONImpl(jSONArray2.getJSONObject(i));
                }
                return;
            }
            this.containedWithIn = null;
        } catch (JSONException e) {
            throw new TwitterException(e.getMessage() + ":" + jSONObject.toString(), (Throwable) e);
        }
    }

    public int compareTo(Place place) {
        return this.id.compareTo(place.getId());
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this != obj) {
            return (obj instanceof Place) && ((Place) obj).getId().equals(this.id);
        }
        return true;
    }

    public GeoLocation[][] getBoundingBoxCoordinates() {
        return this.boundingBoxCoordinates;
    }

    public String getBoundingBoxType() {
        return this.boundingBoxType;
    }

    public Place[] getContainedWithIn() {
        return this.containedWithIn;
    }

    public String getCountry() {
        return this.country;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public String getFullName() {
        return this.fullName;
    }

    public GeoLocation[][] getGeometryCoordinates() {
        return this.geometryCoordinates;
    }

    public String getGeometryType() {
        return this.geometryType;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPlaceType() {
        return this.placeType;
    }

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public String getURL() {
        return this.url;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String toString() {
        List list = null;
        StringBuilder append = new StringBuilder().append("PlaceJSONImpl{name='").append(this.name).append('\'').append(", streetAddress='").append(this.streetAddress).append('\'').append(", countryCode='").append(this.countryCode).append('\'').append(", id='").append(this.id).append('\'').append(", country='").append(this.country).append('\'').append(", placeType='").append(this.placeType).append('\'').append(", url='").append(this.url).append('\'').append(", fullName='").append(this.fullName).append('\'').append(", boundingBoxType='").append(this.boundingBoxType).append('\'').append(", boundingBoxCoordinates=").append(this.boundingBoxCoordinates == null ? null : Arrays.asList(this.boundingBoxCoordinates)).append(", geometryType='").append(this.geometryType).append('\'').append(", geometryCoordinates=").append(this.geometryCoordinates == null ? null : Arrays.asList(this.geometryCoordinates)).append(", containedWithIn=");
        if (this.containedWithIn != null) {
            list = Arrays.asList(this.containedWithIn);
        }
        return append.append(list).append('}').toString();
    }
}
