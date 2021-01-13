package com.xiaomi.asr.engine;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.asr.engine.impl.W2VPEngineImpl;
import com.xiaomi.asr.engine.record.AudioType;
import com.xiaomi.asr.engine.record.XSpeedStreamingFileRecorderSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum ReEnrollmentAction {
    ReEnrollment;
    
    private static final String ENROLL_AUDIO_PREFIX = "enroll_";
    private static final String ENROLL_AUDIO_SUFFIX = ".pcm";
    private static final String TAG = "ReEnrollmentAction";
    private FilenameFilter enrollAudioFilter = new FilenameFilter() {
        /* class com.xiaomi.asr.engine.ReEnrollmentAction.AnonymousClass1 */

        public boolean accept(File file, String str) {
            return file.isFile() && str.contains(ReEnrollmentAction.ENROLL_AUDIO_PREFIX) && str.contains(ReEnrollmentAction.ENROLL_AUDIO_SUFFIX);
        }
    };
    private Comparator<String> fileNameComparator = new Comparator<String>() {
        /* class com.xiaomi.asr.engine.ReEnrollmentAction.AnonymousClass2 */

        public int compare(String str, String str2) {
            return str.compareToIgnoreCase(str2);
        }
    };
    private String mBlobFilePath;
    private Context mContext;
    private int mCurrentProcess;
    private String mEnrollBackupFolder;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private List<String> mReEnrollAudioFileList = new ArrayList();
    private List<String> mReEnrollAudioFileListCache;
    private String mReEnrollPhrase;
    private List<String> mReEnrollPhraseCommittedList = new ArrayList();
    private List<String> mReEnrollPhraseList = new ArrayList();
    private Runnable mReEnrollRunnable = new Runnable() {
        /* class com.xiaomi.asr.engine.ReEnrollmentAction.AnonymousClass3 */

        public void run() {
            Log.i(ReEnrollmentAction.TAG, "Start re-enroll with backup audio");
            String str = (String) ReEnrollmentAction.this.mReEnrollAudioFileList.remove(0);
            XSpeedStreamingFileRecorderSource xSpeedStreamingFileRecorderSource = new XSpeedStreamingFileRecorderSource(AudioType.PCM_16k, str);
            Log.i(ReEnrollmentAction.TAG, "Start re-enroll with backup audio: " + str);
            W2VPEngineImpl.getInstance().startEnrollmentStreamingFileRecorder(xSpeedStreamingFileRecorderSource, 3000);
        }
    };
    private ReEnrollmentActionListener mReEnrollmentActionListener;

    public interface ReEnrollmentActionListener {
        void onAudioQualityChecked(String str, boolean z);

        void onEngineVersionChecked(String str);

        void onFinished(boolean z, List<String> list, String str);
    }

    /* access modifiers changed from: private */
    public class WVPCallback implements WVPListener {
        private WVPCallback() {
        }

        private void logText(String str) {
            Log.d(ReEnrollmentAction.TAG, "" + str);
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onAbortEnrollmentComplete() {
            Log.i(ReEnrollmentAction.TAG, "Aborted re-enroll with backup audio");
            if (ReEnrollmentAction.this.mCurrentProcess == 3) {
                ReEnrollmentAction.this.mCurrentProcess = 4;
                W2VPEngine.release();
            } else if (ReEnrollmentAction.this.mCurrentProcess == 6) {
                ReEnrollmentAction.this.reEnrollmentForOnePhraseWithCheck();
            }
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onAudioData(byte[] bArr) {
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onCommitEnrollmentComplete() {
            Log.i(ReEnrollmentAction.TAG, "Committed re-enroll with backup audio");
            if (ReEnrollmentAction.this.mCurrentProcess == 3) {
                ReEnrollmentAction.this.mReEnrollmentActionListener.onAudioQualityChecked(ReEnrollmentAction.this.mReEnrollPhrase, true);
            } else if (ReEnrollmentAction.this.mCurrentProcess == 6) {
                ReEnrollmentAction.this.mReEnrollmentActionListener.onAudioQualityChecked(ReEnrollmentAction.this.mReEnrollPhrase, false);
            }
            ReEnrollmentAction.this.mReEnrollPhraseCommittedList.add(ReEnrollmentAction.this.mReEnrollPhrase);
            ReEnrollmentAction.this.reEnrollmentForOnePhraseWithCheck();
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onConflictAudio() {
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onDebug(String str) {
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onEndOfSpeech() {
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onEnergyLevelAvailable(float f, boolean z) {
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onEnrollAudioBufferAvailable(byte[] bArr, boolean z) {
            logText("onEnrollAudioBufferAvailable: " + bArr.length + " var: " + z);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(ReEnrollmentAction.this.mContext.getExternalFilesDir(null) + File.separator + System.currentTimeMillis() + "_enroll.pcm");
                fileOutputStream.write(bArr);
                fileOutputStream.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onEnrollmentComplete(boolean z, boolean z2, float f, int i) {
            Log.i(ReEnrollmentAction.TAG, "Finshed re-enroll with backup audio");
            if (!z) {
                W2VPEngine.abortEnrollment();
            } else if (ReEnrollmentAction.this.mReEnrollAudioFileList.size() > 0) {
                ReEnrollmentAction.this.mReEnrollRunnable.run();
            } else {
                W2VPEngine.commitEnrollment();
            }
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onGenerateModel(boolean z, String str) {
            logText("onGenerateModel, success:" + z + ", modelFilePath:" + str);
            ReEnrollmentAction.this.notifyFinished(z, str);
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onGrammarUpdated(boolean z) {
            logText("onGrammarUpdated");
            if (z) {
                ReEnrollmentAction.this.mReEnrollAudioFileList.clear();
                ReEnrollmentAction.this.mReEnrollAudioFileList.addAll(ReEnrollmentAction.this.mReEnrollAudioFileListCache);
                ReEnrollmentAction.this.mReEnrollRunnable.run();
                return;
            }
            ReEnrollmentAction.this.notifyFinished(false, null);
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onInit(boolean z) {
            if (z) {
                String version = W2VPEngine.version();
                if (!TextUtils.isEmpty(version)) {
                    String[] split = version.split(" ");
                    logText("onInit:sdk版本号:" + split[0] + "\n");
                    logText("唤醒引擎版本号:" + split[1] + "\n");
                    logText("声纹引擎版本号:" + split[2] + "\n");
                }
                ReEnrollmentAction.this.mReEnrollmentActionListener.onEngineVersionChecked(String.valueOf(version));
                if (ReEnrollmentAction.this.mCurrentProcess == 2) {
                    ReEnrollmentAction.this.mCurrentProcess = 3;
                } else if (ReEnrollmentAction.this.mCurrentProcess == 5) {
                    ReEnrollmentAction.this.mCurrentProcess = 6;
                }
                onGrammarUpdated(true);
                return;
            }
            ReEnrollmentAction.this.notifyFinished(false, null);
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onPhraseSpotted(PhraseWakeupResult phraseWakeupResult) {
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onRelease() {
            logText("onRelease");
            Log.d(ReEnrollmentAction.TAG, "this.mCurrentProcess =  " + ReEnrollmentAction.this.mCurrentProcess);
            if (ReEnrollmentAction.this.mCurrentProcess == 1) {
                ReEnrollmentAction.this.mCurrentProcess = 2;
                W2VPEngine.init(ReEnrollmentAction.this.mBlobFilePath, "reEnroll");
            } else if (ReEnrollmentAction.this.mCurrentProcess == 4) {
                ReEnrollmentAction.this.mCurrentProcess = 5;
                W2VPEngine.init(ReEnrollmentAction.this.mBlobFilePath, "reEnroll");
            }
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onStartAudio() {
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onStartOfSpeech() {
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onStopAudio() {
        }
    }

    private ReEnrollmentAction() {
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void notifyFinished(boolean z, String str) {
        this.mContext = null;
        this.mReEnrollmentActionListener.onFinished(z, this.mReEnrollPhraseCommittedList, str);
        this.mReEnrollmentActionListener = null;
        W2VPEngine.setListener(null);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void reEnrollmentForOnePhraseWithCheck() {
        if (this.mReEnrollPhraseList.size() > 0) {
            this.mReEnrollPhrase = this.mReEnrollPhraseList.remove(0);
            if (!reEnrollmentFromPath(this.mEnrollBackupFolder)) {
                notifyFinished(false, null);
            }
        } else if (this.mReEnrollPhraseCommittedList.size() > 0) {
            W2VPEngine.generateModel();
        } else {
            notifyFinished(false, null);
        }
    }

    private boolean reEnrollmentFromPath(String str) {
        IOException e2;
        Log.i(TAG, "reEnrollmentFromPath phrase:" + this.mReEnrollPhrase + " enrollAudioPath:" + str);
        boolean z = false;
        this.mReEnrollAudioFileListCache = scanFile(new File(str), this.enrollAudioFilter, false);
        Collections.sort(this.mReEnrollAudioFileListCache, this.fileNameComparator);
        if (this.mReEnrollAudioFileListCache.size() > 0) {
            try {
                FileInputStream fileInputStream = new FileInputStream(str + File.separator + "wakeup_phrase.txt");
                if (this.mReEnrollPhrase.equalsIgnoreCase(new BufferedReader(new InputStreamReader(fileInputStream)).readLine())) {
                    try {
                        W2VPEngine.setListener(new WVPCallback());
                        this.mCurrentProcess = 1;
                        W2VPEngine.release();
                        z = true;
                    } catch (IOException e3) {
                        e2 = e3;
                        z = true;
                        Log.d(TAG, e2.getLocalizedMessage());
                        return z;
                    }
                }
                fileInputStream.close();
            } catch (IOException e4) {
                e2 = e4;
                Log.d(TAG, e2.getLocalizedMessage());
                return z;
            }
        }
        return z;
    }

    private ArrayList<String> scanFile(File file, FilenameFilter filenameFilter, boolean z) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (file.exists() && file.listFiles() != null) {
            File[] listFiles = file.listFiles();
            for (File file2 : listFiles) {
                if (filenameFilter.accept(file2, file2.getName())) {
                    arrayList.add(file2.getPath());
                }
                if (file2.isDirectory() && z) {
                    arrayList.addAll(scanFile(file2, filenameFilter, true));
                }
            }
        }
        return arrayList;
    }

    public void reEnrollmentWithBackupAudio(Context context, String str, String str2, String str3, ReEnrollmentActionListener reEnrollmentActionListener) {
        Log.i(TAG, " phrase:" + str + " blobFilePath:" + str3);
        this.mContext = context;
        this.mReEnrollPhraseList.clear();
        this.mReEnrollPhraseCommittedList.clear();
        this.mReEnrollPhraseList.add(str);
        this.mBlobFilePath = str3;
        this.mEnrollBackupFolder = str2;
        this.mReEnrollmentActionListener = reEnrollmentActionListener;
        reEnrollmentForOnePhraseWithCheck();
    }
}
