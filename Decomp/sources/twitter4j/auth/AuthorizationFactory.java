package twitter4j.auth;

public final class AuthorizationFactory {
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: twitter4j.auth.BasicAuthorization} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: twitter4j.auth.BasicAuthorization} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: twitter4j.auth.BasicAuthorization} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: twitter4j.auth.OAuthAuthorization} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: twitter4j.auth.BasicAuthorization} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v0, resolved type: twitter4j.auth.OAuth2Authorization} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: twitter4j.auth.BasicAuthorization} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static twitter4j.auth.Authorization getInstance(twitter4j.conf.Configuration r11) {
        /*
            r2 = 0
            java.lang.String r3 = r11.getOAuthConsumerKey()
            java.lang.String r4 = r11.getOAuthConsumerSecret()
            if (r3 == 0) goto L_0x004f
            if (r4 == 0) goto L_0x004f
            boolean r10 = r11.isApplicationOnlyAuthEnabled()
            if (r10 == 0) goto L_0x0034
            twitter4j.auth.OAuth2Authorization r6 = new twitter4j.auth.OAuth2Authorization
            r6.<init>(r11)
            java.lang.String r9 = r11.getOAuth2TokenType()
            java.lang.String r0 = r11.getOAuth2AccessToken()
            if (r9 == 0) goto L_0x002c
            if (r0 == 0) goto L_0x002c
            twitter4j.auth.OAuth2Token r10 = new twitter4j.auth.OAuth2Token
            r10.<init>(r9, r0)
            r6.setOAuth2Token(r10)
        L_0x002c:
            r2 = r6
        L_0x002d:
            if (r2 != 0) goto L_0x0033
            twitter4j.auth.NullAuthorization r2 = twitter4j.auth.NullAuthorization.getInstance()
        L_0x0033:
            return r2
        L_0x0034:
            twitter4j.auth.OAuthAuthorization r5 = new twitter4j.auth.OAuthAuthorization
            r5.<init>(r11)
            java.lang.String r0 = r11.getOAuthAccessToken()
            java.lang.String r1 = r11.getOAuthAccessTokenSecret()
            if (r0 == 0) goto L_0x004d
            if (r1 == 0) goto L_0x004d
            twitter4j.auth.AccessToken r10 = new twitter4j.auth.AccessToken
            r10.<init>(r0, r1)
            r5.setOAuthAccessToken(r10)
        L_0x004d:
            r2 = r5
            goto L_0x002d
        L_0x004f:
            java.lang.String r8 = r11.getUser()
            java.lang.String r7 = r11.getPassword()
            if (r8 == 0) goto L_0x002d
            if (r7 == 0) goto L_0x002d
            twitter4j.auth.BasicAuthorization r2 = new twitter4j.auth.BasicAuthorization
            r2.<init>(r8, r7)
            goto L_0x002d
        */
        throw new UnsupportedOperationException("Method not decompiled: twitter4j.auth.AuthorizationFactory.getInstance(twitter4j.conf.Configuration):twitter4j.auth.Authorization");
    }
}
