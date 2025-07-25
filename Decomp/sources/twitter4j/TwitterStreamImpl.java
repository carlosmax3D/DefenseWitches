package twitter4j;

import com.tapjoy.TJAdUnitConstants;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import twitter4j.auth.Authorization;
import twitter4j.conf.Configuration;

class TwitterStreamImpl extends TwitterBaseImpl implements TwitterStream {
    private static final int HTTP_ERROR_INITIAL_WAIT = 10000;
    private static final int HTTP_ERROR_WAIT_CAP = 240000;
    private static final int NO_WAIT = 0;
    private static final int TCP_ERROR_INITIAL_WAIT = 250;
    private static final int TCP_ERROR_WAIT_CAP = 16000;
    private static int count = 0;
    private static transient Dispatcher dispatcher = null;
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger(TwitterStreamImpl.class);
    private static int numberOfHandlers = 0;
    private static final long serialVersionUID = 5621090317737561048L;
    private TwitterStreamConsumer handler = null;
    private final HttpClient http;
    /* access modifiers changed from: private */
    public final List<ConnectionLifeCycleListener> lifeCycleListeners = new ArrayList(0);
    private final String stallWarningsGetParam;
    private final HttpParameter stallWarningsParam;
    private final ArrayList<StreamListener> streamListeners = new ArrayList<>(0);

    enum Mode {
        user,
        status,
        site
    }

    abstract class TwitterStreamConsumer extends Thread {
        private final String NAME = ("Twitter Stream consumer-" + TwitterStreamImpl.access$104());
        private volatile boolean closed = false;
        private final Mode mode;
        private RawStreamListener[] rawStreamListeners;
        private StatusStreamBase stream = null;
        private StreamListener[] streamListeners;

        TwitterStreamConsumer(Mode mode2) {
            this.mode = mode2;
            updateListeners();
            setName(this.NAME + "[initializing]");
        }

        private void setStatus(String str) {
            String str2 = this.NAME + str;
            setName(str2);
            TwitterStreamImpl.logger.debug(str2);
        }

        public synchronized void close() {
            setStatus("[Disposing thread]");
            this.closed = true;
            if (this.stream != null) {
                try {
                    this.stream.close();
                } catch (IOException e) {
                } catch (Exception e2) {
                    e2.printStackTrace();
                    TwitterStreamImpl.logger.warn(e2.getMessage());
                }
            }
            return;
        }

        /* access modifiers changed from: package-private */
        public abstract StatusStream getStream() throws TwitterException;

