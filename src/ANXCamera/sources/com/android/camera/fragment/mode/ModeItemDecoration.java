package com.android.camera.fragment.mode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.Util;

public class ModeItemDecoration extends RecyclerView.ItemDecoration {
    private int mBottomMargin;
    private Context mContext;
    private int mHorMargin;
    private int mModeListHorMargin;
    private int mType;

    public ModeItemDecoration(Context context, RecyclerView recyclerView, int i) {
        this.mContext = context;
        this.mType = i;
        int screenWidth = Util.getScreenWidth(context);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.mode_item_width);
        int dimensionPixelSize2 = i == 0 ? this.mContext.getResources().getDimensionPixelSize(R.dimen.mode_item_height_normal) : this.mContext.getResources().getDimensionPixelSize(R.dimen.mode_item_height);
        this.mModeListHorMargin = i == 0 ? this.mContext.getResources().getDimensionPixelSize(R.dimen.mode_list_hor_margin_normal) : this.mContext.getResources().getDimensionPixelSize(R.dimen.mode_list_hor_margin);
        this.mHorMargin = ((screenWidth - (dimensionPixelSize * 3)) - (this.mModeListHorMargin * 2)) / 6;
        this.mBottomMargin = dimensionPixelSize2 - dimensionPixelSize;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        int itemViewType;
        super.getItemOffsets(rect, view, recyclerView, state);
        int i = this.mHorMargin;
        int i2 = this.mBottomMargin;
        int i3 = this.mType;
        if (i3 != 0) {
            if (i3 == 2) {
                int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
                if (recyclerView.getAdapter() != null && ((itemViewType = recyclerView.getAdapter().getItemViewType(childAdapterPosition)) == 2 || itemViewType == 1 || itemViewType == 6)) {
                    i = 0;
                    i2 = 0;
                }
            }
        } else if (recyclerView.getChildAdapterPosition(view) == 0) {
            i2 = 0;
        }
        rect.set(i, 0, i, i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        super.onDraw(canvas, recyclerView, state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        super.onDrawOver(canvas, recyclerView, state);
    }
}
