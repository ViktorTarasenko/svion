package omsu.svion.messages;

import org.springframework.web.socket.WebSocketSession;

/**
 * Created by victor on 11.04.14.
 */
public class NewUserConnectedMessage extends GameManagementMessage{
    public NewUserConnectedMessage(WebSocketSession session) {
        this.className = GameManagementMessage.class.getCanonicalName();
        this.session = session;
    }
     private WebSocketSession session;
     private int playerScore;
    public WebSocketSession getSession() {
        return session;
    }


}
