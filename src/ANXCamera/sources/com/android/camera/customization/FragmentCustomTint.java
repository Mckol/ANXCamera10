package com.android.camera.customization;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.customization.TintColorTableView;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.log.Log;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.ui.ColorImageView;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

public class FragmentCustomTint extends BaseFragment implements TintColorTableView.OnColorChangeListener, View.OnClickListener, ViewPager.OnPageChangeListener {
    private static final int IMAGE_HEIGHT = 2340;
    private static final float IMAGE_RATIO = 0.46153846f;
    private static final int IMAGE_WIDTH = 1080;
    private static String LOG_TAG = "FragCustomTint";
    private static int MODE_PREVIEW = 2;
    private static int MODE_SELECT = 1;
    private static final int PADDING_BOTTOM_MAX = 160;
    private static final int PADDING_TOP_MAX = 110;
    private int PREVIEW_MODE_HEIGHT = (Util.sWindowHeight - Util.sNavigationBarHeight);
    private View actionBar;
    private View colorSelectContainer;
    private TintColorTableView colorView;
    private TintPreviewAdapter mAdapter;
    private int mCurrentIndex = 0;
    private View mCurrentItemView;
    private int mCurrentMode = MODE_SELECT;
    private ViewPager previewList;

    private class TintPreviewAdapter extends PagerAdapter {
        private final Context mContext;
        private final List<TintColor> mData;
        private View.OnClickListener mItemClickListener;

        public TintPreviewAdapter(Context context, List<TintColor> list, View.OnClickListener onClickListener) {
            this.mContext = context;
            this.mData = list;
            this.mItemClickListener = onClickListener;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(@NonNull ViewGroup viewGroup, int i, @NonNull Object obj) {
            viewGroup.removeView((View) obj);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.mData.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        @NonNull
        public Object instantiateItem(@NonNull ViewGroup viewGroup, int i) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_tint_preview_item, viewGroup, false);
            inflate.setTag(Integer.valueOf(i));
            ((ImageView) inflate.findViewById(R.id.custom_tint_item_image)).setImageResource(this.mData.get(i).previewResource());
            viewGroup.addView(inflate);
            View findViewById = inflate.findViewById(R.id.item_container);
            findViewById.setTag(Integer.valueOf(i));
            FragmentCustomTint.this.refreshPreviewItem(inflate);
            FragmentCustomTint.this.refreshPreviewImage(inflate);
            FragmentCustomTint fragmentCustomTint = FragmentCustomTint.this;
            fragmentCustomTint.refreshColorTable(fragmentCustomTint.getView());
            findViewById.setOnClickListener(this.mItemClickListener);
            return inflate;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }
    }

    private void enterPreviewMode(View view) {
        if (view instanceof FrameLayout) {
            this.mCurrentItemView = view;
            toggleMode(view);
        }
    }

    private int imageHeight() {
        return isSelectMode() ? selectModeHeight() : previewModeHeight();
    }

