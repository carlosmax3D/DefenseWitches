package com.ansca.corona;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TextKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.ansca.corona.events.TextEditingTask;
import com.ansca.corona.events.TextTask;
import com.ansca.corona.graphics.FontServices;
import com.ansca.corona.graphics.TypefaceSettings;
import com.ansca.corona.input.CoronaKeyEvent;
import jp.stargarage.g2metrics.BuildConfig;

public class CoronaEditText extends EditText {
    private static int sBorderPaddingBottom = -1;
    private static int sBorderPaddingLeft = -1;
    private static int sBorderPaddingRight = -1;
    private static int sBorderPaddingTop = -1;
    /* access modifiers changed from: private */
    public int editingAfter = 0;
    /* access modifiers changed from: private */
    public int editingBefore = 0;
    /* access modifiers changed from: private */
    public int editingNumDeleted = 0;
    /* access modifiers changed from: private */
    public int editingStart = 0;
    /* access modifiers changed from: private */
    public boolean isEditing = false;
    private boolean myClearingFocus = false;
    /* access modifiers changed from: private */
    public CoronaRuntime myCoronaRuntime = null;
    /* access modifiers changed from: private */
    public String myCurrentText = BuildConfig.FLAVOR;
    /* access modifiers changed from: private */
    public boolean myIsPassword = false;
    private TextKeyListener myKeyListener = null;
    /* access modifiers changed from: private */
    public String myPreviousText = null;
    private int myTextColor = 0;

