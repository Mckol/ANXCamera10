package miui.hybrid;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import com.miui.internal.R;
import com.miui.internal.hybrid.HybridManager;
import java.util.HashSet;
import java.util.Set;
import miui.app.Activity;

public class HybridActivity extends Activity {
    public static final String EXTRA_URL = "com.miui.sdk.hybrid.extra.URL";
    private Set<HybridView> mHybridViews = new HashSet();

    private void destroyHybridView() {
        for (HybridView hybridView : this.mHybridViews) {
            if (hybridView != null) {
                if (hybridView.getParent() != null) {
                    ((ViewGroup) hybridView.getParent()).removeView(hybridView);
                }
                hybridView.destroy();
            }
        }
        this.mHybridViews.clear();
    }

    /* access modifiers changed from: protected */
    public int getConfigResId() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public View getContentView() {
        return getLayoutInflater().inflate(R.layout.hybrid_main, (ViewGroup) null);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        for (HybridView hybridView : this.mHybridViews) {
            hybridView.getHybridManager().onActivityResult(i, i2, intent);
        }
    }

    /* access modifiers changed from: protected */
    @Override // miui.app.Activity
    public void onCreate(Bundle bundle) {
        Intent intent;
        super.onCreate(bundle);
        setContentView(getContentView());
        View findViewById = findViewById(R.id.hybrid_view);
        if (findViewById != null && (findViewById instanceof HybridView)) {
            String str = null;
            if (bundle != null) {
                str = bundle.getString(EXTRA_URL);
            }
            if (str == null && (intent = getIntent()) != null) {
                str = intent.getStringExtra(EXTRA_URL);
            }
            registerHybridView((HybridView) findViewById, getConfigResId(), str);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        for (HybridView hybridView : this.mHybridViews) {
            hybridView.getHybridManager().onDestroy();
        }
        destroyHybridView();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            for (HybridView hybridView : this.mHybridViews) {
                if (hybridView.canGoBack() && !hybridView.getHybridManager().isDetached()) {
                    hybridView.goBack();
                    return true;
                }
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        for (HybridView hybridView : this.mHybridViews) {
            hybridView.getHybridManager().onPause();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        for (HybridView hybridView : this.mHybridViews) {
            hybridView.getHybridManager().onResume();
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        for (HybridView hybridView : this.mHybridViews) {
            hybridView.getHybridManager().onStart();
        }
    }

    /* access modifiers changed from: protected */
    @Override // miui.app.Activity
    public void onStop() {
        super.onStop();
        for (HybridView hybridView : this.mHybridViews) {
            hybridView.getHybridManager().onStop();
        }
    }

    /* access modifiers changed from: protected */
    public final void registerHybridView(View view) {
        registerHybridView(view, getConfigResId());
    }

    /* access modifiers changed from: protected */
    public final void registerHybridView(View view, int i) {
        registerHybridView(view, i, null);
    }

    /* access modifiers changed from: protected */
    public final void registerHybridView(View view, int i, String str) {
        if (view instanceof HybridView) {
            HybridView hybridView = (HybridView) view;
            HybridManager hybridManager = new HybridManager(this, hybridView);
            hybridView.setHybridManager(hybridManager);
            this.mHybridViews.add(hybridView);
            hybridManager.init(i, str);
            return;
        }
        throw new IllegalArgumentException("view being registered is not a hybrid view");
    }

    /* access modifiers changed from: protected */
    public final void unregisterHybridView(View view) {
        if (view instanceof HybridView) {
            this.mHybridViews.remove(view);
            return;
        }
        throw new IllegalArgumentException("view being unregistered is not a hybrid view");
    }
}
