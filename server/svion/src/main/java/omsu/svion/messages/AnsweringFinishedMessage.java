package omsu.svion.messages;

import org.springframework.web.socket.WebSocketSession;

/**
 * Created by victor on 12.05.14.
 */
public class AnsweringFinishedMessage extends GameManagementMessage {
    public AnsweringFinishedMessage(WebSocketSession session) {
        this.session = session;
        this.className = AnsweringFinishedMessage.class.getCanonicalName();
    }
}
