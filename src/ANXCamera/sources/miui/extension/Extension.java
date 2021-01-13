package miui.extension;

import android.util.Log;
import miui.extension.invoker.Invoker;
import miui.reflect.Constructor;
import miui.reflect.IllegalArgumentException;
import miui.reflect.NoSuchClassException;
import miui.reflect.NoSuchMethodException;

public class Extension {
    private static final String TAG = "Extension";
    private final String mAction;
    private boolean mInitialized;
    private final String mInvoker;
    private Invoker mInvokerInstance;
    private final String mTarget;

    public Extension(String str, String str2, String str3) {
        this.mTarget = str;
        this.mAction = str2;
        this.mInvoker = str3;
    }

    private void initialize() {
        if (!this.mInitialized) {
            try {
                this.mInvokerInstance = (Invoker) Constructor.of(this.mInvoker, "()V").newInstance(new Object[0]);
            } catch (NoSuchClassException e2) {
                Log.e(TAG, "Fail to initialize ActivityExecutor, invoker=" + this.mInvoker, e2);
            } catch (NoSuchMethodException e3) {
                Log.e(TAG, "Fail to initialize ActivityExecutor, invoker=" + this.mInvoker, e3);
            } catch (Throwable th) {
                this.mInitialized = true;
                throw th;
            }
            this.mInitialized = true;
        }
    }

    public String getAction() {
        return this.mAction;
    }

    public String getInvoker() {
        return this.mInvoker;
    }

    public String getTarget() {
        return this.mTarget;
    }

    public void invoke(String str, Object... objArr) {
        String str2 = this.mAction;
        if (str2 == null || str2.equals(str)) {
            initialize();
            Invoker invoker = this.mInvokerInstance;
            if (invoker != null) {
                try {
                    invoker.invoke(str, objArr);
                } catch (IllegalArgumentException e2) {
                    Log.e(TAG, "Fail to invoke ActivityExecutor, invoker=" + this.mInvoker, e2);
                }
            }
        }
    }
}
