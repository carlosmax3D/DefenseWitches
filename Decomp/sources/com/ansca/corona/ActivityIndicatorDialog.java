package com.ansca.corona;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class ActivityIndicatorDialog extends Dialog {
    private boolean fIsCancelable;

    public ActivityIndicatorDialog(Context context) {
        this(context, 0);
    }

    public ActivityIndicatorDialog(Context context, int i) {
        super(context, i);
        this.fIsCancelable = true;
        requestWindowFeature(1);
        ProgressBar progressBar = new ProgressBar(context);
        int minimumWidth = progressBar.getIndeterminateDrawable().getMinimumWidth() / 2;
        int minimumHeight = progressBar.getIndeterminateDrawable().getMinimumHeight() / 2;
        progressBar.setPadding(minimumWidth, minimumWidth, minimumHeight, minimumHeight);
        addContentView(progressBar, new ViewGroup.LayoutParams(-2, -2));
    }

    public boolean isCancelable() {
        return this.fIsCancelable;
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (this.fIsCancelable || i != 84) {
            return super.onKeyUp(i, keyEvent);
        }
        return true;
    }

    public void setCancelable(boolean z) {
        this.fIsCancelable = z;
        super.setCancelable(z);
    }
}