    public CoronaEditText(Context context, CoronaRuntime coronaRuntime) {
        super(context);
        this.myCoronaRuntime = coronaRuntime;
        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean z) {
                if (CoronaEditText.this.myCoronaRuntime != null && CoronaEditText.this.myCoronaRuntime.isRunning() && CoronaEditText.this.getId() != 0) {
                    boolean unused = CoronaEditText.this.isEditing = z;
                    CoronaEditText.this.myCoronaRuntime.getTaskDispatcher().send(new TextTask(CoronaEditText.this.getId(), z, false));
                }
            }
        });
        setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean z = (CoronaEditText.this.getInputType() & 131072) == 0;
                if ((z || i != 0) && z && CoronaEditText.this.getId() != 0 && CoronaEditText.this.myCoronaRuntime != null && CoronaEditText.this.myCoronaRuntime.isRunning()) {
                    CoronaEditText.this.myCoronaRuntime.getTaskDispatcher().send(new TextTask(CoronaEditText.this.getId(), false, true));
                }
                return false;
            }
        });
        addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
                String unused = CoronaEditText.this.myCurrentText = editable.toString();
                if (CoronaEditText.this.myCurrentText == null) {
                    String unused2 = CoronaEditText.this.myCurrentText = BuildConfig.FLAVOR;
                }
                if (CoronaEditText.this.isEditing && CoronaEditText.this.getId() != 0) {
                    String str = null;
                    String str2 = null;
                    if (editable != null) {
                        str = CoronaEditText.this.myCurrentText;
                        str2 = editable.subSequence(CoronaEditText.this.editingStart, CoronaEditText.this.editingStart + CoronaEditText.this.editingAfter).toString();
                    }
                    if (CoronaEditText.this.myCoronaRuntime != null && CoronaEditText.this.myCoronaRuntime.isRunning()) {
                        CoronaEditText.this.myCoronaRuntime.getTaskDispatcher().send(new TextEditingTask(CoronaEditText.this.getId(), CoronaEditText.this.editingStart, CoronaEditText.this.editingNumDeleted, str2, CoronaEditText.this.myPreviousText, str));
                    }
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (CoronaEditText.this.isEditing) {
                    String unused = CoronaEditText.this.myPreviousText = new String(charSequence.toString());
                    int unused2 = CoronaEditText.this.editingNumDeleted = i2;
                }
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (CoronaEditText.this.isEditing) {
                    int unused = CoronaEditText.this.editingStart = i;
                    int unused2 = CoronaEditText.this.editingBefore = i2;
                    int unused3 = CoronaEditText.this.editingAfter = i3;
                }
            }
        });
        this.myKeyListener = new TextKeyListener(TextKeyListener.Capitalize.NONE, false) {
            public int getInputType() {
                Controller controller;
                int i = CoronaEditText.this.myIsPassword ? 1 | 128 : 1 | 0;
                return (CoronaEditText.this.myCoronaRuntime == null || (controller = CoronaEditText.this.myCoronaRuntime.getController()) == null) ? i : i | controller.getAndroidVersionSpecific().inputTypeFlagsNoSuggestions();
            }
        };
        setKeyListener(this.myKeyListener);
        setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != 67 || (keyEvent instanceof CoronaKeyEvent)) {
                    return false;
                }
                view.dispatchKeyEvent(new CoronaKeyEvent(keyEvent));
                return true;
            }
        });
        setTransformationMethod(SingleLineTransformationMethod.getInstance());
    }

    private void fetchBorderPadding() {
        if (sBorderPaddingTop < 0 || sBorderPaddingBottom < 0 || sBorderPaddingLeft < 0 || sBorderPaddingRight < 0) {
            sBorderPaddingTop = 0;
            sBorderPaddingBottom = 0;
            sBorderPaddingLeft = 0;
            sBorderPaddingRight = 0;
            int width = getWidth();
            int height = getHeight();
            if (width <= 0 || height <= 0) {
                measure(0, 0);
                layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
                width = getWidth();
                height = getHeight();
            }
            Bitmap bitmap = null;
            try {
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                draw(new Canvas(bitmap));
            } catch (Exception e) {
            }
            if (bitmap != null) {
                boolean z = false;
                boolean z2 = false;
                boolean z3 = false;
                boolean z4 = false;
                int i = -1;
                int i2 = -1;
                int i3 = -1;
                int i4 = -1;
                for (int i5 = 0; i5 < height; i5++) {
                    boolean z5 = false;
                    int i6 = -1;
                    int i7 = -1;
                    for (int i8 = 0; i8 < width; i8++) {
                        if (Color.alpha(bitmap.getPixel(i8, i5)) > 64) {
                            if (!z5) {
                                i6 = i8;
                            }
                            i7 = i8;
                            z5 = true;
                        }
                    }
                    if (z5) {
                        if (!z) {
                            i = i5;
                            z = true;
                        }
                        i2 = i5;
                        z2 = true;
                        if (!z3) {
                            i3 = i6;
                            z3 = true;
                        } else if (i6 < i3) {
                            i3 = i6;
                        }
                        if (!z4) {
                            i4 = i7;
                            z4 = true;
                        } else if (i7 < i4) {
                            i4 = i7;
                        }
                    }
                }
                if (z) {
                    sBorderPaddingTop = i;
                }
                if (z2) {
                    sBorderPaddingBottom = height - (i2 + 1);
                }
                if (z3) {
                    sBorderPaddingLeft = i3;
                }
                if (z4) {
                    sBorderPaddingRight = width - (i4 + 1);
                }
                bitmap.recycle();
            }
        }
    }

    public void clearFocus() {
        this.myClearingFocus = true;
        super.clearFocus();
        this.myClearingFocus = false;
    }

    public int getBorderPaddingBottom() {
        fetchBorderPadding();
        return sBorderPaddingBottom;
    }

    public int getBorderPaddingLeft() {
        fetchBorderPadding();
        return sBorderPaddingLeft;
    }

    public int getBorderPaddingRight() {
        fetchBorderPadding();
        return sBorderPaddingRight;
    }

    public int getBorderPaddingTop() {
        fetchBorderPadding();
        return sBorderPaddingTop;
    }

    public String getTextString() {
        Looper mainLooper = Looper.getMainLooper();
        return (mainLooper == null || mainLooper.getThread() != Thread.currentThread()) ? this.myCurrentText : getText().toString();
    }

    public String getTextViewAlign() {
        switch (getGravity() & 7) {
            case 1:
                return "center";
            case 3:
                return "left";
            case 5:
                return "right";
            default:
                return "unknown";
        }
    }

    public int getTextViewColor() {
        return this.myTextColor;
    }

    public String getTextViewInputType() {
        KeyListener keyListener = getKeyListener();
        if (keyListener == null) {
            return "unknown";
        }
        int inputType = keyListener.getInputType();
        switch (inputType & 15) {
            case 1:
                switch (inputType & 4080) {
                    case 16:
                        return "url";
                    case 32:
                        return "email";
                    default:
                        return AdNetworkKey.DEFAULT;
                }
            case 2:
                return "number";
            case 3:
                return "phone";
            default:
                return AdNetworkKey.DEFAULT;
        }
    }

    public boolean getTextViewPassword() {
        return this.myIsPassword;
    }

    public float getTextViewSize() {
        return getTextSize();
    }

    public boolean isReadOnly() {
        return getKeyListener() == null;
    }

    public boolean requestFocus(int i, Rect rect) {
        if (this.myClearingFocus) {
            return false;
        }
        return super.requestFocus(i, rect);
    }

    public void setReadOnly(boolean z) {
        setKeyListener(z ? null : this.myKeyListener);
    }

    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        boolean z = this.isEditing;
        this.isEditing = false;
        super.setText(charSequence, bufferType);
        this.isEditing = z;
    }

    public void setTextViewAlign(String str) {
        int i = 3;
        if ("left".equals(str)) {
            i = 3;
        } else if ("center".equals(str)) {
            i = 1;
        } else if ("right".equals(str)) {
            i = 5;
        }
        setGravity(i | (getGravity() & 112));
    }

    public void setTextViewColor(int i) {
        this.myTextColor = i;
        setTextColor(i);
    }

    public void setTextViewFont(String str, float f, boolean z) {
        TypefaceSettings typefaceSettings = new TypefaceSettings();
        typefaceSettings.setName(str);
        typefaceSettings.setIsBold(z);
        setTypeface(new FontServices(getContext()).fetchTypefaceFor(typefaceSettings), typefaceSettings.getAndroidTypefaceStyle());
        setTextSize(0, f);
    }

    public void setTextViewInputType(String str) {
        int i;
        if ("number".equals(str)) {
            i = 2;
        } else if ("phone".equals(str)) {
            i = 3;
        } else if ("url".equals(str)) {
            i = 17;
        } else if ("email".equals(str)) {
            i = 33;
        } else {
            i = 1;
            if (!this.myIsPassword) {
                i = 1 | 0;
            }
        }
        if (this.myIsPassword) {
            i |= 128;
        }
        int inputTypeFlagsNoSuggestions = i | this.myCoronaRuntime.getController().getAndroidVersionSpecific().inputTypeFlagsNoSuggestions();
        if (inputTypeFlagsNoSuggestions != 0) {
            setInputType(inputTypeFlagsNoSuggestions);
        }
    }

    public void setTextViewPassword(boolean z) {
        setTransformationMethod(z ? PasswordTransformationMethod.getInstance() : SingleLineTransformationMethod.getInstance());
        this.myIsPassword = z;
        setTextViewInputType("password");
    }

    public void setTextViewSize(float f) {
        setTextSize(0, f);
    }
}
