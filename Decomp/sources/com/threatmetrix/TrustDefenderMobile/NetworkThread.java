package com.threatmetrix.TrustDefenderMobile;

final class NetworkThread extends Thread {
    private Runnable m_runnable = null;

    public NetworkThread(Runnable runnable) {
        this.m_runnable = runnable;
    }

    public final HttpRunner getHttpRunner() {
        if (this.m_runnable instanceof HttpRunner) {
            return (HttpRunner) this.m_runnable;
        }
        return null;
    }

    public final void interrupt() {
        if (this.m_runnable instanceof HttpRunner) {
            ((HttpRunner) this.m_runnable).abort();
        }
        super.interrupt();
    }

    public final void run() {
        this.m_runnable.run();
    }
}
