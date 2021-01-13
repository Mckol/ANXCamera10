package miui.widget;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.WrapperListAdapter;
import androidx.core.view.GravityCompat;
import com.miui.internal.R;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import miui.animation.Folme;
import miui.animation.IVisibleStyle;
import miui.animation.base.AnimConfig;
import miui.animation.controller.AnimState;
import miui.animation.listener.TransitionListener;
import miui.animation.property.FloatProperty;
import miui.animation.property.ViewProperty;
import miui.animation.utils.EaseManager;
import miui.util.HapticFeedbackUtil;
import miui.util.ViewUtils;
import miui.view.MiuiHapticFeedbackConstants;

public class AlphabetFastIndexer extends LinearLayout {
    private static final int ANIM_DURATION = 300;
    private static final String ANIM_LABEL_END = "end";
    private static final String ANIM_LABEL_START = "start";
    private static final int FADE_DELAYED = 200;
    private static final int MSG_FADE = 1;
    private static final String STARRED_LABEL = "♥";
    public static final String STARRED_TITLE = "!";
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_NONE = 0;
    private final float DAMPING;
    private final float DAMPING_HIDE;
    private final int FINGER_STATE_HIDE;
    private final int FINGER_STATE_INIT;
    private final int FINGER_STATE_MOVE;
    private final int FINGER_STATE_SHOW;
    private final float HEIGHT_FACTOR;
    private final int INVALID_INDEX;
    private final float RESPONSE;
    private final float RESPONSE_HIDE;
    private final float SCALE;
    private final float SCALE_START;
    private final int TRANSLATION_X;
    private AnimConfig mAnimConfig;
    private ArgbEvaluator mArgbEvaluator;
    private boolean mDrawOverlay;
    private Handler mHandler;
    private int mIndexerMinWidth;
    private AnimConfig mItemAnimConfig;
    private int mItemHeight;
    private int mItemMargin;
    private int mLastAlphabetIndex;
    private int mListScrollState;
    private AdapterView<?> mListView;
    private TextView mOverlay;
    private Drawable mOverlayBackground;
    private int mOverlayHeight;
    private AnimConfig mOverlayHideAnimConfig;
    private int mOverlayRightMargin;
    private int mOverlayTextColor;
    private int mOverlayTextSize;
    private int mOverlayWidth;
    HashMap<Object, Integer> mSectionMap;
    private boolean mShowAnimBegin;
    private int mState;
    private TextHilighter mTextHilighter;
    private int mVerticalPosition;

    /* access modifiers changed from: private */
    public class ItemAnimaState {
        boolean highLight;
        int middleY;
        float scale;
        float translationX;
        TextView view;

        private ItemAnimaState() {
        }
    }

    private static class OnScrollerDecorator implements AbsListView.OnScrollListener {
        private final WeakReference<AlphabetFastIndexer> mIndexerRef;
        private final AbsListView.OnScrollListener mListener;

        public OnScrollerDecorator(AlphabetFastIndexer alphabetFastIndexer, AbsListView.OnScrollListener onScrollListener) {
            this.mIndexerRef = new WeakReference<>(alphabetFastIndexer);
            this.mListener = onScrollListener;
        }

        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            AlphabetFastIndexer alphabetFastIndexer = this.mIndexerRef.get();
            if (alphabetFastIndexer != null) {
                alphabetFastIndexer.refreshMask();
            }
            AbsListView.OnScrollListener onScrollListener = this.mListener;
            if (onScrollListener != null) {
                onScrollListener.onScroll(absListView, i, i2, i3);
            }
        }

