package android.support.v4.util;

import java.io.PrintWriter;

public class TimeUtils {
    public static final int HUNDRED_DAY_FIELD_LEN = 19;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
    private static char[] sFormatStr = new char[24];
    private static final Object sFormatSync = new Object();

    private static int accumField(int i, int i2, boolean z, int i3) {
        if (i > 99 || (z && i3 >= 3)) {
            return i2 + 3;
        }
        if (i > 9 || (z && i3 >= 2)) {
            return i2 + 2;
        }
        if (z || i > 0) {
            return i2 + 1;
        }
        return 0;
    }

    public static void formatDuration(long j, long j2, PrintWriter printWriter) {
        if (j == 0) {
            printWriter.print("--");
        } else {
            formatDuration(j - j2, printWriter, 0);
        }
    }

    public static void formatDuration(long j, PrintWriter printWriter) {
        formatDuration(j, printWriter, 0);
    }

    public static void formatDuration(long j, PrintWriter printWriter, int i) {
        synchronized (sFormatSync) {
            printWriter.print(new String(sFormatStr, 0, formatDurationLocked(j, i)));
        }
    }

    public static void formatDuration(long j, StringBuilder sb) {
        synchronized (sFormatSync) {
            sb.append(sFormatStr, 0, formatDurationLocked(j, 0));
        }
    }

    private static int formatDurationLocked(long j, int i) {
        char c;
        if (sFormatStr.length < i) {
            sFormatStr = new char[i];
        }
        char[] cArr = sFormatStr;
        if (j == 0) {
            int i2 = i - 1;
            while (0 < i2) {
                cArr[0] = ' ';
            }
            cArr[0] = '0';
            return 1;
        }
        if (j > 0) {
            c = '+';
        } else {
            c = '-';
            j = -j;
        }
        int i3 = (int) (j % 1000);
        int floor = (int) Math.floor((double) (j / 1000));
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        if (floor > SECONDS_PER_DAY) {
            i4 = floor / SECONDS_PER_DAY;
            floor -= SECONDS_PER_DAY * i4;
        }
        if (floor > SECONDS_PER_HOUR) {
            i5 = floor / SECONDS_PER_HOUR;
            floor -= i5 * SECONDS_PER_HOUR;
        }
        if (floor > SECONDS_PER_MINUTE) {
            i6 = floor / SECONDS_PER_MINUTE;
            floor -= i6 * SECONDS_PER_MINUTE;
        }
        int i7 = 0;
        if (i != 0) {
            int accumField = accumField(i4, 1, false, 0);
            int accumField2 = accumField + accumField(i5, 1, accumField > 0, 2);
            int accumField3 = accumField2 + accumField(i6, 1, accumField2 > 0, 2);
            int accumField4 = accumField3 + accumField(floor, 1, accumField3 > 0, 2);
            for (int accumField5 = accumField4 + accumField(i3, 2, true, accumField4 > 0 ? 3 : 0) + 1; accumField5 < i; accumField5++) {
                cArr[i7] = ' ';
                i7++;
            }
        }
        cArr[i7] = c;
        int i8 = i7 + 1;
        int i9 = i8;
        boolean z = i != 0;
        int printField = printField(cArr, i4, 'd', i8, false, 0);
        int printField2 = printField(cArr, i5, 'h', printField, printField != i9, z ? 2 : 0);
        int printField3 = printField(cArr, i6, 'm', printField2, printField2 != i9, z ? 2 : 0);
        int printField4 = printField(cArr, floor, 's', printField3, printField3 != i9, z ? 2 : 0);
        int printField5 = printField(cArr, i3, 'm', printField4, true, (!z || printField4 == i9) ? 0 : 3);
        cArr[printField5] = 's';
        return printField5 + 1;
    }

    private static int printField(char[] cArr, int i, char c, int i2, boolean z, int i3) {
        if (!z && i <= 0) {
            return i2;
        }
        int i4 = i2;
        if ((z && i3 >= 3) || i > 99) {
            int i5 = i / 100;
            cArr[i2] = (char) (i5 + 48);
            i2++;
            i -= i5 * 100;
        }
        if ((z && i3 >= 2) || i > 9 || i4 != i2) {
            int i6 = i / 10;
            cArr[i2] = (char) (i6 + 48);
            i2++;
            i -= i6 * 10;
        }
        cArr[i2] = (char) (i + 48);
        int i7 = i2 + 1;
        cArr[i7] = c;
        return i7 + 1;
    }
}
