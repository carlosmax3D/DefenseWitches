package jp.stargarage.g2metrics;

import java.util.List;

final class ParamReadNotification extends ParamBase {
    EntityAppInfo appInfo;
    EntityDeviceInfo deviceInfo;
    String game_key;
    List<EntityLogData> noticeList;
    String sdk_version;
    String uuid;

    ParamReadNotification() {
    }

    public String getMethodType() {
        return "POST";
    }

    public Class getResponseClass() {
        return null;
    }

    public String getUrl() {
        return String.format("%s%s", new Object[]{Constant.apiUrlPrefix(), "readNotification"});
    }
}
