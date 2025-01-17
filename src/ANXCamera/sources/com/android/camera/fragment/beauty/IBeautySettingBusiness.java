package com.android.camera.fragment.beauty;

import com.android.camera.data.data.TypeItem;
import com.android.camera.data.data.runing.ComponentRunningShine;
import java.util.List;

public interface IBeautySettingBusiness extends IBeautyBusiness {
    void clearBeauty();

    int getDefaultProgressByCurrentItem();

    int getProgressByCurrentItem();

    List<TypeItem> getSupportedTypeArray(@ComponentRunningShine.ShineType String str);

    void onStateChanged();

    void resetBeauty();

    void setCurrentType(String str);

    void setProgressForCurrentItem(int i);

    void updateExtraTable();
}
