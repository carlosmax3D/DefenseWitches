package android.support.v4.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

class PrintHelperKitkat {
    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    private static final String LOG_TAG = "PrintHelperKitkat";
    private static final int MAX_PRINT_SIZE = 3500;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    int mColorMode = 2;
    final Context mContext;
    BitmapFactory.Options mDecodeOptions = null;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    int mOrientation = 1;
    int mScaleMode = 2;

    public interface OnPrintFinishCallback {
        void onFinish();
    }

    PrintHelperKitkat(Context context) {
        this.mContext = context;
    }

    /* access modifiers changed from: private */
    public Matrix getMatrix(int i, int i2, RectF rectF, int i3) {
        Matrix matrix = new Matrix();
        float width = rectF.width() / ((float) i);
        float max = i3 == 2 ? Math.max(width, rectF.height() / ((float) i2)) : Math.min(width, rectF.height() / ((float) i2));
        matrix.postScale(max, max);
        matrix.postTranslate((rectF.width() - (((float) i) * max)) / 2.0f, (rectF.height() - (((float) i2) * max)) / 2.0f);
        return matrix;
    }

    private Bitmap loadBitmap(Uri uri, BitmapFactory.Options options) throws FileNotFoundException {
        if (uri == null || this.mContext == null) {
            throw new IllegalArgumentException("bad argument to loadBitmap");
        }
        InputStream inputStream = null;
        try {
            inputStream = this.mContext.getContentResolver().openInputStream(uri);
            Bitmap decodeStream = BitmapFactory.decodeStream(inputStream, (Rect) null, options);
            if (inputStream != null) {
                try {
                } catch (IOException e) {
                    Log.w(LOG_TAG, "close fail ", e);
                }
            }
            return decodeStream;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    Log.w(LOG_TAG, "close fail ", e2);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public Bitmap loadConstrainedBitmap(Uri uri, int i) throws FileNotFoundException {
        BitmapFactory.Options options;
        Bitmap bitmap = null;
        if (i <= 0 || uri == null || this.mContext == null) {
            throw new IllegalArgumentException("bad argument to getScaledBitmap");
        }
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inJustDecodeBounds = true;
        loadBitmap(uri, options2);
        int i2 = options2.outWidth;
        int i3 = options2.outHeight;
        if (i2 > 0 && i3 > 0) {
            int max = Math.max(i2, i3);
            int i4 = 1;
            while (max > i) {
                max >>>= 1;
                i4 <<= 1;
            }
            if (i4 > 0 && Math.min(i2, i3) / i4 > 0) {
                synchronized (this.mLock) {
                    this.mDecodeOptions = new BitmapFactory.Options();
                    this.mDecodeOptions.inMutable = true;
                    this.mDecodeOptions.inSampleSize = i4;
                    options = this.mDecodeOptions;
                }
                try {
                    bitmap = loadBitmap(uri, options);
                    synchronized (this.mLock) {
                        this.mDecodeOptions = null;
                    }
                } catch (Throwable th) {
                    synchronized (this.mLock) {
                        this.mDecodeOptions = null;
                        throw th;
                    }
                }
            }
        }
        return bitmap;
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
        if (bitmap != null) {
            final int i = this.mScaleMode;
            PrintManager printManager = (PrintManager) this.mContext.getSystemService("print");
            PrintAttributes.MediaSize mediaSize = PrintAttributes.MediaSize.UNKNOWN_PORTRAIT;
            if (bitmap.getWidth() > bitmap.getHeight()) {
                mediaSize = PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE;
            }
            final String str2 = str;
            final Bitmap bitmap2 = bitmap;
            final OnPrintFinishCallback onPrintFinishCallback2 = onPrintFinishCallback;
            printManager.print(str, new PrintDocumentAdapter() {
                private PrintAttributes mAttributes;

                public void onFinish() {
                    if (onPrintFinishCallback2 != null) {
                        onPrintFinishCallback2.onFinish();
                    }
                }

                public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
                    boolean z = true;
                    this.mAttributes = printAttributes2;
                    PrintDocumentInfo build = new PrintDocumentInfo.Builder(str2).setContentType(1).setPageCount(1).build();
                    if (printAttributes2.equals(printAttributes)) {
                        z = false;
                    }
                    layoutResultCallback.onLayoutFinished(build, z);
                }

                public void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
                    PrintedPdfDocument printedPdfDocument = new PrintedPdfDocument(PrintHelperKitkat.this.mContext, this.mAttributes);
                    try {
                        PdfDocument.Page startPage = printedPdfDocument.startPage(1);
                        startPage.getCanvas().drawBitmap(bitmap2, PrintHelperKitkat.this.getMatrix(bitmap2.getWidth(), bitmap2.getHeight(), new RectF(startPage.getInfo().getContentRect()), i), (Paint) null);
                        printedPdfDocument.finishPage(startPage);
                        printedPdfDocument.writeTo(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
                        writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                    } catch (IOException e) {
                        Log.e(PrintHelperKitkat.LOG_TAG, "Error writing printed content", e);
                        writeResultCallback.onWriteFailed((CharSequence) null);
                    } catch (Throwable th) {
                        if (printedPdfDocument != null) {
                            printedPdfDocument.close();
                        }
                        if (parcelFileDescriptor != null) {
                            try {
                                parcelFileDescriptor.close();
                            } catch (IOException e2) {
                            }
                        }
                        throw th;
                    }
                    if (printedPdfDocument != null) {
                        printedPdfDocument.close();
                    }
                    if (parcelFileDescriptor != null) {
                        try {
                            parcelFileDescriptor.close();
                        } catch (IOException e3) {
                        }
                    }
                }
            }, new PrintAttributes.Builder().setMediaSize(mediaSize).setColorMode(this.mColorMode).build());
        }
    }

    public void printBitmap(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
        final int i = this.mScaleMode;
        final String str2 = str;
        final Uri uri2 = uri;
        final OnPrintFinishCallback onPrintFinishCallback2 = onPrintFinishCallback;
        AnonymousClass2 r0 = new PrintDocumentAdapter() {
            private PrintAttributes mAttributes;
            Bitmap mBitmap = null;
            AsyncTask<Uri, Boolean, Bitmap> mLoadBitmap;

            /* access modifiers changed from: private */
            public void cancelLoad() {
                synchronized (PrintHelperKitkat.this.mLock) {
                    if (PrintHelperKitkat.this.mDecodeOptions != null) {
                        PrintHelperKitkat.this.mDecodeOptions.requestCancelDecode();
                        PrintHelperKitkat.this.mDecodeOptions = null;
                    }
                }
            }

            public void onFinish() {
                super.onFinish();
                cancelLoad();
                if (this.mLoadBitmap != null) {
                    this.mLoadBitmap.cancel(true);
                }
                if (onPrintFinishCallback2 != null) {
                    onPrintFinishCallback2.onFinish();
                }
            }

            public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
                boolean z = true;
                this.mAttributes = printAttributes2;
                if (cancellationSignal.isCanceled()) {
                    layoutResultCallback.onLayoutCancelled();
                } else if (this.mBitmap != null) {
                    PrintDocumentInfo build = new PrintDocumentInfo.Builder(str2).setContentType(1).setPageCount(1).build();
                    if (printAttributes2.equals(printAttributes)) {
                        z = false;
                    }
                    layoutResultCallback.onLayoutFinished(build, z);
                } else {
                    final CancellationSignal cancellationSignal2 = cancellationSignal;
                    final PrintAttributes printAttributes3 = printAttributes2;
                    final PrintAttributes printAttributes4 = printAttributes;
                    final PrintDocumentAdapter.LayoutResultCallback layoutResultCallback2 = layoutResultCallback;
                    this.mLoadBitmap = new AsyncTask<Uri, Boolean, Bitmap>() {
                        /* access modifiers changed from: protected */
                        public Bitmap doInBackground(Uri... uriArr) {
                            try {
                                return PrintHelperKitkat.this.loadConstrainedBitmap(uri2, PrintHelperKitkat.MAX_PRINT_SIZE);
                            } catch (FileNotFoundException e) {
                                return null;
                            }
                        }

                        /* access modifiers changed from: protected */
                        public void onCancelled(Bitmap bitmap) {
                            layoutResultCallback2.onLayoutCancelled();
                            AnonymousClass2.this.mLoadBitmap = null;
                        }

                        /* access modifiers changed from: protected */
                        public void onPostExecute(Bitmap bitmap) {
                            boolean z = true;
                            super.onPostExecute(bitmap);
                            AnonymousClass2.this.mBitmap = bitmap;
                            if (bitmap != null) {
                                PrintDocumentInfo build = new PrintDocumentInfo.Builder(str2).setContentType(1).setPageCount(1).build();
                                if (printAttributes3.equals(printAttributes4)) {
                                    z = false;
                                }
                                layoutResultCallback2.onLayoutFinished(build, z);
                            } else {
                                layoutResultCallback2.onLayoutFailed((CharSequence) null);
                            }
                            AnonymousClass2.this.mLoadBitmap = null;
                        }

                        /* access modifiers changed from: protected */
                        public void onPreExecute() {
                            cancellationSignal2.setOnCancelListener(new CancellationSignal.OnCancelListener() {
                                public void onCancel() {
                                    AnonymousClass2.this.cancelLoad();
                                    AnonymousClass1.this.cancel(false);
                                }
                            });
                        }
                    }.execute(new Uri[0]);
                }
            }

            public void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
                PrintedPdfDocument printedPdfDocument = new PrintedPdfDocument(PrintHelperKitkat.this.mContext, this.mAttributes);
                try {
                    PdfDocument.Page startPage = printedPdfDocument.startPage(1);
                    startPage.getCanvas().drawBitmap(this.mBitmap, PrintHelperKitkat.this.getMatrix(this.mBitmap.getWidth(), this.mBitmap.getHeight(), new RectF(startPage.getInfo().getContentRect()), i), (Paint) null);
                    printedPdfDocument.finishPage(startPage);
                    printedPdfDocument.writeTo(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
                    writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                } catch (IOException e) {
                    Log.e(PrintHelperKitkat.LOG_TAG, "Error writing printed content", e);
                    writeResultCallback.onWriteFailed((CharSequence) null);
                } catch (Throwable th) {
                    if (printedPdfDocument != null) {
                        printedPdfDocument.close();
                    }
                    if (parcelFileDescriptor != null) {
                        try {
                            parcelFileDescriptor.close();
                        } catch (IOException e2) {
                        }
                    }
                    throw th;
                }
                if (printedPdfDocument != null) {
                    printedPdfDocument.close();
                }
                if (parcelFileDescriptor != null) {
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException e3) {
                    }
                }
            }
        };
        PrintManager printManager = (PrintManager) this.mContext.getSystemService("print");
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setColorMode(this.mColorMode);
        if (this.mOrientation == 1) {
            builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
        } else if (this.mOrientation == 2) {
            builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
        }
        printManager.print(str, r0, builder.build());
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
