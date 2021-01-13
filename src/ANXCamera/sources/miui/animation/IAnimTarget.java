package miui.animation;

import android.os.Handler;
import android.util.ArrayMap;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import miui.animation.internal.AnimRunnable;
import miui.animation.internal.AnimTask;
import miui.animation.property.FloatProperty;
import miui.animation.property.IIntValueProperty;
import miui.animation.property.IntValueProperty;
import miui.animation.property.ValueProperty;
import miui.animation.utils.CommonUtils;
import miui.animation.utils.VelocityMonitor;

public abstract class IAnimTarget<T> {
    public static final long FLAT_ONESHOT = 1;
    private static Map<String, FloatProperty> sPropertyMap = new ArrayMap();
    private static AtomicInteger sTargetIds = new AtomicInteger(Integer.MAX_VALUE);
    private final AnimRunnable mAnimRunnable = new AnimRunnable();
    private AnimTask mAnimTask;
    private float mDefaultMinVisible = Float.MAX_VALUE;
    private long mFlags;
    private Handler mHandler = new Handler();
    private final Integer mId = Integer.valueOf(sTargetIds.decrementAndGet());
    private Map<Object, Float> mMinVisibleChanges = new ArrayMap();
    private Map<FloatProperty, MonitorInfo> mMonitors = new ArrayMap();
    private ArrayMap<Object, Double> mValueMap = new ArrayMap<>();

    /* access modifiers changed from: private */
    public static class MonitorInfo {
        VelocityMonitor monitor;
        ResetRunnable resetTask;

        private MonitorInfo() {
            this.monitor = new VelocityMonitor();
            this.resetTask = new ResetRunnable(this);
        }
    }

    private static class ResetRunnable implements Runnable {
        MonitorInfo mMonitorInfo;
        FloatProperty mProperty;
        WeakReference<IAnimTarget> mTargetRef;

        ResetRunnable(MonitorInfo monitorInfo) {
            this.mMonitorInfo = monitorInfo;
        }

        /* access modifiers changed from: package-private */
        public void post(IAnimTarget iAnimTarget, FloatProperty floatProperty) {
            WeakReference<IAnimTarget> weakReference = this.mTargetRef;
            if (weakReference == null || weakReference.get() != iAnimTarget) {
                this.mTargetRef = new WeakReference<>(iAnimTarget);
            }
            this.mProperty = floatProperty;
            iAnimTarget.mHandler.removeCallbacks(this);
            iAnimTarget.mHandler.postDelayed(this, 500);
        }

        public void run() {
            IAnimTarget iAnimTarget = this.mTargetRef.get();
            if (iAnimTarget != null) {
                if (!iAnimTarget.getAnimTask().isRunning(this.mProperty)) {
                    iAnimTarget.setVelocity(this.mProperty, 0.0d);
                }
                this.mMonitorInfo.monitor.clear();
            }
        }
    }

    public IAnimTarget() {
        setMinVisibleChange(0.1f, 9, 10, 11);
        setMinVisibleChange(0.00390625f, 4, 14, 7, 8);
        setMinVisibleChange(0.002f, 2, 3);
    }

    private MonitorInfo getMonitor(FloatProperty floatProperty) {
        MonitorInfo monitorInfo = this.mMonitors.get(floatProperty);
        if (monitorInfo != null) {
            return monitorInfo;
        }
        MonitorInfo monitorInfo2 = new MonitorInfo();
        this.mMonitors.put(floatProperty, monitorInfo2);
        return monitorInfo2;
    }

    public boolean allowAnimRun() {
        return true;
    }

    public FloatProperty createProperty(String str, Class<?> cls) {
        FloatProperty floatProperty = sPropertyMap.get(str);
        if (floatProperty == null) {
            floatProperty = (cls == Integer.TYPE || cls == Integer.class) ? new IntValueProperty(str) : new ValueProperty(str);
            sPropertyMap.put(str, floatProperty);
        }
        return floatProperty;
    }

    public void executeAnim(long j, long j2) {
        this.mAnimRunnable.init(this, j, j2);
        post(this.mAnimRunnable);
    }

    public void executeOnInitialized(Runnable runnable) {
        post(runnable);
    }

