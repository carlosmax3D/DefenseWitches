package com.ansca.corona;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import com.ansca.corona.storage.ResourceServices;
import java.io.File;
import java.io.FileOutputStream;

public class CameraActivity extends Activity {
    /* access modifiers changed from: private */
    public static int sNextImageFileNumber = 1;
    /* access modifiers changed from: private */
    public CameraView fCameraView;

    static /* synthetic */ int access$008() {
        int i = sNextImageFileNumber;
        sNextImageFileNumber = i + 1;
        return i;
    }

    public static void clearCachedPhotos(Context context) {
        for (int i = 1; i < sNextImageFileNumber; i++) {
            try {
                File createCameraShotFileObjectWith = createCameraShotFileObjectWith(context, i);
                if (createCameraShotFileObjectWith != null) {
                    createCameraShotFileObjectWith.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sNextImageFileNumber = 1;
    }

    /* access modifiers changed from: private */
    public static File createCameraShotFileObjectWith(Context context, int i) {
        if (context == null) {
            return null;
        }
        return new File(context.getCacheDir(), "CameraShot" + Integer.toString(i) + ".jpg");
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ResourceServices resourceServices = new ResourceServices(this);
        FrameLayout frameLayout = new FrameLayout(this);
        setContentView(frameLayout);
        this.fCameraView = new CameraView(this);
        frameLayout.addView(this.fCameraView);
        this.fCameraView.setTakePictureListener(new Camera.PictureCallback() {
            public void onPictureTaken(byte[] bArr, Camera camera) {
                if (bArr != null && bArr.length > 0) {
                    Uri uri = null;
                    if (!(CameraActivity.this.getIntent() == null || (uri = CameraActivity.this.getIntent().getData()) != null || CameraActivity.this.getIntent().getExtras() == null)) {
                        uri = (Uri) CameraActivity.this.getIntent().getExtras().get("output");
                    }
                    boolean z = true;
                    if (uri == null) {
                        uri = Uri.fromFile(CameraActivity.createCameraShotFileObjectWith(CameraActivity.this, CameraActivity.sNextImageFileNumber));
                        z = false;
                    }
                    boolean z2 = false;
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(uri.getPath());
                        fileOutputStream.write(bArr);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        z2 = true;
                        if (!z) {
                            CameraActivity.access$008();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (z2) {
                        Intent intent = new Intent();
                        intent.setData(uri);
                        CameraActivity.this.setResult(-1, intent);
                        CameraActivity.this.finish();
                    }
                }
            }
        });
        FrameLayout frameLayout2 = new FrameLayout(this);
        frameLayout2.setBackgroundColor(Color.argb(192, 0, 0, 0));
        frameLayout.addView(frameLayout2, new FrameLayout.LayoutParams(-1, -2, 80));
        ImageButton imageButton = new ImageButton(this);
        imageButton.setPadding(2, 2, 2, 2);
        imageButton.setBackgroundColor(0);
        imageButton.setImageResource(resourceServices.getDrawableResourceId("ic_menu_camera"));
        frameLayout2.addView(imageButton, new FrameLayout.LayoutParams(-2, -2, 17));
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CameraActivity.this.fCameraView.takePicture();
            }
        });
        if (CameraServices.getCameraCount() > 1) {
            ImageButton imageButton2 = new ImageButton(this);
            imageButton2.setPadding(2, 2, 2, 2);
            imageButton2.setBackgroundColor(0);
            imageButton2.setImageResource(resourceServices.getDrawableResourceId("ic_menu_refresh"));
            frameLayout2.addView(imageButton2, new FrameLayout.LayoutParams(-2, -2, 21));
            imageButton2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    CameraActivity.this.fCameraView.selectCameraByIndex((CameraActivity.this.fCameraView.getSelectedCameraIndex() + 1) % CameraServices.getCameraCount());
                }
            });
        }
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        switch (i) {
            case 23:
            case 27:
                this.fCameraView.takePicture();
                break;
        }
        return super.onKeyUp(i, keyEvent);
    }
}
