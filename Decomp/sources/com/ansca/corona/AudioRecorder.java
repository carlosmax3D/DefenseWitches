package com.ansca.corona;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Process;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;

public class AudioRecorder {
    private static final int STATUS_ERROR = -1;
    private static final int STATUS_OK = 0;
    /* access modifiers changed from: private */
    public CoronaRuntime fCoronaRuntime;
    private Handler fHandler;
    /* access modifiers changed from: private */
    public int fId;
    /* access modifiers changed from: private */
    public boolean fIsNotifying = false;
    /* access modifiers changed from: private */
    public boolean fIsRunning;
    private AudioRecorderAbstract fRecorderImplementation;

    public static class AudioByteBufferHolder {
        ByteBuffer myBuffer;
        int myValidBytes = 0;

        AudioByteBufferHolder(int i) {
            this.myBuffer = ByteBuffer.allocateDirect(i);
        }
    }

    private abstract class AudioRecorderAbstract {
        private AudioRecorderAbstract() {
        }

        public AudioByteBufferHolder getCurrentBuffer() {
            return null;
        }

        public AudioByteBufferHolder getNextBuffer() {
            return null;
        }

        public void releaseCurrentBuffer() {
        }

        public abstract void startRecording();

        public abstract void stopRecording();
    }

    private class AudioRecorderThread extends Thread {
        private static final int ENCODING = 2;
        static final int MAX_BUFFERS = 5;
        private static final int SAMPLE_RATE = 8000;
        private AudioRecord myAudioRecordInstance;
        private int myBufferSize;
        private LinkedList<AudioByteBufferHolder> myBuffers = new LinkedList<>();
        private float myFrameSeconds = 0.1f;
        private LinkedList<AudioByteBufferHolder> myFreeBuffers = new LinkedList<>();

        AudioRecorderThread() {
        }

        /* access modifiers changed from: package-private */
        public AudioByteBufferHolder getNextBuffer() {
            synchronized (this.myBuffers) {
                if (this.myBuffers.isEmpty()) {
                    return null;
                }
                AudioByteBufferHolder remove = this.myBuffers.remove();
                return remove;
            }
        }

        /* access modifiers changed from: package-private */
        public void releaseBuffer(AudioByteBufferHolder audioByteBufferHolder) {
            synchronized (this.myFreeBuffers) {
                this.myFreeBuffers.add(audioByteBufferHolder);
                if (!AudioRecorder.this.fIsRunning) {
                    this.myFreeBuffers.clear();
                }
            }
        }

        public void run() {
            try {
                Process.setThreadPriority(-19);
                int minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioRecorder.this.fCoronaRuntime.getController().getAndroidVersionSpecific().audioChannelMono(), 2);
                int i = (int) (16000.0f * this.myFrameSeconds);
                if (i < minBufferSize) {
                    i = minBufferSize;
                }
                this.myBufferSize = i;
                this.myAudioRecordInstance = new AudioRecord(1, SAMPLE_RATE, AudioRecorder.this.fCoronaRuntime.getController().getAndroidVersionSpecific().audioChannelMono(), 2, this.myBufferSize);
                this.myFreeBuffers.add(new AudioByteBufferHolder(this.myBufferSize));
                this.myAudioRecordInstance.startRecording();
                while (AudioRecorder.this.fIsRunning) {
                    AudioByteBufferHolder audioByteBufferHolder = null;
                    synchronized (this) {
                        if (!this.myFreeBuffers.isEmpty()) {
                            audioByteBufferHolder = this.myFreeBuffers.remove();
                        } else if (this.myBuffers.size() < 5) {
                            audioByteBufferHolder = new AudioByteBufferHolder(this.myBufferSize);
                        }
                    }
                    if (audioByteBufferHolder != null) {
                        int read = this.myAudioRecordInstance.read(audioByteBufferHolder.myBuffer, this.myBufferSize);
                        synchronized (this) {
                            if (read > 0) {
                                audioByteBufferHolder.myValidBytes = read;
                                this.myBuffers.add(audioByteBufferHolder);
                                AudioRecorder.this.postStatus(read);
                            } else {
                                this.myFreeBuffers.add(audioByteBufferHolder);
                            }
                        }
                    }
                    sleep(15);
                }
                this.myAudioRecordInstance.stop();
                AudioRecorder.this.postStatus(0);
            } catch (Exception e) {
                AudioRecorder.this.postStatus(-1);
            }
        }

