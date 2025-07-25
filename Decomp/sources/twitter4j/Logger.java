package twitter4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;

public abstract class Logger {
    private static final LoggerFactory LOGGER_FACTORY;
    private static final String LOGGER_FACTORY_IMPLEMENTATION = "twitter4j.loggerFactory";

    static {
        LoggerFactory loggerFactory = null;
        String property = System.getProperty(LOGGER_FACTORY_IMPLEMENTATION);
        if (property != null) {
            loggerFactory = getLoggerFactoryIfAvailable(property, property);
        }
        Configuration instance = ConfigurationContext.getInstance();
        String loggerFactory2 = instance.getLoggerFactory();
        if (loggerFactory2 != null) {
            loggerFactory = getLoggerFactoryIfAvailable(loggerFactory2, loggerFactory2);
        }
        if (loggerFactory == null) {
            loggerFactory = getLoggerFactoryIfAvailable("org.slf4j.impl.StaticLoggerBinder", "twitter4j.SLF4JLoggerFactory");
        }
        if (loggerFactory == null) {
            loggerFactory = getLoggerFactoryIfAvailable("org.apache.commons.logging.Log", "twitter4j.CommonsLoggingLoggerFactory");
        }
        if (loggerFactory == null) {
            loggerFactory = getLoggerFactoryIfAvailable("org.apache.log4j.Logger", "twitter4j.Log4JLoggerFactory");
        }
        if (loggerFactory == null) {
            loggerFactory = getLoggerFactoryIfAvailable("com.google.appengine.api.urlfetch.URLFetchService", "twitter4j.JULLoggerFactory");
        }
        if (loggerFactory == null) {
            loggerFactory = new StdOutLoggerFactory();
        }
        LOGGER_FACTORY = loggerFactory;
        try {
            Method method = instance.getClass().getMethod("dumpConfiguration", new Class[0]);
            method.setAccessible(true);
            method.invoke(instance, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
        }
    }

    public static Logger getLogger(Class cls) {
        return LOGGER_FACTORY.getLogger(cls);
    }

    private static LoggerFactory getLoggerFactoryIfAvailable(String str, String str2) {
        try {
            Class.forName(str);
            return (LoggerFactory) Class.forName(str2).newInstance();
        } catch (InstantiationException e) {
            throw new AssertionError(e);
        } catch (ClassNotFoundException | SecurityException e2) {
            return null;
        } catch (IllegalAccessException e3) {
            throw new AssertionError(e3);
        }
    }

    public abstract void debug(String str);

    public abstract void debug(String str, String str2);

    public abstract void error(String str);

    public abstract void error(String str, Throwable th);

    public abstract void info(String str);

    public abstract void info(String str, String str2);

    public abstract boolean isDebugEnabled();

    public abstract boolean isErrorEnabled();

    public abstract boolean isInfoEnabled();

    public abstract boolean isWarnEnabled();

    public abstract void warn(String str);

    public abstract void warn(String str, String str2);
}
