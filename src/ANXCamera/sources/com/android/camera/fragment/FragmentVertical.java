package com.android.camera.fragment;

import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.protocol.ModeProtocol;
import io.reactivex.Completable;
import java.util.List;

public class FragmentVertical extends BaseFragment implements ModeProtocol.VerticalProtocol {
    private TextView mLeftAlertStatus;
    private TextView mLeftLightingPattern;
    private TextView mRightAlertStatus;
    private TextView mRightLightingPattern;
    private String mStateValueText = "";
    private View mVerticalViewMenu;
    private int oldDegree;
    private int stringLightingRes;

    private void adjustViewHeight(View view) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin = (Util.sWindowHeight - ((int) (((float) getResources().getDisplayMetrics().widthPixels) / 0.75f))) - Util.getBottomHeight();
    }

    private void updateLightingRelativeView(boolean z, boolean z2) {
        AlphaOutOnSubscribe.directSetResult(this.mRightAlertStatus);
        AlphaOutOnSubscribe.directSetResult(this.mRightLightingPattern);
        AlphaOutOnSubscribe.directSetResult(this.mLeftAlertStatus);
        AlphaOutOnSubscribe.directSetResult(this.mLeftLightingPattern);
        if (z) {
            this.stringLightingRes = -1;
            this.mStateValueText = "";
        } else if (!DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171).equals("0")) {
            if (!isLandScape()) {
                int i = this.oldDegree;
                if (i == 90) {
                    startAnimateViewGone(this.mRightAlertStatus, false);
                    startAnimateViewGone(this.mRightLightingPattern, false);
                    startAnimateViewGone(this.mLeftAlertStatus, z2);
                    startAnimateViewGone(this.mLeftLightingPattern, z2);
                } else if (i == 270) {
                    startAnimateViewGone(this.mLeftAlertStatus, false);
                    startAnimateViewGone(this.mLeftLightingPattern, false);
                    startAnimateViewGone(this.mRightAlertStatus, z2);
                    startAnimateViewGone(this.mRightLightingPattern, z2);
                }
            } else if (isLeftLandScape()) {
                if (!TextUtils.isEmpty(this.mStateValueText)) {
                    startAnimateViewVisible(this.mLeftAlertStatus, z2);
                }
                if (this.stringLightingRes > 0) {
                    startAnimateViewVisible(this.mLeftLightingPattern, z2);
                }
            } else if (isRightLandScape()) {
                if (!TextUtils.isEmpty(this.mStateValueText)) {
                    startAnimateViewVisible(this.mRightAlertStatus, z2);
                }
                if (this.stringLightingRes > 0) {
                    startAnimateViewVisible(this.mRightLightingPattern, z2);
                }
            }
            this.oldDegree = this.mDegree;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.VerticalProtocol
    public void alertLightingHint(int i) {
        int i2 = i != 3 ? i != 4 ? i != 5 ? -1 : R.string.lighting_hint_needed : R.string.lighting_hint_too_far : R.string.lighting_hint_too_close;
        if (i2 == -1) {
            this.mStateValueText = "";
            AlphaOutOnSubscribe.directSetResult(this.mRightAlertStatus);
            AlphaOutOnSubscribe.directSetResult(this.mLeftAlertStatus);
            return;
        }
        this.mStateValueText = getResources().getString(i2);
        this.mRightAlertStatus.setText(this.mStateValueText);
        this.mLeftAlertStatus.setText(this.mStateValueText);
        if (isLeftLandScape()) {
            AlphaInOnSubscribe.directSetResult(this.mLeftAlertStatus);
        } else if (isRightLandScape()) {
            AlphaInOnSubscribe.directSetResult(this.mRightAlertStatus);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_VERTICAL;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_vertical;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mVerticalViewMenu = view.findViewById(R.id.vertical_view_menu);
        Rect displayRect = Util.getDisplayRect(DataRepository.dataItemGlobal().getDisplayMode() == 2 ? 1 : 0);
        int i = displayRect.top;
        int height = displayRect.height();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mVerticalViewMenu.getLayoutParams();
        marginLayoutParams.topMargin = i;
        marginLayoutParams.height = height;
        this.mLeftAlertStatus = (TextView) view.findViewById(R.id.alert_status_value_left);
        this.mLeftLightingPattern = (TextView) view.findViewById(R.id.lighting_pattern_left);
        this.mRightAlertStatus = (TextView) view.findViewById(R.id.alert_status_value_right);
        this.mRightLightingPattern = (TextView) view.findViewById(R.id.lighting_pattern_right);
        this.oldDegree = this.mDegree;
        ViewCompat.setRotation(this.mLeftAlertStatus, 90.0f);
        ViewCompat.setRotation(this.mLeftLightingPattern, 90.0f);
        ViewCompat.setRotation(this.mRightAlertStatus, 270.0f);
        ViewCompat.setRotation(this.mRightLightingPattern, 270.0f);
    }

    @Override // com.android.camera.protocol.ModeProtocol.VerticalProtocol
    public boolean isAnyViewVisible() {
        return this.mLeftAlertStatus.getVisibility() == 0 || this.mRightAlertStatus.getVisibility() == 0 || this.mLeftLightingPattern.getVisibility() == 0 || this.mRightLightingPattern.getVisibility() == 0;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (isAnyViewVisible()) {
            updateLightingRelativeView(i2 == 3, false);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (this.mCurrentMode == 171) {
            updateLightingRelativeView(false, true);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(198, this);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(198, this);
    }
}
