package twitter4j;

public class UserStreamAdapter extends StatusAdapter implements UserStreamListener {
    public void onBlock(User user, User user2) {
    }

    public void onDeletionNotice(long j, long j2) {
    }

    public void onDirectMessage(DirectMessage directMessage) {
    }

    public void onException(Exception exc) {
    }

    public void onFavorite(User user, User user2, Status status) {
    }

    public void onFollow(User user, User user2) {
    }

    public void onFriendList(long[] jArr) {
    }

    public void onUnblock(User user, User user2) {
    }

    public void onUnfavorite(User user, User user2, Status status) {
    }

    public void onUnfollow(User user, User user2) {
    }

    public void onUserListCreation(User user, UserList userList) {
    }

    public void onUserListDeletion(User user, UserList userList) {
    }

    public void onUserListMemberAddition(User user, User user2, UserList userList) {
    }

    public void onUserListMemberDeletion(User user, User user2, UserList userList) {
    }

    public void onUserListSubscription(User user, User user2, UserList userList) {
    }

    public void onUserListUnsubscription(User user, User user2, UserList userList) {
    }

    public void onUserListUpdate(User user, UserList userList) {
    }

    public void onUserProfileUpdate(User user) {
    }
}
