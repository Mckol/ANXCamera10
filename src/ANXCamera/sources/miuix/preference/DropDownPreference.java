package miuix.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import miui.external.adapter.SpinnerCheckableArrayAdapter;
import miui.external.adapter.SpinnerDoubleLineContentAdapter;
import miui.external.widget.Spinner;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;

public class DropDownPreference extends Preference {
    private static final Class<?>[] ADAPTER_CONSTRUCTOR_SIGNATURE = {Context.class, AttributeSet.class};
    private static final CharSequence[] EMPTY = new CharSequence[0];
    private static final String TAG = "DropDownPreference";
    private ArrayAdapter mAdapter;
    private ArrayAdapter mContentAdapter;
    private CharSequence[] mEntries;
    private Drawable[] mEntryIcons;
    private CharSequence[] mEntryValues;
    private final AdapterView.OnItemSelectedListener mItemSelectedListener;
    private Handler mNotifyHandler;
    private Spinner mSpinner;
    private String mValue;
    private boolean mValueSet;

    /* access modifiers changed from: private */
    public static class DropDownLayoutAdapter extends SpinnerDoubleLineContentAdapter {
        private CharSequence[] mValues;

        DropDownLayoutAdapter(Context context, AttributeSet attributeSet, int i, int i2) {
            super(context, 0);
            int[] iArr;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DropDownPreference, i, i2);
            this.mEntries = TypedArrayUtils.getTextArray(obtainStyledAttributes, R.styleable.DropDownPreference_entries, 0);
            this.mValues = TypedArrayUtils.getTextArray(obtainStyledAttributes, R.styleable.DropDownPreference_entryValues, 0);
            this.mSummaries = TypedArrayUtils.getTextArray(obtainStyledAttributes, R.styleable.DropDownPreference_entrySummaries, 0);
            int resourceId = obtainStyledAttributes.getResourceId(R.styleable.DropDownPreference_entryIcons, -1);
            obtainStyledAttributes.recycle();
            if (resourceId > 0) {
                TypedArray obtainTypedArray = context.getResources().obtainTypedArray(resourceId);
                iArr = new int[obtainTypedArray.length()];
                for (int i3 = 0; i3 < obtainTypedArray.length(); i3++) {
                    iArr[i3] = obtainTypedArray.getResourceId(i3, 0);
                }
                obtainTypedArray.recycle();
            } else {
                iArr = null;
            }
            setEntryIcons(iArr);
        }

        public CharSequence[] getEntryValues() {
            return this.mValues;
        }

