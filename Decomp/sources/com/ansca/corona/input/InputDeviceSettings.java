package com.ansca.corona.input;

import java.util.ArrayList;
import java.util.List;
import jp.stargarage.g2metrics.BuildConfig;

public class InputDeviceSettings implements Cloneable {
    private ArrayList<AxisSettings> fAxisCollection = new ArrayList<>();
    private InputDeviceType fDeviceType = InputDeviceType.UNKNOWN;
    private String fDisplayName = BuildConfig.FLAVOR;
    private String fPermanentStringId = null;
    private String fProductName = BuildConfig.FLAVOR;

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: com.ansca.corona.input.InputDeviceSettings} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.ansca.corona.input.InputDeviceSettings clone() {
        /*
            r6 = this;
            r2 = 0
            java.lang.Object r3 = super.clone()     // Catch:{ Exception -> 0x002c }
            r0 = r3
            com.ansca.corona.input.InputDeviceSettings r0 = (com.ansca.corona.input.InputDeviceSettings) r0     // Catch:{ Exception -> 0x002c }
            r2 = r0
            java.util.ArrayList<com.ansca.corona.input.AxisSettings> r3 = r2.fAxisCollection     // Catch:{ Exception -> 0x002c }
            r3.clear()     // Catch:{ Exception -> 0x002c }
            java.util.ArrayList<com.ansca.corona.input.AxisSettings> r3 = r6.fAxisCollection     // Catch:{ Exception -> 0x002c }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ Exception -> 0x002c }
        L_0x0014:
            boolean r4 = r3.hasNext()     // Catch:{ Exception -> 0x002c }
            if (r4 == 0) goto L_0x002d
            java.lang.Object r1 = r3.next()     // Catch:{ Exception -> 0x002c }
            com.ansca.corona.input.AxisSettings r1 = (com.ansca.corona.input.AxisSettings) r1     // Catch:{ Exception -> 0x002c }
            if (r1 == 0) goto L_0x002e
            java.util.ArrayList<com.ansca.corona.input.AxisSettings> r4 = r2.fAxisCollection     // Catch:{ Exception -> 0x002c }
            com.ansca.corona.input.AxisSettings r5 = r1.clone()     // Catch:{ Exception -> 0x002c }
            r4.add(r5)     // Catch:{ Exception -> 0x002c }
            goto L_0x0014
        L_0x002c:
            r3 = move-exception
        L_0x002d:
            return r2
        L_0x002e:
            java.util.ArrayList<com.ansca.corona.input.AxisSettings> r4 = r2.fAxisCollection     // Catch:{ Exception -> 0x002c }
            r5 = 0
            r4.add(r5)     // Catch:{ Exception -> 0x002c }
            goto L_0x0014
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.input.InputDeviceSettings.clone():com.ansca.corona.input.InputDeviceSettings");
    }

    public List<AxisSettings> getAxes() {
        return this.fAxisCollection;
    }

    public String getDisplayName() {
        return this.fDisplayName;
    }

    public String getPermanentStringId() {
        return this.fPermanentStringId;
    }

    public String getProductName() {
        return this.fProductName;
    }

    public InputDeviceType getType() {
        return this.fDeviceType;
    }

    public boolean hasPermanentStringId() {
        return this.fPermanentStringId != null && this.fPermanentStringId.length() > 0;
    }

    public void setDisplayName(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fDisplayName = str;
    }

    public void setPermanentStringId(String str) {
        if (str != null && str.length() <= 0) {
            str = null;
        }
        this.fPermanentStringId = str;
    }

    public void setProductName(String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.fProductName = str;
    }

    public void setType(InputDeviceType inputDeviceType) {
        if (inputDeviceType == null) {
            inputDeviceType = InputDeviceType.UNKNOWN;
        }
        this.fDeviceType = inputDeviceType;
    }
}
