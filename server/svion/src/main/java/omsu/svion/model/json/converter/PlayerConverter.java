package omsu.svion.model.json.converter;

import omsu.svion.game.Player;
import omsu.svion.game.model.PlayerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 12.04.14.
 */
public class PlayerConverter {
    public List<PlayerModel> convert(List<Player> players){
        ArrayList<PlayerModel> res = new ArrayList<PlayerModel>(players.size());
        for (Player player : players) {
            res.add(new PlayerModel(player.getScore(),player.getEmail(),player.getState()));
        }
        return res;

    }
}
