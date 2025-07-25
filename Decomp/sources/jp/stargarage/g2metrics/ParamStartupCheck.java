package jp.stargarage.g2metrics;

public class ParamStartupCheck extends ParamBase {
    EntityDeviceInfo deviceInfo;
    Integer firstLaunch;
    String game_key;
    String game_version;
    String uuid;

    public String getMethodType() {
        return "POST";
    }

    public Class getResponseClass() {
        return ResponseStartupCheck.class;
    }

    public String getUrl() {
        return String.format("%s%s", new Object[]{Constant.apiUrlPrefix(), "appStartup"});
    }
}
