package com.google.ads.mediation.admob;

import com.google.ads.mediation.MediationServerParameters;

public final class AdMobServerParameters extends MediationServerParameters {
    public String adJson;
    @MediationServerParameters.Parameter(name = "pubid")
    public String adUnitId;
    @MediationServerParameters.Parameter(name = "mad_hac", required = false)
    public String allowHouseAds = null;
    public int tagForChildDirectedTreatment = -1;
}
