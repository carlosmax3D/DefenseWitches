package com.threatmetrix.TrustDefenderMobile;

import android.content.Context;
import android.location.Location;
import com.threatmetrix.TrustDefenderMobile.TrustDefenderMobile;
import java.util.List;

public interface TrustDefenderMobileStandard {
    void cancel();

    TrustDefenderMobile.THMStatusCode doProfileRequest(Context context, String str);

    TrustDefenderMobile.THMStatusCode doProfileRequest(Context context, String str, String str2);

    TrustDefenderMobile.THMStatusCode doProfileRequest(Context context, String str, String str2, int i);

    TrustDefenderMobile.THMStatusCode doProfileRequest(Context context, String str, String str2, String str3);

    TrustDefenderMobile.THMStatusCode doProfileRequest(Context context, String str, String str2, String str3, int i);

    TrustDefenderMobile.THMStatusCode doProfileRequest(Context context, String str, String str2, String str3, boolean z);

    String getApiKey();

    String getSessionID();

    TrustDefenderMobile.THMStatusCode getStatus();

    int getTimeout();

    void init(Context context);

    void initWithoutWebView(Context context);

    void pauseLocationServices(boolean z);

    boolean registerLocationServices(Context context);

    boolean registerLocationServices(Context context, long j, long j2, int i);

    void setApiKey(String str);

    void setCompletionNotifier(ProfileNotifyBase profileNotifyBase) throws InterruptedException;

    void setCustomAttributes(List<String> list);

    void setLocation(Location location);

    void setSessionID(String str);

    void setTimeout(int i);

    void tidyUp();
}
