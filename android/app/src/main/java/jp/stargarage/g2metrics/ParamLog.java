package jp.stargarage.g2metrics;

import java.util.List;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
final class ParamLog extends ParamBase {
    private static boolean useSsl;
    EntityAppInfo appInfo;
    EntityDeviceInfo deviceInfo;
    String game_key;
    List<EntityLogData> logList;
    String sdk_version;
    String uuid;

    ParamLog(boolean z) {
        useSsl = z;
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
        Object[] objArr = new Object[2];
        objArr[0] = useSsl ? Constant.apiSSlUrlPrefix() : Constant.apiUrlPrefix();
        objArr[1] = "sendLog";
        return String.format("%s%s", objArr);
    }
}
