package twitter4j;

import java.util.logging.Logger;

final class JULLoggerFactory extends LoggerFactory {
    JULLoggerFactory() {
    }

    public Logger getLogger(Class cls) {
        return new JULLogger(Logger.getLogger(cls.getName()));
    }
}
