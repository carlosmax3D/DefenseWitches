package com.flurry.sdk;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cz {
    private static final String a = cz.class.getSimpleName();

    public Map<String, List<String>> a(String str) {
        eo.a(3, a, "Parsing referrer map");
        if (str == null) {
            return Collections.emptyMap();
        }
        HashMap hashMap = new HashMap();
        String[] split = str.split("&");
        int length = split.length;
        for (int i = 0; i < length; i++) {
            String[] split2 = split[i].split("=");
            if (split2.length != 2) {
                eo.a(5, a, "Invalid referrer Element: " + split[i] + " in referrer tag " + str);
            } else {
                String decode = URLDecoder.decode(split2[0]);
                String decode2 = URLDecoder.decode(split2[1]);
                if (hashMap.get(decode) == null) {
                    hashMap.put(decode, new ArrayList());
                }
                ((List) hashMap.get(decode)).add(decode2);
            }
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            eo.a(3, a, "entry: " + ((String) entry.getKey()) + "=" + entry.getValue());
        }
        StringBuilder sb = new StringBuilder();
        if (hashMap.get("utm_source") == null) {
            sb.append("Campaign Source is missing.\n");
        }
        if (hashMap.get("utm_medium") == null) {
            sb.append("Campaign Medium is missing.\n");
        }
        if (hashMap.get("utm_campaign") == null) {
            sb.append("Campaign Name is missing.\n");
        }
        if (sb.length() > 0) {
            eo.a(5, a, "Detected missing referrer keys : " + sb.toString());
        }
        return hashMap;
    }
}
