package jp.stargarage.g2metrics;

import twitter4j.HttpResponseCode;

final class LogSettings {
    Integer healthCheckInterval = 180;
    Integer logSendCount = Integer.valueOf(HttpResponseCode.OK);
    Integer logSendInterval = 120;
    boolean useSsl = false;

    LogSettings() {
    }
}
