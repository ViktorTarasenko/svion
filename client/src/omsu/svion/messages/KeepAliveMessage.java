package omsu.svion.messages;

/**
 * Created by victor on 19.04.14.
 */
public class KeepAliveMessage extends MessageFromClient{
    public KeepAliveMessage() {
        this.className = KeepAliveMessage.class.getCanonicalName();
    }
}
