package com.tapjoy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.drive.DriveFile;
import java.util.HashMap;

public class TJCOffers {
    private static final String TAG = "TapjoyOffers";
    private static TapjoyOffersNotifier tapjoyOffersNotifier;
    Context context;

    public TJCOffers(Context context2) {
        this.context = context2;
    }

    public static void getOffersNotifierResponse() {
        if (tapjoyOffersNotifier != null) {
            tapjoyOffersNotifier.getOffersResponse();
        }
    }

    public static void getOffersNotifierResponseFailed(String str) {
        if (tapjoyOffersNotifier != null) {
            tapjoyOffersNotifier.getOffersResponseFailed(str);
        }
    }

    public void showOffers(TapjoyOffersNotifier tapjoyOffersNotifier2) {
        showOffersWithCurrencyID((String) null, false, tapjoyOffersNotifier2);
    }

    public void showOffersWithCurrencyID(String str, boolean z, TJEventData tJEventData, String str2, TapjoyOffersNotifier tapjoyOffersNotifier2) {
        if (!TapjoyConnectCore.isViewOpen()) {
            TapjoyConnectCore.viewWillOpen(0);
            tapjoyOffersNotifier = tapjoyOffersNotifier2;
            String str3 = z ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO;
            HashMap hashMap = new HashMap(TapjoyConnectCore.getURLParams());
            TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_CURRENCY_ID, str, true);
            TapjoyUtil.safePut(hashMap, TapjoyConstants.TJC_MULTIPLE_CURRENCY_SELECTOR_FLAG, str3, true);
            Intent intent = new Intent(this.context, TJCOffersWebView.class);
            if (tJEventData != null) {
                TapjoyLog.i(TAG, "showOffers for eventName: " + tJEventData.name);
            }
            if (str2 != null && str2.length() > 0) {
                intent.putExtra(TJAdUnitConstants.EXTRA_CALLBACK_ID, str2);
            }
            intent.putExtra(TJAdUnitConstants.EXTRA_VIEW_TYPE, 2);
            intent.putExtra(TJAdUnitConstants.EXTRA_TJEVENT, tJEventData);
            intent.putExtra(TJAdUnitConstants.EXTRA_LEGACY_VIEW, true);
            intent.putExtra(TapjoyConstants.EXTRA_URL_PARAMS, hashMap);
            if (this.context instanceof Activity) {
                ((Activity) this.context).startActivityForResult(intent, 0);
                return;
            }
            intent.setFlags(DriveFile.MODE_READ_ONLY);
            this.context.startActivity(intent);
        }
    }

    public void showOffersWithCurrencyID(String str, boolean z, TapjoyOffersNotifier tapjoyOffersNotifier2) {
        showOffersWithCurrencyID(str, z, (TJEventData) null, (String) null, tapjoyOffersNotifier2);
    }
}
