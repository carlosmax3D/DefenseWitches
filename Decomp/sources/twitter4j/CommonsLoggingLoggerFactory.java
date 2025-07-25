package twitter4j;

import org.apache.commons.logging.LogFactory;

final class CommonsLoggingLoggerFactory extends LoggerFactory {
    CommonsLoggingLoggerFactory() {
    }

    public Logger getLogger(Class cls) {
        return new CommonsLoggingLogger(LogFactory.getLog(cls));
    }
}
