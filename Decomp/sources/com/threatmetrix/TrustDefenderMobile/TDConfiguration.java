package com.threatmetrix.TrustDefenderMobile;

import android.util.Log;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import jp.stargarage.g2metrics.BuildConfig;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

class TDConfiguration {
    private static final String TAG = TDConfiguration.class.getName();
    public final ArrayList<URI> ex_paths = new ArrayList<>();
    public final ArrayList<String> jb_paths = new ArrayList<>();
    public int options = 0;
    public String w = BuildConfig.FLAVOR;

    TDConfiguration() {
    }

    private boolean parseEX(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int next = xmlPullParser.next();
        String str = BuildConfig.FLAVOR;
        while (next != 1) {
            switch (next) {
                case 0:
                    String str2 = TAG;
                    break;
                case 2:
                    str = xmlPullParser.getName();
                    break;
                case 3:
                    if (!xmlPullParser.getName().equals("EX")) {
                        break;
                    } else {
                        return true;
                    }
                case 4:
                    if (str != null) {
                        if (!str.equals("E")) {
                            String str3 = TAG;
                            new StringBuilder("Found tag content unexpectedly: ").append(str);
                            break;
                        } else {
                            try {
                                this.ex_paths.add(new URI(xmlPullParser.getText()));
                                break;
                            } catch (URISyntaxException e) {
                                Log.e(TAG, "Failed to parse E into URI", e);
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                default:
                    String str4 = TAG;
                    new StringBuilder("Found unexpected event type: ").append(next);
                    break;
            }
            next = xmlPullParser.next();
        }
        return false;
    }

    private boolean parsePS(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int next = xmlPullParser.next();
        String str = BuildConfig.FLAVOR;
        while (next != 1) {
            switch (next) {
                case 0:
                    String str2 = TAG;
                    break;
                case 2:
                    str = xmlPullParser.getName();
                    break;
                case 3:
                    if (!xmlPullParser.getName().equals("PS")) {
                        break;
                    } else {
                        return true;
                    }
                case 4:
                    if (str != null) {
                        if (!str.equals("P")) {
                            String str3 = TAG;
                            new StringBuilder("Found tag content unexpectedly: ").append(str);
                            break;
                        } else {
                            this.jb_paths.add(xmlPullParser.getText());
                            break;
                        }
                    } else {
                        break;
                    }
                default:
                    String str4 = TAG;
                    new StringBuilder("Found unexpected event type: ").append(next);
                    break;
            }
            next = xmlPullParser.next();
        }
        return false;
    }

    public final boolean isUsable() {
        return this.w != null && !this.w.isEmpty();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean parseConfigFromStream(java.io.InputStream r10) {
        /*
            r9 = this;
            r6 = 1
            r4 = 0
            r0 = 0
            org.xmlpull.v1.XmlPullParserFactory r3 = org.xmlpull.v1.XmlPullParserFactory.newInstance()     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            org.xmlpull.v1.XmlPullParser r5 = r3.newPullParser()     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            java.io.InputStreamReader r7 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            r7.<init>(r10)     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            r5.setInput(r7)     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            int r2 = r5.getEventType()     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
        L_0x0017:
            if (r2 == r6) goto L_0x0069
            switch(r2) {
                case 0: goto L_0x001c;
                case 1: goto L_0x001c;
                case 2: goto L_0x0021;
                case 3: goto L_0x004d;
                case 4: goto L_0x004f;
                default: goto L_0x001c;
            }     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
        L_0x001c:
            int r2 = r5.next()     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            goto L_0x0017
        L_0x0021:
            java.lang.String r7 = r5.getName()     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            java.lang.String r8 = "PS"
            boolean r7 = r7.equals(r8)     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            if (r7 == 0) goto L_0x0035
            boolean r7 = r9.parsePS(r5)     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            if (r7 != 0) goto L_0x001c
        L_0x0033:
            r4 = 1
            goto L_0x001c
        L_0x0035:
            java.lang.String r7 = r5.getName()     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            java.lang.String r8 = "EX"
            boolean r7 = r7.equals(r8)     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            if (r7 == 0) goto L_0x0048
            boolean r7 = r9.parseEX(r5)     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            if (r7 != 0) goto L_0x001c
            goto L_0x0033
        L_0x0048:
            java.lang.String r0 = r5.getName()     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            goto L_0x001c
        L_0x004d:
            r0 = 0
            goto L_0x001c
        L_0x004f:
            if (r0 == 0) goto L_0x001c
            java.lang.String r7 = "w"
            boolean r7 = r0.equals(r7)     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            if (r7 == 0) goto L_0x006c
            java.lang.String r7 = r5.getText()     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            r9.w = r7     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            goto L_0x001c
        L_0x0060:
            r1 = move-exception
            java.lang.String r7 = TAG
            java.lang.String r8 = "IO Error"
            android.util.Log.e(r7, r8, r1)
            r4 = 0
        L_0x0069:
            if (r4 != 0) goto L_0x008d
        L_0x006b:
            return r6
        L_0x006c:
            java.lang.String r7 = "O"
            boolean r7 = r0.equals(r7)     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            if (r7 == 0) goto L_0x0033
            java.lang.String r7 = r5.getText()     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            int r7 = r7.intValue()     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            r9.options = r7     // Catch:{ IOException -> 0x0060, XmlPullParserException -> 0x0083 }
            goto L_0x001c
        L_0x0083:
            r1 = move-exception
            java.lang.String r7 = TAG
            java.lang.String r8 = "XML Parse Error"
            android.util.Log.e(r7, r8, r1)
            r4 = 0
            goto L_0x0069
        L_0x008d:
            r6 = 0
            goto L_0x006b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.threatmetrix.TrustDefenderMobile.TDConfiguration.parseConfigFromStream(java.io.InputStream):boolean");
    }
}
