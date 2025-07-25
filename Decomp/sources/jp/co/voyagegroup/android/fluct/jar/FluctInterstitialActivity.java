package jp.co.voyagegroup.android.fluct.jar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import java.util.ArrayList;
import jp.co.voyagegroup.android.fluct.jar.db.FluctDbAccess;
import jp.co.voyagegroup.android.fluct.jar.db.FluctInterstitialTable;
import jp.co.voyagegroup.android.fluct.jar.sdk.FluctConfig;
import jp.co.voyagegroup.android.fluct.jar.sdk.FluctInterstitialManager;
import jp.co.voyagegroup.android.fluct.jar.sdk.FluctWebView;
import jp.co.voyagegroup.android.fluct.jar.util.FluctConstants;
import jp.co.voyagegroup.android.fluct.jar.util.Log;

public class FluctInterstitialActivity extends Activity {
    private static final String TAG = "FluctInterstitialActivity";
    private int mAdHeight;
    private int mAdWidth;
    private AnimationDrawable mAnimation;
    private ArrayList<Bitmap> mBitmapArray;
    View.OnClickListener mCloseButton = new View.OnClickListener() {
        public void onClick(View view) {
            FluctInterstitialManager.callback(2);
            FluctInterstitialActivity.this.stopAnimation();
            FluctInterstitialActivity.this.cleanupImage();
            FluctInterstitialActivity.this.finish();
        }
    };
    private int mCloseButtonSize;
    private int mFrameColor;
    private int mFrameHeight;
    private int mFrameWidth;
    private FrameLayout mFullScreenLayout;
    private ArrayList<ImageView> mImageArray;
    private String mMediaId;
    private Point mScreen;

    /* access modifiers changed from: private */
    public void cleanupImage() {
        Log.d(TAG, "cleanupImage : ");
        if (this.mBitmapArray != null && this.mBitmapArray.size() > 0) {
            for (int i = 0; this.mBitmapArray.size() > i; i++) {
                Bitmap bitmap = this.mBitmapArray.get(i);
                Log.v(TAG, "cleanupImage : bitmap is " + bitmap);
                bitmap.recycle();
            }
            this.mBitmapArray.clear();
            this.mBitmapArray = null;
        }
        if (this.mImageArray != null && this.mImageArray.size() > 0) {
            for (int i2 = 0; this.mImageArray.size() > i2; i2++) {
                ImageView imageView = this.mImageArray.get(i2);
                Log.v(TAG, "cleanupImage : image is " + imageView);
                imageView.setImageDrawable((Drawable) null);
            }
            this.mImageArray.clear();
            this.mImageArray = null;
        }
    }

