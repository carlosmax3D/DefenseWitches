package com.facebook.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.facebook.R;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.CallbackManagerImpl;

public final class ShareButton extends ShareButtonBase {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.Share.toRequestCode();

    public ShareButton(Context context) {
        super(context, (AttributeSet) null, 0, AnalyticsEvents.EVENT_SHARE_BUTTON_CREATE, DEFAULT_REQUEST_CODE);
    }

    public ShareButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, AnalyticsEvents.EVENT_SHARE_BUTTON_CREATE, DEFAULT_REQUEST_CODE);
    }

    public ShareButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, AnalyticsEvents.EVENT_SHARE_BUTTON_CREATE, DEFAULT_REQUEST_CODE);
    }

    /* access modifiers changed from: protected */
    public int getDefaultStyleResource() {
        return R.style.com_facebook_button_share;
    }

    /* access modifiers changed from: protected */
    public View.OnClickListener getShareOnClickListener() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                (ShareButton.this.getFragment() != null ? new ShareDialog(ShareButton.this.getFragment(), ShareButton.this.getRequestCode()) : new ShareDialog(ShareButton.this.getActivity(), ShareButton.this.getRequestCode())).show(ShareButton.this.getShareContent());
                ShareButton.this.callExternalOnClickListener(view);
            }
        };
    }
}
