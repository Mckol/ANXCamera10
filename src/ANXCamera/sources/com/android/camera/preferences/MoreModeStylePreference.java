package com.android.camera.preferences;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.VideoView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;

public class MoreModeStylePreference extends Preference implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "MoreModeStylePreference";
    private View mPopupCover;
    private RadioButton mPopupRadioBtn;
    private VideoView mPopupVideoView;
    private View mTabCover;
    private RadioButton mTabRadioBtn;
    private VideoView mTabVideoView;
    private int mValue;

    public MoreModeStylePreference(Context context) {
        super(context);
    }

    public MoreModeStylePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MoreModeStylePreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    static /* synthetic */ void a(VideoView videoView, View view, MediaPlayer mediaPlayer) {
        mediaPlayer.setLooping(true);
        mediaPlayer.setOnInfoListener(new a(videoView, view));
    }

    static /* synthetic */ boolean a(VideoView videoView, View view, MediaPlayer mediaPlayer, int i, int i2) {
        if (i != 3) {
            return true;
        }
        videoView.setAlpha(1.0f);
        view.setVisibility(8);
        return true;
    }

    private void preparedVideoView(VideoView videoView, View view, int i) {
        videoView.setVideoURI(Uri.parse("android.resource://" + getContext().getPackageName() + "/" + i));
        videoView.setOnPreparedListener(new b(videoView, view));
    }

    private void preparedVideos() {
        if (Util.isNightUiMode(getContext())) {
            preparedVideoView(this.mTabVideoView, this.mTabCover, R.raw.more_mode_style_tab_night);
            preparedVideoView(this.mPopupVideoView, this.mPopupCover, R.raw.more_mode_style_popup_night);
            return;
        }
        preparedVideoView(this.mTabVideoView, this.mTabCover, R.raw.more_mode_style_tab_light);
        preparedVideoView(this.mPopupVideoView, this.mPopupCover, R.raw.more_mode_style_popup_light);
    }

    public int getValue() {
        return this.mValue;
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mTabVideoView = (VideoView) preferenceViewHolder.findViewById(R.id.open_type_tab_video);
        this.mPopupVideoView = (VideoView) preferenceViewHolder.findViewById(R.id.open_type_popup_video);
        this.mTabRadioBtn = (RadioButton) preferenceViewHolder.findViewById(R.id.open_type_tab_radio);
        this.mPopupRadioBtn = (RadioButton) preferenceViewHolder.findViewById(R.id.open_type_popup_radio);
        this.mTabCover = preferenceViewHolder.findViewById(R.id.tab_cover);
        this.mPopupCover = preferenceViewHolder.findViewById(R.id.popup_cover);
        this.mTabRadioBtn.setOnCheckedChangeListener(this);
        this.mPopupRadioBtn.setOnCheckedChangeListener(this);
        preferenceViewHolder.findViewById(R.id.open_type_popup).setOnClickListener(this);
        preferenceViewHolder.findViewById(R.id.open_type_tab).setOnClickListener(this);
        preparedVideos();
        this.mValue = CameraSettings.getMoreModeStyle();
        String str = TAG;
        Log.d(str, "onBindViewHolder " + this.mValue);
        int i = this.mValue;
        if (i == 0) {
            this.mTabRadioBtn.setChecked(true);
            this.mPopupRadioBtn.setChecked(false);
            this.mTabVideoView.start();
            this.mPopupVideoView.pause();
        } else if (1 == i) {
            this.mTabRadioBtn.setChecked(false);
            this.mPopupRadioBtn.setChecked(true);
            this.mPopupVideoView.start();
            this.mTabVideoView.pause();
        }
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        int id = compoundButton.getId();
        if (id == R.id.open_type_popup_radio) {
            this.mTabRadioBtn.setChecked(!z);
            this.mPopupVideoView.start();
            this.mTabVideoView.pause();
            this.mTabVideoView.seekTo(0);
            this.mValue = 1;
        } else if (id == R.id.open_type_tab_radio) {
            this.mPopupRadioBtn.setChecked(!z);
            this.mPopupVideoView.pause();
            this.mPopupVideoView.seekTo(0);
            this.mTabVideoView.start();
            this.mValue = 0;
        }
        persistString(String.valueOf(this.mValue));
        CameraSettings.setMoreModeStyle(this.mValue);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.open_type_popup) {
            this.mTabRadioBtn.setChecked(false);
            this.mPopupRadioBtn.setChecked(true);
            this.mPopupVideoView.start();
            this.mTabVideoView.pause();
            this.mTabVideoView.seekTo(0);
            this.mValue = 1;
        } else if (id == R.id.open_type_tab) {
            this.mTabRadioBtn.setChecked(true);
            this.mPopupRadioBtn.setChecked(false);
            this.mPopupVideoView.pause();
            this.mPopupVideoView.seekTo(0);
            this.mTabVideoView.start();
            this.mValue = 0;
        }
        persistString(String.valueOf(this.mValue));
        CameraSettings.setMoreModeStyle(this.mValue);
    }

    public void onPause() {
        Log.d(TAG, "onPause");
        VideoView videoView = this.mTabVideoView;
        if (videoView != null) {
            videoView.stopPlayback();
        }
        View view = this.mTabCover;
        if (view != null) {
            view.setVisibility(0);
        }
        VideoView videoView2 = this.mPopupVideoView;
        if (videoView2 != null) {
            videoView2.stopPlayback();
        }
        View view2 = this.mPopupCover;
        if (view2 != null) {
            view2.setVisibility(0);
        }
    }

    public void onResume() {
        Log.d(TAG, "onResume");
        if (this.mTabVideoView != null && this.mPopupVideoView != null) {
            preparedVideos();
            int i = this.mValue;
            if (i == 0) {
                this.mTabVideoView.start();
            } else if (1 == i) {
                this.mPopupVideoView.start();
            }
        }
    }
}
