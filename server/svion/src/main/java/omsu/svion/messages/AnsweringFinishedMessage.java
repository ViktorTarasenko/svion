package omsu.svion.messages;

import org.springframework.web.socket.WebSocketSession;

/**
 * Created by victor on 12.05.14.
 */
public class AnsweringFinishedMessage extends GameManagementMessage {
    public AnsweringFinishedMessage(WebSocketSession session,int rightAnswer) {
        this.session = session;
        this.className = AnsweringFinishedMessage.class.getCanonicalName();
        this.rightAnswer = rightAnswer;
    }
    private int rightAnswer;

    public int getRightAnswer() {
        return rightAnswer;
    }
}
