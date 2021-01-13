package miui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.EditText;
import com.miui.internal.R;
import com.miui.internal.widget.ExploreByTouchHelper;
import java.util.List;
import miui.util.ViewUtils;

public class ClearableEditText extends EditText {
    private static final int[] EMPTY_STATE_SET = {16842921};
    private AccessHelper mAccessHelper;
    private Drawable mDrawable;
    private boolean mPressed;
    private boolean mShown;

    /* access modifiers changed from: private */
    public class AccessHelper extends ExploreByTouchHelper {
        private static final int CHILD_ID = 0;
        private final View forView;
        private final Rect mTempParentBounds = new Rect();

        public AccessHelper(View view) {
            super(view);
            this.forView = view;
        }

        private void getChildRect(Rect rect) {
            this.forView.getLocalVisibleRect(this.mTempParentBounds);
            if (ViewUtils.isLayoutRtl(ClearableEditText.this)) {
                rect.right -= (ClearableEditText.this.getWidth() - ClearableEditText.this.mDrawable.getIntrinsicWidth()) - (ClearableEditText.this.getPaddingLeft() * 2);
            } else {
                rect.left += (ClearableEditText.this.getWidth() - (ClearableEditText.this.getPaddingRight() * 2)) - ClearableEditText.this.mDrawable.getIntrinsicWidth();
            }
        }

        private CharSequence getDescription() {
            return ClearableEditText.this.getResources().getString(R.string.clearable_edittext_clear_description);
        }

        private boolean isVirtualView(float f, float f2) {
            return ViewUtils.isLayoutRtl(ClearableEditText.this) ? f < ((float) (ClearableEditText.this.mDrawable.getIntrinsicWidth() + (ClearableEditText.this.getPaddingLeft() * 2))) : f > ((float) ((ClearableEditText.this.getWidth() - (ClearableEditText.this.getPaddingRight() * 2)) - ClearableEditText.this.mDrawable.getIntrinsicWidth()));
        }

        /* access modifiers changed from: protected */
        @Override // com.miui.internal.widget.ExploreByTouchHelper
        public int getVirtualViewAt(float f, float f2) {
            return (!ClearableEditText.this.mShown || !isVirtualView(f, f2)) ? Integer.MIN_VALUE : 0;
        }

        /* access modifiers changed from: protected */
        @Override // com.miui.internal.widget.ExploreByTouchHelper
        public void getVisibleVirtualViews(List list) {
            if (ClearableEditText.this.mShown) {
                list.add(0);
            }
        }

        /* access modifiers changed from: protected */
        @Override // com.miui.internal.widget.ExploreByTouchHelper
        public boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i == Integer.MIN_VALUE || i2 != 16) {
                return false;
            }
            ClearableEditText.this.onClearButtonClick();
            return true;
        }

        /* access modifiers changed from: protected */
        @Override // com.miui.internal.widget.ExploreByTouchHelper
        public void onPopulateEventForHost(AccessibilityEvent accessibilityEvent) {
            super.onPopulateEventForHost(accessibilityEvent);
            if (accessibilityEvent.getEventType() == 2048 && !ClearableEditText.this.mShown && this.forView.isFocused()) {
                this.forView.sendAccessibilityEvent(32768);
            }
        }

        /* access modifiers changed from: protected */
        @Override // com.miui.internal.widget.ExploreByTouchHelper
        public void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setContentDescription(getDescription());
        }

        /* access modifiers changed from: protected */
        @Override // com.miui.internal.widget.ExploreByTouchHelper
        public void onPopulateNodeForHost(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onPopulateNodeForHost(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(ClearableEditText.class.getName());
        }

        /* access modifiers changed from: protected */
        @Override // com.miui.internal.widget.ExploreByTouchHelper
        public void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfo accessibilityNodeInfo) {
            accessibilityNodeInfo.setContentDescription(getDescription());
            accessibilityNodeInfo.addAction(16);
            accessibilityNodeInfo.setClassName(Button.class.getName());
            getChildRect(this.mTempParentBounds);
            accessibilityNodeInfo.setBoundsInParent(this.mTempParentBounds);
            accessibilityNodeInfo.setClickable(true);
        }
    }

    public ClearableEditText(Context context) {
        this(context, null);
    }

    public ClearableEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.editTextSearchStyle);
    }

    public ClearableEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
        if (compoundDrawablesRelative[0] == null && compoundDrawablesRelative[1] == null && compoundDrawablesRelative[2] != null && compoundDrawablesRelative[3] == null) {
            this.mDrawable = compoundDrawablesRelative[2];
            addTextChangedListener(new TextWatcher() {
                /* class miui.widget.ClearableEditText.AnonymousClass1 */

                public void afterTextChanged(Editable editable) {
                    if (ClearableEditText.this.mShown != (editable.length() > 0)) {
                        ClearableEditText clearableEditText = ClearableEditText.this;
                        clearableEditText.mShown = !clearableEditText.mShown;
                        ClearableEditText.this.refreshDrawableState();
                        if (ClearableEditText.this.mAccessHelper != null) {
                            ClearableEditText.this.mAccessHelper.invalidateRoot();
                        }
                    }
                }

                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }
            });
            if (Build.VERSION.SDK_INT >= 19) {
                this.mAccessHelper = new AccessHelper(this);
                setAccessibilityDelegate(this.mAccessHelper);
                return;
            }
            return;
        }
        throw new IllegalStateException("ClearableEditText need only drawableEnd");
    }

    private void onButtonTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 3 && this.mPressed) {
                    this.mPressed = false;
                }
            } else if (isEnabled() && this.mPressed) {
                onClearButtonClick();
            }
        } else if (isEnabled() && this.mShown) {
            this.mPressed = true;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onClearButtonClick() {
        setText("");
    }

    /* access modifiers changed from: protected */
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        AccessHelper accessHelper;
        if (Build.VERSION.SDK_INT < 19 || (accessHelper = this.mAccessHelper) == null || !this.mShown || !accessHelper.dispatchHoverEvent(motionEvent)) {
            return super.dispatchHoverEvent(motionEvent);
        }
        return true;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!(this.mShown && (!ViewUtils.isLayoutRtl(this) ? motionEvent.getX() > ((float) ((getWidth() - getPaddingRight()) - this.mDrawable.getIntrinsicWidth())) : motionEvent.getX() < ((float) (this.mDrawable.getIntrinsicWidth() + getPaddingLeft()))))) {
            return super.dispatchTouchEvent(motionEvent);
        }
        onButtonTouchEvent(motionEvent);
        return true;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mDrawable != null) {
            this.mDrawable.setState(getDrawableState());
            invalidate();
        }
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (!this.mShown) {
            EditText.mergeDrawableStates(onCreateDrawableState, EMPTY_STATE_SET);
        }
        return onCreateDrawableState;
    }

    public void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable == null && drawable2 == null && drawable3 == null && drawable4 == null) {
            super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
            return;
        }
        throw new IllegalStateException("ClearableEditText can only set drawables by setCompoundDrawablesRelative()");
    }

    public void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable == null && drawable2 == null && drawable3 != null && drawable4 == null) {
            this.mDrawable = drawable3;
            super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
            return;
        }
        throw new IllegalStateException("ClearableEditText can only set drawableRight");
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mDrawable;
    }
}
