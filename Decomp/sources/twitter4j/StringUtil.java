package twitter4j;

class StringUtil {
    private StringUtil() {
        throw new AssertionError();
    }

    public static String join(long[] jArr) {
        StringBuilder sb = new StringBuilder(jArr.length * 11);
        for (long j : jArr) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(j);
        }
        return sb.toString();
    }

    public static String join(String[] strArr) {
        StringBuilder sb = new StringBuilder(strArr.length * 11);
        for (String str : strArr) {
            if (sb.length() != 0) {
                sb.append(",");
            }
            sb.append(str);
        }
        return sb.toString();
    }
}
