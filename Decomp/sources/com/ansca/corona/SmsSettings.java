package com.ansca.corona;

import android.content.Intent;
import android.net.Uri;
import com.facebook.share.internal.ShareConstants;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import jp.stargarage.g2metrics.BuildConfig;

public class SmsSettings {
    private LinkedHashSet<String> fRecipients = new LinkedHashSet<>();
    private String fText = BuildConfig.FLAVOR;

    public static SmsSettings from(HashMap<String, Object> hashMap) {
        SmsSettings smsSettings = new SmsSettings();
        if (hashMap != null) {
            for (Map.Entry next : hashMap.entrySet()) {
                String str = (String) next.getKey();
                Object value = next.getValue();
                if (!(str == null || str.length() <= 0 || value == null)) {
                    String trim = str.toLowerCase().trim();
                    if (trim.equals(ShareConstants.WEB_DIALOG_PARAM_TO)) {
                        if (value instanceof String) {
                            smsSettings.getRecipients().add((String) value);
                        } else if (value instanceof String[]) {
                            for (String add : (String[]) value) {
                                smsSettings.getRecipients().add(add);
                            }
                        } else if (value instanceof HashMap) {
                            for (Object next2 : ((HashMap) value).values()) {
                                if (next2 instanceof String) {
                                    smsSettings.getRecipients().add((String) next2);
                                }
                            }
                        } else if (value instanceof Collection) {
                            try {
                                smsSettings.getRecipients().addAll((Collection) value);
                            } catch (Exception e) {
                            }
                        }
                    } else if (trim.equals("body") && (value instanceof String)) {
                        smsSettings.setText((String) value);
                    }
                }
            }
        }
        return smsSettings;
    }

    public LinkedHashSet<String> getRecipients() {
        return this.fRecipients;
    }

    public String getText() {
        return this.fText;
    }

    public void setText(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fText = str;
    }

    public Intent toIntent() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setType("vnd.android-dir/mms-sms");
        if (this.fRecipients.size() > 0) {
            boolean z = true;
            StringBuilder sb = new StringBuilder();
            sb.append("sms:");
            Iterator it = this.fRecipients.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (str != null && str.length() > 0) {
                    if (!z) {
                        sb.append(",");
                    }
                    sb.append(str);
                    z = false;
                }
            }
            intent.setData(Uri.parse(sb.toString()));
        }
        if (this.fText.length() > 0) {
            intent.putExtra("sms_body", this.fText);
        }
        return intent;
    }
}
