package com.android.camera.backup;

import android.content.Context;
import android.content.SharedPreferences;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import java.util.ArrayList;
import java.util.List;
import miui.cloud.backup.ICloudBackup;
import miui.cloud.backup.data.DataPackage;
import miui.cloud.backup.data.PrefsBackupHelper;

public class CameraSettingsBackupImpl implements ICloudBackup {
    private static final int FRONT_CLOUD_CAMERA_ID = 1;
    private static final PrefsBackupHelper.PrefEntry[] PREF_ENTRIES = CameraBackupSettings.PREF_ENTRIES;
    private static final int REAR_CLOUD_CAMERA_ID = 0;
    private static final String TAG = "CameraSettingsBackup";

    /* access modifiers changed from: package-private */
    public interface BackupRestoreHandler {
        void handle(SharedPreferences sharedPreferences, DataPackage dataPackage, PrefsBackupHelper.PrefEntry[] prefEntryArr);
    }

    private static PrefsBackupHelper.PrefEntry[] addPrefixToEntries(PrefsBackupHelper.PrefEntry[] prefEntryArr, String str) {
        PrefsBackupHelper.PrefEntry[] prefEntryArr2 = new PrefsBackupHelper.PrefEntry[prefEntryArr.length];
        for (int i = 0; i < prefEntryArr.length; i++) {
            PrefsBackupHelper.PrefEntry prefEntry = prefEntryArr[i];
            Class valueClass = prefEntry.getValueClass();
            String str2 = str + "::" + prefEntry.getCloudKey();
            String localKey = prefEntry.getLocalKey();
            Object defaultValue = prefEntry.getDefaultValue();
            PrefsBackupHelper.PrefEntry prefEntry2 = null;
            if (valueClass.equals(Integer.class)) {
                prefEntry2 = defaultValue == null ? PrefsBackupHelper.PrefEntry.createIntEntry(str2, localKey) : PrefsBackupHelper.PrefEntry.createIntEntry(str2, localKey, ((Integer) defaultValue).intValue());
            } else if (valueClass.equals(Boolean.class)) {
                prefEntry2 = defaultValue == null ? PrefsBackupHelper.PrefEntry.createBoolEntry(str2, localKey) : PrefsBackupHelper.PrefEntry.createBoolEntry(str2, localKey, ((Boolean) defaultValue).booleanValue());
            } else if (valueClass.equals(String.class)) {
                prefEntry2 = defaultValue == null ? PrefsBackupHelper.PrefEntry.createStringEntry(str2, localKey) : PrefsBackupHelper.PrefEntry.createStringEntry(str2, localKey, (String) defaultValue);
            } else if (valueClass.equals(Long.class)) {
                prefEntry2 = defaultValue == null ? PrefsBackupHelper.PrefEntry.createLongEntry(str2, localKey) : PrefsBackupHelper.PrefEntry.createLongEntry(str2, localKey, ((Long) defaultValue).longValue());
            }
            prefEntryArr2[i] = prefEntry2;
        }
        return prefEntryArr2;
    }

    private static boolean checkCameraId(int i) {
        if (i < 0) {
            return false;
        }
        if (i < 2) {
            return true;
        }
        throw new IllegalArgumentException("cameraId is invalid: " + i);
    }

    private static List<Integer> getAvailableCameraIds() {
        ArrayList arrayList = new ArrayList();
        if (checkCameraId(0)) {
            arrayList.add(0);
        }
        if (checkCameraId(1)) {
            arrayList.add(1);
        }
        return arrayList;
    }

    private static String getCloudPrefix(int i, int i2) {
        if (checkCameraId(i)) {
            if (i == 0) {
                i = 0;
            } else if (i == 1) {
                i = 1;
            }
        }
        return "camera_settings_simple_mode_local_" + DataItemConfig.provideLocalId(i, i2);
    }

    private static String getCloudPrefixByCameraIdAndMode(int i, int i2) {
        if (checkCameraId(i)) {
            if (i == 0) {
                i = 0;
            } else if (i == 1) {
                i = 1;
            }
        }
        return "camera_settings_simple_mode_local_" + BaseModule.getPreferencesLocalId(i, i2);
    }

    private static String getSharedPreferencesName(int i, int i2) {
        return "camera_settings_simple_mode_local_" + DataItemConfig.provideLocalId(i, i2);
    }

