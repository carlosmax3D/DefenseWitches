package jp.stargarage.g2metrics;

/* compiled from: Constant */
enum Work {
    NetHealthCheck(1),
    SendLog(2),
    StartTimer(3),
    Launch(4);
    
    final Integer id;

    private Work(Integer num) {
        this.id = num;
    }

    static Work valueOf(Integer num) {
        for (Work work : values()) {
            if (work.id.equals(num)) {
                return work;
            }
        }
        return null;
    }
}
