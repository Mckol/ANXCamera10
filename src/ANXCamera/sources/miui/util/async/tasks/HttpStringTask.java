package miui.util.async.tasks;

import java.util.Map;
import miui.net.http.HttpSession;
import miui.util.async.tasks.HttpTask;

public class HttpStringTask extends HttpTextTask<String> {
    public HttpStringTask(String str) {
        this(null, HttpTask.Method.Get, str, null);
    }

    public HttpStringTask(HttpSession httpSession, String str) {
        this(httpSession, HttpTask.Method.Get, str, null);
    }

    public HttpStringTask(HttpSession httpSession, HttpTask.Method method, String str, Map<String, String> map) {
        super(httpSession, method, str, map);
    }

    @Override // miui.util.async.Task
    public String doLoad() throws Exception {
        return requestText();
    }
}
