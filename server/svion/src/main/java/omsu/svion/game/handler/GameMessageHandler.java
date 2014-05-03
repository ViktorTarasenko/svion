package omsu.svion.game.handler;

import omsu.svion.game.Game;
import omsu.svion.messages.AbstractMessage;

/**
 * Created by victor on 11.04.14.
 */
public interface GameMessageHandler {
    public void handle(AbstractMessage message, Game game);
}
