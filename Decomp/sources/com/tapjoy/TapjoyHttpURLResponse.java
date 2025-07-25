package com.tapjoy;

import java.util.List;
import java.util.Map;

public class TapjoyHttpURLResponse {
    public int contentLength;
    public Map<String, List<String>> headerFields;
    public String redirectURL;
    public String response;
    public int statusCode;

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0007, code lost:
        r0 = r4.headerFields.get(r5);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getHeaderFieldAsString(java.lang.String r5) {
        /*
            r4 = this;
            r3 = 0
            java.lang.String r1 = ""
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r2 = r4.headerFields
            if (r2 == 0) goto L_0x001d
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r2 = r4.headerFields
            java.lang.Object r0 = r2.get(r5)
            java.util.List r0 = (java.util.List) r0
            if (r0 == 0) goto L_0x001d
            java.lang.Object r2 = r0.get(r3)
            if (r2 == 0) goto L_0x001d
            java.lang.Object r1 = r0.get(r3)
            java.lang.String r1 = (java.lang.String) r1
        L_0x001d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tapjoy.TapjoyHttpURLResponse.getHeaderFieldAsString(java.lang.String):java.lang.String");
    }
}
