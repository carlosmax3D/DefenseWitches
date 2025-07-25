package com.ansca.corona;

public class TimeSpan implements Comparable {
    private long fMilliseconds;

    public TimeSpan(long j, long j2, long j3, long j4, long j5) {
        this.fMilliseconds = j5;
        this.fMilliseconds += 1000 * j4;
        this.fMilliseconds += 60000 * j3;
        this.fMilliseconds += 3600000 * j2;
        this.fMilliseconds += 86400000 * j;
    }

    public static TimeSpan fromMilliseconds(long j) {
        return new TimeSpan(0, 0, 0, 0, j);
    }

    public static TimeSpan fromMinutes(long j) {
        return new TimeSpan(0, 0, j, 0, 0);
    }

    public static TimeSpan fromSeconds(long j) {
        return new TimeSpan(0, 0, 0, j, 0);
    }

    public int compareTo(TimeSpan timeSpan) {
        if (timeSpan == null) {
            return 1;
        }
        long j = this.fMilliseconds - timeSpan.fMilliseconds;
        if (j < 0) {
            return -1;
        }
        return j == 0 ? 0 : 1;
    }

    public int compareTo(Object obj) {
        if (!(obj instanceof TimeSpan)) {
            return 1;
        }
        return compareTo((TimeSpan) obj);
    }

    public boolean equals(TimeSpan timeSpan) {
        return timeSpan != null && this.fMilliseconds == timeSpan.fMilliseconds;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TimeSpan)) {
            return false;
        }
        return equals((TimeSpan) obj);
    }

    public int getDays() {
        return (int) (this.fMilliseconds / 86400000);
    }

    public int getHours() {
        return ((int) (this.fMilliseconds / 3600000)) % 24;
    }

    public int getMilliseconds() {
        return (int) (this.fMilliseconds % 1000);
    }

    public int getMinutes() {
        return ((int) (this.fMilliseconds / 60000)) % 60;
    }

    public int getSeconds() {
        return ((int) (this.fMilliseconds / 1000)) % 60;
    }

    public double getTotalDays() {
        return ((double) this.fMilliseconds) / 8.64E7d;
    }

    public double getTotalHours() {
        return ((double) this.fMilliseconds) / 3600000.0d;
    }

    public long getTotalMilliseconds() {
        return this.fMilliseconds;
    }

    public double getTotalMinutes() {
        return ((double) this.fMilliseconds) / 60000.0d;
    }

    public double getTotalSeconds() {
        return ((double) this.fMilliseconds) / 1000.0d;
    }

    public int hashCode() {
        return (int) (this.fMilliseconds ^ (this.fMilliseconds >>> 32));
    }
}
