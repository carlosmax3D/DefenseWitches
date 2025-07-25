package jp.stargarage.g2metrics;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
final class ParamHealthCheck extends ParamBase {
    String gamekey;

    ParamHealthCheck() {
    }

    @Override // jp.stargarage.g2metrics.IParamEntity
    public String getMethodType() {
        return "GET";
    }

    @Override // jp.stargarage.g2metrics.IParamEntity
    public Class getResponseClass() {
        return ResponseHealthCheck.class;
    }

    @Override // jp.stargarage.g2metrics.IParamEntity
    public String getUrl() {
        return String.format("%s%s/%s", Constant.apiUrlPrefix(), "health", this.gamekey);
    }
}
