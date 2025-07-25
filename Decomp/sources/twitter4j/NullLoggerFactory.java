package twitter4j;

final class NullLoggerFactory extends LoggerFactory {
    private static final Logger SINGLETON = new NullLogger();

    NullLoggerFactory() {
    }

    public Logger getLogger(Class cls) {
        return SINGLETON;
    }
}
