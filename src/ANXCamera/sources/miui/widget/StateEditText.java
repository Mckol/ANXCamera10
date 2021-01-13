package miui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import com.miui.internal.R;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import miui.util.ViewUtils;

public class StateEditText extends EditText {
    private static final Class<?>[] WIDGET_MANAGER_CONSTRUCTOR_SIGNATURE = {Context.class, AttributeSet.class};
    private Drawable[] mExtraDrawables;
    private String mLabel;
    private int mLabelLength;
    private boolean mPressed;
    private WidgetManager mWidgetManager;
    private int mWidgetPadding;

    public static abstract class WidgetManager {
        public WidgetManager(Context context, AttributeSet attributeSet) {
        }

        /* access modifiers changed from: protected */
        public abstract Drawable[] getWidgetDrawables();

        /* access modifiers changed from: protected */
        public void onAttached(StateEditText stateEditText) {
        }

        /* access modifiers changed from: protected */
        public void onDetached() {
        }

        /* access modifiers changed from: protected */
        public abstract void onWidgetClick(int i);
    }

    public StateEditText(Context context) {
        this(context, null);
    }

    public StateEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.stateEditTextStyle);
    }

    public StateEditText(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public StateEditText(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initView(context, attributeSet, i, i2);
    }

    private WidgetManager createWidgetManager(Context context, String str, AttributeSet attributeSet) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            Constructor<? extends U> constructor = context.getClassLoader().loadClass(str).asSubclass(WidgetManager.class).getConstructor(WIDGET_MANAGER_CONSTRUCTOR_SIGNATURE);
            Object[] objArr = {context, attributeSet};
            constructor.setAccessible(true);
            return (WidgetManager) constructor.newInstance(objArr);
        } catch (InstantiationException e2) {
            throw new IllegalStateException("Could not instantiate the WidgetManager: " + str, e2);
        } catch (InvocationTargetException e3) {
            throw new IllegalStateException("Could not instantiate the WidgetManager: " + str, e3);
        } catch (IllegalAccessException e4) {
            throw new IllegalStateException("Can't access non-public constructor " + str, e4);
        } catch (ClassNotFoundException e5) {
            throw new IllegalStateException("Can't find WidgetManager: " + str, e5);
        } catch (NoSuchMethodException e6) {
            throw new IllegalStateException("Error creating WidgetManager " + str, e6);
        }
    }

    private boolean dispatchEndDrawableTouchEvent(MotionEvent motionEvent) {
        if (this.mWidgetManager != null) {
            return isWidgetResumedEvent(motionEvent);
        }
        return false;
    }

    private void drawExtraWidget(Canvas canvas) {
        if (this.mExtraDrawables != null) {
            int width = getWidth();
            int height = getHeight();
            int scrollX = getScrollX();
            int paddingEnd = getPaddingEnd();
            Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
            int i = 0;
            int intrinsicWidth = compoundDrawablesRelative[2] == null ? 0 : compoundDrawablesRelative[2].getIntrinsicWidth() + this.mWidgetPadding;
            int i2 = height / 2;
            int i3 = 0;
            while (true) {
                Drawable[] drawableArr = this.mExtraDrawables;
                if (i < drawableArr.length) {
                    int intrinsicWidth2 = drawableArr[i].getIntrinsicWidth();
                    int intrinsicHeight = this.mExtraDrawables[i].getIntrinsicHeight();
                    if (ViewUtils.isLayoutRtl(this)) {
                        int i4 = scrollX + paddingEnd + intrinsicWidth;
                        int i5 = intrinsicHeight / 2;
                        this.mExtraDrawables[i].setBounds(i4 + i3, i2 - i5, i4 + intrinsicWidth2 + i3, i5 + i2);
                    } else {
                        int i6 = ((scrollX + width) - paddingEnd) - intrinsicWidth;
                        int i7 = intrinsicHeight / 2;
                        this.mExtraDrawables[i].setBounds((i6 - intrinsicWidth2) - i3, i2 - i7, i6 - i3, i7 + i2);
                    }
                    i3 = this.mWidgetPadding + intrinsicWidth2;
                    this.mExtraDrawables[i].draw(canvas);
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    private void drawLabel(Canvas canvas) {
        if (!TextUtils.isEmpty(this.mLabel)) {
            int color = getPaint().getColor();
            getPaint().setColor(getCurrentTextColor());
            int paddingStart = getPaddingStart();
            Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
            int i = 0;
            if (compoundDrawablesRelative[0] != null) {
                i = this.mWidgetPadding + compoundDrawablesRelative[0].getIntrinsicWidth();
            }
            if (ViewUtils.isLayoutRtl(this)) {
                canvas.drawText(this.mLabel, (float) ((((getScrollX() + getWidth()) - i) - this.mLabelLength) - paddingStart), (float) getBaseline(), getPaint());
            } else {
                canvas.drawText(this.mLabel, (float) (paddingStart + getScrollX() + i), (float) getBaseline(), getPaint());
            }
            getPaint().setColor(color);
        }
    }

    private int getLabelLength() {
        return this.mLabelLength + (this.mLabelLength == 0 ? 0 : this.mWidgetPadding);
    }

    private int getWidgetLength() {
        Drawable[] drawableArr = this.mExtraDrawables;
        if (drawableArr == null) {
            return 0;
        }
        int i = 0;
        for (Drawable drawable : drawableArr) {
            i = i + drawable.getIntrinsicWidth() + this.mWidgetPadding;
        }
        return i;
    }

    private void initView(Context context, AttributeSet attributeSet, int i, int i2) {
        String str;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.StateEditText, i, i2);
            str = obtainStyledAttributes.getString(R.styleable.StateEditText_widgetManager);
            this.mLabel = obtainStyledAttributes.getString(R.styleable.StateEditText_label);
            this.mWidgetPadding = obtainStyledAttributes.getDimensionPixelSize(R.styleable.StateEditText_widgetPadding, 0);
            obtainStyledAttributes.recycle();
        } else {
            str = null;
        }
        setWidgetManager(createWidgetManager(context, str, attributeSet));
        this.mExtraDrawables = null;
        WidgetManager widgetManager = this.mWidgetManager;
        if (widgetManager != null) {
            this.mExtraDrawables = widgetManager.getWidgetDrawables();
        }
        setLabel(this.mLabel);
    }

    private boolean isWidgetResumedEvent(MotionEvent motionEvent) {
        if (this.mExtraDrawables != null) {
            int scrollX = getScrollX();
            int i = 0;
            while (true) {
                Drawable[] drawableArr = this.mExtraDrawables;
                if (i >= drawableArr.length) {
                    break;
                }
                Rect bounds = drawableArr[i].getBounds();
                if (motionEvent.getX() >= ((float) (bounds.right - scrollX)) || motionEvent.getX() <= ((float) (bounds.left - scrollX))) {
                    i++;
                } else {
                    onWidgetTouchEvent(motionEvent, i);
                    return true;
                }
            }
        }
        return false;
    }

    private void onWidgetTouchEvent(MotionEvent motionEvent, int i) {
        WidgetManager widgetManager;
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mPressed = true;
        } else if (action != 1) {
            if (action == 3 && this.mPressed) {
                this.mPressed = false;
            }
        } else if (this.mPressed && (widgetManager = this.mWidgetManager) != null) {
            widgetManager.onWidgetClick(i);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (dispatchEndDrawableTouchEvent(motionEvent)) {
            return true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public int getCompoundPaddingLeft() {
        return super.getCompoundPaddingLeft() + (ViewUtils.isLayoutRtl(this) ? getWidgetLength() : getLabelLength());
    }

    public int getCompoundPaddingRight() {
        return super.getCompoundPaddingRight() + (ViewUtils.isLayoutRtl(this) ? getLabelLength() : getWidgetLength());
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        drawLabel(canvas);
        drawExtraWidget(canvas);
        super.onDraw(canvas);
    }

    public void setLabel(String str) {
        this.mLabel = str;
        this.mLabelLength = TextUtils.isEmpty(this.mLabel) ? 0 : (int) getPaint().measureText(this.mLabel);
        invalidate();
    }

    public void setWidgetManager(WidgetManager widgetManager) {
        WidgetManager widgetManager2 = this.mWidgetManager;
        if (widgetManager2 != null) {
            widgetManager2.onDetached();
        }
        this.mWidgetManager = widgetManager;
        WidgetManager widgetManager3 = this.mWidgetManager;
        if (widgetManager3 != null) {
            widgetManager3.onAttached(this);
        }
    }
}
