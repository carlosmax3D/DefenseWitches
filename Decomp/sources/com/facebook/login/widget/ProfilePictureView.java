package com.facebook.login.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.facebook.FacebookException;
import com.facebook.LoggingBehavior;
import com.facebook.R;
import com.facebook.internal.ImageDownloader;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.ImageResponse;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;

public class ProfilePictureView extends FrameLayout {
    private static final String BITMAP_HEIGHT_KEY = "ProfilePictureView_height";
    private static final String BITMAP_KEY = "ProfilePictureView_bitmap";
    private static final String BITMAP_WIDTH_KEY = "ProfilePictureView_width";
    public static final int CUSTOM = -1;
    private static final boolean IS_CROPPED_DEFAULT_VALUE = true;
    private static final String IS_CROPPED_KEY = "ProfilePictureView_isCropped";
    public static final int LARGE = -4;
    private static final int MIN_SIZE = 1;
    public static final int NORMAL = -3;
    private static final String PENDING_REFRESH_KEY = "ProfilePictureView_refresh";
    private static final String PRESET_SIZE_KEY = "ProfilePictureView_presetSize";
    private static final String PROFILE_ID_KEY = "ProfilePictureView_profileId";
    public static final int SMALL = -2;
    private static final String SUPER_STATE_KEY = "ProfilePictureView_superState";
    public static final String TAG = ProfilePictureView.class.getSimpleName();
    private Bitmap customizedDefaultProfilePicture = null;
    private ImageView image;
    private Bitmap imageContents;
    private boolean isCropped = true;
    private ImageRequest lastRequest;
    private OnErrorListener onErrorListener;
    private int presetSizeType = -1;
    private String profileId;
    private int queryHeight = 0;
    private int queryWidth = 0;

    public interface OnErrorListener {
        void onError(FacebookException facebookException);
    }

    public ProfilePictureView(Context context) {
        super(context);
        initialize(context);
    }

