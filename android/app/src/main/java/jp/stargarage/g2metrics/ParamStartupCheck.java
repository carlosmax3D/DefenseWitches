package jp.stargarage.g2metrics;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class ParamStartupCheck extends ParamBase {
    EntityDeviceInfo deviceInfo;
    Integer firstLaunch;
    String game_key;
    String game_version;
    String uuid;

    @Override // jp.stargarage.g2metrics.IParamEntity
    public String getMethodType() {
        return "POST";
    }

    @Override // jp.stargarage.g2metrics.IParamEntity
    public Class getResponseClass() {
        return ResponseStartupCheck.class;
    }

    @Override // jp.stargarage.g2metrics.IParamEntity
    public String getUrl() {
        return String.format("%s%s", Constant.apiUrlPrefix(), "appStartup");
    }
}
