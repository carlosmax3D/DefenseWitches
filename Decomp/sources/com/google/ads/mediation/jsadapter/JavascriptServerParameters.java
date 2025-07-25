package com.google.ads.mediation.jsadapter;

import com.google.ads.mediation.MediationServerParameters;

public class JavascriptServerParameters extends MediationServerParameters {
    @MediationServerParameters.Parameter(name = "adxtym_height", required = false)
    public Integer height;
    @MediationServerParameters.Parameter(name = "adxtym_html", required = true)
    public String htmlScript;
    @MediationServerParameters.Parameter(name = "adxtym_passback_url", required = false)
    public String passBackUrl;
    @MediationServerParameters.Parameter(name = "adxtym_width", required = false)
    public Integer width;
}
