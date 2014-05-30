package omsu.svion.game.handler.impl;

import omsu.svion.game.Game;
import omsu.svion.game.Player;
import omsu.svion.game.handler.GameMessageHandler;
import omsu.svion.game.model.QuestionModel;
import omsu.svion.game.states.ChoosingCategory;
import omsu.svion.game.states.Finished;
import omsu.svion.game.worker.PlayerConnectorOrGameRemover;
import omsu.svion.messages.*;
import omsu.svion.model.json.converter.PlayerConverter;
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
 * Created by victor on 12.05.14.
 */
@Service("answer_message")
public class AnswerMessageHandler implements GameMessageHandler {
    @Autowired
    private PlayerConnectorOrGameRemover playerConnectorOrGameRemover;
    private PlayerConverter playerConverter = new PlayerConverter();
    @Autowired
    private ObjectMapper objectMapper;
    private static final Logger logger = Logger.getLogger(AnswerMessageHandler.class);
    public void handle(AbstractMessage message, Game game) {
        logger.debug("somebody answered question");
        AnswerMessage answerMessage = (AnswerMessage) message;
        QuestionModel questionModel = game.getCurrentQuestion();
        logger.debug(questionModel+" current question");
        Player player = game.getPlayer(message.getSession().getPrincipal().getName());
        player.setAnsweredQuestion(true);
        if (answerMessage.getVariant() == questionModel.getCorrectAnswer()) {
            logger.debug(player.getEmail()+" answered correctly");
            player.setIncrementScore(questionModel.getCost().getCost());
            logger.debug("incremented score "+questionModel.getCost().getCost());
        }
        else {
            logger.debug(player.getEmail()+" answered wrong");
            player.setIncrementScore(0);
        }
        if (isAllAnswered(game)) {
            logger.debug("all answered, firing handler for it");
            AnsweringFinishedMessage answeringFinishedMessage = new AnsweringFinishedMessage(message.getSession(),questionModel.getCorrectAnswer());
            game.handleMessage(answeringFinishedMessage);
        }

    }
    private boolean isAllAnswered(Game game) {
        for (Player player : game.getPlayers().values()) {
            if (!player.isAnsweredQuestion()) {
                return false;
            }
        }
        return  true;
    }
}
