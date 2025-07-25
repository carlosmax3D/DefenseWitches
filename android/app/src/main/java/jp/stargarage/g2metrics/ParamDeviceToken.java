package jp.stargarage.g2metrics;

import java.util.List;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
final class ParamDeviceToken extends ParamBase {
    EntityAppInfo appInfo;
    EntityDeviceInfo deviceInfo;
    String game_key;
    String sdk_version;
    List<EntityLogData> tokenList;
    String uuid;

    ParamDeviceToken() {
    }

    @Override // jp.stargarage.g2metrics.IParamEntity
    public String getMethodType() {
        return "POST";
    }

    @Override // jp.stargarage.g2metrics.IParamEntity
    public Class getResponseClass() {
        return null;
    }

    @Override // jp.stargarage.g2metrics.IParamEntity
    public String getUrl() {
        return String.format("%s%s", Constant.apiUrlPrefix(), "registToken");
    }
}
