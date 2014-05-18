package omsu.svion.game.handler.impl;

import android.content.Intent;
import android.graphics.Color;
import android.util.Pair;
import android.widget.TextView;
import com.svion.client.GameActivity;
import com.svion.client.QuestionActivity;
import com.svion.client.WaitingForEnoughPlayersActivity;
import omsu.svion.game.handler.MessageFromServerHandler;
import omsu.svion.messages.MessageFromServer;
import omsu.svion.messages.QuestionMessage;
import omsu.svion.questions.Cost;
import omsu.svion.questions.Theme;

/**
 * Created by victor on 16.05.14.
 */
public class QuestionMessageHandler implements MessageFromServerHandler {
    public QuestionMessageHandler(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    private GameActivity gameActivity;
    @Override
    public void handle(MessageFromServer message) {
        final QuestionMessage questionMessage = (QuestionMessage) message;
        final TextView textViewToHighLight = gameActivity.getTextViews().get(new Pair<Theme, Cost>(questionMessage.getQuestionModel().getTheme(),questionMessage.getQuestionModel().getCost()));
        final QuestionMessage questionMessage1 = questionMessage;
        textViewToHighLight.post(new Runnable() {
            @Override
            public void run() {
                textViewToHighLight.setBackgroundColor(Color.GREEN);
                for (TextView textView : gameActivity.getCosts()) {
                    textView.setEnabled(false);
                }
                try {
                    Thread.sleep(1000);//спим секунду, затем показываем game activity
                    Intent intent = new Intent(gameActivity, QuestionActivity.class);
                    intent.putExtra("question",questionMessage.getQuestionModel());
                    //gameActivity.finish();
                    gameActivity.startActivity(intent);


                } catch (InterruptedException e) {
                    return;
                }
            }
        });
       //далее стартовать через секунду вопрос активити
    }
}