        /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
            jadx.core.utils.exceptions.JadxOverflowException: 
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
            	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
            */
        public void run() {
            /*
                r14 = this;
                r13 = 200(0xc8, float:2.8E-43)
                r12 = 1
                r8 = 0
                r6 = 0
                r0 = 0
            L_0x0006:
                boolean r7 = r14.closed
                if (r7 != 0) goto L_0x01fd
                boolean r7 = r14.closed     // Catch:{ TwitterException -> 0x0059 }
                if (r7 != 0) goto L_0x0006
                twitter4j.StatusStreamBase r7 = r14.stream     // Catch:{ TwitterException -> 0x0059 }
                if (r7 != 0) goto L_0x0006
                twitter4j.Logger r7 = twitter4j.TwitterStreamImpl.logger     // Catch:{ TwitterException -> 0x0059 }
                java.lang.String r9 = "Establishing connection."
                r7.info(r9)     // Catch:{ TwitterException -> 0x0059 }
                java.lang.String r7 = "[Establishing connection]"
                r14.setStatus(r7)     // Catch:{ TwitterException -> 0x0059 }
                twitter4j.StatusStream r7 = r14.getStream()     // Catch:{ TwitterException -> 0x0059 }
                twitter4j.StatusStreamBase r7 = (twitter4j.StatusStreamBase) r7     // Catch:{ TwitterException -> 0x0059 }
                r14.stream = r7     // Catch:{ TwitterException -> 0x0059 }
                r0 = 1
                twitter4j.Logger r7 = twitter4j.TwitterStreamImpl.logger     // Catch:{ TwitterException -> 0x0059 }
                java.lang.String r9 = "Connection established."
                r7.info(r9)     // Catch:{ TwitterException -> 0x0059 }
                twitter4j.TwitterStreamImpl r7 = twitter4j.TwitterStreamImpl.this     // Catch:{ TwitterException -> 0x0059 }
                java.util.List r7 = r7.lifeCycleListeners     // Catch:{ TwitterException -> 0x0059 }
                java.util.Iterator r7 = r7.iterator()     // Catch:{ TwitterException -> 0x0059 }
            L_0x003c:
                boolean r9 = r7.hasNext()     // Catch:{ TwitterException -> 0x0059 }
                if (r9 == 0) goto L_0x008f
                java.lang.Object r3 = r7.next()     // Catch:{ TwitterException -> 0x0059 }
                twitter4j.ConnectionLifeCycleListener r3 = (twitter4j.ConnectionLifeCycleListener) r3     // Catch:{ TwitterException -> 0x0059 }
                r3.onConnect()     // Catch:{ Exception -> 0x004c }
                goto L_0x003c
            L_0x004c:
                r1 = move-exception
                twitter4j.Logger r9 = twitter4j.TwitterStreamImpl.logger     // Catch:{ TwitterException -> 0x0059 }
                java.lang.String r10 = r1.getMessage()     // Catch:{ TwitterException -> 0x0059 }
                r9.warn(r10)     // Catch:{ TwitterException -> 0x0059 }
                goto L_0x003c
            L_0x0059:
                r5 = move-exception
                twitter4j.Logger r7 = twitter4j.TwitterStreamImpl.logger
                java.lang.String r9 = r5.getMessage()
                r7.info(r9)
                boolean r7 = r14.closed
                if (r7 != 0) goto L_0x0006
                if (r6 != 0) goto L_0x014e
                int r7 = r5.getStatusCode()
                r9 = 403(0x193, float:5.65E-43)
                if (r7 != r9) goto L_0x00fa
                twitter4j.Logger r7 = twitter4j.TwitterStreamImpl.logger
                java.lang.String r9 = "This account is not in required role. "
                java.lang.String r10 = r5.getMessage()
                r7.warn(r9, r10)
                r14.closed = r12
                twitter4j.StreamListener[] r7 = r14.streamListeners
                int r9 = r7.length
            L_0x0085:
                if (r8 >= r9) goto L_0x01fd
                r4 = r7[r8]
                r4.onException(r5)
                int r8 = r8 + 1
                goto L_0x0085
            L_0x008f:
                r6 = 0
                twitter4j.Logger r7 = twitter4j.TwitterStreamImpl.logger     // Catch:{ TwitterException -> 0x0059 }
                java.lang.String r9 = "Receiving status stream."
                r7.info(r9)     // Catch:{ TwitterException -> 0x0059 }
                java.lang.String r7 = "[Receiving stream]"
                r14.setStatus(r7)     // Catch:{ TwitterException -> 0x0059 }
            L_0x009e:
                boolean r7 = r14.closed     // Catch:{ TwitterException -> 0x0059 }
                if (r7 != 0) goto L_0x0006
                twitter4j.StatusStreamBase r7 = r14.stream     // Catch:{ IllegalStateException -> 0x00ac, TwitterException -> 0x00ba, Exception -> 0x00d0 }
                twitter4j.StreamListener[] r9 = r14.streamListeners     // Catch:{ IllegalStateException -> 0x00ac, TwitterException -> 0x00ba, Exception -> 0x00d0 }
                twitter4j.RawStreamListener[] r10 = r14.rawStreamListeners     // Catch:{ IllegalStateException -> 0x00ac, TwitterException -> 0x00ba, Exception -> 0x00d0 }
                r7.next(r9, r10)     // Catch:{ IllegalStateException -> 0x00ac, TwitterException -> 0x00ba, Exception -> 0x00d0 }
                goto L_0x009e
            L_0x00ac:
                r2 = move-exception
                twitter4j.Logger r7 = twitter4j.TwitterStreamImpl.logger     // Catch:{ TwitterException -> 0x0059 }
                java.lang.String r9 = r2.getMessage()     // Catch:{ TwitterException -> 0x0059 }
                r7.warn(r9)     // Catch:{ TwitterException -> 0x0059 }
                goto L_0x0006
            L_0x00ba:
                r1 = move-exception
                twitter4j.Logger r7 = twitter4j.TwitterStreamImpl.logger     // Catch:{ TwitterException -> 0x0059 }
                java.lang.String r9 = r1.getMessage()     // Catch:{ TwitterException -> 0x0059 }
                r7.info(r9)     // Catch:{ TwitterException -> 0x0059 }
                twitter4j.StatusStreamBase r7 = r14.stream     // Catch:{ TwitterException -> 0x0059 }
                twitter4j.StreamListener[] r9 = r14.streamListeners     // Catch:{ TwitterException -> 0x0059 }
                twitter4j.RawStreamListener[] r10 = r14.rawStreamListeners     // Catch:{ TwitterException -> 0x0059 }
                r7.onException(r1, r9, r10)     // Catch:{ TwitterException -> 0x0059 }
                throw r1     // Catch:{ TwitterException -> 0x0059 }
            L_0x00d0:
                r1 = move-exception
                boolean r7 = r1 instanceof java.lang.NullPointerException     // Catch:{ TwitterException -> 0x0059 }
                if (r7 != 0) goto L_0x009e
                java.lang.String r7 = r1.getMessage()     // Catch:{ TwitterException -> 0x0059 }
                java.lang.String r9 = "Inflater has been closed"
                boolean r7 = r7.equals(r9)     // Catch:{ TwitterException -> 0x0059 }
                if (r7 != 0) goto L_0x009e
                twitter4j.Logger r7 = twitter4j.TwitterStreamImpl.logger     // Catch:{ TwitterException -> 0x0059 }
                java.lang.String r9 = r1.getMessage()     // Catch:{ TwitterException -> 0x0059 }
                r7.info(r9)     // Catch:{ TwitterException -> 0x0059 }
                twitter4j.StatusStreamBase r7 = r14.stream     // Catch:{ TwitterException -> 0x0059 }
                twitter4j.StreamListener[] r9 = r14.streamListeners     // Catch:{ TwitterException -> 0x0059 }
                twitter4j.RawStreamListener[] r10 = r14.rawStreamListeners     // Catch:{ TwitterException -> 0x0059 }
                r7.onException(r1, r9, r10)     // Catch:{ TwitterException -> 0x0059 }
                r7 = 1
                r14.closed = r7     // Catch:{ TwitterException -> 0x0059 }
                goto L_0x0006
            L_0x00fa:
                int r7 = r5.getStatusCode()
                r9 = 406(0x196, float:5.69E-43)
                if (r7 != r9) goto L_0x011e
                twitter4j.Logger r7 = twitter4j.TwitterStreamImpl.logger
                java.lang.String r9 = "Parameter not accepted with the role. "
                java.lang.String r10 = r5.getMessage()
                r7.warn(r9, r10)
                r14.closed = r12
                twitter4j.StreamListener[] r7 = r14.streamListeners
                int r9 = r7.length
            L_0x0114:
                if (r8 >= r9) goto L_0x01fd
                r4 = r7[r8]
                r4.onException(r5)
                int r8 = r8 + 1
                goto L_0x0114
            L_0x011e:
                r0 = 0
                twitter4j.TwitterStreamImpl r7 = twitter4j.TwitterStreamImpl.this
                java.util.List r7 = r7.lifeCycleListeners
                java.util.Iterator r7 = r7.iterator()
            L_0x0129:
                boolean r9 = r7.hasNext()
                if (r9 == 0) goto L_0x0146
                java.lang.Object r3 = r7.next()
                twitter4j.ConnectionLifeCycleListener r3 = (twitter4j.ConnectionLifeCycleListener) r3
                r3.onDisconnect()     // Catch:{ Exception -> 0x0139 }
                goto L_0x0129
            L_0x0139:
                r1 = move-exception
                twitter4j.Logger r9 = twitter4j.TwitterStreamImpl.logger
                java.lang.String r10 = r1.getMessage()
                r9.warn(r10)
                goto L_0x0129
            L_0x0146:
                int r7 = r5.getStatusCode()
                if (r7 <= r13) goto L_0x0183
                r6 = 10000(0x2710, float:1.4013E-41)
            L_0x014e:
                int r7 = r5.getStatusCode()
                if (r7 <= r13) goto L_0x015a
                r7 = 10000(0x2710, float:1.4013E-41)
                if (r6 >= r7) goto L_0x015a
                r6 = 10000(0x2710, float:1.4013E-41)
            L_0x015a:
                if (r0 == 0) goto L_0x0188
                twitter4j.TwitterStreamImpl r7 = twitter4j.TwitterStreamImpl.this
                java.util.List r7 = r7.lifeCycleListeners
                java.util.Iterator r7 = r7.iterator()
            L_0x0166:
                boolean r9 = r7.hasNext()
                if (r9 == 0) goto L_0x0188
                java.lang.Object r3 = r7.next()
                twitter4j.ConnectionLifeCycleListener r3 = (twitter4j.ConnectionLifeCycleListener) r3
                r3.onDisconnect()     // Catch:{ Exception -> 0x0176 }
                goto L_0x0166
            L_0x0176:
                r1 = move-exception
                twitter4j.Logger r9 = twitter4j.TwitterStreamImpl.logger
                java.lang.String r10 = r1.getMessage()
                r9.warn(r10)
                goto L_0x0166
            L_0x0183:
                if (r6 != 0) goto L_0x014e
                r6 = 250(0xfa, float:3.5E-43)
                goto L_0x014e
            L_0x0188:
                twitter4j.StreamListener[] r9 = r14.streamListeners
                int r10 = r9.length
                r7 = r8
            L_0x018c:
                if (r7 >= r10) goto L_0x0196
                r4 = r9[r7]
                r4.onException(r5)
                int r7 = r7 + 1
                goto L_0x018c
            L_0x0196:
                boolean r7 = r14.closed
                if (r7 != 0) goto L_0x01e9
                twitter4j.Logger r7 = twitter4j.TwitterStreamImpl.logger
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                java.lang.String r10 = "Waiting for "
                java.lang.StringBuilder r9 = r9.append(r10)
                java.lang.StringBuilder r9 = r9.append(r6)
                java.lang.String r10 = " milliseconds"
                java.lang.StringBuilder r9 = r9.append(r10)
                java.lang.String r9 = r9.toString()
                r7.info(r9)
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                java.lang.String r9 = "[Waiting for "
                java.lang.StringBuilder r7 = r7.append(r9)
                java.lang.StringBuilder r7 = r7.append(r6)
                java.lang.String r9 = " milliseconds]"
                java.lang.StringBuilder r7 = r7.append(r9)
                java.lang.String r7 = r7.toString()
                r14.setStatus(r7)
                long r10 = (long) r6
                java.lang.Thread.sleep(r10)     // Catch:{ InterruptedException -> 0x02dd }
            L_0x01da:
                int r9 = r6 * 2
                int r7 = r5.getStatusCode()
                if (r7 <= r13) goto L_0x01fa
                r7 = 240000(0x3a980, float:3.36312E-40)
            L_0x01e5:
                int r6 = java.lang.Math.min(r9, r7)
            L_0x01e9:
                r7 = 0
                r14.stream = r7
                twitter4j.Logger r7 = twitter4j.TwitterStreamImpl.logger
                java.lang.String r9 = r5.getMessage()
                r7.debug(r9)
                r0 = 0
                goto L_0x0006
            L_0x01fa:
                r7 = 16000(0x3e80, float:2.2421E-41)
                goto L_0x01e5
            L_0x01fd:
                twitter4j.StatusStreamBase r7 = r14.stream
                if (r7 == 0) goto L_0x02b6
                if (r0 == 0) goto L_0x02b6
                twitter4j.StatusStreamBase r7 = r14.stream     // Catch:{ IOException -> 0x022f, Exception -> 0x0257 }
                r7.close()     // Catch:{ IOException -> 0x022f, Exception -> 0x0257 }
                twitter4j.TwitterStreamImpl r7 = twitter4j.TwitterStreamImpl.this
                java.util.List r7 = r7.lifeCycleListeners
                java.util.Iterator r7 = r7.iterator()
            L_0x0212:
                boolean r8 = r7.hasNext()
                if (r8 == 0) goto L_0x02b6
                java.lang.Object r3 = r7.next()
                twitter4j.ConnectionLifeCycleListener r3 = (twitter4j.ConnectionLifeCycleListener) r3
                r3.onDisconnect()     // Catch:{ Exception -> 0x0222 }
                goto L_0x0212
            L_0x0222:
                r1 = move-exception
                twitter4j.Logger r8 = twitter4j.TwitterStreamImpl.logger
                java.lang.String r9 = r1.getMessage()
                r8.warn(r9)
                goto L_0x0212
            L_0x022f:
                r7 = move-exception
                twitter4j.TwitterStreamImpl r7 = twitter4j.TwitterStreamImpl.this
                java.util.List r7 = r7.lifeCycleListeners
                java.util.Iterator r7 = r7.iterator()
            L_0x023a:
                boolean r8 = r7.hasNext()
                if (r8 == 0) goto L_0x02b6
                java.lang.Object r3 = r7.next()
                twitter4j.ConnectionLifeCycleListener r3 = (twitter4j.ConnectionLifeCycleListener) r3
                r3.onDisconnect()     // Catch:{ Exception -> 0x024a }
                goto L_0x023a
            L_0x024a:
                r1 = move-exception
                twitter4j.Logger r8 = twitter4j.TwitterStreamImpl.logger
                java.lang.String r9 = r1.getMessage()
                r8.warn(r9)
                goto L_0x023a
            L_0x0257:
                r1 = move-exception
                r1.printStackTrace()     // Catch:{ all -> 0x028d }
                twitter4j.Logger r7 = twitter4j.TwitterStreamImpl.logger     // Catch:{ all -> 0x028d }
                java.lang.String r8 = r1.getMessage()     // Catch:{ all -> 0x028d }
                r7.warn(r8)     // Catch:{ all -> 0x028d }
                twitter4j.TwitterStreamImpl r7 = twitter4j.TwitterStreamImpl.this
                java.util.List r7 = r7.lifeCycleListeners
                java.util.Iterator r7 = r7.iterator()
            L_0x0270:
                boolean r8 = r7.hasNext()
                if (r8 == 0) goto L_0x02b6
                java.lang.Object r3 = r7.next()
                twitter4j.ConnectionLifeCycleListener r3 = (twitter4j.ConnectionLifeCycleListener) r3
                r3.onDisconnect()     // Catch:{ Exception -> 0x0280 }
                goto L_0x0270
            L_0x0280:
                r1 = move-exception
                twitter4j.Logger r8 = twitter4j.TwitterStreamImpl.logger
                java.lang.String r9 = r1.getMessage()
                r8.warn(r9)
                goto L_0x0270
            L_0x028d:
                r7 = move-exception
                twitter4j.TwitterStreamImpl r8 = twitter4j.TwitterStreamImpl.this
                java.util.List r8 = r8.lifeCycleListeners
                java.util.Iterator r8 = r8.iterator()
            L_0x0298:
                boolean r9 = r8.hasNext()
                if (r9 == 0) goto L_0x02b5
                java.lang.Object r3 = r8.next()
                twitter4j.ConnectionLifeCycleListener r3 = (twitter4j.ConnectionLifeCycleListener) r3
                r3.onDisconnect()     // Catch:{ Exception -> 0x02a8 }
                goto L_0x0298
            L_0x02a8:
                r1 = move-exception
                twitter4j.Logger r9 = twitter4j.TwitterStreamImpl.logger
                java.lang.String r10 = r1.getMessage()
                r9.warn(r10)
                goto L_0x0298
            L_0x02b5:
                throw r7
            L_0x02b6:
                twitter4j.TwitterStreamImpl r7 = twitter4j.TwitterStreamImpl.this
                java.util.List r7 = r7.lifeCycleListeners
                java.util.Iterator r7 = r7.iterator()
            L_0x02c0:
                boolean r8 = r7.hasNext()
                if (r8 == 0) goto L_0x02e0
                java.lang.Object r3 = r7.next()
                twitter4j.ConnectionLifeCycleListener r3 = (twitter4j.ConnectionLifeCycleListener) r3
                r3.onCleanUp()     // Catch:{ Exception -> 0x02d0 }
                goto L_0x02c0
            L_0x02d0:
                r1 = move-exception
                twitter4j.Logger r8 = twitter4j.TwitterStreamImpl.logger
                java.lang.String r9 = r1.getMessage()
                r8.warn(r9)
                goto L_0x02c0
            L_0x02dd:
                r7 = move-exception
                goto L_0x01da
            L_0x02e0:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: twitter4j.TwitterStreamImpl.TwitterStreamConsumer.run():void");
        }

        /* access modifiers changed from: package-private */
        public void updateListeners() {
            switch (this.mode) {
                case site:
                    this.streamListeners = TwitterStreamImpl.this.getSiteStreamsListeners();
                    break;
                default:
                    this.streamListeners = TwitterStreamImpl.this.getStatusListeners();
                    break;
            }
            this.rawStreamListeners = TwitterStreamImpl.this.getRawStreamListeners();
        }
    }

