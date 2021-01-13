package miuix.preference;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.ViewUtils;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import miui.util.AttributeResolver;
import miuix.springback.view.SpringBackLayout;

public abstract class PreferenceFragment extends androidx.preference.PreferenceFragment {
    private static final String DIALOG_FRAGMENT_TAG = "androidx.preference.PreferenceFragment.DIALOG";
    private boolean mAdapterInvalid = true;
    private FrameDecoration mFrameDecoration;
    private PreferenceGroupAdapter mGroupAdapter;

    private class FrameDecoration extends RecyclerView.ItemDecoration {
        private Paint mClipPaint;
        private int mHeightPixels;
        private int mMaskPaddingBottom;
        private int mMaskPaddingEnd;
        private int mMaskPaddingStart;
        private int mMaskPaddingTop;
        private int mMaskRadius;
        private PreferenceGroupRect mPreferenceGroupRect;
        private Map<Integer, PreferenceGroupRect> mPreferenceGroupRectMaps;

        private FrameDecoration(Context context) {
            this.mMaskPaddingTop = context.getResources().getDimensionPixelSize(R.dimen.miuix_preference_checkable_item_mask_padding_top);
            this.mMaskPaddingBottom = context.getResources().getDimensionPixelSize(R.dimen.miuix_preference_checkable_item_mask_padding_bottom);
            this.mMaskPaddingStart = context.getResources().getDimensionPixelSize(R.dimen.miuix_preference_checkable_item_mask_padding_start);
            this.mMaskPaddingEnd = context.getResources().getDimensionPixelSize(R.dimen.miuix_preference_checkable_item_mask_padding_end);
            this.mMaskRadius = context.getResources().getDimensionPixelSize(R.dimen.miuix_preference_checkable_item_mask_radius);
            this.mClipPaint = new Paint();
            this.mClipPaint.setColor(AttributeResolver.resolveColor(context, R.attr.preferenceCheckableMaskColor));
            this.mClipPaint.setAntiAlias(true);
            this.mPreferenceGroupRectMaps = new HashMap();
            this.mHeightPixels = context.getResources().getDisplayMetrics().heightPixels;
        }

        private boolean checkEndRadioButtonPreferenceCategory(RecyclerView recyclerView, int i, int i2) {
            int i3 = i + 1;
            if (i3 >= i2) {
                return false;
            }
            return !(PreferenceFragment.this.mGroupAdapter.getItem(recyclerView.getChildAdapterPosition(recyclerView.getChildAt(i3))) instanceof RadioSetPreferenceCategory);
        }

        private void drawMask(@NonNull Canvas canvas, int i, int i2, int i3, int i4, boolean z, boolean z2, boolean z3, boolean z4) {
            if (!PreferenceFragment.this.mAdapterInvalid) {
                float f = (float) i2;
                float f2 = (float) i4;
                RectF rectF = new RectF((float) i, f, (float) i3, f2);
                RectF rectF2 = new RectF((float) (i + (z4 ? this.mMaskPaddingEnd : this.mMaskPaddingStart)), f, (float) (i3 - (z4 ? this.mMaskPaddingStart : this.mMaskPaddingEnd)), f2);
                Path path = new Path();
                float f3 = 0.0f;
                float f4 = z ? (float) this.mMaskRadius : 0.0f;
                if (z2) {
                    f3 = (float) this.mMaskRadius;
                }
                path.addRoundRect(rectF2, new float[]{f4, f4, f4, f4, f3, f3, f3, f3}, Path.Direction.CW);
                int saveLayer = canvas.saveLayer(rectF, this.mClipPaint, 31);
                canvas.drawRect(rectF, this.mClipPaint);
                if (z3) {
                    this.mClipPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                } else {
                    this.mClipPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
                }
                canvas.drawPath(path, this.mClipPaint);
                this.mClipPaint.setXfermode(null);
                canvas.restoreToCount(saveLayer);
            }
        }

