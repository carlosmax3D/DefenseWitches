package twitter4j;

import org.slf4j.Logger;

final class SLF4JLogger extends Logger {
    private final Logger LOGGER;

    SLF4JLogger(Logger logger) {
        this.LOGGER = logger;
    }

    public void debug(String str) {
        this.LOGGER.debug(str);
    }

    public void debug(String str, String str2) {
        this.LOGGER.debug(str + str2);
    }

    public void error(String str) {
        this.LOGGER.error(str);
    }

    public void error(String str, Throwable th) {
        this.LOGGER.error(str, th);
    }

    public void info(String str) {
        this.LOGGER.info(str);
    }

    public void info(String str, String str2) {
        this.LOGGER.info(str + str2);
    }

    public boolean isDebugEnabled() {
        return this.LOGGER.isDebugEnabled();
    }

    public boolean isErrorEnabled() {
        return this.LOGGER.isErrorEnabled();
    }

    public boolean isInfoEnabled() {
        return this.LOGGER.isInfoEnabled();
    }

    public boolean isWarnEnabled() {
        return this.LOGGER.isWarnEnabled();
    }

    public void warn(String str) {
        this.LOGGER.warn(str);
    }

    public void warn(String str, String str2) {
        this.LOGGER.warn(str + str2);
    }
}
