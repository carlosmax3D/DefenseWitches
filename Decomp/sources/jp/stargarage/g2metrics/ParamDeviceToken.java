package jp.stargarage.g2metrics;

import java.util.List;

final class ParamDeviceToken extends ParamBase {
    EntityAppInfo appInfo;
    EntityDeviceInfo deviceInfo;
    String game_key;
    String sdk_version;
    List<EntityLogData> tokenList;
    String uuid;

    ParamDeviceToken() {
    }

    public String getMethodType() {
        return "POST";
    }

    public Class getResponseClass() {
        return null;
    }

    public String getUrl() {
        return String.format("%s%s", new Object[]{Constant.apiUrlPrefix(), "registToken"});
    }
}
