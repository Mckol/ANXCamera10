package com.android.camera.dualvideo.render;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Compose {
    public static final int DOWN = 12;
    public static final int FULL = 13;
    public static final int MINI = 10;
    public static int[][] PRIORITYS = {new int[]{100, 100, 100, 100, 100, 100, 99, 95, 95, 91}, new int[]{100, 100, 100, 100, 100, 100, 99, 95, 95, 91}, new int[]{100, 100, 100, 100, 100, 100, 99, 95, 95, 91}, new int[]{100, 100, 100, 100, 100, 100, 99, 95, 95, 91}, new int[]{100, 100, 100, 100, 100, 100, 99, 95, 95, 91}, new int[]{100, 100, 100, 100, 100, 100, 99, 95, 95, 91}, new int[]{89, 89, 89, 89, 89, 89, 79, 75, 75, 72}, new int[]{85, 85, 85, 85, 85, 85, 69, 65, 65, 61}, new int[]{85, 85, 85, 85, 85, 85, 69, 65, 65, 61}, new int[]{81, 81, 81, 81, 81, 81, 59, 55, 55, 51}};
    public static final int SIX_PATCH_0 = 20;
    public static final int SIX_PATCH_1 = 21;
    public static final int SIX_PATCH_2 = 22;
    public static final int SIX_PATCH_3 = 23;
    public static final int SIX_PATCH_4 = 24;
    public static final int SIX_PATCH_5 = 25;
    public static final int UP = 11;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RenderComposeType {
    }

    public static int getIndex(int i) {
        switch (i) {
            case 10:
                return 9;
            case 11:
                return 7;
            case 12:
                return 8;
            case 13:
                return 6;
            default:
                switch (i) {
                    case 20:
                        return 0;
                    case 21:
                        return 1;
                    case 22:
                        return 2;
                    case 23:
                        return 3;
                    case 24:
                        return 4;
                    case 25:
                        return 5;
                    default:
                        return -1;
                }
        }
    }
}