    private int imageWidth() {
        return isSelectMode() ? selectModeWidth() : Util.sWindowWidth;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isSelectMode() {
        return this.mCurrentMode == MODE_SELECT;
    }

    private int previewModeHeight() {
        int i = Util.sWindowHeight;
        if (Util.checkDeviceHasNavigationBar(getActivity())) {
            i -= Util.sNavigationBarHeight;
        }
        return i - (Util.isNotchDevice ? Util.getStatusBarHeight(getActivity()) : 0);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void refreshColorTable(View view) {
        ((RelativeLayout.LayoutParams) view.findViewById(R.id.color_select_view).getLayoutParams()).bottomMargin = getResources().getDimensionPixelSize(Util.checkDeviceHasNavigationBar(getActivity()) ? R.dimen.custom_tint_color_table_bottom_margin : R.dimen.custom_tint_color_table_bottom_margin_full);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void refreshPreviewImage(View view) {
        int i;
        int i2;
        ImageView imageView = (ImageView) view.findViewById(R.id.custom_tint_item_image);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.custom_tint_preview_image_margin);
        layoutParams.bottomMargin = isSelectMode() ? dimensionPixelSize : 0;
        layoutParams.topMargin = isSelectMode() ? dimensionPixelSize : 0;
        layoutParams.leftMargin = isSelectMode() ? dimensionPixelSize : 0;
        if (!isSelectMode()) {
            dimensionPixelSize = 0;
        }
        layoutParams.rightMargin = dimensionPixelSize;
        if (!isSelectMode()) {
            int imageHeight = imageHeight();
            int round = Math.round((((float) imageWidth()) * 2340.0f) / 1080.0f);
            if (round > imageHeight) {
                int i3 = round - imageHeight;
                int i4 = i3 / 2;
                i2 = Math.min(i3 - i4, 160) * -1;
                i = Math.min(i4, 110) * -1;
                imageView.setPadding(0, i, 0, i2);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            } else if (round < imageHeight) {
                i = (imageHeight - round) / 2;
                i2 = i;
                imageView.setPadding(0, i, 0, i2);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }
        i = 0;
        i2 = i;
        imageView.setPadding(0, i, 0, i2);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void refreshPreviewItem(View view) {
        int i = 0;
        view.findViewById(R.id.background).setVisibility(isSelectMode() ? 0 : 8);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.findViewById(R.id.item_container).getLayoutParams();
        layoutParams.width = imageWidth();
        layoutParams.height = imageHeight();
        if (isSelectMode()) {
            i = getResources().getDimensionPixelSize(R.dimen.custom_tint_preview_bottom_margin);
        }
        layoutParams.bottomMargin = i;
    }

    private int selectModeHeight() {
        int statusBarHeight = ((Util.sWindowHeight - Util.getStatusBarHeight(getActivity())) - getResources().getDimensionPixelSize(R.dimen.custom_tint_preview_bottom_height_full)) - getResources().getDimensionPixelSize(R.dimen.custom_tint_actionbar_height);
        if (Util.checkDeviceHasNavigationBar(getActivity())) {
            statusBarHeight -= Util.sNavigationBarHeight;
        }
        return (statusBarHeight - getResources().getDimensionPixelSize(R.dimen.custom_tint_preview_top_margin)) - getResources().getDimensionPixelSize(R.dimen.custom_tint_preview_bottom_margin);
    }

    private int selectModeWidth() {
        return Math.round(((float) selectModeHeight()) * IMAGE_RATIO);
    }

    private void toggleMode(View view) {
        int intValue = ((Integer) view.getTag()).intValue();
        if (this.mCurrentMode == MODE_SELECT) {
            String str = LOG_TAG;
            Log.d(str, "Enter preview mode for " + intValue);
            this.mCurrentMode = MODE_PREVIEW;
        } else {
            String str2 = LOG_TAG;
            Log.d(str2, "Enter select mode for " + intValue);
            this.mCurrentMode = MODE_SELECT;
        }
        AnonymousClass1 r0 = new TransitionListener() {
            /* class com.android.camera.customization.FragmentCustomTint.AnonymousClass1 */

            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                WindowManager.LayoutParams attributes = FragmentCustomTint.this.getActivity().getWindow().getAttributes();
                if (FragmentCustomTint.this.isSelectMode()) {
                    attributes.flags &= -1025;
                } else {
                    attributes.flags |= 1024;
                }
                FragmentCustomTint.this.getActivity().getWindow().setAttributes(attributes);
                int i = 8;
                FragmentCustomTint.this.actionBar.setVisibility(FragmentCustomTint.this.isSelectMode() ? 0 : 8);
                View view = FragmentCustomTint.this.colorSelectContainer;
                if (FragmentCustomTint.this.isSelectMode()) {
                    i = 0;
                }
                view.setVisibility(i);
                for (int i2 = 0; i2 < FragmentCustomTint.this.previewList.getChildCount(); i2++) {
                    View childAt = FragmentCustomTint.this.previewList.getChildAt(i2);
                    FragmentCustomTint.this.refreshPreviewItem(childAt);
                    FragmentCustomTint.this.refreshPreviewImage(childAt);
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
            }
        };
        AnimState add = new AnimState(MistatsConstants.BaseEvent.PREVIEW).add(ViewProperty.HEIGHT, previewModeHeight(), new long[0]).add(ViewProperty.WIDTH, Util.sWindowWidth, new long[0]);
        AnimState add2 = new AnimState("select").add(ViewProperty.HEIGHT, selectModeHeight(), new long[0]).add(ViewProperty.WIDTH, selectModeWidth(), new long[0]);
        if (isSelectMode()) {
            AnimConfig addListeners = new AnimConfig(ViewProperty.WIDTH, ViewProperty.HEIGHT).setEase(6, 300.0f).addListeners(r0);
            Folme.useAt(view).state().fromTo(add, add2, addListeners);
            return;
        }
        AnimConfig addListeners2 = new AnimConfig(ViewProperty.WIDTH, ViewProperty.HEIGHT).addListeners(r0);
        Folme.useAt(view).state().fromTo(add2, add, addListeners2);
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_CUSTOM_TINT;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.activity_tint;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mCurrentIndex = TintColor.readColorId();
        this.colorSelectContainer = view.findViewById(R.id.color_select_container);
        this.actionBar = view.findViewById(R.id.actionbar);
        ColorImageView colorImageView = (ColorImageView) view.findViewById(R.id.back);
        colorImageView.setColor(getResources().getColor(R.color.white_80));
        colorImageView.setOnClickListener(this);
        this.colorView = (TintColorTableView) view.findViewById(R.id.color_select_view);
        this.colorView.initialize(TintColor.getAvailableColors(), this.mCurrentIndex);
        this.colorView.setOnColorChangeListener(this);
        View findViewById = view.findViewById(R.id.apply);
        FolmeUtils.handleListItemTouch(findViewById);
        findViewById.setOnClickListener(this);
        this.mAdapter = new TintPreviewAdapter(getContext(), TintColor.getAvailableColors(), this);
        this.previewList = (ViewPager) view.findViewById(R.id.preview_list);
        this.previewList.setAdapter(this.mAdapter);
        this.previewList.setCurrentItem(this.mCurrentIndex);
        this.previewList.addOnPageChangeListener(this);
    }

    public boolean onBackPressed() {
        if (this.mCurrentMode != MODE_PREVIEW) {
            return false;
        }
        toggleMode(this.mCurrentItemView);
        return true;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.apply) {
            TintColor.persistColorId(this.mCurrentIndex);
            MistatsWrapper.settingClickEvent("attr_edit_tint", Integer.valueOf(this.mCurrentIndex));
            getActivity().finish();
        } else if (id != R.id.back) {
            enterPreviewMode(view);
        } else {
            getActivity().finish();
        }
    }

    @Override // com.android.camera.customization.TintColorTableView.OnColorChangeListener
    public void onColorChange(int i) {
        String str = LOG_TAG;
        Log.d(str, "onColorChange " + i);
        this.mCurrentIndex = i;
        this.previewList.setCurrentItem(i, true);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        ViewPager viewPager = this.previewList;
        if (viewPager != null) {
            viewPager.removeOnPageChangeListener(this);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        String str = LOG_TAG;
        Log.d(str, "onPageSelected " + i);
        this.mCurrentIndex = i;
        this.colorView.setCurrent(this.mCurrentIndex);
    }
}
