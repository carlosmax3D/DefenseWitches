package jp.newgate.game.android.dw.util;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.android.vending.billing.IInAppBillingService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jp.stargarage.g2metrics.BuildConfig;
import org.json.JSONException;

public class IabHelper {
    public static final int BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE = 3;
    public static final int BILLING_RESPONSE_RESULT_DEVELOPER_ERROR = 5;
    public static final int BILLING_RESPONSE_RESULT_ERROR = 6;
    public static final int BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED = 7;
    public static final int BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED = 8;
    public static final int BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE = 4;
    public static final int BILLING_RESPONSE_RESULT_OK = 0;
    public static final int BILLING_RESPONSE_RESULT_USER_CANCELED = 1;
    public static final String GET_SKU_DETAILS_ITEM_LIST = "ITEM_ID_LIST";
    public static final String GET_SKU_DETAILS_ITEM_TYPE_LIST = "ITEM_TYPE_LIST";
    public static final int IABHELPER_BAD_RESPONSE = -1002;
    public static final int IABHELPER_ERROR_BASE = -1000;
    public static final int IABHELPER_INVALID_CONSUMPTION = -1010;
    public static final int IABHELPER_MISSING_TOKEN = -1007;
    public static final int IABHELPER_REMOTE_EXCEPTION = -1001;
    public static final int IABHELPER_SEND_INTENT_FAILED = -1004;
    public static final int IABHELPER_SUBSCRIPTIONS_NOT_AVAILABLE = -1009;
    public static final int IABHELPER_UNKNOWN_ERROR = -1008;
    public static final int IABHELPER_UNKNOWN_PURCHASE_RESPONSE = -1006;
    public static final int IABHELPER_USER_CANCELLED = -1005;
    public static final int IABHELPER_VERIFICATION_FAILED = -1003;
    public static final String INAPP_CONTINUATION_TOKEN = "INAPP_CONTINUATION_TOKEN";
    public static final String ITEM_TYPE_INAPP = "inapp";
    public static final String ITEM_TYPE_SUBS = "subs";
    public static final String RESPONSE_BUY_INTENT = "BUY_INTENT";
    public static final String RESPONSE_CODE = "RESPONSE_CODE";
    public static final String RESPONSE_GET_SKU_DETAILS_LIST = "DETAILS_LIST";
    public static final String RESPONSE_INAPP_ITEM_LIST = "INAPP_PURCHASE_ITEM_LIST";
    public static final String RESPONSE_INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";
    public static final String RESPONSE_INAPP_PURCHASE_DATA_LIST = "INAPP_PURCHASE_DATA_LIST";
    public static final String RESPONSE_INAPP_SIGNATURE = "INAPP_DATA_SIGNATURE";
    public static final String RESPONSE_INAPP_SIGNATURE_LIST = "INAPP_DATA_SIGNATURE_LIST";
    boolean mAsyncInProgress = false;
    String mAsyncOperation = BuildConfig.FLAVOR;
    Context mContext;
    boolean mDebugLog = false;
    String mDebugTag = "IabHelper";
    boolean mDisposed = false;
    OnIabPurchaseFinishedListener mPurchaseListener;
    String mPurchasingItemType;
    int mRequestCode;
    IInAppBillingService mService;
    ServiceConnection mServiceConn;
    boolean mSetupDone = false;
    String mSignatureBase64 = null;
    boolean mSubscriptionsSupported = false;

    public interface OnConsumeFinishedListener {
        void onConsumeFinished(Purchase purchase, IabResult iabResult);
    }

    public interface OnConsumeMultiFinishedListener {
        void onConsumeMultiFinished(List<Purchase> list, List<IabResult> list2);
    }

    public interface OnIabPurchaseFinishedListener {
        void onIabPurchaseFinished(IabResult iabResult, Purchase purchase);
    }

    public interface OnIabSetupFinishedListener {
        void onIabSetupFinished(IabResult iabResult);
    }

    public interface QueryInventoryFinishedListener {
        void onQueryInventoryFinished(IabResult iabResult, Inventory inventory);
    }

    public IabHelper(Context context, String str) {
        this.mContext = context.getApplicationContext();
        this.mSignatureBase64 = str;
        logDebug("IAB helper created.");
    }

    private void checkNotDisposed() {
        if (this.mDisposed) {
            throw new IllegalStateException("IabHelper was disposed of, so it cannot be used.");
        }
    }

