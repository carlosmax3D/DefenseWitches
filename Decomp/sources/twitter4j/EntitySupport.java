package twitter4j;

public interface EntitySupport {
    MediaEntity[] getExtendedMediaEntities();

    HashtagEntity[] getHashtagEntities();

    MediaEntity[] getMediaEntities();

    SymbolEntity[] getSymbolEntities();

    URLEntity[] getURLEntities();

    UserMentionEntity[] getUserMentionEntities();
}
