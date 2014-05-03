package omsu.svion.game.handler.impl;

import omsu.svion.game.Game;
import omsu.svion.game.Player;
import omsu.svion.game.handler.GameMessageHandler;
import omsu.svion.game.worker.PlayerConnectorOrGameRemover;
import omsu.svion.messages.AbstractMessage;
import omsu.svion.messages.GameStateUpdateMessage;
import omsu.svion.messages.UserOccasionallyDisconnected;
import omsu.svion.model.json.converter.PlayerConverter;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by victor on 11.04.14.
 */
@Service("disconnectedInWaitingState")
public class UserDisconnectedInWaitingStateHandler implements GameMessageHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PlayerConnectorOrGameRemover playerConnectorOrGameRemover;
   public void handle(AbstractMessage message, Game game) {
       PlayerConverter playerConverter = new PlayerConverter();
            if (game.getPlayer(message.getSession().getPrincipal().getName()) != null){
                game.removePlayer(message.getSession().getPrincipal().getName());
                    GameStateUpdateMessage gameStateUpdateMessage = new GameStateUpdateMessage(playerConverter.convert(new ArrayList<Player>(game.getPlayers().values())),game.getState());
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
                    }


            }
    }
}