        /* access modifiers changed from: package-private */
        public void startRecording() {
            start();
        }

        /* access modifiers changed from: package-private */
        public void stopRecording() {
        }
    }

    private class MemoryRecorder extends AudioRecorderAbstract {
        AudioByteBufferHolder fCurrentBuffer;
        AudioRecorderThread fRecorderThread;

        private MemoryRecorder() {
            super();
        }

        public AudioByteBufferHolder getCurrentBuffer() {
            return this.fCurrentBuffer;
        }

        public AudioByteBufferHolder getNextBuffer() {
            if (this.fRecorderThread != null) {
                this.fCurrentBuffer = this.fRecorderThread.getNextBuffer();
            } else {
                this.fCurrentBuffer = null;
            }
            return this.fCurrentBuffer;
        }

        public synchronized void releaseCurrentBuffer() {
            if (this.fCurrentBuffer != null) {
                this.fRecorderThread.releaseBuffer(this.fCurrentBuffer);
                this.fCurrentBuffer = null;
            }
            boolean unused = AudioRecorder.this.fIsNotifying = false;
        }

        public void startRecording() {
            this.fRecorderThread = new AudioRecorderThread();
            this.fRecorderThread.startRecording();
        }

        public void stopRecording() {
            if (this.fRecorderThread != null) {
                this.fRecorderThread.stopRecording();
                this.fRecorderThread = null;
            }
        }
    }

    private class ThreeGPRecorder extends AudioRecorderAbstract {
        MediaRecorder fMediaRecorder;
        String fPath;

        public ThreeGPRecorder(String str) {
            super();
            this.fPath = str;
        }

        public void startRecording() {
            File parentFile = new File(this.fPath).getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            this.fMediaRecorder = new MediaRecorder();
            this.fMediaRecorder.setAudioSource(1);
            this.fMediaRecorder.setOutputFormat(1);
            this.fMediaRecorder.setAudioEncoder(1);
            this.fMediaRecorder.setOutputFile(this.fPath);
            this.fMediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
                public void onError(MediaRecorder mediaRecorder, int i, int i2) {
                    System.out.println("MediaRecorder error " + i + " " + i2);
                    ThreeGPRecorder.this.stopRecording();
                }
            });
            this.fMediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
                public void onInfo(MediaRecorder mediaRecorder, int i, int i2) {
                    System.out.println("MediaRecorder info " + i + " " + i2);
                }
            });
            try {
                this.fMediaRecorder.prepare();
                this.fMediaRecorder.start();
            } catch (Exception e) {
                AudioRecorder.this.postStatus(-1);
            }
        }

        public void stopRecording() {
            this.fMediaRecorder.stop();
            this.fMediaRecorder.release();
            this.fMediaRecorder = null;
        }
    }

    private class WavRecorder extends AudioRecorderAbstract {
        private static final int RECORDER_AUDIO_ENCODING = 2;
        private static final int RECORDER_BPP = 16;
        private static final int RECORDER_CHANNELS = 16;
        private static final int RECORDER_SAMPLERATE = 44100;
        int fBufferSize = 0;
        volatile boolean fIsRecording;
        FileOutputStream fOutputStream;
        String fPath;
        AudioRecord fRecorder;
        private Thread fRecordingThread = null;
        volatile long fTotalRead;

        public WavRecorder(String str) {
            super();
            this.fPath = str;
            this.fBufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE, 16, 2);
            this.fTotalRead = 0;
        }

        /* JADX WARNING: Removed duplicated region for block: B:16:0x007b A[SYNTHETIC, Splitter:B:16:0x007b] */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x008b A[SYNTHETIC, Splitter:B:21:0x008b] */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x0094 A[SYNTHETIC, Splitter:B:26:0x0094] */
        /* JADX WARNING: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void overwriteData(java.lang.String r13, long r14) {
            /*
                r12 = this;
                r10 = 255(0xff, double:1.26E-321)
                r3 = 0
                java.io.RandomAccessFile r4 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x0075 }
                java.lang.String r5 = "rws"
                r4.<init>(r13, r5)     // Catch:{ Exception -> 0x0075 }
                r8 = 36
                long r6 = r14 + r8
                r8 = 4
                r4.seek(r8)     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r5 = 4
                byte[] r0 = new byte[r5]     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r5 = 0
                long r8 = r6 & r10
                int r8 = (int) r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                byte r8 = (byte) r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r0[r5] = r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r5 = 1
                r8 = 8
                long r8 = r6 >> r8
                long r8 = r8 & r10
                int r8 = (int) r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                byte r8 = (byte) r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r0[r5] = r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r5 = 2
                r8 = 16
                long r8 = r6 >> r8
                long r8 = r8 & r10
                int r8 = (int) r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                byte r8 = (byte) r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r0[r5] = r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r5 = 3
                r8 = 24
                long r8 = r6 >> r8
                long r8 = r8 & r10
                int r8 = (int) r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                byte r8 = (byte) r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r0[r5] = r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r4.write(r0)     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r8 = 40
                r4.seek(r8)     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r5 = 0
                long r8 = r14 & r10
                int r8 = (int) r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                byte r8 = (byte) r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r0[r5] = r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r5 = 1
                r8 = 8
                long r8 = r14 >> r8
                long r8 = r8 & r10
                int r8 = (int) r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                byte r8 = (byte) r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r0[r5] = r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r5 = 2
                r8 = 16
                long r8 = r14 >> r8
                long r8 = r8 & r10
                int r8 = (int) r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                byte r8 = (byte) r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r0[r5] = r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r5 = 3
                r8 = 24
                long r8 = r14 >> r8
                long r8 = r8 & r10
                int r8 = (int) r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                byte r8 = (byte) r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r0[r5] = r8     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                r4.write(r0)     // Catch:{ Exception -> 0x009f, all -> 0x009a }
                if (r4 == 0) goto L_0x00a2
                r4.close()     // Catch:{ Exception -> 0x0072 }
                r3 = r4
            L_0x0071:
                return
            L_0x0072:
                r5 = move-exception
                r3 = r4
                goto L_0x0071
            L_0x0075:
                r1 = move-exception
            L_0x0076:
                r1.printStackTrace()     // Catch:{ all -> 0x0091 }
                if (r3 == 0) goto L_0x0089
                java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x009d }
                r2.<init>(r13)     // Catch:{ Exception -> 0x009d }
                boolean r5 = r2.exists()     // Catch:{ Exception -> 0x009d }
                if (r5 == 0) goto L_0x0089
                r2.delete()     // Catch:{ Exception -> 0x009d }
            L_0x0089:
                if (r3 == 0) goto L_0x0071
                r3.close()     // Catch:{ Exception -> 0x008f }
                goto L_0x0071
            L_0x008f:
                r5 = move-exception
                goto L_0x0071
            L_0x0091:
                r5 = move-exception
            L_0x0092:
                if (r3 == 0) goto L_0x0097
                r3.close()     // Catch:{ Exception -> 0x0098 }
            L_0x0097:
                throw r5
            L_0x0098:
                r8 = move-exception
                goto L_0x0097
            L_0x009a:
                r5 = move-exception
                r3 = r4
                goto L_0x0092
            L_0x009d:
                r5 = move-exception
                goto L_0x0089
            L_0x009f:
                r1 = move-exception
                r3 = r4
                goto L_0x0076
            L_0x00a2:
                r3 = r4
                goto L_0x0071
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ansca.corona.AudioRecorder.WavRecorder.overwriteData(java.lang.String, long):void");
        }

        /* access modifiers changed from: private */
        public void writeAudioDataToFile() {
            long read;
            byte[] bArr = new byte[this.fBufferSize];
            try {
                this.fOutputStream = new FileOutputStream(this.fPath);
                writeWaveFileHeader(this.fOutputStream, 1, 2, 44100, 1, (long) 88200);
                this.fTotalRead = 0;
                while (AudioRecorder.this.fIsRunning) {
                    synchronized (this.fRecorder) {
                        read = this.fRecorder.getRecordingState() == 3 ? (long) this.fRecorder.read(bArr, 0, this.fBufferSize) : -3;
                    }
                    if (-3 != read) {
                        synchronized (this.fOutputStream) {
                            this.fOutputStream.write(bArr);
                            this.fTotalRead += read;
                        }
                    }
                }
                if (this.fOutputStream != null) {
                    try {
                        this.fOutputStream.close();
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e2) {
                try {
                    e2.printStackTrace();
                    if (this.fOutputStream != null) {
                        try {
                            this.fOutputStream.close();
                            this.fOutputStream = null;
                            File file = new File(this.fPath);
                            if (file.exists()) {
                                file.delete();
                            }
                        } catch (Exception e3) {
                        }
                    }
                    if (this.fOutputStream != null) {
                        try {
                            this.fOutputStream.close();
                        } catch (Exception e4) {
                        }
                    }
                } finally {
                    if (this.fOutputStream != null) {
                        try {
                            this.fOutputStream.close();
                        } catch (Exception e5) {
                        }
                    }
                }
            }
            boolean unused = AudioRecorder.this.fIsRunning = false;
        }

        private void writeWaveFileHeader(FileOutputStream fileOutputStream, long j, long j2, long j3, int i, long j4) throws IOException {
            fileOutputStream.write(new byte[]{82, 73, 70, 70, (byte) ((int) (255 & j2)), (byte) ((int) ((j2 >> 8) & 255)), (byte) ((int) ((j2 >> 16) & 255)), (byte) ((int) ((j2 >> 24) & 255)), 87, 65, 86, 69, 102, 109, 116, 32, 16, 0, 0, 0, 1, 0, (byte) i, 0, (byte) ((int) (255 & j3)), (byte) ((int) ((j3 >> 8) & 255)), (byte) ((int) ((j3 >> 16) & 255)), (byte) ((int) ((j3 >> 24) & 255)), (byte) ((int) (255 & j4)), (byte) ((int) ((j4 >> 8) & 255)), (byte) ((int) ((j4 >> 16) & 255)), (byte) ((int) ((j4 >> 24) & 255)), 2, 0, 16, 0, 100, 97, 116, 97, (byte) ((int) (255 & j)), (byte) ((int) ((j >> 8) & 255)), (byte) ((int) ((j >> 16) & 255)), (byte) ((int) ((j >> 24) & 255))}, 0, 44);
        }

        public void startRecording() {
            this.fIsRecording = true;
            this.fRecorder = new AudioRecord(1, RECORDER_SAMPLERATE, 16, 2, this.fBufferSize);
            this.fRecorder.startRecording();
            this.fRecordingThread = new Thread(new Runnable() {
                public void run() {
                    WavRecorder.this.writeAudioDataToFile();
                }
            });
            this.fRecordingThread.start();
        }

        public void stopRecording() {
            if (this.fRecorder != null) {
                this.fRecorder.stop();
                this.fRecorder.release();
            }
            if (this.fOutputStream != null) {
                try {
                    synchronized (this.fOutputStream) {
                        this.fOutputStream.close();
                        overwriteData(this.fPath, this.fTotalRead);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.fRecorder = null;
            this.fRecordingThread = null;
            this.fOutputStream = null;
        }
    }

    AudioRecorder(CoronaRuntime coronaRuntime, Handler handler) {
        this.fHandler = handler;
        this.fCoronaRuntime = coronaRuntime;
    }

    private boolean hasMicrophone() {
        if (CoronaEnvironment.getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.microphone")) {
            return true;
        }
        Log.d("Corona", "Device does not have a microphone");
        return false;
    }

    /* access modifiers changed from: private */
    public synchronized void postStatus(final int i) {
        if (!this.fIsNotifying) {
            this.fIsNotifying = true;
            this.fHandler.post(new Runnable() {
                public void run() {
                    JavaToNativeShim.recordCallback(AudioRecorder.this.fCoronaRuntime, i, AudioRecorder.this.fId);
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public AudioByteBufferHolder getCurrentBuffer() {
        if (this.fRecorderImplementation != null) {
            return this.fRecorderImplementation.getCurrentBuffer();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public AudioByteBufferHolder getNextBuffer() {
        if (this.fRecorderImplementation != null) {
            return this.fRecorderImplementation.getNextBuffer();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void releaseCurrentBuffer() {
        if (this.fRecorderImplementation != null) {
            this.fRecorderImplementation.releaseCurrentBuffer();
        }
    }

    public void setId(int i) {
        this.fId = i;
    }

    public void startRecording(String str) {
        if (!hasMicrophone()) {
            postStatus(-1);
        } else if (!this.fIsRunning) {
            this.fIsRunning = true;
            if (str == null || str.length() < 1) {
                this.fRecorderImplementation = new MemoryRecorder();
            }
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
            if (str.endsWith(".3gp")) {
                this.fRecorderImplementation = new ThreeGPRecorder(str);
            } else {
                this.fRecorderImplementation = new WavRecorder(str);
            }
            this.fRecorderImplementation.startRecording();
        }
    }

    public void stopRecording() {
        this.fIsRunning = false;
        if (this.fRecorderImplementation != null) {
            this.fRecorderImplementation.stopRecording();
        }
    }
}
