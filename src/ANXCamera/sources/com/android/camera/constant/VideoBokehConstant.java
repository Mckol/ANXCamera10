package com.android.camera.constant;

public class VideoBokehConstant {
    public static final String[] BEAUTY_CATEGORY_VIDEO_BOKEH = {"key_video_bokeh_blur_null", "key_video_bokeh_blur_ratio", "key_video_bokeh_spin_ratio", "key_video_bokeh_zoom_out_ratio", "key_video_bokeh_zoom_in_ratio", "key_video_bokeh_color_point_ratio"};
    public static final int MODE_VIDEO_BOKEH_BLUR = 6;
    public static final int MODE_VIDEO_BOKEH_COLOR_POINT = 2;
    public static final int MODE_VIDEO_BOKEH_COLOR_RETENTION = 1;
    public static final int MODE_VIDEO_BOKEH_NULL = 0;
    public static final int MODE_VIDEO_BOKEH_SPIN = 4;
    public static final int MODE_VIDEO_BOKEH_ZOOM_IN = 3;
    public static final int MODE_VIDEO_BOKEH_ZOOM_OUT = 5;
    public static final String VIDEO_BOKEH_BLUR_NULL = "key_video_bokeh_blur_null";
    public static final String VIDEO_BOKEH_BLUR_RATIO = "key_video_bokeh_blur_ratio";
    public static final String VIDEO_BOKEH_COLOR_POINT_RATIO = "key_video_bokeh_color_point_ratio";
    public static final String VIDEO_BOKEH_COLOR_RETENTION = "key_video_bokeh_color_point_retention";
    public static final String VIDEO_BOKEH_SPIN_RATIO = "key_video_bokeh_spin_ratio";
    public static final String VIDEO_BOKEH_ZOOM_IN_RATIO = "key_video_bokeh_zoom_in_ratio";
    public static final String VIDEO_BOKEH_ZOOM_OUT_RATIO = "key_video_bokeh_zoom_out_ratio";

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getDefaultValueByKey(String str) {
        char c2;
        switch (str.hashCode()) {
            case 463390384:
                if (str.equals("key_video_bokeh_zoom_out_ratio")) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 678742590:
                if (str.equals("key_video_bokeh_color_point_ratio")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 1414809360:
                if (str.equals("key_video_bokeh_spin_ratio")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 1742391003:
                if (str.equals("key_video_bokeh_zoom_in_ratio")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 2089357173:
                if (str.equals("key_video_bokeh_blur_ratio")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        return (c2 == 0 || c2 == 1 || c2 == 2 || c2 == 3 || c2 == 4) ? 60 : 0;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getVideoBokehMode(String str) {
        char c2;
        switch (str.hashCode()) {
            case -1992095791:
                if (str.equals("key_video_bokeh_color_point_retention")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case -902532899:
                if (str.equals("key_video_bokeh_blur_null")) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 463390384:
                if (str.equals("key_video_bokeh_zoom_out_ratio")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 678742590:
                if (str.equals("key_video_bokeh_color_point_ratio")) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 1414809360:
                if (str.equals("key_video_bokeh_spin_ratio")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 1742391003:
                if (str.equals("key_video_bokeh_zoom_in_ratio")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 2089357173:
                if (str.equals("key_video_bokeh_blur_ratio")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        if (c2 == 0) {
            return 6;
        }
        if (c2 == 1) {
            return 4;
        }
        if (c2 == 2) {
            return 5;
        }
        if (c2 == 3) {
            return 3;
        }
        if (c2 != 4) {
            return c2 != 5 ? 0 : 1;
        }
        return 2;
    }
}
