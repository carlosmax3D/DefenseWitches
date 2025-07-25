package jp.newgate.game.android.dw;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.GraphResponse;
import com.tapjoy.TJAdUnitConstants;
import java.io.File;
import jp.stargarage.g2metrics.BuildConfig;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class CoronaNativeActivity extends Activity {
    private static final int TWITTER_LOGIN_REQUEST_CODE = 1;
    /* access modifiers changed from: private */
    public String TAG = "CoronaNativeActivity";
    /* access modifiers changed from: private */
    public String caption;
    private String command = BuildConfig.FLAVOR;
    /* access modifiers changed from: private */
    public String googlePlayUrl = "https://play.google.com/store/apps/details?id=jp.newgate.game.android.dw";
    private String msg;
    private ProgressDialog pDialog;
    private SharedPreferences preferences;
    /* access modifiers changed from: private */
    public Dialog shareTwitter;
    /* access modifiers changed from: private */
    public String url;

    class TweetPoster extends AsyncTask<String, Void, String> {
        private File image_path;
        private String message;
        ProgressDialog pDialog;
        private Twitter twitter;

        TweetPoster() {
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... strArr) {
            try {
                Log.d(CoronaNativeActivity.this.TAG, "do in background share twitter");
                return CoronaNativeActivity.this.sharePicAndTextTitter(this.image_path, this.message, this.twitter);
            } catch (Exception e) {
                if (e.getMessage().toString().contains("duplicate")) {
                    return "Posting Failed because of Duplicate message...";
                }
                e.printStackTrace();
                return "Posting Failed!!!";
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String str) {
            Toast.makeText(CoronaNativeActivity.this, str, 1).show();
            super.onPostExecute(str);
            this.pDialog.dismiss();
            CoronaNativeActivity.this.shareTwitter.dismiss();
            CoronaNativeActivity.this.onBackPressed();
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            if (this.pDialog == null) {
                this.pDialog = new ProgressDialog(CoronaNativeActivity.this);
                this.pDialog.setMessage("Posting Twitt...");
                this.pDialog.setCancelable(false);
            }
            if (!this.pDialog.isShowing()) {
                this.pDialog.show();
            }
            super.onPreExecute();
        }

        public TweetPoster setImagePath(File file) {
            this.image_path = file;
            return this;
        }

        public TweetPoster setMessage(String str) {
            this.message = str;
            return this;
        }

        public TweetPoster setTwitter(Twitter twitter2) {
            this.twitter = twitter2;
            return this;
        }
    }

    private void authenTwitter() {
        if (!TwitterLoginActivity.isConnected(this)) {
            Intent intent = new Intent(this, TwitterLoginActivity.class);
            intent.putExtra(Constants.TWITTER_CONSUMER_KEY_NAME, Constants.TWITTER_CONSUMER_KEY_VALUE);
            intent.putExtra(Constants.TWITTER_CONSUMER_SECRET_NAME, Constants.TWITTER_CONSUMER_SECRET_VALUE);
            startActivityForResult(intent, 1);
        }
    }

    private void goBack() {
        onBackPressed();
    }

    private boolean isTWAuthenticated() {
        return this.preferences.getString(Constants.TWITTER_KEY_TOKEN_NAME, (String) null) != null;
    }

    private void shareTwitter() {
        Log.e("shareTwitter", "msg: " + this.msg);
        if (isTWAuthenticated()) {
            showTWShareDialog();
        } else {
            authenTwitter();
        }
    }

    private void showInfoDialog(String str, String str2) {
        new AlertDialog.Builder(this).setTitle(str).setMessage(str2).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                CoronaNativeActivity.this.onBackPressed();
            }
        }).show();
    }

    private void showTWShareDialog() {
        View inflate = getLayoutInflater().inflate(R.layout.twitter_share_layout, (ViewGroup) null);
        this.shareTwitter = new Dialog(this, 16973941);
        this.shareTwitter.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                CoronaNativeActivity.this.onBackPressed();
            }
        });
        this.shareTwitter.setContentView(inflate);
        this.shareTwitter.show();
        ((TextView) inflate.findViewById(R.id.link)).setText(this.caption + " " + this.url);
        ((TextView) inflate.findViewById(R.id.link)).setText(Html.fromHtml(this.googlePlayUrl));
        View findViewById = inflate.findViewById(R.id.btnCancel);
        View findViewById2 = inflate.findViewById(R.id.btnTwitter);
        findViewById.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CoronaNativeActivity.this.shareTwitter.dismiss();
            }
        });
        findViewById2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Twitter instance = new TwitterFactory().getInstance();
                instance.setOAuthConsumer(Constants.TWITTER_CONSUMER_KEY_VALUE, Constants.TWITTER_CONSUMER_SECRET_VALUE);
                AccessToken accessToken = new AccessToken(CoronaNativeActivity.this.getAccessToken(), CoronaNativeActivity.this.getAccessTokenSecret());
                Log.d(CoronaNativeActivity.this.TAG, "access token: " + CoronaNativeActivity.this.getAccessToken());
                Log.d(CoronaNativeActivity.this.TAG, "access token secret: " + CoronaNativeActivity.this.getAccessTokenSecret());
                instance.setOAuthAccessToken(accessToken);
                new TweetPoster().setTwitter(instance).setMessage(CoronaNativeActivity.this.caption + " " + CoronaNativeActivity.this.googlePlayUrl).execute(new String[0]);
                Log.e("test", "url" + CoronaNativeActivity.this.url);
            }
        });
    }

    private void showWarningDialog(String str, String str2) {
        new AlertDialog.Builder(this).setTitle(str).setMessage(str2).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                CoronaNativeActivity.this.onBackPressed();
            }
        }).setIcon(17301543).show();
    }

    public String getAccessToken() {
        return this.preferences.getString(Constants.TWITTER_KEY_TOKEN_NAME, (String) null);
    }

    public String getAccessTokenSecret() {
        return this.preferences.getString(Constants.TWITTER_KEY_SECRET_NAME, (String) null);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1) {
            Log.e(this.TAG, "twitter login request code");
            if (i2 == 1) {
                Log.e(this.TAG, "twitter login success");
                showTWShareDialog();
            } else if (i2 == 2) {
                Log.d(this.TAG, "twitter login fail");
                onBackPressed();
            } else {
                Log.e(this.TAG, "other twitter login fail");
                onBackPressed();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.preferences = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        this.command = getIntent().getExtras().getString(TJAdUnitConstants.String.COMMAND);
        if (this.command.equals("shareTW")) {
            Intent intent = getIntent();
            this.msg = intent.getExtras().getString("msg");
            this.caption = intent.getExtras().getString("caption");
            this.url = intent.getExtras().getString("url");
            shareTwitter();
        }
    }

    public String sharePicAndTextTitter(File file, String str, Twitter twitter) throws Exception {
        try {
            StatusUpdate statusUpdate = new StatusUpdate(str);
            if (file != null) {
                statusUpdate.setMedia(file);
            }
            twitter.updateStatus(statusUpdate);
            return GraphResponse.SUCCESS_KEY;
        } catch (TwitterException e) {
            if (e.getStatusCode() == -1) {
            }
            return e.getMessage().toString().contains("suspended") ? "Failed to update on Twitter\nYour account is suspended and is not permitted to tweet!" : e.getMessage().toString().contains("duplicate") ? "Failed to update on Twitter\nYou cannot post the same status!" : "Failed to update on Twitter";
        } catch (Exception e2) {
            e2.printStackTrace();
            return "Unknow error";
        }
    }
}
