package miui.util.async;

/* access modifiers changed from: package-private */
public class TaskExecutingException extends RuntimeException {
    public TaskExecutingException(Exception exc) {
        super(exc);
    }
}
