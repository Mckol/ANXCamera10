package com.miui.internal.util;

import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import com.miui.internal.variable.Android_Graphics_Drawable_Drawable_class;
import com.miui.internal.variable.Android_View_ViewGroup_class;
import com.miui.internal.variable.Android_View_View_class;
import miui.R;
import miui.util.ViewUtils;
import miui.view.OnTaggingDrawableState;

public class TaggingDrawableHelper {
    static int[] CHILD_SEQUENCE_STATE = {R.attr.children_sequence_state};
    private static final Android_Graphics_Drawable_Drawable_class DrawableClass = Android_Graphics_Drawable_Drawable_class.Factory.getInstance().get();
    private static final Android_View_View_class ViewClass = Android_View_View_class.Factory.getInstance().get();
    private static final Android_View_ViewGroup_class ViewGroupClass = Android_View_ViewGroup_class.Factory.getInstance().get();
    private static final ThreadLocal<Rect> sRect = new ThreadLocal<>();

    private TaggingDrawableHelper() {
    }

    private static int getOrientationState(ViewGroup viewGroup, int i, boolean z) {
        boolean z2;
        boolean z3;
        if (viewGroup instanceof OnTaggingDrawableState) {
            OnTaggingDrawableState onTaggingDrawableState = (OnTaggingDrawableState) viewGroup;
            z3 = onTaggingDrawableState.sameWithNeighbour(viewGroup, i, z, false);
            z2 = onTaggingDrawableState.sameWithNeighbour(viewGroup, i, z, true);
        } else {
            z3 = sameWithNeighbour(viewGroup, i, z, false);
            z2 = sameWithNeighbour(viewGroup, i, z, true);
        }
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(viewGroup);
        return z3 ? z2 ? z ? R.attr.state_middle_h : R.attr.state_middle_v : isLayoutRtl ? z ? R.attr.state_first_h : R.attr.state_last_v : z ? R.attr.state_last_h : R.attr.state_last_v : z2 ? isLayoutRtl ? z ? R.attr.state_last_h : R.attr.state_first_v : z ? R.attr.state_first_h : R.attr.state_first_v : z ? R.attr.state_single_h : R.attr.state_single_v;
    }

    public static void initTagChildSequenceState(ViewGroup viewGroup, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = viewGroup.getContext().obtainStyledAttributes(attributeSet, CHILD_SEQUENCE_STATE);
        boolean z = obtainStyledAttributes.getBoolean(0, true);
        obtainStyledAttributes.recycle();
        ViewGroupClass.setTagChildrenSequenceState(viewGroup, z);
    }

    public static void initViewSequenceStates(View view, AttributeSet attributeSet) {
        if (!ViewClass.hasInitViewSequenceStates(view)) {
            ViewClass.setHasInitViewSequenceStates(view, true);
            TypedArray obtainStyledAttributes = view.getContext().obtainStyledAttributes(attributeSet, R.styleable.DrawableStates);
            int length = R.styleable.DrawableStates.length;
            int i = R.attr.state_single_h;
            int i2 = R.attr.state_single_v;
            int i3 = i;
            for (int i4 = 0; i4 < length; i4++) {
                if (obtainStyledAttributes.getBoolean(i4, false)) {
                    if (i4 == R.styleable.DrawableStates_state_first_h || i4 == R.styleable.DrawableStates_state_middle_h || i4 == R.styleable.DrawableStates_state_last_h || i4 == R.styleable.DrawableStates_state_single_h) {
                        i3 = R.styleable.DrawableStates[i4];
                    } else if (i4 == R.styleable.DrawableStates_state_first_v || i4 == R.styleable.DrawableStates_state_middle_v || i4 == R.styleable.DrawableStates_state_last_v || i4 == R.styleable.DrawableStates_state_single_v) {
                        i2 = R.styleable.DrawableStates[i4];
                    }
                }
            }
            obtainStyledAttributes.recycle();
            ViewClass.setHorizontalState(view, i3);
            ViewClass.setVerticalState(view, i2);
        }
    }

    private static boolean isSameBackground(View view, View view2) {
        if (view == null || view2 == null) {
            return false;
        }
        Drawable background = view.getBackground();
        Drawable background2 = view2.getBackground();
        if (background == background2) {
            return true;
        }
        if (background == null || background2 == null) {
            return false;
        }
        int id = DrawableClass.getId(background);
        return id != 0 && id == DrawableClass.getId(background2);
    }

