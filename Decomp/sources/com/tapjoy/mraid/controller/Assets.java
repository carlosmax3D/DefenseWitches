package com.tapjoy.mraid.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.webkit.JavascriptInterface;
import com.ansca.corona.Crypto;
import com.tapjoy.TapjoyLog;
import com.tapjoy.mraid.view.MraidView;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.jar.JarFile;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;

public class Assets extends Abstract {
    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String TAG = "MRAID Assets";
    private int imageNameCounter = 0;

    public Assets(MraidView mraidView, Context context) {
        super(mraidView, context);
    }

    private String asHex(MessageDigest messageDigest) {
        byte[] digest = messageDigest.digest();
        char[] cArr = new char[(digest.length * 2)];
        int i = 0;
        for (int i2 = 0; i2 < digest.length; i2++) {
            int i3 = i + 1;
            cArr[i] = HEX_CHARS[(digest[i2] >>> 4) & 15];
            i = i3 + 1;
            cArr[i3] = HEX_CHARS[digest[i2] & 15];
        }
        return new String(cArr);
    }

    private boolean contains(StringBuffer stringBuffer, String str) {
        try {
            return stringBuffer.indexOf(str) >= 0;
        } catch (Exception e) {
            TapjoyLog.d("contains", "html file does not contain " + str);
        }
    }

