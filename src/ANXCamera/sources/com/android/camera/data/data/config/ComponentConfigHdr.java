package com.android.camera.data.data.config;

import android.text.TextUtils;
import b.c.a.c;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentConfigHdr extends ComponentData {
    public static final int HDR_UI_STATUS_AUTO = 2;
    public static final int HDR_UI_STATUS_OFF = 0;
    public static final int HDR_UI_STATUS_ON = 1;
    public static final String HDR_VALUE_AUTO = "auto";
    public static final String HDR_VALUE_LIVE = "live";
    public static final String HDR_VALUE_NORMAL = "normal";
    public static final String HDR_VALUE_OFF = "off";
    public static final String HDR_VALUE_ON = "on";
    private boolean mAutoSupported;
    private boolean mIsClosed;
    private boolean mSupportHdrCheckerWhenOn;

    public ComponentConfigHdr(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
        this.mItems = new ArrayList();
        this.mItems.add(new ComponentDataItem(getConfigHDROffRes()[0], getConfigHDROffRes()[1], (int) R.string.pref_camera_hdr_entry_off, "off"));
    }

    private int[] getConfigHDRAutoRes() {
        return new int[]{R.drawable.ic_new_config_hdr_auto, R.drawable.ic_new_config_hdr_auto, R.drawable.ic_new_config_hdr_auto_label};
    }

    private int[] getConfigHDRLiveRes() {
        return new int[]{R.drawable.ic_new_config_hdr_live, R.drawable.ic_new_config_hdr_live};
    }

    private int[] getConfigHDRNormalRes() {
        return new int[]{R.drawable.ic_new_config_hdr_normal, R.drawable.ic_new_config_hdr_normal};
    }

    private int[] getConfigHDROffRes() {
        return new int[]{R.drawable.ic_new_config_hdr_off, R.drawable.ic_new_config_hdr_off};
    }

    public static int getHdrUIStatus(String str) {
        if ("on".equals(str) || "normal".equals(str)) {
            return 1;
        }
        return "auto".equals(str) ? 2 : 0;
    }

    private void initForHDR(List<ComponentDataItem> list, CameraCapabilities cameraCapabilities) {
        list.add(new ComponentDataItem(getConfigHDROffRes()[0], (int) R.drawable.ic_new_config_hdr_off_shadow, getConfigHDROffRes()[0], (int) R.string.pref_camera_hdr_entry_off, "off"));
        if (cameraCapabilities.isSupportAutoHdr()) {
            this.mAutoSupported = true;
            list.add(new ComponentDataItem(getConfigHDRAutoRes()[0], R.drawable.ic_new_config_hdr_auto_shadow, getConfigHDRAutoRes()[0], getConfigHDRAutoRes()[0], getConfigHDRAutoRes()[2], R.drawable.ic_new_config_hdr_auto_label_shadow, R.string.pref_camera_hdr_entry_auto, "auto"));
        }
        if (!c.Gn()) {
            list.add(new ComponentDataItem(getConfigHDRNormalRes()[0], (int) R.drawable.ic_new_config_hdr_normal_shadow, getConfigHDRNormalRes()[0], (int) R.string.pref_simple_hdr_entry_on, "normal"));
        } else {
            list.add(new ComponentDataItem(getConfigHDRNormalRes()[0], (int) R.drawable.ic_new_config_hdr_normal_shadow, getConfigHDRNormalRes()[0], (int) R.string.pref_camera_hdr_entry_normal, "normal"));
            list.add(new ComponentDataItem(getConfigHDRLiveRes()[0], getConfigHDRLiveRes()[0], (int) R.string.pref_camera_hdr_entry_live, "live"));
        }
        if (cameraCapabilities.isSupportHdrCheckerStatus()) {
            this.mSupportHdrCheckerWhenOn = true;
        }
    }

    public void clearClosed() {
        this.mIsClosed = false;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        return (!isClosed() && !isEmpty()) ? (171 != i || !CameraSettings.isBackCamera() || !DataRepository.dataItemFeature().Dl()) ? super.getComponentValue(i) : "auto" : "off";
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        if (isClosed() || isEmpty()) {
            return "off";
        }
        if (CameraSettings.isFrontCamera()) {
            return (162 != i || !DataRepository.dataItemFeature().Kk().booleanValue()) ? "off" : "normal";
        }
        if (171 == i && CameraSettings.isBackCamera() && DataRepository.dataItemFeature().Dl()) {
            return "auto";
        }
        String Fh = DataRepository.dataItemFeature().Fh();
        if (!TextUtils.isEmpty(Fh)) {
            char c2 = 65535;
            int hashCode = Fh.hashCode();
            if (hashCode != 3551) {
                if (hashCode != 109935) {
                    if (hashCode == 3005871 && Fh.equals("auto")) {
                        c2 = 0;
                    }
                } else if (Fh.equals("off")) {
                    c2 = 2;
                }
            } else if (Fh.equals("on")) {
                c2 = 1;
            }
            if (c2 == 0) {
                return this.mAutoSupported ? "auto" : "off";
            }
            if (c2 == 1) {
                return "on";
            }
            if (c2 == 2) {
                return "off";
            }
        }
        return this.mAutoSupported ? "auto" : "off";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_hdr_title;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        if (i != 160) {
            return (i == 162 || i == 169) ? CameraSettings.KEY_VIDEO_HDR : i != 171 ? i != 180 ? CameraSettings.KEY_CAMERA_HDR : CameraSettings.KEY_VIDEO_HDR : CameraSettings.KEY_PORTRAIT_HDR;
        }
        throw new RuntimeException("unspecified hdr");
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getPersistValue(int i) {
        return super.getComponentValue(i);
    }

    public int getValueSelectedDrawableIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("off".equals(componentValue)) {
            return getConfigHDROffRes()[1];
        }
        if ("auto".equals(componentValue)) {
            return getConfigHDRAutoRes()[1];
        }
        if ("normal".equals(componentValue)) {
            return getConfigHDRNormalRes()[1];
        }
        if ("live".equals(componentValue)) {
            return getConfigHDRLiveRes()[1];
        }
        if ("on".equals(componentValue)) {
            return getConfigHDRNormalRes()[1];
        }
        return -1;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getValueSelectedShadowDrawable(int i) {
        String componentValue = getComponentValue(i);
        if ("off".equals(componentValue)) {
            return R.drawable.ic_new_config_hdr_off_shadow;
        }
        if ("auto".equals(componentValue)) {
            return R.drawable.ic_new_config_hdr_auto_shadow;
        }
        if ("normal".equals(componentValue)) {
            return R.drawable.ic_new_config_hdr_normal_shadow;
        }
        if ("live".equals(componentValue)) {
            return getConfigHDRLiveRes()[1];
        }
        if ("on".equals(componentValue)) {
            return R.drawable.ic_new_config_hdr_normal_shadow;
        }
        return -1;
    }

    public int getValueSelectedStringIdIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("off".equals(componentValue)) {
            return R.string.accessibility_hdr_off;
        }
        if ("auto".equals(componentValue)) {
            return R.string.accessibility_hdr_auto;
        }
        if ("normal".equals(componentValue)) {
            return R.string.accessibility_hdr_on;
        }
        if ("live".equals(componentValue)) {
            return R.string.accessibility_hdr_live;
        }
        if ("on".equals(componentValue)) {
            return R.string.accessibility_hdr_on;
        }
        return -1;
    }

    public boolean isClosed() {
        return this.mIsClosed;
    }

    public boolean isHdrOnWithChecker(String str) {
        if ("on".equals(str) || "normal".equals(str)) {
            return this.mSupportHdrCheckerWhenOn;
        }
        return false;
    }

    public boolean isSupportAutoHdr() {
        return this.mAutoSupported;
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities, int i3) {
        ArrayList arrayList = new ArrayList();
        this.mAutoSupported = false;
        this.mSupportHdrCheckerWhenOn = false;
        if (cameraCapabilities.isSupportHdr()) {
            if (i != 165) {
                if (!(i == 167 || i == 169)) {
                    if (i != 171) {
                        if (i != 180) {
                            if (i != 205) {
                                switch (i) {
                                    case 162:
                                        if (cameraCapabilities.isSupportVideoHdr()) {
                                            arrayList.add(new ComponentDataItem(getConfigHDROffRes()[0], (int) R.drawable.ic_new_config_hdr_off_shadow, getConfigHDROffRes()[0], (int) R.string.pref_camera_hdr_entry_off, "off"));
                                            arrayList.add(new ComponentDataItem(getConfigHDRNormalRes()[0], (int) R.drawable.ic_new_config_hdr_normal_shadow, getConfigHDRNormalRes()[0], (int) R.string.pref_simple_hdr_entry_on, "normal"));
                                            break;
                                        }
                                        break;
                                }
                            }
                        } else {
                            initForHDR(arrayList, cameraCapabilities);
                        }
                    } else if (DataRepository.dataItemFeature().Dl() && CameraSettings.isBackCamera()) {
                        arrayList.add(new ComponentDataItem(getConfigHDROffRes()[0], (int) R.drawable.ic_new_config_hdr_off_shadow, getConfigHDROffRes()[0], (int) R.string.pref_camera_hdr_entry_off, "off"));
                        if (cameraCapabilities.isSupportAutoHdr()) {
                            this.mAutoSupported = true;
                            arrayList.add(new ComponentDataItem(getConfigHDRAutoRes()[0], (int) R.drawable.ic_new_config_hdr_auto_shadow, getConfigHDRAutoRes()[0], (int) R.string.pref_camera_hdr_entry_auto, "auto"));
                        }
                    }
                }
                this.mItems = Collections.unmodifiableList(arrayList);
            }
            if (!cameraCapabilities.isSupportLightTripartite() || i3 == 0) {
                initForHDR(arrayList, cameraCapabilities);
            }
            this.mItems = Collections.unmodifiableList(arrayList);
        }
    }

    public void setClosed(boolean z) {
        this.mIsClosed = z;
    }

    @Override // com.android.camera.data.data.ComponentData
    public void setComponentValue(int i, String str) {
        setClosed(false);
        super.setComponentValue(i, str);
    }
}
