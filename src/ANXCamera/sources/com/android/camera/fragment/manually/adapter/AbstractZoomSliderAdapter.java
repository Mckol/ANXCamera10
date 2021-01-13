package com.android.camera.fragment.manually.adapter;

import com.android.camera.fragment.manually.ZoomValueListener;
import com.android.camera.ui.BaseHorizontalZoomView;

public abstract class AbstractZoomSliderAdapter<T> extends BaseHorizontalZoomView.HorizontalDrawAdapter implements BaseHorizontalZoomView.OnPositionSelectListener {
    protected ZoomValueListener mZoomValueListener;

    public abstract boolean isEnable();

    public abstract T mapPositionToValue(float f);

    public abstract float mapValueToPosition(T t);

    public abstract void setEnable(boolean z);
}