    public static boolean deleteDirectory(File file) {
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isDirectory()) {
                    deleteDirectory(listFiles[i]);
                } else {
                    listFiles[i].delete();
                }
            }
        }
        return file.delete();
    }

    public static boolean deleteDirectory(String str) {
        if (str != null) {
            return deleteDirectory(new File(str));
        }
        return false;
    }

    private File getAssetDir(String str) {
        return new File(this.mContext.getFilesDir().getPath() + File.separator + str);
    }

    private String getAssetName(String str) {
        return str.lastIndexOf(File.separatorChar) >= 0 ? str.substring(str.lastIndexOf(File.separatorChar) + 1) : str;
    }

    private String getAssetPath(String str) {
        return str.lastIndexOf(File.separatorChar) >= 0 ? str.substring(0, str.lastIndexOf(File.separatorChar)) : "/";
    }

    private String getFilesDir() {
        return this.mContext.getFilesDir().getPath();
    }

    private HttpEntity getHttpEntity(String str) {
        try {
            return new DefaultHttpClient().execute(new HttpGet(str)).getEntity();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String moveToAdDirectory(String str, String str2, String str3) {
        File file = new File(str2 + File.separator + str);
        new File(str2 + File.separator + "ad").mkdir();
        File file2 = new File(str2 + File.separator + "ad" + File.separator + str3);
        file2.mkdir();
        file.renameTo(new File(file2, file.getName()));
        return file2.getPath() + File.separator;
    }

    private void replace(StringBuffer stringBuffer, String str, String str2) {
        int indexOf = stringBuffer.indexOf(str);
        TapjoyLog.d("replace ", str2);
        stringBuffer.replace(indexOf, str.length() + indexOf, "file://" + str2);
    }

    public void addAsset(String str, String str2) {
        HttpEntity httpEntity = getHttpEntity(str2);
        InputStream inputStream = null;
        try {
            inputStream = httpEntity.getContent();
            writeToDisk(inputStream, str, false);
            this.mMraidView.injectMraidJavaScript("MraidAdController.addedAsset('" + str + "' )");
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e3) {
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e4) {
                }
            }
            throw th;
        }
        try {
            httpEntity.consumeContent();
        } catch (Exception e5) {
            e5.printStackTrace();
        }
    }

    public int cacheRemaining() {
        StatFs statFs = new StatFs(this.mContext.getFilesDir().getPath());
        return statFs.getFreeBlocks() * statFs.getBlockSize();
    }

    public String copyTextFromJarIntoAssetDir(String str, String str2) {
        InputStream inputStream;
        InputStream inputStream2 = null;
        try {
            URL resource = Assets.class.getClassLoader().getResource(str2);
            if (resource == null) {
                inputStream = this.mContext.getAssets().open(str2);
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
                inputStream = jarFile.getInputStream(jarFile.getJarEntry(str2));
            }
            String writeToDisk = writeToDisk(inputStream, str, false);
            if (inputStream == null) {
                return writeToDisk;
            }
            try {
                inputStream.close();
            } catch (Exception e) {
            }
            return writeToDisk;
        } catch (Exception e2) {
            TapjoyLog.e(TAG, "copyTextFromJarIntoAssetDir: " + e2.toString());
            if (inputStream2 != null) {
                try {
                    inputStream2.close();
                } catch (Exception e3) {
                }
            }
            return null;
        } catch (Throwable th) {
            if (inputStream2 != null) {
                try {
                    inputStream2.close();
                } catch (Exception e4) {
                }
            }
            throw th;
        }
    }

    public void deleteOldAds() {
        deleteDirectory(new File(getFilesDir() + File.separator + "ad"));
    }

    public FileOutputStream getAssetOutputString(String str) throws FileNotFoundException {
        File assetDir = getAssetDir(getAssetPath(str));
        assetDir.mkdirs();
        return new FileOutputStream(new File(assetDir, getAssetName(str)));
    }

    public String getAssetPath() {
        return "file://" + this.mContext.getFilesDir() + "/";
    }

    public void removeAsset(String str) {
        File assetDir = getAssetDir(getAssetPath(str));
        assetDir.mkdirs();
        new File(assetDir, getAssetName(str)).delete();
        this.mMraidView.injectMraidJavaScript("MraidAdController.assetRemoved('" + str + "' )");
    }

    public void stopAllListeners() {
    }

    public void storePicture(String str) {
        TapjoyLog.d(TAG, "Storing media from " + str + " to device photo album.  Output directory: " + Environment.getExternalStorageDirectory() + " state: " + Environment.getExternalStorageState());
        this.imageNameCounter++;
        try {
            URL url = new URL(str);
            String str2 = "MraidMedia" + this.imageNameCounter + ".jpg";
            File file = new File(Environment.getExternalStorageDirectory().toString() + "/" + str2);
            long currentTimeMillis = System.currentTimeMillis();
            Log.d(TAG, "download beginning");
            Log.d(TAG, "download url:" + url);
            Log.d(TAG, "downloaded file name:" + str2);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openConnection().getInputStream());
            ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(50);
            while (true) {
                int read = bufferedInputStream.read();
                if (read != -1) {
                    byteArrayBuffer.append((byte) read);
                } else {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(byteArrayBuffer.toByteArray());
                    fileOutputStream.close();
                    Log.d(TAG, "download ready in" + ((System.currentTimeMillis() - currentTimeMillis) / 1000) + " sec");
                    return;
                }
            }
        } catch (IOException e) {
            Log.d(TAG, "Error: " + e);
        }
    }

    @JavascriptInterface
    public void storePictureInit(String str) {
        final String str2 = str;
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setMessage("Are you sure you want to save file from " + str + " to your SD card?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Assets.this.storePicture(str2);
            }
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) null);
        builder.show();
    }

    public String writeToDisk(InputStream inputStream, String str, boolean z) throws IllegalStateException, IOException {
        int i = 0;
        byte[] bArr = new byte[1024];
        MessageDigest messageDigest = null;
        if (z) {
            try {
                messageDigest = MessageDigest.getInstance(Crypto.ALGORITHM_MD5);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = getAssetOutputString(str);
            while (true) {
                int read = inputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                if (z && messageDigest != null) {
                    messageDigest.update(bArr);
                }
                fileOutputStream.write(bArr, 0, read);
                i++;
            }
            fileOutputStream.flush();
            String filesDir = getFilesDir();
            if (z && messageDigest != null) {
                filesDir = moveToAdDirectory(str, filesDir, asHex(messageDigest));
            }
            return filesDir + str;
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e2) {
                }
            }
        }
    }

    public String writeToDiskWrap(InputStream inputStream, String str, boolean z, String str2, String str3) throws IllegalStateException, IOException {
        byte[] bArr = new byte[1024];
        MessageDigest messageDigest = null;
        if (z) {
            try {
                messageDigest = MessageDigest.getInstance(Crypto.ALGORITHM_MD5);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileOutputStream fileOutputStream = null;
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                if (z && messageDigest != null) {
                    messageDigest.update(bArr);
                }
                byteArrayOutputStream.write(bArr, 0, read);
            } finally {
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Exception e2) {
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (Exception e3) {
                    }
                }
            }
        }
        String byteArrayOutputStream2 = byteArrayOutputStream.toString();
        boolean z2 = byteArrayOutputStream2.indexOf("</html>") >= 0;
        StringBuffer stringBuffer = new StringBuffer(byteArrayOutputStream2);
        if (z2) {
            if (contains(stringBuffer, "mraid.js")) {
                replace(stringBuffer, "mraid.js", str3);
            } else if (contains(stringBuffer, "ormma.js")) {
                replace(stringBuffer, "ormma.js", str3);
            } else if (contains(stringBuffer, "ormma_bridge.js")) {
                replace(stringBuffer, "ormma_bridge.js", str3);
            }
        }
        fileOutputStream = getAssetOutputString(str);
        if (!z2) {
            fileOutputStream.write("<html>".getBytes());
            fileOutputStream.write("<head>".getBytes());
            fileOutputStream.write("<meta name='viewport' content='user-scalable=no initial-scale=1.0' />".getBytes());
            fileOutputStream.write("<title>Advertisement</title> ".getBytes());
            fileOutputStream.write(("<script src=\"file://" + str3 + "\" type=\"text/javascript\"></script>").getBytes());
            if (str2 != null) {
                fileOutputStream.write("<script type=\"text/javascript\">".getBytes());
                fileOutputStream.write(str2.getBytes());
                fileOutputStream.write("</script>".getBytes());
            }
            fileOutputStream.write("</head>".getBytes());
            fileOutputStream.write("<body style=\"margin:0; padding:0; overflow:hidden; background-color:transparent;\">".getBytes());
            fileOutputStream.write("<div align=\"center\"> ".getBytes());
        }
        if (!z2) {
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
        } else {
            fileOutputStream.write(stringBuffer.toString().getBytes());
        }
        if (!z2) {
            fileOutputStream.write("</div> ".getBytes());
            fileOutputStream.write("</body> ".getBytes());
            fileOutputStream.write("</html> ".getBytes());
        }
        fileOutputStream.flush();
        String filesDir = getFilesDir();
        return (!z || messageDigest == null) ? filesDir : moveToAdDirectory(str, filesDir, asHex(messageDigest));
    }
}
