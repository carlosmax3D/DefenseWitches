package com.facebook.internal;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.facebook.AccessToken;
import com.facebook.FacebookDialogException;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookServiceException;
import com.facebook.R;

public class WebDialog extends Dialog {
    private static final int API_EC_DIALOG_CANCEL = 4201;
    private static final int BACKGROUND_GRAY = -872415232;
    static final String CANCEL_URI = "fbconnect://cancel";
    public static final int DEFAULT_THEME = 16973840;
    static final boolean DISABLE_SSL_CHECK_FOR_TESTING = false;
    private static final String DISPLAY_TOUCH = "touch";
    private static final String LOG_TAG = "FacebookSDK.WebDialog";
    private static final int MAX_PADDING_SCREEN_HEIGHT = 1280;
    private static final int MAX_PADDING_SCREEN_WIDTH = 800;
    private static final double MIN_SCALE_FACTOR = 0.5d;
    private static final int NO_PADDING_SCREEN_HEIGHT = 800;
    private static final int NO_PADDING_SCREEN_WIDTH = 480;
    static final String REDIRECT_URI = "fbconnect://success";
    /* access modifiers changed from: private */
    public FrameLayout contentFrameLayout;
    /* access modifiers changed from: private */
    public ImageView crossImageView;
    /* access modifiers changed from: private */
    public String expectedRedirectUrl;
    /* access modifiers changed from: private */
    public boolean isDetached;
    /* access modifiers changed from: private */
    public boolean isPageFinished;
    private boolean listenerCalled;
    private OnCompleteListener onCompleteListener;
    /* access modifiers changed from: private */
    public ProgressDialog spinner;
    private String url;
    /* access modifiers changed from: private */
    public WebView webView;

    public static class Builder {
        private AccessToken accessToken;
        private String action;
        private String applicationId;
        private Context context;
        private OnCompleteListener listener;
        private Bundle parameters;
        private int theme;

        public Builder(Context context2, String str, Bundle bundle) {
            this.theme = WebDialog.DEFAULT_THEME;
            this.accessToken = AccessToken.getCurrentAccessToken();
            if (this.accessToken == null) {
                String metadataApplicationId = Utility.getMetadataApplicationId(context2);
                if (metadataApplicationId != null) {
                    this.applicationId = metadataApplicationId;
                } else {
                    throw new FacebookException("Attempted to create a builder without a valid access token or a valid default Application ID.");
                }
            }
            finishInit(context2, str, bundle);
        }

        public Builder(Context context2, String str, String str2, Bundle bundle) {
            this.theme = WebDialog.DEFAULT_THEME;
            str = str == null ? Utility.getMetadataApplicationId(context2) : str;
            Validate.notNullOrEmpty(str, "applicationId");
            this.applicationId = str;
            finishInit(context2, str2, bundle);
        }

        private void finishInit(Context context2, String str, Bundle bundle) {
            this.context = context2;
            this.action = str;
            if (bundle != null) {
                this.parameters = bundle;
            } else {
                this.parameters = new Bundle();
            }
        }

        public WebDialog build() {
            if (this.accessToken != null) {
                this.parameters.putString("app_id", this.accessToken.getApplicationId());
                this.parameters.putString("access_token", this.accessToken.getToken());
            } else {
                this.parameters.putString("app_id", this.applicationId);
            }
            return new WebDialog(this.context, this.action, this.parameters, this.theme, this.listener);
        }

        public String getApplicationId() {
            return this.applicationId;
        }

        public Context getContext() {
            return this.context;
        }

        public OnCompleteListener getListener() {
            return this.listener;
        }

        public Bundle getParameters() {
            return this.parameters;
        }

        public int getTheme() {
            return this.theme;
        }

        public Builder setOnCompleteListener(OnCompleteListener onCompleteListener) {
            this.listener = onCompleteListener;
            return this;
        }

        public Builder setTheme(int i) {
            this.theme = i;
            return this;
        }
    }

