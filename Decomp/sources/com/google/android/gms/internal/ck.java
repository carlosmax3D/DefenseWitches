package com.google.android.gms.internal;

import com.ansca.corona.Crypto;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public final class ck {
    private static final Object hC = new Object();
    public static final String iu;
    private static BigInteger iv = BigInteger.ONE;

    static {
        UUID randomUUID = UUID.randomUUID();
        byte[] byteArray = BigInteger.valueOf(randomUUID.getLeastSignificantBits()).toByteArray();
        byte[] byteArray2 = BigInteger.valueOf(randomUUID.getMostSignificantBits()).toByteArray();
        String bigInteger = new BigInteger(1, byteArray).toString();
        for (int i = 0; i < 2; i++) {
            try {
                MessageDigest instance = MessageDigest.getInstance(Crypto.ALGORITHM_MD5);
                instance.update(byteArray);
                instance.update(byteArray2);
                byte[] bArr = new byte[8];
                System.arraycopy(instance.digest(), 0, bArr, 0, 8);
                bigInteger = new BigInteger(1, bArr).toString();
            } catch (NoSuchAlgorithmException e) {
            }
        }
        iu = bigInteger;
    }

    public static String ar() {
        String bigInteger;
        synchronized (hC) {
            bigInteger = iv.toString();
            iv.add(BigInteger.ONE);
        }
        return bigInteger;
    }
}
