package android.support.v4.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v4.print.PrintHelperKitkat;
import java.io.FileNotFoundException;

public final class PrintHelper {
    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    PrintHelperVersionImpl mImpl;

    public interface OnPrintFinishCallback {
        void onFinish();
    }

    private static final class PrintHelperKitkatImpl implements PrintHelperVersionImpl {
        private final PrintHelperKitkat mPrintHelper;

        PrintHelperKitkatImpl(Context context) {
            this.mPrintHelper = new PrintHelperKitkat(context);
        }

        public int getColorMode() {
            return this.mPrintHelper.getColorMode();
        }

        public int getOrientation() {
            return this.mPrintHelper.getOrientation();
        }

        public int getScaleMode() {
            return this.mPrintHelper.getScaleMode();
        }

        public void printBitmap(String str, Bitmap bitmap, final OnPrintFinishCallback onPrintFinishCallback) {
            AnonymousClass1 r0 = null;
            if (onPrintFinishCallback != null) {
                r0 = new PrintHelperKitkat.OnPrintFinishCallback() {
                    public void onFinish() {
                        onPrintFinishCallback.onFinish();
                    }
                };
            }
            this.mPrintHelper.printBitmap(str, bitmap, (PrintHelperKitkat.OnPrintFinishCallback) r0);
        }

        public void printBitmap(String str, Uri uri, final OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
            AnonymousClass2 r0 = null;
            if (onPrintFinishCallback != null) {
                r0 = new PrintHelperKitkat.OnPrintFinishCallback() {
                    public void onFinish() {
                        onPrintFinishCallback.onFinish();
                    }
                };
            }
            this.mPrintHelper.printBitmap(str, uri, (PrintHelperKitkat.OnPrintFinishCallback) r0);
        }

        public void setColorMode(int i) {
            this.mPrintHelper.setColorMode(i);
        }

        public void setOrientation(int i) {
            this.mPrintHelper.setOrientation(i);
        }

        public void setScaleMode(int i) {
            this.mPrintHelper.setScaleMode(i);
        }
    }

    private static final class PrintHelperStubImpl implements PrintHelperVersionImpl {
        int mColorMode;
        int mOrientation;
        int mScaleMode;

        private PrintHelperStubImpl() {
            this.mScaleMode = 2;
            this.mColorMode = 2;
            this.mOrientation = 1;
        }

        public int getColorMode() {
            return this.mColorMode;
        }

        public int getOrientation() {
            return this.mOrientation;
        }

        public int getScaleMode() {
            return this.mScaleMode;
        }

        public void printBitmap(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
        }

        public void printBitmap(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) {
        }

        public void setColorMode(int i) {
            this.mColorMode = i;
        }

        public void setOrientation(int i) {
            this.mOrientation = i;
        }

        public void setScaleMode(int i) {
            this.mScaleMode = i;
        }
    }

    interface PrintHelperVersionImpl {
        int getColorMode();

        int getOrientation();

        int getScaleMode();

        void printBitmap(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback);

        void printBitmap(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException;

        void setColorMode(int i);

        void setOrientation(int i);

        void setScaleMode(int i);
    }

    public PrintHelper(Context context) {
        if (systemSupportsPrint()) {
            this.mImpl = new PrintHelperKitkatImpl(context);
        } else {
            this.mImpl = new PrintHelperStubImpl();
        }
    }

    public static boolean systemSupportsPrint() {
        return Build.VERSION.SDK_INT >= 19;
    }

    public int getColorMode() {
        return this.mImpl.getColorMode();
    }

    public int getOrientation() {
        return this.mImpl.getOrientation();
    }

    public int getScaleMode() {
        return this.mImpl.getScaleMode();
    }

    public void printBitmap(String str, Bitmap bitmap) {
        this.mImpl.printBitmap(str, bitmap, (OnPrintFinishCallback) null);
    }

    public void printBitmap(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
        this.mImpl.printBitmap(str, bitmap, onPrintFinishCallback);
    }

    public void printBitmap(String str, Uri uri) throws FileNotFoundException {
        this.mImpl.printBitmap(str, uri, (OnPrintFinishCallback) null);
    }

    public void printBitmap(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
        this.mImpl.printBitmap(str, uri, onPrintFinishCallback);
    }

    public void setColorMode(int i) {
        this.mImpl.setColorMode(i);
    }

    public void setOrientation(int i) {
        this.mImpl.setOrientation(i);
    }

    public void setScaleMode(int i) {
        this.mImpl.setScaleMode(i);
    }
}
