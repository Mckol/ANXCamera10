package com.ss.android.ttve.mediacodec;

import android.media.MediaCodecInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

class MediaCodecUtils {
    MediaCodecUtils() {
    }

    @Nullable
    @RequiresApi(api = 18)
    static MediaCodecInfo.CodecProfileLevel findBestMatchedProfile(@NonNull MediaCodecInfo.CodecCapabilities codecCapabilities, int i) {
        MediaCodecInfo.CodecProfileLevel[] codecProfileLevelArr = codecCapabilities.profileLevels;
        MediaCodecInfo.CodecProfileLevel codecProfileLevel = null;
        for (MediaCodecInfo.CodecProfileLevel codecProfileLevel2 : codecProfileLevelArr) {
            int i2 = codecProfileLevel2.profile;
            if (i2 == i) {
                return codecProfileLevel2;
            }
            if (codecProfileLevel == null || codecProfileLevel.profile < i2) {
                codecProfileLevel = codecProfileLevel2;
            }
        }
        return codecProfileLevel;
    }
}
