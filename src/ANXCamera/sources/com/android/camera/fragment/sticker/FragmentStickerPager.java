package com.android.camera.fragment.sticker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.CtaNoticeFragment;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.sticker.BaseSelectAdapter;
import com.android.camera.fragment.sticker.StickerAdapter;
import com.android.camera.log.Log;
import com.android.camera.network.net.base.ErrorCode;
import com.android.camera.network.net.base.ResponseListener;
import com.android.camera.network.resource.DownloadHelper;
import com.android.camera.network.resource.OnDownloadListener;
import com.android.camera.network.resource.Resource;
import com.android.camera.network.resource.ResourceDownloadManager;
import com.android.camera.network.resource.StickerResourceRequest;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.sticker.StickerHelper;
import com.android.camera.sticker.StickerInfo;
import java.util.List;

public class FragmentStickerPager extends Fragment {
    private static final int MSG_NOTIFY_ITEM = 1;
    private static final int MSG_NOTIFY_LIST = 2;
    private static final String TAG = "FragmentStickerPager";
    private StickerAdapter mAdapter;
    private DownloadHelper mDefaultDownloadHelper = new DownloadHelper() {
        /* class com.android.camera.fragment.sticker.FragmentStickerPager.AnonymousClass5 */

        @Override // com.android.camera.network.resource.DownloadHelper
        public String getDownloadPath(Resource resource) {
            return StickerHelper.getInstance().getStickerPath(resource.id);
        }
    };
    private OnDownloadListener mDownloadListener = new OnDownloadListener() {
        /* class com.android.camera.fragment.sticker.FragmentStickerPager.AnonymousClass3 */

        @Override // com.android.camera.network.resource.OnDownloadListener
        public void onFinish(long j, int i) {
            Log.i(FragmentStickerPager.TAG, j + ":" + i);
            for (int i2 = 0; i2 < FragmentStickerPager.this.mList.size(); i2++) {
                if (((StickerInfo) FragmentStickerPager.this.mList.get(i2)).id == j) {
                    ((StickerInfo) FragmentStickerPager.this.mList.get(i2)).downloadState = i;
                    Message.obtain(FragmentStickerPager.this.mHandler, 1, i2, 0).sendToTarget();
                }
            }
        }

        @Override // com.android.camera.network.resource.OnDownloadListener
        public void onProgressUpdate(long j, int i) {
            Log.i(FragmentStickerPager.TAG, i + "");
        }
    };
    private Handler mHandler = new Handler() {
        /* class com.android.camera.fragment.sticker.FragmentStickerPager.AnonymousClass4 */

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                FragmentStickerPager.this.mAdapter.notifyItemChanged(message.arg1);
                Log.i(FragmentStickerPager.TAG, "MSG_NOTIFY_ITEM");
            } else if (i == 2) {
                FragmentStickerPager.this.mAdapter.notifyDataSetChanged();
            }
            super.handleMessage(message);
        }
    };
    private List<StickerInfo> mList;
    private RecyclerView mRecyclerView;

    private void initView(View view) {
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.sticker_item_list);
        this.mRecyclerView.setFocusable(false);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getContext(), 0, false, "sticker_item_list"));
        this.mAdapter = new StickerAdapter(getContext());
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.setItemSelectChangeListener(new BaseSelectAdapter.ItemSelectChangeListener() {
            /* class com.android.camera.fragment.sticker.FragmentStickerPager.AnonymousClass1 */

            @Override // com.android.camera.fragment.sticker.BaseSelectAdapter.ItemSelectChangeListener
            public boolean onItemSelect(BaseSelectAdapter.BaseSelectHolder baseSelectHolder, int i, boolean z) {
                ModeProtocol.FilterProtocol filterProtocol;
                StickerInfo stickerInfo = (StickerInfo) FragmentStickerPager.this.mAdapter.getItemData(i);
                int downloadState = stickerInfo.getDownloadState();
                boolean z2 = false;
                if (downloadState == 1 || downloadState == 3) {
                    String srcPath = stickerInfo.getSrcPath();
                    FragmentStickerPager.this.persistSticker(srcPath);
                    EffectController.getInstance().setCurrentSticker(srcPath);
                    EffectController instance = EffectController.getInstance();
                    if (i == 0) {
                        z2 = true;
                    }
                    instance.enableMakeup(z2);
                    ModeProtocol.StickerProtocol stickerProtocol = (ModeProtocol.StickerProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(178);
                    if (stickerProtocol != null) {
                        stickerProtocol.onStickerChanged(srcPath);
                    }
                    int filterId = stickerInfo.getFilterId();
                    if (!(filterId == FilterInfo.FILTER_ID_NONE || (filterProtocol = (ModeProtocol.FilterProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(165)) == null)) {
                        filterProtocol.onFilterChanged(1, filterId);
                    }
                    return true;
                }
                if ((downloadState == 0 || downloadState == 4) && CtaNoticeFragment.checkCta(FragmentStickerPager.this.getActivity().getFragmentManager(), 3)) {
                    ((StickerAdapter.StickerHolder) baseSelectHolder).mDownloadView.startDownload();
                    stickerInfo.downloadState = 2;
                    ResourceDownloadManager.getInstance().download((Resource) FragmentStickerPager.this.mList.get(i), FragmentStickerPager.this.mDefaultDownloadHelper);
                }
                return false;
            }
        });
        ResourceDownloadManager.getInstance().addDownloadListener(this.mDownloadListener);
        loadData();
    }

    private void loadData() {
        new StickerResourceRequest().execute(new ResponseListener() {
            /* class com.android.camera.fragment.sticker.FragmentStickerPager.AnonymousClass2 */

            @Override // com.android.camera.network.net.base.ResponseListener
            public void onResponse(Object... objArr) {
                List list = (List) objArr[0];
                FragmentStickerPager.this.refreshDownloadState(list);
                FragmentStickerPager.this.refreshData(list);
                Object[] objArr2 = new Object[1];
                objArr2[0] = Integer.valueOf(list == null ? -1 : list.size());
                Log.d(FragmentStickerPager.TAG, String.format("getStickerList %d ", objArr2));
            }

            @Override // com.android.camera.network.net.base.ResponseListener
            public void onResponseError(ErrorCode errorCode, String str, Object obj) {
                FragmentStickerPager.this.refreshData(null);
                Log.e(FragmentStickerPager.TAG, String.format("errorCode %d msg:%s", Integer.valueOf(errorCode.CODE), str));
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void persistSticker(String str) {
        DataProvider.ProviderEditor editor = DataRepository.dataItemConfig().editor();
        editor.putString("pref_sticker_path_key", str);
        editor.apply();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void refreshData(List<StickerInfo> list) {
        this.mList = StickerHelper.getInstance().getLocalStick();
        if (list != null) {
            this.mList.addAll(list);
        }
        this.mAdapter.setList(this.mList);
        setSelectItem();
        this.mHandler.sendEmptyMessage(2);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void refreshDownloadState(List<StickerInfo> list) {
        for (StickerInfo stickerInfo : list) {
            stickerInfo.downloadState = ResourceDownloadManager.getInstance().getDownloadState(stickerInfo.id);
        }
    }

    private void setSelectItem() {
        String currentSticker = EffectController.getInstance().getCurrentSticker();
        for (int i = 0; i < this.mList.size(); i++) {
            if (TextUtils.equals(currentSticker, this.mList.get(i).getSrcPath())) {
                this.mAdapter.initSelectItem(i);
                return;
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_sticker_pager, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        ResourceDownloadManager.getInstance().removeDownloadListener(this.mDownloadListener);
    }

    @Override // androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        if (z && this.mRecyclerView != null) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(300);
            this.mRecyclerView.startAnimation(alphaAnimation);
        }
    }
}
