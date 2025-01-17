package com.android.camera.features.mimoji2.fragment.edit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.data.DataRepository;
import com.android.camera.features.mimoji2.bean.MimojiInfo2;
import com.android.camera.features.mimoji2.bean.MimojiLevelBean2;
import com.android.camera.features.mimoji2.bean.MimojiTypeBean2;
import com.android.camera.features.mimoji2.fragment.edit.EditLevelListAdapter2;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.utils.ClickCheck2;
import com.android.camera.features.mimoji2.widget.MimojiEditGLTextureView2;
import com.android.camera.features.mimoji2.widget.autoselectview.AutoSelectAdapter;
import com.android.camera.features.mimoji2.widget.autoselectview.AutoSelectHorizontalView;
import com.android.camera.features.mimoji2.widget.helper.AvatarEngineManager2;
import com.android.camera.features.mimoji2.widget.helper.MimojiHelper2;
import com.android.camera.features.mimoji2.widget.helper.MimojiPageChangeAnimManager2;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.arcsoft.avatar2.AvatarConfig;
import com.arcsoft.avatar2.AvatarEngine;
import com.arcsoft.avatar2.emoticon.EmoInfo;
import io.reactivex.Completable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class FragmentMimojiEdit2 extends BaseFragment implements MimojiModeProtocol.MimojiEditor2, View.OnClickListener, ModeProtocol.HandleBackTrace {
    private static final int EDIT_ABANDON = 4;
    private static final int EDIT_ABANDON_CAPTURE = 3;
    private static final int EDIT_BACK = 1;
    private static final int EDIT_CANCEL = 5;
    public static final int EDIT_STATE_CREATE = 2;
    private static final int EDIT_STATE_CREATE_EDITED = 3;
    public static final int EDIT_STATE_CREATE_EMOTICON = 7;
    public static final int EDIT_STATE_EMOTICON_BACK = 8;
    public static final int EDIT_STATE_POP = 4;
    private static final int EDIT_STATE_POP_EDITED = 5;
    public static final int EDIT_STATE_SAVE = 6;
    private static final int FRAGMENT_INFO = 65521;
    public static final String TAG = "FragmentMimojiEdit2";
    private int fromTag;
    private AvatarEngine mAvatar;
    private AvatarEngineManager2 mAvatarEngineManager2;
    private TextView mBackTextView;
    private TextView mConfirmTextView;
    private Context mContext;
    private TextView mCreateEmoticonTextView;
    private AlertDialog mCurrentAlertDialog;
    private String mCurrentConfigPath = "";
    private int mCurrentTopPannelState = -1;
    private EditLevelListAdapter2 mEditLevelListAdapter2;
    private boolean mEditState = false;
    private boolean mEnterFromMimoji = false;
    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new Handler() {
        /* class com.android.camera.features.mimoji2.fragment.edit.FragmentMimojiEdit2.AnonymousClass3 */

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 4) {
                Bitmap thumbnailBitmapFromData = MimojiHelper2.getThumbnailBitmapFromData((byte[]) message.obj, 200, 200);
                String format = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault()).format(new Date());
                String str = MimojiHelper2.CUSTOM_DIR + format + "/";
                String str2 = str + format + "config.dat";
                String str3 = str + format + "pic.png";
                FileUtils.saveBitmap(thumbnailBitmapFromData, str3);
                int saveConfig = FragmentMimojiEdit2.this.mAvatar.saveConfig(str2);
                FragmentMimojiEdit2.this.mAvatar.loadConfig(str2);
                Log.d(FragmentMimojiEdit2.TAG, "res = " + saveConfig + "  save path : " + str2);
                if (FragmentMimojiEdit2.this.mEnterFromMimoji) {
                    FileUtils.deleteFile(FragmentMimojiEdit2.this.mPopSaveDeletePath);
                }
                MimojiInfo2 mimojiInfo2 = new MimojiInfo2();
                mimojiInfo2.mConfigPath = str2;
                mimojiInfo2.mAvatarTemplatePath = AvatarEngineManager2.TEMPLATE_PATH_HUMAN;
                mimojiInfo2.mThumbnailUrl = str3;
                DataRepository.dataItemLive().getMimojiStatusManager2().setCurrentMimojiInfo(mimojiInfo2);
                int i2 = FragmentMimojiEdit2.this.mCurrentTopPannelState;
                if (i2 == 6) {
                    FragmentMimojiEdit2.this.goBack(false, true);
                } else if (i2 == 7) {
                    FragmentMimojiEdit2.this.showEmoticon();
                }
            } else if (i == 5) {
                Bundle bundle = (Bundle) message.obj;
                FragmentMimojiEdit2.this.mEditLevelListAdapter2.notifyThumbnailUpdate(bundle.getInt("TYPE"), bundle.getInt("OUTER"), bundle.getInt("INNER"));
            } else if (i == 6) {
                int selectType = AvatarEngineManager2.getInstance().getSelectType();
                boolean isColorSelected = AvatarEngineManager2.getInstance().isColorSelected();
                CopyOnWriteArrayList<MimojiLevelBean2> subConfigList = AvatarEngineManager2.getInstance().getSubConfigList(FragmentMimojiEdit2.this.mContext, selectType);
                boolean isNeedUpdate = AvatarEngineManager2.getInstance().isNeedUpdate(selectType);
                FragmentMimojiEdit2.this.mEditLevelListAdapter2.refreshData(subConfigList, !isNeedUpdate, isColorSelected);
                if (isNeedUpdate) {
                    FragmentMimojiEdit2.this.mRenderThread.draw(false);
                }
            }
        }
    };
    private boolean mIsStartEdit;
    private RecyclerView mLevelRecyleView;
    private MimojiEditGLTextureView2 mMimojiEditGLTextureView2;
    private View mMimojiEditViewLayout;
    private MimojiPageChangeAnimManager2 mMimojiPageChangeAnimManager2;
    private AutoSelectAdapter<MimojiTypeBean2> mMimojiTypeAdapter;
    private AutoSelectHorizontalView mMimojiTypeSelectView;
    private TextView mMimojiTypeView;
    private LinearLayout mOperateSelectLayout;
    private String mPopSaveDeletePath = "";
    private MimojiThumbnailRenderThread2 mRenderThread;
    private LinearLayout mRlAllEditContent;
    private RelativeLayout mRlMainLayout;
    private RelativeLayout mRlNavigationlayout;
    private TextView mSaveFinishTextView;
    private volatile boolean mSetupCompleted = false;

    private void initConfigList() {
        EditLevelListAdapter2 editLevelListAdapter2;
        this.mRenderThread.initAvatar(this.mEnterFromMimoji ? this.mCurrentConfigPath : AvatarEngineManager2.TempOriginalConfigPath);
        AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue = new AvatarConfig.ASAvatarConfigValue();
        this.mAvatar.getConfigValue(aSAvatarConfigValue);
        this.mAvatarEngineManager2.setASAvatarConfigValue(aSAvatarConfigValue);
        this.mAvatarEngineManager2.setConfigTypeList(this.mAvatar.getSupportConfigType(this.mAvatarEngineManager2.getASAvatarConfigValue().gender));
        if (this.mLevelRecyleView.getAdapter() == null || (editLevelListAdapter2 = this.mEditLevelListAdapter2) == null) {
            if (this.mEditLevelListAdapter2 == null) {
                this.mEditLevelListAdapter2 = new EditLevelListAdapter2(this.mContext, new e(this));
            }
            this.mLevelRecyleView.setAdapter(this.mEditLevelListAdapter2);
        } else {
            editLevelListAdapter2.setLevelDatas(null);
            this.mEditLevelListAdapter2.notifyDataSetChanged();
        }
        this.mEditLevelListAdapter2.setIsColorNeedNotify(true);
        if (this.mMimojiTypeAdapter == null) {
            this.mMimojiTypeAdapter = new AutoSelectAdapter<>(null);
            this.mMimojiTypeAdapter.setOnSelectListener(new c(this));
        }
        ArrayList<AvatarConfig.ASAvatarConfigType> configTypeList = AvatarEngineManager2.getInstance().getConfigTypeList();
        ArrayList<MimojiTypeBean2> arrayList = new ArrayList<>();
        Iterator<AvatarConfig.ASAvatarConfigType> it = configTypeList.iterator();
        while (it.hasNext()) {
            AvatarConfig.ASAvatarConfigType next = it.next();
            ArrayList<AvatarConfig.ASAvatarConfigInfo> config = AvatarEngineManager2.getInstance().queryAvatar().getConfig(next.configType, AvatarEngineManager2.getInstance().getASAvatarConfigValue().gender);
            String str = TAG;
            Log.i(str, "putConfigList:" + next.configTypeDesc + ":" + next.configType);
            AvatarEngineManager2.getInstance().putConfigList(next.configType, config);
            if (!AvatarEngineManager2.filterTypeTitle(next.configType)) {
                MimojiTypeBean2 mimojiTypeBean2 = new MimojiTypeBean2();
                String replaceTabTitle = AvatarEngineManager2.replaceTabTitle(getContext(), next.configType);
                mimojiTypeBean2.setText(replaceTabTitle + "");
                mimojiTypeBean2.setCurLength(this.mMimojiTypeView.getPaint().measureText(mimojiTypeBean2.getText()));
                mimojiTypeBean2.setCurTotalLength(arrayList.size() > 0 ? arrayList.get(arrayList.size() - 1).getCurTotalLength() + mimojiTypeBean2.getCurLength() : mimojiTypeBean2.getCurLength());
                mimojiTypeBean2.setAlpha(0);
                mimojiTypeBean2.setASAvatarConfigType(next);
                arrayList.add(mimojiTypeBean2);
            }
        }
        if (arrayList.size() == 0) {
            Log.e(TAG, " initConfigList() size 0 error");
            MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
            if (mimojiAvatarEngine2 != null) {
                mimojiAvatarEngine2.initMimojiResource();
                initConfigList();
                return;
            }
            Log.e(TAG, " initConfigList() mimojiAvatarEngine2 null");
            return;
        }
        this.mMimojiTypeAdapter.setDataList(arrayList);
        this.mMimojiTypeSelectView.setAdapter(this.mMimojiTypeAdapter);
        if (201 == this.fromTag) {
            updateTitleState(4);
        } else {
            updateTitleState(2);
        }
    }

    private void initMimojiEdit(View view) {
        this.mRlMainLayout = (RelativeLayout) view.findViewById(R.id.rl_main_layout);
        this.mRlNavigationlayout = (RelativeLayout) view.findViewById(R.id.rv_navigation_layout);
        this.mRlNavigationlayout.setOnClickListener(this);
        ((RelativeLayout) view.findViewById(R.id.rl_fragment_mimoji_edit_container)).setOnClickListener(this);
        this.mRlAllEditContent = (LinearLayout) view.findViewById(R.id.ll_bottom_editoperate_content);
        this.mCreateEmoticonTextView = (TextView) view.findViewById(R.id.tv_create_emoticon);
        this.mCreateEmoticonTextView.setOnClickListener(this);
        this.mSaveFinishTextView = (TextView) view.findViewById(R.id.tv_save_finish);
        this.mSaveFinishTextView.setOnClickListener(this);
        this.mBackTextView = (TextView) view.findViewById(R.id.tv_back);
        this.mBackTextView.setOnClickListener(this);
        this.mConfirmTextView = (TextView) view.findViewById(R.id.btn_confirm);
        this.mConfirmTextView.setOnClickListener(this);
        this.mMimojiEditGLTextureView2 = (MimojiEditGLTextureView2) view.findViewById(R.id.mimoji_edit_preview);
        this.mMimojiEditGLTextureView2.setHandler(this.mHandler);
        this.mOperateSelectLayout = (LinearLayout) view.findViewById(R.id.operate_select_layout);
        this.mOperateSelectLayout.setVisibility(8);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mOperateSelectLayout.getLayoutParams();
        layoutParams.bottomMargin = Util.sNavigationBarHeight + getResources().getDimensionPixelOffset(R.dimen.mimoji_emoticon_margin_bottom_action);
        this.mOperateSelectLayout.setLayoutParams(layoutParams);
        FolmeUtils.touchButtonTint(R.color.mimoji_btn_pressed_bg, this.mSaveFinishTextView, this.mCreateEmoticonTextView);
        if (this.mMimojiTypeView == null) {
            this.mMimojiTypeView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.mimoij_type_item, (ViewGroup) null, false).findViewById(R.id.tv_type);
        }
        this.mMimojiTypeSelectView = (AutoSelectHorizontalView) view.findViewById(R.id.mimoji_type_view);
        this.mMimojiTypeSelectView.getItemAnimator().setChangeDuration(0);
        this.mLevelRecyleView = (RecyclerView) view.findViewById(R.id.color_level);
        this.mLevelRecyleView.setFocusable(false);
        if (this.mLevelRecyleView.getLayoutManager() == null) {
            LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(this.mContext, "color_level");
            linearLayoutManagerWrapper.setOrientation(1);
            this.mLevelRecyleView.setLayoutManager(linearLayoutManagerWrapper);
        }
        this.mEditLevelListAdapter2 = new EditLevelListAdapter2(this.mContext, new EditLevelListAdapter2.ItfGvOnItemClickListener() {
            /* class com.android.camera.features.mimoji2.fragment.edit.FragmentMimojiEdit2.AnonymousClass2 */

            @Override // com.android.camera.features.mimoji2.fragment.edit.EditLevelListAdapter2.ItfGvOnItemClickListener
            public void notifyUIChanged() {
                FragmentMimojiEdit2.this.mEditState = true;
                if (FragmentMimojiEdit2.this.fromTag == 203) {
                    FragmentMimojiEdit2.this.updateTitleState(3);
                } else {
                    FragmentMimojiEdit2.this.updateTitleState(5);
                }
            }
        });
        this.mLevelRecyleView.setAdapter(this.mEditLevelListAdapter2);
        this.mMimojiPageChangeAnimManager2 = new MimojiPageChangeAnimManager2();
        this.mMimojiPageChangeAnimManager2.initView(this.mContext, this.mMimojiEditGLTextureView2, this.mRlAllEditContent, 2);
    }

    private void resetData() {
        this.mHandler.removeMessages(6);
        this.mHandler.removeMessages(16);
        this.mAvatarEngineManager2.resetData();
        this.mEditLevelListAdapter2.setIsColorNeedNotify(true);
        this.mEditLevelListAdapter2.setLevelDatas(AvatarEngineManager2.getInstance().getSubConfigList(this.mContext, AvatarEngineManager2.getInstance().getSelectType()));
        if (this.mRenderThread.getIsRendering()) {
            this.mRenderThread.setResetStopRender(true);
        } else {
            this.mRenderThread.draw(true);
        }
        this.mEditLevelListAdapter2.notifyDataSetChanged();
        String str = TAG;
        Log.i(str, "resetData   mEnterFromMimoji :" + this.mEnterFromMimoji);
        this.mAvatar.loadConfig(this.mEnterFromMimoji ? this.mCurrentConfigPath : AvatarEngineManager2.TempOriginalConfigPath);
    }

    private void setupAvatar() {
        Log.d(TAG, "setup avatar");
        this.mSetupCompleted = false;
        this.mAvatarEngineManager2 = AvatarEngineManager2.getInstance();
        this.mAvatar = this.mAvatarEngineManager2.queryAvatar();
        this.mAvatar.loadColorValue(AvatarEngineManager2.TEMPLATE_PATH_HUMAN);
        if (!this.mEnterFromMimoji) {
            this.mAvatar.setTemplatePath(AvatarEngineManager2.TEMPLATE_PATH_HUMAN);
        }
        AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue = new AvatarConfig.ASAvatarConfigValue();
        this.mAvatar.getConfigValue(aSAvatarConfigValue);
        this.mAvatarEngineManager2.setASAvatarConfigValue(aSAvatarConfigValue);
        this.mAvatarEngineManager2.setASAvatarConfigValueDefault(aSAvatarConfigValue);
        this.mAvatar.setRenderScene(false, 0.85f);
        this.mRenderThread = new MimojiThumbnailRenderThread2("MimojiEdit", 500, 500, this.mContext);
        this.mRenderThread.start();
        this.mRenderThread.waitUntilReady();
        this.mRenderThread.setUpdateHandler(this.mHandler);
        EditLevelListAdapter2 editLevelListAdapter2 = this.mEditLevelListAdapter2;
        if (editLevelListAdapter2 != null) {
            editLevelListAdapter2.setRenderThread(this.mRenderThread);
        }
        this.mAvatarEngineManager2.initUpdatePara();
        this.mAvatar.saveConfig(AvatarEngineManager2.TempOriginalConfigPath);
        this.mSetupCompleted = true;
        MimojiEditGLTextureView2 mimojiEditGLTextureView2 = this.mMimojiEditGLTextureView2;
        if (mimojiEditGLTextureView2 != null) {
            mimojiEditGLTextureView2.setIsStopRenderForce(false);
            this.mMimojiEditGLTextureView2.setStopRender(false);
        }
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (mimojiAvatarEngine2 != null) {
            mimojiAvatarEngine2.onMimojiInitFinish();
        }
    }

    private void showAlertDialog(final int i) {
        if (this.mCurrentAlertDialog == null) {
            int i2 = i != 1 ? i != 3 ? (i == 4 || i == 5) ? R.string.mimoji_edit_abandon_alert : -1 : R.string.mimoji_edit_abandon_capture_alert : R.string.mimoji_edit_cancel_alert;
            if (i2 != -1) {
                this.mCurrentAlertDialog = RotateDialogController.showSystemAlertDialog(getActivity(), getString(i2), null, getString(R.string.mimoji_confirm), new Runnable() {
                    /* class com.android.camera.features.mimoji2.fragment.edit.FragmentMimojiEdit2.AnonymousClass4 */

                    public void run() {
                        boolean z = i == 1;
                        if (!z && FragmentMimojiEdit2.this.mIsStartEdit) {
                            FragmentMimojiEdit2.this.mAvatar.loadConfig(FragmentMimojiEdit2.this.mEnterFromMimoji ? FragmentMimojiEdit2.this.mCurrentConfigPath : AvatarEngineManager2.TempOriginalConfigPath);
                        }
                        FragmentMimojiEdit2.this.goBack(z, false);
                        int i = i;
                        if (i == 1) {
                            CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_PREVIEW_MID_BACK, MistatsConstants.Mimoji.PREVIEW_MID);
                        } else if (i == 3) {
                            CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_PREVIEW_MID_SOFT_BACK, MistatsConstants.Mimoji.PREVIEW_MID);
                        } else if (i == 4) {
                            CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_EDIT_SOFT_BACK, MistatsConstants.BaseEvent.EDIT);
                        } else if (i == 5) {
                            CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_EDIT_CANCEL, MistatsConstants.Mimoji.PREVIEW_MID);
                        }
                    }
                }, null, null, getString(R.string.mimoji_cancle), null);
                this.mCurrentAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    /* class com.android.camera.features.mimoji2.fragment.edit.FragmentMimojiEdit2.AnonymousClass5 */

                    public void onDismiss(DialogInterface dialogInterface) {
                        FragmentMimojiEdit2.this.mCurrentAlertDialog = null;
                    }
                });
            }
        }
    }

    public /* synthetic */ void a(MimojiTypeBean2 mimojiTypeBean2, int i) {
        String str = TAG;
        Log.v(str, "onSelectListener position  : " + i);
        this.mMimojiPageChangeAnimManager2.updateLayoutPosition();
        EditLevelListAdapter2 editLevelListAdapter2 = this.mEditLevelListAdapter2;
        if (editLevelListAdapter2 != null) {
            editLevelListAdapter2.setIsColorNeedNotify(true);
        }
        AvatarConfig.ASAvatarConfigType aSAvatarConfigType = mimojiTypeBean2.getASAvatarConfigType();
        MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
        if (!(mimojiEditor2 == null || aSAvatarConfigType == null)) {
            mimojiEditor2.onTypeConfigSelect(aSAvatarConfigType.configType);
        }
        this.mLevelRecyleView.scrollToPosition(0);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2
    public void createEmoticonPicture(List<EmoInfo> list) {
        MimojiThumbnailRenderThread2 mimojiThumbnailRenderThread2 = this.mRenderThread;
        if (mimojiThumbnailRenderThread2 == null || !mimojiThumbnailRenderThread2.isAlive()) {
            Log.e(TAG, "createEmoticonVideo mRenderThread null ");
            setupAvatar();
        }
        this.mRenderThread.drawForEmoticonPicture(list);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2
    public void createEmoticonThumbnail() {
        MimojiThumbnailRenderThread2 mimojiThumbnailRenderThread2 = this.mRenderThread;
        if (mimojiThumbnailRenderThread2 == null || !mimojiThumbnailRenderThread2.isAlive()) {
            Log.e(TAG, "createEmoticonVideo mRenderThread null ");
            setupAvatar();
        }
        this.mRenderThread.drawForEmoticonThumbnail();
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2
    public void createEmoticonVideo(List<EmoInfo> list) {
        MimojiThumbnailRenderThread2 mimojiThumbnailRenderThread2 = this.mRenderThread;
        if (mimojiThumbnailRenderThread2 == null || !mimojiThumbnailRenderThread2.isAlive()) {
            Log.e(TAG, "createEmoticonVideo mRenderThread null ");
            setupAvatar();
        }
        this.mRenderThread.recordForEmoticonVideo(list);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2
    public void directlyEnterEditMode(MimojiInfo2 mimojiInfo2, int i) {
        String str = TAG;
        Log.d(str, "configPath = " + this.mCurrentConfigPath);
        this.mPopSaveDeletePath = mimojiInfo2.mPackPath;
        this.mCurrentConfigPath = mimojiInfo2.mConfigPath;
        this.mEnterFromMimoji = true;
        this.mIsStartEdit = true;
        DataRepository.dataItemLive().getMimojiStatusManager2().setMode(6);
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (actionProcessing != null) {
            actionProcessing.forceSwitchFront();
        }
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (mimojiAvatarEngine2 != null) {
            mimojiAvatarEngine2.setDisableSingleTapUp(true);
        }
        ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).disableMenuItem(true, 197, 193);
        startMimojiEdit(i);
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 65521;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_mimoji_edit2;
    }

    public void goBack(boolean z, boolean z2) {
        MimojiModeProtocol.MimojiBottomList mimojiBottomList;
        AvatarEngineManager2.getInstance().clear();
        DataRepository.dataItemLive().getMimojiStatusManager2().setMode(2);
        if (this.mMimojiEditGLTextureView2 != null) {
            releaseRender();
        }
        this.mCurrentTopPannelState = -1;
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (mimojiAvatarEngine2 != null) {
            if (z) {
                mimojiAvatarEngine2.backToPreview(z2, false);
                mimojiAvatarEngine2.onMimojiCreate();
            } else {
                ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onMimojiCreateBack();
            }
        }
        if (z2 && (mimojiBottomList = (MimojiModeProtocol.MimojiBottomList) ModeCoordinatorImpl.getInstance().getAttachProtocol(248)) != null) {
            mimojiBottomList.refreshMimojiList();
        }
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.updateMimojiBottomTipImage();
        }
        this.mEnterFromMimoji = false;
        this.mIsStartEdit = false;
        View view = this.mMimojiEditViewLayout;
        if (view != null) {
            view.setVisibility(8);
            this.mOperateSelectLayout.setVisibility(8);
            this.mRlAllEditContent.setVisibility(8);
        }
        this.mMimojiEditGLTextureView2.setVisibility(8);
        this.mRenderThread.quit();
        FragmentUtils.removeFragmentByTag(getFragmentManager(), TAG);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mContext = getActivity();
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        if (DataRepository.dataItemLive().getMimojiStatusManager2().getMode() != 8 && i == 1) {
            if (this.mIsStartEdit) {
                if (this.mCurrentTopPannelState != 8) {
                    showAlertDialog(4);
                } else {
                    goBack(false, false);
                }
                return true;
            } else if (this.fromTag == 203 && this.mCurrentTopPannelState == 2) {
                showAlertDialog(1);
                return true;
            } else if (this.mCurrentTopPannelState == 6) {
                showAlertDialog(4);
                return true;
            } else {
                View view = this.mMimojiEditViewLayout;
                if (!(view == null || view.getVisibility() == 8 || !DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiEdit())) {
                    showAlertDialog(3);
                    return true;
                }
            }
        }
        return false;
    }

    public void onClick(View view) {
        if (this.mSetupCompleted && ClickCheck2.getInstance().checkClickable()) {
            switch (view.getId()) {
                case R.id.btn_confirm:
                    updateTitleState(6);
                    return;
                case R.id.tv_back:
                    int i = this.fromTag;
                    if (i == 201) {
                        showAlertDialog(5);
                        return;
                    } else if (i == 203 && this.mCurrentTopPannelState == 2) {
                        showAlertDialog(1);
                        return;
                    } else if (this.mEditState) {
                        this.mEditState = false;
                        updateTitleState(2);
                        resetData();
                        CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_EDIT_RESET, MistatsConstants.BaseEvent.EDIT);
                        return;
                    } else {
                        return;
                    }
                case R.id.tv_create_emoticon:
                    updateTitleState(7);
                    showEmoticon();
                    return;
                case R.id.tv_save_finish:
                    quitAndSaveEdit(true);
                    return;
                default:
                    return;
            }
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2
    public void onDeviceRotationChange(int i) {
        MimojiEditGLTextureView2 mimojiEditGLTextureView2 = this.mMimojiEditGLTextureView2;
        if (mimojiEditGLTextureView2 != null) {
            mimojiEditGLTextureView2.onDeviceRotationChange(i);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        AvatarEngine avatarEngine = this.mAvatar;
        if (avatarEngine != null) {
            avatarEngine.saveConfig(this.mEnterFromMimoji ? this.mCurrentConfigPath : AvatarEngineManager2.TempEditConfigPath);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        ClickCheck2.getInstance().setForceDisabled(true);
        super.onResume();
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        ClickCheck2.getInstance().setForceDisabled(true);
        super.onStop();
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2
    public void onTypeConfigSelect(int i) {
        this.mAvatarEngineManager2.setIsColorSelected(false);
        this.mAvatarEngineManager2.setSelectType(i);
        if (!this.mRenderThread.getIsRendering()) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.what = 6;
            this.mHandler.sendMessage(obtainMessage);
            return;
        }
        this.mRenderThread.setStopRender(true);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        String str = TAG;
        Log.d(str, "provideAnimateElement, animateInElements" + list + "resetType = " + i2);
        View view = this.mMimojiEditViewLayout;
        if (view != null && view.getVisibility() == 0 && i2 == 3) {
            Log.d(TAG, "mimoji edit timeout");
            goBack(false, false);
            DataRepository.dataItemLive().getMimojiStatusManager2().reset();
            AlertDialog alertDialog = this.mCurrentAlertDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.mCurrentAlertDialog = null;
            }
            ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).getAnimationComposite().remove(getFragmentInto());
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2
    public void quitAndSaveEdit(boolean z) {
        if (this.mIsStartEdit) {
            AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue = new AvatarConfig.ASAvatarConfigValue();
            this.mAvatar.getConfigValue(aSAvatarConfigValue);
            Map<String, String> mimojiConfigValue = AvatarEngineManager2.getMimojiConfigValue(aSAvatarConfigValue);
            mimojiConfigValue.put(MistatsConstants.BaseEvent.EVENT_NAME, "click");
            if (this.mEnterFromMimoji) {
                mimojiConfigValue.put(MistatsConstants.Mimoji.PARAM_MIMOJI_EDIT_COUNT, "second");
                CameraStatUtils.trackMimojiSavePara(MistatsConstants.Mimoji.MIMOJI_CLICK_EDIT_SAVE, mimojiConfigValue);
            } else {
                mimojiConfigValue.put(MistatsConstants.Mimoji.PARAM_MIMOJI_EDIT_COUNT, "first");
                CameraStatUtils.trackMimojiSavePara(MistatsConstants.Mimoji.MIMOJI_CLICK_EDIT_SAVE, mimojiConfigValue);
            }
        } else {
            CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_PREVIEW_MID_SAVE, MistatsConstants.Mimoji.PREVIEW_MID);
        }
        if (z) {
            View view = this.mMimojiEditViewLayout;
            if (view != null) {
                view.setVisibility(8);
                this.mOperateSelectLayout.setVisibility(8);
                this.mRlAllEditContent.setVisibility(8);
            }
            this.mMimojiEditGLTextureView2.setVisibility(8);
            this.mCurrentTopPannelState = 6;
            this.mMimojiEditGLTextureView2.setSaveConfigThum(true);
            return;
        }
        goBack(false, false);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2
    public void quitCoverEmoticon() {
        MimojiThumbnailRenderThread2 mimojiThumbnailRenderThread2 = this.mRenderThread;
        if (mimojiThumbnailRenderThread2 != null) {
            mimojiThumbnailRenderThread2.quitEmoticonVideo();
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2
    public void reInitMimojiEdit() {
        updateTitleState(8);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(247, this);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2
    public void releaseRender() {
        MimojiEditGLTextureView2 mimojiEditGLTextureView2 = this.mMimojiEditGLTextureView2;
        if (mimojiEditGLTextureView2 != null) {
            mimojiEditGLTextureView2.setIsStopRenderForce(true);
            this.mMimojiEditGLTextureView2.queueEvent(new b(this));
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2
    public void requestRender(boolean z) {
        MimojiEditGLTextureView2 mimojiEditGLTextureView2 = this.mMimojiEditGLTextureView2;
        if (mimojiEditGLTextureView2 != null && mimojiEditGLTextureView2.isAvailable() && this.mMimojiEditGLTextureView2.isAttachedToWindow()) {
            this.mMimojiEditGLTextureView2.setStopRender(z);
            this.mMimojiEditGLTextureView2.requestRender();
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2
    public void resetClickEnable(boolean z) {
        ClickCheck2.getInstance().setForceDisabled(!z);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2
    public void resetConfig() {
        MimojiEditGLTextureView2 mimojiEditGLTextureView2 = this.mMimojiEditGLTextureView2;
        if (mimojiEditGLTextureView2 == null) {
            Log.d(TAG, "resetConfig view NULL, UI need init ");
            return;
        }
        mimojiEditGLTextureView2.setStopRender(true);
        this.mAvatarEngineManager2 = AvatarEngineManager2.getInstance();
        this.mAvatar = this.mAvatarEngineManager2.queryAvatar();
        this.mAvatar.loadConfig(this.mEnterFromMimoji ? this.mCurrentConfigPath : AvatarEngineManager2.TempEditConfigPath);
        this.mAvatar.setRenderScene(false, 0.85f);
        this.mMimojiEditGLTextureView2.setIsStopRenderForce(false);
        this.mMimojiEditGLTextureView2.setStopRender(false);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2
    public void showEmoticon() {
        Context context = this.mContext;
        if (context != null) {
            ((Activity) context).runOnUiThread(new d(this));
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2
    public void startMimojiEdit(final int i) {
        LinearLayout.LayoutParams layoutParams;
        String str = TAG;
        Log.d(str, "startMimojiEdit：" + i);
        this.mSetupCompleted = false;
        if (this.mMimojiEditViewLayout == null) {
            this.mMimojiEditViewLayout = getView();
            initMimojiEdit(this.mMimojiEditViewLayout);
        }
        RecyclerView recyclerView = this.mLevelRecyleView;
        if (!(recyclerView == null || (layoutParams = (LinearLayout.LayoutParams) recyclerView.getLayoutParams()) == null)) {
            if (!Util.isFullScreenNavBarHidden(getContext())) {
                layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.mimoji_edit_config_bottom);
            } else {
                layoutParams.bottomMargin = 0;
            }
            this.mLevelRecyleView.setLayoutParams(layoutParams);
        }
        ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).getAnimationComposite().put(getFragmentInto(), this);
        this.mRlMainLayout.setVisibility(0);
        this.mRlNavigationlayout.setVisibility(0);
        this.mMimojiEditViewLayout.setVisibility(0);
        this.mMimojiEditGLTextureView2.setStopRender(true);
        this.mMimojiEditGLTextureView2.setVisibility(4);
        this.fromTag = i;
        this.mMimojiEditViewLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /* class com.android.camera.features.mimoji2.fragment.edit.FragmentMimojiEdit2.AnonymousClass1 */

            public void onGlobalLayout() {
                FragmentMimojiEdit2.this.mMimojiEditViewLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                FragmentMimojiEdit2.this.mMimojiEditGLTextureView2.setVisibility(0);
                if (i == 201) {
                    FragmentMimojiEdit2.this.mMimojiPageChangeAnimManager2.resetLayoutPosition(4);
                } else {
                    FragmentMimojiEdit2.this.mMimojiPageChangeAnimManager2.resetLayoutPosition(2);
                }
            }
        });
        setupAvatar();
        if (i != 204) {
            initConfigList();
            return;
        }
        this.mRenderThread.initAvatar(this.mEnterFromMimoji ? this.mCurrentConfigPath : AvatarEngineManager2.TempOriginalConfigPath);
        showEmoticon();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(247, this);
        this.mIsStartEdit = false;
    }

    public void updateTitleState(int i) {
        switch (i) {
            case 2:
                this.mCurrentTopPannelState = 2;
                this.mRlAllEditContent.setVisibility(0);
                LinearLayout linearLayout = this.mOperateSelectLayout;
                if (linearLayout != null) {
                    linearLayout.setVisibility(8);
                }
                this.mBackTextView.setVisibility(0);
                this.mBackTextView.setTextColor(getResources().getColor(R.color.white));
                this.mBackTextView.setText(getResources().getString(R.string.mimoji_recapture));
                this.mBackTextView.setClickable(true);
                this.mConfirmTextView.setVisibility(0);
                this.mConfirmTextView.setTextColor(getResources().getColor(R.color.white));
                this.mConfirmTextView.setText(getResources().getString(R.string.mimoji_save));
                this.mConfirmTextView.setClickable(true);
                return;
            case 3:
                this.mCurrentTopPannelState = 3;
                this.mRlAllEditContent.setVisibility(0);
                LinearLayout linearLayout2 = this.mOperateSelectLayout;
                if (linearLayout2 != null) {
                    linearLayout2.setVisibility(8);
                }
                this.mBackTextView.setVisibility(0);
                this.mConfirmTextView.setVisibility(0);
                this.mBackTextView.setTextColor(getResources().getColor(R.color.white));
                this.mConfirmTextView.setTextColor(getResources().getColor(R.color.white));
                this.mConfirmTextView.setClickable(true);
                this.mBackTextView.setClickable(true);
                this.mConfirmTextView.setClickable(true);
                this.mConfirmTextView.setText(getResources().getString(R.string.mimoji_save));
                this.mBackTextView.setText(getResources().getString(R.string.mimoji_reset));
                return;
            case 4:
                this.mCurrentTopPannelState = 4;
                this.mRlAllEditContent.setVisibility(0);
                LinearLayout linearLayout3 = this.mOperateSelectLayout;
                if (linearLayout3 != null) {
                    linearLayout3.setVisibility(8);
                }
                this.mBackTextView.setVisibility(0);
                this.mConfirmTextView.setVisibility(0);
                this.mBackTextView.setTextColor(getResources().getColor(R.color.white));
                this.mBackTextView.setClickable(true);
                this.mBackTextView.setText(getResources().getString(R.string.mimoji_cancle));
                this.mConfirmTextView.setText(getResources().getString(R.string.mimoji_save));
                this.mConfirmTextView.setTextColor(getResources().getColor(R.color.white_alpha_4d));
                this.mConfirmTextView.setClickable(false);
                return;
            case 5:
                LinearLayout linearLayout4 = this.mOperateSelectLayout;
                if (linearLayout4 != null) {
                    linearLayout4.setVisibility(8);
                }
                this.mConfirmTextView.setTextColor(getResources().getColor(R.color.white));
                this.mConfirmTextView.setClickable(true);
                return;
            case 6:
            case 8:
                this.mCurrentTopPannelState = i;
                this.mBackTextView.setVisibility(8);
                this.mConfirmTextView.setVisibility(8);
                this.mMimojiPageChangeAnimManager2.initView(this.mContext, this.mMimojiEditGLTextureView2, this.mRlAllEditContent, 6);
                this.mRlAllEditContent.setVisibility(8);
                LinearLayout linearLayout5 = this.mOperateSelectLayout;
                if (linearLayout5 != null) {
                    linearLayout5.setVisibility(0);
                    return;
                }
                return;
            case 7:
                this.mCurrentTopPannelState = 7;
                return;
            default:
                return;
        }
    }

    public /* synthetic */ void wa() {
        this.mEditState = true;
        if (this.fromTag == 203) {
            updateTitleState(3);
            this.mMimojiPageChangeAnimManager2.resetLayoutPosition(4);
            return;
        }
        updateTitleState(5);
    }

    public /* synthetic */ void xa() {
        if (this.mAvatar != null) {
            Log.d(TAG, "avatar releaseRender 2");
            this.mAvatar.releaseRender();
        }
    }

    public /* synthetic */ void ya() {
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(19);
        }
        DataRepository.dataItemLive().getMimojiStatusManager2().setMode(8);
        CameraStatUtils.trackMimoji2Click(null, MistatsConstants.Mimoji.MIMOJI_STICKER_PACK);
        boolean z = this.fromTag == 204;
        MimojiModeProtocol.MimojiEditor2.MimojiEmoticon mimojiEmoticon = (MimojiModeProtocol.MimojiEditor2.MimojiEmoticon) ModeCoordinatorImpl.getInstance().getAttachProtocol(250);
        if (mimojiEmoticon != null) {
            mimojiEmoticon.setIsBackToPreview(z);
        }
        if (z) {
            this.mCurrentTopPannelState = -1;
            if (this.mMimojiEditViewLayout != null) {
                this.mOperateSelectLayout.setVisibility(8);
                this.mRlMainLayout.setVisibility(8);
                this.mRlNavigationlayout.setVisibility(8);
            }
            AvatarEngineManager2.getInstance().clear();
            this.mEnterFromMimoji = false;
            this.mIsStartEdit = false;
            return;
        }
        updateTitleState(5);
    }
}