    public static String getResponseDesc(int i) {
        String[] split = "0:OK/1:User Canceled/2:Unknown/3:Billing Unavailable/4:Item unavailable/5:Developer Error/6:Error/7:Item Already Owned/8:Item not owned".split("/");
        String[] split2 = "0:OK/-1001:Remote exception during initialization/-1002:Bad response received/-1003:Purchase signature verification failed/-1004:Send intent failed/-1005:User cancelled/-1006:Unknown purchase response/-1007:Missing token/-1008:Unknown error/-1009:Subscriptions not available/-1010:Invalid consumption attempt".split("/");
        if (i > -1000) {
            return (i < 0 || i >= split.length) ? String.valueOf(i) + ":Unknown" : split[i];
        }
        int i2 = -1000 - i;
        return (i2 < 0 || i2 >= split2.length) ? String.valueOf(i) + ":Unknown IAB Helper Error" : split2[i2];
    }

    /* access modifiers changed from: package-private */
    public void checkSetupDone(String str) {
        if (!this.mSetupDone) {
            logError("Illegal state for operation (" + str + "): IAB helper is not set up.");
            throw new IllegalStateException("IAB helper is not set up. Can't perform operation: " + str);
        }
    }

    /* access modifiers changed from: package-private */
    public void consume(Purchase purchase) throws IabException {
        checkNotDisposed();
        checkSetupDone("consume");
        if (!purchase.mItemType.equals("inapp")) {
            throw new IabException(-1010, "Items of type '" + purchase.mItemType + "' can't be consumed.");
        }
        try {
            String token = purchase.getToken();
            String sku = purchase.getSku();
            if (token == null || token.equals(BuildConfig.FLAVOR)) {
                logError("Can't consume " + sku + ". No token.");
                throw new IabException(-1007, "PurchaseInfo is missing token for sku: " + sku + " " + purchase);
            }
            logDebug("Consuming sku: " + sku + ", token: " + token);
            int consumePurchase = this.mService.consumePurchase(3, this.mContext.getPackageName(), token);
            if (consumePurchase == 0) {
                logDebug("Successfully consumed sku: " + sku);
            } else {
                logDebug("Error consuming consuming sku " + sku + ". " + getResponseDesc(consumePurchase));
                throw new IabException(consumePurchase, "Error consuming sku " + sku);
            }
        } catch (RemoteException e) {
            throw new IabException(-1001, "Remote exception while consuming. PurchaseInfo: " + purchase, e);
        }
    }

    public void consumeAsync(List<Purchase> list, OnConsumeMultiFinishedListener onConsumeMultiFinishedListener) {
        checkNotDisposed();
        checkSetupDone("consume");
        consumeAsyncInternal(list, (OnConsumeFinishedListener) null, onConsumeMultiFinishedListener);
    }

    public void consumeAsync(Purchase purchase, OnConsumeFinishedListener onConsumeFinishedListener) {
        checkNotDisposed();
        checkSetupDone("consume");
        ArrayList arrayList = new ArrayList();
        arrayList.add(purchase);
        consumeAsyncInternal(arrayList, onConsumeFinishedListener, (OnConsumeMultiFinishedListener) null);
    }

    /* access modifiers changed from: package-private */
    public void consumeAsyncInternal(List<Purchase> list, OnConsumeFinishedListener onConsumeFinishedListener, OnConsumeMultiFinishedListener onConsumeMultiFinishedListener) {
        final Handler handler = new Handler();
        flagStartAsync("consume");
        final List<Purchase> list2 = list;
        final OnConsumeFinishedListener onConsumeFinishedListener2 = onConsumeFinishedListener;
        final OnConsumeMultiFinishedListener onConsumeMultiFinishedListener2 = onConsumeMultiFinishedListener;
        new Thread(new Runnable() {
            public void run() {
                final ArrayList arrayList = new ArrayList();
                for (Purchase purchase : list2) {
                    try {
                        IabHelper.this.consume(purchase);
                        arrayList.add(new IabResult(0, "Successful consume of sku " + purchase.getSku()));
                    } catch (IabException e) {
                        arrayList.add(e.getResult());
                    }
                }
                IabHelper.this.flagEndAsync();
                if (!IabHelper.this.mDisposed && onConsumeFinishedListener2 != null) {
                    handler.post(new Runnable() {
                        public void run() {
                            onConsumeFinishedListener2.onConsumeFinished((Purchase) list2.get(0), (IabResult) arrayList.get(0));
                        }
                    });
                }
                if (!IabHelper.this.mDisposed && onConsumeMultiFinishedListener2 != null) {
                    handler.post(new Runnable() {
                        public void run() {
                            onConsumeMultiFinishedListener2.onConsumeMultiFinished(list2, arrayList);
                        }
                    });
                }
            }
        }).start();
    }

