package omsu.svion.game;

import omsu.svion.game.handler.resolver.GameMessageHandlersResolver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import sun.util.logging.resources.logging;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by victor on 10.04.14.
 */
@Component
public class GameList {
    private static final Logger logger = Logger.getLogger(GameList.class);

    private Set<Game> games = new HashSet<Game>();
    public synchronized void addGame(Game game) {
        games.add(game);
    }
    public synchronized void removeGame(Game game) {
        games.remove(game);
    }
    public Set<Game> getGames() {
        return games;
    }
    private Game waitingForStartGame;

    public Game getWaitingForStartGame() {
        return waitingForStartGame;
    }

    public void setWaitingForStartGame(Game waitingForStartGame) {
        this.waitingForStartGame = waitingForStartGame;
    }
}
