package com.google.android.gms.ads.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.internal.ag;

public final class SearchAdView extends ViewGroup {
    private final ag dZ;

    public SearchAdView(Context context) {
        super(context);
        this.dZ = new ag(this);
    }

    public SearchAdView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.dZ = new ag(this, attributeSet, false);
    }

    public SearchAdView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.dZ = new ag(this, attributeSet, false);
    }

    public void destroy() {
        this.dZ.destroy();
    }

    public AdListener getAdListener() {
        return this.dZ.getAdListener();
    }

    public AdSize getAdSize() {
        return this.dZ.getAdSize();
    }

    public String getAdUnitId() {
        return this.dZ.getAdUnitId();
    }

    public void loadAd(SearchAdRequest searchAdRequest) {
        this.dZ.a(searchAdRequest.v());
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View childAt = getChildAt(0);
        if (childAt != null && childAt.getVisibility() != 8) {
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            int i5 = ((i3 - i) - measuredWidth) / 2;
            int i6 = ((i4 - i2) - measuredHeight) / 2;
            childAt.layout(i5, i6, measuredWidth + i5, measuredHeight + i6);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4 = 0;
        View childAt = getChildAt(0);
        AdSize adSize = getAdSize();
        if (childAt != null && childAt.getVisibility() != 8) {
            measureChild(childAt, i, i2);
            i3 = childAt.getMeasuredWidth();
            i4 = childAt.getMeasuredHeight();
        } else if (adSize != null) {
            Context context = getContext();
            i3 = adSize.getWidthInPixels(context);
            i4 = adSize.getHeightInPixels(context);
        } else {
            i3 = 0;
        }
        setMeasuredDimension(View.resolveSize(Math.max(i3, getSuggestedMinimumWidth()), i), View.resolveSize(Math.max(i4, getSuggestedMinimumHeight()), i2));
    }

    public void pause() {
        this.dZ.pause();
    }

    public void resume() {
        this.dZ.resume();
    }

    public void setAdListener(AdListener adListener) {
        this.dZ.setAdListener(adListener);
    }

    public void setAdSize(AdSize adSize) {
        this.dZ.setAdSizes(adSize);
    }

    public void setAdUnitId(String str) {
        this.dZ.setAdUnitId(str);
    }
}
