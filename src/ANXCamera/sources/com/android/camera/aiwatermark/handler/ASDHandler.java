package com.android.camera.aiwatermark.handler;

import android.content.Context;
import android.text.TextUtils;
import com.android.camera.aiwatermark.algo.ASDEngine;
import com.android.camera.aiwatermark.data.ASDWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.aiwatermark.lisenter.IASDListener;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class ASDHandler extends AbstractHandler {
    private static final String TAG = "ASDHandler";
    private IASDListener mASDListener = null;
    protected Context mContext = null;
    private ArrayList<WatermarkItem> mList = new ArrayList<>();
    private int mSpots = 0;

    public ASDHandler(boolean z, Context context) {
        super(z);
        this.mContext = context;
        this.mData = new ASDWatermark();
    }

    private IASDListener createASDListner() {
        return new IASDListener() {
            /* class com.android.camera.aiwatermark.handler.ASDHandler.AnonymousClass1 */

            @Override // com.android.camera.aiwatermark.lisenter.IASDListener
            public void onASDChange(int i) {
                String str = ASDHandler.TAG;
                Log.d(str, "onASDChange spots = " + i);
                boolean z = (ASDEngine.spots2ASDKey(ASDHandler.this.mSpots) == WatermarkConstant.ASD_NEGATIVE && ASDEngine.spots2ASDKey(i) == WatermarkConstant.ASD_NEGATIVE) ? false : true;
                ASDHandler.this.mSpots = i;
                if (z) {
                    WatermarkItem findWatermark = ASDHandler.this.findWatermark();
                    String str2 = ASDHandler.TAG;
                    Log.d(str2, "item=" + findWatermark);
                    if (findWatermark != null) {
                        ASDHandler.this.updateWatermark(findWatermark);
                        return;
                    }
                    ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                    if (configChanges != null) {
                        configChanges.findBestWatermarkItem(89);
                    }
                }
            }
        };
    }

    private void registerASDListener(IASDListener iASDListener) {
        ModeProtocol.AIWatermarkDetect aIWatermarkDetect = (ModeProtocol.AIWatermarkDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(254);
        if (aIWatermarkDetect != null) {
            aIWatermarkDetect.setListener(iASDListener);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateWatermark(WatermarkItem watermarkItem) {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol != null) {
            mainContentProtocol.updateWatermarkSample(watermarkItem);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.AbstractHandler
    public WatermarkItem findWatermark() {
        String str = TAG;
        Log.d(str, "ASDHandler findWatermark mASDListener = " + this.mASDListener);
        DataRepository.dataItemRunning().getComponentRunningAIWatermark().setIWatermarkEnable(true);
        if (this.mASDListener == null) {
            this.mASDListener = createASDListner();
            registerASDListener(this.mASDListener);
        }
        if (this.mList.size() == 0) {
            this.mList = getASDWatermarkList();
        }
        String str2 = TAG;
        Log.d(str2, "findWatermark mList.size() = " + this.mList.size());
        String spots2ASDKey = ASDEngine.spots2ASDKey(this.mSpots);
        String str3 = TAG;
        Log.d(str3, "key = " + spots2ASDKey);
        Iterator<WatermarkItem> it = this.mList.iterator();
        while (it.hasNext()) {
            WatermarkItem next = it.next();
            if (TextUtils.equals(spots2ASDKey, next.getKey())) {
                return next;
            }
        }
        Log.d(TAG, "ASD watermark null");
        return null;
    }

    /* access modifiers changed from: protected */
    public abstract ArrayList<WatermarkItem> getASDWatermarkList();
}
