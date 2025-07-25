package twitter4j.auth;

import com.tapjoy.TapjoyConstants;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import jp.newgate.game.android.dw.Constants;
import jp.stargarage.g2metrics.BuildConfig;
import twitter4j.BASE64Encoder;
import twitter4j.HttpClient;
import twitter4j.HttpClientFactory;
import twitter4j.HttpParameter;
import twitter4j.HttpRequest;
import twitter4j.HttpResponseListener;
import twitter4j.Logger;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;

public class OAuthAuthorization implements Authorization, Serializable, OAuthSupport {
    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final HttpParameter OAUTH_SIGNATURE_METHOD = new HttpParameter(TapjoyConstants.TJC_NOTIFICATION_OAUTH_SIGNATURE_METHOD, "HMAC-SHA1");
    private static final Random RAND = new Random();
    private static transient HttpClient http = null;
    private static final Logger logger = Logger.getLogger(OAuthAuthorization.class);
    private static final long serialVersionUID = -886869424811858868L;
    private final Configuration conf;
    private String consumerKey = BuildConfig.FLAVOR;
    private String consumerSecret;
    private OAuthToken oauthToken = null;
    private String realm = null;

    public OAuthAuthorization(Configuration configuration) {
        this.conf = configuration;
        http = HttpClientFactory.getInstance(configuration.getHttpClientConfiguration());
        setOAuthConsumer(configuration.getOAuthConsumerKey(), configuration.getOAuthConsumerSecret());
        if (configuration.getOAuthAccessToken() != null && configuration.getOAuthAccessTokenSecret() != null) {
            setOAuthAccessToken(new AccessToken(configuration.getOAuthAccessToken(), configuration.getOAuthAccessTokenSecret()));
        }
    }

    static String constructRequestURL(String str) {
        int indexOf = str.indexOf("?");
        if (-1 != indexOf) {
            str = str.substring(0, indexOf);
        }
        int indexOf2 = str.indexOf("/", 8);
        String lowerCase = str.substring(0, indexOf2).toLowerCase();
        int indexOf3 = lowerCase.indexOf(":", 8);
        if (-1 != indexOf3) {
            if (lowerCase.startsWith("http://") && lowerCase.endsWith(":80")) {
                lowerCase = lowerCase.substring(0, indexOf3);
            } else if (lowerCase.startsWith("https://") && lowerCase.endsWith(":443")) {
                lowerCase = lowerCase.substring(0, indexOf3);
            }
        }
        return lowerCase + str.substring(indexOf2);
    }

    public static String encodeParameters(List<HttpParameter> list) {
        return encodeParameters(list, "&", false);
    }

    public static String encodeParameters(List<HttpParameter> list, String str, boolean z) {
        StringBuilder sb = new StringBuilder();
        for (HttpParameter next : list) {
            if (!next.isFile()) {
                if (sb.length() != 0) {
                    if (z) {
                        sb.append("\"");
                    }
                    sb.append(str);
                }
                sb.append(HttpParameter.encode(next.getName())).append("=");
                if (z) {
                    sb.append("\"");
                }
                sb.append(HttpParameter.encode(next.getValue()));
            }
        }
        if (sb.length() != 0 && z) {
            sb.append("\"");
        }
        return sb.toString();
    }

    private void ensureTokenIsAvailable() {
        if (this.oauthToken == null) {
            throw new IllegalStateException("No Token available.");
        }
    }

    private static String normalizeRequestParameters(List<HttpParameter> list) {
        Collections.sort(list);
        return encodeParameters(list);
    }

    static String normalizeRequestParameters(HttpParameter[] httpParameterArr) {
        return normalizeRequestParameters(toParamList(httpParameterArr));
    }

    private void parseGetParameters(String str, List<HttpParameter> list) {
        int indexOf = str.indexOf("?");
        if (-1 != indexOf) {
            str.split("&");
            try {
                for (String split : str.substring(indexOf + 1).split("&")) {
                    String[] split2 = split.split("=");
                    if (split2.length == 2) {
                        list.add(new HttpParameter(URLDecoder.decode(split2[0], "UTF-8"), URLDecoder.decode(split2[1], "UTF-8")));
                    } else {
                        list.add(new HttpParameter(URLDecoder.decode(split2[0], "UTF-8"), BuildConfig.FLAVOR));
                    }
                }
            } catch (UnsupportedEncodingException e) {
            }
        }
    }

