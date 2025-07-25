package com.naef.jnlua;

public final class NativeSupport {
    private static final NativeSupport INSTANCE = new NativeSupport();
    private Loader loader = new DefaultLoader();

    private class DefaultLoader implements Loader {
        private DefaultLoader() {
        }

        public void load() {
            System.loadLibrary("jnlua5.1");
        }
    }

    public interface Loader {
        void load();
    }

    private NativeSupport() {
    }

    public static NativeSupport getInstance() {
        return INSTANCE;
    }

    public Loader getLoader() {
        return this.loader;
    }

    public void setLoader(Loader loader2) {
        if (loader2 == null) {
            throw new NullPointerException("loader must not be null");
        }
        this.loader = loader2;
    }
}
