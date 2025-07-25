package com.tapjoy;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.xml.parsers.DocumentBuilderFactory;
import jp.stargarage.g2metrics.BuildConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TapjoyUtil {
    private static final String ASSET_PREFIX = "com/tapjoy/res/";
    private static final String TAG = "TapjoyUtil";
    private static HashMap<String, Object> _resources = new HashMap<>();
    private static String mraidJs = null;

    public static String SHA1(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return hashAlgorithm("SHA-1", str);
    }

    public static String SHA256(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return hashAlgorithm("SHA-256", str);
    }

    public static Document buildDocument(String str) {
        try {
            DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
            return newInstance.newDocumentBuilder().parse(new ByteArrayInputStream(str.getBytes("UTF-8")));
        } catch (Exception e) {
            TapjoyLog.e(TAG, "buildDocument exception: " + e.toString());
            return null;
        }
    }

    private static String convertToHex(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bArr.length; i++) {
            byte b = (bArr[i] >>> 4) & 15;
            int i2 = 0;
            while (true) {
                if (b < 0 || b > 9) {
                    stringBuffer.append((char) ((b - 10) + 97));
                } else {
                    stringBuffer.append((char) (b + 48));
                }
                b = bArr[i] & 15;
                int i3 = i2 + 1;
                if (i2 >= 1) {
                    break;
                }
                i2 = i3;
            }
        }
        return stringBuffer.toString();
    }

    public static String convertURLParams(Map<String, String> map, boolean z) {
        String str = BuildConfig.FLAVOR;
        for (Map.Entry next : map.entrySet()) {
            if (str.length() > 0) {
                str = str + "&";
            }
            str = z ? str + Uri.encode((String) next.getKey()) + "=" + Uri.encode((String) next.getValue()) : str + ((String) next.getKey()) + "=" + ((String) next.getValue());
        }
        return str;
    }

    public static Map<String, String> convertURLParams(String str, boolean z) {
        HashMap hashMap = new HashMap();
        int i = 0;
        boolean z2 = false;
        String str2 = BuildConfig.FLAVOR;
        String str3 = BuildConfig.FLAVOR;
        while (i < str.length() && i != -1) {
            char charAt = str.charAt(i);
            if (!z2) {
                if (charAt == '=') {
                    z2 = true;
                    str3 = z ? Uri.decode(str2) : str2;
                    str2 = BuildConfig.FLAVOR;
                } else {
                    str2 = str2 + charAt;
                }
            } else if (z2) {
                if (charAt == '&') {
                    z2 = false;
                    String decode = z ? Uri.decode(str2) : str2;
                    str2 = BuildConfig.FLAVOR;
                    hashMap.put(str3, decode);
                } else {
                    str2 = str2 + charAt;
                }
            }
            i++;
        }
        if (z2 && str2.length() > 0) {
            hashMap.put(str3, z ? Uri.decode(str2) : str2);
        }
        return hashMap;
    }

    public static String copyTextFromJarIntoString(String str) {
        return copyTextFromJarIntoString(str, (Context) null);
    }

    public static String copyTextFromJarIntoString(String str, Context context) {
        InputStream inputStream;
        byte[] bArr = new byte[1024];
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream2 = null;
        URL resource = TapjoyUtil.class.getClassLoader().getResource(str);
        if (context == null || resource != null) {
            String file = resource.getFile();
            if (file.startsWith("jar:")) {
                file = file.substring(4);
            }
            if (file.startsWith("file:")) {
                file = file.substring(5);
            }
            int indexOf = file.indexOf("!");
            if (indexOf > 0) {
                file = file.substring(0, indexOf);
            }
            JarFile jarFile = new JarFile(file);
            inputStream = jarFile.getInputStream(jarFile.getJarEntry(str));
        } else {
            try {
                inputStream = context.getAssets().open(str);
            } catch (Exception e) {
                TapjoyLog.d(TAG, "file exception: " + e.toString());
                e.printStackTrace();
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    } catch (Exception e2) {
                    }
                }
                return null;
            } catch (Throwable th) {
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    } catch (Exception e3) {
                    }
                }
                throw th;
            }
        }
        while (true) {
            int read = inputStream.read(bArr);
            if (read <= 0) {
                break;
            }
            stringBuffer.append(new String(bArr).substring(0, read));
        }
        String stringBuffer2 = stringBuffer.toString();
        if (inputStream == null) {
            return stringBuffer2;
        }
        try {
            inputStream.close();
        } catch (Exception e4) {
        }
        return stringBuffer2;
    }

    public static Bitmap createBitmapFromView(View view) {
        Bitmap bitmap = null;
        if (view == null || view.getLayoutParams().width <= 0 || view.getLayoutParams().height <= 0) {
            return null;
        }
        try {
            bitmap = Bitmap.createBitmap(view.getLayoutParams().width, view.getLayoutParams().height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            view.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            TapjoyLog.i(TAG, "error creating bitmap: " + e.toString());
            return bitmap;
        }
    }

    public static void deleteFileOrDirectory(File file) {
        if (file.isDirectory()) {
            for (File deleteFileOrDirectory : file.listFiles()) {
                deleteFileOrDirectory(deleteFileOrDirectory);
            }
        }
        TapjoyLog.i(TAG, "****************************************");
        TapjoyLog.i(TAG, "deleteFileOrDirectory: " + file.getAbsolutePath());
        TapjoyLog.i(TAG, "****************************************");
        file.delete();
    }

    public static String determineMimeType(String str) {
        String str2 = BuildConfig.FLAVOR;
        if (str.endsWith(".")) {
            str = str.substring(0, str.length() - 1);
        }
        if (str.lastIndexOf(46) != -1) {
            str2 = str.substring(str.lastIndexOf(46) + 1);
        }
        return str2.equals("css") ? "text/css" : str2.equals("js") ? "text/javascript" : str2.equals("html") ? "text/html" : "application/octet-stream";
    }

    public static long fileOrDirectorySize(File file) {
        long j = 0;
        for (File file2 : file.listFiles()) {
            j += file2.isFile() ? file2.length() : fileOrDirectorySize(file2);
        }
        return j;
    }

    public static String getNodeTrimValue(NodeList nodeList) {
        Element element = (Element) nodeList.item(0);
        String str = BuildConfig.FLAVOR;
        if (element == null) {
            return null;
        }
        NodeList childNodes = element.getChildNodes();
        int length = childNodes.getLength();
        for (int i = 0; i < length; i++) {
            Node item = childNodes.item(i);
            if (item != null) {
                str = str + item.getNodeValue();
            }
        }
        if (str == null || str.equals(BuildConfig.FLAVOR)) {
            return null;
        }
        return str.trim();
    }

    public static String getRedirectDomain(String str) {
        return str != null ? str.substring(str.indexOf("//") + "//".length(), str.lastIndexOf("/")) : BuildConfig.FLAVOR;
    }

    public static Object getResource(String str) {
        return _resources.get(str);
    }

    private static String hashAlgorithm(String str, String str2) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] bArr = new byte[40];
        MessageDigest instance = MessageDigest.getInstance(str);
        instance.update(str2.getBytes("iso-8859-1"), 0, str2.length());
        return convertToHex(instance.digest());
    }

    public static Bitmap loadBitmapFromJar(String str, Context context) {
        InputStream inputStream;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        byte[] bArr = (byte[]) getResource(str);
        if (bArr != null) {
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            bitmap = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }
        if (bitmap == null) {
            InputStream inputStream2 = null;
            String str2 = ASSET_PREFIX + str;
            try {
                URL resource = TapjoyUtil.class.getClassLoader().getResource(str2);
                if (resource == null) {
                    AssetManager assets = context.getAssets();
                    BitmapFactory.decodeStream(assets.open(str2), (Rect) null, options);
                    inputStream = assets.open(str2);
                } else {
                    String file = resource.getFile();
                    if (file.startsWith("jar:")) {
                        file = file.substring(4);
                    }
                    if (file.startsWith("file:")) {
                        file = file.substring(5);
                    }
                    int indexOf = file.indexOf("!");
                    if (indexOf > 0) {
                        file = file.substring(0, indexOf);
                    }
                    JarFile jarFile = new JarFile(file);
                    JarEntry jarEntry = jarFile.getJarEntry(str2);
                    BitmapFactory.decodeStream(jarFile.getInputStream(jarEntry), (Rect) null, options);
                    inputStream = jarFile.getInputStream(jarEntry);
                }
                bitmap = BitmapFactory.decodeStream(inputStream);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (inputStream2 == null) {
                    return null;
                }
                try {
                    inputStream2.close();
                    return null;
                } catch (IOException e3) {
                    return null;
                }
            } catch (Throwable th) {
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    } catch (IOException e4) {
                    }
                }
                throw th;
            }
        }
        float deviceScreenDensityScale = TapjoyConnectCore.getDeviceScreenDensityScale();
        if (bitmap != null) {
            bitmap = Bitmap.createScaledBitmap(bitmap, (int) (((float) options.outWidth) * deviceScreenDensityScale), (int) (((float) options.outHeight) * deviceScreenDensityScale), true);
        }
        return bitmap;
    }

    public static void safePut(Map<String, String> map, String str, String str2, boolean z) {
        if (str != null && str.length() > 0 && str2 != null && str2.length() > 0) {
            if (z) {
                map.put(Uri.encode(str), Uri.encode(str2));
            } else {
                map.put(str, str2);
            }
        }
    }

    public static View scaleDisplayAd(View view, int i) {
        int i2 = view.getLayoutParams().width;
        int i3 = view.getLayoutParams().height;
        TapjoyLog.i(TAG, "wxh: " + i2 + "x" + i3);
        if (i2 > i) {
            int intValue = Double.valueOf(Double.valueOf(Double.valueOf((double) i).doubleValue() / Double.valueOf((double) i2).doubleValue()).doubleValue() * 100.0d).intValue();
            ((WebView) view).getSettings().setSupportZoom(true);
            ((WebView) view).setPadding(0, 0, 0, 0);
            ((WebView) view).setVerticalScrollBarEnabled(false);
            ((WebView) view).setHorizontalScrollBarEnabled(false);
            ((WebView) view).setInitialScale(intValue);
            view.setLayoutParams(new ViewGroup.LayoutParams(i, (i * i3) / i2));
        }
        return view;
    }

    public static void setResource(String str, Object obj) {
        _resources.put(str, obj);
    }

    public static void writeFileToDevice(BufferedInputStream bufferedInputStream, OutputStream outputStream) throws IOException, SocketTimeoutException {
        byte[] bArr = new byte[1024];
        while (true) {
            int read = bufferedInputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }
}
