package com.ansca.corona.maps;

import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.ansca.corona.JavaToNativeShim;

public class MapAddressReceivedTask implements CoronaRuntimeTask {
    private String fCity;
    private String fCityDetails;
    private String fCountry;
    private String fCountryCode;
    private String fPostalCode;
    private String fRegion;
    private String fRegionDetails;
    private String fStreet;
    private String fStreetDetails;

    public MapAddressReceivedTask(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        this.fStreet = str;
        this.fStreetDetails = str2;
        this.fCity = str3;
        this.fCityDetails = str4;
        this.fRegion = str5;
        this.fRegionDetails = str6;
        this.fPostalCode = str7;
        this.fCountry = str8;
        this.fCountryCode = str9;
    }

    public void executeUsing(CoronaRuntime coronaRuntime) {
        JavaToNativeShim.mapAddressReceivedEvent(coronaRuntime, this.fStreet, this.fStreetDetails, this.fCity, this.fCityDetails, this.fRegion, this.fRegionDetails, this.fPostalCode, this.fCountry, this.fCountryCode);
    }
}
