package com.android.camera.fragment.beauty;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.customization.TintColor;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.TypeItem;
import com.android.camera.ui.ColorImageView;
import java.util.List;

public class MakeupSingleCheckAdapter extends RecyclerView.Adapter<SingleCheckViewHolder> {
    private Context mContext;
    private int mDegree;
    private int mPositionFirstItem;
    private int mPositionLastItem;
    private int mPreSelectedItem = 0;
    private RecyclerView mRecyclerView;
    private int mSelectedItem = 0;
    private List<TypeItem> mSingleCheckList;
    private AdapterView.OnItemClickListener onItemClickListener;

    /* access modifiers changed from: package-private */
    public class SingleCheckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemView;
        private MakeupSingleCheckAdapter mAdapter;
        private ColorImageView mBase;
        private TextView mText;

        public SingleCheckViewHolder(View view, MakeupSingleCheckAdapter makeupSingleCheckAdapter) {
            super(view);
            this.mAdapter = makeupSingleCheckAdapter;
            this.itemView = view;
            this.mText = (TextView) view.findViewById(R.id.makeup_item_name);
            this.mBase = (ColorImageView) view.findViewById(R.id.makeup_item_icon);
            view.setOnClickListener(this);
            FolmeUtils.handleListItemTouch(view);
        }

