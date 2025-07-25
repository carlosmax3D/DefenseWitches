package com.tapjoy.mraid.util;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import jp.stargarage.g2metrics.BuildConfig;

public class Encryptor {
    private static final String HEX = "0123456789ABCDEF";

    private static void appendHex(StringBuffer stringBuffer, byte b) {
        stringBuffer.append(HEX.charAt((b >> 4) & 15)).append(HEX.charAt(b & 15));
    }

    public static String decrypt(String str, String str2) throws Exception {
        return new String(decrypt(getRawKey(str.getBytes()), toByte(str2)));
    }

    private static byte[] decrypt(byte[] bArr, byte[] bArr2) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        Cipher instance = Cipher.getInstance("AES");
        instance.init(2, secretKeySpec);
        return instance.doFinal(bArr2);
    }

    public static String encrypt(String str, String str2) throws Exception {
        return toHex(encrypt(getRawKey(str.getBytes()), str2.getBytes()));
    }

    private static byte[] encrypt(byte[] bArr, byte[] bArr2) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        Cipher instance = Cipher.getInstance("AES");
        instance.init(1, secretKeySpec);
        return instance.doFinal(bArr2);
    }

    public static String fromHex(String str) {
        return new String(toByte(str));
    }

    private static byte[] getRawKey(byte[] bArr) throws Exception {
        KeyGenerator instance = KeyGenerator.getInstance("AES");
        SecureRandom instance2 = SecureRandom.getInstance("SHA1PRNG");
        instance2.setSeed(bArr);
        instance.init(256, instance2);
        return instance.generateKey().getEncoded();
    }

    public static byte[] toByte(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr[i] = Integer.valueOf(str.substring(i * 2, (i * 2) + 2), 16).byteValue();
        }
        return bArr;
    }

    public static String toHex(String str) {
        return toHex(str.getBytes());
    }

    public static String toHex(byte[] bArr) {
        if (bArr == null) {
            return BuildConfig.FLAVOR;
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte appendHex : bArr) {
            appendHex(stringBuffer, appendHex);
        }
        return stringBuffer.toString();
    }
}