    private class DialogWebViewClient extends WebViewClient {
        private DialogWebViewClient() {
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (!WebDialog.this.isDetached) {
                WebDialog.this.spinner.dismiss();
            }
            WebDialog.this.contentFrameLayout.setBackgroundColor(0);
            WebDialog.this.webView.setVisibility(0);
            WebDialog.this.crossImageView.setVisibility(0);
            boolean unused = WebDialog.this.isPageFinished = true;
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            Utility.logd(WebDialog.LOG_TAG, "Webview loading URL: " + str);
            super.onPageStarted(webView, str, bitmap);
            if (!WebDialog.this.isDetached) {
                WebDialog.this.spinner.show();
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            WebDialog.this.sendErrorToListener(new FacebookDialogException(str, i, str2));
        }

        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
            sslErrorHandler.cancel();
            WebDialog.this.sendErrorToListener(new FacebookDialogException((String) null, -11, (String) null));
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            Utility.logd(WebDialog.LOG_TAG, "Redirect URL: " + str);
            if (str.startsWith(WebDialog.this.expectedRedirectUrl)) {
                Bundle parseResponseUri = WebDialog.this.parseResponseUri(str);
                String string = parseResponseUri.getString("error");
                if (string == null) {
                    string = parseResponseUri.getString(NativeProtocol.BRIDGE_ARG_ERROR_TYPE);
                }
                String string2 = parseResponseUri.getString("error_msg");
                if (string2 == null) {
                    string2 = parseResponseUri.getString(AnalyticsEvents.PARAMETER_SHARE_ERROR_MESSAGE);
                }
                if (string2 == null) {
                    string2 = parseResponseUri.getString(NativeProtocol.BRIDGE_ARG_ERROR_DESCRIPTION);
                }
                String string3 = parseResponseUri.getString(NativeProtocol.BRIDGE_ARG_ERROR_CODE);
                int i = -1;
                if (!Utility.isNullOrEmpty(string3)) {
                    try {
                        i = Integer.parseInt(string3);
                    } catch (NumberFormatException e) {
                        i = -1;
                    }
                }
                if (Utility.isNullOrEmpty(string) && Utility.isNullOrEmpty(string2) && i == -1) {
                    WebDialog.this.sendSuccessToListener(parseResponseUri);
                    return true;
                } else if (string != null && (string.equals("access_denied") || string.equals("OAuthAccessDeniedException"))) {
                    WebDialog.this.cancel();
                    return true;
                } else if (i == WebDialog.API_EC_DIALOG_CANCEL) {
                    WebDialog.this.cancel();
                    return true;
                } else {
                    WebDialog.this.sendErrorToListener(new FacebookServiceException(new FacebookRequestError(i, string, string2), string2));
                    return true;
                }
            } else if (str.startsWith("fbconnect://cancel")) {
                WebDialog.this.cancel();
                return true;
            } else if (str.contains("touch")) {
                return false;
            } else {
                WebDialog.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                return true;
            }
        }
    }

    public interface OnCompleteListener {
        void onComplete(Bundle bundle, FacebookException facebookException);
    }

    public WebDialog(Context context, String str) {
        this(context, str, DEFAULT_THEME);
    }

    public WebDialog(Context context, String str, int i) {
        super(context, i);
        this.expectedRedirectUrl = "fbconnect://success";
        this.listenerCalled = false;
        this.isDetached = false;
        this.isPageFinished = false;
        this.url = str;
    }

    public WebDialog(Context context, String str, Bundle bundle, int i, OnCompleteListener onCompleteListener2) {
        super(context, i);
        this.expectedRedirectUrl = "fbconnect://success";
        this.listenerCalled = false;
        this.isDetached = false;
        this.isPageFinished = false;
        bundle = bundle == null ? new Bundle() : bundle;
        bundle.putString(ServerProtocol.DIALOG_PARAM_REDIRECT_URI, "fbconnect://success");
        bundle.putString("display", "touch");
        this.url = Utility.buildUri(ServerProtocol.getDialogAuthority(), ServerProtocol.getAPIVersion() + "/" + ServerProtocol.DIALOG_PATH + str, bundle).toString();
        this.onCompleteListener = onCompleteListener2;
    }

