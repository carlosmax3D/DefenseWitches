package twitter4j;

public interface UserStreamListener extends StatusListener {
    void onBlock(User user, User user2);

    void onDeletionNotice(long j, long j2);

    void onDirectMessage(DirectMessage directMessage);

    void onFavorite(User user, User user2, Status status);

    void onFollow(User user, User user2);

    void onFriendList(long[] jArr);

    void onUnblock(User user, User user2);

    void onUnfavorite(User user, User user2, Status status);

    void onUnfollow(User user, User user2);

    void onUserListCreation(User user, UserList userList);

    void onUserListDeletion(User user, UserList userList);

    void onUserListMemberAddition(User user, User user2, UserList userList);

    void onUserListMemberDeletion(User user, User user2, UserList userList);

    void onUserListSubscription(User user, User user2, UserList userList);

    void onUserListUnsubscription(User user, User user2, UserList userList);

    void onUserListUpdate(User user, UserList userList);

    void onUserProfileUpdate(User user);
}
