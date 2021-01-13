package miui.external.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SpinnerAdapter;
import android.widget.ThemedSpinnerAdapter;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Field;
import miui.app.AlertDialog;
import miui.external.adapter.SpinnerCheckableArrayAdapter;
import miui.external.adapter.SpinnerDoubleLineContentAdapter;
import miui.external.graphics.TaggingDrawableUtil;
import miui.util.HapticFeedbackUtil;
import miui.view.MiuiHapticFeedbackConstants;
import miui.widget.ImmersionListPopupWindow;
import miuix.compat.R;

public class Spinner extends android.widget.Spinner {
    private static Field FORWARDING_LISTENER = null;
    private static final int MAX_ITEMS_MEASURED = 15;
    private static final int MODE_DIALOG = 0;
    private static final int MODE_DROPDOWN = 1;
    private static final int MODE_THEME = -1;
    private static final String TAG = "Spinner";
    int mDropDownMinWidth;
    int mDropDownWidth;
    private OnSpinnerDismissListener mOnSpinnerDismissListener;
    private SpinnerPopup mPopup;
    private final Context mPopupContext;
    private final boolean mPopupSet;
    private SpinnerAdapter mTempAdapter;
    final Rect mTempRect;
    private float mTouchX;
    private float mTouchY;

    /* access modifiers changed from: private */
    public class DialogPopup implements SpinnerPopup, DialogInterface.OnClickListener {
        private ListAdapter mListAdapter;
        AlertDialog mPopup;
        private CharSequence mPrompt;

