package omsu.svion;

import omsu.svion.game.Game;
import omsu.svion.game.GameList;
import omsu.svion.game.handler.GameMessageHandler;
import omsu.svion.game.handler.resolver.GameMessageHandlersResolver;
import omsu.svion.game.states.Playing;
import omsu.svion.game.states.State;
import omsu.svion.game.states.WaitingForDisconnectedUserReturn;
import omsu.svion.game.states.WaitingForEnoughPlayers;
import omsu.svion.game.worker.PlayerConnectorOrGameRemover;
import omsu.svion.messages.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;

import java.util.AbstractMap;


/**
 * Created by victor on 12.04.14.
 */
public class Configuration implements InitializingBean {
    private static final Logger logger = Logger.getLogger(Configuration.class);
    @Autowired
    private PlayerConnectorOrGameRemover playerConnectorOrGameRemover;
    @Autowired
    @Qualifier("playerAdded")
    private GameMessageHandler playerAddedHandler;
    @Autowired
    @Qualifier("userDidNotAppearedTooLong")
    private GameMessageHandler userDidNotAppearTooLongHandler;
    @Autowired
    @Qualifier("disconnectedInWaitingState")
    private GameMessageHandler userDisconnectedInWaitingStateHandler;
    @Autowired
    @Qualifier("userLeftGame")
    private GameMessageHandler userLeftGameHandler;
    @Autowired
    @Qualifier("occasionallyDisconnected")
    private GameMessageHandler userOccasionallyDisconnectedHandler;
    @Autowired
    @Qualifier("rtb")
    private GameMessageHandler userReturnedToGameHandler;
    @Autowired
    private GameMessageHandlersResolver gameMessageHandlersResolver;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private GameList gameList;
    public void afterPropertiesSet() throws Exception {
        logger.debug("starting init bean");
        gameMessageHandlersResolver.getHandlers().put(new AbstractMap.SimpleEntry<Class<? extends AbstractMessage>, Class<? extends State>>(NewPlayerAdded.class, WaitingForEnoughPlayers.class),playerAddedHandler);
        gameMessageHandlersResolver.getHandlers().put(new AbstractMap.SimpleEntry<Class<? extends AbstractMessage>, Class<? extends State>>(UserDidNotAppearTooLong.class, WaitingForDisconnectedUserReturn.class),userDidNotAppearTooLongHandler);
        gameMessageHandlersResolver.getHandlers().put(new AbstractMap.SimpleEntry<Class<? extends AbstractMessage>, Class<? extends State>>(UserLeftGame.class, WaitingForEnoughPlayers.class),userDisconnectedInWaitingStateHandler);
        gameMessageHandlersResolver.getHandlers().put(new AbstractMap.SimpleEntry<Class<? extends AbstractMessage>, Class<? extends State>>(UserOccasionallyDisconnected.class, WaitingForEnoughPlayers.class),userDisconnectedInWaitingStateHandler);
        gameMessageHandlersResolver.getHandlers().put(new AbstractMap.SimpleEntry<Class<? extends AbstractMessage>, Class<? extends State>>(UserLeftGame.class, Playing.class),userLeftGameHandler);
        gameMessageHandlersResolver.getHandlers().put(new AbstractMap.SimpleEntry<Class<? extends AbstractMessage>, Class<? extends State>>(UserOccasionallyDisconnected.class, Playing.class),userOccasionallyDisconnectedHandler);
        gameMessageHandlersResolver.getHandlers().put(new AbstractMap.SimpleEntry<Class<? extends AbstractMessage>, Class<? extends State>>(UserLeftGame.class, WaitingForDisconnectedUserReturn.class),userLeftGameHandler);
        gameMessageHandlersResolver.getHandlers().put(new AbstractMap.SimpleEntry<Class<? extends AbstractMessage>, Class<? extends State>>(UserOccasionallyDisconnected.class, WaitingForDisconnectedUserReturn.class),userOccasionallyDisconnectedHandler);
        gameMessageHandlersResolver.getHandlers().put(new AbstractMap.SimpleEntry<Class<? extends AbstractMessage>, Class<? extends State>>(NewPlayerAdded.class, WaitingForDisconnectedUserReturn.class),userReturnedToGameHandler);
        Game waitingForStartGame = new Game(taskExecutor,gameMessageHandlersResolver);
        gameList.setWaitingForStartGame(waitingForStartGame);
        logger.debug("first game created and waiting for players!");
        taskExecutor.execute(playerConnectorOrGameRemover);
    }
}
