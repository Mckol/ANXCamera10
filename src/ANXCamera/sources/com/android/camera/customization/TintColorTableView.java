package com.android.camera.customization;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.customization.TintShapeView;
import java.util.List;

public class TintColorTableView extends LinearLayout implements View.OnClickListener {
    private int itemPaddingLeft = 0;
    private List<TintColor> mColorOptions;
    private int mCurrentSelection = 0;
    private OnColorChangeListener mOnColorChangeListener;

    /* access modifiers changed from: package-private */
    public interface OnColorChangeListener {
        void onColorChange(int i);
    }

    public TintColorTableView(Context context) {
        super(context);
    }

    public TintColorTableView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TintColorTableView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public TintColorTableView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    private void addColorItem(int i) {
        TintShapeView tintShapeView = (TintShapeView) LayoutInflater.from(getContext()).inflate(R.layout.tint_color_item, (ViewGroup) this, false);
        tintShapeView.setTag(Integer.valueOf(i));
        tintShapeView.setOnClickListener(this);
        tintShapeView.config(new TintShapeView.TintShape().shape(TintShapeView.ShapeType.CIRCLE).innerColor(this.mColorOptions.get(i).color()).outerColor(-1).innerRadius(getResources().getDimensionPixelSize(R.dimen.custom_tint_color_item) / 2).outerRadius((getResources().getDimensionPixelSize(R.dimen.custom_tint_color_item_outer) / 2) - getResources().getDimensionPixelSize(R.dimen.custom_tint_color_item_stroke)).stroke((float) getResources().getDimensionPixelSize(R.dimen.custom_tint_color_item_stroke)));
        tintShapeView.setSelection(i == this.mCurrentSelection);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tintShapeView.getLayoutParams();
        layoutParams.rightMargin = i == this.mColorOptions.size() - 1 ? 0 : this.itemPaddingLeft;
        layoutParams.leftMargin = 0;
        addView(tintShapeView, layoutParams);
    }

    private int calculateLeftPadding() {
        int size = this.mColorOptions.size();
        int dimensionPixelSize = (Util.sWindowWidth - (getResources().getDimensionPixelSize(R.dimen.custom_tint_color_table_margin) * 2)) - (getResources().getDimensionPixelSize(R.dimen.custom_tint_color_item_outer) * size);
        if (size == 1) {
            return 0;
        }
        return dimensionPixelSize / (size - 1);
    }

    public void initialize(List<TintColor> list, int i) {
        this.mColorOptions = list;
        this.mCurrentSelection = i;
        removeAllViews();
        this.itemPaddingLeft = calculateLeftPadding();
        for (int i2 = 0; i2 < this.mColorOptions.size(); i2++) {
            addColorItem(i2);
        }
    }

    public void onClick(View view) {
        View childAt = getChildAt(this.mCurrentSelection);
        if (childAt != view) {
            ((TintShapeView) childAt).setSelection(false);
            ((TintShapeView) view).setSelection(true);
            this.mCurrentSelection = ((Integer) view.getTag()).intValue();
            OnColorChangeListener onColorChangeListener = this.mOnColorChangeListener;
            if (onColorChangeListener != null) {
                onColorChangeListener.onColorChange(this.mCurrentSelection);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setCurrent(int i) {
        int i2 = this.mCurrentSelection;
        if (i != i2) {
            ((TintShapeView) getChildAt(i2)).setSelection(false);
            ((TintShapeView) getChildAt(i)).setSelection(true);
            this.mCurrentSelection = i;
        }
    }

    public void setOnColorChangeListener(OnColorChangeListener onColorChangeListener) {
        this.mOnColorChangeListener = onColorChangeListener;
    }
}
