package twitter4j;

public interface Dispatcher {
    void invokeLater(Runnable runnable);

    void shutdown();
}
