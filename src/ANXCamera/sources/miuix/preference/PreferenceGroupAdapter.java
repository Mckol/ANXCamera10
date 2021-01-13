package miuix.preference;

import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.ViewUtils;
import androidx.preference.DialogPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.TwoStatePreference;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import miui.external.graphics.TaggingDrawable;
import miui.util.AttributeResolver;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;

/* access modifiers changed from: package-private */
public class PreferenceGroupAdapter extends androidx.preference.PreferenceGroupAdapter {
    private static final int[] STATES_TAGS = {16842915, 16842916, 16842917, 16842918, R.attr.state_no_title};
    private static final int[] STATE_SET_FIRST = {16842916};
    private static final int[] STATE_SET_LAST = {16842918};
    private static final int[] STATE_SET_MIDDLE = {16842917};
    private static final int[] STATE_SET_NO_TITLE = {R.attr.state_no_title};
    private static final int[] STATE_SET_SINGLE = {16842915};
    static final int TYPE_FIRST = 2;
    static final int TYPE_LAST = 4;
    static final int TYPE_MIDDLE = 3;
    static final int TYPE_SINGLE = 1;
    private int mCheckableFilterColorChecked;
    private int mCheckableFilterColorNormal;
    private int mCheckablePaddingStartExtra;
    private PositionDescriptor[] mDescriptors = new PositionDescriptor[getItemCount()];
    private RecyclerView.AdapterDataObserver mObserver = new RecyclerView.AdapterDataObserver() {
        /* class miuix.preference.PreferenceGroupAdapter.AnonymousClass1 */

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            super.onChanged();
            PreferenceGroupAdapter preferenceGroupAdapter = PreferenceGroupAdapter.this;
            preferenceGroupAdapter.mDescriptors = new PositionDescriptor[preferenceGroupAdapter.getItemCount()];
        }
    };
    private RecyclerView mRecyclerView;

    /* access modifiers changed from: package-private */
    public class PositionDescriptor {
        int[] status;
        int type;

        PositionDescriptor() {
        }
    }

    static {
        Arrays.sort(STATES_TAGS);
    }

    public PreferenceGroupAdapter(PreferenceGroup preferenceGroup) {
        super(preferenceGroup);
        this.mCheckablePaddingStartExtra = preferenceGroup.getContext().getResources().getDimensionPixelSize(R.dimen.miuix_preference_item_padding_start);
        this.mCheckableFilterColorChecked = AttributeResolver.resolveColor(preferenceGroup.getContext(), R.attr.checkablePreferenceItemColorFilterChecked);
        this.mCheckableFilterColorNormal = AttributeResolver.resolveColor(preferenceGroup.getContext(), R.attr.checkablePreferenceItemColorFilterNormal);
    }

    private boolean ableToUseFolmeAnim(Preference preference) {
        return !(preference instanceof PreferenceCategory) && !(preference instanceof DropDownPreference) && (!(preference instanceof FolmeAnimationController) || ((FolmeAnimationController) preference).isTouchAnimationEnable());
    }

    private List<Preference> getAllVisiblePreferences(PreferenceGroup preferenceGroup) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < preferenceGroup.getPreferenceCount(); i++) {
            Preference preference = preferenceGroup.getPreference(i);
            if (preference.isVisible()) {
                arrayList.add(preference);
            }
        }
        return arrayList;
    }

    private void getPreferenceDescriptor(Preference preference, int i) {
        int[] iArr;
        PreferenceGroup parent;
        int[] iArr2;
        if (i >= 0) {
            PositionDescriptor[] positionDescriptorArr = this.mDescriptors;
            if (i < positionDescriptorArr.length) {
                if (positionDescriptorArr[i] == null) {
                    positionDescriptorArr[i] = new PositionDescriptor();
                }
                iArr = this.mDescriptors[i].status;
                if (iArr == null && (parent = preference.getParent()) != null) {
                    List<Preference> allVisiblePreferences = getAllVisiblePreferences(parent);
                    if (!allVisiblePreferences.isEmpty()) {
                        int i2 = 1;
                        if (allVisiblePreferences.size() == 1) {
                            iArr2 = STATE_SET_SINGLE;
                        } else if (preference.compareTo(allVisiblePreferences.get(0)) == 0) {
                            iArr2 = STATE_SET_FIRST;
                            i2 = 2;
                        } else if (preference.compareTo(allVisiblePreferences.get(allVisiblePreferences.size() - 1)) == 0) {
                            iArr2 = STATE_SET_LAST;
                            i2 = 4;
                        } else {
                            iArr2 = STATE_SET_MIDDLE;
                            i2 = 3;
                        }
                        if ((preference instanceof PreferenceCategory) && TextUtils.isEmpty(((PreferenceCategory) preference).getTitle())) {
                            int[] iArr3 = STATE_SET_NO_TITLE;
                            int[] iArr4 = new int[(iArr3.length + iArr2.length)];
                            System.arraycopy(iArr3, 0, iArr4, 0, iArr3.length);
                            System.arraycopy(iArr2, 0, iArr4, STATE_SET_NO_TITLE.length, iArr2.length);
                            iArr2 = iArr4;
                        }
                        PositionDescriptor[] positionDescriptorArr2 = this.mDescriptors;
                        positionDescriptorArr2[i].status = iArr2;
                        positionDescriptorArr2[i].type = i2;
                        return;
                    }
                    return;
                }
            }
        }
        iArr = null;
        if (iArr == null) {
        }
    }

    private boolean isArrowRightVisible(Preference preference) {
        return (preference.getIntent() == null && preference.getFragment() == null && (preference.getOnPreferenceClickListener() == null || (preference instanceof TwoStatePreference)) && !(preference instanceof DialogPreference)) ? false : true;
    }

    /* access modifiers changed from: package-private */
    public int getPositionType(int i) {
        return this.mDescriptors[i].type;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        registerAdapterDataObserver(this.mObserver);
        this.mRecyclerView = recyclerView;
    }

    @Override // androidx.preference.PreferenceGroupAdapter
    public void onBindViewHolder(@NonNull PreferenceViewHolder preferenceViewHolder, int i) {
        super.onBindViewHolder(preferenceViewHolder, i);
        Preference item = getItem(i);
        getPreferenceDescriptor(item, i);
        int[] iArr = this.mDescriptors[i].status;
        Drawable background = preferenceViewHolder.itemView.getBackground();
        if ((background instanceof StateListDrawable) && TaggingDrawable.containsTagState((StateListDrawable) background, STATES_TAGS)) {
            TaggingDrawable taggingDrawable = new TaggingDrawable(background);
            preferenceViewHolder.itemView.setBackground(taggingDrawable);
            background = taggingDrawable;
        }
        if (background instanceof TaggingDrawable) {
            TaggingDrawable taggingDrawable2 = (TaggingDrawable) background;
            if (iArr != null) {
                taggingDrawable2.setTaggingState(iArr);
            }
            Rect rect = new Rect();
            if (taggingDrawable2.getPadding(rect)) {
                if (item.getParent() instanceof RadioSetPreferenceCategory) {
                    taggingDrawable2.setColorFilter(((RadioSetPreferenceCategory) item.getParent()).isChecked() ? this.mCheckableFilterColorChecked : this.mCheckableFilterColorNormal, PorterDuff.Mode.SRC_OVER);
                    RecyclerView recyclerView = this.mRecyclerView;
                    if (recyclerView != null) {
                        if (ViewUtils.isLayoutRtl(recyclerView)) {
                            rect.right += this.mCheckablePaddingStartExtra;
                        } else {
                            rect.left += this.mCheckablePaddingStartExtra;
                        }
                    }
                } else {
                    taggingDrawable2.setColorFilter(null);
                }
                preferenceViewHolder.itemView.setPadding(rect.left, rect.top, rect.right, rect.bottom);
            }
            if ((item instanceof RadioButtonPreference) && ((RadioButtonPreference) item).isChecked()) {
                taggingDrawable2.setTaggingState(new int[]{16842912});
            }
        }
        View findViewById = preferenceViewHolder.itemView.findViewById(R.id.arrow_right);
        if (findViewById != null) {
            findViewById.setVisibility(isArrowRightVisible(item) ? 0 : 8);
        }
        if (ableToUseFolmeAnim(item)) {
            Folme.useAt(preferenceViewHolder.itemView).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).handleTouchOf(preferenceViewHolder.itemView, new AnimConfig[0]);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        unregisterAdapterDataObserver(this.mObserver);
        this.mRecyclerView = null;
    }

    @Override // androidx.preference.PreferenceGroupAdapter, androidx.preference.Preference.OnPreferenceChangeInternalListener
    public void onPreferenceChange(Preference preference) {
        Preference findPreference;
        super.onPreferenceChange(preference);
        String dependency = preference.getDependency();
        if (!TextUtils.isEmpty(dependency) && (findPreference = preference.getPreferenceManager().findPreference(dependency)) != null) {
            if (!(preference instanceof PreferenceCategory)) {
                preference.setVisible(preference.isEnabled());
            } else if (findPreference instanceof TwoStatePreference) {
                preference.setVisible(((TwoStatePreference) findPreference).isChecked());
            } else {
                preference.setVisible(findPreference.isEnabled());
            }
        }
    }
}