        private void colorAnimate(final ColorImageView colorImageView, int i, int i2) {
            ValueAnimator ofObject = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(i), Integer.valueOf(i2));
            ofObject.setDuration(200L);
            ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                /* class com.android.camera.fragment.beauty.MakeupSingleCheckAdapter.SingleCheckViewHolder.AnonymousClass2 */

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    colorImageView.setColorAndRefresh(((Integer) valueAnimator.getAnimatedValue()).intValue());
                }
            });
            ofObject.start();
        }

        private void textColorAnimate(final TextView textView, int i, int i2) {
            ValueAnimator ofObject = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(i), Integer.valueOf(i2));
            ofObject.setDuration(200L);
            ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                /* class com.android.camera.fragment.beauty.MakeupSingleCheckAdapter.SingleCheckViewHolder.AnonymousClass1 */

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    textView.setTextColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                }
            });
            ofObject.start();
        }

        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition == MakeupSingleCheckAdapter.this.mSelectedItem) {
                this.mAdapter.onItemHolderClick(this);
                return;
            }
            MakeupSingleCheckAdapter makeupSingleCheckAdapter = MakeupSingleCheckAdapter.this;
            makeupSingleCheckAdapter.mPreSelectedItem = makeupSingleCheckAdapter.mSelectedItem;
            MakeupSingleCheckAdapter.this.mSelectedItem = adapterPosition;
            SingleCheckViewHolder singleCheckViewHolder = (SingleCheckViewHolder) MakeupSingleCheckAdapter.this.mRecyclerView.findViewHolderForAdapterPosition(MakeupSingleCheckAdapter.this.mPreSelectedItem);
            SingleCheckViewHolder singleCheckViewHolder2 = (SingleCheckViewHolder) MakeupSingleCheckAdapter.this.mRecyclerView.findViewHolderForAdapterPosition(MakeupSingleCheckAdapter.this.mSelectedItem);
            Resources resources = MakeupSingleCheckAdapter.this.mContext.getResources();
            if (singleCheckViewHolder != null) {
                MakeupSingleCheckAdapter makeupSingleCheckAdapter2 = MakeupSingleCheckAdapter.this;
                if (makeupSingleCheckAdapter2.normalItem(makeupSingleCheckAdapter2.mPreSelectedItem)) {
                    MakeupSingleCheckAdapter makeupSingleCheckAdapter3 = MakeupSingleCheckAdapter.this;
                    if (makeupSingleCheckAdapter3.normalItem(makeupSingleCheckAdapter3.mSelectedItem)) {
                        colorAnimate(singleCheckViewHolder.mBase, TintColor.tintColor(), -1);
                        textColorAnimate(singleCheckViewHolder.mText, TintColor.tintColor(), resources.getColor(R.color.beautycamera_beauty_advanced_item_text_normal));
                    }
                }
            }
            if (singleCheckViewHolder2 != null) {
                MakeupSingleCheckAdapter makeupSingleCheckAdapter4 = MakeupSingleCheckAdapter.this;
                if (makeupSingleCheckAdapter4.normalItem(makeupSingleCheckAdapter4.mPreSelectedItem)) {
                    MakeupSingleCheckAdapter makeupSingleCheckAdapter5 = MakeupSingleCheckAdapter.this;
                    if (makeupSingleCheckAdapter5.normalItem(makeupSingleCheckAdapter5.mSelectedItem)) {
                        colorAnimate(singleCheckViewHolder2.mBase, -1, TintColor.tintColor());
                        textColorAnimate(singleCheckViewHolder2.mText, resources.getColor(R.color.beautycamera_beauty_advanced_item_text_normal), TintColor.tintColor());
                    }
                }
            }
            if (singleCheckViewHolder == null) {
                this.mAdapter.notifyItemChanged(MakeupSingleCheckAdapter.this.mPreSelectedItem);
            }
            this.mAdapter.onItemHolderClick(this);
        }

        public void setDataToView(TypeItem typeItem, int i) {
            String str;
            this.itemView.setTag(typeItem);
            Resources resources = MakeupSingleCheckAdapter.this.mContext.getResources();
            if (DataRepository.dataItemGlobal().getCurrentMode() == 165) {
                this.itemView.setBackgroundResource(R.drawable.makeup_item_bg_square);
            } else {
                this.itemView.setBackgroundResource(R.drawable.makeup_item_bg);
            }
            this.mText.setText(resources.getString(typeItem.getTextResource()));
            this.mText.setTextColor((i != MakeupSingleCheckAdapter.this.mSelectedItem || !MakeupSingleCheckAdapter.this.normalItem(i)) ? resources.getColor(R.color.beautycamera_beauty_advanced_item_text_normal) : TintColor.tintColor());
            if (Util.isAccessible()) {
                TextView textView = this.mText;
                if (i == MakeupSingleCheckAdapter.this.mSelectedItem) {
                    str = resources.getString(typeItem.getTextResource()) + resources.getString(R.string.accessibility_open);
                } else {
                    str = resources.getString(typeItem.getTextResource()) + resources.getString(R.string.accessibility_closed);
                }
                textView.setContentDescription(str);
            }
            this.mBase.setImageResource(typeItem.getImageResource());
            this.mBase.setColor((i != MakeupSingleCheckAdapter.this.mSelectedItem || !MakeupSingleCheckAdapter.this.normalItem(i)) ? -1 : TintColor.tintColor());
        }
    }

    public MakeupSingleCheckAdapter(Context context, List<TypeItem> list) {
        this.mContext = context;
        this.mSingleCheckList = list;
        this.mPositionFirstItem = 0;
        this.mPositionLastItem = list.size() - 1;
    }

    public MakeupSingleCheckAdapter(Context context, List<TypeItem> list, int i, int i2) {
        this.mContext = context;
        this.mSingleCheckList = list;
        this.mPositionFirstItem = i;
        this.mPositionLastItem = i2;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean normalItem(int i) {
        return i >= this.mPositionFirstItem && i <= this.mPositionLastItem;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mSingleCheckList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [androidx.recyclerview.widget.RecyclerView$ViewHolder, int, java.util.List] */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull SingleCheckViewHolder singleCheckViewHolder, int i, @NonNull List list) {
        onBindViewHolder(singleCheckViewHolder, i, (List<Object>) list);
    }

    public void onBindViewHolder(SingleCheckViewHolder singleCheckViewHolder, int i) {
        try {
            singleCheckViewHolder.setDataToView(this.mSingleCheckList.get(i), i);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        singleCheckViewHolder.itemView.findViewById(R.id.container_wrapper).setRotation((float) this.mDegree);
    }

    public void onBindViewHolder(@NonNull SingleCheckViewHolder singleCheckViewHolder, int i, @NonNull List<Object> list) {
        if (list.isEmpty()) {
            super.onBindViewHolder((RecyclerView.ViewHolder) singleCheckViewHolder, i, list);
        } else {
            onBindViewHolder(singleCheckViewHolder, i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public SingleCheckViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SingleCheckViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.makeup_item, viewGroup, false), this);
    }

    public void onItemHolderClick(SingleCheckViewHolder singleCheckViewHolder) {
        AdapterView.OnItemClickListener onItemClickListener2 = this.onItemClickListener;
        if (onItemClickListener2 != null) {
            onItemClickListener2.onItemClick(null, singleCheckViewHolder.itemView, singleCheckViewHolder.getAdapterPosition(), singleCheckViewHolder.getItemId());
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    public void setRotation(int i) {
        this.mDegree = i;
    }

    public void setSelectedPosition(int i) {
        this.mSelectedItem = i;
    }
}
