package miui.util.async.tasks.listeners;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedHashSet;
import miui.util.AppConstants;
import miui.util.async.Task;
import miui.util.async.TaskManager;

public class ImageViewBindingListener extends BaseTaskListener {
    private static final LinkedHashSet<ImageViewBindingListener> ALL_LISTENERS = new LinkedHashSet<>();
    private WeakReference<Task<?>> mCurrentTask;
    private Drawable mError;
    private final WeakReference<ImageView> mImageView;
    private Drawable mPlaceHolder;

    public ImageViewBindingListener(ImageView imageView) {
        this.mImageView = new WeakReference<>(imageView);
    }

    private boolean isSameImageView(ImageViewBindingListener imageViewBindingListener) {
        return this.mImageView.get() == imageViewBindingListener.mImageView.get();
    }

    public final ImageView getImageView() {
        return this.mImageView.get();
    }

    @Override // miui.util.async.Task.Listener, miui.util.async.tasks.listeners.BaseTaskListener
    public void onException(TaskManager taskManager, Task<?> task, Exception exc) {
        ImageView imageView;
        if (this.mError != null && (imageView = this.mImageView.get()) != null) {
            imageView.setImageDrawable(this.mError);
        }
    }

    @Override // miui.util.async.Task.Listener, miui.util.async.tasks.listeners.BaseTaskListener
    public void onFinalize(TaskManager taskManager, Task<?> task) {
        ALL_LISTENERS.remove(this);
    }

    @Override // miui.util.async.Task.Listener, miui.util.async.tasks.listeners.BaseTaskListener
    public void onPrepare(TaskManager taskManager, Task<?> task) {
        ImageView imageView;
        Task<?> task2;
        WeakReference<Task<?>> weakReference = this.mCurrentTask;
        if (!(weakReference == null || (task2 = weakReference.get()) == null)) {
            task2.cancel();
        }
        this.mCurrentTask = new WeakReference<>(task);
        Task<?> task3 = null;
        Iterator<ImageViewBindingListener> it = ALL_LISTENERS.iterator();
        while (true) {
            if (it.hasNext()) {
                ImageViewBindingListener next = it.next();
                if (next != this && isSameImageView(next)) {
                    task3 = next.mCurrentTask.get();
                    break;
                }
            } else {
                break;
            }
        }
        if (task3 != null) {
            task3.cancel();
        }
        ALL_LISTENERS.add(this);
        if (this.mPlaceHolder != null && (imageView = this.mImageView.get()) != null) {
            imageView.setImageDrawable(this.mPlaceHolder);
        }
    }

    @Override // miui.util.async.Task.Listener, miui.util.async.tasks.listeners.BaseTaskListener
    public Object onResult(TaskManager taskManager, Task<?> task, Object obj) {
        ImageView imageView = this.mImageView.get();
        if (imageView != null) {
            Drawable[] drawableArr = {imageView.getDrawable(), null};
            if (drawableArr[0] != null) {
                Resources resources = AppConstants.getCurrentApplication().getResources();
                if (obj instanceof Bitmap) {
                    drawableArr[1] = new BitmapDrawable(resources, (Bitmap) obj);
                } else {
                    drawableArr[1] = (Drawable) obj;
                }
                TransitionDrawable transitionDrawable = new TransitionDrawable(drawableArr);
                transitionDrawable.setCrossFadeEnabled(true);
                imageView.setImageDrawable(transitionDrawable);
                transitionDrawable.startTransition(resources.getInteger(17694720));
            } else if (obj instanceof Bitmap) {
                imageView.setImageBitmap((Bitmap) obj);
            } else {
                imageView.setImageDrawable((Drawable) obj);
            }
        }
        return obj;
    }

    public ImageViewBindingListener setErrorDrawable(Drawable drawable) {
        this.mError = drawable;
        return this;
    }

    public ImageViewBindingListener setPlaceHolderDrawable(Drawable drawable) {
        this.mPlaceHolder = drawable;
        return this;
    }
}
