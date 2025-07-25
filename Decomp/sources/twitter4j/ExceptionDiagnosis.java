package twitter4j;

import java.io.Serializable;
import jp.stargarage.g2metrics.BuildConfig;

final class ExceptionDiagnosis implements Serializable {
    private static final long serialVersionUID = 8501009773274399369L;
    private String hexString;
    private int lineNumberHash;
    private int stackLineHash;

    ExceptionDiagnosis(Throwable th) {
        this(th, new String[0]);
    }

    ExceptionDiagnosis(Throwable th, String[] strArr) {
        this.hexString = BuildConfig.FLAVOR;
        Throwable th2 = th;
        StackTraceElement[] stackTrace = th.getStackTrace();
        this.stackLineHash = 0;
        this.lineNumberHash = 0;
        for (int length = stackTrace.length - 1; length >= 0; length--) {
            StackTraceElement stackTraceElement = stackTrace[length];
            int length2 = strArr.length;
            int i = 0;
            while (true) {
                if (i >= length2) {
                    break;
                }
                if (stackTraceElement.getClassName().startsWith(strArr[i])) {
                    this.stackLineHash = (this.stackLineHash * 31) + stackTraceElement.getClassName().hashCode() + stackTraceElement.getMethodName().hashCode();
                    this.lineNumberHash = (this.lineNumberHash * 31) + stackTraceElement.getLineNumber();
                    break;
                }
                i++;
            }
        }
        this.hexString += toHexString(this.stackLineHash) + AdNetworkKey.DEFAULT_AD + toHexString(this.lineNumberHash);
        if (th.getCause() != null) {
            this.hexString += " " + new ExceptionDiagnosis(th.getCause(), strArr).asHexString();
        }
    }

    private String toHexString(int i) {
        String str = "0000000" + Integer.toHexString(i);
        return str.substring(str.length() - 8, str.length());
    }

    /* access modifiers changed from: package-private */
    public String asHexString() {
        return this.hexString;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ExceptionDiagnosis exceptionDiagnosis = (ExceptionDiagnosis) obj;
        if (this.lineNumberHash != exceptionDiagnosis.lineNumberHash) {
            return false;
        }
        return this.stackLineHash == exceptionDiagnosis.stackLineHash;
    }

    /* access modifiers changed from: package-private */
    public int getLineNumberHash() {
        return this.lineNumberHash;
    }

    /* access modifiers changed from: package-private */
    public String getLineNumberHashAsHex() {
        return toHexString(this.lineNumberHash);
    }

    /* access modifiers changed from: package-private */
    public int getStackLineHash() {
        return this.stackLineHash;
    }

    /* access modifiers changed from: package-private */
    public String getStackLineHashAsHex() {
        return toHexString(this.stackLineHash);
    }

    public int hashCode() {
        return (this.stackLineHash * 31) + this.lineNumberHash;
    }

    public String toString() {
        return "ExceptionDiagnosis{stackLineHash=" + this.stackLineHash + ", lineNumberHash=" + this.lineNumberHash + '}';
    }
}
