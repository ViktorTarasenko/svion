package omsu.svion.messages;

import omsu.svion.game.Game;

/**
 * Created by victor on 11.04.14.
 */
public class RemoveGameRequest extends GameManagementMessage{
    public RemoveGameRequest(Game game) {
        this.game = game;
    }
    private Game game;
    public Game getGame() {
        return game;
    }
}
