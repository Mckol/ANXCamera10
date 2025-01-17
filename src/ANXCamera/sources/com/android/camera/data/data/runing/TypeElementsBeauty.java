package com.android.camera.data.data.runing;

import com.android.camera.R;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.constant.VideoBokehConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.TypeElements;
import com.android.camera.data.data.TypeItem;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.List;

public class TypeElementsBeauty extends TypeElements {
    public TypeElementsBeauty(ComponentRunningShine componentRunningShine) {
        super(componentRunningShine);
    }

    private TypeItem createTypeItem(@ComponentRunningShine.ShineType String str, String str2, boolean z, CameraCapabilities cameraCapabilities) {
        if (z && !supportType(str2, cameraCapabilities)) {
            return null;
        }
        char c2 = 65535;
        switch (str2.hashCode()) {
            case -2110473153:
                if (str2.equals("key_live_smooth_strength")) {
                    c2 = 26;
                    break;
                }
                break;
            case -1735290593:
                if (str2.equals("pref_beauty_body_slim_ratio")) {
                    c2 = 19;
                    break;
                }
                break;
            case -1584074159:
                if (str2.equals("pref_beautify_jelly_lips_ratio_key")) {
                    c2 = 15;
                    break;
                }
                break;
            case -1518569458:
                if (str2.equals("pref_beauty_butt_slim_ratio")) {
                    c2 = 23;
                    break;
                }
                break;
            case -1262324777:
                if (str2.equals("pref_beauty_whole_body_slim_ratio")) {
                    c2 = 22;
                    break;
                }
                break;
            case -1110975408:
                if (str2.equals("pref_beautify_smile_ratio_key")) {
                    c2 = '\n';
                    break;
                }
                break;
            case -958841118:
                if (str2.equals("pref_beautify_eyebrow_dye_ratio_key")) {
                    c2 = '\r';
                    break;
                }
                break;
            case -902532899:
                if (str2.equals("key_video_bokeh_blur_null")) {
                    c2 = 27;
                    break;
                }
                break;
            case -897176419:
                if (str2.equals("pref_beautify_slim_nose_ratio_key")) {
                    c2 = 11;
                    break;
                }
                break;
            case -335791057:
                if (str2.equals("pref_beautify_neck_ratio_key")) {
                    c2 = '\t';
                    break;
                }
                break;
            case -310919472:
                if (str2.equals("pref_beautify_lips_ratio_key")) {
                    c2 = 7;
                    break;
                }
                break;
            case -271212966:
                if (str2.equals("pref_beautify_chin_ratio_key")) {
                    c2 = '\b';
                    break;
                }
                break;
            case -146567779:
                if (str2.equals("key_beauty_leg_slim_ratio")) {
                    c2 = 21;
                    break;
                }
                break;
            case -39674120:
                if (str2.equals("pref_beautify_risorius_ratio_key")) {
                    c2 = 5;
                    break;
                }
                break;
            case 175697132:
                if (str2.equals("key_live_shrink_face_ratio")) {
                    c2 = 24;
                    break;
                }
                break;
            case 463390384:
                if (str2.equals("key_video_bokeh_zoom_out_ratio")) {
                    c2 = 30;
                    break;
                }
                break;
            case 678742590:
                if (str2.equals("key_video_bokeh_color_point_ratio")) {
                    c2 = ' ';
                    break;
                }
                break;
            case 894643879:
                if (str2.equals("pref_beautify_slim_face_ratio_key")) {
                    c2 = 1;
                    break;
                }
                break;
            case 917154040:
                if (str2.equals("pref_beautify_skin_smooth_ratio_key")) {
                    c2 = 3;
                    break;
                }
                break;
            case 942822477:
                if (str2.equals("pref_eye_light_type_key")) {
                    c2 = 17;
                    break;
                }
                break;
            case 987275939:
                if (str2.equals("pref_beautify_nose_ratio_key")) {
                    c2 = 4;
                    break;
                }
                break;
            case 1048736236:
                if (str2.equals("pref_beautify_enlarge_eye_ratio_key")) {
                    c2 = 2;
                    break;
                }
                break;
            case 1057690737:
                if (str2.equals("pref_beautify_skin_color_ratio_key")) {
                    c2 = 0;
                    break;
                }
                break;
            case 1414809360:
                if (str2.equals("key_video_bokeh_spin_ratio")) {
                    c2 = 29;
                    break;
                }
                break;
            case 1427427765:
                if (str2.equals("pref_beautify_blusher_ratio_key")) {
                    c2 = 16;
                    break;
                }
                break;
            case 1440042841:
                if (str2.equals("pref_beautify_makeup_ratio_key")) {
                    c2 = '\f';
                    break;
                }
                break;
            case 1644342645:
                if (str2.equals("pref_beautify_pupil_line_ratio_key")) {
                    c2 = 14;
                    break;
                }
                break;
            case 1659922406:
                if (str2.equals("pref_beautify_hairline_ratio_key")) {
                    c2 = 6;
                    break;
                }
                break;
            case 1709402593:
                if (str2.equals("pref_beauty_shoulder_slim_ratio")) {
                    c2 = 20;
                    break;
                }
                break;
            case 1742391003:
                if (str2.equals("key_video_bokeh_zoom_in_ratio")) {
                    c2 = 31;
                    break;
                }
                break;
            case 1771202045:
                if (str2.equals("key_live_enlarge_eye_ratio")) {
                    c2 = 25;
                    break;
                }
                break;
            case 1945143841:
                if (str2.equals("pref_beauty_head_slim_ratio")) {
                    c2 = 18;
                    break;
                }
                break;
            case 2089357173:
                if (str2.equals("key_video_bokeh_blur_ratio")) {
                    c2 = 28;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                return new TypeItem((int) R.drawable.ic_vector_beauty_smile, (int) R.string.edit_skin_white, str, str2);
            case 1:
                return new TypeItem((int) R.drawable.ic_vector_face_slender_n, (int) R.string.edit_face_slender, str, str2);
            case 2:
                return new TypeItem((int) R.drawable.icon_eye_large_n, (int) R.string.edit_eye_large, str, str2);
            case 3:
                return new TypeItem((int) R.drawable.icon_smooth_n, (int) R.string.edit_skin_smooth, str, str2);
            case 4:
                return new TypeItem((int) R.drawable.icon_nose_n, (int) R.string.edit_nose, str, str2);
            case 5:
                return new TypeItem((int) R.drawable.icon_risorius_n, (int) R.string.edit_risorius, str, str2);
            case 6:
                return new TypeItem((int) R.drawable.icon_hair_n, (int) R.string.edit_hairline, str, str2);
            case 7:
                return new TypeItem((int) R.drawable.ic_vector_lips_n, (int) R.string.edit_lips, str, str2);
            case '\b':
                return new TypeItem((int) R.drawable.icon_chin_n, (int) R.string.edit_chin, str, str2);
            case '\t':
                return new TypeItem((int) R.drawable.icon_neck_n, (int) R.string.edit_neck, str, str2);
            case '\n':
                return new TypeItem((int) R.drawable.ic_vector_beauty_smile, (int) R.string.edit_smile, str, str2);
            case 11:
                return new TypeItem((int) R.drawable.icon_nose_n, (int) R.string.edit_slim_nose, str, str2);
            case '\f':
                return new TypeItem((int) R.drawable.ic_vector_makeup, (int) R.string.edit_makeup, str, str2);
            case '\r':
                return new TypeItem((int) R.drawable.icon_eyebrow_dye_n, (int) R.string.edit_eyebrow_dye, str, str2);
            case 14:
                return new TypeItem((int) R.drawable.icon_pupil_line_n, (int) R.string.edit_pupil_line, str, str2);
            case 15:
                return new TypeItem((int) R.drawable.ic_vector_jelly_lips_n, (int) R.string.edit_jelly_lips, str, str2);
            case 16:
                return new TypeItem((int) R.drawable.icon_blusher_n, (int) R.string.edit_blusher, str, str2);
            case 17:
                return new TypeItem((int) R.drawable.icon_eye_light, (int) R.string.eye_light, str, str2);
            case 18:
                return new TypeItem((int) R.drawable.icon_head_slim, (int) R.string.beauty_body_head, str, str2);
            case 19:
                return new TypeItem((int) R.drawable.icon_body_slim, (int) R.string.beauty_body_body, str, str2);
            case 20:
                return new TypeItem((int) R.drawable.icon_shoulder_slim, (int) R.string.beauty_body_shoulder, str, str2);
            case 21:
                return new TypeItem((int) R.drawable.icon_leg_slim, (int) R.string.beauty_body_legged, str, str2);
            case 22:
                return new TypeItem((int) R.drawable.icon_whole_body_slim, (int) R.string.beauty_body_whole, str, str2);
            case 23:
                return null;
            case 24:
                return new TypeItem((int) R.drawable.ic_vector_face_slender_n, (int) R.string.edit_face_slender, str, str2);
            case 25:
                return new TypeItem((int) R.drawable.icon_eye_large_n, (int) R.string.edit_eye_large, str, str2);
            case 26:
                return new TypeItem((int) R.drawable.icon_smooth_n, (int) R.string.edit_skin_smooth, str, str2);
            case 27:
                return new TypeItem((int) R.drawable.ic_lighting_none, (int) R.string.lighting_pattern_null, str, str2);
            case 28:
                return new TypeItem((int) R.drawable.icon_video_bokeh_blur, (int) R.string.video_bokeh_blur, str, str2);
            case 29:
                return new TypeItem((int) R.drawable.icon_video_bokeh_spin, (int) R.string.video_bokeh_spin, str, str2);
            case 30:
                return new TypeItem((int) R.drawable.icon_video_bokeh_zoom_out, (int) R.string.video_bokeh_zoom_out, str, str2);
            case 31:
                return new TypeItem((int) R.drawable.icon_video_bokeh_zoom_in, (int) R.string.video_bokeh_zoom_in, str, str2);
            case ' ':
                return new TypeItem((int) R.drawable.icon_video_bokeh_color_point, (int) R.string.video_bokeh_color_point, str, str2);
            default:
                throw new RuntimeException("to be continued createTypeItem = " + str2);
        }
    }

    private void initAdvanceItems(List<TypeItem> list, int i, CameraCapabilities cameraCapabilities, ComponentRunningShine componentRunningShine) {
        initItemsBySupportedTypes("3", BeautyConstant.BEAUTY_CATEGORY_FRONT_ADVANCE, list, true, cameraCapabilities);
    }

    private void initFigureItems(List<TypeItem> list, int i, CameraCapabilities cameraCapabilities, ComponentRunningShine componentRunningShine) {
        TypeItem createTypeItem;
        initItemsBySupportedTypes("6", BeautyConstant.BEAUTY_CATEGORY_BACK_FIGURE, list, true, cameraCapabilities);
        if (componentRunningShine.isSmoothDependBeautyVersion() && (createTypeItem = createTypeItem("4", "pref_beautify_skin_smooth_ratio_key", true, cameraCapabilities)) != null) {
            list.add(0, createTypeItem);
        }
    }

    private void initItemsBySupportedTypes(@ComponentRunningShine.ShineType String str, String[] strArr, List<TypeItem> list, boolean z, CameraCapabilities cameraCapabilities) {
        for (String str2 : strArr) {
            TypeItem createTypeItem = createTypeItem(str, str2, z, cameraCapabilities);
            if (createTypeItem != null) {
                list.add(createTypeItem);
            }
        }
    }

    private void initLiveBeautyItems(List<TypeItem> list, int i, CameraCapabilities cameraCapabilities, ComponentRunningShine componentRunningShine) {
        initItemsBySupportedTypes("11", BeautyConstant.BEAUTY_CATEGORY_LIVE, list, false, cameraCapabilities);
    }

    private void initMakeUpItems(List<TypeItem> list, int i, CameraCapabilities cameraCapabilities, ComponentRunningShine componentRunningShine) {
        String[] strArr = BeautyConstant.BEAUTY_CATEGORY_FRONT_MAKEUP;
        for (String str : strArr) {
            TypeItem typeItem = null;
            char c2 = 65535;
            if (str.hashCode() == 942822477 && str.equals("pref_eye_light_type_key")) {
                c2 = 0;
            }
            if (c2 != 0) {
                typeItem = createTypeItem("5", str, true, cameraCapabilities);
            } else if (i == 1 && DataRepository.dataItemFeature().tl()) {
                typeItem = createTypeItem("5", str, false, cameraCapabilities);
            }
            if (typeItem != null) {
                list.add(typeItem);
            }
        }
    }

    private void initMiLiveItems(List<TypeItem> list, int i, CameraCapabilities cameraCapabilities, ComponentRunningShine componentRunningShine) {
        TypeItem createTypeItem;
        initItemsBySupportedTypes("15", BeautyConstant.BEAUTY_CATEGORY_MI_LIVE, list, true, cameraCapabilities);
        if (componentRunningShine.isSmoothDependBeautyVersion() && (createTypeItem = createTypeItem("15", "pref_beautify_skin_smooth_ratio_key", true, cameraCapabilities)) != null) {
            list.add(0, createTypeItem);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0052  */
    private void initReModelingItems(List<TypeItem> list, int i, CameraCapabilities cameraCapabilities, ComponentRunningShine componentRunningShine) {
        TypeItem createTypeItem;
        boolean z;
        initItemsBySupportedTypes("4", BeautyConstant.BEAUTY_CATEGORY_FRONT_REMODELING, list, true, cameraCapabilities);
        int i2 = 0;
        if (componentRunningShine.isSmoothDependBeautyVersion()) {
            int size = list.size();
            int i3 = -1;
            int i4 = -1;
            for (int i5 = 0; i5 < size; i5++) {
                String str = list.get(i5).mKeyOrType;
                int hashCode = str.hashCode();
                if (hashCode != -39674120) {
                    if (hashCode == 1659922406 && str.equals("pref_beautify_hairline_ratio_key")) {
                        z = true;
                        if (!z) {
                            i4 = i5;
                        } else if (z) {
                            i3 = i5;
                        }
                    }
                } else if (str.equals("pref_beautify_risorius_ratio_key")) {
                    z = false;
                    if (!z) {
                    }
                }
                z = true;
                if (!z) {
                }
            }
            if (!(i3 == -1 || i4 == -1)) {
                list.remove(i4);
            }
            TypeItem createTypeItem2 = createTypeItem("4", "pref_beautify_skin_smooth_ratio_key", true, cameraCapabilities);
            if (createTypeItem2 != null) {
                list.add(0, createTypeItem2);
            }
        }
        while (true) {
            if (i2 >= list.size()) {
                i2 = -1;
                break;
            } else if (list.get(i2).mKeyOrType == "pref_beautify_risorius_ratio_key") {
                break;
            } else {
                i2++;
            }
        }
        if (i2 != -1) {
            list.remove(i2);
        }
        if (componentRunningShine.supportBeautyMakeUp() && (createTypeItem = createTypeItem("4", "pref_beautify_makeup_ratio_key", true, cameraCapabilities)) != null) {
            list.add(createTypeItem);
        }
    }

    private void initSuperNightBeautyItems(List<TypeItem> list, int i, CameraCapabilities cameraCapabilities, ComponentRunningShine componentRunningShine) {
        initItemsBySupportedTypes("17", BeautyConstant.BEAUTY_CATEGORY_FRONT_SUPER_NIGHT, list, false, cameraCapabilities);
    }

    private void initVideoBokehItems(List<TypeItem> list, int i, CameraCapabilities cameraCapabilities, ComponentRunningShine componentRunningShine) {
        initItemsBySupportedTypes("14", VideoBokehConstant.BEAUTY_CATEGORY_VIDEO_BOKEH, list, false, cameraCapabilities);
    }

    private boolean supportType(String str, CameraCapabilities cameraCapabilities) {
        if (str == null) {
            return false;
        }
        return cameraCapabilities.isSupportBeautyType(str);
    }

    public List<TypeItem> initAndGetSupportItems(int i, CameraCapabilities cameraCapabilities, @ComponentRunningShine.ShineType String str) {
        ComponentRunningShine componentRunningShine = (ComponentRunningShine) this.mComponentData;
        ArrayList arrayList = new ArrayList();
        char c2 = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 1568) {
            if (hashCode != 1574) {
                if (hashCode != 1571) {
                    if (hashCode != 1572) {
                        switch (hashCode) {
                            case 51:
                                if (str.equals("3")) {
                                    c2 = 0;
                                    break;
                                }
                                break;
                            case 52:
                                if (str.equals("4")) {
                                    c2 = 1;
                                    break;
                                }
                                break;
                            case 53:
                                if (str.equals("5")) {
                                    c2 = 3;
                                    break;
                                }
                                break;
                            case 54:
                                if (str.equals("6")) {
                                    c2 = 4;
                                    break;
                                }
                                break;
                        }
                    } else if (str.equals("15")) {
                        c2 = 2;
                    }
                } else if (str.equals("14")) {
                    c2 = 6;
                }
            } else if (str.equals("17")) {
                c2 = 7;
            }
        } else if (str.equals("11")) {
            c2 = 5;
        }
        switch (c2) {
            case 0:
                initAdvanceItems(arrayList, i, cameraCapabilities, componentRunningShine);
                break;
            case 1:
                initReModelingItems(arrayList, i, cameraCapabilities, componentRunningShine);
                break;
            case 2:
                initMiLiveItems(arrayList, i, cameraCapabilities, componentRunningShine);
                break;
            case 3:
                initMakeUpItems(arrayList, i, cameraCapabilities, componentRunningShine);
                break;
            case 4:
                initFigureItems(arrayList, i, cameraCapabilities, componentRunningShine);
                break;
            case 5:
                initLiveBeautyItems(arrayList, i, cameraCapabilities, componentRunningShine);
                break;
            case 6:
                initVideoBokehItems(arrayList, i, cameraCapabilities, componentRunningShine);
                break;
            case 7:
                initSuperNightBeautyItems(arrayList, i, cameraCapabilities, componentRunningShine);
                break;
            default:
                throw new RuntimeException("to be continuedshineType" + str);
        }
        return arrayList;
    }
}
