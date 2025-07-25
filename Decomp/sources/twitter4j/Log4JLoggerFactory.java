package twitter4j;

import org.apache.log4j.Logger;

final class Log4JLoggerFactory extends LoggerFactory {
    Log4JLoggerFactory() {
    }

    public Logger getLogger(Class cls) {
        return new Log4JLogger(Logger.getLogger(cls));
    }
}
