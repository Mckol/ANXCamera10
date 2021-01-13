package com.android.camera.fragment.fastmotion;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.dialog.BaseDialogFragment;
import java.util.ArrayList;
import java.util.Arrays;

public class FragmentFastMotionDescription extends BaseDialogFragment {
    public static final String TAG = "FragmentFastMotionDescription";
    private ArrayList<FastMotionDescriptionItem> mDataItems;
    FastMotionDescriptionAdapter mFastMotionDescriptionAdapter;
    LinearLayoutManagerWrapper mLayoutManager;
    RecyclerView mRecyclerView;

    /* access modifiers changed from: private */
    public class FastmotionDescriptionItemDecoration extends RecyclerView.ItemDecoration {
        private FastmotionDescriptionItemDecoration() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
            super.getItemOffsets(rect, view, recyclerView, state);
            int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
            if (childAdapterPosition == recyclerView.getAdapter().getItemCount() - 2) {
                rect.set(0, 0, 0, FragmentFastMotionDescription.this.getResources().getDimensionPixelOffset(R.dimen.fastmotion_description_item_text_second_margin_top));
            }
            if (childAdapterPosition == recyclerView.getAdapter().getItemCount() - 1) {
                rect.set(0, 0, 0, FragmentFastMotionDescription.this.getResources().getDimensionPixelOffset(R.dimen.fastmotion_description_item_text_second_margin_bottom));
            }
        }
    }

    private ArrayList<FastMotionDescriptionItem> getParameterData() {
        ArrayList<FastMotionDescriptionItem> arrayList = this.mDataItems;
        if (arrayList != null) {
            return arrayList;
        }
        ArrayList<FastMotionDescriptionItem> arrayList2 = new ArrayList<>(Arrays.asList(new FastMotionDescriptionItem(false, 0, "", 0, getString(R.string.pref_camera_fastmotion_speed), getString(R.string.pref_camera_fastmotion_speed_description)), new FastMotionDescriptionItem(true, R.raw.fastmotion_30x, getString(R.string.pref_camera_fastmotion_speed_30x) + " | 4X-30X", R.drawable.fastmotion_cover_30x, "", ""), new FastMotionDescriptionItem(true, R.raw.fastmotion_90x, getString(R.string.pref_camera_fastmotion_speed_90x) + " | 60X-90X", R.drawable.fastmotion_cover_90x, "", ""), new FastMotionDescriptionItem(true, R.raw.fastmotion_150x, getString(R.string.pref_camera_fastmotion_speed_150x) + " | 120X-150X", R.drawable.fastmotion_cover_150x, "", ""), new FastMotionDescriptionItem(true, R.raw.fastmotion_750x, getString(R.string.pref_camera_fastmotion_speed_750x) + " | 300X-600X", R.drawable.fastmotion_cover_750x, "", ""), new FastMotionDescriptionItem(true, R.raw.fastmotion_1800x, getString(R.string.pref_camera_fastmotion_speed_1800x) + " | 900X-1800X", R.drawable.fastmotion_cover_1800x, "", ""), new FastMotionDescriptionItem(false, 0, "", 0, getString(R.string.pref_camera_fastmotion_duration), getString(R.string.pref_camera_fastmotion_duration_description))));
        this.mDataItems = arrayList2;
        return arrayList2;
    }

    public /* synthetic */ void c(View view) {
        FragmentUtils.removeFragmentByTag(getFragmentManager(), TAG);
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        getDialog().setCanceledOnTouchOutside(false);
        ImageView imageView = (ImageView) view.findViewById(R.id.fastmotion_description_back);
        if (Util.isLayoutRTL(getContext())) {
            imageView.setRotation(180.0f);
        }
        imageView.setOnClickListener(new c(this));
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.fastmotion_description_list);
        this.mRecyclerView.setFocusable(false);
        this.mLayoutManager = new LinearLayoutManagerWrapper(getContext(), "parameter_recycler_view");
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.mRecyclerView.addItemDecoration(new FastmotionDescriptionItemDecoration());
        this.mFastMotionDescriptionAdapter = new FastMotionDescriptionAdapter(this.mRecyclerView, getContext(), getParameterData());
        this.mRecyclerView.setAdapter(this.mFastMotionDescriptionAdapter);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mRecyclerView.getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        if (!Util.isFullScreenNavBarHidden(getContext())) {
            layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.mimoji_edit_config_bottom);
        } else {
            layoutParams.bottomMargin = 0;
        }
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_dialog_fastmotion_description, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        dismiss();
    }
}
