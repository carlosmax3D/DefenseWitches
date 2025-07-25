package jp.newgate.game.android.dw;

import android.R;
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

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
public class CoronaNativeActivity extends Activity {
    private static final int TWITTER_LOGIN_REQUEST_CODE = 1;
    private String caption;
    private String msg;
    private ProgressDialog pDialog;
    private SharedPreferences preferences;
    private Dialog shareTwitter;
    private String url;
    private String TAG = "CoronaNativeActivity";
    private String command = BuildConfig.FLAVOR;
    private String googlePlayUrl = "https://play.google.com/store/apps/details?id=jp.newgate.game.android.dw";

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class TweetPoster extends AsyncTask<String, Void, String> {
        private File image_path;
        private String message;
        ProgressDialog pDialog;
        private Object twitter;

        TweetPoster() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
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

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(String str) {
            Toast.makeText(CoronaNativeActivity.this, str, Toast.LENGTH_LONG).show();
            TweetPoster tp = new TweetPoster();
            tp.message = str;
            super.onPostExecute(str);
            this.pDialog.dismiss();
            CoronaNativeActivity.this.shareTwitter.dismiss();
            CoronaNativeActivity.this.onBackPressed();
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
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

        public TweetPoster setTwitter(Object twitter) {
            this.twitter = twitter;
            return this;
        }
    }

    private void authenTwitter() {
        if (TwitterLoginActivity.isConnected(this)) {
            return;
        }
        Intent intent = new Intent(this, (Class<?>) TwitterLoginActivity.class);
        intent.putExtra(Constants.TWITTER_CONSUMER_KEY_NAME, Constants.TWITTER_CONSUMER_KEY_VALUE);
        intent.putExtra(Constants.TWITTER_CONSUMER_SECRET_NAME, Constants.TWITTER_CONSUMER_SECRET_VALUE);
        startActivityForResult(intent, 1);
    }

    private void goBack() {
        onBackPressed();
    }

    private boolean isTWAuthenticated() {
        return this.preferences.getString(Constants.TWITTER_KEY_TOKEN_NAME, null) != null;
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
        new AlertDialog.Builder(this).setTitle(str).setMessage(str2).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: jp.newgate.game.android.dw.CoronaNativeActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                CoronaNativeActivity.this.onBackPressed();
            }
        }).show();
    }

    private void showTWShareDialog() {
        View viewInflate = getLayoutInflater().inflate(C1242R.layout.twitter_share_layout, (ViewGroup) null);
        this.shareTwitter = new Dialog(this, R.style.Theme_Holo_Light_Dialog_NoActionBar);
        this.shareTwitter.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: jp.newgate.game.android.dw.CoronaNativeActivity.3
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                CoronaNativeActivity.this.onBackPressed();
            }
        });
        this.shareTwitter.setContentView(viewInflate);
        this.shareTwitter.show();
        ((TextView) viewInflate.findViewById(C1242R.id.link)).setText(this.caption + " " + this.url);
        ((TextView) viewInflate.findViewById(C1242R.id.link)).setText(Html.fromHtml(this.googlePlayUrl));
        View viewFindViewById = viewInflate.findViewById(C1242R.id.btnCancel);
        View viewFindViewById2 = viewInflate.findViewById(C1242R.id.btnTwitter);
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: jp.newgate.game.android.dw.CoronaNativeActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CoronaNativeActivity.this.shareTwitter.dismiss();
            }
        });
        viewFindViewById2.setOnClickListener(new View.OnClickListener() { // from class: jp.newgate.game.android.dw.CoronaNativeActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
//                Twitter twitterFactory = new TwitterFactory().getInstance();
//                twitterFactory.setOAuthConsumer(Constants.TWITTER_CONSUMER_KEY_VALUE, Constants.TWITTER_CONSUMER_SECRET_VALUE);
//                AccessToken accessToken = new AccessToken(CoronaNativeActivity.this.getAccessToken(), CoronaNativeActivity.this.getAccessTokenSecret());
                Log.d(CoronaNativeActivity.this.TAG, "access token: " + CoronaNativeActivity.this.getAccessToken());
                Log.d(CoronaNativeActivity.this.TAG, "access token secret: " + CoronaNativeActivity.this.getAccessTokenSecret());
//                twitterFactory.setOAuthAccessToken(accessToken);
//                CoronaNativeActivity.this.new TweetPoster().setTwitter(twitterFactory).setMessage(CoronaNativeActivity.this.caption + " " + CoronaNativeActivity.this.googlePlayUrl).execute(new String[0]);
                Log.e("test", "url" + CoronaNativeActivity.this.url);
            }
        });
    }

    private void showWarningDialog(String str, String str2) {
        new AlertDialog.Builder(this).setTitle(str).setMessage(str2).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: jp.newgate.game.android.dw.CoronaNativeActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                CoronaNativeActivity.this.onBackPressed();
            }
        }).setIcon(R.drawable.ic_dialog_alert).show();
    }

    public String getAccessToken() {
        return this.preferences.getString(Constants.TWITTER_KEY_TOKEN_NAME, null);
    }

    public String getAccessTokenSecret() {
        return this.preferences.getString(Constants.TWITTER_KEY_SECRET_NAME, null);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
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

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.preferences = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        this.command = getIntent().getExtras().getString("command");//TJAdUnitConstants.String.COMMAND
        if (this.command.equals("shareTW")) {
            Intent intent = getIntent();
            this.msg = intent.getExtras().getString("msg");
            this.caption = intent.getExtras().getString("caption");
            this.url = intent.getExtras().getString("url");
            shareTwitter();
        }
    }

    public String sharePicAndTextTitter(File file, String str, Object twitter) throws Exception {
        try {
/*            StatusUpdate statusUpdate = new StatusUpdate(str);
            if (file != null) {
                statusUpdate.setMedia(file);
            }
            twitter.updateStatus(statusUpdate);
            return GraphResponse.SUCCESS_KEY;
        } catch (TwitterException e) {
            if (e.getStatusCode() == -1) {
            }
            return e.getMessage().toString().contains("suspended") ? "Failed to update on Twitter\nYour account is suspended and is not permitted to tweet!" : e.getMessage().toString().contains("duplicate") ? "Failed to update on Twitter\nYou cannot post the same status!" : "Failed to update on Twitter";
*/        } catch (Exception e2) {
            e2.printStackTrace();
            return "Unknow error";
        }
        return "";
    }
}
