package twitter4j;

public class SiteStreamsAdapter implements SiteStreamsListener {
    public void onBlock(long j, User user, User user2) {
    }

    public void onDeletionNotice(long j, long j2, long j3) {
    }

    public void onDeletionNotice(long j, StatusDeletionNotice statusDeletionNotice) {
    }

    public void onDirectMessage(long j, DirectMessage directMessage) {
    }

    public void onDisconnectionNotice(String str) {
    }

    public void onException(Exception exc) {
    }

    public void onFavorite(long j, User user, User user2, Status status) {
    }

    public void onFollow(long j, User user, User user2) {
    }

    public void onFriendList(long j, long[] jArr) {
    }

    public void onStatus(long j, Status status) {
    }

    public void onUnblock(long j, User user, User user2) {
    }

    public void onUnfavorite(long j, User user, User user2, Status status) {
    }

    public void onUnfollow(long j, User user, User user2) {
    }

    public void onUserListCreation(long j, User user, UserList userList) {
    }

    public void onUserListDeletion(long j, User user, UserList userList) {
    }

    public void onUserListMemberAddition(long j, User user, User user2, UserList userList) {
    }

    public void onUserListMemberDeletion(long j, User user, User user2, UserList userList) {
    }

    public void onUserListSubscription(long j, User user, User user2, UserList userList) {
    }

    public void onUserListUnsubscription(long j, User user, User user2, UserList userList) {
    }

    public void onUserListUpdate(long j, User user, UserList userList) {
    }

    public void onUserProfileUpdate(long j, User user) {
    }
}
