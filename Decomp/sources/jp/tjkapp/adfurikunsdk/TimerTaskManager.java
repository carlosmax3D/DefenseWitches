package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.os.Handler;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import jp.stargarage.g2metrics.BuildConfig;
import jp.tjkapp.adfurikunsdk.FillerTask2;
import jp.tjkapp.adfurikunsdk.GetInfoTask2;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: /home/carlos/AnypointStudio/classes.dex */
class TimerTaskManager {
    private static final int GETINFO_ID_LENGTH = 24;
    private static final boolean IS_VISIBLE_DEBUG_LOG_TSUKUI = false;
    private String IDFA;
    private boolean initialRun;
    private Context mContext;
    private LogUtil mLog;
    private String mUserAgent;
    private long mOldTime = -1;
    private int maxRetryLimit = 5;
    private int getIDFAPhase = 1;
    private int getInfoPhase = 2;
    private int getFillerPhase = 3;
    private int totalPhaseSize = 3;
    private long initializeTime = new Date().getTime();
    private Timer mMainTimer = null;
    private Handler mHandler = new Handler();
    private String mAppID = BuildConfig.FLAVOR;
    private Hashtable<Integer, Boolean> successStatus = new Hashtable<>();
    private Hashtable<Integer, Boolean> loadingStatus = new Hashtable<>();
    private int retryCounter = 0;