    public void dispose() {
        logDebug("Disposing.");
        this.mSetupDone = false;
        if (this.mServiceConn != null) {
            logDebug("Unbinding from service.");
            if (this.mContext != null) {
                this.mContext.unbindService(this.mServiceConn);
            }
        }
        this.mDisposed = true;
        this.mContext = null;
        this.mServiceConn = null;
        this.mService = null;
        this.mPurchaseListener = null;
    }

    public void enableDebugLogging(boolean z) {
        checkNotDisposed();
        this.mDebugLog = z;
    }

    public void enableDebugLogging(boolean z, String str) {
        checkNotDisposed();
        this.mDebugLog = z;
        this.mDebugTag = str;
    }

    /* access modifiers changed from: package-private */
    public void flagEndAsync() {
        logDebug("Ending async operation: " + this.mAsyncOperation);
        this.mAsyncOperation = BuildConfig.FLAVOR;
        this.mAsyncInProgress = false;
    }

    /* access modifiers changed from: package-private */
    public void flagStartAsync(String str) {
        if (this.mAsyncInProgress) {
            throw new IllegalStateException("Can't start async operation (" + str + ") because another async operation(" + this.mAsyncOperation + ") is in progress.");
        }
        this.mAsyncOperation = str;
        this.mAsyncInProgress = true;
        logDebug("Starting async operation: " + str);
    }

