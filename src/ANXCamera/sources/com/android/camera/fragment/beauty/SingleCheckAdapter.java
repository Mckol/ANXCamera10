package com.android.camera.fragment.beauty;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.customization.TintColor;
import com.android.camera.data.DataRepository;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.ColorImageView;
import java.util.List;

public class SingleCheckAdapter extends RecyclerView.Adapter<SingleCheckViewHolder> {
    private ArgbEvaluator mArgbEvaluator;
    private Context mContext;
    private int mDegree;
    private boolean mEnableClick = true;
    private FloatEvaluator mFloatEvaluator;
    private int mItemHorizontalMargin = 0;
    private AdapterView.OnItemClickListener mOnItemClickListener;
    private int mPreSelectedItem = 0;
    private RecyclerView mRecyclerView;
    private int mSelectedItem = 0;
    private List<LevelItem> mSingleCheckList;

    public static class LevelItem {
        private String mText;
        private int mTextResource;

        public LevelItem(int i) {
            this.mTextResource = i;
        }

        public LevelItem(String str) {
            this.mText = str;
        }

        public String getText() {
            return this.mText;
        }

        public int getTextResource() {
            return this.mTextResource;
        }
    }

    /* access modifiers changed from: package-private */
    public class SingleCheckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private SingleCheckAdapter mAdapter;
        private ColorImageView mBase;
        private ImageView mImageView;
        private TextView mText;

        public SingleCheckViewHolder(View view, SingleCheckAdapter singleCheckAdapter) {
            super(view);
            this.mAdapter = singleCheckAdapter;
            this.mText = (TextView) view.findViewById(R.id.second_text);
            this.mImageView = (ImageView) view.findViewById(R.id.second_image);
            this.mBase = (ColorImageView) view.findViewById(R.id.second_base);
            this.mBase.setIsNeedTransparent(false, true);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mBase.getLayoutParams();
            marginLayoutParams.setMarginStart(SingleCheckAdapter.this.mItemHorizontalMargin);
            marginLayoutParams.setMarginEnd(SingleCheckAdapter.this.mItemHorizontalMargin);
            view.setOnClickListener(this);
            FolmeUtils.handleListItemTouch(view);
        }

