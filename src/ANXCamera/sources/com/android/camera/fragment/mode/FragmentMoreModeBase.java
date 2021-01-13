package com.android.camera.fragment.mode;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.global.ComponentModuleList;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import java.util.List;

public abstract class FragmentMoreModeBase extends BaseFragment implements View.OnClickListener {
    public static final int MAX_ICON_COUNT_PER_LINES = 3;
    public static final int TYPE_EDIT = 2;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_POPUP = 1;
    protected ComponentModuleList mComponentModuleList;
    protected ModeAdapter mModeAdapter;
    protected RecyclerView mModeList;
    protected View mRootView;

    public ComponentModuleList getComponentModuleList() {
        return this.mComponentModuleList;
    }

    /* access modifiers changed from: protected */
    public abstract RecyclerView.LayoutManager getLayoutManager();

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_module_more;
    }

    public ModeAdapter getModeAdapter() {
        return this.mModeAdapter;
    }

    /* access modifiers changed from: protected */
    public abstract ModeItemDecoration getModeItemDecoration();

    /* access modifiers changed from: protected */
    public abstract RecyclerView getModeList();

    /* access modifiers changed from: protected */
    public abstract int getType();

    /* access modifiers changed from: protected */
    public abstract boolean hide();

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mRootView = view;
        this.mModeList = getModeList();
        this.mModeList.setLayoutManager(getLayoutManager());
        if (this.mModeList.getItemDecorationCount() == 0) {
            this.mModeList.addItemDecoration(getModeItemDecoration());
        }
        this.mModeAdapter = new ModeAdapter(getContext(), this);
        this.mModeAdapter.setRotate(this.mDegree);
        this.mModeList.setAdapter(this.mModeAdapter);
        this.mModeList.setClickable(false);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        this.mModeAdapter.notifyDataSetChanged();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.mode_item) {
            int parseInt = Integer.parseInt(((ComponentDataItem) view.getTag()).mValue);
            if (parseInt == 255) {
                ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                if (configChanges != null) {
                    configChanges.configModeEdit();
                    return;
                }
                return;
            }
            hide();
            ModeProtocol.ModeChangeController modeChangeController = (ModeProtocol.ModeChangeController) ModeCoordinatorImpl.getInstance().getAttachProtocol(179);
            if (modeChangeController != null) {
                modeChangeController.changeModeByNewMode(parseInt, 0);
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.mComponentModuleList = DataRepository.dataItemGlobal().getComponentModuleList();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        this.mDegree = i;
    }
}
