package twitter4j.api;

import twitter4j.GeoLocation;
import twitter4j.GeoQuery;
import twitter4j.Place;
import twitter4j.ResponseList;
import twitter4j.TwitterException;

public interface PlacesGeoResources {
    Place getGeoDetails(String str) throws TwitterException;

    ResponseList<Place> getSimilarPlaces(GeoLocation geoLocation, String str, String str2, String str3) throws TwitterException;

    ResponseList<Place> reverseGeoCode(GeoQuery geoQuery) throws TwitterException;

    ResponseList<Place> searchPlaces(GeoQuery geoQuery) throws TwitterException;
}
