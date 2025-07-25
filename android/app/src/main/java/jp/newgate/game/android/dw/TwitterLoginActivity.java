package jp.newgate.game.android.dw;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import jp.stargarage.g2metrics.BuildConfig;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class TwitterLoginActivity extends Activity {
    public static final int TWITTER_LOGIN_RESULT_CODE_FAILURE = 2;
    public static final int TWITTER_LOGIN_RESULT_CODE_SUCCESS = 1;
    public static String TWITTER_USER_ID = "TWITTER_USER_ID";
    public static String TWITTER_USER_NAME = "TWITTER_USER_NAME";
    private static Object requestToken;
    private static Object twitter;
    private String TAG = "T4JTwitterLoginActivity";
    private ProgressDialog mProgressDialog;
    private SharedPreferences preferences;
    public String twitterConsumerKey;
    public String twitterConsumerSecret;
    private WebView twitterLoginWebView;

    private void askOAuth() {
/*        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(this.twitterConsumerKey);
        configurationBuilder.setOAuthConsumerSecret(this.twitterConsumerSecret);
        twitter = new TwitterFactory(configurationBuilder.build()).getInstance();
        new Thread(new Runnable() { // from class: jp.newgate.game.android.dw.TwitterLoginActivity.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    RequestToken unused = TwitterLoginActivity.requestToken = TwitterLoginActivity.twitter.getOAuthRequestToken(Constants.TWITTER_CALLBACK_URL);
                    TwitterLoginActivity.this.runOnUiThread(new Runnable() { // from class: jp.newgate.game.android.dw.TwitterLoginActivity.3.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Log.d(TwitterLoginActivity.this.TAG, "LOADING AUTH URL");
                            TwitterLoginActivity.this.twitterLoginWebView.loadUrl(TwitterLoginActivity.requestToken.getAuthenticationURL());
                            Log.d(TwitterLoginActivity.this.TAG, "request token: " + TwitterLoginActivity.requestToken.getAuthenticationURL());
                        }
                    });
                } catch (Exception e) {
                    final String string = e.toString();
                    TwitterLoginActivity.this.runOnUiThread(new Runnable() { // from class: jp.newgate.game.android.dw.TwitterLoginActivity.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            TwitterLoginActivity.this.mProgressDialog.cancel();
                            Log.d(TwitterLoginActivity.this.TAG, string.toString());
                            TwitterLoginActivity.this.finish();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();*/
    }

    public static String getAccessToken(Context context) {
        return context.getSharedPreferences(Constants.PREFERENCE_NAME, 0).getString(Constants.TWITTER_KEY_TOKEN_NAME, null);
    }

    public static String getAccessTokenSecret(Context context) {
        return context.getSharedPreferences(Constants.PREFERENCE_NAME, 0).getString(Constants.TWITTER_KEY_SECRET_NAME, null);
    }

    private void getConsumerKeys() {
        this.twitterConsumerKey = getIntent().getStringExtra(Constants.TWITTER_CONSUMER_KEY_NAME);
        this.twitterConsumerSecret = getIntent().getStringExtra(Constants.TWITTER_CONSUMER_SECRET_NAME);
    }

    public static boolean isConnected(Context context) {
        return context.getSharedPreferences(Constants.PREFERENCE_NAME, 0).getString(Constants.TWITTER_KEY_TOKEN_NAME, null) != null;
    }

    public static void logOutOfTwitter(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(Constants.PREFERENCE_NAME, 0).edit();
        editorEdit.putString(Constants.TWITTER_KEY_TOKEN_NAME, null);
        editorEdit.putString(Constants.TWITTER_KEY_SECRET_NAME, null);
        editorEdit.commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveAccessTokenAndFinish(final Uri uri) {
        new Thread(new Runnable() { // from class: jp.newgate.game.android.dw.TwitterLoginActivity.2
            @Override // java.lang.Runnable
            public void run() {
                String queryParameter = uri.getQueryParameter(Constants.IEXTRA_OAUTH_VERIFIER);
                try {
/*                    SharedPreferences sharedPreferences = TwitterLoginActivity.this.getSharedPreferences(Constants.PREFERENCE_NAME, 0);
                    AccessToken oAuthAccessToken = TwitterLoginActivity.twitter.getOAuthAccessToken(TwitterLoginActivity.requestToken, queryParameter);
                    SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                    editorEdit.putString(Constants.TWITTER_KEY_TOKEN_NAME, oAuthAccessToken.getToken());
                    editorEdit.putString(Constants.TWITTER_KEY_SECRET_NAME, oAuthAccessToken.getTokenSecret());
                    editorEdit.commit();
                    TwitterStream twitterStreamFactory = new TwitterStreamFactory(new ConfigurationBuilder().setOAuthConsumerKey(TwitterLoginActivity.this.twitterConsumerKey).setOAuthConsumerSecret(TwitterLoginActivity.this.twitterConsumerSecret).setOAuthAccessToken(oAuthAccessToken.getToken()).setOAuthAccessTokenSecret(oAuthAccessToken.getTokenSecret()).build()).getInstance();
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(TwitterLoginActivity.TWITTER_USER_ID, BuildConfig.FLAVOR + twitterStreamFactory.getId());
                    bundle.putString(TwitterLoginActivity.TWITTER_USER_NAME, twitterStreamFactory.getScreenName());
                    intent.putExtras(bundle);
                    TwitterLoginActivity.this.setResult(1, intent);*/
                } catch (Exception e) {
                    e.printStackTrace();
                    if (e.getMessage() != null) {
                        Log.e(TwitterLoginActivity.this.TAG, e.getMessage());
                    } else {
                        Log.e(TwitterLoginActivity.this.TAG, "ERROR: Twitter callback failed");
                    }
//                    TwitterLoginActivity.this.setResult(2);
                }
//                TwitterLoginActivity.this.finish();
            }
        }).start();
    }

    private void showLoginWebView() {
        this.mProgressDialog = new ProgressDialog(this);
        this.mProgressDialog.setMessage("Please wait...");
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.setCanceledOnTouchOutside(false);
        this.mProgressDialog.show();
        this.twitterLoginWebView = (WebView) findViewById(C1242R.id.twitter_login_web_view);
        this.twitterLoginWebView.setWebViewClient(new WebViewClient() { // from class: jp.newgate.game.android.dw.TwitterLoginActivity.1
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                if (TwitterLoginActivity.this.mProgressDialog != null) {
                    TwitterLoginActivity.this.mProgressDialog.dismiss();
                }
            }

            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
                if (TwitterLoginActivity.this.mProgressDialog != null) {
                    TwitterLoginActivity.this.mProgressDialog.show();
                }
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (!str.contains(Constants.TWITTER_CALLBACK_URL)) {
                    return false;
                }
                TwitterLoginActivity.this.saveAccessTokenAndFinish(Uri.parse(str));
                return true;
            }
        });
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1242R.layout.activity_twitter_login);
        getConsumerKeys();
        if (this.twitterConsumerKey == null || this.twitterConsumerSecret == null) {
            Log.e(this.TAG, "ERROR: Consumer Key and Consumer Secret required!");
            setResult(2);
            finish();
        }
        showLoginWebView();
        askOAuth();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (this.mProgressDialog != null) {
            this.mProgressDialog.dismiss();
        }
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
    }
}
