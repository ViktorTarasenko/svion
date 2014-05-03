package omsu.svion.game.handler.impl;

import omsu.svion.game.Game;
import omsu.svion.game.Player;
import omsu.svion.game.handler.GameMessageHandler;
import omsu.svion.game.states.Finished;
import omsu.svion.game.states.State;
import omsu.svion.game.worker.PlayerConnectorOrGameRemover;
import omsu.svion.messages.*;
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
@Service("userDidNotAppearedTooLong")
public class UserDidNotAppearedTooLongHandler implements GameMessageHandler {
    private static final Logger logger = Logger.getLogger(UserDidNotAppearedTooLongHandler.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PlayerConnectorOrGameRemover playerConnectorOrGameRemover;
    public void handle(AbstractMessage message, Game game) {
        PlayerConverter playerConverter = new PlayerConverter();
        Player player;
        logger.debug("entered handler"+message.getSession());
        logger.debug("entered handler"+message.getSession().getPrincipal().getName());
        if ((player = game.getPlayer(message.getSession().getPrincipal().getName())) != null){
            game.removePlayer(message.getSession().getPrincipal().getName());
               logger.debug("now players size is"+game.getPlayers().size());

                if (game.getPlayers().size() < 2) {
                    logger.debug("less than 2 players remains");
                    game.setState(Finished.class);
                    logger.debug("game is ended");
                    GameFinished gameFinished = new GameFinished(GameFinished.Status.TOO_LITTLE_USERS);
                    for (Player p : game.getPlayers().values()) {
                        if (p.getState() != Player.State.ONLINE) {
                            continue;
                        }
                        try {
                            p.getWebSocketSession().sendMessage(new TextMessage(objectMapper.writeValueAsString(gameFinished)));
                        } catch (IOException e) {
                            UserOccasionallyDisconnected userOccasionallyDisconnected = new UserOccasionallyDisconnected();
                            userOccasionallyDisconnected.setSession(message.getSession());
                            playerConnectorOrGameRemover.handle(userOccasionallyDisconnected);
                        }
                    }

                    playerConnectorOrGameRemover.handle(new RemoveGameRequest(game));
                }
                else {
                    if (checkIfWaitingUsersExists(game)) {
                        logger.debug("waiting users exists");
                        GameStateUpdateMessage gameStateUpdateMessage = new GameStateUpdateMessage(playerConverter.convert(new ArrayList<Player>(game.getPlayers().values())),game.getState());
                        for (Player p : game.getPlayers().values()) {
                            if (p.getState() != Player.State.ONLINE) {
                                continue;
                            }
                            try {
                                p.getWebSocketSession().sendMessage(new TextMessage(objectMapper.writeValueAsString(gameStateUpdateMessage)));
                            } catch (IOException e) {
                                UserOccasionallyDisconnected userOccasionallyDisconnected = new UserOccasionallyDisconnected();
                                userOccasionallyDisconnected.setSession(message.getSession());
                                playerConnectorOrGameRemover.handle(userOccasionallyDisconnected);
                            }
                        }
                    }
                    else {
                        logger.debug("no waiting users more, can continue");
                        game.restoreState();
                        logger.debug("restored state "+game.getState());
                        GameStateUpdateMessage gameStateUpdateMessage = new GameStateUpdateMessage(playerConverter.convert(new ArrayList<Player>(game.getPlayers().values())),game.getState());
                        for (Player p : game.getPlayers().values()) {
                            if (p.getState() != Player.State.ONLINE) {
                                continue;
                            }
                            try {
                                p.getWebSocketSession().sendMessage(new TextMessage(objectMapper.writeValueAsString(gameStateUpdateMessage)));
                            } catch (IOException e) {
                                UserOccasionallyDisconnected userOccasionallyDisconnected = new UserOccasionallyDisconnected();
                                userOccasionallyDisconnected.setSession(message.getSession());
                                playerConnectorOrGameRemover.handle(userOccasionallyDisconnected);
                            }
                        }
                    }

                }
            }
        }
    private boolean checkIfWaitingUsersExists(Game game) {
        for (Player p: game.getPlayers().values()) {
            if (p.getState() == Player.State.OCCASIONALLY_DISCONNECTED) {
                return true;
            }
        }
        return  false;
    }
}
