package com.xiaomi.asr.engine.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import com.google.android.apps.photos.api.PhotosOemApi;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class SaveVoiceUtil {
    private static String RECORD_DIR = "/sdcard/miasr/voiceprint/record";
    private static final String TAG = "SaveVoiceUtil";
    private FileOutputStream mOutputStream;
    private Handler mSaveHandle;
    private HandlerThread mSaveThread = new HandlerThread("save_voice");

    class SaveVoiceCallback implements Handler.Callback {
        SaveVoiceCallback() {
        }

        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                if (i == 2) {
                    byte[] byteArray = message.getData().getByteArray(PhotosOemApi.PATH_SPECIAL_TYPE_DATA);
                    if (SaveVoiceUtil.this.mOutputStream == null) {
                        return false;
                    }
                    try {
                        SaveVoiceUtil.this.mOutputStream.write(byteArray);
                        return false;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        Log.e(SaveVoiceUtil.TAG, "write error:" + e2.getMessage());
                        return false;
                    }
                } else if (i != 3 || SaveVoiceUtil.this.mOutputStream == null) {
                    return false;
                } else {
                    try {
                        SaveVoiceUtil.this.mOutputStream.close();
                        SaveVoiceUtil.this.mOutputStream = null;
                        return false;
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        Log.e(SaveVoiceUtil.TAG, "close error:" + e3.getMessage());
                        return false;
                    }
                }
            } else if (!SaveVoiceUtil.this.checkAndCreateDir(SaveVoiceUtil.RECORD_DIR)) {
                return false;
            } else {
                try {
                    SaveVoiceUtil saveVoiceUtil = SaveVoiceUtil.this;
                    saveVoiceUtil.mOutputStream = new FileOutputStream(SaveVoiceUtil.RECORD_DIR + "/" + SaveVoiceUtil.this.getCurrentTime() + "_" + ((String) message.obj) + ".cmvn");
                    return false;
                } catch (FileNotFoundException e4) {
                    e4.printStackTrace();
                    Log.e(SaveVoiceUtil.TAG, "create error:" + e4.getMessage());
                    return false;
                }
            }
        }
    }

    public SaveVoiceUtil() {
        this.mSaveThread.start();
        this.mSaveHandle = new Handler(this.mSaveThread.getLooper(), new SaveVoiceCallback());
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean checkAndCreateDir(String str) {
        File file = new File(str);
        return file.exists() || file.mkdirs();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getCurrentTime() {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(1);
        int i2 = instance.get(2);
        int i3 = instance.get(5);
        int i4 = instance.get(11);
        int i5 = instance.get(12);
        int i6 = instance.get(13);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(i);
        stringBuffer.append("-");
        stringBuffer.append(i2 + 1);
        stringBuffer.append("-");
        stringBuffer.append(i3);
        stringBuffer.append("-");
        stringBuffer.append(i4);
        stringBuffer.append("-");
        stringBuffer.append(i5);
        stringBuffer.append("-");
        stringBuffer.append(i6);
        return stringBuffer.toString();
    }

    public void closeFile() {
        this.mSaveHandle.sendMessage(this.mSaveHandle.obtainMessage(3));
    }

    public void createFile(String str) {
        Message obtainMessage = this.mSaveHandle.obtainMessage(1);
        obtainMessage.obj = str;
        this.mSaveHandle.sendMessage(obtainMessage);
    }

    public void setSaveDir(String str) {
        RECORD_DIR = str;
    }

    public void writeFile(byte[] bArr) {
        if (bArr != null && bArr.length > 0) {
            Message obtainMessage = this.mSaveHandle.obtainMessage(2);
            Bundle bundle = new Bundle();
            bundle.putByteArray(PhotosOemApi.PATH_SPECIAL_TYPE_DATA, bArr);
            obtainMessage.setData(bundle);
            this.mSaveHandle.sendMessage(obtainMessage);
        }
    }
}
