package jp.stargarage.g2metrics;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* compiled from: Constant.java */
/* renamed from: jp.stargarage.g2metrics.HealthStatus, reason: use source file name */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
enum Constant2 {
    Unknown(-1),
    Ok(0),
    NotRegistered(1),
    InMaintenance(2),
    ServerError(3);


    /* renamed from: id */
    final Integer f3203id;

    Constant2(Integer num) {
        this.f3203id = num;
    }

    static Constant2 valueOf(Integer num) {
        for (Constant2 constant2 : values()) {
            if (constant2.f3203id.equals(num)) {
                return constant2;
            }
        }
        return null;
    }
}