    public static void onDrawableStateChange(View view) {
        Drawable background = view.getBackground();
        if (background != null && background.isStateful()) {
            Drawable.ConstantState constantState = background.getConstantState();
            if (background.isStateful() && constantState != null && (constantState instanceof DrawableContainer.DrawableContainerState) && ((DrawableContainer.DrawableContainerState) constantState).getConstantPadding() == null) {
                Rect rect = sRect.get();
                if (rect == null) {
                    rect = new Rect();
                    sRect.set(rect);
                }
                if (!background.getPadding(rect)) {
                    return;
                }
                if (rect.left != view.getPaddingLeft() || rect.top != view.getPaddingTop() || rect.right != view.getPaddingRight() || rect.bottom != view.getPaddingBottom()) {
                    if (DrawableClass.getLayoutDirection(background) != 1) {
                        view.setPadding(rect.left, rect.top, rect.right, rect.bottom);
                    } else {
                        view.setPadding(rect.right, rect.top, rect.left, rect.bottom);
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a4, code lost:
        if (r3 == 0) goto L_0x00ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00a8, code lost:
        if (r3 == 1) goto L_0x00ac;
     */
    private static boolean sameWithNeighbour(ViewGroup viewGroup, int i, boolean z, boolean z2) {
        boolean z3;
        int i2;
        boolean z4 = true;
        if (viewGroup instanceof AbsListView) {
            AbsListView absListView = (AbsListView) viewGroup;
            int numColumns = absListView instanceof GridView ? ((GridView) absListView).getNumColumns() : 1;
            int childCount = absListView.getChildCount();
            if (z) {
                int i3 = i % numColumns;
                if (numColumns == 1) {
                    return false;
                }
                if (z2) {
                    if (i3 + 1 >= numColumns || (i2 = i + 1) >= childCount) {
                        return false;
                    }
                    return isSameBackground(absListView.getChildAt(i), absListView.getChildAt(i2));
                } else if (i3 - 1 >= 0) {
                    return isSameBackground(absListView.getChildAt(i), absListView.getChildAt(i - 1));
                } else {
                    return false;
                }
            } else {
                int firstVisiblePosition = absListView.getFirstVisiblePosition();
                Adapter adapter = absListView.getAdapter();
                int count = absListView.getCount();
                if (z2) {
                    int i4 = numColumns + i;
                    if (i4 < childCount) {
                        z3 = isSameBackground(absListView.getChildAt(i), absListView.getChildAt(i4));
                        return z3;
                    }
                    int i5 = i4 + firstVisiblePosition;
                    if (i5 >= count || adapter.getItemViewType(i + firstVisiblePosition) != adapter.getItemViewType(i5)) {
                        return false;
                    }
                } else {
                    int i6 = i - numColumns;
                    if (i6 >= 0) {
                        z3 = isSameBackground(absListView.getChildAt(i), absListView.getChildAt(i6));
                        return z3;
                    }
                    int i7 = i6 + firstVisiblePosition;
                    if (i7 < 0 || adapter.getItemViewType(i + firstVisiblePosition) != adapter.getItemViewType(i7)) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            boolean z5 = viewGroup instanceof LinearLayout;
            if (z5) {
                int orientation = ((LinearLayout) viewGroup).getOrientation();
                if (z) {
                }
                if (!z) {
                }
            }
            z4 = false;
            if (z4 || (!z5 && z)) {
                return sameWithNeighbourInLinear(viewGroup, i, z2);
            }
            return false;
        }
    }

    private static boolean sameWithNeighbourInLinear(ViewGroup viewGroup, int i, boolean z) {
        int i2 = -1;
        int i3 = z ? 1 : -1;
        if (z) {
            i2 = viewGroup.getChildCount();
        }
        View childAt = viewGroup.getChildAt(i);
        View view = null;
        while (true) {
            i += i3;
            if (i == i2) {
                break;
            }
            View childAt2 = viewGroup.getChildAt(i);
            if (childAt2.getVisibility() != 8) {
                view = childAt2;
                break;
            }
        }
        return (view == null || view.getVisibility() == 4 || !isSameBackground(view, childAt)) ? false : true;
    }

    public static void tagChildSequenceState(ViewGroup viewGroup) {
        if (ViewGroupClass.getTagChildrenSequenceState(viewGroup)) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt.getVisibility() == 0) {
                    tagView(childAt, getOrientationState(viewGroup, i, true), getOrientationState(viewGroup, i, false));
                }
            }
        }
    }

    private static void tagView(View view, int i, int i2) {
        int horizontalState = ViewClass.getHorizontalState(view);
        int verticalState = ViewClass.getVerticalState(view);
        if (horizontalState != i || verticalState != i2) {
            ViewClass.setHorizontalState(view, i);
            ViewClass.setVerticalState(view, i2);
            int paddingLeft = view.getPaddingLeft();
            int paddingTop = view.getPaddingTop();
            int paddingRight = view.getPaddingRight();
            int paddingBottom = view.getPaddingBottom();
            view.refreshDrawableState();
            if (paddingLeft != view.getPaddingLeft() || paddingTop != view.getPaddingTop() || paddingRight != view.getPaddingRight() || paddingBottom != view.getPaddingBottom()) {
                ViewClass.relayout(view);
            }
        }
    }
}
