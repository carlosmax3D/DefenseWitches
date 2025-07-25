package com.facebook.login.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.ExploreByTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.facebook.R;
import java.lang.ref.WeakReference;

public class ToolTipPopup {
    public static final long DEFAULT_POPUP_DISPLAY_TIME = 6000;
    /* access modifiers changed from: private */
    public final WeakReference<View> mAnchorViewRef;
    private final Context mContext;
    private long mNuxDisplayTime = DEFAULT_POPUP_DISPLAY_TIME;
    /* access modifiers changed from: private */
    public PopupContentView mPopupContent;
    /* access modifiers changed from: private */
    public PopupWindow mPopupWindow;
    private final ViewTreeObserver.OnScrollChangedListener mScrollListener = new ViewTreeObserver.OnScrollChangedListener() {
        public void onScrollChanged() {
            if (ToolTipPopup.this.mAnchorViewRef.get() != null && ToolTipPopup.this.mPopupWindow != null && ToolTipPopup.this.mPopupWindow.isShowing()) {
                if (ToolTipPopup.this.mPopupWindow.isAboveAnchor()) {
                    ToolTipPopup.this.mPopupContent.showBottomArrow();
                } else {
                    ToolTipPopup.this.mPopupContent.showTopArrow();
                }
            }
        }
    };
    private Style mStyle = Style.BLUE;
    private final String mText;

    private class PopupContentView extends FrameLayout {
        /* access modifiers changed from: private */
        public View bodyFrame;
        /* access modifiers changed from: private */
        public ImageView bottomArrow;
        /* access modifiers changed from: private */
        public ImageView topArrow;
        /* access modifiers changed from: private */
        public ImageView xOut;

        public PopupContentView(Context context) {
            super(context);
            init();
        }

        private void init() {
            LayoutInflater.from(getContext()).inflate(R.layout.com_facebook_tooltip_bubble, this);
            this.topArrow = (ImageView) findViewById(R.id.com_facebook_tooltip_bubble_view_top_pointer);
            this.bottomArrow = (ImageView) findViewById(R.id.com_facebook_tooltip_bubble_view_bottom_pointer);
            this.bodyFrame = findViewById(R.id.com_facebook_body_frame);
            this.xOut = (ImageView) findViewById(R.id.com_facebook_button_xout);
        }

        public void showBottomArrow() {
            this.topArrow.setVisibility(4);
            this.bottomArrow.setVisibility(0);
        }

        public void showTopArrow() {
            this.topArrow.setVisibility(0);
            this.bottomArrow.setVisibility(4);
        }
    }

    public enum Style {
        BLUE,
        BLACK
    }

    public ToolTipPopup(String str, View view) {
        this.mText = str;
        this.mAnchorViewRef = new WeakReference<>(view);
        this.mContext = view.getContext();
    }

    private void registerObserver() {
        unregisterObserver();
        if (this.mAnchorViewRef.get() != null) {
            ((View) this.mAnchorViewRef.get()).getViewTreeObserver().addOnScrollChangedListener(this.mScrollListener);
        }
    }

    private void unregisterObserver() {
        if (this.mAnchorViewRef.get() != null) {
            ((View) this.mAnchorViewRef.get()).getViewTreeObserver().removeOnScrollChangedListener(this.mScrollListener);
        }
    }

    private void updateArrows() {
        if (this.mPopupWindow != null && this.mPopupWindow.isShowing()) {
            if (this.mPopupWindow.isAboveAnchor()) {
                this.mPopupContent.showBottomArrow();
            } else {
                this.mPopupContent.showTopArrow();
            }
        }
    }

    public void dismiss() {
        unregisterObserver();
        if (this.mPopupWindow != null) {
            this.mPopupWindow.dismiss();
        }
    }

    public void setNuxDisplayTime(long j) {
        this.mNuxDisplayTime = j;
    }

    public void setStyle(Style style) {
        this.mStyle = style;
    }

    public void show() {
        if (this.mAnchorViewRef.get() != null) {
            this.mPopupContent = new PopupContentView(this.mContext);
            ((TextView) this.mPopupContent.findViewById(R.id.com_facebook_tooltip_bubble_view_text_body)).setText(this.mText);
            if (this.mStyle == Style.BLUE) {
                this.mPopupContent.bodyFrame.setBackgroundResource(R.drawable.com_facebook_tooltip_blue_background);
                this.mPopupContent.bottomArrow.setImageResource(R.drawable.com_facebook_tooltip_blue_bottomnub);
                this.mPopupContent.topArrow.setImageResource(R.drawable.com_facebook_tooltip_blue_topnub);
                this.mPopupContent.xOut.setImageResource(R.drawable.com_facebook_tooltip_blue_xout);
            } else {
                this.mPopupContent.bodyFrame.setBackgroundResource(R.drawable.com_facebook_tooltip_black_background);
                this.mPopupContent.bottomArrow.setImageResource(R.drawable.com_facebook_tooltip_black_bottomnub);
                this.mPopupContent.topArrow.setImageResource(R.drawable.com_facebook_tooltip_black_topnub);
                this.mPopupContent.xOut.setImageResource(R.drawable.com_facebook_tooltip_black_xout);
            }
            View decorView = ((Activity) this.mContext).getWindow().getDecorView();
            int width = decorView.getWidth();
            int height = decorView.getHeight();
            registerObserver();
            this.mPopupContent.measure(View.MeasureSpec.makeMeasureSpec(width, ExploreByTouchHelper.INVALID_ID), View.MeasureSpec.makeMeasureSpec(height, ExploreByTouchHelper.INVALID_ID));
            this.mPopupWindow = new PopupWindow(this.mPopupContent, this.mPopupContent.getMeasuredWidth(), this.mPopupContent.getMeasuredHeight());
            this.mPopupWindow.showAsDropDown((View) this.mAnchorViewRef.get());
            updateArrows();
            if (this.mNuxDisplayTime > 0) {
                this.mPopupContent.postDelayed(new Runnable() {
                    public void run() {
                        ToolTipPopup.this.dismiss();
                    }
                }, this.mNuxDisplayTime);
            }
            this.mPopupWindow.setTouchable(true);
            this.mPopupContent.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ToolTipPopup.this.dismiss();
                }
            });
        }
    }
}
