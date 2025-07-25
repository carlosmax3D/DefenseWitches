package com.threatmetrix.TrustDefenderMobile;

import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import twitter4j.HttpResponseCode;

enum NativeGatherer {
    INSTANCE;
    
    private final String TAG;
    private final NativeGathererHelper m_gatherer;

    private class NativeGathererHelper {
        private final String TAG;
        boolean m_available;
        int m_packageLimit;
        int m_packageTotalLimit;
        int m_packagesFound;
        String m_systemPath;

        NativeGathererHelper() {
            this.TAG = NativeGathererHelper.class.getName();
            this.m_packagesFound = 0;
            this.m_packageLimit = HttpResponseCode.OK;
            this.m_packageTotalLimit = this.m_packageLimit;
            this.m_systemPath = "/system/app";
            boolean z = false;
            try {
                System.loadLibrary("trustdefender-jni");
                z = init(TrustDefenderMobileVersion.numeric.intValue());
                if (z) {
                    String str = this.TAG;
                    this.m_packagesFound = findPackages(this.m_packageLimit, this.m_systemPath);
                }
            } catch (UnsatisfiedLinkError e) {
                z = false;
            } catch (Throwable th) {
                Log.e(this.TAG, "Native code:", th);
            }
            this.m_available = z;
            String str2 = this.TAG;
            new StringBuilder("NativeGatherer() complete, found ").append(this.m_packagesFound);
        }

        /* access modifiers changed from: package-private */
        public native String[] checkURLs(String[] strArr);

        /* access modifiers changed from: protected */
        public void finalize() throws Throwable {
            super.finalize();
            finit();
        }

        /* access modifiers changed from: package-private */
        public native int findPackages(int i, String str);

        /* access modifiers changed from: package-private */
        public native String[] findRunningProcs();

        /* access modifiers changed from: package-private */
        public native void finit();

        /* access modifiers changed from: package-private */
        public native String[] getFontList(String str);

        /* access modifiers changed from: package-private */
        public native String getRandomString(int i);

        /* access modifiers changed from: package-private */
        public native String hashFile(String str);

        /* access modifiers changed from: package-private */
        public native boolean init(int i);

        /* access modifiers changed from: package-private */
        public native String md5(String str);

        /* access modifiers changed from: package-private */
        public native String sha1(String str);

        /* access modifiers changed from: package-private */
        public native String urlEncode(String str);

        /* access modifiers changed from: package-private */
        public native String xor(String str, String str2);
    }

    private String[] findRunningProcs() {
        if (this.m_gatherer.m_available) {
            return this.m_gatherer.findRunningProcs();
        }
        return null;
    }

    public final String[] checkURLs(String[] strArr) {
        String str = this.TAG;
        new StringBuilder().append(this.m_gatherer.m_available ? " available " : "not available ").append(" Found ").append(this.m_gatherer.m_packagesFound).append(" out of ").append(this.m_gatherer.m_packageTotalLimit);
        if (!this.m_gatherer.m_available) {
            return null;
        }
        if (this.m_gatherer.m_packagesFound == this.m_gatherer.m_packageLimit) {
            String str2 = this.TAG;
            new StringBuilder("Finding more packages ").append(this.m_gatherer.m_packagesFound).append(" / ").append(this.m_gatherer.m_packageTotalLimit);
            this.m_gatherer.m_packageTotalLimit += this.m_gatherer.findPackages(this.m_gatherer.m_packageLimit, this.m_gatherer.m_systemPath);
        }
        return this.m_gatherer.checkURLs(strArr);
    }

    public final List<String> getFontList(String str) {
        if (!this.m_gatherer.m_available) {
            return null;
        }
        String[] fontList = this.m_gatherer.getFontList(str);
        return fontList != null ? Arrays.asList(fontList) : new ArrayList();
    }

    public final String getRandomString(int i) {
        if (this.m_gatherer.m_available) {
            return this.m_gatherer.getRandomString(32);
        }
        return null;
    }

    public final String hashFile(String str) {
        if (this.m_gatherer.m_available) {
            return this.m_gatherer.hashFile(str);
        }
        return null;
    }

    public final boolean isAvailable() {
        return this.m_gatherer.m_available;
    }

    public final String md5(String str) {
        if (this.m_gatherer.m_available) {
            return this.m_gatherer.md5(str);
        }
        return null;
    }

    public final String sha1(String str) {
        if (this.m_gatherer.m_available) {
            return this.m_gatherer.sha1(str);
        }
        return null;
    }

    public final String urlEncode(String str) {
        if (this.m_gatherer.m_available) {
            return this.m_gatherer.urlEncode(str);
        }
        return null;
    }

    public final String xor(String str, String str2) {
        if (this.m_gatherer.m_available) {
            return this.m_gatherer.xor(str, str2);
        }
        return null;
    }
}
