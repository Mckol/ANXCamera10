package com.android.camera.fragment.manually;

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
import com.android.camera.constant.DurationConstant;
import com.android.camera.data.data.AmbilightDescriptionItem;
import com.android.camera.fragment.DefaultItemAnimator;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.dialog.BaseDialogFragment;
import com.android.camera.fragment.manually.adapter.AmbilightDescriptionAdapter;
import com.android.camera.module.AmbilightModule;
import java.util.ArrayList;
import java.util.Arrays;

public class FragmentAmbilightDescription extends BaseDialogFragment {
    public static final String TAG = "FragmentAmbilightDescription";
    private ArrayList<AmbilightDescriptionItem> mDataItems;

    private ArrayList<AmbilightDescriptionItem> getParameterData() {
        ArrayList<AmbilightDescriptionItem> arrayList = this.mDataItems;
        if (arrayList != null) {
            return arrayList;
        }
        AmbilightDescriptionItem[] ambilightDescriptionItemArr = new AmbilightDescriptionItem[6];
        ambilightDescriptionItemArr[0] = new AmbilightDescriptionItem(R.string.ambilight_scene_crowd_moving, R.drawable.ambilight_intro_crowd_moving_part1, getResources().getString(R.string.ambilight_intro_crowd_moving_part1), R.drawable.ambilight_intro_crowd_moving_part2, getResources().getString(R.string.ambilight_intro_crowd_moving_part2));
        ambilightDescriptionItemArr[1] = new AmbilightDescriptionItem(R.string.ambilight_scene_traffic_light, R.drawable.ambilight_intro_traffic_light, getResources().getString(R.string.ambilight_intro_traffic_light), -1, null);
        ambilightDescriptionItemArr[2] = new AmbilightDescriptionItem(R.string.ambilight_scene_silky_water, R.drawable.ambilight_intro_silky_water_part1, getResources().getString(R.string.ambilight_intro_silky_water_part1), R.drawable.ambilight_intro_silky_water_part2, getResources().getString(R.string.ambilight_intro_silky_water_part2));
        ambilightDescriptionItemArr[3] = new AmbilightDescriptionItem(R.string.ambilight_scene_light_track, R.drawable.ambilight_intro_light_track, getResources().getString(R.string.ambilight_intro_light_track), -1, null);
        ambilightDescriptionItemArr[4] = new AmbilightDescriptionItem(R.string.ambilight_scene_magic_star, R.drawable.ambilight_intro_magic_star, getResources().getString(AmbilightModule.mSupportAutoAe ? R.string.ambilight_intro_magic_star_ae : R.string.ambilight_intro_magic_star, Integer.valueOf(DurationConstant.DURATION_AMBILIGHT_MAGIC_STAR_CAPTURE / 1000)), -1, null);
        ambilightDescriptionItemArr[5] = new AmbilightDescriptionItem(R.string.ambilight_scene_star_track, R.drawable.ambilight_intro_star_track, getResources().getString(R.string.ambilight_intro_star_track_2, 30), -1, null);
        ArrayList<AmbilightDescriptionItem> arrayList2 = new ArrayList<>(Arrays.asList(ambilightDescriptionItemArr));
        this.mDataItems = arrayList2;
        return arrayList2;
    }

    public /* synthetic */ void d(View view) {
        FragmentUtils.removeFragmentByTag(getFragmentManager(), TAG);
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        getDialog().setCanceledOnTouchOutside(false);
        ImageView imageView = (ImageView) view.findViewById(R.id.ambilight_description_back);
        if (Util.isLayoutRTL(getContext())) {
            imageView.setRotation(180.0f);
        }
        imageView.setOnClickListener(new a(this));
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.ambilight_description_list);
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(getContext(), "parameter_recycler_view"));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new AmbilightDescriptionAdapter(getParameterData()));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
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
        View inflate = layoutInflater.inflate(R.layout.fragment_dialog_ambilight_description, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        dismiss();
    }
}
