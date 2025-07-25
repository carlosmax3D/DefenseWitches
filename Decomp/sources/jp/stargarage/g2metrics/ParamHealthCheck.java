package jp.stargarage.g2metrics;

final class ParamHealthCheck extends ParamBase {
    String gamekey;

    ParamHealthCheck() {
    }

    public String getMethodType() {
        return "GET";
    }

    public Class getResponseClass() {
        return ResponseHealthCheck.class;
    }

    public String getUrl() {
        return String.format("%s%s/%s", new Object[]{Constant.apiUrlPrefix(), "health", this.gamekey});
    }
}