    public ProfilePictureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
        parseAttributes(attributeSet);
    }

    public ProfilePictureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initialize(context);
        parseAttributes(attributeSet);
    }

    private int getPresetSizeInPixels(boolean z) {
        int i;
        switch (this.presetSizeType) {
            case -4:
                i = R.dimen.com_facebook_profilepictureview_preset_size_large;
                break;
            case -3:
                i = R.dimen.com_facebook_profilepictureview_preset_size_normal;
                break;
            case -2:
                i = R.dimen.com_facebook_profilepictureview_preset_size_small;
                break;
            case -1:
                if (z) {
                    i = R.dimen.com_facebook_profilepictureview_preset_size_normal;
                    break;
                } else {
                    return 0;
                }
            default:
                return 0;
        }
        return getResources().getDimensionPixelSize(i);
    }

    private void initialize(Context context) {
        removeAllViews();
        this.image = new ImageView(context);
        this.image.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        addView(this.image);
    }

    private void parseAttributes(AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.com_facebook_profile_picture_view);
        setPresetSize(obtainStyledAttributes.getInt(R.styleable.com_facebook_profile_picture_view_preset_size, -1));
        this.isCropped = obtainStyledAttributes.getBoolean(R.styleable.com_facebook_profile_picture_view_is_cropped, true);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: private */
    public void processResponse(ImageResponse imageResponse) {
        if (imageResponse.getRequest() == this.lastRequest) {
            this.lastRequest = null;
            Bitmap bitmap = imageResponse.getBitmap();
            Exception error = imageResponse.getError();
            if (error != null) {
                OnErrorListener onErrorListener2 = this.onErrorListener;
                if (onErrorListener2 != null) {
                    onErrorListener2.onError(new FacebookException("Error in downloading profile picture for profileId: " + getProfileId(), (Throwable) error));
                } else {
                    Logger.log(LoggingBehavior.REQUESTS, 6, TAG, error.toString());
                }
            } else if (bitmap != null) {
                setImageBitmap(bitmap);
                if (imageResponse.isCachedRedirect()) {
                    sendImageRequest(false);
                }
            }
        }
    }

    private void refreshImage(boolean z) {
        boolean updateImageQueryParameters = updateImageQueryParameters();
        if (this.profileId == null || this.profileId.length() == 0 || (this.queryWidth == 0 && this.queryHeight == 0)) {
            setBlankProfilePicture();
        } else if (updateImageQueryParameters || z) {
            sendImageRequest(true);
        }
    }

    private void sendImageRequest(boolean z) {
        ImageRequest build = new ImageRequest.Builder(getContext(), ImageRequest.getProfilePictureUri(this.profileId, this.queryWidth, this.queryHeight)).setAllowCachedRedirects(z).setCallerTag(this).setCallback(new ImageRequest.Callback() {
            public void onCompleted(ImageResponse imageResponse) {
                ProfilePictureView.this.processResponse(imageResponse);
            }
        }).build();
        if (this.lastRequest != null) {
            ImageDownloader.cancelRequest(this.lastRequest);
        }
        this.lastRequest = build;
        ImageDownloader.downloadAsync(build);
    }

    private void setBlankProfilePicture() {
        if (this.lastRequest != null) {
            ImageDownloader.cancelRequest(this.lastRequest);
        }
        if (this.customizedDefaultProfilePicture == null) {
            setImageBitmap(BitmapFactory.decodeResource(getResources(), isCropped() ? R.drawable.com_facebook_profile_picture_blank_square : R.drawable.com_facebook_profile_picture_blank_portrait));
            return;
        }
        updateImageQueryParameters();
        setImageBitmap(Bitmap.createScaledBitmap(this.customizedDefaultProfilePicture, this.queryWidth, this.queryHeight, false));
    }

    private void setImageBitmap(Bitmap bitmap) {
        if (this.image != null && bitmap != null) {
            this.imageContents = bitmap;
            this.image.setImageBitmap(bitmap);
        }
    }

    private boolean updateImageQueryParameters() {
        boolean z = true;
        int height = getHeight();
        int width = getWidth();
        if (width < 1 || height < 1) {
            return false;
        }
        int presetSizeInPixels = getPresetSizeInPixels(false);
        if (presetSizeInPixels != 0) {
            width = presetSizeInPixels;
            height = presetSizeInPixels;
        }
        if (width <= height) {
            height = isCropped() ? width : 0;
        } else {
            width = isCropped() ? height : 0;
        }
        if (width == this.queryWidth && height == this.queryHeight) {
            z = false;
        }
        this.queryWidth = width;
        this.queryHeight = height;
        return z;
    }

    public final OnErrorListener getOnErrorListener() {
        return this.onErrorListener;
    }

    public final int getPresetSize() {
        return this.presetSizeType;
    }

    public final String getProfileId() {
        return this.profileId;
    }

    public final boolean isCropped() {
        return this.isCropped;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.lastRequest = null;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        refreshImage(false);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        boolean z = false;
        int size = View.MeasureSpec.getSize(i2);
        int size2 = View.MeasureSpec.getSize(i);
        if (View.MeasureSpec.getMode(i2) != 1073741824 && layoutParams.height == -2) {
            size = getPresetSizeInPixels(true);
            i2 = View.MeasureSpec.makeMeasureSpec(size, 1073741824);
            z = true;
        }
        if (View.MeasureSpec.getMode(i) != 1073741824 && layoutParams.width == -2) {
            size2 = getPresetSizeInPixels(true);
            i = View.MeasureSpec.makeMeasureSpec(size2, 1073741824);
            z = true;
        }
        if (z) {
            setMeasuredDimension(size2, size);
            measureChildren(i, i2);
            return;
        }
        super.onMeasure(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable.getClass() != Bundle.class) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable(SUPER_STATE_KEY));
        this.profileId = bundle.getString(PROFILE_ID_KEY);
        this.presetSizeType = bundle.getInt(PRESET_SIZE_KEY);
        this.isCropped = bundle.getBoolean(IS_CROPPED_KEY);
        this.queryWidth = bundle.getInt(BITMAP_WIDTH_KEY);
        this.queryHeight = bundle.getInt(BITMAP_HEIGHT_KEY);
        setImageBitmap((Bitmap) bundle.getParcelable(BITMAP_KEY));
        if (bundle.getBoolean(PENDING_REFRESH_KEY)) {
            refreshImage(true);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SUPER_STATE_KEY, onSaveInstanceState);
        bundle.putString(PROFILE_ID_KEY, this.profileId);
        bundle.putInt(PRESET_SIZE_KEY, this.presetSizeType);
        bundle.putBoolean(IS_CROPPED_KEY, this.isCropped);
        bundle.putParcelable(BITMAP_KEY, this.imageContents);
        bundle.putInt(BITMAP_WIDTH_KEY, this.queryWidth);
        bundle.putInt(BITMAP_HEIGHT_KEY, this.queryHeight);
        bundle.putBoolean(PENDING_REFRESH_KEY, this.lastRequest != null);
        return bundle;
    }

    public final void setCropped(boolean z) {
        this.isCropped = z;
        refreshImage(false);
    }

    public final void setDefaultProfilePicture(Bitmap bitmap) {
        this.customizedDefaultProfilePicture = bitmap;
    }

    public final void setOnErrorListener(OnErrorListener onErrorListener2) {
        this.onErrorListener = onErrorListener2;
    }

    public final void setPresetSize(int i) {
        switch (i) {
            case -4:
            case -3:
            case -2:
            case -1:
                this.presetSizeType = i;
                requestLayout();
                return;
            default:
                throw new IllegalArgumentException("Must use a predefined preset size");
        }
    }

    public final void setProfileId(String str) {
        boolean z = false;
        if (Utility.isNullOrEmpty(this.profileId) || !this.profileId.equalsIgnoreCase(str)) {
            setBlankProfilePicture();
            z = true;
        }
        this.profileId = str;
        refreshImage(z);
    }
}