    public AnimTask getAnimTask() {
        if (this.mAnimTask == null) {
            this.mAnimTask = new AnimTask(this);
        }
        return this.mAnimTask;
    }

    public float getDefaultMinVisible() {
        return 1.0f;
    }

    public int getId() {
        return this.mId.intValue();
    }

    public int getIntValue(IIntValueProperty iIntValueProperty) {
        T targetObject = getTargetObject();
        if (targetObject != null) {
            return iIntValueProperty.getIntValue(targetObject);
        }
        return Integer.MAX_VALUE;
    }

    public void getLocationOnScreen(int[] iArr) {
        iArr[1] = 0;
        iArr[0] = 0;
    }

    public float getMinVisibleChange(Object obj) {
        int type;
        Float f = this.mMinVisibleChanges.get(obj);
        if (f == null && (obj instanceof FloatProperty) && (type = getType((FloatProperty) obj)) != -1) {
            f = this.mMinVisibleChanges.get(Integer.valueOf(type));
        }
        if (f != null) {
            return f.floatValue();
        }
        float f2 = this.mDefaultMinVisible;
        return f2 != Float.MAX_VALUE ? f2 : getDefaultMinVisible();
    }

    public abstract FloatProperty getProperty(int i);

    public abstract T getTargetObject();

    public abstract int getType(FloatProperty floatProperty);

    public float getValue(int i) {
        return getValue(getProperty(i));
    }

    public float getValue(FloatProperty floatProperty) {
        T targetObject = getTargetObject();
        if (targetObject != null) {
            return floatProperty.getValue(targetObject);
        }
        return Float.MAX_VALUE;
    }

    public double getVelocity(FloatProperty floatProperty) {
        Double d2 = this.mValueMap.get(floatProperty);
        if (d2 != null) {
            return d2.doubleValue();
        }
        return 0.0d;
    }

    public boolean hasFlags(long j) {
        return CommonUtils.hasFlags(this.mFlags, j);
    }

    public boolean isValid() {
        return true;
    }

    public void onFrameEnd(boolean z) {
    }

    public void post(Runnable runnable) {
        runnable.run();
    }

    public IAnimTarget setDefaultMinVisibleChange(float f) {
        this.mDefaultMinVisible = f;
        return this;
    }

    public void setFlags(long j) {
        this.mFlags = j;
    }

    public void setIntValue(IIntValueProperty iIntValueProperty, int i) {
        T targetObject = getTargetObject();
        if (targetObject != null && i != Integer.MAX_VALUE) {
            iIntValueProperty.setIntValue(targetObject, i);
        }
    }

    public IAnimTarget setMinVisibleChange(float f, int... iArr) {
        for (int i : iArr) {
            this.mMinVisibleChanges.put(Integer.valueOf(i), Float.valueOf(f));
        }
        return this;
    }

    public IAnimTarget setMinVisibleChange(float f, String... strArr) {
        for (String str : strArr) {
            setMinVisibleChange(new ValueProperty(str), f);
        }
        return this;
    }

    public IAnimTarget setMinVisibleChange(Object obj, float f) {
        this.mMinVisibleChanges.put(obj, Float.valueOf(f));
        return this;
    }

    public void setValue(FloatProperty floatProperty, float f) {
        T targetObject = getTargetObject();
        if (targetObject != null && f != Float.MAX_VALUE) {
            floatProperty.setValue(targetObject, f);
        }
    }

    public void setVelocity(FloatProperty floatProperty, double d2) {
        if (d2 != 3.4028234663852886E38d) {
            this.mValueMap.put(floatProperty, Double.valueOf(d2));
        }
    }

    public boolean shouldUseIntValue(FloatProperty floatProperty) {
        return floatProperty instanceof IIntValueProperty;
    }

    public void trackVelocity(FloatProperty floatProperty, double d2) {
        MonitorInfo monitor = getMonitor(floatProperty);
        monitor.monitor.update(d2);
        float velocity = monitor.monitor.getVelocity(0);
        if (velocity != 0.0f) {
            monitor.resetTask.post(this, floatProperty);
            setVelocity(floatProperty, (double) velocity);
        }
    }
}
