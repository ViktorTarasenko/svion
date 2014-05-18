package omsu.svion.messages;


import omsu.svion.game.model.QuestionModel;

/**
 * Created by victor on 12.05.14.
 */
public class QuestionMessage extends MessageFromServer {
    public QuestionMessage(QuestionModel questionModel) {
        this.questionModel = questionModel;
        this.className =  QuestionMessage.class.getCanonicalName();
    }

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
}