    private void handleBackupOrRestore(Context context, DataPackage dataPackage, BackupRestoreHandler backupRestoreHandler) {
        int[] iArr = {0, 1};
        List<Integer> availableCameraIds = getAvailableCameraIds();
        for (int i : iArr) {
            for (Integer num : availableCameraIds) {
                int intValue = num.intValue();
                SharedPreferences sharedPreferences = context.getSharedPreferences(getSharedPreferencesName(intValue, i), 0);
                if (sharedPreferences != null) {
                    backupRestoreHandler.handle(sharedPreferences, dataPackage, addPrefixToEntries(PREF_ENTRIES, getCloudPrefix(intValue, i)));
                }
            }
        }
        backupRestoreHandler.handle(context.getSharedPreferences("camera_settings_global", 0), dataPackage, addPrefixToEntries(PREF_ENTRIES, "camera_settings_global"));
    }

    private void restoreFromVersion1(Context context, DataPackage dataPackage) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("camera_settings_global", 0);
        int i = 2;
        int[] iArr = {0, 1};
        List<Integer> availableCameraIds = getAvailableCameraIds();
        int length = iArr.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = iArr[i2];
            for (Integer num : availableCameraIds) {
                int intValue = num.intValue();
                SharedPreferences sharedPreferences2 = context.getSharedPreferences(getSharedPreferencesName(intValue, i3), 0);
                if (sharedPreferences2 != null) {
                    PrefsBackupHelper.PrefEntry[] addPrefixToEntries = addPrefixToEntries(PREF_ENTRIES, getCloudPrefixByCameraIdAndMode(intValue, i3 == 0 ? 0 : i));
                    CameraBackupHelper.restoreSettings(sharedPreferences2, dataPackage, addPrefixToEntries, false);
                    if (i3 == 0 && intValue == 0) {
                        CameraBackupHelper.restoreSettings(sharedPreferences, dataPackage, addPrefixToEntries, true);
                    }
                }
                i = 2;
            }
            i2++;
            i = 2;
        }
        CameraBackupHelper.restoreSettings(sharedPreferences, dataPackage, addPrefixToEntries(PREF_ENTRIES, "camera_settings_global"), true);
    }

    private void restoreFromVersion3(Context context, DataPackage dataPackage) {
        int[] iArr = {0, 1};
        List<Integer> availableCameraIds = getAvailableCameraIds();
        for (int i : iArr) {
            for (Integer num : availableCameraIds) {
                int intValue = num.intValue();
                SharedPreferences sharedPreferences = context.getSharedPreferences(getSharedPreferencesName(intValue, i), 0);
                if (sharedPreferences != null) {
                    CameraBackupHelper.restoreSettings(sharedPreferences, dataPackage, addPrefixToEntries(PREF_ENTRIES, getCloudPrefix(intValue, i)), false);
                }
            }
        }
        CameraBackupHelper.restoreSettings(context.getSharedPreferences("camera_settings_global", 0), dataPackage, addPrefixToEntries(PREF_ENTRIES, "camera_settings_global"), true);
    }

    public int getCurrentVersion(Context context) {
        return 4;
    }

    public void onBackupSettings(Context context, DataPackage dataPackage) {
        Log.d(TAG, "backup version " + getCurrentVersion(context));
        handleBackupOrRestore(context, dataPackage, new BackupRestoreHandler() {
            /* class com.android.camera.backup.CameraSettingsBackupImpl.AnonymousClass1 */

            @Override // com.android.camera.backup.CameraSettingsBackupImpl.BackupRestoreHandler
            public void handle(SharedPreferences sharedPreferences, DataPackage dataPackage, PrefsBackupHelper.PrefEntry[] prefEntryArr) {
                PrefsBackupHelper.backup(sharedPreferences, dataPackage, prefEntryArr);
            }
        });
    }

    public void onRestoreSettings(Context context, DataPackage dataPackage, int i) {
        int currentVersion = getCurrentVersion(context);
        if (i > currentVersion) {
            Log.w(TAG, "skip restore due to cloud version " + i + " is higher than local version " + currentVersion);
            return;
        }
        Log.d(TAG, "restore version " + i);
        if (4 <= i) {
            handleBackupOrRestore(context, dataPackage, new BackupRestoreHandler() {
                /* class com.android.camera.backup.CameraSettingsBackupImpl.AnonymousClass2 */

                @Override // com.android.camera.backup.CameraSettingsBackupImpl.BackupRestoreHandler
                public void handle(SharedPreferences sharedPreferences, DataPackage dataPackage, PrefsBackupHelper.PrefEntry[] prefEntryArr) {
                    PrefsBackupHelper.restore(sharedPreferences, dataPackage, prefEntryArr);
                }
            });
        } else if (3 == i) {
            restoreFromVersion3(context, dataPackage);
        } else if (1 == i) {
            restoreFromVersion1(context, dataPackage);
        }
    }
}
