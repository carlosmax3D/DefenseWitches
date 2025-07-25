package jp.co.voyagegroup.android.fluct.jar.sdk;

import android.content.Context;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import jp.co.voyagegroup.android.fluct.jar.db.FluctDbAccess;
import jp.co.voyagegroup.android.fluct.jar.db.FluctInterstitialTable;
import jp.co.voyagegroup.android.fluct.jar.setting.FluctSetting;
import jp.co.voyagegroup.android.fluct.jar.util.FluctUtils;
import jp.co.voyagegroup.android.fluct.jar.util.FluctXMLParser;
import jp.co.voyagegroup.android.fluct.jar.util.Log;

public class FluctConfig {
    static final /* synthetic */ boolean $assertionsDisabled = (!FluctConfig.class.desiredAssertionStatus());
    private static final String TAG = "FluctConfig";
    private static FluctConfig sInstance;
    private String mErrorMessage = null;
    private Map<String, ReadWriteLock> mMediaLocks;
    private Map<String, FluctSetting> mSettingsMap;

    private FluctConfig() {
        Log.d(TAG, "FluctConfig : ");
        this.mSettingsMap = new Hashtable();
        this.mMediaLocks = new HashMap();
    }

    private FluctSetting getFluctSetting(Context context, String str) {
        FluctSetting fluctSetting = null;
        ReadWriteLock lock = getLock(str);
        if (lock.writeLock().tryLock()) {
            fluctSetting = getNetConfig(context, str);
            FluctInterstitialTable fluctInterstitialTable = null;
            if (fluctSetting != null) {
                FluctDbAccess.saveConfig(context, fluctSetting);
                fluctInterstitialTable = fluctSetting.getFluctInterstitial();
            }
            if (fluctInterstitialTable != null) {
                FluctDbAccess.setInterstitial(context, fluctInterstitialTable);
            } else if (FluctDbAccess.checkInterstitialData(context, str)) {
                FluctDbAccess.deleteInterstitialData(context, str);
            }
            if (fluctSetting != null) {
                this.mSettingsMap.put(str, fluctSetting);
            }
            lock.writeLock().unlock();
        }
        return fluctSetting;
    }

    public static FluctConfig getInstance() {
        Log.d(TAG, "getInstance : ");
        if (sInstance == null) {
            sInstance = new FluctConfig();
        }
        return sInstance;
    }

    private ReadWriteLock getLock(String str) {
        Log.d(TAG, "getLock : ");
        if (this.mMediaLocks.containsKey(str)) {
            return this.mMediaLocks.get(str);
        }
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.mMediaLocks.put(str, reentrantReadWriteLock);
        return reentrantReadWriteLock;
    }

    public FluctSetting getConfigFromMap(String str) {
        Log.d(TAG, "getConfigFromMap : ");
        FluctSetting fluctSetting = this.mSettingsMap.get(str);
        if ($assertionsDisabled || fluctSetting != null) {
            return fluctSetting;
        }
        throw new AssertionError();
    }

    public FluctSetting getFromDB(Context context, String str) {
        Log.d(TAG, "getFromDB : ");
        FluctSetting config = FluctDbAccess.getConfig(context, str);
        if (config != null) {
            this.mSettingsMap.put(str, config);
        }
        return config;
    }

    public FluctSetting getFromDBWithResultLock(Context context, String str) {
        Log.d(TAG, "getFromDBWithResultLock : ");
        ReadWriteLock lock = getLock(str);
        lock.readLock().lock();
        FluctSetting config = FluctDbAccess.getConfig(context, str);
        if (config != null) {
            this.mSettingsMap.put(str, config);
        }
        lock.readLock().unlock();
        return config;
    }

    public FluctSetting getFromNet(Context context, String str) {
        Log.d(TAG, "getFromNet : ");
        return getFluctSetting(context, str);
    }

    public String getFromNetErrorMsg(Context context, String str) {
        Log.d(TAG, "getFromNetErrorMsg : ");
        getFluctSetting(context, str);
        return this.mErrorMessage;
    }

    public FluctSetting getNetConfig(Context context, String str) {
        Log.d(TAG, "getNetConfig : ");
        String configURL = FluctUtils.getConfigURL(str);
        Log.v(TAG, "getNetConfig : requestConfigUrl is " + configURL);
        FluctSetting parserConfig = FluctXMLParser.parserConfig(context, FluctXMLParser.executeUrl(context, configURL), str);
        if (parserConfig == null) {
            return parserConfig;
        }
        Log.v(TAG, "getNetConfig : setting is " + parserConfig.toString());
        if (parserConfig.getErrorMessages() == null) {
            parserConfig.setMediaId(str);
            this.mErrorMessage = null;
            return parserConfig;
        }
        this.mErrorMessage = parserConfig.getErrorMessages();
        Log.e(TAG, "getNetConfig : ErrorMessages is " + parserConfig.getErrorMessages());
        return null;
    }
}
