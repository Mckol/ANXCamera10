package miuix.animation;

import android.os.Handler;
import android.os.Looper;
import android.util.ArrayMap;
import android.util.SparseArray;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import miuix.animation.internal.AnimRunnable;
import miuix.animation.internal.AnimTask;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.property.IntValueProperty;
import miuix.animation.property.ValueProperty;
import miuix.animation.utils.CommonUtils;
import miuix.animation.utils.VelocityMonitor;

public abstract class IAnimTarget<T> {
    public static final long FLAT_ONESHOT = 1;
    static final ConcurrentHashMap<Long, Handler> sHandlerCache = new ConcurrentHashMap<>();
    private static Map<String, FloatProperty> sPropertyMap = new ArrayMap();
    private static AtomicInteger sTargetIds = new AtomicInteger(Integer.MAX_VALUE);
    private final AnimRunnable mAnimRunnable = new AnimRunnable();
    private AnimTask mAnimTask = new AnimTask(this);
    private float mDefaultMinVisible = Float.MAX_VALUE;
    private long mFlags;
    private Handler mHandler = getCurHandler();
    private final Integer mId = Integer.valueOf(sTargetIds.decrementAndGet());
    private Map<Object, Float> mMinVisibleChanges = new ArrayMap();
    private Map<FloatProperty, MonitorInfo> mMonitors = new ArrayMap();
    private SparseArray<Double> mVelocityMap = new SparseArray<>();

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

    private static synchronized Handler createAndCacheHandler(long j) {
        Handler handler;
        synchronized (IAnimTarget.class) {
            handler = sHandlerCache.get(Long.valueOf(j));
            if (handler == null) {
                Looper myLooper = Looper.myLooper();
                if (myLooper == null) {
                    myLooper = Looper.getMainLooper();
                }
                Handler handler2 = new Handler(myLooper);
                sHandlerCache.put(Long.valueOf(j), handler2);
                handler = handler2;
            }
        }
        return handler;
    }

    public static Handler getCurHandler() {
        long id = Thread.currentThread().getId();
        Handler handler = sHandlerCache.get(Long.valueOf(id));
        return handler == null ? createAndCacheHandler(id) : handler;
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

    public double getVelocity(int i) {
        Double d2 = this.mVelocityMap.get(i);
        if (d2 != null) {
            return d2.doubleValue();
        }
        return 0.0d;
    }

    public double getVelocity(FloatProperty floatProperty) {
        return getVelocity(floatProperty.getName().hashCode());
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
        if (this.mHandler.getLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            this.mHandler.post(runnable);
        }
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

    public void setVelocity(int i, double d2) {
        if (d2 != 3.4028234663852886E38d) {
            this.mVelocityMap.put(i, Double.valueOf(d2));
        }
    }

    public void setVelocity(FloatProperty floatProperty, double d2) {
        setVelocity(floatProperty.getName().hashCode(), d2);
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
