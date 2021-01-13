package miui.util.async.tasks;

import java.util.Map;
import miui.net.http.HttpSession;
import miui.util.async.tasks.HttpTask;
import org.json.JSONObject;

public class HttpJsonObjectTask extends HttpTextTask<JSONObject> {
    public HttpJsonObjectTask(String str) {
        super(str);
    }

    public HttpJsonObjectTask(HttpSession httpSession, String str) {
        super(httpSession, str);
    }

    public HttpJsonObjectTask(HttpSession httpSession, HttpTask.Method method, String str, Map<String, String> map) {
        super(httpSession, method, str, map);
    }

    @Override // miui.util.async.Task
    public JSONObject doLoad() throws Exception {
        return new JSONObject(requestText());
    }
}
