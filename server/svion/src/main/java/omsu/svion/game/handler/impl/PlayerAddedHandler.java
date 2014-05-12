package omsu.svion.game.handler.impl;

import omsu.svion.game.Game;
import omsu.svion.game.Player;
import omsu.svion.game.handler.GameMessageHandler;
import omsu.svion.game.states.ChoosingCategory;
import omsu.svion.game.states.Playing;
import omsu.svion.game.worker.PlayerConnectorOrGameRemover;
import omsu.svion.messages.*;
import omsu.svion.model.json.converter.PlayerConverter;
import omsu.svion.questions.Cost;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by victor on 11.04.14.
 */
@Service("playerAdded")
public class PlayerAddedHandler implements GameMessageHandler {
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private PlayerConnectorOrGameRemover playerConnectorOrGameRemover;
    private static final Logger logger = Logger.getLogger(PlayerAddedHandler.class);
    @Autowired
    private ObjectMapper objectMapper;
    public void handle(AbstractMessage message, Game game) {
        logger.debug("1");
        logger.debug(game.isReadyToStart());
        if (game.isReadyToStart()) {
            logger.debug("game started!");
            game.setState(ChoosingCategory.class);
        }
        logger.debug("2");
        PlayerConverter playerConverter = new PlayerConverter();
        ChooseThemeAndCostRequestMessage mes =  new ChooseThemeAndCostRequestMessage(playerConverter.convert(new ArrayList<Player>(game.getPlayers().values())),game.getAvailableCostsAndThemes().get(game.getTourNumber()-1),false,game.getTourNumber(),game.getCurrentQuestionNumber());
        ChooseThemeAndCostRequestMessage messageForAnswering = new ChooseThemeAndCostRequestMessage(playerConverter.convert(new ArrayList<Player>(game.getPlayers().values())),game.getAvailableCostsAndThemes().get(game.getTourNumber()-1),true,game.getTourNumber(),game.getCurrentQuestionNumber());

        Player[] players = (Player[]) game.getPlayers().values().toArray();
        Player player;
        int answeringPlayerNumber = new SecureRandom().nextInt() % game.getPlayers().size();

        logger.debug("3");
            for (int i = 0; i < players.length;++i)
            {
             player =players[i];
                if (player.getState() != Player.State.ONLINE) {
                    logger.debug("skipped player" + player);
                    continue;
                }
                try {
                    if (i == answeringPlayerNumber) {
                        player.getWebSocketSession().sendMessage(new TextMessage(objectMapper.writeValueAsString(messageForAnswering)));
                        game.setPreviousAnsweredPlayer(player);
                    }
                    else {
                        player.getWebSocketSession().sendMessage(new TextMessage(objectMapper.writeValueAsString(mes)));
                    }
                } catch (IOException e) {
                    UserOccasionallyDisconnected userOccasionallyDisconnected = new UserOccasionallyDisconnected();
                    userOccasionallyDisconnected.setSession(message.getSession());
                    playerConnectorOrGameRemover.handle(userOccasionallyDisconnected);
                }
            }

        logger.debug("4");
        if (game.getState().equals(ChoosingCategory.class)) {
            final Game game1 = game;
            final WebSocketSession webSocketSession = message.getSession();
            Runnable costAndThemeChosenChecker = new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        return;
                    }
                    if (game1.getCostAndThemeChosenChecker() == this) {
                        ThemeAndCostNotChosenMessage themeAndCostNotChosenMessage = new ThemeAndCostNotChosenMessage();
                        themeAndCostNotChosenMessage.setSession(webSocketSession);
                        game1.handleMessage(themeAndCostNotChosenMessage);
                    }
                }
            };
            game.setCostAndThemeChosenChecker(costAndThemeChosenChecker);
            taskExecutor.execute(costAndThemeChosenChecker);
        }

        }
}
