package omsu.svion.game.handler.impl;

import omsu.svion.dao.AccountService;
import omsu.svion.entities.Account;
import omsu.svion.game.Game;
import omsu.svion.game.Player;
import omsu.svion.game.handler.GameMessageHandler;
import omsu.svion.game.states.Finished;
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
@Service("userLeftGame")
public class UserLeftGameHandler implements GameMessageHandler{
    private static final Logger logger = Logger.getLogger(UserLeftGameHandler.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PlayerConnectorOrGameRemover playerConnectorOrGameRemover;
    @Autowired
    private AccountService accountService;
    public void handle(AbstractMessage message, Game game) {
        Account account;
        logger.debug("user left game 1");
        PlayerConverter playerConverter = new PlayerConverter();
        if (game.getPlayer(message.getSession().getPrincipal().getName()) != null){
            logger.debug("user left game 2");
            game.removePlayer(message.getSession().getPrincipal().getName());
            if  (game.getPlayers().values().size() >= 2) {
                logger.debug("not many users,not finishing game");
                /*GameStateUpdateMessage gameStateUpdateMessage = new GameStateUpdateMessage(playerConverter.convert(new ArrayList<Player>(game.getPlayers().values())),game.getState());
                for (Player player : game.getPlayers().values()) {
                    if (player.getState() != Player.State.ONLINE) {
                        continue;
                    }
                    try {
                        player.getWebSocketSession().sendMessage(new TextMessage(objectMapper.writeValueAsString(gameStateUpdateMessage)));
                    } catch (IOException e) {
                        UserOccasionallyDisconnected userOccasionallyDisconnected = new UserOccasionallyDisconnected();
                        userOccasionallyDisconnected.setSession(message.getSession());
                        playerConnectorOrGameRemover.handle(userOccasionallyDisconnected);
                    }
                }*/
            }
            else {
                game.setState(Finished.class);
                GameFinishedTooLittleUsers gameFinishedTooLittleUsers = new GameFinishedTooLittleUsers(playerConverter.convert(new ArrayList<Player>(game.getPlayers().values())));
                logger.debug("too little users, finishing game");
                for (Player player : game.getPlayers().values()) {
                    if (player.getState() != Player.State.ONLINE) {
                        continue;
                    }
                    account = accountService.findByEmail(player.getEmail());
                    accountService.setTotalScore(account.getId(),account.getTotalScore() + player.getScore());
                    logger.debug("new score for account "+account.getEmail()+" is"+account.getTotalScore() + player.getScore());
                    try {
                        player.getWebSocketSession().sendMessage(new TextMessage(objectMapper.writeValueAsString(gameFinishedTooLittleUsers)));
                    } catch (IOException e) {
                        UserOccasionallyDisconnected userOccasionallyDisconnected = new UserOccasionallyDisconnected();
                        userOccasionallyDisconnected.setSession(message.getSession());
                        playerConnectorOrGameRemover.handle(userOccasionallyDisconnected);
                    }
                }
                playerConnectorOrGameRemover.handle(new RemoveGameRequest(game));
            }
        }
    }
}
