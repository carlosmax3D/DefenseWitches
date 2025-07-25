package jp.tjkapp.adfurikunsdk;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import jp.stargarage.g2metrics.BuildConfig;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class IntersView extends RelativeLayout {
    private static final int AD_BUTTON_BOTTOM = -15163902;
    private static final int AD_BUTTON_LINE = -15363584;
    private static final int AD_BUTTON_TEXT = -1;
    private static final int AD_BUTTON_TOP = -11995872;
    private static final int BUTTON_BOTTOM = -3947581;
    private static final int BUTTON_FONT_SIZE_LAND = 10;
    private static final int BUTTON_FONT_SIZE_PORT = 12;
    private static final int BUTTON_LINE = -6645094;
    private static final int BUTTON_TEXT = -11184811;
    private static final int BUTTON_TOP = -2039584;
    private static final int CLOSE_BOTTOM = -1;
    private static final String CLOSE_BUTTON_TEXT = "close";
    private static final int CLOSE_LINE = -12412573;
    private static final int CLOSE_TEXT = -11184811;
    private static final int CLOSE_TOP = -1;
    private static final int DIALOG_COLOR = -16777216;
    private static final int DIALOG_FRAME_COLOR = -15363584;
    private static final int ID_AD_CONTAINER = 3;
    private static final int ID_LEFT_BUTTON = 4;
    private static final int ID_RIGHT_BUTTON = 5;
    private static final int ID_ROOT = 2;
    private static final int ID_TITLEBAR = 1;
    private static final int SHADOW_COLOR = -1728053248;
    private static final int THEME_DENSITY = 320;
    private static final int TITLEBAR_BOTTOM = -15163902;
    private static final String TITLEBAR_CLOSE_ICON = "adfurikun/images/close_bt.png";
    private static final int TITLEBAR_FONT_SIZE = 14;
    private static final int TITLEBAR_LINE = -15363584;
    private static final int TITLEBAR_TEXT = -1;
    private static final int TITLEBAR_TOP = -11995872;
    private Button mAdButton;
    private Button mCustomButton;
    private String mCustomButtonName;
    private OnAdfurikunIntersClickListener mOnAdfurikunIntersClickListener;
    private String mTitlebarText;
    private RelativeLayout.LayoutParams mTopParams;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    public interface OnAdfurikunIntersClickListener {
        void onClickCancel();

        void onClickCustom();
    }

    public IntersView(Context context, String str, IntersAdLayout intersAdLayout, String str2) throws IOException {
        super(context);
        this.mTitlebarText = "おすすめ";
        this.mCustomButtonName = BuildConfig.FLAVOR;
        this.mOnAdfurikunIntersClickListener = null;
        initialize(context, str, intersAdLayout, str2);
    }

    private GradientDrawable createGradient(int i, int i2, int i3, float f, float f2, float f3, float f4, int i4) {
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{i, i2});
        gradientDrawable.setStroke(i4, i3);
        gradientDrawable.setCornerRadii(new float[]{f, f, f2, f2, f3, f3, f4, f4});
        return gradientDrawable;
    }

    private GradientDrawable createGradient(int i, int i2, int i3, float f, int i4) {
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{i, i2});
        gradientDrawable.setStroke(i4, i3);
        gradientDrawable.setCornerRadius(f);
        return gradientDrawable;
    }

    private Bitmap getAssetsBitmap(String str) throws IOException {
        Bitmap bitmapDecodeStream = null;
        InputStream inputStreamOpen = null;
        try {
            inputStreamOpen = getResources().getAssets().open(str);
            bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpen);
            bitmapDecodeStream.setDensity(THEME_DENSITY);
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (IOException e) {
                }
            }
        } catch (FileNotFoundException e2) {
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (IOException e3) {
                }
            }
        } catch (UnsupportedEncodingException e4) {
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (IOException e5) {
                }
            }
        } catch (Exception e6) {
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (IOException e7) {
                }
            }
        } catch (Throwable th) {
            if (inputStreamOpen != null) {
                try {
                    inputStreamOpen.close();
                } catch (IOException e8) {
                }
            }
            throw th;
        }
        return bitmapDecodeStream;
    }

    private String getLandText(String str) {
        if (str == null) {
            return BuildConfig.FLAVOR;
        }
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append("\n");
            }
            sb.append(str.substring(i, i + 1));
        }
        return sb.toString();
    }

    private StateListDrawable getThemeButtonDrawable(int i, int i2, int i3, float f, int i4) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{R.attr.state_pressed}, createGradient(i2, i, i3, f, i4));
        stateListDrawable.addState(new int[]{-16842919}, createGradient(i, i2, i3, f, i4));
        return stateListDrawable;
    }

    private void initialize(Context context, String str, IntersAdLayout intersAdLayout, String str2) throws IOException {
        if (str != null && str.length() > 0) {
            this.mTitlebarText = str;
        }
        this.mCustomButtonName = str2;
        float f = getResources().getDisplayMetrics().density;
        int i = (int) ((4.0f * f) + 0.5f);
        int i2 = (int) ((4.0f * f) + 0.5f);
        int i3 = (int) ((1.0f * f) + 0.5f);
        int i4 = (int) ((4.0f * f) + 0.5f);
        int i5 = (int) ((300.0f * f) + 0.5f);
        int i6 = (int) ((250.0f * f) + 0.5f);
        setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        setBackgroundColor(SHADOW_COLOR);
        setClickable(true);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13, -1);
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(layoutParams);
        relativeLayout.setBackgroundDrawable(createGradient(-16777216, -16777216, -15363584, i, i3));
        addView(relativeLayout, layoutParams);
        int i7 = (int) ((4.0f * f) + 0.5f);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        RelativeLayout relativeLayout2 = new RelativeLayout(context);
        relativeLayout2.setPadding(i7, i7, i7, i7);
        relativeLayout2.setId(1);
        relativeLayout2.setBackgroundDrawable(createGradient(-11995872, -15163902, -15363584, i, i, 0.0f, 0.0f, i3));
        relativeLayout.addView(relativeLayout2, layoutParams2);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams3.addRule(9, -1);
        layoutParams3.addRule(15, -1);
        TextView textView = new TextView(context);
        textView.setPadding(i7, 0, i7, 0);
        textView.setTextColor(-1);
        textView.setTextSize(14.0f);
        textView.setText(this.mTitlebarText);
        relativeLayout2.addView(textView, layoutParams3);
        int i8 = (int) ((31.0f * f) + 0.5f);
        int i9 = (int) ((28.0f * f) + 0.5f);
        Bitmap assetsBitmap = getAssetsBitmap(TITLEBAR_CLOSE_ICON);
        if (assetsBitmap != null) {
            ImageView imageView = new ImageView(context);
            setImageView(imageView, assetsBitmap, true);
            RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(i8, i9);
            layoutParams4.addRule(11, -1);
            layoutParams4.addRule(15, -1);
            relativeLayout2.addView(imageView, layoutParams4);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: jp.tjkapp.adfurikunsdk.IntersView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (IntersView.this.mOnAdfurikunIntersClickListener != null) {
                        IntersView.this.mOnAdfurikunIntersClickListener.onClickCancel();
                    }
                }
            });
        } else {
            int i10 = (int) ((8.0f * f) + 0.5f);
            TextView textView2 = new TextView(context);
            textView2.setGravity(16);
            textView2.setText("close");
            textView2.setTextColor(-11184811);
            textView2.setTextSize(14.0f);
            textView2.setPadding(i10, 0, i10, 0);
            textView2.setBackgroundDrawable(createGradient(-1, -1, CLOSE_LINE, i2, i3));
            RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(-2, i9);
            layoutParams5.addRule(11, -1);
            layoutParams5.addRule(15, -1);
            relativeLayout2.addView(textView2, layoutParams5);
            textView2.setOnClickListener(new View.OnClickListener() { // from class: jp.tjkapp.adfurikunsdk.IntersView.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (IntersView.this.mOnAdfurikunIntersClickListener != null) {
                        IntersView.this.mOnAdfurikunIntersClickListener.onClickCancel();
                    }
                }
            });
        }
        RelativeLayout.LayoutParams layoutParams6 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams6.addRule(3, 1);
        RelativeLayout relativeLayout3 = new RelativeLayout(context);
        relativeLayout3.setId(2);
        relativeLayout3.setPadding(i4, i4, i4, i4);
        relativeLayout3.setLayoutParams(layoutParams6);
        relativeLayout.addView(relativeLayout3, layoutParams6);
        ViewGroup.LayoutParams layoutParams7 = new RelativeLayout.LayoutParams(i5, i6);
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setId(3);
        frameLayout.setBackgroundColor(-16777216);
        relativeLayout3.addView(frameLayout, layoutParams7);
        FrameLayout.LayoutParams layoutParams8 = new FrameLayout.LayoutParams(i5, i6);
        if (intersAdLayout != null) {
            frameLayout.addView(intersAdLayout, layoutParams8);
        }
        if (getResources().getConfiguration().orientation == 2) {
            setLandLayout(context, relativeLayout3, f, i5, i6, i3);
            this.mAdButton.setTextSize(10.0f);
            this.mAdButton.setText(getLandText(BuildConfig.FLAVOR));
            if (this.mCustomButton != null) {
                this.mCustomButton.setTextSize(10.0f);
                this.mCustomButton.setText(getLandText(this.mCustomButtonName));
            }
        } else {
            setPortLayout(context, relativeLayout3, f, i5, i6, i3);
            this.mAdButton.setTextSize(12.0f);
            this.mAdButton.setText(BuildConfig.FLAVOR);
            if (this.mCustomButton != null) {
                this.mCustomButton.setTextSize(12.0f);
                this.mCustomButton.setText(this.mCustomButtonName);
            }
        }
        this.mAdButton.setVisibility(8);
        if (this.mCustomButton != null) {
            this.mCustomButton.setLayoutParams(this.mTopParams);
        }
        if (layoutParams2 != null) {
            layoutParams2.addRule(5, 2);
            layoutParams2.addRule(7, 2);
        }
        this.mAdButton.setTextColor(-1);
        this.mAdButton.setBackgroundDrawable(getThemeButtonDrawable(-11995872, -15163902, -15363584, i2, i3));
        if (this.mCustomButton != null) {
            this.mCustomButton.setTextColor(-11184811);
            this.mCustomButton.setBackgroundDrawable(getThemeButtonDrawable(BUTTON_TOP, BUTTON_BOTTOM, BUTTON_LINE, i2, i3));
            this.mCustomButton.setOnClickListener(new View.OnClickListener() { // from class: jp.tjkapp.adfurikunsdk.IntersView.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (IntersView.this.mOnAdfurikunIntersClickListener != null) {
                        IntersView.this.mOnAdfurikunIntersClickListener.onClickCustom();
                    }
                }
            });
        }
    }

    private void setImageView(ImageView imageView, Bitmap bitmap, boolean z) {
        if (imageView != null) {
            if (bitmap == null) {
                if (z) {
                    imageView.setImageDrawable(null);
                    return;
                } else {
                    imageView.setBackgroundDrawable(null);
                    return;
                }
            }
            bitmap.setDensity(THEME_DENSITY);
            if (z) {
                imageView.setAdjustViewBounds(true);
                imageView.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
                return;
            }
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            imageView.setMinimumWidth(width);
            imageView.setMinimumHeight(height);
            imageView.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        }
    }

    private void setLandLayout(Context context, RelativeLayout relativeLayout, float f, int i, int i2, int i3) {
        int i4 = (int) ((40.0f * f) + 0.5f);
        int i5 = (int) ((117.0f * f) + 0.5f);
        int i6 = (int) ((4.0f * f) + 0.5f);
        int i7 = (int) ((10.0f * f) + 0.5f);
        int i8 = (int) ((8.0f * f) + 0.5f);
        this.mTopParams = new RelativeLayout.LayoutParams(i4, -2);
        this.mTopParams.setMargins(i6, i6, 0, i6);
        this.mTopParams.addRule(6, 3);
        this.mTopParams.addRule(8, 3);
        this.mTopParams.addRule(1, 3);
        if (this.mCustomButtonName.length() <= 0) {
            Button button = new Button(context);
            button.setPadding(i7, i8, i7, i8);
            button.setId(4);
            relativeLayout.addView(button, this.mTopParams);
            this.mAdButton = button;
            this.mCustomButton = null;
            return;
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i4, i5);
        layoutParams.setMargins(i6, i6, 0, i6 / 2);
        layoutParams.addRule(1, 3);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(i4, -2);
        layoutParams2.setMargins(i6, i6 / 2, 0, i6);
        layoutParams2.addRule(1, 3);
        Button button2 = new Button(context);
        button2.setPadding(i7, i8, i7, i8);
        button2.setId(4);
        Button button3 = new Button(context);
        button3.setPadding(i7, i8, i7, i8);
        button3.setId(5);
        layoutParams.addRule(1, 3);
        layoutParams2.addRule(1, 3);
        layoutParams2.addRule(3, 4);
        layoutParams2.addRule(8, 3);
        relativeLayout.addView(button2, layoutParams);
        relativeLayout.addView(button3, layoutParams2);
        this.mAdButton = button2;
        this.mCustomButton = button3;
    }

    private void setPortLayout(Context context, RelativeLayout relativeLayout, float f, int i, int i2, int i3) {
        int i4 = (int) ((142.0f * f) + 0.5f);
        int i5 = (int) ((4.0f * f) + 0.5f);
        int i6 = (int) ((8.0f * f) + 0.5f);
        int i7 = (int) ((10.0f * f) + 0.5f);
        this.mTopParams = new RelativeLayout.LayoutParams(-2, -2);
        this.mTopParams.setMargins(i5, i5, i5, 0);
        this.mTopParams.addRule(5, 3);
        this.mTopParams.addRule(7, 3);
        this.mTopParams.addRule(3, 3);
        if (this.mCustomButtonName.length() <= 0) {
            Button button = new Button(context);
            button.setPadding(i6, i7, i6, i7);
            button.setId(4);
            relativeLayout.addView(button, this.mTopParams);
            this.mAdButton = button;
            this.mCustomButton = null;
            return;
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i4, -2);
        layoutParams.setMargins(i5, i5, i5 / 2, 0);
        layoutParams.addRule(3, 3);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.setMargins(i5 / 2, i5, i5, 0);
        layoutParams2.addRule(3, 3);
        Button button2 = new Button(context);
        button2.setPadding(i6, i7, i6, i7);
        button2.setId(4);
        Button button3 = new Button(context);
        button3.setPadding(i6, i7, i6, i7);
        button3.setId(5);
        layoutParams2.addRule(1, 4);
        layoutParams2.addRule(7, 3);
        relativeLayout.addView(button2, layoutParams);
        relativeLayout.addView(button3, layoutParams2);
        this.mAdButton = button2;
        this.mCustomButton = button3;
    }

    public void setOnAdfurikunIntersClickListener(OnAdfurikunIntersClickListener onAdfurikunIntersClickListener) {
        this.mOnAdfurikunIntersClickListener = onAdfurikunIntersClickListener;
    }
}
