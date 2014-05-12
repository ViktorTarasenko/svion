package omsu.svion.game.handler.impl;

import omsu.svion.game.Game;
import omsu.svion.game.Player;
import omsu.svion.game.handler.GameMessageHandler;
import omsu.svion.game.states.*;
import omsu.svion.game.worker.PlayerConnectorOrGameRemover;
import omsu.svion.messages.*;
import omsu.svion.model.json.converter.PlayerConverter;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by victor on 11.04.14.
 */
@Service("occasionallyDisconnected")
public class UserOccasionallyDisconnectedHandler implements GameMessageHandler {
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PlayerConnectorOrGameRemover playerConnectorOrGameRemover;
    public void handle(AbstractMessage message, Game game) {
        PlayerConverter playerConverter = new PlayerConverter();
       if (game.getPlayer(message.getSession().getPrincipal().getName()) != null){
           if (! WaitingForDisconnectedUserReturn.class.isAssignableFrom(game.getState())) {
                game.pushState(WaitingForDisconnectedUserReturn.class);
           }
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
           final PlayerConnectorOrGameRemover playerConnectorOrGameRemover1 = this.playerConnectorOrGameRemover;
           final Game g = game;
           final Player p = game.getPlayer(message.getSession().getPrincipal().getName());
            final AbstractMessage mes = message;
            Runnable playerStateChecker = new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        return;
                    }
                    if (p.getDisconnectedScheduler() == this) {
                        UserDidNotAppearTooLong userDidNotAppearTooLong = new UserDidNotAppearTooLong();
                        userDidNotAppearTooLong.setSession(mes.getSession());
                        playerConnectorOrGameRemover1.handle(userDidNotAppearTooLong);
                    }


                }
            };
            p.setDisconnectedScheduler(playerStateChecker);
            taskExecutor.execute(playerStateChecker);
       }
    }
}