        private void animateIn(final ColorImageView colorImageView, final TextView textView, final ImageView imageView) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat.setDuration(100L);
            ofFloat.setInterpolator(new LinearInterpolator() {
                /* class com.android.camera.fragment.beauty.SingleCheckAdapter.SingleCheckViewHolder.AnonymousClass3 */

                public float getInterpolation(float f) {
                    float interpolation = super.getInterpolation(f);
                    colorImageView.setColorAndRefresh(((Integer) SingleCheckAdapter.this.mArgbEvaluator.evaluate(interpolation, Integer.valueOf(SingleCheckAdapter.this.baseNormalColor()), Integer.valueOf(TintColor.tintColor()))).intValue());
                    textView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    ((ColorImageView) imageView).setColorAndRefresh(ViewCompat.MEASURED_STATE_MASK);
                    return interpolation;
                }
            });
            ofFloat.start();
        }

        private void animateOut(final ColorImageView colorImageView, final TextView textView, final ImageView imageView) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat.setDuration(100L);
            ofFloat.setInterpolator(new LinearInterpolator() {
                /* class com.android.camera.fragment.beauty.SingleCheckAdapter.SingleCheckViewHolder.AnonymousClass2 */

                public float getInterpolation(float f) {
                    float interpolation = super.getInterpolation(f);
                    colorImageView.setColorAndRefresh(((Integer) SingleCheckAdapter.this.mArgbEvaluator.evaluate(interpolation, Integer.valueOf(TintColor.tintColor()), Integer.valueOf(SingleCheckAdapter.this.baseNormalColor()))).intValue());
                    textView.setTextColor(-1);
                    ((ColorImageView) imageView).setColorAndRefresh(-1);
                    return interpolation;
                }
            });
            ofFloat.start();
        }

        private void colorAnimate(final ColorImageView colorImageView, int i, int i2) {
            ValueAnimator ofObject = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(i), Integer.valueOf(i2));
            ofObject.setDuration(200L);
            ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                /* class com.android.camera.fragment.beauty.SingleCheckAdapter.SingleCheckViewHolder.AnonymousClass1 */

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    colorImageView.setColorAndRefresh(((Integer) valueAnimator.getAnimatedValue()).intValue());
                }
            });
            ofObject.start();
        }

        public void onClick(View view) {
            ModeProtocol.CameraAction cameraAction;
            int adapterPosition;
            if (SingleCheckAdapter.this.mEnableClick && (cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161)) != null && !cameraAction.isDoingAction() && !cameraAction.isRecording() && (adapterPosition = getAdapterPosition()) != SingleCheckAdapter.this.mSelectedItem) {
                SingleCheckAdapter singleCheckAdapter = SingleCheckAdapter.this;
                singleCheckAdapter.mPreSelectedItem = singleCheckAdapter.mSelectedItem;
                SingleCheckAdapter.this.mSelectedItem = adapterPosition;
                SingleCheckViewHolder singleCheckViewHolder = (SingleCheckViewHolder) SingleCheckAdapter.this.mRecyclerView.findViewHolderForAdapterPosition(SingleCheckAdapter.this.mPreSelectedItem);
                SingleCheckViewHolder singleCheckViewHolder2 = (SingleCheckViewHolder) SingleCheckAdapter.this.mRecyclerView.findViewHolderForAdapterPosition(SingleCheckAdapter.this.mSelectedItem);
                animateOut(singleCheckViewHolder.mBase, singleCheckViewHolder.mText, singleCheckViewHolder.mImageView);
                animateIn(singleCheckViewHolder2.mBase, singleCheckViewHolder2.mText, singleCheckViewHolder2.mImageView);
                this.mAdapter.onItemHolderClick(this);
            }
        }

        public void setDateToView(LevelItem levelItem, int i) throws Exception {
            String str;
            if (TextUtils.isEmpty(levelItem.mText)) {
                this.mText.setVisibility(8);
                this.mImageView.setVisibility(0);
                this.mImageView.setImageResource(levelItem.getTextResource());
            } else {
                this.mText.setVisibility(0);
                this.mImageView.setVisibility(8);
                this.mText.setTextSize(0, (float) SingleCheckAdapter.this.mContext.getResources().getDimensionPixelSize(R.dimen.beauty_level_text_size));
                this.mText.setText(levelItem.getText());
            }
            this.mText.setAlpha(1.0f);
            TextView textView = this.mText;
            int i2 = SingleCheckAdapter.this.mSelectedItem;
            int i3 = ViewCompat.MEASURED_STATE_MASK;
            textView.setTextColor(i == i2 ? -16777216 : -1);
            this.mBase.setColor(i == SingleCheckAdapter.this.mSelectedItem ? TintColor.tintColor() : SingleCheckAdapter.this.baseNormalColor());
            ColorImageView colorImageView = (ColorImageView) this.mImageView;
            if (i != SingleCheckAdapter.this.mSelectedItem) {
                i3 = -1;
            }
            colorImageView.setColor(i3);
            if (!Util.isAccessible()) {
                return;
            }
            if (i == 0) {
                this.mText.setContentDescription(i == SingleCheckAdapter.this.mSelectedItem ? SingleCheckAdapter.this.mContext.getString(R.string.accessibility_beauty_switch_closed) : SingleCheckAdapter.this.mContext.getString(R.string.accessibility_beauty_switch_open));
                return;
            }
            TextView textView2 = this.mText;
            if (i == SingleCheckAdapter.this.mSelectedItem) {
                str = i + SingleCheckAdapter.this.mContext.getString(R.string.accessibility_open);
            } else {
                str = i + SingleCheckAdapter.this.mContext.getString(R.string.accessibility_closed);
            }
            textView2.setContentDescription(str);
        }
    }

    public SingleCheckAdapter(Context context, List<LevelItem> list, int i) {
        this.mSingleCheckList = list;
        this.mItemHorizontalMargin = i;
        this.mArgbEvaluator = new ArgbEvaluator();
        this.mFloatEvaluator = new FloatEvaluator();
        this.mContext = context;
    }

    public int baseNormalColor() {
        return DataRepository.dataItemGlobal().getCurrentMode() == 165 ? Color.argb(0.15f, 1.0f, 1.0f, 1.0f) : Color.argb(0.3f, 0.0f, 0.0f, 0.0f);
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

    public void onBindViewHolder(SingleCheckViewHolder singleCheckViewHolder, int i) {
        try {
            singleCheckViewHolder.setDateToView(this.mSingleCheckList.get(i), i);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        ViewCompat.setRotation(singleCheckViewHolder.itemView, (float) this.mDegree);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public SingleCheckViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SingleCheckViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.beauty_level_item, viewGroup, false), this);
    }

    public void onItemHolderClick(SingleCheckViewHolder singleCheckViewHolder) {
        AdapterView.OnItemClickListener onItemClickListener = this.mOnItemClickListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(null, singleCheckViewHolder.itemView, singleCheckViewHolder.getAdapterPosition(), singleCheckViewHolder.getItemId());
        }
    }

    public void setEnableClick(boolean z) {
        this.mEnableClick = z;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setRotation(int i) {
        this.mDegree = i;
    }

    public void setSelectedPosition(int i) {
        this.mSelectedItem = i;
    }
}