    /* JADX WARN: Classes with same name are omitted:
      classes.dex
     */
    class MainTimerTask extends TimerTask {
        MainTimerTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            if (!TimerTaskManager.this.initialRun && TimerTaskManager.this.allSucceed()) {
                TimerTaskManager.this.mLog.detail(Constants.TAG_NAME, "全部成功したのでタイマー終了");
                TimerTaskManager.this.timerDestory();
                return;
            }
            if (TimerTaskManager.this.maxRetryLimit < TimerTaskManager.this.retryCounter) {
                TimerTaskManager.this.mLog.detail(Constants.TAG_NAME, "リトライ上限を超えたのでタイマー終了");
                TimerTaskManager.this.timerDestory();
                return;
            }
            if (!ApiAccessUtil.isConnected(TimerTaskManager.this.mContext)) {
                TimerTaskManager.access$508(TimerTaskManager.this);
                return;
            }
            if (TimerTaskManager.this.mAppID == null || TimerTaskManager.this.mAppID.length() <= 0) {
                TimerTaskManager.this.timerDestory();
                return;
            }
            switch (TimerTaskManager.this.initialRun ? 1 : TimerTaskManager.this.getUnSuccessIndex()) {
                case 1:
                    TimerTaskManager.this.getIDFA();
                case 2:
                    TimerTaskManager.this.getInfo();
                case 3:
                    TimerTaskManager.this.getFiller();
                    break;
            }
            TimerTaskManager.this.mLog.detail(Constants.TAG_NAME, "retry counter: " + String.valueOf(TimerTaskManager.this.retryCounter));
            TimerTaskManager.access$508(TimerTaskManager.this);
        }
    }

    public TimerTaskManager(Context context, LogUtil logUtil) {
        this.mUserAgent = BuildConfig.FLAVOR;
        this.IDFA = BuildConfig.FLAVOR;
        this.mContext = context;
        this.mLog = logUtil;
        this.mUserAgent = FileUtil.getUserAgent(this.mContext);
        this.IDFA = BuildConfig.FLAVOR;
    }

    static /* synthetic */ int access$508(TimerTaskManager timerTaskManager) {
        int i = timerTaskManager.retryCounter;
        timerTaskManager.retryCounter = i + 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean allSucceed() {
        return getUnSuccessIndex() == 9999;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getFiller_org() {
        if (this.loadingStatus.containsKey(Integer.valueOf(this.getFillerPhase)) && new Boolean(this.loadingStatus.get(Integer.valueOf(this.getFillerPhase)).booleanValue()).booleanValue()) {
            return;
        }
        if (this.mAppID == null || this.mAppID.equals(BuildConfig.FLAVOR)) {
            this.loadingStatus.put(Integer.valueOf(this.getFillerPhase), false);
            return;
        }
        if (this.mAppID.length() != GETINFO_ID_LENGTH) {
            this.loadingStatus.put(Integer.valueOf(this.getFillerPhase), false);
            return;
        }
        if (!shouldDlGetInfo() && FileUtil.isFillerCache(this.mContext, this.mAppID)) {
            this.successStatus.put(Integer.valueOf(this.getFillerPhase), true);
            return;
        }
        this.loadingStatus.put(Integer.valueOf(this.getFillerPhase), true);
        try {
            new FillerTask2(new FillerTask2.OnLoadListener() { // from class: jp.tjkapp.adfurikunsdk.TimerTaskManager.4
                @Override // jp.tjkapp.adfurikunsdk.FillerTask2.OnLoadListener
                public void onLoadFinish(Integer num) {
                    if (num.intValue() == 200) {
                        TimerTaskManager.this.successStatus.put(Integer.valueOf(TimerTaskManager.this.getFillerPhase), true);
                    } else {
                        TimerTaskManager.this.successStatus.put(Integer.valueOf(TimerTaskManager.this.getFillerPhase), false);
                    }
                    TimerTaskManager.this.loadingStatus.put(Integer.valueOf(TimerTaskManager.this.getFillerPhase), false);
                }
            }, this.mContext, this.mLog, this.mAppID, this.mUserAgent, this.IDFA).forceLoad();
        } catch (Error e) {
            this.loadingStatus.put(Integer.valueOf(this.getFillerPhase), false);
            this.successStatus.put(Integer.valueOf(this.getFillerPhase), false);
        } catch (Exception e2) {
            this.loadingStatus.put(Integer.valueOf(this.getFillerPhase), false);
            this.successStatus.put(Integer.valueOf(this.getFillerPhase), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getInfo_org() {
        if ((this.loadingStatus.containsKey(Integer.valueOf(this.getInfoPhase)) && new Boolean(this.loadingStatus.get(Integer.valueOf(this.getInfoPhase)).booleanValue()).booleanValue()) || this.mAppID == null || this.mAppID.equals(BuildConfig.FLAVOR) || this.mAppID.length() != GETINFO_ID_LENGTH) {
            return;
        }
        if (!shouldDlGetInfo()) {
            this.successStatus.put(Integer.valueOf(this.getInfoPhase), true);
            return;
        }
        this.loadingStatus.put(Integer.valueOf(this.getInfoPhase), true);
        try {
            new GetInfoTask2(new GetInfoTask2.OnLoadListener() { // from class: jp.tjkapp.adfurikunsdk.TimerTaskManager.2
                @Override // jp.tjkapp.adfurikunsdk.GetInfoTask2.OnLoadListener
                public void onLoadFinish(Integer num) {
                    if (num.intValue() == 200 || num.intValue() == 400) {
                        TimerTaskManager.this.successStatus.put(Integer.valueOf(TimerTaskManager.this.getInfoPhase), true);
                    } else {
                        TimerTaskManager.this.successStatus.put(Integer.valueOf(TimerTaskManager.this.getInfoPhase), false);
                    }
                    TimerTaskManager.this.loadingStatus.put(Integer.valueOf(TimerTaskManager.this.getInfoPhase), false);
                }
            }, this.mContext, this.mAppID).forceLoad();
        } catch (Error e) {
            this.loadingStatus.put(Integer.valueOf(this.getInfoPhase), false);
            this.successStatus.put(Integer.valueOf(this.getInfoPhase), false);
        } catch (Exception e2) {
            this.loadingStatus.put(Integer.valueOf(this.getInfoPhase), false);
            this.successStatus.put(Integer.valueOf(this.getInfoPhase), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getUnSuccessIndex() {
        int i = 9999;
        if (this.successStatus.size() < this.totalPhaseSize) {
            this.mLog.detail(Constants.TAG_NAME, "初回ロード中");
            return 1;
        }
        this.initialRun = false;
        Enumeration<Integer> enumerationKeys = this.successStatus.keys();
        while (enumerationKeys.hasMoreElements()) {
            Integer numNextElement = enumerationKeys.nextElement();
            Boolean bool = this.successStatus.get(numNextElement);
            int iIntValue = new Integer(numNextElement.intValue()).intValue();
            boolean zBooleanValue = new Boolean(bool.booleanValue()).booleanValue();
            this.mLog.detail(Constants.TAG_NAME, "phase => " + String.valueOf(iIntValue) + " , status => " + String.valueOf(zBooleanValue));
            if (!zBooleanValue && iIntValue < i) {
                i = iIntValue;
            }
        }
        return i;
    }

    private boolean shouldDlGetInfo() {
        if (!FileUtil.isGetInfoCache(this.mContext, this.mAppID)) {
            return true;
        }
        long time = new Date().getTime();
        long adLastTime = FileUtil.getAdLastTime(this.mContext, this.mAppID);
        return adLastTime == -1 || ((long) Constants.GETINFO_RETRY_TIME) < time - adLastTime;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void timerDestory() {
        if (this.mMainTimer != null) {
            this.mMainTimer.cancel();
            this.mMainTimer = null;
        }
    }

    public void getFiller() {
        this.mHandler.post(new Runnable() { // from class: jp.tjkapp.adfurikunsdk.TimerTaskManager.3
            @Override // java.lang.Runnable
            public void run() {
                TimerTaskManager.this.getFiller_org();
            }
        });
    }

    public void getIDFA() {
        if (this.loadingStatus.containsKey(Integer.valueOf(this.getIDFAPhase)) && new Boolean(this.loadingStatus.get(Integer.valueOf(this.getIDFAPhase)).booleanValue()).booleanValue()) {
            return;
        }
        if (!this.IDFA.equals(BuildConfig.FLAVOR)) {
            this.loadingStatus.put(Integer.valueOf(this.getIDFAPhase), false);
            this.successStatus.put(Integer.valueOf(this.getIDFAPhase), true);
            return;
        }
        String id = null;
        boolean zIsLimitAdTrackingEnabled = false;
        this.loadingStatus.put(Integer.valueOf(this.getIDFAPhase), true);
        try {
            if (Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient") != null) {
                try {
                    AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.mContext);
                    if (advertisingIdInfo != null) {
                        id = advertisingIdInfo.getId();
                        zIsLimitAdTrackingEnabled = advertisingIdInfo.isLimitAdTrackingEnabled();
                    }
                } catch (IOException e) {
                    this.successStatus.put(Integer.valueOf(this.getIDFAPhase), false);
                } catch (Error e2) {
                    this.successStatus.put(Integer.valueOf(this.getIDFAPhase), false);
                } catch (IllegalStateException e3) {
                    this.successStatus.put(Integer.valueOf(this.getIDFAPhase), false);
                } catch (Exception e4) {
                    this.successStatus.put(Integer.valueOf(this.getIDFAPhase), false);
                }
            }
        } catch (Error e5) {
            this.successStatus.put(Integer.valueOf(this.getIDFAPhase), false);
        } catch (Exception e6) {
            this.successStatus.put(Integer.valueOf(this.getIDFAPhase), false);
        }
        if (id == null) {
            id = BuildConfig.FLAVOR;
            this.successStatus.put(Integer.valueOf(this.getIDFAPhase), false);
        } else {
            FileUtil.setIDFA(this.mContext, id, zIsLimitAdTrackingEnabled);
            this.successStatus.put(Integer.valueOf(this.getIDFAPhase), true);
        }
        this.IDFA = id;
        this.loadingStatus.put(Integer.valueOf(this.getIDFAPhase), false);
    }

    public void getInfo() {
        this.mHandler.post(new Runnable() { // from class: jp.tjkapp.adfurikunsdk.TimerTaskManager.1
            @Override // java.lang.Runnable
            public void run() {
                TimerTaskManager.this.getInfo_org();
            }
        });
    }

    protected void onPause() {
        this.mLog.detail(Constants.TAG_NAME, "Timer Task onPause");
    }

    protected void onResume() {
        this.mLog.detail(Constants.TAG_NAME, "Timer Task onResume");
        if (allSucceed() && !shouldDlGetInfo()) {
            this.mLog.detail(Constants.TAG_NAME, "全フェーズの取得処理が成功しているのでタイマー自体を動かさない");
            return;
        }
        if (shouldDlGetInfo()) {
            this.retryCounter = 0;
            this.successStatus = new Hashtable<>();
        }
        if (this.mMainTimer == null) {
            this.mMainTimer = new Timer();
            this.mMainTimer.schedule(new MainTimerTask(), 0L, 5000L);
        }
    }

    protected void setAppID(String str) {
        this.mAppID = String.copyValueOf(str.toCharArray());
        onResume();
    }

    public void stopTimer() {
    }
}
