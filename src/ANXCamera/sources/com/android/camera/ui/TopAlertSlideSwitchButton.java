package com.android.camera.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.view.ViewCompat;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.customization.TintColor;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import miui.view.animation.CubicEaseOutInterpolator;

public class TopAlertSlideSwitchButton extends LinearLayout implements View.OnClickListener {
    private static final int DISABLE_COLOR = 1308622847;
    public static final int NONE = -1;
    private static final int NORMAL_COLOR = -1;
    private int SELECT_COLOR;
    private ArgbEvaluator mArgbEvaluator;
    private int mBackgroundColor;
    private Paint mBackgroundPaint;
    private float mChildMargin;
    private int mIndicatorColor;
    private Paint mIndicatorPaint;
    private ValueAnimator mIndicatorValueAnimator;
    private boolean mIsRTL;
    private boolean mIsTextView;
    private List<ItemData> mItemDataList;
    private float mItemHeight;
    private float mItemWidth;
    private int mMaxHeight;
    private int mMaxWidth;
    private float mSelectChildWidth;
    private int mSelectIndex;
    private float mSelectMarginLeft;
    private SlideSwitchListener mSlideSwitchListener;
    private boolean mTextBold;
    private float mTextSize;
    private int mType;

    /* access modifiers changed from: private */
    public static class ItemData {
        private int mDisabledColor = TopAlertSlideSwitchButton.DISABLE_COLOR;
        @StringRes
        private int mDisplayNameRes;
        @DrawableRes
        private int mIconRes;
        private boolean mIsDisabled;
        private boolean mIsShowText;
        private int mOriginalSelectColor;
        private String mValue;

        public ItemData() {
        }

        public ItemData(int i, String str, int i2, int i3, boolean z, boolean z2) {
            this.mIconRes = i;
            this.mValue = str;
            this.mDisplayNameRes = i2;
            this.mOriginalSelectColor = i3;
            this.mIsDisabled = z;
            this.mIsShowText = z2;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            ItemData itemData = (ItemData) obj;
            return itemData.mIconRes == this.mIconRes && itemData.mValue.equals(this.mValue) && itemData.mDisplayNameRes == this.mDisplayNameRes && itemData.mOriginalSelectColor == this.mOriginalSelectColor && itemData.mIsShowText == this.mIsShowText;
        }

        public int getDisplayNameRes() {
            return this.mDisplayNameRes;
        }

        public int getIconRes() {
            return this.mIconRes;
        }

        public String getValue() {
            return this.mValue;
        }

        public boolean isIsDisabled() {
            return this.mIsDisabled;
        }

        public boolean isIsShowText() {
            return this.mIsShowText;
        }

        public void setDisplayNameRes(int i) {
            this.mDisplayNameRes = i;
        }

        public void setIconRes(int i) {
            this.mIconRes = i;
        }

        public void setIsDisabled(boolean z) {
            this.mIsDisabled = z;
        }

        public void setIsShowText(boolean z) {
            this.mIsShowText = z;
        }

        public void setOriginalSelectColor(int i) {
            this.mOriginalSelectColor = i;
        }

        public void setValue(String str) {
            this.mValue = str;
        }
    }

    public interface SlideSwitchListener {
        boolean enableSwitch();

        void toSlideSwitch(int i, String str);
    }

    public TopAlertSlideSwitchButton(Context context) {
        this(context, null, 0);
    }

    public TopAlertSlideSwitchButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TopAlertSlideSwitchButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBackgroundColor = 855638016;
        this.mSelectIndex = 0;
        this.mSelectMarginLeft = -1.0f;
        this.mSelectChildWidth = -1.0f;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.SlideSwitchButton, i, 0);
        this.mChildMargin = (float) obtainStyledAttributes.getDimensionPixelSize(0, 12);
        this.mIndicatorColor = TintColor.tintColor();
        this.SELECT_COLOR = obtainStyledAttributes.getColor(5, ViewCompat.MEASURED_STATE_MASK);
        this.mMaxWidth = obtainStyledAttributes.getDimensionPixelSize(4, Util.sWindowWidth);
        this.mMaxHeight = obtainStyledAttributes.getDimensionPixelSize(3, 160);
        this.mIsTextView = obtainStyledAttributes.getBoolean(2, false);
        this.mTextSize = (float) obtainStyledAttributes.getDimensionPixelSize(7, 48);
        this.mTextBold = obtainStyledAttributes.getBoolean(6, false);
        obtainStyledAttributes.recycle();
        setWillNotDraw(false);
        this.mBackgroundPaint = new Paint(1);
        this.mBackgroundPaint.setColor(this.mBackgroundColor);
        this.mBackgroundPaint.setStyle(Paint.Style.FILL);
        this.mIndicatorPaint = new Paint(1);
        this.mIndicatorPaint.setColor(this.mIndicatorColor);
        this.mIndicatorPaint.setStyle(Paint.Style.FILL);
        this.mArgbEvaluator = new ArgbEvaluator();
        this.mIsRTL = Util.isLayoutRTL(getContext());
    }

    private void addImageChild(String str, int i, ItemData itemData) {
        ColorImageView colorImageView = new ColorImageView(getContext());
        colorImageView.setImageResource(itemData.mIconRes);
        colorImageView.setTag(itemData.mValue);
        colorImageView.setScaleType(ImageView.ScaleType.CENTER);
        colorImageView.setContentDescription(getResources().getString(itemData.mDisplayNameRes));
        FolmeUtils.touchTint(colorImageView);
        if (str.equals(itemData.mValue)) {
            this.mSelectIndex = i;
            this.mIndicatorPaint.setColor(getSelectColor(i));
            if (getSelectColor(i) != this.mIndicatorColor) {
                colorImageView.setColorAndRefresh(-1);
            } else {
                colorImageView.setColorAndRefresh(this.SELECT_COLOR);
            }
            colorImageView.setEnabled(true);
        } else if (itemData.mIsDisabled) {
            colorImageView.setColorAndRefresh(DISABLE_COLOR);
            colorImageView.setEnabled(false);
        } else {
            colorImageView.setColorAndRefresh(-1);
            colorImageView.setEnabled(true);
        }
        colorImageView.setOnClickListener(this);
        addView(colorImageView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) colorImageView.getLayoutParams();
        layoutParams.width = getResources().getDimensionPixelSize(R.dimen.slide_switch_button_item_image_max_width);
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.slide_switch_button_item_image_max_height);
        float f = this.mChildMargin;
        layoutParams.setMargins((int) f, (int) f, (int) f, (int) f);
        colorImageView.setLayoutParams(layoutParams);
    }

    private void addTextChild(String str, int i, ItemData itemData) {
        AdaptiveTextView adaptiveTextView = new AdaptiveTextView(getContext());
        adaptiveTextView.setGravity(17);
        adaptiveTextView.setMaxWidth(getResources().getDimensionPixelSize(R.dimen.slide_switch_button_item_max_width));
        adaptiveTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        adaptiveTextView.setMarqueeRepeatLimit(-1);
        adaptiveTextView.setSingleLine();
        adaptiveTextView.setFocusable(true);
        adaptiveTextView.setFocusableInTouchMode(true);
        adaptiveTextView.setHorizontallyScrolling(true);
        adaptiveTextView.setClickable(true);
        adaptiveTextView.setTextSize(0, this.mTextSize);
        adaptiveTextView.setText(itemData.mDisplayNameRes);
        adaptiveTextView.setTag(itemData.mValue);
        adaptiveTextView.getPaint().setFakeBoldText(this.mTextBold);
        adaptiveTextView.setPadding(getResources().getDimensionPixelSize(R.dimen.slide_switch_button_item_text_padding), 0, getResources().getDimensionPixelSize(R.dimen.slide_switch_button_item_text_padding), 0);
        adaptiveTextView.setContentDescription(getResources().getString(itemData.mDisplayNameRes));
        FolmeUtils.touchTint(adaptiveTextView);
        if (str.equals(itemData.mValue)) {
            adaptiveTextView.setTextColor(this.SELECT_COLOR);
            this.mSelectIndex = i;
            adaptiveTextView.setEnabled(true);
        } else if (itemData.mIsDisabled) {
            adaptiveTextView.setTextColor(DISABLE_COLOR);
            adaptiveTextView.setEnabled(false);
        } else {
            adaptiveTextView.setTextColor(-1);
            adaptiveTextView.setEnabled(true);
        }
        adaptiveTextView.setOnClickListener(this);
        addView(adaptiveTextView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) adaptiveTextView.getLayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -1;
        float f = this.mChildMargin;
        layoutParams.setMargins((int) f, (int) f, (int) f, (int) f);
        adaptiveTextView.setLayoutParams(layoutParams);
    }

    private int getIndex(String str, List<ItemData> list) {
        if (str == null) {
            return -1;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).mValue.equals(str)) {
                return i;
            }
        }
        return -1;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getSelectColor(int i) {
        List<ItemData> list = this.mItemDataList;
        if (list == null || list.size() <= i) {
            return 0;
        }
        int i2 = this.mItemDataList.get(i).mOriginalSelectColor;
        return i2 == 0 ? this.mIndicatorColor : i2;
    }

    private boolean isDataEqual(List<ItemData> list, List<ItemData> list2) {
        return list != null && list2 != null && list.size() == list2.size() && list.containsAll(list2) && list2.containsAll(list);
    }

    private List<ItemData> mapItemData(List<ComponentDataItem> list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            ItemData itemData = new ItemData();
            itemData.setIconRes(list.get(i).mIconRes);
            itemData.setDisplayNameRes(list.get(i).mDisplayNameRes);
            itemData.setIsDisabled(list.get(i).mIsDisabled);
            itemData.setIsShowText(list.get(i).mIsShowText);
            itemData.setOriginalSelectColor(list.get(i).mSelectColor);
            itemData.setValue(list.get(i).mValue);
            arrayList.add(itemData);
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setChildColor(int i, int i2) {
        if (getChildAt(i) instanceof TextView) {
            ((TextView) getChildAt(i)).setTextColor(i2);
        } else if (getSelectColor(i) == this.mIndicatorColor) {
            ((ColorImageView) getChildAt(i)).setColorAndRefresh(i2);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setIndex(int i) {
        setChildColor(i, this.SELECT_COLOR);
        int i2 = this.mSelectIndex;
        if (i2 != -1) {
            setChildColor(i2, -1);
        }
        int left = getChildAt(i).getLeft();
        int width = getChildAt(i).getWidth();
        this.mIndicatorPaint.setColor(getSelectColor(i));
        this.mSelectIndex = i;
        this.mSelectMarginLeft = (float) left;
        this.mSelectChildWidth = (float) width;
        invalidate();
    }

    private void setIndex(final int i, boolean z) {
        if (this.mSelectIndex != i) {
            final int left = getChildAt(i).getLeft();
            final int width = getChildAt(i).getWidth();
            if (z) {
                ValueAnimator valueAnimator = this.mIndicatorValueAnimator;
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    this.mIndicatorValueAnimator.cancel();
                }
                final float f = this.mSelectMarginLeft;
                final float f2 = this.mSelectChildWidth;
                this.mIndicatorValueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mIndicatorValueAnimator.setDuration(300L);
                this.mIndicatorValueAnimator.setInterpolator(new CubicEaseOutInterpolator() {
                    /* class com.android.camera.ui.TopAlertSlideSwitchButton.AnonymousClass1 */

                    @Override // miui.view.animation.CubicEaseOutInterpolator
                    public float getInterpolation(float f) {
                        float interpolation = super.getInterpolation(f);
                        TopAlertSlideSwitchButton topAlertSlideSwitchButton = TopAlertSlideSwitchButton.this;
                        float f2 = f;
                        topAlertSlideSwitchButton.mSelectMarginLeft = f2 + ((((float) left) - f2) * interpolation);
                        TopAlertSlideSwitchButton topAlertSlideSwitchButton2 = TopAlertSlideSwitchButton.this;
                        float f3 = f2;
                        topAlertSlideSwitchButton2.mSelectChildWidth = f3 + ((((float) width) - f3) * interpolation);
                        Paint paint = TopAlertSlideSwitchButton.this.mIndicatorPaint;
                        ArgbEvaluator argbEvaluator = TopAlertSlideSwitchButton.this.mArgbEvaluator;
                        TopAlertSlideSwitchButton topAlertSlideSwitchButton3 = TopAlertSlideSwitchButton.this;
                        paint.setColor(((Integer) argbEvaluator.evaluate(interpolation, Integer.valueOf(topAlertSlideSwitchButton3.getSelectColor(topAlertSlideSwitchButton3.mSelectIndex)), Integer.valueOf(TopAlertSlideSwitchButton.this.getSelectColor(i)))).intValue());
                        TopAlertSlideSwitchButton topAlertSlideSwitchButton4 = TopAlertSlideSwitchButton.this;
                        topAlertSlideSwitchButton4.setChildColor(i, ((Integer) topAlertSlideSwitchButton4.mArgbEvaluator.evaluate(interpolation, -1, Integer.valueOf(TopAlertSlideSwitchButton.this.SELECT_COLOR))).intValue());
                        if (TopAlertSlideSwitchButton.this.mSelectIndex != -1) {
                            TopAlertSlideSwitchButton topAlertSlideSwitchButton5 = TopAlertSlideSwitchButton.this;
                            topAlertSlideSwitchButton5.setChildColor(topAlertSlideSwitchButton5.mSelectIndex, ((Integer) TopAlertSlideSwitchButton.this.mArgbEvaluator.evaluate(interpolation, Integer.valueOf(TopAlertSlideSwitchButton.this.SELECT_COLOR), -1)).intValue());
                        }
                        TopAlertSlideSwitchButton.this.invalidate();
                        return interpolation;
                    }
                });
                this.mIndicatorValueAnimator.addListener(new AnimatorListenerAdapter() {
                    /* class com.android.camera.ui.TopAlertSlideSwitchButton.AnonymousClass2 */

                    public void onAnimationCancel(Animator animator) {
                        super.onAnimationCancel(animator);
                        TopAlertSlideSwitchButton.this.setEnabled(true);
                        TopAlertSlideSwitchButton.this.setIndex(i);
                    }

                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        TopAlertSlideSwitchButton.this.setEnabled(true);
                        TopAlertSlideSwitchButton.this.mSelectIndex = i;
                    }
                });
                this.mIndicatorValueAnimator.start();
            } else {
                setEnabled(true);
                setIndex(i);
            }
            this.mSlideSwitchListener.toSlideSwitch(((Integer) getTag()).intValue(), (String) getChildAt(i).getTag());
        }
    }

    private void setItemColor(View view, int i) {
        if (view instanceof ColorImageView) {
            ColorImageView colorImageView = (ColorImageView) view;
            if (colorImageView.getColor() != i) {
                colorImageView.setColorAndRefresh(i);
            }
        } else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            if (textView.getCurrentTextColor() != i) {
                textView.setTextColor(i);
            }
        }
    }

    private void updateViewsStatus(List<ItemData> list) {
        for (int i = 0; i < list.size(); i++) {
            View childAt = getChildAt(i);
            if (list.get(i).mIsDisabled) {
                childAt.setEnabled(false);
                setItemColor(childAt, DISABLE_COLOR);
            } else if (this.mSelectIndex == i) {
                childAt.setEnabled(true);
                setItemColor(childAt, this.SELECT_COLOR);
            } else {
                childAt.setEnabled(true);
                setItemColor(childAt, -1);
            }
            if (getSelectColor(i) != this.mIndicatorColor) {
                setItemColor(childAt, -1);
            }
        }
    }

    public int getIndicatorCount() {
        return getChildCount();
    }

    public int getType() {
        return this.mType;
    }

    public void onClick(View view) {
        if (isEnabled()) {
            SlideSwitchListener slideSwitchListener = this.mSlideSwitchListener;
            if (slideSwitchListener == null || slideSwitchListener.enableSwitch()) {
                int i = 0;
                while (true) {
                    if (getChildCount() <= 0) {
                        i = 0;
                        break;
                    } else if (!view.getTag().equals(getChildAt(i).getTag())) {
                        i++;
                    } else if (i == this.mSelectIndex) {
                        return;
                    }
                }
                setEnabled(false);
                setIndex(i, true);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mSelectMarginLeft == -1.0f) {
            this.mSelectMarginLeft = (float) getChildAt(this.mSelectIndex).getLeft();
        }
        if (this.mSelectChildWidth == -1.0f) {
            this.mSelectChildWidth = (float) getChildAt(this.mSelectIndex).getWidth();
        }
        canvas.drawRoundRect(0.0f, 0.0f, (float) (getWidth() + 0), (float) (getHeight() + 0), (float) (getHeight() / 2), (float) (getHeight() / 2), this.mBackgroundPaint);
        for (int i = 0; i < getChildCount(); i++) {
            if (i == this.mSelectIndex) {
                canvas.drawRoundRect(this.mSelectMarginLeft, (float) getChildAt(i).getTop(), this.mSelectMarginLeft + this.mSelectChildWidth, (float) getChildAt(i).getBottom(), (float) (getHeight() / 2), (float) (getHeight() / 2), this.mIndicatorPaint);
            }
        }
        super.onDraw(canvas);
    }

    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
        this.mBackgroundPaint.setColor(i);
        invalidate();
    }

    public void setComponentData(ComponentData componentData, int i, boolean z) {
        List<ItemData> list;
        this.mSelectMarginLeft = -1.0f;
        this.mSelectChildWidth = -1.0f;
        String str = null;
        try {
            list = mapItemData(componentData.getItems());
            try {
                if (this.mIsRTL) {
                    Collections.reverse(list);
                }
                str = componentData.getComponentValue(DataRepository.dataItemGlobal().getCurrentMode());
                if (isDataEqual(list, this.mItemDataList) && this.mIsTextView == z) {
                    int index = getIndex(str, list);
                    if ((this.mIndicatorValueAnimator == null || !this.mIndicatorValueAnimator.isRunning()) && index != this.mSelectIndex) {
                        setIndex(index);
                    }
                    updateViewsStatus(list);
                    return;
                }
            } catch (Exception unused) {
            }
        } catch (Exception unused2) {
            list = null;
        }
        this.mItemDataList = list;
        this.mIsTextView = z;
        removeAllViews();
        setTag(Integer.valueOf(i));
        this.mSelectMarginLeft = -1.0f;
        for (int i2 = 0; i2 < this.mItemDataList.size(); i2++) {
            ItemData itemData = this.mItemDataList.get(i2);
            if (this.mIsTextView) {
                addTextChild(str, i2, itemData);
            } else if (itemData.mIsShowText) {
                addTextChild(str, i2, itemData);
            } else {
                addImageChild(str, i2, itemData);
            }
        }
    }

    public void setIndicatorColor(int i) {
        this.mIndicatorColor = i;
        this.mIndicatorPaint.setColor(this.mIndicatorColor);
        invalidate();
    }

    public void setSlideSwitchListener(SlideSwitchListener slideSwitchListener) {
        this.mSlideSwitchListener = slideSwitchListener;
    }

    public void setType(int i) {
        this.mType = i;
    }
}
