package miui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import miui.animation.physics.DynamicAnimation;
import miui.animation.physics.SpringAnimation;
import miui.animation.property.FloatProperty;

public class SeekBarContainerLinearLayout extends LinearLayout {
    public static final String FROM_TOUCH_ANIM = "from_touch_anim";
    private int mCurrentProgress;
    private boolean mIsInClickableRect = false;
    private boolean mIsInThumb = false;
    private float mProgress;
    private SpringAnimation mProgressAnim;
    private FloatProperty<SeekBarContainerLinearLayout> mProgressProperty = new FloatProperty<SeekBarContainerLinearLayout>("Progress") {
        /* class miui.widget.SeekBarContainerLinearLayout.AnonymousClass1 */

        public float getValue(SeekBarContainerLinearLayout seekBarContainerLinearLayout) {
            return SeekBarContainerLinearLayout.this.getProgress();
        }

        public void setValue(SeekBarContainerLinearLayout seekBarContainerLinearLayout, float f) {
            SeekBarContainerLinearLayout.this.setProgress(Math.round(f));
        }
    };
    private SeekBar mSeekBar;

    public SeekBarContainerLinearLayout(Context context) {
        super(context);
        initAnim();
    }

    public SeekBarContainerLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initAnim();
    }

    public SeekBarContainerLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initAnim();
    }

    private void attemptClaimDrag() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    private int getThumbLeft() {
        Drawable thumb = this.mSeekBar.getThumb();
        return ((thumb.getBounds().left + (this.mSeekBar.getPaddingLeft() - this.mSeekBar.getThumbOffset())) - (thumb.getIntrinsicWidth() / 2)) + this.mSeekBar.getLeft();
    }

    private int getThumbRight() {
        Drawable thumb = this.mSeekBar.getThumb();
        return thumb.getBounds().right + (this.mSeekBar.getPaddingLeft() - this.mSeekBar.getThumbOffset()) + (thumb.getIntrinsicWidth() / 2) + this.mSeekBar.getLeft();
    }

    private void initAnim() {
        this.mProgressAnim = new SpringAnimation(this, this.mProgressProperty, -1.0f);
        this.mProgressAnim.getSpring().setStiffness(986.96f);
        this.mProgressAnim.getSpring().setDampingRatio(0.6f);
        this.mProgressAnim.setMinimumVisibleChange(0.002f);
        this.mProgressAnim.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            /* class miui.widget.SeekBarContainerLinearLayout.AnonymousClass2 */

            @Override // miui.animation.physics.DynamicAnimation.OnAnimationEndListener
            public void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                SeekBarContainerLinearLayout.this.mSeekBar.setTag(null);
            }
        });
    }

    private boolean isInExtendThumb(float f, float f2, int i) {
        Rect rect = new Rect();
        this.mSeekBar.getHitRect(rect);
        return f > ((float) (getThumbLeft() - i)) && f < ((float) (getThumbRight() + i)) && f2 > ((float) (rect.top - i)) && f2 < ((float) (rect.bottom + i));
    }

    private boolean isInThumbXRange(float f, int i) {
        if (this.mSeekBar.getThumb() == null) {
            return false;
        }
        return f > ((float) (getThumbLeft() - i)) && f < ((float) (getThumbRight() + i));
    }

    private void onTouchSeekBar(MotionEvent motionEvent) {
        motionEvent.setLocation(motionEvent.getX() - ((float) this.mSeekBar.getLeft()), motionEvent.getY());
        this.mSeekBar.onTouchEvent(motionEvent);
    }

    private void releaseClaimDrag() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(false);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x009b  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00ac  */
    private void trackTouchEvent(MotionEvent motionEvent) {
        if (this.mIsInThumb) {
            onTouchSeekBar(motionEvent);
            return;
        }
        float x = motionEvent.getX();
        int width = (this.mSeekBar.getWidth() - this.mSeekBar.getPaddingLeft()) - this.mSeekBar.getPaddingRight();
        float f = 1.0f;
        if (getLayoutDirection() == 1) {
            int right = this.mSeekBar.getRight() - this.mSeekBar.getPaddingRight();
            int left = this.mSeekBar.getLeft() + this.mSeekBar.getPaddingLeft();
            if (x <= ((float) right)) {
                float f2 = (float) left;
                if (x >= f2) {
                    float f3 = (float) width;
                    f = ((f3 - x) + f2) / f3;
                }
                float range = 0.0f + (f * ((float) ProgressBarHelper.getRange(this.mSeekBar))) + ((float) ProgressBarHelper.getMin(this.mSeekBar));
                if (isInThumbXRange(x, 0)) {
                    this.mProgressAnim.cancel();
                    this.mSeekBar.setProgress(Math.round(range));
                    this.mIsInThumb = true;
                    return;
                }
                this.mProgressAnim.getSpring().setFinalPosition(range);
                if (!this.mProgressAnim.isRunning()) {
                    this.mProgressAnim.start();
                    this.mSeekBar.setTag(FROM_TOUCH_ANIM);
                    return;
                }
                return;
            }
        } else {
            int left2 = this.mSeekBar.getLeft() + this.mSeekBar.getPaddingLeft();
            int right2 = this.mSeekBar.getRight() - this.mSeekBar.getPaddingRight();
            float f4 = (float) left2;
            if (x >= f4) {
                if (x <= ((float) right2)) {
                    f = (x - f4) / ((float) width);
                }
                float range2 = 0.0f + (f * ((float) ProgressBarHelper.getRange(this.mSeekBar))) + ((float) ProgressBarHelper.getMin(this.mSeekBar));
                if (isInThumbXRange(x, 0)) {
                }
            }
        }
        f = 0.0f;
        float range22 = 0.0f + (f * ((float) ProgressBarHelper.getRange(this.mSeekBar))) + ((float) ProgressBarHelper.getMin(this.mSeekBar));
        if (isInThumbXRange(x, 0)) {
        }
    }

    public float getProgress() {
        return this.mProgress;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        int i = 0;
        while (true) {
            if (i >= getChildCount()) {
                break;
            }
            View childAt = getChildAt(i);
            if (childAt instanceof SeekBar) {
                this.mSeekBar = (SeekBar) childAt;
                break;
            }
            i++;
        }
        if (this.mSeekBar == null) {
            throw new IllegalStateException("fail to get seek bar");
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        Drawable thumb = this.mSeekBar.getThumb();
        if (thumb == null) {
            return false;
        }
        return isInExtendThumb(motionEvent.getX(), motionEvent.getY(), thumb.getIntrinsicWidth() * 2);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setParentClipChildren();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            Drawable thumb = this.mSeekBar.getThumb();
            if (thumb != null) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                boolean isInExtendThumb = isInExtendThumb(x, y, thumb.getIntrinsicWidth() * 2);
                this.mIsInThumb = isInExtendThumb(x, y, 0);
                if (!isInExtendThumb || !this.mSeekBar.isEnabled()) {
                    this.mIsInClickableRect = false;
                } else {
                    this.mIsInClickableRect = true;
                    this.mSeekBar.setPressed(true);
                    this.mCurrentProgress = this.mSeekBar.getProgress();
                    this.mProgress = (float) this.mCurrentProgress;
                    if (this.mIsInThumb) {
                        onTouchSeekBar(motionEvent);
                    } else {
                        this.mProgressAnim.getSpring().setFinalPosition((float) this.mCurrentProgress);
                    }
                    attemptClaimDrag();
                }
            } else {
                this.mIsInClickableRect = false;
                this.mIsInThumb = false;
            }
        } else if (action != 1) {
            if (action != 2) {
                if (action == 3 && this.mIsInClickableRect) {
                    this.mSeekBar.setPressed(false);
                    this.mSeekBar.invalidate();
                    releaseClaimDrag();
                    onTouchSeekBar(motionEvent);
                }
            } else if (this.mIsInClickableRect) {
                Drawable background = this.mSeekBar.getBackground();
                if (background != null) {
                    background.setHotspot(motionEvent.getX(), motionEvent.getY());
                }
                trackTouchEvent(motionEvent);
            }
        } else if (this.mIsInClickableRect) {
            this.mSeekBar.setPressed(false);
            this.mSeekBar.invalidate();
            releaseClaimDrag();
            onTouchSeekBar(motionEvent);
        }
        return true;
    }

    public void setParentClipChildren() {
        setClipChildren(false);
        ViewParent parent = getParent();
        if (parent != null && (parent instanceof ViewGroup)) {
            ((ViewGroup) parent).setClipChildren(false);
        }
    }

    public void setProgress(int i) {
        this.mProgress = (float) i;
        this.mSeekBar.setProgress(i);
    }

    public boolean shouldDelayChildPressedState() {
        return true;
    }
}
