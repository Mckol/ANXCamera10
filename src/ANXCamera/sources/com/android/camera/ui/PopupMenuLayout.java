package com.android.camera.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.camera.ui.DragLayout;

public class PopupMenuLayout extends FrameLayout implements DragLayout.OnDragListener {
    public PopupMenuLayout(@NonNull Context context) {
        super(context);
    }

    public PopupMenuLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PopupMenuLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.android.camera.ui.DragLayout.OnDragListener
    public void onDragDone(boolean z) {
        if (z) {
            setAlpha(0.0f);
            setVisibility(8);
            return;
        }
        setAlpha(1.0f);
        setVisibility(0);
        setTranslationY(0.0f);
    }

    @Override // com.android.camera.ui.DragLayout.OnDragListener
    public void onDragProgress(int i) {
        setTranslationY((float) i);
        setAlpha(1.0f - Math.min(1.0f, ((float) Math.abs(i)) / DragLayout.getAnimationConfig().getDisappearDistance()));
    }

    @Override // com.android.camera.ui.DragLayout.OnDragListener
    public void onDragStart(boolean z) {
        if (z) {
            setAlpha(1.0f);
            return;
        }
        setVisibility(0);
        setAlpha(0.0f);
    }
}
