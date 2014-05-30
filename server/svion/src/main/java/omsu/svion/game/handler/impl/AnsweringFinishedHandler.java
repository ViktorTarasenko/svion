package omsu.svion.game.handler.impl;

import omsu.svion.dao.AccountService;
import omsu.svion.entities.Account;
import omsu.svion.game.Game;
import omsu.svion.game.Player;
import omsu.svion.game.handler.GameMessageHandler;
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
@Service("answering_finished")
public class AnsweringFinishedHandler implements GameMessageHandler {
    private static final Logger logger = Logger.getLogger(AnsweringFinishedHandler.class);
    private PlayerConverter playerConverter = new PlayerConverter();
    private SecureRandom secureRandom = new SecureRandom();
    @Autowired
    private AccountService accountService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PlayerConnectorOrGameRemover playerConnectorOrGameRemover;
    @Autowired
    private TaskExecutor taskExecutor;
     public void handle(AbstractMessage message, Game game) {
         AnsweringFinishedMessage answeringFinishedMessage = (AnsweringFinishedMessage) message;
        logger.debug("answering finished");
         for (Player p : game.getPlayers().values()) {
             p.setScore(p.getScore()+p.getIncrementScore());
             logger.debug("incremented score "+p.getIncrementScore());
             // p.setIncrementScore(0);
         }
         int newQuestionNumber = game.getCurrentQuestionNumber() + 1;
         logger.debug("new question number "+newQuestionNumber);
         Integer newRound = newQuestionNumber % 2 == 0 ? game.getTourNumber() +1 : null;
         logger.debug("new round "+newRound);
         if((newRound == null) || (newRound <=2)) {
             game.setState(ChoosingCategory.class);
             logger.debug("now choosing category");
             game.setCurrentQuestionNumber(newQuestionNumber);
             if (newRound != null) {
                 game.setTourNumber(newRound);
             }
             Player nextPlayerForChoosingThemeAndCost = getNextPlayerForChoosingThemeAndCost(game);
             ChooseThemeAndCostRequestMessage msg =  new ChooseThemeAndCostRequestMessage(playerConverter.convert(new ArrayList<Player>(game.getPlayers().values())),game.getAvailableCostsAndThemes().get(game.getTourNumber()-1),false,newRound,game.getCurrentQuestionNumber(),nextPlayerForChoosingThemeAndCost.getEmail(),((AnsweringFinishedMessage) message).getRightAnswer());
             ChooseThemeAndCostRequestMessage messageForAnswering = new ChooseThemeAndCostRequestMessage(playerConverter.convert(new ArrayList<Player>(game.getPlayers().values())),game.getAvailableCostsAndThemes().get(game.getTourNumber()-1),true,newRound,game.getCurrentQuestionNumber(),nextPlayerForChoosingThemeAndCost.getEmail(),((AnsweringFinishedMessage) message).getRightAnswer());
             logger.debug(nextPlayerForChoosingThemeAndCost+" this user will choose theme and cost now");
             game.setPreviousAnsweredPlayer(nextPlayerForChoosingThemeAndCost);
             try {
                 TextMessage msgText = new TextMessage(objectMapper.writeValueAsString(msg));
                 TextMessage msgForAnswerText = new TextMessage(objectMapper.writeValueAsString(messageForAnswering));
                 for (Player p : game.getPlayers().values()) {
                     p.setIncrementScore(0);
                     if (p.getState() != Player.State.ONLINE) {
                         continue;
                     }
                     try {
                         if (p == nextPlayerForChoosingThemeAndCost) {
                             logger.debug("this user will answer, sending to him "+p);
                             p.getWebSocketSession().sendMessage(msgForAnswerText);
                         }
                         else {
                             p.getWebSocketSession().sendMessage(msgText);
                         }
                     } catch (IOException e) {
                         UserOccasionallyDisconnected userOccasionallyDisconnected = new UserOccasionallyDisconnected();
                         userOccasionallyDisconnected.setSession(message.getSession());
                         playerConnectorOrGameRemover.handle(userOccasionallyDisconnected);
                     }

                 }
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
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
         else { //завершаем игру, записываем очки, туры закончились
             logger.debug("now game will be finished");
             Account account;
             GameStateUpdateMessage gameStateUpdateMessage = new GameStateUpdateMessage(playerConverter.convert(new ArrayList<Player>(game.getPlayers().values())),Finished.class);
             gameStateUpdateMessage.getIntegerParams().put("rightAnswer",((AnsweringFinishedMessage) message).getRightAnswer());
             try {
                 TextMessage msgText = new TextMessage(objectMapper.writeValueAsString(gameStateUpdateMessage));
                 for (Player p : game.getPlayers().values()) {
                     account = accountService.findByEmail(p.getEmail());
                     accountService.setTotalScore(account.getId(),account.getTotalScore() + p.getScore());
                     logger.debug("new score for account "+account.getEmail()+" is"+account.getTotalScore() + p.getScore());
                     p.setIncrementScore(0);
                     if (p.getState() != Player.State.ONLINE) {
                         continue;
                     }
                     try {
                         p.getWebSocketSession().sendMessage(msgText);
                     } catch (IOException e) {
                         UserOccasionallyDisconnected userOccasionallyDisconnected = new UserOccasionallyDisconnected();
                         userOccasionallyDisconnected.setSession(message.getSession());
                         playerConnectorOrGameRemover.handle(userOccasionallyDisconnected);
                     }

                 }
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
             game.setState(Finished.class);
             RemoveGameRequest removeGameRequest = new RemoveGameRequest(game);
             removeGameRequest.setSession(message.getSession());
             playerConnectorOrGameRemover.handle(removeGameRequest);
         }
    }
    private Player  getNextPlayerForChoosingThemeAndCost(Game game) {
        int number = Math.abs(secureRandom.nextInt()) %  game.getPlayers().values().size();
        int i = 0;
        for (Player player : game.getPlayers().values()) {
            if (i == number) {
                return player;
            }
            ++i;
        }
        return null;

    }
}
