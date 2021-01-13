package com.android.camera.data.data.runing;

import androidx.annotation.NonNull;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.DataItemBase;
import com.android.camera.dualvideo.CameraIDManager;
import com.android.camera.dualvideo.UserSelectData;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ComponentRunningDualVideo extends ComponentData {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String TAG = "ComponentRunningDualVideo";
    private ConcurrentHashMap<Integer, Integer> mIds;
    private boolean mShouldDraw6Patch;
    private ArrayList<UserSelectData> mUserSelectDataList = new ArrayList<>();

    public <D extends DataItemBase> ComponentRunningDualVideo(D d2) {
        super(d2);
        setIds(Camera2DataContainer.getInstance().getDefaultDualVideoCameraIds());
    }

    private void initUserSelectionDataIfNeed() {
        if (!this.mUserSelectDataList.isEmpty()) {
            return;
        }
        if (DataRepository.dataItemFeature().tj()) {
            this.mShouldDraw6Patch = true;
            this.mUserSelectDataList.add(new UserSelectData(1, getIds().get(100).intValue(), 20, 12));
            this.mUserSelectDataList.add(new UserSelectData(2, getIds().get(101).intValue(), 23, 11));
            return;
        }
        this.mShouldDraw6Patch = false;
        this.mUserSelectDataList.add(new UserSelectData(1, getIds().get(100).intValue(), 21, 11));
        this.mUserSelectDataList.add(new UserSelectData(2, getIds().get(101).intValue(), 25, 12));
    }

    private void switchMainSubId(ConcurrentHashMap concurrentHashMap) {
        int intValue = ((Integer) concurrentHashMap.get(100)).intValue();
        concurrentHashMap.put(100, Integer.valueOf(((Integer) concurrentHashMap.get(101)).intValue()));
        concurrentHashMap.put(101, Integer.valueOf(intValue));
    }

    private void updateSelectData() {
        initUserSelectionDataIfNeed();
        this.mUserSelectDataList.get(0).mCameraID = getIds().get(100).intValue();
        this.mUserSelectDataList.get(1).mCameraID = getIds().get(101).intValue();
        Log.d(TAG, "initIdComposeMapbyIdChanged: " + Arrays.deepToString(this.mUserSelectDataList.toArray()));
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    public ConcurrentHashMap<Integer, Integer> getIds() {
        return this.mIds;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return null;
    }

    public ArrayList<UserSelectData> getSelectedData() {
        return this.mUserSelectDataList;
    }

    public ArrayList<UserSelectData> getmSelectedData() {
        return this.mUserSelectDataList;
    }

    public boolean ismShouldDraw6Patch() {
        return this.mShouldDraw6Patch;
    }

    public void resetIds() {
        setIds(Camera2DataContainer.getInstance().getDefaultDualVideoCameraIds());
    }

    public void setIds(ConcurrentHashMap concurrentHashMap) {
        if (Camera2DataContainer.getInstance().isFrontCameraId(((Integer) concurrentHashMap.get(101)).intValue())) {
            this.mIds = concurrentHashMap;
        } else if (Camera2DataContainer.getInstance().isFrontCameraId(((Integer) concurrentHashMap.get(100)).intValue())) {
            switchMainSubId(concurrentHashMap);
            this.mIds = concurrentHashMap;
        } else {
            CameraIDManager cameraIDManager = new CameraIDManager();
            if (cameraIDManager.getZoomById(((Integer) concurrentHashMap.get(101)).intValue()) > cameraIDManager.getZoomById(((Integer) concurrentHashMap.get(100)).intValue())) {
                switchMainSubId(concurrentHashMap);
            }
            this.mIds = concurrentHashMap;
        }
        updateSelectData();
        Log.d(TAG, "setIds:" + this.mIds.toString());
    }

    public void setSelectedData(ArrayList<UserSelectData> arrayList) {
        Log.d(TAG, "setSelectedItems: " + arrayList.toString());
        this.mUserSelectDataList = arrayList;
    }

    public void setmShouldDraw6Patch(boolean z) {
        this.mShouldDraw6Patch = z;
    }
}
