package com.tapjoy.mraid.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.tapjoy.TJAdUnitConstants;
import com.tapjoy.TapjoyLog;
import com.tapjoy.mraid.controller.Assets;
import java.io.InputStream;
import java.util.jar.JarFile;

public class Browser extends Activity {
    private static final String BACKGROUND_PNG = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAAsCAIAAAArRUU2AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJ\nbWFnZVJlYWR5ccllPAAAAEFJREFUeNpicPP0Zvr3/z/T/3//gDQQg+i//5j+gum/QBqIQXwg+x+Y\njckH6fkL0/f3NwMPHz8jKxsbAw0AQIABAGYHPKslk98oAAAAAElFTkSuQmCC";
    private static final int BackwardId = 103;
    private static final int ButtonId = 100;
    private static final int ForwardId = 102;
    private static final String LEFT_ARROW_PNG = "iVBORw0KGgoAAAANSUhEUgAAABIAAAAUCAYAAACAl21KAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJ\nbWFnZVJlYWR5ccllPAAAApVJREFUeNqUVEuLkmEYfT6/9/MejjjeRhvvOo7XomLIzRTChEQU0Sa6\nLdoEA21azN6NzG9oIGE2bdwERYggrdqI2SLb5G5o0VwYRZGZ0a/zig5WNo4PHF4+5DnvOed9Htnq\n6irNUuVymeHQARbA5XA4vDirbAYCBQ4NYAIuarXacDQafbK0tJRqNBr32TkIBBxKwAgsKBQKTzgc\nfgCCO36/XxOPx6lYLBKbQsB/vwDYuI3FxcWby8vLDwOBwEIkEiFO4vV6qVarTSYCiYhDC5g5gdFo\nTMLGs2AwGIeSAUEoFCKLxUJqtZpEUfyTaJiDapiDU6lUBmKx2CM03YAKiRNAESFg0ul0BJunvWzM\nhgTMAXauAs13gXsgmOMEUEQul4sMBgMx9q8RNnxOPWDlBHa7/TpufQobbn47J0GoZDKZCApJEISJ\nmbKhEpter78GGy+gYgUklEgkiOdhs9lIo9H8l+CUCDcJaHjsdDpf+nw+3cgGvgnkgyDPU2xvb6+L\n5D96PJ4MpjwJVTQ/Pz8xh7NKgea+SqX6lMvlruzs7DxvtVq/jo6OSJblmYhEt9tNvV6PoETe3Nz8\ncnx8/BoKJazAZVzAuLVp+ZRKpbeDQej3+9RsNimdTsu7u7sHmUzmVT6fT1YqlQ/7+/t0cnIy3dr4\nB29AZrS2tibX6/XvqVTqdqFQuPUNBcuDC8+09ndxq7wR+yRD6Q+o28IDHMDuCmyr+UOM2+XWJhKN\nCnnR4eEhYdd62Wz2MxRtYaYMWI9LyE8YjcZUIl789brdLmFVuLXOxsbGe6vV+g7CglDokSSJqtXq\nm6lEo+L5dDodPu1yu93+ub6+vo3/pa8gu4px2RZm/asdvBC2nm8/9pLMZrMK1sXfAgwAtiLartJw\n+UAAAAAASUVORK5CYII=";
    private static final String REFRESH_PNG = "iVBORw0KGgoAAAANSUhEUgAAABMAAAAUCAYAAABvVQZ0AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJ\nbWFnZVJlYWR5ccllPAAAA2NJREFUeNqMVF1Ik1EYPm7TpsIca84pGs6VrboYaeRPpFgXKUzBH4S8\nsLoIMVCpRERCAgfSZXpR2ZVJCEIgaIQm+MdCIkXRTTTnQG0qorCFm21zp+f9+IQ5NHvh4eP7vnOe\n877Ped5Xyv4dEYAMSK2rq/tkt9tlXq83Cu9BwC8+zyQ4B5wHLgAG4M7IyAgfHR31FhcXv8F7EXAZ\nUADSo43SMCLKQimS0GKDTqfLys7OvlldXZ2Vnp4uy8nJuZGWlmacnp52ejyeANZ4AB/AQ4kigSQg\nT6/XPzebzd8pk4WFBb6+vs6xkVP4/X6+s7PDx8bGPJWVlWasTxGTOJZRInC3oaHh88zMjLDh4OCA\n+3w+4RkIBPhRHB4e8u3tbd7f38+rqqpM2CcJ1YhKu4Vsvq2urnKIzN1uN3c4HHxqaso1Pj7+Y3d3\nVyAicqfTyaHhenNzc1Eo0VF5F2traz+srKwIRFtbW3xoaMjW1tZ2X9Q12WazcZfLxefn53lXV9fb\niooKjZjIsVAkJSXdIw1IFyqvt7d3pLy8PCFksXJgYIAPDg5aGxsbs8OzCY2E+vr692tra4LIFovl\nd15eXmrYqbKmpiZzuNAnRXJ3d7eVStjc3OQo7ZXos7OC5ImjZAA1EE0nSbRarUYulzNoxeCfb/gW\n+A+yGEAvuoC8ZieyoFKpVEulUgYPMVy3Qyzn8IwuiYWNXl5FQB4/9r0gIf+gxG34iEVFRbH8/HwS\nOPakmwovMTMz81JZWZm+oKDAkJiYGENk3o2NjSVYgsXFxbHc3NxCMXX5KYRkFRU2XzEajQZUxSQS\nCdvb2/tJZAfLy8uDyI4pFApmMpkKNRpNJr7rxEaWiaS0NhrQAuk1NTVPoDWDiRmS2RweHrYK9aO0\nFPjMvb+/z3EC7+vr+6VWqx/j321xauhEsa8DJjR9NxIQDI6xxFtbW58emx4tLS0PFhcXhT4kwomJ\nCS/67qNKparH/4fAo4yMDHNHR8c8EdDB1J8w+NRJVors7Ox8t7S0JCwkIH1utVr57Owsn5ub49Ru\ndBAdSC2HG7SVlJRoT7ssaXt7e+3k5KSLiIiQJgRFMBgUmpzIqE97enq+lJaWhrbciYwRKE+NW32G\nIVgcHx9/TSaTMfAxkLsx2ywge40J8zV8bP8VYACAQuluULZPjQAAAABJRU5ErkJggg==";
    private static final String RIGHT_ARROW_PNG = "iVBORw0KGgoAAAANSUhEUgAAABEAAAAUCAYAAABroNZJAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJ\nbWFnZVJlYWR5ccllPAAAAq5JREFUeNqUlN9LmmEUx4++/ihKy6yZGEMjlExdEjgqQmm78ioGu41g\nu7Ftt+5mBF0Go8sIBvsTtggqGgODboK66aIwNi0lJ7p+2TT89b7Pvo/LaKucHTj4+LzP+bzne855\nXpnVan0ZiUSSoiimiCgBP4UXfD4fozpsbW2NhP7+/vdDQ0MvFLBkMnmBfQEuHhwclOHMbDbXhOAM\nCQ6HY8Lr9Tr8fv9ji8Xy6PDw8CKTyUh4LoNzUBkg9l/I4OCg2ePxkNPpfOB2u0cbGhoeQuJZsVjk\nWbHLrMTbYBVIb2/vhMvlMttsNurs7CSj0Si32+0W7D0BRMthOKuAS5cw6brEvyCQQk1NTdTY2Eht\nbW3U1dWlxr6rp6dnJJVKldLpdBEx8kuJYrVeNyAcwE0QBGpubqb29nbq7u7WDgwMjABs39/fz+Ry\nOV4rVoXx9a0QbjKZjJRKJWm1WjIYDIRRMKJeT/ECQzQazZTLZZ4Vb0D5Tsh1mFqtJp1ORyaTSejr\n67MixouMFLFY7AeOnCuoTsMYkV6vJ41GwxugR1cnNzY2POvr66/qhlzPChlRS0tLHuuv6F74XpCq\nYRg/7e7uvh0eHo7wwsrvE5zP53cQPIr6PMcV+T41NVUZvrog6MTPeDz+BoXn1yI0MzMjLS8v09bW\n1p961QpmjJWOj48/rKysvBsfHz+bm5tji4uLBBBls1nCzaeamUD3F1Tf3dHR8RoFPQ0GgywUCtHe\n3h5/dgWoQtg/ur+Fw+Fnra2tfoz7zvT0NFtdXaXt7W06Ojri0m68UI7+5/lCkqRfiUQiGAgEnPj9\nPDs7K3Ldm5ubhP9UKBS4vFuzlqtUqnPo+7i0tGTFpeMfqALXjSEijHdF+13BV/MzNjamWVhYyM7P\nzzOMcSXw5OSESqUS1ft5/C3AAL39YeI2ufApAAAAAElFTkSuQmCC";
    public static final String SHOW_BACK_EXTRA = "open_show_back";
    public static final String SHOW_FORWARD_EXTRA = "open_show_forward";
    public static final String SHOW_REFRESH_EXTRA = "open_show_refresh";
    private static final String TAG = "Mraid Browser";
    private static final String UN_RIGHT_ARROW_PNG = "iVBORw0KGgoAAAANSUhEUgAAABEAAAAUCAYAAABroNZJAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJ\nbWFnZVJlYWR5ccllPAAAAqNJREFUeNqUlEtrE1EUx8+8EjNT27zTaMw0k6RJGpu+VqIi4k7rQtyK\nIn6FrEraRhTET+AnENxUXLpWcFfRhaCbCpY0Lc3DpE07c+dxPTckojTG5sAfhnvP/fE/99wz3IV4\n/Fl1Z+eDZVlVAKigmihjwuejcIpoNZsg+AOBlz6//2E4EonWazUGElC2oesWip7xeIZCMAeEUDhc\npBw3LkqubDqdvs1xQNqt1iHucygGshBEh0LQQREBY2fHx8F2HLfkcl1OJKaut1vtCiEGy6M9V/Yg\nWBcSDIWKHM+PyYoMLpcbJEkCYpoT/mDg1rloNF+tVrcwV0Q5PZjzZ4l/QdiGIAjAoxiI43jOtKyY\nOqXeVRRFrmPgGb5Xot2/rxMQnhe6dA4vRhDFLsymjkApzGta8iYhpNnpdAgrsQ9j3wMh/cB1EAUR\ny3QBsSyPMqbcUFX1UrPRqJimyUAOgw2F9GzhOsLQFXNHiBmMRCeXg8FgfG939wtmtLnszMwuJ4gR\nr98Hoij993E5jgPoAvSjI1A8ngPT0B+JMGIwV263G2RZhulU8lNle/vdyBAWc4XCdijgv7deKr1n\nFzsSJB6L6ZnMdHFtZeVFbrbgxBMa/Pi+BaeCeL1eZ3529tWT8vr9iwsLdiKVhka9BqZBuvtDIRJ2\nY3Fh/jM4dBkBlVQmSxv7NTAMHWzbBkrpcEg+l9ufjIQflFdX32byeRpTVfiJY29Z5u/D/TgBOR+N\nkmw287xcKj2eKczZqqZBs97AthKg2N5B48zjvLOnCzJO6LWrV95g330bG6/XtPS0zeo+aLeBoH3n\nH4CuE5wTktS0r1pSu/O0XP6WzuZop3OIg2Vg3dYJ64OCW1xamvy4ubmXwsOGfgz68TFY+CIdeqq/\nY/f3+EuAAQARwzy3ZhCNHQAAAABJRU5ErkJggg==";
    public static final String URL_EXTRA = "extra_url";
    private static final int WebViewId = 101;

