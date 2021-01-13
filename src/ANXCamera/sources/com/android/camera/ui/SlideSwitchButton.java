package com.android.camera.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class SlideSwitchButton extends ViewGroup implements View.OnClickListener {
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
    private int mSelectIndex;
    private float mSelectMarginLeft;
    private SlideSwitchListener mSlideSwitchListener;
    private boolean mTextBold;
    private float mTextSize;
    private int mType;

    /* access modifiers changed from: private */
    public static class ItemData {
        private int mDisabledColor = SlideSwitchButton.DISABLE_COLOR;
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

    public SlideSwitchButton(Context context) {
        this(context, null, 0);
    }

    public SlideSwitchButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlideSwitchButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBackgroundColor = 654311423;
        this.mSelectIndex = -1;
        this.mSelectMarginLeft = -1.0f;
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
            if (itemData.mIsDisabled) {
                this.mIndicatorPaint.setColor(this.mBackgroundColor);
                colorImageView.setColorAndRefresh(DISABLE_COLOR);
            } else if (getSelectColor(i) != this.mIndicatorColor) {
                this.mIndicatorPaint.setColor(getSelectColor(i));
                colorImageView.setColorAndRefresh(-1);
            } else {
                this.mIndicatorPaint.setColor(getSelectColor(i));
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
    }

    private void addTextChild(String str, int i, ItemData itemData) {
        TextView textView = new TextView(getContext());
        textView.setGravity(17);
        textView.setTextSize(0, this.mTextSize);
        textView.setText(itemData.mDisplayNameRes);
        textView.setTag(itemData.mValue);
        textView.getPaint().setFakeBoldText(this.mTextBold);
        textView.setContentDescription(getResources().getString(itemData.mDisplayNameRes));
        FolmeUtils.touchTint(textView);
        if (str.equals(itemData.mValue)) {
            textView.setTextColor(this.SELECT_COLOR);
            this.mSelectIndex = i;
            textView.setEnabled(true);
            this.mIndicatorPaint.setColor(getSelectColor(i));
        } else if (itemData.mIsDisabled) {
            textView.setTextColor(DISABLE_COLOR);
            textView.setEnabled(false);
        } else {
            textView.setTextColor(-1);
            textView.setEnabled(true);
        }
        textView.setOnClickListener(this);
        addView(textView);
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
        float f = this.mChildMargin;
        this.mIndicatorPaint.setColor(getSelectColor(i));
        this.mSelectIndex = i;
        this.mSelectMarginLeft = (float) ((int) (((float) getPaddingLeft()) + f + ((this.mItemWidth + f + f) * ((float) i))));
        invalidate();
    }

    private void setIndex(final int i, boolean z) {
        ValueAnimator valueAnimator;
        if (z && (valueAnimator = this.mIndicatorValueAnimator) != null && valueAnimator.isRunning()) {
            this.mIndicatorValueAnimator.cancel();
        }
        if (this.mSelectIndex != i) {
            float f = this.mChildMargin;
            final int paddingLeft = (int) (((float) getPaddingLeft()) + f + ((this.mItemWidth + f + f) * ((float) i)));
            if (z) {
                final float f2 = this.mSelectMarginLeft;
                this.mIndicatorValueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mIndicatorValueAnimator.setDuration(300L);
                this.mIndicatorValueAnimator.setInterpolator(new CubicEaseOutInterpolator() {
                    /* class com.android.camera.ui.SlideSwitchButton.AnonymousClass1 */

                    @Override // miui.view.animation.CubicEaseOutInterpolator
                    public float getInterpolation(float f) {
                        float interpolation = super.getInterpolation(f);
                        SlideSwitchButton slideSwitchButton = SlideSwitchButton.this;
                        float f2 = f2;
                        slideSwitchButton.mSelectMarginLeft = f2 + ((((float) paddingLeft) - f2) * interpolation);
                        Paint paint = SlideSwitchButton.this.mIndicatorPaint;
                        ArgbEvaluator argbEvaluator = SlideSwitchButton.this.mArgbEvaluator;
                        SlideSwitchButton slideSwitchButton2 = SlideSwitchButton.this;
                        paint.setColor(((Integer) argbEvaluator.evaluate(interpolation, Integer.valueOf(slideSwitchButton2.getSelectColor(slideSwitchButton2.mSelectIndex)), Integer.valueOf(SlideSwitchButton.this.getSelectColor(i)))).intValue());
                        SlideSwitchButton slideSwitchButton3 = SlideSwitchButton.this;
                        slideSwitchButton3.setChildColor(i, ((Integer) slideSwitchButton3.mArgbEvaluator.evaluate(interpolation, -1, Integer.valueOf(SlideSwitchButton.this.SELECT_COLOR))).intValue());
                        if (SlideSwitchButton.this.mSelectIndex != -1) {
                            SlideSwitchButton slideSwitchButton4 = SlideSwitchButton.this;
                            slideSwitchButton4.setChildColor(slideSwitchButton4.mSelectIndex, ((Integer) SlideSwitchButton.this.mArgbEvaluator.evaluate(interpolation, Integer.valueOf(SlideSwitchButton.this.SELECT_COLOR), -1)).intValue());
                        }
                        SlideSwitchButton.this.invalidate();
                        return interpolation;
                    }
                });
                this.mIndicatorValueAnimator.addListener(new AnimatorListenerAdapter() {
                    /* class com.android.camera.ui.SlideSwitchButton.AnonymousClass2 */

                    public void onAnimationCancel(Animator animator) {
                        super.onAnimationCancel(animator);
                        SlideSwitchButton.this.setIndex(i);
                    }

                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        SlideSwitchButton.this.mSelectIndex = i;
                    }
                });
                this.mIndicatorValueAnimator.start();
            } else {
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
                this.mIndicatorPaint.setColor(getSelectColor(i));
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
                int i2 = 0;
                while (true) {
                    if (getChildCount() <= 0) {
                        break;
                    } else if (view.getTag().equals(getChildAt(i2).getTag())) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                setIndex(i, true);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mSelectMarginLeft == -1.0f) {
            float f = this.mChildMargin;
            this.mSelectMarginLeft = (float) ((int) (((float) getPaddingLeft()) + f + ((this.mItemWidth + f + f) * ((float) this.mSelectIndex))));
        }
        canvas.drawRoundRect(0.0f, 0.0f, (float) (getWidth() + 0), (float) (getHeight() + 0), (float) (getHeight() / 2), (float) (getHeight() / 2), this.mBackgroundPaint);
        float f2 = this.mSelectMarginLeft;
        float paddingTop = ((float) getPaddingTop()) + this.mChildMargin;
        float f3 = this.mSelectMarginLeft + this.mItemWidth;
        float paddingTop2 = ((float) getPaddingTop()) + this.mChildMargin;
        float f4 = this.mItemHeight;
        canvas.drawRoundRect(f2, paddingTop, f3, paddingTop2 + f4, f4 / 2.0f, f4 / 2.0f, this.mIndicatorPaint);
        super.onDraw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft = (int) (((float) getPaddingLeft()) + this.mChildMargin);
        int paddingTop = (int) (((float) getPaddingTop()) + this.mChildMargin);
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            float f = (float) paddingLeft;
            getChildAt(i5).layout(paddingLeft, paddingTop, (int) (this.mItemWidth + f), (int) (((float) paddingTop) + this.mItemHeight));
            float f2 = this.mChildMargin;
            paddingLeft = (int) (f + this.mItemWidth + f2 + f2);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(ViewGroup.resolveSizeAndState(this.mMaxWidth, i, 0), ViewGroup.resolveSizeAndState(this.mMaxHeight, i2, 0));
        this.mItemWidth = (((float) ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight())) - ((this.mChildMargin * 2.0f) * ((float) getChildCount()))) / ((float) getChildCount());
        this.mItemHeight = ((float) ((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom())) - (this.mChildMargin * 2.0f);
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            getChildAt(i3).measure(View.MeasureSpec.makeMeasureSpec((int) this.mItemWidth, 1073741824), View.MeasureSpec.makeMeasureSpec((int) this.mItemHeight, 1073741824));
        }
    }

    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
        this.mBackgroundPaint.setColor(i);
        invalidate();
    }

    public void setComponentData(ComponentData componentData, int i, boolean z) {
        List<ItemData> list;
        String str = null;
        try {
            list = mapItemData(componentData.getItems());
            try {
                if (this.mIsRTL) {
                    Collections.reverse(list);
                }
                str = componentData.getDisplayComponentValue(DataRepository.dataItemGlobal().getCurrentMode());
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
