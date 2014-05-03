package omsu.svion.game.states;

import omsu.svion.messages.AbstractMessage;

/**
 * Created by victor on 11.04.14.
 */
public class GameSuspendedBecauseUserDisconnected extends AbstractMessage {
    public GameSuspendedBecauseUserDisconnected() {
        this.className = AbstractMessage.class.getCanonicalName();
    }
}