    TwitterStreamImpl(Configuration configuration, Authorization authorization) {
        super(configuration, authorization);
        this.http = HttpClientFactory.getInstance(new StreamingReadTimeoutConfiguration(configuration));
        this.http.addDefaultRequestHeader("Connection", TJAdUnitConstants.String.CLOSE);
        this.stallWarningsGetParam = "stall_warnings=" + (configuration.isStallWarningsEnabled() ? "true" : "false");
        this.stallWarningsParam = new HttpParameter("stall_warnings", configuration.isStallWarningsEnabled());
    }

    static /* synthetic */ int access$104() {
        int i = count + 1;
        count = i;
        return i;
    }

    private void ensureSiteStreamsListenerIsSet() {
        if (getSiteStreamsListeners().length == 0 && getRawStreamListeners().length == 0) {
            throw new IllegalStateException("SiteStreamsListener is not set.");
        }
    }

    private void ensureStatusStreamListenerIsSet() {
        if (this.streamListeners.size() == 0) {
            throw new IllegalStateException("StatusListener is not set.");
        }
    }

    private StatusStream getCountStream(String str, int i) throws TwitterException {
        ensureAuthorizationEnabled();
        try {
            return new StatusStreamImpl(getDispatcher(), this.http.post(this.conf.getStreamBaseURL() + str, new HttpParameter[]{new HttpParameter("count", String.valueOf(i)), this.stallWarningsParam}, this.auth, (HttpResponseListener) null), this.conf);
        } catch (IOException e) {
            throw new TwitterException((Exception) e);
        }
    }

