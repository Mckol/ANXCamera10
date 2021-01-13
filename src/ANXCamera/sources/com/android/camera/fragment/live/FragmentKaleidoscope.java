package com.android.camera.fragment.live;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.ComponentRunningKaleidoscope;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.EffectItemAdapter;
import com.android.camera.fragment.beauty.BaseBeautyFragment;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;

public class FragmentKaleidoscope extends BaseBeautyFragment {
    private static final String TAG = "FragmentKaleidoscope";
    private KaleidoscopeAdapter mAdapter;
    private ComponentRunningKaleidoscope mComponentRunningKaleidoscope;
    int mFutureSelectIndex;
    private int mItemWidth;
    private List<ComponentDataItem> mKaleidoscopeList;
    private RecyclerView mKaleidoscopeRecyclerView;
    private LinearLayoutManagerWrapper mLayoutManager;
    int mSelectIndex;
    private int mTotalWidth;

    /* access modifiers changed from: private */
    public static class KaleidoscopeAdapter extends RecyclerView.Adapter<KaleidoItemHolder> {
        Context mContext;
        RequestOptions mGlideOptions = ((RequestOptions) new RequestOptions().placeholder(R.drawable.ic_live_sticker_placeholder));
        List<ComponentDataItem> mKaleidoscopeList;
        LayoutInflater mLayoutInflater;
        AdapterView.OnItemClickListener mListener;
        int mSelectIndex;

        /* access modifiers changed from: package-private */
        public class KaleidoItemHolder extends CommonRecyclerViewHolder implements View.OnClickListener {
            public KaleidoItemHolder(View view) {
                super(view);
                view.setOnClickListener(this);
                FolmeUtils.touchItemScale(view);
            }

            public void onClick(View view) {
                int adapterPosition = getAdapterPosition();
                KaleidoscopeAdapter kaleidoscopeAdapter = KaleidoscopeAdapter.this;
                if (adapterPosition != kaleidoscopeAdapter.mSelectIndex) {
                    kaleidoscopeAdapter.mListener.onItemClick(null, view, adapterPosition, getItemId());
                }
            }
        }

        public KaleidoscopeAdapter(Context context, List<ComponentDataItem> list, int i, AdapterView.OnItemClickListener onItemClickListener) {
            this.mContext = context;
            this.mKaleidoscopeList = list;
            this.mSelectIndex = i;
            this.mLayoutInflater = LayoutInflater.from(context);
            this.mListener = onItemClickListener;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mKaleidoscopeList.size();
        }

        public void onBindViewHolder(KaleidoItemHolder kaleidoItemHolder, int i) {
            View view = kaleidoItemHolder.getView(R.id.mimoji_item_selected_indicator);
            ComponentDataItem componentDataItem = this.mKaleidoscopeList.get(i);
            kaleidoItemHolder.itemView.setTag(componentDataItem);
            ((ImageView) kaleidoItemHolder.getView(R.id.mimoji_item_image)).setImageResource(componentDataItem.mIconSelectedRes);
            if (i == this.mSelectIndex) {
                view.setVisibility(0);
                view.setBackground(this.mContext.getResources().getDrawable(R.drawable.effect_filter_item_selector));
                return;
            }
            view.setVisibility(8);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public KaleidoItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new KaleidoItemHolder(this.mLayoutInflater.inflate(R.layout.fragment_kaleidoscope_item, viewGroup, false));
        }

        public void setSelectIndex(int i) {
            this.mSelectIndex = i;
        }
    }

    private void initView(View view) {
        this.mItemWidth = getResources().getDimensionPixelSize(R.dimen.live_sticker_item_size);
        this.mTotalWidth = getResources().getDisplayMetrics().widthPixels;
        this.mKaleidoscopeRecyclerView = (RecyclerView) view.findViewById(R.id.kaleidoscope_list);
        this.mSelectIndex = 0;
        this.mComponentRunningKaleidoscope = DataRepository.dataItemRunning().getComponentRunningKaleidoscope();
        this.mKaleidoscopeList = this.mComponentRunningKaleidoscope.getItems();
        String kaleidoscopeValue = this.mComponentRunningKaleidoscope.getKaleidoscopeValue();
        if (kaleidoscopeValue != null) {
            int i = 0;
            while (true) {
                if (i >= this.mKaleidoscopeList.size()) {
                    break;
                } else if (kaleidoscopeValue.equals(this.mKaleidoscopeList.get(i).mValue)) {
                    this.mSelectIndex = i;
                    break;
                } else {
                    i++;
                }
            }
        }
        this.mAdapter = new KaleidoscopeAdapter(getContext(), this.mKaleidoscopeList, this.mSelectIndex, new AdapterView.OnItemClickListener() {
            /* class com.android.camera.fragment.live.FragmentKaleidoscope.AnonymousClass1 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                FragmentKaleidoscope.this.onItemSelected(i, view);
            }
        });
        this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), "kaleidoscope_list");
        this.mLayoutManager.setOrientation(0);
        this.mKaleidoscopeRecyclerView.setLayoutManager(this.mLayoutManager);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setChangeDuration(150);
        defaultItemAnimator.setMoveDuration(150);
        defaultItemAnimator.setAddDuration(150);
        defaultItemAnimator.setSupportsChangeAnimations(false);
        this.mKaleidoscopeRecyclerView.setItemAnimator(defaultItemAnimator);
        this.mKaleidoscopeRecyclerView.addItemDecoration(new EffectItemAdapter.EffectItemPadding(getContext()));
        this.mKaleidoscopeRecyclerView.setAdapter(this.mAdapter);
        setItemInCenter(this.mSelectIndex);
    }

    private boolean scrollIfNeed(int i) {
        int max = (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) ? Math.max(0, i - 1) : (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) ? Math.min(i + 1, this.mLayoutManager.getItemCount() - 1) : i;
        if (max == i) {
            return false;
        }
        this.mLayoutManager.scrollToPosition(max);
        return true;
    }

    private void setItemInCenter(int i) {
        this.mLayoutManager.scrollToPositionWithOffset(i, (this.mTotalWidth / 2) - (this.mItemWidth / 2));
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public View getAnimateView() {
        return this.mKaleidoscopeRecyclerView;
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_kaleidoscope, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    /* access modifiers changed from: protected */
    public void onItemSelected(int i, View view) {
        this.mFutureSelectIndex = i;
        ComponentDataItem componentDataItem = this.mKaleidoscopeList.get(i);
        Log.v(TAG, "select kaleidoscope " + i);
        int i2 = this.mSelectIndex;
        this.mSelectIndex = i;
        this.mAdapter.setSelectIndex(this.mSelectIndex);
        this.mAdapter.notifyItemChanged(i2);
        this.mAdapter.notifyItemChanged(this.mSelectIndex);
        scrollIfNeed(this.mSelectIndex);
        this.mComponentRunningKaleidoscope.setKaleidoscopeValue(componentDataItem.mValue);
        CameraStatUtils.trackKaleidoscopeValue(componentDataItem.mValue);
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.setKaleidoscope(componentDataItem.mValue);
        }
    }
}
