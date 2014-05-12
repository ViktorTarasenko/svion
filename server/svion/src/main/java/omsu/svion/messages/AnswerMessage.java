package omsu.svion.messages;

/**
 * Created by victor on 12.05.14.
 */
public class AnswerMessage extends MessageFromClient {
    public AnswerMessage(short variant) {

        this.variant = variant;
        this.className = AnswerMessage.class.getCanonicalName();
    }

    private short variant;

    public short getVariant() {
        return variant;
    }

}
