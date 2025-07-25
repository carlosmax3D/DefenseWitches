package com.ansca.corona;

import android.os.Handler;

public class MessageBasedTimer {
    public static final TimeSpan MIN_INTERVAL = TimeSpan.fromMilliseconds(5);
    private Handler fHandler = null;
    private TimeSpan fInterval = TimeSpan.fromMinutes(1);
    private boolean fIsRunning = false;
    private Listener fListener = null;
    private Ticks fNextElapseTimeInTicks = Ticks.fromCurrentTime();
    private Runnable fRunnable = null;

    public interface Listener {
        void onTimerElapsed();
    }

    /* access modifiers changed from: private */
    public void onElapsed() {
        if (this.fIsRunning) {
            if (this.fListener != null) {
                this.fListener.onTimerElapsed();
            }
            if (this.fIsRunning) {
                Ticks fromCurrentTime = Ticks.fromCurrentTime();
                do {
                    this.fNextElapseTimeInTicks = this.fNextElapseTimeInTicks.add(this.fInterval);
                } while (this.fNextElapseTimeInTicks.compareTo(fromCurrentTime) <= 0);
                this.fHandler.postDelayed(this.fRunnable, this.fNextElapseTimeInTicks.subtract(fromCurrentTime).getTotalMilliseconds());
            }
        }
    }

    public Handler getHandler() {
        return this.fHandler;
    }

    public TimeSpan getInterval() {
        return this.fInterval;
    }

    public Listener getListener() {
        return this.fListener;
    }

    public boolean isNotRunning() {
        return !this.fIsRunning;
    }

    public boolean isRunning() {
        return this.fIsRunning;
    }

    public void setHandler(Handler handler) {
        if (handler != this.fHandler) {
            boolean z = this.fIsRunning;
            if (this.fIsRunning) {
                stop();
            }
            this.fHandler = handler;
            if (z && handler != null) {
                start();
            }
        }
    }

    public void setInterval(TimeSpan timeSpan) {
        if (timeSpan == null) {
            throw new NullPointerException();
        }
        if (timeSpan.compareTo(MIN_INTERVAL) <= 0) {
            timeSpan = MIN_INTERVAL;
        }
        if (!timeSpan.equals(this.fInterval)) {
            this.fInterval = timeSpan;
        }
    }

    public void setListener(Listener listener) {
        this.fListener = listener;
    }

    public void start() {
        if (!this.fIsRunning && this.fHandler != null) {
            this.fRunnable = new Runnable() {
                public void run() {
                    MessageBasedTimer.this.onElapsed();
                }
            };
            this.fIsRunning = true;
            this.fNextElapseTimeInTicks = Ticks.fromCurrentTime().add(this.fInterval);
            this.fHandler.postDelayed(this.fRunnable, this.fInterval.getTotalMilliseconds());
        }
    }

    public void stop() {
        if (this.fIsRunning) {
            if (!(this.fHandler == null || this.fRunnable == null)) {
                this.fHandler.removeCallbacks(this.fRunnable);
                this.fRunnable = null;
            }
            this.fIsRunning = false;
        }
    }
}
