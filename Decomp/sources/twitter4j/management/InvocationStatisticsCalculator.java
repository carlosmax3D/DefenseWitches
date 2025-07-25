package twitter4j.management;

public class InvocationStatisticsCalculator implements InvocationStatistics {
    private long callCount;
    private long errorCount;
    private int index;
    private final String name;
    private long[] times;
    private long totalTime;

    public InvocationStatisticsCalculator(String str, int i) {
        this.name = str;
        this.times = new long[i];
    }

    public synchronized long getAverageTime() {
        long j;
        int min = Math.min(Math.abs((int) this.callCount), this.times.length);
        if (min == 0) {
            j = 0;
        } else {
            long j2 = 0;
            for (int i = 0; i < min; i++) {
                j2 += this.times[i];
            }
            j = j2 / ((long) min);
        }
        return j;
    }

    public long getCallCount() {
        return this.callCount;
    }

    public long getErrorCount() {
        return this.errorCount;
    }

    public String getName() {
        return this.name;
    }

    public long getTotalTime() {
        return this.totalTime;
    }

    /* access modifiers changed from: package-private */
    public void increment(long j, boolean z) {
        long j2 = 1;
        this.callCount++;
        long j3 = this.errorCount;
        if (z) {
            j2 = 0;
        }
        this.errorCount = j2 + j3;
        this.totalTime += j;
        this.times[this.index] = j;
        int i = this.index + 1;
        this.index = i;
        if (i >= this.times.length) {
            this.index = 0;
        }
    }

    public synchronized void reset() {
        this.callCount = 0;
        this.errorCount = 0;
        this.totalTime = 0;
        this.times = new long[this.times.length];
        this.index = 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("calls=").append(getCallCount()).append(",").append("errors=").append(getErrorCount()).append(",").append("totalTime=").append(getTotalTime()).append(",").append("avgTime=").append(getAverageTime());
        return sb.toString();
    }
}
