package omsu.svion.messages;


import omsu.svion.game.model.PlayerModel;
import omsu.svion.game.model.QuestionModel;

import java.util.List;

/**
 * Created by victor on 12.05.14.
 */
public class QuestionMessage extends MessageFromServer {
    public QuestionMessage(QuestionModel questionModel,List<PlayerModel> players) {
        this.questionModel = questionModel;
        this.players = players;
        this.className =  QuestionMessage.class.getCanonicalName();
    }
    private List<PlayerModel> players;

    private QuestionModel questionModel;
    public QuestionModel getQuestionModel() {
        return questionModel;
    }

    public void setQuestionModel(QuestionModel questionModel) {
        this.questionModel = questionModel;
    }

    public QuestionMessage() {
        this.className =  QuestionMessage.class.getCanonicalName();
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerModel> players) {
        this.players = players;
    }
}
