package com.android.camera.fragment.mode;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.Camera;
import com.android.camera.CameraSettings;
import com.android.camera.ModeEditorActivity;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import java.util.List;

public class FragmentMoreModeEdit extends FragmentMoreModeBase implements View.OnClickListener, ModeProtocol.HandleBackTrace {
    private static final String TAG = "MoreModeEdit";
    private AlertDialog mExitDialog;

    private void confirmEdit() {
        List<ComponentDataItem> createChangeItems = this.mModeAdapter.createChangeItems();
        int[] iArr = new int[createChangeItems.size()];
        for (int i = 0; i < createChangeItems.size(); i++) {
            iArr[i] = Integer.parseInt(createChangeItems.get(i).mValue);
        }
        DataRepository.dataItemGlobal().setSortModes(iArr);
        DataRepository.dataItemGlobal().editor().putBoolean(DataItemGlobal.DATA_COMMON_USER_EDIT_MODES, true).apply();
        this.mComponentModuleList.reInit(true);
        if (CameraSettings.isPopupMoreStyle()) {
            DataRepository.dataItemGlobal().setCurrentMode(163);
        }
        hide();
        CameraStatUtils.trackModeEditClick(MistatsConstants.EditMode.VALUE_EDIT_MODE_CLICK_CONFIRM);
        CameraStatUtils.trackModeEditInfo();
    }

    private boolean showExitConfirm() {
        CameraStatUtils.trackModeEditClick(MistatsConstants.EditMode.VALUE_EDIT_MODE_CLICK_EXIT);
        if (!this.mModeAdapter.isDataChange()) {
            hide();
            return false;
        } else if (this.mExitDialog != null) {
            return false;
        } else {
            this.mExitDialog = RotateDialogController.showSystemAlertDialog(getContext(), null, getString(R.string.mode_edit_exit_message), getString(R.string.mimoji_confirm), new Runnable() {
                /* class com.android.camera.fragment.mode.FragmentMoreModeEdit.AnonymousClass1 */

                public void run() {
                    CameraStatUtils.trackModeEditClick(MistatsConstants.EditMode.VALUE_EDIT_MODE_CLICK_EXIT_CONFIRM);
                    FragmentMoreModeEdit.this.mExitDialog.dismiss();
                    FragmentMoreModeEdit.this.hide();
                }
            }, null, null, getString(R.string.mode_edit_cancel), new Runnable() {
                /* class com.android.camera.fragment.mode.FragmentMoreModeEdit.AnonymousClass2 */

                public void run() {
                    CameraStatUtils.trackModeEditClick(MistatsConstants.EditMode.VALUE_EDIT_MODE_CLICK_EXIT_CANCEL);
                }
            });
            this.mExitDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                /* class com.android.camera.fragment.mode.FragmentMoreModeEdit.AnonymousClass3 */

                public void onDismiss(DialogInterface dialogInterface) {
                    FragmentMoreModeEdit.this.mExitDialog = null;
                }
            });
            return true;
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_MODES_EDIT;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.mode.FragmentMoreModeBase
    public RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 3, 1, false);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.fragment.mode.FragmentMoreModeBase
    public int getLayoutResourceId() {
        return R.layout.fragment_module_edit;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.mode.FragmentMoreModeBase
    public ModeItemDecoration getModeItemDecoration() {
        return new ModeItemDecoration(getContext(), getModeList(), getType());
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.mode.FragmentMoreModeBase
    public RecyclerView getModeList() {
        return (RecyclerView) this.mRootView.findViewById(R.id.mode_edit_list);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.mode.FragmentMoreModeBase
    public int getType() {
        return 2;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.mode.FragmentMoreModeBase
    public boolean hide() {
        ModeProtocol.ConfigChanges configChanges;
        if (getActivity() != null && (getActivity() instanceof ModeEditorActivity)) {
            getActivity().finish();
            return true;
        } else if (getActivity() == null || !(getActivity() instanceof Camera) || (configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)) == null) {
            return false;
        } else {
            configChanges.configModeEdit();
            return true;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.fragment.mode.FragmentMoreModeBase
    public void initView(View view) {
        super.initView(view);
        view.findViewById(R.id.mode_edit_done).setOnClickListener(this);
        view.findViewById(R.id.mode_edit_exit).setOnClickListener(this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        return showExitConfirm();
    }

    @Override // com.android.camera.fragment.mode.FragmentMoreModeBase
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mode_edit_done:
                confirmEdit();
                return;
            case R.id.mode_edit_exit:
                showExitConfirm();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    @CallSuper
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        Log.d(TAG, "register");
        registerBackStack(modeCoordinator, this);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    @CallSuper
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        Log.d(TAG, "unRegister");
        unRegisterBackStack(modeCoordinator, this);
    }
}
