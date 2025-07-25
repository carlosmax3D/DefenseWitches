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
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterLoginActivity extends Activity {
    public static final int TWITTER_LOGIN_RESULT_CODE_FAILURE = 2;
    public static final int TWITTER_LOGIN_RESULT_CODE_SUCCESS = 1;
    public static String TWITTER_USER_ID = "TWITTER_USER_ID";
    public static String TWITTER_USER_NAME = "TWITTER_USER_NAME";
    /* access modifiers changed from: private */
    public static RequestToken requestToken;
    /* access modifiers changed from: private */
    public static Twitter twitter;
    /* access modifiers changed from: private */
    public String TAG = "T4JTwitterLoginActivity";
    /* access modifiers changed from: private */
    public ProgressDialog mProgressDialog;
    private SharedPreferences preferences;
    public String twitterConsumerKey;
    public String twitterConsumerSecret;
    /* access modifiers changed from: private */
    public WebView twitterLoginWebView;

    private void askOAuth() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(this.twitterConsumerKey);
        configurationBuilder.setOAuthConsumerSecret(this.twitterConsumerSecret);
        twitter = new TwitterFactory(configurationBuilder.build()).getInstance();
        new Thread(new Runnable() {
            public void run() {
                try {
                    RequestToken unused = TwitterLoginActivity.requestToken = TwitterLoginActivity.twitter.getOAuthRequestToken(Constants.TWITTER_CALLBACK_URL);
                    TwitterLoginActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Log.d(TwitterLoginActivity.this.TAG, "LOADING AUTH URL");
                            TwitterLoginActivity.this.twitterLoginWebView.loadUrl(TwitterLoginActivity.requestToken.getAuthenticationURL());
                            Log.d(TwitterLoginActivity.this.TAG, "request token: " + TwitterLoginActivity.requestToken.getAuthenticationURL());
                        }
                    });
                } catch (Exception e) {
                    final String exc = e.toString();
                    TwitterLoginActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            TwitterLoginActivity.this.mProgressDialog.cancel();
                            Log.d(TwitterLoginActivity.this.TAG, exc.toString());
                            TwitterLoginActivity.this.finish();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static String getAccessToken(Context context) {
        return context.getSharedPreferences(Constants.PREFERENCE_NAME, 0).getString(Constants.TWITTER_KEY_TOKEN_NAME, (String) null);
    }

    public static String getAccessTokenSecret(Context context) {
        return context.getSharedPreferences(Constants.PREFERENCE_NAME, 0).getString(Constants.TWITTER_KEY_SECRET_NAME, (String) null);
    }

    private void getConsumerKeys() {
        this.twitterConsumerKey = getIntent().getStringExtra(Constants.TWITTER_CONSUMER_KEY_NAME);
        this.twitterConsumerSecret = getIntent().getStringExtra(Constants.TWITTER_CONSUMER_SECRET_NAME);
    }

    public static boolean isConnected(Context context) {
        return context.getSharedPreferences(Constants.PREFERENCE_NAME, 0).getString(Constants.TWITTER_KEY_TOKEN_NAME, (String) null) != null;
    }

    public static void logOutOfTwitter(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(Constants.PREFERENCE_NAME, 0).edit();
        edit.putString(Constants.TWITTER_KEY_TOKEN_NAME, (String) null);
        edit.putString(Constants.TWITTER_KEY_SECRET_NAME, (String) null);
        edit.commit();
    }

    /* access modifiers changed from: private */
    public void saveAccessTokenAndFinish(final Uri uri) {
        new Thread(new Runnable() {
            public void run() {
                String queryParameter = uri.getQueryParameter(Constants.IEXTRA_OAUTH_VERIFIER);
                try {
                    SharedPreferences sharedPreferences = TwitterLoginActivity.this.getSharedPreferences(Constants.PREFERENCE_NAME, 0);
                    AccessToken oAuthAccessToken = TwitterLoginActivity.twitter.getOAuthAccessToken(TwitterLoginActivity.requestToken, queryParameter);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString(Constants.TWITTER_KEY_TOKEN_NAME, oAuthAccessToken.getToken());
                    edit.putString(Constants.TWITTER_KEY_SECRET_NAME, oAuthAccessToken.getTokenSecret());
                    edit.commit();
                    TwitterStream instance = new TwitterStreamFactory(new ConfigurationBuilder().setOAuthConsumerKey(TwitterLoginActivity.this.twitterConsumerKey).setOAuthConsumerSecret(TwitterLoginActivity.this.twitterConsumerSecret).setOAuthAccessToken(oAuthAccessToken.getToken()).setOAuthAccessTokenSecret(oAuthAccessToken.getTokenSecret()).build()).getInstance();
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(TwitterLoginActivity.TWITTER_USER_ID, BuildConfig.FLAVOR + instance.getId());
                    bundle.putString(TwitterLoginActivity.TWITTER_USER_NAME, instance.getScreenName());
                    intent.putExtras(bundle);
                    TwitterLoginActivity.this.setResult(1, intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (e.getMessage() != null) {
                        Log.e(TwitterLoginActivity.this.TAG, e.getMessage());
                    } else {
                        Log.e(TwitterLoginActivity.this.TAG, "ERROR: Twitter callback failed");
                    }
                    TwitterLoginActivity.this.setResult(2);
                }
                TwitterLoginActivity.this.finish();
            }
        }).start();
    }

    private void showLoginWebView() {
        this.mProgressDialog = new ProgressDialog(this);
        this.mProgressDialog.setMessage("Please wait...");
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.setCanceledOnTouchOutside(false);
        this.mProgressDialog.show();
        this.twitterLoginWebView = (WebView) findViewById(R.id.twitter_login_web_view);
        this.twitterLoginWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                if (TwitterLoginActivity.this.mProgressDialog != null) {
                    TwitterLoginActivity.this.mProgressDialog.dismiss();
                }
            }

            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
                if (TwitterLoginActivity.this.mProgressDialog != null) {
                    TwitterLoginActivity.this.mProgressDialog.show();
                }
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (!str.contains(Constants.TWITTER_CALLBACK_URL)) {
                    return false;
                }
                TwitterLoginActivity.this.saveAccessTokenAndFinish(Uri.parse(str));
                return true;
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_twitter_login);
        getConsumerKeys();
        if (this.twitterConsumerKey == null || this.twitterConsumerSecret == null) {
            Log.e(this.TAG, "ERROR: Consumer Key and Consumer Secret required!");
            setResult(2);
            finish();
        }
        showLoginWebView();
        askOAuth();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.mProgressDialog != null) {
            this.mProgressDialog.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }
}
