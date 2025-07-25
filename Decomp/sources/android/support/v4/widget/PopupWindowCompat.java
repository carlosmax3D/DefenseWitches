package android.support.v4.widget;

import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

public class PopupWindowCompat {
    static final PopupWindowImpl IMPL;

    static class BasePopupWindowImpl implements PopupWindowImpl {
        BasePopupWindowImpl() {
        }

        public void showAsDropDown(PopupWindow popupWindow, View view, int i, int i2, int i3) {
            popupWindow.showAsDropDown(view, i, i2);
        }
    }

    static class KitKatPopupWindowImpl extends BasePopupWindowImpl {
        KitKatPopupWindowImpl() {
        }

        public void showAsDropDown(PopupWindow popupWindow, View view, int i, int i2, int i3) {
            PopupWindowCompatKitKat.showAsDropDown(popupWindow, view, i, i2, i3);
        }
    }

    interface PopupWindowImpl {
        void showAsDropDown(PopupWindow popupWindow, View view, int i, int i2, int i3);
    }

    static {
        if (Build.VERSION.SDK_INT >= 19) {
            IMPL = new KitKatPopupWindowImpl();
        } else {
            IMPL = new BasePopupWindowImpl();
        }
    }

    private PopupWindowCompat() {
    }

    public static void showAsDropDown(PopupWindow popupWindow, View view, int i, int i2, int i3) {
        IMPL.showAsDropDown(popupWindow, view, i, i2, i3);
    }
}
