package omsu.svion.game.handler.impl;

import omsu.svion.game.Game;
import omsu.svion.game.Player;
import omsu.svion.game.handler.GameMessageHandler;
import omsu.svion.game.states.ChoosingCategory;
import omsu.svion.game.states.Playing;
import omsu.svion.game.worker.PlayerConnectorOrGameRemover;
import omsu.svion.messages.AbstractMessage;
import omsu.svion.messages.GameStateUpdateMessage;
import omsu.svion.messages.UserOccasionallyDisconnected;
import omsu.svion.model.json.converter.PlayerConverter;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by victor on 11.04.14.
 */
@Service("playerAdded")
public class PlayerAddedHandler implements GameMessageHandler {
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
            GameStateUpdateMessage mes = new GameStateUpdateMessage(playerConverter.convert(new ArrayList<Player>(game.getPlayers().values())), game.getState());
        logger.debug("3");
            for (Player player :game.getPlayers().values())
            {
                if (player.getState() != Player.State.ONLINE) {
                    logger.debug("skipped player" + player);
                    continue;
                }
                try {
                    player.getWebSocketSession().sendMessage(new TextMessage(objectMapper.writeValueAsString(mes)));
                } catch (IOException e) {
                    UserOccasionallyDisconnected userOccasionallyDisconnected = new UserOccasionallyDisconnected();
                    userOccasionallyDisconnected.setSession(message.getSession());
                    playerConnectorOrGameRemover.handle(userOccasionallyDisconnected);
                }
            }
        logger.debug("4");
        }
}
