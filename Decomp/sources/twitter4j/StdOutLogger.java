package twitter4j;

import java.util.Date;
import twitter4j.conf.ConfigurationContext;

final class StdOutLogger extends Logger {
    private static final boolean DEBUG = ConfigurationContext.getInstance().isDebugEnabled();

    StdOutLogger() {
    }

    public void debug(String str) {
        if (DEBUG) {
            System.out.println("[" + new Date() + "]" + str);
        }
    }

    public void debug(String str, String str2) {
        if (DEBUG) {
            debug(str + str2);
        }
    }

    public void error(String str) {
        System.out.println("[" + new Date() + "]" + str);
    }

    public void error(String str, Throwable th) {
        System.out.println(str);
        th.printStackTrace(System.err);
    }

    public void info(String str) {
        System.out.println("[" + new Date() + "]" + str);
    }

    public void info(String str, String str2) {
        info(str + str2);
    }

    public boolean isDebugEnabled() {
        return DEBUG;
    }

    public boolean isErrorEnabled() {
        return true;
    }

    public boolean isInfoEnabled() {
        return true;
    }

    public boolean isWarnEnabled() {
        return true;
    }

    public void warn(String str) {
        System.out.println("[" + new Date() + "]" + str);
    }

    public void warn(String str, String str2) {
        warn(str + str2);
    }
}
