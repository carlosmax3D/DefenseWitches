package twitter4j;

import java.util.logging.Level;
import java.util.logging.Logger;

final class JULLogger extends Logger {
    private final Logger LOGGER;

    JULLogger(Logger logger) {
        this.LOGGER = logger;
    }

    public void debug(String str) {
        this.LOGGER.fine(str);
    }

    public void debug(String str, String str2) {
        this.LOGGER.fine(str + str2);
    }

    public void error(String str) {
        this.LOGGER.severe(str);
    }

    public void error(String str, Throwable th) {
        this.LOGGER.severe(str + th.getMessage());
    }

    public void info(String str) {
        this.LOGGER.info(str);
    }

    public void info(String str, String str2) {
        this.LOGGER.info(str + str2);
    }

    public boolean isDebugEnabled() {
        return this.LOGGER.isLoggable(Level.FINE);
    }

    public boolean isErrorEnabled() {
        return this.LOGGER.isLoggable(Level.SEVERE);
    }

    public boolean isInfoEnabled() {
        return this.LOGGER.isLoggable(Level.INFO);
    }

    public boolean isWarnEnabled() {
        return this.LOGGER.isLoggable(Level.WARNING);
    }

    public void warn(String str) {
        this.LOGGER.warning(str);
    }

    public void warn(String str, String str2) {
        this.LOGGER.warning(str + str2);
    }
}
