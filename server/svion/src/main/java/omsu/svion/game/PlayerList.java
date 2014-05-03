package omsu.svion.game;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by victor on 05.04.14.
 */
@Component
public class PlayerList {
    private Map<String,Player> players = new ConcurrentHashMap<String, Player>();
    public void addPlayer(Player player,String email) {
        players.put(email,player);
    }
    public Player getPlayer(String email) {
        return players.get(email);
    }
    public synchronized Player removePlayer(String email) {
        return players.remove(email);
    }
}
