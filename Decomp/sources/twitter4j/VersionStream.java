package twitter4j;

public final class VersionStream {
    private static final String TITLE = "Twitter4J Streaming API support";
    private static final String VERSION = "4.0.2";

    private VersionStream() {
        throw new AssertionError();
    }

    public static String getVersion() {
        return VERSION;
    }

    public static void main(String[] strArr) {
        System.out.println("Twitter4J Streaming API support 4.0.2");
    }
}
