package com.android.camera.fragment.sticker;

import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RadioGroup;
import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.camera.R;
import com.android.camera.animation.FragmentAnimationFactory;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.CtaNoticeFragment;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.NoScrollViewPager;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class FragmentSticker extends BaseFragment implements ModeProtocol.HandleBackTrace {
    public static final int FRAGMENT_INFO = 255;
    private ImageView mBackButton;
    private OnRadioButtonChangedListener mOnRadioButtonChangedListener;
    private RadioGroup mRadioGroup;
    private NoScrollViewPager mViewPager;

    public interface OnRadioButtonChangedListener {
        void onChanged();
    }

    private class StickerPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList;

        public StickerPagerAdapter(FragmentManager fragmentManager, List<Fragment> list) {
            super(fragmentManager);
            this.mFragmentList = list;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            List<Fragment> list = this.mFragmentList;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override // androidx.fragment.app.FragmentPagerAdapter
        public Fragment getItem(int i) {
            return this.mFragmentList.get(i);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 255;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_sticker;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        view.getLayoutParams().height = (int) (((float) getResources().getDisplayMetrics().heightPixels) - (((float) getResources().getDisplayMetrics().widthPixels) / 0.75f));
        this.mViewPager = (NoScrollViewPager) view.findViewById(R.id.viewPager);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new FragmentStickerPager());
        arrayList.add(new FragmentStickerPager());
        this.mViewPager.setAdapter(new StickerPagerAdapter(getChildFragmentManager(), arrayList));
        this.mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /* class com.android.camera.fragment.sticker.FragmentSticker.AnonymousClass1 */

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                if (i == 0) {
                    FragmentSticker.this.mRadioGroup.check(R.id.radio_button_sticker_one);
                } else if (i == 1) {
                    FragmentSticker.this.mRadioGroup.check(R.id.radio_button_sticker_two);
                }
            }
        });
        this.mRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group_layout_sticker);
        this.mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /* class com.android.camera.fragment.sticker.FragmentSticker.AnonymousClass2 */

            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.radio_button_sticker_one:
                        FragmentSticker.this.mViewPager.setCurrentItem(0, false);
                        break;
                    case R.id.radio_button_sticker_two:
                        FragmentSticker.this.mViewPager.setCurrentItem(1, false);
                        break;
                }
                if (FragmentSticker.this.mOnRadioButtonChangedListener != null) {
                    FragmentSticker.this.mOnRadioButtonChangedListener.onChanged();
                }
            }
        });
        this.mRadioGroup.check(R.id.radio_button_sticker_one);
        this.mBackButton = (ImageView) view.findViewById(R.id.effect_back);
        this.mBackButton.setOnClickListener(new View.OnClickListener() {
            /* class com.android.camera.fragment.sticker.FragmentSticker.AnonymousClass3 */

            public void onClick(View view) {
                ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                if (((BaseFragment) FragmentSticker.this).mCurrentMode == 161) {
                    ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).reInitTipImage();
                }
            }
        });
        CtaNoticeFragment.checkCta(getActivity().getFragmentManager(), 3);
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        return baseDelegate != null && baseDelegate.getActiveFragment(R.id.bottom_action) == 255;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideEnterAnimation(int i) {
        return FragmentAnimationFactory.wrapperAnimation(167, 161);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideExitAnimation(int i) {
        return FragmentAnimationFactory.wrapperAnimation(168, 162);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
    }

    public void setOnRadioButtonChangedListener(OnRadioButtonChangedListener onRadioButtonChangedListener) {
        this.mOnRadioButtonChangedListener = onRadioButtonChangedListener;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
    }
}
