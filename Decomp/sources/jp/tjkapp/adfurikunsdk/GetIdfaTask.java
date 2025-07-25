package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class GetIdfaTask extends AsyncTaskLoader<String> {
    private Context mContext;
    private OnLoadListener mLoadListener;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    public interface OnLoadListener {
        void onLoadFinish(String str);
    }

    public GetIdfaTask(OnLoadListener onLoadListener, Context context) {
        super(context);
        this.mLoadListener = onLoadListener;
        this.mContext = context;
    }

    @Override // android.support.v4.content.Loader
    public void deliverResult(String str) {
        super.deliverResult((GetIdfaTask) str);
        try {
            if (this.mLoadListener != null) {
                this.mLoadListener.onLoadFinish(str);
            }
        } catch (Exception e) {
        }
    }

    @Override // android.support.v4.content.AsyncTaskLoader
    public String loadInBackground() {
        return FileUtil.getIDFA2(this.mContext);
    }
}
