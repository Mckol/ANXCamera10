package miui.util.async.tasks.listeners;

import miui.util.async.Task;
import miui.util.async.TaskManager;

public abstract class BaseTaskListener implements Task.Listener {
    @Override // miui.util.async.Task.Listener
    public void onCanceled(TaskManager taskManager, Task<?> task) {
    }

    @Override // miui.util.async.Task.Listener
    public void onException(TaskManager taskManager, Task<?> task, Exception exc) {
    }

    @Override // miui.util.async.Task.Listener
    public void onFinalize(TaskManager taskManager, Task<?> task) {
    }

    @Override // miui.util.async.Task.Listener
    public void onPrepare(TaskManager taskManager, Task<?> task) {
    }

    @Override // miui.util.async.Task.Listener
    public void onProgress(TaskManager taskManager, Task<?> task, int i, int i2) {
    }

    @Override // miui.util.async.Task.Listener
    public Object onResult(TaskManager taskManager, Task<?> task, Object obj) {
        return obj;
    }
}