    /* access modifiers changed from: private */
    public Dispatcher getDispatcher() {
        if (dispatcher == null) {
            synchronized (TwitterStreamImpl.class) {
                if (dispatcher == null) {
                    dispatcher = new DispatcherFactory(this.conf).getInstance();
                }
            }
        }
        return dispatcher;
    }

    /* access modifiers changed from: private */
    public RawStreamListener[] getRawStreamListeners() {
        ArrayList arrayList = new ArrayList();
        Iterator<StreamListener> it = this.streamListeners.iterator();
        while (it.hasNext()) {
            StreamListener next = it.next();
            if (next instanceof RawStreamListener) {
                arrayList.add((RawStreamListener) next);
            }
        }
        return (RawStreamListener[]) arrayList.toArray(new RawStreamListener[arrayList.size()]);
    }

    /* access modifiers changed from: private */
    public SiteStreamsListener[] getSiteStreamsListeners() {
        ArrayList arrayList = new ArrayList();
        Iterator<StreamListener> it = this.streamListeners.iterator();
        while (it.hasNext()) {
            StreamListener next = it.next();
            if (next instanceof SiteStreamsListener) {
                arrayList.add((SiteStreamsListener) next);
            }
        }
        return (SiteStreamsListener[]) arrayList.toArray(new SiteStreamsListener[arrayList.size()]);
    }

