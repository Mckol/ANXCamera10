package com.android.camera.data.data.config;

import android.text.TextUtils;
import b.c.a.c;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentManuallyWB extends ComponentData {
    public static final String MANUAL_WHITEBALANCE_VALUE = "pref_qc_manual_whitebalance_k_value_key";

    public ComponentManuallyWB(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    private void resetCustomWB() {
        this.mParentDataItem.editor().remove("pref_qc_manual_whitebalance_k_value_key").apply();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.data.data.ComponentData
    public boolean checkValueValid(int i, String str) {
        for (ComponentDataItem componentDataItem : getItems()) {
            if (TextUtils.equals(str, componentDataItem.mValue)) {
                return true;
            }
        }
        return false;
    }

    public int getCustomWB() {
        return this.mParentDataItem.getInt("pref_qc_manual_whitebalance_k_value_key", CameraAppImpl.getAndroidContext().getResources().getInteger(R.integer.default_manual_whitebalance_value));
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return "1";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_whitebalance_title_abbr;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return i != 167 ? CameraSettings.KEY_PRO_VIDEO_WHITE_BALANCE : CameraSettings.KEY_WHITE_BALANCE;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getValueDisplayString(int i) {
        if (getComponentValue(i).equals("1")) {
            return R.string.pref_camera_exposuretime_entry_auto_abbr;
        }
        return -1;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x007d A[RETURN] */
    @Override // com.android.camera.data.data.ComponentData
    public int getValueSelectedDrawable(int i) {
        boolean z;
        String componentValue = getComponentValue(i);
        int hashCode = componentValue.hashCode();
        if (hashCode != -1081415738) {
            if (hashCode != 53) {
                if (hashCode != 54) {
                    switch (hashCode) {
                        case 49:
                            if (componentValue.equals("1")) {
                                z = false;
                                break;
                            }
                            break;
                        case 50:
                            if (componentValue.equals("2")) {
                                z = true;
                                break;
                            }
                            break;
                        case 51:
                            if (componentValue.equals("3")) {
                                z = true;
                                break;
                            }
                            break;
                    }
                    if (!z) {
                        return R.drawable.ic_manua_auto;
                    }
                    if (z) {
                        return R.drawable.ic_white_balance_incandescent;
                    }
                    if (z) {
                        return R.drawable.ic_white_balance_sunlight;
                    }
                    if (z) {
                        return R.drawable.ic_white_balance_fluorescent;
                    }
                    if (z) {
                        return R.drawable.ic_white_balance_cloudy;
                    }
                    if (!z) {
                        return -1;
                    }
                    return R.drawable.ic_white_balance_manual;
                } else if (componentValue.equals("6")) {
                    z = true;
                    if (!z) {
                    }
                }
            } else if (componentValue.equals("5")) {
                z = true;
                if (!z) {
                }
            }
        } else if (componentValue.equals("manual")) {
            z = true;
            if (!z) {
            }
        }
        z = true;
        if (!z) {
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x007d A[RETURN] */
    @Override // com.android.camera.data.data.ComponentData
    public int getValueSelectedShadowDrawable(int i) {
        boolean z;
        String componentValue = getComponentValue(i);
        int hashCode = componentValue.hashCode();
        if (hashCode != -1081415738) {
            if (hashCode != 53) {
                if (hashCode != 54) {
                    switch (hashCode) {
                        case 49:
                            if (componentValue.equals("1")) {
                                z = false;
                                break;
                            }
                            break;
                        case 50:
                            if (componentValue.equals("2")) {
                                z = true;
                                break;
                            }
                            break;
                        case 51:
                            if (componentValue.equals("3")) {
                                z = true;
                                break;
                            }
                            break;
                    }
                    if (!z) {
                        return R.drawable.ic_manua_auto_shadow;
                    }
                    if (z) {
                        return R.drawable.ic_white_balance_incandescent_shadow;
                    }
                    if (z) {
                        return R.drawable.ic_white_balance_sunlight_shadow;
                    }
                    if (z) {
                        return R.drawable.ic_white_balance_fluorescent_shadow;
                    }
                    if (z) {
                        return R.drawable.ic_white_balance_cloudy_shadow;
                    }
                    if (!z) {
                        return -1;
                    }
                    return R.drawable.ic_white_balance_manual_shadow;
                } else if (componentValue.equals("6")) {
                    z = true;
                    if (!z) {
                    }
                }
            } else if (componentValue.equals("5")) {
                z = true;
                if (!z) {
                }
            }
        } else if (componentValue.equals("manual")) {
            z = true;
            if (!z) {
            }
        }
        z = true;
        if (!z) {
        }
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_manu_auto_normal, (int) R.drawable.ic_manu_auto_normal_shadow, -1, -1, "1"));
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_white_balance_incandescent_normal, (int) R.drawable.ic_white_balance_incandescent_normal_shadow, -1, -1, "2"));
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_white_balance_sunlight_normal, (int) R.drawable.ic_white_balance_sunlight_normal_shadow, -1, -1, "5"));
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_white_balance_fluorescent_normal, (int) R.drawable.ic_white_balance_fluorescent_normal_shadow, -1, -1, "3"));
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_white_balance_cloudy_normal, (int) R.drawable.ic_white_balance_cloudy_normal_shadow, -1, -1, "6"));
        if (c.Mn() && DataRepository.dataItemFeature().sl()) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_white_balance_manual_normal, (int) R.drawable.ic_white_balance_manual_normal_shadow, -1, -1, "manual"));
        }
        this.mItems = Collections.unmodifiableList(arrayList);
    }

    @Override // com.android.camera.data.data.ComponentData
    public void reset(int i) {
        super.reset(i);
        resetCustomWB();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.data.data.ComponentData
    public void resetComponentValue(int i) {
        setComponentValue(i, getDefaultValue(i));
    }

    public void setCustomWB(int i) {
        this.mParentDataItem.editor().putInt("pref_qc_manual_whitebalance_k_value_key", i).apply();
    }
}
