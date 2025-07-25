package com.ansca.corona.version;

import jp.stargarage.g2metrics.BuildConfig;

public class AndroidVersionSpecificFactory {
    public static IAndroidVersionSpecific create() {
        try {
            return (IAndroidVersionSpecific) Class.forName("com.ansca.corona.version.android" + getAndroidVersion() + ".AndroidVersionSpecific").newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SecurityException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
            return null;
        } catch (InstantiationException e4) {
            e4.printStackTrace();
            return null;
        } catch (IllegalAccessException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    private static String getAndroidVersion() {
        try {
            return (String) Class.forName("com.ansca.corona.version.AndroidVersion").getField("apiVersion").get((Object) null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return BuildConfig.FLAVOR;
        } catch (SecurityException e2) {
            e2.printStackTrace();
            return BuildConfig.FLAVOR;
        } catch (NoSuchFieldException e3) {
            e3.printStackTrace();
            return BuildConfig.FLAVOR;
        } catch (IllegalArgumentException e4) {
            e4.printStackTrace();
            return BuildConfig.FLAVOR;
        } catch (IllegalAccessException e5) {
            e5.printStackTrace();
            return BuildConfig.FLAVOR;
        }
    }
}