    /* access modifiers changed from: private */
    public StatusListener[] getStatusListeners() {
        ArrayList arrayList = new ArrayList();
        Iterator<StreamListener> it = this.streamListeners.iterator();
        while (it.hasNext()) {
            StreamListener next = it.next();
            if (next instanceof StatusListener) {
                arrayList.add((StatusListener) next);
            }
        }
        return (StatusListener[]) arrayList.toArray(new StatusListener[arrayList.size()]);
    }

    private synchronized void startHandler(TwitterStreamConsumer twitterStreamConsumer) {
        cleanUp();
        this.handler = twitterStreamConsumer;
        this.handler.start();
        numberOfHandlers++;
    }

    private synchronized void updateListeners() {
        if (this.handler != null) {
            this.handler.updateListeners();
        }
    }

    public void addConnectionLifeCycleListener(ConnectionLifeCycleListener connectionLifeCycleListener) {
        this.lifeCycleListeners.add(connectionLifeCycleListener);
    }

    public synchronized void addListener(StreamListener streamListener) {
        this.streamListeners.add(streamListener);
        updateListeners();
    }

    public synchronized void cleanUp() {
        if (this.handler != null) {
            this.handler.close();
            numberOfHandlers--;
        }
    }

    public synchronized void clearListeners() {
        this.streamListeners.clear();
        updateListeners();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        TwitterStreamImpl twitterStreamImpl = (TwitterStreamImpl) obj;
        if (this.handler == null ? twitterStreamImpl.handler != null : !this.handler.equals(twitterStreamImpl.handler)) {
            return false;
        }
        if (this.http == null ? twitterStreamImpl.http != null : !this.http.equals(twitterStreamImpl.http)) {
            return false;
        }
        if (this.lifeCycleListeners == null ? twitterStreamImpl.lifeCycleListeners != null : !this.lifeCycleListeners.equals(twitterStreamImpl.lifeCycleListeners)) {
            return false;
        }
        if (this.stallWarningsGetParam == null ? twitterStreamImpl.stallWarningsGetParam != null : !this.stallWarningsGetParam.equals(twitterStreamImpl.stallWarningsGetParam)) {
            return false;
        }
        if (this.stallWarningsParam == null ? twitterStreamImpl.stallWarningsParam != null : !this.stallWarningsParam.equals(twitterStreamImpl.stallWarningsParam)) {
            return false;
        }
        if (this.streamListeners != null) {
            if (this.streamListeners.equals(twitterStreamImpl.streamListeners)) {
                return true;
            }
        } else if (twitterStreamImpl.streamListeners == null) {
            return true;
        }
        return false;
    }

