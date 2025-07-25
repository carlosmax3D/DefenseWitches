package jp.stargarage.g2metrics;

import java.util.List;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
final class ParamReadNotification extends ParamBase {
    EntityAppInfo appInfo;
    EntityDeviceInfo deviceInfo;
    String game_key;
    List<EntityLogData> noticeList;
    String sdk_version;
    String uuid;

    ParamReadNotification() {
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
        return String.format("%s%s", Constant.apiUrlPrefix(), "readNotification");
    }
}
