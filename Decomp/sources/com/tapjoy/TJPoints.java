package com.tapjoy;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;
import java.util.UUID;
import jp.stargarage.g2metrics.BuildConfig;
import org.w3c.dom.Document;

public class TJPoints {
    private static final String TAG = "TapjoyPoints";
    /* access modifiers changed from: private */
    public static TapjoyAwardPointsNotifier tapjoyAwardPointsNotifier;
    private static TapjoyEarnedPointsNotifier tapjoyEarnedPointsNotifier;
    /* access modifiers changed from: private */
    public static TapjoyNotifier tapjoyNotifier;
    /* access modifiers changed from: private */
    public static TapjoySpendPointsNotifier tapjoySpendPointsNotifier;
    int awardTapPoints = 0;
    Context context;
    String spendTapPoints = null;

    public TJPoints(Context context2) {
        this.context = context2;
    }

    /* access modifiers changed from: private */
    public boolean handleAwardPointsResponse(String str) {
        Document buildDocument = TapjoyUtil.buildDocument(str);
        if (buildDocument != null) {
            String nodeTrimValue = TapjoyUtil.getNodeTrimValue(buildDocument.getElementsByTagName("Success"));
            if (nodeTrimValue != null && nodeTrimValue.equals("true")) {
                String nodeTrimValue2 = TapjoyUtil.getNodeTrimValue(buildDocument.getElementsByTagName("TapPoints"));
                String nodeTrimValue3 = TapjoyUtil.getNodeTrimValue(buildDocument.getElementsByTagName("CurrencyName"));
                if (nodeTrimValue2 == null || nodeTrimValue3 == null) {
                    TapjoyLog.e(TAG, "Invalid XML: Missing tags.");
                } else {
                    saveTapPointsTotal(Integer.parseInt(nodeTrimValue2));
                    tapjoyAwardPointsNotifier.getAwardPointsResponse(nodeTrimValue3, Integer.parseInt(nodeTrimValue2));
                    return true;
                }
            } else if (nodeTrimValue == null || !nodeTrimValue.endsWith("false")) {
                TapjoyLog.e(TAG, "Invalid XML: Missing <Success> tag.");
            } else {
                String nodeTrimValue4 = TapjoyUtil.getNodeTrimValue(buildDocument.getElementsByTagName("Message"));
                TapjoyLog.i(TAG, nodeTrimValue4);
                tapjoyAwardPointsNotifier.getAwardPointsResponseFailed(nodeTrimValue4);
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public synchronized boolean handleGetPointsResponse(String str) {
        boolean z;
        Document buildDocument = TapjoyUtil.buildDocument(str);
        if (buildDocument != null) {
            String nodeTrimValue = TapjoyUtil.getNodeTrimValue(buildDocument.getElementsByTagName("Success"));
            if (nodeTrimValue == null || !nodeTrimValue.equals("true")) {
                TapjoyLog.e(TAG, "Invalid XML: Missing <Success> tag.");
            } else {
                String nodeTrimValue2 = TapjoyUtil.getNodeTrimValue(buildDocument.getElementsByTagName("TapPoints"));
                String nodeTrimValue3 = TapjoyUtil.getNodeTrimValue(buildDocument.getElementsByTagName("CurrencyName"));
                if (nodeTrimValue2 == null || nodeTrimValue3 == null) {
                    TapjoyLog.e(TAG, "Invalid XML: Missing tags.");
                } else {
                    try {
                        int parseInt = Integer.parseInt(nodeTrimValue2);
                        int localTapPointsTotal = getLocalTapPointsTotal();
                        if (!(tapjoyEarnedPointsNotifier == null || localTapPointsTotal == -9999 || parseInt <= localTapPointsTotal)) {
                            TapjoyLog.i(TAG, "earned: " + (parseInt - localTapPointsTotal));
                            tapjoyEarnedPointsNotifier.earnedTapPoints(parseInt - localTapPointsTotal);
                        }
                        saveTapPointsTotal(Integer.parseInt(nodeTrimValue2));
                        tapjoyNotifier.getUpdatePoints(nodeTrimValue3, Integer.parseInt(nodeTrimValue2));
                        z = true;
                    } catch (Exception e) {
                        TapjoyLog.e(TAG, "Error parsing XML and calling notifier: " + e.toString());
                    }
                }
            }
        }
        z = false;
        return z;
    }

    /* access modifiers changed from: private */
    public boolean handleSpendPointsResponse(String str) {
        Document buildDocument = TapjoyUtil.buildDocument(str);
        if (buildDocument != null) {
            String nodeTrimValue = TapjoyUtil.getNodeTrimValue(buildDocument.getElementsByTagName("Success"));
            if (nodeTrimValue != null && nodeTrimValue.equals("true")) {
                String nodeTrimValue2 = TapjoyUtil.getNodeTrimValue(buildDocument.getElementsByTagName("TapPoints"));
                String nodeTrimValue3 = TapjoyUtil.getNodeTrimValue(buildDocument.getElementsByTagName("CurrencyName"));
                if (nodeTrimValue2 == null || nodeTrimValue3 == null) {
                    TapjoyLog.e(TAG, "Invalid XML: Missing tags.");
                } else {
                    saveTapPointsTotal(Integer.parseInt(nodeTrimValue2));
                    tapjoySpendPointsNotifier.getSpendPointsResponse(nodeTrimValue3, Integer.parseInt(nodeTrimValue2));
                    return true;
                }
            } else if (nodeTrimValue == null || !nodeTrimValue.endsWith("false")) {
                TapjoyLog.e(TAG, "Invalid XML: Missing <Success> tag.");
            } else {
                String nodeTrimValue4 = TapjoyUtil.getNodeTrimValue(buildDocument.getElementsByTagName("Message"));
                TapjoyLog.i(TAG, nodeTrimValue4);
                tapjoySpendPointsNotifier.getSpendPointsResponseFailed(nodeTrimValue4);
                return true;
            }
        }
        return false;
    }

    public void awardTapPoints(int i, TapjoyAwardPointsNotifier tapjoyAwardPointsNotifier2) {
        if (i < 0) {
            TapjoyLog.e(TAG, "spendTapPoints error: amount must be a positive number");
            return;
        }
        this.awardTapPoints = i;
        tapjoyAwardPointsNotifier = tapjoyAwardPointsNotifier2;
        new Thread(new Runnable() {
            public void run() {
                boolean z = false;
                String uuid = UUID.randomUUID().toString();
                long currentTimeMillis = System.currentTimeMillis() / 1000;
                Map<String, String> genericURLParams = TapjoyConnectCore.getGenericURLParams();
                TapjoyUtil.safePut(genericURLParams, TapjoyConstants.TJC_TAP_POINTS, String.valueOf(TJPoints.this.awardTapPoints), true);
                TapjoyUtil.safePut(genericURLParams, TapjoyConstants.TJC_GUID, uuid, true);
                TapjoyUtil.safePut(genericURLParams, "timestamp", String.valueOf(currentTimeMillis), true);
                TapjoyUtil.safePut(genericURLParams, TapjoyConstants.TJC_VERIFIER, TapjoyConnectCore.getAwardPointsVerifier(currentTimeMillis, TJPoints.this.awardTapPoints, uuid), true);
                TapjoyHttpURLResponse responseFromURL = new TapjoyURLConnection().getResponseFromURL(TapjoyConnectCore.getHostURL() + TapjoyConstants.TJC_AWARD_POINTS_URL_PATH, genericURLParams);
                if (responseFromURL.response != null) {
                    z = TJPoints.this.handleAwardPointsResponse(responseFromURL.response);
                }
                if (!z) {
                    TJPoints.tapjoyAwardPointsNotifier.getAwardPointsResponseFailed("Failed to award points.");
                }
            }
        }).start();
    }

    public int getLocalTapPointsTotal() {
        return this.context.getSharedPreferences(TapjoyConstants.TJC_PREFERENCE, 0).getInt(TapjoyConstants.PREF_LAST_TAP_POINTS, -9999);
    }

    public void getTapPoints(TapjoyNotifier tapjoyNotifier2) {
        tapjoyNotifier = tapjoyNotifier2;
        new Thread(new Runnable() {
            public void run() {
                boolean z = false;
                TapjoyHttpURLResponse responseFromURL = new TapjoyURLConnection().getResponseFromURL(TapjoyConnectCore.getHostURL() + TapjoyConstants.TJC_USERDATA_URL_PATH, TapjoyConnectCore.getURLParams());
                if (responseFromURL.response != null) {
                    z = TJPoints.this.handleGetPointsResponse(responseFromURL.response);
                }
                if (!z) {
                    TJPoints.tapjoyNotifier.getUpdatePointsFailed("Failed to retrieve points from server");
                }
            }
        }).start();
    }

    public void saveTapPointsTotal(int i) {
        SharedPreferences.Editor edit = this.context.getSharedPreferences(TapjoyConstants.TJC_PREFERENCE, 0).edit();
        edit.putInt(TapjoyConstants.PREF_LAST_TAP_POINTS, i);
        edit.commit();
    }

    public void setEarnedPointsNotifier(TapjoyEarnedPointsNotifier tapjoyEarnedPointsNotifier2) {
        tapjoyEarnedPointsNotifier = tapjoyEarnedPointsNotifier2;
    }

    public void spendTapPoints(int i, TapjoySpendPointsNotifier tapjoySpendPointsNotifier2) {
        if (i < 0) {
            TapjoyLog.e(TAG, "spendTapPoints error: amount must be a positive number");
            return;
        }
        this.spendTapPoints = BuildConfig.FLAVOR + i;
        tapjoySpendPointsNotifier = tapjoySpendPointsNotifier2;
        new Thread(new Runnable() {
            public void run() {
                boolean z = false;
                Map<String, String> uRLParams = TapjoyConnectCore.getURLParams();
                TapjoyUtil.safePut(uRLParams, TapjoyConstants.TJC_TAP_POINTS, TJPoints.this.spendTapPoints, true);
                TapjoyHttpURLResponse responseFromURL = new TapjoyURLConnection().getResponseFromURL(TapjoyConnectCore.getHostURL() + TapjoyConstants.TJC_SPEND_POINTS_URL_PATH, uRLParams);
                if (responseFromURL.response != null) {
                    z = TJPoints.this.handleSpendPointsResponse(responseFromURL.response);
                }
                if (!z) {
                    TJPoints.tapjoySpendPointsNotifier.getSpendPointsResponseFailed("Failed to spend points.");
                }
            }
        }).start();
    }
}
