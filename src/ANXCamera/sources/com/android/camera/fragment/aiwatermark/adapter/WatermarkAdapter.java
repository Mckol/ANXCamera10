package com.android.camera.fragment.aiwatermark.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningAIWatermark;
import com.android.camera.fragment.aiwatermark.holder.WatermarkHolder;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import java.util.ArrayList;
import java.util.List;

public class WatermarkAdapter extends RecyclerView.Adapter<WatermarkHolder> implements View.OnClickListener {
    private static final String TAG = "WatermarkAdapter";
    private ComponentRunningAIWatermark mComponentRunningAIWatermark;
    private int mDegree;
    private int mHorizontalPadding = 0;
    protected List<WatermarkItem> mItems = new ArrayList();
    protected LayoutInflater mLayoutInflater;
    private LinearLayoutManager mLayoutManager;
    private int mSelectedIndex;

    public WatermarkAdapter(Context context, LinearLayoutManager linearLayoutManager, int i, List<WatermarkItem> list) {
        this.mItems = list;
        this.mLayoutManager = linearLayoutManager;
        this.mSelectedIndex = i;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mHorizontalPadding = context.getResources().getDimensionPixelSize(R.dimen.beautycamera_makeup_item_margin);
        this.mComponentRunningAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
    }

    private void notifyItemChanged(int i, int i2) {
        if (i > -1) {
            notifyItemChanged(i, (Object) false);
        }
        if (i2 > -1) {
            notifyItemChanged(i2, (Object) true);
        }
    }

    private void scrollIfNeed(int i) {
        if (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) {
            int i2 = this.mHorizontalPadding;
            View findViewByPosition = this.mLayoutManager.findViewByPosition(i);
            if (i > 0 && findViewByPosition != null) {
                i2 = (this.mHorizontalPadding * 2) + findViewByPosition.getWidth();
            }
            this.mLayoutManager.scrollToPositionWithOffset(Math.max(0, i), i2);
        } else if (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.min(i + 1, getItemCount() - 1));
        }
    }

    private void updateASDStatus() {
        ModeProtocol.AIWatermarkDetect aIWatermarkDetect = (ModeProtocol.AIWatermarkDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(254);
        if (aIWatermarkDetect != null) {
            aIWatermarkDetect.resetResult();
        }
        ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).updateASDForWatermark();
    }

    private void updateSelectItem(WatermarkHolder watermarkHolder, int i) {
        watermarkHolder.updateSelectItem(TextUtils.equals(this.mItems.get(i).getKey(), this.mComponentRunningAIWatermark.getCurrentKey()) ? 0 : 8);
    }

    private void updateWatermark(WatermarkItem watermarkItem) {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        if (mainContentProtocol != null) {
            mainContentProtocol.updateWatermarkSample(watermarkItem);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<WatermarkItem> list = this.mItems;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public List<WatermarkItem> getItems() {
        return this.mItems;
    }

    public int getSelectedIndex() {
        return this.mSelectedIndex;
    }

    public void onBindViewHolder(@NonNull WatermarkHolder watermarkHolder, int i) {
        watermarkHolder.bindHolder(i, this.mItems.get(i));
        watermarkHolder.itemView.setTag(Integer.valueOf(i));
        watermarkHolder.itemView.setOnClickListener(this);
        updateSelectItem(watermarkHolder, i);
        watermarkHolder.itemView.setRotation((float) this.mDegree);
    }

    public void onBindViewHolder(@NonNull WatermarkHolder watermarkHolder, int i, @NonNull List list) {
        if (list.isEmpty()) {
            onBindViewHolder(watermarkHolder, i);
        } else if (list.get(0) instanceof Boolean) {
            boolean booleanValue = ((Boolean) list.get(0)).booleanValue();
            watermarkHolder.itemView.setActivated(booleanValue);
            if (watermarkHolder.getSelectedIndicator() != null) {
                Util.updateSelectIndicator(watermarkHolder.getSelectedIndicator(), booleanValue, true);
                Util.correctionSelectView(watermarkHolder.getImageView(), booleanValue);
            }
        }
    }

    public void onClick(View view) {
        int i;
        try {
            i = Integer.parseInt(view.getTag().toString());
        } catch (NumberFormatException unused) {
            Log.e(TAG, "Object can not cast to Integer");
            i = 0;
        }
        WatermarkItem watermarkItem = this.mItems.get(i);
        String key = watermarkItem.getKey();
        if (TextUtils.equals(key, this.mComponentRunningAIWatermark.getCurrentKey())) {
            Log.d(TAG, "user touch the same item. do nothing.");
            return;
        }
        if (TextUtils.equals(key, WatermarkConstant.AI_TRIGGER) || TextUtils.equals(this.mComponentRunningAIWatermark.getCurrentKey(), WatermarkConstant.AI_TRIGGER)) {
            updateASDStatus();
        }
        this.mComponentRunningAIWatermark.setCurrentKey(key);
        boolean equals = TextUtils.equals(this.mItems.get(i).getKey(), WatermarkConstant.AI_TRIGGER);
        DataRepository.dataItemRunning().getComponentRunningAIWatermark().setIWatermarkEnable(equals);
        if (equals) {
            ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).showWatermarkSample(true);
        } else {
            updateWatermark(watermarkItem);
        }
        onSelected(i, true);
        int intValue = Integer.valueOf(this.mComponentRunningAIWatermark.getCurrentType()).intValue();
        if (intValue == 11 || intValue == 12) {
            CameraStatUtils.trackSuperMoonClick(MistatsConstants.SuperMoon.PARAM_SUPER_MOON_EFFECT_SELECT);
            CameraStatUtils.trackSuperMoonEffectKey(key);
            return;
        }
        CameraStatUtils.trackAIWatermarkClick(MistatsConstants.AIWatermark.AI_WATERMARK_SELECT);
        CameraStatUtils.trackAIWatermarkKey(key);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public WatermarkHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        String str = TAG;
        Log.d(str, "onCreateViewHolder i = " + i);
        View inflate = this.mLayoutInflater.inflate(R.layout.watermark_recyclerview_item, viewGroup, false);
        WatermarkHolder watermarkHolder = new WatermarkHolder(inflate);
        FolmeUtils.touchScale(inflate);
        return watermarkHolder;
    }

    public void onSelected(int i, boolean z) {
        int i2 = this.mSelectedIndex;
        if (i2 != i) {
            this.mSelectedIndex = i;
            if (z) {
                scrollIfNeed(i);
                notifyItemChanged(i2, this.mSelectedIndex);
                return;
            }
            notifyDataSetChanged();
        }
    }

    public void setRotation(int i) {
        this.mDegree = i;
    }
}
