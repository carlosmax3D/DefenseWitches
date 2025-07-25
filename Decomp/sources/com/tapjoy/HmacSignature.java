package com.tapjoy;

import android.net.Uri;
import android.util.Log;
import com.flurry.android.Constants;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSignature {
    private String _method;
    private String _secret;

    public HmacSignature(String str, String str2) {
        this._method = str;
        this._secret = str2;
    }

    private String prepareMessage(String str, Map<String, String> map) {
        Uri parse = Uri.parse(str);
        String str2 = parse.getScheme() + "://" + parse.getHost();
        if (!((parse.getScheme().equals("http") && parse.getPort() == 80) || (parse.getScheme().equals("https") && parse.getPort() == 443)) && -1 != parse.getPort()) {
            str2 = str2 + ":" + parse.getPort();
        }
        String str3 = "POST&" + Uri.encode(str2.toLowerCase() + parse.getPath()) + "&" + Uri.encode(prepareParams(map));
        Log.v("HmacSignature", "Base Url: " + str3);
        return str3;
    }

    private String prepareParams(Map<String, String> map) {
        TreeSet treeSet = new TreeSet(map.keySet());
        StringBuilder sb = new StringBuilder();
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            sb.append(str + "=" + map.get(str) + "&");
        }
        sb.deleteCharAt(sb.lastIndexOf("&"));
        Log.v("HmacSignature", "Unhashed String: " + sb.toString());
        return sb.toString();
    }

    public boolean matches(String str, Map<String, String> map, String str2) {
        return sign(str, map).equals(str2);
    }

    public String sign(String str, Map<String, String> map) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(this._secret.getBytes(), this._method);
            Mac instance = Mac.getInstance(this._method);
            instance.init(secretKeySpec);
            byte[] doFinal = instance.doFinal(prepareMessage(str, map).getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : doFinal) {
                String hexString = Integer.toHexString(b & Constants.UNKNOWN);
                if (hexString.length() == 1) {
                    sb.append('0');
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
