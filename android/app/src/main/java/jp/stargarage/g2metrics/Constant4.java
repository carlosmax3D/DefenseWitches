package jp.stargarage.g2metrics;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* compiled from: Constant.java */
/* renamed from: jp.stargarage.g2metrics.Work, reason: use source file name */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
enum Constant4 {
    NetHealthCheck(1),
    SendLog(2),
    StartTimer(3),
    Launch(4);


    /* renamed from: id */
    final Integer f3205id;

    Constant4(Integer num) {
        this.f3205id = num;
    }

    static Constant4 valueOf(Integer num) {
        for (Constant4 constant4 : values()) {
            if (constant4.f3205id.equals(num)) {
                return constant4;
            }
        }
        return null;
    }
}
