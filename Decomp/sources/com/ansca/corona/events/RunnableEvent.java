package com.ansca.corona.events;

public class RunnableEvent extends Event {
    private Runnable fRunnable;

    public RunnableEvent(Runnable runnable) {
        this.fRunnable = runnable;
    }

    public void Send() {
        if (this.fRunnable != null) {
            this.fRunnable.run();
        }
    }
}
