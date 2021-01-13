package com.android.camera.features.mimoji2.fragment.bottomlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.data.DataRepository;
import com.android.camera.features.mimoji2.bean.MimojiBgInfo;
import com.android.camera.features.mimoji2.bean.MimojiInfo2;
import com.android.camera.features.mimoji2.bean.MimojiTimbreInfo;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.widget.baseview.BaseItemAnimator;
import com.android.camera.features.mimoji2.widget.baseview.BaseLinearLayoutManager;
import com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerAdapter;
import com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerViewHolder;
import com.android.camera.features.mimoji2.widget.helper.AvatarEngineManager2;
import com.android.camera.features.mimoji2.widget.helper.BubbleEditMimojiPresenter2;
import com.android.camera.features.mimoji2.widget.helper.MimojiHelper2;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;
import com.android.camera.fragment.EffectItemAdapter;
import com.android.camera.fragment.live.FragmentLiveBase;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.arcsoft.avatar2.BackgroundInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FragmentMimojiBottomList extends FragmentLiveBase implements MimojiModeProtocol.MimojiBottomList, View.OnClickListener {
    public static final String ADD_STATE = "add_state";
    public static final String CLOSE_STATE = "close_state";
    private static final int FRAGMENT_INFO = 65520;
    private static final String TAG = "FragmentMimojiBottomList";
    private BubbleEditMimojiPresenter2 bubbleEditMimojiPresenter2;
    private AlertDialog mAlertDialog;
    private long mClickTime;
    private Context mContext;
    private HandlerThread mHanderThread;
    private Handler mHandler;
    private boolean mIsRTL;
    private int mItemWidth;
    private BaseLinearLayoutManager mLayoutManager;
    private LinearLayout mLlProgress;
    private MimojiBgAdapter mMimojiBgAdapter;
    private MimojiAvatarAdapter mMimojiCreateItemAdapter2;
    private List<MimojiInfo2> mMimojiInfo2List = new ArrayList();
    private MimojiInfo2 mMimojiInfo2Select;
    private RecyclerView mMimojiRecylerView;
    private MimojiTimbreAdapter mMimojiTimbreAdapter;
    private int mSelectIndex;
    private int mTotalWidth;
    private MimojiStatusManager2 mimojiStatusManager2;
    private RelativeLayout popContainer;
    private RelativeLayout popParent;
    private final int[] resourceBg = {R.drawable.ic_mimoji_bg_wave, R.drawable.ic_mimoji_bg_rotate, R.drawable.ic_mimoji_bg_circle, R.drawable.ic_mimoji_bg_white, R.drawable.ic_mimoji_bg_pink, R.drawable.ic_mimoji_bg_blue};
    private final int[] resourceTimbre = {R.drawable.ic_mimoji_timbre_gentlemen, R.drawable.ic_mimoji_timbre_lady, R.drawable.ic_mimoji_timbre_girl, R.drawable.ic_mimoji_timbre_baby, R.drawable.ic_mimoji_timbre_robot};

    static /* synthetic */ void Oa() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0054 A[Catch:{ Exception -> 0x0062 }] */
    private boolean autoMove(int i, RecyclerView.Adapter adapter) {
        int i2;
        boolean z = false;
        if (!(this.mMimojiRecylerView == null || this.mLayoutManager == null || adapter == null || adapter.getItemCount() == 0)) {
            try {
                int findFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
                int findFirstCompletelyVisibleItemPosition = this.mLayoutManager.findFirstCompletelyVisibleItemPosition();
                int itemCount = adapter == null ? 0 : adapter.getItemCount();
                if (!(i == findFirstVisibleItemPosition || i == findFirstCompletelyVisibleItemPosition)) {
                    if (i != findFirstCompletelyVisibleItemPosition - 2) {
                        if (i != this.mLayoutManager.findLastVisibleItemPosition()) {
                            if (i != this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
                                i2 = i;
                                if (i2 != i) {
                                    z = true;
                                }
                                if (z || i2 == 0 || i2 == itemCount - 1) {
                                    this.mMimojiRecylerView.smoothScrollToPosition(i2);
                                }
                            }
                        }
                        i2 = Math.min(i + 1, itemCount - 1);
                        if (i2 != i) {
                        }
                        this.mMimojiRecylerView.smoothScrollToPosition(i2);
                    }
                }
                i2 = Math.max(0, i - 1);
                if (i2 != i) {
                }
                this.mMimojiRecylerView.smoothScrollToPosition(i2);
            } catch (Exception unused) {
                Log.d(TAG, "mimoji boolean autoMove[position, adapter]");
            }
        }
        return z;
    }

    private void initAvatarList() {
        this.mSelectIndex = 0;
        HandlerThread handlerThread = this.mHanderThread;
        if (handlerThread == null || !handlerThread.isAlive()) {
            this.mHanderThread = new HandlerThread("mimojilist") {
                /* class com.android.camera.features.mimoji2.fragment.bottomlist.FragmentMimojiBottomList.AnonymousClass2 */
            };
            this.mHanderThread.start();
        }
        if (this.mHandler == null) {
            this.mHandler = new Handler(this.mHanderThread.getLooper()) {
                /* class com.android.camera.features.mimoji2.fragment.bottomlist.FragmentMimojiBottomList.AnonymousClass3 */

                public void handleMessage(@NonNull Message message) {
                    MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2;
                    super.handleMessage(message);
                    if (message.what == 65520 && (mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246)) != null) {
                        Object obj = message.obj;
                        if (obj == null) {
                            mimojiAvatarEngine2.onMimojiChangeBg(null);
                            mimojiAvatarEngine2.onMimojiSelect(null, false);
                            return;
                        }
                        mimojiAvatarEngine2.onMimojiSelect((MimojiInfo2) obj, false);
                    }
                }
            };
        }
        int i = DataRepository.dataItemRunning().getUiStyle() == 4 ? R.drawable.makeup_item_bg_white : R.drawable.makeup_item_bg;
        this.mMimojiCreateItemAdapter2 = new MimojiAvatarAdapter(null);
        this.mMimojiCreateItemAdapter2.setResourceBg(i);
        this.mMimojiCreateItemAdapter2.setOnRecyclerItemClickListener(new b(this));
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        int i2 = 1;
        firstProgressShow(this.mimojiStatusManager2.IsLoading() || (mimojiAvatarEngine2 != null && !mimojiAvatarEngine2.isAvatarInited()));
        filelistToMinojiInfo();
        this.mLayoutManager = new BaseLinearLayoutManager(getContext());
        this.mLayoutManager.setOrientation(0);
        this.mMimojiRecylerView.setLayoutManager(this.mLayoutManager);
        this.mMimojiRecylerView.setAdapter(this.mMimojiCreateItemAdapter2);
        String currentMimojiState = this.mimojiStatusManager2.getCurrentMimojiState();
        while (true) {
            if (i2 < this.mMimojiInfo2List.size()) {
                if (!TextUtils.isEmpty(this.mMimojiInfo2List.get(i2).mConfigPath) && currentMimojiState.equals(this.mMimojiInfo2List.get(i2).mConfigPath)) {
                    this.mSelectIndex = i2;
                    break;
                }
                i2++;
            } else {
                break;
            }
        }
        this.mMimojiCreateItemAdapter2.setLastSelectPosition(this.mSelectIndex);
        this.mMimojiCreateItemAdapter2.setRotation(this.mDegree);
        this.mMimojiCreateItemAdapter2.notifyDataSetChanged();
    }

    private void initBgList() {
        ArrayList arrayList = new ArrayList();
        CopyOnWriteArrayList<BackgroundInfo> backgroundInfos = AvatarEngineManager2.getInstance().getBackgroundInfos();
        if (backgroundInfos.size() != this.resourceBg.length) {
            Log.e(TAG, "mimoji bg resource size error");
            return;
        }
        this.mSelectIndex = 0;
        MimojiBgInfo currentMimojiBgInfo = this.mimojiStatusManager2.getCurrentMimojiBgInfo();
        arrayList.add(new MimojiBgInfo(currentMimojiBgInfo == null));
        int i = 0;
        while (i < backgroundInfos.size()) {
            int i2 = i + 1;
            MimojiBgInfo mimojiBgInfo = new MimojiBgInfo(backgroundInfos.get(i), this.resourceBg[i], i2);
            if (currentMimojiBgInfo != null && currentMimojiBgInfo.getBackgroundInfo().getName().equals(mimojiBgInfo.getBackgroundInfo().getName())) {
                mimojiBgInfo.setSelected(true);
                this.mSelectIndex = i;
            }
            arrayList.add(mimojiBgInfo);
            i = i2;
        }
        this.mMimojiBgAdapter = new MimojiBgAdapter(arrayList);
        this.mMimojiBgAdapter.setRotation(this.mDegree);
        this.mMimojiBgAdapter.setOnRecyclerItemClickListener(new a(this));
        this.mLayoutManager = new BaseLinearLayoutManager(getContext());
        this.mLayoutManager.setOrientation(0);
        this.mMimojiRecylerView.setLayoutManager(this.mLayoutManager);
        this.mMimojiRecylerView.setAdapter(this.mMimojiBgAdapter);
    }

    private void initMargin() {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.popParent.getLayoutParams();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.beautycamera_makeup_item_margin);
        if (this.mimojiStatusManager2.isInMimojiPreviewPlay()) {
            marginLayoutParams.bottomMargin = 0;
        } else if (CameraSettings.isGifOn()) {
            marginLayoutParams.bottomMargin = Util.getBottomHeight() - ((dimensionPixelSize / 2) * 3);
        } else {
            marginLayoutParams.bottomMargin = Util.getBottomHeight();
        }
        this.popParent.setLayoutParams(marginLayoutParams);
    }

    private void initTimbreList() {
        ArrayList arrayList = new ArrayList();
        if (this.resourceTimbre.length != MimojiTimbreInfo.timbreTypes.length) {
            Log.e(TAG, "mimoji timbre resource size error");
            return;
        }
        boolean z = false;
        this.mSelectIndex = 0;
        MimojiTimbreInfo currentMimojiTimbreInfo = this.mimojiStatusManager2.getCurrentMimojiTimbreInfo();
        arrayList.add(new MimojiTimbreInfo(currentMimojiTimbreInfo == null));
        int i = 0;
        while (true) {
            int[] iArr = this.resourceTimbre;
            if (i >= iArr.length) {
                break;
            }
            MimojiTimbreInfo mimojiTimbreInfo = new MimojiTimbreInfo(MimojiTimbreInfo.timbreTypes[i], iArr[i]);
            if (currentMimojiTimbreInfo != null && currentMimojiTimbreInfo.getTimbreId() == mimojiTimbreInfo.getTimbreId()) {
                mimojiTimbreInfo.setSelected(true);
                this.mSelectIndex = i;
                this.mSelectIndex++;
            }
            arrayList.add(mimojiTimbreInfo);
            i++;
        }
        this.mMimojiTimbreAdapter = new MimojiTimbreAdapter(arrayList);
        this.mMimojiTimbreAdapter.setRotation(this.mDegree);
        this.mMimojiTimbreAdapter.setOnRecyclerItemClickListener(new d(this));
        this.mLayoutManager = new BaseLinearLayoutManager(getContext());
        this.mLayoutManager.setOrientation(0);
        this.mMimojiRecylerView.setLayoutManager(this.mLayoutManager);
        this.mMimojiRecylerView.setAdapter(this.mMimojiTimbreAdapter);
        MimojiTimbreAdapter mimojiTimbreAdapter = this.mMimojiTimbreAdapter;
        int i2 = this.mSelectIndex;
        if (this.mimojiStatusManager2.isInMimojiPreviewPlay() && this.mMimojiTimbreAdapter.getItemCount() >= this.mSelectIndex && ((MimojiTimbreInfo) this.mMimojiTimbreAdapter.getDataList().get(this.mSelectIndex)).getTimbreId() > 0) {
            z = true;
        }
        mimojiTimbreAdapter.setSelectState(i2, z);
        this.mMimojiTimbreAdapter.hideProgress();
    }

    private boolean scrollIfNeed(int i) {
        if (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.max(0, i - 1));
            return true;
        } else if (i != this.mLayoutManager.findLastVisibleItemPosition() && i != this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
            return false;
        } else {
            this.mLayoutManager.scrollToPosition(Math.min(i + 1, this.mMimojiCreateItemAdapter2.getItemCount() - 1));
            return true;
        }
    }

    private void setSelectItemInCenter() {
        if (this.mimojiStatusManager2.getMimojiPanelState() != 0) {
            this.mLayoutManager.scrollToPositionWithOffset(this.mSelectIndex, (this.mTotalWidth / 2) - (this.mItemWidth / 2));
        }
    }

    private void showAlertDialog() {
        if (this.mAlertDialog == null) {
            this.mAlertDialog = RotateDialogController.showSystemAlertDialog(getActivity(), getString(R.string.mimoji_delete_dialog_title), null, null, null, getText(R.string.mimoji_delete), new Runnable() {
                /* class com.android.camera.features.mimoji2.fragment.bottomlist.FragmentMimojiBottomList.AnonymousClass4 */

                public void run() {
                    if (FragmentMimojiBottomList.this.mMimojiInfo2Select != null && !TextUtils.isEmpty(FragmentMimojiBottomList.this.mMimojiInfo2Select.mPackPath)) {
                        FileUtils.deleteFile(FragmentMimojiBottomList.this.mMimojiInfo2Select.mPackPath);
                        FragmentMimojiBottomList.this.bubbleEditMimojiPresenter2.processBubbleAni(-2, -2, null);
                        FragmentMimojiBottomList.this.mSelectIndex = 0;
                        FragmentMimojiBottomList.this.mMimojiCreateItemAdapter2.setLastSelectPosition(0);
                        FragmentMimojiBottomList.this.mMimojiCreateItemAdapter2.notifyDataSetChanged();
                        FragmentMimojiBottomList.this.filelistToMinojiInfo();
                        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
                        if (mimojiAvatarEngine2 != null) {
                            mimojiAvatarEngine2.onMimojiDeleted();
                        }
                        CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_DELETE, "delete");
                        CameraStatUtils.trackMimojiCount(Integer.toString(FragmentMimojiBottomList.this.mMimojiInfo2List.size() - 4));
                    }
                }
            }, getString(R.string.mimoji_cancle), null);
            this.mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                /* class com.android.camera.features.mimoji2.fragment.bottomlist.FragmentMimojiBottomList.AnonymousClass5 */

                public void onDismiss(DialogInterface dialogInterface) {
                    FragmentMimojiBottomList.this.mAlertDialog = null;
                }
            });
        }
    }

    public /* synthetic */ void Na() {
        MimojiTimbreAdapter mimojiTimbreAdapter;
        if (this.mimojiStatusManager2.getMimojiPanelState() == 3 && (mimojiTimbreAdapter = this.mMimojiTimbreAdapter) != null) {
            mimojiTimbreAdapter.hideProgress();
        }
    }

    public /* synthetic */ void Pa() {
        MimojiTimbreAdapter mimojiTimbreAdapter;
        int i = 1;
        if (this.mimojiStatusManager2.getMimojiPanelState() != 1 || this.mMimojiCreateItemAdapter2 == null) {
            if (this.mimojiStatusManager2.getMimojiPanelState() == 3 && (mimojiTimbreAdapter = this.mMimojiTimbreAdapter) != null) {
                mimojiTimbreAdapter.hideProgress();
            }
            if (this.mMimojiRecylerView.getAdapter() != null) {
                this.mMimojiRecylerView.getAdapter().notifyDataSetChanged();
                return;
            }
            return;
        }
        filelistToMinojiInfo();
        this.bubbleEditMimojiPresenter2.processBubbleAni(-2, -2, null);
        Log.d(TAG, "refreshMimojiList AVATAR");
        this.mSelectIndex = 0;
        String currentMimojiState = this.mimojiStatusManager2.getCurrentMimojiState();
        while (true) {
            if (i < this.mMimojiInfo2List.size()) {
                if (!TextUtils.isEmpty(this.mMimojiInfo2List.get(i).mConfigPath) && currentMimojiState.equals(this.mMimojiInfo2List.get(i).mConfigPath)) {
                    this.mSelectIndex = i;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        this.mMimojiCreateItemAdapter2.setLastSelectPosition(-1);
        this.mMimojiCreateItemAdapter2.setSelectState(this.mSelectIndex);
        CameraStatUtils.trackMimojiCount(Integer.toString(this.mMimojiCreateItemAdapter2.getItemCount()));
    }

    public /* synthetic */ void a(MimojiBgInfo mimojiBgInfo, int i, View view) {
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (mimojiAvatarEngine2 != null) {
            mimojiAvatarEngine2.onMimojiChangeBg(mimojiBgInfo);
        }
        this.mMimojiBgAdapter.setSelectState(i);
        autoMove(i, this.mMimojiBgAdapter);
    }

    public /* synthetic */ void a(MimojiInfo2 mimojiInfo2, int i, View view) {
        if (mimojiInfo2 != null && !TextUtils.isEmpty(mimojiInfo2.mConfigPath)) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.mClickTime >= ((long) BaseItemAnimator.DEFAULT_LIST_DURATION)) {
                this.mClickTime = currentTimeMillis;
                this.mSelectIndex = i;
                this.bubbleEditMimojiPresenter2.processBubbleAni(-2, -2, null);
                if ("add_state".equals(mimojiInfo2.mConfigPath)) {
                    onAddItemSelected();
                    CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_ADD, MistatsConstants.BaseEvent.ADD);
                    return;
                }
                String str = mimojiInfo2.mConfigPath;
                String currentMimojiState = this.mimojiStatusManager2.getCurrentMimojiState();
                Log.i(TAG, "click　currentState:" + str + " lastState:" + currentMimojiState);
                if (currentMimojiState.equals(str) && mimojiInfo2.getDefaultFrame() > 0) {
                    mimojiInfo2.nextFrame();
                    mimojiInfo2.setIsNeedAnim(true);
                }
                this.mimojiStatusManager2.setCurrentMimojiInfo(mimojiInfo2);
                this.mMimojiCreateItemAdapter2.setSelectState(i);
                if (!autoMove(i, this.mMimojiCreateItemAdapter2)) {
                    processBubble(mimojiInfo2, str, currentMimojiState, view, false);
                }
                Message obtainMessage = this.mHandler.obtainMessage();
                obtainMessage.what = 65520;
                if ("close_state".equals(mimojiInfo2.mConfigPath)) {
                    mimojiInfo2 = null;
                }
                obtainMessage.obj = mimojiInfo2;
                this.mHandler.sendMessageDelayed(obtainMessage, 200);
            }
        }
    }

    public /* synthetic */ void a(MimojiTimbreInfo mimojiTimbreInfo, int i, View view) {
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        if (mimojiVideoEditor == null || !mimojiVideoEditor.isComposing()) {
            if (this.mMimojiTimbreAdapter.setSelectState(i, this.mimojiStatusManager2.isInMimojiPreviewPlay() && mimojiTimbreInfo.getTimbreId() > 0)) {
                MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
                if (mimojiAvatarEngine2 != null) {
                    mimojiAvatarEngine2.onMimojiChangeTimbre(mimojiTimbreInfo, i);
                }
                autoMove(i, this.mMimojiTimbreAdapter);
            }
        }
    }

    public void filelistToMinojiInfo() {
        if (this.mMimojiCreateItemAdapter2 != null && !this.mimojiStatusManager2.IsLoading()) {
            if (this.mimojiStatusManager2.getAvatarPanelState() == 100) {
                this.mMimojiInfo2List = MimojiHelper2.getMimojiHumanList();
            } else {
                this.mMimojiInfo2List = MimojiHelper2.getMimojiCartoonList();
                MimojiInfo2 currentMimojiInfo = this.mimojiStatusManager2.getCurrentMimojiInfo();
                if (currentMimojiInfo != null && !TextUtils.isEmpty(currentMimojiInfo.mAvatarTemplatePath)) {
                    Iterator<MimojiInfo2> it = this.mMimojiInfo2List.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            MimojiInfo2 next = it.next();
                            if (next != null && !TextUtils.isEmpty(next.mAvatarTemplatePath) && next.mAvatarTemplatePath.equals(currentMimojiInfo.mAvatarTemplatePath)) {
                                next.setFrame(currentMimojiInfo.getFrame());
                                Message obtainMessage = this.mHandler.obtainMessage();
                                obtainMessage.what = 65520;
                                obtainMessage.obj = next;
                                this.mHandler.sendMessageDelayed(obtainMessage, 200);
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
            this.mMimojiCreateItemAdapter2.setDataList(this.mMimojiInfo2List);
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiBottomList
    public void firstProgressShow(boolean z) {
        if (getActivity() == null) {
            Log.e(TAG, "not attached to Activity , skip     firstProgressShow........");
            return;
        }
        LinearLayout linearLayout = this.mLlProgress;
        if (linearLayout == null) {
            initView(getView());
        } else if (z) {
            linearLayout.setVisibility(0);
            this.mMimojiRecylerView.setVisibility(8);
        } else {
            linearLayout.setVisibility(8);
            this.mMimojiRecylerView.setVisibility(0);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 65520;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_mimoji;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiBottomList
    public int hideTimbreProgress() {
        if (this.mMimojiRecylerView == null || getActivity() == null) {
            return -1;
        }
        getActivity().runOnUiThread(new f(this));
        return 0;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mItemWidth = getResources().getDimensionPixelSize(R.dimen.live_sticker_item_size);
        this.mTotalWidth = getResources().getDisplayMetrics().widthPixels;
        this.mContext = getContext();
        this.mIsRTL = Util.isLayoutRTL(this.mContext);
        this.mMimojiRecylerView = (RecyclerView) view.findViewById(R.id.mimoji_list);
        this.mMimojiRecylerView.setFocusable(false);
        this.popContainer = (RelativeLayout) view.findViewById(R.id.ll_bubble_pop_occupation);
        this.popParent = (RelativeLayout) view.findViewById(R.id.rl_bubble_pop_parent);
        this.mLlProgress = (LinearLayout) view.findViewById(R.id.ll_updating);
        this.bubbleEditMimojiPresenter2 = new BubbleEditMimojiPresenter2(getContext(), this, this.popParent);
        this.mMimojiRecylerView.setItemAnimator(new BaseItemAnimator());
        this.mMimojiRecylerView.addItemDecoration(new EffectItemAdapter.EffectItemPadding(getContext()));
        this.mMimojiRecylerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /* class com.android.camera.features.mimoji2.fragment.bottomlist.FragmentMimojiBottomList.AnonymousClass1 */

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                FragmentMimojiBottomList.this.bubbleEditMimojiPresenter2.processBubbleAni(-2, -2, null);
            }
        });
        this.mSelectIndex = -1;
        this.mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
        initMargin();
        int mimojiPanelState = this.mimojiStatusManager2.getMimojiPanelState();
        if (mimojiPanelState == 0) {
            Log.d(TAG, "init MimojiPanelState close");
        } else if (mimojiPanelState == 1) {
            initAvatarList();
        } else if (mimojiPanelState == 2) {
            initBgList();
        } else if (mimojiPanelState == 3) {
            initTimbreList();
        }
        setSelectItemInCenter();
    }

    /* access modifiers changed from: protected */
    public void onAddItemSelected() {
        this.mIsNeedShowWhenExit = false;
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (mimojiAvatarEngine2 != null) {
            mimojiAvatarEngine2.onMimojiCreate();
        }
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (actionProcessing != null) {
            actionProcessing.forceSwitchFront();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace, com.android.camera.fragment.live.FragmentLiveBase
    public boolean onBackEvent(int i) {
        ModeProtocol.BottomPopupTips bottomPopupTips;
        String str = TAG;
        Log.d(str, "onBackEvent = " + i);
        if (this.mimojiStatusManager2.isInMimojiEdit() && i != 4) {
            return false;
        }
        if (!(!this.mimojiStatusManager2.isInMimojiPreview() || (bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)) == null || this.mimojiStatusManager2.getMimojiPanelState() == 0)) {
            boolean showMimojiPanel = bottomPopupTips.showMimojiPanel(0);
            if (i == 1 && showMimojiPanel) {
                return true;
            }
        }
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate == null) {
            return false;
        }
        if (baseDelegate.getActiveFragment(R.id.bottom_beauty) != getFragmentInto() && baseDelegate.getActiveFragment(R.id.bottom_mimoji) != getFragmentInto()) {
            return false;
        }
        this.mRemoveFragment = true;
        baseDelegate.delegateEvent(14);
        ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).onRestoreCameraActionMenu(i);
        FolmeUtils.animateDeparture(getView(), e.INSTANCE);
        onAnimationEnd();
        return true;
    }

    public void onClick(View view) {
        int intValue = ((Integer) view.getTag()).intValue();
        if (intValue == 201) {
            MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
            if (mimojiAvatarEngine2 != null) {
                mimojiAvatarEngine2.releaseRender();
            }
            MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
            if (mimojiEditor2 != null) {
                mimojiEditor2.directlyEnterEditMode(this.mMimojiInfo2Select, 201);
            }
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.showMimojiPanel(0);
            }
            CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_EDIT, MistatsConstants.BaseEvent.EDIT);
            this.bubbleEditMimojiPresenter2.processBubbleAni(-2, -2, null);
        } else if (intValue == 202) {
            showAlertDialog();
        } else if (intValue == 204) {
            MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine22 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
            if (mimojiAvatarEngine22 != null) {
                mimojiAvatarEngine22.releaseRender();
            }
            MimojiModeProtocol.MimojiEditor2 mimojiEditor22 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
            if (mimojiEditor22 != null) {
                mimojiEditor22.directlyEnterEditMode(this.mMimojiInfo2Select, 204);
            }
            ModeProtocol.BottomPopupTips bottomPopupTips2 = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips2 != null) {
                bottomPopupTips2.showMimojiPanel(0);
            }
            CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_EDIT, MistatsConstants.BaseEvent.EDIT);
            this.bubbleEditMimojiPresenter2.processBubbleAni(-2, -2, null);
        }
    }

    public void processBubble(MimojiInfo2 mimojiInfo2, String str, String str2, View view, boolean z) {
        boolean isPreCartoonModel = AvatarEngineManager2.isPreCartoonModel(mimojiInfo2.mConfigPath);
        if (str.equals(str2) && !str2.equals("add_state") && !str2.equals("close_state") && !z && !isPreCartoonModel) {
            this.mMimojiInfo2Select = mimojiInfo2;
            int width = view.getWidth();
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            int i = iArr[0];
            int height = this.popContainer.getHeight();
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.manually_indicator_layout_width);
            float f = (float) dimensionPixelSize;
            int i2 = width / 2;
            float dimensionPixelSize2 = f / ((1.0f * f) / ((float) this.mContext.getResources().getDimensionPixelSize(R.dimen.mimoji_edit_bubble_width)));
            int i3 = (int) (((float) (i + i2)) - dimensionPixelSize2);
            if (this.mIsRTL) {
                i3 = (int) (((float) ((this.mTotalWidth - i) - width)) + (((float) i2) - dimensionPixelSize2));
            }
            int i4 = height - (dimensionPixelSize / 2);
            String str3 = TAG;
            Log.i(str3, "coordinateY:" + i4);
            BubbleEditMimojiPresenter2 bubbleEditMimojiPresenter22 = this.bubbleEditMimojiPresenter2;
            if (bubbleEditMimojiPresenter22 != null) {
                bubbleEditMimojiPresenter22.getBubblePop().mIvDeleteFisrt.setRotation((float) this.mDegree);
                this.bubbleEditMimojiPresenter2.getBubblePop().mIvEditFirst.setRotation((float) this.mDegree);
                this.bubbleEditMimojiPresenter2.getBubblePop().mIvEmoticonFirst.setRotation((float) this.mDegree);
            }
            this.bubbleEditMimojiPresenter2.processBubbleAni(i3, i4, view);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        BubbleEditMimojiPresenter2 bubbleEditMimojiPresenter22 = this.bubbleEditMimojiPresenter2;
        if (bubbleEditMimojiPresenter22 != null) {
            list.add(bubbleEditMimojiPresenter22.getBubblePop().mIvDeleteFisrt);
            list.add(this.bubbleEditMimojiPresenter2.getBubblePop().mIvEditFirst);
            list.add(this.bubbleEditMimojiPresenter2.getBubblePop().mIvEmoticonFirst);
        }
        if (this.mMimojiRecylerView != null) {
            for (int i2 = 0; i2 < this.mMimojiRecylerView.getChildCount(); i2++) {
                BaseRecyclerViewHolder baseRecyclerViewHolder = (BaseRecyclerViewHolder) this.mMimojiRecylerView.getChildViewHolder(this.mMimojiRecylerView.getChildAt(i2));
                if (!(baseRecyclerViewHolder == null || baseRecyclerViewHolder.getRotateViews() == null)) {
                    list.addAll(Arrays.asList(baseRecyclerViewHolder.getRotateViews()));
                }
            }
        }
        if (this.mMimojiRecylerView.getAdapter() != null) {
            ((BaseRecyclerAdapter) this.mMimojiRecylerView.getAdapter()).setRotation(i);
            int findFirstVisibleItemPosition = this.mLayoutManager.findFirstVisibleItemPosition();
            int findLastVisibleItemPosition = this.mLayoutManager.findLastVisibleItemPosition();
            for (int i3 = 0; i3 < findFirstVisibleItemPosition; i3++) {
                this.mMimojiRecylerView.getAdapter().notifyItemChanged(i3);
            }
            while (true) {
                findLastVisibleItemPosition++;
                if (findLastVisibleItemPosition < this.mMimojiRecylerView.getAdapter().getItemCount()) {
                    this.mMimojiRecylerView.getAdapter().notifyItemChanged(findLastVisibleItemPosition);
                } else {
                    return;
                }
            }
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiBottomList
    public int refreshMimojiList() {
        if (this.mMimojiRecylerView == null || getActivity() == null || this.mimojiStatusManager2.IsLoading()) {
            return -1;
        }
        getActivity().runOnUiThread(new c(this));
        return 0;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiBottomList
    public int refreshMimojiList(int i) {
        return 0;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.fragment.live.FragmentLiveBase
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        this.mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
        ModeCoordinatorImpl.getInstance().attachProtocol(248, this);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiBottomList
    public int switchMimojiList() {
        if (this.mimojiStatusManager2.IsLoading()) {
            return -1;
        }
        FolmeUtils.animateHide(this.mMimojiRecylerView);
        refreshMimojiList();
        FolmeUtils.animateShow(this.mMimojiRecylerView);
        return 0;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.fragment.live.FragmentLiveBase
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        BubbleEditMimojiPresenter2 bubbleEditMimojiPresenter22 = this.bubbleEditMimojiPresenter2;
        if (bubbleEditMimojiPresenter22 != null) {
            bubbleEditMimojiPresenter22.processBubbleAni(-2, -2, null);
        }
        ModeCoordinatorImpl.getInstance().detachProtocol(248, this);
        this.mimojiStatusManager2.setMimojiPanelState(0);
        HandlerThread handlerThread = this.mHanderThread;
        if (handlerThread != null) {
            handlerThread.quit();
            this.mHanderThread = null;
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeMessages(65520);
            this.mHandler = null;
        }
    }
}
