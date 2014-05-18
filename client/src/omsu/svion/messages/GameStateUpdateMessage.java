package omsu.svion.messages;


import omsu.svion.game.model.PlayerModel;
import omsu.svion.game.states.State;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by victor on 09.04.14.
 */
public class GameStateUpdateMessage extends MessageFromServer {
    public GameStateUpdateMessage(List<PlayerModel> players, Class<? extends State> newState) {
        this.players = players;
        this.newState = newState;
        this.className = GameStateUpdateMessage.class.getCanonicalName();
    }

    public GameStateUpdateMessage() {
    }

    private Class<? extends State> newState;
    private List<PlayerModel> players;

    public Class<? extends State> getNewState() {
        return newState;
    }

    public void setNewState(Class<? extends State> newState) {
        this.newState = newState;
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerModel> players) {
        this.players = players;
    }


}
