package jp.newgate.game.android.dw.util;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class IabException extends Exception {
    IabResult mResult;

    public IabException(int i, String str) {
        this(new IabResult(i, str));
    }

    public IabException(int i, String str, Exception exc) {
        this(new IabResult(i, str), exc);
    }

    public IabException(IabResult iabResult) {
        this(iabResult, (Exception) null);
    }

    public IabException(IabResult iabResult, Exception exc) {
        super(iabResult.getMessage(), exc);
        this.mResult = iabResult;
    }

    public IabResult getResult() {
        return this.mResult;
    }
}
