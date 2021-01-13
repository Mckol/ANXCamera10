package com.android.camera.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.customization.TintColor;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.global.ComponentModuleList;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import java.util.Arrays;
import java.util.List;
import miuix.view.animation.CubicEaseOutInterpolator;

public class ModeSelectView extends RecyclerView implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final int BOUNCE_RANGE = 100;
    private static final String TAG = "ModeSelectView";
    public static final int TYPE_HEADER_FOOTER = 1;
    public static final int TYPE_NORMAL = 0;
    private ModeSelectAdapter mAdapter;
    private int mCurMode;
    private boolean mInit = false;
    private boolean mIsModeChange;
    private List<ComponentDataItem> mItems;
    private ModeLayoutManager mLayoutManager;
    private onModeSelectedListener mModeSelectedListener;
    private TextView mPaintView;
    private ModeSnapHelper mSnapHelper;
    private int mTempMode;

    public class ModeLayoutManager extends LinearLayoutManager {
        public ModeLayoutManager(Context context) {
            super(context);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager, androidx.recyclerview.widget.LinearLayoutManager
        public void onLayoutCompleted(RecyclerView.State state) {
            super.onLayoutCompleted(state);
        }
    }

    public class ModeSelectAdapter extends RecyclerView.Adapter<ModeSelectViewHolder> {
        public ModeSelectAdapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return ModeSelectView.this.mItems.size() + 2;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            return (i < 1 || i >= getItemCount() - 1) ? 1 : 0;
        }

        public /* synthetic */ void j(View view) {
            if (ModeSelectView.this.canScroll()) {
                int modeFromTag = ModeSelectView.this.getModeFromTag(view);
                ModeSelectView modeSelectView = ModeSelectView.this;
                if (!modeSelectView.isSameMode(modeFromTag, modeSelectView.mCurMode)) {
                    String str = ModeSelectView.TAG;
                    Log.d(str, "mode change , mCurMode = " + ModeSelectView.this.mCurMode + ", newMode = " + modeFromTag);
                    ModeSelectView.this.mCurMode = modeFromTag;
                    if (ModeSelectView.this.mModeSelectedListener != null) {
                        ModeSelectView.this.mModeSelectedListener.onModeSelected(ModeSelectView.this.mCurMode);
                    }
                    int[] calculateDistanceToFinalSnap = ModeSelectView.this.mSnapHelper.calculateDistanceToFinalSnap(ModeSelectView.this.mLayoutManager, view);
                    ModeSelectView.this.smoothScrollBy(calculateDistanceToFinalSnap[0], calculateDistanceToFinalSnap[1], new CubicEaseOutInterpolator());
                }
            }
        }

        public void onBindViewHolder(@NonNull ModeSelectViewHolder modeSelectViewHolder, int i) {
            if (getItemViewType(i) == 0) {
                modeSelectViewHolder.mModeItem.setText(ModeSelectView.this.getItemText(i));
                modeSelectViewHolder.mModeItem.setTextColor(ModeSelectView.this.isItemSelected(i) ? TintColor.tintColor() : ModeSelectView.this.getResources().getColor(R.color.mode_name_color));
                modeSelectViewHolder.itemView.setTag(Integer.valueOf(ModeSelectView.this.getModeByPos(i)));
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @NonNull
        public ModeSelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            if (i == 1) {
                View view = new View(ModeSelectView.this.getContext());
                view.setLayoutParams(new RecyclerView.LayoutParams(ModeSelectView.this.getHeaderOffset() + 100, -1));
                Log.d(ModeSelectView.TAG, "[onCreateViewHolder] h&f");
                return new ModeSelectViewHolder(view);
            }
            FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(ModeSelectView.this.getContext()).inflate(R.layout.mode_select_item, viewGroup, false);
            frameLayout.setOnClickListener(new e(this));
            return new ModeSelectViewHolder(frameLayout);
        }
    }

    public class ModeSelectViewHolder extends RecyclerView.ViewHolder {
        TextView mModeItem;

        public ModeSelectViewHolder(@NonNull View view) {
            super(view);
            this.mModeItem = (TextView) view.findViewById(R.id.mode_select_item);
        }
    }

    public class ModeSnapHelper extends LinearSnapHelper {
        public ModeSnapHelper() {
        }

        @Override // androidx.recyclerview.widget.SnapHelper, androidx.recyclerview.widget.LinearSnapHelper
        public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View view) {
            return super.calculateDistanceToFinalSnap(layoutManager, view);
        }

        @Override // androidx.recyclerview.widget.SnapHelper
        public int[] calculateScrollDistance(int i, int i2) {
            int[] iArr = new int[2];
            View findSnapView = findSnapView(ModeSelectView.this.mLayoutManager);
            if (findSnapView != null) {
                int width = findSnapView.getWidth();
                if (i <= 0) {
                    width = -width;
                }
                iArr[0] = width;
            }
            return iArr;
        }

        @Override // androidx.recyclerview.widget.SnapHelper, androidx.recyclerview.widget.LinearSnapHelper
        public View findSnapView(RecyclerView.LayoutManager layoutManager) {
            return super.findSnapView(layoutManager);
        }

        @Override // androidx.recyclerview.widget.SnapHelper, androidx.recyclerview.widget.LinearSnapHelper
        public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int i, int i2) {
            return super.findTargetSnapPosition(layoutManager, i, i2);
        }
    }

    public interface onModeSelectedListener {
        void onModeSelected(int i);
    }

    public ModeSelectView(@NonNull Context context) {
        super(context);
    }

    public ModeSelectView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ModeSelectView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean canScroll() {
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction != null && cameraAction.isDoingAction()) {
            return false;
        }
        ModeProtocol.ModeChangeController modeChangeController = (ModeProtocol.ModeChangeController) ModeCoordinatorImpl.getInstance().getAttachProtocol(179);
        return modeChangeController == null || modeChangeController.canSwipeChangeMode();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getHeaderOffset() {
        if (this.mPaintView == null) {
            this.mPaintView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.mode_select_item, (ViewGroup) null, false).findViewById(R.id.mode_select_item);
        }
        String itemText = getItemText(getSelectPos());
        return (Util.getScreenWidth(getContext()) - (((int) this.mPaintView.getPaint().measureText(itemText)) + (getResources().getDimensionPixelSize(R.dimen.mode_select_item_gap) * 2))) / 2;
    }

    private int getItemMode(int i) {
        if (i == 180) {
            return 167;
        }
        if (i == 176) {
            return 166;
        }
        return i;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getItemText(int i) {
        if (i <= 0) {
            String str = TAG;
            Log.w(str, "getItemText fail, pos is " + i);
            return "";
        }
        ComponentDataItem componentDataItem = this.mItems.get(i - 1);
        if (componentDataItem.mDisplayNameRes != 0) {
            return getContext().getString(componentDataItem.mDisplayNameRes);
        }
        String str2 = componentDataItem.mDisplayNameStr;
        if (str2 != null) {
            return str2;
        }
        throw new IllegalStateException("can't find mode text.");
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getModeByPos(int i) {
        if (getAdapter() == null || i == 0 || i == getAdapter().getItemCount()) {
            return 163;
        }
        return Integer.parseInt(this.mItems.get(i - 1).mValue);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getModeFromTag(View view) {
        Object tag = view.getTag();
        if (tag != null) {
            return ((Integer) tag).intValue();
        }
        return 160;
    }

    private int getPosition(int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < this.mItems.size(); i3++) {
            if (Integer.parseInt(this.mItems.get(i3).mValue) == i) {
                return i3 + 1;
            }
            if (163 == Integer.parseInt(this.mItems.get(i3).mValue)) {
                i2 = i3 + 1;
            }
        }
        return i2;
    }

    private int getSelectPos() {
        return getPosition(this.mCurMode);
    }

    private int getSelectedMode(int i) {
        View findSnapView = this.mSnapHelper.findSnapView(this.mLayoutManager);
        if (findSnapView != null) {
            return getModeFromTag(findSnapView);
        }
        Log.d(TAG, "target is null???");
        return i;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isItemSelected(int i) {
        if (i > 0) {
            return Integer.parseInt(this.mItems.get(i - 1).mValue) == this.mCurMode;
        }
        String str = TAG;
        Log.w(str, "isItemSelected fail, pos is " + i);
        return false;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isSameMode(int i, int i2) {
        boolean z = i == i2;
        if (i == 163 && i2 == 165) {
            return true;
        }
        return z;
    }

    private void updateSelectedItemColor(int i) {
        ModeSelectViewHolder modeSelectViewHolder;
        if (this.mItems != null && this.mLayoutManager != null) {
            int i2 = 0;
            while (i2 < this.mItems.size()) {
                i2++;
                View findViewByPosition = this.mLayoutManager.findViewByPosition(i2);
                if (!(findViewByPosition == null || (modeSelectViewHolder = (ModeSelectViewHolder) findContainingViewHolder(findViewByPosition)) == null || modeSelectViewHolder.mModeItem == null)) {
                    if (isSameMode(getModeFromTag(findViewByPosition), i)) {
                        modeSelectViewHolder.mModeItem.setTextColor(TintColor.tintColor());
                    } else {
                        modeSelectViewHolder.mModeItem.setTextColor(getResources().getColor(R.color.mode_name_color));
                    }
                }
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (canScroll()) {
            return super.dispatchTouchEvent(motionEvent);
        }
        Log.v(TAG, "dispatchTouchEvent skip.");
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public boolean fling(int i, int i2) {
        boolean fling = super.fling(i, i2);
        if (!this.mIsModeChange) {
            return fling;
        }
        return false;
    }

    public int getCurSelectMode() {
        return this.mCurMode;
    }

    public void init(ComponentModuleList componentModuleList, int i, onModeSelectedListener onmodeselectedlistener) {
        String str = TAG;
        Log.d(str, "init curMode = " + i);
        this.mModeSelectedListener = onmodeselectedlistener;
        this.mItems = componentModuleList.getCommonItems();
        String str2 = TAG;
        Log.d(str2, "init mItems = " + Arrays.toString(this.mItems.toArray()));
        this.mCurMode = getItemMode(i);
        this.mTempMode = this.mCurMode;
        if (this.mAdapter == null) {
            this.mAdapter = new ModeSelectAdapter();
            setAdapter(this.mAdapter);
        }
        if (this.mLayoutManager == null) {
            this.mLayoutManager = new ModeLayoutManager(getContext());
            this.mLayoutManager.setOrientation(0);
            setLayoutManager(this.mLayoutManager);
        }
        if (this.mSnapHelper == null) {
            this.mSnapHelper = new ModeSnapHelper();
            this.mSnapHelper.attachToRecyclerView(this);
        }
        this.mInit = false;
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public void moveToPosition(int i) {
        if (this.mCurMode != i) {
            this.mCurMode = i;
            this.mTempMode = i;
        }
        int position = getPosition(i);
        int headerOffset = getHeaderOffset();
        String str = TAG;
        Log.d(str, "moveToPosition select pos = " + position + ", offset = " + headerOffset + ", mode = " + i);
        this.mLayoutManager.scrollToPositionWithOffset(position, headerOffset);
        post(new f(this));
    }

    public /* synthetic */ void oa() {
        updateSelectedItemColor(this.mCurMode);
    }

    public void onGlobalLayout() {
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
        if (!this.mInit) {
            String str = TAG;
            Log.d(str, "onGlobalLayout mCurMode " + this.mCurMode);
            moveToPosition(this.mCurMode);
            this.mInit = true;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void onScrollStateChanged(int i) {
        super.onScrollStateChanged(i);
        int selectedMode = getSelectedMode(this.mCurMode);
        String str = TAG;
        Log.d(str, "onScrollStateChanged state = " + i);
        if (i == 0 && this.mCurMode != selectedMode) {
            this.mIsModeChange = false;
            String str2 = TAG;
            Log.d(str2, "mode change , mCurMode = " + this.mCurMode + ", newMode = " + selectedMode);
            this.mCurMode = selectedMode;
            onModeSelectedListener onmodeselectedlistener = this.mModeSelectedListener;
            if (onmodeselectedlistener != null) {
                onmodeselectedlistener.onModeSelected(this.mCurMode);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void onScrolled(int i, int i2) {
        super.onScrolled(i, i2);
        if (this.mInit) {
            int selectedMode = getSelectedMode(this.mTempMode);
            if (!isSameMode(selectedMode, this.mTempMode)) {
                updateSelectedItemColor(selectedMode);
                Log.d(TAG, "onScrolled");
                this.mTempMode = selectedMode;
                this.mIsModeChange = true;
                if (getScrollState() != 0) {
                    ViberatorContext.getInstance(getContext().getApplicationContext()).performModeSwitch();
                }
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (canScroll()) {
            return super.onTouchEvent(motionEvent);
        }
        Log.v(TAG, "onTouchEvent skip.");
        return false;
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        updateSelectedItemColor(this.mCurMode);
    }

    public void refresh() {
        this.mLayoutManager.scrollToPositionWithOffset(getPosition(this.mCurMode), getHeaderOffset());
    }

    public void setItems(List<ComponentDataItem> list) {
        List<ComponentDataItem> list2 = this.mItems;
        if (list2 != null) {
            list2.clear();
            this.mItems.addAll(list);
            return;
        }
        this.mItems = list;
    }
}