        private int findNearViewY(RecyclerView recyclerView, View view, int i, int i2, boolean z) {
            View childAt;
            if (!z) {
                for (int i3 = i - 1; i3 > i2; i3--) {
                    View childAt2 = recyclerView.getChildAt(i3);
                    if (childAt2 != null) {
                        return ((int) childAt2.getY()) + childAt2.getHeight();
                    }
                }
            } else if (view == null || view.getBottom() + view.getHeight() >= this.mHeightPixels) {
                return -1;
            } else {
                do {
                    i++;
                    if (i < i2) {
                        childAt = recyclerView.getChildAt(i);
                    }
                } while (childAt == null);
                return (int) childAt.getY();
            }
            return -1;
        }

        private void updateOperationTopAndBottom(RecyclerView recyclerView, PreferenceGroupRect preferenceGroupRect) {
            int i;
            int size = preferenceGroupRect.preferenceList.size();
            int i2 = 0;
            int i3 = 0;
            int i4 = -1;
            int i5 = -1;
            for (int i6 = 0; i6 < size; i6++) {
                View childAt = recyclerView.getChildAt(preferenceGroupRect.preferenceList.get(i6).intValue());
                if (childAt != null) {
                    int top = childAt.getTop();
                    int bottom = childAt.getBottom();
                    if (i6 == 0) {
                        i3 = bottom;
                        i2 = top;
                    }
                    if (i2 > top) {
                        i2 = top;
                    }
                    if (i3 < bottom) {
                        i3 = bottom;
                    }
                }
                int i7 = preferenceGroupRect.nextViewY;
                if (i7 != -1 && i7 > preferenceGroupRect.preViewHY) {
                    i4 = i7 - this.mMaskPaddingTop;
                }
                int i8 = preferenceGroupRect.preViewHY;
                if (i8 != -1 && i8 < (i = preferenceGroupRect.nextViewY)) {
                    i5 = i - this.mMaskPaddingTop;
                }
            }
            preferenceGroupRect.currentEndtb = new int[]{i2, i3};
            if (i4 != -1) {
                i3 = i4;
            }
            if (i5 != -1) {
                i2 = i5;
            }
            preferenceGroupRect.currentMovetb = new int[]{i2, i3};
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
            int childAdapterPosition;
            Preference item;
            if (!PreferenceFragment.this.mAdapterInvalid && (item = PreferenceFragment.this.mGroupAdapter.getItem((childAdapterPosition = recyclerView.getChildAdapterPosition(view)))) != null && (item.getParent() instanceof RadioSetPreferenceCategory)) {
                if (ViewUtils.isLayoutRtl(recyclerView)) {
                    rect.left = recyclerView.getScrollBarSize();
                } else {
                    rect.right = recyclerView.getScrollBarSize();
                }
                int positionType = PreferenceFragment.this.mGroupAdapter.getPositionType(childAdapterPosition);
                if (positionType == 1) {
                    rect.top += this.mMaskPaddingTop;
                    rect.bottom += this.mMaskPaddingBottom;
                } else if (positionType == 2) {
                    rect.top += this.mMaskPaddingTop;
                } else if (positionType == 4) {
                    rect.bottom += this.mMaskPaddingBottom;
                }
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
            int i;
            int i2;
            View view;
            int i3;
            if (!PreferenceFragment.this.mAdapterInvalid) {
                this.mPreferenceGroupRectMaps.clear();
                int childCount = recyclerView.getChildCount();
                int scrollBarSize = recyclerView.getScrollBarSize();
                boolean isLayoutRtl = ViewUtils.isLayoutRtl(recyclerView);
                if (isLayoutRtl) {
                    i2 = scrollBarSize;
                    i = recyclerView.getWidth();
                } else {
                    i = recyclerView.getWidth() - scrollBarSize;
                    i2 = 0;
                }
                for (int i4 = 0; i4 < childCount; i4++) {
                    View childAt = recyclerView.getChildAt(i4);
                    int childAdapterPosition = recyclerView.getChildAdapterPosition(childAt);
                    Preference item = PreferenceFragment.this.mGroupAdapter.getItem(childAdapterPosition);
                    if (item != null && (item.getParent() instanceof RadioSetPreferenceCategory)) {
                        int positionType = PreferenceFragment.this.mGroupAdapter.getPositionType(childAdapterPosition);
                        if (positionType == 1 || positionType == 2) {
                            this.mPreferenceGroupRect = new PreferenceGroupRect();
                            PreferenceGroupRect preferenceGroupRect = this.mPreferenceGroupRect;
                            preferenceGroupRect.startRadioButtonCategory = true;
                            i3 = positionType;
                            view = childAt;
                            preferenceGroupRect.preViewHY = findNearViewY(recyclerView, childAt, i4, 0, false);
                            this.mPreferenceGroupRect.addPreference(i4);
                        } else {
                            i3 = positionType;
                            view = childAt;
                        }
                        if (this.mPreferenceGroupRect != null && (i3 == 4 || i3 == 3)) {
                            this.mPreferenceGroupRect.addPreference(i4);
                        }
                        if (this.mPreferenceGroupRect != null && (i3 == 1 || i3 == 4)) {
                            this.mPreferenceGroupRect.nextViewY = findNearViewY(recyclerView, view, i4, childCount, true);
                            this.mPreferenceGroupRect.index = this.mPreferenceGroupRectMaps.size();
                            this.mPreferenceGroupRect.endRadioButtonCategory = checkEndRadioButtonPreferenceCategory(recyclerView, i4, childCount);
                            PreferenceGroupRect preferenceGroupRect2 = this.mPreferenceGroupRect;
                            preferenceGroupRect2.type = 4;
                            this.mPreferenceGroupRectMaps.put(Integer.valueOf(preferenceGroupRect2.index), this.mPreferenceGroupRect);
                            this.mPreferenceGroupRect = null;
                        }
                    }
                }
                PreferenceGroupRect preferenceGroupRect3 = this.mPreferenceGroupRect;
                if (preferenceGroupRect3 != null && preferenceGroupRect3.preferenceList.size() > 0) {
                    PreferenceGroupRect preferenceGroupRect4 = this.mPreferenceGroupRect;
                    preferenceGroupRect4.nextViewY = -1;
                    preferenceGroupRect4.index = this.mPreferenceGroupRectMaps.size();
                    PreferenceGroupRect preferenceGroupRect5 = this.mPreferenceGroupRect;
                    preferenceGroupRect5.endRadioButtonCategory = false;
                    preferenceGroupRect5.type = -1;
                    this.mPreferenceGroupRectMaps.put(Integer.valueOf(preferenceGroupRect5.index), this.mPreferenceGroupRect);
                    this.mPreferenceGroupRect = null;
                }
                Map<Integer, PreferenceGroupRect> map = this.mPreferenceGroupRectMaps;
                if (map != null && map.size() > 0) {
                    for (Map.Entry<Integer, PreferenceGroupRect> entry : this.mPreferenceGroupRectMaps.entrySet()) {
                        updateOperationTopAndBottom(recyclerView, entry.getValue());
                    }
                    for (Map.Entry<Integer, PreferenceGroupRect> entry2 : this.mPreferenceGroupRectMaps.entrySet()) {
                        int intValue = entry2.getKey().intValue();
                        PreferenceGroupRect value = entry2.getValue();
                        int i5 = value.currentMovetb[1];
                        int i6 = intValue == 0 ? value.currentEndtb[0] : value.preViewHY + this.mMaskPaddingBottom;
                        drawMask(canvas, i2, i6 - this.mMaskPaddingTop, i, i6, false, false, true, isLayoutRtl);
                        drawMask(canvas, i2, i5, i, i5 + this.mMaskPaddingBottom, false, false, true, isLayoutRtl);
                        drawMask(canvas, i2, i6, i, i5, true, true, false, isLayoutRtl);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public class PreferenceGroupRect {
        public int[] currentEndtb;
        public int[] currentMovetb;
        public boolean endRadioButtonCategory;
        public int index;
        public int nextViewY;
        public int preViewHY;
        public List<Integer> preferenceList;
        public boolean startRadioButtonCategory;
        public int type;

        private PreferenceGroupRect() {
            this.preferenceList = new ArrayList();
            this.currentMovetb = null;
            this.currentEndtb = null;
            this.index = 0;
            this.preViewHY = -1;
            this.nextViewY = -1;
            this.endRadioButtonCategory = false;
            this.startRadioButtonCategory = false;
            this.type = -1;
        }

        public void addPreference(int i) {
            this.preferenceList.add(Integer.valueOf(i));
        }

        public String toString() {
            return "PreferenceGroupRect{preferenceList=" + this.preferenceList + ", currentMovetb=" + Arrays.toString(this.currentMovetb) + ", currentEndtb=" + Arrays.toString(this.currentEndtb) + ", index=" + this.index + ", preViewHY=" + this.preViewHY + ", nextViewY=" + this.nextViewY + ", end=" + this.endRadioButtonCategory + '}';
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.preference.PreferenceFragment
    public final RecyclerView.Adapter onCreateAdapter(PreferenceScreen preferenceScreen) {
        this.mGroupAdapter = new PreferenceGroupAdapter(preferenceScreen);
        boolean z = true;
        if (this.mGroupAdapter.getItemCount() >= 1) {
            z = false;
        }
        this.mAdapterInvalid = z;
        return this.mGroupAdapter;
    }

    @Override // androidx.preference.PreferenceFragment
    public RecyclerView onCreateRecyclerView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        RecyclerView recyclerView = (RecyclerView) layoutInflater.inflate(R.layout.miuix_preference_recyclerview, viewGroup, false);
        if (recyclerView instanceof miuix.recyclerview.widget.RecyclerView) {
            ((miuix.recyclerview.widget.RecyclerView) recyclerView).setSpringEnabled(false);
        }
        recyclerView.setLayoutManager(onCreateLayoutManager());
        this.mFrameDecoration = new FrameDecoration(recyclerView.getContext());
        recyclerView.addItemDecoration(this.mFrameDecoration);
        if (viewGroup instanceof SpringBackLayout) {
            ((SpringBackLayout) viewGroup).setTarget(recyclerView);
        }
        return recyclerView;
    }

    @Override // androidx.preference.PreferenceManager.OnDisplayPreferenceDialogListener, androidx.preference.PreferenceFragment
    public void onDisplayPreferenceDialog(Preference preference) {
        DialogFragment dialogFragment;
        boolean onPreferenceDisplayDialog = getCallbackFragment() instanceof PreferenceFragment.OnPreferenceDisplayDialogCallback ? ((PreferenceFragment.OnPreferenceDisplayDialogCallback) getCallbackFragment()).onPreferenceDisplayDialog(this, preference) : false;
        if (!onPreferenceDisplayDialog && (getActivity() instanceof PreferenceFragment.OnPreferenceDisplayDialogCallback)) {
            onPreferenceDisplayDialog = ((PreferenceFragment.OnPreferenceDisplayDialogCallback) getActivity()).onPreferenceDisplayDialog(this, preference);
        }
        if (!onPreferenceDisplayDialog && getFragmentManager().findFragmentByTag(DIALOG_FRAGMENT_TAG) == null) {
            if (preference instanceof EditTextPreference) {
                dialogFragment = EditTextPreferenceDialogFragmentCompat.newInstance(preference.getKey());
            } else if (preference instanceof ListPreference) {
                dialogFragment = ListPreferenceDialogFragmentCompat.newInstance(preference.getKey());
            } else if (preference instanceof MultiSelectListPreference) {
                dialogFragment = MultiSelectListPreferenceDialogFragmentCompat.newInstance(preference.getKey());
            } else {
                throw new IllegalArgumentException("Cannot display dialog for an unknown Preference type: " + preference.getClass().getSimpleName() + ". Make sure to implement onPreferenceDisplayDialog() to handle displaying a custom dialog for this Preference.");
            }
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
        }
    }
}
