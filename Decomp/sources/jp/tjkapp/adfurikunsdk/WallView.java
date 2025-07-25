package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class WallView extends RelativeLayout {
    private static final String CLOSE_BUTTON_TEXT = "close";
    private static final int CLOSE_FONT_SIZE = 14;
    private static final String CLOSE_ICON_DARK = "adfurikun/images/close_dark.png";
    private static final String CLOSE_ICON_LIGHT = "adfurikun/images/close_light.png";
    private static final int ID_TITLEBAR = 1;
    public static final int THEME_DARK = 1;
    private static final int THEME_DENSITY = 320;
    public static final int THEME_LIGHT = 2;
    public static final int THEME_RANDOM = 0;
    private static final int TITLEBAR_BOTTOM_DARK = -13881555;
    private static final int TITLEBAR_BOTTOM_LIGHT = -2566699;
    private static final int TITLEBAR_TEXT_DARK = -1118482;
    private static final int TITLEBAR_TEXT_LIGHT = -12697025;
    private static final int TITLEBAR_TOP_DARK = -12697025;
    private static final int TITLEBAR_TOP_LIGHT = -1118482;
    private OnAdfurikunWallClickListener mOnAdfurikunWallClickListener;
    private Random mRandom;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    public interface OnAdfurikunWallClickListener {
        void onClickClose();
    }

    public WallView(Context context, WallAdLayout wallAdLayout, int i) throws IOException {
        super(context);
        this.mOnAdfurikunWallClickListener = null;
        initialize(context, wallAdLayout, i);
    }

    private GradientDrawable createGradient(int i, int i2) {
        return new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{i, i2});
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

    private void initialize(Context context, WallAdLayout wallAdLayout, int i) throws IOException {
        ViewGroup.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        setLayoutParams(layoutParams);
        setBackgroundColor(-1);
        if (i == 0) {
            if (this.mRandom == null) {
                this.mRandom = new Random();
            }
            i = this.mRandom.nextInt(2) == 0 ? 1 : 2;
        }
        int i2 = -12697025;
        int i3 = TITLEBAR_BOTTOM_DARK;
        int i4 = -1118482;
        if (i == 2) {
            i2 = -1118482;
            i3 = TITLEBAR_BOTTOM_LIGHT;
            i4 = -12697025;
        }
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        relativeLayout.setBackgroundDrawable(createGradient(i2, i3));
        float f = getResources().getDisplayMetrics().density;
        int i5 = (int) ((28.0f * f) + 0.5f);
        int i6 = (int) ((27.0f * f) + 0.5f);
        String str = CLOSE_ICON_DARK;
        if (i == 2) {
            str = CLOSE_ICON_LIGHT;
        }
        Bitmap assetsBitmap = getAssetsBitmap(str);
        if (assetsBitmap != null) {
            ImageView imageView = new ImageView(context);
            setImageView(imageView, assetsBitmap, true);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(i5, i6);
            layoutParams2.addRule(11, -1);
            relativeLayout.addView(imageView, layoutParams2);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: jp.tjkapp.adfurikunsdk.WallView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (WallView.this.mOnAdfurikunWallClickListener != null) {
                        WallView.this.mOnAdfurikunWallClickListener.onClickClose();
                    }
                }
            });
        } else {
            int i7 = (int) ((8.0f * f) + 0.5f);
            TextView textView = new TextView(context);
            textView.setGravity(16);
            textView.setText("close");
            textView.setTextColor(i4);
            textView.setTextSize(14.0f);
            textView.setPadding(i7, 0, i7, 0);
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, i6);
            layoutParams3.addRule(11, -1);
            layoutParams3.addRule(15, -1);
            relativeLayout.addView(textView, layoutParams3);
            textView.setOnClickListener(new View.OnClickListener() { // from class: jp.tjkapp.adfurikunsdk.WallView.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (WallView.this.mOnAdfurikunWallClickListener != null) {
                        WallView.this.mOnAdfurikunWallClickListener.onClickClose();
                    }
                }
            });
        }
        relativeLayout.setId(1);
        addView(relativeLayout);
        RelativeLayout relativeLayout2 = new RelativeLayout(context);
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-1, 0);
        layoutParams4.addRule(3, 1);
        layoutParams4.addRule(12, -1);
        addView(relativeLayout2, layoutParams4);
        relativeLayout2.addView(wallAdLayout, layoutParams);
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

    public void setOnAdfurikunWallClickListener(OnAdfurikunWallClickListener onAdfurikunWallClickListener) {
        this.mOnAdfurikunWallClickListener = onAdfurikunWallClickListener;
    }
}