    /* access modifiers changed from: package-private */
    public int getResponseCodeFromBundle(Bundle bundle) {
        Object obj = bundle.get("RESPONSE_CODE");
        if (obj == null) {
            logDebug("Bundle with null response code, assuming OK (known issue)");
            return 0;
        } else if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        } else {
            if (obj instanceof Long) {
                return (int) ((Long) obj).longValue();
            }
            logError("Unexpected type for bundle response code.");
            logError(obj.getClass().getName());
            throw new RuntimeException("Unexpected type for bundle response code: " + obj.getClass().getName());
        }
    }

    /* access modifiers changed from: package-private */
    public int getResponseCodeFromIntent(Intent intent) {
        Object obj = intent.getExtras().get("RESPONSE_CODE");
        if (obj == null) {
            logError("Intent with no response code, assuming OK (known issue)");
            return 0;
        } else if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        } else {
            if (obj instanceof Long) {
                return (int) ((Long) obj).longValue();
            }
            logError("Unexpected type for intent response code.");
            logError(obj.getClass().getName());
            throw new RuntimeException("Unexpected type for intent response code: " + obj.getClass().getName());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0161  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean handleActivityResult(int r13, int r14, android.content.Intent r15) {
        /*
            r12 = this;
            int r8 = r12.mRequestCode
            if (r13 == r8) goto L_0x0006
            r8 = 0
        L_0x0005:
            return r8
        L_0x0006:
            r12.checkNotDisposed()
            java.lang.String r8 = "handleActivityResult"
            r12.checkSetupDone(r8)
            r12.flagEndAsync()
            if (r15 != 0) goto L_0x002d
            java.lang.String r8 = "Null data in IAB activity result."
            r12.logError(r8)
            jp.newgate.game.android.dw.util.IabResult r6 = new jp.newgate.game.android.dw.util.IabResult
            r8 = -1002(0xfffffffffffffc16, float:NaN)
            java.lang.String r9 = "Null data in IAB result"
            r6.<init>(r8, r9)
            jp.newgate.game.android.dw.util.IabHelper$OnIabPurchaseFinishedListener r8 = r12.mPurchaseListener
            if (r8 == 0) goto L_0x002b
            jp.newgate.game.android.dw.util.IabHelper$OnIabPurchaseFinishedListener r8 = r12.mPurchaseListener
            r9 = 0
            r8.onIabPurchaseFinished(r6, r9)
        L_0x002b:
            r8 = 1
            goto L_0x0005
        L_0x002d:
            int r5 = r12.getResponseCodeFromIntent(r15)
            java.lang.String r8 = "INAPP_PURCHASE_DATA"
            java.lang.String r4 = r15.getStringExtra(r8)
            java.lang.String r8 = "INAPP_DATA_SIGNATURE"
            java.lang.String r0 = r15.getStringExtra(r8)
            r8 = -1
            if (r14 != r8) goto L_0x016a
            if (r5 != 0) goto L_0x016a
            java.lang.String r8 = "Successful resultcode from purchase activity."
            r12.logDebug(r8)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Purchase data: "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r8 = r8.append(r4)
            java.lang.String r8 = r8.toString()
            r12.logDebug(r8)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Data signature: "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r8 = r8.append(r0)
            java.lang.String r8 = r8.toString()
            r12.logDebug(r8)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Extras: "
            java.lang.StringBuilder r8 = r8.append(r9)
            android.os.Bundle r9 = r15.getExtras()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r12.logDebug(r8)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Expected item type: "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = r12.mPurchasingItemType
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r12.logDebug(r8)
            if (r4 == 0) goto L_0x00a9
            if (r0 != 0) goto L_0x00e2
        L_0x00a9:
            java.lang.String r8 = "BUG: either purchaseData or dataSignature is null."
            r12.logError(r8)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Extras: "
            java.lang.StringBuilder r8 = r8.append(r9)
            android.os.Bundle r9 = r15.getExtras()
            java.lang.String r9 = r9.toString()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r12.logDebug(r8)
            jp.newgate.game.android.dw.util.IabResult r6 = new jp.newgate.game.android.dw.util.IabResult
            r8 = -1008(0xfffffffffffffc10, float:NaN)
            java.lang.String r9 = "IAB returned null purchaseData or dataSignature"
            r6.<init>(r8, r9)
            jp.newgate.game.android.dw.util.IabHelper$OnIabPurchaseFinishedListener r8 = r12.mPurchaseListener
            if (r8 == 0) goto L_0x00df
            jp.newgate.game.android.dw.util.IabHelper$OnIabPurchaseFinishedListener r8 = r12.mPurchaseListener
            r9 = 0
            r8.onIabPurchaseFinished(r6, r9)
        L_0x00df:
            r8 = 1
            goto L_0x0005
        L_0x00e2:
            r2 = 0
            jp.newgate.game.android.dw.util.Purchase r3 = new jp.newgate.game.android.dw.util.Purchase     // Catch:{ JSONException -> 0x014b }
            java.lang.String r8 = r12.mPurchasingItemType     // Catch:{ JSONException -> 0x014b }
            r3.<init>(r8, r4, r0)     // Catch:{ JSONException -> 0x014b }
            java.lang.String r7 = r3.getSku()     // Catch:{ JSONException -> 0x0206 }
            java.lang.String r8 = r12.mSignatureBase64     // Catch:{ JSONException -> 0x0206 }
            boolean r8 = jp.newgate.game.android.dw.util.Security.verifyPurchase(r8, r4, r0)     // Catch:{ JSONException -> 0x0206 }
            if (r8 != 0) goto L_0x0132
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0206 }
            r8.<init>()     // Catch:{ JSONException -> 0x0206 }
            java.lang.String r9 = "Purchase signature verification FAILED for sku "
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ JSONException -> 0x0206 }
            java.lang.StringBuilder r8 = r8.append(r7)     // Catch:{ JSONException -> 0x0206 }
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x0206 }
            r12.logError(r8)     // Catch:{ JSONException -> 0x0206 }
            jp.newgate.game.android.dw.util.IabResult r6 = new jp.newgate.game.android.dw.util.IabResult     // Catch:{ JSONException -> 0x0206 }
            r8 = -1003(0xfffffffffffffc15, float:NaN)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0206 }
            r9.<init>()     // Catch:{ JSONException -> 0x0206 }
            java.lang.String r10 = "Signature verification failed for sku "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ JSONException -> 0x0206 }
            java.lang.StringBuilder r9 = r9.append(r7)     // Catch:{ JSONException -> 0x0206 }
            java.lang.String r9 = r9.toString()     // Catch:{ JSONException -> 0x0206 }
            r6.<init>(r8, r9)     // Catch:{ JSONException -> 0x0206 }
            jp.newgate.game.android.dw.util.IabHelper$OnIabPurchaseFinishedListener r8 = r12.mPurchaseListener     // Catch:{ JSONException -> 0x0206 }
            if (r8 == 0) goto L_0x012f
            jp.newgate.game.android.dw.util.IabHelper$OnIabPurchaseFinishedListener r8 = r12.mPurchaseListener     // Catch:{ JSONException -> 0x0206 }
            r8.onIabPurchaseFinished(r6, r3)     // Catch:{ JSONException -> 0x0206 }
        L_0x012f:
            r8 = 1
            goto L_0x0005
        L_0x0132:
            java.lang.String r8 = "Purchase signature successfully verified."
            r12.logDebug(r8)     // Catch:{ JSONException -> 0x0206 }
            jp.newgate.game.android.dw.util.IabHelper$OnIabPurchaseFinishedListener r8 = r12.mPurchaseListener
            if (r8 == 0) goto L_0x0148
            jp.newgate.game.android.dw.util.IabHelper$OnIabPurchaseFinishedListener r8 = r12.mPurchaseListener
            jp.newgate.game.android.dw.util.IabResult r9 = new jp.newgate.game.android.dw.util.IabResult
            r10 = 0
            java.lang.String r11 = "Success"
            r9.<init>(r10, r11)
            r8.onIabPurchaseFinished(r9, r3)
        L_0x0148:
            r8 = 1
            goto L_0x0005
        L_0x014b:
            r1 = move-exception
        L_0x014c:
            java.lang.String r8 = "Failed to parse purchase data."
            r12.logError(r8)
            r1.printStackTrace()
            jp.newgate.game.android.dw.util.IabResult r6 = new jp.newgate.game.android.dw.util.IabResult
            r8 = -1002(0xfffffffffffffc16, float:NaN)
            java.lang.String r9 = "Failed to parse purchase data."
            r6.<init>(r8, r9)
            jp.newgate.game.android.dw.util.IabHelper$OnIabPurchaseFinishedListener r8 = r12.mPurchaseListener
            if (r8 == 0) goto L_0x0167
            jp.newgate.game.android.dw.util.IabHelper$OnIabPurchaseFinishedListener r8 = r12.mPurchaseListener
            r9 = 0
            r8.onIabPurchaseFinished(r6, r9)
        L_0x0167:
            r8 = 1
            goto L_0x0005
        L_0x016a:
            r8 = -1
            if (r14 != r8) goto L_0x0199
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Result code was OK but in-app billing response was not OK: "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = getResponseDesc(r5)
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r12.logDebug(r8)
            jp.newgate.game.android.dw.util.IabHelper$OnIabPurchaseFinishedListener r8 = r12.mPurchaseListener
            if (r8 == 0) goto L_0x0148
            jp.newgate.game.android.dw.util.IabResult r6 = new jp.newgate.game.android.dw.util.IabResult
            java.lang.String r8 = "Problem purchashing item."
            r6.<init>(r5, r8)
            jp.newgate.game.android.dw.util.IabHelper$OnIabPurchaseFinishedListener r8 = r12.mPurchaseListener
            r9 = 0
            r8.onIabPurchaseFinished(r6, r9)
            goto L_0x0148
        L_0x0199:
            if (r14 != 0) goto L_0x01c9
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Purchase canceled - Response: "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = getResponseDesc(r5)
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r12.logDebug(r8)
            jp.newgate.game.android.dw.util.IabResult r6 = new jp.newgate.game.android.dw.util.IabResult
            r8 = -1005(0xfffffffffffffc13, float:NaN)
            java.lang.String r9 = "User canceled."
            r6.<init>(r8, r9)
            jp.newgate.game.android.dw.util.IabHelper$OnIabPurchaseFinishedListener r8 = r12.mPurchaseListener
            if (r8 == 0) goto L_0x0148
            jp.newgate.game.android.dw.util.IabHelper$OnIabPurchaseFinishedListener r8 = r12.mPurchaseListener
            r9 = 0
            r8.onIabPurchaseFinished(r6, r9)
            goto L_0x0148
        L_0x01c9:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Purchase failed. Result code: "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = java.lang.Integer.toString(r14)
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = ". Response: "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = getResponseDesc(r5)
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r12.logError(r8)
            jp.newgate.game.android.dw.util.IabResult r6 = new jp.newgate.game.android.dw.util.IabResult
            r8 = -1006(0xfffffffffffffc12, float:NaN)
            java.lang.String r9 = "Unknown purchase response."
            r6.<init>(r8, r9)
            jp.newgate.game.android.dw.util.IabHelper$OnIabPurchaseFinishedListener r8 = r12.mPurchaseListener
            if (r8 == 0) goto L_0x0148
            jp.newgate.game.android.dw.util.IabHelper$OnIabPurchaseFinishedListener r8 = r12.mPurchaseListener
            r9 = 0
            r8.onIabPurchaseFinished(r6, r9)
            goto L_0x0148
        L_0x0206:
            r1 = move-exception
            r2 = r3
            goto L_0x014c
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.newgate.game.android.dw.util.IabHelper.handleActivityResult(int, int, android.content.Intent):boolean");
    }

    public void launchPurchaseFlow(Activity activity, String str, int i, OnIabPurchaseFinishedListener onIabPurchaseFinishedListener) {
        launchPurchaseFlow(activity, str, i, onIabPurchaseFinishedListener, BuildConfig.FLAVOR);
    }

    public void launchPurchaseFlow(Activity activity, String str, int i, OnIabPurchaseFinishedListener onIabPurchaseFinishedListener, String str2) {
        launchPurchaseFlow(activity, str, "inapp", i, onIabPurchaseFinishedListener, str2);
    }

    public void launchPurchaseFlow(Activity activity, String str, String str2, int i, OnIabPurchaseFinishedListener onIabPurchaseFinishedListener, String str3) {
        checkNotDisposed();
        checkSetupDone("launchPurchaseFlow");
        flagStartAsync("launchPurchaseFlow");
        if (!str2.equals("subs") || this.mSubscriptionsSupported) {
            try {
                logDebug("Constructing buy intent for " + str + ", item type: " + str2);
                Bundle buyIntent = this.mService.getBuyIntent(3, this.mContext.getPackageName(), str, str2, str3);
                int responseCodeFromBundle = getResponseCodeFromBundle(buyIntent);
                if (responseCodeFromBundle != 0) {
                    logError("Unable to buy item, Error response: " + getResponseDesc(responseCodeFromBundle));
                    flagEndAsync();
                    IabResult iabResult = new IabResult(responseCodeFromBundle, "Unable to buy item");
                    if (onIabPurchaseFinishedListener != null) {
                        onIabPurchaseFinishedListener.onIabPurchaseFinished(iabResult, (Purchase) null);
                        return;
                    }
                    return;
                }
                logDebug("Launching buy intent for " + str + ". Request code: " + i);
                this.mRequestCode = i;
                this.mPurchaseListener = onIabPurchaseFinishedListener;
                this.mPurchasingItemType = str2;
                IntentSender intentSender = ((PendingIntent) buyIntent.getParcelable("BUY_INTENT")).getIntentSender();
                Intent intent = new Intent();
                Integer num = 0;
                int intValue = num.intValue();
                Integer num2 = 0;
                int intValue2 = num2.intValue();
                Integer num3 = 0;
                activity.startIntentSenderForResult(intentSender, i, intent, intValue, intValue2, num3.intValue());
            } catch (IntentSender.SendIntentException e) {
                logError("SendIntentException while launching purchase flow for sku " + str);
                e.printStackTrace();
                flagEndAsync();
                IabResult iabResult2 = new IabResult(-1004, "Failed to send intent.");
                if (onIabPurchaseFinishedListener != null) {
                    onIabPurchaseFinishedListener.onIabPurchaseFinished(iabResult2, (Purchase) null);
                }
            } catch (RemoteException e2) {
                logError("RemoteException while launching purchase flow for sku " + str);
                e2.printStackTrace();
                flagEndAsync();
                IabResult iabResult3 = new IabResult(-1001, "Remote exception while starting purchase flow");
                if (onIabPurchaseFinishedListener != null) {
                    onIabPurchaseFinishedListener.onIabPurchaseFinished(iabResult3, (Purchase) null);
                }
            }
        } else {
            IabResult iabResult4 = new IabResult(-1009, "Subscriptions are not available.");
            flagEndAsync();
            if (onIabPurchaseFinishedListener != null) {
                onIabPurchaseFinishedListener.onIabPurchaseFinished(iabResult4, (Purchase) null);
            }
        }
    }

    public void launchSubscriptionPurchaseFlow(Activity activity, String str, int i, OnIabPurchaseFinishedListener onIabPurchaseFinishedListener) {
        launchSubscriptionPurchaseFlow(activity, str, i, onIabPurchaseFinishedListener, BuildConfig.FLAVOR);
    }

    public void launchSubscriptionPurchaseFlow(Activity activity, String str, int i, OnIabPurchaseFinishedListener onIabPurchaseFinishedListener, String str2) {
        launchPurchaseFlow(activity, str, "subs", i, onIabPurchaseFinishedListener, str2);
    }

    /* access modifiers changed from: package-private */
    public void logDebug(String str) {
        if (this.mDebugLog) {
            Log.d(this.mDebugTag, str);
        }
    }

    /* access modifiers changed from: package-private */
    public void logError(String str) {
        Log.e(this.mDebugTag, "In-app billing error: " + str);
    }

    /* access modifiers changed from: package-private */
    public void logWarn(String str) {
        Log.w(this.mDebugTag, "In-app billing warning: " + str);
    }

    public Inventory queryInventory(boolean z, List<String> list) throws IabException {
        return queryInventory(z, list, (List<String>) null);
    }

    public Inventory queryInventory(boolean z, List<String> list, List<String> list2) throws IabException {
        int querySkuDetails;
        checkNotDisposed();
        checkSetupDone("queryInventory");
        try {
            Inventory inventory = new Inventory();
            int queryPurchases = queryPurchases(inventory, "inapp");
            if (queryPurchases != 0) {
                throw new IabException(queryPurchases, "Error refreshing inventory (querying owned items).");
            }
            if (z) {
                int querySkuDetails2 = querySkuDetails("inapp", inventory, list);
                if (querySkuDetails2 != 0) {
                    throw new IabException(querySkuDetails2, "Error refreshing inventory (querying prices of items).");
                }
            }
            if (this.mSubscriptionsSupported) {
                int queryPurchases2 = queryPurchases(inventory, "subs");
                if (queryPurchases2 != 0) {
                    throw new IabException(queryPurchases2, "Error refreshing inventory (querying owned subscriptions).");
                } else if (z && (querySkuDetails = querySkuDetails("subs", inventory, list)) != 0) {
                    throw new IabException(querySkuDetails, "Error refreshing inventory (querying prices of subscriptions).");
                }
            }
            return inventory;
        } catch (RemoteException e) {
            throw new IabException(-1001, "Remote exception while refreshing inventory.", e);
        } catch (JSONException e2) {
            throw new IabException(-1002, "Error parsing JSON response while refreshing inventory.", e2);
        }
    }

    public void queryInventoryAsync(QueryInventoryFinishedListener queryInventoryFinishedListener) {
        queryInventoryAsync(true, (List<String>) null, queryInventoryFinishedListener);
    }

    public void queryInventoryAsync(boolean z, List<String> list, QueryInventoryFinishedListener queryInventoryFinishedListener) {
        final Handler handler = new Handler();
        checkNotDisposed();
        checkSetupDone("queryInventory");
        flagStartAsync("refresh inventory");
        final boolean z2 = z;
        final List<String> list2 = list;
        final QueryInventoryFinishedListener queryInventoryFinishedListener2 = queryInventoryFinishedListener;
        new Thread(new Runnable() {
            public void run() {
                IabResult iabResult = new IabResult(0, "Inventory refresh successful.");
                Inventory inventory = null;
                try {
                    inventory = IabHelper.this.queryInventory(z2, list2);
                } catch (IabException e) {
                    iabResult = e.getResult();
                }
                IabHelper.this.flagEndAsync();
                final IabResult iabResult2 = iabResult;
                final Inventory inventory2 = inventory;
                if (!IabHelper.this.mDisposed && queryInventoryFinishedListener2 != null) {
                    handler.post(new Runnable() {
                        public void run() {
                            queryInventoryFinishedListener2.onQueryInventoryFinished(iabResult2, inventory2);
                        }
                    });
                }
            }
        }).start();
    }

    public void queryInventoryAsync(boolean z, QueryInventoryFinishedListener queryInventoryFinishedListener) {
        queryInventoryAsync(z, (List<String>) null, queryInventoryFinishedListener);
    }

    /* access modifiers changed from: package-private */
    public int queryPurchases(Inventory inventory, String str) throws JSONException, RemoteException {
        logDebug("Querying owned items, item type: " + str);
        logDebug("Package name: " + this.mContext.getPackageName());
        boolean z = false;
        String str2 = null;
        do {
            logDebug("Calling getPurchases with continuation token: " + str2);
            Bundle purchases = this.mService.getPurchases(3, this.mContext.getPackageName(), str, str2);
            int responseCodeFromBundle = getResponseCodeFromBundle(purchases);
            logDebug("Owned items response: " + String.valueOf(responseCodeFromBundle));
            if (responseCodeFromBundle != 0) {
                logDebug("getPurchases() failed: " + getResponseDesc(responseCodeFromBundle));
                return responseCodeFromBundle;
            } else if (!purchases.containsKey("INAPP_PURCHASE_ITEM_LIST") || !purchases.containsKey("INAPP_PURCHASE_DATA_LIST") || !purchases.containsKey("INAPP_DATA_SIGNATURE_LIST")) {
                logError("Bundle returned from getPurchases() doesn't contain required fields.");
                return -1002;
            } else {
                ArrayList<String> stringArrayList = purchases.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                ArrayList<String> stringArrayList2 = purchases.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList<String> stringArrayList3 = purchases.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                for (int i = 0; i < stringArrayList2.size(); i++) {
                    String str3 = stringArrayList2.get(i);
                    String str4 = stringArrayList3.get(i);
                    String str5 = stringArrayList.get(i);
                    if (Security.verifyPurchase(this.mSignatureBase64, str3, str4)) {
                        logDebug("Sku is owned: " + str5);
                        Purchase purchase = new Purchase(str, str3, str4);
                        if (TextUtils.isEmpty(purchase.getToken())) {
                            logWarn("BUG: empty/null token!");
                            logDebug("Purchase data: " + str3);
                        }
                        inventory.addPurchase(purchase);
                    } else {
                        logWarn("Purchase signature verification **FAILED**. Not adding item.");
                        logDebug("   Purchase data: " + str3);
                        logDebug("   Signature: " + str4);
                        z = true;
                    }
                }
                str2 = purchases.getString("INAPP_CONTINUATION_TOKEN");
                logDebug("Continuation token: " + str2);
            }
        } while (!TextUtils.isEmpty(str2));
        return z ? -1003 : 0;
    }

    /* access modifiers changed from: package-private */
    public int querySkuDetails(String str, Inventory inventory, List<String> list) throws RemoteException, JSONException {
        logDebug("Querying SKU details.");
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(inventory.getAllOwnedSkus(str));
        if (list != null) {
            for (String next : list) {
                if (!arrayList.contains(next)) {
                    arrayList.add(next);
                }
            }
        }
        if (arrayList.size() == 0) {
            logDebug("queryPrices: nothing to do because there are no SKUs.");
            return 0;
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("ITEM_ID_LIST", arrayList);
        Bundle skuDetails = this.mService.getSkuDetails(3, this.mContext.getPackageName(), str, bundle);
        if (!skuDetails.containsKey("DETAILS_LIST")) {
            int responseCodeFromBundle = getResponseCodeFromBundle(skuDetails);
            if (responseCodeFromBundle != 0) {
                logDebug("getSkuDetails() failed: " + getResponseDesc(responseCodeFromBundle));
                return responseCodeFromBundle;
            }
            logError("getSkuDetails() returned a bundle with neither an error nor a detail list.");
            return -1002;
        }
        Iterator<String> it = skuDetails.getStringArrayList("DETAILS_LIST").iterator();
        while (it.hasNext()) {
            SkuDetails skuDetails2 = new SkuDetails(str, it.next());
            logDebug("Got sku details: " + skuDetails2);
            inventory.addSkuDetails(skuDetails2);
        }
        return 0;
    }

    public void startSetup(final OnIabSetupFinishedListener onIabSetupFinishedListener) {
        checkNotDisposed();
        if (this.mSetupDone) {
            throw new IllegalStateException("IAB helper is already set up.");
        }
        logDebug("Starting in-app billing setup.");
        this.mServiceConn = new ServiceConnection() {
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                if (!IabHelper.this.mDisposed) {
                    IabHelper.this.logDebug("Billing service connected.");
                    IabHelper.this.mService = IInAppBillingService.Stub.asInterface(iBinder);
                    String packageName = IabHelper.this.mContext.getPackageName();
                    try {
                        IabHelper.this.logDebug("Checking for in-app billing 3 support.");
                        int isBillingSupported = IabHelper.this.mService.isBillingSupported(3, packageName, "inapp");
                        if (isBillingSupported != 0) {
                            if (onIabSetupFinishedListener != null) {
                                onIabSetupFinishedListener.onIabSetupFinished(new IabResult(isBillingSupported, "Error checking for billing v3 support."));
                            }
                            IabHelper.this.mSubscriptionsSupported = false;
                            return;
                        }
                        IabHelper.this.logDebug("In-app billing version 3 supported for " + packageName);
                        int isBillingSupported2 = IabHelper.this.mService.isBillingSupported(3, packageName, "subs");
                        if (isBillingSupported2 == 0) {
                            IabHelper.this.logDebug("Subscriptions AVAILABLE.");
                            IabHelper.this.mSubscriptionsSupported = true;
                        } else {
                            IabHelper.this.logDebug("Subscriptions NOT AVAILABLE. Response: " + isBillingSupported2);
                        }
                        IabHelper.this.mSetupDone = true;
                        if (onIabSetupFinishedListener != null) {
                            onIabSetupFinishedListener.onIabSetupFinished(new IabResult(0, "Setup successful."));
                        }
                    } catch (RemoteException e) {
                        if (onIabSetupFinishedListener != null) {
                            onIabSetupFinishedListener.onIabSetupFinished(new IabResult(-1001, "RemoteException while setting up in-app billing."));
                        }
                        e.printStackTrace();
                    }
                }
            }

            public void onServiceDisconnected(ComponentName componentName) {
                IabHelper.this.logDebug("Billing service disconnected.");
                IabHelper.this.mService = null;
            }
        };
        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        if (!this.mContext.getPackageManager().queryIntentServices(intent, 0).isEmpty()) {
            this.mContext.bindService(intent, this.mServiceConn, 1);
        } else if (onIabSetupFinishedListener != null) {
            onIabSetupFinishedListener.onIabSetupFinished(new IabResult(3, "Billing service unavailable on device."));
        }
    }

    public boolean subscriptionsSupported() {
        checkNotDisposed();
        return this.mSubscriptionsSupported;
    }
}
