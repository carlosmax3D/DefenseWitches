package twitter4j;

import android.support.v4.widget.ExploreByTouchHelper;
import com.facebook.share.internal.ShareConstants;
import java.io.Serializable;

class StatusDeletionNoticeImpl implements StatusDeletionNotice, Serializable {
    private static final long serialVersionUID = 9144204870473786368L;
    private final long statusId;
    private final long userId;

    StatusDeletionNoticeImpl(JSONObject jSONObject) {
        this.statusId = ParseUtil.getLong(ShareConstants.WEB_DIALOG_PARAM_ID, jSONObject);
        this.userId = ParseUtil.getLong("user_id", jSONObject);
    }

    public int compareTo(StatusDeletionNotice statusDeletionNotice) {
        long statusId2 = this.statusId - statusDeletionNotice.getStatusId();
        if (statusId2 < -2147483648L) {
            return ExploreByTouchHelper.INVALID_ID;
        }
        if (statusId2 > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) statusId2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StatusDeletionNoticeImpl statusDeletionNoticeImpl = (StatusDeletionNoticeImpl) obj;
        if (this.statusId != statusDeletionNoticeImpl.statusId) {
            return false;
        }
        return this.userId == statusDeletionNoticeImpl.userId;
    }

    public long getStatusId() {
        return this.statusId;
    }

    public long getUserId() {
        return this.userId;
    }

    public int hashCode() {
        return (((int) (this.statusId ^ (this.statusId >>> 32))) * 31) + ((int) (this.userId ^ (this.userId >>> 32)));
    }

    public String toString() {
        return "StatusDeletionNoticeImpl{statusId=" + this.statusId + ", userId=" + this.userId + '}';
    }
}
