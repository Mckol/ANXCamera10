package com.android.camera.fragment.beauty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import androidx.annotation.Nullable;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.beauty.SingleCheckAdapter;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.SeekBarCompat;
import java.util.List;

public class BeautySmoothLevelFragment extends BaseBeautyFragment {
    private static final int INTERVAL = 5;
    private SeekBarCompat mAdjustSeekBar;
    private boolean mIsRTL;
    private int mSeekBarMaxProgress = 100;

    private void initView(View view) {
        this.mIsRTL = Util.isLayoutRTL(getContext());
        this.mAdjustSeekBar = (SeekBarCompat) view.findViewById(R.id.beauty_level_adjust_seekbar);
        initBeautyItems();
        this.mSeekBarMaxProgress = 100;
        int faceBeautyRatio = CameraSettings.getFaceBeautyRatio("pref_beautify_skin_smooth_ratio_key");
        int defaultValueByKey = BeautyConstant.getDefaultValueByKey("pref_beautify_skin_smooth_ratio_key");
        this.mAdjustSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar_style));
        this.mAdjustSeekBar.setMax(this.mSeekBarMaxProgress);
        this.mAdjustSeekBar.setSeekBarPinProgress(defaultValueByKey);
        this.mAdjustSeekBar.setProgress(faceBeautyRatio, false);
        this.mAdjustSeekBar.setOnSeekBarChangeListener(new SeekBarCompat.OnSeekBarCompatChangeListener() {
            /* class com.android.camera.fragment.beauty.BeautySmoothLevelFragment.AnonymousClass1 */

            @Override // com.android.camera.ui.SeekBarCompat.OnSeekBarCompatChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                BeautySmoothLevelFragment.this.onLevelSelected(i, z);
            }

            @Override // com.android.camera.ui.SeekBarCompat.OnSeekBarCompatChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // com.android.camera.ui.SeekBarCompat.OnSeekBarCompatChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.mAdjustSeekBar.setOnSeekBarCompatTouchListener(new SeekBarCompat.OnSeekBarCompatTouchListener() {
            /* class com.android.camera.fragment.beauty.BeautySmoothLevelFragment.AnonymousClass2 */

            @Override // com.android.camera.ui.SeekBarCompat.OnSeekBarCompatTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 0) {
                    if (!BeautySmoothLevelFragment.this.mAdjustSeekBar.getTouchRect().contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                        return true;
                    }
                } else if (!(action == 1 || action == 2)) {
                    return false;
                }
                BeautySmoothLevelFragment.this.mAdjustSeekBar.setProgress(BeautySmoothLevelFragment.this.mAdjustSeekBar.getNextProgress(motionEvent.getX()), true);
                return true;
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onLevelSelected(int i, boolean z) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && z) {
            topAlert.alertUpdateValue(1, String.valueOf(this.mAdjustSeekBar.isCenterTwoWayMode() ? i / 2 : i));
        }
        DataRepository.dataItemGlobal().getCurrentMode();
        CameraSettings.setFaceBeautyRatio("pref_beautify_skin_smooth_ratio_key", i);
        ShineHelper.onBeautyChanged();
    }

    /* access modifiers changed from: protected */
    public int beautyLevelToPosition(int i, int i2) {
        return Util.clamp(i, 0, i2);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment
    public View getAnimateView() {
        return this.mAdjustSeekBar;
    }

    /* access modifiers changed from: protected */
    public List<SingleCheckAdapter.LevelItem> initBeautyItems() {
        return null;
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_beauty_smooth, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment, com.android.camera.fragment.BaseViewPagerFragment
    public void onViewCreatedAndJumpOut() {
        super.onViewCreatedAndJumpOut();
        this.mAdjustSeekBar.setEnabled(false);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.beauty.BaseBeautyFragment, com.android.camera.fragment.BaseViewPagerFragment
    public void onViewCreatedAndVisibleToUser(boolean z) {
        super.onViewCreatedAndVisibleToUser(z);
        this.mAdjustSeekBar.setEnabled(true);
    }

    /* access modifiers changed from: protected */
    public int provideItemHorizontalMargin() {
        return getResources().getDimensionPixelSize(R.dimen.beautycamera_beauty_level_item_margin);
    }

    public void setEnableClick(boolean z) {
        this.mAdjustSeekBar.setEnabled(z);
    }
}
