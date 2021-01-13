package com.xiaomi.asr.engine.record;

import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XSpeedStreamingFileRecorderSource extends AudioSource {
    private static final String TAG = "XSpeedStreamingFileRecorderSource";
    private String _fileName;

    /* renamed from: a  reason: collision with root package name */
    private final AudioType f386a;
    private InputStream mInputStream = null;

    public XSpeedStreamingFileRecorderSource(AudioType audioType, String str) {
        this.f386a = audioType;
        this._fileName = str;
    }

    private void initData() {
        this.mInputStream = null;
        try {
            this.mInputStream = new FileInputStream(new File(this._fileName));
            String str = TAG;
            Log.e(str, "" + this.mInputStream.available());
        } catch (IOException e2) {
            this.mInputStream = null;
            Log.e(TAG, "", e2);
        }
    }

    private static void slientSleep(long j) {
        try {
            Thread.sleep(j);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.xiaomi.asr.engine.record.AudioSource
    public void init() {
        initData();
    }

    @Override // com.xiaomi.asr.engine.record.AudioSource
    public int read(byte[] bArr, int i, int i2) {
        InputStream inputStream = this.mInputStream;
        if (inputStream == null) {
            return -1;
        }
        try {
            return inputStream.read(bArr, i, i2);
        } catch (IOException e2) {
            Log.e(TAG, "", e2);
            return -1;
        }
    }

    @Override // com.xiaomi.asr.engine.record.AudioSource
    public void release() {
        InputStream inputStream = this.mInputStream;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e2) {
                Log.e(TAG, "", e2);
            }
        }
    }

    @Override // com.xiaomi.asr.engine.record.AudioSource
    public void startRecording() {
    }
}
