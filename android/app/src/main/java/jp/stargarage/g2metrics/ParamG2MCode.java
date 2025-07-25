package jp.stargarage.g2metrics;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
final class ParamG2MCode extends ParamBase {
    EntityDeviceInfo deviceInfo;
    String gameKey;

    ParamG2MCode() {
    }

    @Override // jp.stargarage.g2metrics.IParamEntity
    public String getMethodType() {
        return "POST";
    }

    @Override // jp.stargarage.g2metrics.IParamEntity
    public Class getResponseClass() {
        return ResponseG2MCode.class;
    }

    @Override // jp.stargarage.g2metrics.IParamEntity
    public String getUrl() {
        return String.format("%s%s", Constant.apiUrlPrefix(), "g2mCode");
    }
}
