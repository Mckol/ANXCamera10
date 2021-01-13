package com.android.camera.fragment.mode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.global.ComponentModuleList;
import com.android.camera.log.Log;
import com.android.camera.statistic.CameraStatUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;

public class ModeAdapter extends RecyclerView.Adapter<ModeViewHolder> {
    private static final int MAX_TOP_ITEM_COUNT = 10;
    private static final String TAG = "ModeAdapter";
    public static final int TYPE_CAPTURE = 3;
    public static final int TYPE_EDIT = 5;
    public static final int TYPE_FOOTER = 6;
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_LINE = 2;
    public static final int TYPE_VIDEO = 4;
    protected View.OnClickListener mClickListener;
    protected Context mContext;
    private float mDegree;
    protected int mFragmentType;
    private boolean mInit;
    protected List<ComponentDataItem> mItems;
    protected ComponentModuleList mModuleList;

    public static class ModeViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIconView;
        public TextView mNameView;

        public ModeViewHolder(@NonNull View view) {
            super(view);
            if (view.getId() == R.id.mode_item) {
                this.mIconView = (ImageView) view.findViewById(R.id.mode_icon);
                this.mNameView = (TextView) view.findViewById(R.id.mode_name);
            }
        }
    }

    public ModeAdapter(Context context, FragmentMoreModeBase fragmentMoreModeBase) {
        this.mContext = context;
        this.mModuleList = fragmentMoreModeBase.getComponentModuleList();
        this.mClickListener = fragmentMoreModeBase;
        this.mFragmentType = fragmentMoreModeBase.getType();
        if (this.mFragmentType == 2) {
            this.mItems = new ArrayList(this.mModuleList.getItems().subList(0, this.mModuleList.getItems().size() - 1));
        } else {
            this.mItems = this.mModuleList.getMoreItems();
        }
    }

    private ItemTouchHelper createTouchHelper() {
        if (this.mFragmentType != 2) {
            return null;
        }
        return new ItemTouchHelper(new ItemTouchHelper.Callback() {
            /* class com.android.camera.fragment.mode.ModeAdapter.AnonymousClass2 */
            private boolean isFull = false;

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder2) {
                if (viewHolder2.getItemViewType() == 1 || viewHolder2.getItemViewType() == 6) {
                    return false;
                }
                if (viewHolder2.getItemViewType() == 4 && viewHolder.getLayoutPosition() < viewHolder2.getLayoutPosition()) {
                    return false;
                }
                if (viewHolder2.getItemViewType() == 3 && viewHolder.getLayoutPosition() > viewHolder2.getLayoutPosition()) {
                    return false;
                }
                if (recyclerView.getAdapter() != null && viewHolder.getLayoutPosition() >= 12 && viewHolder2.getLayoutPosition() < 12) {
                    for (int i = 0; i < recyclerView.getAdapter().getItemCount(); i++) {
                        if (recyclerView.getAdapter().getItemViewType(i) == 2 && i > 10) {
                            if (!this.isFull) {
                                Log.d(ModeAdapter.TAG, "favorite mode full!");
                                this.isFull = true;
                                ToastUtils.showToast(ModeAdapter.this.mContext, (int) R.string.module_name_edit_full_toast, true);
                                CameraStatUtils.trackCommonModeFull();
                            }
                            return false;
                        }
                    }
                }
                return super.canDropOver(recyclerView, viewHolder, viewHolder2);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return ItemTouchHelper.Callback.makeMovementFlags((viewHolder.getItemViewType() == 1 || viewHolder.getItemViewType() == 6 || viewHolder.getItemViewType() == 2 || viewHolder.getItemViewType() == 3 || viewHolder.getItemViewType() == 4) ? 0 : 15, 0);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder2) {
                Log.d(ModeAdapter.TAG, "[onMove] current " + viewHolder + ",target = " + viewHolder2);
                int adapterPosition = viewHolder.getAdapterPosition();
                int adapterPosition2 = viewHolder2.getAdapterPosition();
                if (adapterPosition < adapterPosition2) {
                    for (int i = adapterPosition; i < adapterPosition2; i++) {
                        Collections.swap(ModeAdapter.this.mItems, i - 1, i);
                    }
                } else {
                    for (int i2 = adapterPosition; i2 > adapterPosition2; i2--) {
                        Collections.swap(ModeAdapter.this.mItems, i2 - 1, i2 - 2);
                    }
                }
                ModeAdapter.this.notifyItemMoved(adapterPosition, adapterPosition2);
                return true;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int i, @NonNull RecyclerView.ViewHolder viewHolder2, int i2, int i3, int i4) {
                super.onMoved(recyclerView, viewHolder, i, viewHolder2, i2, i3, i4);
                Log.d(ModeAdapter.TAG, "[onMoved]");
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int i) {
                super.onSelectedChanged(viewHolder, i);
                String str = ModeAdapter.TAG;
                Log.d(str, "[onSelectedChanged] viewHolder = " + viewHolder + ", actionState = " + i);
                if (viewHolder == null && this.isFull) {
                    this.isFull = false;
                }
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            }
        });
    }

    private int getHeaderHeightForNormal() {
        int i = 0;
        int height = Util.getDisplayRect(0).height() - this.mContext.getResources().getDimensionPixelOffset(R.dimen.mode_more_bottom_margin);
        int size = this.mItems.size() / 3;
        if (this.mItems.size() % 3 != 0) {
            i = 1;
        }
        int i2 = size + i;
        int i3 = 4;
        if (i2 <= 4) {
            i3 = i2;
        }
        int dimensionPixelOffset = this.mFragmentType == 0 ? this.mContext.getResources().getDimensionPixelOffset(R.dimen.mode_item_height_normal) : this.mContext.getResources().getDimensionPixelOffset(R.dimen.mode_item_height);
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("getHeaderHeightForNormal ");
        int i4 = height - (i3 * dimensionPixelOffset);
        sb.append(i4);
        Log.d(str, sb.toString());
        return i4;
    }

    private ComponentDataItem getItem(int i) {
        int i2 = this.mFragmentType;
        return (i2 == 0 || i2 == 2) ? this.mItems.get(i - 1) : this.mItems.get(i);
    }

    public List<ComponentDataItem> createChangeItems() {
        if (this.mFragmentType != 2) {
            return null;
        }
        List<ComponentDataItem> list = this.mItems;
        if (Integer.parseInt(list.get(list.size() - 1).mValue) != 255) {
            this.mItems.add(this.mModuleList.getItems().get(this.mModuleList.getItems().size() - 1));
        }
        return this.mItems;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        int i = this.mFragmentType;
        return i == 1 ? this.mItems.size() : i == 0 ? this.mItems.size() + 1 : this.mItems.size() + 2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        int i2 = this.mFragmentType;
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 == 2) {
                    if (i == 0) {
                        return 1;
                    }
                    if (i == this.mItems.size() + 1) {
                        return 6;
                    }
                    if (Integer.parseInt(getItem(i).mValue) == 254) {
                        return 2;
                    }
                    if (Integer.parseInt(getItem(i).mValue) == 163) {
                        return 3;
                    }
                    if (Integer.parseInt(getItem(i).mValue) == 162) {
                        return 4;
                    }
                }
            } else if (Integer.parseInt(getItem(i).mValue) == 255) {
                return 5;
            }
        } else if (i == 0) {
            return 1;
        } else {
            if (Integer.parseInt(getItem(i).mValue) == 255) {
                return 5;
            }
        }
        return super.getItemViewType(i);
    }

    public boolean isDataChange() {
        if (this.mFragmentType != 2) {
            return false;
        }
        List<ComponentDataItem> list = this.mItems;
        List<ComponentDataItem> subList = this.mModuleList.getItems().subList(0, this.mModuleList.getItems().size() - 1);
        if (list.size() == subList.size()) {
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).mValue.equals(subList.get(i).mValue)) {
                    return true;
                }
            }
            return false;
        }
        throw new IllegalStateException("data lost.");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        ItemTouchHelper createTouchHelper = createTouchHelper();
        if (createTouchHelper != null) {
            createTouchHelper.attachToRecyclerView(recyclerView);
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                /* class com.android.camera.fragment.mode.ModeAdapter.AnonymousClass1 */

                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i) {
                    int itemViewType = ModeAdapter.this.getItemViewType(i);
                    int i2 = ModeAdapter.this.mFragmentType;
                    if (i2 == 2) {
                        if (itemViewType == 2 || itemViewType == 1) {
                            return gridLayoutManager.getSpanCount();
                        }
                    } else if (i2 == 0 && (itemViewType == 1 || itemViewType == 6)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    public void onBindViewHolder(@NonNull ModeViewHolder modeViewHolder, int i) {
        if (this.mFragmentType == 0 && (modeViewHolder.getItemViewType() == 1 || modeViewHolder.getItemViewType() == 6)) {
            modeViewHolder.itemView.setVisibility(4);
            if (modeViewHolder.getItemViewType() == 1) {
                ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) modeViewHolder.itemView.getLayoutParams())).height = getHeaderHeightForNormal();
            }
        }
        if (modeViewHolder.getItemViewType() != 2 && modeViewHolder.getItemViewType() != 1 && modeViewHolder.getItemViewType() != 6) {
            ComponentDataItem item = getItem(i);
            modeViewHolder.itemView.setTag(item);
            modeViewHolder.itemView.setRotation(this.mDegree);
            int i2 = item.mIconRes;
            if (i2 != -1) {
                modeViewHolder.mIconView.setImageResource(i2);
                if (modeViewHolder.getItemViewType() == 5) {
                    modeViewHolder.mIconView.setBackgroundResource(R.drawable.bg_mode_item_edit);
                }
            }
            int i3 = item.mDisplayNameRes;
            if (i3 != 0) {
                modeViewHolder.mNameView.setText(i3);
            } else {
                String str = item.mDisplayNameStr;
                if (str != null) {
                    modeViewHolder.mNameView.setText(str);
                }
            }
            if (modeViewHolder.getItemViewType() == 3 || modeViewHolder.getItemViewType() == 4) {
                modeViewHolder.mNameView.setTextColor(this.mContext.getResources().getColor(R.color.mode_edit_icon_disable_name_color));
            } else {
                modeViewHolder.mNameView.setTextColor(this.mContext.getResources().getColor(R.color.mode_edit_icon_name_color));
            }
            if (this.mFragmentType == 0 && !this.mInit) {
                if (getItemViewType(i) == 5 || i >= 12) {
                    this.mInit = true;
                }
                MoreModeListAnimation.getInstance().startTranAnimation(modeViewHolder.itemView, i, 1);
                MoreModeListAnimation.getInstance().startAlphaAnimation(modeViewHolder.itemView, i);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ModeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int i2 = this.mFragmentType;
        if (i2 == 2) {
            if (i == 2) {
                return new ModeViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.mode_edit_dividing_line, viewGroup, false));
            }
            if (i == 1) {
                return new ModeViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.mode_edit_header, viewGroup, false));
            }
            if (i == 6) {
                View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.mode_item, viewGroup, false);
                inflate.setVisibility(4);
                return new ModeViewHolder(inflate);
            }
        } else if (i2 == 0 && i == 1) {
            View view = new View(this.mContext);
            view.setLayoutParams(new RecyclerView.LayoutParams(this.mContext.getResources().getDimensionPixelOffset(R.dimen.mode_icon_size), getHeaderHeightForNormal()));
            return new ModeViewHolder(view);
        }
        View inflate2 = LayoutInflater.from(this.mContext).inflate(R.layout.mode_item, viewGroup, false);
        ModeViewHolder modeViewHolder = new ModeViewHolder(inflate2);
        if (this.mFragmentType == 2) {
            inflate2.setOnClickListener(this.mClickListener);
        } else {
            inflate2.setOnClickListener(this.mClickListener);
            inflate2.setRotation(this.mDegree);
            Folme.useAt(modeViewHolder.mIconView).touch().handleTouchOf(inflate2, new AnimConfig[0]);
        }
        return modeViewHolder;
    }

    public void onViewAttachedToWindow(@NonNull ModeViewHolder modeViewHolder) {
        super.onViewAttachedToWindow((RecyclerView.ViewHolder) modeViewHolder);
        modeViewHolder.itemView.setRotation(this.mDegree);
    }

    public void setRotate(int i) {
        this.mDegree = (float) i;
    }
}
