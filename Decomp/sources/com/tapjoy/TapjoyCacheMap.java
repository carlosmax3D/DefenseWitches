package com.tapjoy;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jp.stargarage.g2metrics.BuildConfig;

public class TapjoyCacheMap extends ConcurrentHashMap<String, TapjoyCachedAssetData> {
    private static final String TAG = "TapjoyCacheMap";
    private static final long serialVersionUID = 914426585683985523L;
    private int _cacheLimit = -1;
    private Context _context;

    public TapjoyCacheMap(Context context, int i) {
        this._context = context;
        this._cacheLimit = i;
    }

    private String findOldestAsset() {
        long j = -1;
        String str = BuildConfig.FLAVOR;
        for (Map.Entry entry : entrySet()) {
            long timestampInSeconds = ((TapjoyCachedAssetData) entry.getValue()).getTimestampInSeconds();
            if (j == 0 || timestampInSeconds < j) {
                j = timestampInSeconds;
                str = (String) entry.getKey();
            }
        }
        return str;
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public TapjoyCachedAssetData put(String str, TapjoyCachedAssetData tapjoyCachedAssetData) {
        if (tapjoyCachedAssetData == null || tapjoyCachedAssetData.getTimeOfDeathInSeconds() <= System.currentTimeMillis() / 1000) {
            return null;
        }
        if (size() == this._cacheLimit) {
            remove((Object) findOldestAsset());
        }
        SharedPreferences.Editor edit = this._context.getSharedPreferences(TapjoyConstants.PREF_TAPJOY_CACHE, 0).edit();
        edit.putString(tapjoyCachedAssetData.getLocalFilePath(), tapjoyCachedAssetData.toRawJSONString());
        edit.commit();
        return (TapjoyCachedAssetData) super.put(str, tapjoyCachedAssetData);
    }

    public TapjoyCachedAssetData remove(Object obj) {
        if (!containsKey(obj)) {
            return null;
        }
        SharedPreferences.Editor edit = this._context.getSharedPreferences(TapjoyConstants.PREF_TAPJOY_CACHE, 0).edit();
        edit.remove(((TapjoyCachedAssetData) get(obj)).getLocalFilePath());
        edit.commit();
        String localFilePath = ((TapjoyCachedAssetData) get(obj)).getLocalFilePath();
        if (localFilePath != null && localFilePath.length() > 0) {
            TapjoyUtil.deleteFileOrDirectory(new File(localFilePath));
        }
        return (TapjoyCachedAssetData) super.remove(obj);
    }

    public TapjoyCachedAssetData replace(String str, TapjoyCachedAssetData tapjoyCachedAssetData) {
        throw new UnsupportedOperationException();
    }

    public boolean replace(String str, TapjoyCachedAssetData tapjoyCachedAssetData, TapjoyCachedAssetData tapjoyCachedAssetData2) {
        throw new UnsupportedOperationException();
    }
}
