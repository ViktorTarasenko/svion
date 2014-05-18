package omsu.svion.game.handler.impl;

import omsu.svion.game.Game;
import omsu.svion.game.handler.GameMessageHandler;
import omsu.svion.messages.AbstractMessage;
import omsu.svion.messages.ChooseThemeAndCostMessage;
import omsu.svion.questions.Cost;
import omsu.svion.questions.Theme;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * Created by victor on 12.05.14.
 */
@Service("theme_and_cost_not_chosen")
public class ThemeAndCostNotChosenHandler implements GameMessageHandler {
    private static final Logger logger = Logger.getLogger(ThemeAndCostNotChosenHandler.class);
    public void handle(AbstractMessage message, Game game) {
        logger.debug("theme and cost not chosen, will choose automatically");
        SecureRandom secureRandom = new SecureRandom();
        Cost cost = null;
        Theme theme = null;
        do {
             theme = Theme.getByNumber(Math.abs(secureRandom.nextInt()) % 6);
        }while (game.getAvailableCostsAndThemes().get(game.getTourNumber()-1).get(theme).size() == 0);
        logger.debug("theme chosen "+theme);
         cost = game.getAvailableCostsAndThemes().get(game.getTourNumber()-1).get(theme).get(Math.abs(secureRandom.nextInt()) % game.getAvailableCostsAndThemes().get(game.getTourNumber()-1).get(theme).size());
        logger.debug("cost chosen "+cost);
        ChooseThemeAndCostMessage chooseThemeAndCostMessage = new ChooseThemeAndCostMessage(cost,theme);
        chooseThemeAndCostMessage.setSession(message.getSession());
        game.handleMessage(chooseThemeAndCostMessage);
        logger.debug("cost handling this ");

    }
}
