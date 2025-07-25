package jp.stargarage.g2metrics;

/* compiled from: Constant */
enum HealthStatus {
    Unknown(-1),
    Ok(0),
    NotRegistered(1),
    InMaintenance(2),
    ServerError(3);
    
    final Integer id;

    private HealthStatus(Integer num) {
        this.id = num;
    }

    static HealthStatus valueOf(Integer num) {
        for (HealthStatus healthStatus : values()) {
            if (healthStatus.id.equals(num)) {
                return healthStatus;
            }
        }
        return null;
    }
}
