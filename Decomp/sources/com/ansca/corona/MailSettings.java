package com.ansca.corona;

import android.content.Context;
import android.content.Intent;
import android.net.MailTo;
import android.net.Uri;
import android.os.Parcelable;
import android.text.Html;
import com.ansca.corona.storage.FileContentProvider;
import com.ansca.corona.storage.FileServices;
import com.facebook.share.internal.ShareConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import jp.stargarage.g2metrics.BuildConfig;

public class MailSettings {
    private LinkedHashSet<String> fBccList = new LinkedHashSet<>();
    private String fBody = BuildConfig.FLAVOR;
    private LinkedHashSet<String> fCcList = new LinkedHashSet<>();
    private LinkedHashSet<Uri> fFileAttachments = new LinkedHashSet<>();
    private boolean fIsHtml = false;
    private String fSubject = BuildConfig.FLAVOR;
    private LinkedHashSet<String> fToList = new LinkedHashSet<>();

    private static void addFileAttachmentObjectToCollection(Context context, LinkedHashSet<Uri> linkedHashSet, Object obj) {
        int i = 0;
        if (linkedHashSet != null && obj != null) {
            if (obj instanceof String) {
                if (context != null) {
                    linkedHashSet.add(FileContentProvider.createContentUriForFile(context, (String) obj));
                }
            } else if (obj instanceof String[]) {
                if (context != null) {
                    String[] strArr = (String[]) obj;
                    int length = strArr.length;
                    while (i < length) {
                        linkedHashSet.add(FileContentProvider.createContentUriForFile(context, strArr[i]));
                        i++;
                    }
                }
            } else if (obj instanceof File) {
                if (context != null) {
                    linkedHashSet.add(FileContentProvider.createContentUriForFile(context, ((File) obj).getPath()));
                }
            } else if (obj instanceof File[]) {
                if (context != null) {
                    File[] fileArr = (File[]) obj;
                    int length2 = fileArr.length;
                    while (i < length2) {
                        linkedHashSet.add(FileContentProvider.createContentUriForFile(context, fileArr[i].getPath()));
                        i++;
                    }
                }
            } else if (obj instanceof Uri) {
                linkedHashSet.add((Uri) obj);
            } else if (obj instanceof Uri[]) {
                Uri[] uriArr = (Uri[]) obj;
                int length3 = uriArr.length;
                while (i < length3) {
                    linkedHashSet.add(uriArr[i]);
                    i++;
                }
            }
        }
    }

    private static void addStringObjectToCollection(LinkedHashSet<String> linkedHashSet, Object obj) {
        if (linkedHashSet != null && obj != null) {
            try {
                if (obj instanceof String) {
                    linkedHashSet.add((String) obj);
                } else if (obj instanceof String[]) {
                    for (String add : (String[]) obj) {
                        linkedHashSet.add(add);
                    }
                } else if (obj instanceof HashMap) {
                    for (Object next : ((HashMap) obj).values()) {
                        if (next instanceof String) {
                            linkedHashSet.add((String) next);
                        }
                    }
                } else if (obj instanceof Collection) {
                    linkedHashSet.addAll((Collection) obj);
                }
            } catch (Exception e) {
            }
        }
    }

    public static MailSettings from(Context context, HashMap<String, Object> hashMap) {
        MailSettings mailSettings = new MailSettings();
        if (hashMap != null) {
            for (Map.Entry next : hashMap.entrySet()) {
                String str = (String) next.getKey();
                Object value = next.getValue();
                if (!(str == null || str.length() <= 0 || value == null)) {
                    String trim = str.toLowerCase().trim();
                    if (trim.equals(ShareConstants.WEB_DIALOG_PARAM_TO)) {
                        addStringObjectToCollection(mailSettings.getToList(), value);
                    } else if (trim.equals("cc")) {
                        addStringObjectToCollection(mailSettings.getCcList(), value);
                    } else if (trim.equals("bcc")) {
                        addStringObjectToCollection(mailSettings.getBccList(), value);
                    } else if (trim.equals("subject")) {
                        if (value instanceof String) {
                            mailSettings.setSubject((String) value);
                        }
                    } else if (trim.equals("body")) {
                        if (value instanceof String) {
                            mailSettings.setBody((String) value);
                        }
                    } else if (trim.equals("isbodyhtml")) {
                        if (value instanceof Boolean) {
                            mailSettings.setHtmlFlag(((Boolean) value).booleanValue());
                        }
                    } else if (trim.equals("attachment")) {
                        if (value instanceof HashMap) {
                            for (Object addFileAttachmentObjectToCollection : ((HashMap) value).values()) {
                                addFileAttachmentObjectToCollection(context, mailSettings.getFileAttachments(), addFileAttachmentObjectToCollection);
                            }
                        } else {
                            addFileAttachmentObjectToCollection(context, mailSettings.getFileAttachments(), value);
                        }
                    }
                }
            }
        }
        return mailSettings;
    }

