package jp.stargarage.g2metrics;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public final class DeviceInfo {
    String advertisingIdentifier;
    String androidId;
    String careerName;
    String ipv4;
    String ipv6;
    String isoCountryCode;
    String language;
    String macAddress;
    String mcc;
    String mnc;
    String model;
    String phoneNumber;
    String resolution;
    String serialNumber;
    String simSerialNumber;
    String simState;
    String systemVersion;
    String systemVersionRelease;
    String timeZone;
    String voiceMailNumber;

    public String getAdvertisingIdentifier() {
        return this.advertisingIdentifier == null ? "NONE" : this.advertisingIdentifier;
    }

    public String getAndroidId() {
        return this.androidId == null ? "NONE" : this.androidId;
    }

    public String getIpv4() {
        return this.ipv4 == null ? "NONE" : this.ipv4;
    }

    public String getModel() {
        return this.model == null ? "NONE" : this.model;
    }

    public String getSystemVersion() {
        return this.systemVersionRelease == null ? "NONE" : this.systemVersionRelease;
    }
}
