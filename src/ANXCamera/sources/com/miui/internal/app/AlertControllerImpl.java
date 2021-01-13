package com.miui.internal.app;

import android.app.Dialog;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.miui.internal.R;
import com.miui.internal.util.DeviceHelper;
import com.miui.internal.util.FolmeAnimHelper;
import com.miui.internal.variable.AlertControllerWrapper;
import com.miui.internal.view.menu.MenuBuilder;
import com.miui.internal.view.menu.MenuPresenter;
import com.miui.internal.widget.ActionBarContainer;
import com.miui.internal.widget.ActionBarContextView;
import com.miui.internal.widget.ActionBarView;
import com.miui.internal.widget.DialogButtonPanel;
import com.miui.internal.widget.SingleCenterTextView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import miui.app.ActionBar;
import miui.os.SystemProperties;
import miui.reflect.Method;
import miui.reflect.NoSuchMethodException;
import miui.util.AttributeResolver;
import miui.util.HapticFeedbackUtil;
import miui.util.Log;
import miui.view.EditActionMode;
import miui.view.MiuiHapticFeedbackConstants;
import miui.widget.EditableListViewWrapper;
import miui.widget.ProgressBar;

public class AlertControllerImpl implements MenuBuilder.Callback {
    private static final Method ADD_EXTRA_FLAGS = getAddExtraFlags();
    private static final int BREAK_STRATEGY_SIMPLE = 0;
    private static final int FLAG_NO_EAR_AREA = 768;
    private static final String TAG = "AlertController";
    private static int sScreenMinorSize;
    private ActionBar mActionBar;
    private ActionBarView mActionBarView;
    private ArrayList<AlertControllerWrapper.AlertParams.ActionItem> mActionItems;
    private ListAdapter mAdapter;
    private final int mAlertDialogLayout;
    private ViewGroup mAlertDialogView;
    private View.OnClickListener mButtonHandler = new View.OnClickListener() {
        /* class com.miui.internal.app.AlertControllerImpl.AnonymousClass1 */

        public void onClick(View view) {
            Message message;
            Button button = AlertControllerImpl.this.mButtonPositive;
            int i = MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_TAP_LIGHT;
            if (view != button || AlertControllerImpl.this.mButtonPositiveMessage == null) {
                message = (view != AlertControllerImpl.this.mButtonNegative || AlertControllerImpl.this.mButtonNegativeMessage == null) ? (view != AlertControllerImpl.this.mButtonNeutral || AlertControllerImpl.this.mButtonNeutralMessage == null) ? null : Message.obtain(AlertControllerImpl.this.mButtonNeutralMessage) : Message.obtain(AlertControllerImpl.this.mButtonNegativeMessage);
            } else {
                message = Message.obtain(AlertControllerImpl.this.mButtonPositiveMessage);
                i = 268435456;
            }
            if (message != null) {
                message.sendToTarget();
            }
            if (HapticFeedbackUtil.isSupportLinearMotorVibrate(i)) {
                view.performHapticFeedback(i);
            }
            AlertControllerImpl.this.mHandler.obtainMessage(1, AlertControllerImpl.this.mDialogInterface).sendToTarget();
        }
    };
    private Button mButtonNegative;
    private Message mButtonNegativeMessage;
    private CharSequence mButtonNegativeText;
    private Button mButtonNeutral;
    private Message mButtonNeutralMessage;
    private CharSequence mButtonNeutralText;
    private Button mButtonPositive;
    private Message mButtonPositiveMessage;
    private CharSequence mButtonPositiveText;
    private Button mButtonSelect;
    private CharSequence mCheckBoxMessage;
    private int mCheckedItem = -1;
    private boolean[] mCheckedItems;
    private AlertComponentCallbacks mComponentCallback;
    private Context mContext;
    private View mCustomTitleView;
    private Drawable mDefaultWindowBg;
    private DialogInterface mDialogInterface;
    private EditableListViewWrapper mEditableListViewWrapper;
    private Handler mHandler;
    public boolean mHapticFeedbackEnabled;
    private Drawable mIcon;
    private int mIconId = 0;
    private final Runnable mInvalidateMenuRunnable = new Runnable() {
        /* class com.miui.internal.app.AlertControllerImpl.AnonymousClass2 */

        public void run() {
            MenuBuilder createMenu = AlertControllerImpl.this.createMenu();
            if (!AlertControllerImpl.this.onCreatePanelMenu(createMenu) || !AlertControllerImpl.this.onPreparePanelMenu(createMenu)) {
                AlertControllerImpl.this.setMenu(null);
            } else {
                AlertControllerImpl.this.setMenu(createMenu);
            }
        }
    };
    private boolean mIsChecked;
    private final int mListItemLayout;
    private final int mListLayout;
    private ListView mListView;
    private MenuBuilder mMenu;
    private MenuPresenter.Callback mMenuPresenterCallback = new MenuPresenter.Callback() {
        /* class com.miui.internal.app.AlertControllerImpl.AnonymousClass6 */

        @Override // com.miui.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }

        @Override // com.miui.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            return false;
        }
    };
    private CharSequence mMessage;
    private TextView mMessageView;
    private final int mMultiChoiceItemLayout;
    private DialogInterface.OnClickListener mOnActionItemClickListener;
    private ScrollView mScrollView;
    private final int mSingleChoiceItemLayout;
    private CharSequence mTitle;
    private TextView mTitleView;
    private View mView;
    private final Window mWindow;
    private Window.Callback mWindowCallback = new Window.Callback() {
        /* class com.miui.internal.app.AlertControllerImpl.AnonymousClass7 */

        public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
            return false;
        }

        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return false;
        }

        public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            return false;
        }

        public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            return false;
        }

        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            return false;
        }

        public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
            return false;
        }

        public void onActionModeFinished(ActionMode actionMode) {
        }

        public void onActionModeStarted(ActionMode actionMode) {
        }

        public void onAttachedToWindow() {
        }

        public void onContentChanged() {
        }

        public boolean onCreatePanelMenu(int i, Menu menu) {
            return false;
        }

        public View onCreatePanelView(int i) {
            return null;
        }

        public void onDetachedFromWindow() {
        }

        public boolean onMenuItemSelected(int i, MenuItem menuItem) {
            return false;
        }

        public boolean onMenuOpened(int i, Menu menu) {
            return false;
        }

        public void onPanelClosed(int i, Menu menu) {
        }

        public boolean onPreparePanel(int i, View view, Menu menu) {
            return false;
        }

        public boolean onSearchRequested() {
            return false;
        }

        public boolean onSearchRequested(SearchEvent searchEvent) {
            return false;
        }

        public void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        }

        public void onWindowFocusChanged(boolean z) {
        }

        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            return null;
        }

        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
            return null;
        }
    };

    /* access modifiers changed from: private */
    public static class AlertComponentCallbacks implements ComponentCallbacks {
        private WeakReference<AlertControllerImpl> mImplRef;

        AlertComponentCallbacks(AlertControllerImpl alertControllerImpl) {
            this.mImplRef = new WeakReference<>(alertControllerImpl);
        }

        public void onConfigurationChanged(Configuration configuration) {
            AlertControllerImpl alertControllerImpl = this.mImplRef.get();
            if (alertControllerImpl != null && (configuration.diff(alertControllerImpl.mContext.getResources().getConfiguration()) & 128) != 0 && alertControllerImpl.mWindow.getDecorView().isAttachedToWindow()) {
                alertControllerImpl.setupWindow(configuration.orientation);
            }
        }

        public void onLowMemory() {
        }
    }

    private static final class ButtonHandler extends Handler {
        private static final int MSG_DISMISS_DIALOG = 1;
        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialogInterface) {
            this.mDialog = new WeakReference<>(dialogInterface);
        }

        public void handleMessage(Message message) {
            Object obj = message.obj;
            DialogInterface dialogInterface = obj instanceof DialogInterface ? (DialogInterface) obj : this.mDialog.get();
            if (!(dialogInterface instanceof Dialog) || ((Dialog) dialogInterface).isShowing()) {
                int i = message.what;
                if (i == -3 || i == -2 || i == -1) {
                    ((DialogInterface.OnClickListener) message.obj).onClick(this.mDialog.get(), message.what);
                } else if (i == 1) {
                    ((DialogInterface) message.obj).dismiss();
                }
            } else {
                Log.w(AlertControllerImpl.TAG, "dialog has already been dismissed, disregard message process");
            }
        }
    }

    public AlertControllerImpl(Context context, DialogInterface dialogInterface, Window window) {
        Method method;
        initScreenMinorSize(context);
        this.mContext = context;
        this.mDialogInterface = dialogInterface;
        this.mWindow = window;
        this.mWindow.requestFeature(1);
        if (Build.VERSION.SDK_INT < 28 && SystemProperties.getInt("ro.miui.notch", 0) == 1 && (method = ADD_EXTRA_FLAGS) != null) {
            method.invoke(Window.class, this.mWindow, Integer.valueOf((int) FLAG_NO_EAR_AREA));
        }
        this.mHandler = new ButtonHandler(dialogInterface);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.AlertDialog, 16842845, 0);
        this.mAlertDialogLayout = obtainStyledAttributes.getResourceId(R.styleable.AlertDialog_layout, R.layout.alert_dialog);
        this.mListLayout = obtainStyledAttributes.getResourceId(R.styleable.AlertDialog_listLayout, R.layout.select_dialog);
        this.mMultiChoiceItemLayout = obtainStyledAttributes.getResourceId(R.styleable.AlertDialog_multiChoiceItemLayout, R.layout.select_dialog_multichoice);
        this.mSingleChoiceItemLayout = obtainStyledAttributes.getResourceId(R.styleable.AlertDialog_singleChoiceItemLayout, miui.R.layout.select_dialog_singlechoice);
        this.mListItemLayout = obtainStyledAttributes.getResourceId(R.styleable.AlertDialog_listItemLayout, R.layout.select_dialog_item);
        obtainStyledAttributes.recycle();
    }

    static boolean canTextInput(View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        while (childCount > 0) {
            childCount--;
            if (canTextInput(viewGroup.getChildAt(childCount))) {
                return true;
            }
        }
        return false;
    }

    private void centerIfSingleLine(TextView textView, CharSequence charSequence) {
        if (this.mView == null && this.mCheckBoxMessage == null && (textView instanceof SingleCenterTextView)) {
            ((SingleCenterTextView) textView).setEnableSingleCenter(true);
        }
    }

    private void ensureSubDecor() {
        if (this.mActionItems != null) {
            View inflate = View.inflate(this.mContext, R.layout.screen_action_bar, null);
            this.mActionBarView = (ActionBarView) inflate.findViewById(R.id.action_bar);
            this.mActionBarView.setWindowCallback(this.mWindowCallback);
            ActionBarContainer actionBarContainer = (ActionBarContainer) inflate.findViewById(R.id.split_action_bar);
            if (actionBarContainer != null) {
                this.mActionBarView.setSplitView(actionBarContainer);
                this.mActionBarView.setSplitActionBar(true);
                this.mActionBarView.setSplitWhenNarrow(true);
                ActionBarContextView actionBarContextView = (ActionBarContextView) inflate.findViewById(R.id.action_context_bar);
                actionBarContextView.setSplitView(actionBarContainer);
                actionBarContextView.setSplitActionBar(true);
                actionBarContextView.setSplitWhenNarrow(true);
            }
            View.inflate(this.mContext, this.mAlertDialogLayout, (ViewGroup) inflate.findViewById(16908290));
            this.mWindow.setContentView(inflate);
            this.mWindow.getDecorView().post(this.mInvalidateMenuRunnable);
            this.mActionBar = new ActionBarImpl((Dialog) this.mDialogInterface);
            this.mActionBar.setDisplayOptions(0);
            this.mActionBarView.setCollapsable(true);
        } else {
            this.mWindow.setContentView(this.mAlertDialogLayout);
            if (!DeviceHelper.IS_TABLET) {
                setupWindow(this.mContext.getResources().getConfiguration().orientation);
            }
        }
        AlertControllerImplHelper.initTouchAnim(this.mWindow);
    }

    private static Method getAddExtraFlags() {
        try {
            return Method.of(Window.class, "addExtraFlags", "(I)V");
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    private static void initScreenMinorSize(Context context) {
        if (sScreenMinorSize == 0) {
            Point point = new Point();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getSize(point);
            sScreenMinorSize = Math.min(point.x, point.y);
        }
    }

    private boolean isEllipsized(TextView textView, int i) {
        return ((int) textView.getPaint().measureText(textView.getText().toString())) > (i - textView.getPaddingStart()) - textView.getPaddingEnd();
    }

    private boolean needRemoveCustomMargin() {
        return TextUtils.isEmpty(this.mButtonPositiveText) && TextUtils.isEmpty(this.mButtonNegativeText) && TextUtils.isEmpty(this.mButtonNeutralText);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean onCreatePanelMenu(MenuBuilder menuBuilder) {
        Iterator<AlertControllerWrapper.AlertParams.ActionItem> it = this.mActionItems.iterator();
        while (it.hasNext()) {
            AlertControllerWrapper.AlertParams.ActionItem next = it.next();
            menuBuilder.add(0, next.id, 0, next.label).setIcon(next.icon).setShowAsAction(2);
        }
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean onPreparePanelMenu(MenuBuilder menuBuilder) {
        return true;
    }

    private void relayoutButtons(DialogButtonPanel dialogButtonPanel) {
        dialogButtonPanel.setOrientation(1);
        dialogButtonPanel.removeAllViews();
        Button button = this.mButtonPositive;
        if (button != null) {
            dialogButtonPanel.addView(button);
        }
        Button button2 = this.mButtonNeutral;
        if (button2 != null) {
            dialogButtonPanel.addView(button2);
        }
        Button button3 = this.mButtonNegative;
        if (button3 != null) {
            dialogButtonPanel.addView(button3);
        }
    }

    private void setBreakStragegy(TextView textView, int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Method.of(TextView.class, "setBreakStrategy", "(I)V").invoke(TextView.class, textView, Integer.valueOf(i));
            } catch (Exception unused) {
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setMenu(MenuBuilder menuBuilder) {
        if (menuBuilder != this.mMenu) {
            this.mMenu = menuBuilder;
            ActionBarView actionBarView = this.mActionBarView;
            if (actionBarView != null) {
                actionBarView.setMenu(menuBuilder, this.mMenuPresenterCallback);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00a9  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x010b  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x004a  */
    private void setupButtons(ViewGroup viewGroup) {
        int i;
        int i2;
        Button button;
        Button button2;
        this.mButtonPositive = (Button) viewGroup.findViewById(EditActionMode.BUTTON1);
        Button button3 = this.mButtonPositive;
        if (button3 != null) {
            button3.setOnClickListener(this.mButtonHandler);
            if (TextUtils.isEmpty(this.mButtonPositiveText)) {
                this.mButtonPositive.setVisibility(8);
            } else {
                this.mButtonPositive.setText(this.mButtonPositiveText);
                this.mButtonPositive.setVisibility(0);
                FolmeAnimHelper.addPressAnimWithTint(this.mButtonPositive);
                i = 1;
                i2 = i;
                this.mButtonNegative = (Button) viewGroup.findViewById(EditActionMode.BUTTON2);
                button = this.mButtonNegative;
                if (button != null) {
                    button.setOnClickListener(this.mButtonHandler);
                    if (TextUtils.isEmpty(this.mButtonNegativeText)) {
                        this.mButtonNegative.setVisibility(8);
                    } else {
                        this.mButtonNegative.setText(this.mButtonNegativeText);
                        this.mButtonNegative.setVisibility(0);
                        i++;
                        FolmeAnimHelper.addPressAnim(this.mButtonNegative);
                        i2 = 1;
                    }
                }
                this.mButtonNeutral = (Button) viewGroup.findViewById(16908315);
                button2 = this.mButtonNeutral;
                if (button2 != null) {
                    button2.setOnClickListener(this.mButtonHandler);
                    if (TextUtils.isEmpty(this.mButtonNeutralText)) {
                        this.mButtonNeutral.setVisibility(8);
                    } else {
                        this.mButtonNeutral.setText(this.mButtonNeutralText);
                        this.mButtonNeutral.setVisibility(0);
                        i++;
                        FolmeAnimHelper.addPressAnim(this.mButtonNeutral);
                        i2 = 1;
                    }
                }
                if (i2 == 0) {
                    DialogButtonPanel dialogButtonPanel = (DialogButtonPanel) viewGroup.findViewById(R.id.buttonGroup);
                    if (i > 2) {
                        relayoutButtons(dialogButtonPanel);
                        return;
                    } else if (i == 1) {
                        dialogButtonPanel.clearVisibleChildMargins();
                        return;
                    } else {
                        int i3 = this.mContext.getResources().getDisplayMetrics().widthPixels;
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams();
                        int marginStart = ((i3 - (marginLayoutParams.getMarginStart() + marginLayoutParams.getMarginEnd())) - this.mContext.getResources().getDimensionPixelOffset(R.dimen.dialog_btn_margin_horizontal)) / 2;
                        boolean z = false;
                        for (int i4 = 0; i4 < dialogButtonPanel.getChildCount(); i4++) {
                            TextView textView = (TextView) dialogButtonPanel.getChildAt(i4);
                            if (textView.getVisibility() == 0) {
                                z = isEllipsized(textView, marginStart);
                            }
                            if (z) {
                                break;
                            }
                        }
                        if (z) {
                            relayoutButtons(dialogButtonPanel);
                            return;
                        }
                        return;
                    }
                } else {
                    viewGroup.setVisibility(8);
                    return;
                }
            }
        }
        i = 0;
        i2 = i;
        this.mButtonNegative = (Button) viewGroup.findViewById(EditActionMode.BUTTON2);
        button = this.mButtonNegative;
        if (button != null) {
        }
        this.mButtonNeutral = (Button) viewGroup.findViewById(16908315);
        button2 = this.mButtonNeutral;
        if (button2 != null) {
        }
        if (i2 == 0) {
        }
    }

    private void setupCheckbox(CheckBox checkBox) {
        if (this.mCheckBoxMessage != null) {
            checkBox.setVisibility(0);
            checkBox.setChecked(this.mIsChecked);
            checkBox.setText(this.mCheckBoxMessage);
            return;
        }
        checkBox.setVisibility(8);
    }

    private void setupContent(ViewGroup viewGroup) {
        this.mScrollView = (ScrollView) this.mAlertDialogView.findViewById(R.id.scrollView);
        this.mScrollView.setFocusable(false);
        this.mMessageView = (TextView) this.mAlertDialogView.findViewById(miui.R.id.message);
        TextView textView = this.mMessageView;
        if (textView != null) {
            if (this.mMessage != null) {
                setBreakStragegy(textView, 0);
                this.mMessageView.setText(this.mMessage);
                centerIfSingleLine(this.mMessageView, this.mMessage);
                return;
            }
            textView.setVisibility(8);
            this.mScrollView.removeView(this.mMessageView);
            if (this.mListView != null) {
                setupListView();
                viewGroup.removeAllViews();
                viewGroup.addView(this.mListView, new LinearLayout.LayoutParams(-1, -1));
                return;
            }
            viewGroup.setVisibility(8);
        }
    }

    private void setupCustom(FrameLayout frameLayout) {
        if (this.mView != null) {
            ((FrameLayout) this.mAlertDialogView.findViewById(16908331)).addView(this.mView, new ViewGroup.LayoutParams(-1, -1));
            if (this.mListView != null) {
                ((LinearLayout.LayoutParams) frameLayout.getLayoutParams()).weight = 0.0f;
            }
            if (needRemoveCustomMargin()) {
                ((LinearLayout.LayoutParams) frameLayout.getLayoutParams()).bottomMargin = 0;
            }
            View view = this.mView;
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.dialog_custom_horizontal_padding);
                int paddingLeft = viewGroup.getPaddingLeft() != 0 ? viewGroup.getPaddingLeft() : dimensionPixelSize;
                if (viewGroup.getPaddingRight() != 0) {
                    dimensionPixelSize = viewGroup.getPaddingRight();
                }
                View findViewById = viewGroup.findViewById(16908301);
                if (findViewById != null && !(findViewById instanceof ProgressBar)) {
                    frameLayout.setPadding(0, 0, 0, 0);
                } else if (viewGroup.findViewById(R.id.datePicker) == null && viewGroup.findViewById(R.id.timePicker) == null && viewGroup.findViewById(R.id.dateTimePicker) == null) {
                    frameLayout.setPadding(paddingLeft, 0, dimensionPixelSize, 0);
                    viewGroup.setPadding(0, 0, 0, 0);
                } else {
                    frameLayout.setPadding(0, 0, 0, 0);
                }
            }
        } else {
            frameLayout.setVisibility(8);
        }
    }

    private void setupListView() {
        int choiceMode = this.mListView.getChoiceMode();
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null) {
            if (choiceMode == 2) {
                Button button = (Button) this.mAlertDialogView.findViewById(R.id.cancel);
                if (button != null) {
                    button.setOnClickListener(new View.OnClickListener() {
                        /* class com.miui.internal.app.AlertControllerImpl.AnonymousClass3 */

                        public void onClick(View view) {
                            AlertControllerImpl.this.mDialogInterface.dismiss();
                        }
                    });
                }
                final Button button2 = (Button) this.mAlertDialogView.findViewById(R.id.select);
                if (button2 != null) {
                    this.mButtonSelect = button2;
                    button2.setOnClickListener(new View.OnClickListener() {
                        /* class com.miui.internal.app.AlertControllerImpl.AnonymousClass4 */

                        public void onClick(View view) {
                            boolean isAllItemsChecked = AlertControllerImpl.this.mEditableListViewWrapper.isAllItemsChecked();
                            AlertControllerImpl.this.mEditableListViewWrapper.setAllItemsChecked(!isAllItemsChecked);
                            button2.setText(isAllItemsChecked ? miui.R.string.select_all : miui.R.string.deselect_all);
                        }
                    });
                }
                if (this.mButtonSelect != null) {
                    this.mEditableListViewWrapper = new EditableListViewWrapper(this.mListView);
                    this.mListView.setChoiceMode(choiceMode);
                    this.mEditableListViewWrapper.setAdapter(this.mAdapter);
                    final AdapterView.OnItemClickListener onItemClickListener = this.mListView.getOnItemClickListener();
                    this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        /* class com.miui.internal.app.AlertControllerImpl.AnonymousClass5 */

                        @Override // android.widget.AdapterView.OnItemClickListener
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                            onItemClickListener.onItemClick(adapterView, view, i, j);
                            AlertControllerImpl.this.mButtonSelect.setText(AlertControllerImpl.this.mEditableListViewWrapper.isAllItemsChecked() ? miui.R.string.deselect_all : miui.R.string.select_all);
                        }
                    });
                    if (this.mCheckedItems != null) {
                        int i = 0;
                        while (true) {
                            boolean[] zArr = this.mCheckedItems;
                            if (i >= zArr.length) {
                                break;
                            }
                            this.mEditableListViewWrapper.setItemChecked(i, zArr[i]);
                            i++;
                        }
                    }
                    this.mButtonSelect.setText(this.mEditableListViewWrapper.isAllItemsChecked() ? miui.R.string.deselect_all : miui.R.string.select_all);
                } else {
                    this.mListView.setAdapter(this.mAdapter);
                }
            } else {
                this.mListView.setAdapter(listAdapter);
            }
        }
        int i2 = this.mCheckedItem;
        if (i2 > -1) {
            this.mListView.setItemChecked(i2, true);
            this.mListView.setSelection(this.mCheckedItem);
        }
    }

    private void setupTitle(ViewGroup viewGroup) {
        if (this.mCustomTitleView != null) {
            viewGroup.addView(this.mCustomTitleView, 0, new LinearLayout.LayoutParams(-1, -2));
            viewGroup.removeView(this.mAlertDialogView.findViewById(R.id.alertTitle));
        } else if (!TextUtils.isEmpty(this.mTitle)) {
            this.mTitleView = (TextView) viewGroup.findViewById(R.id.alertTitle);
            this.mTitleView.setText(this.mTitle);
            Drawable drawable = this.mIcon;
            if (drawable != null) {
                this.mTitleView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
            }
            int i = this.mIconId;
            if (i != 0) {
                this.mTitleView.setCompoundDrawablesRelativeWithIntrinsicBounds(i, 0, 0, 0);
            }
        } else {
            viewGroup.setVisibility(8);
        }
    }

    private void setupView() {
        ViewGroup viewGroup = (ViewGroup) this.mAlertDialogView.findViewById(R.id.topPanel);
        if (viewGroup != null) {
            setupTitle(viewGroup);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.mAlertDialogView.findViewById(R.id.contentPanel);
        if (viewGroup2 != null) {
            setupContent(viewGroup2);
        }
        FrameLayout frameLayout = (FrameLayout) this.mAlertDialogView.findViewById(miui.R.id.customPanel);
        if (frameLayout != null) {
            setupCustom(frameLayout);
        }
        CheckBox checkBox = (CheckBox) this.mAlertDialogView.findViewById(16908289);
        if (checkBox != null) {
            setupCheckbox(checkBox);
        }
        ViewGroup viewGroup3 = (ViewGroup) this.mAlertDialogView.findViewById(R.id.buttonPanel);
        if (viewGroup3 != null) {
            setupButtons(viewGroup3);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setupWindow(int i) {
        boolean z = i == 2;
        this.mWindow.setGravity(z ? AlertControllerImplHelper.getLandScapeGravity() : 80);
        this.mWindow.setLayout(z ? sScreenMinorSize : -1, -2);
        Drawable drawable = this.mDefaultWindowBg;
        if (z) {
            drawable = AttributeResolver.resolveDrawable(this.mContext, R.attr.dialogRoundWindowBg);
        }
        this.mWindow.setBackgroundDrawable(drawable);
    }

    /* access modifiers changed from: package-private */
    public MenuBuilder createMenu() {
        MenuBuilder menuBuilder = new MenuBuilder(this.mContext);
        menuBuilder.setCallback(this);
        return menuBuilder;
    }

    public ActionBar getActionBar() {
        return this.mActionBar;
    }

    public Button getButton(int i) {
        if (i == -3) {
            return this.mButtonNeutral;
        }
        if (i == -2) {
            return this.mButtonNegative;
        }
        if (i != -1) {
            return null;
        }
        return this.mButtonPositive;
    }

    public boolean[] getCheckedItems() {
        return this.mCheckedItems;
    }

    public DialogInterface getDialogInterface() {
        return this.mDialogInterface;
    }

    public int getListItemLayout() {
        return this.mListItemLayout;
    }

    public int getListLayout() {
        return this.mListLayout;
    }

    public ListView getListView() {
        return this.mListView;
    }

    public TextView getMessageView() {
        return this.mMessageView;
    }

    public int getMultiChoiceItemLayout() {
        return this.mMultiChoiceItemLayout;
    }

    public int getSingleChoiceItemLayout() {
        return this.mSingleChoiceItemLayout;
    }

    public boolean hasButton() {
        return ((ViewGroup) this.mAlertDialogView.findViewById(R.id.buttonPanel)).getVisibility() == 0;
    }

    public boolean hasTitle() {
        return ((ViewGroup) this.mAlertDialogView.findViewById(R.id.topPanel)).getVisibility() == 0;
    }

    public void installContent() {
        View view = this.mView;
        if (view == null || !canTextInput(view)) {
            this.mWindow.setFlags(131072, 131072);
        }
        this.mDefaultWindowBg = this.mWindow.getDecorView().getBackground();
        ensureSubDecor();
        this.mAlertDialogView = (ViewGroup) this.mWindow.findViewById(R.id.parentPanel);
        setupView();
    }

    public boolean isChecked() {
        boolean isChecked = ((CheckBox) this.mAlertDialogView.findViewById(16908289)).isChecked();
        this.mIsChecked = isChecked;
        return isChecked;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        ScrollView scrollView = this.mScrollView;
        return scrollView != null && scrollView.executeKeyEvent(keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        ScrollView scrollView = this.mScrollView;
        return scrollView != null && scrollView.executeKeyEvent(keyEvent);
    }

    @Override // com.miui.internal.view.menu.MenuBuilder.Callback
    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        DialogInterface.OnClickListener onClickListener = this.mOnActionItemClickListener;
        if (onClickListener == null) {
            return true;
        }
        onClickListener.onClick(this.mDialogInterface, menuItem.getItemId());
        return true;
    }

    @Override // com.miui.internal.view.menu.MenuBuilder.Callback
    public void onMenuModeChange(MenuBuilder menuBuilder) {
    }

    public void onStart() {
        if (this.mComponentCallback == null) {
            this.mComponentCallback = new AlertComponentCallbacks(this);
        }
        this.mContext.registerComponentCallbacks(this.mComponentCallback);
    }

    public void onStop() {
        AlertComponentCallbacks alertComponentCallbacks = this.mComponentCallback;
        if (alertComponentCallbacks != null) {
            this.mContext.unregisterComponentCallbacks(alertComponentCallbacks);
            this.mComponentCallback = null;
        }
    }

    public void setActionItems(ArrayList<AlertControllerWrapper.AlertParams.ActionItem> arrayList, DialogInterface.OnClickListener onClickListener) {
        this.mActionItems = arrayList;
        this.mOnActionItemClickListener = onClickListener;
    }

    public void setAdapter(ListAdapter listAdapter) {
        this.mAdapter = listAdapter;
    }

    public void setButton(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message) {
        if (message == null && onClickListener != null) {
            message = this.mHandler.obtainMessage(i, onClickListener);
        }
        if (i == -3) {
            this.mButtonNeutralText = charSequence;
            this.mButtonNeutralMessage = message;
        } else if (i == -2) {
            this.mButtonNegativeText = charSequence;
            this.mButtonNegativeMessage = message;
        } else if (i == -1) {
            this.mButtonPositiveText = charSequence;
            this.mButtonPositiveMessage = message;
        } else {
            throw new IllegalStateException("Button does not exist");
        }
    }

    public void setCheckBox(boolean z, CharSequence charSequence) {
        this.mIsChecked = z;
        this.mCheckBoxMessage = charSequence;
    }

    public void setCheckedItem(int i) {
        this.mCheckedItem = i;
    }

    public void setCheckedItems(boolean[] zArr) {
        this.mCheckedItems = zArr;
    }

    public void setCustomTitle(View view) {
        this.mCustomTitleView = view;
    }

    public void setIcon(int i) {
        this.mIconId = i;
        this.mIcon = null;
    }

    public void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        this.mIconId = 0;
    }

    public void setListView(ListView listView) {
        this.mListView = listView;
    }

    public void setMessage(CharSequence charSequence) {
        this.mMessage = charSequence;
        TextView textView = this.mMessageView;
        if (textView != null) {
            textView.setText(charSequence);
            centerIfSingleLine(this.mMessageView, charSequence);
        }
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void setView(View view) {
        this.mView = view;
    }
}