    private void createCrossImage() {
        this.crossImageView = new ImageView(getContext());
        this.crossImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                WebDialog.this.cancel();
            }
        });
        this.crossImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.com_facebook_close));
        this.crossImageView.setVisibility(4);
    }

    private int getScaledSize(int i, float f, int i2, int i3) {
        int i4 = (int) (((float) i) / f);
        return (int) (((double) i) * (i4 <= i2 ? 1.0d : i4 >= i3 ? MIN_SCALE_FACTOR : MIN_SCALE_FACTOR + ((((double) (i3 - i4)) / ((double) (i3 - i2))) * MIN_SCALE_FACTOR)));
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void setUpWebView(int i) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        this.webView = new WebView(getContext()) {
            public void onWindowFocusChanged(boolean z) {
                try {
                    super.onWindowFocusChanged(z);
                } catch (NullPointerException e) {
                }
            }
        };
        this.webView.setVerticalScrollBarEnabled(false);
        this.webView.setHorizontalScrollBarEnabled(false);
        this.webView.setWebViewClient(new DialogWebViewClient());
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.loadUrl(this.url);
        this.webView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.webView.setVisibility(4);
        this.webView.getSettings().setSavePassword(false);
        this.webView.getSettings().setSaveFormData(false);
        this.webView.setFocusable(true);
        this.webView.setFocusableInTouchMode(true);
        this.webView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (view.hasFocus()) {
                    return false;
                }
                view.requestFocus();
                return false;
            }
        });
        linearLayout.setPadding(i, i, i, i);
        linearLayout.addView(this.webView);
        linearLayout.setBackgroundColor(BACKGROUND_GRAY);
        this.contentFrameLayout.addView(linearLayout);
    }

    public void cancel() {
        if (this.onCompleteListener != null && !this.listenerCalled) {
            sendErrorToListener(new FacebookOperationCanceledException());
        }
    }

    public void dismiss() {
        if (this.webView != null) {
            this.webView.stopLoading();
        }
        if (!this.isDetached && this.spinner.isShowing()) {
            this.spinner.dismiss();
        }
        super.dismiss();
    }

    public OnCompleteListener getOnCompleteListener() {
        return this.onCompleteListener;
    }

    /* access modifiers changed from: protected */
    public WebView getWebView() {
        return this.webView;
    }

    /* access modifiers changed from: protected */
    public boolean isListenerCalled() {
        return this.listenerCalled;
    }

    /* access modifiers changed from: protected */
    public boolean isPageFinished() {
        return this.isPageFinished;
    }

    public void onAttachedToWindow() {
        this.isDetached = false;
        super.onAttachedToWindow();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.spinner = new ProgressDialog(getContext());
        this.spinner.requestWindowFeature(1);
        this.spinner.setMessage(getContext().getString(R.string.com_facebook_loading));
        this.spinner.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                WebDialog.this.cancel();
            }
        });
        requestWindowFeature(1);
        this.contentFrameLayout = new FrameLayout(getContext());
        resize();
        getWindow().setGravity(17);
        getWindow().setSoftInputMode(16);
        createCrossImage();
        setUpWebView((this.crossImageView.getDrawable().getIntrinsicWidth() / 2) + 1);
        this.contentFrameLayout.addView(this.crossImageView, new ViewGroup.LayoutParams(-2, -2));
        setContentView(this.contentFrameLayout);
    }

    public void onDetachedFromWindow() {
        this.isDetached = true;
        super.onDetachedFromWindow();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            cancel();
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        resize();
    }

    /* access modifiers changed from: protected */
    public Bundle parseResponseUri(String str) {
        Uri parse = Uri.parse(str);
        Bundle parseUrlQueryString = Utility.parseUrlQueryString(parse.getQuery());
        parseUrlQueryString.putAll(Utility.parseUrlQueryString(parse.getFragment()));
        return parseUrlQueryString;
    }

    public void resize() {
        Display defaultDisplay = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        getWindow().setLayout(Math.min(getScaledSize(displayMetrics.widthPixels < displayMetrics.heightPixels ? displayMetrics.widthPixels : displayMetrics.heightPixels, displayMetrics.density, NO_PADDING_SCREEN_WIDTH, 800), displayMetrics.widthPixels), Math.min(getScaledSize(displayMetrics.widthPixels < displayMetrics.heightPixels ? displayMetrics.heightPixels : displayMetrics.widthPixels, displayMetrics.density, 800, MAX_PADDING_SCREEN_HEIGHT), displayMetrics.heightPixels));
    }

    /* access modifiers changed from: protected */
    public void sendErrorToListener(Throwable th) {
        if (this.onCompleteListener != null && !this.listenerCalled) {
            this.listenerCalled = true;
            this.onCompleteListener.onComplete((Bundle) null, th instanceof FacebookException ? (FacebookException) th : new FacebookException(th));
            dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void sendSuccessToListener(Bundle bundle) {
        if (this.onCompleteListener != null && !this.listenerCalled) {
            this.listenerCalled = true;
            this.onCompleteListener.onComplete(bundle, (FacebookException) null);
            dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void setExpectedRedirectUrl(String str) {
        this.expectedRedirectUrl = str;
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener2) {
        this.onCompleteListener = onCompleteListener2;
    }
}