    public void filter(final FilterQuery filterQuery) {
        ensureAuthorizationEnabled();
        ensureStatusStreamListenerIsSet();
        startHandler(new TwitterStreamConsumer(Mode.status) {
            public StatusStream getStream() throws TwitterException {
                return TwitterStreamImpl.this.getFilterStream(filterQuery);
            }
        });
    }

    public void firehose(final int i) {
        ensureAuthorizationEnabled();
        ensureStatusStreamListenerIsSet();
        startHandler(new TwitterStreamConsumer(Mode.status) {
            public StatusStream getStream() throws TwitterException {
                return TwitterStreamImpl.this.getFirehoseStream(i);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public StatusStream getFilterStream(FilterQuery filterQuery) throws TwitterException {
        ensureAuthorizationEnabled();
        try {
            return new StatusStreamImpl(getDispatcher(), this.http.post(this.conf.getStreamBaseURL() + "statuses/filter.json", filterQuery.asHttpParameterArray(this.stallWarningsParam), this.auth, (HttpResponseListener) null), this.conf);
        } catch (IOException e) {
            throw new TwitterException((Exception) e);
        }
    }

    /* access modifiers changed from: package-private */
    public StatusStream getFirehoseStream(int i) throws TwitterException {
        ensureAuthorizationEnabled();
        return getCountStream("statuses/firehose.json", i);
    }

    /* access modifiers changed from: package-private */
    public StatusStream getLinksStream(int i) throws TwitterException {
        ensureAuthorizationEnabled();
        return getCountStream("statuses/links.json", i);
    }

    /* access modifiers changed from: package-private */
    public StatusStream getRetweetStream() throws TwitterException {
        ensureAuthorizationEnabled();
        try {
            return new StatusStreamImpl(getDispatcher(), this.http.post(this.conf.getStreamBaseURL() + "statuses/retweet.json", new HttpParameter[]{this.stallWarningsParam}, this.auth, (HttpResponseListener) null), this.conf);
        } catch (IOException e) {
            throw new TwitterException((Exception) e);
        }
    }

    /* access modifiers changed from: package-private */
    public StatusStream getSampleStream() throws TwitterException {
        ensureAuthorizationEnabled();
        try {
            return new StatusStreamImpl(getDispatcher(), this.http.get(this.conf.getStreamBaseURL() + "statuses/sample.json?" + this.stallWarningsGetParam, (HttpParameter[]) null, this.auth, (HttpResponseListener) null), this.conf);
        } catch (IOException e) {
            throw new TwitterException((Exception) e);
        }
    }

    /* access modifiers changed from: package-private */
    public InputStream getSiteStream(boolean z, long[] jArr) throws TwitterException {
        ensureOAuthEnabled();
        HttpClient httpClient = this.http;
        String str = this.conf.getSiteStreamBaseURL() + "site.json";
        HttpParameter[] httpParameterArr = new HttpParameter[3];
        httpParameterArr[0] = new HttpParameter("with", z ? "followings" : "user");
        httpParameterArr[1] = new HttpParameter("follow", StringUtil.join(jArr));
        httpParameterArr[2] = this.stallWarningsParam;
        return httpClient.post(str, httpParameterArr, this.auth, (HttpResponseListener) null).asStream();
    }

    /* access modifiers changed from: package-private */
    public UserStream getUserStream(String[] strArr) throws TwitterException {
        ensureAuthorizationEnabled();
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.stallWarningsParam);
            if (this.conf.isUserStreamRepliesAllEnabled()) {
                arrayList.add(new HttpParameter("replies", "all"));
            }
            if (!this.conf.isUserStreamWithFollowingsEnabled()) {
                arrayList.add(new HttpParameter("with", "user"));
            }
            if (strArr != null) {
                arrayList.add(new HttpParameter("track", StringUtil.join(strArr)));
            }
            return new UserStreamImpl(getDispatcher(), this.http.post(this.conf.getUserStreamBaseURL() + "user.json", (HttpParameter[]) arrayList.toArray(new HttpParameter[arrayList.size()]), this.auth, (HttpResponseListener) null), this.conf);
        } catch (IOException e) {
            throw new TwitterException((Exception) e);
        }
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((super.hashCode() * 31) + (this.http != null ? this.http.hashCode() : 0)) * 31) + (this.lifeCycleListeners != null ? this.lifeCycleListeners.hashCode() : 0)) * 31) + (this.handler != null ? this.handler.hashCode() : 0)) * 31) + (this.stallWarningsGetParam != null ? this.stallWarningsGetParam.hashCode() : 0)) * 31) + (this.stallWarningsParam != null ? this.stallWarningsParam.hashCode() : 0)) * 31;
        if (this.streamListeners != null) {
            i = this.streamListeners.hashCode();
        }
        return hashCode + i;
    }

    public void links(final int i) {
        ensureAuthorizationEnabled();
        ensureStatusStreamListenerIsSet();
        startHandler(new TwitterStreamConsumer(Mode.status) {
            public StatusStream getStream() throws TwitterException {
                return TwitterStreamImpl.this.getLinksStream(i);
            }
        });
    }

    public synchronized void removeListener(StreamListener streamListener) {
        this.streamListeners.remove(streamListener);
        updateListeners();
    }

    public synchronized void replaceListener(StreamListener streamListener, StreamListener streamListener2) {
        this.streamListeners.remove(streamListener);
        this.streamListeners.add(streamListener2);
        updateListeners();
    }

    public void retweet() {
        ensureAuthorizationEnabled();
        ensureStatusStreamListenerIsSet();
        startHandler(new TwitterStreamConsumer(Mode.status) {
            public StatusStream getStream() throws TwitterException {
                return TwitterStreamImpl.this.getRetweetStream();
            }
        });
    }

    public void sample() {
        ensureAuthorizationEnabled();
        ensureStatusStreamListenerIsSet();
        startHandler(new TwitterStreamConsumer(Mode.status) {
            public StatusStream getStream() throws TwitterException {
                return TwitterStreamImpl.this.getSampleStream();
            }
        });
    }

    public synchronized void shutdown() {
        cleanUp();
        synchronized (TwitterStreamImpl.class) {
            if (numberOfHandlers == 0 && dispatcher != null) {
                dispatcher.shutdown();
                dispatcher = null;
            }
        }
    }

    public StreamController site(boolean z, long[] jArr) {
        ensureOAuthEnabled();
        ensureSiteStreamsListenerIsSet();
        final StreamController streamController = new StreamController(this.http, this.auth);
        final boolean z2 = z;
        final long[] jArr2 = jArr;
        startHandler(new TwitterStreamConsumer(Mode.site) {
            public StatusStream getStream() throws TwitterException {
                try {
                    return new SiteStreamsImpl(TwitterStreamImpl.this.getDispatcher(), TwitterStreamImpl.this.getSiteStream(z2, jArr2), TwitterStreamImpl.this.conf, streamController);
                } catch (IOException e) {
                    throw new TwitterException((Exception) e);
                }
            }
        });
        return streamController;
    }

    public String toString() {
        return "TwitterStreamImpl{http=" + this.http + ", lifeCycleListeners=" + this.lifeCycleListeners + ", handler=" + this.handler + ", stallWarningsGetParam='" + this.stallWarningsGetParam + '\'' + ", stallWarningsParam=" + this.stallWarningsParam + ", streamListeners=" + this.streamListeners + '}';
    }

    public void user() {
        user((String[]) null);
    }

    public void user(final String[] strArr) {
        ensureAuthorizationEnabled();
        ensureStatusStreamListenerIsSet();
        startHandler(new TwitterStreamConsumer(Mode.user) {
            public StatusStream getStream() throws TwitterException {
                return TwitterStreamImpl.this.getUserStream(strArr);
            }
        });
    }
}
