package miui.util;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.miui.internal.R;

public class AnimationEffects {
    private static Bundle getScaleUpAnimationBundle(View view) {
        return ActivityOptions.makeScaleUpAnimation(view, 0, 0, 50, 50).toBundle();
    }

    public static void setActivityExitAnimation(Activity activity) {
        setActivityExitAnimation(activity, R.anim.dialog_scale_down);
    }

    public static void setActivityExitAnimation(Activity activity, int i) {
        activity.overridePendingTransition(0, i);
    }

    public static void startActivityForResultWithScaleUpAnimation(Activity activity, Intent intent, int i, View view) {
        activity.startActivityForResult(intent, i, getScaleUpAnimationBundle(view));
    }

    public static void startActivityWithScaleUpAnimation(Context context, Intent intent, View view) {
        context.startActivity(intent, getScaleUpAnimationBundle(view));
    }
}
