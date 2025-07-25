package twitter4j;

import java.io.Serializable;

public final class StallWarning implements Serializable {
    private static final long serialVersionUID = -4294628635422470314L;
    private final String code;
    private final String message;
    private final int percentFull;

    StallWarning(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = jSONObject.getJSONObject("warning");
        this.code = ParseUtil.getRawString("code", jSONObject2);
        this.message = ParseUtil.getRawString("message", jSONObject2);
        this.percentFull = ParseUtil.getInt("percent_full", jSONObject2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StallWarning stallWarning = (StallWarning) obj;
        if (this.percentFull != stallWarning.percentFull) {
            return false;
        }
        if (this.code == null ? stallWarning.code != null : !this.code.equals(stallWarning.code)) {
            return false;
        }
        if (this.message != null) {
            if (this.message.equals(stallWarning.message)) {
                return true;
            }
        } else if (stallWarning.message == null) {
            return true;
        }
        return false;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public int getPercentFull() {
        return this.percentFull;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (this.code != null ? this.code.hashCode() : 0) * 31;
        if (this.message != null) {
            i = this.message.hashCode();
        }
        return ((hashCode + i) * 31) + this.percentFull;
    }

    public String toString() {
        return "StallWarning{code='" + this.code + '\'' + ", message='" + this.message + '\'' + ", percentFull=" + this.percentFull + '}';
    }
}
