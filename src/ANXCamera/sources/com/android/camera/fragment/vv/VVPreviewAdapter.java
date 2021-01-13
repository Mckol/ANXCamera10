package com.android.camera.fragment.vv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import com.android.camera.R;
import com.android.camera.ui.TextureVideoView;
import com.bumptech.glide.Glide;
import java.util.Locale;

@Deprecated
public class VVPreviewAdapter extends PagerAdapter {
    private View.OnClickListener mClickListener;
    private VVList mVVList;

    public VVPreviewAdapter(VVList vVList, View.OnClickListener onClickListener) {
        this.mVVList = vVList;
        this.mClickListener = onClickListener;
    }

    private String getDurationString(long j) {
        return String.format(Locale.ENGLISH, "%.1f", Float.valueOf(Math.abs(((float) j) / 1000.0f)));
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        View view = (View) obj;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        VVList vVList = this.mVVList;
        if (vVList != null) {
            return vVList.getSize();
        }
        return 0;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        VVItem vVItem = (VVItem) this.mVVList.getItem(i);
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_vv_preview_item, viewGroup, false);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.vv_preview_item_image);
        TextureVideoView textureVideoView = (TextureVideoView) inflate.findViewById(R.id.vv_preview_texture_video);
        TextView textView = (TextView) inflate.findViewById(R.id.vv_preview_item_hint);
        inflate.setTag(Integer.valueOf(i));
        ((ImageView) inflate.findViewById(R.id.vv_preview_item_collapsing)).setOnClickListener(this.mClickListener);
        ViewCompat.setTransitionName(imageView, vVItem.name);
        Glide.with(viewGroup.getContext()).load(vVItem.coverPath).into(imageView);
        textView.setText(textView.getResources().getString(R.string.vv_duartion_hint, vVItem.name, Integer.valueOf(vVItem.getEssentialFragmentSize()), getDurationString(vVItem.getTotalDuration())));
        viewGroup.addView(inflate);
        return inflate;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }
}
