package miui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.miui.internal.R;
import com.miui.internal.variable.Android_Widget_PopupWindow_class;
import com.miui.internal.widget.ArrowPopupView;
import miui.util.AttributeResolver;

public class ArrowPopupWindow extends PopupWindow {
    public static final int ARROW_BOTTOM_MODE = 0;
    public static final int ARROW_LEFT_MODE = 3;
    public static final int ARROW_RIGHT_MODE = 2;
    public static final int ARROW_TOP_MODE = 1;
    protected ArrowPopupView mArrowPopupView;
    private boolean mAutoDismiss;
    private Context mContext;
    private int mListViewMaxHeight;

    public ArrowPopupWindow(Context context) {
        this(context, null);
    }

    public ArrowPopupWindow(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.style.Widget_PopupWindow);
    }

    public ArrowPopupWindow(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ArrowPopupWindow(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mAutoDismiss = true;
        this.mContext = context;
        this.mAutoDismiss = true;
        setupPopupWindow();
    }

    private boolean isTranslucentStatusEnabled() {
        return (getContext().getResources().getInteger(R.integer.window_translucent_status) == 0 || AttributeResolver.resolveInt(getContext(), R.attr.windowTranslucentStatus, 0) == 0) ? false : true;
    }

    private void setupPopupWindow() {
        int i;
        this.mListViewMaxHeight = this.mContext.getResources().getDimensionPixelOffset(R.dimen.arrow_popup_window_list_max_height);
        this.mArrowPopupView = (ArrowPopupView) getLayoutInflater().inflate(R.layout.arrow_popup_view, (ViewGroup) null, false);
        super.setContentView(this.mArrowPopupView);
        if (isTranslucentStatusEnabled()) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
            i = displayMetrics.heightPixels;
        } else {
            i = -1;
        }
        super.setWidth(-1);
        super.setHeight(i);
        setSoftInputMode(3);
        this.mArrowPopupView.setArrowPopupWindow(this);
        super.setTouchInterceptor(getDefaultOnTouchListener());
        this.mArrowPopupView.addShadow();
        onPrepareWindow();
        update();
    }

    public void dismiss(boolean z) {
        if (z) {
            this.mArrowPopupView.animateToDismiss();
        } else {
            dismiss();
        }
    }

    public int getArrowMode() {
        return this.mArrowPopupView.getArrowMode();
    }

    public int getContentHeight() {
        View contentView = getContentView();
        if (contentView != null) {
            return contentView.getHeight();
        }
        return 0;
    }

    public View getContentView() {
        return this.mArrowPopupView.getContentView();
    }

    public int getContentWidth() {
        View contentView = getContentView();
        if (contentView != null) {
            return contentView.getWidth();
        }
        return 0;
    }

    public Context getContext() {
        return this.mContext;
    }

    public View.OnTouchListener getDefaultOnTouchListener() {
        return this.mArrowPopupView;
    }

    public int getHeight() {
        return getContentHeight();
    }

    /* access modifiers changed from: protected */
    public LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(this.mContext);
    }

    public Button getNegativeButton() {
        return this.mArrowPopupView.getNegativeButton();
    }

    public Button getPositiveButton() {
        return this.mArrowPopupView.getPositiveButton();
    }

    public int getWidth() {
        return getContentWidth();
    }

    /* access modifiers changed from: protected */
    public void onPrepareWindow() {
    }

    public void setArrowMode(int i) {
        this.mArrowPopupView.setArrowMode(i);
    }

    public void setAutoDismiss(boolean z) {
        this.mAutoDismiss = z;
    }

    public void setContentHeight(int i) {
        int i2;
        View contentView = getContentView();
        if (!(contentView instanceof ListView) || i <= (i2 = this.mListViewMaxHeight)) {
            i2 = i;
        }
        if (contentView != null) {
            ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
            layoutParams.height = i2;
            contentView.setLayoutParams(layoutParams);
        }
    }

    public final void setContentView(int i) {
        this.mArrowPopupView.setContentView(i);
    }

    public final void setContentView(View view) {
        this.mArrowPopupView.setContentView(view);
    }

    public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.mArrowPopupView.setContentView(view, layoutParams);
    }

    public void setContentWidth(int i) {
        View contentView = getContentView();
        if (contentView != null) {
            ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
            layoutParams.width = i;
            contentView.setLayoutParams(layoutParams);
        }
    }

    public void setHeight(int i) {
        setContentHeight(i);
    }

    public void setNegativeButton(int i, View.OnClickListener onClickListener) {
        setNegativeButton(this.mContext.getString(i), onClickListener);
    }

    public void setNegativeButton(CharSequence charSequence, View.OnClickListener onClickListener) {
        this.mArrowPopupView.setNegativeButton(charSequence, onClickListener);
    }

    public void setPositiveButton(int i, View.OnClickListener onClickListener) {
        setPositiveButton(this.mContext.getString(i), onClickListener);
    }

    public void setPositiveButton(CharSequence charSequence, View.OnClickListener onClickListener) {
        this.mArrowPopupView.setPositiveButton(charSequence, onClickListener);
    }

    public void setTitle(int i) {
        setTitle(this.mContext.getString(i));
    }

    public void setTitle(CharSequence charSequence) {
        this.mArrowPopupView.setTitle(charSequence);
    }

    public void setTouchInterceptor(View.OnTouchListener onTouchListener) {
        this.mArrowPopupView.setTouchInterceptor(onTouchListener);
    }

    public void setWidth(int i) {
        setContentWidth(i);
    }

    public void show(View view, int i, int i2) {
        this.mArrowPopupView.setAnchor(view);
        this.mArrowPopupView.setOffset(i, i2);
        Android_Widget_PopupWindow_class.Factory.getInstance().get().setLayoutInScreenEnabled(this, isTranslucentStatusEnabled());
        showAtLocation(view, 8388659, 0, 0);
        this.mArrowPopupView.setAutoDismiss(this.mAutoDismiss);
        this.mArrowPopupView.animateToShow();
    }

    public void showAsDropDown(View view, int i, int i2) {
        show(view, i, i2);
    }

    public void showAsDropDown(View view, int i, int i2, int i3) {
        show(view, i, i2);
    }

    @Override // android.widget.PopupWindow
    public void update(int i, int i2, int i3, int i4, boolean z) {
        super.update(0, 0, -2, -2, z);
        setContentHeight(i4);
    }
}
