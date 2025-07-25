package jp.newgate.game.android.dw;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class DefenseInformationWebview extends WebView {
    public DefenseInformationWebview(Context context) {
        super(context);
    }

    public DefenseInformationWebview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void initView() {
        loadUrl("http://dw.n-gate.jp/app-info_jp/");
        setInitialScale(1);
        getSettings().setUseWideViewPort(true);
    }
}
