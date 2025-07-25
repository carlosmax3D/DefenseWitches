package twitter4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import twitter4j.conf.ConfigurationContext;

public final class HttpClientFactory {
    private static final Constructor HTTP_CLIENT_CONSTRUCTOR;
    private static final String HTTP_CLIENT_IMPLEMENTATION = "twitter4j.http.httpClient";
    private static final HashMap<HttpClientConfiguration, HttpClient> confClientMap = new HashMap<>();

    static {
        Class<?> cls = null;
        String property = System.getProperty(HTTP_CLIENT_IMPLEMENTATION);
        if (property != null) {
            try {
                cls = Class.forName(property);
            } catch (ClassNotFoundException e) {
            }
        }
        if (cls == null) {
            try {
                cls = Class.forName("twitter4j.AlternativeHttpClientImpl");
            } catch (ClassNotFoundException e2) {
            }
        }
        if (cls == null) {
            try {
                cls = Class.forName("twitter4j.HttpClientImpl");
            } catch (ClassNotFoundException e3) {
                throw new AssertionError(e3);
            }
        }
        try {
            HTTP_CLIENT_CONSTRUCTOR = cls.getConstructor(new Class[]{HttpClientConfiguration.class});
        } catch (NoSuchMethodException e4) {
            throw new AssertionError(e4);
        }
    }

    public static HttpClient getInstance() {
        return getInstance(ConfigurationContext.getInstance().getHttpClientConfiguration());
    }

    public static HttpClient getInstance(HttpClientConfiguration httpClientConfiguration) {
        HttpClient httpClient = confClientMap.get(httpClientConfiguration);
        if (httpClient != null) {
            return httpClient;
        }
        try {
            HttpClient httpClient2 = (HttpClient) HTTP_CLIENT_CONSTRUCTOR.newInstance(new Object[]{httpClientConfiguration});
            confClientMap.put(httpClientConfiguration, httpClient2);
            return httpClient2;
        } catch (InstantiationException e) {
            throw new AssertionError(e);
        } catch (IllegalAccessException e2) {
            throw new AssertionError(e2);
        } catch (InvocationTargetException e3) {
            throw new AssertionError(e3);
        }
    }
}
