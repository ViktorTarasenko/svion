package omsu.svion.game.worker;

import omsu.svion.game.Game;
import omsu.svion.game.GameList;
import omsu.svion.game.Player;
import omsu.svion.game.PlayerList;
import omsu.svion.game.handler.resolver.GameMessageHandlersResolver;
import omsu.svion.game.states.WaitingForEnoughPlayers;
import omsu.svion.messages.GameManagementMessage;
import omsu.svion.messages.NewPlayerAdded;
import omsu.svion.messages.NewUserConnectedMessage;
import omsu.svion.messages.RemoveGameRequest;
import omsu.svion.messages.UserOccasionallyDisconnected;
import omsu.svion.messages.UserLeftGame;
import omsu.svion.messages.UserDidNotAppearTooLong;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by victor on 11.04.14.
 */
@Component
public class PlayerConnectorOrGameRemover extends Thread{
    private static final Logger logger = Logger.getLogger(PlayerConnectorOrGameRemover.class);
    @Autowired
    private GameList gameList;
    @Autowired
    private PlayerList playerList;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private GameMessageHandlersResolver gameMessageHandlersResolver;
    public void handle(GameManagementMessage connectOrRemoveGameMessage) {
        try {
            logger.debug("message added");
            messages.put(connectOrRemoveGameMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private BlockingQueue<GameManagementMessage> messages = new LinkedBlockingQueue<GameManagementMessage>();
    public void run() {
        logger.debug("started handling messages ");
        while (true) {
            try {
                GameManagementMessage message = messages.take();
                logger.debug("took msg "+message);
                if (message instanceof NewUserConnectedMessage) {
                    logger.debug("user connected");
                    NewUserConnectedMessage newUserConnectedMessage = (NewUserConnectedMessage)message;
                    Player player;
                    Game game;
                    if ((player = playerList.getPlayer(newUserConnectedMessage.getSession().getPrincipal().getName())) != null) {
                        logger.debug("user already exists ");
                        if((game = player.getGame()) != null) {
                            if (player.getState() == Player.State.OCCASIONALLY_DISCONNECTED){
                                player.setState(Player.State.ONLINE);
                                logger.debug("set status to playing ,user was frozen");
                            }
                            game.handleMessage(message);
                        }
                    }
                    else {
                        logger.debug("user no exists session is "+newUserConnectedMessage.getSession());
                        player = new Player(newUserConnectedMessage.getSession().getPrincipal().getName(),newUserConnectedMessage.getSession(),0);
                        logger.debug("user no exists 1");
                        gameList.getWaitingForStartGame().addPlayer(player.getEmail(),player);
                        logger.debug("new size is"+gameList.getWaitingForStartGame().getPlayers().size());
                        logger.debug("user no exists 2");
                        player.setGame(gameList.getWaitingForStartGame());
                        logger.debug("user no exists 3");
                        playerList.addPlayer(player,player.getEmail());
                        logger.debug("user no exists 4");
                        NewPlayerAdded newPlayerAdded = new NewPlayerAdded();
                        logger.debug("user no exists 5");
                        newPlayerAdded.setSession(message.getSession());
                        logger.debug("user no exists 6");
                        game = gameList.getWaitingForStartGame();
                        if (gameList.getWaitingForStartGame().isReadyToStart()) {
                            logger.debug("user no exists 7");
                            gameList.addGame(gameList.getWaitingForStartGame());
                            logger.debug("user no exists 8");
                            gameList.setWaitingForStartGame(new Game(taskExecutor,gameMessageHandlersResolver));

                        }
                        game.handleMessage(newPlayerAdded);
                        logger.debug("returned from handler");
                    }
                }
                else if (message instanceof RemoveGameRequest) {
                    logger.debug("removing game");
                    Game game = ((RemoveGameRequest) message).getGame();
                    logger.debug("game is "+game);
                    for (String email : game.getPlayers().keySet()) {
                        try {
                            playerList.getPlayer(email).getWebSocketSession().close();
                        } catch (IOException e) {
                        }
                        logger.debug("removed player"+playerList.removePlayer(email));
                    }
                    gameList.removeGame(game);
                    game.destroy();

                }
                else if (message instanceof UserOccasionallyDisconnected) {
                    logger.debug("took msg: user disconnected occasionally");
                    Player player = playerList.getPlayer(message.getSession().getPrincipal().getName());
                    if (player != null) {
                        player.setState(Player.State.OCCASIONALLY_DISCONNECTED);
                        Game game  = player.getGame();
                        if (WaitingForEnoughPlayers.class.isAssignableFrom(game.getState())) {
                            playerList.removePlayer(player.getEmail());
                        }
                        if (game != null) {
                            game.handleMessage(message);
                        }
                    }
                }
                else if ((message instanceof UserDidNotAppearTooLong)) {
                    Player player = playerList.getPlayer(message.getSession().getPrincipal().getName());
                    if (player != null) {
                        Game game  = player.getGame();
                        if ((game != null) && (player.getState() == Player.State.OCCASIONALLY_DISCONNECTED)){
                            playerList.removePlayer(player.getEmail());
                        }
                        game.handleMessage(message);
                    }
                }
                else if ((message instanceof UserLeftGame)) {
                    logger.debug("took msg: user left game");
                    Player player = playerList.getPlayer(message.getSession().getPrincipal().getName());
                    if (player != null) {
                        playerList.removePlayer(player.getEmail());
                        Game game  = player.getGame();
                        if (game != null){
                            logger.debug("took msg: user left game");
                            game.handleMessage(message);
                        }
                    }
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
