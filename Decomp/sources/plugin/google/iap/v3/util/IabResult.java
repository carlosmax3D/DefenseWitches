package plugin.google.iap.v3.util;

public class IabResult {
    String mMessage;
    int mResponse;

    public IabResult(int i, String str) {
        this.mResponse = i;
        if (str == null || str.trim().length() == 0) {
            this.mMessage = IabHelper.getResponseDesc(i);
        } else {
            this.mMessage = str + " (response: " + IabHelper.getResponseDesc(i) + ")";
        }
    }

    public String getMessage() {
        return this.mMessage;
    }

    public int getResponse() {
        return this.mResponse;
    }

    public boolean isFailure() {
        return !isSuccess();
    }

    public boolean isSuccess() {
        return this.mResponse == 0;
    }

    public String toString() {
        return "IabResult: " + getMessage();
    }
}
