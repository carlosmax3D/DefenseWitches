package twitter4j;

final class StdOutLoggerFactory extends LoggerFactory {
    private static final Logger SINGLETON = new StdOutLogger();

    StdOutLoggerFactory() {
    }

    public Logger getLogger(Class cls) {
        return SINGLETON;
    }
}
