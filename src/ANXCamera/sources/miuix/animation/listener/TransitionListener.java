package miuix.animation.listener;

import java.util.Collection;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;

public class TransitionListener {
    public TransitionListener() {
    }

    @Deprecated
    public TransitionListener(long j) {
    }

    public void onBegin(Object obj) {
    }

    public void onBegin(Object obj, UpdateInfo updateInfo) {
    }

    public void onCancel(Object obj) {
    }

    public void onCancel(Object obj, UpdateInfo updateInfo) {
    }

    public void onComplete(Object obj) {
    }

    public void onComplete(Object obj, UpdateInfo updateInfo) {
    }

    public void onUpdate(Object obj, Collection<UpdateInfo> collection) {
    }

    public void onUpdate(Object obj, FloatProperty floatProperty, float f, float f2, boolean z) {
    }

    @Deprecated
    public void onUpdate(Object obj, FloatProperty floatProperty, float f, boolean z) {
    }

    public void onUpdate(Object obj, IIntValueProperty iIntValueProperty, int i, float f, boolean z) {
    }
}
