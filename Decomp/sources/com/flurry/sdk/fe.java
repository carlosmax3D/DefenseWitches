package com.flurry.sdk;

import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import jp.stargarage.g2metrics.BuildConfig;

public final class fe {
    private static final String a = fe.class.getSimpleName();

    public static long a(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read < 0) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += (long) read;
        }
    }

    public static String a(String str) {
        return a(str, (int) MotionEventCompat.ACTION_MASK);
    }

    public static String a(String str, int i) {
        return str == null ? BuildConfig.FLAVOR : str.length() > i ? str.substring(0, i) : str;
    }

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (byte b : bArr) {
            sb.append(cArr[(byte) ((b & 240) >> 4)]);
            sb.append(cArr[(byte) (b & 15)]);
        }
        return sb.toString();
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
            }
        }
    }

    public static boolean a(long j) {
        return j == 0 || System.currentTimeMillis() <= j;
    }

    public static boolean a(Intent intent) {
        return Cdo.a().c().queryIntentActivities(intent, 65536).size() > 0;
    }

    public static String b(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            eo.a(5, a, "Cannot encode '" + str + "'");
            return BuildConfig.FLAVOR;
        }
    }

    public static boolean b(Intent intent) {
        if (intent == null) {
            return false;
        }
        return Cdo.a().b().getPackageName().equals(intent.resolveActivity(Cdo.a().c()).getPackageName());
    }

    public static String c(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            eo.a(5, a, "Cannot decode '" + str + "'");
            return BuildConfig.FLAVOR;
        }
    }

    public static byte[] d(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(str.getBytes(), 0, str.length());
            return instance.digest();
        } catch (NoSuchAlgorithmException e) {
            eo.a(6, a, "Unsupported SHA1: " + e.getMessage());
            return null;
        }
    }

    public static String e(String str) {
        return str.replace("'", "\\'").replace("\\n", BuildConfig.FLAVOR).replace("\\r", BuildConfig.FLAVOR).replace("\\t", BuildConfig.FLAVOR);
    }

    public static Map<String, String> f(String str) {
        HashMap hashMap = new HashMap();
        if (!TextUtils.isEmpty(str)) {
            for (String split : str.split("&")) {
                String[] split2 = split.split("=");
                if (!split2[0].equals("event")) {
                    hashMap.put(c(split2[0]), c(split2[1]));
                }
            }
        }
        return hashMap;
    }

    public static long g(String str) {
        if (str == null) {
            return 0;
        }
        int length = str.length();
        long j = 1125899906842597L;
        int i = 0;
        while (i < length) {
            i++;
            j = ((long) str.charAt(i)) + (j * 31);
        }
        return j;
    }
}