    private static List<HttpParameter> toParamList(HttpParameter[] httpParameterArr) {
        ArrayList arrayList = new ArrayList(httpParameterArr.length);
        arrayList.addAll(Arrays.asList(httpParameterArr));
        return arrayList;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OAuthSupport)) {
            return false;
        }
        OAuthAuthorization oAuthAuthorization = (OAuthAuthorization) obj;
        if (this.consumerKey == null ? oAuthAuthorization.consumerKey != null : !this.consumerKey.equals(oAuthAuthorization.consumerKey)) {
            return false;
        }
        if (this.consumerSecret == null ? oAuthAuthorization.consumerSecret != null : !this.consumerSecret.equals(oAuthAuthorization.consumerSecret)) {
            return false;
        }
        if (this.oauthToken != null) {
            if (this.oauthToken.equals(oAuthAuthorization.oauthToken)) {
                return true;
            }
        } else if (oAuthAuthorization.oauthToken == null) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public String generateAuthorizationHeader(String str, String str2, HttpParameter[] httpParameterArr, String str3, String str4, OAuthToken oAuthToken) {
        if (httpParameterArr == null) {
            httpParameterArr = new HttpParameter[0];
        }
        ArrayList arrayList = new ArrayList(5);
        arrayList.add(new HttpParameter("oauth_consumer_key", this.consumerKey));
        arrayList.add(OAUTH_SIGNATURE_METHOD);
        arrayList.add(new HttpParameter("oauth_timestamp", str4));
        arrayList.add(new HttpParameter("oauth_nonce", str3));
        arrayList.add(new HttpParameter("oauth_version", "1.0"));
        if (oAuthToken != null) {
            arrayList.add(new HttpParameter(Constants.TWITTER_KEY_TOKEN_NAME, oAuthToken.getToken()));
        }
        ArrayList arrayList2 = new ArrayList(arrayList.size() + httpParameterArr.length);
        arrayList2.addAll(arrayList);
        if (!HttpParameter.containsFile(httpParameterArr)) {
            arrayList2.addAll(toParamList(httpParameterArr));
        }
        parseGetParameters(str2, arrayList2);
        StringBuilder append = new StringBuilder(str).append("&").append(HttpParameter.encode(constructRequestURL(str2))).append("&");
        append.append(HttpParameter.encode(normalizeRequestParameters((List<HttpParameter>) arrayList2)));
        String sb = append.toString();
        logger.debug("OAuth base string: ", sb);
        String generateSignature = generateSignature(sb, oAuthToken);
        logger.debug("OAuth signature: ", generateSignature);
        arrayList.add(new HttpParameter(TapjoyConstants.TJC_NOTIFICATION_OAUTH_SIGNATURE, generateSignature));
        if (this.realm != null) {
            arrayList.add(new HttpParameter("realm", this.realm));
        }
        return "OAuth " + encodeParameters(arrayList, ",", true);
    }

    /* access modifiers changed from: package-private */
    public String generateAuthorizationHeader(String str, String str2, HttpParameter[] httpParameterArr, OAuthToken oAuthToken) {
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        return generateAuthorizationHeader(str, str2, httpParameterArr, String.valueOf(currentTimeMillis + ((long) RAND.nextInt())), String.valueOf(currentTimeMillis), oAuthToken);
    }

    public List<HttpParameter> generateOAuthSignatureHttpParams(String str, String str2) {
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        ArrayList arrayList = new ArrayList(5);
        arrayList.add(new HttpParameter("oauth_consumer_key", this.consumerKey));
        arrayList.add(OAUTH_SIGNATURE_METHOD);
        arrayList.add(new HttpParameter("oauth_timestamp", currentTimeMillis));
        arrayList.add(new HttpParameter("oauth_nonce", currentTimeMillis + ((long) RAND.nextInt())));
        arrayList.add(new HttpParameter("oauth_version", "1.0"));
        if (this.oauthToken != null) {
            arrayList.add(new HttpParameter(Constants.TWITTER_KEY_TOKEN_NAME, this.oauthToken.getToken()));
        }
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        arrayList2.addAll(arrayList);
        parseGetParameters(str2, arrayList2);
        StringBuilder append = new StringBuilder(str).append("&").append(HttpParameter.encode(constructRequestURL(str2))).append("&");
        append.append(HttpParameter.encode(normalizeRequestParameters((List<HttpParameter>) arrayList2)));
        arrayList.add(new HttpParameter(TapjoyConstants.TJC_NOTIFICATION_OAUTH_SIGNATURE, generateSignature(append.toString(), this.oauthToken)));
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    public String generateSignature(String str) {
        return generateSignature(str, (OAuthToken) null);
    }

    /* access modifiers changed from: package-private */
    public String generateSignature(String str, OAuthToken oAuthToken) {
        SecretKeySpec secretKeySpec;
        try {
            Mac instance = Mac.getInstance(HMAC_SHA1);
            if (oAuthToken == null) {
                secretKeySpec = new SecretKeySpec((HttpParameter.encode(this.consumerSecret) + "&").getBytes(), HMAC_SHA1);
            } else {
                secretKeySpec = oAuthToken.getSecretKeySpec();
                if (secretKeySpec == null) {
                    secretKeySpec = new SecretKeySpec((HttpParameter.encode(this.consumerSecret) + "&" + HttpParameter.encode(oAuthToken.getTokenSecret())).getBytes(), HMAC_SHA1);
                    oAuthToken.setSecretKeySpec(secretKeySpec);
                }
            }
            instance.init(secretKeySpec);
            return BASE64Encoder.encode(instance.doFinal(str.getBytes()));
        } catch (InvalidKeyException e) {
            logger.error("Failed initialize \"Message Authentication Code\" (MAC)", e);
            throw new AssertionError(e);
        } catch (NoSuchAlgorithmException e2) {
            logger.error("Failed to get HmacSHA1 \"Message Authentication Code\" (MAC)", e2);
            throw new AssertionError(e2);
        }
    }

    public String getAuthorizationHeader(HttpRequest httpRequest) {
        return generateAuthorizationHeader(httpRequest.getMethod().name(), httpRequest.getURL(), httpRequest.getParameters(), this.oauthToken);
    }

    public AccessToken getOAuthAccessToken() throws TwitterException {
        ensureTokenIsAvailable();
        if (this.oauthToken instanceof AccessToken) {
            return (AccessToken) this.oauthToken;
        }
        this.oauthToken = new AccessToken(http.post(this.conf.getOAuthAccessTokenURL(), (HttpParameter[]) null, this, (HttpResponseListener) null));
        return (AccessToken) this.oauthToken;
    }

    public AccessToken getOAuthAccessToken(String str) throws TwitterException {
        ensureTokenIsAvailable();
        this.oauthToken = new AccessToken(http.post(this.conf.getOAuthAccessTokenURL(), new HttpParameter[]{new HttpParameter((String) Constants.IEXTRA_OAUTH_VERIFIER, str)}, this, (HttpResponseListener) null));
        return (AccessToken) this.oauthToken;
    }

    public AccessToken getOAuthAccessToken(String str, String str2) throws TwitterException {
        try {
            String oAuthAccessTokenURL = this.conf.getOAuthAccessTokenURL();
            if (oAuthAccessTokenURL.indexOf("http://") == 0) {
                oAuthAccessTokenURL = "https://" + oAuthAccessTokenURL.substring(7);
            }
            this.oauthToken = new AccessToken(http.post(oAuthAccessTokenURL, new HttpParameter[]{new HttpParameter("x_auth_username", str), new HttpParameter("x_auth_password", str2), new HttpParameter("x_auth_mode", "client_auth")}, this, (HttpResponseListener) null));
            return (AccessToken) this.oauthToken;
        } catch (TwitterException e) {
            throw new TwitterException("The screen name / password combination seems to be invalid.", e, e.getStatusCode());
        }
    }

    public AccessToken getOAuthAccessToken(RequestToken requestToken) throws TwitterException {
        this.oauthToken = requestToken;
        return getOAuthAccessToken();
    }

    public AccessToken getOAuthAccessToken(RequestToken requestToken, String str) throws TwitterException {
        this.oauthToken = requestToken;
        return getOAuthAccessToken(str);
    }

    public RequestToken getOAuthRequestToken() throws TwitterException {
        return getOAuthRequestToken((String) null, (String) null);
    }

    public RequestToken getOAuthRequestToken(String str) throws TwitterException {
        return getOAuthRequestToken(str, (String) null);
    }

    public RequestToken getOAuthRequestToken(String str, String str2) throws TwitterException {
        if (this.oauthToken instanceof AccessToken) {
            throw new IllegalStateException("Access token already available.");
        }
        ArrayList arrayList = new ArrayList();
        if (str != null) {
            arrayList.add(new HttpParameter("oauth_callback", str));
        }
        if (str2 != null) {
            arrayList.add(new HttpParameter("x_auth_access_type", str2));
        }
        this.oauthToken = new RequestToken(http.post(this.conf.getOAuthRequestTokenURL(), (HttpParameter[]) arrayList.toArray(new HttpParameter[arrayList.size()]), this, (HttpResponseListener) null), (OAuthSupport) this);
        return (RequestToken) this.oauthToken;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (((this.consumerKey != null ? this.consumerKey.hashCode() : 0) * 31) + (this.consumerSecret != null ? this.consumerSecret.hashCode() : 0)) * 31;
        if (this.oauthToken != null) {
            i = this.oauthToken.hashCode();
        }
        return hashCode + i;
    }

    public boolean isEnabled() {
        return this.oauthToken != null && (this.oauthToken instanceof AccessToken);
    }

    public void setOAuthAccessToken(AccessToken accessToken) {
        this.oauthToken = accessToken;
    }

    public void setOAuthConsumer(String str, String str2) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        this.consumerKey = str;
        if (str2 == null) {
            str2 = BuildConfig.FLAVOR;
        }
        this.consumerSecret = str2;
    }

    public void setOAuthRealm(String str) {
        this.realm = str;
    }

    public String toString() {
        return "OAuthAuthorization{consumerKey='" + this.consumerKey + '\'' + ", consumerSecret='******************************************'" + ", oauthToken=" + this.oauthToken + '}';
    }
}
