package omsu.svion.messages;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by victor on 09.04.14.
 */
public class AbstractMessage {
    protected WebSocketSession session;

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    protected String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
