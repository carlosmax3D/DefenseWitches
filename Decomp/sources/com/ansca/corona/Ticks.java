package com.ansca.corona;

import android.os.SystemClock;

public class Ticks implements Comparable {
    private long fMilliseconds;

    private Ticks(long j) {
        this.fMilliseconds = j;
    }

    public Ticks(Ticks ticks) {
        if (ticks != null) {
            this.fMilliseconds = ticks.fMilliseconds;
        }
    }

    public static Ticks fromCurrentTime() {
        return new Ticks(SystemClock.elapsedRealtime());
    }

    public static Ticks fromLong(long j) {
        return new Ticks(j);
    }

    public Ticks add(TimeSpan timeSpan) {
        return timeSpan == null ? this : new Ticks(this.fMilliseconds + timeSpan.getTotalMilliseconds());
    }

    public Ticks addMilliseconds(long j) {
        return j == 0 ? this : new Ticks(this.fMilliseconds + j);
    }

    public Ticks addSeconds(long j) {
        return j == 0 ? this : new Ticks(this.fMilliseconds + (1000 * j));
    }

    public int compareTo(Ticks ticks) {
        if (ticks == null) {
            return 1;
        }
        long j = ticks.fMilliseconds;
        if (j == Long.MIN_VALUE) {
            j++;
        }
        long j2 = this.fMilliseconds - j;
        if (j2 < 0) {
            return -1;
        }
        return j2 == 0 ? 0 : 1;
    }

    public int compareTo(Object obj) {
        if (!(obj instanceof Ticks)) {
            return 1;
        }
        return compareTo((Ticks) obj);
    }

    public boolean equals(Ticks ticks) {
        return ticks != null && this.fMilliseconds == ticks.fMilliseconds;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Ticks)) {
            return false;
        }
        return equals((Ticks) obj);
    }

    public int hashCode() {
        return (int) (this.fMilliseconds ^ (this.fMilliseconds >>> 32));
    }

    public TimeSpan subtract(Ticks ticks) {
        long j = 0;
        if (ticks != null) {
            j = ticks.fMilliseconds;
        }
        if (j == Long.MIN_VALUE) {
            j++;
        }
        return TimeSpan.fromMilliseconds(this.fMilliseconds - j);
    }

    public long toLong() {
        return this.fMilliseconds;
    }

    public String toString() {
        return Long.toString(this.fMilliseconds);
    }
}
