package com.android.camera.customization;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import b.c.a.c;
import com.android.camera.R;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.log.Log;
import com.android.camera.statistic.MistatsWrapper;
import java.util.ArrayList;
import java.util.List;

public class PreferenceCustomSound extends Preference implements SoundPool.OnLoadCompleteListener, View.OnClickListener {
    private static final String TAG = "PrefCustomSound";
    private List<ShutterSound> mAvailableSounds;
    private View mLastItemView;
    private List<Integer> mPreviewSoundIds;
    private int mSelectedPosition;
    private SoundPool mSoundPool;
    private int mSoundToPlay = -1;

    public PreferenceCustomSound(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public PreferenceCustomSound(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public PreferenceCustomSound(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init();
    }

    private void bindItem(View view, int i) {
        ImageView imageView = (ImageView) view.findViewById(R.id.indicator);
        ShutterSound shutterSound = this.mAvailableSounds.get(i);
        ((ImageView) view.findViewById(R.id.thumbnail)).setImageResource(shutterSound.cover());
        imageView.setVisibility(i == this.mSelectedPosition ? 0 : 8);
        ((TextView) view.findViewById(R.id.item_text)).setText(shutterSound.title());
        view.setTag(Integer.valueOf(i));
        view.setOnClickListener(this);
        FolmeUtils.handleListItemTouch(view);
        if (i == this.mSelectedPosition) {
            this.mLastItemView = view;
        }
    }

    private void init() {
        this.mAvailableSounds = ShutterSound.loadAvailableSounds();
        this.mSelectedPosition = ShutterSound.read();
        SoundPool.Builder builder = new SoundPool.Builder();
        int i = 1;
        builder.setMaxStreams(1);
        AudioAttributes.Builder builder2 = new AudioAttributes.Builder();
        if (!c.On()) {
            i = 7;
        }
        builder.setAudioAttributes(builder2.setInternalLegacyStreamType(i).build());
        this.mSoundPool = builder.build();
        this.mSoundPool.setOnLoadCompleteListener(this);
        this.mPreviewSoundIds = new ArrayList(this.mAvailableSounds.size());
        for (int i2 = 0; i2 < this.mAvailableSounds.size(); i2++) {
            this.mPreviewSoundIds.add(-1);
        }
    }

    private void refreshItemView(View view) {
        if (view != null) {
            ((ImageView) view.findViewById(R.id.indicator)).setVisibility(((Integer) view.getTag()).intValue() == this.mSelectedPosition ? 0 : 8);
        }
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        bindItem(preferenceViewHolder.itemView.findViewById(R.id.sound_default), 0);
        bindItem(preferenceViewHolder.itemView.findViewById(R.id.sound_art), 1);
        bindItem(preferenceViewHolder.itemView.findViewById(R.id.sound_old), 2);
        bindItem(preferenceViewHolder.itemView.findViewById(R.id.sound_modern), 3);
    }

    public void onClick(View view) {
        int intValue = ((Integer) view.getTag()).intValue();
        Log.d(TAG, "You selected " + this.mAvailableSounds.get(intValue) + ", position " + intValue);
        this.mSelectedPosition = intValue;
        ShutterSound.persist(intValue);
        refreshItemView(this.mLastItemView);
        refreshItemView(view);
        if (this.mPreviewSoundIds.get(intValue).intValue() == -1) {
            int loadFromAsset = ShutterSound.loadFromAsset(this.mAvailableSounds.get(intValue).soundPath(0), this.mSoundPool);
            this.mSoundToPlay = loadFromAsset;
            this.mPreviewSoundIds.set(intValue, Integer.valueOf(loadFromAsset));
        } else {
            this.mSoundToPlay = -1;
            this.mSoundPool.play(this.mPreviewSoundIds.get(intValue).intValue(), 1.0f, 1.0f, 0, 0, 1.0f);
        }
        this.mLastItemView = view;
        MistatsWrapper.settingClickEvent("attr_edit_sound", this.mAvailableSounds.get(intValue).getFolderName());
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        SoundPool soundPool = this.mSoundPool;
        if (soundPool != null) {
            soundPool.release();
            this.mSoundPool = null;
        }
    }

    public void onLoadComplete(SoundPool soundPool, int i, int i2) {
        if (this.mSoundToPlay == i && i2 == 0) {
            soundPool.play(i, 1.0f, 1.0f, 0, 0, 1.0f);
            this.mSoundToPlay = -1;
        }
    }
}
