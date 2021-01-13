package com.xiaomi.asr.engine.utils;

import android.util.Log;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DSPWakeupInfoParser {
    private static Pattern DSP_WAKEUP_INFO_PATTERN = Pattern.compile("^FMT:(\\d+);VENDOR:NUANCE;START:(\\d+);FTT_LENGTH:(\\d+);SCORE:(\\d+);AEC:(\\d+);PCM_LENGTH:(\\d+)$");
    boolean mIsValideInfo = false;
    private boolean mMxeWakeupAec = false;
    private long mMxeWakeupEndTime = -1;
    private int mMxeWakeupSocre = -1;
    private long mMxeWakeupStartTime = -1;
    private int mPcmLength = -1;
    private long mWakeupRequiredLeadingSilence = -1;

    public int getPcmLength() {
        return this.mPcmLength;
    }

    public long getWakeupEndTime() {
        return this.mMxeWakeupEndTime;
    }

    public int getWakeupScore() {
        return this.mMxeWakeupSocre;
    }

    public long getWakeupStartTime() {
        return this.mMxeWakeupStartTime;
    }

    public boolean isInfoValid() {
        return this.mIsValideInfo;
    }

    public boolean isWakeupAec() {
        return this.mMxeWakeupAec;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00f5, code lost:
        r13 = r18;
        r1 = r20;
     */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00bf  */
    public void parse(short[] sArr) {
        int i;
        int i2;
        int i3;
        StringBuilder sb;
        boolean z;
        int i4;
        short[] sArr2 = sArr;
        int length = sArr2.length;
        StringBuilder sb2 = new StringBuilder();
        int length2 = sArr2.length;
        int i5 = -1;
        int i6 = -1;
        long j = -1;
        long j2 = -1;
        int i7 = 0;
        boolean z2 = false;
        short s = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        while (true) {
            int i11 = i5;
            if (i7 >= length2) {
                i = i6;
                break;
            }
            short s2 = sArr2[i7];
            byte b2 = (byte) s2;
            byte b3 = (byte) (s2 >> 8);
            i = i6;
            if (!z2) {
                if (b2 != 0) {
                    if (b3 == 0) {
                        sb2.append((char) b2);
                    } else {
                        sb2.append((char) b2);
                        sb2.append((char) b3);
                        if (!z2) {
                            String sb3 = sb2.toString();
                            Log.i("DSPWakeupInfoParser", "Dsp header is " + sb3);
                            Matcher matcher = DSP_WAKEUP_INFO_PATTERN.matcher(sb3);
                            if (!matcher.find()) {
                                Log.i("DSPWakeupInfoParser", "format pattern not valid");
                                break;
                            }
                            int parseInt = Integer.parseInt(matcher.group(1));
                            j = Long.parseLong(matcher.group(2));
                            j2 = Long.parseLong(matcher.group(3));
                            i2 = Integer.parseInt(matcher.group(4));
                            sb = sb2;
                            int parseInt2 = Integer.parseInt(matcher.group(5));
                            i3 = Integer.parseInt(matcher.group(6));
                            if (parseInt2 > 0) {
                                i4 = 1;
                                this.mMxeWakeupAec = true;
                                z = false;
                            } else {
                                i4 = 1;
                                z = false;
                                this.mMxeWakeupAec = false;
                            }
                            if (parseInt != i4) {
                                Log.i("DSPWakeupInfoParser", "format version not valid");
                                break;
                            }
                            i11 = i2;
                            i6 = i3;
                            z2 = z2;
                        } else {
                            sb = sb2;
                            i6 = i;
                        }
                    }
                }
                z2 = true;
                if (!z2) {
                }
            } else {
                sb = sb2;
                i6 = i;
            }
            if (i8 % 2 == 0) {
                s = s2;
            } else {
                int i12 = ((s << 16) & -65536) | (65535 & s2);
                if (i8 < length - 2) {
                    i9 = i12 ^ i9;
                } else {
                    i10 = i12;
                }
            }
            i8++;
            i7++;
            sArr2 = sArr;
            i5 = i11;
            length2 = length2;
            sb2 = sb;
        }
        if (i10 == 0 || j <= 0 || j2 <= 0 || i2 < 0 || i3 <= 0) {
            Log.i("DSPWakeupInfoParser", "format check sum not valid verify " + i9 + " checksum " + i10);
            return;
        }
        Log.i("DSPWakeupInfoParser", "format valid " + i10);
        this.mIsValideInfo = true;
        this.mMxeWakeupStartTime = j;
        this.mMxeWakeupEndTime = this.mMxeWakeupStartTime + j2;
        this.mMxeWakeupSocre = i2;
        this.mPcmLength = i3;
    }

    public DSPWakeupInfoParser reset() {
        this.mIsValideInfo = false;
        this.mMxeWakeupStartTime = -1;
        this.mMxeWakeupEndTime = -1;
        this.mMxeWakeupSocre = -1;
        this.mWakeupRequiredLeadingSilence = -1;
        this.mMxeWakeupAec = false;
        this.mPcmLength = -1;
        return this;
    }
}
