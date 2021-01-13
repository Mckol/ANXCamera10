package com.xiaomi.asr.engine.record;

import android.util.Log;
import com.xiaomi.asr.engine.utils.JSONCompliant;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

public class AudioType implements JSONCompliant {
    public static final AudioType OPUS_NB = new AudioType(Encoding.OPUS, 8000);
    public static final AudioType OPUS_WB = new AudioType(Encoding.OPUS, 16000);
    public static final AudioType PCM_11k = new AudioType(Encoding.PCM_16, Frequency.FREQ_11KHZ);
    public static final AudioType PCM_16k = new AudioType(Encoding.PCM_16, 16000);
    public static final AudioType PCM_22k = new AudioType(Encoding.PCM_16, Frequency.FREQ_22KHZ);
    public static final AudioType PCM_44k = new AudioType(Encoding.PCM_16, 44100);
    public static final AudioType PCM_48k = new AudioType(Encoding.PCM_16, Frequency.FREQ_48KHZ);
    public static final AudioType PCM_8k = new AudioType(Encoding.PCM_16, 8000);
    public static final AudioType SPEEX_NB = new AudioType(Encoding.SPEEX, 8000);
    public static final AudioType SPEEX_WB = new AudioType(Encoding.SPEEX, 16000);
    public static final AudioType UNKNOWN = new AudioType(Encoding.UNKNOWN, 0);
    public final Encoding encoding;
    public final int frequency;
    public final byte[] sse;

    public enum Encoding {
        PCM_16,
        SPEEX,
        OPUS,
        UNKNOWN
    }

    public static class Frequency {
        public static final int FREQ_11KHZ = 11025;
        public static final int FREQ_16KHZ = 16000;
        public static final int FREQ_22KHZ = 22050;
        public static final int FREQ_44KHZ = 44100;
        public static final int FREQ_48KHZ = 48000;
        public static final int FREQ_8KHZ = 8000;
    }

    public AudioType(Encoding encoding2, int i) {
        this(encoding2, i, null);
    }

    public AudioType(Encoding encoding2, int i, byte[] bArr) {
        this.frequency = i;
        this.encoding = encoding2;
        this.sse = bArr;
    }

    public static AudioType fromJSON(JSONObject jSONObject) throws JSONException {
        int i = jSONObject.getInt("freq");
        String string = jSONObject.getString("enc");
        Encoding[] values = Encoding.values();
        for (Encoding encoding2 : values) {
            if (encoding2.name().equals(string)) {
                return new AudioType(encoding2, i);
            }
        }
        throw new JSONException("Encoding '" + string + "' not valid");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || AudioType.class != obj.getClass()) {
            return false;
        }
        AudioType audioType = (AudioType) obj;
        if (this.encoding == audioType.encoding && this.frequency == audioType.frequency) {
            return Arrays.equals(this.sse, audioType.sse);
        }
        return false;
    }

    public int getDuration(int i) {
        if (this.encoding == Encoding.PCM_16) {
            return (int) ((((long) i) * 1000) / ((long) this.frequency));
        }
        Log.e("AudioType", "Unable to detect duration for encoding " + this.encoding.name());
        return 0;
    }

    public int getDuration(short[] sArr) {
        return getDuration(sArr.length);
    }

    public int getSampleCount(int i) {
        return (this.frequency * i) / 1000;
    }

    public final int hashCode() {
        Encoding encoding2 = this.encoding;
        return (((((encoding2 == null ? 0 : encoding2.hashCode()) + 31) * 31) + this.frequency) * 31) + Arrays.hashCode(this.sse);
    }

    @Override // com.xiaomi.asr.engine.utils.JSONCompliant
    public JSONObject toJSON() {
        return new JSONObject();
    }

    public final String toString() {
        return "AudioType [frequency=" + this.frequency + ", encoding=" + this.encoding + ", sse=" + Arrays.toString(this.sse) + "]";
    }
}
