package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.text.TextUtils;
import com.ansca.corona.purchasing.StoreName;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyConstants;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class cf {
    private static final SimpleDateFormat hJ = new SimpleDateFormat("yyyyMMdd");

    public static cb a(Context context, bz bzVar, String str) {
        cb cbVar;
        List<String> list;
        List<String> list2;
        List<String> list3;
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("ad_base_url", (String) null);
            String optString2 = jSONObject.optString("ad_url", (String) null);
            String optString3 = jSONObject.optString("ad_size", (String) null);
            String optString4 = jSONObject.optString("ad_html", (String) null);
            long j = jSONObject.has("interstitial_timeout") ? (long) (jSONObject.getDouble("interstitial_timeout") * 1000.0d) : -1;
            String optString5 = jSONObject.optString("orientation", (String) null);
            int i = -1;
            if ("portrait".equals(optString5)) {
                i = co.av();
            } else if ("landscape".equals(optString5)) {
                i = co.au();
            }
            if (!TextUtils.isEmpty(optString4)) {
                if (TextUtils.isEmpty(optString)) {
                    ct.v("Could not parse the mediation config: Missing required ad_base_url field");
                    return new cb(0);
                }
                cbVar = null;
            } else if (!TextUtils.isEmpty(optString2)) {
                cb a = ce.a(context, bzVar.ej.iJ, optString2);
                optString = a.gL;
                optString4 = a.hw;
                cbVar = a;
            } else {
                ct.v("Could not parse the mediation config: Missing required ad_html or ad_url field.");
                return new cb(0);
            }
            JSONArray optJSONArray = jSONObject.optJSONArray("click_urls");
            List<String> list4 = cbVar == null ? null : cbVar.fK;
            if (optJSONArray != null) {
                if (list4 == null) {
                    list4 = new LinkedList<>();
                }
                for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                    list4.add(optJSONArray.getString(i2));
                }
                list = list4;
            } else {
                list = list4;
            }
            JSONArray optJSONArray2 = jSONObject.optJSONArray("impression_urls");
            List<String> list5 = cbVar == null ? null : cbVar.fL;
            if (optJSONArray2 != null) {
                if (list5 == null) {
                    list5 = new LinkedList<>();
                }
                for (int i3 = 0; i3 < optJSONArray2.length(); i3++) {
                    list5.add(optJSONArray2.getString(i3));
                }
                list2 = list5;
            } else {
                list2 = list5;
            }
            JSONArray optJSONArray3 = jSONObject.optJSONArray("manual_impression_urls");
            List<String> list6 = cbVar == null ? null : cbVar.hA;
            if (optJSONArray3 != null) {
                if (list6 == null) {
                    list6 = new LinkedList<>();
                }
                for (int i4 = 0; i4 < optJSONArray3.length(); i4++) {
                    list6.add(optJSONArray3.getString(i4));
                }
                list3 = list6;
            } else {
                list3 = list6;
            }
            if (cbVar != null) {
                if (cbVar.orientation != -1) {
                    i = cbVar.orientation;
                }
                if (cbVar.hx > 0) {
                    j = cbVar.hx;
                }
            }
            return new cb(optString, optString4, list, list2, j, false, -1, list3, -1, i, optString3);
        } catch (JSONException e) {
            ct.v("Could not parse the mediation config: " + e.getMessage());
            return new cb(0);
        }
    }

    public static String a(bz bzVar, ci ciVar, Location location) {
        try {
            HashMap hashMap = new HashMap();
            if (bzVar.hq != null) {
                hashMap.put("ad_pos", bzVar.hq);
            }
            a((HashMap<String, Object>) hashMap, bzVar.hr);
            hashMap.put("format", bzVar.em.eF);
            if (bzVar.em.width == -1) {
                hashMap.put("smart_w", "full");
            }
            if (bzVar.em.height == -2) {
                hashMap.put("smart_h", "auto");
            }
            if (bzVar.em.eH != null) {
                StringBuilder sb = new StringBuilder();
                for (x xVar : bzVar.em.eH) {
                    if (sb.length() != 0) {
                        sb.append("|");
                    }
                    sb.append(xVar.width == -1 ? (int) (((float) xVar.widthPixels) / ciVar.ip) : xVar.width);
                    sb.append("x");
                    sb.append(xVar.height == -2 ? (int) (((float) xVar.heightPixels) / ciVar.ip) : xVar.height);
                }
                hashMap.put("sz", sb);
            }
            hashMap.put("slotname", bzVar.adUnitId);
            hashMap.put("pn", bzVar.applicationInfo.packageName);
            if (bzVar.hs != null) {
                hashMap.put("vc", Integer.valueOf(bzVar.hs.versionCode));
            }
            hashMap.put("ms", bzVar.ht);
            hashMap.put("seq_num", bzVar.hu);
            hashMap.put(TapjoyConstants.TJC_SESSION_ID, bzVar.hv);
            hashMap.put("js", bzVar.ej.iJ);
            a((HashMap<String, Object>) hashMap, ciVar);
            if (bzVar.hr.versionCode >= 2 && bzVar.hr.eE != null) {
                a((HashMap<String, Object>) hashMap, bzVar.hr.eE);
            }
            if (ct.n(2)) {
                ct.u("Ad Request JSON: " + co.m(hashMap).toString(2));
            }
            return co.m(hashMap).toString();
        } catch (JSONException e) {
            ct.v("Problem serializing ad request to JSON: " + e.getMessage());
            return null;
        }
    }

    private static void a(HashMap<String, Object> hashMap, Location location) {
        HashMap hashMap2 = new HashMap();
        Float valueOf = Float.valueOf(location.getAccuracy() * 1000.0f);
        Long valueOf2 = Long.valueOf(location.getTime() * 1000);
        Long valueOf3 = Long.valueOf((long) (location.getLatitude() * 1.0E7d));
        Long valueOf4 = Long.valueOf((long) (location.getLongitude() * 1.0E7d));
        hashMap2.put("radius", valueOf);
        hashMap2.put(TJAdUnitConstants.String.LAT, valueOf3);
        hashMap2.put(TJAdUnitConstants.String.LONG, valueOf4);
        hashMap2.put("time", valueOf2);
        hashMap.put("uule", hashMap2);
    }

    private static void a(HashMap<String, Object> hashMap, ai aiVar) {
        String str;
        String str2 = null;
        if (Color.alpha(aiVar.eZ) != 0) {
            hashMap.put("acolor", m(aiVar.eZ));
        }
        if (Color.alpha(aiVar.backgroundColor) != 0) {
            hashMap.put("bgcolor", m(aiVar.backgroundColor));
        }
        if (!(Color.alpha(aiVar.fa) == 0 || Color.alpha(aiVar.fb) == 0)) {
            hashMap.put("gradientto", m(aiVar.fa));
            hashMap.put("gradientfrom", m(aiVar.fb));
        }
        if (Color.alpha(aiVar.fc) != 0) {
            hashMap.put("bcolor", m(aiVar.fc));
        }
        hashMap.put("bthick", Integer.toString(aiVar.fd));
        switch (aiVar.fe) {
            case 0:
                str = StoreName.NONE;
                break;
            case 1:
                str = "dashed";
                break;
            case 2:
                str = "dotted";
                break;
            case 3:
                str = "solid";
                break;
            default:
                str = null;
                break;
        }
        if (str != null) {
            hashMap.put("btype", str);
        }
        switch (aiVar.ff) {
            case 0:
                str2 = "light";
                break;
            case 1:
                str2 = "medium";
                break;
            case 2:
                str2 = "dark";
                break;
        }
        if (str2 != null) {
            hashMap.put("callbuttoncolor", str2);
        }
        if (aiVar.fg != null) {
            hashMap.put("channel", aiVar.fg);
        }
        if (Color.alpha(aiVar.fh) != 0) {
            hashMap.put("dcolor", m(aiVar.fh));
        }
        if (aiVar.fi != null) {
            hashMap.put("font", aiVar.fi);
        }
        if (Color.alpha(aiVar.fj) != 0) {
            hashMap.put("hcolor", m(aiVar.fj));
        }
        hashMap.put("headersize", Integer.toString(aiVar.fk));
        if (aiVar.fl != null) {
            hashMap.put("q", aiVar.fl);
        }
    }

    private static void a(HashMap<String, Object> hashMap, ci ciVar) {
        hashMap.put("am", Integer.valueOf(ciVar.hZ));
        hashMap.put("cog", j(ciVar.ia));
        hashMap.put("coh", j(ciVar.ib));
        if (!TextUtils.isEmpty(ciVar.ic)) {
            hashMap.put("carrier", ciVar.ic);
        }
        hashMap.put("gl", ciVar.id);
        if (ciVar.ie) {
            hashMap.put("simulator", 1);
        }
        hashMap.put("ma", j(ciVar.f1if));
        hashMap.put("sp", j(ciVar.ig));
        hashMap.put("hl", ciVar.ih);
        if (!TextUtils.isEmpty(ciVar.ii)) {
            hashMap.put("mv", ciVar.ii);
        }
        hashMap.put("muv", Integer.valueOf(ciVar.ij));
        if (ciVar.ik != -2) {
            hashMap.put("cnt", Integer.valueOf(ciVar.ik));
        }
        hashMap.put("gnt", Integer.valueOf(ciVar.il));
        hashMap.put("pt", Integer.valueOf(ciVar.im));
        hashMap.put("rm", Integer.valueOf(ciVar.in));
        hashMap.put("riv", Integer.valueOf(ciVar.io));
        hashMap.put("u_sd", Float.valueOf(ciVar.ip));
        hashMap.put("sh", Integer.valueOf(ciVar.ir));
        hashMap.put("sw", Integer.valueOf(ciVar.iq));
    }

    private static void a(HashMap<String, Object> hashMap, v vVar) {
        String as = cl.as();
        if (as != null) {
            hashMap.put("abf", as);
        }
        if (vVar.ex != -1) {
            hashMap.put("cust_age", hJ.format(new Date(vVar.ex)));
        }
        if (vVar.extras != null) {
            hashMap.put("extras", vVar.extras);
        }
        if (vVar.ey != -1) {
            hashMap.put("cust_gender", Integer.valueOf(vVar.ey));
        }
        if (vVar.ez != null) {
            hashMap.put("kw", vVar.ez);
        }
        if (vVar.tagForChildDirectedTreatment != -1) {
            hashMap.put("tag_for_child_directed_treatment", Integer.valueOf(vVar.tagForChildDirectedTreatment));
        }
        if (vVar.eA) {
            hashMap.put("adtest", "on");
        }
        if (vVar.versionCode >= 2) {
            if (vVar.eB) {
                hashMap.put("d_imp_hdr", 1);
            }
            if (!TextUtils.isEmpty(vVar.eC)) {
                hashMap.put("ppid", vVar.eC);
            }
            if (vVar.eD != null) {
                a(hashMap, vVar.eD);
            }
        }
    }

    private static Integer j(boolean z) {
        return Integer.valueOf(z ? 1 : 0);
    }

    private static String m(int i) {
        return String.format(Locale.US, "#%06x", new Object[]{Integer.valueOf(16777215 & i)});
    }
}
