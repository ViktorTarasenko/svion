package omsu.svion.game.handler.impl;

import omsu.svion.dao.QuestionService;
import omsu.svion.entities.Question;
import omsu.svion.game.Game;
import omsu.svion.game.Player;
import omsu.svion.game.handler.GameMessageHandler;
import omsu.svion.game.model.QuestionModel;
import omsu.svion.game.states.WaitingForAnswer;
import omsu.svion.game.worker.PlayerConnectorOrGameRemover;
import omsu.svion.images.ImageLoader;
import omsu.svion.messages.*;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.security.SecureRandom;

/**
 * Created by victor on 12.05.14.
 */
@Service("themeAndCostChosen")
public class ThemeAndCostChosenHandler implements GameMessageHandler {
    @Autowired
    private ImageLoader imageLoader;
    @Autowired
    private PlayerConnectorOrGameRemover playerConnectorOrGameRemover;
    private static final Logger logger = Logger.getLogger(ThemeAndCostChosenHandler.class);
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TaskExecutor taskExecutor;
    public void handle(AbstractMessage message, Game game) {
        game.setState(WaitingForAnswer.class);
        logger.debug("got question number and category");
        ChooseThemeAndCostMessage chooseThemeAndCostMessage = (ChooseThemeAndCostMessage)message;
        logger.debug("cast message");
        int countOfPossibleQuestions = questionService.getCountOfRemainingQuestionsByThemeAndCost(chooseThemeAndCostMessage.getTheme(), chooseThemeAndCostMessage.getCost(),game.getUsedQuestionsIds()).intValue();
        logger.debug("questions available "+countOfPossibleQuestions);
        int questionNumber = Math.abs(new SecureRandom().nextInt()) % countOfPossibleQuestions;
        logger.debug("question number chosen "+questionNumber);
        final Question question = questionService.findNotUsedByThemeAndCost(chooseThemeAndCostMessage.getTheme(), chooseThemeAndCostMessage.getCost(),game.getUsedQuestionsIds(), questionNumber);
        logger.debug("question number chosen "+questionNumber);
        game.getUsedQuestionsIds().add(question.getId());
        logger.debug("removed category "+game.getAvailableCostsAndThemes().get(game.getTourNumber()-1).get(chooseThemeAndCostMessage.getTheme()).remove(chooseThemeAndCostMessage.getCost()));
        logger.debug("added new used id" + question.getId());
        QuestionModel questionModel = null;
        try {
            questionModel = new QuestionModel(question.getId(),imageLoader.loadByName(question.getImage()),question.getVar1(),question.getVar2(),question.getVar3(),question.getVar4(),question.getTimeForAnswer(),question.getCorrectAnswer(),question.getTheme(),question.getCost(),question.getText());
            logger.debug("question model constructed");
            game.setCurrentQuestion(questionModel);
            QuestionMessage questionMessage = new QuestionMessage(questionModel);
            logger.debug("question message constructed");
            try {
                TextMessage stringQuestionMessage = new TextMessage(objectMapper.writeValueAsString(questionMessage));
                for(Player player : game.getPlayers().values()) {
                 player.setAnsweredQuestion(false);
                 player.setIncrementScore(0);
                 if (player.getState() != Player.State.ONLINE) {
                     logger.debug("skipped player" + player);
                        continue;
                    }
                    player.getWebSocketSession().sendMessage(stringQuestionMessage);
                    logger.debug("sending finished");
                     }
            } catch (IOException e) {
                throw  new RuntimeException(e);
            }
            final  Game game1 = game;
            final WebSocketSession webSocketSession = message.getSession();
            Runnable everybodyAnsweredChecker = new Runnable() {
            public void run() {
                    try {
                     Thread.sleep(question.getTimeForAnswer());
                        if (this == game1.getEverybodyAnsweredChecker()) {
                         AnsweringFinishedMessage answeringFinishedMessage = new AnsweringFinishedMessage(webSocketSession);
                        game1.handleMessage(answeringFinishedMessage);
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
        };
        game.setEverybodyAnsweredChecker(everybodyAnsweredChecker);
        taskExecutor.execute(everybodyAnsweredChecker);
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
