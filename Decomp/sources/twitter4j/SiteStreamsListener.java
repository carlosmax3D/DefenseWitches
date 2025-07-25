package twitter4j;

public interface SiteStreamsListener extends StreamListener {
    void onBlock(long j, User user, User user2);

    void onDeletionNotice(long j, long j2, long j3);

    void onDeletionNotice(long j, StatusDeletionNotice statusDeletionNotice);

    void onDirectMessage(long j, DirectMessage directMessage);

    void onDisconnectionNotice(String str);

    void onException(Exception exc);

    void onFavorite(long j, User user, User user2, Status status);

    void onFollow(long j, User user, User user2);

    void onFriendList(long j, long[] jArr);

    void onStatus(long j, Status status);

    void onUnblock(long j, User user, User user2);

    void onUnfavorite(long j, User user, User user2, Status status);

    void onUnfollow(long j, User user, User user2);

    void onUserListCreation(long j, User user, UserList userList);

    void onUserListDeletion(long j, User user, UserList userList);

    void onUserListMemberAddition(long j, User user, User user2, UserList userList);

    void onUserListMemberDeletion(long j, User user, User user2, UserList userList);

    void onUserListSubscription(long j, User user, User user2, UserList userList);

    void onUserListUnsubscription(long j, User user, User user2, UserList userList);

    void onUserListUpdate(long j, User user, UserList userList);

    void onUserProfileUpdate(long j, User user);
}
