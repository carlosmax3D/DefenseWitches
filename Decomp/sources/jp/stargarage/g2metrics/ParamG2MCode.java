package jp.stargarage.g2metrics;

final class ParamG2MCode extends ParamBase {
    EntityDeviceInfo deviceInfo;
    String gameKey;

    ParamG2MCode() {
    }

    public String getMethodType() {
        return "POST";
    }

    public Class getResponseClass() {
        return ResponseG2MCode.class;
    }

    public String getUrl() {
        return String.format("%s%s", new Object[]{Constant.apiUrlPrefix(), "g2mCode"});
    }
}
