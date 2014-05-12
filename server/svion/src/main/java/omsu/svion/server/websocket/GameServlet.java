package omsu.svion.server.websocket;

import omsu.svion.dao.AccountService;
import omsu.svion.entities.Account;
import omsu.svion.game.Game;
import omsu.svion.game.Player;
import omsu.svion.game.PlayerList;
import omsu.svion.game.worker.PlayerConnectorOrGameRemover;
import omsu.svion.messages.*;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 * Created by victor on 05.04.14.
 */

public class GameServlet extends TextWebSocketHandler {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PlayerConnectorOrGameRemover playerConnectorOrGameRemover;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private PlayerList playerList;
    @Autowired
    private AccountService accountService;
    private static final Logger logger = Logger.getLogger(GameServlet.class);
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        playerConnectorOrGameRemover.handle(new NewUserConnectedMessage(session));
    }
    @Override
    public void handleTextMessage(WebSocketSession session,TextMessage message) throws Exception {
        logger.debug("got text message "+message);
        Player player = playerList.getPlayer(session.getPrincipal().getName());
        if (player == null) {
            return;
        }
        Game game = player.getGame();
        if (game == null) {
            return;
        }
        try {
        String className = objectMapper.readTree(message.getPayload()).path("className").asText();
        if (className == null)
            return;
        try {
            Class messageClass =  Class.forName(className);
            logger.debug("message class is "+messageClass);
            if (MessageFromClient.class.isAssignableFrom(messageClass)) {
                MessageFromClient msg = objectMapper.readValue(message.getPayload(),(Class<? extends MessageFromClient>)messageClass);
                if (!(msg instanceof KeepAliveMessage)) {
                 game.handleMessage(msg);
                }
                else {
                    logger.debug("got keepalive message");
                }
            }

        }
        catch (ClassNotFoundException e) {
            logger.error(e);
        }
        }
        catch (IOException e) {
            logger.error(e);
        }
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.debug("closed connection");
        if (status == CloseStatus.NORMAL) {
            logger.debug("closed normal");
            UserLeftGame message = new UserLeftGame();
            message.setSession(session);
            playerConnectorOrGameRemover.handle(message);

        }
        else {
            logger.debug("closed bad");
            UserOccasionallyDisconnected message = new UserOccasionallyDisconnected();
            message.setSession(session);
            playerConnectorOrGameRemover.handle(message);
        }
    }
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.debug("error happened");
    }

}
