package jp.stargarage.g2metrics;

import java.util.List;

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

    public String getMethodType() {
        return "POST";
    }

    public Class getResponseClass() {
        return null;
    }

    public String getUrl() {
        Object[] objArr = new Object[2];
        objArr[0] = useSsl ? Constant.apiSSlUrlPrefix() : Constant.apiUrlPrefix();
        objArr[1] = "sendLog";
        return String.format("%s%s", objArr);
    }
}
