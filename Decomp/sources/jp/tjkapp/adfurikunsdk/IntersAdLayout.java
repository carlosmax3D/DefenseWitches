package jp.tjkapp.adfurikunsdk;

import android.content.Context;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class IntersAdLayout extends LayoutBase {
    public IntersAdLayout(Context context) {
        super(context, false);
    }

    public IntersAdLayout(Context context, boolean z) {
        super(context, z);
    }

    @Override // jp.tjkapp.adfurikunsdk.LayoutBase
    protected void initialize(Context context, int i) {
        this.defaultAdType = 1;
        super.initialize(context, i);
    }
}
