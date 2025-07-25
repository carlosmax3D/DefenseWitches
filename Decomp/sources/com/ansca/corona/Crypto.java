package com.ansca.corona;

import java.security.MessageDigest;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
    public static final String ALGORITHM_HMAC_MD4 = "HmacMD4";
    public static final String ALGORITHM_HMAC_MD5 = "HmacMD5";
    public static final String ALGORITHM_HMAC_SHA1A = "HmacSHA1A";
    public static final String ALGORITHM_HMAC_SHA224A = "HmacSHA224A";
    public static final String ALGORITHM_HMAC_SHA256A = "HmacSHA256A";
    public static final String ALGORITHM_HMAC_SHA384 = "HmacSHA384";
    public static final String ALGORITHM_HMAC_SHA512 = "HmacSHA512";
    public static final String ALGORITHM_MD4 = "MD4";
    public static final String ALGORITHM_MD5 = "MD5";
    public static final String ALGORITHM_SHA1A = "SHA1A";
    public static final String ALGORITHM_SHA224A = "SHA224A";
    public static final String ALGORITHM_SHA256A = "SHA256A";
    public static final String ALGORITHM_SHA384 = "SHA384";
    public static final String ALGORITHM_SHA512 = "SHA512";

    public static byte[] CalculateDigest(String str, byte[] bArr) {
        try {
            return MessageDigest.getInstance(str).digest(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] CalculateHMAC(String str, byte[] bArr, byte[] bArr2) {
        try {
            Mac instance = Mac.getInstance(str);
            instance.init(new SecretKeySpec(bArr, "RAW"));
            return instance.doFinal(bArr2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int GetDigestLength(String str) {
        try {
            return MessageDigest.getInstance(str).getDigestLength();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