        public void setEntryValues(CharSequence[] charSequenceArr) {
            this.mValues = charSequenceArr;
        }
    }

    /* access modifiers changed from: private */
    public static class PreferenceCheckedProvider implements SpinnerCheckableArrayAdapter.CheckedStateProvider {
        private ArrayAdapter mAdapter;
        private DropDownPreference mPreference;

        public PreferenceCheckedProvider(DropDownPreference dropDownPreference, ArrayAdapter arrayAdapter) {
            this.mPreference = dropDownPreference;
            this.mAdapter = arrayAdapter;
        }

        @Override // miui.external.adapter.SpinnerCheckableArrayAdapter.CheckedStateProvider
        public boolean isChecked(int i) {
            return TextUtils.equals(this.mPreference.getValue(), this.mPreference.mEntryValues[i]);
        }
    }

    /* access modifiers changed from: private */
    public static class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            /* class miuix.preference.DropDownPreference.SavedState.AnonymousClass1 */

            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        String mValue;

        SavedState(Parcel parcel) {
            super(parcel);
            this.mValue = parcel.readString();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(@NonNull Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.mValue);
        }
    }

    public DropDownPreference(Context context) {
        this(context, null);
    }

    public DropDownPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.dropdownPreferenceStyle);
    }

    public DropDownPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DropDownPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mNotifyHandler = new Handler();
        this.mItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            /* class miuix.preference.DropDownPreference.AnonymousClass1 */

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (i >= 0) {
                    final String str = (String) DropDownPreference.this.mEntryValues[i];
                    DropDownPreference.this.mNotifyHandler.post(new Runnable() {
                        /* class miuix.preference.DropDownPreference.AnonymousClass1.AnonymousClass1 */

                        public void run() {
                            if (!str.equals(DropDownPreference.this.getValue()) && DropDownPreference.this.callChangeListener(str)) {
                                DropDownPreference.this.setValue(str);
                            }
                        }
                    });
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DropDownPreference, i, i2);
        String string = obtainStyledAttributes.getString(R.styleable.DropDownPreference_adapter);
        obtainStyledAttributes.recycle();
        if (!TextUtils.isEmpty(string)) {
            this.mContentAdapter = initAdapter(context, attributeSet, string);
        } else {
            this.mContentAdapter = new DropDownLayoutAdapter(context, attributeSet, i, i2);
        }
        this.mAdapter = createAdapter();
        constructEntries();
    }

    private void constructEntries() {
        ArrayAdapter arrayAdapter = this.mContentAdapter;
        if (arrayAdapter instanceof DropDownLayoutAdapter) {
            this.mEntries = ((DropDownLayoutAdapter) arrayAdapter).getEntries();
            this.mEntryValues = ((DropDownLayoutAdapter) this.mContentAdapter).getEntryValues();
            this.mEntryIcons = ((DropDownLayoutAdapter) this.mContentAdapter).getEntryIcons();
            return;
        }
        int count = arrayAdapter.getCount();
        this.mEntries = new CharSequence[this.mContentAdapter.getCount()];
        for (int i = 0; i < count; i++) {
            this.mEntries[i] = this.mContentAdapter.getItem(i).toString();
        }
        this.mEntryValues = this.mEntries;
        this.mEntryIcons = null;
    }

    private void disableSpinnerClick(Spinner spinner) {
        spinner.setClickable(false);
        spinner.setLongClickable(false);
        spinner.setContextClickable(false);
    }

    private int findSpinnerIndexOfValue(String str) {
        int i = 0;
        while (true) {
            CharSequence[] charSequenceArr = this.mEntryValues;
            if (i >= charSequenceArr.length) {
                return -1;
            }
            if (TextUtils.equals(charSequenceArr[i], str)) {
                return i;
            }
            i++;
        }
    }

    private ArrayAdapter initAdapter(Context context, AttributeSet attributeSet, String str) {
        try {
            Constructor<? extends U> constructor = context.getClassLoader().loadClass(str).asSubclass(ArrayAdapter.class).getConstructor(ADAPTER_CONSTRUCTOR_SIGNATURE);
            Object[] objArr = {context, attributeSet};
            constructor.setAccessible(true);
            return (ArrayAdapter) constructor.newInstance(objArr);
        } catch (NoSuchMethodException e2) {
            throw new IllegalStateException("Error creating Adapter " + str, e2);
        } catch (InstantiationException | InvocationTargetException e3) {
            throw new IllegalStateException("Could not instantiate the Adapter: " + str, e3);
        } catch (IllegalAccessException e4) {
            throw new IllegalStateException("Can't access non-public constructor " + str, e4);
        } catch (ClassNotFoundException e5) {
            throw new IllegalStateException("Can't find Adapter: " + str, e5);
        }
    }

    /* access modifiers changed from: package-private */
    public ArrayAdapter createAdapter() {
        Context context = getContext();
        ArrayAdapter arrayAdapter = this.mContentAdapter;
        return new SpinnerCheckableArrayAdapter(context, arrayAdapter, new PreferenceCheckedProvider(this, arrayAdapter));
    }

    public int findIndexOfValue(String str) {
        return findSpinnerIndexOfValue(str);
    }

    public CharSequence[] getEntries() {
        return this.mEntries;
    }

    public Drawable[] getEntryIcons() {
        return this.mEntryIcons;
    }

    public CharSequence[] getEntryValues() {
        ArrayAdapter arrayAdapter = this.mContentAdapter;
        return arrayAdapter instanceof DropDownLayoutAdapter ? ((DropDownLayoutAdapter) arrayAdapter).getEntryValues() : EMPTY;
    }

    public CharSequence[] getSummaries() {
        ArrayAdapter arrayAdapter = this.mContentAdapter;
        if (arrayAdapter instanceof DropDownLayoutAdapter) {
            ((DropDownLayoutAdapter) arrayAdapter).getSummaries();
        }
        return EMPTY;
    }

    public String getValue() {
        return this.mValue;
    }

    public int getValueIndex() {
        return findIndexOfValue(this.mValue);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.preference.Preference
    public void notifyChanged() {
        super.notifyChanged();
        if (this.mAdapter != null) {
            this.mNotifyHandler.post(new Runnable() {
                /* class miuix.preference.DropDownPreference.AnonymousClass2 */

                public void run() {
                    DropDownPreference.this.mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(final PreferenceViewHolder preferenceViewHolder) {
        if (this.mAdapter.getCount() > 0) {
            this.mSpinner = (Spinner) preferenceViewHolder.itemView.findViewById(R.id.spinner);
            disableSpinnerClick(this.mSpinner);
            this.mSpinner.setAdapter((SpinnerAdapter) this.mAdapter);
            this.mSpinner.setOnItemSelectedListener(null);
            this.mSpinner.setSelection(findSpinnerIndexOfValue(getValue()));
            this.mSpinner.post(new Runnable() {
                /* class miuix.preference.DropDownPreference.AnonymousClass3 */

                public void run() {
                    DropDownPreference.this.mSpinner.setOnItemSelectedListener(DropDownPreference.this.mItemSelectedListener);
                }
            });
            this.mSpinner.setOnSpinnerDismissListener(new Spinner.OnSpinnerDismissListener() {
                /* class miuix.preference.DropDownPreference.AnonymousClass4 */

                @Override // miui.external.widget.Spinner.OnSpinnerDismissListener
                public void onSpinnerDismiss() {
                    Folme.useAt(preferenceViewHolder.itemView).touch().touchUp(new AnimConfig[0]);
                }
            });
            preferenceViewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
                /* class miuix.preference.DropDownPreference.AnonymousClass5 */

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        Folme.useAt(view).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).touchDown(new AnimConfig[0]);
                    } else if (action == 1) {
                        DropDownPreference.this.mSpinner.performClick(motionEvent.getX(), motionEvent.getY());
                    } else if (action == 3) {
                        Folme.useAt(view).touch().touchUp(new AnimConfig[0]);
                    }
                    return true;
                }
            });
        }
        super.onBindViewHolder(preferenceViewHolder);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.preference.Preference
    public Object onGetDefaultValue(TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.preference.Preference
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setValue(savedState.mValue);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.preference.Preference
    public Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        SavedState savedState = new SavedState(onSaveInstanceState);
        savedState.mValue = getValue();
        return savedState;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.preference.Preference
    public void onSetInitialValue(boolean z, Object obj) {
        setValue(getPersistedString((String) obj));
    }

    /* access modifiers changed from: protected */
    @Override // androidx.preference.Preference
    public void performClick(View view) {
        Spinner spinner = this.mSpinner;
        if (spinner != null) {
            spinner.performClick();
            Log.d(TAG, "trigger from perform click");
        }
    }

    public void setAdapter(ArrayAdapter arrayAdapter) {
        this.mContentAdapter = arrayAdapter;
        this.mAdapter = createAdapter();
        constructEntries();
    }

    public void setEntries(@ArrayRes int i) {
        setEntries(getContext().getResources().getTextArray(i));
    }

    public void setEntries(CharSequence[] charSequenceArr) {
        this.mEntries = charSequenceArr;
        ArrayAdapter arrayAdapter = this.mContentAdapter;
        if (arrayAdapter instanceof DropDownLayoutAdapter) {
            ((DropDownLayoutAdapter) arrayAdapter).setEntries(this.mEntries);
        } else {
            arrayAdapter.clear();
            this.mContentAdapter.addAll(charSequenceArr);
            this.mEntryValues = this.mEntries;
        }
        notifyChanged();
    }

    public void setEntryIcons(int[] iArr) {
        ArrayAdapter arrayAdapter = this.mContentAdapter;
        if (arrayAdapter instanceof DropDownLayoutAdapter) {
            ((DropDownLayoutAdapter) arrayAdapter).setEntryIcons(iArr);
            this.mEntryIcons = ((DropDownLayoutAdapter) this.mContentAdapter).getEntryIcons();
        }
        notifyChanged();
    }

    public void setEntryIcons(Drawable[] drawableArr) {
        ArrayAdapter arrayAdapter = this.mContentAdapter;
        if (arrayAdapter instanceof DropDownLayoutAdapter) {
            ((DropDownLayoutAdapter) arrayAdapter).setEntryIcons(drawableArr);
            this.mEntryIcons = ((DropDownLayoutAdapter) this.mContentAdapter).getEntryIcons();
        }
        notifyChanged();
    }

    public void setEntryValues(@ArrayRes int i) {
        setEntryValues(getContext().getResources().getTextArray(i));
    }

    public void setEntryValues(CharSequence[] charSequenceArr) {
        ArrayAdapter arrayAdapter = this.mContentAdapter;
        if (arrayAdapter instanceof DropDownLayoutAdapter) {
            ((DropDownLayoutAdapter) arrayAdapter).setEntryValues(charSequenceArr);
            this.mAdapter.notifyDataSetChanged();
            this.mEntryValues = charSequenceArr;
        }
    }

    public void setSummaries(CharSequence[] charSequenceArr) {
        ArrayAdapter arrayAdapter = this.mContentAdapter;
        if (arrayAdapter instanceof DropDownLayoutAdapter) {
            ((DropDownLayoutAdapter) arrayAdapter).setSummaries(charSequenceArr);
            notifyChanged();
        }
    }

    public void setValue(String str) {
        boolean z = !TextUtils.equals(this.mValue, str);
        if (z || !this.mValueSet) {
            this.mValue = str;
            this.mValueSet = true;
            persistString(str);
            if (z) {
                notifyChanged();
            }
        }
    }

    public void setValueIndex(int i) {
        setValue(this.mEntryValues[i].toString());
        Spinner spinner = this.mSpinner;
        if (spinner != null) {
            spinner.setSelection(i);
        }
    }
}
