package com.tapjoy;

import android.view.View;

public interface TapjoyDisplayAdNotifier {
    void getDisplayAdResponse(View view);

    void getDisplayAdResponseFailed(String str);
}
