package jp.stargarage.g2metrics;

final class Constant {
    private static String apiSSlUrlPrefix = "https://event.g2-metrics.com/";
    private static String apiUrlPrefix = "http://event.g2-metrics.com/";
    private static String devApiSSlUrlPrefix = "https://dev49.g2-metrics.com:7201/";
    private static String devApiUrlPrefix = "http://dev49.g2-metrics.com:7201/";
    static boolean devModel = false;
    static Integer httpTimeout = 2000;
    static final String sdkVersion = "1.4.0";

    Constant() {
    }

    static String apiSSlUrlPrefix() {
        return devModel ? devApiSSlUrlPrefix : apiSSlUrlPrefix;
    }

    static String apiUrlPrefix() {
        return devModel ? devApiUrlPrefix : apiUrlPrefix;
    }

    static String getPopupBrowserUrl(String str, String str2, String str3) {
        return String.format("%s%s/%s/%s/%s/%s", new Object[]{apiUrlPrefix(), "cv", str, str2, "1.4.0", str3});
    }
}