        public void onScrollStateChanged(AbsListView absListView, int i) {
            AlphabetFastIndexer alphabetFastIndexer = this.mIndexerRef.get();
            if (alphabetFastIndexer != null) {
                alphabetFastIndexer.mListScrollState = i;
            }
            AbsListView.OnScrollListener onScrollListener = this.mListener;
            if (onScrollListener != null) {
                onScrollListener.onScrollStateChanged(absListView, i);
            }
        }
    }

    /* access modifiers changed from: private */
    public static class TextHilighter {
        int mActivatedColor;
        int mHilightColor;
        int mIndexerTextSize;
        String[] mIndexes;
        int mNormalColor;

        TextHilighter(Context context, TypedArray typedArray) {
            Resources resources = context.getResources();
            CharSequence[] textArray = typedArray.getTextArray(R.styleable.AlphabetFastIndexer_indexerTable);
            if (textArray != null) {
                this.mIndexes = new String[textArray.length];
                int length = textArray.length;
                int i = 0;
                int i2 = 0;
                while (i < length) {
                    this.mIndexes[i2] = textArray[i].toString();
                    i++;
                    i2++;
                }
            } else {
                this.mIndexes = resources.getStringArray(R.array.alphabet_table);
            }
            String[] strArr = this.mIndexes;
            if (strArr.length > 1) {
                String str = strArr[strArr.length - 1];
            }
            this.mNormalColor = typedArray.getColor(R.styleable.AlphabetFastIndexer_indexerTextColor, resources.getColor(R.color.alphabet_indexer_text_color));
            this.mActivatedColor = typedArray.getColor(R.styleable.AlphabetFastIndexer_indexerTextActivatedColor, resources.getColor(R.color.alphabet_indexer_activated_text_color));
            this.mHilightColor = typedArray.getColor(R.styleable.AlphabetFastIndexer_indexerTextHighlightColor, resources.getColor(R.color.alphabet_indexer_highlight_text_color));
            this.mIndexerTextSize = typedArray.getDimensionPixelSize(R.styleable.AlphabetFastIndexer_indexerTextSize, resources.getDimensionPixelSize(R.dimen.alphabet_indexer_text_size));
        }
    }

    public AlphabetFastIndexer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AlphabetFastIndexer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mState = 0;
        this.DAMPING = 0.8f;
        this.RESPONSE = 0.55f;
        this.DAMPING_HIDE = 0.99f;
        this.RESPONSE_HIDE = 0.8f;
        this.FINGER_STATE_INIT = 1;
        this.FINGER_STATE_SHOW = 2;
        this.FINGER_STATE_MOVE = 3;
        this.FINGER_STATE_HIDE = 4;
        this.SCALE = 1.3f;
        this.SCALE_START = 1.0f;
        this.HEIGHT_FACTOR = 3.0f;
        this.INVALID_INDEX = -1;
        this.mSectionMap = new HashMap<>();
        this.mListScrollState = 0;
        this.mHandler = new Handler() {
            /* class miui.widget.AlphabetFastIndexer.AnonymousClass5 */

            public void handleMessage(Message message) {
                if (message.what == 1) {
                    AlphabetFastIndexer.this.hideOverlay();
                }
            }
        };
        Resources resources = context.getResources();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AlphabetFastIndexer, 0, attributeSet.getStyleAttribute() != 0 ? attributeSet.getStyleAttribute() : i);
        this.mTextHilighter = new TextHilighter(context, obtainStyledAttributes);
        this.mDrawOverlay = obtainStyledAttributes.getBoolean(R.styleable.AlphabetFastIndexer_drawOverlay, true);
        if (this.mDrawOverlay) {
            int dimensionPixelOffset = resources.getDimensionPixelOffset(R.dimen.alphabet_indexer_overlay_offset);
            this.mOverlayRightMargin = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.AlphabetFastIndexer_overlayMarginRight, resources.getDimensionPixelOffset(R.dimen.alphabet_indexer_overlay_offset));
            if (this.mOverlayRightMargin < dimensionPixelOffset) {
                this.mOverlayRightMargin = dimensionPixelOffset;
            }
            this.mOverlayTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AlphabetFastIndexer_overlayTextSize, resources.getDimensionPixelSize(R.dimen.alphabet_indexer_overlay_text_size));
            this.mOverlayTextColor = obtainStyledAttributes.getColor(R.styleable.AlphabetFastIndexer_overlayTextColor, resources.getColor(R.color.alphabet_indexer_overlay_text_color));
            this.mOverlayBackground = resources.getDrawable(R.drawable.alphabet_indexer_overlay);
            this.mItemHeight = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.AlphabetFastIndexer_itemHeight, resources.getDimensionPixelOffset(R.dimen.alphabet_indexer_item_height));
            this.mItemMargin = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.AlphabetFastIndexer_itemMargin, resources.getDimensionPixelOffset(R.dimen.alphabet_indexer_item_margin));
            this.mOverlayWidth = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.AlphabetFastIndexer_overlayWidth, resources.getDimensionPixelOffset(R.dimen.alphabet_overlay_width));
            this.mOverlayHeight = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.AlphabetFastIndexer_overlayHeight, resources.getDimensionPixelOffset(R.dimen.alphabet_overlay_height));
            this.mIndexerMinWidth = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.AlphabetFastIndexer_indexerMinWidth, resources.getDimensionPixelOffset(R.dimen.alphabet_indexer_min_width));
        }
        obtainStyledAttributes.recycle();
        this.mVerticalPosition = GravityCompat.END;
        setGravity(1);
        setOrientation(1);
        this.TRANSLATION_X = 50;
        initAnimConfig();
        init();
        setMinimumWidth(this.mIndexerMinWidth);
        post(new Runnable() {
            /* class miui.widget.AlphabetFastIndexer.AnonymousClass1 */

            public void run() {
                AlphabetFastIndexer.this.checkItemHeight();
                int i = AlphabetFastIndexer.this.mOverlayHeight / 2;
                int paddingTop = AlphabetFastIndexer.this.getPaddingTop();
                if (paddingTop < i) {
                    paddingTop = i;
                }
                int paddingBottom = AlphabetFastIndexer.this.getPaddingBottom();
                if (paddingBottom >= i) {
                    i = paddingBottom;
                }
                AlphabetFastIndexer alphabetFastIndexer = AlphabetFastIndexer.this;
                alphabetFastIndexer.setPadding(alphabetFastIndexer.getPaddingLeft(), paddingTop, AlphabetFastIndexer.this.getPaddingRight(), i);
            }
        });
        addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            /* class miui.widget.AlphabetFastIndexer.AnonymousClass2 */

            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                AlphabetFastIndexer.this.checkItemHeight();
                AlphabetFastIndexer.this.removeOnLayoutChangeListener(this);
            }
        });
    }

    private void animateItem(int i, float f, int i2) {
        ItemAnimaState[] constructItemState = constructItemState(f, i2);
        executeAnimation(i, constructItemState);
        restoreState(i2);
        drawThumbInternal(constructItemState[0].view.getText(), (float) constructItemState[0].middleY);
    }

    private void animateItemHide(ItemAnimaState... itemAnimaStateArr) {
        AnimState generateFolmeAnimState = generateFolmeAnimState(ANIM_LABEL_END, 0.0f, 1.0f);
        for (ItemAnimaState itemAnimaState : itemAnimaStateArr) {
            if (itemAnimaState != null) {
                Folme.useAt(itemAnimaState.view).state().fromTo(generateFolmeAnimState("start", itemAnimaState.translationX, itemAnimaState.scale), generateFolmeAnimState, this.mItemAnimConfig);
                itemAnimaState.view.setTextColor(this.mTextHilighter.mNormalColor);
                if (itemAnimaState.highLight) {
                    itemAnimaState.view.setTextColor(this.mTextHilighter.mHilightColor);
                }
            }
        }
    }

    private void animateItemMove(ItemAnimaState... itemAnimaStateArr) {
        for (ItemAnimaState itemAnimaState : itemAnimaStateArr) {
            if (itemAnimaState != null) {
                Folme.useAt(itemAnimaState.view).state().fromTo(generateFolmeAnimState("start", itemAnimaState.view.getTranslationX(), itemAnimaState.view.getScaleX()), generateFolmeAnimState(ANIM_LABEL_END, itemAnimaState.translationX, itemAnimaState.scale), this.mItemAnimConfig);
            }
        }
    }

    private void animateItemShow(ItemAnimaState... itemAnimaStateArr) {
        AnimState generateFolmeAnimState = generateFolmeAnimState("start", 0.0f, 1.0f);
        for (ItemAnimaState itemAnimaState : itemAnimaStateArr) {
            if (itemAnimaState != null) {
                Folme.useAt(itemAnimaState.view).state().fromTo(generateFolmeAnimState, generateFolmeAnimState(ANIM_LABEL_END, itemAnimaState.translationX, itemAnimaState.scale), this.mItemAnimConfig);
            }
        }
    }

    private int calculateIndex(float f, int i) {
        View childAt = getChildAt(i);
        if (f > ((float) childAt.getBottom())) {
            for (int i2 = i + 1; i2 < getChildCount(); i2++) {
                if (f < ((float) getChildAt(i2).getBottom())) {
                    return i2;
                }
            }
        } else if (f < ((float) childAt.getTop())) {
            for (int i3 = i - 1; i3 > 0; i3--) {
                if (f > ((float) getChildAt(i3).getTop())) {
                    return i3;
                }
            }
        }
        return i;
    }

    private int calculateOverlayPosition(int i) {
        int normalizeIndex = normalizeIndex(i);
        View childAt = getChildAt(normalizeIndex);
        if (childAt == null) {
            return 0;
        }
        int top = (childAt.getTop() + childAt.getBottom()) / 2;
        return top <= 0 ? (int) (((((double) (normalizeIndex + 1)) + 0.5d) * ((double) this.mItemHeight)) + ((double) getPaddingTop())) : top;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void checkItemHeight() {
        int height = getChildAt(0).getHeight();
        if (height != 0 && height != this.mItemHeight) {
            this.mItemHeight = height;
        }
    }

    private ItemAnimaState[] constructItemState(float f, int i) {
        ItemAnimaState[] itemAnimaStateArr = new ItemAnimaState[5];
        itemAnimaStateArr[0] = setupItemState(f, i);
        itemAnimaStateArr[0].view.setTextColor(this.mTextHilighter.mHilightColor);
        itemAnimaStateArr[0].highLight = true;
        int i2 = i - 1;
        if (i2 >= 0) {
            itemAnimaStateArr[1] = setupItemState(f, i2);
            itemAnimaStateArr[1].view.setTextColor(this.mTextHilighter.mActivatedColor);
        }
        int i3 = i - 2;
        if (i3 >= 0) {
            itemAnimaStateArr[2] = setupItemState(f, i3);
            itemAnimaStateArr[2].view.setTextColor(this.mTextHilighter.mActivatedColor);
        }
        int i4 = i + 1;
        if (i4 < getChildCount()) {
            itemAnimaStateArr[3] = setupItemState(f, i4);
            itemAnimaStateArr[3].view.setTextColor(this.mTextHilighter.mActivatedColor);
        }
        int i5 = i + 2;
        if (i5 < getChildCount()) {
            itemAnimaStateArr[4] = setupItemState(f, i5);
            itemAnimaStateArr[4].view.setTextColor(this.mTextHilighter.mActivatedColor);
        }
        return itemAnimaStateArr;
    }

    private void drawThumbInternal(CharSequence charSequence, float f) {
        if (this.mListView != null && this.mOverlay != null) {
            if (TextUtils.equals(charSequence, STARRED_TITLE)) {
                charSequence = STARRED_LABEL;
            }
            TextView textView = this.mOverlay;
            textView.setTranslationY(f - ((float) (textView.getHeight() / 2)));
            this.mOverlay.setText(charSequence);
            this.mOverlay.setVisibility(0);
            showOverlay();
            if (!isPressed()) {
                stop(200);
            }
        }
    }

    private void executeAnimation(int i, ItemAnimaState... itemAnimaStateArr) {
        if (i == 1) {
            return;
        }
        if (i == 2) {
            animateItemShow(itemAnimaStateArr);
        } else if (i == 3) {
            animateItemMove(itemAnimaStateArr);
        } else if (i == 4) {
            animateItemHide(itemAnimaStateArr);
        }
    }

    private AnimState generateFolmeAnimState(String str, float f, float f2) {
        return new AnimState(str).add(ViewProperty.TRANSLATION_X, f, new long[0]).add(ViewProperty.SCALE_X, f2, new long[0]).add(ViewProperty.SCALE_Y, f2, new long[0]);
    }

    private int getIndex(String str) {
        int i = this.mLastAlphabetIndex;
        int i2 = 0;
        while (true) {
            String[] strArr = this.mTextHilighter.mIndexes;
            if (i2 >= strArr.length) {
                break;
            }
            if (TextUtils.equals(str, strArr[i2])) {
                i = i2;
            }
            i2++;
        }
        if (i == -1) {
            return 0;
        }
        return i;
    }

    private int getListOffset() {
        AdapterView<?> adapterView = this.mListView;
        if (adapterView instanceof ListView) {
            return ((ListView) adapterView).getHeaderViewsCount();
        }
        return 0;
    }

    private int getPosition(int i, SectionIndexer sectionIndexer) {
        Object[] sections = sectionIndexer.getSections();
        if (sections == null || (getHeight() - getPaddingTop()) - getPaddingBottom() <= 0 || i < 0) {
            return -1;
        }
        if (i >= this.mTextHilighter.mIndexes.length) {
            return sections.length;
        }
        this.mSectionMap.clear();
        for (int i2 = 0; i2 < sections.length; i2++) {
            this.mSectionMap.put(sections[i2].toString().toUpperCase(), Integer.valueOf(i2));
        }
        String[] strArr = this.mTextHilighter.mIndexes;
        int i3 = 0;
        while (true) {
            int i4 = i3 + i;
            if (i4 >= strArr.length && i < i3) {
                return 0;
            }
            int i5 = i - i3;
            if (i4 < strArr.length && this.mSectionMap.containsKey(strArr[i4])) {
                return this.mSectionMap.get(strArr[i4]).intValue();
            }
            if (i5 >= 0 && this.mSectionMap.containsKey(strArr[i5])) {
                return this.mSectionMap.get(strArr[i5]).intValue();
            }
            i3++;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001b  */
    /* JADX WARNING: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    private SectionIndexer getSectionIndexer() {
        boolean z;
        AdapterView<?> adapterView = this.mListView;
        if (adapterView == null) {
            return null;
        }
        Object adapter = adapterView.getAdapter();
        while (true) {
            z = adapter instanceof SectionIndexer;
            if (!z && (adapter instanceof WrapperListAdapter)) {
                adapter = ((WrapperListAdapter) adapter).getWrappedAdapter();
            } else if (!z) {
                return (SectionIndexer) adapter;
            } else {
                return null;
            }
        }
        if (!z) {
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void hideOverlay() {
        TextView textView = this.mOverlay;
        if (textView != null) {
            Folme.useAt(textView).visible().setScale(1.0f, IVisibleStyle.VisibleType.SHOW).setScale(0.0f, IVisibleStyle.VisibleType.HIDE).setAlpha(1.0f, IVisibleStyle.VisibleType.SHOW).setAlpha(0.0f, IVisibleStyle.VisibleType.HIDE).hide(this.mOverlayHideAnimConfig);
        }
    }

    private void init() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 1;
        layoutParams.weight = 1.0f;
        layoutParams.setMarginStart(this.mItemMargin);
        String[] strArr = this.mTextHilighter.mIndexes;
        for (String str : strArr) {
            TextView textView = new TextView(getContext());
            textView.setGravity(17);
            textView.setMinHeight(this.mItemHeight);
            textView.setTextColor(this.mTextHilighter.mNormalColor);
            textView.setTextSize(0, (float) this.mTextHilighter.mIndexerTextSize);
            if (TextUtils.equals(str, STARRED_TITLE)) {
                str = STARRED_LABEL;
            }
            textView.setText(str);
            addView(textView, layoutParams);
        }
    }

    private void initAnimConfig() {
        this.mArgbEvaluator = new ArgbEvaluator();
        this.mAnimConfig = new AnimConfig();
        this.mAnimConfig.setEase(EaseManager.getStyle(0, 300.0f, 0.8f, 0.55f));
        this.mAnimConfig.addListeners(new TransitionListener() {
            /* class miui.widget.AlphabetFastIndexer.AnonymousClass3 */

            @Override // miui.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                AlphabetFastIndexer.this.mShowAnimBegin = true;
            }

            @Override // miui.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                AlphabetFastIndexer.this.mShowAnimBegin = false;
            }

            @Override // miui.animation.listener.TransitionListener
            public void onUpdate(Object obj, FloatProperty floatProperty, float f, boolean z) {
                super.onUpdate(obj, floatProperty, f, z);
                AlphabetFastIndexer.this.updateOverlayTranslationX(f);
                AlphabetFastIndexer.this.stop(200);
            }
        });
        this.mItemAnimConfig = new AnimConfig();
        this.mItemAnimConfig.setEase(EaseManager.getStyle(0, 300.0f, 0.8f, 0.55f));
        this.mOverlayHideAnimConfig = new AnimConfig();
        this.mOverlayHideAnimConfig.setEase(EaseManager.getStyle(0, 300.0f, 0.99f, 0.8f));
        this.mOverlayHideAnimConfig.addListeners(new TransitionListener() {
            /* class miui.widget.AlphabetFastIndexer.AnonymousClass4 */

            @Override // miui.animation.listener.TransitionListener
            public void onUpdate(Object obj, FloatProperty floatProperty, float f, boolean z) {
                AlphabetFastIndexer.this.updateOverlayTranslationX(f);
                int i = AlphabetFastIndexer.this.mLastAlphabetIndex;
                if (i == -1) {
                    i = 0;
                }
                AlphabetFastIndexer.this.updateIndexer(i, f);
            }
        });
    }

    private int normalizeIndex(int i) {
        if (i < 0) {
            return 0;
        }
        return i >= getChildCount() ? getChildCount() - 1 : i;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void refreshMask() {
        SectionIndexer sectionIndexer;
        int i;
        int i2;
        if (this.mListView != null && (sectionIndexer = getSectionIndexer()) != null) {
            int sectionForPosition = sectionIndexer.getSectionForPosition(this.mListView.getFirstVisiblePosition() - getListOffset());
            if (sectionForPosition != -1) {
                String str = (String) sectionIndexer.getSections()[sectionForPosition];
                if (!TextUtils.isEmpty(str)) {
                    String upperCase = str.toUpperCase();
                    i = 0;
                    while (true) {
                        String[] strArr = this.mTextHilighter.mIndexes;
                        if (i >= strArr.length) {
                            break;
                        } else if (TextUtils.equals(upperCase, strArr[i])) {
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (i != -1 && (i2 = this.mLastAlphabetIndex) != i) {
                        int normalizeIndex = normalizeIndex(i);
                        this.mLastAlphabetIndex = normalizeIndex;
                        if (!isPressed()) {
                            animateItem(i2 != -1 ? 2 : 1, (float) calculateOverlayPosition(normalizeIndex), normalizeIndex);
                            return;
                        }
                        return;
                    }
                }
            }
            i = -1;
            if (i != -1) {
            }
        }
    }

    private void restoreState(int i) {
        int i2;
        int i3 = i > 2 ? i - 2 : i > 1 ? i - 1 : -1;
        if (i3 != -1) {
            restoreViewState(0, i3);
        }
        int i4 = i + 2;
        if (i4 < getChildCount()) {
            i2 = i4;
        } else {
            i2 = i + 1;
            if (i2 >= getChildCount()) {
                i2 = -1;
            }
        }
        if (i2 != -1) {
            restoreViewState(i2 + 1, getChildCount());
        }
    }

    private void restoreViewState(int i, int i2) {
        while (i < i2) {
            View childAt = getChildAt(i);
            if (childAt.getTranslationX() != 0.0f) {
                AnimState generateFolmeAnimState = generateFolmeAnimState("start", childAt.getTranslationX(), childAt.getScaleX());
                AnimState generateFolmeAnimState2 = generateFolmeAnimState(ANIM_LABEL_END, 0.0f, 1.0f);
                Folme.useAt(childAt).state().fromTo(generateFolmeAnimState, generateFolmeAnimState2, this.mItemAnimConfig);
            }
            ((TextView) childAt).setTextColor(this.mTextHilighter.mNormalColor);
            i++;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0080  */
    private void scrollTo(SectionIndexer sectionIndexer, int i) {
        int i2;
        int i3;
        float f;
        float f2;
        int i4;
        int count = this.mListView.getCount();
        int listOffset = getListOffset();
        float f3 = (1.0f / ((float) count)) / 8.0f;
        Object[] sections = sectionIndexer.getSections();
        if (sections == null || sections.length <= 1) {
            setListSelection(Math.round((float) (i * count)), listOffset);
            i2 = -1;
        } else {
            int length = sections.length;
            int i5 = i >= length ? length - 1 : i;
            int positionForSection = sectionIndexer.getPositionForSection(i5);
            int i6 = i5 + 1;
            int positionForSection2 = i5 < length + -1 ? sectionIndexer.getPositionForSection(i6) : count;
            int i7 = i5;
            int i8 = positionForSection;
            if (positionForSection2 == positionForSection) {
                while (true) {
                    if (i7 > 0) {
                        i7--;
                        i8 = sectionIndexer.getPositionForSection(i7);
                        if (i8 == positionForSection) {
                            if (i7 == 0) {
                                i2 = 0;
                                i7 = i5;
                                break;
                            }
                        } else {
                            break;
                        }
                    } else {
                        i2 = i5;
                        i7 = i2;
                        break;
                    }
                }
                i3 = i6 + 1;
                while (i3 < length && sectionIndexer.getPositionForSection(i3) == positionForSection2) {
                    i3++;
                    i6++;
                }
                float f4 = (float) length;
                f = ((float) i7) / f4;
                float f5 = ((float) i6) / f4;
                f2 = ((float) i) / f4;
                if (i7 != i5 || f2 - f >= f3) {
                    i8 += Math.round((((float) (positionForSection2 - i8)) * (f2 - f)) / (f5 - f));
                }
                i4 = count - 1;
                if (i8 > i4) {
                    i8 = i4;
                }
                setListSelection(i8, listOffset);
            }
            i2 = i7;
            i3 = i6 + 1;
            while (i3 < length) {
                i3++;
                i6++;
            }
            float f42 = (float) length;
            f = ((float) i7) / f42;
            float f52 = ((float) i6) / f42;
            f2 = ((float) i) / f42;
            i8 += Math.round((((float) (positionForSection2 - i8)) * (f2 - f)) / (f52 - f));
            i4 = count - 1;
            if (i8 > i4) {
            }
            setListSelection(i8, listOffset);
        }
        updateOverlay(i2, sections);
    }

    private void scrollToSelection(int i, float f, int i2, SectionIndexer sectionIndexer) {
        int position = getPosition(i2, sectionIndexer);
        if (position < 0) {
            this.mListView.setSelection(0);
        } else {
            scrollTo(sectionIndexer, position);
        }
        animateItem(i, f, i2);
    }

    private void setListSelection(int i, int i2) {
        AdapterView<?> adapterView = this.mListView;
        if (adapterView instanceof ExpandableListView) {
            ExpandableListView expandableListView = (ExpandableListView) adapterView;
            expandableListView.setSelectionFromTop(expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(i + i2)), 0);
        } else if (adapterView instanceof ListView) {
            ((ListView) adapterView).setSelectionFromTop(i + i2, 0);
        } else {
            adapterView.setSelection(i + i2);
        }
    }

    private ItemAnimaState setupItemState(float f, int i) {
        ItemAnimaState itemAnimaState = new ItemAnimaState();
        View childAt = getChildAt(i);
        childAt.setTag(itemAnimaState);
        itemAnimaState.view = (TextView) childAt;
        itemAnimaState.middleY = (childAt.getTop() + childAt.getBottom()) / 2;
        float pow = (float) Math.pow((double) (1.0f - Math.min(1.0f, Math.abs(f - ((float) itemAnimaState.middleY)) / (((float) this.mItemHeight) * 3.0f))), 2.0d);
        itemAnimaState.scale = (0.29999995f * pow) + 1.0f;
        itemAnimaState.translationX = (-pow) * ((float) this.TRANSLATION_X);
        if (ViewUtils.isLayoutRtl(this)) {
            itemAnimaState.translationX *= -1.0f;
        }
        return itemAnimaState;
    }

    private void showOverlay() {
        TextView textView = this.mOverlay;
        if (textView != null && !this.mShowAnimBegin) {
            Folme.useAt(textView).visible().setScale(0.0f, IVisibleStyle.VisibleType.HIDE).setScale(1.0f, IVisibleStyle.VisibleType.SHOW).setAlpha(0.0f, IVisibleStyle.VisibleType.HIDE).setAlpha(1.0f, IVisibleStyle.VisibleType.SHOW).show(this.mAnimConfig);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void stop(int i) {
        this.mState = 0;
        this.mHandler.removeMessages(1);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), i <= 0 ? 0 : (long) i);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateIndexer(int i, float f) {
        ItemAnimaState itemAnimaState;
        for (int i2 = -2; i2 <= 2; i2++) {
            View childAt = getChildAt(i2 + i);
            if (!(childAt == null || (itemAnimaState = (ItemAnimaState) childAt.getTag()) == null)) {
                TextView textView = (TextView) childAt;
                textView.setTranslationX(itemAnimaState.translationX * f);
                float max = Math.max(itemAnimaState.scale * f, 1.0f);
                textView.setScaleX(max);
                textView.setScaleY(max);
                int currentTextColor = textView.getCurrentTextColor();
                if (i2 == 0) {
                    textView.setTextColor(((Integer) this.mArgbEvaluator.evaluate(1.0f - f, Integer.valueOf(currentTextColor), Integer.valueOf(this.mTextHilighter.mHilightColor))).intValue());
                } else {
                    textView.setTextColor(((Integer) this.mArgbEvaluator.evaluate(1.0f - f, Integer.valueOf(currentTextColor), Integer.valueOf(this.mTextHilighter.mNormalColor))).intValue());
                }
            }
        }
    }

    private void updateOverlay(int i, Object[] objArr) {
        if (i >= 0 && objArr != null) {
            String obj = objArr[i].toString();
            if (!TextUtils.isEmpty(obj)) {
                CharSequence subSequence = obj.toUpperCase().subSequence(0, 1);
                TextView textView = this.mOverlay;
                if (textView != null && !TextUtils.equals(textView.getText(), subSequence) && HapticFeedbackUtil.isSupportLinearMotorVibrate(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_MESH_NORMAL)) {
                    performHapticFeedback(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_MESH_NORMAL);
                }
                drawThumbInternal(subSequence, (float) calculateOverlayPosition(i));
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateOverlayTranslationX(float f) {
        float width = ((float) this.mOverlay.getWidth()) * (1.0f - f);
        if (ViewUtils.isLayoutRtl(this)) {
            width *= -1.0f;
        }
        this.mOverlay.setTranslationX(width);
    }

    public void attatch(AdapterView<?> adapterView) {
        if (this.mListView != adapterView) {
            detach();
            if (adapterView != null) {
                this.mLastAlphabetIndex = -1;
                this.mListView = adapterView;
                Context context = getContext();
                FrameLayout frameLayout = (FrameLayout) getParent();
                if (this.mDrawOverlay) {
                    this.mOverlay = new TextView(context);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, this.mOverlayHeight, GravityCompat.END);
                    layoutParams.topMargin = ((FrameLayout.LayoutParams) getLayoutParams()).topMargin;
                    layoutParams.setMarginEnd(this.mOverlayRightMargin);
                    this.mOverlay.setMinWidth(this.mOverlayWidth);
                    this.mOverlay.setLayoutParams(layoutParams);
                    this.mOverlay.setPaddingRelative(0, 0, 30, 0);
                    this.mOverlay.setBackground(this.mOverlayBackground);
                    this.mOverlay.setGravity(17);
                    this.mOverlay.setTextSize(0, (float) this.mOverlayTextSize);
                    this.mOverlay.setTextColor(this.mOverlayTextColor);
                    this.mOverlay.setVisibility(8);
                    frameLayout.addView(this.mOverlay);
                }
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) getLayoutParams();
                layoutParams2.gravity = this.mVerticalPosition | 48;
                setLayoutParams(layoutParams2);
            }
        }
    }

    public AbsListView.OnScrollListener decorateScrollListener(AbsListView.OnScrollListener onScrollListener) {
        return new OnScrollerDecorator(this, onScrollListener);
    }

    public void detach() {
        if (this.mListView != null) {
            stop(0);
            FrameLayout frameLayout = (FrameLayout) getParent();
            TextView textView = this.mOverlay;
            if (textView != null) {
                frameLayout.removeView(textView);
            }
            setVisibility(8);
            this.mListView = null;
        }
    }

    public void drawThumb(CharSequence charSequence) {
        if (this.mState == 0 && this.mListScrollState == 2) {
            int normalizeIndex = normalizeIndex(getIndex(charSequence.toString().toUpperCase()));
            float calculateOverlayPosition = (float) calculateOverlayPosition(normalizeIndex);
            animateItem(3, calculateOverlayPosition, normalizeIndex);
            drawThumbInternal(this.mTextHilighter.mIndexes[normalizeIndex], calculateOverlayPosition);
        }
    }

    public int getIndexerIntrinsicWidth() {
        Drawable background = getBackground();
        if (background != null) {
            return background.getIntrinsicWidth();
        }
        return 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0044, code lost:
        if (r2 != 3) goto L_0x0065;
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mListView == null) {
            stop(0);
            return false;
        }
        SectionIndexer sectionIndexer = getSectionIndexer();
        if (sectionIndexer == null) {
            stop(0);
            return false;
        }
        int action = motionEvent.getAction() & 255;
        float y = motionEvent.getY() - ((float) getPaddingTop());
        if (y < 0.0f) {
            y = 0.0f;
        }
        int calculateIndex = calculateIndex(motionEvent.getY(), normalizeIndex((int) (y / ((float) this.mItemHeight))));
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    scrollToSelection(3, motionEvent.getY(), calculateIndex, sectionIndexer);
                }
            }
            setPressed(false);
            restoreState(calculateIndex);
            stop(200);
        } else {
            setPressed(true);
            scrollToSelection(2, motionEvent.getY(), calculateIndex, sectionIndexer);
        }
        return true;
    }

    public void setOverlayOffset(int i, int i2) {
    }

    public void setVerticalPosition(boolean z) {
        this.mVerticalPosition = z ? GravityCompat.END : GravityCompat.START;
    }
}