        private DialogPopup() {
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public void dismiss() {
            AlertDialog alertDialog = this.mPopup;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.mPopup = null;
            }
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public Drawable getBackground() {
            return null;
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public CharSequence getHintText() {
            return this.mPrompt;
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public int getHorizontalOffset() {
            return 0;
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public int getHorizontalOriginalOffset() {
            return 0;
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public int getVerticalOffset() {
            return 0;
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public boolean isShowing() {
            AlertDialog alertDialog = this.mPopup;
            return alertDialog != null && alertDialog.isShowing();
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            Spinner.this.setSelection(i);
            Spinner.this.vibrate();
            if (Spinner.this.getOnItemClickListener() != null) {
                Spinner.this.performItemClick(null, i, this.mListAdapter.getItemId(i));
            }
            dismiss();
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public void setAdapter(ListAdapter listAdapter) {
            this.mListAdapter = listAdapter;
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public void setBackgroundDrawable(Drawable drawable) {
            Log.e(Spinner.TAG, "Cannot set popup background for MODE_DIALOG, ignoring");
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public void setHorizontalOffset(int i) {
            Log.e(Spinner.TAG, "Cannot set horizontal offset for MODE_DIALOG, ignoring");
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public void setHorizontalOriginalOffset(int i) {
            Log.e(Spinner.TAG, "Cannot set horizontal (original) offset for MODE_DIALOG, ignoring");
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public void setPromptText(CharSequence charSequence) {
            this.mPrompt = charSequence;
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public void setVerticalOffset(int i) {
            Log.e(Spinner.TAG, "Cannot set vertical offset for MODE_DIALOG, ignoring");
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public void show(int i, int i2) {
            if (this.mListAdapter != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Spinner.this.getPopupContext());
                CharSequence charSequence = this.mPrompt;
                if (charSequence != null) {
                    builder.setTitle(charSequence);
                }
                this.mPopup = builder.setSingleChoiceItems(this.mListAdapter, Spinner.this.getSelectedItemPosition(), this).setOnDismissListener(new DialogInterface.OnDismissListener() {
                    /* class miui.external.widget.Spinner.DialogPopup.AnonymousClass1 */

                    public void onDismiss(DialogInterface dialogInterface) {
                        Spinner.this.notifySpinnerDismiss();
                    }
                }).create();
                ListView listView = this.mPopup.getListView();
                listView.setTextDirection(i);
                listView.setTextAlignment(i2);
                this.mPopup.show();
            }
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public void show(int i, int i2, float f, float f2) {
            show(i, i2);
        }
    }

    /* access modifiers changed from: private */
    public static class DialogPopupAdapter extends DropDownAdapter {
        DialogPopupAdapter(@Nullable SpinnerAdapter spinnerAdapter, @Nullable Resources.Theme theme) {
            super(spinnerAdapter, theme);
        }

        @Override // miui.external.widget.Spinner.DropDownAdapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2 = super.getView(i, view, viewGroup);
            TaggingDrawableUtil.updateBackgroundState(view2, TaggingDrawableUtil.STATE_SET_MIDDLE);
            return view2;
        }
    }

    private static class DropDownAdapter implements ListAdapter, SpinnerAdapter {
        private SpinnerAdapter mAdapter;
        private ListAdapter mListAdapter;

        public DropDownAdapter(@Nullable SpinnerAdapter spinnerAdapter, @Nullable Resources.Theme theme) {
            this.mAdapter = spinnerAdapter;
            if (spinnerAdapter instanceof ListAdapter) {
                this.mListAdapter = (ListAdapter) spinnerAdapter;
            }
            if (theme == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 23 && (spinnerAdapter instanceof ThemedSpinnerAdapter)) {
                ThemedSpinnerAdapter themedSpinnerAdapter = (ThemedSpinnerAdapter) spinnerAdapter;
                if (themedSpinnerAdapter.getDropDownViewTheme() != theme) {
                    themedSpinnerAdapter.setDropDownViewTheme(theme);
                }
            } else if (spinnerAdapter instanceof ThemedAdapter) {
                ThemedAdapter themedAdapter = (ThemedAdapter) spinnerAdapter;
                if (themedAdapter.getDropDownViewTheme() == null) {
                    themedAdapter.setDropDownViewTheme(theme);
                }
            }
        }

        public boolean areAllItemsEnabled() {
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                return listAdapter.areAllItemsEnabled();
            }
            return true;
        }

        public int getCount() {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return 0;
            }
            return spinnerAdapter.getCount();
        }

        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getDropDownView(i, view, viewGroup);
        }

        public Object getItem(int i) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return null;
            }
            return spinnerAdapter.getItem(i);
        }

        public long getItemId(int i) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) {
                return -1;
            }
            return spinnerAdapter.getItemId(i);
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return getDropDownView(i, view, viewGroup);
        }

        public int getViewTypeCount() {
            return 1;
        }

        public boolean hasStableIds() {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            return spinnerAdapter != null && spinnerAdapter.hasStableIds();
        }

        public boolean isEmpty() {
            return getCount() == 0;
        }

        public boolean isEnabled(int i) {
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                return listAdapter.isEnabled(i);
            }
            return true;
        }

        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter != null) {
                spinnerAdapter.registerDataSetObserver(dataSetObserver);
            }
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter != null) {
                spinnerAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    /* access modifiers changed from: private */
    public static class DropDownPopupAdapter extends DropDownAdapter {
        DropDownPopupAdapter(@Nullable SpinnerAdapter spinnerAdapter, @Nullable Resources.Theme theme) {
            super(spinnerAdapter, theme);
        }

        @Override // miui.external.widget.Spinner.DropDownAdapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2 = super.getView(i, view, viewGroup);
            TaggingDrawableUtil.updateItemPadding(view2, i, getCount());
            return view2;
        }
    }

    /* access modifiers changed from: private */
    public class DropdownPopup extends ImmersionListPopupWindow implements SpinnerPopup {
        private static final float SCREEN_MARGIN_BOTTOM_PROPORTION = 0.1f;
        private static final float SCREEN_MARGIN_TOP_PROPORTION = 0.1f;
        ListAdapter mAdapter;
        private CharSequence mHintText;
        private int mMarginScreen = 40;
        private int mOriginalHorizontalOffset;
        private final Rect mVisibleRect = new Rect();

        public DropdownPopup(Context context, AttributeSet attributeSet, int i) {
            super(context);
            setOnItemClickListener(new AdapterView.OnItemClickListener(Spinner.this) {
                /* class miui.external.widget.Spinner.DropdownPopup.AnonymousClass1 */

                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    Spinner.this.setSelection(i);
                    Spinner.this.vibrate();
                    if (Spinner.this.getOnItemClickListener() != null) {
                        DropdownPopup dropdownPopup = DropdownPopup.this;
                        Spinner.this.performItemClick(view, i, dropdownPopup.mAdapter.getItemId(i));
                    }
                    DropdownPopup.this.dismiss();
                }
            });
        }

        private void changeWindowBackground(View view, float f) {
            if (view != null) {
                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.flags |= 2;
                    layoutParams.dimAmount = f;
                    ((WindowManager) view.getContext().getSystemService("window")).updateViewLayout(view, layoutParams);
                    return;
                }
                return;
            }
            Log.w(Spinner.TAG, "can't change window dim with null view");
        }

        private int getListViewHeight() {
            if (getContentView() instanceof ListView) {
                ListAdapter adapter = ((ListView) getContentView()).getAdapter();
                int i = 0;
                for (int i2 = 0; i2 < adapter.getCount(); i2++) {
                    View view = adapter.getView(i2, null, (ListView) getContentView());
                    view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
                    i += view.getMeasuredHeight();
                }
                return i;
            }
            getContentView().measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            return getContentView().getMeasuredHeight() + 0;
        }

        private void initListView(int i, int i2) {
            if (getContentView() instanceof ListView) {
                ListView listView = (ListView) getContentView();
                listView.setChoiceMode(1);
                listView.setTextDirection(i);
                listView.setTextAlignment(i2);
                int selectedItemPosition = Spinner.this.getSelectedItemPosition();
                listView.setSelection(selectedItemPosition);
                listView.setItemChecked(selectedItemPosition, true);
            }
        }

        private void showWithAnchor(View view, float f, float f2) {
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            int i = (int) f;
            boolean z = true;
            int i2 = iArr[1];
            View rootView = view.getRootView();
            rootView.getLocationInWindow(iArr);
            if (i > rootView.getWidth() / 2) {
                z = false;
            }
            int width = z ? this.mMarginScreen : (rootView.getWidth() - this.mMarginScreen) - getWidth();
            int listViewHeight = getListViewHeight();
            float f3 = (float) i2;
            if (f3 < ((float) rootView.getHeight()) * 0.1f) {
                f3 = ((float) rootView.getHeight()) * 0.1f;
            }
            float f4 = (float) listViewHeight;
            if (f3 + f4 > ((float) rootView.getHeight()) * 0.9f) {
                f3 = (((float) rootView.getHeight()) * 0.9f) - f4;
            }
            if (f3 < ((float) rootView.getHeight()) * 0.1f) {
                f3 = ((float) rootView.getHeight()) * 0.1f;
                setHeight((int) (((float) rootView.getHeight()) * 0.79999995f));
            }
            showAtLocation(view, 0, width, (int) f3);
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public CharSequence getHintText() {
            return this.mHintText;
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public int getHorizontalOffset() {
            return 0;
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public int getHorizontalOriginalOffset() {
            return this.mOriginalHorizontalOffset;
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public int getVerticalOffset() {
            return 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isVisibleToUser(View view) {
            return ViewCompat.isAttachedToWindow(view) && view.getGlobalVisibleRect(this.mVisibleRect);
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup, miui.widget.ImmersionListPopupWindow
        public void setAdapter(ListAdapter listAdapter) {
            super.setAdapter(listAdapter);
            this.mAdapter = listAdapter;
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public void setHorizontalOffset(int i) {
            Log.w(Spinner.TAG, "setHorizontalOffset do nothing");
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public void setHorizontalOriginalOffset(int i) {
            this.mOriginalHorizontalOffset = i;
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public void setPromptText(CharSequence charSequence) {
            this.mHintText = charSequence;
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public void setVerticalOffset(int i) {
            Log.w(Spinner.TAG, "setVerticalOffset do nothing");
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public void show(int i, int i2) {
            show(i, i2, 0.0f, 0.0f);
        }

        @Override // miui.external.widget.Spinner.SpinnerPopup
        public void show(final int i, final int i2, float f, float f2) {
            ViewTreeObserver viewTreeObserver;
            boolean isShowing = isShowing();
            setInputMethodMode(2);
            show(Spinner.this, (ViewGroup) null, f, f2);
            initListView(i, i2);
            if (!isShowing && (viewTreeObserver = Spinner.this.getViewTreeObserver()) != null) {
                final AnonymousClass2 r7 = new ViewTreeObserver.OnGlobalLayoutListener() {
                    /* class miui.external.widget.Spinner.DropdownPopup.AnonymousClass2 */

                    public void onGlobalLayout() {
                        DropdownPopup dropdownPopup = DropdownPopup.this;
                        if (!dropdownPopup.isVisibleToUser(Spinner.this)) {
                            DropdownPopup.this.dismiss();
                            return;
                        }
                        DropdownPopup dropdownPopup2 = DropdownPopup.this;
                        dropdownPopup2.show(i, i2, Spinner.this.mTouchX, Spinner.this.mTouchY);
                    }
                };
                viewTreeObserver.addOnGlobalLayoutListener(r7);
                setOnDismissListener(new PopupWindow.OnDismissListener() {
                    /* class miui.external.widget.Spinner.DropdownPopup.AnonymousClass3 */

                    public void onDismiss() {
                        ViewTreeObserver viewTreeObserver = Spinner.this.getViewTreeObserver();
                        if (viewTreeObserver != null) {
                            viewTreeObserver.removeGlobalOnLayoutListener(r7);
                        }
                        Spinner.this.notifySpinnerDismiss();
                    }
                });
            }
        }

        public void show(View view, ViewGroup viewGroup, float f, float f2) {
            if (prepareShow(view, viewGroup)) {
                showWithAnchor(view, f, f2);
                changeWindowBackground(getContentView().getRootView(), 0.3f);
            }
        }
    }

    public interface OnSpinnerDismissListener {
        void onSpinnerDismiss();
    }

    /* access modifiers changed from: package-private */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            /* class miui.external.widget.Spinner.SavedState.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        boolean mShowDropdown;

        SavedState(Parcel parcel) {
            super(parcel);
            this.mShowDropdown = parcel.readByte() != 0;
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeByte(this.mShowDropdown ? (byte) 1 : 0);
        }
    }

    private static class SpinnerCheckedProvider implements SpinnerCheckableArrayAdapter.CheckedStateProvider {
        private Spinner mSpinner;

        public SpinnerCheckedProvider(Spinner spinner) {
            this.mSpinner = spinner;
        }

        @Override // miui.external.adapter.SpinnerCheckableArrayAdapter.CheckedStateProvider
        public boolean isChecked(int i) {
            return this.mSpinner.getSelectedItemPosition() == i;
        }
    }

    /* access modifiers changed from: private */
    public interface SpinnerPopup {
        void dismiss();

        Drawable getBackground();

        CharSequence getHintText();

        int getHorizontalOffset();

        int getHorizontalOriginalOffset();

        int getVerticalOffset();

        boolean isShowing();

        void setAdapter(ListAdapter listAdapter);

        void setBackgroundDrawable(Drawable drawable);

        void setHorizontalOffset(int i);

        void setHorizontalOriginalOffset(int i);

        void setPromptText(CharSequence charSequence);

        void setVerticalOffset(int i);

        void show(int i, int i2);

        void show(int i, int i2, float f, float f2);
    }

    public interface ThemedAdapter extends SpinnerAdapter {
        @Nullable
        Resources.Theme getDropDownViewTheme();

        void setDropDownViewTheme(@Nullable Resources.Theme theme);
    }

    static {
        try {
            FORWARDING_LISTENER = android.widget.Spinner.class.getDeclaredField("mForwardingListener");
            FORWARDING_LISTENER.setAccessible(true);
        } catch (NoSuchFieldException e2) {
            Log.e(TAG, "static initializer: ", e2);
        }
    }

    public Spinner(Context context) {
        this(context, (AttributeSet) null);
    }

    public Spinner(Context context, int i) {
        this(context, null, R.attr.miuiSpinnerStyle, i);
    }

    public Spinner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.miuiSpinnerStyle);
    }

    public Spinner(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, -1);
    }

    public Spinner(Context context, AttributeSet attributeSet, int i, int i2) {
        this(context, attributeSet, i, i2, null);
    }

    public Spinner(Context context, AttributeSet attributeSet, int i, int i2, Resources.Theme theme) {
        super(context, attributeSet, i);
        this.mTempRect = new Rect();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Spinner, i, 0);
        if (theme == null || Build.VERSION.SDK_INT < 23) {
            int resourceId = obtainStyledAttributes.getResourceId(R.styleable.Spinner_popupTheme, 0);
            if (resourceId != 0) {
                this.mPopupContext = new ContextThemeWrapper(context, resourceId);
            } else {
                this.mPopupContext = context;
            }
        } else {
            this.mPopupContext = new ContextThemeWrapper(context, theme);
        }
        i2 = i2 == -1 ? obtainStyledAttributes.getInt(R.styleable.Spinner_spinnerModeCompat, 1) : i2;
        if (i2 == 0) {
            this.mPopup = new DialogPopup();
            this.mPopup.setPromptText(obtainStyledAttributes.getString(R.styleable.Spinner_android_prompt));
        } else if (i2 == 1) {
            DropdownPopup dropdownPopup = new DropdownPopup(this.mPopupContext, attributeSet, i);
            TypedArray obtainStyledAttributes2 = this.mPopupContext.obtainStyledAttributes(attributeSet, R.styleable.Spinner, i, 0);
            this.mDropDownWidth = obtainStyledAttributes2.getLayoutDimension(R.styleable.Spinner_android_dropDownWidth, -2);
            this.mDropDownMinWidth = obtainStyledAttributes2.getLayoutDimension(R.styleable.Spinner_dropDownMinWidth, -2);
            int resourceId2 = obtainStyledAttributes2.getResourceId(R.styleable.Spinner_android_popupBackground, 0);
            if (resourceId2 != 0) {
                setPopupBackgroundResource(resourceId2);
            } else {
                dropdownPopup.setBackgroundDrawable(obtainStyledAttributes2.getDrawable(R.styleable.Spinner_android_popupBackground));
            }
            dropdownPopup.setPromptText(obtainStyledAttributes.getString(R.styleable.Spinner_android_prompt));
            obtainStyledAttributes2.recycle();
            this.mPopup = dropdownPopup;
            makeSupperForwardingListenerInvalid();
        }
        CharSequence[] textArray = obtainStyledAttributes.getTextArray(R.styleable.Spinner_android_entries);
        if (textArray != null) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.miuix_compat_simple_spinner_layout, 16908308, textArray);
            arrayAdapter.setDropDownViewResource(R.layout.miuix_compat_simple_spinner_dropdown_item);
            setAdapter((SpinnerAdapter) arrayAdapter);
        }
        obtainStyledAttributes.recycle();
        this.mPopupSet = true;
        SpinnerAdapter spinnerAdapter = this.mTempAdapter;
        if (spinnerAdapter != null) {
            setAdapter(spinnerAdapter);
            this.mTempAdapter = null;
        }
    }

    private int compatMeasureSelectItemWidth(SpinnerAdapter spinnerAdapter, Drawable drawable) {
        if (spinnerAdapter == null || spinnerAdapter.getCount() == 0) {
            return 0;
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        View view = spinnerAdapter.getView(Math.max(0, getSelectedItemPosition()), null, this);
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        }
        view.measure(makeMeasureSpec, makeMeasureSpec2);
        int max = Math.max(0, view.getMeasuredWidth());
        if (drawable == null) {
            return max;
        }
        drawable.getPadding(this.mTempRect);
        Rect rect = this.mTempRect;
        return max + rect.left + rect.right;
    }

    private void makeSupperForwardingListenerInvalid() {
        Field field = FORWARDING_LISTENER;
        if (field != null) {
            try {
                field.set(this, null);
            } catch (IllegalAccessException e2) {
                Log.e(TAG, "makeSupperForwardingListenerInvalid: ", e2);
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void notifySpinnerDismiss() {
        OnSpinnerDismissListener onSpinnerDismissListener = this.mOnSpinnerDismissListener;
        if (onSpinnerDismissListener != null) {
            onSpinnerDismissListener.onSpinnerDismiss();
        }
    }

    private boolean superViewPerformClick() {
        sendAccessibilityEvent(1);
        return false;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void vibrate() {
        if (HapticFeedbackUtil.isSupportLinearMotorVibrate(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_MESH_NORMAL)) {
            performHapticFeedback(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_MESH_NORMAL);
        }
    }

    /* access modifiers changed from: package-private */
    public int compatMeasureContentWidth(SpinnerAdapter spinnerAdapter, Drawable drawable) {
        int i = 0;
        if (spinnerAdapter == null) {
            return 0;
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        int max = Math.max(0, getSelectedItemPosition());
        int min = Math.min(spinnerAdapter.getCount(), max + 15);
        int i2 = 0;
        View view = null;
        for (int max2 = Math.max(0, max - (15 - (min - max))); max2 < min; max2++) {
            int itemViewType = spinnerAdapter.getItemViewType(max2);
            if (itemViewType != i) {
                view = null;
                i = itemViewType;
            }
            view = spinnerAdapter.getView(max2, view, this);
            if (view.getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            view.measure(makeMeasureSpec, makeMeasureSpec2);
            i2 = Math.max(i2, view.getMeasuredWidth());
        }
        if (drawable == null) {
            return i2;
        }
        drawable.getPadding(this.mTempRect);
        Rect rect = this.mTempRect;
        return i2 + rect.left + rect.right;
    }

    public int getDropDownHorizontalOffset() {
        SpinnerPopup spinnerPopup = this.mPopup;
        return spinnerPopup != null ? spinnerPopup.getHorizontalOffset() : super.getDropDownHorizontalOffset();
    }

    public int getDropDownVerticalOffset() {
        SpinnerPopup spinnerPopup = this.mPopup;
        return spinnerPopup != null ? spinnerPopup.getVerticalOffset() : super.getDropDownVerticalOffset();
    }

    public int getDropDownWidth() {
        return this.mPopup != null ? this.mDropDownWidth : super.getDropDownWidth();
    }

    public Drawable getPopupBackground() {
        SpinnerPopup spinnerPopup = this.mPopup;
        return spinnerPopup != null ? spinnerPopup.getBackground() : super.getPopupBackground();
    }

    public Context getPopupContext() {
        return this.mPopupContext;
    }

    public CharSequence getPrompt() {
        SpinnerPopup spinnerPopup = this.mPopup;
        return spinnerPopup != null ? spinnerPopup.getHintText() : super.getPrompt();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null && spinnerPopup.isShowing()) {
            this.mPopup.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mPopup != null && View.MeasureSpec.getMode(i) == Integer.MIN_VALUE) {
            setMeasuredDimension(Math.min(Math.min(getMeasuredWidth(), compatMeasureSelectItemWidth(getAdapter(), getBackground())), View.MeasureSpec.getSize(i)), getMeasuredHeight());
        }
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        ViewTreeObserver viewTreeObserver;
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.mShowDropdown && (viewTreeObserver = getViewTreeObserver()) != null) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                /* class miui.external.widget.Spinner.AnonymousClass1 */

                public void onGlobalLayout() {
                    if (!Spinner.this.mPopup.isShowing()) {
                        Spinner.this.showPopup();
                    }
                    ViewTreeObserver viewTreeObserver = Spinner.this.getViewTreeObserver();
                    if (viewTreeObserver != null) {
                        viewTreeObserver.removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SpinnerPopup spinnerPopup = this.mPopup;
        savedState.mShowDropdown = spinnerPopup != null && spinnerPopup.isShowing();
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            this.mTouchX = motionEvent.getX();
            this.mTouchY = motionEvent.getY();
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean performClick() {
        int[] iArr = new int[2];
        getLocationInWindow(iArr);
        return performClick((float) iArr[0], (float) iArr[1]);
    }

    public boolean performClick(float f, float f2) {
        vibrate();
        this.mTouchX = f;
        this.mTouchY = f2;
        if (superViewPerformClick()) {
            return true;
        }
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup == null) {
            return super.performClick();
        }
        if (!spinnerPopup.isShowing()) {
            showPopup(this.mTouchX, this.mTouchY);
        }
        return true;
    }

    @Override // android.widget.AbsSpinner, android.widget.Spinner
    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        if (!this.mPopupSet) {
            this.mTempAdapter = spinnerAdapter;
            return;
        }
        super.setAdapter(spinnerAdapter);
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup instanceof DialogPopup) {
            spinnerPopup.setAdapter(new DialogPopupAdapter(spinnerAdapter, getPopupContext().getTheme()));
        } else if (spinnerPopup instanceof DropdownPopup) {
            spinnerPopup.setAdapter(new DropDownPopupAdapter(spinnerAdapter, getPopupContext().getTheme()));
        }
    }

    public void setDoubleLineContentAdapter(SpinnerDoubleLineContentAdapter spinnerDoubleLineContentAdapter) {
        setAdapter((SpinnerAdapter) new SpinnerCheckableArrayAdapter(getContext(), R.layout.miuix_compat_simple_spinner_layout, spinnerDoubleLineContentAdapter, new SpinnerCheckedProvider(this)));
    }

    public void setDropDownHorizontalOffset(int i) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setHorizontalOriginalOffset(i);
            this.mPopup.setHorizontalOffset(i);
            return;
        }
        super.setDropDownHorizontalOffset(i);
    }

    public void setDropDownVerticalOffset(int i) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setVerticalOffset(i);
        } else {
            super.setDropDownVerticalOffset(i);
        }
    }

    public void setDropDownWidth(int i) {
        if (this.mPopup != null) {
            this.mDropDownWidth = i;
        } else {
            super.setDropDownWidth(i);
        }
    }

    public void setOnSpinnerDismissListener(OnSpinnerDismissListener onSpinnerDismissListener) {
        this.mOnSpinnerDismissListener = onSpinnerDismissListener;
    }

    public void setPopupBackgroundDrawable(Drawable drawable) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setBackgroundDrawable(drawable);
        } else {
            super.setPopupBackgroundDrawable(drawable);
        }
    }

    public void setPopupBackgroundResource(@DrawableRes int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            setPopupBackgroundDrawable(getPopupContext().getDrawable(i));
        } else {
            setPopupBackgroundDrawable(getPopupContext().getResources().getDrawable(i));
        }
    }

    public void setPrompt(CharSequence charSequence) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setPromptText(charSequence);
        } else {
            super.setPrompt(charSequence);
        }
    }

    /* access modifiers changed from: package-private */
    public void showPopup() {
        this.mPopup.show(getTextDirection(), getTextAlignment());
    }

    /* access modifiers changed from: package-private */
    public void showPopup(float f, float f2) {
        this.mPopup.show(getTextDirection(), getTextAlignment(), f, f2);
    }
}
