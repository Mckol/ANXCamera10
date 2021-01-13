package miui.media;

import com.miui.internal.JniNames;

public class Mp3Encoder {
    public static final int DEFAULT_AUDIO_CHANNEL = 16;
    public static final int DEFAULT_CHANNEL_COUNT = 1;
    public static final int DEFAULT_OUT_BIT_RATE = 64;
    public static final int DEFAULT_OUT_MODE = 3;
    public static final int DEFAULT_QUALITY = 0;
    public static final int DEFAULT_SAMPLE_RATE = 44100;
    public static final int DEFAULT_VBR_QUALITY = -1;
    public static final int OUT_MODE_MONO = 3;
    public static final int OUT_MODE_STEREO = 0;
    private int mChannelCount = 1;
    private int mInSampleRate = 44100;
    private int mOutBitRate = 64;
    private int mOutMode = 3;
    private int mOutSampleRate = 44100;
    private int mQuality = 0;
    private int mVBRQuality = -1;
    private long ptr_lame_global_flag = -1;

    static {
        System.loadLibrary(JniNames.LIB_MP3_LAME);
    }

    private native int lame_close(long j);

    private native int lame_encode(long j, short[] sArr, short[] sArr2, int i, byte[] bArr, int i2);

    private native int lame_encode_interleaved(long j, short[] sArr, int i, byte[] bArr, int i2);

    private native int lame_flush(long j, byte[] bArr, int i);

    private native long lame_init(int i, int i2, int i3, int i4, int i5, int i6, int i7);

    private native int lame_samples_to_encode(long j);

    private native int lame_write_vbr_header(long j, String str);

    public int close() {
        long j = this.ptr_lame_global_flag;
        if (j <= 0) {
            return -1;
        }
        int lame_close = lame_close(j);
        this.ptr_lame_global_flag = -1;
        return lame_close;
    }

    public int encode(short[] sArr, short[] sArr2, int i, byte[] bArr, int i2) {
        return lame_encode(this.ptr_lame_global_flag, sArr, sArr2, i, bArr, i2);
    }

    public int encodeInterleaved(short[] sArr, int i, byte[] bArr, int i2) {
        return lame_encode_interleaved(this.ptr_lame_global_flag, sArr, i, bArr, i2);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        super.finalize();
        long j = this.ptr_lame_global_flag;
        if (j > 0) {
            lame_close(j);
        }
    }

    public int flush(byte[] bArr, int i) {
        return lame_flush(this.ptr_lame_global_flag, bArr, i);
    }

    public int getSamplesToEncode() {
        return lame_samples_to_encode(this.ptr_lame_global_flag);
    }

    public boolean init() {
        this.ptr_lame_global_flag = lame_init(this.mInSampleRate, this.mOutMode, this.mChannelCount, this.mOutSampleRate, this.mOutBitRate, this.mQuality, this.mVBRQuality);
        return this.ptr_lame_global_flag > 0;
    }

    public void setChannelCount(int i) {
        this.mChannelCount = i;
    }

    public void setInSampleRate(int i) {
        this.mInSampleRate = i;
    }

    public void setOutBitRate(int i) {
        this.mOutBitRate = i;
    }

    public void setOutMode(int i) {
        this.mOutMode = i;
    }

    public void setOutSampleRate(int i) {
        this.mOutSampleRate = i;
    }

    public void setQuality(int i) {
        this.mQuality = i;
    }

    public void setVBRQuality(int i) {
        this.mVBRQuality = i;
    }

    public void writeVBRHeader(String str) {
        lame_write_vbr_header(this.ptr_lame_global_flag, str);
    }
}
