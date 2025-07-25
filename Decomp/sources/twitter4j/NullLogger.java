package twitter4j;

final class NullLogger extends Logger {
    NullLogger() {
    }

    public void debug(String str) {
    }

    public void debug(String str, String str2) {
    }

    public void error(String str) {
    }

    public void error(String str, Throwable th) {
    }

    public void info(String str) {
    }

    public void info(String str, String str2) {
    }

    public boolean isDebugEnabled() {
        return false;
    }

    public boolean isErrorEnabled() {
        return false;
    }

    public boolean isInfoEnabled() {
        return false;
    }

    public boolean isWarnEnabled() {
        return false;
    }

    public void warn(String str) {
    }

    public void warn(String str, String str2) {
    }
}
