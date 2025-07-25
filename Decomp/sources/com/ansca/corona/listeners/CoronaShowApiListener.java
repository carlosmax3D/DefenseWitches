package com.ansca.corona.listeners;

import com.ansca.corona.MailSettings;
import com.ansca.corona.SmsSettings;
import java.util.HashMap;

public interface CoronaShowApiListener {
    boolean showAppStoreWindow(HashMap<String, Object> hashMap);

    void showCameraWindowForImage(String str);

    void showCameraWindowForVideo(int i, int i2);

    void showSelectImageWindowUsing(String str);

    void showSelectVideoWindow();

    void showSendMailWindowUsing(MailSettings mailSettings);

    void showSendSmsWindowUsing(SmsSettings smsSettings);
}
