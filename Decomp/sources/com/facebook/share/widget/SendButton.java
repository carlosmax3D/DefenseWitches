package com.facebook.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.facebook.R;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.CallbackManagerImpl;

public final class SendButton extends ShareButtonBase {
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.Message.toRequestCode();

    public SendButton(Context context) {
        super(context, (AttributeSet) null, 0, AnalyticsEvents.EVENT_SEND_BUTTON_CREATE, DEFAULT_REQUEST_CODE);
    }

    public SendButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, AnalyticsEvents.EVENT_SEND_BUTTON_CREATE, DEFAULT_REQUEST_CODE);
    }

    public SendButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, AnalyticsEvents.EVENT_SEND_BUTTON_CREATE, DEFAULT_REQUEST_CODE);
    }

    /* access modifiers changed from: protected */
    public int getDefaultStyleResource() {
        return R.style.com_facebook_button_send;
    }

    /* access modifiers changed from: protected */
    public View.OnClickListener getShareOnClickListener() {
        return new View.OnClickListener() {
            public void onClick(View view) {
                (SendButton.this.getFragment() != null ? new MessageDialog(SendButton.this.getFragment(), SendButton.this.getRequestCode()) : new MessageDialog(SendButton.this.getActivity(), SendButton.this.getRequestCode())).show(SendButton.this.getShareContent());
                SendButton.this.callExternalOnClickListener(view);
            }
        };
    }
}