    private ImageView setCloseButton(Context context, float f) {
        Log.d(TAG, "setCloseButton : ");
        ImageView imageView = new ImageView(context);
        int i = (((this.mScreen.x - this.mFrameWidth) / 2) + this.mFrameWidth) - (this.mCloseButtonSize - (this.mCloseButtonSize / 4));
        int i2 = ((this.mScreen.y - this.mFrameHeight) / 2) - (this.mCloseButtonSize / 2);
        if (this.mAdHeight + (this.mCloseButtonSize * 2) > this.mScreen.y) {
            i2 = (this.mScreen.y - this.mAdHeight) / 2;
            i = this.mAdWidth + ((this.mScreen.x - this.mAdWidth) / 2);
        } else if (this.mAdWidth + (this.mCloseButtonSize * 2) > this.mScreen.x) {
            i = this.mScreen.x - this.mCloseButtonSize;
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(this.mCloseButtonSize, this.mCloseButtonSize);
        layoutParams.setMargins(i, i2, 0, 0);
        layoutParams.gravity = 48;
        imageView.setLayoutParams(layoutParams);
        byte[] closeButtonImage = FluctDbAccess.getCloseButtonImage(context);
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(closeButtonImage, 0, closeButtonImage.length, new BitmapFactory.Options());
        imageView.setImageBitmap(decodeByteArray);
        imageView.setOnClickListener(this.mCloseButton);
        this.mBitmapArray.add(decodeByteArray);
        this.mImageArray.add(imageView);
        return imageView;
    }

    private FluctWebView setFluctWebView(Context context, FluctInterstitialTable fluctInterstitialTable) {
        Log.d(TAG, "setFluctWebView : ");
        FluctWebView fluctWebView = new FluctWebView(context, FluctConfig.getInstance().getFromDB(context, this.mMediaId), this);
        fluctWebView.setAdHtml(fluctInterstitialTable.getAdHtml());
        fluctWebView.setVisibility(0);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(this.mAdWidth, this.mAdHeight);
        layoutParams.gravity = 17;
        fluctWebView.setLayoutParams(layoutParams);
        return fluctWebView;
    }

    private FrameLayout setFrameView(Context context, float f) {
        Log.d(TAG, "setFrameView : ");
        FrameLayout frameLayout = new FrameLayout(context);
        this.mFrameWidth = (int) (((float) this.mAdWidth) + (40.0f * f));
        this.mFrameHeight = (int) (((float) this.mAdHeight) + (40.0f * f));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(this.mFrameWidth, this.mFrameHeight);
        layoutParams.gravity = 17;
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.setBackgroundColor(this.mFrameColor);
        return frameLayout;
    }

    private ImageView setLoadingImage(Context context, float f) {
        Log.d(TAG, "setLoadingImage : ");
        ImageView imageView = new ImageView(context);
        this.mAnimation = new AnimationDrawable();
        ArrayList<byte[]> loadingImage = FluctDbAccess.getLoadingImage(context);
        BitmapFactory.Options options = new BitmapFactory.Options();
        for (int i = 0; i < loadingImage.size(); i++) {
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(loadingImage.get(i), 0, loadingImage.get(i).length, options);
            this.mAnimation.addFrame(new BitmapDrawable(getResources(), decodeByteArray), 100);
            this.mBitmapArray.add(decodeByteArray);
        }
        this.mAnimation.setOneShot(false);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) (36.0f * f), (int) (36.0f * f));
        layoutParams.gravity = 17;
        imageView.setLayoutParams(layoutParams);
        imageView.setImageDrawable(this.mAnimation);
        this.mImageArray.add(imageView);
        return imageView;
    }

    /* access modifiers changed from: private */
    public void stopAnimation() {
        Log.d(TAG, "stopAnimation : ");
        if (this.mAnimation != null && this.mAnimation.isRunning()) {
            this.mAnimation.stop();
        }
    }

    public void callback(int i) {
        Log.d(TAG, "callback : status " + i);
        FluctInterstitialManager.callback(i);
        stopAnimation();
        cleanupImage();
        finish();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d(TAG, "onConfigurationChanged : orientation is " + configuration.orientation);
        FluctInterstitialManager.callback(2);
        stopAnimation();
        cleanupImage();
        finish();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d(TAG, "onCreate : ");
        Intent intent = getIntent();
        this.mMediaId = intent.getStringExtra("media_id");
        this.mFrameColor = intent.getIntExtra("frame_color", FluctConstants.FRAME_BASE_COLOR);
        this.mFullScreenLayout = new FrameLayout(getApplicationContext());
        this.mFullScreenLayout.setBackgroundColor(FluctConstants.SCREEN_BASE_COLOR);
        this.mFullScreenLayout.setForegroundGravity(17);
        setContentView(this.mFullScreenLayout);
        this.mBitmapArray = new ArrayList<>();
        this.mImageArray = new ArrayList<>();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        FluctInterstitialManager.callback(2);
        stopAnimation();
        cleanupImage();
        finish();
        return true;
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        Log.d(TAG, "onWindowFocusChanged : hasFocus " + z);
        if (z) {
            Context applicationContext = getApplicationContext();
            this.mScreen = new Point();
            this.mScreen.x = this.mFullScreenLayout.getWidth();
            this.mScreen.y = this.mFullScreenLayout.getHeight();
            FluctInterstitialTable interstitial = FluctDbAccess.getInterstitial(getApplicationContext(), this.mMediaId);
            float f = getResources().getDisplayMetrics().density;
            this.mAdWidth = (int) (((float) interstitial.getWidth()) * f);
            this.mAdHeight = (int) (((float) interstitial.getHeight()) * f);
            this.mCloseButtonSize = (int) (36.0f * f);
            if (this.mAdWidth + (this.mCloseButtonSize * 2) <= this.mScreen.x || this.mAdHeight + (this.mCloseButtonSize * 2) <= this.mScreen.y) {
                FluctWebView fluctWebView = setFluctWebView(applicationContext, interstitial);
                FrameLayout frameView = setFrameView(applicationContext, f);
                ImageView loadingImage = setLoadingImage(applicationContext, f);
                ImageView closeButton = setCloseButton(applicationContext, f);
                frameView.addView(loadingImage);
                this.mFullScreenLayout.addView(frameView);
                this.mFullScreenLayout.addView(closeButton);
                this.mFullScreenLayout.addView(fluctWebView);
                this.mAnimation.start();
                FluctInterstitialManager.callback(0);
                return;
            }
            FluctInterstitialManager.callback(7);
            stopAnimation();
            cleanupImage();
            finish();
        }
    }
}
