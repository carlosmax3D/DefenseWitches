package com.facebook.internal;

import android.net.Uri;
import com.facebook.LoggingBehavior;
import com.facebook.internal.FileLruCache;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

class UrlRedirectCache {
    private static final String REDIRECT_CONTENT_TAG = (TAG + "_Redirect");
    static final String TAG = UrlRedirectCache.class.getSimpleName();
    private static volatile FileLruCache urlRedirectCache;

    UrlRedirectCache() {
    }

    static void cacheUriRedirect(Uri uri, Uri uri2) {
        if (uri != null && uri2 != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getCache().openPutStream(uri.toString(), REDIRECT_CONTENT_TAG);
                outputStream.write(uri2.toString().getBytes());
            } catch (IOException e) {
            } finally {
                Utility.closeQuietly(outputStream);
            }
        }
    }

    static void clearCache() {
        try {
            getCache().clearCache();
        } catch (IOException e) {
            Logger.log(LoggingBehavior.CACHE, 5, TAG, "clearCache failed " + e.getMessage());
        }
    }

    static synchronized FileLruCache getCache() throws IOException {
        FileLruCache fileLruCache;
        synchronized (UrlRedirectCache.class) {
            if (urlRedirectCache == null) {
                urlRedirectCache = new FileLruCache(TAG, new FileLruCache.Limits());
            }
            fileLruCache = urlRedirectCache;
        }
        return fileLruCache;
    }

    static Uri getRedirectedUri(Uri uri) {
        InputStreamReader inputStreamReader;
        Uri uri2 = null;
        if (uri != null) {
            String uri3 = uri.toString();
            InputStreamReader inputStreamReader2 = null;
            try {
                FileLruCache cache = getCache();
                boolean z = false;
                while (true) {
                    try {
                        inputStreamReader = inputStreamReader2;
                        InputStream inputStream = cache.get(uri3, REDIRECT_CONTENT_TAG);
                        if (inputStream == null) {
                            break;
                        }
                        z = true;
                        inputStreamReader2 = new InputStreamReader(inputStream);
                        char[] cArr = new char[128];
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            int read = inputStreamReader2.read(cArr, 0, cArr.length);
                            if (read <= 0) {
                                break;
                            }
                            sb.append(cArr, 0, read);
                        }
                        Utility.closeQuietly(inputStreamReader2);
                        uri3 = sb.toString();
                    } catch (IOException e) {
                        inputStreamReader2 = inputStreamReader;
                        Utility.closeQuietly(inputStreamReader2);
                        return uri2;
                    } catch (Throwable th) {
                        th = th;
                        inputStreamReader2 = inputStreamReader;
                        Utility.closeQuietly(inputStreamReader2);
                        throw th;
                    }
                }
                if (z) {
                    uri2 = Uri.parse(uri3);
                    Utility.closeQuietly(inputStreamReader);
                } else {
                    Utility.closeQuietly(inputStreamReader);
                    InputStreamReader inputStreamReader3 = inputStreamReader;
                }
            } catch (IOException e2) {
                Utility.closeQuietly(inputStreamReader2);
                return uri2;
            } catch (Throwable th2) {
                th = th2;
                Utility.closeQuietly(inputStreamReader2);
                throw th;
            }
        }
        return uri2;
    }
}
