package omsu.svion.messages;

import omsu.svion.game.model.PlayerModel;
import omsu.svion.game.states.State;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by victor on 11.04.14.
 */
public class GameFinishedTooLittleUsers extends MessageFromServer {
    public GameFinishedTooLittleUsers(List<PlayerModel> players) {
        this.players = players;
        this.className = GameFinishedTooLittleUsers.class.getCanonicalName();
    }

    public GameFinishedTooLittleUsers() {
    }


    private List<PlayerModel> players;

    public List<PlayerModel> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerModel> players) {
        this.players = players;
    }
}
