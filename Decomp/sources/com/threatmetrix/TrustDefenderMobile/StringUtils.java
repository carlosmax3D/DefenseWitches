package com.threatmetrix.TrustDefenderMobile;

import android.util.Log;
import com.ansca.corona.Crypto;
import com.flurry.android.Constants;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import jp.stargarage.g2metrics.BuildConfig;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

class StringUtils {
    private static final String TAG = StringUtils.class.getSimpleName();
    private static final char[] hexArray = "0123456789abcdef".toCharArray();
    private static final MessageDigest m_md5Digest;
    private static final MessageDigest m_sha1Digest;

    static {
        MessageDigest messageDigest = null;
        if (!NativeGatherer.INSTANCE.isAvailable()) {
            String str = TAG;
            try {
                messageDigest = MessageDigest.getInstance("SHA1");
            } catch (NoSuchAlgorithmException e) {
                String str2 = TAG;
            }
        }
        m_sha1Digest = messageDigest;
        MessageDigest messageDigest2 = null;
        if (!NativeGatherer.INSTANCE.isAvailable()) {
            String str3 = TAG;
            try {
                messageDigest2 = MessageDigest.getInstance(Crypto.ALGORITHM_MD5);
            } catch (NoSuchAlgorithmException e2) {
                String str4 = TAG;
            }
        }
        m_md5Digest = messageDigest2;
    }

    StringUtils() {
    }

    static String ListToSeperatedString(List<String> list, String str) {
        StringBuilder sb = new StringBuilder();
        for (String next : list) {
            if (!next.isEmpty()) {
                if (sb.length() > 0) {
                    sb.append(str);
                }
                sb.append(next);
            }
        }
        return sb.toString();
    }

    static String MD5(String str) {
        String bytesToHex;
        if (str == null || str.isEmpty()) {
            return BuildConfig.FLAVOR;
        }
        if (NativeGatherer.INSTANCE.isAvailable()) {
            return NativeGatherer.INSTANCE.md5(str);
        }
        if (m_md5Digest == null) {
            return BuildConfig.FLAVOR;
        }
        synchronized (m_md5Digest) {
            m_md5Digest.update(str.getBytes());
            byte[] digest = m_md5Digest.digest();
            m_md5Digest.reset();
            bytesToHex = bytesToHex(digest);
        }
        return bytesToHex;
    }

    static String SHA1(String str) {
        String bytesToHex;
        if (str == null || str.isEmpty()) {
            return BuildConfig.FLAVOR;
        }
        if (NativeGatherer.INSTANCE.isAvailable()) {
            return NativeGatherer.INSTANCE.sha1(str);
        }
        if (m_sha1Digest == null) {
            return BuildConfig.FLAVOR;
        }
        synchronized (m_sha1Digest) {
            m_sha1Digest.update(str.getBytes());
            byte[] digest = m_sha1Digest.digest();
            m_sha1Digest.reset();
            bytesToHex = bytesToHex(digest);
        }
        return bytesToHex;
    }

    private static String bytesToHex(byte[] bArr) {
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i] & Constants.UNKNOWN;
            cArr[i * 2] = hexArray[b >>> 4];
            cArr[(i * 2) + 1] = hexArray[b & 15];
        }
        return new String(cArr);
    }

    private static Header[] mapToHeader(Map<String, String> map) {
        Header[] headerArr = new Header[map.size()];
        int i = 0;
        for (Map.Entry next : map.entrySet()) {
            if (!((String) next.getKey()).isEmpty() && !((String) next.getValue()).isEmpty()) {
                headerArr[i] = new BasicHeader((String) next.getKey(), (String) next.getValue());
                i++;
            }
        }
        return (Header[]) Arrays.copyOf(headerArr, i);
    }

    static String new_session_id() {
        String str = TAG;
        return NativeGatherer.INSTANCE.isAvailable() ? NativeGatherer.INSTANCE.getRandomString(32) : UUID.randomUUID().toString().toLowerCase(Locale.US).replaceAll(AdNetworkKey.DEFAULT_AD, BuildConfig.FLAVOR);
    }

    static List<String> splitNonRegex(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        while (true) {
            int indexOf = str.indexOf(str2);
            if (indexOf == -1) {
                break;
            }
            if (indexOf > 1) {
                arrayList.add(str.substring(0, indexOf));
            }
            str = str.substring(str2.length() + indexOf);
        }
        if (!str.isEmpty()) {
            arrayList.add(str);
        }
        return arrayList;
    }

    static Map<String, String> splitQuery(String str) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (String next : splitNonRegex(str, "&")) {
            int indexOf = next.indexOf("=");
            try {
                linkedHashMap.put(URLDecoder.decode(next.substring(0, indexOf), "UTF-8"), URLDecoder.decode(next.substring(indexOf + 1), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                String str2 = TAG;
            }
        }
        return linkedHashMap;
    }

    static String urlEncode(String str) {
        if (NativeGatherer.INSTANCE.isAvailable()) {
            return NativeGatherer.INSTANCE.urlEncode(str);
        }
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Failed url encoding", e);
            return null;
        }
    }

    static String xor(String str, String str2) {
        if (NativeGatherer.INSTANCE.isAvailable()) {
            return NativeGatherer.INSTANCE.xor(str, str2);
        }
        String str3 = str.length() + "&";
        byte[] bArr = new byte[(str.length() + str3.length())];
        int length = str2.length();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i3 < str3.length()) {
            int i4 = i2 + 1;
            int i5 = i + 1;
            bArr[i2] = (byte) (((byte) str3.charAt(i3)) ^ (((byte) str2.charAt(i)) & 10));
            i = i5 >= length ? 0 : i5;
            i3++;
            i2 = i4;
        }
        int i6 = 0;
        while (i6 < str.length()) {
            int i7 = i2 + 1;
            int i8 = i + 1;
            bArr[i2] = (byte) (((byte) str.charAt(i6)) ^ (((byte) str2.charAt(i)) & 10));
            i = i8 >= length ? 0 : i8;
            i6++;
            i2 = i7;
        }
        return bytesToHex(bArr);
    }
}
