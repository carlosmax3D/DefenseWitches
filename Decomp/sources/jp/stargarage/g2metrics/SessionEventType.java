package jp.stargarage.g2metrics;

/* compiled from: Constant */
enum SessionEventType {
    FirstLaunch("ss01"),
    Launch("ss02"),
    LaunchFromNotification("ss03"),
    Terminate("ss04");
    
    final String id;

    private SessionEventType(String str) {
        this.id = str;
    }
}
