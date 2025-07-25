package jp.stargarage.g2metrics;

final class ParamLogSetting extends ParamBase {
    String gameKey;

    ParamLogSetting() {
    }

    public String getMethodType() {
        return "GET";
    }

    public Class getResponseClass() {
        return ResponseLogSetting.class;
    }

    public String getUrl() {
        return String.format("%s%s/%s", new Object[]{Constant.apiUrlPrefix(), "logSetting", this.gameKey});
    }
}
