package omsu.svion.messages;

import omsu.svion.game.model.PlayerModel;
import omsu.svion.questions.Cost;
import omsu.svion.questions.Theme;

import java.util.List;
import java.util.Map;

/**
 * Created by victor on 12.05.14.
 */
public class ChooseThemeAndCostRequestMessage extends MessageFromServer {


    public ChooseThemeAndCostRequestMessage(List<PlayerModel> players, Map<Theme, List<Cost>> availableThemesAndCosts, boolean answer, Integer newRound, int currentQuestion) {
        this.players = players;
        this.availableThemesAndCosts = availableThemesAndCosts;
        this.answer = answer;
        this.newRound = newRound;
        this.currentQuestion = currentQuestion;
    }

    private List<PlayerModel> players;
    private Map<Theme,List<Cost>> availableThemesAndCosts;
    private boolean answer;
    private Integer newRound;
    private int currentQuestion;

    public Map<Theme, List<Cost>> getAvailableThemesAndCosts() {
        return availableThemesAndCosts;
    }

    public boolean isAnswer() {
        return answer;
    }

    public Integer getNewRound() {
        return newRound;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }
}