    private Bitmap bitmapFromBase64(String str) {
        byte[] decode = Base64.decode(str, 0);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    /* access modifiers changed from: private */
    public Bitmap getDensityScaledBitmap(String str) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Bitmap bitmapFromBase64 = bitmapFromBase64(str);
        if (bitmapFromBase64 == null) {
            return bitmapFromBase64;
        }
        int width = bitmapFromBase64.getWidth();
        int height = bitmapFromBase64.getHeight();
        float f = displayMetrics.scaledDensity;
        float f2 = displayMetrics.scaledDensity;
        Matrix matrix = new Matrix();
        matrix.postScale(f, f2);
        return Bitmap.createBitmap(bitmapFromBase64, 0, 0, width, height, matrix, true);
    }

    public Bitmap bitmapFromJar(String str) {
        InputStream inputStream = null;
        try {
            String file = Assets.class.getClassLoader().getResource(str).getFile();
            if (file.startsWith("file:")) {
                file = file.substring(5);
            }
            int indexOf = file.indexOf("!");
            if (indexOf > 0) {
                file = file.substring(0, indexOf);
            }
            JarFile jarFile = new JarFile(file);
            InputStream inputStream2 = jarFile.getInputStream(jarFile.getJarEntry(str));
            Bitmap decodeStream = BitmapFactory.decodeStream(inputStream2);
            if (inputStream2 == null) {
                return decodeStream;
            }
            try {
                inputStream2.close();
            } catch (Exception e) {
            }
            return decodeStream;
        } catch (Exception e2) {
            e2.printStackTrace();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e3) {
                }
            }
            return null;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e4) {
                }
            }
            throw th;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        RelativeLayout relativeLayout = new RelativeLayout(this);
        WebView webView = new WebView(this);
        getWindow().requestFeature(2);
        getWindow().setFeatureInt(2, -1);
        Intent intent = getIntent();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(0);
        linearLayout.setId(100);
        linearLayout.setWeightSum(100.0f);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.addRule(2, 100);
        linearLayout.setBackgroundDrawable(new BitmapDrawable(getDensityScaledBitmap(BACKGROUND_PNG)));
        relativeLayout.addView(webView, layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams2.addRule(12);
        relativeLayout.addView(linearLayout, layoutParams2);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -1);
        layoutParams3.weight = 25.0f;
        layoutParams3.gravity = 16;
        ImageButton imageButton = new ImageButton(this);
        imageButton.setBackgroundColor(getResources().getColor(17170445));
        imageButton.setId(BackwardId);
        linearLayout.addView(imageButton, layoutParams3);
        if (!intent.getBooleanExtra(SHOW_BACK_EXTRA, true)) {
            imageButton.setVisibility(8);
        }
        imageButton.setImageBitmap(getDensityScaledBitmap(LEFT_ARROW_PNG));
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                WebView webView = (WebView) Browser.this.findViewById(101);
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    Browser.this.finish();
                }
            }
        });
        ImageButton imageButton2 = new ImageButton(this);
        imageButton2.setBackgroundColor(getResources().getColor(17170445));
        imageButton2.setId(102);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-2, -1);
        layoutParams4.weight = 25.0f;
        layoutParams4.gravity = 16;
        linearLayout.addView(imageButton2, layoutParams4);
        if (!intent.getBooleanExtra(SHOW_FORWARD_EXTRA, true)) {
            imageButton2.setVisibility(8);
        }
        imageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((WebView) Browser.this.findViewById(101)).goForward();
            }
        });
        ImageButton imageButton3 = new ImageButton(this);
        imageButton3.setImageBitmap(getDensityScaledBitmap(REFRESH_PNG));
        imageButton3.setBackgroundColor(getResources().getColor(17170445));
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams5.weight = 25.0f;
        layoutParams5.gravity = 16;
        linearLayout.addView(imageButton3, layoutParams5);
        if (!intent.getBooleanExtra(SHOW_REFRESH_EXTRA, true)) {
            imageButton3.setVisibility(8);
        }
        imageButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((WebView) Browser.this.findViewById(101)).reload();
            }
        });
        ImageButton imageButton4 = new ImageButton(this);
        imageButton4.setBackgroundColor(getResources().getColor(17170445));
        LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams6.weight = 25.0f;
        layoutParams6.gravity = 16;
        linearLayout.addView(imageButton4, layoutParams6);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Browser.this.finish();
            }
        });
        getWindow().requestFeature(2);
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(intent.getStringExtra(URL_EXTRA));
        webView.setId(101);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                ImageButton imageButton = (ImageButton) Browser.this.findViewById(102);
                TapjoyLog.i(Browser.TAG, "onPageFinished: " + str);
                if (webView.canGoForward()) {
                    imageButton.setImageBitmap(Browser.this.getDensityScaledBitmap(Browser.RIGHT_ARROW_PNG));
                } else {
                    imageButton.setImageBitmap(Browser.this.getDensityScaledBitmap(Browser.UN_RIGHT_ARROW_PNG));
                }
            }

            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
                ((ImageButton) Browser.this.findViewById(102)).setImageBitmap(Browser.this.getDensityScaledBitmap(Browser.UN_RIGHT_ARROW_PNG));
            }

            public void onReceivedError(WebView webView, int i, String str, String str2) {
                TapjoyLog.i(Browser.TAG, "WebView error: " + str);
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                webView.loadUrl(str);
                return true;
            }
        });
        setContentView(relativeLayout);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int i) {
                Activity activity = (Activity) webView.getContext();
                activity.setTitle(TJAdUnitConstants.SPINNER_TITLE);
                activity.setProgress(i * 100);
                if (i == 100) {
                    activity.setTitle(webView.getUrl());
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        CookieSyncManager.getInstance().stopSync();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        CookieSyncManager.getInstance().startSync();
    }
}
