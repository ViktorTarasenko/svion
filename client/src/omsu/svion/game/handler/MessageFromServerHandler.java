package omsu.svion.game.handler;

import omsu.svion.messages.MessageFromServer;

/**
 * Created by victor on 03.05.14.
 */
public interface MessageFromServerHandler  {
    public void handle(MessageFromServer message);
}