    public static MailSettings fromUrl(String str) {
        MailSettings mailSettings = new MailSettings();
        if (str != null && str.length() > 0) {
            try {
                MailTo parse = MailTo.parse(str);
                if (parse.getTo() != null) {
                    for (String add : parse.getTo().split(",")) {
                        mailSettings.getToList().add(add);
                    }
                }
                if (parse.getCc() != null) {
                    for (String add2 : parse.getCc().split(",")) {
                        mailSettings.getCcList().add(add2);
                    }
                }
                if (parse.getSubject() != null) {
                    mailSettings.setSubject(parse.getSubject());
                }
                if (parse.getBody() != null) {
                    mailSettings.setBody(parse.getBody());
                }
                for (Map.Entry next : parse.getHeaders().entrySet()) {
                    if (((String) next.getKey()).toLowerCase().equals("bcc")) {
                        for (String add3 : ((String) next.getValue()).split(",")) {
                            mailSettings.getBccList().add(add3);
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
        return mailSettings;
    }

    public LinkedHashSet<String> getBccList() {
        return this.fBccList;
    }

    public String getBody() {
        return this.fBody;
    }

    public LinkedHashSet<String> getCcList() {
        return this.fCcList;
    }

    public LinkedHashSet<Uri> getFileAttachments() {
        return this.fFileAttachments;
    }

    public String getSubject() {
        return this.fSubject;
    }

    public LinkedHashSet<String> getToList() {
        return this.fToList;
    }

    public boolean isHtml() {
        return this.fIsHtml;
    }

    public void setBody(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fBody = str;
    }

    public void setHtmlFlag(boolean z) {
        this.fIsHtml = z;
    }

    public void setSubject(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fSubject = str;
    }

    public Intent toIntent() {
        Context applicationContext;
        String str = this.fFileAttachments.size() > 1 ? "android.intent.action.SEND_MULTIPLE" : "android.intent.action.SEND";
        String str2 = this.fIsHtml ? "text/html" : "plain/text";
        if (this.fFileAttachments.size() > 0 && (applicationContext = CoronaEnvironment.getApplicationContext()) != null) {
            FileServices fileServices = new FileServices(applicationContext);
            boolean z = true;
            Iterator it = this.fFileAttachments.iterator();
            str2 = fileServices.getMimeTypeFrom((Uri) it.next());
            if (str2 == null) {
                str2 = "*/*";
            }
            while (it.hasNext() && z) {
                if (str2 != fileServices.getMimeTypeFrom((Uri) it.next())) {
                    z = false;
                    str2 = "*/*";
                }
            }
        }
        Intent intent = new Intent(str);
        intent.setType(str2);
        if (this.fToList.size() > 0) {
            intent.putExtra("android.intent.extra.EMAIL", (String[]) this.fToList.toArray(new String[0]));
        }
        if (this.fCcList.size() > 0) {
            intent.putExtra("android.intent.extra.CC", (String[]) this.fCcList.toArray(new String[0]));
        }
        if (this.fBccList.size() > 0) {
            intent.putExtra("android.intent.extra.BCC", (String[]) this.fBccList.toArray(new String[0]));
        }
        if (this.fSubject.length() > 0) {
            intent.putExtra("android.intent.extra.SUBJECT", this.fSubject);
        }
        if (this.fBody.length() > 0) {
            intent.putExtra("android.intent.extra.TEXT", this.fIsHtml ? Html.fromHtml(this.fBody) : this.fBody);
        }
        if (this.fFileAttachments.size() > 1) {
            ArrayList arrayList = new ArrayList(this.fFileAttachments.size());
            arrayList.addAll(this.fFileAttachments);
            intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
        } else if (this.fFileAttachments.size() == 1) {
            intent.putExtra("android.intent.extra.STREAM", (Parcelable) this.fFileAttachments.iterator().next());
        }
        if (this.fFileAttachments.size() > 0) {
            intent.addFlags(1);
        }
        return intent;
    }
}
