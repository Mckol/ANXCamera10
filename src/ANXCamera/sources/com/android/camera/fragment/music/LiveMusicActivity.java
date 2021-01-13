package com.android.camera.fragment.music;

import android.os.Bundle;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import miui.app.ActionBar;
import miui.app.Activity;

public class LiveMusicActivity extends Activity {
    public static final int LOCAL = 1;
    public static final int ONLINE = 0;
    public static final String TAG = "LiveMusicActivity";
    private int mOldOriginVolumeStream;

    public interface Mp3DownloadCallback {
        void onCompleted(LiveMusicInfo liveMusicInfo);

        void onFailed();
    }

    private void init() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.live_music_change_bgm);
            actionBar.setFragmentViewPagerMode(this, getFragmentManager(), false);
            boolean z = !Util.isGlobalVersion();
            if (z && !DataRepository.dataItemFeature().Kj()) {
                z = DataRepository.dataItemCloud().supportTMMusic();
            }
            if (z) {
                String string = getString(R.string.live_music_hot_title);
                getActionBar().addFragmentTab(string, actionBar.newTab().setText(string), FragmentLiveMusicOnline.class, null, false);
            }
            String string2 = getString(R.string.live_music_local_title);
            getActionBar().addFragmentTab(string2, actionBar.newTab().setText(string2), FragmentLiveMusicLocal.class, null, false);
        }
    }

    /* access modifiers changed from: protected */
    @Override // miui.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mOldOriginVolumeStream = getVolumeControlStream();
        setVolumeControlStream(3);
        if (getIntent().getBooleanExtra("StartActivityWhenLocked", false)) {
            setShowWhenLocked(true);
        }
        init();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        setVolumeControlStream(this.mOldOriginVolumeStream);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        DataRepository.dataItemGlobal().resetTimeOut();
    }
}
