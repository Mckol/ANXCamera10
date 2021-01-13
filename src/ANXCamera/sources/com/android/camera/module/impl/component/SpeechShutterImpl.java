package com.android.camera.module.impl.component;

import android.content.Intent;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraIntentManager;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.xiaomi.asr.engine.MultiWakeupEngine;
import com.xiaomi.asr.engine.PhraseWakeupResult;
import com.xiaomi.asr.engine.WVPListener;

public class SpeechShutterImpl implements ModeProtocol.SpeechShutterDetect {
    private static final boolean DEBUG = true;
    private static final String TAG = "SpeechShutterImpl";
    private boolean mIsInit;

    static class WVPCallback implements WVPListener {
        WVPCallback() {
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onAbortEnrollmentComplete() {
            Log.d(SpeechShutterImpl.TAG, "onAbortEnrollmentComplete");
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onAudioData(byte[] bArr) {
            String str = SpeechShutterImpl.TAG;
            Log.d(str, "onAudioData:" + bArr.length);
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onCommitEnrollmentComplete() {
            Log.d(SpeechShutterImpl.TAG, "onCommitEnrollmentComplete");
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onConflictAudio() {
            Log.d(SpeechShutterImpl.TAG, "onConflictAudio");
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onDebug(String str) {
            String str2 = SpeechShutterImpl.TAG;
            Log.d(str2, "onDebug:" + str);
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onEndOfSpeech() {
            Log.d(SpeechShutterImpl.TAG, "onEndOfSpeech");
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onEnergyLevelAvailable(float f, boolean z) {
            String str = SpeechShutterImpl.TAG;
            Log.d(str, "onEnergyLevelAvailable volume:" + f + ", hasSpeech:" + z);
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onEnrollAudioBufferAvailable(byte[] bArr, boolean z) {
            String str = SpeechShutterImpl.TAG;
            Log.d(str, "onEnrollAudioBufferAvailable:" + bArr.length + ", hasSpeech:" + z);
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onEnrollmentComplete(boolean z, boolean z2, float f, int i) {
            String str = SpeechShutterImpl.TAG;
            Log.d(str, "onEnrollmentComplete wakeup:" + z + ", voicePrint:" + z2 + ", type:" + i);
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onGenerateModel(boolean z, String str) {
            String str2 = SpeechShutterImpl.TAG;
            Log.d(str2, "onGenerateModel success:" + z + ", modelFilePath:" + str);
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onGrammarUpdated(boolean z) {
            String str = SpeechShutterImpl.TAG;
            Log.d(str, "onGrammarUpdated update:" + z);
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onInit(boolean z) {
            if (z) {
                String wakeupVersion = MultiWakeupEngine.wakeupVersion();
                String str = SpeechShutterImpl.TAG;
                Log.d(str, "version:" + wakeupVersion);
            }
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onPhraseSpotted(PhraseWakeupResult phraseWakeupResult) {
            String str = SpeechShutterImpl.TAG;
            Log.d(str, "onPhraseSpotted " + phraseWakeupResult.toString());
            CameraAppImpl.getAndroidContext().sendBroadcast(new Intent(CameraIntentManager.ACTION_SPEECH_SHUTTER));
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onRelease() {
            Log.d(SpeechShutterImpl.TAG, "onRelease");
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onStartAudio() {
            Log.d(SpeechShutterImpl.TAG, "onStartAudio");
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onStartOfSpeech() {
            Log.d(SpeechShutterImpl.TAG, "onStartOfSpeech");
        }

        @Override // com.xiaomi.asr.engine.WVPListener
        public void onStopAudio() {
            Log.d(SpeechShutterImpl.TAG, "onStopAudio");
        }
    }

    public static ModeProtocol.BaseProtocol create() {
        return new SpeechShutterImpl();
    }

    @Override // com.android.camera.protocol.ModeProtocol.SpeechShutterDetect
    public void onDestroy() {
        if (this.mIsInit) {
            Log.d(TAG, "onDestroy");
            MultiWakeupEngine.release();
            this.mIsInit = false;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.SpeechShutterDetect
    public void processingSpeechShutter(boolean z) {
        String str = TAG;
        Log.d(str, "processingSpeechShutter " + z);
        if (z) {
            if (!this.mIsInit) {
                Log.d(TAG, "init start");
                MultiWakeupEngine.setListener(new WVPCallback());
                MultiWakeupEngine.init();
                Log.d(TAG, "init end ");
                MultiWakeupEngine.start();
                Log.d(TAG, "processingSpeechShutter start");
                this.mIsInit = true;
                return;
            }
            MultiWakeupEngine.restart();
            Log.d(TAG, "processingSpeechShutter restart");
        } else if (this.mIsInit) {
            MultiWakeupEngine.stop(0);
            Log.d(TAG, "processingSpeechShutter stop");
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(255, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(255, this);
        onDestroy();
    }
}
