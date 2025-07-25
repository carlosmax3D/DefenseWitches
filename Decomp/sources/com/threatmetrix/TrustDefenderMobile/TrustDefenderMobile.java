package com.threatmetrix.TrustDefenderMobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.http.AndroidHttpClient;
import android.util.Log;
import android.util.TimingLogger;
import com.tapjoy.TapjoyConstants;
import com.threatmetrix.TrustDefenderMobile.HttpRunner;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class TrustDefenderMobile implements TrustDefenderMobileStandard {
    private static final boolean PROFILE_MODE = false;
    /* access modifiers changed from: private */
    public static final String TAG = TrustDefenderMobile.class.getSimpleName();
    public static final int THM_OPTION_ALL_ASYNC = 3327;
    @Deprecated
    public static final int THM_OPTION_ALL_SYNC = 3326;
    @Deprecated
    public static final int THM_OPTION_LEAN_ASYNC = 3135;
    @Deprecated
    public static final int THM_OPTION_LEAN_SYNC = 3134;
    @Deprecated
    public static final int THM_OPTION_MOST_ASYNC = 3199;
    @Deprecated
    public static final int THM_OPTION_MOST_SYNC = 3198;
    public static final int THM_OPTION_WEBVIEW = 38;
    private static final Executor m_pool = Executors.newFixedThreadPool(6);
    public static final String version = "2.5-16";
    private final TDLocationManager m_TDLocationManager = new TDLocationManager();
    private volatile boolean m_active = false;
    /* access modifiers changed from: private */
    public final BrowserInfoGatherer m_browser_info = new BrowserInfoGatherer();
    private final ReentrantLock m_callback_lock = new ReentrantLock();
    private volatile AtomicBoolean m_cancel = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public Context m_context = null;
    private boolean m_generate_session_id = false;
    AndroidHttpClient m_httpClient = null;
    /* access modifiers changed from: private */
    public CountDownLatch m_init_latch = new CountDownLatch(1);
    private AtomicBoolean m_inited = new AtomicBoolean(false);
    private boolean m_manual_session_id = false;
    private int m_options = 0;
    private ProfileNotifyBase m_profileNotify = null;
    private Thread m_profile_thread = null;
    private final ReadWriteLock m_request_lock = new ReentrantReadWriteLock();
    private final Lock m_request_lock_read = this.m_request_lock.readLock();
    private final Lock m_request_lock_write = this.m_request_lock.writeLock();
    private final ArrayList<NetworkThread> m_requests = new ArrayList<>();
    private int m_savedOptions = 0;
    final TrustDefenderMobileCore m_td = new TrustDefenderMobileCore();
    /* access modifiers changed from: private */
    public int m_timeout_ms = 10000;
    /* access modifiers changed from: private */
    public TimingLogger m_timings = null;

    public enum THMStatusCode {
        THM_NotYet,
        THM_OK,
        THM_Connection_Error,
        THM_HostNotFound_Error,
        THM_NetworkTimeout_Error,
        THM_Internal_Error,
        THM_HostVerification_Error,
        THM_Interrupted_Error,
        THM_InvalidOrgID,
        THM_ConfigurationError,
        THM_PartialProfile;
        
        private final String desc;
        private final String label;

        static THMStatusCode exportedType(InternalStatusCode internalStatusCode) {
            return valueOf(internalStatusCode.name());
        }

        /* access modifiers changed from: package-private */
        public final InternalStatusCode coreType() {
            return InternalStatusCode.valueOf(name());
        }

        public final String getDesc() {
            return this.desc;
        }

        public final String toString() {
            return this.label;
        }
    }

    private NetworkThread addNetworkRequest(Runnable runnable) {
        if (runnable == null) {
            return null;
        }
        if (this.m_cancel.get()) {
            return null;
        }
        try {
            NetworkThread networkThread = new NetworkThread(runnable);
            if (runnable instanceof HttpRunner) {
                this.m_request_lock_write.lock();
                this.m_requests.add(networkThread);
                this.m_request_lock_write.unlock();
            }
            networkThread.start();
            return networkThread;
        } catch (RuntimeException e) {
            String str = TAG;
            return null;
        } catch (Throwable th) {
            this.m_request_lock_write.unlock();
            throw th;
        }
    }

    private void clearRequests() throws InterruptedException {
        try {
            this.m_request_lock_write.lockInterruptibly();
            this.m_requests.clear();
        } finally {
            this.m_request_lock_write.unlock();
        }
    }

    private HttpConfigRunner getConfigFromServer() {
        HttpConfigRunner httpConfigRunner = new HttpConfigRunner(this.m_httpClient, this.m_td.getConfigurationPath(), this.m_td.getConfigurationParams(), this.m_td.getConfigurationHeaders(), this);
        if (addNetworkRequest(httpConfigRunner) != null) {
            return httpConfigRunner;
        }
        return null;
    }

    private void init(Context context, boolean z) {
        if (!this.m_inited.compareAndSet(false, true)) {
            String str = TAG;
        } else {
            new Thread(new CompleteProfile(this, (CountDownLatch) null, context, z) {
                final /* synthetic */ Context val$context;
                final /* synthetic */ boolean val$initWebView;

                {
                    this.val$context = r5;
                    this.val$initWebView = r6;
                }

                public final void run() {
                    try {
                        String unused = TrustDefenderMobile.TAG;
                        TrustDefenderMobile.this.m_browser_info.initJSExecutor(this.val$context, this.val$initWebView, 0);
                        if (TrustDefenderMobile.this.m_timings != null) {
                            TrustDefenderMobile.this.m_timings.addSplit("init - initJSExecutor");
                        }
                        String browserStringFromJS = TrustDefenderMobile.this.m_browser_info.getBrowserStringFromJS();
                        if (TrustDefenderMobile.this.m_timings != null) {
                            TrustDefenderMobile.this.m_timings.addSplit("getUserAgent");
                        }
                        if (TrustDefenderMobile.this.m_httpClient == null) {
                            TrustDefenderMobile.this.m_httpClient = AndroidHttpClient.newInstance(browserStringFromJS, TrustDefenderMobile.this.m_context);
                            HttpParams params = TrustDefenderMobile.this.m_httpClient.getParams();
                            HttpConnectionParams.setConnectionTimeout(params, TrustDefenderMobile.this.m_timeout_ms);
                            HttpConnectionParams.setSoTimeout(params, TrustDefenderMobile.this.m_timeout_ms);
                            URLConnection.setSSLSocketFactory(this.val$context, TrustDefenderMobile.this.m_httpClient, TrustDefenderMobile.this.m_timeout_ms);
                            HttpConnectionParams.setTcpNoDelay(params, true);
                            HttpConnectionParams.setStaleCheckingEnabled(params, false);
                        }
                        if (TrustDefenderMobile.this.m_timings != null) {
                            TrustDefenderMobile.this.m_timings.addSplit("create AndroidHttpClient");
                        }
                        NativeGatherer.INSTANCE.isAvailable();
                        StringUtils.MD5((String) null);
                    } finally {
                        if (TrustDefenderMobile.this.m_init_latch != null) {
                            TrustDefenderMobile.this.m_init_latch.countDown();
                        }
                    }
                }
            }).start();
        }
    }

    private void interruptThread(Thread thread) {
        m_pool.execute(new Runnable(thread) {
            final Thread t;

            {
                this.t = r2;
            }

            public final void run() {
                String unused = TrustDefenderMobile.TAG;
                new StringBuilder("sending interrupt to TID: ").append(this.t.getId());
                this.t.interrupt();
            }
        });
    }

    private void interruptThreads(boolean z) {
        if (!z) {
            try {
                this.m_request_lock_read.lock();
            } catch (Throwable th) {
                if (!z) {
                    this.m_request_lock_read.unlock();
                }
                throw th;
            }
        }
        Iterator<NetworkThread> it = this.m_requests.iterator();
        while (it.hasNext()) {
            interruptThread(it.next());
        }
        if (!z) {
            this.m_request_lock_read.unlock();
        }
    }

    private void reset() {
        this.m_td.reset();
        this.m_TDLocationManager.unregister();
    }

    private boolean waitForInit() {
        if (!this.m_inited.get()) {
            return true;
        }
        String str = TAG;
        try {
            boolean await = this.m_init_latch.await((long) this.m_timeout_ms, TimeUnit.MILLISECONDS);
            if (await) {
                return await;
            }
            Log.e(TAG, "Timed out waiting for init to complete");
            return await;
        } catch (InterruptedException e) {
            Log.e(TAG, "Waiting for init to complete interrupted", e);
            return false;
        }
    }

    private THMStatusCode waitForNetworkRequests(boolean z) throws InterruptedException {
        THMStatusCode tHMStatusCode = THMStatusCode.THM_NotYet;
        try {
            this.m_request_lock_read.lockInterruptibly();
            Iterator<NetworkThread> it = this.m_requests.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                NetworkThread next = it.next();
                if (this.m_cancel.get() || Thread.currentThread().isInterrupted()) {
                    tHMStatusCode = THMStatusCode.THM_Interrupted_Error;
                } else {
                    next.join((long) this.m_timeout_ms);
                    if (next.getState() != Thread.State.TERMINATED) {
                        tHMStatusCode = THMStatusCode.THM_Connection_Error;
                        if (!z) {
                            interruptThreads(true);
                            break;
                        }
                        interruptThread(next);
                    } else {
                        HttpRunner httpRunner = next.getHttpRunner();
                        if (httpRunner == null) {
                            continue;
                        } else {
                            THMStatusCode statusCode = next.getHttpRunner().getStatusCode();
                            if (statusCode == THMStatusCode.THM_OK && httpRunner.getHttpStatusCode() != 200) {
                                String str = TAG;
                                new StringBuilder("Connection returned http status code:").append(httpRunner.getHttpStatusCode());
                                tHMStatusCode = THMStatusCode.THM_Connection_Error;
                                if (!z) {
                                    interruptThreads(true);
                                    break;
                                }
                            } else if (statusCode != THMStatusCode.THM_OK) {
                                String str2 = TAG;
                                new StringBuilder("Connection returned status :").append(httpRunner.getStatusCode().getDesc());
                                tHMStatusCode = statusCode;
                                if (!z) {
                                    interruptThreads(true);
                                    break;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            if (tHMStatusCode == THMStatusCode.THM_NotYet) {
                tHMStatusCode = THMStatusCode.THM_Connection_Error;
            }
            interruptThreads(true);
            String str3 = TAG;
        } catch (Throwable th) {
            if (this.m_timings != null) {
                this.m_timings.addSplit("wait for network threads");
            }
            this.m_request_lock_read.unlock();
            throw th;
        }
        if (this.m_timings != null) {
            this.m_timings.addSplit("wait for network threads");
        }
        this.m_request_lock_read.unlock();
        return tHMStatusCode == THMStatusCode.THM_NotYet ? THMStatusCode.THM_OK : tHMStatusCode;
    }

    public synchronized void cancel() {
        String str = TAG;
        if (!this.m_cancel.compareAndSet(false, true)) {
            Log.w(TAG, "Cancel already happened");
        } else {
            if (this.m_active) {
                String str2 = TAG;
                interruptThreads(false);
                if (this.m_profile_thread != null) {
                    String str3 = TAG;
                    new StringBuilder("sending interrupt to profile thread TID: ").append(this.m_profile_thread.getId());
                    this.m_profile_thread.interrupt();
                }
                String str4 = TAG;
                try {
                    this.m_request_lock_read.lock();
                    Iterator<NetworkThread> it = this.m_requests.iterator();
                    while (it.hasNext()) {
                        NetworkThread next = it.next();
                        String str5 = TAG;
                        next.join();
                    }
                    this.m_request_lock_read.unlock();
                    if (this.m_profile_thread != null && this.m_profile_thread.isAlive()) {
                        try {
                            String str6 = TAG;
                            this.m_profile_thread.join();
                        } catch (InterruptedException e) {
                        }
                    }
                    String str7 = TAG;
                } catch (InterruptedException e2) {
                    String str8 = TAG;
                } catch (Throwable th) {
                    this.m_request_lock_read.unlock();
                    throw th;
                }
            }
            try {
                this.m_request_lock_write.lock();
                this.m_requests.clear();
            } finally {
                this.m_request_lock_write.unlock();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void completeProfileRequest() {
        try {
            String str = TAG;
            new StringBuilder("continuing profile request ").append(this.m_inited.get() ? "inited already" : " needs init");
            if (this.m_timings != null) {
                this.m_timings.addSplit("after startup and thread split");
            }
            if (this.m_cancel.get() || Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            if (!this.m_inited.get()) {
                String str2 = TAG;
                init(this.m_context, (this.m_options & 38) != 0);
            }
            if (!waitForInit()) {
                Log.e(TAG, "Timed out waiting for init thread, aborting");
                this.m_td.setStatus(THMStatusCode.THM_Internal_Error.coreType());
                if (this.m_cancel.get()) {
                    this.m_td.setStatus(THMStatusCode.THM_Interrupted_Error.coreType());
                    Thread.interrupted();
                }
                try {
                    this.m_callback_lock.lockInterruptibly();
                    if (this.m_profileNotify != null) {
                        if (this.m_profileNotify instanceof ProfileNotify) {
                            ((ProfileNotify) this.m_profileNotify).complete();
                        } else if (this.m_profileNotify instanceof ProfileNotifyV2) {
                            ((ProfileNotifyV2) this.m_profileNotify).complete(THMStatusCode.exportedType(this.m_td.getStatus()));
                        }
                    }
                    if (this.m_callback_lock.isHeldByCurrentThread()) {
                        this.m_callback_lock.unlock();
                    }
                } catch (InterruptedException e) {
                    Log.e(TAG, "profileNotify callback interrupted", e);
                    if (this.m_callback_lock.isHeldByCurrentThread()) {
                        this.m_callback_lock.unlock();
                    }
                } catch (Throwable th) {
                    if (this.m_callback_lock.isHeldByCurrentThread()) {
                        this.m_callback_lock.unlock();
                    }
                    throw th;
                }
                this.m_active = false;
                return;
            }
            this.m_browser_info.initJSExecutor(this.m_context, (this.m_options & 38) != 0, this.m_options);
            if (this.m_timings != null) {
                this.m_timings.addSplit("initJSExecutor");
            }
            if (this.m_cancel.get() || Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            HttpConfigRunner httpConfigRunner = new HttpConfigRunner(this.m_httpClient, this.m_td.getConfigurationPath(), this.m_td.getConfigurationParams(), this.m_td.getConfigurationHeaders(), this);
            HttpConfigRunner httpConfigRunner2 = addNetworkRequest(httpConfigRunner) != null ? httpConfigRunner : null;
            if (this.m_timings != null) {
                this.m_timings.addSplit("get Config");
            }
            if (httpConfigRunner2 == null) {
                Log.e(TAG, "Failed to connect to server, aborting");
                this.m_td.setStatus(THMStatusCode.THM_Internal_Error.coreType());
                if (this.m_cancel.get()) {
                    this.m_td.setStatus(THMStatusCode.THM_Interrupted_Error.coreType());
                    Thread.interrupted();
                }
                try {
                    this.m_callback_lock.lockInterruptibly();
                    if (this.m_profileNotify != null) {
                        if (this.m_profileNotify instanceof ProfileNotify) {
                            ((ProfileNotify) this.m_profileNotify).complete();
                        } else if (this.m_profileNotify instanceof ProfileNotifyV2) {
                            ((ProfileNotifyV2) this.m_profileNotify).complete(THMStatusCode.exportedType(this.m_td.getStatus()));
                        }
                    }
                    if (this.m_callback_lock.isHeldByCurrentThread()) {
                        this.m_callback_lock.unlock();
                    }
                } catch (InterruptedException e2) {
                    Log.e(TAG, "profileNotify callback interrupted", e2);
                    if (this.m_callback_lock.isHeldByCurrentThread()) {
                        this.m_callback_lock.unlock();
                    }
                } catch (Throwable th2) {
                    if (this.m_callback_lock.isHeldByCurrentThread()) {
                        this.m_callback_lock.unlock();
                    }
                    throw th2;
                }
                this.m_active = false;
            } else if (this.m_cancel.get() || Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            } else {
                boolean shouldCallBrowserInfoHelper = this.m_browser_info.shouldCallBrowserInfoHelper();
                if (shouldCallBrowserInfoHelper) {
                    this.m_browser_info.getBrowserInfoHelper();
                    if (this.m_timings != null) {
                        this.m_timings.addSplit("get browser info");
                    }
                }
                this.m_td.gatherInfo();
                if (this.m_cancel.get() || Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }
                if (shouldCallBrowserInfoHelper) {
                    this.m_browser_info.waitForBrowserInfoHelper();
                    this.m_td.setBrowserInfo(this.m_browser_info);
                }
                if (this.m_timings != null) {
                    this.m_timings.addSplit("wait for browser info");
                }
                if ((this.m_options & 1024) != 0) {
                    String str3 = this.m_td.getOrgID() + AdNetworkKey.DEFAULT_AD + StringUtils.SHA1(this.m_td.getSessionID()) + "-mob";
                    if (str3.length() >= 64) {
                        Log.w(TAG, "combined session id and org id too long for host name fragment");
                    } else {
                        String str4 = TAG;
                        String str5 = "d";
                        if (this.m_td.getFPServer().equals("qa2-h.online-metrix.net")) {
                            str5 = "q";
                        }
                        addNetworkRequest(new DNSLookup(str3 + "." + str5 + ".aa.online-metrix.net"));
                    }
                    if (this.m_timings != null) {
                        this.m_timings.addSplit("Started DNS request");
                    }
                }
                THMStatusCode waitForNetworkRequests = waitForNetworkRequests(false);
                if (this.m_timings != null) {
                    this.m_timings.addSplit("wait for config network request");
                }
                clearRequests();
                if (waitForNetworkRequests != THMStatusCode.THM_OK) {
                    Log.e(TAG, "Failed to retrieve config, aborting: " + waitForNetworkRequests.toString());
                    this.m_td.setStatus(waitForNetworkRequests.coreType());
                    if (this.m_cancel.get()) {
                        this.m_td.setStatus(THMStatusCode.THM_Interrupted_Error.coreType());
                        Thread.interrupted();
                    }
                    try {
                        this.m_callback_lock.lockInterruptibly();
                        if (this.m_profileNotify != null) {
                            if (this.m_profileNotify instanceof ProfileNotify) {
                                ((ProfileNotify) this.m_profileNotify).complete();
                            } else if (this.m_profileNotify instanceof ProfileNotifyV2) {
                                ((ProfileNotifyV2) this.m_profileNotify).complete(THMStatusCode.exportedType(this.m_td.getStatus()));
                            }
                        }
                        if (this.m_callback_lock.isHeldByCurrentThread()) {
                            this.m_callback_lock.unlock();
                        }
                    } catch (InterruptedException e3) {
                        Log.e(TAG, "profileNotify callback interrupted", e3);
                        if (this.m_callback_lock.isHeldByCurrentThread()) {
                            this.m_callback_lock.unlock();
                        }
                    } catch (Throwable th3) {
                        if (this.m_callback_lock.isHeldByCurrentThread()) {
                            this.m_callback_lock.unlock();
                        }
                        throw th3;
                    }
                    this.m_active = false;
                    return;
                }
                this.m_td.setConfig(httpConfigRunner2.m_config);
                if (this.m_td.getConfig().options != this.m_savedOptions) {
                    String str6 = TAG;
                    new StringBuilder("dynamic options (").append(this.m_td.getConfig().options).append(") != saved: ").append(this.m_savedOptions);
                    SharedPreferences.Editor edit = this.m_context.getSharedPreferences(this.m_context.getPackageName() + "TDM", 0).edit();
                    edit.putInt("options", this.m_td.getConfig().options);
                    edit.apply();
                    if (this.m_timings != null) {
                        this.m_timings.addSplit("Processed stored options");
                    }
                }
                this.m_td.setLocation(this.m_TDLocationManager.getLocation());
                addNetworkRequest(new HttpRunner(this.m_httpClient, HttpRunner.HttpRunnerType.POST_CONSUME, "https://" + this.m_td.getFPServer() + "/fp/clear.png", this.m_td.getRiskBodyParameterMap(), this.m_td.getRiskDataHeaders(), this));
                if ((this.m_options & 64) != 0) {
                    addNetworkRequest(new PutXML(this.m_td.getFPServer(), this.m_td.getOrgID(), this.m_td.getSessionID(), this.m_td.getConfig().w, this.m_timeout_ms));
                }
                if (this.m_timings != null) {
                    this.m_timings.addSplit("build network threads");
                }
                THMStatusCode waitForNetworkRequests2 = waitForNetworkRequests(true);
                this.m_td.setStatus(waitForNetworkRequests2.coreType());
                if (waitForNetworkRequests2 != THMStatusCode.THM_OK) {
                    Log.w(TAG, "Received " + waitForNetworkRequests2.getDesc() + " error, profiling will be incomplete");
                    this.m_td.setStatus(THMStatusCode.THM_PartialProfile.coreType());
                }
                clearRequests();
                String str7 = TAG;
                if (this.m_timings != null) {
                    this.m_timings.dumpToLog();
                }
                if (this.m_cancel.get()) {
                    this.m_td.setStatus(THMStatusCode.THM_Interrupted_Error.coreType());
                    Thread.interrupted();
                }
                try {
                    this.m_callback_lock.lockInterruptibly();
                    if (this.m_profileNotify != null) {
                        if (this.m_profileNotify instanceof ProfileNotify) {
                            ((ProfileNotify) this.m_profileNotify).complete();
                        } else if (this.m_profileNotify instanceof ProfileNotifyV2) {
                            ((ProfileNotifyV2) this.m_profileNotify).complete(THMStatusCode.exportedType(this.m_td.getStatus()));
                        }
                    }
                    if (this.m_callback_lock.isHeldByCurrentThread()) {
                        this.m_callback_lock.unlock();
                    }
                } catch (InterruptedException e4) {
                    Log.e(TAG, "profileNotify callback interrupted", e4);
                    if (this.m_callback_lock.isHeldByCurrentThread()) {
                        this.m_callback_lock.unlock();
                    }
                } catch (Throwable th4) {
                    if (this.m_callback_lock.isHeldByCurrentThread()) {
                        this.m_callback_lock.unlock();
                    }
                    throw th4;
                }
                this.m_active = false;
            }
        } catch (InterruptedException e5) {
            String str8 = TAG;
            this.m_td.setStatus(THMStatusCode.THM_Internal_Error.coreType());
            if (this.m_cancel.get()) {
                this.m_td.setStatus(THMStatusCode.THM_Interrupted_Error.coreType());
                Thread.interrupted();
            }
            try {
                this.m_callback_lock.lockInterruptibly();
                if (this.m_profileNotify != null) {
                    if (this.m_profileNotify instanceof ProfileNotify) {
                        ((ProfileNotify) this.m_profileNotify).complete();
                    } else if (this.m_profileNotify instanceof ProfileNotifyV2) {
                        ((ProfileNotifyV2) this.m_profileNotify).complete(THMStatusCode.exportedType(this.m_td.getStatus()));
                    }
                }
                if (this.m_callback_lock.isHeldByCurrentThread()) {
                    this.m_callback_lock.unlock();
                }
            } catch (InterruptedException e6) {
                Log.e(TAG, "profileNotify callback interrupted", e6);
                if (this.m_callback_lock.isHeldByCurrentThread()) {
                    this.m_callback_lock.unlock();
                }
            } catch (Throwable th5) {
                if (this.m_callback_lock.isHeldByCurrentThread()) {
                    this.m_callback_lock.unlock();
                }
                throw th5;
            }
            this.m_active = false;
        } catch (Exception e7) {
            this.m_td.setStatus(THMStatusCode.THM_Internal_Error.coreType());
            String str9 = TAG;
            if (this.m_cancel.get()) {
                this.m_td.setStatus(THMStatusCode.THM_Interrupted_Error.coreType());
                Thread.interrupted();
            }
            try {
                this.m_callback_lock.lockInterruptibly();
                if (this.m_profileNotify != null) {
                    if (this.m_profileNotify instanceof ProfileNotify) {
                        ((ProfileNotify) this.m_profileNotify).complete();
                    } else if (this.m_profileNotify instanceof ProfileNotifyV2) {
                        ((ProfileNotifyV2) this.m_profileNotify).complete(THMStatusCode.exportedType(this.m_td.getStatus()));
                    }
                }
                if (this.m_callback_lock.isHeldByCurrentThread()) {
                    this.m_callback_lock.unlock();
                }
            } catch (InterruptedException e8) {
                Log.e(TAG, "profileNotify callback interrupted", e8);
                if (this.m_callback_lock.isHeldByCurrentThread()) {
                    this.m_callback_lock.unlock();
                }
            } catch (Throwable th6) {
                if (this.m_callback_lock.isHeldByCurrentThread()) {
                    this.m_callback_lock.unlock();
                }
                throw th6;
            }
            this.m_active = false;
        } catch (Throwable th7) {
            Throwable th8 = th7;
            if (this.m_cancel.get()) {
                this.m_td.setStatus(THMStatusCode.THM_Interrupted_Error.coreType());
                Thread.interrupted();
            }
            try {
                this.m_callback_lock.lockInterruptibly();
                if (this.m_profileNotify != null) {
                    if (this.m_profileNotify instanceof ProfileNotify) {
                        ((ProfileNotify) this.m_profileNotify).complete();
                    } else if (this.m_profileNotify instanceof ProfileNotifyV2) {
                        ((ProfileNotifyV2) this.m_profileNotify).complete(THMStatusCode.exportedType(this.m_td.getStatus()));
                    }
                }
                if (this.m_callback_lock.isHeldByCurrentThread()) {
                    this.m_callback_lock.unlock();
                }
            } catch (InterruptedException e9) {
                Log.e(TAG, "profileNotify callback interrupted", e9);
                if (this.m_callback_lock.isHeldByCurrentThread()) {
                    this.m_callback_lock.unlock();
                }
            } catch (Throwable th9) {
                if (this.m_callback_lock.isHeldByCurrentThread()) {
                    this.m_callback_lock.unlock();
                }
                throw th9;
            }
            this.m_active = false;
            throw th8;
        }
    }

    public THMStatusCode doProfileRequest(Context context, String str) {
        return doProfileRequest(context, str, (String) null, (String) null, (int) THM_OPTION_ALL_ASYNC);
    }

    public THMStatusCode doProfileRequest(Context context, String str, String str2) {
        return doProfileRequest(context, str, str2, (String) null, (int) THM_OPTION_ALL_ASYNC);
    }

    public THMStatusCode doProfileRequest(Context context, String str, String str2, int i) {
        return doProfileRequest(context, str, str2, (String) null, i);
    }

    public THMStatusCode doProfileRequest(Context context, String str, String str2, String str3) {
        return doProfileRequest(context, str, str2, str3, (int) THM_OPTION_ALL_ASYNC);
    }

    public THMStatusCode doProfileRequest(Context context, String str, String str2, String str3, int i) {
        if (context == null) {
            return THMStatusCode.THM_Internal_Error;
        }
        try {
            this.m_request_lock_write.lockInterruptibly();
            String str4 = TAG;
            new StringBuilder("starting profile request using - 2.5-16 with options ").append(i);
            if (this.m_active) {
                return THMStatusCode.THM_NotYet;
            }
            if (!this.m_td.setProfileOptions(i)) {
                THMStatusCode tHMStatusCode = THMStatusCode.THM_Internal_Error;
                this.m_request_lock_write.unlock();
                return tHMStatusCode;
            }
            this.m_options = i;
            this.m_td.reset();
            this.m_TDLocationManager.unregister();
            this.m_td.setCancelObject(this.m_cancel);
            this.m_cancel.set(false);
            this.m_td.setStatus(THMStatusCode.THM_NotYet.coreType());
            if (this.m_requests.size() > 0) {
                String str5 = TAG;
                interruptThreads(true);
            }
            this.m_requests.clear();
            if (!this.m_td.setFPServer(str2)) {
                THMStatusCode tHMStatusCode2 = THMStatusCode.THM_ConfigurationError;
                this.m_request_lock_write.unlock();
                return tHMStatusCode2;
            } else if (!this.m_td.setOrgID(str)) {
                if (this.m_timings != null) {
                    this.m_timings.dumpToLog();
                }
                THMStatusCode tHMStatusCode3 = THMStatusCode.THM_InvalidOrgID;
                this.m_request_lock_write.unlock();
                return tHMStatusCode3;
            } else {
                this.m_active = true;
                if (this.m_generate_session_id || this.m_td.getSessionID() == null) {
                    this.m_td.setSessionID(StringUtils.new_session_id());
                }
                if (this.m_manual_session_id && this.m_generate_session_id) {
                    Log.w(TAG, "Previous profile used manually specified session ID, but generated session ID used this time. This is likely a bug, make sure setSessionID() is called before every profile");
                }
                this.m_generate_session_id = true;
                this.m_context = context.getApplicationContext();
                this.m_td.setContext(this.m_context);
                String packageName = this.m_context.getPackageName();
                this.m_td.setURLS(str3, packageName);
                String str6 = packageName + "TDM";
                this.m_savedOptions = 0;
                try {
                    this.m_savedOptions = this.m_context.getSharedPreferences(str6, 0).getInt("options", 0);
                } catch (ClassCastException e) {
                    String str7 = TAG;
                }
                if (this.m_cancel.get()) {
                    throw new InterruptedException();
                }
                String str8 = TAG;
                new StringBuilder("applying inverted saved options - ").append(this.m_savedOptions).append(" with options, resulting in  ").append(this.m_options);
                this.m_options = (this.m_options ^ (this.m_savedOptions & 38)) | (this.m_savedOptions & 768);
                if ((this.m_options & 1) == 0) {
                    completeProfileRequest();
                    THMStatusCode exportedType = THMStatusCode.exportedType(this.m_td.getStatus());
                    this.m_request_lock_write.unlock();
                    return exportedType;
                }
                this.m_profile_thread = new Thread(new CompleteProfile(this, (CountDownLatch) null));
                this.m_profile_thread.start();
                THMStatusCode tHMStatusCode4 = THMStatusCode.THM_OK;
                this.m_request_lock_write.unlock();
                return tHMStatusCode4;
            }
        } catch (InterruptedException e2) {
            if (this.m_profile_thread != null) {
                this.m_profile_thread.interrupt();
            }
            this.m_active = false;
            return THMStatusCode.THM_Interrupted_Error;
        } finally {
            this.m_request_lock_write.unlock();
        }
    }

    public THMStatusCode doProfileRequest(Context context, String str, String str2, String str3, boolean z) {
        return z ? doProfileRequest(context, str, str2, str3, 3289) : doProfileRequest(context, str, str2, str3, (int) THM_OPTION_ALL_ASYNC);
    }

    public String getApiKey() {
        return this.m_td.getApiKey();
    }

    public String getSessionID() {
        return this.m_td.getSessionID();
    }

    public THMStatusCode getStatus() {
        return THMStatusCode.exportedType(this.m_td.getStatus());
    }

    public int getTimeout() {
        return this.m_timeout_ms / 1000;
    }

    public void init(Context context) {
        init(context, true);
    }

    public void initWithoutWebView(Context context) {
        init(context, false);
    }

    public void pauseLocationServices(boolean z) {
        if (z) {
            this.m_TDLocationManager.pause();
        } else {
            this.m_TDLocationManager.resume();
        }
    }

    public boolean registerLocationServices(Context context) {
        return this.m_TDLocationManager.registerLocationServices(context, TapjoyConstants.PAID_APP_TIME, 3600000, 1);
    }

    public boolean registerLocationServices(Context context, long j, long j2, int i) {
        return this.m_TDLocationManager.registerLocationServices(context, j, j2, i);
    }

    public void setApiKey(String str) {
        this.m_td.setApiKey(str);
    }

    public void setCompletionNotifier(ProfileNotifyBase profileNotifyBase) throws InterruptedException {
        try {
            this.m_callback_lock.lockInterruptibly();
            this.m_profileNotify = profileNotifyBase;
        } finally {
            if (this.m_callback_lock.isHeldByCurrentThread()) {
                this.m_callback_lock.unlock();
            }
        }
    }

    public void setCustomAttributes(List<String> list) {
        this.m_td.setCustomAttributes(list);
    }

    public void setLocation(Location location) {
        this.m_td.setLocation(location);
    }

    public void setSessionID(String str) {
        if (this.m_td.getSessionID() != str) {
            this.m_td.setSessionID(str);
        }
        if (this.m_td.getSessionID() == null || this.m_td.getSessionID().isEmpty()) {
            this.m_manual_session_id = false;
            return;
        }
        this.m_generate_session_id = false;
        this.m_manual_session_id = true;
    }

    /* access modifiers changed from: package-private */
    public final void setStatus(THMStatusCode tHMStatusCode) {
        this.m_td.setStatus(tHMStatusCode.coreType());
    }

    public void setTimeout(int i) {
        this.m_timeout_ms = i * 1000;
    }

    public void tidyUp() {
        String str = TAG;
        cancel();
        this.m_TDLocationManager.unregister();
        waitForInit();
        if (this.m_httpClient != null) {
            if (this.m_httpClient.getConnectionManager() != null) {
                m_pool.execute(new Runnable(this.m_httpClient) {
                    final AndroidHttpClient t;

                    {
                        this.t = r2;
                    }

                    public final void run() {
                        if (this.t != null) {
                            try {
                                this.t.close();
                                this.t.getConnectionManager().shutdown();
                            } catch (RuntimeException e) {
                                Log.e(TrustDefenderMobile.TAG, "Swallowing", e);
                            }
                        }
                    }
                });
            }
            this.m_httpClient = null;
        }
        this.m_browser_info.waitForBrowserInfoHelper();
        SingletonWebView.clearWebView();
        this.m_inited.set(false);
        this.m_init_latch = new CountDownLatch(1);
    }
}
