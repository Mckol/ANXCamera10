package com.android.camera.constant;

import b.c.a.c;

public class DurationConstant {
    public static final int DURATION_AMBILIGHT_CAPTURE = 2000;
    public static final int DURATION_AMBILIGHT_CROWD_MOVING_CAPTURE = 2000;
    public static final int DURATION_AMBILIGHT_MAGIC_STAR_CAPTURE = (c.isMTKPlatform() ? 20000 : DURATION_VIDEO_BOKEH_RECORDING);
    public static final int DURATION_AMBILIGHT_STAR_TRACK_CAPTURE = (c.isMTKPlatform() ? 6400 : 3200);
    public static final int DURATION_AUTOZOOM = 800;
    public static final int DURATION_CHECK_CAMERA_ALIVE = 5000;
    public static final int DURATION_FPS960_VIDEO = 2000;
    public static final int DURATION_GIF_VIDEO_RECORDING_FUN = 5000;
    public static final int DURATION_LANDSCAPE_HINT = 6000;
    public static final int DURATION_LIVE_RECORD = 15400;
    public static final int DURATION_MI_LIVE_RECORD = 15000;
    public static final int DURATION_RESET_FALLBACK = 1500;
    public static final int DURATION_RESET_WAIT_SAVE_FINISH_STATE = 5000;
    public static final int DURATION_STARTUP_BLUR_MASK = 200;
    public static final int DURATION_STARTUP_MASK = 100;
    public static final int DURATION_SUPER_NIGHT_CAPTURE = 2000;
    public static final int DURATION_SUPER_NIGHT_CAPTURE_HINT = 300;
    public static final int DURATION_VIDEO_BOKEH_RECORDING = 30000;
    public static final int DURATION_VIDEO_RECORDING_CIRCLE = 10000;
    public static final int DURATION_VIDEO_RECORDING_FUN = 15000;
    public static final long MIN_RECORD_TIME = 500;
}
